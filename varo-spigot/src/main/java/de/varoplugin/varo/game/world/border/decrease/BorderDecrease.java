package de.varoplugin.varo.game.world.border.decrease;

import java.math.BigDecimal;

public class BorderDecrease {

	private final double amount;
    private final BigDecimal speed;
	private final DecreaseReason reason;

	public BorderDecrease(double amount, BigDecimal speed, DecreaseReason reason) {
		this.amount = amount;
		this.speed = speed;
		this.reason = reason;
	}
	
	public double getAmount() {
        return this.amount;
    }

	public BigDecimal getSpeed() {
		return this.speed;
	}
	
	public DecreaseReason getReason() {
        return this.reason;
    }
}