package com.isoftstone.smart.kingstone.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.inject.Inject;
import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.service.BaseService;
import com.isoftstone.smart.core.util.CoreUtil;
import com.isoftstone.smart.kingstone.business.AdjustAssetService;
import com.isoftstone.smart.kingstone.business.AdviceInvestService;
import com.isoftstone.smart.kingstone.business.BalanceService;
import com.isoftstone.smart.kingstone.business.CashconfigService;
import com.isoftstone.smart.kingstone.business.ConsultantService;
import com.isoftstone.smart.kingstone.business.ConsultantlocationService;
import com.isoftstone.smart.kingstone.business.CustomerService;
import com.isoftstone.smart.kingstone.business.DocRecordService;
import com.isoftstone.smart.kingstone.business.DocService;
import com.isoftstone.smart.kingstone.business.EducationService;
import com.isoftstone.smart.kingstone.business.IncomeService;
import com.isoftstone.smart.kingstone.business.InsuranceService;
import com.isoftstone.smart.kingstone.business.InvestgroupService;
import com.isoftstone.smart.kingstone.business.InvestmentService;
import com.isoftstone.smart.kingstone.business.JobConfigService;
import com.isoftstone.smart.kingstone.business.PensionService;
import com.isoftstone.smart.kingstone.business.QaService;
import com.isoftstone.smart.kingstone.business.RiskService;
import com.isoftstone.smart.kingstone.entity.AdjustAsset;
import com.isoftstone.smart.kingstone.entity.AdviceInvest;
import com.isoftstone.smart.kingstone.entity.Balance;
import com.isoftstone.smart.kingstone.entity.CashConfig;
import com.isoftstone.smart.kingstone.entity.Consultant;
import com.isoftstone.smart.kingstone.entity.ConsultantLocation;
import com.isoftstone.smart.kingstone.entity.Customer;
import com.isoftstone.smart.kingstone.entity.Doc;
import com.isoftstone.smart.kingstone.entity.DocRecord;
import com.isoftstone.smart.kingstone.entity.Education;
import com.isoftstone.smart.kingstone.entity.Income;
import com.isoftstone.smart.kingstone.entity.Insurance;
import com.isoftstone.smart.kingstone.entity.Investgroup;
import com.isoftstone.smart.kingstone.entity.Investment;
import com.isoftstone.smart.kingstone.entity.JobConfig;
import com.isoftstone.smart.kingstone.entity.Pension;
import com.isoftstone.smart.kingstone.entity.Qa;
import com.isoftstone.smart.kingstone.entity.Risk;
import com.isoftstone.smart.kingstone.utils.Models;
import com.isoftstone.smart.kingstone.utils.UploadUtil;

@Path("/doc")
@Produces(MediaType.APPLICATION_JSON)
public class DocResource extends BaseService<Doc> {

  @Inject
  DocService dDao;

  @Inject
  JobConfigService jcDao;

  @Inject
  CashconfigService ccDao;

  @Inject
  ConsultantService cDao;

  @Inject
  DocRecordService drDao;

  @Inject
  InvestgroupService igDao;

  @Inject
  CustomerService cusDao;

  @Inject
  IncomeService inDao;

  @Inject
  InsuranceService iDao;

  @Inject
  QaService qDao;

  @Inject
  BalanceService balDao;

  @Inject
  RiskService rDao;

  @Inject
  PensionService pDao;

  @Inject
  EducationService eDao;

  @Inject
  InvestmentService imDao;

  @Inject
  AdviceInvestService aiDao;

  @Inject
  AdjustAssetService aaDao;

  @Inject
  ConsultantlocationService clDao;

