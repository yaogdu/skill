package com.isoftstone.smart.kingstone.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.isoftstone.smart.core.entity.BaseEntity;

@Entity
@Table(name = "investgroup")
public class Investgroup extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1955556637206069767L;

  private int level;// 级别 level smallint 0:低,1:中,2:高
  private String fundname;// 基金名称 fundname varchar
  private double percentage;// 比例 percentage double
  private String fundtype;// 基金类别 fundtype varchar
  private Timestamp generatetime;// 生成时间 generatetime timestamp
  private Timestamp updatetime;// 更新时间 updatetime timestamp

  private String picpath;

  private String memo;

  public String getFundname() {
    return fundname;
  }

  public String getFundtype() {
    return fundtype;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public int getLevel() {
    return level;
  }

  public String getMemo() {
    return memo;
  }

  public double getPercentage() {
    return percentage;
  }

  public String getPicpath() {
    return picpath;
  }

  public Timestamp getUpdatetime() {
    return updatetime;
  }

  public void setFundname(String fundname) {
    this.fundname = fundname;
  }

  public void setFundtype(String fundtype) {
    this.fundtype = fundtype;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public void setPercentage(double percentage) {
    this.percentage = percentage;
  }

  public void setPicpath(String picpath) {
    this.picpath = picpath;
  }

  public void setUpdatetime(Timestamp updatetime) {
    this.updatetime = updatetime;
  }

}
