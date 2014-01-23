package com.isoftstone.smart.core.service;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.isoftstone.smart.core.entity.Tenant;

@Path("tenants")
public class TenantService extends BaseEntityService<Tenant> {

  @Override
  public Class<Tenant> getEntityType() {
    return Tenant.class;
  }

  @Path("{id}")
  public Tenant getUser(@PathParam("id") String id) {
    return getById(id);
  }

}
