package de.cuuky.varo.ban;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.varoplugin.banapi.AccountType;
import de.varoplugin.banapi.Ban;
import de.varoplugin.banapi.BanChangeListener;
import de.varoplugin.banapi.BanDataListener;
import de.varoplugin.banapi.BanUser;
import de.varoplugin.banapi.UsersDataWrapper;

public class VaroPlayerBanListener implements BanChangeListener, BanDataListener {

    private final VaroPlayerBanHandler handler;

    public VaroPlayerBanListener(VaroPlayerBanHandler handler) {
        this.handler = handler;
    }

    private void kickPlayer(Player player, Ban ban) {
        new BukkitRunnable() {

            @Override
            public void run() {
                player.kickPlayer(handler.getKickMessage(ban));
            }
        }.runTask(Main.getInstance());
    }

    private boolean canJoin(BanUser user) {
        return user == null || !user.hasActiveMinecraftBan();
    }

    @Override
    public void onBanDataUpdated(UsersDataWrapper data) {
        for (Player player : Main.getInstance().getServer().getOnlinePlayers()) {
            BanUser user = data.getUser(player.getUniqueId());
            Ban ban;
            if ((ban = this.handler.getBan(user, player)) == null)
                continue;

            this.kickPlayer(player, ban);
        }
    }

    @Override
    public void onBanUpdate(BanUser user, Ban ban, AccountType type) {
        if (!this.canJoin(user))
            return;

        for (String uuid : user.getMinecraftUuids()) {
            Player player = Main.getInstance().getServer().getPlayer(UUID.fromString(uuid));
            if (player == null || !player.isOnline())
                return;

            this.kickPlayer(player, ban);
        }
    }
}