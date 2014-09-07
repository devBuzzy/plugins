package me.Stijn.AnimatedMenu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	

	// Useful Symbol: █

	Inventory inv;

	int num = 1;

	public void onEnable() {	
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		inv = Bukkit.createInventory(null, 9, "§4§lPortal: §f§lTutorial");

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
				new Runnable() {
					public void run() {
						List<String> line = new ArrayList<String>();
						line.add(ChatColor.translateAlternateColorCodes('&', "&f\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592"));
						line.add(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592&f\u2592            "));
						line.add(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&0\u2592&4\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&f\u2592            "));
						line.add(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&f\u2592            "));
						line.add(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&4\u2592&0\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&f\u2592            "));
						line.add(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&f\u2592            "));
						line.add(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592&4\u2592&0\u2592&0\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&4\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&0\u2592&4\u2592&0\u2592&4\u2592&4\u2592&4\u2592&0\u2592&f\u2592            "));
						line.add(ChatColor.translateAlternateColorCodes('&', "&f\u2592&0\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592&f\u2592            "));
						line.add(ChatColor.translateAlternateColorCodes('&', "&f\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592"));
						
						String[] displayname = {"0█","1","2","3","4","5","6","7"};
						
						List<String> lore = line;
					
						animate(inv,0,Material.APPLE,3,displayname,lore);
						
						displayname[0] = "derp";
						displayname[6] = "lol";
						animate(inv,1,Material.DIRT,3,displayname,lore);
						num ++;
					}
				}, 0, 1 * 10);
		}
	
	public void animate(Inventory inv, int slot, Material material, int amount, String[] displayname, List<String> lore){
		if(num > displayname.length){
			num = 1;
		}
		switch(num){
		case 1: inv.setItem(slot, make(material, amount, (short) 0, displayname[0], lore)); break;
		case 2: inv.setItem(slot, make(material, amount, (short) 0, displayname[1], lore)); break;
		case 3: inv.setItem(slot, make(material, amount, (short) 0, displayname[2], lore)); break;
		case 4: inv.setItem(slot, make(material, amount, (short) 0, displayname[3], lore)); break;
		case 5: inv.setItem(slot, make(material, amount, (short) 0, displayname[4], lore)); break;
		case 6: inv.setItem(slot, make(material, amount, (short) 0, displayname[5], lore)); break;
		case 7: inv.setItem(slot, make(material, amount, (short) 0, displayname[6], lore)); break;
		case 8: inv.setItem(slot, make(material, amount, (short) 0, displayname[7], lore)); break;
	}                                  
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getInventory().getTitle() == "§4§lPortal: §f§lTutorial"){
			Player player = (Player)e.getWhoClicked();
			e.setCancelled(true);
		}
	}

	public ItemStack make(Material material, int amount, int shrt, String displayname, List<String> lore1) {
		ItemStack item = new ItemStack(material, amount, (short) shrt);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayname);
		meta.setLore(lore1);
		item.setItemMeta(meta);
		return item;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] a) {
		final Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("animatedmenu")) {
			player.openInventory(inv);
		}
		return false;
	}

}
