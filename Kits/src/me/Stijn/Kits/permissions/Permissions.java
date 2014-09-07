package me.Stijn.Kits.permissions;

import me.Stijn.Kits.Util.Message;
import me.Stijn.Kits.Util.Message.MessageType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class Permissions
{
  public static final String KITS_BASE = "kits";
  public static final String KITS_LIST = "kits.list";
  public static final String KITS_SPAWN = "kits.spawn";
  public static final String KITS_SPAWN_OTHERS = "kits.spawn.others";
  public static final String KITS_SIGN = "kits.sign";
  public static final String KITS_FLAGS = "kits.flags";
  public static final String KITS_NODELAY = "kits.nodelay";
  public static final String KITS_ADMIN = "kits.admin";

  public static boolean checkPermission(Player player, String permission)
  {
    if (!player.hasPermission(permission)) {
      Message.showMessage(player, Message.show("Warning", "You do not have permission to perform that action.", Message.MessageType.WARNING), new String[] { permission });

      return false;
    }
    return true;
  }

  public static boolean checkPermission(Player player, String permission, String[] subPerms) {
    return checkPermission(player, permission + "." + StringUtils.join(subPerms, "."));
  }

  public static boolean hasPermission(Player player, String permission, String[] subPerms) {
    return player.hasPermission(permission + "." + StringUtils.join(subPerms, "."));
  }
}