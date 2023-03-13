package me.eyetealer.mckingofthehill.kingofthehill.gamestate;

import java.util.HashMap;
import me.eyetealer.mckingofthehill.kingofthehill.KingOfTheHill;
import me.eyetealer.mckingofthehill.kingofthehill.ingame.IngameListener;
import me.eyetealer.mckingofthehill.kingofthehill.lobby.JoinListener;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class StateHandler {

  private static final HashMap<GameState, Listener> listener = new HashMap<>();

  static {
    listener.put(GameState.LOBBY, new JoinListener());
    listener.put(GameState.INGAME, new IngameListener());
  }

  private final KingOfTheHill plugin;

  public StateHandler(KingOfTheHill plugin) {
    this.plugin = plugin;
  }

  public void setGameState(GameState gameState) {
    listener.entrySet().stream()
        .filter(entry -> entry.getKey() == gameState)
        .forEach(entry -> plugin.getServer().getPluginManager().registerEvents(entry.getValue(), plugin));
    listener.entrySet().stream()
        .filter(entry -> entry.getKey() != gameState)
        .forEach(entry -> HandlerList.unregisterAll(entry.getValue()));
  }
}
