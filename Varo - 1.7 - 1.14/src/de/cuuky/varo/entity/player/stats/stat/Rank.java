package de.cuuky.varo.entity.player.stats.stat;

import java.util.ArrayList;

import de.cuuky.varo.scoreboard.nametag.Nametag;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class Rank implements VaroSerializeable {

	private static ArrayList<Rank> ranks;
	private static int highestLocation;

	static {
		ranks = new ArrayList<Rank>();
		highestLocation = 1;
	}

	@VaroSerializeField(path = "name")
	private String name;

	@VaroSerializeField(path = "tablistLocation")
	private int tablistLocation;

	@VaroSerializeField(path = "colorcode")
	private int colorcode;

	public Rank() {
		ranks.add(this);
	}

	public Rank(String name) {
		this.name = name.replace("&", "§");
		this.tablistLocation = 1;

		ranks.add(this);

		Nametag.refreshAll();
	}

	public static ArrayList<Rank> getRanks() {
		return ranks;
	}

	public static int getHighestLocation() {
		return highestLocation;
	}

	@Override
	public void onDeserializeEnd() {
		if (tablistLocation > highestLocation)
			highestLocation = tablistLocation;
	}

	@Override
	public void onSerializeStart() {
	}

	public void remove() {
		ranks.remove(this);
	}

	public String getName() {
		return name;
	}

	public int getColorcode() {
		return colorcode;
	}

	public void setColorcode(int colorcode) {
		this.colorcode = colorcode;
	}

	public int getTablistLocation() {
		return tablistLocation;
	}

	public void setTablistLocation(int tablistLocation) {
		this.tablistLocation = tablistLocation;

		if (tablistLocation > highestLocation)
			highestLocation = tablistLocation;
	}

	public String getDisplay() {
		return name;
	}
}
