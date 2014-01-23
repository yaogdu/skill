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
import com.isoftstone.smart.kingstone.business.DocService;
import com.isoftstone.smart.kingstone.business.PensionService;
import com.isoftstone.smart.kingstone.entity.Doc;
import com.isoftstone.smart.kingstone.entity.Pension;

@Path("/pension")
@Produces(MediaType.APPLICATION_JSON)
public class PensionResource extends BaseService<Pension> {

  @Inject
  DocService dDao;

  @Inject
  PensionService pDao;

  /***
   * new pension info
   * 
   * @param pension
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/collectInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectInfo(Pension pension) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      // pension.setUpdatetime(new Timestamp(System.currentTimeMillis()));
      // pension.setGeneratetime(new Timestamp(System.currentTimeMillis()));
      //
      // Doc doc = dDao.getDocByCustomerid(pension.getCustomer().getId());
      //
      // if (doc != null) {
      // pension.setDoc(doc);
      // doc.setExpired(0);
      // dDao.save(doc);
      // }

      pDao.save(pension);

      result.put("msg", "保存成功");
      result.put("status", 0);
    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
    }
    return result.toString();
  }

  @Override
  public Class<Pension> getEntityType() {
    // TODO Auto-generated method stub
    return Pension.class;
  }

  @GET
  @Path("/getInfo")
  public String getInfo(@QueryParam("customerid") String customerid) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      Doc doc = dDao.getDocByCustomerid(customerid);
      List<Pension> ps = null;
      JSONArray pArray = new JSONArray();
      if (doc != null) {
        ps = pDao.getPensionByDocId(doc.getId());
        if (ps != null && ps.size() > 0) {
          for (int i = 0; i < ps.size(); i++) {
            Pension p = ps.get(i);

            JSONObject pObj = new JSONObject();

            pObj.put("age", p.getAge());
            pObj.put("rate", p.getRate());
            pObj.put("retireage", p.getRetireage());
            pObj.put("year", p.getYear());
            pObj.put("currentexpense", p.getCurrentexpense());
            pObj.put("id", p.getId());
            pObj.put("retireexpense", p.getRetireexpense());
            pObj.put("total", p.getTotal());
            pObj.put("docid", doc.getId());
            pObj.put("path", doc.getPath());
            pObj.put("expired", doc.getExpired());

            pArray.put(pObj);
          }
          result.put("msg", "查询成功");
          result.put("status", 0);
          result.put("items", pArray);
        } else {
          result.put("msg", "暂时没有相关信息");
          result.put("status", 1);
          result.put("items", new JSONArray());
        }
      } else {
        result.put("msg", "暂时没有相关信息");
        result.put("status", 1);
        result.put("items", new JSONArray());
      }

    } catch (Exception e) {
      result.put("msg", "暂时没有相关信息");
      result.put("status", 1);
      result.put("items", new JSONArray());
    }
    return result.toString();
  }

  @GET
  @Path("/list")
  public String list(@QueryParam("customerid") String customerid) throws JSONException {
    JSONObject result = new JSONObject();
    try {

      List<Pension> ps = null;
      JSONArray pArray = new JSONArray();

      ps = pDao.getList(customerid);
      if (ps != null && ps.size() > 0) {
        for (int i = 0; i < ps.size(); i++) {
          Pension p = ps.get(i);

          JSONObject pObj = new JSONObject();

          pObj.put("age", p.getAge());
          pObj.put("rate", p.getRate());
          pObj.put("retireage", p.getRetireage());
          pObj.put("year", p.getYear());
          pObj.put("currentexpense", p.getCurrentexpense());
          pObj.put("id", p.getId());
          pObj.put("retireexpense", p.getRetireexpense());
          pObj.put("total", p.getTotal());

          pArray.put(pObj);
        }
        result.put("msg", "查询成功");
        result.put("status", 0);
        result.put("items", pArray);
      } else {
        result.put("msg", "暂时没有相关信息");
        result.put("status", 1);
        result.put("items", new JSONArray());
      }

    } catch (Exception e) {
      result.put("msg", "暂时没有相关信息");
      result.put("status", 1);
      result.put("items", new JSONArray());
    }
    return result.toString();
  }

  /***
   * new pension info
   * 
   * @param pension
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/modify")
  @Consumes(MediaType.APPLICATION_JSON)
  public String modify(Pension pension) throws JSONException {
    JSONObject result = new JSONObject();

    try {

      // Doc doc = dDao.getById(pension.getDoc().getId());
      // doc.setExpired(0);
      // dDao.save(doc);

      Pension p = pDao.getById(pension.getId());

      p.setAge(pension.getAge());
      p.setCurrentexpense(pension.getCurrentexpense());
      // p.setDoc(doc);
      p.setRate(pension.getRate());
      p.setRetireage(pension.getRetireage());
      p.setYear(pension.getYear());
      p.setTotal(pension.getTotal());
      p.setRetireexpense(pension.getRetireexpense());
      p.setUpdatetime(new Timestamp(System.currentTimeMillis()));

      pDao.save(p);

      result.put("msg", "修改成功");
      result.put("status", 0);
    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
    }
    return result.toString();
  }

}
