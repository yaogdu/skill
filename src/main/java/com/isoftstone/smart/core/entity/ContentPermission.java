package com.isoftstone.smart.core.entity;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authz.Permission;

public class ContentPermission<T extends Action> implements Permission {

  private Content content;

  private Set<T> actions = new HashSet<>();

  private int actionBits = 0;

  public ContentPermission(Content content, T... actions) {
    this.content = content;
    addActions(actions);
  }

  public void addAction(T action) {
    this.actions.add(action);
    int bit = 1 << action.ordinal();
    this.actionBits |= bit;
  }

  public void addActions(Set<T> actions) {
    for (T action : actions) {
      addAction(action);
    }
  }

  public void addActions(T... actions) {
    for (T action : actions) {
      addAction(action);
    }
  }

  public Set<T> getActions() {
    return actions;
  }

  public Class<T> getActionType() {
    return actions.isEmpty() ? null : (Class<T>) actions.iterator().next().getClass();
  }

  public Content getContent() {
    return content;
  }

  public boolean implies(ContentPermission<?> p) {
    return this.content.equals(p.content) && ((this.actionBits & p.actionBits) == p.actionBits);
  }

  @Override
  public boolean implies(Permission p) {
    if (p instanceof ContentPermission) {
      implies((ContentPermission<?>) p);
    }
    return false;
  }

  public void setContent(Content content) {
    this.content = content;
  }

}
