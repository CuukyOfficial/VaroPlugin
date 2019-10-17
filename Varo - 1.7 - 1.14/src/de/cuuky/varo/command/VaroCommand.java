package de.cuuky.varo.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.varo.AbortCommand;
import de.cuuky.varo.command.varo.ActionbarCommand;
import de.cuuky.varo.command.varo.AutoSetupCommand;
import de.cuuky.varo.command.varo.AutoStartCommand;
import de.cuuky.varo.command.varo.BackpackCommand;
import de.cuuky.varo.command.varo.ConfigCommand;
import de.cuuky.varo.command.varo.DiscordCommand;
import de.cuuky.varo.command.varo.EnchantmentCommand;
import de.cuuky.varo.command.varo.EventsCommand;
import de.cuuky.varo.command.varo.ExportCommand;
import de.cuuky.varo.command.varo.GameCommand;
import de.cuuky.varo.command.varo.InfoCommand;
import de.cuuky.varo.command.varo.IntroCommand;
import de.cuuky.varo.command.varo.ItemCommand;
import de.cuuky.varo.command.varo.LobbyCommand;
import de.cuuky.varo.command.varo.MenuCommand;
import de.cuuky.varo.command.varo.PlayerCommand;
import de.cuuky.varo.command.varo.PresetCommand;
import de.cuuky.varo.command.varo.RandomTeamCommand;
import de.cuuky.varo.command.varo.ResetCommand;
import de.cuuky.varo.command.varo.RestartCommand;
import de.cuuky.varo.command.varo.ScoreboardCommand;
import de.cuuky.varo.command.varo.SetupCommand;
import de.cuuky.varo.command.varo.SortCommand;
import de.cuuky.varo.command.varo.SpawnsCommand;
import de.cuuky.varo.command.varo.StartCommand;
import de.cuuky.varo.command.varo.StatsCommand;
import de.cuuky.varo.command.varo.StrikeCommand;
import de.cuuky.varo.command.varo.TeamCommand;
import de.cuuky.varo.command.varo.TeamRequestCommand;
import de.cuuky.varo.command.varo.TrollCommand;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;

public abstract class VaroCommand {

	private static ArrayList<VaroCommand> varoCommands = new ArrayList<>();

	static {
		new StartCommand();
		new DiscordCommand();
		new TeamCommand();
		new SpawnsCommand();
		new AutoStartCommand();
		new TeamRequestCommand();
		new ConfigCommand();
		new MenuCommand();
		new RandomTeamCommand();
		new ScoreboardCommand();
		new ActionbarCommand();
		new AbortCommand();
		new AutoSetupCommand();
		new ResetCommand();
		new RestartCommand();
		new StrikeCommand();
		new PlayerCommand();
		new SortCommand();
		new SetupCommand();
		new ItemCommand();
		new EnchantmentCommand();
		new InfoCommand();
		new BackpackCommand();
		new GameCommand();
		new PresetCommand();
		new LobbyCommand();
		new IntroCommand();
		new EventsCommand();
		new TrollCommand();
		new ExportCommand();
		new StatsCommand();
		// new TestCommand();
	}

	private String name;
	private String[] aliases;
	private String description;
	private String permission;

	public VaroCommand(String name, String description, String permission, String... aliases) {
		this.name = name;
		this.aliases = aliases;
		this.description = description;
		this.permission = permission;

		varoCommands.add(this);
	}

	public abstract void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getAliases() {
		return aliases;
	}

	public String getPermission() {
		return permission;
	}

	/**
	 * @return Returns the no Permission String
	 */
	public String getNoPermission() {
		return getNoPermission(permission);
	}

	public void setAliases(String[] aliases) {
		this.aliases = aliases;
	}

	public String getDescription() {
		return description;
	}

	public boolean isAlias(String s) {
		if (this.aliases == null)
			return false;

		for (String alias : aliases)
			if (alias.equalsIgnoreCase(s))
				return true;

		return false;
	}

	/**
	 * Returns the No Permission string
	 * 
	 * @param permission The Permission that should be added in the String
	 * @return Returns the String + the Permission added
	 */
	public static String getNoPermission(String permission) {
		return Main.getPrefix() + ConfigMessages.OTHER_NO_PERMISSION.getValue().replaceAll("%permission%", permission);
	}

	/**
	 * @param description The description in the plugin.yml file
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param command The ingame command
	 * @return Returns the VaroCommand Object
	 */
	public static VaroCommand getCommand(String command) {
		for (VaroCommand chunkCommand : varoCommands) {
			if (!chunkCommand.getName().equalsIgnoreCase(command) && !chunkCommand.isAlias(command))
				continue;

			return chunkCommand;
		}
		return null;
	}

	/**
	 * @return Returns all VaroCommand Objects
	 */
	public static ArrayList<VaroCommand> getVaroCommand() {
		return varoCommands;
	}
}
