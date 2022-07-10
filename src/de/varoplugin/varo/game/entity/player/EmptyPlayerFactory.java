package de.varoplugin.varo.game.entity.player;

import de.varoplugin.cfw.utils.UUIDUtils;

import java.util.Objects;
import java.util.UUID;

public class EmptyPlayerFactory implements PlayerFactory {

    private UUID uuid;
    private String name;
    private org.bukkit.entity.Player player;
    private ParticipantState state = VaroParticipantState.ALIVE;
    private PlayerMode mode = VaroPlayerMode.NONE;

    @Override
    public PlayerFactory uuid(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid);
        return this;
    }

    @Override
    public PlayerFactory name(String name) {
        this.name = Objects.requireNonNull(name);
        return this;
    }

    @Override
    public PlayerFactory player(org.bukkit.entity.Player player) {
        this.player = Objects.requireNonNull(player);
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        return this;
    }

    @Override
    public PlayerFactory state(ParticipantState state) {
        this.state = Objects.requireNonNull(state);
        return this;
    }

    @Override
    public PlayerFactory mode(PlayerMode mode) {
        this.mode = Objects.requireNonNull(mode);
        return this;
    }

    @Override
    public PlayerFactory parseMissing() throws ParseException {
        // Who tf just thought it is good API design to just throw Exception :/
        try {
            if (this.uuid == null && this.name != null) this.uuid = UUIDUtils.getUUID(this.name);
        } catch (Exception e) {
            throw new ParseException("Invalid name", e);
        }

        try {
            if (this.uuid != null && this.name == null) this.name = UUIDUtils.getName(this.uuid);
        } catch (Exception e) {
            throw new ParseException("Invalid uuid", e);
        }
        return this;
    }

    @Override
    public Player create() {
        if (this.uuid == null || this.name == null) throw new IllegalArgumentException("No name or uuid provided");
        return new PlayerImpl(this.uuid, this.name, this.player, this.state, this.mode);
    }
}
