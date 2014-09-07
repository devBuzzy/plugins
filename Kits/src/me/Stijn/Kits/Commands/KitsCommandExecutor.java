package me.Stijn.Kits.Commands;

import me.Stijn.Kits.Kits;
import me.Stijn.Kits.api.Kit;
import me.Stijn.Kits.configuration.CollectionManager;
import me.Stijn.Kits.permissions.Permissions;
import me.Stijn.Kits.Util.CommandDescription;
import me.Stijn.Kits.Util.Message;
import me.Stijn.Kits.Util.Message.MessageType;
import me.Stijn.Kits.Util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitsCommandExecutor
  implements CommandExecutor
{
  private Kits plugin;

  public KitsCommandExecutor(Kits instance)
  {
    this.plugin = instance;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args)
  {
    if (args.length < 1) {
      handleBaseCommand(sender);
      return false;
    }

    if (args[0].equalsIgnoreCase("reload")) handleReload(sender);

    return false;
  }

  private void handleBaseCommand(CommandSender sender) {
    if (((sender instanceof Player)) && (!Permissions.checkPermission((Player)sender, "kits.list"))) return;

    if (this.plugin.getCollectionManager().getKitList().size() < 1) {
      sender.sendMessage(Message.show("There are no available kits.", Message.MessageType.WARNING));
      return;
    }

    List commands = new ArrayList();

    for (Kit kit : this.plugin.getCollectionManager().getKitList()) {
      List lines = new ArrayList();
      List items = new ArrayList();
      for (ItemStack item : kit.getItems()) {
        if (item != null)
          items.add(new StringBuilder().append(item.getAmount()).append(" x ").append(item.hasItemMeta() ? item.getItemMeta().getDisplayName() : Utils.capitalize(item.getType().name())).toString());
      }
      lines.add(new StringBuilder().append("Number of items: ").append(items.size()).toString());
      lines.add(new StringBuilder().append("Delay: ").append(kit.getDelay()).append("ms").toString());
      lines.add(new StringBuilder().append("Overwrite: ").append(kit.getOverwrite()).toString());
      lines.add(new StringBuilder().append("Announce: ").append(kit.getAnnounce()).toString());
      commands.add(new CommandDescription(new StringBuilder().append(ChatColor.DARK_AQUA).append(kit.getName()).toString(), new StringBuilder().append("/kit ").append(kit.getName()).toString(), (String[])lines.toArray(new String[lines.size()])));
    }

    if (!(sender instanceof Player)) {
      String message = "Available kits: ";
      for (CommandDescription command : commands) {
        message = new StringBuilder().append(message).append(ChatColor.GRAY).append(", ").append(command.getTitle()).toString();
      }
      sender.sendMessage(message.replaceFirst(Pattern.quote(", "), ""));
      return;
    }

    Message.showCommand((Player)sender, "Available kits: ", (CommandDescription[])commands.toArray(new CommandDescription[commands.size()]));
  }

  private void handleReload(CommandSender sender)
  {
    if ((sender instanceof Player)) {
      if (Permissions.checkPermission((Player)sender, "kits.admin")) {
        this.plugin.reload();
        sender.sendMessage(Message.show("Reloaded configurations.", Message.MessageType.INFO));
      }
    }
    else {
      this.plugin.reload();
      sender.sendMessage(Message.show("Reloaded configurations.", Message.MessageType.INFO));
    }
  }
}