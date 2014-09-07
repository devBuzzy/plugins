package me.Stijn.NGNFWPortalGame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Lobby {
	
	private List<String> players = new ArrayList<String>();
	// private int MAXSIDE
	private int size;
	private String name;
	private Location loc;
	private int id;
	private Arena arena;
	private String titlecommands;
	
	public Lobby(int id, Location loc, String name, Arena arena, String titlecommands) {
		this.setId(id);
		this.setLocation(loc);
		this.setName(name);
		this.setArena(arena);
		this.titlecommands = titlecommands;
	}
	
	public List<String> getPlayers() {
		return players;
	}
	
	public void addPlayer(Player p) {
		String name = p.getName();
		
		if(this.players.contains(name)) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', titlecommands) + ChatColor.RED + "You're already in this lobby.");
			return;
		}
		
		this.players.add(name);
		size++;
	}
	
	public void removePlayer(Player p) {
		String name = p.getName();
		
		if(this.players.contains(name)) {
			this.players.remove(name);
			
		}
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}

	public Location getLocation() {
		return loc;
	}

	public void setLocation(Location loc) {
		this.loc = loc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Arena getArena() {
		return arena;
	}

	public void setArena(Arena arena) {
		this.arena = arena;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
