package com.isoftstone.smart.core.servlet.inject;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.isoftstone.smart.core.WebPlatform;

public final class BootServletContextListener extends GuiceServletContextListener {

  private ServiceLoader<ServletContextListener> servletContextListeners;

  private static final Logger logger = Logger.getLogger(BootServletContextListener.class.getName());

  @Override
  public void contextDestroyed(final ServletContextEvent servletContextEvent) {
    super.contextDestroyed(servletContextEvent);
    if (servletContextListeners != null) {
      Iterator<ServletContextListener> it = servletContextListeners.iterator();
      while (it.hasNext()) {
        ServletContextListener listener = it.next();
        logger.finer("Destroy ServletContextListener: " + listener.getClass().getName());
        listener.contextDestroyed(servletContextEvent);
      }
    }
    logger.finer("Destroyed ServletContextListener");
  }

  @Override
  public void contextInitialized(final ServletContextEvent servletContextEvent) {
    if (servletContextListeners == null) {
      servletContextListeners = ServiceLoader.load(ServletContextListener.class);
    }
    if (servletContextListeners != null) {
      Iterator<ServletContextListener> it = servletContextListeners.iterator();
      while (it.hasNext()) {
        ServletContextListener listener = it.next();
        logger.finer("Initialize ServletContextListener: " + listener.getClass().getName());
        listener.contextInitialized(servletContextEvent);
      }
    }
    super.contextInitialized(servletContextEvent);
    logger.finer("Initialized ServletContextListener");
  }

  @Override
  protected Injector getInjector() {
    Injector injector = Guice.createInjector(WebPlatform.getDefault(), new BootServletModule());
    return injector;
  }
}
