package com.isoftstone.smart.kingstone.service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.inject.Inject;
import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.service.BaseService;
import com.isoftstone.smart.kingstone.business.AccountService;
import com.isoftstone.smart.kingstone.business.ConsultantService;
import com.isoftstone.smart.kingstone.business.ConsultantlocationService;
import com.isoftstone.smart.kingstone.business.CustomerService;
import com.isoftstone.smart.kingstone.business.DocService;
import com.isoftstone.smart.kingstone.business.GroupMemberService;
import com.isoftstone.smart.kingstone.business.GroupService;
import com.isoftstone.smart.kingstone.entity.Consultant;
import com.isoftstone.smart.kingstone.entity.ConsultantLocation;
import com.isoftstone.smart.kingstone.entity.Customer;
import com.isoftstone.smart.kingstone.entity.Doc;
import com.isoftstone.smart.kingstone.entity.Group;
import com.isoftstone.smart.kingstone.entity.GroupMember;
import com.isoftstone.smart.kingstone.entity.Risk;

@Path("/group")
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource extends BaseService<Group> {

  @Inject
  GroupService gDao;

  @Inject
  GroupMemberService gmDao;

  @Inject
  DocService dDao;

  @Inject
  AccountService aDao;

  @Inject
  ConsultantService cDao;

  @Inject
  CustomerService cusDao;

  @Inject
  ConsultantlocationService clDao;

  @GET
  @Path("/allcustomer")
  @Produces(MediaType.APPLICATION_JSON)
  public String allcustomer(@QueryParam("accountid") String accountid, @QueryParam("pageNo") int pageNo,
      @QueryParam("pageSize") int pageSize, @QueryParam("consultantname") String consultantname, @QueryParam("name") String name,
      @QueryParam("groupid") String groupid) throws JSONException {
    JSONObject result = new JSONObject();
    JSONArray cArray = new JSONArray();

    int total = 0;
    List<Customer> customers = new ArrayList<Customer>();
    try {

      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }

      int level = user.getLevel();
      if (level == 0) {// admin or headquarter
        customers = cusDao.allcustomers(pageNo, pageSize, groupid, name, consultantname);
        total = cusDao.total_allcustomers(groupid, name, consultantname);
      } else if (level == 2) {// group leader
        customers = cusDao.allcustomers_byleader(accountid, pageNo, pageSize, groupid, name, consultantname);
        total = cusDao.total_allcustomers_byleader(accountid, groupid, name, consultantname);
      }

      if (customers != null && customers.size() > 0) {

        for (Customer customer : customers) {
          JSONObject cus = new JSONObject();

          Consultant con = customer.getConsultant();
          cus.put("updatetime", String.valueOf(customer.getUpdatetime()));
          cus.put("gender", customer.getGender());
          cus.put("married", customer.getMarried());
          cus.put("address", customer.getAddress());
          cus.put("contact", customer.getContact());
          cus.put("contactphone", customer.getContactphone());
          cus.put("age", String.valueOf(customer.getAge()));
          cus.put("completetime", String.valueOf(customer.getCompletetime()));
          cus.put("industry", customer.getIndustry());
          cus.put("done", customer.getDone());
          cus.put("src", customer.getSrc());
          cus.put("location", customer.getLocation());
          cus.put("idcard", customer.getIdcard());
          cus.put("childrenage", customer.getChildrenage());
          cus.put("email", customer.getEmail());
          cus.put("familymember", customer.getFamilymember());
          cus.put("generatetime", String.valueOf(customer.getGeneratetime()));
          cus.put("id", customer.getId());
          cus.put("name", customer.getName());
          cus.put("mobile", customer.getMobile());
          cus.put("memo", customer.getMemo());
          cus.put("matename", customer.getMatename());
          cus.put("mateage", customer.getMateage());
          cus.put("job", customer.getJob());
          if (con != null) {
            cus.put("consultantid", con.getId());
            cus.put("consultantname", con.getName());
            cus.put("consultantno", con.getConsultantno());
          } else {
            cus.put("consultantid", "");
            cus.put("consultantname", "");
            cus.put("consultantno", "");
          }
          cArray.put(cus);
        }
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", cArray);
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      } else {
        result.put("status", 0);
        result.put("msg", "");
        result.put("items", new JSONArray());
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      }
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      result.put("total", total);
      result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
    }

    return result.toString();
  }

  @GET
  @Path("/clientanalysis")
  @Produces(MediaType.APPLICATION_JSON)
  public String clientanalysis(@QueryParam("accountid") String accountid, @QueryParam("groupid") String groupid) throws JSONException {
    JSONObject result = new JSONObject();
    // JSONArray gArray = new JSONArray();

    List<Object[]> sexuals = new ArrayList<Object[]>();
    List<Object[]> age = new ArrayList<Object[]>();
    List<Object[]> fund = new ArrayList<Object[]>();
    List<Object[]> jobs = new ArrayList<Object[]>();
    try {
      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("item", "");
        return result.toString();
      }
      int level = user.getLevel();

      if (level == 0) {
        sexuals = gDao.clientanalysis_sexual(groupid);
        age = gDao.clientanalysis_age(groupid);
        fund = gDao.clientanalysis_fund(groupid);
        jobs = gDao.clientanalysis_jobs(groupid);
      } else if (level == 1) {
        // FIXME district manager code here
      } else if (level == 2) {
        sexuals = gDao.clientanalysis_sexual_byleader(accountid, groupid);
        age = gDao.clientanalysis_age_byleader(accountid, groupid);
        fund = gDao.clientanalysis_fund_byleader(accountid, groupid);
        jobs = gDao.clientanalysis_jobs_byleader(accountid, groupid);
      } else if (level > 2) {
        result.put("status", 1);
        result.put("msg", "您所在的用户组没有该权限");
        result.put("sexual", "");
        return result.toString();
      }

      if (sexuals != null && sexuals.size() > 0) {
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("sexual", handleclientsexual(sexuals));
        result.put("age", handleclientage(age));
        result.put("fund", handleclientfund(fund));
        result.put("jobs", handleclientjobs(jobs));
        return result.toString();
      } else {
        result.put("status", 1);
        result.put("msg", "暂时没有相关数据");
        result.put("sexual", "");
        return result.toString();
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("item", "");
    }

    return result.toString();

  }

  @GET
  @Path("/customer_age")
  @Produces(MediaType.APPLICATION_JSON)
  public String customer_age(@QueryParam("groupid") String groupid, @QueryParam("pageNo") int pageNo,
      @QueryParam("accountid") String accountid, @QueryParam("pageSize") int pageSize, @QueryParam("clickindex") int clickindex)
      throws JSONException {
    JSONObject result = new JSONObject();
    JSONArray cArray = new JSONArray();

    int total = 0;
    List<Customer> customers = new ArrayList<Customer>();
    try {

      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }

      int min = 0;
      int max = 0;

      switch (clickindex) {
        case 0:
          min = 0;
          max = 24;
          break;
        case 1:
          min = 25;
          max = 30;
          break;
        case 2:
          min = 31;
          max = 35;
          break;
        case 3:
          min = 36;
          max = 40;
          break;
        case 4:
          min = 41;
          max = 45;
          break;
        case 5:
          min = 46;
          max = 50;
          break;
        case 6:
          min = 50;
          max = 120;
          break;
      }

      int level = user.getLevel();
      if (level == 0) {// admin or headquarter
        customers = cusDao.customer_age(groupid, min, max, pageNo, pageSize);
        total = cusDao.total_customer_age(groupid, min, max);
      } else if (level == 2) {// group leader
        customers = cusDao.customer_age_byleader(accountid, groupid, min, max, pageNo, pageSize);
        total = cusDao.total_customer_age_byleader(accountid, groupid, min, max);
      }

      if (customers != null && customers.size() > 0) {

        for (Customer customer : customers) {
          JSONObject cus = new JSONObject();
          cus.put("id", customer.getId());
          cus.put("done", customer.getDone());
          cus.put("generatetime", String.valueOf(customer.getGeneratetime()));
          cus.put("email", customer.getEmail());
          cus.put("gender", customer.getGender());
          cus.put("name", customer.getName());
          cus.put("mobile", customer.getMobile());
          cus.put("age", String.valueOf(customer.getAge()));
          cus.put("job", customer.getJob());
          cArray.put(cus);
        }
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", cArray);
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      } else {
        result.put("status", 0);
        result.put("msg", "");
        result.put("items", new JSONArray());
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      }
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      result.put("total", total);
      result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
    }

    return result.toString();
  }

  @GET
  @Path("/customer_asset")
  @Produces(MediaType.APPLICATION_JSON)
  public String customer_asset(@QueryParam("groupid") String groupid, @QueryParam("pageNo") int pageNo,
      @QueryParam("accountid") String accountid, @QueryParam("pageSize") int pageSize, @QueryParam("clickindex") int clickindex)
      throws JSONException {
    JSONObject result = new JSONObject();
    JSONArray cArray = new JSONArray();

    int total = 0;
    List<Customer> customers = new ArrayList<Customer>();
    try {

      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }

      int min = 0;
      int max = 0;

      switch (clickindex) {
        case 0:
          min = 0;
          max = 1000000;
          break;
        case 1:
          min = 1000000;
          max = 5000000;
          break;
        case 2:
          min = 5000000;
          max = 10000000;
          break;
        case 3:
          min = 10000000;
          max = 50000000;
          break;
        case 4:
          min = 50000000;
          max = -1;
          break;

      }

      int level = user.getLevel();
      if (level == 0) {// admin or headquarter
        customers = cusDao.customer_asset(groupid, min, max, pageNo, pageSize);
        total = cusDao.total_customer_asset(groupid, min, max);
      } else if (level == 2) {// group leader
        customers = cusDao.customer_asset_byleader(accountid, groupid, min, max, pageNo, pageSize);
        total = cusDao.total_customer_asset_byleader(accountid, groupid, min, max);
      }

      if (customers != null && customers.size() > 0) {

        for (Customer customer : customers) {
          JSONObject cus = new JSONObject();
          cus.put("id", customer.getId());
          cus.put("done", customer.getDone());
          cus.put("generatetime", String.valueOf(customer.getGeneratetime()));
          cus.put("email", customer.getEmail());
          cus.put("gender", customer.getGender());
          cus.put("name", customer.getName());
          cus.put("mobile", customer.getMobile());
          cus.put("age", String.valueOf(customer.getAge()));
          cus.put("job", customer.getJob());
          cArray.put(cus);
        }
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", cArray);
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      } else {
        result.put("status", 0);
        result.put("msg", "");
        result.put("items", new JSONArray());
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      }
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      result.put("total", total);
      result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
    }

    return result.toString();
  }

  @GET
  @Path("/customer_gender")
  @Produces(MediaType.APPLICATION_JSON)
  public String customer_gender(@QueryParam("groupid") String groupid, @QueryParam("pageNo") int pageNo,
      @QueryParam("accountid") String accountid, @QueryParam("pageSize") int pageSize, @QueryParam("clickindex") int clickindex)
      throws JSONException {
    JSONObject result = new JSONObject();
    JSONArray cArray = new JSONArray();

    int total = 0;
    List<Customer> customers = new ArrayList<Customer>();
    try {

      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }

      int level = user.getLevel();
      if (level == 0) {// admin or headquarter
        customers = cusDao.customer_gender(groupid, clickindex, pageNo, pageSize);
        total = cusDao.total_customer_gender(groupid, clickindex);
      } else if (level == 2) {// group leader
        customers = cusDao.customer_gender_byleader(accountid, groupid, clickindex, pageNo, pageSize);
        total = cusDao.total_customer_gender_byleader(accountid, groupid, clickindex);
      }

      if (customers != null && customers.size() > 0) {

        for (Customer customer : customers) {
          JSONObject cus = new JSONObject();
          cus.put("id", customer.getId());
          cus.put("done", customer.getDone());
          cus.put("generatetime", String.valueOf(customer.getGeneratetime()));
          cus.put("email", customer.getEmail());
          cus.put("gender", customer.getGender());
          cus.put("name", customer.getName());
          cus.put("mobile", customer.getMobile());
          cus.put("age", String.valueOf(customer.getAge()));
          cus.put("job", customer.getJob());
          cArray.put(cus);
        }
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", cArray);
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      } else {
        result.put("status", 0);
        result.put("msg", "");
        result.put("items", new JSONArray());
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      }
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      result.put("total", total);
      result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
    }

    return result.toString();
  }

  @GET
  @Path("/customer_jobs")
  @Produces(MediaType.APPLICATION_JSON)
  public String customer_jobs(@QueryParam("groupid") String groupid, @QueryParam("pageNo") int pageNo,
      @QueryParam("accountid") String accountid, @QueryParam("pageSize") int pageSize, @QueryParam("job") String job) throws JSONException {
    JSONObject result = new JSONObject();
    JSONArray cArray = new JSONArray();

    int total = 0;
    List<Customer> customers = new ArrayList<Customer>();
    try {

      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }

      int level = user.getLevel();
      if (level == 0) {// admin or headquarter
        customers = cusDao.customer_jobs(groupid, job, pageNo, pageSize);
        total = cusDao.total_customer_jobs(groupid, job);
      } else if (level == 2) {// group leader
        customers = cusDao.customer_jobs_byleader(accountid, groupid, job, pageNo, pageSize);
        total = cusDao.total_customer_jobs_byleader(accountid, groupid, job);
      }

      if (customers != null && customers.size() > 0) {

        for (Customer customer : customers) {
          JSONObject cus = new JSONObject();
          cus.put("id", customer.getId());
          cus.put("done", customer.getDone());
          cus.put("generatetime", String.valueOf(customer.getGeneratetime()));
          cus.put("email", customer.getEmail());
          cus.put("gender", customer.getGender());
          cus.put("name", customer.getName());
          cus.put("mobile", customer.getMobile());
          cus.put("age", String.valueOf(customer.getAge()));
          cus.put("job", customer.getJob());
          cArray.put(cus);
        }
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", cArray);
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      } else {
        result.put("status", 0);
        result.put("msg", "");
        result.put("items", new JSONArray());
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      }
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      result.put("total", total);
      result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
    }

    return result.toString();
  }

  @POST
  @Path("/delgroup")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String delgroup(Group group) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      Group g = gDao.getById(group.getId());
      if (g != null) {
        int total = gmDao.delgm(g.getId());
        if (total == -1) {
          result.put("status", 1);
          result.put("msg", "发生错误");
          return result.toString();
        } else {
          gDao.deletebyid(g.getId());
          result.put("status", 0);
          result.put("msg", "删除成功");
          return result.toString();
        }
      } else {
        result.put("status", 1);
        result.put("msg", "该组不存在");
        return result.toString();
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
    }

    return result.toString();
  }

  @POST
  @Path("/done")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String done(Customer customer) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      String id = customer.getId();
      if (id != null && !"".equals(id)) {
        Customer cus = cusDao.getById(id);
        if (cus != null) {
          cus.setDone(1);
          cus.setCompletetime(customer.getCompletetime());
          cusDao.save(cus);
          result.put("status", 0);
          result.put("msg", "保存成功");
          result.put("items", new JSONArray());
        } else {
          result.put("status", 1);
          result.put("msg", "该客户不存在,请刷新页面!");
          result.put("items", new JSONArray());
        }
      } else {
        result.put("status", 1);
        result.put("msg", "该客户不存在,请刷新页面!");
        result.put("items", new JSONArray());
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      // result.put("total", total);
      // result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
    }

    return result.toString();
  }

  @POST
  @Path("/editgroup")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String editgroup(Group group) throws JSONException {
    JSONObject result = new JSONObject();

    try {

      Group g = gDao.groupwithleader(group.getId());

      Account leader = group.getLeader();

      if (leader != null) {
        Account user = aDao.getById(leader.getId().trim());
        if (user.getLevel() > 2) {
          user.setLevel(2);
        }
        aDao.save(user);
        g.setLeader(user);
      }

      g.setDistrict(group.getDistrict());
      g.setName(group.getName());
      gDao.save(g);

      int total = gmDao.delgm(g.getId());
      if (total == -1) {
        result.put("status", 1);
        result.put("msg", "发生错误");
        return result.toString();
      }

      String members = group.getMember();

      boolean b = false;
      if (members != null && !"".equals(members)) {
        String[] mems = members.split(",");
        for (String mem : mems) {
          if (mem.trim().equals(leader.getId().trim())) {
            b = true;
          }
          GroupMember gm = new GroupMember();
          gm.setGeneratetime(new Timestamp(System.currentTimeMillis()));
          gm.setGroup(g);
          gm.setMember(aDao.getById(mem.trim()));
          gmDao.save(gm);
        }
      }

      if (!b) {
        GroupMember gmm = new GroupMember();
        gmm.setGeneratetime(new Timestamp(System.currentTimeMillis()));
        gmm.setGroup(g);
        gmm.setMember(aDao.getById(leader.getId().trim()));
        gmDao.save(gmm);
      }
      result.put("status", 0);
      result.put("msg", "保存成功");
      result.put("items", new JSONArray());
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      // result.put("total", total);
      // result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
    }

    return result.toString();
  }

  @GET
  @Path("/consultants")
  @Produces(MediaType.APPLICATION_JSON)
  public String getConsultants(@QueryParam("accountid") String accountid, @QueryParam("pageNo") int pageNo,
      @QueryParam("pageSize") int pageSize, @QueryParam("district") String groupid, @QueryParam("name") String name,
      @QueryParam("title") int title) throws JSONException, UnsupportedEncodingException {
    JSONObject result = new JSONObject();
    JSONArray cArray = new JSONArray();

    System.out.println("name======" + name);
    String strName = java.net.URLDecoder.decode(name, "UTF-8");
    System.out.println("Strname======" + strName);
    int total = 0;
    List<Consultant> cons = new ArrayList<Consultant>();
    try {
      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }

      int level = user.getLevel();
      if (level == 0) {// admin or headquarter
        cons = cDao.list(pageNo, pageSize, groupid, strName, title);// all consultants
        total = cDao.total(groupid, strName, title);
      } else if (level == 2) {// group leader
        cons = cDao.getByGroupLeader(accountid, pageNo, pageSize, groupid, strName, title);
        total = cDao.grouptotal(accountid, groupid, strName, title);
      }

      if (cons != null && cons.size() > 0) {

        for (Consultant con : cons) {
          JSONObject c = new JSONObject();
          c.put("certificate", con.getCertificate());
          c.put("consultantno", con.getConsultantno());
          c.put("experience", con.getExperience());
          c.put("feature", con.getFeature());
          c.put("id", con.getId());
          c.put("name", con.getName());
          c.put("gender", con.getGender());
          String titles = "";
          if (con.getTitle() == 0) {
            titles = "技术总监";
          } else if (con.getTitle() == 1) {
            titles = "首席理财师";
          } else if (con.getTitle() == 2) {
            titles = "资深理财师";
          } else if (con.getTitle() == 3) {
            titles = "理财师";
          } else if (con.getTitle() == 4) {
            titles = "助理理财师";
          }

          c.put("title", titles);
          cArray.put(c);
        }
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", cArray);
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);

      } else {
        result.put("status", 1);
        result.put("msg", "暂时没有相关数据");
        result.put("items", new JSONArray());
        result.put("total", total);
        result.put("page", 0);
      }
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
    }

    return result.toString();
  }

  @GET
  @Path("/customers")
  @Produces(MediaType.APPLICATION_JSON)
  public String getCustomers(@QueryParam("consultantid") String consultantid, @QueryParam("pageNo") int pageNo,
      @QueryParam("pageSize") int pageSize) throws JSONException {
    JSONObject result = new JSONObject();
    JSONArray cArray = new JSONArray();

    int total = 0;
    List<Customer> customers = new ArrayList<Customer>();
    try {
      customers = cusDao.getByConsultantId(consultantid, pageNo, pageSize);

      if (customers != null && customers.size() > 0) {

        for (Customer customer : customers) {
          JSONObject cus = new JSONObject();
          Doc doc = dDao.getDocByCustomerid(customer.getId());

          if (doc != null) {
            cus.put("pdfpath", doc.getPath());

          } else {
            cus.put("pdfpath", "");
          }
          cus.put("updatetime", String.valueOf(customer.getUpdatetime()));
          cus.put("gender", customer.getGender());
          cus.put("married", customer.getMarried());
          cus.put("address", customer.getAddress());
          cus.put("contact", customer.getContact());
          cus.put("contactphone", customer.getContactphone());
          cus.put("age", String.valueOf(customer.getAge()));
          cus.put("completetime", String.valueOf(customer.getCompletetime()));
          cus.put("industry", customer.getIndustry());
          cus.put("done", customer.getDone());
          cus.put("src", customer.getSrc());
          cus.put("location", customer.getLocation());
          cus.put("idcard", customer.getIdcard());
          cus.put("childrenage", customer.getChildrenage());
          cus.put("email", customer.getEmail());
          cus.put("familymember", customer.getFamilymember());
          cus.put("generatetime", String.valueOf(customer.getGeneratetime()));
          cus.put("id", customer.getId());
          cus.put("name", customer.getName());
          cus.put("mobile", customer.getMobile());
          cus.put("memo", customer.getMemo());
          cus.put("matename", customer.getMatename());
          cus.put("mateage", customer.getMateage());
          cus.put("job", customer.getJob());
          cArray.put(cus);
        }
        total = cusDao.totalByConsultantid(consultantid);
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", cArray);
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      } else {
        result.put("status", 0);
        result.put("msg", "");
        result.put("items", new JSONArray());
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      }
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      result.put("total", total);
      result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
    }

    return result.toString();
  }

  @Override
  public Class<Group> getEntityType() {
    // TODO Auto-generated method stub
    return Group.class;
  }

  @GET
  @Path("/getinfo")
  @Produces(MediaType.APPLICATION_JSON)
  public String getinfo(@QueryParam("groupid") String groupid) throws JSONException {
    JSONObject result = new JSONObject();

    try {

      Group group = gDao.groupwithleader(groupid);
      if (group == null) {
        result.put("status", 1);
        result.put("msg", "该组信息不存在");
        result.put("items", new JSONArray());
        result.put("group", new JSONObject());
        return result.toString();
      } else {

        JSONObject g = new JSONObject();
        g.put("district", group.getDistrict());
        g.put("name", group.getName());
        g.put("generatetime", String.valueOf(group.getGeneratetime()));
        g.put("id", group.getId());
        g.put("leadername", group.getLeader().getFullname());
        g.put("leaderno", group.getLeader().getLoginId());
        g.put("leaderid", group.getLeader().getId());

        result.put("msg", "查询成功");
        result.put("status", 0);
        result.put("group", g);
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("group", new JSONObject());
    }

    return result.toString();
  }

  @GET
  @Path("/groupanalysis")
  @Produces(MediaType.APPLICATION_JSON)
  public String groupanalysis(@QueryParam("accountid") String accountid) throws JSONException {
    JSONObject result = new JSONObject();
    // JSONArray gArray = new JSONArray();

    List<Object[]> groups = new ArrayList<Object[]>();
    try {
      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("item", "");
        return result.toString();
      }
      int level = user.getLevel();

      if (level == 0) {
        groups = gDao.groupAnalysis();
      } else if (level == 1) {
        // FIXME district manager code here
      } else if (level == 2) {
        groups = gDao.groupAnalysisbyleader(accountid);
      } else if (level > 2) {
        result.put("status", 1);
        result.put("msg", "您所在的用户组没有该权限");
        result.put("item", "");
        return result.toString();
      }

      if (groups != null && groups.size() > 0) {
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("item", handleanalysis(groups));
        return result.toString();
      } else {
        result.put("status", 1);
        result.put("msg", "暂时没有相关数据");
        result.put("item", "");
        return result.toString();
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("item", "");
    }

    return result.toString();

  }

  @GET
  @Path("/groupcustomer")
  @Produces(MediaType.APPLICATION_JSON)
  public String groupcustomer(@QueryParam("accountid") String accountid) throws JSONException {
    JSONObject result = new JSONObject();
    // JSONArray gArray = new JSONArray();

    List<Object[]> groups = new ArrayList<Object[]>();
    try {
      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("item", "");
        return result.toString();
      }
      int level = user.getLevel();

      if (level == 0) {
        groups = gDao.groupcustomer();
      } else if (level == 1) {
        // FIXME district manager code here
      } else if (level == 2) {
        groups = gDao.groupcustomerbyleader(accountid);
      } else if (level > 2) {
        result.put("status", 1);
        result.put("msg", "您所在的用户组没有该权限");
        result.put("items", "");
        return result.toString();
      }

      if (groups != null && groups.size() > 0) {
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", handlegroupcustomer(groups));
        return result.toString();
      } else {
        result.put("status", 1);
        result.put("msg", "暂时没有相关数据");
        result.put("items", "");
        return result.toString();
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", "");
    }

    return result.toString();

  }

  @GET
  @Path("/groupinfo")
  @Produces(MediaType.APPLICATION_JSON)
  public String groupinfo(@QueryParam("groupid") String groupid) throws JSONException {
    JSONObject result = new JSONObject();
    JSONArray gArray = new JSONArray();

    List<GroupMember> gms = new ArrayList<GroupMember>();
    try {

      Group group = gDao.groupwithleader(groupid);
      if (group == null) {
        result.put("status", 1);
        result.put("msg", "该组信息不存在");
        result.put("items", new JSONArray());
        result.put("group", new JSONObject());
        return result.toString();
      } else {
        gms = gmDao.listByGroupid(group.getId());

        if (gms != null && gms.size() > 0) {
          for (GroupMember gm : gms) {
            JSONObject gmm = new JSONObject();
            Account member = gm.getMember();
            gmm.put("generatetime", String.valueOf(gm.getGeneratetime()));
            gmm.put("id", gm.getId());
            gmm.put("name", member.getFullname());
            gmm.put("accountid", member.getId());
            gmm.put("consultantno", member.getLoginId());
            gArray.put(gmm);
          }
        }

        JSONObject g = new JSONObject();
        g.put("district", group.getDistrict());
        g.put("name", group.getName());
        g.put("generatetime", String.valueOf(group.getGeneratetime()));
        g.put("id", group.getId());
        g.put("leadername", group.getLeader().getFullname());
        g.put("leaderno", group.getLeader().getLoginId());
        g.put("leaderid", group.getLeader().getId());

        result.put("items", gArray);
        result.put("msg", "查询成功");
        result.put("status", 0);
        result.put("group", g);
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      result.put("group", new JSONObject());
    }

    return result.toString();
  }

  @GET
  @Path("/grouproute")
  public String grouproute(@QueryParam("district") String district, @QueryParam("accountid") String accountid) throws JSONException,
      ParseException {
    JSONObject result = new JSONObject();
    JSONArray cArray = new JSONArray();

    try {
      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }

      List<ConsultantLocation> locations = new ArrayList<ConsultantLocation>();
      int level = user.getLevel();

      if (level == 0) {
        locations = clDao.grouproute(district);
      } else if (level == 1) {
        // FIXME district manager code here
      } else if (level == 2) {
        locations = clDao.grouproute(accountid, district);
      }
      // FIXME fetch data of each group member
      if (locations != null && locations.size() > 0) {
        for (ConsultantLocation cl : locations) {

          Customer customer = cl.getCustomer();
          JSONObject c = new JSONObject();
          c.put("generatetime", String.valueOf(cl.getGeneratetime()));
          c.put("location", cl.getLocation());
          if (customer != null) {
            c.put("customername", customer.getName());
          } else {
            c.put("customername", "");
          }
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

  @GET
  @Path("/groups")
  @Produces(MediaType.APPLICATION_JSON)
  public String groups(@QueryParam("accountid") String accountid) throws JSONException {

    JSONObject result = new JSONObject();
    JSONArray gArray = new JSONArray();

    List<Group> groups = new ArrayList<Group>();
    try {

      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }
      int level = user.getLevel();

      if (level == 0) {
        groups = gDao.groups();
      } else if (level == 1) {
        // FIXME district manager code here
      } else if (level == 2) {
        groups = gDao.getGroupByLeader(accountid);
      } else if (level > 2) {
        result.put("status", 1);
        result.put("msg", "您所在的用户组没有该权限");
        result.put("items", new JSONArray());
        return result.toString();
      }
      if (groups != null && groups.size() > 0) {
        for (Group g : groups) {
          JSONObject go = new JSONObject();
          go.put("district", g.getDistrict());
          go.put("generatetime", String.valueOf(g.getGeneratetime()));
          go.put("id", g.getId());
          go.put("leadername", g.getLeader().getFullname());
          go.put("accountid", g.getLeader().getId());
          go.put("name", g.getName());
          go.put("consultantno", g.getLeader().getLoginId());
          gArray.put(go);
        }
        result.put("items", gArray);
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("total", groups.size());
        return result.toString();

      } else {
        result.put("status", 1);
        result.put("msg", "暂时没有相关数据");
        result.put("items", new JSONArray());
        result.put("total", 0);
        return result.toString();
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      result.put("total", 0);
    }

    return result.toString();
  }

  @GET
  @Path("/initmenu")
  @Produces(MediaType.APPLICATION_JSON)
  public String initMenu(@QueryParam("accountid") String accountid) throws JSONException {
    JSONObject result = new JSONObject();
    JSONArray items = new JSONArray();
    try {
      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }

      JSONObject menu1 = new JSONObject();
      menu1.put("value", "index.html");
      menu1.put("key", "顾问列表");

      items.put(menu1);

      menu1 = new JSONObject();
      menu1.put("value", "system_group.html");
      menu1.put("key", "渠道管理");
      items.put(menu1);

      menu1 = new JSONObject();
      menu1.put("value", "chart_all.html");
      menu1.put("key", "统计分析");
      items.put(menu1);

      menu1 = new JSONObject();
      menu1.put("value", "account.html");
      menu1.put("key", "账号管理");
      items.put(menu1);

      int level = user.getLevel();

      if (level == 0) {
        result.put("items", items);
      } else if (level == 1) {
        // menus.remove("")
      } else if (level == 2) {
        result.put("items", items);
      }
      JSONObject acc = new JSONObject();

      acc.put("level", user.getLevel());
      acc.put("fullname", user.getFullname());
      acc.put("id", user.getId());
      result.put("item", acc);
      result.put("status", 0);
      result.put("msg", "初始化成功");
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("item", new JSONArray());
    }

    return result.toString();
  }

  @POST
  @Path("/newgroup")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String newgroup(Group group) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      Account leader = group.getLeader();

      if (leader != null) {
        Account user = aDao.getById(leader.getId().trim());
        if (user.getLevel() > 2) {
          user.setLevel(2);
        }
        aDao.save(user);
        group.setLeader(user);
      }

      group.setGeneratetime(new Timestamp(System.currentTimeMillis()));
      group.setLevel(0);
      // group.set
      gDao.save(group);

      String members = group.getMember();

      boolean b = false;
      if (members != null && !"".equals(members)) {
        String[] mems = members.split(",");
        for (String mem : mems) {
          if (mem.trim().equals(leader.getId().trim())) {
            b = true;
          }
          GroupMember gm = new GroupMember();
          gm.setGeneratetime(new Timestamp(System.currentTimeMillis()));
          gm.setGroup(group);
          gm.setMember(aDao.getById(mem.trim()));
          gmDao.save(gm);
        }
      }

      if (!b) {
        GroupMember gmm = new GroupMember();
        gmm.setGeneratetime(new Timestamp(System.currentTimeMillis()));
        gmm.setGroup(group);
        gmm.setMember(aDao.getById(leader.getId().trim()));
        gmDao.save(gmm);
      }
      result.put("status", 0);
      result.put("msg", "保存成功");
      result.put("items", new JSONArray());
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      // result.put("total", total);
      // result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
    }

    return result.toString();
  }

  @GET
  @Path("/visits")
  public String visits(@QueryParam("customerid") String customerid, @QueryParam("accountid") String accountid,
      @QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) throws JSONException, ParseException {
    JSONObject result = new JSONObject();
    JSONArray cArray = new JSONArray();
    int total = 0;
    try {
      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }

      List<ConsultantLocation> cls = clDao.visits(customerid, pageNo, pageSize);
      total = clDao.total_visits(customerid);
      if (cls != null && cls.size() > 0) {

        for (ConsultantLocation cl : cls) {
          JSONObject c = new JSONObject();

          Consultant con = cl.getConsultant();
          c.put("generatetime", String.valueOf(cl.getGeneratetime()));
          c.put("location", cl.getLocation());
          c.put("address", cl.getAddress());
          if (con != null) {
            c.put("consultantid", con.getId());
            c.put("consultantname", con.getName());
            c.put("consultantno", con.getConsultantno());
          } else {
            c.put("consultantid", "");
            c.put("consultantname", "");
            c.put("consultantno", "");
          }
          cArray.put(c);
        }
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", cArray);
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      } else {
        result.put("status", 0);
        result.put("msg", "");
        result.put("items", new JSONArray());
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
    }
    return result.toString();
  }

  @GET
  @Path("/workeffort")
  @Produces(MediaType.APPLICATION_JSON)
  public String workeffort(@QueryParam("accountid") String accountid, @QueryParam("consultantid") String consultantid,
      @QueryParam("timeto") String timeto, @QueryParam("timefrom") String timefrom) throws JSONException, UnsupportedEncodingException {
    JSONObject result = new JSONObject();

    List<Object[]> increases = new ArrayList<Object[]>();
    try {
      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }
      increases = cDao.increase(consultantid, timefrom, timeto);
      List<Object[]> completes = cDao.complete(consultantid, timefrom, timeto);
      List<Object[]> visits = cDao.visit(consultantid, timefrom, timeto);

      if ((increases != null && increases.size() > 0) && (completes != null && completes.size() > 0)
          && (visits != null && visits.size() > 0)) {

        JSONArray increase = new JSONArray();
        JSONArray complete = new JSONArray();
        JSONArray visit = new JSONArray();
        JSONArray date = new JSONArray();

        List<String> datesList = new ArrayList<String>();
        if (increases != null && increases.size() > 0) {
          for (Object[] con : increases) {
            JSONArray i = new JSONArray();
            if (con[2] != null) {
              i.put(con[2]);
              datesList.add(String.valueOf(con[2]));
              i.put(con[3]);
            }
            increase.put(i);
          }

        }

        if (completes != null && completes.size() > 0) {
          for (Object[] con : completes) {
            JSONArray c = new JSONArray();
            if (con[2] != null) {
              c.put(con[2]);
              datesList.add(String.valueOf(con[2]));
              c.put(con[3]);
            }
            complete.put(c);
          }

        }

        if (visits != null && visits.size() > 0) {
          for (Object[] con : visits) {
            JSONArray v = new JSONArray();
            if (con[2] != null) {
              v.put(con[2]);
              datesList.add(String.valueOf(con[2]));
              v.put(con[3]);
            }
            visit.put(v);
          }

        }
        Set<String> set = new HashSet<String>();
        set.addAll(datesList);

        List<String> newlist = new ArrayList<String>();
        newlist.addAll(set);

        Collections.sort(newlist);

        for (String str : newlist) {
          date.put(str);
        }

        JSONObject items = new JSONObject();
        items.put("increase", increase);
        items.put("complete", complete);
        items.put("visit", visit);
        items.put("date", date);

        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", items);
      } else {
        result.put("status", 1);
        result.put("msg", "暂时没有数据");
        result.put("items", new JSONArray());

        return result.toString();
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
    }

    return result.toString();
  }

  @GET
  @Path("/workforce")
  @Produces(MediaType.APPLICATION_JSON)
  public String workforce(@QueryParam("accountid") String accountid, @QueryParam("pageNo") int pageNo,
      @QueryParam("pageSize") int pageSize, @QueryParam("district") String groupid, @QueryParam("timefrom") String timefrom)
      throws JSONException, UnsupportedEncodingException {
    JSONObject result = new JSONObject();
    JSONArray cArray = new JSONArray();

    int total = 0;
    List<Object[]> cons = new ArrayList<Object[]>();
    try {
      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }

      int level = user.getLevel();
      if (level == 0) {// admin or headquarter
        cons = cDao.workforce(pageNo, pageSize, groupid, timefrom);// all consultants
        total = cDao.workforcetotal(groupid);
      } else if (level == 2) {// group leader
        cons = cDao.workforcebyleader(accountid, pageNo, pageSize, groupid, timefrom);
        total = cDao.totalbyleader(accountid, groupid);
      }

      if (cons != null && cons.size() > 0) {

        for (Object[] con : cons) {
          JSONObject c = new JSONObject();

          c.put("id", con[0]);
          c.put("name", con[1]);
          c.put("consultantno", con[2]);
          c.put("customercount", con[3]);
          double customercount = Integer.parseInt(String.valueOf(con[3]));
          if (customercount == 0) {
            c.put("completerate", "0%");
          } else {
            c.put("completerate", (Double.parseDouble(String.valueOf(con[7])) / customercount * 100) + "%");
          }
          c.put("completecustomer", con[7]);
          c.put("monthlyincrease", con[11]);
          c.put("monthlycomplete", con[15]);
          c.put("monthlyvisit", con[19]);
          cArray.put(c);
        }
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", cArray);
        result.put("total", total);
        result.put("page", total % pageSize > 0 ? total / pageSize + 1 : total / pageSize);

      } else {
        result.put("status", 1);
        result.put("msg", "暂时没有相关数据");
        result.put("items", new JSONArray());
        result.put("total", total);
        result.put("page", 0);
      }
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
    }

    return result.toString();
  }

  private JSONArray handleanalysis(List<Object[]> groups) {

    JSONArray gArray = new JSONArray();

    for (int i = 0; i < groups.size(); i++) {
      Object[] objs = groups.get(i);

      JSONArray a = new JSONArray();
      a.put(objs[0]);
      a.put(objs[1]);
      gArray.put(a);
      // groupStr += "['" + objs[0] + "'," + objs[1] + "],";
    }
    // groupStr = groupStr.substring(0, groupStr.lastIndexOf(","));
    // groupStr += "]";

    return gArray;

  }

  private JSONArray handleclientage(List<Object[]> age) {

    JSONArray gArray = new JSONArray();

    for (int i = 0; i < age.size(); i++) {
      Object[] objs = age.get(i);
      JSONArray men = new JSONArray();
      men.put("25岁以下");
      men.put(objs[0]);

      gArray.put(men);

      men = new JSONArray();
      men.put("25-30岁");
      men.put(objs[1]);

      gArray.put(men);

      men = new JSONArray();
      men.put("31-35岁");
      men.put(objs[2]);

      gArray.put(men);

      men = new JSONArray();
      men.put("36-40岁");
      men.put(objs[3]);

      gArray.put(men);

      men = new JSONArray();
      men.put("41-45岁");
      men.put(objs[4]);

      gArray.put(men);

      men = new JSONArray();
      men.put("46-50岁");
      men.put(objs[5]);

      gArray.put(men);

      men = new JSONArray();
      men.put("50岁以上");
      men.put(objs[6]);

      gArray.put(men);
      // groupStr += "['" + objs[0] + "'," + objs[1] + "],";
    }
    // groupStr = groupStr.substring(0, groupStr.lastIndexOf(","));
    // groupStr += "]";

    return gArray;

  }

  private JSONArray handleclientfund(List<Object[]> fund) {

    JSONArray gArray = new JSONArray();

    for (int i = 0; i < fund.size(); i++) {
      Object[] objs = fund.get(i);
      JSONArray total = new JSONArray();
      total.put("100万以下");
      total.put(objs[0]);

      gArray.put(total);

      total = new JSONArray();
      total.put("100-500万");
      total.put(objs[1]);

      gArray.put(total);

      total = new JSONArray();
      total.put("500-1000万");
      total.put(objs[2]);

      gArray.put(total);

      total = new JSONArray();
      total.put("1000-5000万");
      total.put(objs[3]);

      gArray.put(total);

      total = new JSONArray();
      total.put("5000万以上");
      total.put(objs[4]);

      gArray.put(total);

      // groupStr += "['" + objs[0] + "'," + objs[1] + "],";
    }
    // groupStr = groupStr.substring(0, groupStr.lastIndexOf(","));
    // groupStr += "]";

    return gArray;

  }

  private JSONArray handleclientjobs(List<Object[]> jobs) throws JSONException {
    JSONArray jArray = new JSONArray();

    if (jobs != null && jobs.size() > 0) {
      for (Object[] obj : jobs) {
        JSONArray ja = new JSONArray();
        ja.put(obj[0]);
        ja.put(obj[1]);
        jArray.put(ja);
      }
    }

    return jArray;
  }

  private JSONArray handleclientsexual(List<Object[]> sexuals) {

    JSONArray gArray = new JSONArray();

    for (int i = 0; i < sexuals.size(); i++) {
      Object[] objs = sexuals.get(i);
      JSONArray men = new JSONArray();
      men.put("男");
      men.put(objs[0]);

      gArray.put(men);

      JSONArray women = new JSONArray();
      women.put("女");
      women.put(objs[1]);
      gArray.put(women);
      // groupStr += "['" + objs[0] + "'," + objs[1] + "],";
    }
    // groupStr = groupStr.substring(0, groupStr.lastIndexOf(","));
    // groupStr += "]";

    return gArray;

  }

  private JSONObject handlegroupcustomer(List<Object[]> groups) throws JSONException {

    JSONObject items = new JSONObject();

    // g.id,g.name, count(cus.id) total,count(cust.id) complete,count(cl.id) visittotal
    JSONArray names = new JSONArray();
    JSONArray totals = new JSONArray();
    JSONArray completes = new JSONArray();
    JSONArray visittotal = new JSONArray();

    for (int i = 0; i < groups.size(); i++) {
      Object[] objs = groups.get(i);
      names.put(objs[1]);
      totals.put(objs[2]);
      completes.put(objs[5]);
      visittotal.put(objs[8]);
      // groupStr += "['" + objs[0] + "'," + objs[1] + "],";
    }
    items.put("names", names);
    items.put("totals", totals);
    items.put("completes", completes);
    items.put("visittotal", visittotal);
    // groupStr = groupStr.substring(0, groupStr.lastIndexOf(","));
    // groupStr += "]";

    return items;

  }
}
