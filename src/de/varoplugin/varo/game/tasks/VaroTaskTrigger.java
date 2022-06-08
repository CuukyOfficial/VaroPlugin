package de.varoplugin.varo.game.tasks;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroTaskTrigger extends TaskRegistrable {

    boolean addTask(TaskRegistrable task);

    boolean registerTasks();

    boolean unregisterTasks();

}