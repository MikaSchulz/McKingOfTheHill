package me.eyetealer.mckingofthehill.kingofthehill.configuration;

import lombok.Getter;
import me.eyetealer.mckingofthehill.kingofthehill.KingOfTheHill;

public class ConfigProvider {

  private static final String FILE_NAME = "config.yml";
  @Getter
  private static ConfigFile configFile;

  public static void init(KingOfTheHill plugin) {
    configFile = new ConfigFile(plugin, FILE_NAME);
    configFile.addDefault("sql.driver", "mariadb");
    configFile.addDefault("sql.host", "127.0.0.1");
    configFile.addDefault("sql.port", 3306);
    configFile.addDefault("sql.user", "dummyuser");
    configFile.addDefault("sql.password", "dummyPassword");
    configFile.addDefault("sql.database", "dummyDatabase");
    configFile.create();
    configFile.load();
  }
}
