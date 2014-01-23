package com.isoftstone.smart.core.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.xml.bind.JAXBException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;
import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.entity.Place;
import com.isoftstone.smart.core.entity.PlaceAction;
import com.isoftstone.smart.core.entity.PlaceMember;
import com.isoftstone.smart.core.entity.PlaceRole;
import com.isoftstone.smart.core.entity.Tenant;

public class MemberServiceTest extends BaseTest {

  @Inject
  AccountService accountService;

  @Inject
  MemberService memberService;

  @Inject
  SecurityManager securityManager;

  @Inject
  TenantService tenantService;

  @Inject
  PlaceService placeService;

  @Before
  public void initShiro() {
    SecurityUtils.setSecurityManager(securityManager);
  }

  @Test
  public void testPlaceMember() throws JAXBException {
    String tenantId = "iss";
    Tenant tenant = null;
    tenant = tenantService.getById(tenantId);
    if (tenant == null) {
      tenant = new Tenant();
      tenant.setId(tenantId);
      tenant.setFullname("Incito");
      tenant.setShortname(tenantId);
      tenantService.save(tenant);
    }

    String managerId = "txfan";
    String pwd = "123";
    Account manager = createAccount(tenant, managerId, pwd, "Manager A");

    String contributorId = "mwlif";
    Account contributor = createAccount(tenant, contributorId, pwd, "Contributor X");

    Place root = placeService.getRootPlace();
    String path = "iss";
    Place place = placeService.getById(path);
    if (place == null) {
      place = new Place();
      place.setPlace(root);
      place.setId(path);
      place = placeService.save(place);

      addMember(manager, place, PlaceRole.MANAGER);

      addMember(contributor, place, PlaceRole.CONTRIBUTOR);
    }

    login(managerId, pwd);
    assertTrue(place.isPermitted(PlaceAction.ADD));
    assertTrue(place.isPermitted(PlaceAction.GRANT));
    logout();

    login(contributorId, pwd);
    assertTrue(place.isPermitted(PlaceAction.ADD));
    assertFalse(place.isPermitted(PlaceAction.GRANT));
    logout();

    assertFalse(place.isPermitted(PlaceAction.ADD));
    assertFalse(place.isPermitted(PlaceAction.GRANT));
  }

  private void addMember(Account account, Place place, PlaceRole role) {
    PlaceMember member = new PlaceMember();
    member.setRole(role);
    member.setContent(place);
    member.setPrincipal(account);
    memberService.save(member);
  }

  private Account createAccount(Tenant tenant, String accountId, String pwd, String fullname) {
    Account manager = accountService.getById(accountId);
    if (manager == null) {
      manager = new Account();
      manager.setId(accountId);
      manager.setLoginId(accountId);
      manager.setFullname(fullname);
      manager.setTenant(tenant);
      accountService.createAccount(manager);
      accountService.updatePassword(accountId, pwd);
    }
    return manager;
  }

  private void login(String accountId, String pwd) {
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(accountId, pwd);
    subject.login(token);
  }

  private void logout() {
    SecurityUtils.getSubject().logout();
  }

}
