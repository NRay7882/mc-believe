package nray7882.believe;

import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class LevitationListener implements Listener {

    private final Map<UUID, Boolean> hitByShulkerBulletMap = new HashMap<>();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof ShulkerBullet) {
            Player player = (Player) event.getEntity();

            // If player has levitation effect, play sound immediately
            if (player.hasPotionEffect(PotionEffectType.LEVITATION)) {
                player.playSound(player.getLocation(), "custom.believe", 5.0f, 1.0f);
            } else {
                hitByShulkerBulletMap.put(player.getUniqueId(), true);
            }
        }
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {

        // Ignore non-players
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        // Ignore events that aren't the addition of a new effect
        if (event.getAction() != EntityPotionEffectEvent.Action.ADDED) {
            return;
        }

        // Check if the added effect is Levitation
        if (event.getNewEffect().getType().equals(PotionEffectType.LEVITATION)) {
            Player player = (Player) event.getEntity();
            boolean hitByShulkerBullet = hitByShulkerBulletMap.getOrDefault(player.getUniqueId(), false);
            if (hitByShulkerBullet) {
                player.playSound(player.getLocation(), "custom.believe", 5.0f, 1.0f);
                hitByShulkerBulletMap.remove(player.getUniqueId());
            }
        }
    }
}
