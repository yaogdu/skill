package com.isoftstone.smart.core.service;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.isoftstone.smart.core.entity.Place;
import com.isoftstone.smart.core.util.CoreUtil;

@Path("places")
public class PlaceService extends ContentService<Place> {

  public Place getByName(Place parent, String name) {
    TypedQuery<Place> query = em().createQuery("from " + Place.class.getName() + " e where e.place=:place and e.name=:name", Place.class);
    query.setParameter("place", parent);
    query.setParameter("name", name);
    return query.getSingleResult();
  }

  @Path("{path}")
  public Place getByName(@PathParam("path") String path) {
    return getByName(getRootPlace(), path);
  }

  @Override
  public Class<Place> getEntityType() {
    return Place.class;
  }

  @GET
  public Place getRootPlace() {
    Class<Place> type = Place.class;
    Place result = null;
    try {
      result = em().createQuery("from " + type.getName() + " p where p.place=null", type).getSingleResult();
    } catch (NoResultException e) {
      result = CoreUtil.newInstance(type);
      save(result);
    }
    return result;
  }
}
