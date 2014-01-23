package com.isoftstone.smart.kingstone.service;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.isoftstone.smart.core.service.BaseService;
import com.isoftstone.smart.kingstone.entity.AdviceInvest;

@Path("/adviceinvest")
@Produces(MediaType.APPLICATION_JSON)
public class AdviceInvestResource extends BaseService<AdviceInvest> {

  @Override
  public Class<AdviceInvest> getEntityType() {
    // TODO Auto-generated method stub
    return AdviceInvest.class;
  }

}
