package de.cuuky.varo;

import de.cuuky.cfw.CuukyFrameWork;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.event.VaroEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

// TODO: Verify if a collection of a collection should be used here
public class Varo {

    private final JavaPlugin plugin;
    private final Collection<Collection<? extends VaroElement>> elementLists;

    private final CuukyFrameWork cuukyFrameWork;

    private final Map<String, Supplier<VaroEvent>> eventTypes;

    private final Collection<VaroPlayer> players;
    private final Collection<VaroTeam> teams;
    private final Collection<Alert> alerts;
    private final Collection<Report> reports;
    private final Collection<VaroEvent> events;
    private final Collection<Spawn> spawns;

    public Varo(JavaPlugin plugin) {
        this.plugin = plugin;
        this.cuukyFrameWork = new CuukyFrameWork(plugin);
        this.eventTypes = new HashMap<>();
        this.elementLists = new HashSet<>();

        this.players = this.newElementCollection();
        this.teams = this.newElementCollection();
        this.alerts = this.newElementCollection();
        this.reports = this.newElementCollection();
        this.events = this.newElementCollection();
        this.spawns = this.newElementCollection();

        VaroEvent.DEFAULT_EVENTS.forEach(b -> this.registerEvent(b.getName(), b));
    }

    private <T extends VaroElement> Collection<T> newElementCollection() {
        Collection<T> collection = new VaroElementList<>();
        this.elementLists.add(collection);
        return collection;
    }

    public boolean removeElement(VaroElement element) {
        return this.elementLists.stream().anyMatch(elements -> elements.remove(element));
    }

    private <T extends VaroElement> Optional<T> getElement(Collection<T> in, Predicate<T> test) {
        return in.stream().filter(test).findAny();
    }

    private <T extends VaroElement> Optional<T> getElement(Collection<T> in, int id) {
        return this.getElement(in, element -> element.getId() == id);
    }

    public Optional<VaroPlayer> getPlayer(int id) {
        return this.getElement(this.players, id);
    }

    public Optional<VaroTeam> getTeam(int id) {
        return this.getElement(this.teams, id);
    }

    public Optional<VaroEvent> getEvent(int id) {
        return this.getElement(this.events, id);
    }

    public Supplier<VaroEvent> registerEvent(String name, Supplier<VaroEvent> builder) {
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

    public JavaPlugin getPlugin() {
        return this.plugin;
    }
}