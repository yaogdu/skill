package com.isoftstone.smart.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.isoftstone.smart.core.WebPlatform;

@MappedSuperclass
public class BaseEntity {

  @OneToOne
  protected Tenant tenant;

  @OneToOne
  protected Account creator;

  protected Date dateCreated;

  @Id
  @Column(length = 128)
  protected String id;

  protected Date lastModified;

  @OneToOne
  protected Account modifier;

  public Account getCreator() {
    return creator;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public String getId() {
    return id;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public Account getModifier() {
    return modifier;
  }

  public Tenant getTenant() {
    return tenant;
  }

  public void setCreator(Account creator) {
    this.creator = creator;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public void setModifier(Account modifier) {
    this.modifier = modifier;
  }

  public void setTenant(Tenant tenant) {
    this.tenant = tenant;
  }

  protected EntityManager em() {
    return WebPlatform.getDefault().getEntityManager();
  }

}
