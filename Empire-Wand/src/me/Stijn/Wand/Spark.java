package me.Stijn.Wand;

import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.bigteddy98.wandapi.Spell;
import net.bigteddy98.wandapi.WandMaker;

public class Spark implements Spell{

	@Override
	public void castSpell(Player p) {
		Location loc = WandMaker.getPlayerBlock(p, 40);
		WandMaker.playFirework(loc, Type.BURST, Color.AQUA, Color.WHITE, false, true);
		WandMaker.getHelper().damage(loc, 6);
	}

}
