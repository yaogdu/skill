package com.isoftstone.smart.core.entity;

public enum PlaceAction implements Action {

  // 查看
  READ,

  // 查看详情
  READ_DETAIL,

  // 添加
  ADD,

  // 移动、永久刪除等其他管理员操作
  ADMIN,

  // 删除任何内容
  DELETE,

  // 删除我添加的记录
  DELETE_OWN,

  // 修改任何内容
  EDIT,

  // 修改我添加的内容
  EDIT_OWN,

  // 批准、拒绝、隔离内容
  MODERATE,

  // 创建子空间
  ADD_CHILD,

  // 申请加入
  JOIN,

  // 邀请其他人
  INVITE,

  // 直接授权
  GRANT;

}