package com.isoftstone.smart.core.service;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import com.fasterxml.jackson.core.JsonParser;
import com.isoftstone.smart.core.WebPlatform;
import com.isoftstone.smart.core.exception.ShiroExceptionMapper;

public class WebResourceConfig extends ResourceConfig {

  public WebResourceConfig() {
    super(WebPlatform.getDefault().getServices());

    registerClasses(JacksonFeature.class, ShiroExceptionMapper.class, JspMvcFeature.class);
  }

}
