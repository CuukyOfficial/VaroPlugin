package de.cuuky.varo.clientadapter.list.lists;

import java.util.ArrayList;

import de.cuuky.varo.clientadapter.list.BoardList;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class TablistBoardList extends BoardList {

	private ArrayList<String> headerLines, footerLines;

	public TablistBoardList() {
		super("plugins/Varo/config/tablist.yml");
	}

	@Override
	protected void updateList() {
		this.headerLines = new ArrayList<>();
		this.footerLines = new ArrayList<>();
		ArrayList<String> header = new ArrayList<>();
		header.add(" ");
		header.add("%projectname%");
		header.add(" ");

		ArrayList<String> footer = new ArrayList<>();
		footer.add("&7------------------------");
		footer.add("&7Online: %colorcode%%online%");
		footer.add("&7Alive: %colorcode%%remaining%");
		footer.add("&7Registered: %colorcode%%players%");
		footer.add(" ");
		footer.add("&7Plugin &8(&7v%colorcode%%pluginVersion%&8) &7by %colorcode%Cuuky");
		footer.add(" ");
		footer.add("%colorcode%%currDay%&7.%colorcode%%currMonth%&7.%colorcode%%currYear%");
		footer.add("%colorcode%%currHour%&7:%colorcode%%currMin%&7:%colorcode%%currSec%");
		footer.add(" ");
		footer.add("&7------------------------");

		configuration.addDefault("header", header);
		configuration.addDefault("footer", footer);

		if (ConfigSetting.TABLIST_USE_HEADER.getValueAsBoolean())
			headerLines.addAll(configuration.getStringList("header"));
		if (ConfigSetting.TABLIST_USE_FOOTER.getValueAsBoolean())
			footerLines.addAll(configuration.getStringList("footer"));
	}

	public ArrayList<String> getHeaderLines() {
		return this.headerLines;
	}

	public ArrayList<String> getFooterLines() {
		return this.footerLines;
	}
}