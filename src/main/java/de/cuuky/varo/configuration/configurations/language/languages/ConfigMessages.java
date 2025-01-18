package de.cuuky.varo.configuration.configurations.language.languages;

import de.cuuky.cfw.configuration.language.Language;
import de.cuuky.cfw.configuration.language.languages.DefaultLanguage;
import de.cuuky.cfw.player.CustomPlayer;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public enum ConfigMessages implements DefaultLanguage {

	COMBAT_FRIENDLY_FIRE("combat.friendlyfire", "&7Dieser Spieler ist in deinem Team!"),
	COMBAT_IN_FIGHT("combat.inFight", "&7Du bist nun im Kampf, logge dich &4NICHT &7aus!"),
	COMBAT_LOGGED_OUT("combat.loggedOut", "&c%player% &7hat den Server während eines Kampfes verlassen!"),
	COMBAT_NOT_IN_FIGHT("combat.notInFight", "&7Du bist nun nicht mehr im &cKampf&7!"),

	SPAWN_WORLD("spawn.spawn", "%colorcode%Koordinaten&7 vom Spawn: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	SPAWN_NETHER("spawn.spawnNether", "%colorcode%Koordinaten&7 vom Portal zur Oberwelt: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	SPAWN_DISTANCE("spawn.spawnDistance", "&7Du bist %colorcode%%distance% &7Blöcke vom Spawn entfernt!"),
	SPAWN_DISTANCE_NETHER("spawn.spawnDistanceNether", "&7Du bist %colorcode%%distance% &7Blöcke vom Portal zur Oberwelt entfernt!"),

	GAME_VARO_START("game.start.varoStart", "%projectname% &7wurde gestartet! &5Viel Erfolg!"),
	GAME_VARO_START_TITLE("game.start.startTitle", "%colorcode%%countdown%"),
	GAME_VARO_START_SUBTITLE("game.start.startSubtitle", "&7Viel Glück!"),
	GAME_WIN("game.win.single", "&5%player% &7hat %projectname% &7gewonnen! &5Gratulation!"),
	GAME_WIN_TEAM("game.win.team", "&5%winnerPlayers% &7haben %projectname% &7gewonnen! &5Gratulation!"),

	SERVER_MODT_CANT_JOIN_HOURS("motd.cantJoinHours", "&cDu kannst nur zwischen &4%minHour% &cund &4%maxHour%&c Uhr joinen! %nextLine%&7Versuche es später erneut! &7%currHour%&7:&7%currMin%&7:&7%currSec%"),
	SERVER_MODT_NOT_OPENED("motd.serverNotOpened", "&cDer Server wurde noch nicht für alle geöffnet! %nextLine%&7Versuche es später erneut!"),
	SERVER_MODT_OPEN("motd.serverOpen", "&aSei nun bei %projectname% &adabei! \n&7Viel Spass!"),

	CHEST_NOT_TEAM_CHEST("chest.notTeamChest", "&7Diese Kiste gehört %colorcode%%player%&7!"),
	CHEST_NOT_TEAM_FURNACE("chest.notTeamFurnace", "&7Dieser Ofen gehört %colorcode%%player%&7!"),
	CHEST_REMOVED_SAVEABLE("chest.removedChest", "&7Du hast diese/n %saveable% %colorcode%erfolgreich &7entfernt!"),
	CHEST_SAVED_CHEST("chest.newChestSaved", "&7Eine neue Kiste wurde gesichert!"),
	CHEST_SAVED_FURNACE("chest.newFurnaceSaved", "&7Ein neuer Ofen wurde gesichert!"),

	PLACEHOLDER_NO_TOP_PLAYER("placeholder.noTopPlayer", "-"), // TODO delete this
	PLACEHOLDER_NO_TOP_TEAM("placeholder.noTopTeam", "-"), // TODO delete this

	PROTECTION_NO_MOVE_START("protection.noMoveStart", "&7Du kannst dich nicht bewegen, solange das Projekt noch nicht gestartet wurde."),
	PROTECTION_START("protection.start", "&7Die &cSchutzzeit &7startet jetzt und wird &c%seconds% &7Sekunden anhalten!"),
	PROTECTION_TIME_OVER("protection.protectionOver", "&7Die &cSchutzzeit &7ist nun vorrüber!"),
	PROTECTION_TIME_UPDATE("protection.protectionUpdate", "&7Die &cSchutzzeit &7ist in &c%minutes%&7:&c%seconds% &7vorrüber!"),
	PROTECTION_TIME_RUNNING("protection.timeRunning", "&7Die %colorcode%Schutzzeit &7läuft noch!"),

	SORT_NO_HOLE_FOUND("sort.noHoleFound", "&7Es konnte für dich kein Loch gefunden werden!"),
	SORT_NO_HOLE_FOUND_TEAM("sort.noHoleFoundTeam", "Es konnte für dich kein Loch bei deinen Teampartnern gefunden werden."),
	SORT_NUMBER_HOLE("sort.numberHoleTeleport", "Du wurdest in das Loch %colorcode%%number% &7teleportiert!"),
	SORT_OWN_HOLE("sort.ownHoleTeleport", "Du wurdest in dein Loch einsortiert!"),
	SORT_SPECTATOR_TELEPORT("sort.spectatorTeleport", "Du wurdest, da du Spectator bist, zum Spawn teleportiert!"),
	SORT_SORTED("sort.sorted", "&7Du wurdest in das Loch %colorcode%%zahl% &7teleportiert!"),

	SPAWNS_SPAWN_NUMBER("spawns.spawnNameTag.number", "&7Spawn %colorcode%%number%"),
	SPAWNS_SPAWN_PLAYER("spawns.spawnNameTag.player", "&7Spawn von %colorcode%%player%"),

	MODS_BLOCKED_MODS_KICK("mods.blockedModsKick", "&7Bitte entferne folgende Mods: %colorcode%%mods%"),
	MODS_BLOCKED_MODLIST_SPLIT("mods.blockedModsListSplit", "&7, "),
	MODS_BLOCKED_MODS_BROADCAST("mods.blockedModsBroadcast", "&7Der Spieler %colorcode%%player% &7hat versucht mit folgenden blockierten Mods zu joinen: %colorcode%%mods%"),

	LOGGER_FILTER_INVALID_FILTER("blockLoggerFilter.invalidFilter", "&c%filterName% \"%content%\" ist nicht gültig!"),
	LOGGER_FILTER_SET_FILTER("blockLoggerFilter.setFilter", "&7%filterName% wurde auf %colorcode%%newContent% &7gesetzt (vorher: %oldContent%)."),
	LOGGER_FILTER_RESET_FILTER("blockLoggerFilter.resetFilter", "&7%filterName% wurde zurückgesetzt (vorher: %oldContent%)."),
	LOGGER_FILTER_PLAYER_FILTER_MESSAGE("blockLoggerFilter.playerFilterMessage", "&7Bitte gib einen Spielernamen ein:"),
	LOGGER_FILTER_MATERIAL_FILTER_MESSAGE("blockLoggerFilter.materialFilterMessage", "&7Bitte gib einen Materialnamen ein:");

	private String path, defaultMessage, message;

	ConfigMessages(String path, String message) {
		this.path = path;
		this.defaultMessage = message;
		this.message = message;
	}

	private String getMessage(Language lang) {
		String message;
		if (lang == null || lang == Main.getLanguageManager().getDefaultLanguage() || !ConfigSetting.MAIN_LANGUAGE_ALLOW_OTHER.getValueAsBoolean())
			message = Main.getLanguageManager().getDefaultLanguage().getMessage(this.path);
		else {
			String langMsg = lang.getMessage(this.path);
			message = langMsg == null ? Main.getLanguageManager().getDefaultLanguage().getMessage(this.path) : langMsg;
		}

		return Main.getLanguageManager().replaceMessage(message != null ? message : this.message);
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getDefaultMessage() {
		return this.defaultMessage;
	}

	@Override
	public String getValue() {
		return getMessage(Main.getLanguageManager().getDefaultLanguage());
	}

	@Override
	public String getValue(CustomPlayer localeHolder) {
		return getMessage(localeHolder != null && localeHolder.getLocale() != null ? Main.getLanguageManager().getLanguages().get(localeHolder.getLocale()) : Main.getLanguageManager().getDefaultLanguage());
	}

	@Override
	public String getValue(CustomPlayer localeHolder, CustomPlayer replace) {
		return Main.getLanguageManager().replaceMessage(getValue(localeHolder), replace);
	}
}
