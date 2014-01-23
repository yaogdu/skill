package com.isoftstone.smart.kingstone.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.isoftstone.smart.core.entity.BaseEntity;

@Table(name = "cashconfig")
@Entity
public class CashConfig extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -2157847867134718274L;

  private String configtype;

  private String vendor;

  private String producttype;

  private String rate;

  private String bonus;

  private String returntime;

  private String configsample;

  private Timestamp generatetime;

  private Timestamp updatetime;

  public String getBonus() {
    return bonus;
  }

  public String getConfigsample() {
    return configsample;
  }

  public String getConfigtype() {
    return configtype;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getProducttype() {
    return producttype;
  }

  public String getRate() {
    return rate;
  }

  public String getReturntime() {
    return returntime;
  }

  public Timestamp getUpdatetime() {
    return updatetime;
  }

  public String getVendor() {
    return vendor;
  }

  public void setBonus(String bonus) {
    this.bonus = bonus;
  }

  public void setConfigsample(String configsample) {
    this.configsample = configsample;
  }

  public void setConfigtype(String configtype) {
    this.configtype = configtype;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setProducttype(String producttype) {
    this.producttype = producttype;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public void setReturntime(String returntime) {
    this.returntime = returntime;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

}
