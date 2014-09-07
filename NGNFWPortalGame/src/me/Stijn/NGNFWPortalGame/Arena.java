package me.Stijn.NGNFWPortalGame;

import java.util.ArrayList;
import java.util.List;
 
import org.bukkit.Location;
 
public class Arena{
       
        private Location spawn;
        private Location lobby = null;
        private int id;
        private String name;
        private List<String> players = new ArrayList<String>();
       
        public Arena(int id, Location loc, String name) {
                this.spawn = loc;
                this.spawn = loc;
                this.id = id;
                this.name = name;
        }
       
        public int getId() {
                return this.id;
        }
       
        public List<String> getPlayers() {
                return this.players;
        }
       
        public String getName() {
                return this.name;
        }
       
        public Location getSpawn() {
                return this.spawn;
        }
       
        public void setSpawn(Location loc) {
                this.spawn = loc;
        }
       
        public void setLobby(Location loc) {
                this.lobby = loc;
        }
       
        public Location getLobby() {
                return this.lobby;
        }
}
