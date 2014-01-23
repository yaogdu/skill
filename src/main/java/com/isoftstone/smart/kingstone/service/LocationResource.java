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
import com.isoftstone.smart.kingstone.business.LocationService;
import com.isoftstone.smart.kingstone.entity.Consultant;
import com.isoftstone.smart.kingstone.entity.ConsultantLocation;

@Path("/location")
@Produces(MediaType.APPLICATION_JSON)
public class LocationResource extends BaseService<ConsultantLocation> {

  @Inject
  LocationService lDao;

  @Override
  public Class<ConsultantLocation> getEntityType() {
    // TODO Auto-generated method stub
    return ConsultantLocation.class;
  }

  @GET
  @Path("/route")
  public String route(@QueryParam("consultantid") String consultantid) throws JSONException {
    JSONObject result = new JSONObject();
    JSONArray lArray = new JSONArray();
    try {
      List<ConsultantLocation> locations = lDao.routeByConsultantid(consultantid);
      if (locations != null && locations.size() > 0) {
        for (ConsultantLocation cl : locations) {
          JSONObject obj = new JSONObject();
          Consultant c = cl.getConsultant();
          obj.put("id", cl.getId());
          obj.put("generatetime", String.valueOf(cl.getGeneratetime()));
          obj.put("location", cl.getLocation());
          obj.put("consultantid", c.getId());
          obj.put("consultantname", c.getName());
          lArray.put(obj);
        }
        result.put("msg", "查询成功");
        result.put("status", 0);
        result.put("items", lArray);
      } else {
        result.put("msg", "暂时没有相关数据");
        result.put("status", 1);
        result.put("items", lArray);
      }
    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
      result.put("items", new JSONArray());
    }
    return result.toString();

  }

  @POST
  @Path("/track")
  @Consumes(MediaType.APPLICATION_JSON)
  public String track(ConsultantLocation location) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      location.setGeneratetime(new Timestamp(System.currentTimeMillis()));
      lDao.save(location);
      result.put("msg", "保存成功");
      result.put("status", 0);

    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
    }
    return result.toString();

  }
}
