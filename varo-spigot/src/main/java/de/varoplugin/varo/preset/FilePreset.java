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

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.VaroConfig;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

class FilePreset extends AbstractPreset {

    FilePreset(@NotNull String name) {
        super(name);
    }

    @Override
    public void load() throws IOException {
        Path path = PRESET_PATH.resolve(this.getName());
        if (!path.normalize().startsWith(PRESET_PATH) || path.normalize().equals(PRESET_PATH))
            throw new RuntimeException();
        copy(path, CONFIG_PATH);
        Main.getDataManager().reloadConfig();
        VaroConfig.reload();
    }

    static @NotNull Preset create(@NotNull String name) throws IOException {
        Path path = PRESET_PATH.resolve(name);
        if (!path.normalize().startsWith(PRESET_PATH) || path.normalize().equals(PRESET_PATH))
            throw new RuntimeException();
        copy(CONFIG_PATH, path);
        return new FilePreset(name);
    }

    private static void copy(@NotNull Path from, @NotNull Path to) throws IOException {
        Files.createDirectories(to);

        try (Stream<Path> stream = Files.walk(from)) {
            for (Path config : (Iterable<Path>) stream::iterator) {
                if (!Files.isRegularFile(config))
                    continue;

                Files.copy(config, to.resolve(config.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
