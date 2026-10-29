package de.varoplugin.varo.game.world;

import com.cryptomorin.xseries.XMaterial;
import de.varoplugin.cfw.scheduler.Task;
import de.varoplugin.cfw.scheduler.TaskRunnable;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.game.start.AutoStart;
import de.varoplugin.varo.game.world.generators.LobbyGenerator;
import de.varoplugin.varo.game.world.generators.PortalGenerator;
import de.varoplugin.varo.game.world.generators.SpawnGenerator;
import de.varoplugin.varo.spawns.spawn.SpawnChecker;
import de.varoplugin.varo.utils.BlockEditor;
import de.varoplugin.varo.utils.ColumnScan;
import de.varoplugin.varo.utils.VaroUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class AutoSetup {

    private static final Path SCHEMATIC_PATH = Paths.get("plugins/Varo/schematics/");

    private Task task;
    private VaroWorld world;
    private volatile int x, z;
    private Runnable onFinish;
    private volatile boolean searching;

    public AutoSetup(Runnable onFinish) {
        if (Main.getVaroGame().hasStarted())
            return;

        this.onFinish = onFinish;
        world = Main.getVaroGame().getVaroWorldHandler().getMainWorld();
        task = new TaskRunnable() {
            @Override
            public void run() {
                searchTerrain();
            }
        }.runTaskTimer(Main.getInstance(), 1L, 1L);
    }

    private void searchTerrain() {
        // On Folia the result of a probe is only available later
        if (this.searching)
            return;

        Main.getInstance().getLogger().log(Level.INFO, "AutoSetup: " + "Searching for terrain (" + x + ", " + z + ")... (" + world.getWorld().getName() + ")");
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time <= 3000) {
            int amount = ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt();
            CompletableFuture<Boolean> probe = SpawnChecker.checkSpawns(world.getWorld(), x, z, getSpawnRadius(amount), amount);
            if (!probe.isDone()) {
                this.searching = true;
                probe.whenComplete((found, t) -> {
                    try {
                        this.handleProbe(found, t);
                    } finally {
                        this.searching = false;
                    }
                });
                return;
            }

            if (this.handleProbe(probe.getNow(false), null))
                return;
        }
    }

    /**
     * @return Whether the search is finished
     */
    private boolean handleProbe(Boolean found, Throwable throwable) {
        if (throwable != null) {
            task.cancel();
            Main.getInstance().getLogger().log(Level.SEVERE, "AutoSetup: Failed to search for terrain", throwable);
            return true;
        }

        if (found) {
            task.cancel();
            startSetup();
            return true;
        }

        if (!Main.scheduler().isRegionized())
            world.getWorld().getChunkAt(x, z).unload();
        this.x += 100;
        this.z += 100;
        return false;
    }

    private void startSetup() {
        World world = this.world.getWorld();
        Location middle = new Location(world, x, world.getMaxHeight(), z);
        int radius = getSpawnRadius(ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt());

        // The terrain can only be read by the threads owning it on Folia, so all heights are collected first
        List<int[]> columns = ConfigSetting.AUTOSETUP_SPAWNS_FLAT_SURFACE.getValueAsBoolean() ? ColumnScan.disc(x, z, radius) : Collections.singletonList(new int[] { x, z });
        ColumnScan.scan(world, columns, (w, x, z) -> getGroundHeight(w, x, z))
                .thenCompose(ground -> {
                    int groundHeight = ground.get(x, z);
                    return this.setupSpawns(middle, ground, groundHeight, radius)
                            .thenCompose(v -> this.setupPortal(groundHeight))
                            .thenCompose(v -> this.setupLobby(groundHeight));
                })
                .thenRun(() -> Main.scheduler().execute(() -> {
                    setupBorder(middle);
                    setupAutoStart();

                    Main.getInstance().getLogger().log(Level.INFO, "AutoSetup: " + "Finished!");
                    this.onFinish.run();
                }))
                .exceptionally(t -> {
                    Main.getInstance().getLogger().log(Level.SEVERE, "AutoSetup: Failed", t);
                    return null;
                });
    }

    private CompletableFuture<Void> setupPortal(int groundHeight) {
        if (ConfigSetting.AUTOSETUP_PORTAL_ENABLED.getValueAsBoolean()) {
            Main.getInstance().getLogger().log(Level.INFO, "AutoSetup: " + "Setting up the portal...");
            int width = ConfigSetting.AUTOSETUP_PORTAL_WIDTH.getValueAsInt(), height = ConfigSetting.AUTOSETUP_PORTAL_HEIGHT.getValueAsInt();

            if (width < 4 || height < 5) {
                Main.getInstance().getLogger().log(Level.SEVERE, "AutoSetup: The size of the portal is too small!");
                return CompletableFuture.completedFuture(null);
            }

            return new PortalGenerator(world.getWorld(), x, groundHeight, z, width, height).getFuture();
        }
        return CompletableFuture.completedFuture(null);
    }

    private CompletableFuture<Void> setupLobby(int groundHeight) {
        if (ConfigSetting.AUTOSETUP_LOBBY_ENABLED.getValueAsBoolean()) {
            Main.getInstance().getLogger().log(Level.INFO, "AutoSetup: " + "Loading the lobby...");

            Location lobby = new Location(world.getWorld(), x, getLobbyHeight(world.getWorld(), groundHeight), z);

            LobbyGenerator generator;
            boolean schematicEnabled = ConfigSetting.AUTOSETUP_LOBBY_SCHEMATIC_ENABLED.getValueAsBoolean();
            if (schematicEnabled) {
                String schematic = ConfigSetting.AUTOSETUP_LOBBY_SCHEMATIC_FILE.getValueAsString();
                Path path = SCHEMATIC_PATH.resolve(schematic);
                Path normalized = path.normalize();
                if (normalized.startsWith(SCHEMATIC_PATH) && !path.normalize().equals(SCHEMATIC_PATH) && path.toFile().exists()) {
                    generator = new LobbyGenerator(lobby, path.toFile());
                } else {
                    Main.getInstance().getLogger().log(Level.SEVERE, "AutoSetup: Schematic does not exist! Using default lobby generator!");
                    generator = new LobbyGenerator(lobby, ConfigSetting.AUTOSETUP_LOBBY_GENERATED_HEIGHT.getValueAsInt(), ConfigSetting.AUTOSETUP_LOBBY_GENERATED_SIZE.getValueAsInt());
                }
            } else {
                generator = new LobbyGenerator(lobby, ConfigSetting.AUTOSETUP_LOBBY_GENERATED_HEIGHT.getValueAsInt(), ConfigSetting.AUTOSETUP_LOBBY_GENERATED_SIZE.getValueAsInt());
            }

            Main.getVaroGame().setLobby(lobby);
            return generator.getFuture();
        }
        return CompletableFuture.completedFuture(null);
    }

    private void setupBorder(Location middle) {
        if (ConfigSetting.AUTOSETUP_BORDER.isIntActivated()) {
            world.getVaroBorder().setCenter(middle);
            Main.getVaroGame().getVaroWorldHandler().setBorderSize(ConfigSetting.AUTOSETUP_BORDER.getValueAsInt(), 0, null);
        }
    }

    private CompletableFuture<Void> setupSpawns(Location middle, ColumnScan ground, int groundHeight, int radius) {
        Main.getInstance().getLogger().log(Level.INFO, "AutoSetup: Setting the spawns...");

        CompletableFuture<Void> flatSurface = ConfigSetting.AUTOSETUP_SPAWNS_FLAT_SURFACE.getValueAsBoolean() ? this.generateFlatSurface(middle.getWorld(), ground, radius) : CompletableFuture.completedFuture(null);

        int y = groundHeight;
        if (ConfigSetting.AUTOSETUP_PORTAL_ENABLED.getValueAsBoolean())
            y += ConfigSetting.AUTOSETUP_PORTAL_HEIGHT.getValueAsInt();
        final int spawnY = y;
        Main.scheduler().execute(() -> {
            middle.getWorld().setSpawnLocation(this.x, spawnY, this.z);
            Main.getVaroGame().getVaroWorldHandler().refreshWorldSpawn(middle.getWorld());
        });

        return flatSurface.thenCompose(v -> new SpawnGenerator(middle, radius, ConfigSetting.AUTOSETUP_SPAWNS_AMOUNT.getValueAsInt(),
                (XMaterial) ConfigSetting.AUTOSETUP_SPAWNS_BLOCKID.getValueAsEnum(), (XMaterial) ConfigSetting.AUTOSETUP_SPAWNS_SIDEBLOCKID.getValueAsEnum()).getFuture());
    }

    private CompletableFuture<Void> generateFlatSurface(World world, ColumnScan ground, int radius) {
        int flatHeight = ground.get(x, z);

        for (int[] column : ColumnScan.disc(x, z, radius))
            flatHeight = Math.min(flatHeight, ground.get(column[0], column[1]));

        BlockEditor editor = new BlockEditor(world);
        for (int[] column : ColumnScan.disc(x, z, radius))
            editor.set(column[0], flatHeight, column[1], XMaterial.GRASS_BLOCK);
        return editor.apply();
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

    private int getLobbyHeight(World world, int groundHeight) {
        LobbySnap snapSetting = (LobbySnap) ConfigSetting.AUTOSETUP_LOBBY_SNAP_TYPE.getValueAsEnum();

        switch (snapSetting) {
            case GROUND:
                return groundHeight + ConfigSetting.AUTOSETUP_LOBBY_SNAP_GROUND_OFFSET.getValueAsInt();
            case ABSOLUTE:
                return ConfigSetting.AUTOSETUP_LOBBY_SNAP_ABSOLUTE_YPOS.getValueAsInt();
            case MAX_HEIGHT:
                return world.getMaxHeight() - ConfigSetting.AUTOSETUP_LOBBY_SNAP_MAX_HEIGHT_OFFSET.getValueAsInt();
            default:
                throw new UnsupportedOperationException("This LobbySnap value is currently not implemented!");
        }
    }

    private static int getGroundHeight(World world, int x, int z) {
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
