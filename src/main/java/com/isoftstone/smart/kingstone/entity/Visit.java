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
@Table(name = "visit")
public class Visit extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6578678378874925216L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerid")
  private Customer customer;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "consultantid")
  private Consultant consultant;
  @Transient
  private String customerid;

  @Transient
  private String docid;

  private Timestamp visittime;

  private String visitplace;

  private String visitcontent;

  private String signature;

  private String location;

  public Consultant getConsultant() {
    return consultant;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getCustomerid() {
    return customerid;
  }

  public String getDocid() {
    return docid;
  }

  public String getLocation() {
    return location;
  }

  public String getSignature() {
    return signature;
  }

  public String getVisitcontent() {
    return visitcontent;
  }

  public String getVisitplace() {
    return visitplace;
  }

  public Timestamp getVisittime() {
    return visittime;
  }

  public void setConsultant(Consultant consultant) {
    this.consultant = consultant;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setCustomerid(String customerid) {
    this.customerid = customerid;
  }

  public void setDocid(String docid) {
    this.docid = docid;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public void setVisitcontent(String visitcontent) {
    this.visitcontent = visitcontent;
  }

  public void setVisitplace(String visitplace) {
    this.visitplace = visitplace;
  }

  public void setVisittime(Timestamp visittime) {
    this.visittime = visittime;
  }

}
