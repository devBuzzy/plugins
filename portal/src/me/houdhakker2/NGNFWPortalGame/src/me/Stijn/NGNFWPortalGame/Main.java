package me.Stijn.NGNFWPortalGame;


import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
		
       public final Logger logger = Logger.getLogger("Minecraft");
       ArenaManager am ;
   	   FileConfiguration config;
   	   File cfile;
       public PluginDescriptionFile pdfFile = getDescription();
       SettingsManager settings = SettingsManager.getInstance();
       Set<Location> locations = new HashSet<Location>();
       
       String titlecommands;
       
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
    		
    		titlecommands = getConfig().getString("TitleCommands");
    		
    		am = new ArenaManager(titlecommands);
    		
    		dataRegen();
    		getInfo();
       }
       
       public void dataRegen(){
    	   if (settings.getData().get("Amount_Of_Arenas") == null){
    		   settings.getData().set("Amount_Of_Arenas", 1);
    	   }
       }
       
       
       public void getInfo(){
    	   for (int i = 1 ; i <= settings.getData().getInt("Amount_Of_Arenas"); i++){
    		   if (settings.getData().get("Arenas") != null){
    			   if (settings.getData().get("Arenas." + i)!= null){
    				   am.CreateArena(new Location(
    						   getServer().getWorld(settings.getData().getString("Arenas." + i + ".Loc.world")), 
    						   settings.getData().getDouble("Arenas." + i + ".Loc.x"), 
    						   settings.getData().getDouble("Arenas." + i + ".Loc.y"), 
    						   settings.getData().getDouble("Arenas." + i + ".Loc.z"), 
    						   (float) settings.getData().getDouble("Arenas." + i + ".Loc.yaw"), 
    						   (float) settings.getData().getDouble("Arenas." + i + ".Loc.pitch")),
    						   settings.getData().getString("Arenas." + i + ".Name"), i);
    				   
    				   if (settings.getData().get("Arenas." + i + ".Lobby") != null){
    					   am.getArena(i).setLobby(new Location(
    						   getServer().getWorld(settings.getData().getString("Arenas." + i + ".Lobby.Loc.world")), 
    						   settings.getData().getDouble("Arenas." + i + ".Lobby.Loc.x"), 
    						   settings.getData().getDouble("Arenas." + i + ".Lobby.Loc.y"), 
    						   settings.getData().getDouble("Arenas." + i + ".Lobby.Loc.z"), 
    						   (float) settings.getData().getDouble("Arenas." + i + ".Lobby.Loc.yaw"), 
    						   (float) settings.getData().getDouble("Arenas." + i + ".Lobby.Loc.pitch")));
    				   }
    			   }
    		   }
    	   }
       }

       @Override
       public void onDisable() {
            logger.info("*--==[ NGNFW PortalGame ]==--*");
       		logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now disabled!");
       		saveConfig();
       }

// ##########  COMMANDS  ########## COMMANDS  ##########  COMMANDS  ############################
       public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
               if(sender instanceof Player) {
                       Player p = (Player) sender;
                       Location loc = p.getLocation();
                       if(label.equalsIgnoreCase("Portal")) {
                               if(args.length > 0) {
                                       if(args[0].equalsIgnoreCase("Create") && p.isOp()) {
                                    	   if(args.length > 1) {
                                               String name = args[1];
                                               if(am.getArena(name) != null) {
                                                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + "Arena with the name " + name + " already exists!");
                                               } else {
                                                       am.CreateArena(loc, name);
                                                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.GREEN + "Arena with the name " + name + " succesfully created!");
                                                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.YELLOW + "You now need to set the Spawn and the Lobby Locations.");
                                               }
                                    	   } else {
                                    		   p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "Usage: /Portal create <arena>");
                                    	   }
                                       } else if(args[0].equalsIgnoreCase("Join")) {
                                           
                                    	   if(am.isinGame(p)) {
                                        	   p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "You're already in game.");
                                        	   return false;
                                           }
                                    	   if(args.length > 1) {
                                               String arenaname = args[1];
                                               if(args[1] == null) {
                                                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "Command usage: /Portal Join <arena>.");
                                                       return false;
                                               }
                                               
                                               Arena arena = am.getArena(arenaname);
                                               if(arena == null) {
                                                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "No Arena found with that name.");
                                                       return false;
                                               }
                                              
                                               am.addPlayer(p, arena);
                                               p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.GREEN + "Succesfully joined arena: " + arena.getName());
                                              
                                               Location lobby = arena.getLobby();
                                               p.teleport(lobby);
                                    	   } else {
                                    		   p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "Command usage: /Portal Join <arena>.");
                                    	   }
