package packages.me.max.java.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import packages.me.max.java.DataManager;
import packages.me.max.java.Packages;
import packages.me.max.java.objects.Package;
import packages.me.max.java.objects.Package.PackageType;

public class PackageUtils {
	private FileConfiguration PackageConfig = null;
	private File PackageFile                = null;
	public Packages plugin                  = new Packages();
	private List<String> packageNames       = null;
	private List<String> MailBoxes          = null;
	private List<Package> Packages          = null;
	public PackageUtils(){
		packageNames = new LinkedList<String>();
		MailBoxes    = new LinkedList<String>();
		Packages     = new LinkedList<Package>();

		for(String s : getPackConfig().getKeys(true)) {
			if(s.startsWith("Package.")){
				if(s.endsWith("pack")){
					packageNames.add(s.replace("Package.", ""));
					Packages.add(packageFromConfig(getPackConfig().getConfigurationSection(s), s.replace("Package.", "")));
				}
			}else if(s.startsWith("MailBoxes.")) {
				String toAdd = s.replace("MailBoxes.", "");
				if(!MailBoxes.contains(toAdd)) {
					MailBoxes.add(toAdd);
				}
			}
		}
	}
	public void save(Package pack) {
		FileConfiguration c = getPackConfig();
		ConfigurationSection cs = c.getConfigurationSection(pack.getID());
		cs.set("Price",   pack.getPrice());
		cs.set("Message", pack.getMessage());
		cs.set("To",      pack.getTo().getName());
		cs.set("From",    pack.getFrom().getName());
		saveItemsToConfig(pack.getInv(), cs);
	}
	/**
	 * Reload my config for Packages
	 */
	public void reloadPackageConfig() {
	    if (PackageFile == null) {
	    PackageFile = new File(plugin.getDataFolder(), "Packages.yml");
	    }
	    PackageConfig = YamlConfiguration.loadConfiguration(PackageFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource("Packages.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        PackageConfig.setDefaults(defConfig);
	    }
	}
	/** 
	 * 
	 * @return the config for packages
	 */
	public FileConfiguration getPackConfig() {
	    if (PackageConfig == null) {
	        this.reloadPackageConfig();
	    }
	    return PackageConfig;
	}
	/**
	 * Saves my custom config
	 */
	public void saveCustomConfig() {
	    if (PackageConfig == null || PackageFile == null) {
	    return;
	    }
	    try {
	        getPackConfig().save(PackageFile);
	    } catch (IOException ex) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + PackageFile, ex);
	    }
	}
	public List<String> getNameList() {
		return this.packageNames;
	}
	public List<String> getMailBoxes() {
		return this.MailBoxes;
	}
	/**
	 * 
	 * @param cpack
	 * @param ID
	 * @return
	 */
	public Package packageFromConfig(ConfigurationSection cpack, String ID) {
		int         price     = cpack.getInt("Price");
		Player      giver     = Bukkit.getPlayer(cpack.getString("From"));
		Player      reciever  = Bukkit.getPlayer(cpack.getString("To"));
		String      message   = cpack.getString("Message");
		ItemStack[] stack     = getItemsForPack(cpack.getConfigurationSection("Contents"));
		
		return new Package(stack, giver, reciever, message, price);
	}
	public Chest getPlayerMailBox(Player p) {
		return (Chest) DataManager.StringToLocation(getPackConfig().getString("MailBoxes" + p.getName())).getBlock().getState();
	}
	public static ItemStack[] getItemsForPack(ConfigurationSection cs) {
		List<ItemStack> stack = new LinkedList<ItemStack>();
		for(String s : cs.getKeys(true)) {
			if(s.endsWith(".")) continue;
			stack.add(getItemFromConfig(cs.getConfigurationSection(s), s));
		}
		return (ItemStack[]) stack.toArray();
	}
	public static ItemStack getItemFromConfig(ConfigurationSection cs, String name) {
		ItemStack stack = new ItemStack(Material.getMaterial(name), 1);
		
		String Damage   = cs.getString("Damage");
		int Amount      = cs.getInt("Amount");
		stack.setAmount(Amount);
		stack.setDurability(Short.valueOf(Damage));
		
		String Enchants = cs.getString("Enchants");
		if(Enchants == null) return stack;
		String[] ench   = Enchants.split("-");
		for(String s : ench) {
			String[] e = s.split("=");
			stack.addEnchantment(Enchantment.getByName(e[0]), Integer.valueOf(e[1]));
		}
		
		return stack;
	}
	public void saveItemsToConfig(ItemStack[] i, ConfigurationSection section) {
		ConfigurationSection cs = section.getConfigurationSection("Contents");
		for(ItemStack stack : i) {
			ConfigurationSection is = cs.getConfigurationSection(stack.getType().toString());
			if(cs.getKeys(true).contains(stack.getType().toString() + "." )) {
				is = cs.getConfigurationSection(getNameEdit(cs,stack.getType().toString()));
			}
			if(is.get("Amount") == null) {
				is.set("Amount", stack.getAmount());
			}else{
				is.set("Amount", is.getInt("Amount") + stack.getAmount());
			}
			
			is.set("Enchants", getEnchants(stack));
		}
	}
	public String getEnchants(ItemStack i) {
		String enchants = "";
		for(Enchantment e : i.getEnchantments().keySet()) {
			if(enchants.equals(""))
				enchants = enchants + e.getName() + "=" + i.getEnchantmentLevel(e);
			else
				enchants = enchants + "-" + e.getName() + "=" + i.getEnchantmentLevel(e);
		}
		return enchants;
	}
	public String getNameEdit(ConfigurationSection cs, String item) {
		int dashes = 0;
		for(int i = 0; i > -1; i++) {
			dashes++;
			if(cs.getString(item + addDashes(dashes)) == null) {
				continue;
			}
		}
		return item + addDashes(dashes);
	}
	public String addDashes(int dashes) {
		String d = "";
		for(int i = 1; i < dashes; i++){
			d = d + "-";
		}
		return d;
	}
}
