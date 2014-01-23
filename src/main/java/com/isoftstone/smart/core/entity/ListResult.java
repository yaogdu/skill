package com.isoftstone.smart.core.entity;

import java.util.ArrayList;
import java.util.List;

public class ListResult<T extends Object> extends Result<List<T>> {

  protected List<String[]> sort = new ArrayList<>();

  public List<String[]> getSort() {
    return sort;
  }

  public int getTotalRows() {
    return getData().size();
  }

  public void setSort(List<String[]> sort) {
    this.sort = sort;
  }

}
