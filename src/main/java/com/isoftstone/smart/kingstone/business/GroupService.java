package com.isoftstone.smart.kingstone.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.google.inject.persist.Transactional;
import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Group;

public class GroupService extends BaseEntityService<Group> {

  @SuppressWarnings("unchecked")
  public List<Object[]> clientanalysis_age(String groupid) {
    List<Object[]> age = new ArrayList<Object[]>();
    try {
      // String sql =
      // "select case when cus.gender=0 then '男' when cus.gender=1 then '女' end sex ,count(cus.id) from customer cus group by case when cus.gender=0 then '男' when cus.gender=1 then '女' end ";

      String sql =
          " select sum(case when (year(now())-year(age))<25 then 1 else 0 end), "
              + " sum(case when 25<=(year(now())-year(age)) and (year(now())-year(age))<=30 then 1 else 0 end ) , "
              + " sum(case when  31<=(year(now())-year(age)) and (year(now())-year(age))<=35 then 1 else 0 end ),"
              + " sum(case when  36<=(year(now())-year(age)) and (year(now())-year(age))<=40 then 1 else 0 end),"
              + " sum(case when  41<=(year(now())-year(age)) and (year(now())-year(age))<=45 then 1 else 0 end) ,"
              + " sum(case  when  46<=(year(now())-year(age)) and (year(now())-year(age))<=50 then 1 else 0 end) ,"
              + " sum( case when  (year(now())-year(age))>=50 then 1 else 0 end)   from customer cus ";
      if (!"-1".equals(groupid)) {
        sql =
            " select sum(case when (year(now())-year(age))<25 then 1 else 0 end), "
                + " sum(case when 25<=(year(now())-year(age)) and (year(now())-year(age))<=30 then 1 else 0 end ) , "
                + " sum(case when  31<=(year(now())-year(age)) and (year(now())-year(age))<=35 then 1 else 0 end ),"
                + " sum(case when  36<=(year(now())-year(age)) and (year(now())-year(age))<=40 then 1 else 0 end),"
                + " sum(case when  41<=(year(now())-year(age)) and (year(now())-year(age))<=45 then 1 else 0 end) ,"
                + " sum(case  when  46<=(year(now())-year(age)) and (year(now())-year(age))<=50 then 1 else 0 end) ,"
                + " sum( case when  (year(now())-year(age))>=50 then 1 else 0 end)   from customer cus ,consultant con,groupmember gm where cus.consultantid=con.id "
                + " and con.accountid=gm.accountid AND gm.groupid='" + groupid + "' ";

      }
      Query query = em().createNativeQuery(sql);
      age = query.getResultList();
    } catch (Exception e) {
      age = null;
    }

    return age;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> clientanalysis_age_byleader(String accountid, String groupid) {
    List<Object[]> age = new ArrayList<Object[]>();
    try {
      String sql = "";

      if (!"-1".equals(groupid)) {
        // sql =
        // " select  case when cus.gender=0 then '男' when cus.gender=1 then '女' end sex, "
        // + " count(cus.id) from customer cus where cus.consultantid in "
        // +
        // " (select id from consultant where accountid in (select accountid from groupmember where groupid ='"
        // + groupid + "' "
        // + ")) " + " group by case when cus.gender=0 then '男' when cus.gender=1 then '女' end ";

        sql =
            " select sum(case when (year(now())-year(age))<25 then 1 else 0 end), "
                + " sum(case when 25<=(year(now())-year(age)) and (year(now())-year(age))<=30 then 1 else 0 end ) , "
                + " sum(case when  31<=(year(now())-year(age)) and (year(now())-year(age))<=35 then 1 else 0 end ), "
                + " sum(case when  36<=(year(now())-year(age)) and (year(now())-year(age))<=40 then 1 else 0 end), "
                + " sum(case when  41<=(year(now())-year(age)) and (year(now())-year(age))<=45 then 1 else 0 end) , "
                + " sum(case  when  46<=(year(now())-year(age)) and (year(now())-year(age))<=50 then 1 else 0 end) ,  "
                + " sum( case when  (year(now())-year(age))>=50 then 1 else 0 end)   from customer cus where cus.consultantid in "
                + "  (select id from consultant where accountid in (select accountid from groupmember where groupid ='" + groupid + "' ))";
      } else {
        sql =
            "  select sum(case when (year(now())-year(age))<25 then 1 else 0 end), "
                + " sum(case when 25<=(year(now())-year(age)) and (year(now())-year(age))<=30 then 1 else 0 end ) , "
                + " sum(case when  31<=(year(now())-year(age)) and (year(now())-year(age))<=35 then 1 else 0 end ), "
                + " sum(case when  36<=(year(now())-year(age)) and (year(now())-year(age))<=40 then 1 else 0 end), "
                + " sum(case when  41<=(year(now())-year(age)) and (year(now())-year(age))<=45 then 1 else 0 end) , "
                + " sum(case  when  46<=(year(now())-year(age)) and (year(now())-year(age))<=50 then 1 else 0 end) ,  "
                + " sum( case when  (year(now())-year(age))>=50 then 1 else 0 end)   from customer cus where cus.consultantid in "
                + " (select id from consultant where accountid in " + " (select accountid from groupmember where groupid in "
                + " (select id from groups where accountid='" + accountid + "'))) " + "  ";
      }
      Query query = em().createNativeQuery(sql);
      age = query.getResultList();
    } catch (Exception e) {
      age = null;
    }

    return age;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> clientanalysis_fund(String groupid) {
    List<Object[]> fund = new ArrayList<Object[]>();
    try {
      // String sql =
      // "select case when cus.gender=0 then '男' when cus.gender=1 then '女' end sex ,count(cus.id) from customer cus group by case when cus.gender=0 then '男' when cus.gender=1 then '女' end ";

      String sql =
          "select sum(case when b.total<1000000 then 1 else 0 end), "
              + " sum(case when 1000000<=b.total and b.total<5000000 then 1 else 0 end ) , "
              + " sum(case when 5000000<=b.total and b.total<10000000 then 1 else 0 end ) , "
              + " sum(case when 10000000<=b.total and b.total<50000000 then 1 else 0 end ) , "
              + " sum(case when 50000000<=b.total then 1 else 0 end )  from balance b ,customer cus where b.customerid  = cus.id ";
      if (!"-1".equals(groupid)) {
        sql =
            "select sum(case when b.total<1000000 then 1 else 0 end), "
                + " sum(case when 1000000<=b.total and b.total<5000000 then 1 else 0 end ) , "
                + " sum(case when 5000000<=b.total and b.total<10000000 then 1 else 0 end ) , "
                + " sum(case when 10000000<=b.total and b.total<50000000 then 1 else 0 end ) , "
                + " sum(case when 50000000<=b.total then 1 else 0 end )  from balance b ,customer cus  ,consultant con,groupmember gm where b.customerid=cus.id and cus.consultantid=con.id "
                + " and con.accountid=gm.accountid AND gm.groupid='" + groupid + "' ";

      }
      Query query = em().createNativeQuery(sql);
      fund = query.getResultList();
    } catch (Exception e) {
      fund = null;
    }

    return fund;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> clientanalysis_fund_byleader(String accountid, String groupid) {
    List<Object[]> fund = new ArrayList<Object[]>();
    try {
      String sql = "";

      if (!"-1".equals(groupid)) {
        // sql =
        // " select  case when cus.gender=0 then '男' when cus.gender=1 then '女' end sex, "
        // + " count(cus.id) from customer cus where cus.consultantid in "
        // +
        // " (select id from consultant where accountid in (select accountid from groupmember where groupid ='"
        // + groupid + "' "
        // + ")) " + " group by case when cus.gender=0 then '男' when cus.gender=1 then '女' end ";

        sql =
            " select sum(case when b.total<1000000 then 1 else 0 end), "
                + " sum(case when 1000000<=b.total and b.total<5000000 then 1 else 0 end ) , "
                + " sum(case when 5000000<=b.total and b.total<10000000 then 1 else 0 end ) , "
                + " sum(case when 10000000<=b.total and b.total<50000000 then 1 else 0 end ) , "
                + " sum(case when 50000000<=b.total then 1 else 0 end )  from balance b ,customer cus where b.customerid  = cus.id and cus.consultantid in "
                + "  (select id from consultant where accountid in (select accountid from groupmember where groupid ='" + groupid + "' ))";
      } else {
        sql =
            "  select sum(case when b.total<1000000 then 1 else 0 end), "
                + " sum(case when 1000000<=b.total and b.total<5000000 then 1 else 0 end ) , "
                + " sum(case when 5000000<=b.total and b.total<10000000 then 1 else 0 end ) , "
                + " sum(case when 10000000<=b.total and b.total<50000000 then 1 else 0 end ) , "
                + " sum(case when 50000000<=b.total then 1 else 0 end )  from balance b ,customer cus where b.customerid  = cus.id and cus.consultantid in "
                + " (select id from consultant where accountid in " + " (select accountid from groupmember where groupid in "
                + " (select id from groups where accountid='" + accountid + "'))) " + "  ";
      }
      Query query = em().createNativeQuery(sql);
      fund = query.getResultList();
    } catch (Exception e) {
      fund = null;
    }

    return fund;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> clientanalysis_jobs(String groupid) {
    List<Object[]> jobs = new ArrayList<Object[]>();
    try {
      // String sql =
      // "select case when cus.gender=0 then '男' when cus.gender=1 then '女' end sex ,count(cus.id) from customer cus group by case when cus.gender=0 then '男' when cus.gender=1 then '女' end ";

      String sql = "select cus.job,count(cus.id) from customer cus   group by cus.job ";
      if (!"-1".equals(groupid)) {
        sql =
            " select cus.job,count(cus.id) from  customer cus   "
                + " where cus.consultantid in(select id from consultant where accountid in "
                + " (select accountid from groupmember where groupid='" + groupid + "')) group by jc.id,jc.name ";

      }
      Query query = em().createNativeQuery(sql);
      jobs = query.getResultList();
    } catch (Exception e) {
      jobs = null;
    }

    return jobs;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> clientanalysis_jobs_byleader(String accountid, String groupid) {
    List<Object[]> jobs = new ArrayList<Object[]>();
    try {
      String sql = "";

      if (!"-1".equals(groupid)) {

        sql =
            " select cus.job,count(cus.id) from  customer cus   "
                + " where cus.consultantid in(select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "')) " + " group by jc.id,jc.name ";
      } else {
        sql =
            " select cus.job,count(cus.id) from customer cus   "
                + " where cus.consultantid in(select id from consultant where accountid in (select accountid from groupmember where groupid "
                + " in (select id from groups where accountid ='" + accountid + "'))) group by jc.id,jc.name ";
      }
      Query query = em().createNativeQuery(sql);
      jobs = query.getResultList();
    } catch (Exception e) {
      jobs = null;
    }

    return jobs;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> clientanalysis_sexual(String groupid) {
    List<Object[]> sexuals = new ArrayList<Object[]>();
    try {
      // String sql =
      // "select case when cus.gender=0 then '男' when cus.gender=1 then '女' end sex ,count(cus.id) from customer cus group by case when cus.gender=0 then '男' when cus.gender=1 then '女' end ";

      String sql =
          "select sum(case when cus.gender=0 then 1 else 0 end) men ,sum(case when cus.gender=1 then 1 else 0 end) women "
              + "from customer cus ";
      if (!"-1".equals(groupid)) {
        // sql =
        // "select SUM(case when cus.gender =0 then 1 else 0 end) '男',SUM(case when cus.gender=1 then 1 else 0 end) '女' "
        // +
        // " from customer cus,consultant con,groupmember gm where cus.consultantid=con.id and con.accountid=gm.accountid "
        // + " AND gm.groupid='" + groupid + "'";
        // sql =
        // "select case when cus.gender=0 then '男' when cus.gender=1 then '女' end sex,count(cus.id) "
        //
        // + " from customer cus,consultant con,groupmember gm where cus.consultantid=con.id "
        // + " and con.accountid=gm.accountid AND gm.groupid='" + groupid + "' "
        // + " group by case when cus.gender=0 then '男' when cus.gender=1 then '女' end";

        sql =
            "select sum(case when cus.gender=0 then 1 else 0 end) men, sum(case when cus.gender=1 then 1 else 0 end) women "

            + " from customer cus,consultant con,groupmember gm where cus.consultantid=con.id "
                + " and con.accountid=gm.accountid AND gm.groupid='" + groupid + "' ";
      }
      Query query = em().createNativeQuery(sql);
      sexuals = query.getResultList();
    } catch (Exception e) {
      sexuals = null;
    }

    return sexuals;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> clientanalysis_sexual_byleader(String accountid, String groupid) {
    List<Object[]> sexuals = new ArrayList<Object[]>();
    try {
      String sql = "";

      if (!"-1".equals(groupid)) {
        // sql =
        // " select  case when cus.gender=0 then '男' when cus.gender=1 then '女' end sex, "
        // + " count(cus.id) from customer cus where cus.consultantid in "
        // +
        // " (select id from consultant where accountid in (select accountid from groupmember where groupid ='"
        // + groupid + "' "
        // + ")) " + " group by case when cus.gender=0 then '男' when cus.gender=1 then '女' end ";

        sql =
            " select  sum(case when cus.gender=0 then 1 else 0 end) men, sum(case when cus.gender=1 then 1 else 0 end) women  "
                + " from customer cus where cus.consultantid in "
                + "  (select id from consultant where accountid in (select accountid from groupmember where groupid ='" + groupid + "' ))";
      } else {
        sql =
            " select  sum(case when cus.gender=0 then 1 else 0 end) men, sum(case when cus.gender=1 then 1 else 0 end) women "
                + " from customer cus where cus.consultantid in " + " (select id from consultant where accountid in "
                + " (select accountid from groupmember where groupid in " + " (select id from groups where accountid='" + accountid
                + "'))) " + "  ";
      }
      Query query = em().createNativeQuery(sql);
      sexuals = query.getResultList();
    } catch (Exception e) {
      sexuals = null;
    }

    return sexuals;
  }

  @Transactional
  public int deletebyid(String groupid) {
    int total = 0;
    try {
      Query query = em().createNativeQuery("delete from groups where id=?1");
      query.setParameter(1, groupid);
      total = query.executeUpdate();
    } catch (Exception e) {
      total = -1;
    }
    return total;
  }

  @Override
  public Class<Group> getEntityType() {
    // TODO Auto-generated method stub
    return Group.class;
  }

  @SuppressWarnings("unchecked")
  public List<Group> getGroupByLeader(String accountid) {
    List<Group> groups = null;
    try {
      String sql = "select gp from Group gp left join fetch gp.leader where gp.leader.id=?1";
      Query query = em().createQuery(sql, Group.class);
      query.setParameter(1, accountid);
      groups = query.getResultList();
    } catch (Exception e) {
      groups = null;
    }

    return groups;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> groupAnalysis() {
    List<Object[]> groups = new ArrayList<Object[]>();
    try {
      String sql =
          "select g.name,count(cus.id) membercount from customer cus,consultant con,groupmember gm,groups g  "
              + " where cus.consultantid=con.id and con.accountid=gm.accountid and gm.groupid=g.id  group by g.id ";
      Query query = em().createNativeQuery(sql);
      groups = query.getResultList();
    } catch (Exception e) {
      groups = null;
    }

    return groups;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> groupAnalysisbyleader(String accountid) {
    List<Object[]> groups = new ArrayList<Object[]>();
    try {
      String sql =
          "select g.name,count(cus.id) membercount from customer cus,consultant con,groupmember gm,groups g  "
              + " where cus.consultantid=con.id and con.accountid=gm.accountid and gm.groupid=g.id and g.accountid=?1 group by g.id ";
      Query query = em().createNativeQuery(sql);
      query.setParameter(1, accountid);
      groups = query.getResultList();
    } catch (Exception e) {
      groups = null;
    }

    return groups;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> groupcustomer() {
    List<Object[]> groups = new ArrayList<Object[]>();
    try {
      String sql =
          " select * from (select g.id id1,g.name name1, count(cus.id) total  "
              + "   from groups g left join groupmember gm on (g.id=gm.groupid) "
              + "  left join consultant con on(gm.accountid=con.accountid) left join customer cus on (con.id=cus.consultantid ) "
              + "group by g.id,g.name   ) as aa left join  "

              + " (select g.id id2,g.name name2,  count(cust.id) complete  "
              + "   from groups g left join groupmember gm on (g.id=gm.groupid)  "
              + "   left join consultant con on(gm.accountid=con.accountid)  "
              + "    left join customer cust on(con.id=cust.consultantid and cust.done=1) group by g.id,g.name  "
              + "  )  as bb on (aa.id1=bb.id2 ) left join  (select g.id id3,g.name name3,   count(cl.id) visittotal "
              + "   from groups g left join groupmember gm on (g.id=gm.groupid)  "
              + "  left join consultant con on(gm.accountid=con.accountid)  "
              + " left join consultantlocation cl on (con.id=cl.consultantid)  group by g.id,g.name )as cc  on (bb.id2=cc.id3 )";
      Query query = em().createNativeQuery(sql);
      groups = query.getResultList();
    } catch (Exception e) {
      groups = null;
    }

    return groups;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> groupcustomerbyleader(String accountid) {
    List<Object[]> groups = new ArrayList<Object[]>();
    try {
      String sql =
          " select * from (select g.id id1,g.name name1, count(cus.id) total  "
              + "   from groups g left join groupmember gm on (g.id=gm.groupid) "
              + "  left join consultant con on(gm.accountid=con.accountid) left join customer cus on (con.id=cus.consultantid ) where g.accountid = ?1 "
              + "group by g.id,g.name   ) as aa left join  "

              + " (select g.id id2,g.name name2,  count(cust.id) complete  "
              + "   from groups g left join groupmember gm on (g.id=gm.groupid)  "
              + "   left join consultant con on(gm.accountid=con.accountid)  "
              + "    left join customer cust on(con.id=cust.consultantid and cust.done=1) where g.accountid = ?1 group by g.id,g.name  "
              + "  )  as bb on (aa.id1=bb.id2 ) left join  (select g.id id3,g.name name3,   count(cl.id) visittotal "
              + "   from groups g left join groupmember gm on (g.id=gm.groupid)  "
              + "  left join consultant con on(gm.accountid=con.accountid)  "
              + " left join consultantlocation cl on (con.id=cl.consultantid) where g.accountid = ?1 group by g.id,g.name )as cc  on (bb.id2=cc.id3 )";
      Query query = em().createNativeQuery(sql);
      query.setParameter(1, accountid);
      groups = query.getResultList();
    } catch (Exception e) {
      groups = null;
    }

    return groups;
  }

  @SuppressWarnings("unchecked")
  public List<Group> groups() {
    List<Group> groups = null;
    try {
      Query query = em().createQuery("select gp from Group gp left join fetch gp.leader", Group.class);
      groups = query.getResultList();
    } catch (Exception e) {
      groups = null;
    }
    return groups;
  }

  public Group groupwithleader(String groupid) {
    Group group = null;
    try {
      Query query = em().createQuery("select gp from Group gp left join fetch gp.leader where gp.id=?1", Group.class);
      query.setParameter(1, groupid);
      group = (Group) query.getSingleResult();
    } catch (Exception e) {
      group = null;
    }

    return group;
  }
}
