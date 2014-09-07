package me.Stijn.Kits.Commands;

import me.Stijn.Kits.Kits;
import me.Stijn.Kits.api.Kit;
import me.Stijn.Kits.api.KitException;
import me.Stijn.Kits.api.KitManager;
import me.Stijn.Kits.configuration.CollectionManager;
import me.Stijn.Kits.permissions.Permissions;
import me.Stijn.Kits.Util.DelayedPlayer;
import me.Stijn.Kits.Util.Message;
import me.Stijn.Kits.Util.Message.MessageType;
import me.Stijn.Kits.Util.Time;
import me.Stijn.Kits.Util.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class KitCommandExecutor
  implements CommandExecutor
{
  private Kits plugin;

  public KitCommandExecutor(Kits instance)
  {
    this.plugin = instance;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args)
  {
    if (args.length < 1) {
      handleBaseCommand(sender);
      return false;
    }

    if (args[0].equalsIgnoreCase("create")) createKit(sender, Utils.trim(args));
    else if (args[0].equalsIgnoreCase("edit")) editKit(sender, Utils.trim(args));
    else if (args[0].equalsIgnoreCase("remove")) removeKit(sender, Utils.trim(args)); else {
      spawnKit(sender, args);
    }
    return false;
  }

  private void handleBaseCommand(CommandSender sender) {
    if ((sender instanceof Player)) {
      Message.showMessage((Player)sender, Message.show("Usage", new StringBuilder().append("/kit create ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.INFO), new String[] { "Create a new kit with the specific name." });
      Message.showMessage((Player)sender, Message.show("Usage", new StringBuilder().append("/kit edit ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.INFO), new String[] { "Edit an existing kit with the specific name." });
      Message.showMessage((Player)sender, Message.show("Usage", new StringBuilder().append("/kit edit ").append(ChatColor.ITALIC).append("kitname [flagname] <flagvalue>").toString(), Message.MessageType.INFO), new String[] { "Edit an existing kit's flags with the specific names." });
      Message.showMessage((Player)sender, Message.show("Usage", new StringBuilder().append("/kit remove ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.INFO), new String[] { "Remove an existing kit with the specific name." });
      Message.showMessage((Player)sender, Message.show("Usage", new StringBuilder().append("/kit ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.INFO), new String[] { "Spawn the kit with the specified name." });
      Message.showMessage((Player)sender, Message.show("Usage", new StringBuilder().append("/kit ").append(ChatColor.ITALIC).append("kitname [flags]").toString(), Message.MessageType.INFO), new String[] { "Spawn the kit with the specified name.", "With the specified flags. Example:", "/kit basic -announce" });
      Message.showMessage((Player)sender, Message.show("Usage", new StringBuilder().append("/kit ").append(ChatColor.ITALIC).append("kitname playername").toString(), Message.MessageType.INFO), new String[] { "Spawn the kit with the specified name.", "For the player with the specified name." });
      Message.showMessage((Player)sender, Message.show("Usage", new StringBuilder().append("/kit ").append(ChatColor.ITALIC).append("kitname playername [flags]").toString(), Message.MessageType.INFO), new String[] { "Spawn the kit with the specified name.", "For the player with the specified name.", "With the specified flags. Example:", "/kit basic bob -announce" });
    }
    else {
      sender.sendMessage(Message.show("Usage", new StringBuilder().append("/kit create ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.INFO));
      sender.sendMessage(Message.show("Usage", new StringBuilder().append("/kit edit ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.INFO));
      sender.sendMessage(Message.show("Usage", new StringBuilder().append("/kit edit ").append(ChatColor.ITALIC).append("kitname [flagname] <flagvalue>").toString(), Message.MessageType.INFO));
      sender.sendMessage(Message.show("Usage", new StringBuilder().append("/kit remove ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.INFO));
      sender.sendMessage(Message.show("Usage", new StringBuilder().append("/kit ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.INFO));
      sender.sendMessage(Message.show("Usage", new StringBuilder().append("/kit ").append(ChatColor.ITALIC).append("kitname [flags]").toString(), Message.MessageType.INFO));
      sender.sendMessage(Message.show("Usage", new StringBuilder().append("/kit ").append(ChatColor.ITALIC).append("kitname playername").toString(), Message.MessageType.INFO));
      sender.sendMessage(Message.show("Usage", new StringBuilder().append("/kit ").append(ChatColor.ITALIC).append("kitname playername [flags]").toString(), Message.MessageType.INFO));
    }
  }

  private void createKit(CommandSender sender, String[] args)
  {
    if (!IsPlayer(sender)) return;

    Player player = (Player)sender;

    if (!Permissions.checkPermission(player, "kits.admin")) return;

    if (args.length < 1) {
      Message.showMessage(player, Message.show("Usage", new StringBuilder().append("/kit create ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.INFO), new String[] { "Create a new kit with the specific name." });
      return;
    }

    if (args.length > 1) {
      player.sendMessage(Message.show("Kit names cannot contain spaces.", Message.MessageType.WARNING));
      return;
    }

    if (this.plugin.getKitManager().kitExists(args[0])) {
      player.sendMessage(Message.show(new StringBuilder().append("Kit ").append(args[0]).append(" already exists.").toString(), Message.MessageType.WARNING));
      return;
    }

    if (args[0].length() > 22) {
      player.sendMessage(Message.show("Kit name cannot exceed 22 characters.", Message.MessageType.WARNING));
      return;
    }

    Inventory inventory = Bukkit.createInventory(player, 45, new StringBuilder().append("New kit: ").append(args[0]).toString());
    player.openInventory(inventory);
  }

  private void editKit(CommandSender sender, String[] args)
  {
    if (!IsPlayer(sender)) return;

    Player player = (Player)sender;

    if (!Permissions.checkPermission(player, "kits.admin")) return;

    if (args.length < 1) {
      Message.showMessage(player, Message.show("Usage", new StringBuilder().append("/kit edit ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.INFO), new String[] { "Edit an existing kit with the specific name." });
      Message.showMessage(player, Message.show("Usage", new StringBuilder().append("/kit edit ").append(ChatColor.ITALIC).append("kitname flagname").toString(), Message.MessageType.INFO), new String[] { "Edit an existing kit's flags with the specific names." });
      return;
    }

    if (!this.plugin.getKitManager().kitExists(args[0])) {
      player.sendMessage(Message.show(new StringBuilder().append("Kit ").append(args[0]).append(" does not exist.").toString(), Message.MessageType.WARNING));
      return;
    }

    if (args.length > 1) {
      editKitFlags(player, this.plugin.getKitManager().getKit(args[0]), Utils.trim(args));
      return;
    }

    Inventory inventory = Bukkit.createInventory(player, 45, new StringBuilder().append("Edit kit: ").append(args[0]).toString());

    inventory.setContents(this.plugin.getKitManager().getKit(args[0]).getItems());

    player.openInventory(inventory);
  }

  public void editKitFlags(Player player, Kit kit, String[] args) {
    if (args[0].equalsIgnoreCase("overwrite")) {
      editKitOverwrite(player, kit, Utils.trim(args));
      return;
    }

    if (args[0].equalsIgnoreCase("announce")) {
      editKitAnnounce(player, kit, Utils.trim(args));
      return;
    }

    if (args[0].equalsIgnoreCase("delay")) {
      editKitDelay(player, kit, Utils.trim(args));
      return;
    }
  }

  public void editKitOverwrite(Player player, Kit kit, String[] args) {
    if (args.length < 1) {
      Message.showMessage(player, Message.show("Usage", new StringBuilder().append("/kit edit ").append(ChatColor.ITALIC).append(kit.getName()).append(" overwrite [true|false]").toString(), Message.MessageType.INFO), new String[] { new StringBuilder().append("Change the overwrite flag of ").append(kit.getName()).append(" to true or false.").toString(), new StringBuilder().append("Current overwrite value: ").append(kit.getOverwrite()).append(".").toString() });
      return;
    }

    boolean value = args[0].equalsIgnoreCase("true");
    kit.setOverwrite(value);
    player.sendMessage(Message.show(new StringBuilder().append("Overwrite for kit ").append(kit.getName()).append(" set to ").append(value).toString(), Message.MessageType.INFO));
  }

  public void editKitAnnounce(Player player, Kit kit, String[] args) {
    if (args.length < 1) {
      Message.showMessage(player, Message.show("Usage", new StringBuilder().append("/kit edit ").append(ChatColor.ITALIC).append(kit.getName()).append(" announce [true|false]").toString(), Message.MessageType.INFO), new String[] { new StringBuilder().append("Change the announce flag of ").append(kit.getName()).append(" to true or false.").toString(), new StringBuilder().append("Current announce value: ").append(kit.getAnnounce()).append(".").toString() });
      return;
    }

    boolean value = args[0].equalsIgnoreCase("true");
    kit.setAnnounce(value);
    player.sendMessage(Message.show(new StringBuilder().append("Announce for kit ").append(kit.getName()).append(" set to ").append(value).toString(), Message.MessageType.INFO));
  }

  public void editKitDelay(Player player, Kit kit, String[] args) {
    if (args.length < 1) {
      Message.showMessage(player, Message.show("Usage", new StringBuilder().append("/kit edit ").append(ChatColor.ITALIC).append(kit.getName()).append(" delay [delay]").toString(), Message.MessageType.INFO), new String[] { new StringBuilder().append("Change the delay flag of ").append(kit.getName()).append(".").toString(), "Example: 1h30m for 1 hour 30 minute delay." });
      return;
    }
    try
    {
      Time value = new Time(args[0]);
      kit.setDelay(value.getMilliseconds());
      player.sendMessage(Message.show(new StringBuilder().append("Delay for kit ").append(kit.getName()).append(" set to ").append(args[0]).toString(), Message.MessageType.INFO));
    } catch (Exception ex) {
      player.sendMessage(Message.show("Incorrect delay format. Example: 1h30m for 1 hour 30 minute delay.", Message.MessageType.WARNING));
    }
  }

  private void removeKit(CommandSender sender, String[] args)
  {
    if (!IsPlayer(sender)) return;

    Player player = (Player)sender;

    if (!Permissions.checkPermission(player, "kits.admin")) return;

    if (args.length < 1) {
      Message.showMessage(player, Message.show("Usage", new StringBuilder().append("/kit remove ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.INFO), new String[] { "Remove an existing kit with the specific name." });
      return;
    }
    try
    {
      this.plugin.getKitManager().removeKit(args[0]);
      player.sendMessage(Message.show(new StringBuilder().append("Kit ").append(args[0]).append(" removed.").toString(), Message.MessageType.INFO));
    } catch (KitException e) {
      player.sendMessage(Message.show(new StringBuilder().append("Kit ").append(args[0]).append(" does not exist.").toString(), Message.MessageType.WARNING));
    }
  }

  public void spawnKit(CommandSender sender, String[] args)
  {
    if ((sender instanceof Player)) {
      spawnKit((Player)sender, args);
      return;
    }

    if (args.length < 2) {
      sender.sendMessage(Message.show("Usage", new StringBuilder().append("kit ").append(ChatColor.ITALIC).append("kitname playername").toString(), Message.MessageType.WARNING));
      return;
    }

    if (spawnKit(sender, args[0], args[1], StringUtils.join(Utils.trim(Utils.trim(args)), " ")))
      sender.sendMessage(Message.show(new StringBuilder().append("Kit ").append(args[0]).append(" spawned for ").append(args[1]).append(".").toString(), Message.MessageType.INFO));
  }

  private void spawnKit(Player player, String[] args) {
    if (args.length == 0) {
      Message.showMessage(player, Message.show("Usage", new StringBuilder().append("/kit ").append(ChatColor.ITALIC).append("kitname").toString(), Message.MessageType.WARNING), new String[] { "Spawn the specified kit." });
      Message.showMessage(player, Message.show("Usage", new StringBuilder().append("/kit ").append(ChatColor.ITALIC).append("kitname playername").toString(), Message.MessageType.WARNING), new String[] { "Spawn the specified kit for the specified player." });
      return;
    }

    if (!Permissions.checkPermission(player, new StringBuilder().append("kits.spawn.").append(args[0].toLowerCase()).toString())) return;

    if ((args.length > 1) && (!args[1].startsWith("+")) && (!args[1].startsWith("-"))) {
      if (!Permissions.checkPermission(player, new StringBuilder().append("kits.spawn.others.").append(args[0].toLowerCase()).toString())) return;

      if (spawnKit(player, args[0], args[1], StringUtils.join(Utils.trim(Utils.trim(args)), " "))) {
        player.sendMessage(Message.show(new StringBuilder().append("Kit ").append(args[0]).append(" spawned for ").append(args[1]).append(".").toString(), Message.MessageType.INFO));
      }
      return;
    }

    spawnKit(player, args[0], player.getName(), StringUtils.join(Utils.trim(args), " "));
  }

  private boolean spawnKit(CommandSender sender, String kitName, String playerName, String flags) {
    Player player = GetPlayer(playerName);

    if (player == null) {
      sender.sendMessage(Message.show(new StringBuilder().append(playerName).append(" is not online. Make sure the name is typed correctly.").toString(), Message.MessageType.WARNING));
      return false;
    }

    if (!this.plugin.getKitManager().kitExists(kitName)) {
      sender.sendMessage(Message.show(new StringBuilder().append("Kit ").append(kitName).append(" does not exist. Make sure the name is typed correctly.").toString(), Message.MessageType.WARNING));
      return false;
    }

    return spawnKit(sender, player, this.plugin.getKitManager().getKit(kitName), flags);
  }

  private boolean spawnKit(CommandSender sender, Player player, Kit kit, String flags) {
    List flagList = StringUtils.isEmpty(flags) ? new ArrayList() : Arrays.asList(flags.split(" "));
    HashMap Flags = new HashMap();

    for (String flag : flagList) {
      if ((!flag.isEmpty()) && (flag.length() >= 2)) {
        if ((sender instanceof Player)) { if (!Permissions.hasPermission((Player)sender, "kits.flags", new String[] { flag.replace("+", "").replace("-", "") }))
            sender.sendMessage(Message.show(new StringBuilder().append("You do not have permission to use the ").append(flag.replace("+", "").replace("-", "")).append(" flag.").toString(), Message.MessageType.WARNING));
        }
        else
          Flags.put(flag.replace("+", "").replace("-", ""), Boolean.valueOf(!flag.startsWith("-")));
      }
    }
    return spawnKit(sender, player, kit, Flags);
  }

  private boolean spawnKit(CommandSender sender, Player player, Kit kit, HashMap<String, Boolean> flags) {
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

    return SpawnKit(sender, player, kit, delay, overwrite, announce);
  }

  private boolean SpawnKit(CommandSender sender, Player player, Kit kit, long delay, boolean overwrite, boolean announce)
  {
    if ((this.plugin.getCollectionManager().getDelayedPlayer(player).playerDelayed(kit)) && (kit.getDelay() == delay) && (delay > 0L)) {
      if (announce) {
        String message = new StringBuilder().append(((sender instanceof Player)) && (((Player)sender).getName().equalsIgnoreCase(player.getName())) ? "You are " : new StringBuilder().append(player.getName()).append(" is ").toString()).append("currently delayed for kit ").append(kit.getName()).append(".").append(sender.hasPermission("kits.flags.delay") ? " Use the -delay flag to override this." : "").toString();
        sender.sendMessage(Message.show(message, Message.MessageType.WARNING));
      }
      return false;
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
    if (announce) player.sendMessage(Message.show(new StringBuilder().append("Kit ").append(kit.getName()).append(" spawned.").toString(), Message.MessageType.INFO));

    return true;
  }

  private boolean IsPlayer(CommandSender sender)
  {
    if (!(sender instanceof Player)) {
      sender.sendMessage(Message.show("Command must be issued ingame.", Message.MessageType.WARNING));
      return false;
    }

    return true;
  }

  private Player GetPlayer(String name)
  {
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (player.getName().equalsIgnoreCase(name)) return player;
    }
    return null;
  }
}