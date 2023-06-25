package fr.lunezia.luneziaauth.commands;

import fr.lunezia.luneziaauth.api.LuneziaPlayer;
import fr.lunezia.luneziaauth.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandLogin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSeul un joueur peut exécuter cette commande.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("§cUsage: /login <mot de passe>");
            return true;
        }

        LuneziaPlayer lplayer = PlayerManager.getInstance().get(player);

        if(lplayer.isLogged()) {
            player.sendMessage("§cVous êtes déjà connecté !");
            return true;
        }

        if (lplayer == null) {
            player.sendMessage("§cUne erreur est survenue lors de l'identification. Veuillez vous déconnecter puis vous reconnecter.");
            return true;
        }

        if (!lplayer.isRegistered()) {
            player.sendMessage("§cVous n'avez pas de compte, veuillez vous enregistrer.");
            return true;
        }

        String password = args[0];

        try {
            lplayer.login(password);
            lplayer.login();
            player.sendMessage("§aConnexion réussie, amusez-vous bien !");
        } catch (Exception e) {
            player.sendMessage("§cMot de passe incorrect, veuillez réessayer.");
        }
        return true;
    }
}
