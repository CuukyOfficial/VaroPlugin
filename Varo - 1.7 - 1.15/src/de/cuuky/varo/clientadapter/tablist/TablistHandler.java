package de.cuuky.varo.clientadapter.tablist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.cuuky.varo.clientadapter.BoardHandler;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.utils.JavaUtils;

public class TablistHandler implements BoardHandler {

	private HashMap<Player, ArrayList<String>> headerReplaces;
	private ArrayList<String> headerLines;

	private HashMap<Player, ArrayList<String>> footerReplaces;
	private ArrayList<String> footerLines;

	public TablistHandler() {
		updateList();
	}

	private Object[] updateList(VaroPlayer player, boolean header) {
		ArrayList<String> tablistLines = header ? headerLines : footerLines, oldList = null;

		if(header) {
			oldList = headerReplaces.get(player.getPlayer());
			if(oldList == null)
				headerReplaces.put(player.getPlayer(), oldList = new ArrayList<>());
		} else {
			oldList = footerReplaces.get(player.getPlayer());
			if(oldList == null)
				footerReplaces.put(player.getPlayer(), oldList = new ArrayList<>());
		}

		boolean changed = false;
		for(int index = 0; index < tablistLines.size(); index++) {
			String line = ConfigMessages.getValue(tablistLines.get(index), player);
			
			if(oldList.size() < tablistLines.size()) {
				oldList.add(line);
				changed = true;
			} else if(!oldList.get(index).equals(line)) {
				oldList.set(index, line);
				changed = true;
			}
		}

		return new Object[] { changed, oldList };
	}

	@Override
	public void updateList() {
		this.headerReplaces = new HashMap<>();
		this.headerLines = new ArrayList<>();

		this.footerReplaces = new HashMap<>();
		this.footerLines = new ArrayList<>();

		File file = new File("plugins/Varo/config", "tablist.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		ArrayList<String> header = new ArrayList<>();
		header.add(" ");
		header.add("%projectname%");
		header.add(" ");

		ArrayList<String> footer = new ArrayList<>();
		footer.add("&7------------------------");
		footer.add("&7Registriert: %colorcode%%players%");
		footer.add("&7Lebend: %colorcode%%remaining%");
		footer.add("&7Online: %colorcode%%online%");
		footer.add(" ");
		footer.add("&7Plugin by %colorcode%Cuuky");
		footer.add(" ");
		footer.add("%colorcode%%currDay%&7.%colorcode%%currMonth%&7.%colorcode%%currYear%");
		footer.add("%colorcode%%currHour%&7:%colorcode%%currMin%&7:%colorcode%%currSec%");
		footer.add(" ");
		footer.add("&7------------------------");

		if(!cfg.contains("header")) {
			cfg.set("header", header);
			cfg.set("footer", footer);

			try {
				cfg.save(file);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		headerLines.addAll(cfg.getStringList("header"));
		footerLines.addAll(cfg.getStringList("footer"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updatePlayer(VaroPlayer player) {
		Object[] headerUpdate = updateList(player, true);
		Object[] footerUpdate = updateList(player, false);

		if((boolean) headerUpdate[0] || (boolean) footerUpdate[0])
			player.getNetworkManager().sendTablist(JavaUtils.getArgsToString((ArrayList<String>) headerUpdate[1], "\n"), JavaUtils.getArgsToString((ArrayList<String>) footerUpdate[1], "\n"));
	}
}