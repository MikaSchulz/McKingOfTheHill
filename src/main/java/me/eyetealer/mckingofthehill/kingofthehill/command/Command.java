package me.eyetealer.mckingofthehill.kingofthehill.command;

import cloud.commandframework.Command.Builder;
import cloud.commandframework.bukkit.BukkitCommandManager;
import org.bukkit.command.CommandSender;

public abstract class Command {


  protected final BukkitCommandManager<CommandSender> manager;

  public Command(BukkitCommandManager<CommandSender> manager) {
    this.manager = manager;
  }

  public abstract void registerCommand(Builder<CommandSender> commandBuilder);

}
