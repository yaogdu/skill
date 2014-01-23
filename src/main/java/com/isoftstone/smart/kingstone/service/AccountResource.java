package com.isoftstone.smart.kingstone.service;

import java.sql.Timestamp;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.inject.Inject;
import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.security.CloudRealm;
import com.isoftstone.smart.core.service.BaseService;
import com.isoftstone.smart.kingstone.business.AccountService;
import com.isoftstone.smart.kingstone.business.ConsultantService;
import com.isoftstone.smart.kingstone.entity.Consultant;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource extends BaseService<Account> {

  private final Logger logger = Logger.getLogger(AccountResource.class.getName());
  @Inject
  AccountService aDao;

  @Inject
  ConsultantService conDao;

  @POST
  @Path("/adminlogin")
  @Consumes(MediaType.APPLICATION_JSON)
  public String adminlogin(Account account) throws JSONException {
    logger.info("adminlogin begins");
    JSONObject result = new JSONObject();
    try {
      Account user = aDao.getAdminLoginId(account.getLoginId());
      if (user == null) {
        result.put("msg", "该用户不存在!");
        result.put("status", 1);
        return result.toString();
      }

      String hashedPwd = new SimpleHash(CloudRealm.ALGORITHM_NAME, account.getPasswordHash()).toHex();
      if (user.getPasswordHash().equals(hashedPwd)) {
        result.put("msg", "登录成功!");
        result.put("status", 0);
        return result.toString();
      } else {
        result.put("msg", "用户名密码错误!");
        result.put("status", 1);
        return result.toString();
      }

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
      result.put("item", new JSONObject());
    }
    logger.info("adminlogin ends");
    return result.toString();
  }

  @POST
  @Path("/adminregister")
  @Consumes(MediaType.APPLICATION_JSON)
  public String adminregister(Account account) throws JSONException {
    logger.info("adminregister begins");
    JSONObject result = new JSONObject();

    String cid = account.getId();

    try {
      if (cid != null) {
        Account user = aDao.getById(cid);
        if (user != null) { // judgement of whether consultant exists or not
          result.put("msg", "该用户已存在");
          result.put("status", 1);
          return result.toString();
        }
      }

      Account user = aDao.getAdminLoginId(account.getLoginId());

      if (user != null) {
        result.put("msg", "该用户已存在");
        result.put("status", 1);
        return result.toString();
      }
      account.setLevel(0);
      account.setFullname("admin");
      String password = account.getPasswordHash();
      account.setPasswordHash(new SimpleHash(CloudRealm.ALGORITHM_NAME, password).toHex());
      aDao.save(account);
      result.put("msg", "注册成功!");
      result.put("status", 0);
      return result.toString();

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    logger.info("adminregister ends");
    return result.toString();
  }

  @GET
  @Path("/test/{companyname}")
  public String get(@PathParam("companyname") String companyname) throws JSONException {
    JSONObject result = new JSONObject();

    result.put("msg", "成功!");
    result.put("companyname", companyname);
    result.put("status", 0);
    return result.toString();
  }

  @GET
  @Path("/test2")
  public String get2(@QueryParam("companyname") String companyname) throws JSONException {
    JSONObject result = new JSONObject();

    result.put("msg", "成功!");
    result.put("companyname", companyname);
    result.put("status", 0);
    return result.toString();
  }

  @Override
  public Class<Account> getEntityType() {
    // TODO Auto-generated method stub
    return Account.class;
  }

  /***
   * sales login
   * 
   * @param account
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  public String login(Account account) throws JSONException {
    logger.info("login begins");
    JSONObject result = new JSONObject();
    try {
      Account user = aDao.getByLoginId(account.getLoginId());
      if (user == null) {
        result.put("msg", "该用户不存在!");
        result.put("status", 1);
        return result.toString();
      }

      String hashedPwd = new SimpleHash(CloudRealm.ALGORITHM_NAME, account.getPassword()).toHex();
      if (user.getPasswordHash().equals(hashedPwd)) {
        try {
          Consultant con = conDao.getByAccountId(user.getId());
          if (con == null) {
            result.put("msg", "该用户不存在!");
            result.put("status", 1);
            return result.toString();
          } else {
            JSONObject objCon = new JSONObject();
            objCon.put("certificate", con.getCertificate());
            objCon.put("consultantno", con.getConsultantno());
            objCon.put("experience", con.getExperience());
            objCon.put("feature", con.getFeature());
            objCon.put("id", con.getId());
            objCon.put("degree", con.getDegree());
            objCon.put("email", con.getEmail());
            objCon.put("mobile", con.getMobile());
            objCon.put("name", con.getName());
            objCon.put("title", con.getTitle());
            objCon.put("gender", con.getGender());
            result.put("item", objCon);
            result.put("msg", "登录成功!");
            result.put("status", 0);
            return result.toString();
          }
        } catch (Exception e) {
          result.put("msg", "发生错误!");
          result.put("status", 1);
          result.put("item", new JSONObject());
        }
      } else {
        result.put("msg", "用户名密码错误!");
        result.put("status", 1);
        return result.toString();
      }

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
      result.put("item", new JSONObject());
    }
    logger.info("adminregister ends");
    return result.toString();
  }

  @POST
  @Path("/register")
  @Consumes(MediaType.APPLICATION_JSON)
  public String register(Consultant consultant) throws JSONException {
    logger.info("register begins");
    JSONObject result = new JSONObject();

    String cid = consultant.getId();

    try {
      if (cid != null) {
        Consultant con = conDao.getById(cid);
        if (con != null) { // judgement of whether consultant exists or not
          result.put("msg", "该顾问已存在");
          result.put("status", 1);
          return result.toString();
        }
      }

      int count = conDao.getByConno(consultant.getConsultantno());

      if (count > 0) { // judgement of whether consultant exists or not
        result.put("msg", "该顾问编号已存在");
        result.put("status", 1);
        return result.toString();
      }
      Account account = new Account();
      account.setFullname(consultant.getName());
      account.setLoginId(consultant.getConsultantno());
      account.setLevel(3);
      account.setPasswordHash(new SimpleHash(CloudRealm.ALGORITHM_NAME, consultant.getPassword()).toHex());
      aDao.save(account);

      consultant.setGeneratetime(new Timestamp(System.currentTimeMillis()));
      consultant.setAccount(account);// set consultant account

      conDao.save(consultant); // save consultant to db

      result.put("msg", "注册成功!");
      result.put("status", 0);
      return result.toString();

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    logger.info("register ends");
    return result.toString();
  }

  @POST
  @Path("/resetconsultantpass")
  @Consumes(MediaType.APPLICATION_JSON)
  public String resetconsultantpass(Consultant consultant) throws JSONException {
    JSONObject result = new JSONObject();
    logger.info("resetconsultantpass begins");
    try {
      Consultant con = conDao.getConsultantWithAccount(consultant.getId());
      if (con == null) {
        result.put("msg", "该顾问信息不存在!");
        result.put("status", 1);
        return result.toString();
      }

      Account user = con.getAccount();

      if (user != null) {
        user.setPasswordHash(new SimpleHash(CloudRealm.ALGORITHM_NAME, consultant.getPassword()).toHex());
        aDao.save(user);
        result.put("msg", "修改成功!");
        result.put("status", 0);
        return result.toString();
      } else {
        result.put("msg", "用户不存在!");
        result.put("status", 1);
        return result.toString();
      }

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    logger.info("resetconsultantpass ends");
    return result.toString();
  }

  @POST
  @Path("/resetpassword")
  @Consumes(MediaType.APPLICATION_JSON)
  public String resetpassword(Account account) throws JSONException {
    JSONObject result = new JSONObject();
    logger.info("resetpassword begins");
    try {

      Account user = aDao.getByLoginId(account.getLoginId());

      if (user != null) {
        user.setPasswordHash(new SimpleHash(CloudRealm.ALGORITHM_NAME, account.getPasswordHash()).toHex());
        aDao.save(user);
        result.put("msg", "修改成功!");
        result.put("status", 0);
      } else {
        result.put("msg", "用户不存在!");
        result.put("status", 1);
      }

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    logger.info("resetpassword ends");
    return result.toString();
  }

  @POST
  @Path("/webadd")
  @Consumes(MediaType.APPLICATION_JSON)
  public String webadd(Consultant consultant) throws JSONException {
    logger.info("webadd begins");
    JSONObject result = new JSONObject();
    try {
      int count = conDao.getByConno(consultant.getConsultantno());

      if (count > 0) { // judgement of whether consultant exists or not
        result.put("msg", "该顾问编号已存在");
        result.put("status", 1);
        return result.toString();
      }
      Account account = new Account();
      System.out.println("consultant name==========================" + consultant.getName());
      account.setFullname(consultant.getName());
      account.setLoginId(consultant.getConsultantno());
      account.setLevel(3);
      account.setPasswordHash(new SimpleHash(CloudRealm.ALGORITHM_NAME, consultant.getPassword()).toHex());
      aDao.save(account);

      consultant.setGeneratetime(new Timestamp(System.currentTimeMillis()));

      consultant.setAccount(account);// set consultant account

      conDao.save(consultant); // save consultant to db

      result.put("msg", "添加成功!");
      result.put("status", 0);
      return result.toString();

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    logger.info("webadd ends");
    return result.toString();
  }

  /***
   * sales login
   * 
   * @param account
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/weblogin")
  @Consumes(MediaType.APPLICATION_JSON)
  public String weblogin(Account account) throws JSONException {
    logger.info("weblogin begins");
    JSONObject result = new JSONObject();
    try {
      Account user = aDao.webLogin(account.getLoginId());
      if (user == null) {
        result.put("msg", "该用户不存在!");
        result.put("status", 1);
        return result.toString();
      }

      String hashedPwd = new SimpleHash(CloudRealm.ALGORITHM_NAME, account.getPasswordHash()).toHex();
      if (user.getPasswordHash().equals(hashedPwd)) {
        try {

          if (user.getLevel() <= 2) {
            JSONObject u = new JSONObject();
            u.put("level", user.getLevel());
            u.put("fullname", user.getFullname());
            u.put("id", user.getId());
            u.put("loginid", user.getLoginId());
            result.put("msg", "登录成功!");
            result.put("status", 0);
            result.put("item", u);
            return result.toString();
          } else {
            result.put("msg", "没有权限!");
            result.put("status", 1);
            result.put("item", new JSONObject());
            return result.toString();
          }
          // }
        } catch (Exception e) {
          result.put("msg", "发生错误!");
          result.put("status", 1);
          result.put("item", new JSONObject());
        }
      } else {
        result.put("msg", "用户名密码错误!");
        result.put("status", 1);
        result.put("item", new JSONObject());
        return result.toString();
      }

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
      result.put("item", new JSONObject());
    }
    logger.info("weblogin ends");
    return result.toString();
  }

  @POST
  @Path("/webresetpass")
  @Consumes(MediaType.APPLICATION_JSON)
  public String webresetpass(Account account) throws JSONException {
    JSONObject result = new JSONObject();
    logger.info("webresetpass begins");
    try {

      Account user = aDao.getById(account.getId());

      if (user != null) {
        String password = new SimpleHash(CloudRealm.ALGORITHM_NAME, account.getPassword()).toHex();
        if (!password.equals(user.getPasswordHash())) {
          result.put("msg", "原始密码不正确!");
          result.put("status", 1);
          return result.toString();
        } else {
          user.setPasswordHash(new SimpleHash(CloudRealm.ALGORITHM_NAME, account.getPasswordHash()).toHex());
          aDao.save(user);
          result.put("msg", "修改成功!");
          result.put("status", 0);
          return result.toString();
        }

      } else {
        result.put("msg", "用户不存在!");
        result.put("status", 1);
      }

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    logger.info("webresetpass ends");
    return result.toString();
  }

  @POST
  @Path("/websave")
  @Consumes(MediaType.APPLICATION_JSON)
  public String websave(Consultant consultant) throws JSONException {
    logger.info("websave begins");
    JSONObject result = new JSONObject();
    try {

      String id = consultant.getId();

      if (id != null && !"".equals(id)) {
        Consultant con = conDao.getById(id);
        if (con == null) {
          result.put("msg", "该用户不存在");
          result.put("status", 1);
          return result.toString();
        } else {
          Account user = con.getAccount();
          if (user != null) {
            user.setFullname(consultant.getName());
            aDao.save(user);
          }

          con.setCertificate(consultant.getCertificate());
          con.setDegree(consultant.getDegree());
          con.setEmail(consultant.getEmail());
          con.setExperience(consultant.getExperience());
          con.setFeature(consultant.getFeature());
          con.setGender(consultant.getGender());
          con.setMobile(consultant.getMobile());
          con.setName(consultant.getName());
          con.setTitle(consultant.getTitle());
          conDao.save(con);
          result.put("msg", "保存成功");
          result.put("status", 0);
          return result.toString();
        }
      } else {
        result.put("msg", "该用户不存在");
        result.put("status", 1);
        return result.toString();
      }

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    logger.info("websave ends");
    return result.toString();
  }
}
