package me.eyetealer.mckingofthehill.kingofthehill;

import cloud.commandframework.Command;
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
import java.util.function.Function;
import lombok.Getter;
import me.eyetealer.mckingofthehill.kingofthehill.command.CommandHandler;
import me.eyetealer.mckingofthehill.kingofthehill.configuration.ConfigProvider;
import me.eyetealer.mckingofthehill.kingofthehill.configuration.PluginProperties;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.SqlInitializer;
import me.eyetealer.mckingofthehill.kingofthehill.gamestate.GameState;
import me.eyetealer.mckingofthehill.kingofthehill.gamestate.StateHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class KingOfTheHill extends JavaPlugin {

  @Getter
  private ConfigProvider configProvider;
  private BukkitCommandManager<CommandSender> manager;
  private AnnotationParser<CommandSender> annotationParser;

  public void onEnable() {

    KingOfTheHill plugin = this;

    configProvider = new ConfigProvider(plugin);
    configProvider.init();

    SqlInitializer sqlInitializer = new SqlInitializer(plugin);
    sqlInitializer.init();

    testCloudCommand();

    CommandHandler commandHandler = new CommandHandler(plugin);
    commandHandler.init();

    StateHandler stateHandler = new StateHandler(plugin);

    stateHandler.setGameState(GameState.LOBBY);
  }

  private void testCloudCommand() {

     final Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction =
            CommandExecutionCoordinator.simpleCoordinator();
    //
    // This function maps the command sender type of our choice to the bukkit command sender.
    // However, in this example we use the Bukkit command sender, and so we just need to map it
    // to itself
    //
    final Function<CommandSender, CommandSender> mapperFunction = Function.identity();
    try {
      this.manager = new PaperCommandManager<>(
          /* Owning plugin */ this,
          /* Coordinator function */ executionCoordinatorFunction,
          /* Command Sender -> C */ mapperFunction,
          /* C -> Command Sender */ mapperFunction
      );
    } catch (final Exception e) {
      this.getLogger().severe("Failed to initialize the command this.manager");
      /* Disable the plugin */
      this.getServer().getPluginManager().disablePlugin(this);
      return;
    }

    // Use contains to filter suggestions instead of default startsWith
    this.manager.commandSuggestionProcessor(new FilteringCommandSuggestionProcessor<>(
        FilteringCommandSuggestionProcessor.Filter.<CommandSender>contains(true).andTrimBeforeLastSpace()
    ));

    //
    // Register Brigadier mappings
    //
    if (this.manager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
      this.manager.registerBrigadier();
    }
    //
    // Register asynchronous completions
    //
    if (this.manager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
      ((PaperCommandManager<CommandSender>) this.manager).registerAsynchronousCompletions();
    }
    //
    // Create the annotation parser. This allows you to define commands using methods annotated with
    // @CommandMethod
    //
    final Function<ParserParameters, CommandMeta> commandMetaFunction = p ->
        CommandMeta.simple()
            // This will allow you to decorate commands with descriptions
            .with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description"))
            .build();
    this.annotationParser = new AnnotationParser<>(
        /* Manager */ this.manager,
        /* Command sender type */ CommandSender.class,
        /* Mapper for command meta instances */ commandMetaFunction
    );
    //
    // Create the commands
    //
    this.constructCommands();

  }

  private void constructCommands() {
    //
    // Parse all @CommandMethod-annotated methods
    //
    this.annotationParser.parse(this);
    // Parse all @CommandContainer-annotated classes
    try {
      this.annotationParser.parseContainers();
    } catch (final Exception e) {
      e.printStackTrace();
    }
    //
    // Base command builder
    //
    final Command.Builder<CommandSender> builder = this.manager.commandBuilder("example");
  }

  public void onDisable() {
  }
}
