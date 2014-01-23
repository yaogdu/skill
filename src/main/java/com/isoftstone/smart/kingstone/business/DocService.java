package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.persist.Transactional;
import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Doc;

public class DocService extends BaseEntityService<Doc> {

  @Transactional
  public int executeSql(String sql) {
    int total = 0;
    try {
      Query query = em().createNativeQuery(sql);

      total = query.executeUpdate();

    } catch (Exception e) {
      total = 0;
    }
    return total;
  }

  public Doc getDocByCustomerid(String customerid) {
    Doc doc = null;
    try {
      Query query = em().createNativeQuery("select * from doc where customerid=?1 order by updatetime desc limit 0,1", Doc.class);
      query.setParameter(1, customerid);
      doc = (Doc) query.getSingleResult();
    } catch (Exception e) {
      doc = null;
    }
    return doc;
  }

  @Override
  public Class<Doc> getEntityType() {
    // TODO Auto-generated method stub
    return Doc.class;
  }

  @SuppressWarnings("unchecked")
  public List<Doc> getListbyconsultant(String consultantid) {
    List<Doc> docs = null;
    try {
      Query query =
          em().createNativeQuery("select * from doc where customerid in(select id from customer where consultantid=?1)", Doc.class);
      query.setParameter(1, consultantid);
      docs = query.getResultList();
    } catch (Exception e) {
      docs = null;
    }
    return docs;
  }
}
