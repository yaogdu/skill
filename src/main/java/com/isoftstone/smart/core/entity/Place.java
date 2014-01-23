package com.isoftstone.smart.core.entity;

import javax.persistence.Entity;

@Entity
public class Place extends Content {

  protected String name;

  @Override
  public Class<PlaceMember> getMemberType() {
    return PlaceMember.class;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
