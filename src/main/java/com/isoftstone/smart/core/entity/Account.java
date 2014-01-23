package com.isoftstone.smart.core.entity;

import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Account extends BaseEntity implements Principal {

  protected String fullname;

  protected String loginId;

  @Transient
  private String password;

  private int level;

  protected String passwordHash;

  protected AccountStatus status;

  public String getFullname() {
    return fullname;
  }

  public int getLevel() {
    return level;
  }

  public String getLoginId() {
    return loginId;
  }

  @Override
  public String getName() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public AccountStatus getStatus() {
    return status;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public void setStatus(AccountStatus status) {
    this.status = status;
  }

}
