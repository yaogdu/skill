package com.isoftstone.smart.kingstone.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Consultant;

public class ConsultantService extends BaseEntityService<Consultant> {

  @SuppressWarnings("unchecked")
  public List<Consultant> chartlist(int pageNo, int pageSize, String name) {
    List<Consultant> cons = null;
    try {

      String sql = "select * from consultant  where 1=1  ";

      if (name != null && !"".equals(name)) {
        sql += " and name like '%" + name + "%' ";
      }
      sql += " order by CONVERT( name USING gbk ) limit ?1,?2";

      Query query = em().createNativeQuery(sql, Consultant.class);

      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      cons = query.getResultList();

    } catch (Exception e) {
      cons = null;
    }

    return cons;
  }

  @SuppressWarnings("unchecked")
  public List<Consultant> chartlistbyleader(int pageNo, int pageSize, String name, String accountid) {
    List<Consultant> cons = null;
    try {
      String sql =
          "select * from consultant where accountid in (select accountid from groupmember where groupid in (select id from groups where accountid = ?1))";

      if (name != null && !"".equals(name)) {
        sql += " and name like '%" + name + "%'";
      }

      sql += " limit ?2,?3";
      Query query = em().createNativeQuery(sql, Consultant.class);
      query.setParameter(1, accountid);
      query.setParameter(2, pageSize * (pageNo - 1));
      query.setParameter(3, pageSize);
      cons = query.getResultList();
    } catch (Exception e) {
      cons = null;
    }

    return cons;
  }

