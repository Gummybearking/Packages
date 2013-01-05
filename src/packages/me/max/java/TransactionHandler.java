package packages.me.max.java;

import packages.me.max.java.objects.Package;
import packages.me.max.java.utils.EconUtils;
import packages.me.max.java.utils.PackageUtils;

import org.bukkit.inventory.ItemStack;

public class TransactionHandler {
	PackageUtils config = new PackageUtils();
	public void deliverFreePackage(Package pack) {
		ItemStack[] items = pack.getInv();
		config.getPlayerMailBox(pack.getTo()).getBlockInventory().setContents(items);
		pack.getTo().sendMessage(pack.getMessage());
	}
	public void deliverCOD(Package pack) {
		ItemStack[] items = pack.getInv();
		config.getPlayerMailBox(pack.getTo()).getBlockInventory().setContents(items);
		pack.getTo().sendMessage(pack.getMessage());
		EconUtils.withdraw(pack.getTo(), pack.getPrice());
		EconUtils.deposit(pack.getTo(),  pack.getPrice());
	}
	
}
