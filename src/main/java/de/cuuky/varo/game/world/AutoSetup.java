package de.cuuky.varo.game.world;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.game.world.generators.LobbyGenerator;
import de.cuuky.varo.game.world.generators.PortalGenerator;
import de.cuuky.varo.game.world.generators.SpawnGenerator;
import de.cuuky.varo.spawns.spawn.SpawnChecker;
import de.cuuky.varo.utils.VaroUtils;

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
        Main.getInstance().getLogger().log(Level.INFO, "AutoSetup: " + "Searching for terrain (" + x + ", " + z + ")... (" + world.getWorld().getName() + ")");
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time <= 3000) {
            if (SpawnChecker.checkSpawns(world.getWorld(), x, z, getSpawnRadius(ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt()), ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt())) {
                task.cancel();
                startSetup();
                return;
            }
            world.getWorld().getChunkAt(x, z).unload();
            this.x += 100;
            this.z += 100;
        }
    }

    private void startSetup() {
        Location middle = new Location(world.getWorld(), x, world.getWorld().getMaxHeight(), z);

        setupSpawns(middle);
        setupPortal();
        setupLobby();
        setupBorder(middle);
        setupAutoStart();

        Main.getInstance().getLogger().log(Level.INFO, "AutoSetup: " + "Finished!");
        this.onFinish.run();
    }

    private void setupPortal() {
        if (ConfigSetting.AUTOSETUP_PORTAL_ENABLED.getValueAsBoolean()) {
            Main.getInstance().getLogger().log(Level.INFO, "AutoSetup: " + "Setting up the portal...");
            int width = ConfigSetting.AUTOSETUP_PORTAL_WIDTH.getValueAsInt(), height = ConfigSetting.AUTOSETUP_PORTAL_HEIGHT.getValueAsInt();

            if (width < 4 || height < 5) {
                Main.getInstance().getLogger().log(Level.SEVERE, "AutoSetup: The size of the portal is too small!");
                return;
            }

            new PortalGenerator(world.getWorld(), x, z, width, height);
        }
    }

    private void setupLobby() {
        if (ConfigSetting.AUTOSETUP_LOBBY_ENABLED.getValueAsBoolean()) {
            Main.getInstance().getLogger().log(Level.INFO, "AutoSetup: " + "Loading the lobby...");

            Location lobby = getLobbyLocation(world.getWorld(), x, z);

            File schematicFile = new File(ConfigSetting.AUTOSETUP_LOBBY_SCHEMATIC_FILE.getValueAsString());
            boolean schematicEnabled = ConfigSetting.AUTOSETUP_LOBBY_SCHEMATIC_ENABLED.getValueAsBoolean();
            if (schematicEnabled && schematicFile.exists()) {
                new LobbyGenerator(lobby, schematicFile);
            } else {
                if (schematicEnabled) Main.getInstance().getLogger().log(Level.SEVERE, "AutoSetup: Schematic does not exist! Using default lobby generator!");
                new LobbyGenerator(lobby, ConfigSetting.AUTOSETUP_LOBBY_GENERATED_HEIGHT.getValueAsInt(), ConfigSetting.AUTOSETUP_LOBBY_GENERATED_SIZE.getValueAsInt());
            }

            Main.getVaroGame().setLobby(lobby);
        }
    }

    private void setupBorder(Location middle) {
        if (ConfigSetting.AUTOSETUP_BORDER.isIntActivated()) {
            world.getVaroBorder().setCenter(middle);
            Main.getVaroGame().getVaroWorldHandler().setBorderSize(ConfigSetting.AUTOSETUP_BORDER.getValueAsInt(), 0, null);
        }
    }

    private void setupSpawns(Location middle) {
        Main.getInstance().getLogger().log(Level.INFO, "AutoSetup: Setting the spawns...");

        int y = this.getGroundHeight(middle.getWorld(), this.x, this.z);
        if (ConfigSetting.AUTOSETUP_PORTAL_ENABLED.getValueAsBoolean())
            y += ConfigSetting.AUTOSETUP_PORTAL_HEIGHT.getValueAsInt();
        middle.getWorld().setSpawnLocation(this.x, y, this.z);

        new SpawnGenerator(middle, getSpawnRadius(ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt()),ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt(),
                (XMaterial) ConfigSetting.AUTOSETUP_SPAWNS_BLOCKID.getValueAsEnum(), (XMaterial) ConfigSetting.AUTOSETUP_SPAWNS_SIDEBLOCKID.getValueAsEnum());
    }

    private void setupAutoStart() {
        if (ConfigSetting.AUTOSETUP_TIME_HOUR.isIntActivated() && ConfigSetting.AUTOSETUP_TIME_MINUTE.isIntActivated()) {
            Main.getInstance().getLogger().log(Level.INFO, "AutoSetup: " + "Setting up AutoStart...");
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

    private Location getLobbyLocation(World world, int x, int z) {
        return new Location(world, x, getLobbyHeight(world, x, z), z);
    }

    private int getLobbyHeight(World world, int x, int z) {
        LobbySnap snapSetting = (LobbySnap) ConfigSetting.AUTOSETUP_LOBBY_SNAP_TYPE.getValueAsEnum();

        switch (snapSetting) {
            case GROUND:
                return getGroundHeight(world, x, z) + ConfigSetting.AUTOSETUP_LOBBY_SNAP_GROUND_OFFSET.getValueAsInt();
            case ABSOLUTE:
                return ConfigSetting.AUTOSETUP_LOBBY_SNAP_ABSOLUTE_YPOS.getValueAsInt();
            case MAX_HEIGHT:
                return world.getMaxHeight() - ConfigSetting.AUTOSETUP_LOBBY_SNAP_MAX_HEIGHT_OFFSET.getValueAsInt();
            default:
                throw new UnsupportedOperationException("This LobbySnap value is currently not implemented!");
        }
    }

    private int getGroundHeight(World world, int x, int z) {
        int groundHeight = world.getMaxHeight();

        while (VaroUtils.isNotSolidTerrainOrLiquid(new Location(world, x, groundHeight, z).getBlock()) && groundHeight > 0) {
            groundHeight--;
        }

        return groundHeight;
    }

    public enum LobbySnap {
        ABSOLUTE,
        GROUND,
        MAX_HEIGHT
    }

    public static int getSpawnRadius(int amount) {
        return ConfigSetting.AUTOSETUP_SPAWNS_RADIUS.getValueAsInt() == -1 ? Math.max(10, (int) (amount * 0.85)) : ConfigSetting.AUTOSETUP_SPAWNS_RADIUS.getValueAsInt();
    }
}
