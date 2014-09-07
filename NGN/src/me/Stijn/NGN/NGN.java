package me.Stijn.NGN;

import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public class NGN extends JavaPlugin implements Listener {
	 Inventory inv;
	@Override
	 public void onDisable() {
	  getLogger().info("disabled");
	}
	 
	 
	 @Override
	 public void onEnable() {
	  loadConfig();
	  getLogger().info("enabled");
	  getConfig().options().copyDefaults(true);
	  saveConfig();
	  Bukkit.getServer().getPluginManager().registerEvents(this, this);
	  // ##### Inv Animation ##### Animation #####
	  // Useful Symbol: █
//	  inv = Bukkit.createInventory(null, 9, "§0§nThe Magic Inventory");
//       getServer().getScheduler().scheduleSyncRepeatingTask(this,
//    	new Runnable() {
//         public void run() {
        	 
//          for (int i=0;i<30;i++){}
//           if (num == 1) {
//            inv.setItem(0, createItem(Material.EMERALD, 1, (short) 0, "§1§lN§4§lG§a§lN", "§a█" + "§1█" + "§1█" + "§1█" + "§1█" + "§1█" + "§1█" + "§1█" + "§1█" + "§1█" + "§1█" + "§1█" + "§1█" + "§1█" + "§1█" + "§1█"));
//            num++;
//            }
//         }
//        },0,1*2);
      }
	 
	 @EventHandler
	 // ##### Sign Animation #####
	 public void mobdamage(PlayerInteractEvent e) {
	   if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
	   if(e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
	     Sign sign = (Sign) e.getClickedBlock().getState();
	     if(sign.getLine(0).equalsIgnoreCase("[NGN]")) {
	     sign.setLine(0, ChatColor.AQUA + "NGN" );
	     sign.setLine(1, ChatColor.BLUE + "Hallo" );
	     sign.setLine(2, ChatColor.GOLD + e.getPlayer().getName());
	     sign.setLine(3, ChatColor.RED + "!!!" );
	     sign.update();
	     } else {  
	     }
	   } else{
	   }
	   } else {
	   //do nothing
	   }
	 }
	 // Join Item
	 /*
	 public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args) {
		if(label.equalsIgnoreCase("GetItem"));
		Player player = (Player)sender;
		player.sendMessage(ChatColor.GOLD + "Je hebt het power zwaard gekregen :P");
		
		ItemStack sword = new ItemStack(Material.PISTON_MOVING_PIECE); {
			
//		sword.addEnchantment(Enchantment.KNOCKBACK, 2);
		ItemMeta meta = sword.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_AQUA + "Power_zwaard");
		meta.setLore(Arrays.asList(ChatColor.DARK_RED + "--------------------", ChatColor.GRAY + "Kill everyone! :P"));
		sword.setItemMeta(meta);
		player.getInventory().addItem(sword);
		}
		 return false;
		 
	 }
	 */
	// ##### Cast #####
	 public boolean onCommand1(CommandSender sender, Command cmd, String label, String [] args) {
		if(label.equalsIgnoreCase("cast"));
		if(args[0].equalsIgnoreCase("blue")) {
			String toBroadcast = "";
					for (String s : args) {
						if(!s.contains("blue")) {
							toBroadcast = toBroadcast + s + " ";
						}
					}
					Bukkit.broadcastMessage(ChatColor.BLUE + "[SERVER] " + toBroadcast);
		} else if (args[0].equalsIgnoreCase("red")) {
			String toBroadcast = "";
			for (String s : args) {
				if(!s.contains("red")) {
					toBroadcast = toBroadcast + s + " ";
				}
			}
			Bukkit.broadcastMessage(ChatColor.RED + "[SERVER] " + toBroadcast);
		}else{
			String toBroadcast = "";
			for (String s : args) {
					toBroadcast = toBroadcast + s + " ";
				}
			Bukkit.broadcastMessage(ChatColor.BLACK + "[SERVER] " + toBroadcast);			
		}
			return false;
		 }
	
	 // ##### Quit Event #####
	 /*
	 @EventHandler(priority = EventPriority.NORMAL)
	 public void onPlayerQuit (PlayerQuitEvent e) {
		 Player p = e.getPlayer();
		 String msg = this.getConfig().getString("messages.quit");
		 msg = msg.replace("%PLAYER%", p.getName());
		 e.setQuitMessage(msg);
	 }
	 */
	 
	// ##### Load Config #####
	 public void loadConfig() {
		 FileConfiguration cfg = this.getConfig();
		 cfg.options().copyDefaults(true);
		 this.saveConfig();
	 }
}
