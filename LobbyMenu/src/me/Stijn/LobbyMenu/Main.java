package me.Stijn.LobbyMenu;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public final Logger logger = Logger.getLogger("Minecraft");
	FileConfiguration config;
	File cfile;
    public PluginDescriptionFile pdfFile = getDescription();
    public static Inventory myInventory = Bukkit.createInventory(null, 9, "My custom Inventory!");
	
    @Override
    public void onEnable() {
         
         logger.info("*--==[ NGNFW PortalGame ]==--*");
    		logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now enabled!");
    		
    		Bukkit.getServer().getPluginManager().registerEvents(this, this);
    		
 		config = getConfig();
 		config.options().copyDefaults(true);
 		saveConfig();
 		
 		cfile = new File(getDataFolder(), "config.yml");
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked(); // The player that clicked the item
    ItemStack clicked = event.getCurrentItem(); // The item that was clicked
    Inventory inventory = event.getInventory(); // The inventory that was clicked in
    if (inventory.getName().equals(myInventory.getName())) { // The inventory is our custom Inventory
    if (clicked.getType() == Material.DIRT) { // The item that the player clicked it dirt
    event.setCancelled(true); // Make it so the dirt is back in its original spot
    player.closeInventory(); // Closes there inventory
    player.getInventory().addItem(new ItemStack(Material.DIRT, 1)); // Adds dirt
    }
    }
    }

}
