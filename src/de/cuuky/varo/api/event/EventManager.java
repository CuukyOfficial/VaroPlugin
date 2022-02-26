package de.cuuky.varo.api.event;

import java.lang.reflect.Method;
import java.util.ArrayList;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.api.event.register.VaroEventMethod;
import de.cuuky.varo.api.event.register.VaroListener;

public class EventManager {

	private ArrayList<EventHandler> handlerList;

	public EventManager() {
		this.handlerList = new ArrayList<>();
	}

	public boolean executeEvent(VaroAPIEvent event) {
		for (EventHandler handler : this.handlerList) {
			if (!handler.getEvent().equals(event.getClass()))
				continue;

			handler.execute(event);
			return event.isCancelled();
		}

		return false;
	}

	public ArrayList<EventHandler> getHandler() {
		return handlerList;
	}

	@SuppressWarnings("unchecked")
	public void registerEvent(VaroListener listener) {
		for (Method method : listener.getClass().getDeclaredMethods()) {
			if (method.getAnnotation(VaroEventMethod.class) == null)
				continue;

			Class<?>[] clazzes = method.getParameterTypes();

			if (clazzes.length != 1 || !VaroAPIEvent.class.isAssignableFrom(clazzes[0])) {
				System.out.println(Main.getConsolePrefix() + "Failed to register listener " + listener.getClass().getName() + " caused by wrong parameters given.");
				continue;
			}

			handlerList.add(new EventHandler(listener, method, (Class<? extends VaroAPIEvent>) clazzes[0]));
		}
	}
}
