package com.isoftstone.smart.kingstone.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.isoftstone.smart.core.entity.BaseEntity;

@Entity
@Table(name = "investment")
public class Investment extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7362037640229013440L;

  @Transient
  private String customerid;

  @Transient
  private String docid;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerid")
  private Customer customer;// 拥有者 customerid varchar

  private int year;// 年 year int

  private int month;// 月 month int

  private String totalinvest;// 累计投资额 totalinvest float

  private String totalleft;// 期末余额 totalleft float
  private double rate;// 年收益率 rate flat
  private Timestamp updatetime;
  private Timestamp generatetime;// 生成时间 generatetime timestamp
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "docid")
  private Doc doc;
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

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public int getMonth() {
    return month;
  }

  public double getRate() {
    return rate;
  }

  public String getTotalinvest() {
    return totalinvest;
  }

  public String getTotalleft() {
    return totalleft;
  }

  public Timestamp getUpdatetime() {
    return updatetime;
  }

  public int getYear() {
    return year;
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

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  public void setTotalinvest(String totalinvest) {
    this.totalinvest = totalinvest;
  }

  public void setTotalleft(String totalleft) {
    this.totalleft = totalleft;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

  public void setYear(int year) {
    this.year = year;
  }

}
