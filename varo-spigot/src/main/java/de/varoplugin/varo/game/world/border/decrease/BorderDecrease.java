package de.varoplugin.varo.game.world.border.decrease;

public class BorderDecrease {

	private final double amount, speed;
	private final DecreaseReason reason;

	public BorderDecrease(double amount, double speed, DecreaseReason reason) {
		this.amount = amount;
		this.speed = speed;
		this.reason = reason;
	}
	
	public double getAmount() {
        return this.amount;
    }

	public double getSpeed() {
		return this.speed;
	}
	
	public DecreaseReason getReason() {
        return this.reason;
    }
}