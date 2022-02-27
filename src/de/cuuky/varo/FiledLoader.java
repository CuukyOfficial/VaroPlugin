package de.cuuky.varo;

import de.cuuky.cfw.configuration.BetterYamlConfiguration;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FiledLoader<T> extends BetterYamlConfiguration {

    private final static String DATA_LOCATION = "data";

    private final Supplier<T> getter;
    private final Consumer<T> loader;

    FiledLoader(String name, Supplier<T> getter, Consumer<T> loader) {
        super(name);

        this.getter = getter;
        this.loader = loader;
        this.loader.accept(this.getValue());
    }

    @SuppressWarnings("unchecked")
    private T getValue() {
        return (T) this.get(DATA_LOCATION);
    }

    @Override
    public void save() {
        this.set(DATA_LOCATION, this.getter.get());
        super.save();
    }
}