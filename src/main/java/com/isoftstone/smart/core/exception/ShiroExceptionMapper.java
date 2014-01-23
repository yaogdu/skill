package com.isoftstone.smart.core.exception;

import javax.persistence.Transient;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.glassfish.jersey.server.internal.process.MappableException;
import org.glassfish.jersey.server.mvc.Viewable;

import com.isoftstone.smart.core.entity.ErrorResult;

@Provider
public class ShiroExceptionMapper implements ExceptionMapper<ShiroException> {

  @Context
  @Transient
  protected UriInfo uriInfo;

  @Override
  public Response toResponse(ShiroException exception) {
    if (exception instanceof AuthenticationException) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).entity(
          new Viewable("/login", ErrorResult.from("用户名或密码错误", exception))).build();
    } else if (exception instanceof UnauthorizedException) {
      return Response.status(Status.UNAUTHORIZED).entity("用户未登录!").build();
    }
    throw new MappableException(exception);
  }
}
