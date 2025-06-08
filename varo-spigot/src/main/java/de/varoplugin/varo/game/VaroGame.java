package de.varoplugin.varo.game;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.varoplugin.varo.api.game.VaroStartEvent;
import de.varoplugin.varo.game.start.*;
import de.varoplugin.varo.logger.logger.EventLogger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.cryptomorin.xseries.XEntityType;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.api.game.VaroEndEvent;
import de.varoplugin.varo.bot.discord.VaroDiscordBot;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.game.world.AutoSetup;
import de.varoplugin.varo.game.world.VaroWorld;
import de.varoplugin.varo.game.world.VaroWorldHandler;
import de.varoplugin.varo.game.world.border.decrease.BorderDecreaseDayTimer;
import de.varoplugin.varo.game.world.border.decrease.BorderDecreaseMinuteTimer;
import de.varoplugin.varo.game.world.generators.SpawnGenerator;
import de.varoplugin.varo.listener.helper.cancelable.CancelableType;
import de.varoplugin.varo.listener.helper.cancelable.VaroCancelable;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.stats.stat.PlayerState;
import de.varoplugin.varo.player.stats.stat.YouTubeVideo;
import de.varoplugin.varo.serialize.identifier.VaroSerializeField;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;
import de.varoplugin.varo.spawns.sort.PlayerSort;
import de.varoplugin.varo.threads.daily.checks.YouTubeCheck;
import de.varoplugin.varo.utils.EventUtils;
import de.varoplugin.varo.utils.VaroUtils;
import io.github.almightysatan.slams.Placeholder;

public class VaroGame implements VaroSerializeable {

    @VaroSerializeField(path = "autostart")
    private AutoStart autostart;

    @VaroSerializeField(path = "borderDecrease")
    private BorderDecreaseDayTimer borderDayTimer;

    @VaroSerializeField(path = "gamestate")
    private GameState gamestate;

    @VaroSerializeField(path = "lastCoordsPost")
    private Date lastCoordsPost;

    @VaroSerializeField(path = "lastDayTimer")
    private Date lastDayTimer;

    @VaroSerializeField(path = "lobby")
    private Location lobby;

    @VaroSerializeField(path = "projectTime")
    private long projectTime;

    private boolean finaleJoin;
    private BukkitTask finaleStartScheduler;
    private int finaleCountdown;

    private boolean firstTime;
    private VaroMainHeartbeatThread mainThread; // this is not actually a thread
    private StartSequence startSequence;
    private BorderDecreaseMinuteTimer borderMinuteTimer;
    private ProtectionTime protection;
    private VaroWorldHandler varoWorldHandler;
    private TopScoreManager topScores;

    public VaroGame() {
        Main.setVaroGame(this);
    }

    private void loadVariables() {
        if (mainThread != null)
            mainThread.loadVariables();

        this.topScores = new TopScoreManager();
    }

    public void init() {
        startRefreshTimer();
        loadVariables();

        this.varoWorldHandler = new VaroWorldHandler();

        this.setGamestate(GameState.LOBBY);
        this.borderDayTimer = new BorderDecreaseDayTimer(true);
    }

    public void prepareStart() {
        if (hasStarted() || isStarting())
            return;

        Main.getDataManager().createBackup(null);

        if (ConfigSetting.REMOVE_PLAYERS_ABSENT_AT_START.getValueAsBoolean())
            removeAbsentAtStart();

        if (ConfigSetting.DO_RANDOMTEAM_AT_START.getValueAsInt() > 0) {
            VaroUtils.doRandomTeam(ConfigSetting.DO_RANDOMTEAM_AT_START.getValueAsInt());
            Bukkit.broadcastMessage(Main.getPrefix() + "Alle Spieler haben einen zufälligen Teampartner erhalten!");
        }

        LobbyItem.removeHooks();

        if (ConfigSetting.DO_SPAWN_GENERATE_AT_START.getValueAsBoolean()) {
            new SpawnGenerator(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation(), AutoSetup.getSpawnRadius(VaroPlayer.getAlivePlayer().size()), true, (XMaterial) ConfigSetting.AUTOSETUP_SPAWNS_BLOCKID.getValueAsEnum(), (XMaterial) ConfigSetting.AUTOSETUP_SPAWNS_SIDEBLOCKID.getValueAsEnum());
            Bukkit.broadcastMessage(Main.getPrefix() + "Die Löcher für den Spawn wurden generiert!");
        }

        if (ConfigSetting.DO_SORT_AT_START.getValueAsBoolean()) {
            new PlayerSort().sortPlayers();
            Bukkit.broadcastMessage(Main.getPrefix() + "Alle Spieler wurden sortiert!");
        }

        this.setProjectTime(0L);

        if (borderMinuteTimer != null)
            borderMinuteTimer.remove();

        this.lastDayTimer = new Date();
        this.startSequence = ConfigSetting.SURO_START.getValueAsBoolean() ? new SuroStart(this) : new VaroStart(this);
        this.startSequence.start();
    }

