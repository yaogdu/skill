package com.isoftstone.smart.kingstone.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.isoftstone.smart.core.entity.BaseEntity;

@Entity
@Table(name = "docrecord")
public class DocRecord extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7810735825125204528L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerid")
  private Customer customer;// 拥有者 ownerid varchar

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "consultantid")
  private Consultant consultant;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "riskid")
  private Risk risk;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "docid")
  private Doc doc;

  private Timestamp generatetime;

  private String path;

  private String docname;

  private int type;//

  public Consultant getConsultant() {
    return consultant;
  }

  public Customer getCustomer() {
    return customer;
  }

  public Doc getDoc() {
    return doc;
  }

  public String getDocname() {
    return docname;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getPath() {
    return path;
  }

  public Risk getRisk() {
    return risk;
  }

  public int getType() {
    return type;
  }

  public void setConsultant(Consultant consultant) {
    this.consultant = consultant;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setDoc(Doc doc) {
    this.doc = doc;
  }

  public void setDocname(String docname) {
    this.docname = docname;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setRisk(Risk risk) {
    this.risk = risk;
  }

  public void setType(int type) {
    this.type = type;
  }
}
