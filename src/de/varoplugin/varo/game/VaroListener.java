package de.varoplugin.varo.game;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroListener implements CancelableListener {

    protected final Varo varo;

    public VaroListener(Varo varo) {
        this.varo = varo;
    }
}