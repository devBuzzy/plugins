package me.Stijn.Wand;

import net.bigteddy98.wandapi.Wand;
import net.bigteddy98.wandapi.WandMaker;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		Wand w = WandMaker.registerWand(ChatColor.AQUA + "TestWand", Material.BLAZE_ROD);
		w.setBlockbreak(Material.DRAGON_EGG);
		w.setSelectMessage("You have selected: %spell%!");
		w.registerSpell(new Spark());
		
	}

}
