package de.cuuky.varo.entity.player.stats;

import java.util.function.Consumer;
import java.util.function.Function;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Rank;

public enum StatType {

    ADMIN_IGNORE("adminignore", "§4Admin-Ignore", XMaterial.BOOK, (value, vp) -> vp.setAdminIgnore(Boolean.parseBoolean(value)), VaroPlayer::isAdminIgnore, (vp) -> vp.setAdminIgnore(false)),
    COUNTDOWN("countdown","§bCountdown", XMaterial.OAK_SIGN, (value, vp) -> vp.getStats().setCountdown(Integer.parseInt(value)), vp -> vp.getStats().getCountdown(), (vp) -> vp.getStats().setCountdown(ConfigSetting.PLAY_TIME.getValueAsInt() * 60)),
    EPISODES_PLAYED("episodesplayed","§5Episodes played", XMaterial.BLAZE_POWDER, (value, vp) -> vp.getStats().setSessionsPlayed(Integer.parseInt(value)), vp -> vp.getStats().getSessionsPlayed(), (vp) -> vp.getStats().setSessionsPlayed(0)),
    KILLS("kills","§4Kills", XMaterial.DIAMOND_SWORD, (value, vp) -> vp.getStats().setKills(Integer.parseInt(value)), vp -> vp.getStats().getKills(), (vp) -> vp.getStats().setKills(0)),
    PLAYER_STATE("playerstate","§6Player State", XMaterial.GOLDEN_APPLE, (value, vp) -> vp.getStats().setState(PlayerState.valueOf(value)), vp -> vp.getStats().getState(), (vp) -> vp.getStats().setState(PlayerState.ALIVE)),
    RANK("rank","§2Rank", XMaterial.EMERALD, (value, vp) -> vp.setRank(new Rank(value)), vp -> vp.getRank() == null ? null : vp.getRank().getDisplay(), (vp) -> vp.setRank(null)),
    SESSIONS("sessions","§bSessions", XMaterial.DIAMOND, (value, vp) -> vp.getStats().setSessions(Integer.parseInt(value)), vp -> vp.getStats().getSessions(), (vp) -> vp.getStats().setSessions(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt())),
    WILL_INVENTORY_CLEAR("willInventoryClear", "§cWill Inventory-Clear", XMaterial.CHEST, (value, vp) -> vp.getStats().setWillClear(Boolean.parseBoolean(value)), vp -> vp.getStats().isWillClear(), vp -> vp.getStats().setWillClear(false)),
    WINS("wins","§dWins", XMaterial.GOLD_INGOT, (value, vp) -> vp.getStats().setWins(Integer.parseInt(value)), vp -> vp.getStats().getWins(), (vp) -> vp.getStats().setWins(0)),
    YOUTUBE_LINK("youtubelink","§5YouTube-Link", XMaterial.PAPER, (value, vp) -> vp.getStats().setYoutubeLink(value), vp -> vp.getStats().getYoutubeLink(), (vp) -> vp.getStats().setYoutubeLink(null));

    private final String arg, displayName;
    private final XMaterial icon;
    private final ArgumentReceiver receiver;
    private final Function<VaroPlayer, Object> getter;
    private final Consumer<VaroPlayer> reset;

    StatType(String arg, String displayName, XMaterial icon, ArgumentReceiver receiver, Function<VaroPlayer, Object> getter, Consumer<VaroPlayer> reset) {
        this.arg = arg;
        this.displayName = displayName;
        this.icon = icon;
        this.receiver = receiver;
        this.getter = getter;
        this.reset = reset;
    }

    public boolean execute(Object value, VaroPlayer vp) {
        return this.execute(String.valueOf(value), vp);
    }

    public boolean execute(String value, VaroPlayer vp) {
        try {
            this.receiver.receive(value, vp);
            if (vp.isOnline())
                vp.update();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void remove(VaroPlayer vp) {
        this.reset.accept(vp);
        if (vp.isOnline())
            vp.update();
    }

    public String getDisplayName() {
        return displayName;
    }

    public XMaterial getIcon() {
        return icon;
    }

    public String getArg() {
        return this.arg;
    }

    public Object get(VaroPlayer vp) {
        Object result = this.getter.apply(vp);
        return result == null ? "§c/" : result;
    }

    public static StatType getByName(String name) {
        for (StatType type : values())
            if (type.getArg().equalsIgnoreCase(name))
                return type;

        return null;
    }

    interface ArgumentReceiver {

        void receive(String value, VaroPlayer vp);

    }
}