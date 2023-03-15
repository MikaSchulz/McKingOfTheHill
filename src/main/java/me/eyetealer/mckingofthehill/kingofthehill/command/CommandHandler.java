package me.eyetealer.mckingofthehill.kingofthehill.command;

import me.eyetealer.mckingofthehill.kingofthehill.KingOfTheHill;
import me.eyetealer.mckingofthehill.kingofthehill.command.commands.SetupCommand;
import org.bukkit.command.PluginCommand;

public class CommandHandler {

  private final KingOfTheHill plugin;

  public CommandHandler(KingOfTheHill plugin) {
    this.plugin = plugin;
  }

  public void init() {
    getCommand("setup").setExecutor(new SetupCommand(plugin.getPluginProperties()));
  }

  private PluginCommand getCommand(String command) {
    PluginCommand pluginCommand = plugin.getCommand(command);
    if (pluginCommand == null) {
      throw new RuntimeException("Command pluginCommand=%s wasn't found in plugin.yml");
    }
    return pluginCommand;
  }
}
