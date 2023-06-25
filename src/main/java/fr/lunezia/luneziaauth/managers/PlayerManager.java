package fr.lunezia.luneziaauth.managers;

import fr.lunezia.luneziaauth.Main;
import fr.lunezia.luneziaauth.api.LuneziaPlayer;
import fr.lunezia.luneziaauth.listeners.PlayerEvents;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerManager {

    private static PlayerManager instance = new PlayerManager();

    private List<LuneziaPlayer> players = new ArrayList<>();

    public void load() {
        Main plugin = Main.getInstance();
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        Bukkit.getLogger().info("Initialisation des Events.");
        pluginManager.registerEvents(new PlayerEvents(), plugin);

        for(Player player : Bukkit.getOnlinePlayers()){
            LuneziaPlayer luneziaPlayer = new LuneziaPlayer(player);
            players.add(luneziaPlayer);
        }

    }

    public void unload() {
        players.clear();
    }

    public void register(LuneziaPlayer player) {
        if (players.contains(player)) { throw new IllegalArgumentException(); }
        players.add(player);
    }

    public void unregister(LuneziaPlayer player) { players.remove(player); }

    public List<LuneziaPlayer> getLogoutPlayers() {
        return Collections.unmodifiableList(this.players.stream().filter(luneziaPlayer -> !luneziaPlayer.isLogged()).collect(Collectors.toList()));
    }

    @Nullable
    public LuneziaPlayer get(Player player) {
        return this.players.stream().filter(p -> p.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public static PlayerManager getInstance() {
        return instance;
    }
}
