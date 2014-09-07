package me.Stijn.NGNFWBlockExplosion;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class BlockExplosion extends JavaPlugin implements Listener {
	
	public static void main(String[] args) {
		System.out.println((float) -5 + (float) (Math.random() * ((5 - -5) +1)));
	}
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
  for (Block b : event.blockList()) {
   float x = (float) -1 + (float) (Math.random() * 5);
   float y = (float) -2 + (float) (Math.random() * 5);
   float z = (float) -1 + (float) (Math.random() * 5);
   
   if(b.getType() == Material.TNT){
    
    final Entity fallingBlock = b.getWorld().spawnEntity(b.getLocation(), EntityType.PRIMED_TNT);
    fallingBlock.setVelocity(new Vector(x, y, z));
    
    Location loc = b.getLocation();
    loc.getWorld().playEffect(loc, Effect.EXPLOSION_HUGE, 69);
    
   } else {
    
    @SuppressWarnings("deprecation")
    final FallingBlock fallingBlock = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
    fallingBlock.setVelocity(new Vector(x, y, z));
    
    Location loc = b.getLocation();
    loc.getWorld().playEffect(loc, Effect.SMOKE, 69);
    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
     int  i = 0;
     public void run(){
      
      if (!fallingBlock.isOnGround()){
       if (i < 20 * 8 ){
        
        i++;
        FallingBlock block = fallingBlock;
        
        Location loc = block.getLocation();
        
        loc.getWorld().playEffect(loc, Effect.SMOKE, 69);
       }
      }
      
     }
    }, 0, 1);
   }
   b.setType(Material.AIR);
     }
    }

}
