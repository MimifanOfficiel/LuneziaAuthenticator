package fr.lunezia.luneziaauth.commands.lauth;

import org.bukkit.command.CommandSender;

public class LuneziaAuthHelp {

    public static void sendHelp(CommandSender sender){
        sender.sendMessage("§7------------- §fLunezia Auth §7-------------\n" +
                           "\n" +
                           "§7/lauth login §a<pseudo>§7 - Force un joueur à se connecter sans son mot de passe.\n" +
                           "§7/lauth reset §a<pseudo>§7 - Réinitialise le compte d'un joueur.\n" +
                           "\n" +
                           "§7----------------------------------------");

    }

}
