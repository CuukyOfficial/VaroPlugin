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

import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import de.varoplugin.varo.data.Dependencies;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;

public class VaroPluginLoader implements PluginLoader {

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        classpathBuilder.getContext().getLogger().info("Loading Varo dependencies...");

        // TODO
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        resolver.addDependency(new Dependency(new DefaultArtifact("io.github.almighty-satan.slams:slams-standalone:1.2.1"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.github.almighty-satan.slams:slams-parser-jaskl:1.2.1"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.github.almighty-satan.slams:slams-papi:1.2.1"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.github.almighty-satan.jaskl:jaskl-yaml:1.6.2"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.github.cryptomorin:XSeries:13.2.0"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.apache.commons:commons-collections4:4.4"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.bstats:bstats-bukkit:3.1.0"), null));
        resolver.addRepository(new RemoteRepository.Builder("central", "default", Dependencies.MAVEN_CENTERAL).build());
        classpathBuilder.addLibrary(resolver);
    }

}
