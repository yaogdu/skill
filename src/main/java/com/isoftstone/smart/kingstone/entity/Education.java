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
@Table(name = "education")
public class Education extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6811240364381081557L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerid")
  private Customer customer;// 拥有者 ownerid varchar

  private String year;// 年期 year float
  private String rate;// 通胀率 rate float 9
  private Timestamp generatetime;// 生成时间 generatetime timestamp

  private String age;
  private String course;// 学程 course int 0:高中,1:国外大学,2:额外

  private String livecost;// 生活费 livecost float

  private String getinage; // varchar 预计就读年龄
  private String tuitionrate; // rate学费增长率
  private String tuition;// 当前每年学费支出varchar
  private String getintuition;// 预计就学时每年学费 varchar
  private String totaltuition;// 预计就学时学费总额 varchar
  private String getinlivecost;// 预计就学时每年生活费 varchar
  private String totallivecost;// 预计就学时生活费总额 totallivecost varchar
  private String country;
  private String child;

  private String total;

  private Timestamp updatetime;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "docid")
  private Doc doc;

  @Transient
  private String customerid;

  @Transient
  private String docid;

  public String getAge() {
    return age;
  }

  public String getChild() {
    return child;
  }

  public String getCountry() {
    return country;
  }

  public String getCourse() {
    return course;
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

  public String getGetinage() {
    return getinage;
  }

  public String getGetinlivecost() {
    return getinlivecost;
  }

  public String getGetintuition() {
    return getintuition;
  }

  public String getLivecost() {
    return livecost;
  }

  public String getRate() {
    return rate;
  }

  public String getTotal() {
    return total;
  }

  public String getTotallivecost() {
    return totallivecost;
  }

  public String getTotaltuition() {
    return totaltuition;
  }

  public String getTuition() {
    return tuition;
  }

  public String getTuitionrate() {
    return tuitionrate;
  }

  public Timestamp getUpdatetime() {
    return updatetime;
  }

  public String getYear() {
    return year;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public void setChild(String child) {
    this.child = child;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setCourse(String course) {
    this.course = course;
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

  public void setGetinage(String getinage) {
    this.getinage = getinage;
  }

  public void setGetinlivecost(String getinlivecost) {
    this.getinlivecost = getinlivecost;
  }

  public void setGetintuition(String getintuition) {
    this.getintuition = getintuition;
  }

  public void setLivecost(String livecost) {
    this.livecost = livecost;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public void setTotallivecost(String totallivecost) {
    this.totallivecost = totallivecost;
  }

  public void setTotaltuition(String totaltuition) {
    this.totaltuition = totaltuition;
  }

  public void setTuition(String tuition) {
    this.tuition = tuition;
  }

  public void setTuitionrate(String tuitionrate) {
    this.tuitionrate = tuitionrate;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

  public void setYear(String year) {
    this.year = year;
  }

}
