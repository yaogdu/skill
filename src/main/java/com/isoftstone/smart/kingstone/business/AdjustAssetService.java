package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.AdjustAsset;

public class AdjustAssetService extends BaseEntityService<AdjustAsset> {

  @Override
  public Class<AdjustAsset> getEntityType() {
    // TODO Auto-generated method stub
    return AdjustAsset.class;
  }

  @SuppressWarnings("unchecked")
  public List<AdjustAsset> getListbyconsultant(String consultantid) {
    List<AdjustAsset> aa = null;
    try {
      Query query =
          em().createNativeQuery("select * from adjustasset where customerid in(select id from customer where consultantid=?1)",
              AdjustAsset.class);
      query.setParameter(1, consultantid);
      aa = query.getResultList();
    } catch (Exception e) {
      aa = null;
    }
    return aa;
  }

}
