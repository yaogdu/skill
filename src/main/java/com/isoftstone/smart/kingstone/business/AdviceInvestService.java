package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.AdviceInvest;

public class AdviceInvestService extends BaseEntityService<AdviceInvest> {

  @Override
  public Class<AdviceInvest> getEntityType() {
    // TODO Auto-generated method stub
    return AdviceInvest.class;
  }

  @SuppressWarnings("unchecked")
  public List<AdviceInvest> getListbyconsultant(String consultantid) {
    List<AdviceInvest> ais = null;
    try {
      Query query =
          em().createNativeQuery("select * from adviceinvest where customerid in(select id from customer where consultantid=?1)",
              AdviceInvest.class);
      query.setParameter(1, consultantid);
      ais = query.getResultList();
    } catch (Exception e) {
      ais = null;
    }
    return ais;
  }

}
