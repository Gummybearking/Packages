package packages.me.max.java.utils;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import packages.me.max.java.Packages;

public class ConfigUtils {
	public Packages   plugin             = new Packages();
	FileConfiguration config             = plugin.getConfig();
	
	public ConfigurationSection Options  = config.getConfigurationSection("Options");
	public ConfigurationSection Messages = config.getConfigurationSection("Messages");
	
	public String getOptionS(String s) {
		return Options.getString(s);
	}
	public int getOptionI(String s) {
		return Options.getInt(s);
	}
	public void sendMessage(Player p, String s) {
		String message = ChatColor.translateAlternateColorCodes('&', Messages.getString(s));
		p.sendMessage(message);
	}
}
