package de.cuuky.varo.bstats;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.threads.LagCounter;

public class MetricsLoader {
	
	private static final int BSTATS_ID = 6639;
	
	private Metrics metrics;	
	
	public MetricsLoader(JavaPlugin instance) {
		loadMetrics(instance);
	}
	
	private void loadMetrics(JavaPlugin instance) {
		metrics = new Metrics(instance, BSTATS_ID);
		metrics.addCustomChart(new Metrics.SimpleBarChart("functionsUsed", new Callable<Map<String, Integer>>() {

			@Override
			public Map<String, Integer> call() throws Exception {
				Map<String, Integer> map = new HashMap<>();
				for(ConfigEntry entry : ConfigEntry.values()) {
					if(entry.getValue() instanceof Boolean) {
						map.put(entry.getName(), entry.getValueAsBoolean() ? 1 : 0);
					}
				}

				return map;
			}
		}));

		metrics.addCustomChart(new Metrics.MultiLineChart("functionsValues", new Callable<Map<String, Integer>>() {
			@Override
			public Map<String, Integer> call() throws Exception {
				Map<String, Integer> valueMap = new HashMap<>();
				for(ConfigEntry entry : ConfigEntry.values()) {
					if(entry.getValue() instanceof Integer) {
						valueMap.put(entry.getName(), entry.getValueAsInt());
					}
				}

				return valueMap;
			}
		}));

		metrics.addCustomChart(new Metrics.SingleLineChart("serverTps", new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				return (int) Math.round(LagCounter.getTPS());
			}
		}));
	}
}