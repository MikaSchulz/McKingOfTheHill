package me.eyetealer.mckingofthehill.kingofthehill.configuration.property;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
public class BooleanProperty {

  @Getter
  private boolean enabled;

  public boolean toggle() {
    enabled = !enabled;
    return enabled;
  }

  public void enable() {
    enabled = true;
  }

  public void disable() {
    enabled = false;
  }
}