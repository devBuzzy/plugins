package me.Stijn.NGNFWRadioSign;

	import org.bukkit.Bukkit;
	import org.bukkit.entity.Player;
	import org.bukkit.event.Listener;
	import org.bukkit.plugin.java.JavaPlugin;
	 
	public class RadioSign extends JavaPlugin implements Listener {
	       
	        public void onEnable() {
	                Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	                        public void run() {
	                                for (Player p : Bukkit.getOnlinePlayers()) {
	                                        ParticleEffect.CLOUD.display(p.getLocation(), 10, 10, 10, 0, 1000);
	                                }
	                        }
	                }, 0, 20);
	        }
	}