package packages.me.max.java;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Packages extends JavaPlugin{
	public final Logger logger = Logger.getLogger("Minecraft");
	public void onEnable() {
    	PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info(pdffile.getName() + " Has Been Enabled!");
		
		

	}
		
	public void onDisable() {
		PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info(pdffile.getName() + " Has Been Disabled!");
	}

	public boolean onCommand(CommandSender Sender, Command cmd, String commandLabel, String[] args){
		CommandHandler handler = new CommandHandler();
		if(!commandLabel.equalsIgnoreCase("Package") && !commandLabel.equalsIgnoreCase("Pack")) return true;
		//TODO add support for help command
		if(args.length == 0) return true;
		String c = args[0];
		switch(c.toLowerCase()){
			case "send":
			case "s":
				handler.send((Player)Sender,args);
				break;
			case "read":
			case "r":
				handler.read()
		}
		return true;
	}
}
