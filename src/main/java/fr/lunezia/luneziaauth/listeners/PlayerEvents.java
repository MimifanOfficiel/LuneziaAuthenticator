package fr.lunezia.luneziaauth.listeners;

import fr.lunezia.luneziaauth.api.LuneziaPlayer;
import fr.lunezia.luneziaauth.managers.PlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;


public class PlayerEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        LuneziaPlayer luneziaPlayer = new LuneziaPlayer(player);

        luneziaPlayer.getPlayer().teleport(luneziaPlayer.getTeleportedLocation());
        player.setAllowFlight(true);
        player.setFlying(true);

        askForAuth(luneziaPlayer);

        PlayerManager.getInstance().register(luneziaPlayer);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PlayerManager manager = PlayerManager.getInstance();
        manager.unregister(manager.get(event.getPlayer()));

        player.setFlying(false);
        player.setAllowFlight(false);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        LuneziaPlayer lplayer = PlayerManager.getInstance().get(player);

        if (lplayer == null) return;
        if(lplayer.isLogged()) return;

        Location loginLocation = lplayer.getTeleportedLocation();
        Location playerLocation = player.getLocation();

        if (loginLocation.getBlockX() != playerLocation.getBlockX()
            || loginLocation.getBlockY() != playerLocation.getBlockY()
            || loginLocation.getBlockZ() != playerLocation.getBlockZ()) {
            player.teleport(loginLocation);

            askForAuth(lplayer);
        }

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        LuneziaPlayer luneziaPlayer = PlayerManager.getInstance().get(event.getPlayer());

        if (luneziaPlayer == null) return;
        if(luneziaPlayer.isLogged()) return;

        event.setCancelled(true);
        askForAuth(luneziaPlayer);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        LuneziaPlayer luneziaPlayer = PlayerManager.getInstance().get(event.getPlayer());
        if(luneziaPlayer.isLogged()) return;
        if(!event.getMessage().startsWith("/login") && !event.getMessage().startsWith("/register")) event.setCancelled(true);
    }

    private void askForAuth(LuneziaPlayer lplayer){
        if(!lplayer.isRegistered()) {
            lplayer.getPlayer().sendMessage("§cVeuillez créer un compte (/register <mot de passe> <confirmation>)");
            return;
        }

        lplayer.getPlayer().sendMessage("§cVeuillez vous identifier (/login <mot de passe>)");
    }



}
