/* 
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

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
