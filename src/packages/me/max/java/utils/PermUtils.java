package packages.me.max.java.utils;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PermUtils {
	public static Permission permission = null;  
	/**
	 * Sets up permissions
	 * @return
	 */
	public boolean setupPermissions()
	{
		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
	    if (permissionProvider != null) {
	    	permission = permissionProvider.getProvider();
	    }
	    return (permission != null);
	}
	/**
	 * Uses Players to check for permissions
	 * @param p Player to check
	 * @param perm Permission required
	 * @return
	 */
	public boolean hasPermission(Player p, String perm) {
		return permission.has(p, "Packages." + perm);
	}
	/**
	 * Uses names instead of players
	 * @param name Player's name
	 * @param perm Permission required
	 * @return
	 */
	public boolean hasPermission(String name, String perm) {
		Player p = Bukkit.getPlayer(name);
		return permission.has(p, "Packages." + perm);
	}
}
