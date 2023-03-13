package me.eyetealer.mckingofthehill.kingofthehill;

import me.eyetealer.mckingofthehill.kingofthehill.configuration.ConfigProvider;
import me.eyetealer.mckingofthehill.kingofthehill.gamestate.GameState;
import me.eyetealer.mckingofthehill.kingofthehill.gamestate.StateHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class KingOfTheHill extends JavaPlugin {

  private KingOfTheHill plugin;
  private StateHandler stateHandler;

  public void onEnable() {

    plugin = this;

    ConfigProvider.init(plugin);

    stateHandler = new StateHandler(plugin);

    stateHandler.setGameState(GameState.LOBBY);
  }

  public void onDisable() {
  }
}
