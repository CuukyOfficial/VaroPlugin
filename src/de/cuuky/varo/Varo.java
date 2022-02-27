package de.cuuky.varo;

import de.cuuky.cfw.CuukyFrameWork;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.event.EventProvider;
import de.cuuky.varo.event.VaroEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Stream;

// TODO: Verify if a collection of a collection should be used here
public class Varo {

    private final JavaPlugin plugin;
    private final CuukyFrameWork cuukyFrameWork;

    private final Map<String, EventProvider> eventTypes;

    private final Map<UUID, VaroPlayer> players;
    private final Collection<VaroTeam> teams;
    private final Collection<Alert> alerts;
    private final Collection<Report> reports;
    private final Collection<VaroEvent> events;
    private final Collection<Spawn> spawns;

    private GameState state;

    public Varo(JavaPlugin plugin) {
        this.plugin = plugin;
        this.cuukyFrameWork = new CuukyFrameWork(plugin);
        this.eventTypes = new HashMap<>();

        this.players = new ConcurrentHashMap<>();
        this.teams = new IndexedList<>();
        this.alerts = new IndexedList<>();
        this.reports = new IndexedList<>();
        this.events = new IndexedList<>();
        this.spawns = new IndexedList<>();

        VaroEvent.DEFAULT_EVENTS.forEach(b -> this.registerEvent(b.getName(), b));

        this.state = VaroGameState.LOBBY;
    }

    private <T extends VaroElement> Optional<T> getElement(Collection<T> in, Predicate<T> test) {
        return in.stream().filter(test).findAny();
    }

    private <T extends VaroElement> T getElement(Collection<T> in, int id) {
        return this.getElement(in, element -> element.getId() == id).orElse(null);
    }

    private Stream<VaroPlayer> getPlayers(Predicate<VaroPlayer> filter) {
        return this.getPlayers().filter(filter);
    }

    public VaroPlayer getPlayer(String name) {
        return this.players.values().stream().filter(p -> p.getName().equals(name)).findAny().orElse(null);
    }

    public VaroPlayer getPlayer(UUID uuid) {
        return this.players.get(uuid);
    }

    public VaroPlayer getPlayer(Player player) {
        return this.getPlayer(player.getUniqueId());
    }

    public Stream<VaroPlayer> getPlayers() {
        return this.players.values().stream();
    }

    public Stream<VaroPlayer> getOnlinePlayers() {
        return this.getPlayers(VaroPlayer::isOnline);
    }

    public VaroTeam getTeam(int id) {
        return this.getElement(this.teams, id);
    }

    public VaroEvent getEvent(int id) {
        return this.getElement(this.events, id);
    }

    public Supplier<VaroEvent> registerEvent(String name, EventProvider builder) {
        return this.eventTypes.put(name, builder);
    }

    public VaroEvent createEvent(String name) {
        if (!this.eventTypes.containsKey(name))
            return null;

        VaroEvent built = this.eventTypes.get(name).get();
        this.events.add(built);
        return built;
    }

    public Stream<VaroEvent> getEvents(String name) {
        return this.events.stream().filter(e -> e.getName().equals(name));
    }

    public CuukyFrameWork getCuukyFrameWork() {
        return this.cuukyFrameWork;
    }

    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }
}