package de.cuuky.varo.game.world.schematic;

import java.io.File;

import org.bukkit.Location;

public interface SchematicLoader {

    void paste(File file, Location location);
    
    static SchematicLoader getInstance() {
        try {
            return new WorldEdit7SchematicLoader();
        } catch (Throwable ignored) {
            // nop
        }
        try {
            return new WorldEditLegacySchematicLoader();
        } catch (Throwable ignored) {
            // nop
        }
        return null;
    }
}