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

package de.varoplugin.varo.config.language;

import java.util.HashMap;

public class GermanLanguage extends AbstractLanguage {

	private static final String NAME = "de";

	public GermanLanguage(int id) {
		super(id, NAME, getDefaultValues());
	}

	private static Translation<?>[] getDefaultValues() {
		HashMap<Integer, String> intro = new HashMap<>();
		intro.put(0, "§3LOS!!!");
		intro.put(1, "§3%startcountdown%§7...");
		intro.put(2, "§3%startcountdown%§7...");
		intro.put(3, "§3%startcountdown%§7...");
		intro.put(4, "§3%startcountdown%§7...");
		intro.put(5, "§3%startcountdown%§7...");
		intro.put(10, "§3%startcountdown%§7...");
		intro.put(20, "§3%startcountdown%§7...");
		intro.put(30, "§3%projectname% §7startet in §3%startcountdown% §7seconds");

		return new Translation<?>[] {
			new StringTranslation("bot.discord.notverified", "§7Du bist noch nicht mit Discord verifiziert!\n§7Um dich zu verifizieren, nutze §c/verify §7auf dem Discord!\nLink zum Discord: §c%discord%\n§7Dein code: §c%code%"),
			new StringTranslation("bot.discord.member", "§7Du bist kein Mitglied unseres Discords!\n%discord%"),
			new StringTranslation("bot.discord.command.status.title", ""),
			new StringTranslation("bot.discord.command.status.body", "Whitelist: %whitelist%\nGame-State: %gamestate%\nOnline: %online%"),
			new StringTranslation("bot.discord.command.verify.fail.title", ""),
			new StringTranslation("bot.discord.command.verify.fail.body", "Ungültiger Verifizierungscode!"),
			new StringTranslation("bot.discord.command.verify.success.title", ""),
			new StringTranslation("bot.discord.command.verify.success.body", "Du hast dich Erfolgreich verifiziert!"),
			new StringTranslation("bot.discord.command.verify.alreadyverified.title", ""),
			new StringTranslation("bot.discord.command.verify.alreadyverified.body", "Dieser Discord Account ist bereits verifiziert!"),
			new StringTranslation("bot.discord.modal.verify.title", "Verify"),
			new StringTranslation("bot.discord.modal.verify.inputlabel", "Code:"),

			new IntStringMapTranslation("start.countdownmessages", intro),

			new StringArrayTranslation("scoreboard.header", "%projectname%"),
			new StringArray2Translation("scoreboard.body", new String[] {"", "§7Team§8:", "%colorcode%%team%", "", "§7Kills§8:", "%colorcode%%kills%", "", "§7Time§8:", "%colorcode%%min%§8:%colorcode%%sec%", "                   ", "§7Online: %colorcode%%online%", "§7Alive: %colorcode%%remaining%", "§7Players: %colorcode%%players%", "§7Strikes: %colorcode%%strikes%", ""},
					new String[] {"", "§7Team§8:", "%colorcode%%team%", "", "§7Kills§8:", "%colorcode%%kills%", "", "§7Time§8:", "%colorcode%%min%§8:%colorcode%%sec%", "                   ", "§7Best Players§8:", "§71. %colorcode%%topplayer-1%", "§72. %colorcode%%topplayer-2%", "§73. %colorcode%%topplayer-3%", ""})
		};
	}
}
