package de.varoplugin.varo.serialize.identifier;

public interface VaroSerializeable {

	/**
	 * To mark classes
	 */

	void onDeserializeEnd();

	void onSerializeStart();

}
