package de.varoplugin.varo.ui.tasks;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import de.varoplugin.varo.config.language.component.MessageComponent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.task.VaroScheduledTask;

public class StartingUiTask extends VaroScheduledTask {
	
	private final Map<Integer, MessageComponent> messages;
    private int countdown;

    public StartingUiTask(Varo varo) {
        super(varo);
        this.messages = this.getVaro().getPlugin().getMessages().start_countdown.value();
    }

    @Override
    protected void schedule(BukkitRunnable runnable) {
        runnable.runTaskTimer(this.getVaro().getPlugin(), 20, 20);
        this.countdown = this.getVaro().getPlugin().getVaroConfig().start_countdown.getValue();
    }

    @Override
    public void run() {
    	MessageComponent message = this.messages.get(this.countdown);
    	if (message != null)
    		Bukkit.getServer().broadcastMessage(message.value(this.countdown));
        
        this.countdown--;
    }
}
