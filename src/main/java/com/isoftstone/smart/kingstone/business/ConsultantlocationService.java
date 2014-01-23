package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.ConsultantLocation;

public class ConsultantlocationService extends BaseEntityService<ConsultantLocation> {

  @Override
  public Class<ConsultantLocation> getEntityType() {
    // TODO Auto-generated method stub
    return ConsultantLocation.class;
  }

  @SuppressWarnings("unchecked")
  public List<ConsultantLocation> grouproute(String district) {
    List<ConsultantLocation> locations = null;
    try {

      String sql = "";
      if (!"-1".equals(district)) {
        sql =
            "select conl.* from consultantlocation conl , (select max(generatetime) gt,consultantid from consultantlocation group by consultantid ) as cl "
                + " where conl.consultantid=cl.consultantid and conl.generatetime=cl.gt   and conl.consultantid in   (select id from consultant where accountid in  "
                + " (select accountid from groupmember where groupid =?1)) group by consultantid";

      } else {
        sql =
            "select conl.* from consultantlocation conl , (select max(generatetime) gt,consultantid from consultantlocation group by consultantid ) as cl "
                + " where conl.consultantid=cl.consultantid and conl.generatetime=cl.gt ";

      }
      Query query = em().createNativeQuery(sql, ConsultantLocation.class);
      if (!"-1".equals(district)) {
        query.setParameter(1, district);
      }
      locations = query.getResultList();
    } catch (Exception e) {
      locations = null;
    }
    return locations;
  }

  @SuppressWarnings("unchecked")
  public List<ConsultantLocation> grouproute(String accountid, String district) {
    List<ConsultantLocation> locations = null;
    try {

      String sql = "";
      if (!"-1".equals(district)) {
        sql =
            " select conl.* from consultantlocation conl , (select max(generatetime) gt,consultantid from consultantlocation group by consultantid ) as cl "
                + " where conl.consultantid=cl.consultantid and conl.generatetime=cl.gt and consultantid in (select id from consultant where accountid in(select accountid from groupmember where groupid=?1) ) group by consultantid";

      } else {
        sql =
            "select conl.* from consultantlocation conl , (select max(generatetime) gt,consultantid from consultantlocation group by consultantid ) as cl "
                + " where conl.consultantid=cl.consultantid and conl.generatetime=cl.gt and consultantid in (select id from consultant where accountid in "
                + " (select accountid from groupmember where groupid in  (select id from groups where accountid =?1))) "
                + " group by consultantid";
      }
      Query query = em().createNativeQuery(sql, ConsultantLocation.class);
      if (!"-1".equals(district)) {
        query.setParameter(1, district);
      } else {
        query.setParameter(1, accountid);
      }
      locations = query.getResultList();
    } catch (Exception e) {
      locations = null;
    }
    return locations;
  }

  @SuppressWarnings("unchecked")
  public List<ConsultantLocation> route(String consultantid, String timefrom, String timeto) {
    List<ConsultantLocation> locations = null;
    try {
      Query query =
          em().createNativeQuery(
              "select * from consultantlocation where consultantid =?1 and generatetime>=?2 and generatetime<= ?3 order by generatetime",
              ConsultantLocation.class);
      query.setParameter(1, consultantid);
      query.setParameter(2, timefrom);
      query.setParameter(3, timeto);
      locations = query.getResultList();
    } catch (Exception e) {
      locations = null;
    }
    return locations;
  }

  public int total_visits(String customerid) {
    int total = 0;
    try {
      Query query = em().createNativeQuery("select count(*) from consultantlocation where customerid =?1 ");
      query.setParameter(1, customerid);

      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }
    return total;
  }

  @SuppressWarnings("unchecked")
  public List<ConsultantLocation> visits(String customerid, int pageNo, int pageSize) {
    List<ConsultantLocation> locations = null;
    try {
      Query query =
          em().createNativeQuery("select * from consultantlocation where customerid =?1  order by generatetime desc limit ?2,?3",
              ConsultantLocation.class);
      query.setParameter(1, customerid);
      query.setParameter(2, pageSize * (pageNo - 1));
      query.setParameter(3, pageSize);
      locations = query.getResultList();
    } catch (Exception e) {
      locations = null;
    }
    return locations;
  }
}
