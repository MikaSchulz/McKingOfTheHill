package me.eyetealer.mckingofthehill.kingofthehill.command.commands;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import cloud.commandframework.Command.Builder;
import cloud.commandframework.bukkit.BukkitCommandManager;
import me.eyetealer.mckingofthehill.kingofthehill.command.Command;
import me.eyetealer.mckingofthehill.kingofthehill.configuration.PluginProperties;
import me.eyetealer.mckingofthehill.kingofthehill.utils.Messages;
import org.bukkit.command.CommandSender;

public class SetupCommand extends Command {

  public SetupCommand(BukkitCommandManager<CommandSender> manager) {
    super(manager);
  }

  public void registerCommand(Builder<CommandSender> commandBuilder) {

    manager.command(commandBuilder.literal("setup")
            .handler(context -> manager.taskRecipe().begin(context)
                .synchronous(commandContext -> {
                  commandContext.getSender().sendMessage(Messages.join(miniMessage().deserialize(
                      "<yellow><bold>SETUP_MODE</bold> is <red><bold>%s".formatted(
                          getState(PluginProperties.SETUP_MODE.isEnabled())))));
                }).execute()))
        .command(commandBuilder.literal("setup")
            .literal("toggle")
            .handler(context -> manager.taskRecipe().begin(context)
                .synchronous(commandContext -> {
                  commandContext.getSender().sendMessage(Messages.join(miniMessage().deserialize(
                      "<yellow><bold>SETUP_MODE</bold> has been <red><bold>%s"
                          .formatted(getState(PluginProperties.SETUP_MODE.toggle())))));
                }).execute()))
        .command(commandBuilder.literal("setup")
            .literal("enable")
            .handler(context -> manager.taskRecipe().begin(context)
                .synchronous(commandContext -> {
                  PluginProperties.SETUP_MODE.enable();
                  commandContext.getSender().sendMessage(Messages.join(miniMessage().deserialize(
                      "<yellow><bold>SETUP_MODE</bold> has been <red><bold>enabled")));
                }).execute()))
        .command(commandBuilder.literal("setup")
            .literal("disable")
            .handler(context -> manager.taskRecipe().begin(context)
                .synchronous(commandContext -> {
                  PluginProperties.SETUP_MODE.disable();
                  commandContext.getSender().sendMessage(Messages.join(miniMessage().deserialize(
                      "<yellow><bold>SETUP_MODE</bold> has been <red><bold>disabled")));
                }).execute()));
  }

  private String getState(boolean value) {
    return value ? "enabled" : "disabled";
  }
}
