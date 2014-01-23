package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Income;

public class IncomeService extends BaseEntityService<Income> {

  @Override
  public Class<Income> getEntityType() {
    // TODO Auto-generated method stub
    return Income.class;
  }

  public Income getLatestInfo(String docid, int type) {
    Income in = null;
    try {
      Query query = em().createNativeQuery("select * from income where type=?1 and docid=?2 ", Income.class);
      query.setParameter(1, type);
      query.setParameter(2, docid);
      in = (Income) query.getSingleResult();
    } catch (Exception e) {
      in = null;
    }
    return in;
  }

  @SuppressWarnings("unchecked")
  public List<Income> getList(String customerid) {
    List<Income> ins = null;
    try {
      Query query = em().createNativeQuery("select * from income where  customerid=?1 ", Income.class);

      query.setParameter(1, customerid);
      ins = query.getResultList();
    } catch (Exception e) {
      ins = null;
    }
    return ins;
  }

  @SuppressWarnings("unchecked")
  public List<Income> getListbyconsultant(String consultantid) {
    List<Income> ins = null;
    try {
      Query query =
          em().createNativeQuery("select * from income where  customerid in (select id from customer where consultantid=?1) ", Income.class);

      query.setParameter(1, consultantid);
      ins = query.getResultList();
    } catch (Exception e) {
      ins = null;
    }
    return ins;
  }
}
