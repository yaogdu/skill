package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Visit;

public class VisitService extends BaseEntityService<Visit> {

  @Override
  public Class<Visit> getEntityType() {
    // TODO Auto-generated method stub
    return Visit.class;
  }

  @SuppressWarnings("unchecked")
  public List<Visit> getList(String customerid) {
    List<Visit> visits = null;
    try {
      Query query = em().createNativeQuery("select * from visit where customerid =?1 order by visittime desc", Visit.class);
      query.setParameter(1, customerid);
      visits = query.getResultList();

    } catch (Exception e) {
      visits = null;
    }
    return visits;
  }

}
