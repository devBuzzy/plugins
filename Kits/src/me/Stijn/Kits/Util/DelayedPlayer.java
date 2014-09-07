package me.Stijn.Kits.Util;

import me.Stijn.Kits.api.Kit;
import me.Stijn.Kits.configuration.CollectionManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class DelayedPlayer
  implements ConfigurationSerializable
{
  private OfflinePlayer player;
  private HashMap<String, Long> kits;

  public DelayedPlayer(Player player)
  {
    this.player = player;
    this.kits = new HashMap();
  }

  public DelayedPlayer(UUID player, HashMap<String, Long> kits) {
    this.player = Bukkit.getPlayer(player);
    this.kits = kits;
  }

  public OfflinePlayer getPlayer() {
    return this.player.isOnline() ? this.player.getPlayer() : this.player;
  }

  public void addKit(Kit kit) {
    this.kits.put(kit.getName(), Long.valueOf(System.currentTimeMillis()));
  }

  public boolean playerDelayed(Kit kit) {
    boolean delayed = false;
    if (this.kits.containsKey(kit.getName())) {
      delayed = System.currentTimeMillis() - ((Long)this.kits.get(kit.getName())).longValue() < kit.getDelay();
      if (!delayed) this.kits.remove(kit.getName());
    }
    return delayed;
  }

  public void sortKits(CollectionManager manager) {
    Iterator iter = this.kits.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry)iter.next();
      if ((manager.getKit((String)entry.getKey()) == null) || (System.currentTimeMillis() - ((Long)entry.getValue()).longValue() >= manager.getKit((String)entry.getKey()).getDelay()))
        iter.remove();
    }
  }

  public Map<String, Object> serialize()
  {
    Map result = new HashMap();

    result.put("player", getPlayer().getUniqueId().toString());
    result.put("kits", this.kits);

    return result;
  }

  public static DelayedPlayer deserialize(Map<String, Object> args)
  {
    return new DelayedPlayer(UUID.fromString((String)args.get("player")), (HashMap)args.get("kits"));
  }
}