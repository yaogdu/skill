package com.isoftstone.smart.core.entity;

import java.security.Principal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@MappedSuperclass
@TypeDefs({
    @TypeDef(name = PrincipalUserType.NAME, typeClass = PrincipalUserType.class),
    @TypeDef(name = RoleUserType.NAME, typeClass = RoleUserType.class)})
public abstract class Member<T> extends BaseEntity {

  @Type(type = PrincipalUserType.NAME)
  @Columns(columns = {@Column(name = "principalType"), @Column(name = "principalId")})
  protected Principal principal;

  @Type(type = RoleUserType.NAME)
  @Columns(columns = {@Column(name = "roleType"), @Column(name = "roleId")})
  protected Role role;

  public abstract T getContent();

  public Principal getPrincipal() {
    return principal;
  }

  public Role getRole() {
    return role;
  }

  public abstract void setContent(T content);

  public void setPrincipal(Principal principal) {
    this.principal = principal;
  }

  public void setRole(Role role) {
    this.role = role;
  }

}
