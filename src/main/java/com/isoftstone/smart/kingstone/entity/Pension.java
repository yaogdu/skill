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
@Table(name = "pension")
public class Pension extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1639606868629717647L;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerid")
  private Customer customer;// 拥有者 customerid varchar

  private int age;// 目前年龄 age int
  private int retireage;// 预计退休年龄 retireage int
  private int year;// 预计提取养老金年期 year int
  private double rate;// 预计通货膨胀率（%） rate float
  private String currentexpense;// 当前每月支出 currentexpense float
  private String retireexpense;// 退休时预计每月支出 retireexpense float
  private String total;// 预计一生所需退休金 total float
  private Timestamp generatetime;// 生成时间 generatetime timestamp

  @Transient
  private String customerid;

  @Transient
  private String docid;

  private Timestamp updatetime;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "docid")
  private Doc doc;

  public int getAge() {
    return age;
  }

  public String getCurrentexpense() {
    return currentexpense;
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

  public double getRate() {
    return rate;
  }

  public int getRetireage() {
    return retireage;
  }

  public String getRetireexpense() {
    return retireexpense;
  }

  public String getTotal() {
    return total;
  }

  public Timestamp getUpdatetime() {
    return updatetime;
  }

  public int getYear() {
    return year;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setCurrentexpense(String currentexpense) {
    this.currentexpense = currentexpense;
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

  public void setRate(double rate) {
    this.rate = rate;
  }

  public void setRetireage(int retireage) {
    this.retireage = retireage;
  }

  public void setRetireexpense(String retireexpense) {
    this.retireexpense = retireexpense;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

  public void setYear(int year) {
    this.year = year;
  }

}
