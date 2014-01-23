package com.isoftstone.smart.core.service;

import com.google.inject.persist.Transactional;
import com.isoftstone.smart.core.entity.BaseEntity;
import com.isoftstone.smart.core.util.CoreUtil;

public abstract class BaseEntityService<T extends BaseEntity> extends BaseService<T> {

  @Override
  @Transactional
  public T save(T entity) {
    String id = entity.getId();
    if (id == null) {
      id = CoreUtil.randomID();
      entity.setId(id);
    }
    return super.save(entity);
  }
}
