package de.cuuky.varo.serialize.identifier;

public interface VaroSerializeable {

	/**
	 * To mark classes
	 */

	void onDeserializeEnd();

	void onSerializeStart();

}
