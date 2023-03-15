package me.eyetealer.mckingofthehill.kingofthehill.command.commands;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import me.eyetealer.mckingofthehill.kingofthehill.configuration.PluginProperties;
import me.eyetealer.mckingofthehill.kingofthehill.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class KothCommand implements CommandExecutor {

  // Command: setup
  // Arguments: -
  // Permission: kingofthehill.setup
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {
    boolean isEnabled = PluginProperties.SETUP_MODE.toggle();
    sender.sendMessage(Messages.join(miniMessage().deserialize(
            "<yellow><bold>SETUP_MODE</bold> has been set to <red><bold>%s".formatted(isEnabled))));
    return true;
  }
}
