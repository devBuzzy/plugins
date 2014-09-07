package me.Stijn.NGNFWKick;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Kick extends JavaPlugin implements Listener {
 
    public final Logger logger = Logger.getLogger("Minecraft");
    public PluginDescriptionFile pdfFile = getDescription();
	FileConfiguration config;
	File cfile;
 
    @Override
    public void onEnable () {
        logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now enabled!");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		cfile = new File(getDataFolder(), "config.yml");
    }
 
    @Override
    public void onDisable() {
        logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now disabled!");
    }
 
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerLoginEvent e) {
    	if(getConfig().getBoolean("Enabled") == true) {
    		 final Player p = e.getPlayer();
    		if(p.isOp()) {
    			p.sendMessage(ChatColor.BLUE + "Advertising other servers is not permitted here.  Please read the /rules.");
    		} else {
    			e.setKickMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Header") + "\n" + getConfig().getString("Content")));
    			//e.setKickMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Under" + ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Construction" + "\n" + ChatColor.AQUA + "" + ChatColor.BOLD + "Please come back later :D");
    			p.kickPlayer(e.getKickMessage());
    			e.setResult(Result.KICK_FULL);
    			
    		}
    	}
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
    {
        if(cmd.getName().equalsIgnoreCase("ad")) 
        {
            if(sender.hasPermission("cick.ad"))
            {
                if(args.length == 0) 
                {
                    sender.sendMessage(ChatColor.BLUE + "Advertising other servers is not permitted here.  Please read the /rules.");
                }
 
                if(args.length == 1)
                {
                    Player tplayer = getServer().getPlayer(args[0]);
                    if(tplayer != null)
                    {
                        tplayer.kickPlayer(ChatColor.BLUE + "Advertising other servers is not permitted here.  Please read the /rules.");
                    }
                    else
                    {
                        sender.sendMessage("That player is offline!"); 
                    }
                }
            }
            return true;
        }
        return false;
    }
}