    public void start() {
        this.startSequence.abort();
        this.startSequence = null;

        if (EventUtils.callEvent(new VaroStartEvent(this))) {
            return;
        }

        this.setGamestate(GameState.STARTED);
        Messages.LOG_GAME_STARTED.log(EventLogger.LogType.LOG);

        for (VaroPlayer pl1 : VaroPlayer.getOnlinePlayer()) {
            if (pl1.getStats().isSpectator())
                continue;

            Player pl = pl1.getPlayer();
            pl.playSound(pl.getLocation(), XSound.BLOCK_NOTE_BLOCK_PLING.get(), 1, 1);
            pl.setGameMode(GameMode.SURVIVAL);
            pl1.cleanUpPlayer();
        }

        for (VaroPlayer pl1 : VaroPlayer.getVaroPlayers())
            pl1.getStats().loadStartDefaults();

        Main.getVaroGame().setFirstTime(true);
        Main.getDataManager().getListManager().getStartItems().giveToAll();
        Main.getVaroGame().setBorderMinuteTimer(new BorderDecreaseMinuteTimer());

        for (VaroWorld world : Main.getVaroGame().getVaroWorldHandler().getWorlds())
            world.fillChests();

        if (ConfigSetting.STARTPERIOD_PROTECTIONTIME.getValueAsInt() > 0) {
            Messages.PROTECTION_START.broadcast();
            Main.getVaroGame().setProtection(new ProtectionTime());
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                setFirstTime(false);
            }
        }.runTaskLater(Main.getInstance(), this.getPlayTime() * 60 * 20); // TODO this does not work when PLAY_TIME is -1

        if (ConfigSetting.YOUTUBE_ENABLED.getValueAsBoolean())
            new BukkitRunnable() {

            @Override
            public void run() {
                // Copy the list to avoid ConcurrentModificationException
                // This is only executed once anyway so performance doesn't really matter
                for (VaroPlayer player : VaroPlayer.getVaroPlayers().toArray(new VaroPlayer[0]))
                    if (player.getStats().getYoutubeLink() != null) {
                        List<YouTubeVideo> videos = YouTubeCheck.loadNewVideos(player);
                        if (videos != null)
                            player.getStats().getVideos().addAll(videos);
                    }
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public void abort() {
        this.startSequence.abort();
        this.startSequence = null;
        Messages.GAME_START_ABORT.broadcast();
    }

    public void restart() {
        this.setGamestate(GameState.LOBBY);
        // TODO maybe reset some stuff ???
    }

    private void removeAbsentAtStart() {
        for (VaroPlayer varoplayer : new LinkedList<>(VaroPlayer.getVaroPlayers()))
            if (!varoplayer.isOnline())
                varoplayer.delete();
    }

    public void end(WinnerCheck check) {
        if (EventUtils.callEvent(new VaroEndEvent(this, check)))
            return;

        this.setGamestate(GameState.END);

        for (VaroPlayer vp : check.getPlaces().get(1)) {
            if (!vp.isOnline())
                continue;

            Player p = vp.getPlayer();
            EntityType firework = XEntityType.FIREWORK_ROCKET.get();
            p.getWorld().spawnEntity(p.getLocation().clone().add(1, 0, 0), firework);
            p.getWorld().spawnEntity(p.getLocation().clone().add(-1, 0, 0), firework);
            p.getWorld().spawnEntity(p.getLocation().clone().add(0, 0, 1), firework);
            p.getWorld().spawnEntity(p.getLocation().clone().add(0, 0, -1), firework);
        }

        // This shit needs to be rewritten in v4-next
        String first = "";
        String second = "";
        String third = "";
        for (int i = 1; i <= 3; i++) {
            ArrayList<VaroPlayer> won;
            won = check.getPlaces().get(i);

            if (won == null)
                break;

            StringBuilder names = new StringBuilder();
            for (VaroPlayer vp : won)
                names.append(!won.toArray()[won.size() - 1].equals(vp) ? vp.getName() + (won.size() > 2 ? (won.toArray()[won.size() - 2].equals(vp) ? "" : ", ") : "") : ((won.size() == 1 ? "" : ", ") + vp.getName()));
            names.append(won.get(0).getTeam() != null ? " (#" + won.get(0).getTeam().getName() + ")" : "");

            switch (i) {
                case 1:
                    first = names.toString();
                    break;
                case 2:
                    second = names.toString();
                    break;
                case 3:
                    third = names.toString();
                    break;
            }
        }

        if (first.contains(",")) {
            Messages.LOG_WIN_TEAM.log(LogType.WIN, Placeholder.constant("winner", first));
            Messages.GAME_WIN_TEAM.broadcast(Placeholder.constant("winner", first));
        } else {
            Messages.LOG_WIN_PLAYER.log(LogType.WIN, Placeholder.constant("winner", first));
            Messages.GAME_WIN_PLAYER.broadcast(Placeholder.constant("winner", first));
        }

        long resultChannelId = ConfigSetting.DISCORDBOT_RESULT_CHANNELID.getValueAsLong();
        VaroDiscordBot db = Main.getBotLauncher().getDiscordbot();
        if (db != null && db.isEnabled() && resultChannelId != 0 && resultChannelId != -1) {
            db.sendMessage((":first_place: " + first + (second != null ? "\n" + ":second_place: " + second : "") + (third != null ? "\n" + ":third_place: " + third : "")) + "\n\nHerzlichen Glueckwunsch!", "Das Projekt ist nun vorbei!", Color.MAGENTA, resultChannelId);

            File file = new File("plugins/Varo/logs", "logs.yml");
            if (file.exists())
                db.sendMessage("Die Logs des Projektes", "Logs", file, Color.BLUE, resultChannelId);
        }

        if (ConfigSetting.STOP_SERVER_ON_WIN.isIntActivated()) {
            Bukkit.getServer().broadcastMessage("§7Der Server wird in " + Main.getColorCode() + ConfigSetting.STOP_SERVER_ON_WIN.getValueAsInt() + " Sekunden §7heruntergefahren...");

            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getServer().shutdown();
                }
            }.runTaskLater(Main.getInstance(), ConfigSetting.STOP_SERVER_ON_WIN.getValueAsInt() * 20);
        }
    }

