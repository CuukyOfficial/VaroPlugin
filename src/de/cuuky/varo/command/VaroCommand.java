package de.cuuky.varo.command;

import de.cuuky.varo.command.varo.*;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class VaroCommand {

	private static final List<VaroCommand> varoCommands = new ArrayList<>();

	static {
		new AbortCommand();
		new ActionbarCommand();
		new AutoSetupCommand();
		new AutoStartCommand();
		new BackpackCommand();
		new BugreportCommand();
		new CheckCombatCommand();
		new CommandCommand();
		new ConfigCommand();
		new DiscordCommand();
		new EnchantmentCommand();
		new EpisodesCommand();
		new EventsCommand();
		new ExportCommand();
		new FinaleCommand();
		new GameCommand();
		new InfoCommand();
		new IntroCommand();
		new ItemCommand();
		new LobbyCommand();
		new MenuCommand();
		new PlaceholderCommand();
		new PlayerCommand();
		new PlaytimeCommand();
		new PresetCommand();
		new RandomTeamCommand();
		new ResetCommand();
		new RestartCommand();
		new ReviveCommand();
		new ScoreboardCommand();
		new SetupCommand();
		new SortCommand();
		new SpawnsCommand();
		new StartCommand();
		new StatsCommand();
		new StrikeCommand();
		new TeamCommand();
		new TeamRequestCommand();
		new UpdateCommand();
	}

	private String[] aliases;
	private String[] subCommands = null;
	private String name, permission, description;

	public VaroCommand(String name, String description, String permission, String[] subCommands, String... aliases) {
		this.name = name;
		this.aliases = aliases;
		this.description = description;
		this.permission = permission;
		this.subCommands = subCommands;

		varoCommands.add(this);
	}

	public boolean isAlias(String check) {
	    return this.aliases != null && Arrays.stream(this.aliases).anyMatch(alias -> alias.equalsIgnoreCase(check));
	}

	public void remove() {
		varoCommands.remove(this);
	}

	public boolean matches(String command) {
		return this.name.equalsIgnoreCase(command) || this.isAlias(command);
	}

	public abstract void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args);

	public abstract List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);

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

	public void setPermission(String perm) { this.permission =  perm; }

	public String getDescription() {
		return description;
	}

	public String getName() { return name; }

	public String getPermission() { return permission; }

	public String[] getAliases() { return aliases; }

	public String[] getSubCommands() {
		return subCommands;
	}

	/**
	 * @param command
	 *            The ingame command
	 * @return Returns the VaroCommand Object
	 */
	public static VaroCommand getCommand(String command) {
		return varoCommands.stream().filter(cc -> cc.matches(command)).findFirst().orElse(null);
	}

	/**
	 * @return Returns all VaroCommand Objects
	 */
	public static List<VaroCommand> getVaroCommand() {
		return varoCommands;
	}
}