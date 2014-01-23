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
@Table(name = "adviceinvest")
public class AdviceInvest extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String brand;// 品种

  private String amount;// 数额

  private String income;// 收益

  private double percentage;// 比例

  private int year;// 投资年期

  private String memo;// 备注

  private Timestamp generatetime;// 产生时间

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

  public String getAmount() {
    return amount;
  }

  public String getBrand() {
    return brand;
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

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getIncome() {
    return income;
  }

  public String getMemo() {
    return memo;
  }

  public double getPercentage() {
    return percentage;
  }

  public int getYear() {
    return year;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public void setBrand(String brand) {
    this.brand = brand;
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

  public void setIncome(String income) {
    this.income = income;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public void setPercentage(double percentage) {
    this.percentage = percentage;
  }

  public void setYear(int year) {
    this.year = year;
  }

}
