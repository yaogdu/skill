package com.isoftstone.smart.kingstone.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.isoftstone.smart.core.entity.Account;
import com.isoftstone.smart.core.entity.BaseEntity;

@Entity
@Table(name = "consultant")
public class Consultant extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1251513708013068913L;

  private String consultantno;
  private String name;
  private int title;
  @Transient
  private String password;
  private String certificate;

  private String experience;

  private int degree;

  private String mobile;

  private String email;

  private String feature;

  private Timestamp generatetime;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accountid")
  private Account account;

  private int gender;

  public Account getAccount() {
    return account;
  }

  public String getCertificate() {
    return certificate;
  }

  public String getConsultantno() {
    return consultantno;
  }

  public int getDegree() {
    return degree;
  }

  public String getEmail() {
    return email;
  }

  public String getExperience() {
    return experience;
  }

  public String getFeature() {
    return feature;
  }

  public int getGender() {
    return gender;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getMobile() {
    return mobile;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public int getTitle() {
    return title;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public void setCertificate(String certificate) {
    this.certificate = certificate;
  }

  public void setConsultantno(String consultantno) {
    this.consultantno = consultantno;
  }

  public void setDegree(int degree) {
    this.degree = degree;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setExperience(String experience) {
    this.experience = experience;
  }

  public void setFeature(String feature) {
    this.feature = feature;
  }

  public void setGender(int gender) {
    this.gender = gender;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setTitle(int title) {
    this.title = title;
  }

}
