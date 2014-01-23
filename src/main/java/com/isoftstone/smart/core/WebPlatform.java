package com.isoftstone.smart.core;

import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Transient;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.isoftstone.smart.core.service.BaseService;

@Singleton
public final class WebPlatform extends AbstractModule {

  private static WebPlatform instance;

  public static WebPlatform getDefault() {
    if (instance == null) {
      instance = new WebPlatform();
    }
    return instance;
  }

  @Inject
  @Transient
  private Provider<EntityManager> emProvider;

  @Inject
  private Injector injector;

  private Set<Class<?>> entityClasses = new HashSet<Class<?>>();

  private Set<Class<?>> serviceClasses = new HashSet<Class<?>>();

  private static final Logger logger = Logger.getLogger(WebPlatform.class.getName());

  public static final String INT_PROPERTIES = "/ini.properties";

  public static Properties props = new Properties();

  private WebPlatform() {
  }

  public WebPlatform addEntity(Class<?> cls) {
    entityClasses.add(cls);
    return this;
  }

  public WebPlatform addService(Class<? extends BaseService<?>> cls) {
    serviceClasses.add(cls);
    return this;
  }

  public Set<Class<?>> getEntities() {
    return entityClasses;
  }

  public EntityManager getEntityManager() {
    return emProvider.get();
  }

  public <T> T getInstance(Class<T> resourceType) {
    return injector.getInstance(resourceType);
  }

  public Set<Class<?>> getServices() {
    return serviceClasses;
  }

  public void injectMembers(Object instance) {
    injector.injectMembers(instance);
  }

  @Override
  protected void configure() {
    bind(WebPlatform.class).toInstance(this);
    try {
      URL url = WebPlatform.class.getResource(INT_PROPERTIES);
      InputStream in = url.openStream();
      props.load(in);
      logger.config("load init properties " + url);
      Names.bindProperties(binder(), props);
    } catch (Exception e) {
      logger.finest("classpath:/init.properties not exist, use out-of-box values");
      // okay... we use default values, then.
    }

    loadFromClasspath();
  }

  private void loadFromClasspath() {
    ServiceLoader<Module> modules = ServiceLoader.load(Module.class);
    Iterator<Module> moduleIt = modules.iterator();
    while (moduleIt.hasNext()) {
      Module module = moduleIt.next();
      logger.finer("Install " + module.getClass().getName());
      install(module);
    }
  }

}
