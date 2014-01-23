package com.isoftstone.smart.core.service;

import java.net.URI;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;
import javax.persistence.Transient;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.server.mvc.Viewable;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.entity.AccountStatus;
import com.isoftstone.smart.core.entity.ErrorResult;

@Path("/")
public class RootService extends BaseService<Object> {

  public static final String ADMIN = "admin";

  public static final String LOGIN = "login";

  public static final String LOGOUT = "logout";

  public static final String REGISTER = "register";

  public static final String REGISTRATION = "registration";

  public static final String VERIFICATION = "verification";

  public static void sendMail(String subject, String text, String... emails) {
    final String userName = "txfandev@gmail.com";

    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.socketFactory.port", "465");
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

    properties.put("mail.smtp.auth", true);
    properties.put("mail.smtp.port", 465);

    Session session = Session.getDefaultInstance(properties, new Authenticator() {
      @Override
      protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(userName, "Pa88w0rd");
      }
    });

    MimeMessage mimeMessage = new MimeMessage(session);
    try {
      InternetAddress address = new InternetAddress("txfandev@gmail.com");
      mimeMessage.setFrom(address);
      for (String email : emails) {
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
      }
      mimeMessage.setSubject(subject);
      mimeMessage.setContent(text, "text/html;charset=utf-8");
      // mimeMessage.setText(text);
      Transport.send(mimeMessage);
    } catch (Exception e) {
    }

  }

  @Inject
  protected AccountService accountService;

  @Context
  @Transient
  protected UriInfo uriInfo;

  @GET
  @Path(ADMIN + "/{page}")
  @Produces({MediaType.TEXT_HTML})
  public Response getAdminView(@PathParam("page") String page) {
    // SecurityUtils.getSubject().checkPermission(n);
    Subject subject = SecurityUtils.getSubject();
    if (subject.isAuthenticated()) {
      Account account = (Account) subject.getPrincipal();
      return Response.ok().entity(new Viewable(getAbsolutePath(ADMIN, page), account)).build();
    } else {
      return redirect(LOGIN, ADMIN + "/" + page);
    }
  }

  @Override
  public Class<Object> getEntityType() {
    return Object.class;
  }

  @GET
  @Produces({MediaType.TEXT_HTML})
  public Viewable getIndexView() {
    Subject subject = SecurityUtils.getSubject();
    if (subject.isAuthenticated()) {
      Account account = (Account) subject.getPrincipal();
      return new Viewable(getAbsolutePath(INDEX), account);
    } else {
      return new Viewable(getAbsolutePath(LOGIN));
    }
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Path(LOGIN)
  public Response login(@FormParam("username") String username, @FormParam("password") String password, @FormParam("forward") String forward) {
    Subject subject = SecurityUtils.getSubject();
    AuthenticationToken token = new UsernamePasswordToken(username, password);
    subject.login(token);
    return redirect(forward);
  }

  @GET
  @Path(LOGIN)
  @Produces({MediaType.TEXT_HTML})
  public Response loginCheck(@QueryParam("forward") String forward) {
    Subject subject = SecurityUtils.getSubject();
    if (subject.isAuthenticated()) {
      Account user = (Account) subject.getPrincipal();
      return redirect(forward);
    } else {
      return Response.ok(new Viewable(getAbsolutePath(LOGIN))).build();
    }
  }

  @GET
  @Path(LOGOUT)
  public Response logout(@QueryParam("forward") String forward) {
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    // return redirect(forward);
    return Response.status(Status.OK).entity("asdf").build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Path(REGISTER)
  @Transactional
  public Viewable register(@FormParam("loginId") String loginId) {
    Account account = null;
    try {
      account = accountService.getByLoginId(loginId);
    } catch (NoResultException e) {
    }

    if (account != null) {
      return new Viewable(getAbsolutePath(REGISTER), ErrorResult.from(loginId + "已注册！"));
    }
    account = new Account();
    account.setStatus(AccountStatus.NEW);
    account.setLoginId(loginId);
    accountService.save(account);
    sendMail("激活邮件", "欢迎您注册，点击链接完成注册<a href=\"http://localhost:8080/smart/registration?loginId=" + loginId + "\">点击链接激活账号</a>",
        "limingwei234@163.com");
    return new Viewable(getAbsolutePath(VERIFICATION), account);
  }

  @GET
  @Path(REGISTER)
  @Produces({MediaType.TEXT_HTML})
  public Response registerPage() {
    return Response.ok(new Viewable(getAbsolutePath(REGISTER))).build();
  }

  private Response redirect(String newLocation) {
    return redirect(newLocation, null);
  }

  private Response redirect(String newLocation, String forward) {
    UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
    if (newLocation != null) {
      uriBuilder.path(newLocation);
    }
    if (forward != null) {
      uriBuilder.queryParam("forward", forward);
    }
    URI uri = uriBuilder.build();
    return Response.status(Status.FOUND).location(uri).build();
  }
}
