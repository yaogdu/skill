package com.isoftstone.smart.kingstone.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.isoftstone.smart.core.entity.BaseEntity;

@Entity
@Table(name = "qa")
public class Qa extends BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String question;

  private String answer;

  private Timestamp generatetime;

  private String servanttel;

  private String servantemail;

  public String getAnswer() {
    return answer;
  }

  public Timestamp getGeneratetime() {
    return generatetime;
  }

  public String getQuestion() {
    return question;
  }

  public String getServantemail() {
    return servantemail;
  }

  public String getServanttel() {
    return servanttel;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public void setGeneratetime(Timestamp generatetime) {
    this.generatetime = generatetime;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public void setServantemail(String servantemail) {
    this.servantemail = servantemail;
  }

  public void setServanttel(String servanttel) {
    this.servanttel = servanttel;
  }

}
