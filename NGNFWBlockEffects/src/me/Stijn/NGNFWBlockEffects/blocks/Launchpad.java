package me.Stijn.NGNFWBlockEffects.blocks;

import java.util.ArrayList;
import java.util.List;

import me.Stijn.NGNFWBlockEffects.Main;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Launchpad implements Listener {

	Main plugin;

	public void Setup(Main p) {
		this.plugin = p;
	}

	private ArrayList<Player> jumpers = new ArrayList<Player>();
	public static Effect effectz = Effect.valueOf("STEP_SOUND");

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		// *LaunchPad
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.getMaterial(plugin.getConfig().getString("Launchpad.BlockId"))) {
			e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(10));
			e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getBlockX(), 1.0D, e.getPlayer().getVelocity().getZ()));
			Player player = e.getPlayer();
			Location loc = player.getLocation();
			loc.getWorld().playEffect(loc, Effect.SMOKE, 69);
			loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 69);
			jumpers.add(e.getPlayer());
		}
		// *Trampoline
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.getMaterial(plugin.getConfig().getString("Trampoline.BlockId"))) {
			e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(0));
			e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getBlockX(), 2.0D, e.getPlayer().getVelocity().getZ()));
			Player player = e.getPlayer();
			Location loc = player.getLocation();
			loc.getWorld().playEffect(loc, Effect.EXPLOSION_LARGE, 69);
			loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 69);
			loc.getWorld().playSound(loc, Sound.ENDERMAN_TELEPORT, 69, 1);
			jumpers.add(e.getPlayer());
		}
		// *Run
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.getMaterial(plugin.getConfig().getString("Run.BlockId"))) {

			Player player = e.getPlayer();
			Location loc = player.getLocation();

			Location locBlock = new Location(player.getWorld(), e.getTo().getX(), e.getTo().getY() - 1, e.getTo().getZ());

			if (getMetadata(locBlock.getBlock(), "IceEffect", plugin) == "h") {
				loc.getWorld().playEffect(loc, Effect.COLOURED_DUST, 69);
				loc.getWorld().playEffect(loc, Effect.SPLASH, 69);
				loc.getWorld().playEffect(loc, Effect.FLYING_GLYPH, 69);
				loc.getWorld().playEffect(loc, Effect.CRIT, 69);
				PotionEffect speed = PotionEffectType.SPEED.createEffect(200, 7); // (sec [100 = 5sec], speed)
				player.addPotionEffect(speed, true);
			}
		}
	}

	public void setMetadata(Metadatable object, String key, Object value, Plugin plugin) {
		object.setMetadata(key, new FixedMetadataValue(plugin, value));
	}

	public Object getMetadata(Metadatable object, String key, Plugin plugin) {
		List<MetadataValue> values = object.getMetadata(key);
		for (MetadataValue value : values) {
			if (value.getOwningPlugin() == plugin) {
				return value.value();
			}
		}
		return null;
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FALL && jumpers.contains(p)) {
				e.setDamage(0.0);
				jumpers.remove(p);
			}
		}
	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent e) {
		Block b = e.getBlock();
		ItemStack i = e.getItemInHand();

		if (i.getItemMeta().getDisplayName() == "IceEffect") {
			setMetadata(b, "IceEffect", "h", plugin);

		}
	}
}
