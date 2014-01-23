package com.isoftstone.smart.core.service;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.isoftstone.smart.core.entity.BaseEntity;
import com.isoftstone.smart.core.entity.ListResult;
import com.isoftstone.smart.core.entity.Place;

public abstract class BaseService<T> {

  public static final String INDEX = "index";

  @Context
  protected UriInfo uriInfo;

  @Inject
  @Transient
  private Provider<EntityManager> emProvider;

  public void delete(BaseEntity content) {
    em().remove(content);
  }

  public String getAbsolutePath(String... segments) {
    Path path = getAnnotation(getClass(), Path.class);
    UriBuilder builder = UriBuilder.fromPath("/").path(path.value());
    for (String segment : segments) {
      builder.path(segment);
    }
    String result = builder.build().getPath();
    return result;
  }

  public <T extends Annotation> T getAnnotation(Class<?> cls, Class<T> type) {
    T result = cls.getAnnotation(type);
    if (result == null && cls != Object.class) {
      return getAnnotation(cls.getSuperclass(), type);
    }
    return result;
  }

  public T getById(String id) {
    T result = null;
    Class<T> type = getEntityType();
    result = em().find(type, id);
    return result;
  }

  public abstract Class<T> getEntityType();

  @GET
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
  public ListResult<T> getListResult() {
    ListResult<T> result = new ListResult<T>();
    result.setSuccess(true);
    result.setMessage("success");
    result.setData(listAll());
    return result;
  }

  @GET
  @Path("{path}")
  public Viewable getView(@PathParam("path") String path) {
    return new Viewable(getAbsolutePath(path), this);
  }

  public List<T> listAll() {
    TypedQuery<T> query = em().createQuery("from " + getEntityType().getName(), getEntityType());
    return query.getResultList();
  }

  @Transactional
  public T save(T entity) {
    em().persist(entity);
    return entity;
  }

  @Transactional
  public void update(T entity) {
    em().persist(entity);
  }

  protected EntityManager em() {
    return emProvider.get();
  }

}