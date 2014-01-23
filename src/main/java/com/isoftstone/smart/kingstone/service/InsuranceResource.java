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
import com.isoftstone.smart.kingstone.business.InsuranceService;
import com.isoftstone.smart.kingstone.entity.Doc;
import com.isoftstone.smart.kingstone.entity.Insurance;

@Path("/insurance")
@Produces(MediaType.APPLICATION_JSON)
public class InsuranceResource extends BaseService<Insurance> {

  @Inject
  DocService dDao;

  @Inject
  InsuranceService iDao;

  @POST
  @Path("/collectAdviceInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectAdviceInfo(Insurance insurance) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      // insurance.setUpdatetime(new Timestamp(System.currentTimeMillis()));
      // insurance.setGeneratetime(new Timestamp(System.currentTimeMillis()));
      // insurance.setType(1);
      // Doc doc = dDao.getDocByCustomerid(insurance.getCustomer().getId());
      //
      // if (doc != null) {
      // insurance.setDoc(doc);
      // doc.setExpired(0);
      // dDao.save(doc);
      // }
      iDao.save(insurance);
      result.put("status", 0);
      result.put("msg", "保存成功!");
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误!");
    }

    return result.toString();
  }

  @POST
  @Path("/collectOriginalInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectOriginalInfo(Insurance insurance) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      // insurance.setUpdatetime(new Timestamp(System.currentTimeMillis()));
      // insurance.setGeneratetime(new Timestamp(System.currentTimeMillis()));
      // insurance.setType(0);
      // Doc doc = dDao.getDocByCustomerid(insurance.getCustomer().getId());
      //
      // if (doc != null) {
      // insurance.setDoc(doc);
      // doc.setExpired(0);
      // dDao.save(doc);
      // }
      iDao.save(insurance);
      result.put("status", 0);
      result.put("msg", "保存成功!");
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误!");
    }

    return result.toString();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/delete")
  public String delete(Insurance insurance) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      // Doc doc = dDao.getById(insurance.getDoc().getId());
      // doc.setExpired(0);
      // dDao.save(doc);

      Insurance in = iDao.getById(insurance.getId());

      // in.setDoc(null);// set doc reference to null
      iDao.save(in);
      result.put("msg", "修改成功!");
      result.put("status", 0);

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    return result.toString();
  }

  @GET
  @Path("/getAdviceInfo")
  public String getAdviceInfo(@QueryParam("customerid") String customerid) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      Doc doc = dDao.getDocByCustomerid(customerid);
      List<Insurance> ins = null;
      JSONArray inArray = new JSONArray();
      if (doc != null) {
        ins = iDao.getLatestInfoByDocId(doc.getId(), 1);
        if (ins != null && ins.size() > 0) {
          for (int i = 0; i < ins.size(); i++) {
            Insurance in = ins.get(i);

            JSONObject inObj = new JSONObject();

            inObj.put("type", in.getType());
            inObj.put("year", in.getYear());
            inObj.put("generatetime", String.valueOf(in.getGeneratetime()));
            inObj.put("id", in.getId());
            inObj.put("insured", in.getInsured());
            inObj.put("memo", in.getMemo());
            inObj.put("name", in.getName());
            inObj.put("object", in.getObject());
            inObj.put("total", in.getTotal());
            inObj.put("updatetime", String.valueOf(in.getUpdatetime()));

            inObj.put("docid", doc.getId());
            inObj.put("path", doc.getPath());
            inObj.put("expired", doc.getExpired());

            inArray.put(inObj);
          }
          result.put("msg", "查询成功");
          result.put("status", 0);
          result.put("items", inArray);
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

  @Override
  public Class<Insurance> getEntityType() {
    // TODO Auto-generated method stub
    return Insurance.class;
  }

  @GET
  @Path("/getOriginalInfo")
  public String getOriginalInfo(@QueryParam("customerid") String customerid) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      Doc doc = dDao.getDocByCustomerid(customerid);
      List<Insurance> ins = null;
      JSONArray inArray = new JSONArray();
      if (doc != null) {
        ins = iDao.getLatestInfoByDocId(doc.getId(), 0);
        if (ins != null && ins.size() > 0) {
          for (int i = 0; i < ins.size(); i++) {
            Insurance in = ins.get(i);

            JSONObject inObj = new JSONObject();

            inObj.put("type", in.getType());
            inObj.put("year", in.getYear());
            inObj.put("generatetime", String.valueOf(in.getGeneratetime()));
            inObj.put("id", in.getId());
            inObj.put("insured", in.getInsured());
            inObj.put("memo", in.getMemo());
            inObj.put("name", in.getName());
            inObj.put("object", in.getObject());
            inObj.put("total", in.getTotal());
            inObj.put("updatetime", String.valueOf(in.getUpdatetime()));

            inObj.put("docid", doc.getId());
            inObj.put("path", doc.getPath());
            inObj.put("expired", doc.getExpired());

            inArray.put(inObj);
          }
          result.put("msg", "查询成功");
          result.put("status", 0);
          result.put("items", inArray);
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

      List<Insurance> ins = null;
      JSONArray inArray = new JSONArray();

      ins = iDao.getList(customerid);
      if (ins != null && ins.size() > 0) {
        for (int i = 0; i < ins.size(); i++) {
          Insurance in = ins.get(i);

          JSONObject inObj = new JSONObject();

          inObj.put("type", in.getType());
          inObj.put("year", in.getYear());
          inObj.put("generatetime", String.valueOf(in.getGeneratetime()));
          inObj.put("id", in.getId());
          inObj.put("insured", in.getInsured());
          inObj.put("memo", in.getMemo());
          inObj.put("name", in.getName());
          inObj.put("object", in.getObject());
          inObj.put("total", in.getTotal());
          inObj.put("updatetime", String.valueOf(in.getUpdatetime()));

          inArray.put(inObj);
        }
        result.put("msg", "查询成功");
        result.put("status", 0);
        result.put("items", inArray);
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

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/modify")
  public String modify(Insurance insurance) throws JSONException {
    JSONObject result = new JSONObject();
    try {
      // Doc doc = dDao.getById(insurance.getDoc().getId());
      // doc.setExpired(0);
      // dDao.save(doc);

      Insurance in = iDao.getById(insurance.getId());

      in.setInsured(insurance.getInsured());
      in.setMemo(insurance.getMemo());
      in.setName(insurance.getName());
      in.setObject(insurance.getObject());
      in.setTotal(insurance.getTotal());
      in.setYear(insurance.getYear());
      // in.setDoc(doc);

      in.setUpdatetime(new Timestamp(System.currentTimeMillis()));

      iDao.save(in);
      result.put("msg", "修改成功!");
      result.put("status", 0);

    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    return result.toString();
  }
}
