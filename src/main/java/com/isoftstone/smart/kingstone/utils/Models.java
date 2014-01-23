package com.isoftstone.smart.kingstone.utils;

import java.util.List;

import com.isoftstone.smart.kingstone.entity.AdjustAsset;
import com.isoftstone.smart.kingstone.entity.AdviceInvest;
import com.isoftstone.smart.kingstone.entity.Balance;
import com.isoftstone.smart.kingstone.entity.CashConfig;
import com.isoftstone.smart.kingstone.entity.ConsultantLocation;
import com.isoftstone.smart.kingstone.entity.Customer;
import com.isoftstone.smart.kingstone.entity.Doc;
import com.isoftstone.smart.kingstone.entity.Education;
import com.isoftstone.smart.kingstone.entity.Income;
import com.isoftstone.smart.kingstone.entity.Insurance;
import com.isoftstone.smart.kingstone.entity.Investgroup;
import com.isoftstone.smart.kingstone.entity.Investment;
import com.isoftstone.smart.kingstone.entity.Pension;
import com.isoftstone.smart.kingstone.entity.Risk;

public class Models {

  private List<AdviceInvest> adviceinvests;

  private List<Doc> docs;

  private List<AdjustAsset> adjustassets;

  private List<Balance> balances;

  private List<CashConfig> cashconfigs;

  private List<ConsultantLocation> consultantlocations;

  private List<Customer> customers;

  private List<Education> educations;

  private List<Income> incomes;

  private List<Insurance> insurances;

  private List<Investgroup> investgroups;

  private List<Investment> investments;

  private List<Pension> pensions;

  private List<Risk> risks;

  public List<AdjustAsset> getAdjustassets() {
    return adjustassets;
  }

  public List<AdviceInvest> getAdviceinvests() {
    return adviceinvests;
  }

  public List<Balance> getBalances() {
    return balances;
  }

  public List<CashConfig> getCashconfigs() {
    return cashconfigs;
  }

  public List<ConsultantLocation> getConsultantlocations() {
    return consultantlocations;
  }

  public List<Customer> getCustomers() {
    return customers;
  }

  public List<Doc> getDocs() {
    return docs;
  }

  public List<Education> getEducations() {
    return educations;
  }

  public List<Income> getIncomes() {
    return incomes;
  }

  public List<Insurance> getInsurances() {
    return insurances;
  }

  public List<Investgroup> getInvestgroups() {
    return investgroups;
  }

  public List<Investment> getInvestments() {
    return investments;
  }

  public List<Pension> getPensions() {
    return pensions;
  }

  public List<Risk> getRisks() {
    return risks;
  }

  public void setAdjustassets(List<AdjustAsset> adjustassets) {
    this.adjustassets = adjustassets;
  }

  public void setAdviceinvests(List<AdviceInvest> adviceinvests) {
    this.adviceinvests = adviceinvests;
  }

  public void setBalances(List<Balance> balances) {
    this.balances = balances;
  }

  public void setCashconfigs(List<CashConfig> cashconfigs) {
    this.cashconfigs = cashconfigs;
  }

  public void setConsultantlocations(List<ConsultantLocation> consultantlocations) {
    this.consultantlocations = consultantlocations;
  }

  public void setCustomers(List<Customer> customers) {
    this.customers = customers;
  }

  public void setDocs(List<Doc> docs) {
    this.docs = docs;
  }

  public void setEducations(List<Education> educations) {
    this.educations = educations;
  }

  public void setIncomes(List<Income> incomes) {
    this.incomes = incomes;
  }

  public void setInsurances(List<Insurance> insurances) {
    this.insurances = insurances;
  }

  public void setInvestgroups(List<Investgroup> investgroups) {
    this.investgroups = investgroups;
  }

  public void setInvestments(List<Investment> investments) {
    this.investments = investments;
  }

  public void setPensions(List<Pension> pensions) {
    this.pensions = pensions;
  }

  public void setRisks(List<Risk> risks) {
    this.risks = risks;
  }

}