    private void startRefreshTimer() {
        (mainThread = new VaroMainHeartbeatThread()).runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    private void startFinale() {
        if (!this.isRunning() || this.isFinale())
            throw new IllegalStateException(this.getGameState().name());

        this.setGamestate(GameState.FINALE);

        Messages.LOG_FINALE_START.log(LogType.ALERT);
        Messages.GAME_FINALE_START.broadcast();
        if (ConfigSetting.FINALE_PROTECTION_TIME.getValueAsInt() > 0)
            Main.getVaroGame().setProtection(new ProtectionTime(ConfigSetting.FINALE_PROTECTION_TIME.getValueAsInt()));

        for (VaroPlayer player : VaroPlayer.getVaroPlayers()) {
            if (ConfigSetting.FINALE_FREEZE.getValueAsBoolean())
                VaroCancelable.removeCancelable(player, CancelableType.FREEZE);
            if (player.getPlayer() != null) {
                if (player.getPlayer().isOnline()) {
                    player.saveTeleport(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation());
                    continue;
                }
            }
            if (ConfigSetting.PLAYER_SPECTATE_IN_FINALE.getValueAsBoolean()) {
                player.getStats().setState(PlayerState.SPECTATOR);
            } else {
                player.getStats().setState(PlayerState.DEAD);
            }
        }

        Main.getVaroGame().getVaroWorldHandler().setBorderSize(ConfigSetting.BORDER_SIZE_IN_FINALE.getValueAsInt(), 0, null);
    }

    public void startFinaleCountdown(int countdown) {
        if (!this.isRunning() || this.isFinale())
            throw new IllegalStateException(this.getGameState().name());

        if (this.finaleStartScheduler != null)
            throw new IllegalStateException();

        if (countdown != 0) {
            if (ConfigSetting.FINALE_FREEZE.getValueAsBoolean()) {
                for (VaroPlayer player : VaroPlayer.getOnlineAndAlivePlayer())
                    if (!player.getPlayer().isOp())
                        new VaroCancelable(CancelableType.FREEZE, player);
            }

            this.finaleJoin = true;
            this.finaleCountdown = countdown;
            this.finaleStartScheduler = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
                if (!this.isRunning() || this.isFinale() || this.finaleStartScheduler == null) {
                    abortFinaleStart();
                    return;
                }

                if (this.finaleCountdown != 0) {
                    Messages.GAME_FINALE_COUNTDOWN.broadcast(Placeholder.constant("finale-countdown", String.valueOf(this.finaleCountdown)));
                } else {
                    this.startFinale();
                    this.finaleStartScheduler.cancel();
                }
                this.finaleCountdown--;
            }, 0L, 20L);
        } else {
            this.startFinale();
        }
    }

    public void startFinaleJoin() {
        if (!this.isRunning() || this.isFinale())
            throw new IllegalStateException(this.getGameState().name());

        if (ConfigSetting.FINALE_FREEZE.getValueAsBoolean()) {
            for (VaroPlayer player : VaroPlayer.getOnlineAndAlivePlayer())
                if (!player.getPlayer().isOp())
                    new VaroCancelable(CancelableType.FREEZE, player);
            Bukkit.broadcastMessage(Main.getPrefix() + "Das Finale beginnt bald. Bis zum Start wurden alle gefreezed.");
        } else
            Bukkit.broadcastMessage(Main.getPrefix() + "Das Finale beginnt bald.");

        this.finaleJoin = true;
    }

    public void abortFinaleStart() {
        if (this.finaleStartScheduler != null) {
            this.finaleStartScheduler.cancel();
            this.finaleStartScheduler = null;
        }
        for (VaroPlayer player : VaroPlayer.getVaroPlayers())
            if (ConfigSetting.FINALE_FREEZE.getValueAsBoolean())
                VaroCancelable.removeCancelable(player, CancelableType.FREEZE);
        this.finaleJoin = false;
    }

    public TopScoreManager getTopScores() {
        return this.topScores;
    }

    public VaroMainHeartbeatThread getMainThread() {
        return this.mainThread;
    }

    public AutoStart getAutoStart() {
        return autostart;
    }

    public GameState getGameState() {
        return gamestate;
    }

    private void setGamestate(GameState gamestate) {
        this.gamestate = gamestate;
        VaroUtils.updateWorldTime();
    }

    public boolean hasStarted() {
        return gamestate != GameState.LOBBY;
    }

    public boolean isRunning() {
        return this.gamestate == GameState.STARTED || this.gamestate == GameState.FINALE;
    }

    public boolean isFinale() {
        return this.gamestate == GameState.FINALE;
    }

    public boolean hasEnded() {
        return this.gamestate == GameState.END;
    }

    public boolean isFinaleJoin() {
        return this.finaleJoin;
    }

    public boolean isFinaleCountdown() {
        return this.finaleStartScheduler != null;
    }

    public Date getLastCoordsPost() {
        return lastCoordsPost;
    }

    public Date getLastDayTimer() {
        return lastDayTimer;
    }

    public Location getLobby() {
        return lobby;
    }

    public long getProjectTime() {
        return this.projectTime;
    }

    public void setProjectTime(long projectTime) {
        this.projectTime = projectTime;
    }

    public ProtectionTime getProtection() {
        return protection;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public boolean isStarting() {
        return startSequence != null;
    }

    public int getPlayTime() {
        return this.isFinale() || this.isFinaleJoin() ? -1 : ConfigSetting.PLAY_TIME.getValueAsInt();
    }

    public boolean isPlayTimeLimited() {
        return this.getPlayTime() >= 1;
    }

    public VaroWorldHandler getVaroWorldHandler() {
        return this.varoWorldHandler;
    }

    public void setAutoStart(AutoStart autoStart) {
        this.autostart = autoStart;
    }

    public void setBorderDayTimer(BorderDecreaseDayTimer dayTimer) {
        this.borderDayTimer = dayTimer;
    }

    public void setBorderMinuteTimer(BorderDecreaseMinuteTimer minuteTimer) {
        this.borderMinuteTimer = minuteTimer;
    }

    public void setLastCoordsPost(Date lastCoordsPost) {
        this.lastCoordsPost = lastCoordsPost;
    }

    public void setLastDayTimer(Date lastDayTimer) {
        this.lastDayTimer = lastDayTimer;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public void setProtection(ProtectionTime protection) {
        this.protection = protection;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    @Override
    public void onDeserializeEnd() {
        if (gamestate == GameState.STARTED)
            borderMinuteTimer = new BorderDecreaseMinuteTimer();

        startRefreshTimer();
        loadVariables();

        this.varoWorldHandler = new VaroWorldHandler();
    }

    @Override
    public void onSerializeStart() {
    }
}
