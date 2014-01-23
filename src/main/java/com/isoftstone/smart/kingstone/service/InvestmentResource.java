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
import com.isoftstone.smart.kingstone.business.InvestmentService;
import com.isoftstone.smart.kingstone.entity.Education;
import com.isoftstone.smart.kingstone.entity.Investment;

@Produces(MediaType.APPLICATION_JSON)
@Path("/investment")
public class InvestmentResource extends BaseService<Investment> {

  @Inject
  InvestmentService iDao;

  private final Logger logger = Logger.getLogger(EducationResource.class.getName());

  /***
   * new customer
   * 
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/collectInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectInfo(Investment in) throws JSONException {
    logger.info("collectinfo begins");
    JSONObject result = new JSONObject();
    try {

      iDao.save(in);

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
  public Class<Investment> getEntityType() {
    // TODO Auto-generated method stub
    return Investment.class;
  }

  @GET
  @Path("/list")
  public String list(@QueryParam("customerid") String customerid) throws JSONException {
    logger.info("investment list begins");
    JSONObject result = new JSONObject();

    JSONArray iArray = new JSONArray();
    try {
      List<Investment> investments = iDao.getList(customerid);
      if (investments != null && investments.size() > 0) {
        for (Investment i : investments) {

          JSONObject invest = new JSONObject();
          invest.put("month", i.getMonth());
          invest.put("rate", i.getRate());
          invest.put("year", i.getYear());
          invest.put("generatetime", i.getGeneratetime());
          invest.put("id", i.getId());
          invest.put("updatetime", i.getUpdatetime());
          invest.put("totalleft", i.getTotalleft());
          invest.put("totalinvest", i.getTotalinvest());
          iArray.put(invest);
        }
        result.put("msg", "查询成功");
        result.put("status", 0);
        result.put("items", iArray);
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

    logger.info("investment list ends");
    return result.toString();

  }

  @POST
  @Path("/modify")
  @Consumes(MediaType.APPLICATION_JSON)
  public String modify(Investment investment) throws JSONException {
    logger.info("modify begins");
    JSONObject result = new JSONObject();
    try {

      String id = investment.getId();
      Investment in = iDao.getById(id);
      if (in != null) {
        in.setGeneratetime(investment.getGeneratetime());
        in.setMonth(investment.getMonth());
        in.setRate(investment.getRate());
        in.setTotalinvest(investment.getTotalinvest());
        in.setTotalleft(investment.getTotalleft());
        in.setYear(investment.getYear());
        in.setUpdatetime(investment.getUpdatetime());
        iDao.save(in);

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
