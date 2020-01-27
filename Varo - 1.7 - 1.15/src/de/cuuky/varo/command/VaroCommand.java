package de.cuuky.varo.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
import de.cuuky.varo.command.varo.FinaleCommand;
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
import de.cuuky.varo.command.varo.UpdateCommand;
import de.cuuky.varo.entity.player.VaroPlayer;

public abstract class VaroCommand {

	private static ArrayList<VaroCommand> varoCommands;

	static {
		varoCommands = new ArrayList<>();

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
		new UpdateCommand();
		new FinaleCommand();
//		new TestCommand();
	}

	private String[] aliases;
	private String description;
	private String name;
	private String permission;

	public VaroCommand(String name, String description, String permission, String... aliases) {
		this.name = name;
		this.aliases = aliases;
		this.description = description;
		this.permission = permission;

		varoCommands.add(this);
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getPermission() {
		return permission;
	}

	public boolean isAlias(String s) {
		if(this.aliases == null)
			return false;

		for(String alias : aliases)
			if(alias.equalsIgnoreCase(s))
				return true;

		return false;
	}

	public abstract void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args);

	/**
	 * @param description
	 *            The description in the plugin.yml file
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param command
	 *            The ingame command
	 * @return Returns the VaroCommand Object
	 */
	public static VaroCommand getCommand(String command) {
		for(VaroCommand chunkCommand : varoCommands) {
			if(!chunkCommand.getName().equalsIgnoreCase(command) && !chunkCommand.isAlias(command))
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