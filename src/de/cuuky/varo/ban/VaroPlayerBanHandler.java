package de.cuuky.varo.ban;

import de.cuuky.varo.bot.discord.register.BotRegister;
import de.varoplugin.banapi.*;
import de.varoplugin.banapi.LatestBansHandler.Mode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class VaroPlayerBanHandler {

    private final BanApi banApi;
    private final LatestBansHandler bansHandler;

    public VaroPlayerBanHandler() {
        banApi = new BanApi("http://varoplugin.de/banapi/", null, Throwable::printStackTrace);
        this.bansHandler = new LatestBansHandler(banApi, Mode.MINECRAFT_ONLY, Throwable::printStackTrace);
        this.bansHandler.registerListener(new VaroPlayerBanListener(this));
        this.bansHandler.start();
    }

    private String getDuration(long duration) {
        if (duration == -1)
            return "Permanent";
        if (duration == 0)
            return "Unban";
        else
            return BanDuration.getDisplaynameFromMillis(duration);
    }

    private Ban getMCBan(long discordID) {
        BanUser user = this.bansHandler.getCurrentData().getUser(discordID);
        if (user == null || !user.hasActiveMinecraftBan())
            return null;

        return this.bansHandler.getCurrentData().getUser(discordID).getLatestMinecraftBan();
    }

    protected Ban hasBannedDiscordLink(Player player) {
        BotRegister register = BotRegister.getRegister(player.getUniqueId().toString());
        if (register == null)
            return null;

        return this.getMCBan(register.getUserId());
    }

    protected Ban getBan(BanUser user, Player player) {
        Ban ban = null;
        if ((user == null || !user.hasActiveMinecraftBan()) && (ban = this.hasBannedDiscordLink(player)) == null)
            return null;

        return ban == null ? user.getLatestMinecraftBan() : ban;
    }

    public boolean hasBan(Player player, PlayerLoginEvent event) {
        if (this.bansHandler.getCurrentData() == null)
            return false;

        BanUser user = this.bansHandler.getCurrentData().getUser(player.getUniqueId());
        Ban ban;
        if ((ban = this.getBan(user, player)) == null)
            return false;

        if (event == null)
            player.kickPlayer(getKickMessage(ban));
        else
            event.disallow(Result.KICK_BANNED, getKickMessage(ban));
        return true;
    }

    public String getKickMessage(Ban ban) {
        return "§4You have been banned from all Varo-Servers!\n§cReason: §f" + ban.getReason() + "\n§cDuration: §f" + this.getDuration(ban.getDuration()) + "\n§cDescription: §f" + (ban.getNotes() == null ? "/" : ban.getNotes()) + "\n§cUnban here: §fhttps://discord.gg/CnDSVVx";
    }

    public void cancel() {
        this.bansHandler.cancel();
    }
}