  @POST
  @Path("/collectInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectInfo(Doc doc) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      dDao.save(doc);
      result.put("msg", "保存成功");
      result.put("status", 0);

    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
    }
    return result.toString();
  }

  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/download")
  public String download(@Context HttpServletRequest request, @QueryParam("id") String id, @Context ServletContext servletContext,
      @Context HttpServletResponse response) throws IOException, JSONException {
    JSONObject result = new JSONObject();

    try {
      // Doc doc = dDao.getDocByCustomerid(customerid);
      Doc doc = dDao.getById(id);
      if (doc == null) {
        result.put("status", 1);
        result.put("msg", "文档信息错误");
        return result.toString();
      } else {

        File file = new File(doc.getPath());
        // 取得文件名。
        String filename = doc.getDocname();
        // 取得文件的后缀名。
        // String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
        // 以流的形式下载文件。
        InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        // 设置response的Header

        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        String name = "attachment;filename=" + new String(filename.getBytes("gb2312"), "iso8859-1");
        response.setHeader("Content-disposition", name);
        OutputStream ouputStream = response.getOutputStream();
        ouputStream.write(buffer);
        ouputStream.flush();

        result.put("output", ouputStream);
        result.put("status", 0);
        result.put("msg", "下载成功");
      }
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", 1);
      result.put("msg", "发生错误");
    }
    return result.toString();
  }

  @GET
  @Path("/downloaddata")
  @Produces(MediaType.APPLICATION_JSON)
  public String downloaddata(@QueryParam("consultantid") String consultantid) throws JSONException {
    JSONObject result = new JSONObject();

    try {

      Consultant consultant = cDao.getById(consultantid);

      JSONObject conObj = new JSONObject();
      if (consultant != null) {
        Account user = consultant.getAccount();
        conObj.put("degree", consultant.getDegree());
        conObj.put("gender", consultant.getGender());
        conObj.put("title", consultant.getTitle());
        conObj.put("accountlevel", user.getLevel());
        conObj.put("accountid", user.getId());
        conObj.put("accountstatus", user.getStatus());
        conObj.put("passwordhash", user.getPasswordHash());
        conObj.put("certificate", consultant.getCertificate());
        conObj.put("consultantno", consultant.getConsultantno());
        conObj.put("email", consultant.getEmail());
        conObj.put("experience", consultant.getExperience());
        conObj.put("feature", consultant.getFeature());
        conObj.put("generatetime", String.valueOf(consultant.getGeneratetime()));
        conObj.put("id", consultant.getId());
        conObj.put("mobile", consultant.getMobile());
        conObj.put("name", consultant.getName());
      }
      result.put("consultant", conObj);

      List<Customer> customers = cusDao.getByConsultantId(consultantid);
      JSONArray cusArray = new JSONArray();
      if (customers != null && customers.size() > 0) {
        for (Customer customer : customers) {
          JSONObject cus = new JSONObject();
          cus.put("done", customer.getDone());
          cus.put("gender", customer.getGender());
          cus.put("married", customer.getMarried());
          cus.put("src", customer.getSrc());
          cus.put("status", customer.getStatus());
          cus.put("address", customer.getAddress());
          cus.put("age", customer.getAge());
          cus.put("childrenage", customer.getChildrenage());
          cus.put("client", customer.getClient());
          cus.put("completetime", String.valueOf(customer.getCompletetime()));
          cus.put("consultantid", customer.getConsultant().getId());
          cus.put("contact", customer.getContact());
          cus.put("contactphone", customer.getContactphone());
          cus.put("email", customer.getEmail());
          cus.put("familymember", customer.getFamilymember());
          cus.put("generatetime", String.valueOf(customer.getGeneratetime()));
          cus.put("id", customer.getId());
          cus.put("idcard", customer.getIdcard());
          cus.put("industry", customer.getIndustry());
          cus.put("job", customer.getJob());
          cus.put("location", customer.getLocation());
          cus.put("mateage", customer.getMateage());
          cus.put("matename", customer.getMatename());
          cus.put("memo", customer.getMemo());
          cus.put("mobile", customer.getMobile());
          cus.put("name", customer.getName());
          cus.put("photo", customer.getPhoto()); // download files
          cus.put("signature", customer.getSignature());// download files
          cus.put("signaturetime", String.valueOf(customer.getSignaturetime())); // timestamp
                                                                                 // convert to ss
          cus.put("updatetime", String.valueOf(customer.getUpdatetime()));
          cusArray.put(cus);
        }
      }

      result.put("customers", cusArray);

      List<Balance> balances = balDao.getListbyConsultant(consultantid);
      JSONArray balArray = new JSONArray();
      if (balances != null && balances.size() > 0) {
        for (Balance balance : balances) {
          JSONObject b = new JSONObject();
          b.put("assettotal", balance.getAssettotal());
          b.put("bonds", balance.getBonds());
          b.put("bondsincome", balance.getBondsincome());
          b.put("car", balance.getCar());
          b.put("caryear", balance.getCaryear());
          b.put("cash", balance.getCash());
          b.put("cashincome", balance.getCashincome());
          b.put("consume", balance.getConsume());
          b.put("consumeyear", balance.getConsumeyear());
          b.put("creditcard", balance.getCreditcard());
          b.put("creditcardyear", balance.getCreditcardyear());
          b.put("customerid", balance.getCustomer().getId());
          b.put("decoration", balance.getDecoration());
          b.put("decorationyear", balance.getDecorationyear());
          b.put("docid", balance.getDoc().getId());
          b.put("estate", balance.getEstate());
          b.put("estateincome", balance.getEstateincome());
          b.put("fixedterm", balance.getFixedterm());
          b.put("fixedtermincome", balance.getFixedtermincome());
          b.put("folk", balance.getFolk());
          b.put("folkyear", balance.getFolkyear());
          b.put("fund", balance.getFund());
          b.put("fundincome", balance.getFundincome());
          b.put("generatetime", String.valueOf(balance.getGeneratetime()));
          b.put("housecommerce", balance.getHousecommerce());
          b.put("housecommerceyear", balance.getHousecommerceyear());
          b.put("housefund", balance.getHousefund());
          b.put("housefundyear", balance.getHousefundyear());
          b.put("id", balance.getId());
          b.put("loantotal", balance.getLoantotal());
          b.put("loantotalyear", balance.getLoantotalyear());
          b.put("otherasset", balance.getOtherasset());
          b.put("otherassetincome", balance.getOtherassetincome());
          b.put("otherloan", balance.getOtherloan());
          b.put("otherloanyear", balance.getOtherloanyear());
          b.put("stock", balance.getStock());
          b.put("stockincome", balance.getStockincome());
          b.put("total", balance.getTotal());
          b.put("unfixedterm", balance.getUnfixedterm());
          b.put("unfixedtermincome", balance.getUnfixedtermincome());
          b.put("updatetime", String.valueOf(balance.getUpdatetime()));
          balArray.put(b);
        }
      }
      result.put("balances", balArray);

      List<Income> incomes = inDao.getListbyconsultant(consultantid);
      JSONArray inArray = new JSONArray();
      if (incomes != null && incomes.size() > 0) {
        for (Income income : incomes) {
          JSONObject in = new JSONObject();
          in.put("type", income.getType());
          in.put("customerid", income.getCustomer().getId());
          in.put("balance", income.getBalance());

          in.put("bonuschange", income.getBonuschange());
          in.put("childrenchange", income.getChildrenchange());
          in.put("depositchange", income.getDepositchange());
          in.put("dividendchange", income.getDividendchange());
          in.put("expensetotalchange", income.getExpensetotalchange());
          in.put("familychange", income.getFamilychange());
          in.put("fixedexpensechange", income.getFixedexpensechange());
          in.put("fixedincomechange", income.getFixedincomechange());
          in.put("incomechange", income.getIncomechange());
          in.put("incometotalchange", income.getIncometotalchange());
          in.put("insurancechange", income.getInsurancechange());
          in.put("maintenancechange", income.getMaintenancechange());
          in.put("mateincomechange", income.getMateincomechange());
          in.put("medicalchange", income.getMedicalchange());
          in.put("rentchange", income.getRentchange());
          in.put("rentexpensechange", income.getRentexpensechange());
          in.put("sortdividendchange", income.getSortdividendchange());
          in.put("travelchange", income.getTravelchange());
          in.put("unfixedexpensechange", income.getUnfixedexpensechange());
          in.put("unfixedincomechange", income.getUnfixedincomechange());

          in.put("advice", income.getAdvice());
          in.put("bonus", income.getBonus());
          in.put("children", income.getChildren());
          in.put("docid", income.getDoc().getId());
          in.put("deposit", income.getDeposit());
          in.put("dividend", income.getDividend());
          in.put("expensetotal", income.getExpensetotal());
          in.put("family", income.getFamily());
          in.put("fixedexpense", income.getFixedexpense());
          in.put("fixedincome", income.getFixedincome());
          in.put("generatetime", String.valueOf(income.getGeneratetime()));
          in.put("id", income.getId());
          in.put("income", income.getIncome());
          in.put("incometotal", income.getIncometotal());
          in.put("insurance", income.getInsurance());
          in.put("maintenance", income.getMaintenance());
          in.put("mateincome", income.getMateincome());
          in.put("medical", income.getMedical());
          in.put("rent", income.getRent());
          in.put("rentexpense", income.getRentexpense());
          in.put("sortdividend", income.getSortdividend());
          in.put("travel", income.getTravel());
          in.put("unfixedexpense", income.getUnfixedexpense());
          in.put("unfixedincome", income.getUnfixedincome());
          in.put("updatetime", String.valueOf(income.getUpdatetime()));
          inArray.put(in);
        }
      }
      result.put("income", inArray);

      List<Insurance> insurances = iDao.getListbyconsultant(consultantid);
      JSONArray iArray = new JSONArray();
      if (insurances != null && insurances.size() > 0) {
        for (Insurance in : insurances) {
          JSONObject inObj = new JSONObject();
          inObj.put("type", in.getType());
          inObj.put("customerid", in.getCustomer().getId());
          inObj.put("year", in.getYear());
          inObj.put("generatetime", String.valueOf(in.getGeneratetime()));
          inObj.put("id", in.getId());
          inObj.put("insured", in.getInsured());
          inObj.put("insuredpeople", in.getInsuredpeople());
          inObj.put("memo", in.getMemo());
          inObj.put("name", in.getName());
          inObj.put("object", in.getObject());
          inObj.put("leftyear", in.getLeftyear());
          inObj.put("total", in.getTotal());
          inObj.put("docid", in.getDoc().getId());
          inObj.put("updatetime", String.valueOf(in.getUpdatetime()));
          iArray.put(inObj);
        }
      }
      result.put("insurances", insurances);

      List<Risk> risks = rDao.getListbyconsultant(consultantid);
      JSONArray rArray = new JSONArray();
      if (risks != null && risks.size() > 0) {
        for (Risk risk : risks) {
          JSONObject r = new JSONObject();
          r.put("investgrouplevel", risk.getInvestgrouplevel());
          r.put("point", risk.getPoint());
          r.put("id", risk.getId());
          r.put("generatetime", String.valueOf(risk.getGeneratetime()));
          r.put("updatetime", String.valueOf(risk.getUpdatetime()));
          r.put("chosenlevel", risk.getChosenlevel());
          r.put("customerid", risk.getCustomer().getId());

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
          rArray.put(r);
        }
      }
      result.put("risks", rArray);

      List<Pension> pensions = pDao.getListbyconsultant(consultantid);
      JSONArray pArray = new JSONArray();
      if (pensions != null && pensions.size() > 0) {
        for (Pension p : pensions) {
          JSONObject pObj = new JSONObject();
          pObj.put("age", p.getAge());
          pObj.put("rate", p.getRate());
          pObj.put("retireage", p.getRetireage());
          pObj.put("year", p.getYear());
          pObj.put("currentexpense", p.getCurrentexpense());
          pObj.put("id", p.getId());
          pObj.put("retireexpense", p.getRetireexpense());
          pObj.put("total", p.getTotal());
          pObj.put("customerid", p.getCustomer().getId());
          pObj.put("generatetime", String.valueOf(p.getGeneratetime()));
          pObj.put("updatetime", String.valueOf(p.getUpdatetime()));
          pObj.put("docid", p.getDoc().getId());
          pArray.put(pObj);
        }
      }
      result.put("pensions", pArray);

      List<Education> edus = eDao.getListbyconsultant(consultantid);
      JSONArray eArray = new JSONArray();

      if (edus != null && edus.size() > 0) {
        for (Education edu : edus) {
          JSONObject e = new JSONObject();
          e.put("course", edu.getCourse());
          e.put("rate", edu.getRate());
          e.put("year", edu.getYear());
          e.put("generatetime", String.valueOf(edu.getGeneratetime()));
          e.put("id", edu.getId());
          e.put("livecost", edu.getLivecost());

          // 11.25

          e.put("getinage", edu.getGetinage());
          e.put("tuitionrate", edu.getTuitionrate());
          e.put("getintuition", edu.getGetintuition());
          e.put("totaltuition", edu.getTotaltuition());
          e.put("getinlivecost", edu.getGetinlivecost());
          e.put("totallivecost", edu.getTotallivecost());
          e.put("age", edu.getAge());

          e.put("total", edu.getTotal());
          e.put("tuition", edu.getTuition());
          e.put("updatetime", String.valueOf(edu.getUpdatetime()));
          e.put("country", edu.getCountry());
          e.put("child", edu.getChild());
          e.put("customerid", edu.getCustomer().getId());
          e.put("docid", edu.getDoc().getId());
          eArray.put(e);
        }
      }
      result.put("educations", eArray);

      List<Investment> investments = imDao.getListbyconsultant(consultantid);
      JSONArray investArray = new JSONArray();
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
          invest.put("customerid", i.getCustomer().getId());
          invest.put("docid", i.getDoc().getId());
          investArray.put(invest);
        }
      }
      result.put("investments", investArray);

      List<Investgroup> investgroups = igDao.listAll();
      JSONArray igArray = new JSONArray();
      if (investgroups != null && investgroups.size() > 0) {
        for (Investgroup ig : investgroups) {
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
          igArray.put(obj);
        }
      }
      result.put("investgroups", igArray);

      List<Doc> docs = dDao.getListbyconsultant(consultantid);

      JSONArray dArray = new JSONArray();
      if (docs != null && docs.size() > 0) {
        for (Doc doc : docs) {
          JSONObject d = new JSONObject();
          d.put("id", doc.getId());
          d.put("customerid", doc.getCustomer().getId());
          d.put("consultantid", doc.getConsultant().getId());
          d.put("cashconfigstate", doc.getCashconfigstate());
          d.put("cashbackup", doc.getCashbackup());
          d.put("protectproduct", doc.getProtectproduct());
          d.put("investgoods", doc.getInvestgoods());
          d.put("housing", doc.getHousing());
          d.put("balance", doc.getBalance());
          d.put("cashbackupmemo", doc.getCashbackupmemo());
          d.put("protectproductmemo", doc.getProtectproductmemo());
          d.put("investgoodsmemo", doc.getInvestgoodsmemo());
          d.put("housingmemo", doc.getHousingmemo());
          d.put("balancememo", doc.getBalancememo());
          d.put("cashplanning", doc.getCashplanning());
          d.put("riskmanage", doc.getRiskmanage());
          d.put("riskstate", doc.getRiskstate());
          d.put("educationstate", doc.getEducationstate());
          d.put("stableinvest", doc.getStableinvest());
          d.put("fundmove", doc.getFundmove());
          d.put("fixedstate", doc.getFixedstate());
          d.put("yale", doc.getYale());
          d.put("otheryale", doc.getOtheryale());
          d.put("investplanning", doc.getInvestplanning());
          d.put("country", doc.getCountry());
          d.put("inflation", doc.getInflation());
          d.put("tuitionrate", doc.getTuitionrate());
          d.put("educationplanning", doc.getEducationplanning());
          d.put("investmoney", doc.getInvestmoney());
          d.put("investyear", doc.getInvestyear());
          d.put("docname", doc.getDocname());
          d.put("expired", doc.getExpired());
          d.put("financetarget", doc.getFinancetarget());
          d.put("generatetime", doc.getGeneratetime());
          d.put("path", doc.getPath());
          d.put("updatetime", String.valueOf(doc.getUpdatetime()));
          d.put("generatetime", String.valueOf(doc.getGeneratetime()));
          dArray.put(d);
        }
      }
      result.put("docs", dArray);

      List<AdviceInvest> adviceinvests = aiDao.getListbyconsultant(consultantid);
      JSONArray aiArray = new JSONArray();

      if (adviceinvests != null && adviceinvests.size() > 0) {
        for (AdviceInvest ai : adviceinvests) {
          JSONObject obj = new JSONObject();
          obj.put("percentage", ai.getPercentage());
          obj.put("year", ai.getYear());
          obj.put("amount", ai.getAmount());
          obj.put("brand", ai.getBrand());
          obj.put("generatetime", String.valueOf(ai.getGeneratetime()));
          obj.put("id", ai.getId());
          obj.put("memo", ai.getMemo());
          obj.put("customerid", ai.getCustomer().getId());
          obj.put("docid", ai.getDoc().getId());
          obj.put("income", ai.getIncome());
          aiArray.put(obj);
        }
      }
      result.put("adviceinvests", aiArray);

      List<AdjustAsset> adjustassets = aaDao.getListbyconsultant(consultantid);
      JSONArray asArray = new JSONArray();
      if (adjustassets != null && adjustassets.size() > 0) {
        for (AdjustAsset aa : adjustassets) {
          JSONObject asset = new JSONObject();
          asset.put("type", aa.getType());
          asset.put("adjust", aa.getAdjust());
          asset.put("adviceconfig", aa.getAdviceconfig());
          asset.put("funds", aa.getFunds());
          asset.put("generatetime", String.valueOf(aa.getGeneratetime()));
          asset.put("updatetime", String.valueOf(aa.getUpdatetime()));
          asset.put("state", aa.getState());
          asset.put("name", aa.getName());
          asset.put("memo", aa.getMemo());
          asset.put("docid", aa.getDoc().getId());
          asset.put("customerid", aa.getCustomer().getId());
          asset.put("id", aa.getId());
          asArray.put(asset);
        }
      }
      result.put("adjustassets", asArray);

      List<Qa> qas = qDao.listAll();
      JSONArray qArray = new JSONArray();

      JSONObject servant = new JSONObject();

      if (qas != null && qas.size() > 0) {
        servant.put("servanttel", qas.get(0).getServanttel());
        servant.put("servantemail", qas.get(0).getServantemail());
        for (Qa q : qas) {
          JSONObject o = new JSONObject();
          o.put("id", q.getId());
          o.put("question", q.getQuestion());
          o.put("answer", q.getAnswer());
          o.put("generatetime", String.valueOf(q.getGeneratetime()));
          qArray.put(o);
        }
      }
      result.put("qas", qArray);
      result.put("servant", servant);

      List<JobConfig> jcs = jcDao.listAll();
      JSONArray jArray = new JSONArray();

      if (jcs != null && jcs.size() > 0) {

        for (JobConfig jc : jcs) {
          JSONObject o = new JSONObject();
          o.put("id", jc.getId());
          o.put("name", jc.getName());
          o.put("generatetime", String.valueOf(jc.getGeneratetime()));
          jArray.put(o);
        }
      }
      result.put("jobconfig", jArray);

      result.put("status", 0);
      result.put("msg", "下载成功");
    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
    }

    return result.toString();
  }

  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/downpdf")
  public byte[] downpdf(@Context HttpServletRequest request, @QueryParam("id") String id, @Context ServletContext servletContext,
      @Context HttpServletResponse response) throws IOException, JSONException {

    try {
      // Doc doc = dDao.getDocByCustomerid(customerid);
      Doc doc = dDao.getById(id);

      File file = new File(doc.getPath());
      // 取得文件名。
      String filename = doc.getDocname();
      // 取得文件的后缀名。
      // String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
      // 以流的形式下载文件。
      InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
      byte[] buffer = new byte[fis.available()];
      fis.read(buffer);

      return buffer;
      // fis.close();
      // response.reset();
      // // 设置response的Header
      //
      // response.setContentType("application/pdf");
      // response.setCharacterEncoding("UTF-8");
      // String name = "attachment;filename=" + new String(filename.getBytes("gb2312"),
      // "iso8859-1");
      // response.setHeader("Content-disposition", name);
      // OutputStream ouputStream = response.getOutputStream();
      // ouputStream.write(buffer);
      // ouputStream.flush();
      //
      // result.put("output", ouputStream);
      // result.put("status", 0);
      // result.put("msg", "下载成功");
      // }
    } catch (Exception e) {
      return null;
    }

  }

  @Override
  public Class<Doc> getEntityType() {
    // TODO Auto-generated method stub
    return Doc.class;
  }

  @POST
  @Path("/modify")
  @Consumes(MediaType.APPLICATION_JSON)
  public String modify(Doc doc) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      Doc d = dDao.getById(doc.getId());

      d.setBalance(doc.getBalance());
      d.setCashbackup(doc.getCashbackup());
      // d.setCashconfig(doc.getCashconfig());
      d.setCashconfigstate(doc.getCashconfigstate());
      d.setCashplanning(doc.getCashplanning());
      d.setDocname(doc.getDocname());
      d.setEducationplanning(doc.getEducationplanning());
      d.setExpired(doc.getExpired());
      d.setFinancetarget(doc.getFinancetarget());
      d.setGeneratetime(doc.getGeneratetime());
      d.setHousing(doc.getHousing());
      d.setInvestgoods(doc.getInvestgoods());
      d.setInvestplanning(doc.getInvestplanning());
      d.setPath(doc.getPath());
      d.setProtectproduct(doc.getProtectproduct());
      d.setRiskmanage(doc.getRiskmanage());
      d.setRiskstate(doc.getRiskstate());
      d.setUpdatetime(doc.getUpdatetime());

      dDao.save(d);
      result.put("msg", "保存成功");
      result.put("status", 0);

    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
    }
    return result.toString();
  }

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/risksignature/{riskid}")
  public String risksignature(@Context HttpServletRequest request, @PathParam("riskid") String riskid,
      @Context ServletContext servletContext) throws IOException, JSONException {
    JSONObject result = new JSONObject();

    String picpath = servletContext.getInitParameter("risksignature");// save picture directory

    try {

      // String filepath =
      // request.getSession().getServletContext().getRealPath("/sc/resources/scTemplate/schedulingReport.xls");
      UploadUtil upload = new UploadUtil();
      upload.setMap(request);
      Map<String, FileItem> files = upload.getFiles();

      for (Map.Entry<String, FileItem> key : files.entrySet()) {

        String fileName = key.getValue().getName();// 文件名
        if (!upload.isPdf(fileName)) {
          result.put("status", 1);
          result.put("msg", "文件格式不正确");
          return result.toString();
        }
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);// 后缀
        String newName = CoreUtil.randomID() + "." + suffix;// 随机文件名称

        String actualPath = picpath + System.getProperty("file.separator") + newName;

        File file = new File(picpath);

        File parent = file.getParentFile();
        System.out.println("filepath:==" + file.getAbsolutePath());
        System.out.println("parentpath==" + parent.getAbsolutePath());
        if (parent != null) {
          if (parent.isDirectory() && !parent.exists()) {
            parent.mkdirs();
          }
        } else {
          file.mkdir();
        }

        Risk risk = rDao.getById(riskid);

        if (risk == null) {
          result.put("status", 1);
          result.put("msg", "信息不存在");
          return result.toString();
        }

        // String originalPath = risk.getSignature();
        // if (originalPath != null && !"".equals(originalPath)) {
        // File oFile = new File(originalPath);
        // if (oFile.exists()) {
        // oFile.delete();
        // }
        // }

        file = new File(actualPath);
        file.setWritable(true, false);
        if (!file.exists()) {
          file.createNewFile();
        }
        files.get(key.getKey()).write(file);

        risk.setSignature(newName);

        DocRecord dr = new DocRecord();
        dr.setPath(actualPath);
        dr.setGeneratetime(risk.getSignaturetime());
        dr.setRisk(risk);
        dr.setCustomer(risk.getCustomer());
        dr.setConsultant(risk.getCustomer().getConsultant());
        dr.setDoc(risk.getDoc());
        dr.setDocname(fileName);
        dr.setType(1);
        drDao.save(dr);

        rDao.save(risk);
      }
      result.put("status", 0);
      result.put("msg", "上传成功");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", 1);
      result.put("msg", "发生错误");
    }
    return result.toString();
  }

  @POST
  @Path("/sync")
  @Consumes(MediaType.APPLICATION_JSON)
  public String sync(Models model) throws JSONException {

    JSONObject result = new JSONObject();
    try {
      List<Customer> customers = model.getCustomers();
      if (customers != null && customers.size() > 0) {
        for (Customer customer : customers) {
          Customer cus = cusDao.getById(customer.getId());
          if (cus != null) {
            cus.setAddress(customer.getAddress());
            cus.setAge(customer.getAge());
            cus.setChildrenage(customer.getChildrenage());
            cus.setEmail(customer.getEmail());
            cus.setFamilymember(customer.getFamilymember());
            cus.setGender(customer.getGender());
            cus.setStatus(customer.getStatus());
            cus.setJob(customer.getJob());
            cus.setMarried(customer.getMarried());
            cus.setMateage(customer.getMateage());
            cus.setMatename(customer.getMatename());
            cus.setPhoto(customer.getPhoto());
            cus.setIdcard(customer.getIdcard());
            cus.setUpdatetime(customer.getUpdatetime());
            cus.setName(customer.getName());
            cus.setMobile(customer.getMobile());
            cus.setMemo(customer.getMemo());
            cus.setContact(customer.getContact());
            cus.setCompletetime(customer.getCompletetime());
            cus.setContactphone(customer.getContactphone());
            cus.setId(customer.getId());
            cus.setConsultant(cDao.getById(customer.getConsultantid()));
            cusDao.save(cus);
          } else {
            customer.setConsultant(cDao.getById(customer.getConsultantid()));
            cusDao.save(customer);
          }
        }
      }

      List<CashConfig> ccs = model.getCashconfigs();
      if (ccs != null && ccs.size() > 0) {
        for (CashConfig cc : ccs) {
          CashConfig c = ccDao.getById(cc.getId());
          if (c != null) {
            c.setBonus(cc.getBonus());
            c.setConfigsample(cc.getConfigsample());
            c.setConfigtype(cc.getConfigtype());
            c.setGeneratetime(cc.getGeneratetime());
            c.setId(cc.getId());
            c.setProducttype(cc.getProducttype());
            c.setRate(cc.getRate());
            c.setReturntime(cc.getReturntime());
            c.setUpdatetime(cc.getUpdatetime());
            c.setVendor(cc.getVendor());
            ccDao.save(c);
          } else {
            ccDao.save(cc);
          }
        }
      }

      List<ConsultantLocation> cls = model.getConsultantlocations();
      if (cls != null && cls.size() > 0) {
        for (ConsultantLocation cl : cls) {
          cl.setCustomer(cusDao.getById(cl.getCustomerid()));
          cl.setConsultant(cDao.getById(cl.getConsultantid()));
          clDao.save(cl);
        }
      }

      List<Doc> docs = model.getDocs();

      if (docs != null && docs.size() > 0) {
        for (Doc doc : docs) {
          Doc document = dDao.getById(doc.getId());
          if (document != null) {
            document.setAdjustIncome(doc.getAdjustIncome());
            document.setBalance(doc.getBalance());
            document.setCashbackup(doc.getCashbackup());
            document.setCustomer(cusDao.getById(doc.getCustomerid()));
            document.setCashconfigstate(doc.getCashconfigstate());
            document.setCashplanning(doc.getCashplanning());
            document.setCountry(doc.getCountry());
            document.setEducationplanning(doc.getEducationplanning());
            document.setExpired(doc.getExpired());
            document.setFinancetarget(doc.getFinancetarget());
            document.setHousing(doc.getHousing());
            document.setInflation(doc.getInflation());
            document.setInvestgoods(doc.getInvestgoods());
            document.setInvestmoney(doc.getInvestmoney());
            document.setFixedstate(doc.getFixedstate());
            document.setYale(doc.getYale());
            document.setCashbackupmemo(doc.getCashbackupmemo());
            document.setProtectproductmemo(doc.getProtectproductmemo());
            document.setInvestgoodsmemo(doc.getInvestgoodsmemo());
            document.setHousingmemo(doc.getHousingmemo());
            document.setBalancememo(doc.getBalancememo());
            document.setOtheryale(doc.getOtheryale());
            document.setEducationstate(doc.getEducationstate());
            document.setStableinvest(doc.getStableinvest());
            document.setFundmove(doc.getFundmove());
            document.setInvestplanning(doc.getInvestplanning());
            document.setInvestyear(doc.getInvestyear());
            document.setProtectproduct(doc.getProtectproduct());
            document.setRiskmanage(doc.getRiskmanage());
            document.setRiskstate(doc.getRiskstate());
            document.setTuitionrate(doc.getTuitionrate());
            document.setUpdatetime(doc.getUpdatetime());
            document.setConsultant(cDao.getById(doc.getConsultantid()));
            dDao.save(document);
          } else {
            doc.setCustomer(cusDao.getById(doc.getCustomerid()));
            doc.setConsultant(cDao.getById(doc.getConsultantid()));
            dDao.save(doc);
          }
        }
      }

      List<Balance> balances = model.getBalances();

      if (balances != null && balances.size() > 0) {
        for (Balance balance : balances) {
          Balance bal = balDao.getById(balance.getId());
          if (bal != null) {
            bal.setUpdatetime(balance.getUpdatetime());
            bal.setAssettotal(balance.getAssettotal());
            bal.setBonds(balance.getBonds());
            bal.setCar(balance.getCar());
            bal.setCaryear(balance.getCaryear());
            bal.setCash(balance.getCash());
            // 11.21
            bal.setCashincome(balance.getCashincome());
            bal.setUnfixedtermincome(balance.getUnfixedtermincome());
            bal.setFixedtermincome(balance.getFixedtermincome());
            bal.setStockincome(balance.getStockincome());
            bal.setFundincome(balance.getFundincome());
            bal.setBondsincome(balance.getBondsincome());
            bal.setEstateincome(balance.getEstateincome());
            bal.setOtherassetincome(balance.getOtherassetincome());

            bal.setConsume(balance.getConsume());
            bal.setConsumeyear(balance.getConsumeyear());
            bal.setCreditcard(balance.getCreditcard());
            bal.setCreditcardyear(balance.getCreditcardyear());
            bal.setCustomer(cusDao.getById(balance.getCustomerid()));
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
            bal.setDoc(dDao.getById(balance.getDocid()));
            bal.setUnfixedterm(balance.getUnfixedterm());
            balDao.save(bal);
          } else {
            balance.setDoc(dDao.getById(balance.getDocid()));
            balance.setCustomer(cusDao.getById(balance.getCustomerid()));
            balDao.save(balance);
          }
        }
      }

      List<Income> incomes = model.getIncomes();

      if (incomes != null && incomes.size() > 0) {
        for (Income income : incomes) {
          Income in = inDao.getById(income.getId());
          if (in != null) {
            in.setAdvice(income.getAdvice());
            in.setBalance(income.getBalance());
            in.setBonus(income.getBonus());
            in.setChildren(income.getChildren());
            in.setCustomer(cusDao.getById(income.getCustomerid()));
            in.setDeposit(income.getDeposit());
            in.setDividend(income.getDividend());
            in.setExpensetotal(income.getExpensetotal());
            in.setFamily(income.getFamily());
            in.setFixedexpense(income.getFixedexpense());
            in.setFixedincome(income.getFixedincome());
            in.setIncome(income.getIncome());

            in.setBonuschange(income.getBonuschange());
            in.setChildrenchange(income.getChildrenchange());
            in.setDepositchange(income.getDepositchange());
            in.setDividendchange(income.getDividendchange());
            in.setExpensetotalchange(income.getExpensetotalchange());
            in.setFamilychange(income.getFamilychange());
            in.setFixedexpensechange(income.getFixedexpensechange());
            in.setFixedincomechange(income.getFixedincomechange());
            in.setIncomechange(income.getIncomechange());
            in.setIncometotalchange(income.getIncometotalchange());
            in.setInsurancechange(income.getInsurancechange());
            in.setMaintenancechange(income.getMaintenancechange());
            in.setMateincomechange(income.getMaintenancechange());
            in.setMedicalchange(income.getMedicalchange());
            in.setRentchange(income.getRentchange());
            in.setRentexpensechange(income.getRentexpensechange());
            in.setSortdividendchange(income.getSortdividendchange());
            in.setTravelchange(income.getTravelchange());
            in.setUnfixedexpensechange(income.getUnfixedexpensechange());
            in.setUnfixedincomechange(income.getUnfixedincomechange());

            in.setIncometotal(income.getIncometotal());
            in.setInsurance(income.getInsurance());
            in.setMaintenance(income.getMaintenance());
            in.setMateincome(income.getMateincome());
            in.setMedical(income.getMedical());
            in.setRent(income.getRent());
            in.setType(income.getType());
            in.setRentexpense(income.getRentexpense());
            in.setSortdividend(income.getSortdividend());
            in.setTravel(income.getTravel());
            in.setUnfixedexpense(income.getUnfixedexpense());
            in.setUnfixedincome(income.getUnfixedincome());
            in.setUpdatetime(new Timestamp(System.currentTimeMillis()));
            in.setDoc(dDao.getById(income.getDocid()));
            inDao.save(in);
          } else {
            income.setDoc(dDao.getById(income.getDocid()));
            income.setCustomer(cusDao.getById(income.getCustomerid()));
            inDao.save(income);
          }
        }
      }

      List<Insurance> insurances = model.getInsurances();

      if (insurances != null && insurances.size() > 0) {
        for (Insurance insurance : insurances) {
          Insurance in = iDao.getById(insurance.getId());
          if (in != null) {
            in.setCustomer(cusDao.getById(insurance.getCustomerid()));
            in.setDoc(dDao.getById(insurance.getDocid()));
            in.setInsured(insurance.getInsured());
            in.setMemo(insurance.getMemo());
            in.setName(insurance.getName());
            in.setObject(insurance.getObject());
            in.setTotal(insurance.getTotal());
            in.setYear(insurance.getYear());
            in.setUpdatetime(insurance.getUpdatetime());
            iDao.save(in);
          } else {
            insurance.setCustomer(cusDao.getById(insurance.getCustomerid()));
            insurance.setDoc(dDao.getById(insurance.getDocid()));
            iDao.save(insurance);
          }
        }
      }

      List<Risk> risks = model.getRisks();
      if (risks != null && risks.size() > 0) {
        for (Risk risk : risks) {
          Risk r = rDao.getById(risk.getId());
          if (r != null) {
            r.setInvestgrouplevel(risk.getInvestgrouplevel());
            r.setPoint(risk.getPoint());
            r.setChosenlevel(risk.getChosenlevel());
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
            r.setDoc(dDao.getById(risk.getDocid()));
            r.setCustomer(cusDao.getById(risk.getCustomerid()));
            rDao.save(r);
          } else {
            risk.setDoc(dDao.getById(risk.getDocid()));
            risk.setCustomer(cusDao.getById(risk.getCustomerid()));
            rDao.save(risk);
          }
        }
      }

      List<Pension> pensions = model.getPensions();
      if (pensions != null && pensions.size() > 0) {
        for (Pension pension : pensions) {
          Pension p = pDao.getById(pension.getId());
          if (p != null) {
            p.setAge(pension.getAge());
            p.setCurrentexpense(pension.getCurrentexpense());
            p.setDoc(dDao.getById(pension.getDocid()));
            p.setCustomer(cusDao.getById(pension.getCustomerid()));
            p.setRate(pension.getRate());
            p.setRetireage(pension.getRetireage());
            p.setYear(pension.getYear());
            p.setTotal(pension.getTotal());
            p.setRetireexpense(pension.getRetireexpense());
            p.setUpdatetime(pension.getUpdatetime());
            pDao.save(p);
          } else {
            pension.setCustomer(cusDao.getById(pension.getCustomerid()));
            pension.setDoc(dDao.getById(pension.getDocid()));
            pDao.save(pension);
          }
        }
      }

      List<Education> educations = model.getEducations();
      if (educations != null && educations.size() > 0) {
        for (Education education : educations) {
          Education e = eDao.getById(education.getId());
          if (e != null) {
            e.setCustomer(cusDao.getById(education.getCustomerid()));
            e.setDoc(dDao.getById(education.getDocid()));
            e.setCourse(education.getCourse());
            e.setTotal(education.getTotal());
            e.setGeneratetime(education.getGeneratetime());
            e.setLivecost(education.getLivecost());
            e.setYear(education.getYear());
            // 11.21
            e.setCountry(education.getCountry());
            e.setChild(education.getChild());

            // 11.25
            e.setGetinage(education.getGetinage());
            e.setTuitionrate(education.getTuitionrate());
            e.setGetintuition(education.getGetintuition());
            e.setTotaltuition(education.getTotaltuition());
            e.setGetinlivecost(education.getGetinlivecost());
            e.setTotallivecost(education.getTotallivecost());
            e.setAge(education.getAge());

            e.setUpdatetime(education.getUpdatetime());
            e.setTuition(education.getTuition());
            e.setRate(education.getRate());
            eDao.save(e);
          } else {
            education.setCustomer(cusDao.getById(education.getCustomerid()));
            education.setDoc(dDao.getById(education.getDocid()));
            eDao.save(education);
          }
        }
      }

      List<Investment> investments = model.getInvestments();
      if (investments != null && investments.size() > 0) {
        for (Investment investment : investments) {
          Investment in = imDao.getById(investment.getId());
          if (in != null) {
            in.setCustomer(cusDao.getById(investment.getCustomerid()));
            in.setDoc(dDao.getById(investment.getDocid()));
            in.setGeneratetime(investment.getGeneratetime());
            in.setMonth(investment.getMonth());
            in.setRate(investment.getRate());
            in.setTotalinvest(investment.getTotalinvest());
            in.setTotalleft(investment.getTotalleft());
            in.setYear(investment.getYear());
            in.setUpdatetime(investment.getUpdatetime());
            imDao.save(in);
          } else {
            investment.setCustomer(cusDao.getById(investment.getCustomerid()));
            investment.setDoc(dDao.getById(investment.getDocid()));
            imDao.save(investment);
          }
        }
      }

      List<AdviceInvest> adviceinvests = model.getAdviceinvests();
      if (adviceinvests != null && adviceinvests.size() > 0) {
        for (AdviceInvest ai : adviceinvests) {
          AdviceInvest adi = aiDao.getById(ai.getId());
          if (adi != null) {
            adi.setCustomer(cusDao.getById(ai.getCustomerid()));
            adi.setDoc(dDao.getById(ai.getDocid()));
            adi.setAmount(ai.getAmount());
            adi.setBrand(ai.getBrand());
            adi.setGeneratetime(ai.getGeneratetime());
            adi.setIncome(ai.getIncome());
            adi.setMemo(ai.getMemo());
            adi.setPercentage(ai.getPercentage());
            adi.setYear(ai.getYear());
            aiDao.save(adi);
          } else {
            ai.setCustomer(cusDao.getById(ai.getCustomerid()));
            ai.setDoc(dDao.getById(ai.getDocid()));
            aiDao.save(ai);
          }
        }
      }

      List<AdjustAsset> assets = model.getAdjustassets();
      if (assets != null && assets.size() > 0) {
        for (AdjustAsset asset : assets) {
          AdjustAsset aa = aaDao.getById(asset.getId());
          if (aa != null) {
            aa.setCustomer(cusDao.getById(asset.getCustomerid()));
            aa.setDoc(dDao.getById(asset.getDocid()));
            aa.setAdjust(asset.getAdjust());
            aa.setAdviceconfig(asset.getAdviceconfig());
            aa.setFunds(asset.getFunds());
            aa.setGeneratetime(asset.getGeneratetime());
            aa.setMemo(asset.getMemo());
            aa.setName(asset.getName());
            aa.setState(asset.getState());
            aa.setUpdatetime(asset.getUpdatetime());
            aa.setType(asset.getType());
            aaDao.save(aa);
          } else {
            asset.setCustomer(cusDao.getById(asset.getCustomerid()));
            asset.setDoc(dDao.getById(asset.getDocid()));
            aaDao.save(asset);
          }
        }
      }

      result.put("msg", "保存成功");
      result.put("status", 0);

    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
    }
    return result.toString();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/syncjob")
  public String syncjob() throws JSONException {
    JSONObject result = new JSONObject();

    try {
      List<JobConfig> jcs = new ArrayList<JobConfig>();
      JSONArray jArray = new JSONArray();
      jcs = jcDao.listAll();

      if (jcs != null && jcs.size() > 0) {

        for (JobConfig jc : jcs) {
          JSONObject o = new JSONObject();
          o.put("id", jc.getId());
          o.put("name", jc.getName());
          jArray.put(o);
        }
        result.put("status", 0);
        result.put("msg", "更新成功");
      } else {
        result.put("status", 1);
        result.put("msg", "暂时没有相关数据");
      }

      result.put("items", jArray);

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      result.put("item", new JSONObject());
    }

    return result.toString();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/qa")
  public String syncqa() throws JSONException {
    JSONObject result = new JSONObject();

    try {
      List<Qa> qas = new ArrayList<Qa>();
      JSONArray qArray = new JSONArray();
      qas = qDao.listAll();
      JSONObject servant = new JSONObject();

      if (qas != null && qas.size() > 0) {
        servant.put("servanttel", qas.get(0).getServanttel());
        servant.put("servantemail", qas.get(0).getServantemail());
        for (Qa q : qas) {
          JSONObject o = new JSONObject();
          o.put("question", q.getQuestion());
          o.put("answer", q.getAnswer());
          o.put("generatetime", String.valueOf(q.getGeneratetime()));
          qArray.put(o);
        }
        result.put("status", 0);
        result.put("msg", "更新成功");
      } else {
        result.put("status", 1);
        result.put("msg", "暂时没有相关数据");
      }

      result.put("items", qArray);
      result.put("item", servant);

    } catch (Exception e) {
      result.put("status", 1);
      result.put("msg", "发生错误");
      result.put("items", new JSONArray());
      result.put("item", new JSONObject());
    }

    return result.toString();
  }

  /***
   * used for updating cashbackup
   * ,Protectproduct,Investgoods,Housing,Balance,Cashplanning,Riskstate,Investplanning and
   * Educationplanning,Riskmanage
   * 
   * @param doc
   * @return
   * @throws JSONException
   */
  @POST
  @Consumes
  @Path("/updatedoc")
  public String updatedoc(Doc doc) throws JSONException {
    JSONObject result = new JSONObject();

    try {
      Doc d = dDao.getById(doc.getId());

      if (doc.getFinancetarget() != null) {
        d.setFinancetarget(doc.getFinancetarget());
      }

      if (doc.getCashbackup() != null) {
        d.setCashbackup(doc.getCashbackup());
      }

      if (doc.getProtectproduct() != null) {
        d.setProtectproduct(doc.getProtectproduct());
      }

      if (doc.getInvestgoods() != null) {
        d.setInvestgoods(doc.getInvestgoods());
      }

      if (doc.getHousing() != null) {
        d.setHousing(doc.getHousing());
      }

      if (doc.getBalance() != null) {
        d.setBalance(doc.getBalance());
      }

      if (doc.getCashplanning() != null) {
        d.setCashplanning(doc.getCashplanning());
      }

      if (doc.getRiskmanage() != null) {
        d.setRiskmanage(doc.getRiskmanage());
      }

      if (doc.getRiskstate() != null) {
        d.setRiskstate(doc.getRiskstate());

      }

      if (doc.getInvestplanning() != null) {
        d.setInvestplanning(doc.getInvestplanning());
      }

      if (doc.getEducationplanning() != null) {
        d.setEducationplanning(doc.getEducationplanning());
      }
      d.setUpdatetime(new Timestamp(System.currentTimeMillis()));
      dDao.save(d);

      result.put("msg", "修改成功");
      result.put("status", 0);
    } catch (Exception e) {
      result.put("msg", "发生错误");
      result.put("status", 1);
    }

    return result.toString();
  }

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/uploadsdata")
  public String uploaddata(@Context HttpServletRequest request, @Context ServletContext servletContext) throws IOException, JSONException {
    JSONObject result = new JSONObject();
    try {

      // String picpath = request.getSession().getServletContext().getRealPath("/files");
      UploadUtil upload = new UploadUtil();
      upload.setMap(request);
      Map<String, FileItem> files = upload.getFiles();

      String content = files.get("myfile2").getString("UTF-8");

      String[] sqls = content.split(";");

      Map<String, String> sqlMap = new HashMap<String, String>();

      for (String sql : sqls) {
        if (sql != null && !("").equals(sql.trim())) {

          sqlMap.put(sql, dDao.executeSql(sql) + "");
        }
      }
      result.put("sqlmap", sqlMap.toString());
      result.put("status", 0);
      result.put("msg", "上传成功");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", 1);
      result.put("msg", "发生错误");
    }
    return result.toString();
  }

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/uploadpdf/{docid}")
  public String uploadpdf(@Context HttpServletRequest request, @PathParam("docid") String docid, @Context ServletContext servletContext)
      throws IOException, JSONException {
    JSONObject result = new JSONObject();

    String pdfpath = servletContext.getInitParameter("pdfpath");// save picture directory

    try {

      Doc doc = dDao.getById(docid);
      if (doc == null) {
        result.put("msg", "文档信息不存在");
        result.put("status", 1);
        return result.toString();
      }

      // String originalPath = doc.getPath();
      // if (originalPath != null && !"".equals(originalPath)) {
      // File oFile = new File(originalPath);
      // if (oFile.exists()) {
      // oFile.delete();
      // }
      // }
      // String filepath =
      // request.getSession().getServletContext().getRealPath("/sc/resources/scTemplate/schedulingReport.xls");
      UploadUtil upload = new UploadUtil();
      upload.setMap(request);
      Map<String, FileItem> files = upload.getFiles();

      for (Map.Entry<String, FileItem> key : files.entrySet()) {

        String fileName = key.getValue().getName();// 文件名
        if (!upload.isPdf(fileName)) {
          result.put("status", 1);
          result.put("msg", "文件格式不正确");
          return result.toString();
        }

        File file = new File(pdfpath);
        File parent = file.getParentFile();
        System.out.println("filepath:==" + file.getAbsolutePath());
        System.out.println("parentpath==" + parent.getAbsolutePath());
        if (parent != null) {
          if (parent.isDirectory() && !parent.exists()) {
            parent.mkdirs();
          }
        } else {
          file.mkdir();
        }

        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);// 后缀
        String newName = CoreUtil.randomID() + "." + suffix;// 随机文件名称

        String actualPath = pdfpath + System.getProperty("file.separator") + newName;

        file = new File(actualPath);

        file.setWritable(true, false);
        if (!file.exists()) {
          file.createNewFile();
        }
        files.get(key.getKey()).write(file);

        doc.setDocname(fileName);
        doc.setPath(newName);

        DocRecord dr = new DocRecord();
        dr.setPath(actualPath);
        dr.setGeneratetime(doc.getUpdatetime());
        dr.setCustomer(doc.getCustomer());
        dr.setConsultant(doc.getConsultant());
        dr.setDoc(doc);
        dr.setDocname(fileName);
        dr.setType(0);
        drDao.save(dr);

        dDao.save(doc);

      }
      result.put("status", 0);
      result.put("msg", "上传成功");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", 1);
      result.put("msg", "发生错误");
    }
    return result.toString();
  }

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/uploadphoto/{customerid}")
  public String uploadphoto(@Context HttpServletRequest request, @PathParam("customerid") String customerid,
      @Context ServletContext servletContext) throws IOException, JSONException {
    JSONObject result = new JSONObject();

    String picpath = servletContext.getInitParameter("customerphoto");// save picture directory

    try {

      // String filepath =
      // request.getSession().getServletContext().getRealPath("/sc/resources/scTemplate/schedulingReport.xls");
      UploadUtil upload = new UploadUtil();
      upload.setMap(request);
      Map<String, FileItem> files = upload.getFiles();

      for (Map.Entry<String, FileItem> key : files.entrySet()) {

        String fileName = key.getValue().getName();// 文件名
        if (!upload.isPicRight(fileName)) {
          result.put("status", 1);
          result.put("msg", "文件格式不正确");
          return result.toString();
        }
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);// 后缀
        String newName = CoreUtil.randomID() + "." + suffix;// 随机文件名称

        String actualPath = picpath + System.getProperty("file.separator") + newName;

        File file = new File(picpath);

        Customer cus = cusDao.getById(customerid);

        if (cus == null) {
          result.put("status", 1);
          result.put("msg", "该客户不存在");
          return result.toString();
        }
        // String originalPath = cus.getPhoto();
        // if (originalPath != null && !"".equals(originalPath)) {
        // File oFile = new File(originalPath);
        // if (oFile.exists()) {
        // oFile.delete();
        // }
        // }

        File parent = file.getParentFile();
        System.out.println("filepath:==" + file.getAbsolutePath());
        System.out.println("parentpath==" + parent.getAbsolutePath());
        if (parent != null) {
          if (parent.isDirectory() && !parent.exists()) {
            parent.mkdirs();
          }
        } else {
          file.mkdir();
        }

        file = new File(actualPath);
        System.out.println("can write===" + file.canWrite());
        file.setWritable(true, false);
        if (!file.exists()) {
          file.createNewFile();
        }
        files.get(key.getKey()).write(file);

        cus.setPhoto(actualPath);
        cus.setUpdatetime(new Timestamp(System.currentTimeMillis()));

        DocRecord dr = new DocRecord();
        dr.setPath(actualPath);
        dr.setGeneratetime(cus.getUpdatetime());
        dr.setCustomer(cus);
        dr.setConsultant(cus.getConsultant());
        dr.setDocname(fileName);
        dr.setType(3);
        drDao.save(dr);

        cusDao.save(cus);
      }
      result.put("status", 0);
      result.put("msg", "上传成功");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", 1);
      result.put("msg", "发生错误");
    }
    return result.toString();
  }

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/uploadsignature/{customerid}")
  public String uploadsignature(@Context HttpServletRequest request, @PathParam("customerid") String customerid,
      @Context ServletContext servletContext) throws IOException, JSONException {
    JSONObject result = new JSONObject();

    String picpath = servletContext.getInitParameter("customersignature");// save picture
    // directory

    try {

      // String picpath = request.getSession().getServletContext().getRealPath("/files");
      UploadUtil upload = new UploadUtil();
      upload.setMap(request);
      Map<String, FileItem> files = upload.getFiles();

      for (Map.Entry<String, FileItem> key : files.entrySet()) {

        String fileName = key.getValue().getName();// 文件名
        if (!upload.isPdf(fileName)) {
          result.put("status", 1);
          result.put("msg", "文件格式不正确");
          return result.toString();
        }
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);// 后缀
        String newName = CoreUtil.randomID() + "." + suffix;// 随机文件名称

        String actualPath = picpath + System.getProperty("file.separator") + newName;

        File file = new File(picpath);

        File parent = file.getParentFile();
        System.out.println("filepath:==" + file.getAbsolutePath());
        System.out.println("parentpath==" + parent.getAbsolutePath());
        if (parent != null) {
          if (parent.isDirectory() && !parent.exists()) {
            parent.mkdirs();
          }
        } else {
          file.mkdir();
        }

        Customer cus = cusDao.getById(customerid);

        if (cus == null) {
          result.put("status", 1);
          result.put("msg", "该客户不存在");
          return result.toString();
        }
        // String originalPath = cus.getSignature();
        // if (originalPath != null && !"".equals(originalPath)) {
        // File oFile = new File(originalPath);
        // if (oFile.exists()) {
        // oFile.delete();
        // }
        // }
        file = new File(actualPath);

        file.setWritable(true, false);
        if (!file.exists()) {
          file.createNewFile();
        }
        files.get(key.getKey()).write(file);

        // cus.setPhoto(actualPath);
        cus.setSignature(newName);
        cus.setUpdatetime(new Timestamp(System.currentTimeMillis()));

        DocRecord dr = new DocRecord();
        dr.setPath(actualPath);
        dr.setGeneratetime(cus.getUpdatetime());
        dr.setCustomer(cus);
        dr.setConsultant(cus.getConsultant());
        dr.setDocname(fileName);
        dr.setType(2);
        drDao.save(dr);

        cusDao.save(cus);
      }
      result.put("status", 0);
      result.put("msg", "上传成功");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", 1);
      result.put("msg", "发生错误");
    }
    return result.toString();
  }

  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/webdownload/{type}/{filename}")
  public String webdownload(@Context HttpServletRequest request, @PathParam("filename") String filename, @PathParam("type") String type,
      @Context ServletContext servletContext, @Context HttpServletResponse response) throws IOException, JSONException {
    JSONObject result = new JSONObject();

    try {

      String pdfpath = servletContext.getInitParameter(type);

      File file = new File(pdfpath + System.getProperty("file.separator") + filename);

      if (!file.exists()) {
        result.put("status", 1);
        result.put("msg", "规划书不存在");
        return result.toString();
      } else {
        // 取得文件名。
        // 取得文件的后缀名。
        // String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
        // 以流的形式下载文件。
        InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        // 设置response的Header

        response.setContentType("application/pdf");

        response.setCharacterEncoding("UTF-8");
        String name = "attachment;filename=" + new String(file.getName().getBytes("gb2312"), "iso8859-1") + ".pdf";
        response.setHeader("Content-disposition", null);
        OutputStream ouputStream = response.getOutputStream();
        ouputStream.write(buffer);
        ouputStream.flush();

        result.put("output", ouputStream);
        result.put("status", 0);
        result.put("msg", "下载成功");
      }

    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", 1);
      result.put("msg", "发生错误");
    }
    return result.toString();
  }

}
