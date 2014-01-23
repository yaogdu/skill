package com.isoftstone.smart.core.inject;

import javax.persistence.PostLoad;

import com.isoftstone.smart.core.WebPlatform;

public class InjectionListener {

  @PostLoad
  void onPostLoad(final Object domain) {
    WebPlatform.getDefault().injectMembers(domain);
  }
}
