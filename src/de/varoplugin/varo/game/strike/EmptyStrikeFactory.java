package de.varoplugin.varo.game.strike;

import java.util.Objects;
import java.util.UUID;

public class EmptyStrikeFactory implements StrikeFactory {

    private UUID uuid;
    private StrikeType type;

    @Override
    public StrikeFactory uuid(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid);
        return this;
    }

    @Override
    public StrikeFactory type(StrikeType type) {
        this.type = Objects.requireNonNull(type);
        return this;
    }

    @Override
    public Strike create() {
        if (this.type == null) throw new IllegalArgumentException("No StrikeType provided");
        return new StrikeImpl(this.uuid == null ? UUID.randomUUID() : this.uuid, this.type);
    }
}
