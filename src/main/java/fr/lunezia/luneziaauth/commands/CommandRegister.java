package fr.lunezia.luneziaauth.commands;

import fr.lunezia.luneziaauth.api.LuneziaPlayer;
import fr.lunezia.luneziaauth.exceptions.LuneziaRegisterException;
import fr.lunezia.luneziaauth.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRegister implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSeul un joueur peut exécuter cette commande.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            player.sendMessage("§cUsage: /register <mot de passe> <confirmation>");
            return true;
        }

        LuneziaPlayer lplayer = PlayerManager.getInstance().get(player);

        if (lplayer == null) {
            player.sendMessage("§cUne erreur est survenue lors de l'inscription. Veuillez vous déconnecter puis vous reconnecter.");
            return true;
        }

        if (lplayer.isRegistered()) {
            player.sendMessage("§cVous avez déjà un compte.");
            return true;
        }

        String password = args[0];
        String confirmation = args[1];

        if (!password.equals(confirmation)) {
            player.sendMessage("§cVos mots de passe ne sont pas identiques.");
            return true;
        }

        if(password.contains(player.getName())){
            player.sendMessage("§cVeuillez ne pas mettre votre pseudo dans le mot de passe.");
            return true;
        }

        if(args[0].length() < 6){
            player.sendMessage("§cVotre mot de passe doit contenir au moins 6 caractères.");
            return true;
        }

        try {
            lplayer.register(password);
            player.sendMessage("§aInscription effectuée, vous devez maintenant vous connecter.");
        } catch (LuneziaRegisterException e) {
            player.sendMessage("§cImpossible de créer votre compte, veuillez réessayer.");
        }

        return true;
    }
}
