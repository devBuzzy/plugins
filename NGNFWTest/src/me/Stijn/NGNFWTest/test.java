package me.Stijn.NGNFWTest;
 
import java.util.ArrayList;
import java.util.List;
 
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;
 
public class test extends JavaPlugin implements Listener
{
        List<String> frozen =  new ArrayList<String>();
       
 
        public boolean cuffed(Player p)
        {
                return frozen.contains(p.getName());
        }
       
        public void onEnable()
        {
                Bukkit.getPluginManager().registerEvents(this, this);
        }
       
        public void freeze(Player p)
        {
                frozen.add(p.getName());
        }
       
        public void unfreeze(Player p)
        {
                frozen.remove(p.getName());
        }
       
        public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
        {
                if (cmd.getName().equalsIgnoreCase("sit") && sender.hasPermission("sit.sit"))
                {
                        if (Bukkit.getPlayer(args[0]) != null && !cuffed(Bukkit.getPlayer(args[0])))
                        {
                                Player p = Bukkit.getPlayer(args[0]);
                                p.setPassenger(p);
                                sender.sendMessage("Frozen player");
                                freeze(p);
                                return true;
                        }
                        else
                        {
                                sender.sendMessage("No such player or already frozen");
                                return false;
                        }
                }
               
                if (cmd.getName().equalsIgnoreCase("stand") && sender.hasPermission("sit.stand"))
                {
                        if (Bukkit.getPlayer(args[0]) != null && cuffed(Bukkit.getPlayer(args[0])))
                        {
                                Player p = Bukkit.getPlayer(args[0]);
                                sender.sendMessage("Unfrozen player");
                                p.eject();
                                unfreeze(p);
                                return true;
                        }
                        else
                        {
                                sender.sendMessage("No such player or not frozen");
                                return false;
                        }
                }
                return false;
        }
       
        @EventHandler
        public void onInteract(PlayerInteractEvent e)
        {
                if(!cuffed(e.getPlayer()))
                        return;
               
                e.setCancelled(true);
        }
 
        @EventHandler
        public void onRun(PlayerMoveEvent e)
        {
               
                if(!cuffed(e.getPlayer()))
                        return;
               
                        e.getPlayer().setSprinting(false);
                        e.setCancelled(true);
        }
       
 
        @EventHandler
        public void onHurt(EntityDamageEvent e)
        {
                if (e.getEntityType().equals(EntityType.PLAYER))
                {
                        Player p = (Player) e.getEntity();
                if(!cuffed(p))
                        return;
               
                e.setCancelled(true);
                }
        }
       
        @EventHandler
        public void onHit(EntityDamageByEntityEvent e){
                if(e.getDamager() instanceof Player){
                        if(!cuffed((Player) e.getDamager()))
                                return;
                       
                        e.setCancelled(true);
                }
        }
       
        @EventHandler
        public void onPickUp(PlayerPickupItemEvent e){
                Player player = e.getPlayer();
               
                if(cuffed(player))
                        e.setCancelled(true);
        }
       
        @EventHandler
        public void onChangeInv(InventoryOpenEvent e){
                Player player = (Player) e.getPlayer();
               
                if(cuffed(player))
                        e.setCancelled(true);
        }
       
        @EventHandler
        public void onClick(PlayerInteractEvent e){
                Player player = e.getPlayer();
               
                if(!cuffed(player))
                        return;
               
                e.setCancelled(true);
               
        }
}