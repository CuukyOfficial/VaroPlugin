package de.varoplugin.varo.data;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.utils.ZipUtil;

public class BugReport {

    private static final DateFormat DATE_FROMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    private static final String[] INCLUDED_PATHS = new String[] {
            "logs/",
            "server.properties",
            "plugins/Varo/config/",
            "plugins/Varo/messages/",
            "plugins/Varo/logs/logs.varolog2",
            "plugins/Varo/stats/",
    };

    public static File createBugReport() {
        printInfo();
        if (Main.getDataManager() == null || Main.getDataManager().getConfigHandler() == null)
            return null;

        Map<ConfigSetting, Object> redactedValues = new HashMap<>();
        for (ConfigSetting entry : ConfigSetting.values())
            if (entry.isSensitive()) {
                redactedValues.put(entry, entry.getValue());
                entry.setValue("REDACED", true);
            }

        File zipFile = new File("plugins/Varo/bugreports/bugreport-" + DATE_FROMAT.format(new Date()) + ".zip");
        boolean success = ZipUtil.zip(zipFile, new File("").getAbsoluteFile(),
                path -> Arrays.stream(INCLUDED_PATHS).anyMatch(s -> path.startsWith(s)));

        redactedValues.forEach((entry, value) -> entry.setValue(value, true));
        return success ? zipFile : null;
    }

    private static void printInfo() {
        PluginDescriptionFile pdf = Main.getInstance().getDescription();
        Runtime r = Runtime.getRuntime();

        Main.getInstance().getLogger().log(Level.INFO, "----------------------");
        Main.getInstance().getLogger().log(Level.INFO, "");
        Main.getInstance().getLogger().log(Level.INFO, "Plugin Version: " + pdf.getVersion());
        Main.getInstance().getLogger().log(Level.INFO, "Plugins enabled: " + Bukkit.getPluginManager().getPlugins().length);
        Main.getInstance().getLogger().log(Level.INFO, "Server-Version: " + Bukkit.getVersion());
        Main.getInstance().getLogger().log(Level.INFO, "System OS: " + System.getProperty("os.name"));
        Main.getInstance().getLogger().log(Level.INFO, "System Version: " + System.getProperty("os.version"));
        Main.getInstance().getLogger().log(Level.INFO, "Java Version: " + System.getProperty("java.version"));
        Main.getInstance().getLogger().log(Level.INFO, "Total memory usage: " + (r.totalMemory() - r.freeMemory()) / 1048576 + "MB!");
        Main.getInstance().getLogger().log(Level.INFO, "Total memory available: " + r.maxMemory() / 1048576 + "MB!");
        Main.getInstance().getLogger().log(Level.INFO, "Date: " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
        Main.getInstance().getLogger().log(Level.INFO, "");
        Main.getInstance().getLogger().log(Level.INFO, "----------------------");
    }
}