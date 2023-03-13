package me.eyetealer.mckingofthehill.kingofthehill.configuration;

import lombok.Getter;
import me.eyetealer.mckingofthehill.kingofthehill.KingOfTheHill;

public class ConfigProvider {

  private static final String FILE_NAME = "config.yml";
  @Getter
  private static ConfigFile configFile;

  public static void init(KingOfTheHill plugin) {
    configFile = new ConfigFile(plugin, FILE_NAME);
    configFile.addDefault("testKey", "testValue");
    configFile.init();
    configFile.load();
  }
}
