package com.isoftstone.smart.core.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.apache.shiro.SecurityUtils;

@MappedSuperclass
public class Content extends BaseEntity {

  @OneToOne
  protected Place place;

  public <T extends Action> void checkPermission(@SuppressWarnings("unchecked") T... actions) {
    SecurityUtils.getSubject().checkPermission(new ContentPermission<T>(this, actions));
  }

  public Class<? extends Member<?>> getMemberType() {
    return ContentMember.class;
  }

  public Place getPlace() {
    return place;
  }

  public <T extends Action> boolean isPermitted(@SuppressWarnings("unchecked") T... actions) {
    return SecurityUtils.getSubject().isPermitted(new ContentPermission<T>(this, actions));
  }

  public void setPlace(Place place) {
    this.place = place;
  }
}
