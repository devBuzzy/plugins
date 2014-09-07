package me.KCNJexeld.SignCountdown;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.craftbukkit.Main;

public class SignCountdown extends JavaPlugin implements Listener {
	private World world;
	private double x;
	private double y;
	private double z;
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
	 }{
		 public class SignCountdown extends JavaPlugin implements Listener {
			    Server server = getServer();
			    Logger logger = getLogger();
			    public Logger log = Bukkit.getLogger();
			    String prefix = color(getConfig().getString("prefix").replace("%space%", " "));
			    String enabled = ChatColor.GREEN + "Is now enabled!";
			    String configmade = ChatColor.GREEN + "Config detected! Default config copy job is now cancelled!";
			    public String color(String msg)
			    {
			        String coloredMsg = "";
			        for(int i = 0; i < msg.length(); i++)
			        {
			            if(msg.charAt(i) == '&')
			                coloredMsg += '§';
			            else
			                coloredMsg += msg.charAt(i);
			        }
			        return coloredMsg;
			    }
			 
			    public void onEnable() {
			        server.getPluginManager().registerEvents(this, this);
			        if(!getConfig().contains("made")) {
			            getConfig().options().copyDefaults(true);
			        } else {
			            log.info(prefix + configmade);
			        }
			        log.info(prefix + enabled);
			    }
			    @EventHandler
			    public void onSignCreate(SignChangeEvent sign) {
			        Player player = sign.getPlayer();
			        World world = player.getWorld();
			        if(sign.getLine(0).equalsIgnoreCase("[Countdown]") && sign.getLine(2).endsWith("m")) {
			            sign.setLine(0, prefix);
			            if(sign.getLine(1).contains("%")) {
			            sign.getLine(1).replace("%tournament%", "Tournament starts in...");
			            sign.getLine(1).replace("%game%", "Game starts in...");
			            } else {
			                sign.setLine(0, ChatColor.RED + "[Countdown]");
			                sign.setLine(1, ChatColor.RED + "<Type>");
			                player.sendMessage(color(prefix + ChatColor.YELLOW + "Warning: There is not the right format on the second line!"));
			            }
			            sign.setLine(2, "00:" + sign.getLine(1).replace("m", "") + ":00");
			            Block targetBlock = player.getTargetBlock(null, 15);
			            if (targetBlock.getType() == Material.WALL_SIGN || targetBlock.getType() == Material.SIGN){
			 
			            int x = targetBlock.getX();
			            int y = targetBlock.getY();
			            int z = targetBlock.getZ();
			 
			 
			            Sign targetsign = (Sign) world.getBlockAt(x, y, z);
			            if(sign.getLine(0).equalsIgnoreCase("[Countdown]") && sign.getLine(1).contains("%")) {
			                getConfig().set("countdownsign", targetsign);
			            }
			        } else {
			            }
			            log.warning(color(prefix + ChatColor.YELLOW + "Warning: Sign Made By " + ChatColor.WHITE + player + ChatColor.YELLOW + " is not a countdown sign!"));
			            }
			        }
			    }

	 }}
