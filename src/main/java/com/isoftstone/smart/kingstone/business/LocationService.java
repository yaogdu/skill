package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.ConsultantLocation;

public class LocationService extends BaseEntityService<ConsultantLocation> {

  @Override
  public Class<ConsultantLocation> getEntityType() {
    // TODO Auto-generated method stub
    return ConsultantLocation.class;
  }

  @SuppressWarnings("unchecked")
  public List<ConsultantLocation> routeByConsultantid(String consultantid) {
    List<ConsultantLocation> locations = null;
    try {
      Query query =
          em().createNativeQuery("select * from consultantlocation where consultantid=?1 order by generatetime", ConsultantLocation.class);
      query.setParameter(1, consultantid);
      locations = query.getResultList();
    } catch (Exception e) {
      locations = null;
    }
    return locations;
  }
}
