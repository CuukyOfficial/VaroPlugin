package de.cuuky.varo.alert;

import java.util.ArrayList;
import java.util.Date;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class Alert implements VaroSerializeable {

	private static ArrayList<Alert> alerts;

	static {
		alerts = new ArrayList<Alert>();
	}

	@VaroSerializeField(path = "created")
	private Date created;

	@VaroSerializeField(path = "id")
	private int id;

	@VaroSerializeField(path = "message")
	private String message;

	@VaroSerializeField(path = "open")
	private boolean open;

	@VaroSerializeField(path = "type")
	private AlertType type;

	public Alert() {
		alerts.add(this);
	}

	public Alert(AlertType type, String message) {
		this.type = type;
		this.message = message;
		this.id = generateId();
		this.open = true;
		this.created = new Date();

		alerts.add(this);
	}

	private int generateId() {
		int i = alerts.size() + 1;
		while (getAlert(i) != null)
			i++;

		return i;
	}

	public Date getCreated() {
		return created;
	}

	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public AlertType getType() {
		return type;
	}

	public boolean isOpen() {
		return open;
	}

	@Override
	public void onDeserializeEnd() {}

	@Override
	public void onSerializeStart() {}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public void switchOpenState() {
		this.open = !this.open;
	}

	public static Alert getAlert(int id) {
		for (Alert alert : alerts)
			if (alert.getId() == id)
				return alert;

		return null;
	}

	public static ArrayList<Alert> getAlerts() {
		return alerts;
	}

	public static ArrayList<Alert> getAlerts(AlertType type) {
		ArrayList<Alert> typed = new ArrayList<Alert>();
		for (Alert alert : alerts)
			if (alert.getType() == type)
				typed.add(alert);

		return typed;
	}

	public static ArrayList<Alert> getClosedAlerts() {
		ArrayList<Alert> closed = new ArrayList<Alert>();
		for (Alert alert : alerts)
			if (!alert.isOpen())
				closed.add(alert);

		return closed;
	}

	public static ArrayList<Alert> getOpenAlerts() {
		ArrayList<Alert> open = new ArrayList<Alert>();
		for (Alert alert : alerts)
			if (alert.isOpen())
				open.add(alert);

		return open;
	}
}