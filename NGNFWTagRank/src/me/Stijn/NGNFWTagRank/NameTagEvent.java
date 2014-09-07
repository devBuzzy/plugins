package me.Stijn.NGNFWTagRank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.kitteh.tag.PlayerReceiveNameTagEvent;
import org.kitteh.tag.TagAPI;

@SuppressWarnings("deprecation")
public class NameTagEvent implements Listener{
	
	@EventHandler
	public void onNameTag(final PlayerReceiveNameTagEvent e){
		final Player p = e.getNamedPlayer();
		if (p.hasPermission("ngnfw.tagrank.color")) {
			Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) this, new Runnable(){
			public void run(){
				e.setTag(ChatColor.AQUA + p.getPlayerListName());
				TagAPI.refreshPlayer(p);
				e.setTag(ChatColor.BLUE + p.getPlayerListName());
				TagAPI.refreshPlayer(p);
				e.setTag(ChatColor.DARK_AQUA + p.getPlayerListName());
				TagAPI.refreshPlayer(p);
				e.setTag(ChatColor.DARK_BLUE + p.getPlayerListName());
				TagAPI.refreshPlayer(p);
				e.setTag(ChatColor.DARK_GRAY + p.getPlayerListName());
				TagAPI.refreshPlayer(p);
				e.setTag(ChatColor.DARK_GREEN + p.getPlayerListName());
				TagAPI.refreshPlayer(p);
				e.setTag(ChatColor.DARK_PURPLE + p.getPlayerListName());
				TagAPI.refreshPlayer(p);
				e.setTag(ChatColor.YELLOW + p.getPlayerListName());
				TagAPI.refreshPlayer(p);
			}
		}, 0, 5 * 20); // 20 ticks = 1 second. So 5 * 20 = 100 which is 5 seconds
		}
		if (p.hasPermission("ngnfw.tagrank.admin")) {
			e.setTag(ChatColor.RED + p.getPlayerListName());
		}
		if (p.hasPermission("ngnfw.tagrank.owner")) {
			e.setTag(ChatColor.AQUA + p.getPlayerListName());
		}
		else {
			e.setTag(ChatColor.MAGIC + p.getPlayerListName());
		}
	}
}
