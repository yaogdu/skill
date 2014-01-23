package com.isoftstone.smart.kingstone.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.inject.Inject;
import com.isoftstone.smart.core.service.BaseService;
import com.isoftstone.smart.kingstone.business.CashconfigService;
import com.isoftstone.smart.kingstone.entity.CashConfig;

@Path("/cashconfig")
@Produces(MediaType.APPLICATION_JSON)
public class CashconfigResource extends BaseService<CashConfig> {
  private final Logger logger = Logger.getLogger(CashconfigResource.class.getName());

  @Inject
  CashconfigService cDao;

  @POST
  @Path("/collectInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectInfo(CashConfig cashconfig) throws JSONException {
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
      cDao.save(cashconfig);
      result.put("status", 0);
      result.put("msg", "保存成功!");
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误!");
    }
    return result.toString();
  }

  @Override
  public Class<CashConfig> getEntityType() {
    // TODO Auto-generated method stub
    return CashConfig.class;
  }

  @GET
  @Path("/list")
  public String list() throws JSONException {
    JSONObject result = new JSONObject();

    JSONArray cArray = new JSONArray();
    try {

      List<CashConfig> cashes = cDao.listAll();

      if (cashes != null && cashes.size() > 0) {
        for (CashConfig cash : cashes) {
          JSONObject c = new JSONObject();
          c.put("rate", cash.getRate());
          c.put("bonus", cash.getBonus());
          c.put("configsample", cash.getConfigsample());
          c.put("configtype", cash.getConfigtype());
          c.put("generatetime", String.valueOf(cash.getGeneratetime()));
          c.put("id", cash.getId());
          c.put("producttype", cash.getProducttype());
          c.put("returntime", cash.getReturntime());
          c.put("vendor", cash.getVendor());
          c.put("updatetime", String.valueOf(cash.getUpdatetime()));
          cArray.put(c);
        }
        result.put("msg", "查询成功!");
        result.put("status", 0);
        result.put("items", cArray);
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

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/modify")
  public String modify(CashConfig cash) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      // Doc doc = dDao.getById(balance.getDoc().getId());
      // doc.setExpired(0);
      //
      // dDao.save(doc);

      CashConfig cc = cDao.getById(cash.getId());

      cc.setBonus(cash.getBonus());
      cc.setConfigsample(cash.getConfigsample());
      cc.setConfigtype(cash.getConfigtype());
      cc.setGeneratetime(cash.getGeneratetime());
      cc.setProducttype(cash.getProducttype());
      cc.setRate(cash.getRate());
      cc.setVendor(cash.getVendor());
      cc.setUpdatetime(cash.getUpdatetime());
      cc.setReturntime(cash.getReturntime());
      cDao.save(cc);
      result.put("msg", "修改成功!");
      result.put("status", 0);
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }

    return result.toString();
  }

}
