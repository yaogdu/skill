package com.isoftstone.smart.kingstone.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.isoftstone.smart.core.entity.BaseEntity;

@Entity
@Table(name = "jobconfig")
public class JobConfig extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 673862055640422573L;

  private String name;

  private Timestamp generatetime;

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getName() {
    return name;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setName(String name) {
    this.name = name;
  }

}
