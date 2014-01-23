package com.isoftstone.smart.core.entity;

import java.util.ArrayList;
import java.util.List;

public class ErrorResult extends Result<List<Throwable>> {

  public static ErrorResult from(String msg, Throwable... throwables) {
    ErrorResult result = new ErrorResult();
    result.setSuccess(false);
    result.setMessage(msg);
    List<Throwable> data = new ArrayList<Throwable>();
    for (Throwable t : throwables) {
      data.add(t);
    }
    result.setData(data);
    return result;
  }

}
