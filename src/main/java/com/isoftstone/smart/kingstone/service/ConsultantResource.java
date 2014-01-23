package com.isoftstone.smart.kingstone.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.inject.Inject;
import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.security.CloudRealm;
import com.isoftstone.smart.core.service.BaseService;
import com.isoftstone.smart.kingstone.business.AccountService;
import com.isoftstone.smart.kingstone.business.ConsultantService;
import com.isoftstone.smart.kingstone.business.ConsultantlocationService;
import com.isoftstone.smart.kingstone.entity.Consultant;
import com.isoftstone.smart.kingstone.entity.ConsultantLocation;

@Path("/consultant")
@Produces(MediaType.APPLICATION_JSON)
public class ConsultantResource extends BaseService<Consultant> {

  private final Logger logger = Logger.getLogger(ConsultantResource.class.getName());

  @Inject
  AccountService aDao;

  @Inject
  ConsultantlocationService clDao;

  @Inject
  ConsultantService conDao;

  /***
   * assign an account to a consultant
   * 
   * @param consultant
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/assignaccount")
  @Consumes(MediaType.APPLICATION_JSON)
  public String assignaccount(Consultant consultant) throws JSONException {
    logger.info("register begins");
    JSONObject result = new JSONObject();

    String cid = consultant.getId();

    try {
      if (cid != null) {
        Consultant con = conDao.getById(cid);
        if (con == null) { // judgement of whether consultant exists or not
          result.put("msg", "该顾问信息不存在");
          result.put("status", 1);
          return result.toString();
        } else {
          int count = conDao.getByConno(consultant.getConsultantno());

          if (count > 0) { // judgement of whether consultant exists or not
            result.put("msg", "该顾问已存在登录帐号");
            result.put("status", 1);
            return result.toString();
          }
          Account account = new Account();
          account.setFullname(consultant.getName());
          account.setLoginId(consultant.getConsultantno());
          account.setLevel(3);
          account.setPasswordHash(new SimpleHash(CloudRealm.ALGORITHM_NAME, consultant.getPassword()).toHex());
          aDao.save(account);

          consultant.setAccount(account);// set consultant account

          conDao.save(consultant); // save consultant to db

          result.put("msg", "成功!");
          result.put("status", 0);

        }
      } else {
        result.put("msg", "传入数据有错误!");
        result.put("status", 1);
      }

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    logger.info("register ends");
    return result.toString();
  }

  /***
   * get consultant and his customers information for web page info.html
   * 
   * @return
   * @throws JSONException
   */

