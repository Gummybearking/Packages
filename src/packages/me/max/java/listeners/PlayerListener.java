package packages.me.max.java.listeners;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Chest;

import packages.me.max.java.CommandHandler;
import packages.me.max.java.utils.PackageUtils;
import packages.me.max.java.objects.Package;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Block b = e.getBlock();
		if(b.getState() instanceof Chest){
			if(
					b.getWorld().getBlockAt(b.getX(), b.getY()-1, b.getZ()).getType() == Material.FENCE
					||
					b.getWorld().getBlockAt(b.getX(), b.getY()-1, b.getZ()).getType() == Material.NETHER_FENCE
					||
					b.getWorld().getBlockAt(b.getX(), b.getY()-1, b.getZ()).getType() == Material.COBBLE_WALL
			){
				PackageUtils packu = new PackageUtils();
				packu.getPackConfig().set("MailBoxes." + e.getPlayer().getName(), b.getX() + "," + b.getY() + "," + b.getX());
			}
				
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p    = e.getPlayer();
		String name = p.getName();
		if(CommandHandler.isSending.containsKey(name)) {
			BlockState b = e.getClickedBlock().getState();
			if(b instanceof Chest) {
				Chest chest   = (Chest) b;
				String[] args = CommandHandler.isSending.get(name);
				String Message  = ChatColor.translateAlternateColorCodes('&', "&7" + p.getName() + " has sent you a package! to view it, type &8/Package read");
				if(args.length > 3) {
					Message = "&e[Packages]";
					for (int i = 0; i < args.length; i++) {
						if(i < 2) continue;
						if(i < args.length - 1) {
							Message = Message + " " + args[i];
						}
					}
				}
				Package pack  = new Package(chest.getBlockInventory().getContents(), p, Bukkit.getPlayer(args[2]), Message, 0);
				pack.save();
			}
		}
	}
}