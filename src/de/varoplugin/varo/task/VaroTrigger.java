package de.varoplugin.varo.task;

public interface VaroTrigger extends VaroRegistrable, VaroListener {

    void addChildren(VaroRegistrable... registrable);

}
