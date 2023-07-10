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
            hitByShulkerBulletMap.put(player.getUniqueId(), true);
        }
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {

        // Ignore non-players
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (player.hasPotionEffect(PotionEffectType.LEVITATION)) {
            System.out.println("CONDITIONS MET");
            boolean hitByShulkerBullet = hitByShulkerBulletMap.getOrDefault(player.getUniqueId(), false);
            if (hitByShulkerBullet) {
                System.out.println(player.getName() + " - SHULKER BULLET");
                player.playSound(player.getLocation(), "custom.believe", 5.0f, 1.0f);
                hitByShulkerBulletMap.remove(player.getUniqueId());
            }
        }
    }
}
