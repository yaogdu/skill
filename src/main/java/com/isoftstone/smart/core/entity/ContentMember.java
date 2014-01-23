package com.isoftstone.smart.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@TypeDef(name = ContentUserType.NAME, typeClass = ContentUserType.class)
public class ContentMember extends Member<Content> {

  @Type(type = RoleUserType.NAME)
  @Columns(columns = {@Column(name = "contentType"), @Column(name = "contentId")})
  private Content content;

  @Override
  public Content getContent() {
    return content;
  }

  @Override
  public void setContent(Content content) {
    this.content = content;
  }

}
