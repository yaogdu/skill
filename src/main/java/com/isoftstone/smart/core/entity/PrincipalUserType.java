package com.isoftstone.smart.core.entity;

import java.io.Serializable;
import java.security.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

public class PrincipalUserType implements UserType {

  public static final String NAME = "principal";

  @Override
  public Object assemble(final Serializable cached, final Object arg1) throws HibernateException {
    return cached;
  }

  @Override
  public Object deepCopy(final Object value) throws HibernateException {
    return value;
  }

  @Override
  public Serializable disassemble(final Object value) throws HibernateException {
    return (Serializable) value;
  }

  @Override
  public boolean equals(final Object o1, final Object o2) throws HibernateException {
    if (o1 == o2) {
      return true;
    }
    if (o1 == null || o2 == null) {
      return false;
    }
    final Principal p1 = (Principal) o1;
    final Principal p2 = (Principal) o2;
    return p1.getClass() == p2.getClass() && p1.getName().equals(p2.getName());
  }

  @Override
  public int hashCode(final Object value) throws HibernateException {
    return value.hashCode();
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Object nullSafeGet(final ResultSet resultSet, final String[] names, final SessionImplementor session, final Object owner)
      throws HibernateException, SQLException {
    String type = StringType.INSTANCE.nullSafeGet(resultSet, names[0], session);
    String id = StringType.INSTANCE.nullSafeGet(resultSet, names[1], session);
    if (type != null && id != null) {
      Object result = ((Session) session).createQuery("from " + type + " where id=:id").setParameter("id", id).uniqueResult();
      return result;
    }
    return null;
  }

  @Override
  public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SessionImplementor session)
      throws HibernateException, SQLException {
    if (value == null) {
      StringType.INSTANCE.nullSafeSet(st, null, index, session);
      StringType.INSTANCE.nullSafeSet(st, null, index + 1, session);
    } else {
      Principal principal = (Principal) value;
      StringType.INSTANCE.nullSafeSet(st, principal.getClass().getName(), index, session);
      StringType.INSTANCE.nullSafeSet(st, principal.getName(), index + 1, session);
    }
  }

  @Override
  public Object replace(final Object original, final Object arg1, final Object arg2) throws HibernateException {
    return original;
  }

  @Override
  public Class<?> returnedClass() {
    return Principal.class;
  }

  @Override
  public int[] sqlTypes() {
    return new int[] {StringType.INSTANCE.sqlType(), StringType.INSTANCE.sqlType()};
  }

}