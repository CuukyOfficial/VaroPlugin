package de.cuuky.varo.event;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Varo;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffectType;

public class ExposedVaroEvent extends VaroPotionEffectEvent {

    private static final PotionEffectType TYPE = PotionEffectType.getByName("GLOWING");

    private static final FireworkEffect FIREWORK_EFFECT = FireworkEffect.builder()
        .withColor(Color.RED, Color.WHITE).withFade(Color.PURPLE)
        .with(FireworkEffect.Type.BURST).trail(false).flicker(true).build();

    public ExposedVaroEvent(Varo varo) {
        super("Exposed", "§c", Materials.REDSTONE,
            "Lässt die Spieler auffliegen!\n\n1.9+: Gibt allen 'GLOWING'-Effekt\n<1.9: Spawnt alle 10 Sekunden eine Rakete", TYPE, 1);
    }

    @Override
    void addPotionEffect(Player player) {
        if (this.potionEffectType != null) {
            Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta meta = fw.getFireworkMeta();
            meta.addEffect(FIREWORK_EFFECT);
            meta.setPower(1);
            fw.setFireworkMeta(meta);
        } else
            super.addPotionEffect(player);
    }
}