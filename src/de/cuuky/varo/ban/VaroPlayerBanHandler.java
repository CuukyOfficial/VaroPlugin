package de.cuuky.varo.ban;

import java.util.UUID;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.varoplugin.banapi.Ban;
import de.varoplugin.banapi.BanApi;
import de.varoplugin.banapi.BanDuration;
import de.varoplugin.banapi.BanUser;
import de.varoplugin.banapi.LatestBansHandler;
import de.varoplugin.banapi.LatestBansHandler.Mode;

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

    protected Ban hasBannedDiscordLink(UUID uuid) {
        BotRegister register = BotRegister.getRegister(uuid.toString());
        if (register == null)
            return null;

        return this.getMCBan(register.getUserId());
    }

    protected Ban getBan(BanUser user, UUID uuid) {
        Ban ban = null;
        if ((user == null || !user.hasActiveMinecraftBan()) && (ban = this.hasBannedDiscordLink(uuid)) == null)
            return null;

        return ban == null ? user.getLatestMinecraftBan() : ban;
    }

    public Ban getActiveBan(UUID uuid) {
        if (this.bansHandler.getCurrentData() == null)
            return null;

        BanUser user = this.bansHandler.getCurrentData().getUser(uuid);
        Ban ban = this.getBan(user, uuid);
        if (ban == null)
            return null;

        return ban;
    }

    public String getKickMessage(Ban ban) {
        return "§4You have been banned from all Varo-Servers!\n§cReason: §f" + ban.getReason() + "\n§cDuration: §f" + this.getDuration(ban.getDuration()) + "\n§cNote: §f" + (ban.getNotes() == null ? "/" : ban.getNotes()) + "\n§cUnban here: §f" + Main.DISCORD_INVITE;
    }

    public void cancel() {
        this.bansHandler.cancel();
    }
}