package me.Stijn.NGNFWPortalGame;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import me.Stijn.NGNFWPortalGame.SettingsManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
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
		logger.info("*--==[ NGNFW PortalGame ]==--*");
		logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now enabled!");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		cfile = new File(getDataFolder(), "config.yml");
		settings.setup(this);
	}
	
	@Override
	public void onDisable() {
		logger.info("*--==[ NGNFW PortalGame ]==--*");
		logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now disabled!");
		saveConfig();
	}
	
	// ##########  COMMANDS  ########## COMMANDS  ##########  COMMANDS  ##########
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(sender instanceof Player) {
	        String titlecommands = getConfig().getString("TitleCommands");
            Player p = (Player) sender;
            Location loc = p.getLocation();
            if(label.equalsIgnoreCase("Portal")){
            	if(args.length > 0) {
    // ########## SET SPAWN  ##########
                if(args[0].equalsIgnoreCase("Setspawn")) {
               	   if(args.length > 1) {
                       String arenaname = args[1];
                       if(args[1] == null) {
                               p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " Command usage: /Portal Setspawn <arena>.");
                               return false;
                       }
                       settings.getData().set(arenaname + ".world", p.getLocation().getWorld().getName());
                       settings.getData().set(arenaname + ".x", p.getLocation().getX());
                       settings.getData().set(arenaname + ".y", p.getLocation().getY());
                       settings.getData().set(arenaname + ".z", p.getLocation().getZ());
                       settings.getData().set(arenaname + ".pitch", p.getLocation().getPitch());
                       settings.getData().set(arenaname + ".yaw", p.getLocation().getYaw());
                       settings.saveData();
                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.AQUA + " Spawn set for Portal world: " + arenaname +"!");
                       return true;
            	   } else {
            		  p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " Command usage: /Portal Setspawn <arena>.");
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
                       String gamename = args[1];
                       if(args[1] == null) {
                               p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " Command usage: /Portal join <arena>.");
                               return false;
                       }
                       World w = Bukkit.getServer().getWorld(settings.getData().getString(gamename + ".world"));
                       double x = settings.getData().getDouble(gamename + ".x");
                       double y = settings.getData().getDouble(gamename + ".y");
                       double z = settings.getData().getDouble(gamename + ".z");
                       float pitch = (float) settings.getData().getDouble(gamename + ".pitch");
                       float yaw = (float) settings.getData().getDouble(gamename + ".yaw");
                       p.teleport(new Location(w, x, y, z, yaw, pitch));
                       return true;
            	   } else {
            		  p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + " Command usage: /Portal join <arena>.");
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
                    p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/Portal " + ChatColor.GREEN + "join" + ChatColor.AQUA + " <arena>");
                    p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/Portal " + ChatColor.GREEN + "leave" + ChatColor.AQUA + " <arena>");
                    if(p.isOp()) {
                        p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/Portal " + ChatColor.GREEN + "Setspawn" + ChatColor.AQUA + " <worldname>");
                        p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/Portal " + ChatColor.GREEN + "Setlobby");
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&m////>&b &lWelcome to the &1&lN&4&lG&a&lN&f&lF&a&lW &b&lMinigame: &a&m<////"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592&f\u2592"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&0\u2592&4\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&f\u2592"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&f\u2592"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&4\u2592&0\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&f\u2592"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&f\u2592"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&f\u2592"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592&f\u2592"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592"));
                    }
                 }
               }
        }
	return false;
   }

}
