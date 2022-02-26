package de.cuuky.varo.event;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Varo;
import org.bukkit.Material;

public enum VaroEventType {

    EXPOSED(ExposedVaroEvent::new, "Exposed", "§c", Materials.REDSTONE,
        "Lässt die Spieler auffliegen!\n\n1.9+: Gibt allen 'GLOWING'-Effekt\n<1.9: Spawnt alle 10 Sekunden eine Rakete"),
    MASS_RECORDING(MassRecordingVaroEvent::new, "MassRecording", "§c", Materials.DIAMOND_SWORD,
        "Lässt alle Spieler für eine eingestellte Anzahl an Minuten zusätzlich zu den normalen Folgen auf den Server"),
    MOON_GRAVITY(MoonGravityVaroEvent::new, "MoonGravity", "§2", Materials.STONE, "Mond-Gravitation\nVorsicht: Ab 1.13 möglich."),
    POISON_RAIN(PoisonRainVaroEvent::new, "Poisoned Rain", "§4", Materials.ARROW, "Regen macht Schaden"),
    POISON_WATER(PoisonWaterVaroEvent::new, "Poisoned Water", "§b", Materials.WATER_BUCKET, "Bei Kontakt mit Wasser erhält man Schaden");

    private final EventBuilder builder;
    private final String name;
    private final String colorCode;
    private final String description;
    private final Material material;

    VaroEventType(EventBuilder builder, String name, String colorCode, Materials material, String description) {
        this.builder = builder;
        this.name = name;
        this.colorCode = colorCode;
        this.description = description;
        this.material = material.parseMaterial();
    }

    public IVaroEvent build(Varo varo) {
        return this.builder.apply(varo);
    }

    public String getDisplayName() {
        return this.colorCode + this.name;
    }

    public String getName() {
        return this.name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public String getDescription() {
        return description;
    }

    public Material getMaterial() {
        return material;
    }
}