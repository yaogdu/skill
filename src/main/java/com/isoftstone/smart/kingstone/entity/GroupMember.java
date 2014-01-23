package com.isoftstone.smart.kingstone.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.entity.BaseEntity;

@Entity
@Table(name = "groupmember")
public class GroupMember extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6789659675045139336L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "groupid")
  private Group group;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "accountid")
  private Account member;

  private Timestamp generatetime;

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public Group getGroup() {
    return group;
  }

  public Account getMember() {
    return member;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public void setMember(Account member) {
    this.member = member;
  }

}
