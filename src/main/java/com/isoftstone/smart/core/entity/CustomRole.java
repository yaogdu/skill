package com.isoftstone.smart.core.entity;

import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class CustomRole extends BaseEntity implements Role {

  private String name;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
  @MapKey(name = "actionType")
  private Map<String, CustomRolePermission> permissions;

  @Override
  public Set<Action> getAllowableActions() {
    return null;
  }

}
