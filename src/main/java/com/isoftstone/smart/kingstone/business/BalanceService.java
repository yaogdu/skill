package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Balance;

public class BalanceService extends BaseEntityService<Balance> {

  @Override
  public Class<Balance> getEntityType() {
    // TODO Auto-generated method stub
    return Balance.class;
  }

  public Balance getLatestInfo(String docid) {
    Balance bal = null;
    try {
      Query query = em().createNativeQuery("select * from balance where docid=?1", Balance.class);
      query.setParameter(1, docid);
      bal = (Balance) query.getSingleResult();
    } catch (Exception e) {
      bal = null;
    }
    return bal;
  }

  @SuppressWarnings("unchecked")
  public List<Balance> getList(String customerid) {
    List<Balance> bals = null;
    try {
      Query query = em().createNativeQuery("select * from balance where customerid=?1", Balance.class);
      query.setParameter(1, customerid);
      bals = query.getResultList();
    } catch (Exception e) {
      bals = null;
    }
    return bals;
  }

  @SuppressWarnings("unchecked")
  public List<Balance> getListbyConsultant(String consultantid) {
    List<Balance> bals = null;
    try {
      Query query =
          em().createNativeQuery("select * from balance where customerid in (select id from customer where consultantid =?1)",
              Balance.class);
      query.setParameter(1, consultantid);
      bals = query.getResultList();
    } catch (Exception e) {
      bals = null;
    }
    return bals;
  }

}
