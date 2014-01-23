package com.isoftstone.smart.kingstone.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.inject.Inject;
import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.service.BaseService;
import com.isoftstone.smart.core.util.CoreUtil;
import com.isoftstone.smart.kingstone.business.AccountService;
import com.isoftstone.smart.kingstone.business.ConsultantService;
import com.isoftstone.smart.kingstone.business.CustomerService;
import com.isoftstone.smart.kingstone.business.DocService;
import com.isoftstone.smart.kingstone.business.RiskService;
import com.isoftstone.smart.kingstone.entity.Consultant;
import com.isoftstone.smart.kingstone.entity.Customer;
import com.isoftstone.smart.kingstone.entity.Doc;
import com.isoftstone.smart.kingstone.entity.Risk;
import com.isoftstone.smart.kingstone.utils.UploadUtil;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource extends BaseService<Customer> {

  private final Logger logger = Logger.getLogger(CustomerResource.class.getName());

  @Inject
  CustomerService cusDao;

  @Inject
  AccountService aDao;

  @Inject
  ConsultantService conDao;

  @Inject
  DocService dDao;

  @Inject
  RiskService rDao;

  /***
   * new customer
   * 
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/collectInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public String collectInfo(Customer customer) throws JSONException {
    logger.info("collectinfo begins");
    JSONObject result = new JSONObject();
    try {
      // customer.setUpdatetime(new Timestamp(System.currentTimeMillis()));
      // customer.setGeneratetime(new Timestamp(System.currentTimeMillis()));

      cusDao.save(customer);

      // Doc doc = new Doc();
      // doc.setConsultant(customer.getConsultant());
      // doc.setCustomer(customer);
      // doc.setExpired(0);
      // doc.setGeneratetime(new Timestamp(System.currentTimeMillis()));
      // doc.setUpdatetime(new Timestamp(System.currentTimeMillis()));
      // dDao.save(doc);

      result.put("msg", "保存成功!");
      result.put("status", 0);
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
    }
    logger.info("collectinfo ends");
    return result.toString();
  }

  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/downloadphoto")
  public String downloadphoto(@Context HttpServletRequest request, @QueryParam("customerid") String customerid,
      @Context ServletContext servletContext, @Context HttpServletResponse response) throws IOException, JSONException {
    JSONObject result = new JSONObject();

    try {
      Customer customer = cusDao.getById(customerid);
      if (customer == null) {
        result.put("status", 1);
        result.put("msg", "文档信息错误");
        return result.toString();
      } else {

        File file = new File(customer.getPhoto());
        // 取得文件名。
        // FIXME change the way of getting filename
        String filename =
            customer.getPhoto().substring(customer.getPhoto().lastIndexOf(System.getProperty("separator")),
                customer.getPhoto().length() - 1);
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
        // FIXME about ".jpg"
        String name = "attachment;filename=" + new String(filename.getBytes("gb2312"), "iso8859-1") + ".jpg";
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

  @Override
  public Class<Customer> getEntityType() {
    return Customer.class;
  }

  @GET
  @Path("/getInfo")
  public String getInfo(@QueryParam("customerid") String customerid, @QueryParam("accountid") String accountid,
      @Context HttpServletResponse response) throws JSONException {
    logger.info("customerresource getinfo begins.");
    JSONObject result = new JSONObject();
    try {
      Account user = aDao.getById(accountid);
      if (user == null) {
        result.put("status", 1);
        result.put("msg", "该用户不存在");
        result.put("items", new JSONArray());
        return result.toString();
      }

      Customer customer = cusDao.getById(customerid);
      if (customer != null) {
        JSONObject cus = new JSONObject();

        Consultant con = customer.getConsultant();

        Doc doc = dDao.getDocByCustomerid(customerid);

        if (doc != null) {
          cus.put("pdfpath", doc.getPath());
          Risk risk = rDao.getInfo(doc.getId());
          if (risk != null) {
            cus.put("risk", risk.getSignature());
          } else {
            cus.put("risk", "");
          }
        } else {
          cus.put("pdfpath", "");

          Risk risk = rDao.getbyCustomerid(customer.getId());
          if (risk != null) {
            cus.put("risk", risk.getSignature());
          } else {
            cus.put("risk", "");
          }
        }

        cus.put("updatetime", String.valueOf(customer.getUpdatetime()));
        cus.put("gender", customer.getGender());
        cus.put("married", customer.getMarried());
        cus.put("address", customer.getAddress());
        cus.put("contact", customer.getContact());
        cus.put("contactphone", customer.getContactphone());
        cus.put("age", String.valueOf(customer.getAge()));
        cus.put("completetime", String.valueOf(customer.getCompletetime()));
        cus.put("industry", customer.getIndustry());
        cus.put("done", customer.getDone());
        cus.put("src", customer.getSrc());
        cus.put("location", customer.getLocation());
        cus.put("idcard", customer.getIdcard());
        cus.put("childrenage", customer.getChildrenage());
        cus.put("email", customer.getEmail());
        cus.put("familymember", customer.getFamilymember());
        cus.put("generatetime", String.valueOf(customer.getGeneratetime()));
        cus.put("id", customer.getId());
        cus.put("name", customer.getName());
        cus.put("mobile", customer.getMobile());
        cus.put("memo", customer.getMemo());
        cus.put("matename", customer.getMatename());
        cus.put("mateage", customer.getMateage());
        cus.put("job", customer.getJob());
        cus.put("signature", customer.getSignature());
        if (con != null) {
          cus.put("consultantid", con.getId());
          cus.put("consultantname", con.getName());
          cus.put("consultantno", con.getConsultantno());
        } else {
          cus.put("consultantid", "");
          cus.put("consultantname", "");
          cus.put("consultantno", "");
        }
        result.put("status", 0);
        result.put("msg", "查询成功");
        result.put("item", cus);
      } else {
        result.put("msg", "没有相关信息!");
        result.put("status", 1);
        result.put("item", new JSONObject());
      }
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
      result.put("item", new JSONObject());
    }

    logger.info("customerresource getinfo ends.");
    return result.toString();

  }

  /***
   * customer list for customer management
   * 
   * @param consultantid
   * @return
   * @throws JSONException
   */
  @GET
  @Path("/list")
  public String list(@QueryParam("consultantid") String consultantid) throws JSONException {
    logger.info("list begins");
    JSONObject result = new JSONObject();

    List<Customer> customers = cusDao.getByConsultantId(consultantid);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

    Calendar cal = Calendar.getInstance();

    try {

      JSONArray cusArray = new JSONArray();
      if (customers != null && customers.size() > 0) {
        for (Customer customer : customers) {
          JSONObject cus = new JSONObject();

          int age = 0;
          if (customer.getAge() != null) {
            age = cal.get(Calendar.YEAR) - Integer.parseInt(sdf.format(customer.getAge()));
          }
          int mateage = 0;
          if (customer.getMateage() != null) {
            mateage = cal.get(Calendar.YEAR) - Integer.parseInt(sdf.format(customer.getMateage()));
          }

          cus.put("updatetime", String.valueOf(customer.getUpdatetime()));
          cus.put("gender", customer.getGender());
          cus.put("married", customer.getMarried());
          cus.put("address", customer.getAddress());
          cus.put("age", age);
          cus.put("completetime", String.valueOf(customer.getCompletetime()));
          cus.put("industry", customer.getIndustry());
          cus.put("done", customer.getDone());
          cus.put("src", customer.getSrc());
          cus.put("location", customer.getLocation());
          cus.put("photo", customer.getPhoto());
          cus.put("idcard", customer.getIdcard());
          cus.put("contact", customer.getContact());
          cus.put("contactphone", customer.getContactphone());
          cus.put("childrenage", customer.getChildrenage());
          cus.put("email", customer.getEmail());
          cus.put("familymember", customer.getFamilymember());
          cus.put("generatetime", String.valueOf(customer.getGeneratetime()));
          cus.put("id", customer.getId());
          cus.put("name", customer.getName());
          cus.put("mobile", customer.getMobile());
          cus.put("memo", customer.getMemo());
          cus.put("matename", customer.getMatename());
          cus.put("mateage", mateage);
          cus.put("job", customer.getJob());

          cusArray.put(cus);
        }
        result.put("items", cusArray);
        result.put("msg", "查询成功!");
        result.put("status", 0);
      } else {
        result.put("msg", "暂时没有相关信息!");
        result.put("status", 1);
        result.put("items", new JSONArray());
      }
    } catch (Exception e) {
      result.put("msg", "发生错误!");
      result.put("status", 1);
      result.put("items", new JSONArray());
    }
    logger.info("list ends");
    return result.toString();

  }

  /***
   * modify customer
   * 
   * @return
   * @throws JSONException
   */
  @POST
  @Path("/modify")
  @Consumes(MediaType.APPLICATION_JSON)
  public String modify(Customer customer) throws JSONException {
    logger.info("modify begins");
    JSONObject result = new JSONObject();
    try {

      String cusId = customer.getId();
      Customer cus = cusDao.getById(cusId);
      if (cus != null) {
        cus.setAddress(customer.getAddress());
        cus.setAge(customer.getAge());
        cus.setChildrenage(customer.getChildrenage());
        cus.setEmail(customer.getEmail());
        cus.setFamilymember(customer.getFamilymember());
        cus.setGender(customer.getGender());
        cus.setJob(customer.getJob());
        cus.setMarried(customer.getMarried());
        cus.setMateage(customer.getMateage());
        cus.setMatename(customer.getMatename());
        cus.setPhoto(customer.getPhoto());
        cus.setId(customer.getIdcard());
        cus.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        cus.setName(customer.getName());
        cus.setMobile(customer.getMobile());
        cus.setMemo(customer.getMemo());
        cus.setContact(customer.getContact());
        cus.setContactphone(customer.getContactphone());
        cusDao.save(cus);

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
        if (!file.exists()) {
          file.mkdirs();
        }

        Customer cus = cusDao.getById(customerid);

        if (cus == null) {
          result.put("status", 1);
          result.put("msg", "该客户不存在");
          return result.toString();
        }

        String originalPath = cus.getPhoto();
        File oFile = new File(originalPath);
        if (oFile.exists()) {
          oFile.delete();
        }
        file = new File(actualPath);
        files.get(key.getKey()).write(file);

        cus.setPhoto(actualPath);
        cus.setUpdatetime(new Timestamp(System.currentTimeMillis()));
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

    String picpath = servletContext.getInitParameter("customersignature");// save picture directory

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
        if (!file.exists()) {
          file.mkdirs();
        }

        Customer cus = cusDao.getById(customerid);

        if (cus == null) {
          result.put("status", 1);
          result.put("msg", "该客户不存在");
          return result.toString();
        }
        file = new File(actualPath);
        files.get(key.getKey()).write(file);

        // cus.setPhoto(actualPath);
        cus.setSignature(actualPath);
        cus.setUpdatetime(new Timestamp(System.currentTimeMillis()));
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

}
