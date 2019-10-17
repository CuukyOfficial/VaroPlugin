package de.cuuky.varo.team;

import java.util.ArrayList;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.PlayerState;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.scoreboard.nametag.Nametag;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class Team implements VaroSerializeable {

	private static ArrayList<Team> teams;
	private static int highestNumber;

	static {
		teams = new ArrayList<>();
		highestNumber = 1;
	}

	@VaroSerializeField(path = "name")
	private String name;
	@VaroSerializeField(path = "colorCode")
	private String colorCode;
	@VaroSerializeField(path = "id")
	private int id;
	@VaroSerializeField(path = "lifes")
	private double lifes;
	@VaroSerializeField(path = "memberid")
	private ArrayList<Integer> memberid = new ArrayList<Integer>();

	private ArrayList<VaroPlayer> member = new ArrayList<>();

	public Team() {
		teams.add(this);
	}

	public Team(String name) {
		this.name = name;
		this.id = generateId();
		loadDefaults();

		teams.add(this);

		Nametag.refreshAll();
		if(this.id > highestNumber)
			highestNumber = id;
	}

	public void loadDefaults() {
		this.lifes = ConfigEntry.TEAM_LIFES.getValueAsInt();
	}

	public double getLifes() {
		return lifes;
	}

	public void setLifes(double lifes) {
		this.lifes = lifes;
	}

	public void addMember(VaroPlayer vp) {
		if(this.isMember(vp))
			return;

		this.member.add(vp);
		vp.setTeam(this);
	}

	public void removeMember(VaroPlayer vp) {
		this.member.remove(vp);
		vp.setTeam(null);

		if(member.size() == 0)
			teams.remove(this);
	}

	public boolean isOnline() {
		for(VaroPlayer vp : member)
			if(!vp.isOnline())
				return false;

		return true;
	}

	public ArrayList<VaroSaveable> getSaveables() {
		ArrayList<VaroSaveable> save = new ArrayList<VaroSaveable>();
		for(VaroPlayer vp : member)
			save.addAll(vp.getStats().getSaveablesRaw());

		return save;
	}

	public void removeSaveable(VaroSaveable saveable) {
		for(VaroPlayer vp : member)
			if(vp.getStats().getSaveables().contains(saveable))
				vp.getStats().removeSaveable(saveable);
	}

	public int getKills() {
		int kills = 0;
		for(VaroPlayer player : member)
			kills += player.getStats().getKills();

		return kills;
	}

	public void delete() {
		this.member.forEach(member -> member.setTeam(null));
		teams.remove(this);
	}

	public void statChanged() {
		this.member.forEach(member -> member.update());
	}

	public boolean isMember(VaroPlayer vp) {
		return this.member.contains(vp);
	}

	public ArrayList<VaroPlayer> getMember() {
		return member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		statChanged();
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
		statChanged();
	}

	public String getColorCode() {
		return colorCode == null ? Main.getColorCode() : colorCode;
	}

	public String getDisplay() {
		return getColorCode() + "#" + name;
	}

	public int getId() {
		return id;
	}

	public boolean isDead() {
		for(VaroPlayer player : member) {
			if(player.getStats().getState() != PlayerState.ALIVE)
				continue;

			return false;
		}

		return true;
	}

	private int generateId() {
		int i = teams.size() + 1;
		while(getTeam(i) != null)
			i++;

		return i;
	}

	public static Team getTeam(int id) {
		for(Team team : teams) {
			if(team.getId() != id)
				continue;

			return team;
		}

		return null;
	}

	public static ArrayList<Team> getAliveTeams() {
		ArrayList<Team> alive = new ArrayList<Team>();
		for(Team team : teams)
			if(!team.isDead())
				alive.add(team);

		return alive;
	}

	public static ArrayList<Team> getDeadTeams() {
		ArrayList<Team> dead = new ArrayList<Team>();
		for(Team team : teams)
			if(team.isDead())
				dead.add(team);

		return dead;
	}

	public static ArrayList<Team> getOnlineTeams() {
		ArrayList<Team> online = new ArrayList<Team>();
		for(Team team : teams)
			if(team.isOnline())
				online.add(team);

		return online;
	}

	public static Team getTeam(String name) {
		for(Team team : teams) {
			if(!team.getName().equals(name) && !String.valueOf(team.getId()).equals(name))
				continue;

			return team;
		}

		return null;
	}

	public static ArrayList<Team> getTeams() {
		return teams;
	}

	@Override
	public void onDeserializeEnd() {
		for(int id : memberid) {
			VaroPlayer vp = VaroPlayer.getPlayer(id);
			if(vp == null) {
				Main.getLoggerMaster().getEventLogger().println(LogType.LOG, id + " has been removed without reason - please report this to the creator of this plugin");
				continue;
			}

			addMember(vp);
		}

		if(id > highestNumber)
			highestNumber = id;
		memberid.clear();
	}

	@Override
	public void onSerializeStart() {
		for(VaroPlayer member : member)
			memberid.add(member.getId());
	}

	public static int getHighestNumber() {
		return highestNumber;
	}
}
