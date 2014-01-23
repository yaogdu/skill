package com.isoftstone.smart.core.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public class BaseMember<T extends Content> extends Member<T> {

  @OneToOne
  private T content;

  @Override
  public T getContent() {
    return content;
  }

  @Override
  public void setContent(T content) {
    this.content = content;
  }
}
