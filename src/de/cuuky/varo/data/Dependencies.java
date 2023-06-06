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

package de.cuuky.varo.data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.cfw.dependencies.Dependency;
import de.varoplugin.cfw.dependencies.JarDependency;

public class Dependencies {
    
    protected static final String LIB_FOLDER = "plugins/Varo/libs";

    private static final String MAVEN_CENTERAL = "https://repo1.maven.org/maven2/";

    private static final URL DEPENDENCY_FILE = Dependencies.class.getClassLoader().getResource("dependencies.txt");

    private static final Collection<VaroDependency> OPTIONAL_DEPENDENCIES = new ArrayList<>();

    static {
        OPTIONAL_DEPENDENCIES.add(new VaroDependency("gson", MAVEN_CENTERAL, JarDependency::new, () -> !doesClassExist("com.google.gson.JsonElement")));
        OPTIONAL_DEPENDENCIES.add(new VaroDependency("guava", MAVEN_CENTERAL, JarDependency::new, () -> !doesClassExist("com.google.common.hash.Hashing")));
        OPTIONAL_DEPENDENCIES.add(new VaroDependency("JDA", MAVEN_CENTERAL, JarDependency::new, () -> ConfigSetting.DISCORDBOT_ENABLED.getValueAsBoolean() && !doesClassExist("net.dv8tion.jda.api.JDA")));
        OPTIONAL_DEPENDENCIES.add(new VaroDependency("java-telegram-bot-api", MAVEN_CENTERAL, JarDependency::new, () -> ConfigSetting.TELEGRAM_ENABLED.getValueAsBoolean() && !doesClassExist("com.pengrad.telegrambot.TelegramBot")));
    }

    public static void loadNeeded(Plugin plugin) {
        for (VaroDependency lib : OPTIONAL_DEPENDENCIES)
            try {
                lib.load(plugin);
            } catch (Throwable e) {
                plugin.getLogger().log(Level.SEVERE, "Unable to load dependency", e);
            }
    }

    public static class VaroDependency {

        private final String name;
        private final Dependency[] dependencies;
        private final LoadPolicy loadPolicy;

        private VaroDependency(String name, String repo, DependencySupplier supplier, LoadPolicy loadPolicy) {
            this.name = name;
            this.loadPolicy = loadPolicy;
            // TODO this should be cleaned up and optimized
            List<Dependency> dependencies = new ArrayList<>();
            try (Scanner scanner = new Scanner(DEPENDENCY_FILE.openStream())) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] split = line.split(":");
                    if (split.length != 5)
                        throw new Error();
                    if (split[0].equals(name))
                        dependencies.add(supplier.get(split[2] + "-" + split[3], LIB_FOLDER, repo + split[1].replace('.', '/') + "/" + split[2] + "/" + split[3] + "/" + split[2] + "-" + split[3] + ".jar", split[4]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (dependencies.isEmpty())
                throw new Error("Missing dependency information for " + name);
            this.dependencies = dependencies.toArray(new Dependency[dependencies.size()]);
        }

        private VaroDependency(String name, String repo, DependencySupplier supplier) {
            this(name, repo, supplier, () -> true);
        }

        public void load(Plugin plugin) throws Throwable {
            if (this.loadPolicy.shouldLoad()) {
                for (Dependency dependency : this.dependencies) {
                    plugin.getLogger().info("Loading dependency " + dependency.getName());
                    dependency.load(plugin);
                }
            } else
                plugin.getLogger().info("Dependency " + this.name + " is not required");
        }

        public URL[] getUrls() throws MalformedURLException {
            URL[] urls = new URL[this.dependencies.length];
            for (int i = 0; i < urls.length; i++)
                urls[i] = this.dependencies[i].getUrl();
            return urls;
        }
    }

    @FunctionalInterface
    private interface DependencySupplier {
        Dependency get(String name, String folder, String link, String hash);
    }

    private interface LoadPolicy {
        boolean shouldLoad();
    }

    public static boolean doesClassExist(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
