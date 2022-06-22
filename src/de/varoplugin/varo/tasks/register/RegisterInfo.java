package de.varoplugin.varo.tasks.register;

import de.varoplugin.varo.game.Varo;

public class RegisterInfo implements VaroRegisterInfo {

    private final Varo varo;

    public RegisterInfo(Varo varo) {
        this.varo = varo;
    }

    @Override
    public Varo getVaro() {
        return this.varo;
    }
}