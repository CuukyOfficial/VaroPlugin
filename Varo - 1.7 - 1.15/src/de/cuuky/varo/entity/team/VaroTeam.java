package de.cuuky.varo.entity.team;

import java.util.ArrayList;

import de.cuuky.varo.Main;
import de.cuuky.varo.clientadapter.nametag.Nametag;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.VaroEntity;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.VaroInventory;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;

public class VaroTeam extends VaroEntity {

	private static int highestNumber;
	private static ArrayList<VaroTeam> teams;

	static {
		teams = new ArrayList<>();
		highestNumber = 1;
	}

	@VaroSerializeField(path = "colorCode")
	private String colorCode;

	@VaroSerializeField(path = "id")
	private int id;

	@VaroSerializeField(path = "lifes")
	private double lifes;

	private ArrayList<VaroPlayer> member;

	@VaroSerializeField(path = "memberid")
	private ArrayList<Integer> memberid;

	@VaroSerializeField(path = "name")
	private String name;

	@VaroSerializeField(path = "teamBackPack")
	private VaroInventory teamBackPack;

	public VaroTeam() {
		teams.add(this);
		member = new ArrayList<>();
		teamBackPack = new VaroInventory(ConfigEntry.BACKPACK_TEAM_SIZE.getValueAsInt());
		memberid = new ArrayList<Integer>();
	}

	public VaroTeam(String name) {
		this();

		this.name = name;
		this.id = generateId();
		loadDefaults();

		Nametag.refreshAll();
		if(this.id > highestNumber)
			highestNumber = id;
	}

	private int generateId() {
		int i = teams.size() + 1;
		while(getTeam(i) != null)
			i++;

		return i;
	}

	public void addMember(VaroPlayer vp) {
		if(this.isMember(vp))
			return;

		this.member.add(vp);
		vp.setTeam(this);
	}

	public void delete() {
		this.member.forEach(member -> member.setTeam(null));
		int id = this.getId();
		int number = VaroTeam.getTeams().size();
		for(int i = id; i < number; i++) {
			VaroTeam.getTeams().get(i).setId(i);
		}
		teams.remove(this);
	}

	public boolean isDead() {
		for(VaroPlayer player : member) {
			if(player.getStats().getState() != PlayerState.ALIVE)
				continue;

			return false;
		}

		return true;
	}

	public boolean isOnline() {
		for(VaroPlayer vp : member)
			if(!vp.isOnline())
				return false;

		return true;
	}

	public void loadDefaults() {
		this.lifes = ConfigEntry.TEAM_LIFES.getValueAsInt();
	}

	@Override
	public void onDeserializeEnd() {
		for(int id : memberid) {
			VaroPlayer vp = VaroPlayer.getPlayer(id);
			if(vp == null) {
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.LOG, id + " has been removed without reason - please report this to the creator of this plugin");
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

	public void removeMember(VaroPlayer vp) {
		this.member.remove(vp);
		vp.setTeam(null);

		if(member.size() == 0)
			teams.remove(this);
	}

	public void removeSaveable(VaroSaveable saveable) {
		for(VaroPlayer vp : member)
			if(vp.getStats().getSaveables().contains(saveable))
				vp.getStats().removeSaveable(saveable);
	}

	public boolean isMember(VaroPlayer vp) {
		return this.member.contains(vp);
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
		if(colorCode != null)
			this.colorCode = colorCode.replace("&", "§");
		
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

	public int getKills() {
		int kills = 0;
		for(VaroPlayer player : member)
			kills += player.getStats().getKills();

		return kills;
	}

	public double getLifes() {
		return lifes;
	}

	public ArrayList<VaroPlayer> getMember() {
		return member;
	}

	public String getName() {
		return name;
	}

	public ArrayList<VaroSaveable> getSaveables() {
		ArrayList<VaroSaveable> save = new ArrayList<VaroSaveable>();
		for(VaroPlayer vp : member)
			save.addAll(vp.getStats().getSaveablesRaw());

		return save;
	}

	public VaroInventory getTeamBackPack() {
		return teamBackPack;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLifes(double lifes) {
		this.lifes = lifes;
	}

	public void setName(String name) {
		this.name = name;
		statChanged();
	}

	public void statChanged() {
		this.member.forEach(member -> member.update());
	}

	public static ArrayList<VaroTeam> getAliveTeams() {
		ArrayList<VaroTeam> alive = new ArrayList<VaroTeam>();
		for(VaroTeam team : teams)
			if(!team.isDead())
				alive.add(team);

		return alive;
	}

	public static ArrayList<VaroTeam> getDeadTeams() {
		ArrayList<VaroTeam> dead = new ArrayList<VaroTeam>();
		for(VaroTeam team : teams)
			if(team.isDead())
				dead.add(team);

		return dead;
	}

	public static int getHighestNumber() {
		return highestNumber;
	}

	public static ArrayList<VaroTeam> getOnlineTeams() {
		ArrayList<VaroTeam> online = new ArrayList<VaroTeam>();
		for(VaroTeam team : teams)
			if(team.isOnline())
				online.add(team);

		return online;
	}

	public static VaroTeam getTeam(int id) {
		for(VaroTeam team : teams) {
			if(team.getId() != id)
				continue;

			return team;
		}

		return null;
	}

	public static VaroTeam getTeam(String name) {
		for(VaroTeam team : teams) {
			if(!team.getName().equals(name) && !String.valueOf(team.getId()).equals(name))
				continue;

			return team;
		}

		return null;
	}

	public static ArrayList<VaroTeam> getTeams() {
		return teams;
	}
}