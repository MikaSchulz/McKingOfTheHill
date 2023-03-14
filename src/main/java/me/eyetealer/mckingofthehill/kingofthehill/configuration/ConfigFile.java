package me.eyetealer.mckingofthehill.kingofthehill.configuration;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigFile {

  private final Map<String, Object> defaults;
  private final File directory;
  private final File file;
  @Getter
  private FileConfiguration config;

  public ConfigFile(JavaPlugin plugin, String fileName) {
    defaults = new LinkedHashMap<>();
    directory = new File("plugins/" + plugin.getName());
    file = new File(directory, fileName);
  }

  public void create() {
    if (file.exists()) {
      return;
    }
    if (!directory.exists()) {
      directory.mkdir();
    }
    createConfig();
  }

  public void addDefault(String key, Object value) {
    defaults.put(key, value);
  }

  public void addDefaults(Map<String, Object> defaultMap) {
    defaults.putAll(defaultMap);
  }

  private void setDefaults() {
    defaults.forEach((key, value) -> config.set(key, value));
  }

  public void save() {
    try {
      config.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void load() {
    config = YamlConfiguration.loadConfiguration(file);
  }

  public void createFile() {
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createConfig() {
    createFile();
    load();
    setDefaults();
    save();
  }

  public void deleteFile() {
    file.delete();
  }

}
