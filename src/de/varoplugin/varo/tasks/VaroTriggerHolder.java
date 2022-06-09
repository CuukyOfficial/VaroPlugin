package de.varoplugin.varo.tasks;

import java.util.function.Supplier;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroTriggerHolder<T extends VaroTaskTrigger<V>, V extends VaroTask> {

    T createTrigger();

    void addTask(Supplier<V> taskSupplier);

}