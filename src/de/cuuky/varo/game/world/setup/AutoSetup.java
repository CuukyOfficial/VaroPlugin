package de.cuuky.varo.game.world.setup;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

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
                searchTerrain();
            }
        }.runTaskTimer(Main.getInstance(), 1L, 1L);
    }

    private void searchTerrain() {
        System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Searching for terrain (" + x + ", " + z + ")... (" + world.getWorld().getName() + ")");
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time <= 3000) {
            if (SpawnChecker.checkSpawns(world.getWorld(), x, z, ConfigSetting.AUTOSETUP_SPAWNS_RADIUS.getValueAsInt(), ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt())) {
                task.cancel();
                startSetup();
                return;
            } else {
                world.getWorld().getChunkAt(x, z).unload();
                this.x += 100;
                this.z += 100;
            }
        }
    }

    private void startSetup() {
        Location middle = new Location(world.getWorld(), x, world.getWorld().getMaxHeight(), z);

        setupPortal();
        setupLobby();
        setupBorder(middle);
        setupSpawns(middle);

        System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Finished!");
        this.onFinish.run();
    }

    private void setupPortal() {
        if (ConfigSetting.AUTOSETUP_PORTAL_ENABLED.getValueAsBoolean()) {
            System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting up the portal...");
            int width = ConfigSetting.AUTOSETUP_PORTAL_WIDTH.getValueAsInt(), height = ConfigSetting.AUTOSETUP_PORTAL_HEIGHT.getValueAsInt();

            if (width < 4 || height < 5) {
                System.out.println(Main.getConsolePrefix() + "AutoSetup: The size of the portal is too small!");
                return;
            }

            new PortalGenerator(world.getWorld(), x, z, width, height);
        }
    }

    private void setupLobby() {
        if (ConfigSetting.AUTOSETUP_LOBBY_ENABLED.getValueAsBoolean()) {
            System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Loading the lobby...");

            File file = new File(ConfigSetting.AUTOSETUP_LOBBY_SCHEMATIC.getValueAsString());
            Location lobby = getLobbyLocation(world.getWorld(), x, z);
            if (!file.exists())
                new LobbyGenerator(lobby, ConfigSetting.AUTOSETUP_LOBBY_GEN_HEIGHT.getValueAsInt(), ConfigSetting.AUTOSETUP_LOBBY_GEN_SIZE.getValueAsInt());
            else
                new LobbyGenerator(lobby, file);

            Main.getVaroGame().setLobby(lobby);
        }
    }

    private void setupBorder(Location middle) {
        if (ConfigSetting.AUTOSETUP_BORDER.isIntActivated() && VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) {
            world.getVaroBorder().setBorderCenter(middle);
            Main.getVaroGame().getVaroWorldHandler().setBorderSize(ConfigSetting.AUTOSETUP_BORDER.getValueAsInt(), 0, null);
        }
    }

    private void setupSpawns(Location middle) {
        System.out.println(Main.getConsolePrefix() + "AutoSetup: " + "Setting the spawns...");
        int yPos = getGroundHeight(world.getWorld(), x, z);

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
    }

    private Location getLobbyLocation(World world, long x, long z) {
        return new Location(world, x, getLobbyHeight(world, x, z), z);
    }

    private int getLobbyHeight(World world, long x, long z) {
        int heightSetting = ConfigSetting.AUTOSETUP_LOBBY_SNAP_OFFSET.getValueAsInt();
        LobbySnap snapSetting = LobbySnap.valueOf(ConfigSetting.AUTOSETUP_LOBBY_SNAP.getValueAsString());

        switch (snapSetting) {
            case GROUND:
                return getGroundHeight(world, x, z) + heightSetting;
            case ABSOLUTE:
                return heightSetting;
            default:
            case MAX_HEIGHT:
                return world.getMaxHeight() - heightSetting;
        }
    }

    private int getGroundHeight(World world, long x, long z) {
        int groundHeight = world.getMaxHeight();

        while (BlockUtils.isAir(new Location(world, x, groundHeight, z).getBlock()) && groundHeight > 0) {
            groundHeight--;
        }

        return groundHeight;
    }

    public enum LobbySnap {
        ABSOLUTE,
        GROUND,
        MAX_HEIGHT
    }

}
