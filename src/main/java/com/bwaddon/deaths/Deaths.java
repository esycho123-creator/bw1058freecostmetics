package com.esycho.bwaddon.deaths; // Matches your exact package requirement!

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Deaths implements Listener {

    private final JavaPlugin plugin;

    public Deaths(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerKillEvent event) {
        Player victim = event.getVictim();

        // Safety check to ensure there is a valid victim
        if (victim == null) return;

        // Get the location where the player died
        Location deathLocation = victim.getLocation();

        // Read which Death cosmetic the VICTIM currently has selected
        String path = "players." + victim.getUniqueId().toString() + ".active-death-cosmetic";
        String activeCosmetic = plugin.getConfig().getString(path, "none").toLowerCase();

        switch (activeCosmetic) {
            case "pig":
                spawnRisingEntity(deathLocation, Pig.class);
                break;

            case "squid":
                spawnRisingEntity(deathLocation, Squid.class);
                break;

            case "firework":
                spawnFireworkParticles(deathLocation);
                break;

            default:
                // No death cosmetic equipped
                break;
        }
    }

    /**
     * Spawns an alive entity (Pig or Squid) that floats upwards and disappears after 5 seconds.
     */
    private <T extends Entity> void spawnRisingEntity(Location loc, Class<T> clazz) {
        final T entity = loc.getWorld().spawn(loc.clone().add(0, 0.5, 0), clazz);
        entity.setVelocity(new org.bukkit.util.Vector(0, 0.1, 0));

        new BukkitRunnable() {
            int ticksRun = 0;

            @Override
            public void run() {
                if (entity.isDead() || ticksRun >= 100) {
                    entity.remove();
                    this.cancel();
                    return;
                }

                entity.setVelocity(new org.bukkit.util.Vector(0, 0.15, 0));
                ticksRun += 2;
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }

    /**
     * Spawns firework explosion particles where the player died.
     */
    private void spawnFireworkParticles(Location loc) {
        for (int i = 0; i < 20; i++) {
            double offsetX = (Math.random() - 0.5) * 0.6;
            double offsetY = Math.random() * 1.8;
            double offsetZ = (Math.random() - 0.5) * 0.6;
            
            loc.getWorld().playEffect(loc.clone().add(offsetX, offsetY, offsetZ), Effect.FIREWORKS_SPARK, 0);
        }
    }
}