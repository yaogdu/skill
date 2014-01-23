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
@Table(name = "balance")
public class Balance extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1520531452892793533L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerid")
  private Customer customer;
  private String cash;// 现金： cash float
  private String unfixedterm;// 活期存款： unfixedterm float
  private String fixedterm;// 定期存款： fixedterm float
  private String stock;// 股票： stock int
  private String fund;// 基金： fund float
  private String bonds;// 债券： bonds float
  private String estate;// 房地产： estate float
  private String otherasset;// 其它： otherasset float
  private String housefund;// 房屋公积金贷款： housefund float
  private String housefundyear;// 年期 housefundyear int
  private String housecommerce;// 房屋商业贷款： housecommerce float
  private String housecommerceyear;// 年期 housecommerceyear int
  private String car;// 汽车贷款： car float
  private String caryear;// 年期 caryear int
  private String consume;// 消费贷款: consume float
  private String consumeyear;// 年期 consumeyear int
  private String decoration;// 装修贷款： decoration float
  private String decorationyear;// 年期 decorationyear int
  private String folk;// 民间贷款： folk float
  private String folkyear;// 年期 folkyear int
  private String creditcard;// 信用卡未付款： creditcard float
  private String creditcardyear;// 年期 creditcardyear int
  private String otherloan;// 其它： otherloan float
  private String otherloanyear;// 年期 otherloanyear int
  private String assettotal;// 资产总计 assettotal float
  private String loantotal;// 负债总计 loantotal float
  private String total;// 净值 total float
  private String loantotalyear;
  private Timestamp updatetime;

  private Timestamp generatetime;// 生成时间 generatetime timestamp

  private String cashincome;

  private String unfixedtermincome;

  private String fixedtermincome;

  private String stockincome;

  private String fundincome;

  private String bondsincome;

  private String estateincome;

  private String otherassetincome;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "docid")
  private Doc doc;

  @Transient
  private String customerid;

  @Transient
  private String docid;

  public String getAssettotal() {
    return assettotal;
  }

  public String getBonds() {
    return bonds;
  }

  public String getBondsincome() {
    return bondsincome;
  }

  public String getCar() {
    return car;
  }

  public String getCaryear() {
    return caryear;
  }

  public String getCash() {
    return cash;
  }

  public String getCashincome() {
    return cashincome;
  }

  public String getConsume() {
    return consume;
  }

  public String getConsumeyear() {
    return consumeyear;
  }

  public String getCreditcard() {
    return creditcard;
  }

  public String getCreditcardyear() {
    return creditcardyear;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getCustomerid() {
    return customerid;
  }

  public String getDecoration() {
    return decoration;
  }

  public String getDecorationyear() {
    return decorationyear;
  }

  public Doc getDoc() {
    return doc;
  }

  public String getDocid() {
    return docid;
  }

  public String getEstate() {
    return estate;
  }

  public String getEstateincome() {
    return estateincome;
  }

  public String getFixedterm() {
    return fixedterm;
  }

  public String getFixedtermincome() {
    return fixedtermincome;
  }

  public String getFolk() {
    return folk;
  }

  public String getFolkyear() {
    return folkyear;
  }

  public String getFund() {
    return fund;
  }

  public String getFundincome() {
    return fundincome;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getHousecommerce() {
    return housecommerce;
  }

  public String getHousecommerceyear() {
    return housecommerceyear;
  }

  public String getHousefund() {
    return housefund;
  }

  public String getHousefundyear() {
    return housefundyear;
  }

  public String getLoantotal() {
    return loantotal;
  }

  public String getLoantotalyear() {
    return loantotalyear;
  }

  public String getOtherasset() {
    return otherasset;
  }

  public String getOtherassetincome() {
    return otherassetincome;
  }

  public String getOtherloan() {
    return otherloan;
  }

  public String getOtherloanyear() {
    return otherloanyear;
  }

  public String getStock() {
    return stock;
  }

  public String getStockincome() {
    return stockincome;
  }

  public String getTotal() {
    return total;
  }

  public String getUnfixedterm() {
    return unfixedterm;
  }

  public String getUnfixedtermincome() {
    return unfixedtermincome;
  }

  public Timestamp getUpdatetime() {
    return updatetime;
  }

  public void setAssettotal(String assettotal) {
    this.assettotal = assettotal;
  }

  public void setBonds(String bonds) {
    this.bonds = bonds;
  }

  public void setBondsincome(String bondsincome) {
    this.bondsincome = bondsincome;
  }

  public void setCar(String car) {
    this.car = car;
  }

  public void setCaryear(String caryear) {
    this.caryear = caryear;
  }

  public void setCash(String cash) {
    this.cash = cash;
  }

  public void setCashincome(String cashincome) {
    this.cashincome = cashincome;
  }

  public void setConsume(String consume) {
    this.consume = consume;
  }

  public void setConsumeyear(String consumeyear) {
    this.consumeyear = consumeyear;
  }

  public void setCreditcard(String creditcard) {
    this.creditcard = creditcard;
  }

  public void setCreditcardyear(String creditcardyear) {
    this.creditcardyear = creditcardyear;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setCustomerid(String customerid) {
    this.customerid = customerid;
  }

  public void setDecoration(String decoration) {
    this.decoration = decoration;
  }

  public void setDecorationyear(String decorationyear) {
    this.decorationyear = decorationyear;
  }

  public void setDoc(Doc doc) {
    this.doc = doc;
  }

  public void setDocid(String docid) {
    this.docid = docid;
  }

  public void setEstate(String estate) {
    this.estate = estate;
  }

  public void setEstateincome(String estateincome) {
    this.estateincome = estateincome;
  }

  public void setFixedterm(String fixedterm) {
    this.fixedterm = fixedterm;
  }

  public void setFixedtermincome(String fixedtermincome) {
    this.fixedtermincome = fixedtermincome;
  }

  public void setFolk(String folk) {
    this.folk = folk;
  }

  public void setFolkyear(String folkyear) {
    this.folkyear = folkyear;
  }

  public void setFund(String fund) {
    this.fund = fund;
  }

  public void setFundincome(String fundincome) {
    this.fundincome = fundincome;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setHousecommerce(String housecommerce) {
    this.housecommerce = housecommerce;
  }

  public void setHousecommerceyear(String housecommerceyear) {
    this.housecommerceyear = housecommerceyear;
  }

  public void setHousefund(String housefund) {
    this.housefund = housefund;
  }

  public void setHousefundyear(String housefundyear) {
    this.housefundyear = housefundyear;
  }

  public void setLoantotal(String loantotal) {
    this.loantotal = loantotal;
  }

  public void setLoantotalyear(String loantotalyear) {
    this.loantotalyear = loantotalyear;
  }

  public void setOtherasset(String otherasset) {
    this.otherasset = otherasset;
  }

  public void setOtherassetincome(String otherassetincome) {
    this.otherassetincome = otherassetincome;
  }

  public void setOtherloan(String otherloan) {
    this.otherloan = otherloan;
  }

  public void setOtherloanyear(String otherloanyear) {
    this.otherloanyear = otherloanyear;
  }

  public void setStock(String stock) {
    this.stock = stock;
  }

  public void setStockincome(String stockincome) {
    this.stockincome = stockincome;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public void setUnfixedterm(String unfixedterm) {
    this.unfixedterm = unfixedterm;
  }

  public void setUnfixedtermincome(String unfixedtermincome) {
    this.unfixedtermincome = unfixedtermincome;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

}
