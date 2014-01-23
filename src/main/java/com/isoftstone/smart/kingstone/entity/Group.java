package com.isoftstone.smart.kingstone.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.entity.BaseEntity;

@Entity
@Table(name = "groups")
public class Group extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4292460295849795724L;

  private String name;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "accountid")
  private Account leader;

  private Timestamp generatetime;

  private String district;

  private int level;

  @Transient
  private String member;

  // 组名 name varchar
  // 组长 accountid varchar
  // 创建时间 generatetime timestamp
  // 地区 district varchar
  // 权限值 level int

  public String getDistrict() {
    return district;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public Account getLeader() {
    return leader;
  }

  public int getLevel() {
    return level;
  }

  public String getMember() {
    return member;
  }

  public String getName() {
    return name;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setLeader(Account leader) {
    this.leader = leader;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setMember(String member) {
    this.member = member;
  }

  public void setName(String name) {
    this.name = name;
  }

}
