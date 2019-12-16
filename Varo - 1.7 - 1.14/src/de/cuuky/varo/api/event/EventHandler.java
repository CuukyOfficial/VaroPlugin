package de.cuuky.varo.api.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.cuuky.varo.Main;
import de.cuuky.varo.api.event.register.VaroListener;

public class EventHandler {

	private VaroListener listener;
	private Method method;
	private Class<? extends VaroAPIEvent> event;

	public EventHandler(VaroListener listener, Method method, Class<? extends VaroAPIEvent> event) {
		this.listener = listener;
		this.method = method;
		this.event = event;
	}

	public void execute(VaroAPIEvent event) {
		try {
			method.invoke(listener, event);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.err.println(Main.getConsolePrefix() + "An error occured on invoking a method, did you register it right? Listener: " + this.listener.getClass().getName());
			return;
		}
	}

	public Class<? extends VaroAPIEvent> getEvent() {
		return event;
	}

	public Method getMethod() {
		return method;
	}

	public VaroListener getListener() {
		return listener;
	}
}
