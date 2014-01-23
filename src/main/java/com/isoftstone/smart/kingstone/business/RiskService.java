package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Risk;

public class RiskService extends BaseEntityService<Risk> {

  @Override
  public Class<Risk> getEntityType() {
    // TODO Auto-generated method stub
    return Risk.class;
  }

  public Risk getInfo(String docid) {
    Risk risk = null;
    try {
      Query query = em().createNativeQuery("select * from risk where docid=?1", Risk.class);
      query.setParameter(1, docid);
      risk = (Risk) query.getSingleResult();

    } catch (Exception e) {
      risk = null;
    }
    return risk;
  }

  
  public Risk getbyCustomerid(String customerid) {
    Risk risk = null;
    try {
      Query query = em().createNativeQuery("select * from risk where customerid=?1", Risk.class);
      query.setParameter(1, customerid);
      risk = (Risk) query.getSingleResult();
      
    } catch (Exception e) {
      risk = null;
    }
    return risk;
  }

  @SuppressWarnings("unchecked")
  public List<Risk> getListbyconsultant(String consultantid) {
    List<Risk> risks = null;
    try {
      Query query =
          em().createNativeQuery("select * from risk where customerid in (select id from customer where consultantid =?1)", Risk.class);
      query.setParameter(1, consultantid);
      risks = query.getResultList();

    } catch (Exception e) {
      risks = null;
    }
    return risks;
  }
}
