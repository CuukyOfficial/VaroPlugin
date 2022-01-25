package de.cuuky.varo.configuration.configurations.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.cfw.player.TablistAnimationData;
import de.cuuky.varo.Main;

public class TablistConfig extends BoardConfig {

	private TablistAnimationData header;
	private TablistAnimationData footer;

	public TablistConfig() {
		super("plugins/Varo/config/tablist.yml");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void load() {
		// Remove legacy config entries
		if (!this.configuration.contains("header.updatedelay"))
			this.configuration = new YamlConfiguration();

		this.configuration.options().header("Die Liste alle Placeholder findest du unter /varo placeholder!");

		ArrayList<String> headerFrame = new ArrayList<>();
		headerFrame.add(" ");
		headerFrame.add("%projectname%");
		headerFrame.add(" ");

		ArrayList<String> firstFooterFrame = new ArrayList<>();
		firstFooterFrame.add("&7------------------------");
		firstFooterFrame.add(" ");
		firstFooterFrame.add("&7Plugin &8(&7v%colorcode%%pluginVersion%&8) &7by %colorcode%Cuuky");
		firstFooterFrame.add(" ");
		firstFooterFrame.add("%colorcode%%currDay%&7.%colorcode%%currMonth%&7.%colorcode%%currYear%");
		firstFooterFrame.add("%colorcode%%currHour%&7:%colorcode%%currMin%&7:%colorcode%%currSec%");
		firstFooterFrame.add(" ");
		firstFooterFrame.add("&7------------------------");

		ArrayList<String> secondFooterFrame = new ArrayList<>();
		secondFooterFrame.add("&7------------------------");
		secondFooterFrame.add(" ");
		secondFooterFrame.add("&7Discord: %colorcode%" + Main.DISCORD_INVITE);
		secondFooterFrame.add(" ");
		secondFooterFrame.add("%colorcode%%currDay%&7.%colorcode%%currMonth%&7.%colorcode%%currYear%");
		secondFooterFrame.add("%colorcode%%currHour%&7:%colorcode%%currMin%&7:%colorcode%%currSec%");
		secondFooterFrame.add(" ");
		secondFooterFrame.add("&7------------------------");

		ArrayList<ArrayList<String>> headerFrames = new ArrayList<>();
		headerFrames.add(headerFrame);

		ArrayList<ArrayList<String>> footerFrames = new ArrayList<>();
		footerFrames.add(firstFooterFrame);
		footerFrames.add(secondFooterFrame);

		this.configuration.addDefault("header.updatedelay", 0);
		this.configuration.addDefault("header.content", headerFrames);
		this.configuration.addDefault("footer.updatedelay", 100);
		this.configuration.addDefault("footer.content", footerFrames);

		this.header = new TablistAnimationData(this.configuration.getInt("header.updatedelay"), (List<List<String>>) this.configuration.getList("header.content"));
		this.footer = new TablistAnimationData(this.configuration.getInt("footer.updatedelay"), (List<List<String>>) this.configuration.getList("footer.content"));
	}

	public TablistAnimationData getHeader() {
		return header;
	}

	public TablistAnimationData getFooter() {
		return footer;
	}
}