package com.isoftstone.smart.kingstone.business;

import java.util.List;

import javax.persistence.Query;

import com.isoftstone.smart.core.service.BaseEntityService;
import com.isoftstone.smart.kingstone.entity.Customer;

public class CustomerService extends BaseEntityService<Customer> {

  @SuppressWarnings("unchecked")
  public List<Customer> allcustomers(int pageNo, int pageSize, String groupid, String name, String consultantname) {
    List<Customer> customers = null;
    try {

      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select cus.* from customer cus left join consultant con on (cus.consultantid=con.id) where 1=1 and con.accountid in(select accountid from groupmember where groupid='"
                + groupid + "')";
        if (name != null && !"".equals(name)) {
          sql += " and cus.name  like '%" + name + "%'";
        }
        if (consultantname != null && !"".equals(consultantname)) {
          sql += " and con.name like '%" + consultantname + "%'";
        }
      } else {
        sql = "select cus.* from customer cus left join consultant con on (cus.consultantid=con.id) where 1=1 ";
        if (name != null && !"".equals(name)) {
          sql += " and cus.name  like '%" + name + "%'";
        }
        if (consultantname != null && !"".equals(consultantname)) {
          sql += " and con.name like '%" + consultantname + "%'";
        }
      }
      sql += "  order by CONVERT( cus.name USING gbk ) limit ?1,?2 ";
      Query query = em().createNativeQuery(sql, Customer.class);
      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);

      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> allcustomers_byleader(String accountid, int pageNo, int pageSize, String groupid, String name, String consultantname) {
    List<Customer> customers = null;
    try {

      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select cus.* from customer cus left join consultant con on (cus.consultantid=con.id) where 1=1 and con.accountid in(select accountid from groupmember where groupid='"
                + groupid + "')";
        if (name != null && !"".equals(name)) {
          sql += " and cus.name  like '%" + name + "%'";
        }
        if (consultantname != null && !"".equals(consultantname)) {
          sql += " and con.name like '%" + consultantname + "%'";
        }
      } else {
        sql =
            "select cus.* from customer cus left join consultant con on (cus.consultantid=con.id) where 1=1 and con.accountid in(select accountid from groupmember where groupid in (select id from groups where accountid ='"
                + accountid + "'))";
        if (name != null && !"".equals(name)) {
          sql += " and cus.name  like '%" + name + "%'";
        }
        if (consultantname != null && !"".equals(consultantname)) {
          sql += " and con.name like '%" + consultantname + "%'";
        }
      }
      sql += "  order by CONVERT( cus.name USING gbk ) limit ?1,?2 ";
      Query query = em().createNativeQuery(sql, Customer.class);
      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);

      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> customer_age(String groupid, int min, int max, int pageNo, int pageSize) {
    List<Customer> customers = null;
    try {
      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select * from customer cus where (year(now())-year(cus.age))<="
                + max
                + " and (year(now())-year(cus.age)) >="
                + min
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') )";
      } else {
        sql = "select * from customer cus where  (year(now())-year(cus.age))<=" + max + " and (year(now())-year(cus.age)) >=" + min;
      }
      sql += " order by CONVERT( name USING gbk ) limit ?1,?2";
      Query query = em().createNativeQuery(sql, Customer.class);
      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> customer_age_byleader(String accountid, String groupid, int min, int max, int pageNo, int pageSize) {
    List<Customer> customers = null;
    try {
      String sql = "";

      if (!"-1".equals(groupid)) {
        sql =
            "select * from customer cus where (year(now())-year(cus.age))<="
                + max
                + " and (year(now())-year(cus.age)) >="
                + min
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') )";
      } else {
        sql =
            "select * from customer cus where  (year(now())-year(cus.age))<="
                + max
                + " and (year(now())-year(cus.age)) >="
                + min
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid in (select id from groups where accountid ='"
                + accountid + "')))";
      }
      sql += " order by CONVERT( name USING gbk ) limit ?1,?2";
      Query query = em().createNativeQuery(sql, Customer.class);
      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> customer_asset(String groupid, int min, int max, int pageNo, int pageSize) {
    List<Customer> customers = null;
    try {
      String sql = "";
      if (!"-1".equals(groupid)) {
        sql = "select cus.* from customer cus , balance b  where  (cus.id=b.customerid) and b.total>=" + min;
        if (max != -1) {
          sql += " and b.total <=" + max;
        }
        sql +=
            " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') ) ";
      } else {
        sql = "select cus.* from customer cus , balance b  where  (cus.id=b.customerid) and   b.total >=" + min;
        if (max != -1) {
          sql += " and b.total<=" + max + "";
        }
      }
      sql += " order by CONVERT( name USING gbk ) limit ?1,?2";
      Query query = em().createNativeQuery(sql, Customer.class);
      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> customer_asset_byleader(String accountid, String groupid, int min, int max, int pageNo, int pageSize) {
    List<Customer> customers = null;
    try {
      String sql = "";

      if (!"-1".equals(groupid)) {
        sql = "select cus.* from customer cus , balance b  where  (cus.id=b.customerid) and b.total>=" + min;
        if (max != -1) {
          sql += " and b.total<=" + max;
        }

        sql +=
            " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') )";
      } else {
        sql = "select cus.* from customer cus , balance b  where  (cus.id=b.customerid) and b.total>=" + min;
        if (max != -1) {
          sql += " and b.total<=" + max;
        }
        sql +=
            " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid in (select id from groups where accountid ='"
                + accountid + "')))";
      }
      sql += " order by CONVERT( name USING gbk ) limit ?1,?2";
      Query query = em().createNativeQuery(sql, Customer.class);
      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> customer_gender(String groupid, int clickindex, int pageNo, int pageSize) {
    List<Customer> customers = null;
    try {
      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select * from customer cus where cus.gender="
                + clickindex
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') )";
      } else {
        sql = "select * from customer cus where cus.gender=" + clickindex;
      }
      sql += " order by CONVERT( name USING gbk ) limit ?1,?2";
      Query query = em().createNativeQuery(sql, Customer.class);
      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> customer_gender_byleader(String accountid, String groupid, int clickindex, int pageNo, int pageSize) {
    List<Customer> customers = null;
    try {
      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select * from customer cus where cus.gender="
                + clickindex
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') )";
      } else {
        sql =
            "select * from customer cus where cus.gender="
                + clickindex
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid in (select id from groups where accountid ='"
                + accountid + "')))";
      }
      sql += " order by CONVERT( name USING gbk ) limit ?1,?2";
      Query query = em().createNativeQuery(sql, Customer.class);
      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> customer_jobs(String groupid, String job, int pageNo, int pageSize) {
    List<Customer> customers = null;
    try {
      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select cus.* from customer cus where cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "')) and cus.job ='" + job + "' ";

      } else {
        sql = "select cus.* from customer cus where cus.job = '" + job + "'";

      }
      sql += " order by CONVERT( cus.name USING gbk ) limit ?1,?2";
      Query query = em().createNativeQuery(sql, Customer.class);
      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> customer_jobs_byleader(String accountid, String groupid, String job, int pageNo, int pageSize) {
    List<Customer> customers = null;
    try {
      String sql = "";

      if (!"-1".equals(groupid)) {
        sql =
            "select cus.* from customer cus where cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "')) and cus.job ='" + job + "'";

      } else {
        sql =
            " select cus.* from customer cus where cus.consultantid in(select id from consultant where accountid in(select accountid from groupmember where groupid in(select id from groups where accountid ='"
                + accountid + "'))) " + "and cus.job ='" + job + "'";

      }
      sql += " order by CONVERT( cus.name USING gbk ) limit ?1,?2";
      Query query = em().createNativeQuery(sql, Customer.class);
      query.setParameter(1, pageSize * (pageNo - 1));
      query.setParameter(2, pageSize);
      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> getByConsultantId(String consultantId) {
    List<Customer> customers = null;
    try {
      Query query =
          em().createNativeQuery("select * from customer cu where cu.consultantid=?1 order by CONVERT( name USING gbk )", Customer.class);
      query.setParameter(1, consultantId);
      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> getByConsultantId(String consultantId, int pageNo, int pageSize) {
    List<Customer> customers = null;
    try {
      Query query =
          em().createNativeQuery("select * from customer cu where cu.consultantid=?1 order by CONVERT( name USING gbk ) limit ?2,?3",
              Customer.class);
      query.setParameter(1, consultantId);
      query.setParameter(2, pageSize * (pageNo - 1));
      query.setParameter(3, pageSize);
      customers = query.getResultList();
    } catch (Exception e) {
      customers = null;
    }

    return customers;
  }

  @Override
  public Class<Customer> getEntityType() {
    return Customer.class;
  }

  public int total_allcustomers(String groupid, String name, String consultantname) {
    int total = 0;
    try {
      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select count(cus.id) from customer cus left join consultant con on (cus.consultantid=con.id) where 1=1 and con.accountid in(select accountid from groupmember where groupid='"
                + groupid + "')";
        if (name != null && !"".equals(name)) {
          sql += " and cus.name  like '%" + name + "%'";
        }
        if (consultantname != null && !"".equals(consultantname)) {
          sql += " and con.name like '%" + consultantname + "%'";
        }
      } else {
        sql = "select count(cus.id) from customer cus left join consultant con on (cus.consultantid=con.id) where 1=1 ";
        if (name != null && !"".equals(name)) {
          sql += " and cus.name  like '%" + name + "%'";
        }
        if (consultantname != null && !"".equals(consultantname)) {
          sql += " and con.name like '%" + consultantname + "%'";
        }
      }
      Query query = em().createNativeQuery(sql);

      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  @SuppressWarnings("unchecked")
  public int total_allcustomers_byleader(String accountid, String groupid, String name, String consultantname) {
    int total = 0;
    try {

      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select count(cus.id) from customer cus left join consultant con on (cus.consultantid=con.id) where 1=1 and con.accountid in(select accountid from groupmember where groupid='"
                + groupid + "')";
        if (name != null && !"".equals(name)) {
          sql += " and cus.name  like '%" + name + "%'";
        }
        if (consultantname != null && !"".equals(consultantname)) {
          sql += " and con.name like '%" + consultantname + "%'";
        }
      } else {
        sql =
            "select count(cus.id) from customer cus left join consultant con on (cus.consultantid=con.id) where 1=1 and con.accountid in(select accountid from groupmember where groupid in (select  id from groups where accountid ='"
                + accountid + "'))";
        if (name != null && !"".equals(name)) {
          sql += " and cus.name  like '%" + name + "%'";
        }
        if (consultantname != null && !"".equals(consultantname)) {
          sql += " and con.name like '%" + consultantname + "%'";
        }
      }
      Query query = em().createNativeQuery(sql);
      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  public int total_customer_age(String groupid, int min, int max) {
    int total = 0;
    try {
      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select count(*) from customer cus where (year(now())-year(cus.age))<="
                + max
                + " and (year(now())-year(cus.age)) >="
                + min
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') )";
      } else {
        sql = "select count(*) from customer cus where  (year(now())-year(cus.age))<=" + max + " and (year(now())-year(cus.age)) >=" + min;
      }
      Query query = em().createNativeQuery(sql);
      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  @SuppressWarnings("unchecked")
  public int total_customer_age_byleader(String accountid, String groupid, int min, int max) {
    int total = 0;
    try {
      String sql = "";

      if (!"-1".equals(groupid)) {
        sql =
            "select count(*) from customer cus where (year(now())-year(cus.age))<="
                + max
                + " and (year(now())-year(cus.age)) >="
                + min
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') )";
      } else {
        sql =
            "select count(*) from customer cus where  (year(now())-year(cus.age))<="
                + max
                + " and (year(now())-year(cus.age)) >="
                + min
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid in (select id from groups where accountid ='"
                + accountid + "')))";
      }
      Query query = em().createNativeQuery(sql);
      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  @SuppressWarnings("unchecked")
  public int total_customer_asset(String groupid, int min, int max) {
    int total = 0;
    try {
      String sql = "";
      if (!"-1".equals(groupid)) {
        sql = "select count(cus.id) from customer cus , balance b  where  (cus.id=b.customerid) and b.total>=" + min;
        if (max != -1) {
          sql += " and b.total<=" + max;
        }
        sql +=
            " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') )  ";
      } else {
        sql = "select count(cus.id) from customer cus , balance b  where  (cus.id=b.customerid) and b.total >=" + min;
        if (max != -1) {
          sql += " and b.total<=" + max;
        }
      }
      Query query = em().createNativeQuery(sql);
      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  @SuppressWarnings("unchecked")
  public int total_customer_asset_byleader(String accountid, String groupid, int min, int max) {
    int total = 0;
    try {
      String sql = "";

      if (!"-1".equals(groupid)) {

        sql = " select count(cus.id) from customer cus , balance b  where  (cus.id=b.customerid) and b.total>=" + min;
        if (max != -1) {
          sql += " and b.total <=" + max;
        }

        sql +=
            " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') )";
      } else {
        sql = " select count(cus.id) from customer cus , balance b  where  (cus.id=b.customerid) and b.total>=" + min;
        if (max != -1) {
          sql += " and b.total <=" + max;
        }
        sql +=
            " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid in (select id from groups where accountid ='"
                + accountid + "')))";
      }
      Query query = em().createNativeQuery(sql);
      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  public int total_customer_gender(String groupid, int clickindex) {
    int total = 0;
    try {
      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select count(*) from customer cus where cus.gender="
                + clickindex
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') )";
      } else {
        sql = "select count(*) from customer cus where cus.gender=" + clickindex;
      }
      Query query = em().createNativeQuery(sql);

      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  public int total_customer_gender_byleader(String accountid, String groupid, int clickindex) {
    int total = 0;
    try {
      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select count(*) from customer cus where cus.gender="
                + clickindex
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "') )";
      } else {
        sql =
            "select count(*) from customer cus where cus.gender="
                + clickindex
                + " and cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid in (select id from groups where accountid ='"
                + accountid + "')))";
      }
      Query query = em().createNativeQuery(sql);
      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  public int total_customer_jobs(String groupid, String job) {
    int total = 0;
    try {
      String sql = "";
      if (!"-1".equals(groupid)) {
        sql =
            "select count(*) from customer cus where cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "')) and cus.job ='" + job + "'";

      } else {
        sql = "select count(*) from customer cus where cus.job = '" + job + "'";

      }
      Query query = em().createNativeQuery(sql);
      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  @SuppressWarnings("unchecked")
  public int total_customer_jobs_byleader(String accountid, String groupid, String job) {
    int total = 0;
    try {
      String sql = "";

      if (!"-1".equals(groupid)) {
        sql =
            "select count(cus.id) from customer cus where cus.consultantid in (select id from consultant where accountid in (select accountid from groupmember where groupid='"
                + groupid + "')) and cus.job ='" + job + "'";

      } else {
        sql =
            " select count(cus.id) from customer cus where cus.consultantid in(select id from consultant where accountid in(select accountid from groupmember where groupid in(select id from groups where accountid ='"
                + accountid + "'))) " + "and cus.job ='" + job + "'";

      }
      sql += " order by CONVERT( cus.name USING gbk ) limit ?1,?2";
      Query query = em().createNativeQuery(sql);

      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }

  public int totalByConsultantid(String consultantId) {
    int total = 0;
    try {
      Query query = em().createNativeQuery("select count(*) from customer cu where cu.consultantid=?1  ");
      query.setParameter(1, consultantId);
      total = Integer.parseInt(String.valueOf(query.getSingleResult()));
    } catch (Exception e) {
      total = 0;
    }

    return total;
  }
}
