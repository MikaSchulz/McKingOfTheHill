package me.eyetealer.mckingofthehill.kingofthehill.command;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import cloud.commandframework.Command.Builder;
import cloud.commandframework.CommandTree;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.ParserParameters;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.execution.FilteringCommandSuggestionProcessor;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import java.util.List;
import java.util.function.Function;
import me.eyetealer.mckingofthehill.kingofthehill.KingOfTheHill;
import me.eyetealer.mckingofthehill.kingofthehill.configuration.PluginProperties;
import me.eyetealer.mckingofthehill.kingofthehill.utils.Messages;
import org.bukkit.command.CommandSender;

public class CommandHandler {

  private final KingOfTheHill plugin;
  private BukkitCommandManager<CommandSender> manager;
  private AnnotationParser<CommandSender> annotationParser;

  public CommandHandler(KingOfTheHill plugin) {
    this.plugin = plugin;
  }

  public void init() {

    Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>>
        executionCoordinatorFunction = CommandExecutionCoordinator.simpleCoordinator();
    try {
      manager = PaperCommandManager.createNative(plugin, executionCoordinatorFunction);
    } catch (final Exception e) {
      plugin.getConsoleSender().sendMessage(Messages.join());
      plugin.getServer().getPluginManager().disablePlugin(plugin);
      return;
    }

    manager.commandSuggestionProcessor(new FilteringCommandSuggestionProcessor<>(
        FilteringCommandSuggestionProcessor.Filter.<CommandSender>contains(true)
            .andTrimBeforeLastSpace()
    ));

    if (manager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
      manager.registerBrigadier();
    }
    if (manager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
      ((PaperCommandManager<CommandSender>) manager).registerAsynchronousCompletions();
    }
    final Function<ParserParameters, CommandMeta> commandMetaFunction = p ->
        CommandMeta.simple()
            .with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description"))
            .build();
    annotationParser = new AnnotationParser<>(manager, CommandSender.class,
        commandMetaFunction);
    annotationParser.parse(this);
    try {
      annotationParser.parseContainers();
    } catch (final Exception e) {
      e.printStackTrace();
    }
    List<Builder<CommandSender>> builderList = List.of(
        manager.commandBuilder("kingofthehill"),
        manager.commandBuilder("koth")
    );

    builderList.forEach(builder -> manager.command(builder.literal("setup")
            .handler(context -> manager.taskRecipe().begin(context)
                .synchronous(commandContext -> {
                  commandContext.getSender().sendMessage(Messages.join(miniMessage().deserialize(
                      "<yellow><bold>SETUP_MODE</bold> is <red><bold>%s".formatted(
                          getState(PluginProperties.SETUP_MODE.isEnabled())))));
                }).execute()))
        .command(builder.literal("setup")
            .literal("toggle")
            .handler(context -> manager.taskRecipe().begin(context)
                .synchronous(commandContext -> {
                  commandContext.getSender().sendMessage(Messages.join(miniMessage().deserialize(
                      "<yellow><bold>SETUP_MODE</bold> has been <red><bold>%s"
                          .formatted(getState(PluginProperties.SETUP_MODE.toggle())))));
                }).execute()))
        .command(builder.literal("setup")
            .literal("enable")
            .handler(context -> manager.taskRecipe().begin(context)
                .synchronous(commandContext -> {
                  PluginProperties.SETUP_MODE.enable();
                  commandContext.getSender().sendMessage(Messages.join(miniMessage().deserialize(
                      "<yellow><bold>SETUP_MODE</bold> has been <red><bold>enabled")));
                }).execute()))
        .command(builder.literal("setup")
            .literal("disable")
            .handler(context -> manager.taskRecipe().begin(context)
                .synchronous(commandContext -> {
                  PluginProperties.SETUP_MODE.disable();
                  commandContext.getSender().sendMessage(Messages.join(miniMessage().deserialize(
                      "<yellow><bold>SETUP_MODE</bold> has been <red><bold>disabled")));
                }).execute())));
  }

  private String getState(boolean value) {
    return value ? "enabled" : "disabled";
  }

}
