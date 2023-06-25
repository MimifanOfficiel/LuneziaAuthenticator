package fr.lunezia.luneziaauth.api;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.lunezia.luneziaauth.database.DatabaseQueries;
import fr.lunezia.luneziaauth.exceptions.LuneziaLoginException;
import fr.lunezia.luneziaauth.exceptions.LuneziaRegisterException;
import fr.lunezia.luneziaauth.exceptions.LuneziaResetException;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LuneziaPlayer {

    protected Player player;

    protected boolean isLogged;

    protected Location lastLocation, teleportedLocation;

    public LuneziaPlayer(Player player) {
        this.player = player;
        this.isLogged = false;
        lastLocation = player.getLocation();
        this.teleportedLocation = new Location(player.getPlayer().getWorld(), 0, 255, 0, 0 , 0);
    }

    public Player getPlayer() { return player; }

    public Location getLastLocation() { return lastLocation; }

    public boolean isLogged() { return isLogged; }

    public void setLogged(boolean logged) { isLogged = logged; }

    public boolean isRegistered() {
        return DatabaseQueries.playerExists(player.getUniqueId());
    }

    public void register(String password) throws LuneziaRegisterException {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        try {
            DatabaseQueries.createPlayer(player.getUniqueId(), hashedPassword);
        } catch (Exception e) {
            throw new LuneziaRegisterException();
        }
    }

    public void login(String password) throws LuneziaLoginException {
        String hashedPassword = DatabaseQueries.getPassword(this.getPlayer().getUniqueId());

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);

        if (!result.verified) throw new LuneziaLoginException();

        this.isLogged = true;
    }

    public void login() {
        player.teleport(getLastLocation());
        player.setFlying(false);
        player.setAllowFlight(false);
        isLogged = true;
    }

    public Location getTeleportedLocation() {
        return this.teleportedLocation;
    }

    public void reset() throws LuneziaResetException {
        try {
            DatabaseQueries.removePlayer(player.getUniqueId());
            this.setLogged(false);
            this.lastLocation = player.getLocation();
            player.teleport(getTeleportedLocation());
            player.setAllowFlight(true);
            player.setFlying(true);
        } catch (Exception e){
            throw new LuneziaResetException();
        }
    }
}
