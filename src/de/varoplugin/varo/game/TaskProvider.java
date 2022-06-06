package de.varoplugin.varo.game;

import java.util.List;

/**
 * Represents any object that can provide tasks for a given object T.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface TaskProvider<T> {

    List<CancelableTask> getTasks(T object);

}