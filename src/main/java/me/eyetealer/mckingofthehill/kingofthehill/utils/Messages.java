package me.eyetealer.mckingofthehill.kingofthehill.utils;

import java.util.Arrays;
import java.util.stream.Stream;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;

public class Messages {

  public static final JoinConfiguration SPACE_SEPERATED = JoinConfiguration.separator(
      Component.text(" "));
  public static final Component JOIN_MESSAGE = Component.text("joined the game",
      NamedTextColor.YELLOW);
  public static final Component QUIT_MESSAGE = Component.text("left the game",
      NamedTextColor.YELLOW);
  private static final Component OPEN_BRACKET = Component.text("[", NamedTextColor.GRAY);
  private static final Component CLOSE_BRACKET = Component.text("]", NamedTextColor.GRAY);
  private static final String GAME_NAME = "KingOfTheHill";
  private static final Component PREFIX = OPEN_BRACKET
      .append(Component.text(GAME_NAME).color(NamedTextColor.AQUA))
      .append(CLOSE_BRACKET);

  public static Component join(ComponentLike... componentLikes) {
    ComponentLike[] componentLikesWithPrefix
        = Stream.concat(Stream.of(PREFIX), Arrays.stream(componentLikes))
        .toArray(ComponentLike[]::new);
    return Component.join(SPACE_SEPERATED, componentLikesWithPrefix);
  }

}
