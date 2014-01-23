package com.isoftstone.smart.kingstone.business;

import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.service.BaseEntityService;

public class AccountService extends BaseEntityService<Account> {

  public Account getAdminLoginId(String loginId) {
    Account result = null;
    try {
      result =
          em().createQuery("from " + Account.class.getName() + " u where u.level=0 and u.loginId = :loginId ", Account.class).setParameter(
              "loginId", loginId).getSingleResult();
    } catch (Exception e) {
      result = null;
    }

    return result;
  }

  public Account getByLoginId(String loginId) {
    Account result = null;
    try {
      result =
          em().createQuery("from " + Account.class.getName() + " u where  u.loginId = :loginId ", Account.class).setParameter("loginId",
              loginId).getSingleResult();
    } catch (Exception e) {
      result = null;
    }

    return result;
  }

  @Override
  public Class<Account> getEntityType() {
    return Account.class;
  }

  public Account webLogin(String loginId) {
    Account result = null;
    try {
      result =
          em().createQuery("from " + Account.class.getName() + " u where u.loginId = :loginId ", Account.class).setParameter("loginId",
              loginId).getSingleResult();

    } catch (Exception e) {
      result = null;
    }

    return result;
  }
}
