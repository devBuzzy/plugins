package me.Stijn.TabList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener {
	@Override
	 public void onDisable() {
	  getLogger().info("disabled");
	}
	 
	 
	 @Override
	 public void onEnable() {
	  loadConfig();
	  getLogger().info("enabled");
	  
	  PluginManager pm = Bukkit.getPluginManager();
	  
	  pm.registerEvents(new TabList(), this);
	  pm.registerEvents(new TabListName(), this);
	  
	  getConfig().options().copyDefaults(true);
	  saveConfig();
      }
	 
	 
	// ##### Load Config #####
	 public void loadConfig() {
		 FileConfiguration cfg = this.getConfig();
		 cfg.options().copyDefaults(true);
		 this.saveConfig();
	 }
}
