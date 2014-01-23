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
import com.isoftstone.smart.kingstone.business.BalanceService;
import com.isoftstone.smart.kingstone.business.CustomerService;
import com.isoftstone.smart.kingstone.business.DocService;
import com.isoftstone.smart.kingstone.entity.Balance;
import com.isoftstone.smart.kingstone.entity.Doc;

@Path("/balance")
@Produces(MediaType.APPLICATION_JSON)
public class BalanceResource extends BaseService<Balance> {
  @Inject
  BalanceService bDao;

  @Inject
  CustomerService cDao;

  @Inject
  DocService dDao;

  @POST
  @Path("/collectInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectInfo(Balance balance) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      // balance.setUpdatetime(new Timestamp(System.currentTimeMillis()));
      // balance.setGeneratetime(new Timestamp(System.currentTimeMillis()));
      // Doc doc = dDao.getDocByCustomerid(balance.getCustomer().getId());
      //
      // if (doc != null) {
      // balance.setDoc(doc);
      // doc.setExpired(0);
      // dDao.save(doc);
      // }
      bDao.save(balance);
      result.put("status", 0);
      result.put("msg", "保存成功!");
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误!");
    }
    return result.toString();
  }

  @Override
  public Class<Balance> getEntityType() {
    return Balance.class;
  }

  /***
   * get the latest balance information by customerid
   * 
   * @param customerid
   * @return
   * @throws JSONException
   */
  @GET
  @Path("/getInfo")
  public String getInfo(@QueryParam("customerid") String customerid) throws JSONException {
    JSONObject result = new JSONObject();
    try {

      Doc doc = dDao.getDocByCustomerid(customerid);
      Balance balance = null;
      if (doc != null) {
        balance = bDao.getLatestInfo(doc.getId());

        if (balance != null) {

          JSONObject b = new JSONObject();

          b.put("assettotal", balance.getAssettotal());
          b.put("bonds", balance.getBonds());
          b.put("car", balance.getCar());
          b.put("caryear", balance.getCaryear());
          b.put("cash", balance.getCash());
          b.put("consume", balance.getConsume());
          b.put("consumeyear", balance.getConsumeyear());
          b.put("creditcard", balance.getCreditcard());
          b.put("creditcardyear", balance.getCreditcardyear());
          b.put("decoration", balance.getDecoration());
          b.put("decorationyear", balance.getDecorationyear());
          b.put("estate", balance.getEstate());
          b.put("fixedterm", balance.getFixedterm());
          b.put("folk", balance.getFolk());
          b.put("folkyear", balance.getFolkyear());
          b.put("fund", balance.getFund());
          b.put("generatetime", String.valueOf(balance.getGeneratetime()));
          b.put("housecommerce", balance.getHousecommerce());
          b.put("housecommerceyear", balance.getHousecommerceyear());
          b.put("housefund", balance.getHousefund());
          b.put("housefundyear", balance.getHousefundyear());
          b.put("id", balance.getId());
          b.put("loantotal", balance.getLoantotal());
          b.put("otherasset", balance.getOtherasset());
          b.put("updatetime", String.valueOf(balance.getUpdatetime()));
          b.put("unfixedterm", balance.getUnfixedterm());
          b.put("total", balance.getTotal());
          b.put("stock", balance.getStock());
          b.put("otherloanyear", balance.getOtherloanyear());
          b.put("otherloan", balance.getOtherloan());
          b.put("expired", doc.getExpired());
          b.put("path", doc.getPath());
          b.put("docid", doc.getId());
          result.put("msg", "查询成功!");
          result.put("status", 0);
          result.put("item", b);
        } else {
          result.put("msg", "暂时没有相关信息!");
          result.put("status", 1);
          result.put("item", new JSONObject());
        }
      } else {
        result.put("msg", "暂时没有相关信息!");
        result.put("status", 1);
        result.put("item", new JSONObject());
      }

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
      result.put("item", new JSONObject());
    }

    return result.toString();
  }

  /***
   * get the latest balance information by customerid
   * 
   * @param customerid
   * @return
   * @throws JSONException
   */
  @GET
  @Path("/list")
  public String list(@QueryParam("customerid") String customerid) throws JSONException {
    JSONObject result = new JSONObject();

    JSONArray bArray = new JSONArray();
    try {
      List<Balance> bals = bDao.getList(customerid);

      if (bals != null && bals.size() > 0) {
        for (Balance balance : bals) {
          JSONObject b = new JSONObject();

          b.put("assettotal", balance.getAssettotal());
          b.put("bonds", balance.getBonds());
          b.put("car", balance.getCar());
          b.put("caryear", balance.getCaryear());
          b.put("cash", balance.getCash());
          b.put("consume", balance.getConsume());
          b.put("consumeyear", balance.getConsumeyear());
          b.put("creditcard", balance.getCreditcard());
          b.put("creditcardyear", balance.getCreditcardyear());
          b.put("decoration", balance.getDecoration());
          b.put("decorationyear", balance.getDecorationyear());
          b.put("estate", balance.getEstate());
          b.put("fixedterm", balance.getFixedterm());
          b.put("folk", balance.getFolk());
          b.put("folkyear", balance.getFolkyear());
          b.put("fund", balance.getFund());
          b.put("generatetime", String.valueOf(balance.getGeneratetime()));
          b.put("housecommerce", balance.getHousecommerce());
          b.put("housecommerceyear", balance.getHousecommerceyear());
          b.put("housefund", balance.getHousefund());
          b.put("housefundyear", balance.getHousefundyear());
          b.put("id", balance.getId());
          b.put("loantotal", balance.getLoantotal());
          b.put("otherasset", balance.getOtherasset());
          b.put("updatetime", String.valueOf(balance.getUpdatetime()));
          b.put("unfixedterm", balance.getUnfixedterm());
          b.put("total", balance.getTotal());
          b.put("stock", balance.getStock());
          b.put("otherloanyear", balance.getOtherloanyear());
          b.put("otherloan", balance.getOtherloan());
          bArray.put(b);
        }
        result.put("msg", "查询成功!");
        result.put("status", 0);
        result.put("items", bArray);
      } else {
        result.put("msg", "暂时没有相关信息!");
        result.put("status", 1);
        result.put("item", new JSONArray());
      }

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
      result.put("item", new JSONObject());
    }

    return result.toString();
  }

  /***
   * modify balance information
   * 
   * @param balance
   * @return
   * @throws JSONException
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/modify")
  public String modify(Balance balance) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      // Doc doc = dDao.getById(balance.getDoc().getId());
      // doc.setExpired(0);
      //
      // dDao.save(doc);

      Balance bal = bDao.getById(balance.getId());

      bal.setUpdatetime(new Timestamp(System.currentTimeMillis()));
      bal.setAssettotal(balance.getAssettotal());
      bal.setBonds(balance.getBonds());
      bal.setCar(balance.getCar());
      bal.setCaryear(balance.getCaryear());
      bal.setCash(balance.getCash());
      bal.setConsume(balance.getConsume());
      bal.setConsumeyear(balance.getConsumeyear());
      bal.setCreditcard(balance.getCreditcard());
      bal.setCreditcardyear(balance.getCreditcardyear());
      // bal.setCustomer(cus);
      bal.setDecoration(balance.getDecoration());
      bal.setDecorationyear(balance.getDecorationyear());
      bal.setEstate(balance.getEstate());
      bal.setFixedterm(balance.getFixedterm());
      bal.setFolk(balance.getFolk());
      bal.setFolkyear(balance.getFolkyear());
      bal.setFund(balance.getFund());
      bal.setHousecommerce(balance.getHousecommerce());
      bal.setHousecommerceyear(balance.getHousecommerceyear());
      bal.setHousefund(balance.getHousefund());
      bal.setHousefundyear(balance.getHousefundyear());
      bal.setLoantotal(balance.getLoantotal());
      bal.setOtherasset(balance.getOtherasset());
      bal.setOtherloan(balance.getOtherloan());
      bal.setOtherloanyear(balance.getOtherloanyear());
      bal.setStock(balance.getStock());
      bal.setTotal(balance.getTotal());
      bal.setUnfixedterm(balance.getUnfixedterm());

      bDao.save(bal);
      result.put("msg", "修改成功!");
      result.put("status", 0);
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }

    return result.toString();
  }

}
