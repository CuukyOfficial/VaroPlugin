package de.cuuky.varo.event;

import org.bukkit.Material;

import java.util.function.Function;
import java.util.function.Supplier;

// TODO: Make this access the config and load the players values by a given event name
public class EventBuilder implements EventInformationHolder, Supplier<VaroEvent> {

    private final Function<EventInformationHolder, VaroEvent> creator;
    protected String name;
    protected String displayName;
    protected Material icon;
    protected String description;

    public EventBuilder(Function<EventInformationHolder, VaroEvent> creator) {
        this.creator = creator;
    }

    public EventBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EventBuilder displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public EventBuilder icon(Material icon) {
        this.icon = icon;
        return this;
    }

    public EventBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public Material getIcon() {
        return this.icon;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public VaroEvent get() {
        return this.creator.apply(this);
    }
}