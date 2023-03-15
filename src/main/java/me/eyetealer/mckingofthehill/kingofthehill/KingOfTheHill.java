package me.eyetealer.mckingofthehill.kingofthehill;

import lombok.Getter;
import me.eyetealer.mckingofthehill.kingofthehill.command.CommandHandler;
import me.eyetealer.mckingofthehill.kingofthehill.configuration.ConfigProvider;
import me.eyetealer.mckingofthehill.kingofthehill.configuration.PluginProperties;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.SqlInitializer;
import me.eyetealer.mckingofthehill.kingofthehill.gamestate.GameState;
import me.eyetealer.mckingofthehill.kingofthehill.gamestate.StateHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class KingOfTheHill extends JavaPlugin {

  @Getter
  private ConfigProvider configProvider;
  @Getter
  private PluginProperties pluginProperties;

  public void onEnable() {

    KingOfTheHill plugin = this;

    pluginProperties = new PluginProperties();

    configProvider = new ConfigProvider(plugin);
    configProvider.init();

    SqlInitializer sqlInitializer = new SqlInitializer(plugin);
    sqlInitializer.init();

    CommandHandler commandHandler = new CommandHandler(plugin);
    commandHandler.init();

    StateHandler stateHandler = new StateHandler(plugin);

    stateHandler.setGameState(GameState.LOBBY);
  }

  public void onDisable() {
  }
}
