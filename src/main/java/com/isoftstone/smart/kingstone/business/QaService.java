package com.isoftstone.smart.kingstone.business;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Qa;

public class QaService extends BaseEntityService<Qa> {

  @Override
  public Class<Qa> getEntityType() {
    return Qa.class;
  }

}
