package com.isoftstone.smart.core.inject;

import com.google.inject.AbstractModule;
import com.isoftstone.smart.core.WebPlatform;
import com.isoftstone.smart.core.service.BaseService;

public abstract class ServiceModule extends AbstractModule {

  protected ServiceModule addEntity(Class<?> cls) {
    WebPlatform.getDefault().addEntity(cls);
    return this;
  }

  protected ServiceModule addService(Class<? extends BaseService<?>> cls) {
    WebPlatform.getDefault().addService(cls);
    return this;
  }

}
