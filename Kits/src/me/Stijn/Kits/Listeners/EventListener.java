package me.Stijn.Kits.Listeners;

import me.Stijn.Kits.Kits;
import me.Stijn.Kits.api.Kit;
import me.Stijn.Kits.api.KitException;
import me.Stijn.Kits.api.KitManager;
import me.Stijn.Kits.configuration.CollectionManager;
import me.Stijn.Kits.permissions.Permissions;
import me.Stijn.Kits.Util.Message;
import me.Stijn.Kits.Util.Message.MessageType;
import me.Stijn.Kits.Util.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class EventListener
  implements Listener
{
  public Kits plugin;

  public EventListener(Kits instance)
  {
    this.plugin = instance;
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    if (event.getInventory().getName().toLowerCase().startsWith("new kit: ")) {
      CreateKit((Player)event.getPlayer(), event.getInventory());
    }
    if (event.getInventory().getName().toLowerCase().startsWith("edit kit: "))
      EditKit((Player)event.getPlayer(), event.getInventory());
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    if ((event.getAction() != Action.RIGHT_CLICK_BLOCK) && (event.getAction() != Action.RIGHT_CLICK_AIR)) return;
    if (event.isBlockInHand()) return;
    if ((event.getClickedBlock() == null) || ((event.getClickedBlock().getType() != Material.SIGN_POST) && (event.getClickedBlock().getType() != Material.WALL_SIGN))) return;

    Sign sign = (Sign)event.getClickedBlock().getState();

    if (sign.getLines().length < 2) return;
    if (!sign.getLine(0).equalsIgnoreCase("[kit]")) return;
    if (!Permissions.checkPermission(event.getPlayer(), "kits.sign")) return;

    List lines = new ArrayList(Arrays.asList(StringUtils.join(sign.getLines(), " ").split(" ")));
    lines.removeAll(Arrays.asList(new String[] { "", null }));

    String[] arrayLines = Utils.trim((String[])lines.toArray(new String[lines.size()]));
    try
    {
      this.plugin.getKitManager().spawnKit(event.getPlayer(), arrayLines[0], Utils.trim(arrayLines));
    } catch (KitException e) {
      Bukkit.getLogger().warning("The sign at " + Utils.getLocationationAsString(event.getClickedBlock().getLocation()) + " threw an exception: " + e.getMessage());
    }
    event.getPlayer().updateInventory();
  }

  @EventHandler
  public void onSignChange(SignChangeEvent event) {
    if ((event.getLine(0).equalsIgnoreCase("[kit]")) && (!Permissions.checkPermission(event.getPlayer(), "kits.admin")))
      event.setCancelled(true);
  }

  public void CreateKit(Player player, Inventory inventory)
  {
    String inventoryName = inventory.getName().toLowerCase().replace("new kit: ", "");

    Kit kit = this.plugin.getKitManager().createKit(inventoryName, inventory.getContents());

    player.sendMessage(Message.show("Kit " + kit.getName() + " created.", Message.MessageType.INFO));
  }

  public void EditKit(Player player, Inventory inventory)
  {
    String inventoryName = inventory.getName().toLowerCase().replace("edit kit: ", "");

    String name = Utils.capitalize(inventoryName);

    this.plugin.getCollectionManager().getKit(name).setItems(inventory.getContents());

    player.sendMessage(Message.show("Kit " + name + " edited.", Message.MessageType.INFO));
  }
}