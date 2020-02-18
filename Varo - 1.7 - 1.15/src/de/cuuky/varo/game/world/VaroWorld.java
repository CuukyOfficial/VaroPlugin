package de.cuuky.varo.game.world;

import de.cuuky.varo.game.world.border.VaroBorder;

public class VaroWorld {
	
	private VaroBorder varoBorder;
	
	public VaroWorld() {
		this.varoBorder = new VaroBorder();
	}
	
	public VaroBorder getVaroBorder() {
		return this.varoBorder;
	}

	// TODO: Multiworld with own Borders
	
}