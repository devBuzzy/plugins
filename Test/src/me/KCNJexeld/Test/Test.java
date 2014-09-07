package me.KCNJexeld.Test;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Test extends JavaPlugin implements Listener {
	@Override
	 public void onDisable() {
	  getLogger().info("disabled");
	 }
	 
	 
	 @Override
	 public void onEnable() {
	  getLogger().info("enabled");
	  getConfig().options().copyDefaults(true);
	  saveConfig();
	  
	  Bukkit.getServer().getPluginManager().registerEvents(this, this);
	 }
	 @EventHandler
	 public void mobdamage(PlayerInteractEvent e) {
	   if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
	   if(e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
	     Sign sign = (Sign) e.getClickedBlock().getState();
	     if(sign.getLine(0).equalsIgnoreCase("[zeghallo]")) {
	     sign.setLine(1, ChatColor.AQUA + "Hallo " );
	     sign.setLine(2, ChatColor.GOLD + e.getPlayer().getName());
	     sign.update();
	     } else {
	    
	     }
	   } else{
	    
	   }
	   } else {
	   //do nothing
	   }
	 }
	 }
