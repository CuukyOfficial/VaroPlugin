package de.cuuky.varo.serialize.identifier;

public class NullClass implements VaroSerializeable {

	/*
	 * Empty class to make a default value in the VaroSerializeField
	 */

	@Override
	public void onDeserializeEnd() {
	}

	@Override
	public void onSerializeStart() {
	}
}
