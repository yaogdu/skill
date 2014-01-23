package com.isoftstone.smart.core.service;

import javax.ws.rs.Path;

import com.isoftstone.smart.core.entity.Member;

@Path("members")
public class MemberService extends BaseEntityService<Member> {

  @Override
  public Class<Member> getEntityType() {
    return Member.class;
  }

}
