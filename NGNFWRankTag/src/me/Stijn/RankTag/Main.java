package me.Stijn.RankTag;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin implements Listener{
	
	public final Logger logger = Logger.getLogger("Minecraft");
	
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
	logger.info("1");
        ScoreboardManager sbManager = Bukkit.getScoreboardManager();
        Scoreboard sb = sbManager.getNewScoreboard();
        Objective rank = sb.registerNewObjective("rank", "dummy");
    logger.info("2");
        rank.setDisplayName("Rank:");
        rank.setDisplaySlot(DisplaySlot.BELOW_NAME);
        p.setScoreboard(sb);
        p.getScoreboard().getObjective(DisplaySlot.BELOW_NAME).getScore(p).setScore(60);
    logger.info("3");
	}
}