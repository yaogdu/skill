package com.isoftstone.smart.core.service;

import java.net.URI;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.isoftstone.smart.core.WebPlatform;

public abstract class BaseTest extends JerseyTest {

  protected static final URI BASE_URI = URI.create("http://localhost:8080/smart/");

  @Override
  protected ResourceConfig configure() {
    enable(TestProperties.LOG_TRAFFIC);
    final Injector injector = Guice.createInjector(WebPlatform.getDefault());
    PersistService persistService = injector.getInstance(PersistService.class);
    persistService.start();
    injector.injectMembers(this);
    return injector.getInstance(WebResourceConfig.class);
  }
}
