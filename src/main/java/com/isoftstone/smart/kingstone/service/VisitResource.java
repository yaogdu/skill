package com.isoftstone.smart.kingstone.service;

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
import com.isoftstone.smart.kingstone.business.VisitService;
import com.isoftstone.smart.kingstone.entity.Visit;

@Path("/visit")
@Produces(MediaType.APPLICATION_JSON)
public class VisitResource extends BaseService<Visit> {

  @Inject
  VisitService vDao;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/collectInfo")
  public String collectInfo(Visit visit) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      vDao.save(visit);
      result.put("status", 0);
      result.put("msg", "保存成功!");
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误!");
    }
    return result.toString();
  }

  @Override
  public Class<Visit> getEntityType() {
    // TODO Auto-generated method stub
    return Visit.class;
  }

  @GET
  @Path("/list")
  public String list(@QueryParam("customerid") String customerid) throws JSONException {
    JSONObject result = new JSONObject();

    JSONArray vArray = new JSONArray();

    try {
      List<Visit> visits = vDao.getList(customerid);
      if (visits != null) {
        for (Visit v : visits) {
          JSONObject obj = new JSONObject();
          obj.put("id", v.getId());
          obj.put("location", v.getLocation());
          obj.put("signature", v.getSignature());
          obj.put("visitcontent", v.getVisitcontent());
          obj.put("visitplace", v.getVisitplace());
          obj.put("visittime", String.valueOf(v.getVisittime()));
          vArray.put(obj);
        }
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("items", vArray);
      } else {
        result.put("status", 1);
        result.put("msg", "没有相关数据");
        result.put("items", new JSONArray());
      }

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误!");
      result.put("items", new JSONArray());
    }

    return result.toString();

  }
}
