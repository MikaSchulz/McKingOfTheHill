package me.eyetealer.mckingofthehill.kingofthehill.command;

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
import me.eyetealer.mckingofthehill.kingofthehill.command.commands.LocationCommand;
import me.eyetealer.mckingofthehill.kingofthehill.command.commands.SetupCommand;
import me.eyetealer.mckingofthehill.kingofthehill.utils.Messages;
import org.bukkit.command.CommandSender;

public class CommandHandler {

  private final KingOfTheHill plugin;
  private BukkitCommandManager<CommandSender> manager;

  public CommandHandler(KingOfTheHill plugin) {
    this.plugin = plugin;
  }

  public void init() {
    registerCommandManager();
    registerCommands();
  }

  public void registerCommandManager() {

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
    AnnotationParser<CommandSender> annotationParser = new AnnotationParser<>(manager,
        CommandSender.class,
        commandMetaFunction);
    annotationParser.parse(this);
    try {
      annotationParser.parseContainers();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public void registerCommands() {

    List<Builder<CommandSender>> builderList = List.of(
        manager.commandBuilder("kingofthehill"),
        manager.commandBuilder("koth")
    );

    List<Command> commands = List.of(
        new SetupCommand(manager),
        new LocationCommand(manager)
    );

    commands.forEach(command -> builderList.forEach(command::registerCommand));

  }

}
