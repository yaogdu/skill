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
@Table(name = "risk")
public class Risk extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8102049636986605652L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerid")
  private Customer customer;// 拥有者 customerid varchar

  private int point;// 分数 point int

  private int investgrouplevel;// 适合级别 investgrouplevel smallint

  private int chosenlevel;// 选择的级别

  private Timestamp generatetime;// 生成时间 generatetime timestamp

  private String signature;

  private Timestamp signaturetime;
  private int first;
  private int second;
  private int third;
  private int fourth;

  private int fifth;

  private int sixth;

  private int seventh;

  private int eighth;

  private int ninth;

  private int tenth;

  private Timestamp updatetime;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "docid")
  private Doc doc;

  @Transient
  private String customerid;

  @Transient
  private String docid;

  public int getChosenlevel() {
    return chosenlevel;
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

  public int getEighth() {
    return eighth;
  }

  public int getFifth() {
    return fifth;
  }

  public int getFirst() {
    return first;
  }

  public int getFourth() {
    return fourth;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public int getInvestgrouplevel() {
    return investgrouplevel;
  }

  public int getNinth() {
    return ninth;
  }

  public int getPoint() {
    return point;
  }

  public int getSecond() {
    return second;
  }

  public int getSeventh() {
    return seventh;
  }

  public String getSignature() {
    return signature;
  }

  public Timestamp getSignaturetime() {
    return signaturetime;
  }

  public int getSixth() {
    return sixth;
  }

  public int getTenth() {
    return tenth;
  }

  public int getThird() {
    return third;
  }

  public Timestamp getUpdatetime() {
    return updatetime;
  }

  public void setChosenlevel(int chosenlevel) {
    this.chosenlevel = chosenlevel;
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

  public void setEighth(int eighth) {
    this.eighth = eighth;
  }

  public void setFifth(int fifth) {
    this.fifth = fifth;
  }

  public void setFirst(int first) {
    this.first = first;
  }

  public void setFourth(int fourth) {
    this.fourth = fourth;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setInvestgrouplevel(int investgrouplevel) {
    this.investgrouplevel = investgrouplevel;
  }

  public void setNinth(int ninth) {
    this.ninth = ninth;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public void setSecond(int second) {
    this.second = second;
  }

  public void setSeventh(int seventh) {
    this.seventh = seventh;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public void setSignaturetime(Timestamp signaturetime) {
    this.signaturetime = signaturetime;
  }

  public void setSixth(int sixth) {
    this.sixth = sixth;
  }

  public void setTenth(int tenth) {
    this.tenth = tenth;
  }

  public void setThird(int third) {
    this.third = third;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

}
