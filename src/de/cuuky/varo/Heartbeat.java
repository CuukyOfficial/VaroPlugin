package de.cuuky.varo;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.exceptions.NoSuchPlayerException;
import de.cuuky.varo.heartbeat.PlayerListener;
import de.cuuky.varo.heartbeat.PlayerListenerBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

// Implements Listener itself?
public abstract class Heartbeat extends BukkitRunnable implements Listener {

    private static final int SCHEDULE = 20;

    protected Varo varo;
    private final List<Listener> listener;
    private final List<PlayerListener> playerListener;
    private final List<Function<VaroPlayer, PlayerListener>> playerListenerCreator;

    public Heartbeat(Varo varo) {
        this.varo = varo;
        this.listener = new LinkedList<>();
        this.playerListener = new LinkedList<>();
        this.playerListenerCreator = new LinkedList<>();
        this.runTaskTimer(varo.getPlugin(), SCHEDULE, SCHEDULE);
        this.addListener(this);
    }

    private void unregister(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    private void register(Listener listener) {
        this.varo.getPlugin().getServer().getPluginManager().registerEvents(listener, this.varo.getPlugin());
    }

    private void addListener(VaroPlayer player) {
        this.playerListenerCreator.stream().map(l -> l.apply(player)).forEach(this.playerListener::add);
    }

    protected boolean removeListener(VaroPlayer player) {
        this.playerListener.stream().filter(pl -> pl.isPlayer(player)).forEach(this::unregister);
        return this.playerListener.removeIf(pl -> pl.isPlayer(player));
    }

    protected void addListener(Listener listener) {
        this.listener.add(listener);
        this.register(listener);
    }

    protected boolean addPlayerListener(PlayerListenerBuilder builder) {
        return this.playerListenerCreator.add(builder);
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        this.listener.forEach(this::unregister);
        this.playerListener.forEach(this::unregister);
        super.cancel();
    }

    public abstract void heartbeat(VaroPlayer player);

    @Override
    public void run() {
        this.varo.getOnlinePlayers().forEach(this::heartbeat);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.addListener(this.varo.getPlayer(event.getPlayer()).orElseThrow(NoSuchPlayerException::new));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.removeListener(this.varo.getPlayer(event.getPlayer()).orElseThrow(NoSuchPlayerException::new));
    }
}