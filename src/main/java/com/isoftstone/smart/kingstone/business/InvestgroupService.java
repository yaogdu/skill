package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Investgroup;

public class InvestgroupService extends BaseEntityService<Investgroup> {

  @SuppressWarnings("unchecked")
  public List<Investgroup> getByLevel(int level) {
    List<Investgroup> groups = null;

    try {
      Query query = em().createNativeQuery("select * from investgroup where level=?1", Investgroup.class);
      query.setParameter(1, level);
      groups = query.getResultList();
    } catch (Exception e) {
      groups = null;
    }
    return groups;
  }

  @Override
  public Class<Investgroup> getEntityType() {
    // TODO Auto-generated method stub
    return Investgroup.class;
  }
}
