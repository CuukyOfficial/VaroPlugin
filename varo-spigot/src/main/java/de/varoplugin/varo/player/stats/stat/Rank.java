package de.varoplugin.varo.player.stats.stat;

import java.util.ArrayList;

import de.varoplugin.varo.serialize.identifier.VaroSerializeField;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;

public class Rank implements VaroSerializeable {

	private static int highestLocation;
	private static ArrayList<Rank> ranks;

	static {
		ranks = new ArrayList<Rank>();
		highestLocation = 1;
	}

	@VaroSerializeField(path = "colorcode")
	private int colorcode;

	@VaroSerializeField(path = "name")
	private String name;

	@VaroSerializeField(path = "tablistLocation")
	private int tablistLocation;

	public Rank() {
		ranks.add(this);
	}

	public Rank(String name) {
		this.name = name.replace("&", "§");
		this.tablistLocation = 1;

		ranks.add(this);
	}

	public int getColorcode() {
		return colorcode;
	}

	public String getDisplay() {
		return name;
	}

	public String getName() {
		return name;
	}

	public int getTablistLocation() {
		return tablistLocation;
	}

	@Override
	public void onDeserializeEnd() {
		if (tablistLocation > highestLocation)
			highestLocation = tablistLocation;
	}

	@Override
	public void onSerializeStart() {}

	public void remove() {
		ranks.remove(this);
	}

	public void setColorcode(int colorcode) {
		this.colorcode = colorcode;
	}

	public void setTablistLocation(int tablistLocation) {
		this.tablistLocation = tablistLocation;

		if (tablistLocation > highestLocation)
			highestLocation = tablistLocation;
	}

	public static int getHighestLocation() {
		return highestLocation;
	}

	public static ArrayList<Rank> getRanks() {
		return ranks;
	}
}