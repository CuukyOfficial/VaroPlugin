package de.cuuky.varo.event;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Varo;
import org.bukkit.potion.PotionEffectType;

public class MoonGravityVaroEvent extends VaroPotionEffectEvent {

    private static final PotionEffectType TYPE = PotionEffectType.getByName("SLOW_FALLING");

    public MoonGravityVaroEvent(Varo varo) {
        super("MoonGravity", "§2", Materials.STONE,
            "Mond-Gravitation\nVorsicht: Ab 1.13 möglich.", TYPE, 1);
    }

    @Override
    public void onEnable() {
        if (this.potionEffectType == null)
            throw new IllegalStateException("Could not find potion effect type!");

        super.onEnable();
    }
}