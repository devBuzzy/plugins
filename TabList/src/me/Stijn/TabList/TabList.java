package me.Stijn.TabList;

import net.minecraft.server.v1_7_R2.PacketPlayOutPlayerInfo;

import org.bukkit.craftbukkit.v1_7_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TabList implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		
		// Kleur code: §
		
		PacketPlayOutPlayerInfo p1 = new PacketPlayOutPlayerInfo("   §b----------", true, 0);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(p1);
		
		PacketPlayOutPlayerInfo p2 = new PacketPlayOutPlayerInfo("§6§lFire§4§lWare", true, 0);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(p2);
		
		PacketPlayOutPlayerInfo p3 = new PacketPlayOutPlayerInfo("§b----------", true, 0);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(p3);
		
		PacketPlayOutPlayerInfo p4 = new PacketPlayOutPlayerInfo("    §b----------", true, 0);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(p4);
		
		PacketPlayOutPlayerInfo p5 = new PacketPlayOutPlayerInfo("  §b----------", true, 0);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(p5);
		
		PacketPlayOutPlayerInfo p6 = new PacketPlayOutPlayerInfo(" §b----------", true, 0);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(p6); 
	}
	
}
