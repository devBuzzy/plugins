package me.Stijn.Kits.api;

import me.Stijn.Kits.Kits;
import me.Stijn.Kits.configuration.CollectionManager;
import me.Stijn.Kits.permissions.Permissions;
import me.Stijn.Kits.Util.DelayedPlayer;
import me.Stijn.Kits.Util.Message;
import me.Stijn.Kits.Util.Message.MessageType;
import me.Stijn.Kits.Util.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class KitManager
{
  private Kits plugin;

  public KitManager(Kits instance)
  {
    this.plugin = instance;
  }

  public Kit createKit(String kitName, ItemStack[] contents)
  {
    return createKit(kitName, contents, 0L, true, true);
  }

  public Kit createKit(String kitName, ItemStack[] contents, long delay, boolean overwrite, boolean announce)
  {
    String name = Utils.capitalize(kitName);
    Kit kit = new Kit(name, contents, delay, overwrite, announce);
    this.plugin.getCollectionManager().addKit(kit);
    return kit;
  }

  public void editKit(String kitName, ItemStack[] contents, long delay, boolean overwrite, boolean announce)
    throws KitException
  {
    if (!kitExists(kitName)) throw new KitException("The kit " + kitName + " does not exist.");

    editKit(getKit(kitName), contents, delay, overwrite, announce);
  }

  public void editKit(Kit kit, ItemStack[] contents, long delay, boolean overwrite, boolean announce)
  {
    kit.setItems(contents);
    kit.setDelay(delay);
    kit.setOverwrite(overwrite);
    kit.setAnnounce(announce);
  }

  public Kit getKit(String kitName)
  {
    return this.plugin.getCollectionManager().getKit(Utils.capitalize(kitName.toLowerCase()));
  }

  public boolean kitExists(String kitName)
  {
    return getKit(kitName) != null;
  }

  public void removeKit(String kitName)
    throws KitException
  {
    if (kitExists(kitName)) this.plugin.getCollectionManager().removeKit(getKit(kitName)); else
      throw new KitException("The kit " + kitName + " does not exist.");
  }

  public void spawnKit(Player player, String kitName)
    throws KitException
  {
    spawnKit(player, kitName, "");
  }

  public void spawnKit(Player player, String kitName, String flags)
    throws KitException
  {
    if (kitExists(kitName)) spawnKit(player, getKit(kitName), flags); else
      throw new KitException("The kit " + kitName + " does not exist.");
  }

  public void spawnKit(Player player, String kitName, String[] flags)
    throws KitException
  {
    if (kitExists(kitName)) spawnKit(player, getKit(kitName), flags); else
      throw new KitException("The kit " + kitName + " does not exist.");
  }

  public void spawnKit(Player player, String kitName, HashMap<String, Boolean> flags)
    throws KitException
  {
    if (kitExists(kitName)) spawnKit(player, getKit(kitName), flags); else
      throw new KitException("The kit " + kitName + " does not exist.");
  }

  public void spawnKit(Player player, Kit kit, String flags)
  {
    spawnKit(player, kit, flags.split(" "));
  }

  public void spawnKit(Player player, Kit kit, String[] flags)
  {
    List flagList = Arrays.asList(flags);
    HashMap Flags = new HashMap();

    for (String flag : flagList) {
      if ((!flag.isEmpty()) && (flag.length() >= 2)) {
        Flags.put(flag.replace("+", "").replace("-", ""), Boolean.valueOf(!flag.startsWith("-")));
      }
    }
    spawnKit(player, kit, Flags);
  }

  public void spawnKit(Player player, Kit kit, HashMap<String, Boolean> flags)
  {
    long delay = Permissions.hasPermission(player, "kits.nodelay", new String[] { kit.getName() }) ? 0L : kit.getDelay();
    boolean overwrite = kit.getOverwrite();
    boolean announce = kit.getAnnounce();

    for (String flag : flags.keySet()) {
      switch (flag) {
      case "overwrite":
        overwrite = ((Boolean)flags.get(flag)).booleanValue();
        break;
      case "announce":
        announce = ((Boolean)flags.get(flag)).booleanValue();
        break;
      case "delay":
        delay = ((Boolean)flags.get(flag)).booleanValue() ? delay : 0L;
      }

    }

    spawnKit(player, kit, delay, overwrite, announce);
  }

  public void spawnKit(Player player, String kitName, long delay, boolean overwrite, boolean announce)
    throws KitException
  {
    if (kitExists(kitName)) spawnKit(player, getKit(kitName), delay, overwrite, announce); else
      throw new KitException("The kit " + kitName + " does not exist.");
  }

  public void spawnKit(Player player, Kit kit, long delay, boolean overwrite, boolean announce)
  {
    if ((this.plugin.getCollectionManager().getDelayedPlayer(player).playerDelayed(kit)) && (kit.getDelay() == delay) && (delay > 0L)) {
      String message = player.getName() + " is currently delayed for kit " + kit.getName() + ". Use the -delay flag to override this.";
      this.plugin.getLogger().warning(message);
      return;
    }

    List items = new ArrayList(Arrays.asList(kit.getItems()));
    Collections.replaceAll(items, null, new ItemStack(Material.AIR));

    ItemStack[] armor = { (ItemStack)items.remove(0), (ItemStack)items.remove(0), (ItemStack)items.remove(0), (ItemStack)items.remove(0) };
    ArrayUtils.reverse(armor);

    for (int i = 0; i < 5; i++) {
      items.remove(0);
    }
    if (overwrite) {
      player.getInventory().clear();

      for (int i = 0; i < items.size(); i++)
        player.getInventory().setItem(i + 9 < 36 ? i + 9 : i - 27, (ItemStack)items.get(i));
    }
    else {
      player.getInventory().addItem((ItemStack[])items.toArray(new ItemStack[items.size()]));
    }
    player.getInventory().setArmorContents(armor);

    if (delay > 0L) {
      this.plugin.getCollectionManager().getDelayedPlayer(player).addKit(kit);
    }
    if (announce) player.sendMessage(Message.show("Kit " + kit.getName() + " spawned.", Message.MessageType.INFO));
  }
}