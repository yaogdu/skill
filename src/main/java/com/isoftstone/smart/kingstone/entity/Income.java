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
@Table(name = "income")
public class Income extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3678799913226078444L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerid")
  private Customer customer;
  private String income;// 本人收入： income float
  private String incomechange;
  private String mateincome;// 配偶收入： mateincome float
  private String mateincomechange;
  private String bonus;// 年终奖金： bonus float
  private String bonuschange;
  private String rent;// 房租收入： rent float
  private String rentchange;
  private String deposit;// 存款债券利息： deposit float
  private String depositchange;
  private String dividend;// 股利、股息： dividend float
  private String dividendchange;
  private String sortdividend;// 各类分红： sortdividend float
  private String sortdividendchange;
  private String fixedincome;// 其它固定收入: fixedincome float
  private String fixedincomechange;
  private String unfixedincome;// 其它不固定收入: unfixedincome float
  private String unfixedincomechange;
  private String family;// 家庭生活开销： family float
  private String familychange;
  private String rentexpense;// 房屋支出： rentexpense float
  private String rentexpensechange;
  private String medical;// 医疗费： medical float
  private String medicalchange;
  private String children;// 子女教育费： children float
  private String childrenchange;
  private String insurance;// 保险费： insurance float
  private String insurancechange;
  private String travel;// 旅游费： travel float
  private String travelchange;
  private String maintenance;// 赡养费： maintenance float
  private String maintenancechange;
  private String fixedexpense;// 其它固定支出： fixedexpense float
  private String fixedexpensechange;
  private String unfixedexpense;// 其它不固定支出： unfixedexpense float
  private String unfixedexpensechange;
  private String incometotal;// 收入合计 incometotal float
  private String incometotalchange;
  private String expensetotal;// 支出合计 expensetotal float
  private String expensetotalchange;
  private String balance;// 结余 balance float
  private int type;// 类型 type smallint 0:原有,1:调整后

  private String advice;// 景淳建议 advice varchar

  private Timestamp updatetime;//

  private Timestamp generatetime;// 生成时间 generatetime timestamp

  @Transient
  private String customerid;

  @Transient
  private String docid;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "docid")
  private Doc doc;

  public String getAdvice() {
    return advice;
  }

  public String getBalance() {
    return balance;
  }

  public String getBonus() {
    return bonus;
  }

  public String getBonuschange() {
    return bonuschange;
  }

  public String getChildren() {
    return children;
  }

  public String getChildrenchange() {
    return childrenchange;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getCustomerid() {
    return customerid;
  }

  public String getDeposit() {
    return deposit;
  }

  public String getDepositchange() {
    return depositchange;
  }

  public String getDividend() {
    return dividend;
  }

  public String getDividendchange() {
    return dividendchange;
  }

  public Doc getDoc() {
    return doc;
  }

  public String getDocid() {
    return docid;
  }

  public String getExpensetotal() {
    return expensetotal;
  }

  public String getExpensetotalchange() {
    return expensetotalchange;
  }

  public String getFamily() {
    return family;
  }

  public String getFamilychange() {
    return familychange;
  }

  public String getFixedexpense() {
    return fixedexpense;
  }

  public String getFixedexpensechange() {
    return fixedexpensechange;
  }

  public String getFixedincome() {
    return fixedincome;
  }

  public String getFixedincomechange() {
    return fixedincomechange;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getIncome() {
    return income;
  }

  public String getIncomechange() {
    return incomechange;
  }

  public String getIncometotal() {
    return incometotal;
  }

  public String getIncometotalchange() {
    return incometotalchange;
  }

  public String getInsurance() {
    return insurance;
  }

  public String getInsurancechange() {
    return insurancechange;
  }

  public String getMaintenance() {
    return maintenance;
  }

  public String getMaintenancechange() {
    return maintenancechange;
  }

  public String getMateincome() {
    return mateincome;
  }

  public String getMateincomechange() {
    return mateincomechange;
  }

  public String getMedical() {
    return medical;
  }

  public String getMedicalchange() {
    return medicalchange;
  }

  public String getRent() {
    return rent;
  }

  public String getRentchange() {
    return rentchange;
  }

  public String getRentexpense() {
    return rentexpense;
  }

  public String getRentexpensechange() {
    return rentexpensechange;
  }

  public String getSortdividend() {
    return sortdividend;
  }

  public String getSortdividendchange() {
    return sortdividendchange;
  }

  public String getTravel() {
    return travel;
  }

  public String getTravelchange() {
    return travelchange;
  }

  public int getType() {
    return type;
  }

  public String getUnfixedexpense() {
    return unfixedexpense;
  }

  public String getUnfixedexpensechange() {
    return unfixedexpensechange;
  }

  public String getUnfixedincome() {
    return unfixedincome;
  }

  public String getUnfixedincomechange() {
    return unfixedincomechange;
  }

  public Timestamp getUpdatetime() {
    return updatetime;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  public void setBonus(String bonus) {
    this.bonus = bonus;
  }

  public void setBonuschange(String bonuschange) {
    this.bonuschange = bonuschange;
  }

  public void setChildren(String children) {
    this.children = children;
  }

  public void setChildrenchange(String childrenchange) {
    this.childrenchange = childrenchange;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setCustomerid(String customerid) {
    this.customerid = customerid;
  }

  public void setDeposit(String deposit) {
    this.deposit = deposit;
  }

  public void setDepositchange(String depositchange) {
    this.depositchange = depositchange;
  }

  public void setDividend(String dividend) {
    this.dividend = dividend;
  }

  public void setDividendchange(String dividendchange) {
    this.dividendchange = dividendchange;
  }

  public void setDoc(Doc doc) {
    this.doc = doc;
  }

  public void setDocid(String docid) {
    this.docid = docid;
  }

  public void setExpensetotal(String expensetotal) {
    this.expensetotal = expensetotal;
  }

  public void setExpensetotalchange(String expensetotalchange) {
    this.expensetotalchange = expensetotalchange;
  }

  public void setFamily(String family) {
    this.family = family;
  }

  public void setFamilychange(String familychange) {
    this.familychange = familychange;
  }

  public void setFixedexpense(String fixedexpense) {
    this.fixedexpense = fixedexpense;
  }

  public void setFixedexpensechange(String fixedexpensechange) {
    this.fixedexpensechange = fixedexpensechange;
  }

  public void setFixedincome(String fixedincome) {
    this.fixedincome = fixedincome;
  }

  public void setFixedincomechange(String fixedincomechange) {
    this.fixedincomechange = fixedincomechange;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setIncome(String income) {
    this.income = income;
  }

  public void setIncomechange(String incomechange) {
    this.incomechange = incomechange;
  }

  public void setIncometotal(String incometotal) {
    this.incometotal = incometotal;
  }

  public void setIncometotalchange(String incometotalchange) {
    this.incometotalchange = incometotalchange;
  }

  public void setInsurance(String insurance) {
    this.insurance = insurance;
  }

  public void setInsurancechange(String insurancechange) {
    this.insurancechange = insurancechange;
  }

  public void setMaintenance(String maintenance) {
    this.maintenance = maintenance;
  }

  public void setMaintenancechange(String maintenancechange) {
    this.maintenancechange = maintenancechange;
  }

  public void setMateincome(String mateincome) {
    this.mateincome = mateincome;
  }

  public void setMateincomechange(String mateincomechange) {
    this.mateincomechange = mateincomechange;
  }

  public void setMedical(String medical) {
    this.medical = medical;
  }

  public void setMedicalchange(String medicalchange) {
    this.medicalchange = medicalchange;
  }

  public void setRent(String rent) {
    this.rent = rent;
  }

  public void setRentchange(String rentchange) {
    this.rentchange = rentchange;
  }

  public void setRentexpense(String rentexpense) {
    this.rentexpense = rentexpense;
  }

  public void setRentexpensechange(String rentexpensechange) {
    this.rentexpensechange = rentexpensechange;
  }

  public void setSortdividend(String sortdividend) {
    this.sortdividend = sortdividend;
  }

  public void setSortdividendchange(String sortdividendchange) {
    this.sortdividendchange = sortdividendchange;
  }

  public void setTravel(String travel) {
    this.travel = travel;
  }

  public void setTravelchange(String travelchange) {
    this.travelchange = travelchange;
  }

  public void setType(int type) {
    this.type = type;
  }

  public void setUnfixedexpense(String unfixedexpense) {
    this.unfixedexpense = unfixedexpense;
  }

  public void setUnfixedexpensechange(String unfixedexpensechange) {
    this.unfixedexpensechange = unfixedexpensechange;
  }

  public void setUnfixedincome(String unfixedincome) {
    this.unfixedincome = unfixedincome;
  }

  public void setUnfixedincomechange(String unfixedincomechange) {
    this.unfixedincomechange = unfixedincomechange;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

}
