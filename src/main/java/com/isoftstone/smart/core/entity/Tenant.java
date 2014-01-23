package com.isoftstone.smart.core.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Tenant extends BaseEntity {

  @OneToOne
  protected Account administrator;

  protected String department;

  protected String fullname;

  protected String location;

  protected String password;

  protected String position;

  protected String profession;

  protected String rePassword;

  protected String shortname;

  protected String telphone;

  protected String username;

  protected String workPhone;

  @OneToOne
  protected Place place;

  public Account getAdministrator() {
    return administrator;
  }

  public String getDepartment() {
    return department;
  }

  public String getFullname() {
    return fullname;
  }

  public String getLocation() {
    return location;
  }

  public String getPassword() {
    return password;
  }

  public Place getPlace() {
    return place;
  }

  public String getPosition() {
    return position;
  }

  public String getProfession() {
    return profession;
  }

  public String getRePassword() {
    return rePassword;
  }

  public String getShortname() {
    return shortname;
  }

  public String getTelphone() {
    return telphone;
  }

  public String getUsername() {
    return username;
  }

  public String getWorkPhone() {
    return workPhone;
  }

  public void setAdministrator(Account administrator) {
    this.administrator = administrator;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPlace(Place place) {
    this.place = place;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public void setRePassword(String rePassword) {
    this.rePassword = rePassword;
  }

  public void setShortname(String shortname) {
    this.shortname = shortname;
  }

  public void setTelphone(String telphone) {
    this.telphone = telphone;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setWorkPhone(String workPhone) {
    this.workPhone = workPhone;
  }

}
