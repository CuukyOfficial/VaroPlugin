package de.cuuky.varo.event;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Varo;
import de.cuuky.varo.VaroElement;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public abstract class VaroEvent extends VaroElement {

    // TODO: Make EventBuilder load config stuff and load all these values from config
    public static final List<EventBuilder> DEFAULT_EVENTS = Arrays.asList(
        new EventBuilder(ExposedVaroEvent::new).name("exposed").displayName("§cExposed")
            .icon(Materials.REDSTONE.parseMaterial())
            .description("Lässt die Spieler auffliegen!\n\n1.9+: Gibt allen 'GLOWING'-Effekt\n<1.9: Spawnt alle 10 Sekunden eine Rakete"),
        new EventBuilder(MoonGravityVaroEvent::new).name("moon_gravity").displayName("§2Moon Gravity")
            .icon(Materials.STONE.parseMaterial()).description("Mond-Gravitation\nVorsicht: Ab 1.13 möglich."),
        new EventBuilder(PoisonRainVaroEvent::new).name("poisonous_rain").displayName("§4Poisonous Rain")
            .icon(Materials.ARROW.parseMaterial()).description("Regen macht Schaden"),
        new EventBuilder(PoisonWaterVaroEvent::new).name("poisonous_water").displayName("§bPoisonous Water")
            .icon(Materials.WATER_BUCKET.parseMaterial()).description("Bei Kontakt mit Wasser erhält man Schaden")
    );

    private final EventInformationHolder info;
    private final Collection<BukkitTask> tasks;

    public VaroEvent(EventInformationHolder informationHolder) {
        this.info = informationHolder;
        this.tasks = new LinkedHashSet<>();
    }

    abstract void onEnable();

    abstract void onDisable();

    @Override
    protected void onInitialize(Varo varo) {
        this.onEnable();
    }

    protected void registerTask(BukkitTask task) {
        this.tasks.add(task);
    }

    public void disable() {
        this.tasks.forEach(BukkitTask::cancel);
        this.onDisable();
        this.varo.removeElement(this);
    }

    @Override
    public String toString() {
        return this.info.getName();
    }

    public String getName() {
        return this.info.getName();
    }

    public String getDisplayName() {
        return this.info.getDisplayName();
    }

    public Material getIcon() {
        return this.info.getIcon();
    }

    public String getDescription() {
        return this.info.getDescription();
    }
}