package de.varoplugin.varo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.MultiLineChart;
import org.bstats.charts.SimpleBarChart;
import org.bukkit.plugin.java.JavaPlugin;

import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;

public class MetricsLoader {

	private static final int BSTATS_ID = 6639;
	
	private MetricsLoader() {
	    throw new UnsupportedOperationException();
	}

	public static void loadMetrics(JavaPlugin instance) {
		try {
		    System.setProperty("bstats.relocatecheck", "false"); // bstats is loaded in a different class loader
			Metrics metrics = new Metrics(instance, BSTATS_ID);
			metrics.addCustomChart(new SimpleBarChart("functionsUsed", new Callable<Map<String, Integer>>() {

				@Override
				public Map<String, Integer> call() throws Exception {
					Map<String, Integer> map = new HashMap<>();
					for (ConfigSetting entry : ConfigSetting.values()) {
						if (entry.getValue() instanceof Boolean) {
							map.put(entry.getPath(), entry.getValueAsBoolean() ? 1 : 0);
						}
					}

					return map;
				}
			}));

			metrics.addCustomChart(new MultiLineChart("functionsValues", new Callable<Map<String, Integer>>() {
				@Override
				public Map<String, Integer> call() throws Exception {
					Map<String, Integer> valueMap = new HashMap<>();
					for (ConfigSetting entry : ConfigSetting.values()) {
						if (entry.getValue() instanceof Integer) {
							valueMap.put(entry.getPath(), entry.getValueAsInt());
						}
					}

					return valueMap;
				}
			}));
		} catch (Throwable t) {
		    instance.getLogger().log(Level.WARNING, "Failed to load bstats!", t);
		}
	}
}