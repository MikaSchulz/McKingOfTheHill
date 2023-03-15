package me.eyetealer.mckingofthehill.kingofthehill.configuration;

import lombok.Getter;
import me.eyetealer.mckingofthehill.kingofthehill.KingOfTheHill;

public class ConfigProvider {

  private static final String FILE_NAME = "config.yml";
  @Getter
  private ConfigFile configFile;
  private final KingOfTheHill plugin;

  public ConfigProvider(KingOfTheHill plugin) {
    this.plugin = plugin;
  }

  public void init() {
    configFile = new ConfigFile(plugin, FILE_NAME);
    configFile.addDefault("sql.driver", "mariadb");
    configFile.addDefault("sql.host", "127.0.0.1");
    configFile.addDefault("sql.port", 3306);
    configFile.addDefault("sql.user", "dummyUser");
    configFile.addDefault("sql.password", "dummyPassword");
    configFile.addDefault("sql.database", "dummyDatabase");
    configFile.create();
    configFile.load();
  }
}
