package me.Stijn.NGNFWFireworkSign;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class FireworkSign extends JavaPlugin implements Listener {
	
	private List<Sign> signs = new ArrayList<Sign>();
	FileConfiguration config;
	File cfile;
    public final Logger logger = Logger.getLogger("Minecraft");
    public PluginDescriptionFile pdfFile = getDescription();
	
	public void onEnable(){
		logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now enabled!");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		cfile = new File(getDataFolder(), "config.yml");
		signs.add(Sign());
	}
	
	  @EventHandler
	  public void onPlayer(SignChangeEvent e){
	      Player p = e.getPlayer();
	      final Sign s = (Sign) e.getBlock().getState();
	      if(e.getLine(0).equalsIgnoreCase("[test]")){
	    	  if (p.hasPermission("ngnfw.fireworksign.create")) {
	    		  getConfig().set("loc.", s.getLocation().getWorld().getName());
	    		  getConfig().set("loc.x", s.getLocation().getX());
	    		  getConfig().set("loc.y", s.getLocation().getY());
	    		  getConfig().set("loc.z", s.getLocation().getZ());
	    		  saveConfig();
	        	      p.sendMessage(ChatColor.BLUE + "N" + ChatColor.RED + "G" + ChatColor.GREEN + "N" + ChatColor.GOLD + "F" + ChatColor.RED + "W" + ChatColor.DARK_GRAY + ">" + " " + ChatColor.WHITE + "Firework sign created!");
	        	      e.setLine(0, ChatColor.AQUA + "Firework Sign");
	        			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
	        				public void run(){
	        					firework(s);
	        				}
	        			}, 0, 5 * 20); // 20 ticks = 1 second. So 5 * 20 = 100 which is 5 seconds
	    	  }
	    	  }
	}
	  
	  
	  
	  /*@EventHandler
	    public void OnPlayerRespawnEvent(PlayerRespawnEvent e ){
	     Location loc = e.getRespawnLocation();
	     
	     loc.setWorld(server.getWorld(getConfig().getString(e.getPlayer().getWorld().getName() + ".spawnWorld")));
	     loc.setX(getConfig().getDouble(e.getPlayer().getWorld().getName() + ".x"));
	     loc.setY(getConfig().getDouble(e.getPlayer().getWorld().getName() + ".y"));
	     loc.setZ(getConfig().getDouble(e.getPlayer().getWorld().getName() + ".z"));
	     
	     e.setRespawnLocation(loc);
	    }
	    
	    public void ConfigRegen(){
	     List<World> worlds = server.getWorlds();
	     getConfig().set("AmountOfWorlds", worlds.size());
	     
	     for (int i = 0 ; i < worlds.size() ; i++){
	      if (!getConfig().isSet(worlds.get(i).getName())){
	       getConfig().set(worlds.get(i).getName()+".spawnWorld", worlds.get(i).getName());
	       getConfig().set(worlds.get(i).getName()+".x", worlds.get(i).getSpawnLocation().getBlockX());
	       getConfig().set(worlds.get(i).getName()+".y", worlds.get(i).getSpawnLocation().getBlockY());
	       getConfig().set(worlds.get(i).getName()+".z", worlds.get(i).getSpawnLocation().getBlockZ());
	       saveConfig();
	      }
	     }
	    }*/


	  /*e.getPlayer().sendMessage(ChatColor.BLUE + "N" + ChatColor.RED + "G" + ChatColor.GREEN + "N" + ChatColor.GOLD + "F" + ChatColor.RED + "W" + ChatColor.DARK_GRAY + ">" + " " + ChatColor.WHITE + "Firework sign created!");*/
	
    public void firework(Sign block){
        Firework fw = (Firework) block.getWorld().spawnEntity(block.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwmeta = fw.getFireworkMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.withTrail().withFlicker().withFade(Color.AQUA).withColor(Color.BLUE).withColor(Color.RED)
        .withColor(Color.LIME).withColor(Color.YELLOW).withColor(Color.RED).with(FireworkEffect.Type.BALL_LARGE);
        fwmeta.addEffect(builder.build());
        fwmeta.setPower(1);
        fw.setFireworkMeta(fwmeta);
   }

/*	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] a){
		Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("NGNFWFireworkSign")){
			firework(s);
		}
		return false;
	}*/
}