package com.isoftstone.smart.core.inject;


import org.apache.shiro.guice.ShiroModule;

import com.google.inject.name.Names;
import com.isoftstone.smart.core.security.CloudRealm;

public class ShiroTestModule extends ShiroModule {

  @Override
  protected void configureShiro() {
    bindRealm().to(CloudRealm.class);
    bindConstant().annotatedWith(Names.named("shiro.globalSessionTimeout")).to(30000L);

    // bind(CredentialsMatcher.class).to(HashedCredentialsMatcher.class);
    // bind(HashedCredentialsMatcher.class);
    // bindConstant().annotatedWith(Names.named("shiro.hashAlgorithmName"))
    // .to(Sha1Hash.ALGORITHM_NAME);
  }

}
