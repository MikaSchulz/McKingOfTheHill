package me.eyetealer.mckingofthehill.kingofthehill.lobby;

import static me.eyetealer.mckingofthehill.kingofthehill.utils.Messages.JOIN_MESSAGE;
import static me.eyetealer.mckingofthehill.kingofthehill.utils.Messages.QUIT_MESSAGE;
import static me.eyetealer.mckingofthehill.kingofthehill.utils.Messages.join;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    e.joinMessage(join(e.getPlayer().displayName(), JOIN_MESSAGE));
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    e.quitMessage(join(e.getPlayer().displayName(), QUIT_MESSAGE));
  }

}
