package com.isoftstone.smart.core.servlet.tags;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.isoftstone.smart.core.entity.Content;
import com.isoftstone.smart.core.entity.Place;

public class IncludeTag extends TagSupport {

  private static final long serialVersionUID = 1L;

  private Content content;

  private String page;

  private boolean flush;

  private boolean container;

  @Override
  public int doEndTag() throws JspException {
    if (container && content != null) {
      pageContext.getRequest().setAttribute("it", content);
    }
    return super.doEndTag();
  }

  @Override
  public int doStartTag() throws JspException {
    try {
      ServletRequest request = pageContext.getRequest();
      Class<?> cls = null;
      Object entity = request.getAttribute("it");
      String path = null;
      if (entity != null) {
        cls = entity.getClass();
        if (entity instanceof Content) {
          if (container) {
            content = (Content) entity;
            Place target = content.getPlace();
            if (target != null) {
              request.setAttribute("it", target);
              cls = target.getClass();
            }
          }
        }

        do {
          path = "/WEB-INF/templates/" + cls.getName().replaceAll("\\.", "/") + "/" + page;
        } while (pageContext.getServletContext().getResource(path) == null && (cls = cls.getSuperclass()) != Object.class);
      } else {
        path = page;
      }
      pageContext.include(path, flush);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return EVAL_BODY_INCLUDE;
  }

  public String getPage() {
    return page;
  }

  public boolean isFlush() {
    return flush;
  }

  public boolean isContainer() {
    return container;
  }

  @Override
  public void release() {
    super.release();
    content = null;
  }

  public void setFlush(boolean flush) {
    this.flush = flush;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public void setContainer(boolean container) {
    this.container = container;
  }

}