package me.eyetealer.mckingofthehill.kingofthehill.configuration.property;

import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class BooleanProperty {

  private boolean property;

  public boolean toggle() {
    property = !property;
    return property;
  }

  public void enable() {
    property = true;
  }

  public void disable() {
    property = false;
  }
}