package fr.lunezia.luneziaauth;

import fr.lunezia.luneziaauth.api.LuneziaPlayer;
import fr.lunezia.luneziaauth.commands.CommandLogin;
import fr.lunezia.luneziaauth.commands.lauth.CommandLuneziaAuth;
import fr.lunezia.luneziaauth.commands.CommandRegister;
import fr.lunezia.luneziaauth.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import java.util.logging.Logger;


public final class Main extends JavaPlugin {

    private static Main instance = null;

    @Override
    public void onEnable() {
        instance = this;

        Logger logger = getLogger();
        PluginManager pluginManager = Bukkit.getPluginManager();
        logger.info("Démarrage du plugin d'Authentification de Lunezia.");

        saveDefaultConfig();
        logger.info("Initialisation des commandes.");
        getCommand("login").setExecutor(new CommandLogin());
        getCommand("register").setExecutor(new CommandRegister());
        getCommand("lauth").setExecutor(new CommandLuneziaAuth());

        PlayerManager.getInstance().load();

        // TODO REMOVE THAT
        Player loti = Bukkit.getPlayer("Lotifiz"); // TODO REMOVE THAT
        Player ro = Bukkit.getPlayer("82_111_109_49"); // TODO REMOVE THAT
        LuneziaPlayer lotilun = PlayerManager.getInstance().get(loti); // TODO REMOVE THAT
        LuneziaPlayer rolun = PlayerManager.getInstance().get(ro); // TODO REMOVE THAT
        lotilun.setLogged(true); // TODO REMOVE THAT
        rolun.setLogged(true); // TODO REMOVE THAT
        // TODO REMOVE THAT, YES ALL OF THIS
    }

    @Override
    public void onDisable() {
        PlayerManager.getInstance().unload();

        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }

}
