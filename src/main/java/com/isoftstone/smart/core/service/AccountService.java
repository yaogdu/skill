package com.isoftstone.smart.core.service;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.glassfish.jersey.server.mvc.Viewable;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.entity.AccountStatus;
import com.isoftstone.smart.core.entity.Tenant;
import com.isoftstone.smart.core.security.CloudRealm;

@Path("accounts")
public class AccountService extends BaseEntityService<Account> {

  @Inject
  TenantService tenantService;

  @POST
  @Path("active")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Transactional
  public Response activeAccount(@FormParam("loginId") String loginId, @FormParam("fullname") String tenantFullname,
      @FormParam("shortname") String tenantShortname, @FormParam("location") String location, @FormParam("profession") String profession,
      @FormParam("username") String username, @FormParam("department") String department, @FormParam("position") String position,
      @FormParam("workphone") String workPhone, @FormParam("telphone") String telphone, @FormParam("password") String password,
      @FormParam("repassword") String rePassword) {

    Account account = null;

    try {
      account = getByLoginId(loginId);
    } catch (NoResultException e) {
    }

    // if (account != null) {
    // throw new WebApplicationException(Status.CONFLICT);
    // }
    String tenantId = loginId.substring(loginId.indexOf("@") + 1);
    Tenant tenant = null;
    tenant = tenantService.getById(tenantId);
    if (tenant == null) {
      tenant = new Tenant();
      tenant.setId(tenantId);
      tenant.setUsername(username);
      tenant.setDepartment(department);
      tenant.setLocation(location);
      tenant.setPosition(position);
      tenant.setPassword(password);
      tenant.setRePassword(rePassword);
      tenant.setWorkPhone(workPhone);
      tenant.setTelphone(telphone);
      tenant.setProfession(profession);
      tenant.setFullname(tenantFullname);
      tenant.setShortname(tenantShortname);
      tenantService.save(tenant);
    }
    // account = new Account();
    // account.setLoginId(loginId);
    if (account != null) {
      account.setFullname(username);
      account.setTenant(tenant);

      account.setStatus(AccountStatus.ACTIVE);// 修改帐户状态
      save(account);
      updatePassword(loginId, password);
    }
    @SuppressWarnings("unused")
    String path = getAbsolutePath("complete");
    // path为/acountActive/complete
    return Response.ok(new Viewable(getAbsolutePath("complete"))).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Account createAccount(Account newAccount) {
    save(newAccount);
    return newAccount;
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Transactional
  public Response createAccount(@FormParam("loginId") String loginId, @FormParam("fullname") String tenantFullname,
      @FormParam("shortname") String tenantShortname, @FormParam("location") String location, @FormParam("profession") String profession,
      @FormParam("username") String username, @FormParam("department") String department, @FormParam("position") String position,
      @FormParam("workphone") String workPhone, @FormParam("telphone") String telphone, @FormParam("password") String password,
      @FormParam("repassword") String rePassword) {
    // Account account = null;
    //
    // try {
    // account = getByLoginId(loginId);
    // } catch (NoResultException e) {
    // }
    //
    // // if (account != null) {
    // // throw new WebApplicationException(Status.CONFLICT);
    // // }
    // String tenantId = loginId.substring(loginId.indexOf("@") + 1);
    // Tenant tenant = null;
    // tenant = tenantService.getById(tenantId);
    // if (tenant == null) {
    // tenant = new Tenant();
    // tenant.setId(tenantId);
    // tenant.setUsername(username);
    // tenant.setDepartment(department);
    // tenant.setLocation(location);
    // tenant.setPosition(position);
    // tenant.setPassword(password);
    // tenant.setRePassword(rePassword);
    // tenant.setWorkPhone(workPhone);
    // tenant.setTelphone(telphone);
    // tenant.setProfession(profession);
    // tenant.setFullname(tenantFullname);
    // tenant.setShortname(tenantShortname);
    // tenantService.save(tenant);
    // }
    // account = new Account();
    // account.setLoginId(loginId);
    // account.setFullname(username);
    // account.setTenant(tenant);
    // account.setStatus(com.isoftstone.smart.core.entity.Account.Status.ACTIVE);// 修改帐户状态
    // save(account);
    // updatePassword(loginId, password);
    return Response.ok(new Viewable(getAbsolutePath("complete"))).build();
  }

  public Account getByLoginId(String loginId) {
    System.out.println("getByLoginId:" + loginId);
    Account result =
        em().createQuery("from " + Account.class.getName() + " u where u.loginId = :loginId", Account.class).setParameter("loginId",
            loginId).getSingleResult();

    return result;
  }

  @Override
  public Class<Account> getEntityType() {
    return Account.class;
  }

  @Path("{id}")
  public Account getUser(@PathParam("id") String id) {
    return getById(id);
  }

  @Transactional
  public void updatePassword(final String username, final String newPassword) {
    Account user = getByLoginId(username);
    if (user == null) {
      throw new UnknownAccountException("找不到用户名: " + username);
    }
    String hashedPwd = new SimpleHash(CloudRealm.ALGORITHM_NAME, newPassword).toHex();
    user.setPasswordHash(hashedPwd);
    em().persist(user);
  }
}
