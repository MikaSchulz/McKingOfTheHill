package me.eyetealer.mckingofthehill.kingofthehill.command.commands;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.Command.Builder;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.bukkit.BukkitCommandManager;
import me.eyetealer.mckingofthehill.kingofthehill.command.Command;
import me.eyetealer.mckingofthehill.kingofthehill.utils.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationCommand extends Command {

  public LocationCommand(BukkitCommandManager<CommandSender> manager) {
    super(manager);
  }

  @Override
  public void registerCommand(Builder<CommandSender> commandBuilder) {

    CommandArgument<CommandSender, String> getLocationNameArgument = StringArgument.of("name");
    CommandArgument<CommandSender, String> setLocationNameArgument = StringArgument.of("name");
    ArgumentDescription locationNameArgumentDescription = ArgumentDescription.of("Location Name");

    manager.command(commandBuilder.literal("location")
            // /koth location set <name>
            .literal("set")
            .senderType(Player.class)
            .argument(getLocationNameArgument, locationNameArgumentDescription)
            .handler(context -> manager.taskRecipe().begin(context)
                .synchronous(commandContext -> {
                  CommandSender sender = commandContext.getSender();
                  String locationName = commandContext.get(getLocationNameArgument);
                  sender.sendMessage(Messages.join(miniMessage().deserialize(
                      locationName)));
                }).execute()))
        .command(commandBuilder.literal("location")
            // /koth location get <name>
            .literal("get")
            .senderType(Player.class)
            .argument(setLocationNameArgument, locationNameArgumentDescription)
            .handler(context -> manager.taskRecipe().begin(context)
                .synchronous(commandContext -> {
                  CommandSender sender = commandContext.getSender();
                  String locationName = commandContext.get(setLocationNameArgument);
                  sender.sendMessage(Messages.join(miniMessage().deserialize(
                      locationName)));
                }).execute()));

  }
}
