package de.varoplugin.varo.game;

import java.util.UUID;

public abstract class UniqueGameObject implements VaroObject {

    private final UUID uuid;
    private Varo varo;

    public UniqueGameObject(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void initialize(Varo varo) {
        this.varo = varo;
    }

    @Override
    public Varo getVaro() {
        return this.varo;
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(this.getClass())) return false;
        return ((UniqueGameObject) o).getUuid().equals(this.uuid);
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }
}