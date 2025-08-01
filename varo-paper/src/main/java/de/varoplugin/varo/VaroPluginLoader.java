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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

        MavenLibraryResolver resolver = new MavenLibraryResolver();
        addDependencies(resolver, Dependencies.REQUIRED_DEPENDENCIES);
        addDependencies(resolver, Dependencies.OPTIONAL_DEPENDENCIES);
        
        resolver.addRepository(new RemoteRepository.Builder("central", "default", Dependencies.MAVEN_CENTRAL).build());
        classpathBuilder.addLibrary(resolver);
    }
    
    private void addDependencies(MavenLibraryResolver resolver, Collection<VaroDependency> dependencies) {
        for (var dependency : dependencies) {
            for (var coords : dependency.getMavenCoordinates())
                resolver.addDependency(new Dependency(new DefaultArtifact(coords), null, false, EXCLUDE_TRANSITIVE));
        }
    }
}
