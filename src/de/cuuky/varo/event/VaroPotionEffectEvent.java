package de.cuuky.varo.event;

import de.cuuky.cfw.configuration.serialization.Serialize;
import de.cuuky.cfw.version.VersionUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.function.Consumer;

public abstract class VaroPotionEffectEvent extends VaroScheduledEvent {

    protected static final int EFFECT_LENGTH = 60;

    @Serialize("effectType")
    final PotionEffectType potionEffectType;

    @Serialize("amplifier")
    final int amplifier;

    public VaroPotionEffectEvent(EventInformationHolder informationHolder,
                                 PotionEffectType potionEffectType, int amplifier) {
        super(informationHolder, EFFECT_LENGTH);

        this.potionEffectType = potionEffectType;
        this.amplifier = amplifier;
    }

    private void executeEffect(Consumer<Player> executor) {
        VersionUtils.getVersionAdapter().getOnlinePlayers().forEach(executor);
    }

    private boolean hasBetterActivated(Player player) {
        return player.getActivePotionEffects().stream().anyMatch(p ->
            p.getType() == this.potionEffectType && p.getDuration() > EFFECT_LENGTH);
    }

    void removePotionEffect(Player player) {
        if (!this.hasBetterActivated(player))
            player.removePotionEffect(this.potionEffectType);
    }

    void addPotionEffect(Player player) {
        player.addPotionEffect(new PotionEffect(this.potionEffectType, EFFECT_LENGTH, this.amplifier));
    }

    @Override
    void onDisable() {
        this.executeEffect(this::removePotionEffect);
    }

    @Override
    void onSchedule() {
        this.executeEffect(this::addPotionEffect);
    }

    @Override
    protected void registerPolicies() {
        this.registerPolicy(PotionEffectType.class, this.potionEffectType::getName, PotionEffectType::getByName);
        super.registerPolicies();
    }
}