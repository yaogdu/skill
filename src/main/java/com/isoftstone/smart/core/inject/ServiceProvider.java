package com.isoftstone.smart.core.inject;

import java.util.Set;
import java.util.logging.Logger;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.binding.ServiceBindingBuilder;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.server.internal.JerseyResourceContext;
import org.glassfish.jersey.server.spi.ComponentProvider;

import com.isoftstone.smart.core.WebPlatform;
import com.isoftstone.smart.core.service.BaseService;

public class ServiceProvider implements ComponentProvider {

  private class RepositoryFactory implements Factory<BaseService<?>> {

    private Class<? extends BaseService<?>> serviceType;

    public RepositoryFactory(Class<? extends BaseService<?>> serviceType) {
      this.serviceType = serviceType;
    }

    @Override
    public void dispose(BaseService<?> instance) {
    }

    @Override
    public BaseService<?> provide() {
      BaseService<?> result = WebPlatform.getDefault().getInstance(serviceType);
      // TODO why initResource?
      resourceContext.initResource(result);
      return result;
    }
  }

  private static final Logger LOGGER = Logger.getLogger(ServiceProvider.class.getName());

  private ServiceLocator locator = null;

  JerseyResourceContext resourceContext;

  @SuppressWarnings("unchecked")
  @Override
  public boolean bind(Class<?> component, Set<Class<?>> providerContracts) {

    if (locator == null) {
      throw new IllegalStateException("Guice component is not initialized properly.");
    }

    LOGGER.info("Bind " + component);
    if (!BaseService.class.isAssignableFrom(component)) {
      LOGGER.warning("Out of Guice's control " + component);
      return false;
    }

    Class<? extends BaseService<?>> serviceType = (Class<? extends BaseService<?>>) component;

    DynamicConfiguration dc = Injections.getConfiguration(locator);
    final ServiceBindingBuilder bindingBuilder = Injections.newFactoryBinder(new RepositoryFactory(serviceType));
    bindingBuilder.to(component);
    for (Class contract : providerContracts) {
      bindingBuilder.to(contract);
    }

    Injections.addBinding(bindingBuilder, dc);

    dc.commit();
    return true;
  }

  @Override
  public void done() {
    // TODO bind ExceptionMapper ?
  }

  @Override
  public void initialize(final ServiceLocator locator) {
    this.locator = locator;
    resourceContext = locator.getService(JerseyResourceContext.class);
  }

}