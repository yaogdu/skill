package com.isoftstone.smart.core.servlet.inject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.shiro.guice.web.GuiceShiroFilter;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.server.mvc.jsp.JspProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

import com.google.inject.Singleton;
import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.ServletModule;
import com.isoftstone.smart.core.service.WebResourceConfig;
import com.isoftstone.smart.core.servlet.CharacterEncodingFilter;
import com.isoftstone.smart.core.servlet.DatabaseConnectionFilter;
import com.isoftstone.smart.core.servlet.DatabaseConnectionProvider;

public class CoreServletModule extends ServletModule {

  private final Logger logger = Logger.getLogger(getClass().getName());

  @Override
  protected void configureServlets() {
    logger.finest("installPersistModule begin");
    // filter("/*").through(ProxyFilter.class);
    filter("/*").through(PersistFilter.class);

    requestStaticInjection(DatabaseConnectionProvider.class);
    filter("/*").through(DatabaseConnectionFilter.class);

    filter("/*").through(GuiceShiroFilter.class);

    Map<String, String> encodeParam = new HashMap<String, String>();
    encodeParam.put("encoding", "UTF-8");
    encodeParam.put("forceEncoding", "true");
    filter("/*").through(CharacterEncodingFilter.class, encodeParam);

    Map<String, String> jaxRsParams = new HashMap<String, String>();
    jaxRsParams.put(ServletProperties.JAXRS_APPLICATION_CLASS, WebResourceConfig.class.getName());
    jaxRsParams.put(ServletProperties.FILTER_FORWARD_ON_404, Boolean.TRUE.toString());
    jaxRsParams.put(JspProperties.TEMPLATES_BASE_PATH, "/WEB-INF/templates/");

    // /store/ios/test.html
    jaxRsParams.put(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/(static|store)/.*");

    filter("/*").through(ServletContainer.class, jaxRsParams);

    bind(org.glassfish.jersey.servlet.ServletContainer.class).in(Singleton.class);

    install(new WebSecurityModule(getServletContext()));
    logger.finest("installPersistModule end");
  }
}
