package de.cuuky.varo.game.end;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.game.state.GameState;

public class WinnerCheck {

	private HashMap<Integer, ArrayList<VaroPlayer>> places;

	public WinnerCheck() {
		if(Game.getInstance().getGameState() != GameState.STARTED)
			return;

		places = new HashMap<Integer, ArrayList<VaroPlayer>>();
		ArrayList<VaroPlayer> alive = VaroPlayer.getAlivePlayer();
		if(!(alive.size() <= ConfigEntry.TEAMREQUEST_MAXTEAMMEMBERS.getValueAsInt() || alive.size() <= 2) || alive.size() == 0)
			return;

		VaroPlayer lastAlive = null;
		for(VaroPlayer vp : alive) {
			if(lastAlive == null) {
				lastAlive = vp;
				continue;
			}

			if(lastAlive.getTeam() == null || vp.getTeam() == null || !vp.getTeam().equals(lastAlive.getTeam()))
				return;
		}

		if(lastAlive.getTeam() == null) {
			lastAlive.onEvent(BukkitEventType.WIN);
			ArrayList<VaroPlayer> first = new ArrayList<VaroPlayer>();
			first.add(lastAlive);
			places.put(1, first);
		} else {
			lastAlive.getTeam().getMember().forEach(member -> member.onEvent(BukkitEventType.WIN));
			places.put(1, lastAlive.getTeam().getMember());
		}

		Map<Date, VaroPlayer> sorted = new TreeMap<Date, VaroPlayer>(new Comparator<Date>() {
			@Override
			public int compare(Date d1, Date d2) {
				return d1.after(d2) ? -1 : 1;
			}
		});

		for(VaroPlayer vp : VaroPlayer.getDeadPlayer())
			sorted.put(vp.getStats().getDiedAt(), vp);

		int i = 2;
		for(VaroPlayer vp : sorted.values()) {
			if(isSorted(vp))
				continue;

			if(vp.getTeam() == null) {
				ArrayList<VaroPlayer> first = new ArrayList<VaroPlayer>();
				first.add(vp);
				places.put(i, first);
			} else
				places.put(i, vp.getTeam().getMember());

			i++;
		}

		Game.getInstance().end(this);
	}

	public HashMap<Integer, ArrayList<VaroPlayer>> getPlaces() {
		return places;
	}

	private boolean isSorted(VaroPlayer vp) {
		for(ArrayList<VaroPlayer> list : places.values())
			if(list.contains(vp))
				return true;

		return false;
	}
}
