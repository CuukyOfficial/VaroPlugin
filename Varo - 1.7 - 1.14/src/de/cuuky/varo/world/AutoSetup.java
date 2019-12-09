package de.cuuky.varo.world;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.world.border.VaroBorder;
import org.bukkit.Location;
import org.bukkit.World;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.spawns.spawn.SpawnChecker;
import de.cuuky.varo.spawns.spawn.SpawnGenerator;
import de.cuuky.varo.utils.BlockUtils;
import de.cuuky.varo.world.generators.LobbyGenerator;
import de.cuuky.varo.world.generators.PortalGenerator;

public class AutoSetup {

	public AutoSetup() {
		if(Game.getInstance().hasStarted())
			return;

		World world = WorldHandler.getInstance().getWorld();

		System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Searching for terrain now...");

		int x = 0, z = 0;
		while(!SpawnChecker.checkSpawns(world, x, z, ConfigEntry.AUTOSETUP_SPAWNS_RADIUS.getValueAsInt(), ConfigEntry.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt())) {
			x += 100;
			z += 100;
		}

		Location middle = new Location(world, x, world.getMaxHeight(), z);

		portal: if(ConfigEntry.AUTOSETUP_PORTAL_ENABLED.getValueAsBoolean()) {
			System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting up the portal...");
			int width = ConfigEntry.AUTOSETUP_PORTAL_WIDTH.getValueAsInt(), height = ConfigEntry.AUTOSETUP_PORTAL_HEIGHT.getValueAsInt();

			if(width < 4 || height < 5) {
				System.out.println(Main.getConsolePrefix() + "AutoSetup: The size of the portal is too small!");
				break portal;
			}

			new PortalGenerator(world, x, z, width, height);
		}

		if(ConfigEntry.AUTOSETUP_LOBBY_ENABLED.getValueAsBoolean()) {
			System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Loading the lobby...");

			File file = new File(ConfigEntry.AUTOSETUP_LOBBY_SCHEMATIC.getValueAsString());
			Location lobby = new Location(world, x, world.getMaxHeight() - 50, z);
			if(!file.exists())
				new LobbyGenerator(lobby, ConfigEntry.AUTOSETUP_LOBBY_HEIGHT.getValueAsInt(), ConfigEntry.AUTOSETUP_LOBBY_SIZE.getValueAsInt());
			else
				new LobbyGenerator(lobby, file);

			Game.getInstance().setLobby(lobby);
		}

		if(ConfigEntry.AUTOSETUP_BORDER.isIntActivated()) {
			try {
				Method method = world.getClass().getDeclaredMethod("getWorldBorder");

				if(method != null) {
					System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting the border...");
					Object border = method.invoke(world);

					border.getClass().getDeclaredMethod("setCenter", Location.class).invoke(border, middle);
					border.getClass().getDeclaredMethod("setSize", double.class).invoke(border, ConfigEntry.AUTOSETUP_BORDER.getValueAsInt());
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting the spawns...");
		int yPos = world.getMaxHeight();
		while(BlockUtils.isAir(new Location(world, x, yPos, z).getBlock()))
			yPos--;

		middle.getWorld().setSpawnLocation(x, yPos, z);
		new SpawnGenerator(middle, ConfigEntry.AUTOSETUP_SPAWNS_RADIUS.getValueAsInt(), ConfigEntry.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt(), ConfigEntry.AUTOSETUP_SPAWNS_BLOCKID.getValueAsString(), ConfigEntry.AUTOSETUP_SPAWNS_SIDEBLOCKID.getValueAsString());

		if(ConfigEntry.AUTOSETUP_TIME_HOUR.isIntActivated() && ConfigEntry.AUTOSETUP_TIME_MINUTE.isIntActivated()) {
			System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting up AutoStart...");
			Calendar start = new GregorianCalendar();
			start.set(Calendar.HOUR_OF_DAY, ConfigEntry.AUTOSETUP_TIME_HOUR.getValueAsInt());
			start.set(Calendar.MINUTE, ConfigEntry.AUTOSETUP_TIME_MINUTE.getValueAsInt());
			start.set(Calendar.SECOND, 0);
			start.set(Calendar.MILLISECOND, 0);

			if(new GregorianCalendar().after(start))
				start.add(Calendar.DAY_OF_MONTH, 1);

			new AutoStart(start);
		}

		System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Finished!");
	}
}
