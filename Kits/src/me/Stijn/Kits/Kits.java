package me.Stijn.Kits;

import me.Stijn.Kits.api.Kit;
import me.Stijn.Kits.api.KitManager;
import me.Stijn.Kits.Commands.KitCommandExecutor;
import me.Stijn.Kits.Commands.KitsCommandExecutor;
import me.Stijn.Kits.configuration.CollectionManager;
import me.Stijn.Kits.Listeners.EventListener;
import me.Stijn.Kits.Util.DelayedPlayer;
import me.Stijn.Kits.Util.Message;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Kits extends JavaPlugin
{
  private KitManager kitManager;
  private CollectionManager collectionManager;

  public void onDisable()
  {
    getCollectionManager().save();
  }

  public void onEnable()
  {
    Message.setParent(this);

    this.kitManager = new KitManager(this);
    this.collectionManager = new CollectionManager();

    reload();

    getServer().getPluginManager().registerEvents(new EventListener(this), this);

    getCommand("kits").setExecutor(new KitsCommandExecutor(this));
    getCommand("kit").setExecutor(new KitCommandExecutor(this));
  }

  public void reload() {
    getCollectionManager().reload(this);
  }

  public String getPluginDetails() {
    return getPluginName() + " " + getPluginVersion();
  }

  public String getPluginName() {
    return getDescription().getName();
  }

  public String getPluginVersion() {
    return getDescription().getVersion();
  }

  public KitManager getKitManager() {
    return this.kitManager;
  }

  public CollectionManager getCollectionManager() {
    return this.collectionManager;
  }

  static
  {
    ConfigurationSerialization.registerClass(Kit.class);
    ConfigurationSerialization.registerClass(DelayedPlayer.class);
  }
}