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
@Table(name = "consultantlocation")
public class ConsultantLocation extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5973616087207821506L;

  private String location;

  private String address;

  @ManyToOne
  @JoinColumn(name = "customerid")
  private Customer customer;

  private Timestamp generatetime;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "consultantid")
  private Consultant consultant;

  @Transient
  private String customerid;

  @Transient
  private String consultantid;

  public String getAddress() {
    return address;
  }

  public Consultant getConsultant() {
    return consultant;
  }

  public String getConsultantid() {
    return consultantid;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getCustomerid() {
    return customerid;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getLocation() {
    return location;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setConsultant(Consultant consultant) {
    this.consultant = consultant;
  }

  public void setConsultantid(String consultantid) {
    this.consultantid = consultantid;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setCustomerid(String customerid) {
    this.customerid = customerid;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
