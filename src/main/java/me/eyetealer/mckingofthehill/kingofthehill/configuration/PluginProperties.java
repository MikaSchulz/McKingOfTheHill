package me.eyetealer.mckingofthehill.kingofthehill.configuration;

import lombok.Getter;
import lombok.Setter;

public class PluginProperties {

  @Getter
  @Setter
  private BooleanProperty setupMode = BooleanProperty.of(Boolean.FALSE);

  private static class Property<T> {

    @Getter
    @Setter
    private T property;

    public Property(T property) {
      this.property = property;
    }

    public static <T> Property<T> of(T property) {
      return new Property<T>(property);
    }
  }

//  @AllArgsConstructor(staticName = "of")
  private static class BooleanProperty {

    public static BooleanProperty of(Boolean property) {
      return new BooleanProperty(property);
    }

    public BooleanProperty(Boolean property) {
      super(property);
    }

    public void toggle() {
      setProperty(!getProperty());
    }

    public void enable() {
      setProperty(Boolean.TRUE);
    }

    public void disable() {
      setProperty(Boolean.FALSE);
    }
  }

}
