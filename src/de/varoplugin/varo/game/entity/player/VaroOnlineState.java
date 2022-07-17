package de.varoplugin.varo.game.entity.player;

import java.util.Arrays;

public enum VaroOnlineState implements OnlineState {

    ONLINE(true),
    OFFLINE(false);

    private final boolean online;
    VaroOnlineState(boolean online) {
        this.online = online;
    }

    @Override
    public boolean asBoolean() {
        return this.online;
    }

    public static OnlineState parse(boolean online) {
        return Arrays.stream(values()).filter(s -> s.asBoolean() == online).findAny().orElse(null);
    }
}
