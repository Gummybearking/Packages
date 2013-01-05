package packages.me.max.java.utils;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconUtils {
	
	public static Economy econ       = null;  
	public static ConfigUtils config = new ConfigUtils();
	
	/**
	 * Sets up permissions
	 * @return not important
	 */
	public boolean setupPermissions()
	{
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	    if (economyProvider != null) {
	    	econ = economyProvider.getProvider();
	    }
	    return (econ != null);
	}
	
	/** 
	 * TO-DO Create message support with the deadBeat() method
	 * Returns whether they payed, but also charges if they can
	 * @param p player to withdraw from
	 * @param amount amount to withdraw
	 */
	public static boolean withdraw(Player p, double amount) {
		if(!econ.has(p.getName(), amount)) {
			deadBeat(p);
			return false;
		}
		econ.withdrawPlayer(p.getName(), amount);
		return true;
	}
	/**
	 * 
	 * @param p
	 * @param i
	 */
	public static void deposit(Player p, int i) {
		econ.depositPlayer(p.getName(), i);
	}
	/**
	 * Silly player is poor
	 * @param p Player who ran out of money
	 */
	public static void deadBeat(Player p) {
		config.sendMessage(p, "Not-Enough-Money");
	}
}
