package fr.lunezia.luneziaauth.commands.lauth;

import fr.lunezia.luneziaauth.api.LuneziaPlayer;
import fr.lunezia.luneziaauth.exceptions.LuneziaResetException;
import fr.lunezia.luneziaauth.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLuneziaAuth implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                LuneziaAuthHelp.sendHelp(sender);
            }
        }

        if(args.length == 2){
            if(args[0].equalsIgnoreCase("login")){
                Player player = Bukkit.getPlayer(args[1]);
                LuneziaPlayer luneziaPlayer = PlayerManager.getInstance().get(player);

                if (!player.isOnline() || luneziaPlayer == null) {
                    sender.sendMessage("§cCe joueur n'est pas en ligne.");
                    return true;
                }

                if (luneziaPlayer.isLogged()) {
                    sender.sendMessage("§cCe joueur est déjà identifié.");
                    return true;
                }

                luneziaPlayer.login();
                sender.sendMessage("§aLe joueur §2" + args[1] + " §aest maintenant connecté !");
                player.sendMessage("§aConnexion réussie, amusez-vous bien !");
            } else if (args[0].equalsIgnoreCase("reset")) {
                Player player = Bukkit.getPlayer(args[1]);

                LuneziaPlayer luneziaPlayer = PlayerManager.getInstance().get(player);

                if (luneziaPlayer == null) {
                    luneziaPlayer = new LuneziaPlayer(player);
                }

                if (!luneziaPlayer.isRegistered()) {
                    sender.sendMessage("§cCe joueur n'as pas de compte.");
                    return true;
                }

                try {
                    luneziaPlayer.reset();
                    sender.sendMessage("§aLe compte du joueur à été réinitialisé.");
                } catch (LuneziaResetException e) {
                    sender.sendMessage("§cUne erreur est survenue lors de la réinitialisation du compte.");
                }

            }
        }

        return true;
    }
}
