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
@Table(name = "customer")
public class Customer extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4055087875797089898L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "consultantid")
  private Consultant consultant;

  @Transient
  private String consultantid;

  private String name;

  private int status; // 0 未删除 1 已删除
  private int gender;// 0:男，1：女

  private Timestamp age;

  private String job;

  private int married;// 0:是,1:否

  private String childrenage;// 22,23
  private String address;
  private String mobile;
  private String matename;
  private String signature;
  private Timestamp signaturetime;// 签名日期
  private String client;// 乙方
  // private int expired;// 0 yes,1 no
  private String mateage;
  private String email;
  private String familymember;

  private String memo;

  private Timestamp generatetime;

  private Timestamp updatetime;

  private String contact;

  private String contactphone;

  private String location;

  private String photo;

  private String idcard;

  private Timestamp completetime;

  private String industry;

  private int done;// 0, 成交,1 ,未成交

  private int src;// 0,公司提供 1, 转介绍 2, 自行开发

  public String getAddress() {
    return address;
  }

  public Timestamp getAge() {
    return age;
  }

  public String getChildrenage() {
    return childrenage;
  }

  public String getClient() {
    return client;
  }

  public Timestamp getCompletetime() {
    return completetime;
  }

  public Consultant getConsultant() {
    return consultant;
  }

  public String getConsultantid() {
    return consultantid;
  }

  public String getContact() {
    return contact;
  }

  public String getContactphone() {
    return contactphone;
  }

  public int getDone() {
    return done;
  }

  public String getEmail() {
    return email;
  }

  public String getFamilymember() {
    return familymember;
  }

  public int getGender() {
    return gender;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getIdcard() {
    return idcard;
  }

  public String getIndustry() {
    return industry;
  }

  public String getJob() {
    return job;
  }

  public String getLocation() {
    return location;
  }

  public int getMarried() {
    return married;
  }

  public String getMateage() {
    return mateage;
  }

  public String getMatename() {
    return matename;
  }

  public String getMemo() {
    return memo;
  }

  public String getMobile() {
    return mobile;
  }

  public String getName() {
    return name;
  }

  public String getPhoto() {
    return photo;
  }

  public String getSignature() {
    return signature;
  }

  public Timestamp getSignaturetime() {
    return signaturetime;
  }

  public int getSrc() {
    return src;
  }

  public int getStatus() {
    return status;
  }

  public Timestamp getUpdatetime() {
    return updatetime;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setAge(Timestamp age) {
    this.age = age;
  }

  public void setChildrenage(String childrenage) {
    this.childrenage = childrenage;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public void setCompletetime(Timestamp completetime) {
    this.completetime = completetime;
  }

  public void setConsultant(Consultant consultant) {
    this.consultant = consultant;
  }

  public void setConsultantid(String consultantid) {
    this.consultantid = consultantid;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public void setContactphone(String contactphone) {
    this.contactphone = contactphone;
  }

  public void setDone(int done) {
    this.done = done;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setFamilymember(String familymember) {
    this.familymember = familymember;
  }

  public void setGender(int gender) {
    this.gender = gender;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public void setJob(String job) {
    this.job = job;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setMarried(int married) {
    this.married = married;
  }

  public void setMateage(String mateage) {
    this.mateage = mateage;
  }

  public void setMatename(String matename) {
    this.matename = matename;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public void setSignaturetime(Timestamp signaturetime) {
    this.signaturetime = signaturetime;
  }

  public void setSrc(int src) {
    this.src = src;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

}
