package de.varoplugin.varo.game.task.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

public class CountdownTask extends AbstractHeartbeatTask {

    public CountdownTask(VaroPlayer player) {
        super(player, false);
    }

    @Override
    public void onEnable() {
        if (this.getPlayer().getCountdown() <= 0) this.getPlayer().setCountdown(300); // TODO: Configurable
        super.onEnable();
    }

    @Override
    public void run() {
        this.getPlayer().setCountdown(this.getPlayer().getCountdown() - 1);
        if (this.getPlayer().getCountdown() <= 0) {
            // TODO: Add VaroPlayer#kick method with kickReason enum
            this.runSynchronized(() -> this.getPlayer().getPlayer().kickPlayer("Your session is over!"));
        }
    }
}
