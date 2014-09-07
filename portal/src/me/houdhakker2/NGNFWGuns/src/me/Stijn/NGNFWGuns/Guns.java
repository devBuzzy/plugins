package me.Stijn.NGNFWGuns;

import java.util.HashMap;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Guns extends JavaPlugin implements Listener {
	
	private HashMap<String, Fireball> shooters = new HashMap<String, Fireball>();
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			if(p.getItemInHand().getType() == Material.STICK) {
				Gun(p);
			}
		}
	}
	
	private void Gun(Player p) {
		Arrow ar = p.launchProjectile(Arrow.class);
		ar.setShooter(p);
		ar.setVelocity(p.getLocation().getDirection().multiply(1.0));
		World world = p.getWorld();
		world.playSound(p.getLocation(), Sound.CLICK, 1, 2);
	}
	
	@EventHandler
	public void onHit(ProjectileHitEvent e) {
		if(e.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getEntity();
			Location loc = arrow.getLocation();
			World world = arrow.getWorld();
			double x = loc.getX();
			double y = loc.getY();
			double z = loc.getZ();
			
			world.createExplosion(x, y, z, 1, true, true);
		}
		else if(e.getEntity() instanceof Snowball) {
				Snowball arrow = (Snowball) e.getEntity();
				Location loc = arrow.getLocation();
				World world = arrow.getWorld();
				double x = loc.getX();
				double y = loc.getY();
				double z = loc.getZ();
				
				world.createExplosion(x, y, z, 1, true, true);
				arrow.getWorld().playEffect(arrow.getLocation(), Effect.SMOKE, 100);
		}
	}
	
	/*@EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
            if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) return;
           
            if (!(e.getItem().getType() == Material.IRON_SPADE)) return;
           
            Fireball f = e.getPlayer().launchProjectile(Fireball.class);
            f.setIsIncendiary(false);
            f.setYield(0);
           
            shooters.put(e.getPlayer().getName(), f);
           
            e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.SMOKE, 1);
    }
   
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Snowball) {
                    Snowball f = (Snowball) e.getDamager();
                    if (f.getShooter() instanceof Player) {
                            Player shooter = (Player) f.getShooter();
                            if (shooters.get(shooter).equals(f)) {
                                    e.setDamage(10.0);
                                    f.getWorld().playEffect(f.getLocation(), Effect.SMOKE, 100);
                            }
                    }
            }
    }*/
}
