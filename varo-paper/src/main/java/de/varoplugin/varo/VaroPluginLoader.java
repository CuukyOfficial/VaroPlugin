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

package de.varoplugin.varo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import io.papermc.paper.plugin.loader.library.impl.JarLibrary;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.Exclusion;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import de.varoplugin.varo.data.Dependencies;
import de.varoplugin.varo.data.Dependencies.VaroDependency;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;

public class VaroPluginLoader implements PluginLoader {
    
    private static final List<Exclusion> EXCLUDE_TRANSITIVE = Arrays.asList(new Exclusion("*", "*", null, null));

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        classpathBuilder.getContext().getLogger().info("Loading Varo dependencies...");

        try {
            for (VaroDependency lib : Dependencies.DEPENDENCIES)
                if (lib.shouldLoad()) {
                    classpathBuilder.getContext().getLogger().info("Loading dependency {}", lib.getName());
                    lib.load();
                    for (File file : lib.getFiles())
                        classpathBuilder.addLibrary(new JarLibrary(file.toPath()));
                }
        } catch (Throwable t) {
            classpathBuilder.getContext().getLogger().error("Unable to load dependencies", t);
        }
    }
}
