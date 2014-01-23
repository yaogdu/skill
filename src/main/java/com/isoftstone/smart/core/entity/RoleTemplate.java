package com.isoftstone.smart.core.entity;

public interface RoleTemplate<T extends Action> {

  T[] getActions();

}