  @GET
  @Path("/chartlist")
  @Consumes(MediaType.APPLICATION_JSON)
  public String chartlist(@QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize, @QueryParam("name") String name,
      @QueryParam("accountid") String accountid) throws JSONException {
    logger.info("chartlist begins");
    JSONObject result = new JSONObject();
    JSONArray conArray = new JSONArray();

    List<Consultant> cons = new ArrayList<Consultant>();

    int total = 0;
    try {

      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", "");
        return result.toString();
      }
      int level = user.getLevel();

      if (level == 0) {
        cons = conDao.chartlist(pageNo, pageSize, name);
        total = conDao.chartlisttotal(name);
      } else if (level == 1) {
        // FIXME district manager code here
      } else if (level == 2) {
        cons = conDao.chartlistbyleader(pageNo, pageSize, name, accountid);
        total = conDao.chartlisttotalbyleader(name, accountid);
      } else if (level > 2) {
        result.put("status", 1);
        result.put("msg", "您所在的用户组没有该权限");
        result.put("items", "");
        return result.toString();
      }

      if (cons == null || cons.size() == 0) {
        result.put("status", 1);
        result.put("msg", "暂时没有相关信息");
        result.put("items", conArray);
        return result.toString();
      }

      for (Consultant con : cons) {
        JSONObject c = new JSONObject();
        c.put("consultantno", con.getConsultantno());
        c.put("id", con.getId());
        c.put("name", con.getName());
        c.put("", con.getMobile());
        conArray.put(c);
      }
      result.put("status", 0);
      result.put("msg", "查询成功!");
      result.put("items", conArray);
      result.put("total", total);
      result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
      result.put("items", new JSONArray());
      result.put("total", total);
      result.put("page", 0);
    }
    logger.info("chartlist ends");
    return result.toString();
  }

  /***
   * new consultant information for consultant management
   * 
   * @param consultant
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/collectInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectInfo(Consultant consultant) throws JSONException {
    logger.info("consultant collectInfo begins");

    JSONObject result = new JSONObject();

    try {
      // consultant.setGeneratetime(new Timestamp(System.currentTimeMillis()));

      conDao.save(consultant);
      result.put("status", 0);
      result.put("msg", "保存成功!");

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误!");
    }

    logger.info("consultant collectInfo ends");
    return result.toString();
  }

  /***
   * delete consultant login information for consultant management
   * 
   * @param consultant
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/delete")
  @Consumes(MediaType.APPLICATION_JSON)
  public String deleteConsultant(Consultant consultant) throws JSONException {
    logger.info("delete consultant begins");
    JSONObject result = new JSONObject();

    try {
      if (consultant.getId() != null) {
        Consultant con = conDao.getConsultantWithAccount(consultant.getId());
        if (con == null) {
          result.put("status", 1);
          result.put("msg", "该用户不存在,不需要删除!");
        } else {
          Account user = con.getAccount();
          if (user != null) {
            con.setAccount(null);
            conDao.save(con);
            aDao.delete(user);
            result.put("status", 0);
            result.put("msg", "信息已删除!");
          } else {
            result.put("status", 1);
            result.put("msg", "该用户不存在!");
          }
        }
      }
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误!");
    }
    logger.info("delete consultant ends");
    return result.toString();
  }

  @Override
  public Class<Consultant> getEntityType() {
    // TODO Auto-generated method stub
    return Consultant.class;
  }

  /***
   * consultant list for consultant management
   * 
   * @return
   * @throws JSONException
   */

  @GET
  @Path("/list")
  @Consumes(MediaType.APPLICATION_JSON)
  public String list() throws JSONException {
    logger.info("consultant list begins");
    JSONObject result = new JSONObject();
    JSONArray conArray = new JSONArray();
    try {
      List<Consultant> cons = conDao.list();
      if (cons == null || cons.size() == 0) {
        result.put("status", 1);
        result.put("msg", "暂时没有相关信息");
        result.put("items", conArray);
        return result.toString();
      }

      for (Consultant con : cons) {
        JSONObject c = new JSONObject();
        c.put("certificate", con.getCertificate());
        c.put("consultantno", con.getConsultantno());
        c.put("experience", con.getExperience());
        c.put("feature", con.getFeature());
        c.put("id", con.getId());
        c.put("degree", con.getDegree());
        c.put("email", con.getEmail());
        c.put("mobile", con.getMobile());
        c.put("name", con.getName());
        c.put("gender", con.getGender());
        c.put("title", con.getTitle());
        Account account = con.getAccount();
        if (account != null) {
          c.put("loginid", account.getLoginId());
          c.put("accountid", account.getId());
        } else {
          c.put("loginid", "");
          c.put("accountid", "");
        }
        conArray.put(c);
      }

      result.put("status", 0);
      result.put("msg", "查询成功!");
      result.put("items", conArray);
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
      result.put("items", new JSONArray());
    }
    logger.info("consultant list ends");
    return result.toString();
  }

  /***
   * modify consultant information for consultant management
   * 
   * @param consultant
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/modify")
  @Consumes(MediaType.APPLICATION_JSON)
  public String modify(Consultant consultant) throws JSONException {
    logger.info("consultant modify begins");

    JSONObject result = new JSONObject();

    try {
      if (consultant.getId() != null) {
        Consultant con = conDao.getById(consultant.getId());
        if (con == null) {
          result.put("status", 1);
          result.put("msg", "该用户不存在!");
        } else {
          Account user = con.getAccount();
          if (user != null) {
            user.setFullname(consultant.getName());
            aDao.save(user);
          }

          con.setCertificate(consultant.getCertificate());
          con.setConsultantno(consultant.getConsultantno());
          con.setExperience(consultant.getExperience());
          con.setFeature(consultant.getFeature());
          con.setName(consultant.getName());
          con.setDegree(consultant.getDegree());
          con.setMobile(consultant.getMobile());
          con.setEmail(consultant.getEmail());
          con.setTitle(consultant.getTitle());
          con.setGender(consultant.getGender());
          conDao.save(con);
          result.put("status", 0);
          result.put("msg", "修改成功!");
        }
      }
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误!");
    }

    logger.info("consultant modify ends");
    return result.toString();
  }

  @GET
  @Path("/route")
  public String route(@QueryParam("consultantid") String consultantid, @QueryParam("timefrom") String timefrom,
      @QueryParam("timeto") String timeto) throws JSONException, ParseException {
    JSONObject result = new JSONObject();
    JSONArray cArray = new JSONArray();

    try {
      List<ConsultantLocation> locations = clDao.route(consultantid, timefrom + " 00:00:00", timeto + "23:59:59");
      if (locations != null && locations.size() > 0) {
        for (ConsultantLocation cl : locations) {
          JSONObject c = new JSONObject();
          c.put("generatetime", String.valueOf(cl.getGeneratetime()));
          c.put("location", cl.getLocation());
          c.put("address", cl.getAddress());
          cArray.put(c);
        }

        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", cArray);
      } else {
        result.put("status", 1);
        result.put("msg", "暂时没有数据");
        result.put("items", new JSONArray());
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
    }
    return result.toString();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/track")
  public String track(ConsultantLocation cl) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      clDao.save(cl);
      result.put("status", 0);
      result.put("msg", "保存成功");
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "保存失败");
    }

    return result.toString();
  }

  /***
   * get consultant and his customers information for web page info.html
   * 
   * @return
   * @throws JSONException
   */

  @GET
  @Path("/webconsultantinfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String webconsultantinfo(@QueryParam("consultantid") String consultantid) throws JSONException {
    logger.info("webconsultantinfo begins");
    JSONObject result = new JSONObject();

    try {
      Consultant con = conDao.getConsultantWithAccount(consultantid);
      if (con == null) {
        result.put("status", 1);
        result.put("msg", "暂时没有相关信息");
        result.put("item", new JSONObject());
        return result.toString();
      }

      JSONObject c = new JSONObject();
      c.put("certificate", con.getCertificate());
      c.put("consultantno", con.getConsultantno());
      c.put("experience", con.getExperience());
      c.put("feature", con.getFeature());
      c.put("degree", con.getDegree());
      c.put("email", con.getEmail());
      c.put("mobile", con.getMobile());
      c.put("id", con.getId());
      c.put("name", con.getName());
      c.put("gender", con.getGender());
      c.put("title", con.getTitle());
      Account account = con.getAccount();
      if (account != null) {
        c.put("loginid", account.getLoginId());
        c.put("accountid", account.getId());
      } else {
        c.put("loginid", "");
        c.put("accountid", "");
      }

      result.put("status", 0);
      result.put("msg", "查询成功!");
      result.put("item", c);
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
      result.put("item", new JSONObject());
    }
    logger.info("webconsultantinfo ends");
    return result.toString();
  }

  /***
   * get consultant and his customers information for web page info.html
   * 
   * @return
   * @throws JSONException
   */

  @GET
  @Path("/weblist")
  @Consumes(MediaType.APPLICATION_JSON)
  public String weblist(@QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize, @QueryParam("name") String name)
      throws JSONException {
    logger.info("weblist begins");
    JSONObject result = new JSONObject();
    JSONArray conArray = new JSONArray();
    int total = 0;
    try {
      List<Consultant> cons = conDao.list(pageNo, pageSize, "-1", name, -1);
      total = conDao.total("-1", name, -1);
      if (cons == null || cons.size() == 0) {
        result.put("status", 1);
        result.put("msg", "暂时没有相关信息");
        result.put("items", conArray);
        return result.toString();
      }

      for (Consultant con : cons) {
        JSONObject c = new JSONObject();
        c.put("certificate", con.getCertificate());
        c.put("consultantno", con.getConsultantno());
        c.put("experience", con.getExperience());
        c.put("feature", con.getFeature());
        c.put("id", con.getId());
        c.put("degree", con.getDegree());
        c.put("email", con.getEmail());
        c.put("mobile", con.getMobile());
        c.put("name", con.getName());
        c.put("gender", con.getGender());
        c.put("title", con.getTitle());
        Account account = con.getAccount();
        if (account != null) {
          c.put("loginid", account.getLoginId());
          c.put("accountid", account.getId());
        } else {
          c.put("loginid", "");
          c.put("accountid", "");
        }
        conArray.put(c);
      }

      result.put("status", 0);
      result.put("msg", "查询成功!");
      result.put("items", conArray);
      result.put("total", total);
      result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
      result.put("items", new JSONArray());
      result.put("total", total);
      result.put("page", 0);
    }
    logger.info("weblist ends");
    return result.toString();
  }
}
