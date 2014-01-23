package com.isoftstone.smart.core.entity;

import java.util.HashSet;
import java.util.Set;

public enum PlaceRole implements Role {

  // 访客
  GUEST(PlaceAction.READ),

  // 读者
  READER(PlaceRole.GUEST, PlaceAction.READ),

  // 贡献者
  CONTRIBUTOR(PlaceRole.READER, PlaceAction.ADD, PlaceAction.EDIT, PlaceAction.EDIT_OWN, PlaceAction.DELETE, PlaceAction.DELETE_OWN),

  // 管理者
  MANAGER(CONTRIBUTOR, PlaceAction.GRANT);

  private final Set<Action> allowableActions;

  private PlaceRole(PlaceAction... actions) {
    allowableActions = new HashSet<>();
    for (PlaceAction action : actions) {
      allowableActions.add(action);
    }
  }

  private PlaceRole(PlaceRole role, Action... actions) {
    allowableActions = new HashSet<>();
    allowableActions.addAll(role.getAllowableActions());
    for (Action action : actions) {
      allowableActions.add(action);
    }
  }

  @Override
  public Set<Action> getAllowableActions() {
    return allowableActions;
  }

  @Override
  public String getId() {
    return name();
  }

  @Override
  public String toString() {
    return getClass().getName() + "@" + name();
  }

}
