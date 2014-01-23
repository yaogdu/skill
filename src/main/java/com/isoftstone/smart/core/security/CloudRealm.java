package com.isoftstone.smart.core.security;

import java.util.Map;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.entity.Content;
import com.isoftstone.smart.core.entity.ContentAuthorizationInfo;
import com.isoftstone.smart.core.entity.ContentPermission;
import com.isoftstone.smart.core.service.AccountService;

@Singleton
public class CloudRealm extends AuthorizingRealm {

  public static final String ALGORITHM_NAME = Sha1Hash.ALGORITHM_NAME;

  @Inject
  private AccountService accountSvc;

  @Inject
  public CloudRealm(AccountService accountSvc, MemoryConstrainedCacheManager cacheManager) {
    this.accountSvc = accountSvc;
    HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM_NAME);
    setCredentialsMatcher(matcher);
    setCacheManager(cacheManager);
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
    UsernamePasswordToken upToken = (UsernamePasswordToken) token;
    String userName = upToken.getUsername();

    if (userName == null) {
      throw new AccountException("Null usernames are not allowed by this realm.");
    }

    Account user = accountSvc.getByLoginId(userName);
    if (user == null) {
      return null;
    }
    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPasswordHash(), getName());
    return info;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
    if (principals == null) {
      throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
    }
    ContentAuthorizationInfo info = new ContentAuthorizationInfo(principals);
    return info;
  }

  @Override
  protected boolean isPermitted(Permission permission, AuthorizationInfo info) {
    if (permission instanceof ContentPermission) {
      ContentAuthorizationInfo lazyInfo = (ContentAuthorizationInfo) info;
      ContentPermission<?> p = (ContentPermission<?>) permission;
      Content target = p.getContent();
      Map<Class<?>, ContentPermission<?>> perms = lazyInfo.getPermssions(target);
      if (perms != null && !perms.isEmpty()) {
        ContentPermission<?> perm = perms.get(p.getActionType());
        if (perm.implies(p)) {
          return true;
        }
      }
      return false;
    }
    return super.isPermitted(permission, info);
  }
}
