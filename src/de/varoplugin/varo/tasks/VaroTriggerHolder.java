package de.varoplugin.varo.tasks;

import de.varoplugin.varo.tasks.register.VaroRegisterInfo;

import java.util.function.Supplier;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroTriggerHolder<T extends VaroRegisterInfo> {

    VaroTaskTrigger<T> createTrigger();

    void addTask(Supplier<VaroTask<T>> taskSupplier);

}