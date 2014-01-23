package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Education;

public class EducationService extends BaseEntityService<Education> {

  @Override
  public Class<Education> getEntityType() {
    // TODO Auto-generated method stub
    return Education.class;
  }

  @SuppressWarnings("unchecked")
  public List<Education> getList(String customerid) {
    List<Education> edus = null;
    try {
      Query query = em().createNativeQuery("select * from education where customerid=?1", Education.class);
      query.setParameter(1, customerid);
      edus = query.getResultList();
    } catch (Exception e) {

      edus = null;
    }
    return edus;
  }

  @SuppressWarnings("unchecked")
  public List<Education> getListbyconsultant(String consultantid) {
    List<Education> edus = null;
    try {
      Query query =
          em().createNativeQuery("select * from education where customerid in (select id from customer where consultantid=?1)",
              Education.class);
      query.setParameter(1, consultantid);
      edus = query.getResultList();
    } catch (Exception e) {

      edus = null;
    }
    return edus;
  }
}
