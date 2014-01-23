package com.isoftstone.smart.kingstone.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.inject.Inject;
import com.isoftstone.smart.core.service.BaseService;
import com.isoftstone.smart.kingstone.business.DocService;
import com.isoftstone.smart.kingstone.business.RiskService;
import com.isoftstone.smart.kingstone.entity.Doc;
import com.isoftstone.smart.kingstone.entity.Risk;

@Path("/risk")
@Produces(MediaType.APPLICATION_JSON)
public class RiskResource extends BaseService<Risk> {

  @Inject
  DocService dDao;

  @Inject
  RiskService rDao;

  @POST
  @Path("/collectInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectInfo(Risk risk) throws JSONException {
    JSONObject result = new JSONObject();
    try {

      // risk.setUpdatetime(new Timestamp(System.currentTimeMillis()));
      // risk.setGeneratetime(new Timestamp(System.currentTimeMillis()));
      //
      // int level = 0;
      //
      // int point = risk.getPoint();
      //
      // if (point >= 0 && point <= 20) {
      // level = 0;
      // } else if (point >= 21 && point <= 34) {
      // level = 1;
      // } else {
      // level = 2;
      // }
      // risk.setInvestgrouplevel(level);
      //
      // Doc doc = dDao.getDocByCustomerid(risk.getCustomer().getId());
      //
      // if (doc != null) {
      // risk.setDoc(doc);
      // doc.setExpired(0);
      // dDao.save(doc);
      // }
      rDao.save(risk);
      result.put("msg", "保存成功");
      result.put("status", 0);

    } catch (Exception e) {
      result.put("msg", "保存失败");
      result.put("status", 0);
    }
    return result.toString();
  }

  @Override
  public Class<Risk> getEntityType() {
    // TODO Auto-generated method stub
    return Risk.class;
  }

  @GET
  @Path("/getInfo")
  public String getInfo(@QueryParam("customerid") String customerid) throws JSONException {
    JSONObject result = new JSONObject();
    try {

      Doc doc = dDao.getDocByCustomerid(customerid);
      Risk risk = null;
      if (doc != null) {
        risk = rDao.getInfo(doc.getId());

        if (risk != null) {

          JSONObject r = new JSONObject();
          r.put("investgrouplevel", risk.getInvestgrouplevel());
          r.put("point", risk.getPoint());
          r.put("id", risk.getId());
          r.put("generatetime", String.valueOf(risk.getGeneratetime()));
          r.put("updatetime", String.valueOf(risk.getUpdatetime()));
          r.put("expired", doc.getExpired());
          r.put("path", doc.getPath());
          r.put("docid", doc.getId());

          r.put("first", risk.getFirst());
          r.put("second", risk.getSecond());
          r.put("third", risk.getThird());
          r.put("fourth", risk.getFourth());
          r.put("fifth", risk.getFifth());
          r.put("sixth", risk.getSixth());
          r.put("seventh", risk.getSeventh());
          r.put("eighth", risk.getEighth());
          r.put("ninth", risk.getNinth());
          r.put("tenth", risk.getTenth());
          result.put("msg", "查询成功!");
          result.put("status", 0);
          result.put("item", r);
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

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/modify")
  public String modify(Risk risk) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      // Doc doc = dDao.getById(risk.getDoc().getId());
      // doc.setExpired(0);
      // dDao.save(doc);

      Risk r = rDao.getById(risk.getId());

      // int level = 0;
      //
      // int point = risk.getPoint();
      //
      // if (point >= 0 && point <= 20) {
      // level = 0;
      // } else if (point >= 21 && point <= 34) {
      // level = 1;
      // } else {
      // level = 2;
      // }
      r.setInvestgrouplevel(risk.getInvestgrouplevel());
      r.setPoint(risk.getPoint());
      r.setEighth(risk.getEighth());
      r.setFifth(risk.getFifth());
      r.setFirst(risk.getFirst());
      r.setFourth(risk.getFourth());
      r.setNinth(risk.getNinth());
      r.setSecond(risk.getSecond());
      r.setSeventh(risk.getSeventh());
      r.setSixth(risk.getSixth());
      r.setTenth(risk.getTenth());
      r.setThird(risk.getThird());
      r.setUpdatetime(risk.getUpdatetime());
      r.setGeneratetime(risk.getGeneratetime());
      r.setDoc(risk.getDoc());

      rDao.save(r);
      result.put("msg", "修改成功!");
      result.put("status", 0);
    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
    }
    return result.toString();
  }

}
