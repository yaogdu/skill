package com.isoftstone.smart.kingstone.service;

import java.util.List;
import java.util.logging.Logger;

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
import com.isoftstone.smart.kingstone.business.EducationService;
import com.isoftstone.smart.kingstone.entity.Education;

@Produces(MediaType.APPLICATION_JSON)
@Path("/education")
public class EducationResource extends BaseService<Education> {
  private final Logger logger = Logger.getLogger(EducationResource.class.getName());

  @Inject
  EducationService eDao;

  /***
   * new customer
   * 
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/collectInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectInfo(Education edu) throws JSONException {
    logger.info("collectinfo begins");
    JSONObject result = new JSONObject();
    try {

      eDao.save(edu);

      result.put("msg", "保存成功!");
      result.put("status", 0);
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    logger.info("collectinfo ends");
    return result.toString();
  }

  @Override
  public Class<Education> getEntityType() {
    // TODO Auto-generated method stub
    return Education.class;
  }

  @GET
  @Path("/list")
  public String list(@QueryParam("customerid") String customerid) throws JSONException {
    logger.info("education list begins");
    JSONObject result = new JSONObject();

    JSONArray eArray = new JSONArray();
    try {
      List<Education> edus = eDao.getList(customerid);
      if (edus != null && edus.size() > 0) {
        for (Education edu : edus) {

          JSONObject e = new JSONObject();
          e.put("course", edu.getCourse());
          e.put("rate", edu.getRate());
          e.put("year", edu.getYear());
          e.put("generatetime", String.valueOf(edu.getGeneratetime()));
          e.put("id", edu.getId());
          e.put("livecost", edu.getLivecost());
          e.put("tuition", edu.getTuition());
          e.put("updatetime", String.valueOf(edu.getUpdatetime()));

          eArray.put(e);
        }
        result.put("msg", "查询成功");
        result.put("status", 0);
        result.put("items", eArray);
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

    logger.info("education list ends");
    return result.toString();

  }

  @POST
  @Path("/modify")
  @Consumes(MediaType.APPLICATION_JSON)
  public String modify(Education education) throws JSONException {
    logger.info("modify begins");
    JSONObject result = new JSONObject();
    try {

      String eId = education.getId();
      Education e = eDao.getById(eId);
      if (e != null) {
        e.setCourse(education.getCourse());
        e.setGeneratetime(education.getGeneratetime());
        e.setLivecost(education.getLivecost());
        e.setYear(education.getYear());
        e.setUpdatetime(education.getUpdatetime());
        e.setTuition(education.getTuition());
        e.setRate(education.getRate());
        eDao.save(e);

        // Doc doc = new Doc();
        // doc.setConsultant(cus.getConsultant());
        // doc.setCustomer(cus);
        // doc.setExpired(0);
        // doc.setGeneratetime(new Timestamp(System.currentTimeMillis()));
        // doc.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        // dDao.save(doc);

        result.put("msg", "保存成功!");
        result.put("status", 0);
      } else {
        result.put("msg", "该记录不存在!");
        result.put("status", 1);
      }
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }

    logger.info("modify ends");
    return result.toString();
  }

}
