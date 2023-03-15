package me.eyetealer.mckingofthehill.kingofthehill.command.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetupCommand implements CommandExecutor {

  /**
   * Command: setup
   * Arguments: -
   * Permission: kingofthehill.setup
   */
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {

    return true;
  }
}
