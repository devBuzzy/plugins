package me.Stijn.Kits.Util;

import me.Stijn.Kits.Kits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mkremins.fanciful.FancyMessage;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Message
{
  private static Kits plugin;

  public static void setParent(Kits instance)
  {
    plugin = instance;
  }

  public static String show(String message, MessageType type) {
    return show(plugin.getName(), message, type);
  }

  public static String show(String prefix, String message, MessageType type) {
    return new StringBuilder().append(ChatColor.DARK_GRAY).append(prefix).append(": ").append(type == MessageType.INFO ? ChatColor.DARK_AQUA : type == MessageType.MESSAGE ? ChatColor.GRAY : ChatColor.RED).append(message).toString();
  }

  public static void showMessage(Player player, String title, String[] args)
  {
    if (args.length < 1) {
      player.sendMessage(title);
      return;
    }

    FancyMessage message = new FancyMessage("").then(title).itemTooltip(getMessage(args));

    sendJSONMessage(player, message);
  }

  public static void showCommand(Player player, CommandDescription command) {
    if (command.getArgs().length < 1) {
      player.sendMessage(command.getTitle());
      return;
    }

    FancyMessage message = new FancyMessage("").then(command.getTitle()).itemTooltip(getMessage(command.getArgs())).command(command.getCommand());

    sendJSONMessage(player, message);
  }

  public static void showCommand(Player player, String prefix, CommandDescription[] commands) {
    FancyMessage message = new FancyMessage(prefix).color(ChatColor.DARK_GRAY);

	List commandList = new ArrayList(Arrays.asList(commands));

    for (CommandDescription command : commands) {
      if (command.getArgs().length < 1) {
        player.sendMessage(command.getTitle());
        return;
      }

      message = message.then(command.getTitle()).itemTooltip(getMessage(command.getArgs())).command(command.getCommand());

      if (commandList.get(commandList.size() - 1) != command) {
        message = message.then(", ").color(ChatColor.GRAY);
      }
    }

    sendJSONMessage(player, message);
  }

  public static ItemStack getMessage(String[] args) {
    ItemStack item = new ItemStack(Material.STONE);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(new StringBuilder().append(ChatColor.RESET).append(args[0]).toString());

    List lore = new ArrayList(Arrays.asList(Utils.trim(args)));
    for (String line : Utils.trim(args)) {
      lore.set(lore.indexOf(line), new StringBuilder().append(ChatColor.RESET).append(line).toString());
    }
    meta.setLore(lore);
    item.setItemMeta(meta);

    return item;
  }

  public static void sendJSONMessage(Player player, FancyMessage message) {
    message.send(player);
  }

  public static enum MessageType
  {
    MESSAGE, 
    INFO, 
    WARNING;
  }
}