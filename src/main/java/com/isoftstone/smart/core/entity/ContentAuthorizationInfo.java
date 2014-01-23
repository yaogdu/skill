package com.isoftstone.smart.core.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.TypedQuery;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import com.isoftstone.smart.core.WebPlatform;

public class ContentAuthorizationInfo extends SimpleAuthorizationInfo {

  private PrincipalCollection principals;

  private Map<Content, Map<Class<?>, ContentPermission<?>>> permMap = new HashMap<>();

  private Map<Content, Set<Role>> roleMap = new HashMap<>();

  public ContentAuthorizationInfo(PrincipalCollection principals) {
    this.principals = principals;
  }

  public Map<Class<?>, ContentPermission<?>> getPermssions(Content content) {
    ensureLoaded(content);
    return permMap.get(content);
  }

  public PrincipalCollection getPrincipals() {
    return principals;
  }

  public Set<Role> getRoles(Content content) {
    ensureLoaded(content);
    return roleMap.get(content);
  }

  public boolean isLoaded(Content target) {
    return permMap.containsKey(target);
  }

  public void setPrincipals(PrincipalCollection principals) {
    this.principals = principals;
  }

  private void ensureLoaded(Content content) {
    if (!permMap.containsKey(content)) {
      Set<Role> roles = new HashSet<>();
      for (Object p : principals) {
        TypedQuery<Role> q =
            WebPlatform.getDefault().getEntityManager().createQuery(
                "select m.role from " + content.getMemberType().getName() + " m where m.principal=:principal and m.content=:content",
                Role.class);
        q.setParameter("principal", p);
        q.setParameter("content", content);
        List<Role> result = q.getResultList();
        roles.addAll(result);
      }
      roleMap.put(content, roles);
      Map<Class<?>, ContentPermission<?>> typedPermMap = new HashMap<>();
      for (Role role : roles) {
        for (Action action : role.getAllowableActions()) {
          Class<? extends Action> actionType = action.getClass();
          ContentPermission perm = typedPermMap.get(actionType);
          if (perm == null) {
            typedPermMap.put(actionType, perm = new ContentPermission(content));
            perm.setContent(content);
          }
          perm.addAction(action);
        }
      }
      permMap.put(content, typedPermMap);
    }
  }

}
