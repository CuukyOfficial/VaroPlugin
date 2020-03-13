package de.cuuky.varo.game.world;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bukkit.Location;
import org.bukkit.World;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.game.world.generators.LobbyGenerator;
import de.cuuky.varo.game.world.generators.PortalGenerator;
import de.cuuky.varo.game.world.generators.SpawnGenerator;
import de.cuuky.varo.spawns.spawn.SpawnChecker;
import de.cuuky.varo.utils.BlockUtils;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public class AutoSetup {

	public AutoSetup() {
		if(Main.getVaroGame().hasStarted())
			return;

		setupPlugin();
	}
	
	private void setupPlugin() {
		World world = Main.getVaroGame().getVaroWorld().getWorld();

		System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Searching for terrain now...");

		int x = 0, z = 0;
		while(!SpawnChecker.checkSpawns(world, x, z, ConfigSetting.AUTOSETUP_SPAWNS_RADIUS.getValueAsInt(), ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt())) {
			x += 100;
			z += 100;
		}

		Location middle = new Location(world, x, world.getMaxHeight(), z);

		portal: if(ConfigSetting.AUTOSETUP_PORTAL_ENABLED.getValueAsBoolean()) {
			System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting up the portal...");
			int width = ConfigSetting.AUTOSETUP_PORTAL_WIDTH.getValueAsInt(), height = ConfigSetting.AUTOSETUP_PORTAL_HEIGHT.getValueAsInt();

			if(width < 4 || height < 5) {
				System.out.println(Main.getConsolePrefix() + "AutoSetup: The size of the portal is too small!");
				break portal;
			}

			new PortalGenerator(world, x, z, width, height);
		}

		if(ConfigSetting.AUTOSETUP_LOBBY_ENABLED.getValueAsBoolean()) {
			System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Loading the lobby...");

			File file = new File(ConfigSetting.AUTOSETUP_LOBBY_SCHEMATIC.getValueAsString());
			Location lobby = new Location(world, x, world.getMaxHeight() - 50, z);
			if(!file.exists())
				new LobbyGenerator(lobby, ConfigSetting.AUTOSETUP_LOBBY_HEIGHT.getValueAsInt(), ConfigSetting.AUTOSETUP_LOBBY_SIZE.getValueAsInt());
			else
				new LobbyGenerator(lobby, file);

			Main.getVaroGame().setLobby(lobby);
		}

		if(ConfigSetting.AUTOSETUP_BORDER.isIntActivated() && VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) {
			try {
				Method method = world.getClass().getDeclaredMethod("getWorldBorder");

				if(method != null) {
					System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting the border...");
					Object border = method.invoke(world);

					border.getClass().getDeclaredMethod("setCenter", Location.class).invoke(border, middle);
					border.getClass().getDeclaredMethod("setSize", double.class).invoke(border, ConfigSetting.AUTOSETUP_BORDER.getValueAsInt());
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
		new SpawnGenerator(middle, ConfigSetting.AUTOSETUP_SPAWNS_RADIUS.getValueAsInt(), ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt(), ConfigSetting.AUTOSETUP_SPAWNS_BLOCKID.getValueAsString(), ConfigSetting.AUTOSETUP_SPAWNS_SIDEBLOCKID.getValueAsString());

		if(ConfigSetting.AUTOSETUP_TIME_HOUR.isIntActivated() && ConfigSetting.AUTOSETUP_TIME_MINUTE.isIntActivated()) {
			System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting up AutoStart...");
			Calendar start = new GregorianCalendar();
			start.set(Calendar.HOUR_OF_DAY, ConfigSetting.AUTOSETUP_TIME_HOUR.getValueAsInt());
			start.set(Calendar.MINUTE, ConfigSetting.AUTOSETUP_TIME_MINUTE.getValueAsInt());
			start.set(Calendar.SECOND, 0);
			start.set(Calendar.MILLISECOND, 0);

			if(new GregorianCalendar().after(start))
				start.add(Calendar.DAY_OF_MONTH, 1);

			new AutoStart(start);
		}

		System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Finished!");
	}
}