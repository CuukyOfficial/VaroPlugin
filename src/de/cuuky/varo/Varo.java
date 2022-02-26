package de.cuuky.varo;

import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.report.Report;
import de.cuuky.varo.spawns.Spawn;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Predicate;

// TODO: Verify if a collection of a collection should be used here
public class Varo {

    private final JavaPlugin plugin;
    private final Collection<Collection<? extends VaroElement>> elementLists;

    private final Collection<VaroPlayer> players;
    private final Collection<VaroTeam> teams;
    private final Collection<Alert> alerts;
    private final Collection<Report> reports;
    private final Collection<VaroEvent> events;
    private final Collection<Spawn> spawns;

    Varo(JavaPlugin plugin) {
        this.plugin = plugin;
        this.elementLists = new HashSet<>();
        this.players = this.newElementCollection();
        this.teams = this.newElementCollection();
        this.alerts = this.newElementCollection();
        this.reports = this.newElementCollection();
        this.events = this.newElementCollection();
        this.spawns = this.newElementCollection();
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

//    public void registerEvent(String name, Supplier<VaroEvent> builder) {
//        this.eventBuilder.put(name, builder);
//    }
//
//    public VaroEvent createEvent(String name) {
//        VaroEvent built = type.build(this);
//        this.events.add(built);
//        return built;
//    }
//
//    public boolean removeEvent(VaroEvent event) {
//        return this.events.remove(event);
//    }
//
//    public Stream<VaroEvent> getEvents(VaroEventType type) {
//        return this.events.stream().filter(e -> e.getType() == type);
//    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }
}