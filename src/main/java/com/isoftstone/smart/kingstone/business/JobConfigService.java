package com.isoftstone.smart.kingstone.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.JobConfig;

public class JobConfigService extends BaseEntityService<JobConfig> {

  @Override
  public Class<JobConfig> getEntityType() {
    // TODO Auto-generated method stub
    return JobConfig.class;
  }

  @SuppressWarnings("unchecked")
  public List<JobConfig> getList() {
    List<JobConfig> ccs = new ArrayList<JobConfig>();
    try {
      String sql = "select * from jobconfig order by id";
      Query query = em().createNativeQuery(sql, JobConfig.class);
      ccs = query.getResultList();
    } catch (Exception e) {
      ccs = null;
    }
    return ccs;
  }

}
