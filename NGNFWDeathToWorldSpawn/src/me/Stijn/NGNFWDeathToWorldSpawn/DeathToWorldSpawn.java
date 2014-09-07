package me.Stijn.NGNFWDeathToWorldSpawn;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathToWorldSpawn extends JavaPlugin implements Listener{
	
	private HashMap<String, Location> deathLocations = new HashMap<String, Location>();
	
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void death(PlayerRespawnEvent event){
	Player p = (Player) event.getPlayer();
	Location deathLocation = deathLocations.get(p.getName());
     if (deathLocation != null) {
    	 event.setRespawnLocation(Bukkit.getWorld(getName()).getSpawnLocation());
	 /*event.setRespawnLocation (event.getRespawnLocation());*/
	 /*event.setRespawnLocation (p.getWorld().getSpawnLocation());*/
	 }
	}

}
