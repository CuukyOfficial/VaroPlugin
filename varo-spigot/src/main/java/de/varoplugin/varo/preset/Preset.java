/*
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.varoplugin.varo.preset;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public interface Preset {

    Set<Preset> BUILT_IN_PRESETS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            new OneDayVaroPreset(),
            new SuroPreset(),
            new TitanPreset())));

    Path CONFIG_PATH = Paths.get("plugins/Varo/config/");
    Path PRESET_PATH = Paths.get("plugins/Varo/presets/");

    /**
     * Loads this preset. This will likely reset most configs.
     *
     * @throws IOException If an I/O exception occurred
     */
    void load() throws IOException;

    /**
     * Returns the name of this preset.
     *
     * @return The name of this preset
     */
    @NotNull String getName();

    /**
     * Saves a new preset.
     *
     * @param name The name of the new preset
     * @return The new preset, or null if the name is invalid
     * @throws IOException If an I/O exception occurred
     */
    static @Nullable Preset save(@NotNull String name) throws IOException {
        Objects.requireNonNull(name, "Preset name must not be null");
        if (!name.matches("[0-9a-zA-ZäÄöÖüÜ]+"))
            return null;
        Optional<Preset> duplicate = BUILT_IN_PRESETS.stream().filter(p -> p.getName().equals(name)).findFirst();
        if (duplicate.isPresent())
            return null;
        return FilePreset.create(name);
    }

    /**
     * Returns a {@link Collection} of all available presets.
     * 
     * @return A {@link Collection} of all available presets
     */
    static @NotNull @Unmodifiable Collection<@NotNull Preset> listPresets() {
        Set<Preset> presets = new HashSet<>(BUILT_IN_PRESETS);

        File dir = PRESET_PATH.toFile();
        File[] files = dir.listFiles();
        if (files != null)
            for (File f : files)
                if (f.isDirectory())
                    presets.add(new FilePreset(f.getName()));

        return Collections.unmodifiableCollection(presets);
    }
}
