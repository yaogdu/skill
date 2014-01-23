package com.isoftstone.smart.core.entity;

public enum AccountStatus {
  ACTIVE, // 验证通过的有效用户
  INACTIVE, // 账户已停用
  NEW // 等待邮箱验证的新注册用户;
}