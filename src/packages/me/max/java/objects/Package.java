package packages.me.max.java.objects;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import packages.me.max.java.utils.ConfigUtils;
import packages.me.max.java.utils.PackageUtils;

public class Package {
	private ItemStack[] inventory;
	private Player      giver;
	private Player      reciever;
	private String      message;
	private int         price;
	private PackageType type;
	private String      ID;
	private ConfigUtils config;
	public enum PackageType{
		COD, 
		NORMAL;
	}
	private PackageUtils packu;

	/**
	 * 
	 * @param stack Contents of the package
	 * @param From The sender
	 * @param To the receiver
	 * @param Message the message
	 * @param Price the price
	 */
	public Package(ItemStack[] stack, Player From, Player To, String Message, int Price) {
		config    = new ConfigUtils();
		packu     = new PackageUtils();
		inventory = stack;
		giver     = From;
		reciever  = To;
		message   = Message;
		price     = Price;
		if(price != 0) 
			type  = PackageType.COD;
		else
			type  = PackageType.NORMAL;
		do{
			ID = (new Random()).nextInt() + "pack";
		}while(packu.getNameList().contains(ID));
	}
	public String getMessage() {  
		return this.message;
	}
	public ItemStack[] getInv() { 
		return this.inventory;
	}
	public Player getFrom() {
		return this.giver;
	}
	public Player getTo() {
		return this.reciever;
	}
	public int getPrice() {
		return this.price;
	}
	public PackageType getType() {
		return this.type;
	}
	public void save() {
		packu.save(this);
	}
	public String getID() {
		return this.ID;
	}
	public void setID(String newID) {
		this.ID = newID;
	}
}
