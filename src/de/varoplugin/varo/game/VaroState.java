package de.varoplugin.varo.game;

import java.util.Collection;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroState {

    /**
     * Returns all the tasks the current game state needs.
     *
     * @param varo
     * @return
     */
    Collection<CancelableTask> getTasks(Varo varo);

}