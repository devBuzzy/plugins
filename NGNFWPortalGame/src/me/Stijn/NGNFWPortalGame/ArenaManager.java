package me.Stijn.NGNFWPortalGame;

import java.util.ArrayList;
import java.util.List;
 


import org.bukkit.Location;
import org.bukkit.entity.Player;
 
public class ArenaManager {
        public List<Arena> arenas = new ArrayList<Arena>();
        public List<Lobby> lobbys = new ArrayList<Lobby>();
		private String titlecommands;
       
        public ArenaManager(String titlecommands) {
               this.titlecommands = titlecommands;
        }
       
        public ArenaManager getManager() {
                return this;
        }
       
        public Arena getArena(int id) {
                for(Arena a : arenas) {
                        if(a.getId() == id) {
                                return a;
                        }
                }
                return null;
        }
       
        public Arena getArena(String name) {
                for(Arena a : arenas) {
                        if(a.getName().equalsIgnoreCase(name)) {
                                return a;
                        }
                }
                return null;
        }
       
        public Lobby getLobby(int id) {
                for(Lobby l : lobbys) {
                        if(l.getId() == id) {
                                return l;
                        }
                }
                return null;
        }
       
        public Lobby getLobby(String name) {
                for(Lobby l : lobbys) {
                        if(l.getName().equalsIgnoreCase(name)) {
                                return l;
                        }
                }
                return null;
        }
        
        public void CreateArena(Location loc, String name, int  id){
        	Arena arena = new Arena(id, loc, name);
        	arenas.add(arena);
        	
        	SettingsManager settings = SettingsManager.getInstance();
        	if (settings.getData().get("Arenas." + id + ".lobby") != null) {
        		Lobby lobby = new Lobby(id, loc, name, arena, titlecommands);
                lobbys.add(lobby);
        	}
        }
        
        public void CreateArena(Location loc, String name) {
                int id = arenas.size() + 1;
               
                SettingsManager settings = SettingsManager.getInstance();
                // Add arena
                Arena arena = new Arena(id, loc, name);
                settings.getData().set("Arenas." + id 				, id);
                settings.getData().set("Arenas." + id + ".Loc.world", loc.getWorld().getName());
                settings.getData().set("Arenas." + id + ".Loc.x"	, loc.getX());
                settings.getData().set("Arenas." + id + ".Loc.y"	, loc.getY());
                settings.getData().set("Arenas." + id + ".Loc.z"	, loc.getZ());
                settings.getData().set("Arenas." + id + ".Loc.yaw"	, loc.getYaw());
                settings.getData().set("Arenas." + id + ".Loc.pitch", loc.getPitch());
                settings.getData().set("Arenas." + id + ".Name"		, name);
                settings.saveData();
                arenas.add(arena);
                
                settings.getData().set("Amount_Of_Arenas", settings.getData().getInt("Amount_Of_Arenas") + 1 );
                
                // Add lobby
            	if (settings.getData().get("Arenas." + id + ".lobby") != null) {
            		Lobby lobby = new Lobby(id, loc, name, arena, titlecommands);
                    lobbys.add(lobby);
            	}
        }
       
        public void addPlayer(Player p, Arena arena) {
        	if (arena != null){
                arena.getPlayers().add(p.getName());
                p.teleport(arena.getSpawn());
        	}
        }
       
        public void removePlayer(Player p) {
                Arena a = null;
                for(Arena arena : arenas) {
                        for(String names : arena.getPlayers()) {
                                if(names.equalsIgnoreCase(p.getName())) {
                                        a = arena;
                                }
                        }
                }
                for(Lobby l : lobbys) {
                        if(l.getArena() == a) {
                                p.teleport(l.getLocation());
                        }
                }
               
                if(a == null) {
                        return;
                }
                a.getPlayers().remove(p.getName());
                p.teleport(a.getLobby());
        }
       
        public boolean isinGame(Player p) {
                for(Arena arena : arenas) {
                        for(String names : arena.getPlayers()) {
                                if(names.equalsIgnoreCase(p.getName())) {
                                        return true;
                                }
                        }
                }
                return false;
        }
}