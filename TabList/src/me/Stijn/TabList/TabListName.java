package me.Stijn.TabList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TabListName implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		
		// Kleur code: §
		int spelernaamlengte = p.getName().length();
		
		if((spelernaamlengte == 16)){
			String name = p.getName();
			name = name.substring(0, name.length() -1);
			p.setPlayerListName("§4" + name);
			return;
		}
		if(spelernaamlengte == 15){
			String name = p.getName();
			name = name.substring(0, name.length() -1);
			p.setPlayerListName("§4" + name);
			return;
		}
		p.setPlayerListName("§4" + p.getName());
	}

}
