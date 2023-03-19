package me.eyetealer.mckingofthehill.kingofthehill;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.Command;
import cloud.commandframework.arguments.standard.IntegerArgument;
import cloud.commandframework.bukkit.parsers.MaterialArgument;
import cloud.commandframework.types.tuples.Triplet;
import io.leangen.geantyref.TypeToken;
import java.util.List;
import lombok.Getter;
import me.eyetealer.mckingofthehill.kingofthehill.command.CommandHandler;
import me.eyetealer.mckingofthehill.kingofthehill.configuration.ConfigProvider;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.SqlInitializer;
import me.eyetealer.mckingofthehill.kingofthehill.gamestate.GameState;
import me.eyetealer.mckingofthehill.kingofthehill.gamestate.StateHandler;
import me.eyetealer.mckingofthehill.kingofthehill.utils.Messages;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class KingOfTheHill extends JavaPlugin {

  @Getter
  private ConfigProvider configProvider;

  public void onEnable() {

    KingOfTheHill plugin = this;

    configProvider = new ConfigProvider(plugin);
    configProvider.init();

    SqlInitializer sqlInitializer = new SqlInitializer(plugin);
    sqlInitializer.init();

    CommandHandler commandHandler = new CommandHandler(plugin);
    commandHandler.init();

    StateHandler stateHandler = new StateHandler(plugin);

    stateHandler.setGameState(GameState.LOBBY);
  }

  public void onDisable() {
  }

  public ConsoleCommandSender getConsoleSender() {
    return this.getServer().getConsoleSender();
  }
}
