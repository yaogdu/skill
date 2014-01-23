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
@Table(name = "insurance")
public class Insurance extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4363173118313013452L;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerid")
  private Customer customer;// 拥有者 customerid varchar
  private String object; // 保障对象 object varchar
  private String name;// 险种 name varchar
  private String insured;// 保额 insured float
  private String total;// 保费 total float
  private int year;// 年期 year int
  private String memo;// 备注 memo varchar
  private int type;// 类型 type smallint 0:现有,1:建议
  private Timestamp generatetime;// 生成时间 generatetime timestamp

  private String insuredpeople;

  private int leftyear;

  @Transient
  private String customerid;

  @Transient
  private String docid;

  private Timestamp updatetime;

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

  public String getInsured() {
    return insured;
  }

  public String getInsuredpeople() {
    return insuredpeople;
  }

  public int getLeftyear() {
    return leftyear;
  }

  public String getMemo() {
    return memo;
  }

  public String getName() {
    return name;
  }

  public String getObject() {
    return object;
  }

  public String getTotal() {
    return total;
  }

  public int getType() {
    return type;
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

  public void setInsured(String insured) {
    this.insured = insured;
  }

  public void setInsuredpeople(String insuredpeople) {
    this.insuredpeople = insuredpeople;
  }

  public void setLeftyear(int leftyear) {
    this.leftyear = leftyear;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setObject(String object) {
    this.object = object;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public void setType(int type) {
    this.type = type;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

  public void setYear(int year) {
    this.year = year;
  }

}
