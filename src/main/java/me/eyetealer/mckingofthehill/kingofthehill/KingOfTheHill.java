package me.eyetealer.mckingofthehill.kingofthehill;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.Command;
import cloud.commandframework.CommandTree;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.ParserParameters;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.arguments.standard.IntegerArgument;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.bukkit.parsers.MaterialArgument;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.execution.FilteringCommandSuggestionProcessor;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import cloud.commandframework.types.tuples.Triplet;
import io.leangen.geantyref.TypeToken;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import me.eyetealer.mckingofthehill.kingofthehill.configuration.ConfigProvider;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.SqlInitializer;
import me.eyetealer.mckingofthehill.kingofthehill.gamestate.GameState;
import me.eyetealer.mckingofthehill.kingofthehill.gamestate.StateHandler;
import me.eyetealer.mckingofthehill.kingofthehill.utils.Messages;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

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

//    CommandHandler commandHandler = new CommandHandler(plugin);
//    commandHandler.init();

    StateHandler stateHandler = new StateHandler(plugin);

    stateHandler.setGameState(GameState.LOBBY);
  }

  private void testCloudCommand() {

    final Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction =
        CommandExecutionCoordinator.simpleCoordinator();
    try {
      this.manager = PaperCommandManager.createNative(this, executionCoordinatorFunction);
    } catch (final Exception e) {
      this.getLogger().severe("Failed to initialize the command this.manager");
      this.getServer().getPluginManager().disablePlugin(this);
      return;
    }

    this.manager.commandSuggestionProcessor(new FilteringCommandSuggestionProcessor<>(
        FilteringCommandSuggestionProcessor.Filter.<CommandSender>contains(true)
            .andTrimBeforeLastSpace()
    ));

    if (this.manager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
      this.manager.registerBrigadier();
    }
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
    this.annotationParser = new AnnotationParser<>(this.manager, CommandSender.class,
        commandMetaFunction);

    this.constructCommands();

  }

  private void constructCommands() {
    this.annotationParser.parse(this);
    try {
      this.annotationParser.parseContainers();
    } catch (final Exception e) {
      e.printStackTrace();
    }
    List<Command.Builder<CommandSender>> builderList = List.of(
        manager.commandBuilder("kingofthehill"),
        manager.commandBuilder("koth")
    );

    builderList.forEach(builder -> {

      this.manager.command(builder.literal("setup")
              .literal("me")
              .senderType(Player.class)
              .argumentTriplet(
                  "coords",
                  TypeToken.get(Vector.class),
                  Triplet.of("x", "y", "z"),
                  Triplet.of(Integer.class, Integer.class, Integer.class),
                  (sender, triplet) -> new Vector(triplet.getFirst(), triplet.getSecond(),
                      triplet.getThird()
                  ),
                  ArgumentDescription.of("Coordinates")
              )
              .handler(context -> this.manager.taskRecipe().begin(context)
                  .synchronous(commandContext -> {
                    final Player player = (Player) commandContext.getSender();
                    player.sendMessage(Messages.join(miniMessage().deserialize("TEST123")));
                  }).execute()))
          .command(builder.literal("give")
              .senderType(Player.class)
              .argument(MaterialArgument.of("material"))
              .argument(IntegerArgument.of("amount"))
              .handler(c -> {
                final Material material = c.get("material");
                final int amount = c.get("amount");
                final ItemStack itemStack = new ItemStack(material, amount);
                ((Player) c.getSender()).getInventory().addItem(itemStack);
                c.getSender().sendMessage("You've been given stuff, bro.");
              }));
    });
  }

  public void onDisable() {
  }
}
