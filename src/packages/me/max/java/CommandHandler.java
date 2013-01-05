package packages.me.max.java;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import packages.me.max.java.objects.Package;

public class CommandHandler {
	public static HashMap<String, String[]> isSending = new HashMap<String, String[]>();
	
	public void send(Player sender, String[] args) {
		if(args.length >= 3) {
			boolean isChest = args[1].equalsIgnoreCase("chest");
			
			if(isChest)
				sendChest(sender,args);
			else 
				sendHand(sender,args);
		}
	}
	public void sendChest(Player sender,String[] args) {
		Player p        = Bukkit.getPlayer(args[2]);
		isSending.put(p.getName(), args);
	}
	public void sendHand(Player sender, String[] args) {
		Player p        = Bukkit.getPlayer(args[2]);
		ItemStack stack = p.getItemInHand();
		String Message  = ChatColor.translateAlternateColorCodes('&', "&7" + sender.getName() + " has sent you a package! to view it, type &8/Package read");
		if(args.length > 3) {
			Message = "&e[Packages]";
			for (int i = 0; i < args.length; i++) {
				if(i < 2) continue;
				if(i < args.length - 1) {
					Message = Message + " " + args[i];
				}
			}
		}
		ItemStack[] items = {stack};
		Package pack = new Package(items, sender, p, Message, 0);
		pack.save();
	}
	public void read(Player p) {
		p.sendMessage()
	}
}
