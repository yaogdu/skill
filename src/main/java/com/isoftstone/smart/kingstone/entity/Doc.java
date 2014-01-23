package com.isoftstone.smart.kingstone.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.isoftstone.smart.core.entity.BaseEntity;

@Entity
@Table(name = "doc")
public class Doc extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 9121007223116138315L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerid")
  private Customer customer;// 拥有者 customerid varchar

  @Transient
  private String customerid;

  private String educationstate; // 教育金

  private String stableinvest;// 教育金

  private String fundmove;// 教育金

  @Transient
  private String consultantid;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "consultantid")
  private Consultant consultant;// 顾问 consultantid varchar

  private String path;// 路径 path varchar

  private String docname;// 文件名 docname varchar

  private Timestamp generatetime;// 生成时间 generatetime timestamp

  private int expired;// 0 是 1 否

  private Timestamp updatetime;

  private String investmoney;

  private String fixedstate;// 0 未选择 1 已选择 定期定额描述

  private String yale;// 0 未选择 1 已选择 耶鲁五分法

  private String otheryale;// 其它

  private String investyear;

  private String cashbackup;// 备用金现状 cashbackup varchar

  private String cashbackupmemo;

  private String protectproduct;// 保障品现状 protectproduct varchar

  private String protectproductmemo;

  private String investgoods;// 投资品现状 investgoods varchar

  private String investgoodsmemo;

  private String housing;// 不动产配置现状 housing varchar

  private String housingmemo;

  private String balance;// 结余情况 balance varchar

  private String balancememo;

  private String cashplanning;// 现金流综述及规划 cashplanning varchar

  private String riskmanage;// 风险管理 riskmanage varchar

  private String investplanning;// 投资规划 investplanning varchar

  private String cashconfigid;

  private String educationplanning;// 教育金 educationplanning varchar

  private String financetarget;// 理财目标

  private String riskstate;// 风险综述 line 146

  private String cashconfigstate;// 现金配置

  private String country;

  private String inflation;// 通货膨胀率

  private String tuitionrate;// 学费增长率

  @Transient
  private Set<CashConfig> cashconfigs;

  @Transient
  private Balance bal;

  @Transient
  private List<Education> edus;

  @Transient
  private Income income;

  @Transient
  private Income adjustIncome;

  @Transient
  private List<Insurance> insurances;

  @Transient
  private List<Insurance> adjustinsurances;

  @Transient
  private List<Investgroup> investgroups;
  @Transient
  private List<AdviceInvest> adviceinvests;
  @Transient
  private List<Investment> investments;

  @Transient
  private Pension pension;
  public Income getAdjustIncome() {
    return adjustIncome;
  }

  public List<Insurance> getAdjustinsurances() {
    return adjustinsurances;
  }

  public List<AdviceInvest> getAdviceinvests() {
    return adviceinvests;
  }

  public Balance getBal() {
    return bal;
  }

  public String getBalance() {
    return balance;
  }

  public String getBalancememo() {
    return balancememo;
  }

  public String getCashbackup() {
    return cashbackup;
  }

  public String getCashbackupmemo() {
    return cashbackupmemo;
  }

  public String getCashconfigid() {
    return cashconfigid;
  }

  public Set<CashConfig> getCashconfigs() {
    return cashconfigs;
  }

  public String getCashconfigstate() {
    return cashconfigstate;
  }

  public String getCashplanning() {
    return cashplanning;
  }

  public Consultant getConsultant() {
    return consultant;
  }

  public String getConsultantid() {
    return consultantid;
  }

  public String getCountry() {
    return country;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getCustomerid() {
    return customerid;
  }

  public String getDocname() {
    return docname;
  }

  public String getEducationplanning() {
    return educationplanning;
  }

  public String getEducationstate() {
    return educationstate;
  }

  public List<Education> getEdus() {
    return edus;
  }

  public int getExpired() {
    return expired;
  }

  public String getFinancetarget() {
    return financetarget;
  }

  public String getFixedstate() {
    return fixedstate;
  }

  public String getFundmove() {
    return fundmove;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getHousing() {
    return housing;
  }

  public String getHousingmemo() {
    return housingmemo;
  }

  public Income getIncome() {
    return income;
  }

  public String getInflation() {
    return inflation;
  }

  public List<Insurance> getInsurances() {
    return insurances;
  }

  public String getInvestgoods() {
    return investgoods;
  }

  public String getInvestgoodsmemo() {
    return investgoodsmemo;
  }

  public List<Investgroup> getInvestgroups() {
    return investgroups;
  }

  public List<Investment> getInvestments() {
    return investments;
  }

  public String getInvestmoney() {
    return investmoney;
  }

  public String getInvestplanning() {
    return investplanning;
  }

  public String getInvestyear() {
    return investyear;
  }

  public String getOtheryale() {
    return otheryale;
  }

  public String getPath() {
    return path;
  }

  public Pension getPension() {
    return pension;
  }

  public String getProtectproduct() {
    return protectproduct;
  }

  public String getProtectproductmemo() {
    return protectproductmemo;
  }

  public String getRiskmanage() {
    return riskmanage;
  }

  public String getRiskstate() {
    return riskstate;
  }

  public String getStableinvest() {
    return stableinvest;
  }

  public String getTuitionrate() {
    return tuitionrate;
  }

  public Timestamp getUpdatetime() {
    return updatetime;
  }

  public String getYale() {
    return yale;
  }

  public void setAdjustIncome(Income adjustIncome) {
    this.adjustIncome = adjustIncome;
  }

  public void setAdjustinsurances(List<Insurance> adjustinsurances) {
    this.adjustinsurances = adjustinsurances;
  }

  public void setAdviceinvests(List<AdviceInvest> adviceinvests) {
    this.adviceinvests = adviceinvests;
  }

  public void setBal(Balance bal) {
    this.bal = bal;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  public void setBalancememo(String balancememo) {
    this.balancememo = balancememo;
  }

  public void setCashbackup(String cashbackup) {
    this.cashbackup = cashbackup;
  }

  public void setCashbackupmemo(String cashbackupmemo) {
    this.cashbackupmemo = cashbackupmemo;
  }

  public void setCashconfigid(String cashconfigid) {
    this.cashconfigid = cashconfigid;
  }

  public void setCashconfigs(Set<CashConfig> cashconfigs) {
    this.cashconfigs = cashconfigs;
  }

  public void setCashconfigstate(String cashconfigstate) {
    this.cashconfigstate = cashconfigstate;
  }

  public void setCashplanning(String cashplanning) {
    this.cashplanning = cashplanning;
  }

  public void setConsultant(Consultant consultant) {
    this.consultant = consultant;
  }

  public void setConsultantid(String consultantid) {
    this.consultantid = consultantid;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setCustomerid(String customerid) {
    this.customerid = customerid;
  }

  public void setDocname(String docname) {
    this.docname = docname;
  }

  public void setEducationplanning(String educationplanning) {
    this.educationplanning = educationplanning;
  }

  public void setEducationstate(String educationstate) {
    this.educationstate = educationstate;
  }

  public void setEdus(List<Education> edus) {
    this.edus = edus;
  }

  public void setExpired(int expired) {
    this.expired = expired;
  }

  public void setFinancetarget(String financetarget) {
    this.financetarget = financetarget;
  }

  public void setFixedstate(String fixedstate) {
    this.fixedstate = fixedstate;
  }

  public void setFundmove(String fundmove) {
    this.fundmove = fundmove;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setHousing(String housing) {
    this.housing = housing;
  }

  public void setHousingmemo(String housingmemo) {
    this.housingmemo = housingmemo;
  }

  public void setIncome(Income income) {
    this.income = income;
  }

  public void setInflation(String inflation) {
    this.inflation = inflation;
  }

  public void setInsurances(List<Insurance> insurances) {
    this.insurances = insurances;
  }

  public void setInvestgoods(String investgoods) {
    this.investgoods = investgoods;
  }

  public void setInvestgoodsmemo(String investgoodsmemo) {
    this.investgoodsmemo = investgoodsmemo;
  }

  public void setInvestgroups(List<Investgroup> investgroups) {
    this.investgroups = investgroups;
  }

  public void setInvestments(List<Investment> investments) {
    this.investments = investments;
  }

  public void setInvestmoney(String investmoney) {
    this.investmoney = investmoney;
  }

  public void setInvestplanning(String investplanning) {
    this.investplanning = investplanning;
  }

  public void setInvestyear(String investyear) {
    this.investyear = investyear;
  }

  public void setOtheryale(String otheryale) {
    this.otheryale = otheryale;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setPension(Pension pension) {
    this.pension = pension;
  }

  public void setProtectproduct(String protectproduct) {
    this.protectproduct = protectproduct;
  }

  public void setProtectproductmemo(String protectproductmemo) {
    this.protectproductmemo = protectproductmemo;
  }

  public void setRiskmanage(String riskmanage) {
    this.riskmanage = riskmanage;
  }

  public void setRiskstate(String riskstate) {
    this.riskstate = riskstate;
  }

  public void setStableinvest(String stableinvest) {
    this.stableinvest = stableinvest;
  }

  public void setTuitionrate(String tuitionrate) {
    this.tuitionrate = tuitionrate;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

  public void setYale(String yale) {
    this.yale = yale;
  }

}
