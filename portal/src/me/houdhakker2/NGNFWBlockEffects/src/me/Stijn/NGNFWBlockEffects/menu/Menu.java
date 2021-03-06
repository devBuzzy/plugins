package me.Stijn.NGNFWBlockEffects.menu;

import me.Stijn.NGNFWBlockEffects.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu implements Listener {
	
	Inventory inv = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', "&bBlock Effects"));
	
	// ########## Menu #######################################################################
	public void openGUI(Player player) {
		

		// *LaunchPad
		ItemStack launchpad = new ItemStack(Material.getMaterial(Main.config.getString("Launchpad.BlockId")));
		ItemMeta launchpadMeta = launchpad.getItemMeta();
		launchpadMeta.setDisplayName("LaunchPad");
		launchpad.setItemMeta(launchpadMeta);
		inv.setItem(0, launchpad);

		// *Trampoline
		ItemStack trampoline = new ItemStack(Material.getMaterial(Main.config.getString("Trampoline.BlockId")));
		ItemMeta trampolineMeta = trampoline.getItemMeta();
		trampolineMeta.setDisplayName("Trampoline");
		trampoline.setItemMeta(trampolineMeta);
		inv.setItem(1, trampoline);

		player.openInventory(inv);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getTitle() == "&bBlock Effects") {
			ItemStack launchpad = new ItemStack(Material.getMaterial(Main.config.getString("Launchpad.BlockId")));
			ItemMeta launchpadMeta = launchpad.getItemMeta();
			launchpadMeta.setDisplayName("LaunchPad");
			launchpad.setItemMeta(launchpadMeta);
			inv.setItem(0, launchpad);

			// *Trampoline
			ItemStack trampoline = new ItemStack(Material.getMaterial(Main.config.getString("Trampoline.BlockId")));
			ItemMeta trampolineMeta = trampoline.getItemMeta();
			trampolineMeta.setDisplayName("Trampoline");
			trampoline.setItemMeta(trampolineMeta);
			inv.setItem(1, trampoline);
		}
	}

}