// ########## LEAVE  ############################################################################
                                       } else if(args[0].equalsIgnoreCase("Leave")) {
                                              
                                               if(am.isinGame(p)) {
                                                       am.removePlayer(p);
                                                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.GREEN + "Succesfully left the game!");
                                                       
                                                       World w = Bukkit.getServer().getWorld(settings.getData().getString("lobby.world"));
                                                       double x = settings.getData().getDouble("lobby.x");
                                                       double y = settings.getData().getDouble("lobby.y");
                                                       double z = settings.getData().getDouble("lobby.z");
                                                       float pitch = (float) settings.getData().getDouble("lobby.pitch");
                                                       float yaw = (float) settings.getData().getDouble("lobby.yaw");
                                                       p.teleport(new Location(w, x, y, z, yaw, pitch));
                                                       
                                               } else {
                                                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "You're not in a game!");
                                               }
// ########## SET SPAWN  #########################################################################
                                       } else if(args[0].equalsIgnoreCase("Setspawn") && p.isOp()) {
                                    	   if(args.length > 1) {
                                    		   
                                    		   String name = args[1];
                                    		   Arena arena;
                                    		   if((arena = am.getArena(name)) != null) {
                                    			   arena.setSpawn(p.getLocation());
                                    			   p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.GREEN + "Spawn Location set for arena: " + ChatColor.YELLOW + arena.getName());
                                    		   }
                                    	   } else {
                                    		   p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "Usage: /Portal setspawn <arena>");
                                    	   }
// ########## SET ARENA LOBBY  #######################################################################
                                       } else if(args[0].equalsIgnoreCase("SetArenaLobby") && p.isOp()) {
                                    	   if(args.length > 1) {
                                    		   
                                    		   String name = args[1];
                                    		   Arena arena;
                                    		   
                                    		   if((arena = am.getArena(name)) != null) {
                                                   settings.getData().set("Arenas." + am.getArena(name).getId() + ".Lobby.Loc.world", loc.getWorld().getName());
                                                   settings.getData().set("Arenas." + am.getArena(name).getId() + ".Lobby.Loc.x"	, loc.getX());
                                                   settings.getData().set("Arenas." + am.getArena(name).getId() + ".Lobby.Loc.y"	, loc.getY());
                                                   settings.getData().set("Arenas." + am.getArena(name).getId() + ".Lobby.Loc.z"	, loc.getZ());
                                                   settings.getData().set("Arenas." + am.getArena(name).getId() + ".Lobby.Loc.yaw"	, loc.getYaw());
                                                   settings.getData().set("Arenas." + am.getArena(name).getId() + ".Lobby.Loc.pitch", loc.getPitch());
                                                   settings.getData().set("Arenas." + am.getArena(name).getId() + ".Lobby.Name"		, name);
                                                   settings.saveData();
                                                   
                                    			   arena.setLobby(loc);
                                    			   p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.GREEN + "Lobby Location set for arena: " + ChatColor.YELLOW + arena.getName());
                                    		   } else {
                                    			   p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "There is no arena with that name!");
                                    		   }
                                    		   
                                    	   } else {
                                    		   p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "Usage: /Portal setlobby <arena>");
                                    	   }
                                       }
// ########## SET LOBBY  #######################################################################
                                       else if(args[0].equalsIgnoreCase("SetLobby") && p.isOp()) {
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
// ########## LOBBY  ###########################################################################
                                       else if(args[0].equalsIgnoreCase("Lobby")) {
                                              if (settings.getData().getConfigurationSection("lobby") == null) {
                                                  p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + ChatColor.RED + " The lobby has not yet been set!");
                                                  return true;
                                              }
                                              
                                              if(am.isinGame(p)) {
                                                  am.removePlayer(p);
                                                  p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.GREEN + "Succesfully left the game!");
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
// ########## RELOAD  ###########################################################################
                                       else if(args[0].equalsIgnoreCase("reload")) {
                                       	p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.AQUA + " Config reloaded!");
                                       	System.out.println("NGNFWKitPvP > Config reloaded!");
                                       	reloadConfig();
                                      }                                          
