package me.Stijn.Kits.configuration;

import me.Stijn.Kits.Kits;
import me.Stijn.Kits.api.Kit;
import me.Stijn.Kits.Util.DelayedPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CollectionManager
{
  public Config kitConfig;
  public List<Kit> kitList;
  public static Config playerConfig;
  public static List<DelayedPlayer> playerList;

  public CollectionManager()
  {
    this.kitList = new ArrayList();
    playerList = new ArrayList();
  }

  public void save() {
    this.kitConfig.set("kits", this.kitList);
    this.kitConfig.save();

    for (DelayedPlayer player : playerList) {
      player.sortKits(this);
    }
    playerConfig.set("players", playerList);
    playerConfig.save();
  }

  public void load(Kits plugin)
  {
    this.kitConfig = new Config(plugin, "kits");
    migrateOldKitsFile();
    this.kitList = (this.kitConfig.get("kits") == null ? new ArrayList() : (List)this.kitConfig.get("kits"));

    playerConfig = new Config(plugin, "players");
    playerList = playerConfig.get("players") == null ? new ArrayList() : playerConfig.getList("players");
  }

  private void migrateOldKitsFile()
  {
    if (this.kitConfig.contains("kits")) return;

    List newKits = new ArrayList();

    for (String key : this.kitConfig.getKeys(false)) {
      ItemStack[] items = (ItemStack[])((ArrayList)this.kitConfig.get(key + ".kit")).toArray(new ItemStack[((ArrayList)this.kitConfig.get(key + ".kit")).size()]);
      ArrayUtils.reverse(items);
      newKits.add(new Kit(key, items, this.kitConfig.getLong(key + ".delay"), this.kitConfig.getBoolean(key + ".overwrite"), true));
    }

    for (Kit kit : newKits) {
      this.kitConfig.set(kit.getName(), null);
    }
    this.kitConfig.set("kits", newKits);
  }

  public void reload(Kits plugin) {
    if ((this.kitConfig != null) && (playerConfig != null)) save();
    load(plugin);
  }

  public Kit getKit(String name) {
    for (Kit kit : this.kitList) {
      if (kit.getName().equalsIgnoreCase(name)) return kit;
    }
    return null;
  }

  public void addKit(Kit kit) {
    this.kitList.add(kit);
  }

  public void removeKit(Kit kit) {
    this.kitList.remove(kit);
  }

  public DelayedPlayer getDelayedPlayer(Player player) {
    for (DelayedPlayer delayedPlayer : playerList)
      try {
        if (delayedPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return delayedPlayer; 
      }
      catch (Exception ex) {  }

    DelayedPlayer delayedPlayer = new DelayedPlayer(player);
    playerList.add(delayedPlayer);
    return delayedPlayer;
  }

  public List<Kit> getKitList() {
    return this.kitList;
  }

  public List<DelayedPlayer> getDelayedPlayers() {
    return playerList;
  }
}