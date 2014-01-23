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
import com.isoftstone.smart.kingstone.business.InvestgroupService;
import com.isoftstone.smart.kingstone.entity.Investgroup;

@Path("/investgroup")
@Produces(MediaType.APPLICATION_JSON)
public class InvestgroupResource extends BaseService<Investgroup> {

  @Inject
  InvestgroupService iDao;

  @POST
  @Path("/colectInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectInfo(Investgroup investgroup) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      iDao.save(investgroup);
      result.put("msg", "保存成功");
      result.put("status", 0);
    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
    }
    return result.toString();
  }

  @GET
  @Path("/getbylevel")
  public String getByLevel(@QueryParam("level") int level) throws JSONException {
    JSONObject result = new JSONObject();

    JSONArray iArray = new JSONArray();
    try {
      List<Investgroup> igs = iDao.getByLevel(level);

      if (igs != null && igs.size() > 0) {
        for (Investgroup ig : igs) {
          JSONObject obj = new JSONObject();
          obj.put("level", ig.getLevel());
          obj.put("percentage", ig.getPercentage());
          obj.put("fundname", ig.getFundname());
          obj.put("fundtype", ig.getFundtype());
          obj.put("generatetime", String.valueOf(ig.getGeneratetime()));
          obj.put("id", ig.getId());
          obj.put("updatetime", String.valueOf(ig.getUpdatetime()));
          obj.put("picpath", ig.getPicpath());
          obj.put("memo", ig.getMemo());
          iArray.put(obj);
          result.put("msg", "查询成功");
          result.put("status", 0);
          result.put("items", iArray);
        }
      } else {
        result.put("msg", "暂时没有相关数据");
        result.put("status", 1);
        result.put("items", new JSONArray());
      }

    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
      result.put("items", new JSONArray());
    }

    return result.toString();
  }

  @Override
  public Class<Investgroup> getEntityType() {
    return Investgroup.class;
  }

  @GET
  @Path("/list")
  public String list() throws JSONException {
    JSONObject result = new JSONObject();

    JSONArray iArray = new JSONArray();
    try {
      List<Investgroup> igs = iDao.listAll();

      if (igs != null && igs.size() > 0) {
        for (Investgroup ig : igs) {
          JSONObject obj = new JSONObject();
          obj.put("level", ig.getLevel());
          obj.put("percentage", ig.getPercentage());
          obj.put("fundname", ig.getFundname());
          obj.put("fundtype", ig.getFundtype());
          obj.put("generatetime", String.valueOf(ig.getGeneratetime()));
          obj.put("id", ig.getId());
          obj.put("updatetime", String.valueOf(ig.getUpdatetime()));
          obj.put("picpath", ig.getPicpath());
          obj.put("memo", ig.getMemo());
          iArray.put(obj);
          result.put("msg", "查询成功");
          result.put("status", 0);
          result.put("items", iArray);
        }
      } else {
        result.put("msg", "暂时没有相关数据");
        result.put("status", 1);
        result.put("items", new JSONArray());
      }

    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
      result.put("items", new JSONArray());
    }

    return result.toString();
  }

  @POST
  @Path("/modify")
  @Consumes(MediaType.APPLICATION_JSON)
  public String modify(Investgroup investgroup) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      Investgroup ig = iDao.getById(investgroup.getId());
      if (ig != null) {
        ig.setFundname(investgroup.getFundname());
        ig.setFundtype(investgroup.getFundtype());
        ig.setGeneratetime(investgroup.getGeneratetime());
        ig.setLevel(investgroup.getLevel());
        ig.setMemo(investgroup.getMemo());
        ig.setPercentage(investgroup.getPercentage());
        ig.setPicpath(investgroup.getPicpath());
        ig.setUpdatetime(investgroup.getUpdatetime());

        iDao.save(ig);
        result.put("msg", "保存成功");
        result.put("status", 0);
      } else {
        result.put("msg", "该信息不存在");
        result.put("status", 1);
      }

    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
    }
    return result.toString();
  }

}
