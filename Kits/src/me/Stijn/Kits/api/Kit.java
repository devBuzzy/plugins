package me.Stijn.Kits.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class Kit
  implements ConfigurationSerializable
{
  private String name;
  private long delay;
  private boolean overwrite;
  private boolean announce;
  private ItemStack[] items;

  public Kit(String name, ItemStack[] items, long delay, boolean overwrite, boolean announce)
  {
    this.name = name;
    this.delay = delay;
    this.overwrite = overwrite;
    this.announce = announce;
    this.items = items;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getDelay() {
    return this.delay;
  }

  public void setDelay(long delay) {
    this.delay = delay;
  }

  public boolean getOverwrite() {
    return this.overwrite;
  }

  public void setOverwrite(boolean overwrite) {
    this.overwrite = overwrite;
  }

  public boolean getAnnounce() {
    return this.announce;
  }

  public void setAnnounce(boolean announce) {
    this.announce = announce;
  }

  public ItemStack[] getItems() {
    return this.items;
  }

  public void setItems(ItemStack[] items) {
    this.items = items;
  }

  public Map<String, Object> serialize()
  {
    Map result = new HashMap();

    result.put("name", this.name);
    result.put("delay", Long.valueOf(this.delay));
    result.put("overwrite", Boolean.valueOf(this.overwrite));
    result.put("announce", Boolean.valueOf(this.announce));
    result.put("items", this.items);

    return result;
  }

  public static Kit deserialize(Map<String, Object> args)
  {
    return new Kit((String)args.get("name"), (ItemStack[])((ArrayList)args.get("items")).toArray(new ItemStack[((ArrayList)args.get("items")).size()]), ((Integer)args.get("delay")).intValue(), ((Boolean)args.get("overwrite")).booleanValue(), ((Boolean)args.get("announce")).booleanValue());
  }
}