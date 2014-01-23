package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Pension;

public class PensionService extends BaseEntityService<Pension> {

  @Override
  public Class<Pension> getEntityType() {
    // TODO Auto-generated method stub
    return Pension.class;
  }

  @SuppressWarnings("unchecked")
  public List<Pension> getList(String customerid) {
    List<Pension> ps = null;
    try {
      Query query = em().createNativeQuery("select * from pension where customerid=?1", Pension.class);
      query.setParameter(1, customerid);
      ps = query.getResultList();
    } catch (Exception e) {
      ps = null;
    }
    return ps;
  }

  @SuppressWarnings("unchecked")
  public List<Pension> getListbyconsultant(String consultantid) {
    List<Pension> ps = null;
    try {
      Query query =
          em().createNativeQuery("select * from pension where customerid in (select id from customer where consultantid=?1)", Pension.class);
      query.setParameter(1, consultantid);
      ps = query.getResultList();
    } catch (Exception e) {
      ps = null;
    }
    return ps;
  }

  @SuppressWarnings("unchecked")
  public List<Pension> getPensionByDocId(String docid) {
    List<Pension> ps = null;
    try {
      Query query = em().createNativeQuery("select * from pension where docid=?1", Pension.class);
      query.setParameter(1, docid);
      ps = query.getResultList();
    } catch (Exception e) {
      ps = null;
    }
    return ps;
  }

}
