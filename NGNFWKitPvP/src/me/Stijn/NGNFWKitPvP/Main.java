package me.Stijn.NGNFWKitPvP;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import net.minecraft.server.v1_7_R3.Block;
import net.minecraft.util.com.mojang.authlib.yggdrasil.response.User;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.server.PluginEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	FileConfiguration config;
	File cfile;
    public final Logger logger = Logger.getLogger("Minecraft");
    public PluginDescriptionFile pdfFile = getDescription();
    SettingsManager settings = SettingsManager.getInstance();
    Set<Location> locations = new HashSet<Location>();
	
	@Override
	public void onEnable() {
		logger.info("*--==[ NGNFW KitPvP ]==--*");
		logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now enabled!");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		cfile = new File(getDataFolder(), "config.yml");
		settings.setup(this);
		//Sign Updater
		/*Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            @Override
            public void run() {
                s.update();
            }
           
        }, 0, 20); // 20 ticks = 1 second. So 5 * 20 = 100 which is 5 seconds*/
	}
	
	@Override
	public void onDisable() {
		logger.info("*--==[ NGNFW KitPvP ]==--*");
		logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now disabled!");
		saveConfig();
	}

// ##########  COMMANDS  ########## COMMANDS  ##########  COMMANDS  ##########
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(sender instanceof Player) {
	        String titlesigns = getConfig().getString("TitleSigns");
	        String titlecommands = getConfig().getString("TitleCommands");
            Player p = (Player) sender;
            Location loc = p.getLocation();
            if(label.equalsIgnoreCase("KitPvP")){
            	if(args.length > 0) {
// ########## SET ONLINE  ##########
                if(args[0].equalsIgnoreCase("SetOnline")) {
              	   if(args.length > 1) {
                         String worldname = args[1];
                         if(args[1] == null) {
                                 p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " Command usage: /KitPvP SetOffline <worldname>.");
                                 return false;
                         }
                         p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.AQUA + " This command is currently underconstruction :P");
              	   } else {
              		  p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " Command usage: /KitPvP SetOffline <worldname>.");
              	   }
                        
                 } 
    // ########## SET OFFLINE  ##########
                else if(args[0].equalsIgnoreCase("SetOffline")) {
               	   if(args.length > 1) {
                       String worldname = args[1];
                       if(args[1] == null) {
                               p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " Command usage: /KitPvP SetOffline <worldname>.");
                               return false;
                       }
                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.AQUA + " This command is currently underconstruction :P");
            	   } else {
            		  p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " Command usage: /KitPvP SetOffline <worldname>.");
            	   }
                      
               } 
    // ########## SET SPAWN  ##########
                else if(args[0].equalsIgnoreCase("Setspawn")) {
               	   if(args.length > 1) {
                       String worldname = args[1];
                       if(args[1] == null) {
                               p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " Command usage: /KitPvP Setspawn <worldname>.");
                               return false;
                       }
                       settings.getData().set(worldname + ".world", p.getLocation().getWorld().getName());
                       settings.getData().set(worldname + ".x", p.getLocation().getX());
                       settings.getData().set(worldname + ".y", p.getLocation().getY());
                       settings.getData().set(worldname + ".z", p.getLocation().getZ());
                       settings.getData().set(worldname + ".pitch", p.getLocation().getPitch());
                       settings.getData().set(worldname + ".yaw", p.getLocation().getYaw());
                       settings.saveData();
                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.AQUA + " Spawn set for KitPvP world: " + worldname +"!");
                       return true;
            	   } else {
            		  p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " Command usage: /KitPvP Setspawn <worldname>.");
            	   }
                      
               } 
    // ########## SET LOBBY  ##########
                else if(args[0].equalsIgnoreCase("Setlobby")) {
                       settings.getData().set("lobby.world", p.getLocation().getWorld().getName());
                       settings.getData().set("lobby.x", p.getLocation().getX());
                       settings.getData().set("lobby.y", p.getLocation().getY());
                       settings.getData().set("lobby.z", p.getLocation().getZ());
                       settings.getData().set("lobby.pitch", p.getLocation().getPitch());
                       settings.getData().set("lobby.yaw", p.getLocation().getYaw());
                       settings.saveData();
                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.AQUA + " Lobby set!");
                       return true;
                      
               }
             // ########## LEAVE  ##########
                else if(args[0].equalsIgnoreCase("leave")) {
                       if (settings.getData().getConfigurationSection("lobby") == null) {
                           p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " The lobby has not yet been set!");
                           return true;
                       }
                       World w = Bukkit.getServer().getWorld(settings.getData().getString("lobby.world"));
                       double x = settings.getData().getDouble("lobby.x");
                       double y = settings.getData().getDouble("lobby.y");
                       double z = settings.getData().getDouble("lobby.z");
                       float pitch = (float) settings.getData().getDouble("lobby.pitch");
                       float yaw = (float) settings.getData().getDouble("lobby.yaw");
                       p.teleport(new Location(w, x, y, z, yaw, pitch));
                       return true;
               }
             // ########## JOIN  ##########
                else if(args[0].equalsIgnoreCase("join")) {
               	   if(args.length > 1) {
                       String worldname = args[1];
                       if(args[1] == null) {
                               p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " Command usage: /KitPvP join <arena>.");
                               return false;
                       }
                       World w = Bukkit.getServer().getWorld(settings.getData().getString(worldname + ".world"));
                       double x = settings.getData().getDouble(worldname + ".x");
                       double y = settings.getData().getDouble(worldname + ".y");
                       double z = settings.getData().getDouble(worldname + ".z");
                       float pitch = (float) settings.getData().getDouble(worldname + ".pitch");
                       float yaw = (float) settings.getData().getDouble(worldname + ".yaw");
                       p.teleport(new Location(w, x, y, z, yaw, pitch));
                       return true;
            	   } else {
            		  p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + " Command usage: /KitPvP join <arena>.");
            	   }
                      
               }
             // ########## RELOAD  ##########
                else if(args[0].equalsIgnoreCase("reload")) {
                	p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.AQUA + " Config reloaded!");
                	System.out.println("NGNFWKitPvP > Config reloaded!");
                	reloadConfig();
               }
             // ########## Default command  ##########
            	} else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) );
                    p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/KitPvP " + ChatColor.GREEN + "join" + ChatColor.AQUA + " <arena>");
                    p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/KitPvP " + ChatColor.GREEN + "leave" + ChatColor.AQUA + " <arena>");
                    if(p.isOp()) {
                        p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/KitPvP " + ChatColor.GREEN + "SetOnline" + ChatColor.AQUA + " <worldname>");
                        p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/KitPvP " + ChatColor.GREEN + "SetOffline" + ChatColor.AQUA + " <worldname>");
                        p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/KitPvP " + ChatColor.GREEN + "Setspawn" + ChatColor.AQUA + " <worldname>");
                        p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/KitPvP " + ChatColor.GREEN + "Setlobby");
                    }
                 }
               }
        }
	return false;
            }

    
