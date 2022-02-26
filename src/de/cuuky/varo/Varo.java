package de.cuuky.varo;

import de.cuuky.varo.event.IVaroEvent;
import de.cuuky.varo.event.VaroEventType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Varo {

    private final JavaPlugin plugin;
    private final List<IVaroEvent> events;

    Varo(JavaPlugin plugin) {
        this.plugin = plugin;
        this.events = new LinkedList<>();
    }

    public IVaroEvent createEvent(VaroEventType type) {
        IVaroEvent built = type.build(this);
        this.events.add(built);
        return built;
    }

    public boolean removeEvent(IVaroEvent event) {
        return this.events.remove(event);
    }

    public Stream<IVaroEvent> getEvents(VaroEventType type) {
        return this.events.stream().filter(e -> e.getType() == type);
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }
}