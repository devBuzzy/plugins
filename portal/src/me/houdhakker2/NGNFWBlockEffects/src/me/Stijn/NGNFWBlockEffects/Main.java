package me.Stijn.NGNFWBlockEffects;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public static FileConfiguration config;
	File cfile;
	public final Logger logger = Logger.getLogger("Minecraft");
	public PluginDescriptionFile pdfFile = getDescription();

	@Override
	public void onEnable() {
		logger.info("*--==[ NGNFW Block Effects ]==--*");
		logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now enabled!");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		config = getConfig();
		config.addDefault("TitleCommands", "'&f&lBlock &a&lEffects'");
		config.addDefault("Launchpad.BlockId", "OBSIDIAN");
		config.addDefault("Trampoline.BlockId", "COAL_BLOCK");
		config.addDefault("Run.BlockId", "ICE");
		config.options().copyDefaults(true);
		// ### HEADER ###
		config.options().header(
				"#####################################################" + "\n         Plugin made by Stijn van Nieulande         #" + "\n#####################################################" + "\n                                                    #" + "\n  ##    ##  ########  ##    ##  ######  ##      ##  #" + "\n  ####  ##  ##        ####  ##  ##      ##      ##  #" + "\n  ##  ####  ##  ####  ##  ####  ######  ##      ##  #" + "\n  ##    ##  ##    ##  ##    ##  ##      ##  ##  ##  #" + "\n  ##    ##  ########  ##    ##  ##        ##  ##    #" + "\n                                                    #" + "\n#####################################################" + "\n               Website:  www.ngnfw.nl               #" + "\n#####################################################");
		config.options().copyHeader(true);
		// end of Header
		saveConfig();
		cfile = new File(getDataFolder(), "config.yml");
	}

	@Override
	public void onDisable() {
		logger.info("*--==[ NGNFW Block Effects ]==--*");
		logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now disabled!");
		saveConfig();
	}

	// ########## Block Effects ##################################################################

	// @EventHandler
	// public void onWalk(PlayerMoveEvent e) {
	//
	// }

	public PluginDescriptionFile getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(PluginDescriptionFile pdfFile) {
		this.pdfFile = pdfFile;
	}

	// ##########

}
