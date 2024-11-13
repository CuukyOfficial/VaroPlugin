package de.cuuky.varo.data;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.threads.LagCounter;
import de.cuuky.varo.utils.ZipUtil;

public class BugReport {

    private static final DateFormat DATE_FROMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    private static final String[] INCLUDED_PATHS = new String[] {
            "logs/",
            "server.properties",
            "plugins/Varo/config/",
            "plugins/Varo/languages/",
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

        System.out.println(Main.getConsolePrefix() + "----------------------");
        System.out.println(Main.getConsolePrefix());
        System.out.println(Main.getConsolePrefix() + "Plugin Version: " + pdf.getVersion());
        System.out.println(Main.getConsolePrefix() + "Plugins enabled: " + Bukkit.getPluginManager().getPlugins().length);
        System.out.println(Main.getConsolePrefix() + "Server-Version: " + Bukkit.getVersion());
        System.out.println(Main.getConsolePrefix() + "System OS: " + System.getProperty("os.name"));
        System.out.println(Main.getConsolePrefix() + "System Version: " + System.getProperty("os.version"));
        System.out.println(Main.getConsolePrefix() + "Java Version: " + System.getProperty("java.version"));
        System.out.println(Main.getConsolePrefix() + "Total memory usage: " + (r.totalMemory() - r.freeMemory()) / 1048576 + "MB!");
        System.out.println(Main.getConsolePrefix() + "Total memory available: " + r.maxMemory() / 1048576 + "MB!");
        System.out.println(Main.getConsolePrefix() + "TPS: " + (double) Math.round(LagCounter.getTPS() * 100) / 100);
        System.out.println(Main.getConsolePrefix() + "Date: " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
        System.out.println(Main.getConsolePrefix());
        System.out.println(Main.getConsolePrefix() + "----------------------");
    }
}