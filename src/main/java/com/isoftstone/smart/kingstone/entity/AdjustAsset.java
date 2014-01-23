package com.isoftstone.smart.kingstone.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.isoftstone.smart.core.entity.BaseEntity;

@Entity
@Table(name = "adjustasset")
public class AdjustAsset extends BaseEntity {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerid")
  private Customer customer;//

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "docid")
  private Doc doc;

  @Transient
  private String customerid;

  @Transient
  private String docid;

  private String name;

  private int type;// 0:现金 1:原有保障品 2:年结余 3:股票结余

  private String adjust;

  private String adviceconfig;

  private String funds;

  private String state;

  private String memo;

  private Timestamp generatetime;

  private Timestamp updatetime;

  public String getAdjust() {
    return adjust;
  }

  public String getAdviceconfig() {
    return adviceconfig;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getCustomerid() {
    return customerid;
  }

  public Doc getDoc() {
    return doc;
  }

  public String getDocid() {
    return docid;
  }

  public String getFunds() {
    return funds;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getMemo() {
    return memo;
  }

  public String getName() {
    return name;
  }

  public String getState() {
    return state;
  }

  public int getType() {
    return type;
  }

  public Timestamp getUpdatetime() {
    return updatetime;
  }

  public void setAdjust(String adjust) {
    this.adjust = adjust;
  }

  public void setAdviceconfig(String adviceconfig) {
    this.adviceconfig = adviceconfig;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setCustomerid(String customerid) {
    this.customerid = customerid;
  }

  public void setDoc(Doc doc) {
    this.doc = doc;
  }

  public void setDocid(String docid) {
    this.docid = docid;
  }

  public void setFunds(String funds) {
    this.funds = funds;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setType(int type) {
    this.type = type;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

}
