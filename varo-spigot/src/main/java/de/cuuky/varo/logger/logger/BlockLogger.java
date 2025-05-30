package de.cuuky.varo.logger.logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.logger.CachedVaroLogger;

public class BlockLogger extends CachedVaroLogger<LoggedBlock> {
    
    private final ListValuedMap<String, LoggedBlock> byUuid = new ArrayListValuedHashMap<>();
    private final ListValuedMap<String, LoggedBlock> byName = new ArrayListValuedHashMap<>();
    private final ListValuedMap<String, LoggedBlock> byMaterial = new ArrayListValuedHashMap<>();
    private final Map<String, ListValuedMap<String, LoggedBlock>> byUuidMaterial = new HashMap<>();
    private final Map<String, ListValuedMap<String, LoggedBlock>> byNameMaterial = new HashMap<>();

	public BlockLogger(String name) {
		super(name, LoggedBlock.class);
	}
	
	@Override
	protected void appendLog(LoggedBlock log) {
	    super.appendLog(log);
	    this.byUuid.put(log.getUuid(), log);
	    this.byName.put(log.getName(), log);
	    this.byMaterial.put(log.getMaterial(), log);
	    this.byUuidMaterial.computeIfAbsent(log.getUuid(), key -> new ArrayListValuedHashMap<>()).put(log.getMaterial(), log);
	    this.byNameMaterial.computeIfAbsent(log.getName(), key -> new ArrayListValuedHashMap<>()).put(log.getMaterial(), log);
	}

	public void println(Block block, Player player) {
		if (!Main.getDataManager().getListManager().getDestroyedBlocks().shallLog(block))
			return;

		Location location = block.getLocation();
		this.queueLog(new LoggedBlock(System.currentTimeMillis(), player.getUniqueId().toString(), player.getName(), block.getType().toString(), location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
	}
	
	public List<LoggedBlock> getUuidLogs(String player) {
	    return Collections.unmodifiableList(this.byUuid.get(player));
	}
	
	public List<LoggedBlock> getNameLogs(String player) {
        return Collections.unmodifiableList(this.byUuid.get(player));
    }
	
    public List<LoggedBlock> getMaterialLogs(String material) {
        return Collections.unmodifiableList(this.byMaterial.get(material));
    }
    
    public List<LoggedBlock> getUuidMaterialLogs(String player, String material) {
        ListValuedMap<String, LoggedBlock> map = this.byUuidMaterial.get(player);
        if (map == null)
            return Collections.emptyList();
        return Collections.unmodifiableList(map.get(material));
    }
    
    public List<LoggedBlock> getNameMaterialLogs(String player, String material) {
        ListValuedMap<String, LoggedBlock> map = this.byNameMaterial.get(player);
        if (map == null)
            return Collections.emptyList();
        return Collections.unmodifiableList(map.get(material));
    }
}