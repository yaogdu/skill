package com.isoftstone.smart.kingstone.service;

import java.sql.Timestamp;
import java.util.List;

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
import com.isoftstone.smart.core.service.BaseService;
import com.isoftstone.smart.kingstone.business.CustomerService;
import com.isoftstone.smart.kingstone.business.DocService;
import com.isoftstone.smart.kingstone.business.IncomeService;
import com.isoftstone.smart.kingstone.entity.Doc;
import com.isoftstone.smart.kingstone.entity.Income;

@Path("/income")
@Produces(MediaType.APPLICATION_JSON)
public class IncomeResource extends BaseService<Income> {

  @Inject
  IncomeService inDao;

  @Inject
  CustomerService cDao;

  @Inject
  DocService dDao;

  @POST
  @Path("/collectAdjustInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectAdjustInfo(Income income) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      // income.setUpdatetime(new Timestamp(System.currentTimeMillis()));
      // income.setGeneratetime(new Timestamp(System.currentTimeMillis()));
      // income.setType(1);
      // Doc doc = dDao.getDocByCustomerid(income.getCustomer().getId());
      //
      // if (doc != null) {
      // income.setDoc(doc);
      // doc.setExpired(0);
      // dDao.save(doc);
      // }
      inDao.save(income);
      result.put("status", 0);
      result.put("msg", "保存成功!");
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    return result.toString();
  }

  @POST
  @Path("/collectOriginalInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectOriginalInfo(Income income) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      // income.setUpdatetime(new Timestamp(System.currentTimeMillis()));
      // income.setGeneratetime(new Timestamp(System.currentTimeMillis()));
      // income.setType(0);
      // Doc doc = dDao.getDocByCustomerid(income.getCustomer().getId());
      //
      // if (doc != null) {
      // income.setDoc(doc);
      // doc.setExpired(0);
      // dDao.save(doc);
      // }
      inDao.save(income);
      result.put("status", 0);
      result.put("msg", "保存成功!");
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    return result.toString();
  }

