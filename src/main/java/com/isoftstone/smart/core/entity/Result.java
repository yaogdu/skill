package com.isoftstone.smart.core.entity;

public class Result<T> {

  protected T data;

  protected String message;

  protected boolean success;

  public T getData() {
    return data;
  }

  public String getMessage() {
    return message;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setData(T data) {
    this.data = data;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

}
