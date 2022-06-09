package de.varoplugin.varo.tasks;

import java.util.function.Supplier;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroTriggerHolder {

    VaroTaskTrigger createTrigger();

    void addTask(Supplier<VaroTask> taskSupplier);

}