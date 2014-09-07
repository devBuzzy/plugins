package me.Stijn.NGNFWTagRank;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.tag.TagAPI;


public class Main extends JavaPlugin implements Listener {
	@Override
	 public void onDisable() {
	  getLogger().info("disabled");
	}
	 
	 
	 @Override
	 public void onEnable() {
	  getLogger().info("enabled");
	  
	  PluginManager pm = Bukkit.getPluginManager();
	  
	  pm.registerEvents(new NameTagEvent(), this);
	  
	  this.getCommand("nametag").setExecutor(new NameTag());
	  
	  getConfig().options().copyDefaults(true);
	  saveConfig();
	  
	  /*Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				for (Player player: Bukkit.getServer().getOnlinePlayers()) {
					TagAPI.refreshPlayer(player);
				}
			}
		}, 0, 5 * 20); // 20 ticks = 1 second. So 5 * 20 = 100 which is 5 seconds*/
      }
}
