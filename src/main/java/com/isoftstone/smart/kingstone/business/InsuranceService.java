package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Insurance;

public class InsuranceService extends BaseEntityService<Insurance> {

  @Override
  public Class<Insurance> getEntityType() {
    // TODO Auto-generated method stub
    return Insurance.class;
  }

  @SuppressWarnings("unchecked")
  public List<Insurance> getLatestInfoByDocId(String docid, int type) {
    List<Insurance> ins = null;
    try {
      Query query = em().createNativeQuery("select * from insurance where type=?1 and docid=?2", Insurance.class);
      query.setParameter(1, type);
      query.setParameter(2, docid);
      ins = query.getResultList();
    } catch (Exception e) {
      ins = null;
    }
    return ins;
  }

  @SuppressWarnings("unchecked")
  public List<Insurance> getList(String customerid) {
    List<Insurance> ins = null;
    try {
      Query query = em().createNativeQuery("select * from insurance where   customerid=?1", Insurance.class);
      query.setParameter(1, customerid);
      ins = query.getResultList();
    } catch (Exception e) {
      ins = null;
    }
    return ins;
  }

  @SuppressWarnings("unchecked")
  public List<Insurance> getListbyconsultant(String consultantid) {
    List<Insurance> ins = null;
    try {
      Query query =
          em().createNativeQuery("select * from insurance where customerid in (select id from customer where consultantid=?1)",
              Insurance.class);
      query.setParameter(1, consultantid);
      ins = query.getResultList();
    } catch (Exception e) {
      ins = null;
    }
    return ins;
  }

}
