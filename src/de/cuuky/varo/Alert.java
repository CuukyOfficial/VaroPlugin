package de.cuuky.varo;

import de.cuuky.cfw.configuration.serialization.Serialize;

import java.util.Date;

// TODO: Remove or make AlertType dynamic and remove getters
public class Alert extends VaroElement {

	@Serialize("created")
	private Date created;

	@Serialize("message")
	private String message;

	@Serialize("open")
	private boolean open;

	@Serialize("type")
	private AlertType type;

	public Alert(AlertType type, String message) {
		this.type = type;
		this.message = message;
		this.open = true;
		this.created = new Date();
	}

    @Override
    protected void registerPolicies() {
        this.registerPolicy(AlertType.class, this.type::getName, AlertType::getByName);
        super.registerPolicies();
    }

    @Override
    protected void onInitialize(Varo varo) {}

    public Date getCreated() {
        return created;
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

	public void setOpen(boolean open) {
		this.open = open;
	}

	public void switchOpenState() {
		this.open = !this.open;
	}
}