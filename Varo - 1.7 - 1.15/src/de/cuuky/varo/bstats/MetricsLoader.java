package de.cuuky.varo.bstats;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class MetricsLoader {
	
	private static final int BSTATS_ID = 6639;
	
	private Metrics metrics;	
	
	public MetricsLoader(JavaPlugin instance) {
		loadMetrics(instance);
	}
	
	private void loadMetrics(JavaPlugin instance) {
		try {
			metrics = new Metrics(instance, BSTATS_ID);
			metrics.addCustomChart(new Metrics.SimpleBarChart("functionsUsed", new Callable<Map<String, Integer>>() {

				@Override
				public Map<String, Integer> call() throws Exception {
					Map<String, Integer> map = new HashMap<>();
					for(ConfigSetting entry : ConfigSetting.values()) {
						if(entry.getValue() instanceof Boolean) {
							map.put(entry.getPath(), entry.getValueAsBoolean() ? 1 : 0);
						}
					}

					return map;
				}
			}));

			metrics.addCustomChart(new Metrics.MultiLineChart("functionsValues", new Callable<Map<String, Integer>>() {
				@Override
				public Map<String, Integer> call() throws Exception {
					Map<String, Integer> valueMap = new HashMap<>();
					for(ConfigSetting entry : ConfigSetting.values()) {
						if(entry.getValue() instanceof Integer) {
							valueMap.put(entry.getPath(), entry.getValueAsInt());
						}
					}

					return valueMap;
				}
			}));
		} catch(Throwable e) {
			e.printStackTrace();
			System.err.println(Main.getConsolePrefix() + "Failed to send data to bStats! (Wrong server version?)");
		}
	}
}