  public int chartlisttotal(String name) {
    int total = 0;
    try {
      String sql = "select count(*) from consultant where 1=1  ";

      if (name != null && !"".equals(name)) {
        sql += " and name like '%" + name + "%' ";
      }
      Query query = em().createNativeQuery(sql);

      total = Integer.parseInt(String.valueOf(query.getSingleResult()));

    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  public int chartlisttotalbyleader(String name, String accountid) {
    int total = 0;
    try {
      String sql =
          "select count(*) from consultant where accountid in (select accountid from groupmember where groupid in (select id from groups where accountid = ?1))";

      if (name != null && !"".equals(name)) {
        sql += " and name like '%" + name + "%'";
      }
      Query query = em().createNativeQuery(sql);
      query.setParameter(1, accountid);
      total = Integer.parseInt(String.valueOf(query.getSingleResult()));

    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> complete(String consultantid, String timefrom, String timeto) {
    List<Object[]> cons = null;
    try {

      String sql =
          "  select con.id,con.name, cast(DATE_FORMAT(cust.completetime,'%Y%m') as char) d2, count(cust.id) complete from consultant con  ";

      if (timefrom != null && !"".equals(timefrom)) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat s = new SimpleDateFormat("yyyyMM");

        String strFrom = s.format(sdf.parse(timefrom));
        String strTo = s.format(sdf.parse(timeto));
        sql +=
            " left join customer cust on (con.id=cust.consultantid and cust.done=1 and DATE_FORMAT(cust.completetime,'%Y%m')>=" + strFrom
                + "     and DATE_FORMAT(cust.completetime,'%Y%m') <=" + strTo + ")";
      } else {
        sql +=
            " left join customer cust on (con.id=cust.consultantid and cust.done=1 and DATE_FORMAT(cust.completetime,'%Y%m')=DATE_FORMAT(now(),'%Y%m')) ";

      }
      sql += " where con.id=?1 ";

      sql +=
          " group by con.id,con.name, cast(DATE_FORMAT(cust.completetime,'%Y%m') as char) order by cast(DATE_FORMAT(cust.completetime,'%Y%m') as char)";

      Query query = em().createNativeQuery(sql);
      query.setParameter(1, consultantid);
      cons = query.getResultList();

    } catch (Exception e) {
      cons = null;
    }

    return cons;

  }

  public Consultant getByAccountId(String accountId) {

    Query query = em().createNativeQuery("select * from consultant where accountid=?1", Consultant.class);
    query.setParameter(1, accountId);
    Consultant con = (Consultant) query.getSingleResult();
    return con;
  }

  public int getByConno(String consultantno) {

    Query query = em().createNativeQuery("select count(*) from consultant where consultantno=?1");
    query.setParameter(1, consultantno);

    return Integer.parseInt(String.valueOf(query.getSingleResult()));
  }

  @SuppressWarnings("unchecked")
  public List<Consultant> getByGroupLeader(String accountid, int pageNo, int pageSize, String groupid, String name, int title) {
    List<Consultant> cons = null;
    try {
      String sql = "select * from consultant where 1=1 ";

      if (-1 != title) {
        sql += " and title = " + title;
      }

      if (!"-1".equals(groupid)) {
        sql += " and accountid in (select accountid from groupmember where groupid= '" + groupid + "')";
      } else {
        sql +=
            " and accountid in  (select accountid from groupmember where groupid in (select id from groups where accountid= '" + accountid
                + "'))";
      }

      if (name != null && !"".equals(name)) {
        sql += " and name like '%" + name + "%'";
      }

      sql += " limit ?1,?2";
      Query query = em().createNativeQuery(sql, Consultant.class);
      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      cons = query.getResultList();
    } catch (Exception e) {
      cons = null;
    }

    return cons;
  }

  public Consultant getConsultantWithAccount(String conId) {

    Consultant con = null;
    try {
      Query query = em().createQuery("select con from Consultant con left join fetch con.account where con.id=:id", Consultant.class);
      query.setParameter("id", conId);

      con = (Consultant) query.getSingleResult();
    } catch (Exception e) {
      con = null;
    }
    return con;
  }

  @Override
  public Class<Consultant> getEntityType() {
    return Consultant.class;
  }

  public int grouptotal(String accountid, String groupid, String name, int title) {
    int total = 0;
    try {

      String sql = "select count(*) from consultant where 1=1 ";
      if (-1 != title) {
        sql += " and title=" + title;
      }
      if (!"-1".equals(groupid)) {
        sql += " and accountid in (select accountid from groupmember where groupid= '" + groupid + "')";
      } else {
        sql +=
            " and accountid in  (select accountid from groupmember where groupid in (select id from groups where accountid= '" + accountid
                + "'))";
      }

      if (name != null && !"".equals(name)) {
        sql += " and name like '%" + name + "%'";
      }

      Query query = em().createNativeQuery(sql);
      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> increase(String consultantid, String timefrom, String timeto) {
    List<Object[]> cons = null;
    try {

      String sql =
          "  select  con.id, con.name,  cast(date_format(c.generatetime, '%Y%m') as char) d1, count(c.id) increase from consultant con ";

      if (timefrom != null && !"".equals(timefrom)) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat s = new SimpleDateFormat("yyyyMM");

        String strFrom = s.format(sdf.parse(timefrom));
        String strTo = s.format(sdf.parse(timeto));
        sql +=
            " left join customer c on(con.id=c.consultantid and DATE_FORMAT(c.generatetime,'%Y%m')>=" + strFrom
                + " and DATE_FORMAT(c.generatetime,'%Y%m')<=" + strTo + ")  ";

      } else {
        sql += " left join customer c on( con.id=c.consultantid  and DATE_FORMAT(c.generatetime, '%Y%m')=DATE_FORMAT(now(), '%Y%m'))  ";

      }

      sql += " where con.id=?1 ";

      sql +=
          " group by con.id,  con.name,   cast(date_format(c.generatetime, '%Y%m') as char) order by cast(date_format(c.generatetime, '%Y%m') as char)";

      Query query = em().createNativeQuery(sql);
      query.setParameter(1, consultantid);
      cons = query.getResultList();

    } catch (Exception e) {
      cons = null;
    }
    return cons;
  }

  @SuppressWarnings("unchecked")
  public List<Consultant> list() {
    List<Consultant> cons = null;
    try {
      Query query = em().createQuery("select con from Consultant con left join fetch con.account", Consultant.class);
      cons = query.getResultList();

    } catch (Exception e) {
      cons = null;
    }

    return cons;
  }

  @SuppressWarnings("unchecked")
  public List<Consultant> list(int pageNo, int pageSize, String groupid, String name, int title) {
    List<Consultant> cons = null;
    try {

      String sql = "select * from consultant  where 1=1  ";
      if (-1 != title) {
        sql += " and title = " + title;
      }
      if (!"-1".equals(groupid)) {
        sql += " and accountid in (select accountid from groupmember where groupid = '" + groupid + "' )";
      }
      if (name != null && !"".equals(name)) {
        sql += " and name like '%" + name + "%' ";
      }
      sql += " order by CONVERT( name USING gbk ) limit ?1,?2";

      Query query = em().createNativeQuery(sql, Consultant.class);

      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      cons = query.getResultList();

    } catch (Exception e) {
      cons = null;
    }

    return cons;
  }

  public int total(String groupid, String name, int title) {
    int total = 0;
    try {
      String sql = "select count(*) from consultant where 1=1  ";
      if (-1 != title) {
        sql += " and title=" + title;
      }
      if (!"-1".equals(groupid)) {
        sql += " and accountid in (select accountid from groupmember where groupid = '" + groupid + "' )";
      }
      if (name != null && !"".equals(name)) {
        sql += " and name like '%" + name + "%' ";
      }
      Query query = em().createNativeQuery(sql);

      total = Integer.parseInt(String.valueOf(query.getSingleResult()));

    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  public int totalbyleader(String accountid, String groupid) {
    int total = 0;
    try {
      String sql = "select count(*) from consultant where 1=1  ";

      if (!"-1".equals(groupid)) {
        sql += " and accountid in (select accountid from groupmember where groupid= '" + groupid + "')";
      } else {
        sql +=
            " and accountid in  (select accountid from groupmember where groupid in (select id from groups where accountid= '" + accountid
                + "'))";
      }

      Query query = em().createNativeQuery(sql);

      total = Integer.parseInt(String.valueOf(query.getSingleResult()));

    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> visit(String consultantid, String timefrom, String timeto) {
    List<Object[]> cons = null;
    try {

      String sql =
          "  select con.id,con.name, cast(DATE_FORMAT(cl.generatetime,'%Y%m') as char) d3, count(cl.id) visit  from consultant con  ";

      if (timefrom != null && !"".equals(timefrom)) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat s = new SimpleDateFormat("yyyyMM");

        String strFrom = s.format(sdf.parse(timefrom));
        String strTo = s.format(sdf.parse(timeto));
        sql +=
            " left join consultantlocation cl on (con.id=cl.consultantid and DATE_FORMAT(cl.generatetime,'%Y%m')<=" + strTo
                + " and DATE_FORMAT(cl.generatetime,'%Y%m')>=" + strFrom + ") ";
      } else {
        sql +=
            " left join consultantlocation cl on (con.id=cl.consultantid and DATE_FORMAT(cl.generatetime,'%Y%m')=DATE_FORMAT(now(),'%Y%m')) ";

      }

      sql += " where con.id=?1 ";

      sql +=
          " group by con.id,con.name, cast(DATE_FORMAT(cl.generatetime,'%Y%m') as char) order by cast(DATE_FORMAT(cl.generatetime,'%Y%m') as char) ";

      Query query = em().createNativeQuery(sql);
      query.setParameter(1, consultantid);
      cons = query.getResultList();

    } catch (Exception e) {
      cons = null;
    }

    return cons;

  }

  @SuppressWarnings("unchecked")
  public List<Object[]> workeffort(String consultantid, String timefrom, String timeto) {
    List<Object[]> cons = null;
    try {

      String sql =
          "  select con.id,con.name, cast(date_format(c.generatetime,'%Y%m') as char) d1, count(c.id) increase , cast(DATE_FORMAT(cust.completetime,'%Y%m') as char) d2, count(cust.id) complete , "
              + " cast(DATE_FORMAT(cl.generatetime,'%Y%m') as char) d3, count(cl.id) visit  from consultant con  ";

      if (timefrom != null && !"".equals(timefrom)) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat s = new SimpleDateFormat("yyyyMM");

        String strFrom = s.format(sdf.parse(timefrom));
        String strTo = s.format(sdf.parse(timeto));
        sql +=
            " left join customer c on(con.id=c.consultantid and DATE_FORMAT(c.generatetime,'%Y%m')>=" + strFrom
                + " and DATE_FORMAT(c.generatetime,'%Y%m')<=" + strTo + ")  "
                + " left join customer cust on (con.id=cust.consultantid and cust.done=1 and DATE_FORMAT(cust.completetime,'%Y%m')>="
                + strFrom + "     and DATE_FORMAT(cust.completetime,'%Y%m') <=" + strTo + ")"
                + " left join consultantlocation cl on (con.id=cl.consultantid and DATE_FORMAT(cl.generatetime,'%Y%m')<=" + strTo
                + " and DATE_FORMAT(cl.generatetime,'%Y%m')>=" + strFrom + ") ";
      } else {
        sql +=
            " left join customer c on(con.id=c.consultantid and DATE_FORMAT(c.generatetime,'%Y%m')=DATE_FORMAT(now(),'%Y%m'))  "
                + " left join customer cust on (con.id=cust.consultantid and cust.done=1 and DATE_FORMAT(cust.completetime,'%Y%m')=DATE_FORMAT(now(),'%Y%m')) "
                + " left join consultantlocation cl on (con.id=cl.consultantid and DATE_FORMAT(cl.generatetime,'%Y%m')=DATE_FORMAT(now(),'%Y%m')) ";

      }

      sql += " where con.id=?1 ";

      sql +=
          " group by con.id,con.name, cast(date_format(c.generatetime,'%Y%m') as char),cast(DATE_FORMAT(cust.completetime,'%Y%m') as char),cast(DATE_FORMAT(cl.generatetime,'%Y%m') as char) ";

      Query query = em().createNativeQuery(sql);
      query.setParameter(1, consultantid);
      cons = query.getResultList();

    } catch (Exception e) {
      cons = null;
    }

    return cons;

  }

  @SuppressWarnings("unchecked")
  public List<Object[]> workforce(int pageNo, int pageSize, String groupid, String timefrom) {
    List<Object[]> cons = null;
    try {

      String sql = "";
      String time = "";

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat s = new SimpleDateFormat("yyyyMM");
      if (timefrom != null && !"".equals(timefrom)) {
        time = s.format(sdf.parse(timefrom));
      } else {
        time = s.format(new Date());
      }
      if (!"-1".equals(groupid)) {
        sql =
            " select * from ( select con.id id1,con.name name1 ,con.consultantno no1, count(cus.id) customercount from consultant con "
                + "  left join customer cus on (con.id=cus.consultantid) where con.accountid in (select accountid from groupmember where groupid = '"
                + groupid
                + "' ) "
                + "  group by con.id,con.name,con.consultantno order by CONVERT( name1 USING gbk )   ) as aa left join "

                + "( select con.id id2,con.name name2,con.consultantno no2,  count(cu.id) completecustomer  from consultant con "
                + "  left join customer cu on(cu.done=1 and con.id=cu.consultantid ) where con.accountid in (select accountid from groupmember where groupid = '"
                + groupid
                + "' )"
                + "group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )   ) as bb on (aa.id1 =bb.id2) left join "

                + " (select con.id id3,con.name name3,con.consultantno no3,   "
                + "         count(c.id) monthlyincrease   from consultant con "

                + "  left join customer c on(con.id=c.consultantid and date_format(c.generatetime,'%Y%m')="
                + time
                + ")  where con.accountid in (select accountid from groupmember where groupid = '"
                + groupid
                + "' ) "
                + " group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )   ) as cc on (bb.id2=cc.id3) left join "

                + " (select con.id id4,con.name name4,con.consultantno no4, count(cust.id) monthlycomplete   from consultant con "
                + "            left join customer cust on   "
                + "  (con.id=cust.consultantid and cust.done=1 and date_format(cust.completetime,'%Y%m')="
                + time
                + " ) where con.accountid in (select accountid from groupmember where groupid = '"
                + groupid
                + "' )"

                + "  group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )  ) as dd on (cc.id3=dd.id4) left join "

                + " (select con.id id5,con.name name5,con.consultantno no5,  count(cl.id) monthlyvisit  from consultant con "

                + "  left join consultantlocation cl on(con.id=cl.consultantid and date_format(cl.generatetime,'%Y%m')="
                + time
                + ") where con.accountid in (select accountid from groupmember where groupid = '"
                + groupid
                + "' )"
                + " group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk ) ) as ee on (dd.id4=ee.id5) limit ?1,?2 ";
      } else {

        sql =
            " select * from ( select con.id id1,con.name name1 ,con.consultantno no1, count(cus.id) customercount from consultant con "
                + "  left join customer cus on (con.id=cus.consultantid)  "
                + "  group by con.id,con.name,con.consultantno order by CONVERT( name1 USING gbk )   ) as aa left join "

                + "( select con.id id2,con.name name2,con.consultantno no2,  count(cu.id) completecustomer  from consultant con "
                + "  left join customer cu on(cu.done=1 and con.id=cu.consultantid ) "
                + "group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )   ) as bb on (aa.id1 =bb.id2) left join "

                + " (select con.id id3,con.name name3,con.consultantno no3,   "
                + "         count(c.id) monthlyincrease   from consultant con "

                + "  left join customer c on(con.id=c.consultantid and date_format(c.generatetime,'%Y%m')="
                + time
                + ")   "
                + " group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )   ) as cc on (bb.id2=cc.id3) left join "

                + " (select con.id id4,con.name name4,con.consultantno no4, count(cust.id) monthlycomplete   from consultant con "
                + "            left join customer cust on   "
                + "  (con.id=cust.consultantid and cust.done=1 and date_format(cust.completetime,'%Y%m')="
                + time
                + " ) "

                + "  group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )  ) as dd on (cc.id3=dd.id4) left join "

                + " (select con.id id5,con.name name5,con.consultantno no5,  count(cl.id) monthlyvisit  from consultant con "

                + "  left join consultantlocation cl on(con.id=cl.consultantid and date_format(cl.generatetime,'%Y%m')="
                + time
                + ") "
                + " group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk ) ) as ee on (dd.id4=ee.id5) limit ?1,?2 ";
      }

      Query query = em().createNativeQuery(sql);

      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      cons = query.getResultList();

    } catch (Exception e) {
      cons = null;
    }

    return cons;

  }

  @SuppressWarnings("unchecked")
  public List<Object[]> workforcebyleader(String accountid, int pageNo, int pageSize, String groupid, String timefrom) {
    List<Object[]> cons = null;
    try {

      String sql = "";
      String time = "";

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat s = new SimpleDateFormat("yyyyMM");
      if (timefrom != null && !"".equals(timefrom)) {
        time = s.format(sdf.parse(timefrom));
      } else {
        time = s.format(new Date());
      }
      if (!"-1".equals(groupid)) {
        sql =
            " select * from ( select con.id id1,con.name name1 ,con.consultantno no1, count(cus.id) customercount from consultant con "
                + "  left join customer cus on (con.id=cus.consultantid) where con.accountid in (select accountid from groupmember where groupid = '"
                + groupid
                + "' ) "
                + "  group by con.id,con.name,con.consultantno order by CONVERT( name1 USING gbk )   ) as aa left join "

                + "( select con.id id2,con.name name2,con.consultantno no2,  count(cu.id) completecustomer  from consultant con "
                + "  left join customer cu on(cu.done=1 and con.id=cu.consultantid ) where con.accountid in (select accountid from groupmember where groupid = '"
                + groupid
                + "' )"
                + "group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )   ) as bb on (aa.id1 =bb.id2) left join "

                + " (select con.id id3,con.name name3,con.consultantno no3,   "
                + "         count(c.id) monthlyincrease   from consultant con "

                + "  left join customer c on(con.id=c.consultantid and date_format(c.generatetime,'%Y%m')="
                + time
                + ")  where con.accountid in (select accountid from groupmember where groupid = '"
                + groupid
                + "' ) "
                + " group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )   ) as cc on (bb.id2=cc.id3) left join "

                + " (select con.id id4,con.name name4,con.consultantno no4, count(cust.id) monthlycomplete   from consultant con "
                + "            left join customer cust on   "
                + "  (con.id=cust.consultantid and cust.done=1 and date_format(cust.completetime,'%Y%m')="
                + time
                + " ) where con.accountid in (select accountid from groupmember where groupid = '"
                + groupid
                + "' )"

                + "  group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )  ) as dd on (cc.id3=dd.id4) left join "

                + " (select con.id id5,con.name name5,con.consultantno no5,  count(cl.id) monthlyvisit  from consultant con "

                + "  left join consultantlocation cl on(con.id=cl.consultantid and date_format(cl.generatetime,'%Y%m')="
                + time
                + ") where con.accountid in (select accountid from groupmember where groupid = '"
                + groupid
                + "' )"
                + " group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk ) ) as ee on (dd.id4=ee.id5) limit ?1,?2 ";
      } else {

        sql =
            " select * from ( select con.id id1,con.name name1 ,con.consultantno no1, count(cus.id) customercount from consultant con "
                + "  left join customer cus on (con.id=cus.consultantid)  where con.accountid in  (select accountid from groupmember where groupid in (select id from groups where accountid= '"
                + accountid
                + "'))"
                + "  group by con.id,con.name,con.consultantno order by CONVERT( name1 USING gbk )   ) as aa left join "

                + "( select con.id id2,con.name name2,con.consultantno no2,  count(cu.id) completecustomer  from consultant con "
                + "  left join customer cu on(cu.done=1 and con.id=cu.consultantid ) where con.accountid in  (select accountid from groupmember where groupid in (select id from groups where accountid= '"
                + accountid
                + "'))"
                + "group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )   ) as bb on (aa.id1 =bb.id2) left join "

                + " (select con.id id3,con.name name3,con.consultantno no3,   "
                + "         count(c.id) monthlyincrease   from consultant con "

                + "  left join customer c on(con.id=c.consultantid and date_format(c.generatetime,'%Y%m')="
                + time
                + ")  where con.accountid in  (select accountid from groupmember where groupid in (select id from groups where accountid= '"
                + accountid
                + "')) "
                + " group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )   ) as cc on (bb.id2=cc.id3) left join "

                + " (select con.id id4,con.name name4,con.consultantno no4, count(cust.id) monthlycomplete   from consultant con "
                + "            left join customer cust on   "
                + "  (con.id=cust.consultantid and cust.done=1 and date_format(cust.completetime,'%Y%m')="
                + time
                + " ) where con.accountid in  (select accountid from groupmember where groupid in (select id from groups where accountid= '"
                + accountid
                + "'))"

                + "  group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk )  ) as dd on (cc.id3=dd.id4) left join "

                + " (select con.id id5,con.name name5,con.consultantno no5,  count(cl.id) monthlyvisit  from consultant con "

                + "  left join consultantlocation cl on(con.id=cl.consultantid and date_format(cl.generatetime,'%Y%m')="
                + time
                + ") where con.accountid in  (select accountid from groupmember where groupid in (select id from groups where accountid= '"
                + accountid
                + "'))"
                + " group by con.id,con.name,con.consultantno order by CONVERT( con.name USING gbk ) ) as ee on (dd.id4=ee.id5) limit ?1,?2 ";
      }

      Query query = em().createNativeQuery(sql);

      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      cons = query.getResultList();

    } catch (Exception e) {
      cons = null;
    }

    return cons;
  }

  public int workforcetotal(String groupid) {
    int total = 0;
    try {
      String sql = "select count(*) from consultant where 1=1  ";

      if (!"-1".equals(groupid)) {
        sql += " and accountid in (select accountid from groupmember where groupid = '" + groupid + "' )";
      }

      Query query = em().createNativeQuery(sql);

      total = Integer.parseInt(String.valueOf(query.getSingleResult()));

    } catch (Exception e) {
      total = 0;
    }

    return total;
  }
}
