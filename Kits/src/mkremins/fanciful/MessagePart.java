package mkremins.fanciful;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;

final class MessagePart
{
  ChatColor color = ChatColor.WHITE;
  ChatColor[] styles = new ChatColor[0];
  String clickActionName = null; String clickActionData = null; String hoverActionName = null; String hoverActionData = null;

  String text = null;

  MessagePart(String text) {
    this.text = text;
  }
  MessagePart() {
  }

  boolean hasText() {
    return this.text != null;
  }

  JsonWriter writeJson(JsonWriter json) {
    try {
      json.beginObject().name("text").value(this.text);
      json.name("color").value(this.color.name().toLowerCase());
      for (ChatColor style : this.styles) {
        if (style == ChatColor.MAGIC)
          json.name("obfuscated").value(true);
        else {
          json.name(style.name().toLowerCase()).value(true);
        }
      }
      if ((this.clickActionName != null) && (this.clickActionData != null)) {
        json.name("clickEvent").beginObject().name("action").value(this.clickActionName).name("value").value(this.clickActionData).endObject();
      }

      if ((this.hoverActionName != null) && (this.hoverActionData != null)) {
        json.name("hoverEvent").beginObject().name("action").value(this.hoverActionName).name("value").value(this.hoverActionData).endObject();
      }

      return json.endObject();
    } catch (Exception e) {
      e.printStackTrace();
    }return json;
  }
}