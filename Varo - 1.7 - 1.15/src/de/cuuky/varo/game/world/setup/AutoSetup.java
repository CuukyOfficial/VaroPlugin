package de.cuuky.varo.game.world.setup;

import de.cuuky.cfw.utils.BlockUtils;
import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.game.world.VaroWorld;
import de.cuuky.varo.game.world.generators.LobbyGenerator;
import de.cuuky.varo.game.world.generators.PortalGenerator;
import de.cuuky.varo.game.world.generators.SpawnGenerator;
import de.cuuky.varo.spawns.spawn.SpawnChecker;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AutoSetup {

    private BukkitTask task;
    private VaroWorld world;
    private int x, z;
    private Runnable onFinish;

    public AutoSetup(Runnable onFinish) {
        if (Main.getVaroGame().hasStarted())
            return;

        this.onFinish = onFinish;
        world = Main.getVaroGame().getVaroWorldHandler().getMainWorld();
        task = new BukkitRunnable() {
            @Override
            public void run() {
                setupPlugin();
            }
        }.runTaskTimer(Main.getInstance(), 1L, 1L);
    }

    private void setupPlugin() {
        System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Searching for terrain (" + x + ", " + z + ")... (" + world.getWorld().getName() + ")");
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time <= 3000) {
            if (SpawnChecker.checkSpawns(world.getWorld(), x, z, ConfigSetting.AUTOSETUP_SPAWNS_RADIUS.getValueAsInt(), ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt())) {
                task.cancel();
                this.setupSpawn(world);
                return;
            } else {
                world.getWorld().getChunkAt(x, z).unload();
                this.x += 100;
                this.z += 100;
            }
        }
    }

    private void setupSpawn(VaroWorld world) {
        Location middle = new Location(world.getWorld(), x, world.getWorld().getMaxHeight(), z);

        portal:
        if (ConfigSetting.AUTOSETUP_PORTAL_ENABLED.getValueAsBoolean()) {
            System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting up the portal...");
            int width = ConfigSetting.AUTOSETUP_PORTAL_WIDTH.getValueAsInt(), height = ConfigSetting.AUTOSETUP_PORTAL_HEIGHT.getValueAsInt();

            if (width < 4 || height < 5) {
                System.out.println(Main.getConsolePrefix() + "AutoSetup: The size of the portal is too small!");
                break portal;
            }

            new PortalGenerator(world.getWorld(), x, z, width, height);
        }

        if (ConfigSetting.AUTOSETUP_LOBBY_ENABLED.getValueAsBoolean()) {
            System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Loading the lobby...");

            File file = new File(ConfigSetting.AUTOSETUP_LOBBY_SCHEMATIC.getValueAsString());
            Location lobby = new Location(world.getWorld(), x, world.getWorld().getMaxHeight() - 50, z);
            if (!file.exists())
                new LobbyGenerator(lobby, ConfigSetting.AUTOSETUP_LOBBY_HEIGHT.getValueAsInt(), ConfigSetting.AUTOSETUP_LOBBY_SIZE.getValueAsInt());
            else
                new LobbyGenerator(lobby, file);

            Main.getVaroGame().setLobby(lobby);
        }

        if (ConfigSetting.AUTOSETUP_BORDER.isIntActivated() && VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) {
            Main.getVaroGame().getVaroWorldHandler().setBorderSize(ConfigSetting.AUTOSETUP_BORDER.getValueAsInt(), 0, null);
            world.getVaroBorder().setBorderCenter(middle);
        }

        System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting the spawns...");
        int yPos = world.getWorld().getMaxHeight();
        while (BlockUtils.isAir(new Location(world.getWorld(), x, yPos, z).getBlock()))
            yPos--;

        middle.getWorld().setSpawnLocation(x, yPos, z);
        new SpawnGenerator(middle, ConfigSetting.AUTOSETUP_SPAWNS_RADIUS.getValueAsInt(), ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt(), ConfigSetting.AUTOSETUP_SPAWNS_BLOCKID.getValueAsString(), ConfigSetting.AUTOSETUP_SPAWNS_SIDEBLOCKID.getValueAsString());

        if (ConfigSetting.AUTOSETUP_TIME_HOUR.isIntActivated() && ConfigSetting.AUTOSETUP_TIME_MINUTE.isIntActivated()) {
            System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting up AutoStart...");
            Calendar start = new GregorianCalendar();
            start.set(Calendar.HOUR_OF_DAY, ConfigSetting.AUTOSETUP_TIME_HOUR.getValueAsInt());
            start.set(Calendar.MINUTE, ConfigSetting.AUTOSETUP_TIME_MINUTE.getValueAsInt());
            start.set(Calendar.SECOND, 0);
            start.set(Calendar.MILLISECOND, 0);

            if (new GregorianCalendar().after(start))
                start.add(Calendar.DAY_OF_MONTH, 1);

            new AutoStart(start);
        }

        System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Finished!");
        this.onFinish.run();
    }
}