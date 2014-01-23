package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.persist.Transactional;
import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.GroupMember;

public class GroupMemberService extends BaseEntityService<GroupMember> {

  @Transactional
  public int delgm(String groupid) {
    int total = 0;
    try {
      Query query = em().createNativeQuery("delete from groupmember where groupid=?1");
      query.setParameter(1, groupid);
      total = query.executeUpdate();
    } catch (Exception e) {
      total = -1;
    }

    return total;
  }

  @Override
  public Class<GroupMember> getEntityType() {
    // TODO Auto-generated method stub
    return GroupMember.class;
  }

  @SuppressWarnings("unchecked")
  public List<GroupMember> listByGroupid(String groupid) {
    List<GroupMember> gms = null;
    try {
      Query query = em().createQuery("select gm from GroupMember gm left join fetch gm.member where gm.group.id=?1", GroupMember.class);
      query.setParameter(1, groupid);
      gms = query.getResultList();
    } catch (Exception e) {
      gms = null;
    }

    return gms;
  }
}
