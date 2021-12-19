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
		new ExportCommand();
		new StatsCommand();
		new UpdateCommand();
		new FinaleCommand();
		new PlaceholderCommand();
		new BugreportCommand();
		new ReviveCommand();
		new PlaytimeCommand();
		new CheckCombatCommand();
		new CommandCommand();
		// new TestCommand();
	}

	private String[] aliases;
	private String name, permission, description;

	public VaroCommand(String name, String description, String permission, String... aliases) {
		this.name = name;
		this.aliases = aliases;
		this.description = description;
		this.permission = permission;

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