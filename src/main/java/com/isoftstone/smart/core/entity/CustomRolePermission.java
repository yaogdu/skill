package com.isoftstone.smart.core.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CustomRolePermission {

  private int actionBits;

  private String actionType;

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private CustomRole role;

  public int getActionBits() {
    return actionBits;
  }

  public String getActionType() {
    return actionType;
  }

  public CustomRole getRole() {
    return role;
  }

  public void setActionBits(int actionBits) {
    this.actionBits = actionBits;
  }

  public void setActionType(String actionType) {
    this.actionType = actionType;
  }

  public void setRole(CustomRole role) {
    this.role = role;
  }
}
