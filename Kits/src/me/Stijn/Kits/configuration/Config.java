package me.Stijn.Kits.configuration;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config extends YamlConfiguration
{
  private JavaPlugin plugin;
  private String fileName;

  public Config(JavaPlugin plugin, String fileName)
  {
    this.plugin = plugin;
    this.fileName = new StringBuilder().append(fileName).append(fileName.endsWith(".yml") ? "" : ".yml").toString();

    createFiles();
  }

  public void createFiles() {
    try {
      File file = new File(this.plugin.getDataFolder(), this.fileName);
      if (!file.exists()) {
        if (this.plugin.getResource(this.fileName) != null)
          this.plugin.saveResource(this.fileName, false);
        else
          save(file);
      }
      else {
        load(file);
        try {
          save(file); } catch (Exception ex) {
        }
      }
    } catch (Exception ex) {
    }
  }

  public void save() {
    try { save(new File(this.plugin.getDataFolder(), this.fileName)); }
    catch (Exception ex)
    {
    }
  }
}