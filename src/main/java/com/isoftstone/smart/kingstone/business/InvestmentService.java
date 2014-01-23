package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Investment;

public class InvestmentService extends BaseEntityService<Investment> {

  @Override
  public Class<Investment> getEntityType() {
    // TODO Auto-generated method stub
    return Investment.class;
  }

  @SuppressWarnings("unchecked")
  public List<Investment> getList(String customerid) {
    List<Investment> ins = null;
    try {
      Query query = em().createNativeQuery("select * from investment where customerid =?1", Investment.class);
      query.setParameter(1, customerid);
      ins = query.getResultList();

    } catch (Exception e) {
      ins = null;
    }
    return ins;
  }

  @SuppressWarnings("unchecked")
  public List<Investment> getListbyconsultant(String consultantid) {
    List<Investment> ins = null;
    try {
      Query query =
          em().createNativeQuery("select * from investment where customerid in (select id from customer where consultantid=?1)",
              Investment.class);
      query.setParameter(1, consultantid);
      ins = query.getResultList();

    } catch (Exception e) {
      ins = null;
    }
    return ins;
  }
}