  @GET
  @Path("/getAdjustInfo")
  public String getAdjustInfo(@QueryParam("customerid") String customerid) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      Doc doc = dDao.getDocByCustomerid(customerid);
      Income income = null;
      if (doc != null) {
        income = inDao.getLatestInfo(doc.getId(), 1);
        if (income != null) {
          JSONObject in = new JSONObject();
          in.put("type", income.getType());
          in.put("balance", income.getBalance());
          in.put("advice", income.getAdvice());
          in.put("bonus", income.getBonus());
          in.put("children", income.getChildren());
          in.put("deposit", income.getDeposit());
          in.put("dividend", income.getDividend());
          in.put("expensetotal", income.getExpensetotal());
          in.put("family", income.getFamily());
          in.put("fixedexpense", income.getFixedexpense());
          in.put("fixedincome", income.getFixedincome());
          in.put("generatetime", String.valueOf(income.getGeneratetime()));
          in.put("id", income.getId());
          in.put("income", income.getIncome());
          in.put("incometotal", income.getIncometotal());
          in.put("insurance", income.getInsurance());
          in.put("maintenance", income.getMaintenance());
          in.put("mateincome", income.getMateincome());
          in.put("medical", income.getMedical());
          in.put("rent", income.getRent());
          in.put("rentexpense", income.getRentexpense());
          in.put("sortdividend", income.getSortdividend());
          in.put("travel", income.getTravel());
          in.put("unfixedexpense", income.getUnfixedexpense());
          in.put("unfixedincome", income.getUnfixedincome());
          in.put("updatetime", String.valueOf(income.getUpdatetime()));
          in.put("docid", doc.getId());
          in.put("path", doc.getPath());
          in.put("expired", doc.getExpired());
          result.put("msg", "查询成功");
          result.put("status", 0);
          result.put("item", in);
        } else {
          result.put("msg", "暂时没有相关信息");
          result.put("status", 1);
          result.put("item", new JSONObject());

        }
      } else {
        result.put("msg", "暂时没有相关信息");
        result.put("status", 1);
        result.put("item", new JSONObject());
      }

    } catch (Exception e) {
      result.put("msg", "暂时没有相关信息");
      result.put("status", 1);
      result.put("item", new JSONObject());
    }
    return result.toString();
  }

  @Override
  public Class<Income> getEntityType() {
    return Income.class;
  }

  @GET
  @Path("/getOriginalInfo")
  public String getOriginalInfo(@QueryParam("customerid") String customerid) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      Doc doc = dDao.getDocByCustomerid(customerid);
      Income income = null;
      if (doc != null) {
        income = inDao.getLatestInfo(doc.getId(), 0);
        if (income != null) {
          JSONObject in = new JSONObject();
          in.put("type", income.getType());
          in.put("balance", income.getBalance());
          in.put("advice", income.getAdvice());
          in.put("bonus", income.getBonus());
          in.put("children", income.getChildren());
          in.put("deposit", income.getDeposit());
          in.put("dividend", income.getDividend());
          in.put("expensetotal", income.getExpensetotal());
          in.put("family", income.getFamily());
          in.put("fixedexpense", income.getFixedexpense());
          in.put("fixedincome", income.getFixedincome());
          in.put("generatetime", String.valueOf(income.getGeneratetime()));
          in.put("id", income.getId());
          in.put("income", income.getIncome());
          in.put("incometotal", income.getIncometotal());
          in.put("insurance", income.getInsurance());
          in.put("maintenance", income.getMaintenance());
          in.put("mateincome", income.getMateincome());
          in.put("medical", income.getMedical());
          in.put("rent", income.getRent());
          in.put("rentexpense", income.getRentexpense());
          in.put("sortdividend", income.getSortdividend());
          in.put("travel", income.getTravel());
          in.put("unfixedexpense", income.getUnfixedexpense());
          in.put("unfixedincome", income.getUnfixedincome());
          in.put("updatetime", String.valueOf(income.getUpdatetime()));
          in.put("docid", doc.getId());
          in.put("path", doc.getPath());
          in.put("expired", doc.getExpired());

          result.put("msg", "查询成功");
          result.put("status", 0);
          result.put("item", in);
        } else {
          result.put("msg", "暂时没有相关信息");
          result.put("status", 1);
          result.put("item", new JSONObject());
        }
      } else {
        result.put("msg", "暂时没有相关信息");
        result.put("status", 1);
        result.put("item", new JSONObject());
      }

    } catch (Exception e) {
      result.put("msg", "暂时没有相关信息");
      result.put("status", 1);
      result.put("item", new JSONObject());
    }
    return result.toString();
  }

  @GET
  @Path("/list")
  public String list(@QueryParam("customerid") String customerid) throws JSONException {
    JSONObject result = new JSONObject();

    JSONArray inArray = new JSONArray();
    try {
      List<Income> ins = inDao.getList(customerid);
      if (ins != null && ins.size() > 0) {
        for (Income income : ins) {

          JSONObject in = new JSONObject();
          in.put("type", income.getType());
          in.put("balance", income.getBalance());
          in.put("advice", income.getAdvice());
          in.put("bonus", income.getBonus());
          in.put("children", income.getChildren());
          in.put("deposit", income.getDeposit());
          in.put("dividend", income.getDividend());
          in.put("expensetotal", income.getExpensetotal());
          in.put("family", income.getFamily());
          in.put("fixedexpense", income.getFixedexpense());
          in.put("fixedincome", income.getFixedincome());
          in.put("generatetime", String.valueOf(income.getGeneratetime()));
          in.put("id", income.getId());
          in.put("income", income.getIncome());
          in.put("incometotal", income.getIncometotal());
          in.put("insurance", income.getInsurance());
          in.put("maintenance", income.getMaintenance());
          in.put("mateincome", income.getMateincome());
          in.put("medical", income.getMedical());
          in.put("rent", income.getRent());
          in.put("rentexpense", income.getRentexpense());
          in.put("sortdividend", income.getSortdividend());
          in.put("travel", income.getTravel());
          in.put("unfixedexpense", income.getUnfixedexpense());
          in.put("unfixedincome", income.getUnfixedincome());
          in.put("updatetime", String.valueOf(income.getUpdatetime()));
          inArray.put(in);
        }
        result.put("msg", "查询成功");
        result.put("status", 0);
        result.put("items", inArray);
      } else {
        result.put("msg", "暂时没有相关信息");
        result.put("status", 1);
        result.put("item", new JSONArray());

      }

    } catch (Exception e) {
      result.put("msg", "暂时没有相关信息");
      result.put("status", 1);
      result.put("item", new JSONObject());
    }
    return result.toString();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/modify")
  public String modify(Income income) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      // Doc doc = dDao.getById(income.getDoc().getId());
      // doc.setExpired(0);
      // dDao.save(doc);

      Income in = inDao.getById(income.getId());

      in.setAdvice(income.getAdvice());
      in.setBalance(income.getBalance());
      in.setBonus(income.getBonus());
      in.setChildren(income.getChildren());
      // in.setCustomer(doc.getCustomer());
      in.setDeposit(income.getDeposit());
      in.setDividend(income.getDividend());
      // in.setDoc(doc);
      in.setExpensetotal(income.getExpensetotal());
      in.setFamily(income.getFamily());
      in.setFixedexpense(income.getFixedexpense());
      in.setFixedincome(income.getFixedincome());
      in.setIncome(income.getIncome());
      in.setIncometotal(income.getIncometotal());
      in.setInsurance(income.getInsurance());
      in.setMaintenance(income.getMaintenance());
      in.setMateincome(income.getMateincome());
      in.setMedical(income.getMedical());
      in.setRent(income.getRent());
      in.setRentexpense(income.getRentexpense());
      in.setSortdividend(income.getSortdividend());
      in.setTravel(income.getTravel());
      in.setUnfixedexpense(income.getUnfixedexpense());
      in.setUnfixedincome(income.getUnfixedincome());
      in.setUpdatetime(new Timestamp(System.currentTimeMillis()));

      inDao.save(in);
      result.put("msg", "修改成功!");
      result.put("status", 0);

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    return result.toString();
  }

}