// ########## Default command  ##################################################################
                           	} else {
                                   p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) );
                                   p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/Portal " + ChatColor.GREEN + "join" + ChatColor.AQUA + " <arena>");
                                   p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/Portal " + ChatColor.GREEN + "leave" + ChatColor.AQUA + " <arena>");
                                   if(p.isOp()) {
                                       p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/Portal " + ChatColor.GREEN + "Setspawn" + ChatColor.AQUA + " <arena>");
                                       p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/Portal " + ChatColor.GREEN + "Setlobby");
                                       p.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + "/Portal " + ChatColor.GREEN + "SetArenaLobby");
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
       
// ##########  Sign Create  ########## Sign Create  ##########  Sign Create  ##########
   	@EventHandler
   	public void onPlayer(SignChangeEvent e){
   		String titlesigns = getConfig().getString("TitleSigns");
   		String titlecommands = getConfig().getString("TitleCommands");
   	    Player p = e.getPlayer();
   	    final Sign s = (Sign) e.getBlock().getState();
   	    if(e.getLine(0).equalsIgnoreCase("[Portal]")){
   	    	if (p.hasPermission("ngnfw.portal.create")) {
   	    		if(e.getLine(1).length() > 0){
   		    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + " Sign created!");
   	        	    String arg = e.getLines()[1];
   	        	    e.setLine(1, arg);
   	        	    e.setLine(0, ChatColor.translateAlternateColorCodes('&', titlesigns));
   	        	    e.setLine(2, ChatColor.BOLD + "Status");
   	        	    //e.setLine(3, ChatColor.BOLD + "Players");
   	    		} else {
   	        	    p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + "Please add the arena name on line 2!");
   	        	    ((org.bukkit.block.Block) s).breakNaturally();
   	    		  }
   	    	} else {
   	    		((org.bukkit.block.Block) s).breakNaturally();
   	    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands)  + " You don't have the permission to create a Portal sign!");
   	    	}
   	    }
   	}
   	
// ##########  Sign Interact  ########## Sign Interact  ##########  Sign Interact  ##########
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
    	String titlesigns = getConfig().getString("TitleSigns");
    	String titlecommands = getConfig().getString("TitleCommands");
    	Player p = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
        if (event.getClickedBlock().getState() instanceof Sign) {
            Sign sign = (Sign) event.getClickedBlock().getState();
            Sign s = (Sign) event.getClickedBlock().getState();
            if (s.getLine(0).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', titlesigns))) {
         	   if(am.isinGame(p)) {
            	   p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "You're already in game.");
               }
        	    String arenaname = sign.getLines()[1];
                Arena arena = am.getArena(arenaname);
                if(arena == null) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "No Arena found with that name.");
                }
                   
                am.addPlayer(p, arena);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.GREEN + "Succesfully joined arena: " + arena.getName());
                   
                Location lobby = arena.getLobby();
                p.teleport(lobby);
               
               
            	//World w = Bukkit.getServer().getWorld(settings.getData().getString(arenaname + ".world"));
                //double x = settings.getData().getDouble(arenaname + ".x");
                //double y = settings.getData().getDouble(arenaname + ".y");
                //double z = settings.getData().getDouble(arenaname + ".z");
                //float pitch = (float) settings.getData().getDouble(arenaname + ".pitch");
                //float yaw = (float) settings.getData().getDouble(arenaname + ".yaw");
                //p.teleport(new Location(w, x, y, z, yaw, pitch));
            }
        }
    }
    }
    
// ##########  Player Leave Server  ########## Player Leave Server  ###############################
    @EventHandler
    public void PlayerLeaveEvent(PlayerQuitEvent e) {
    	Player p = e.getPlayer();
    	if (am.isinGame(p)) {
    		am.removePlayer(p);
    	}
    }

// ##########  Player Death  ########## Player Death  #############################################
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(am.isinGame(e.getEntity())){
            am.removePlayer(e.getEntity());
        }
    }
}