// ##########  Sign Create  ########## Sign Create  ##########  Sign Create  ##########
	@EventHandler
	public void onPlayer(SignChangeEvent e){
		String titlesigns = getConfig().getString("TitleSigns");
		String titlecommands = getConfig().getString("TitleCommands");
	    Player p = e.getPlayer();
	    final Sign s = (Sign) e.getBlock().getState();
	    if(e.getLine(0).equalsIgnoreCase("[KitPvP]")){
	    	if (p.hasPermission("ngnfw.kitpvp.create")) {
	    		if(e.getLine(1).length() > 0){
		    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + " Sign created!");
	        	    String arg = e.getLines()[1];
	        	    e.setLine(1, arg);
	        	    e.setLine(0, ChatColor.translateAlternateColorCodes('&', titlesigns));
	        	    e.setLine(2, ChatColor.BOLD + "Status");
	        	    e.setLine(3, ChatColor.AQUA + Bukkit.getOnlinePlayers());
	        	    //e.setLine(3, ChatColor.BOLD + "Players");
	    		} else {
	        	    p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + "Please add the world name on line 2!");
	        	    ((org.bukkit.block.Block) s).breakNaturally();
	    		  }
	    	} else {
	    		((org.bukkit.block.Block) s).breakNaturally();
	    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + " You don't have the permission to create a KitPvP sign!");
	    	}
	    }
	}
    
// ##########  Sign Interact  ########## Sign Interact  ##########  Sign Interact  ##########
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
    	String titlesigns = getConfig().getString("TitleSigns");
    	Player p = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
        if (event.getClickedBlock().getState() instanceof Sign) {
            Sign sign = (Sign) event.getClickedBlock().getState();
            Sign s = (Sign) event.getClickedBlock().getState();
            if (s.getLine(0).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', titlesigns))) {
        		String arg = sign.getLines()[1];
            	World w = Bukkit.getServer().getWorld(settings.getData().getString(arg + ".world"));
                double x = settings.getData().getDouble(arg + ".x");
                double y = settings.getData().getDouble(arg + ".y");
                double z = settings.getData().getDouble(arg + ".z");
                float pitch = (float) settings.getData().getDouble(arg + ".pitch");
                float yaw = (float) settings.getData().getDouble(arg + ".yaw");
                p.teleport(new Location(w, x, y, z, yaw, pitch));
            }
        }
    }
    }

// ##########  Sign Updater  ########## Sign Updater  ##########  Sign Updater  ##########
    /*List<String> players = new ArrayList<String>();
    	
    for (Sign sign : Bukkit.) {
    players.add(player.getName());
    }
    sender.sendMessage(players);
}*/
}
