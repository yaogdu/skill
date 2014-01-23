package com.isoftstone.smart.core.entity;

import java.util.Set;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface Role {

  Set<Action> getAllowableActions();

  String getId();

}
