package de.cuuky.varo.serialize.serializer.serializeable;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.configuration.MemorySection;

import de.cuuky.varo.serialize.VaroSerializeHandler;
import de.cuuky.varo.serialize.VaroSerializeObject;
import de.cuuky.varo.serialize.serializer.serializeable.serializeables.CollectionSerializeable;
import de.cuuky.varo.serialize.serializer.serializeable.serializeables.EnumSerializeable;
import de.cuuky.varo.serialize.serializer.serializeable.serializeables.LocationSerializeable;
import de.cuuky.varo.serialize.serializer.serializeable.serializeables.LongSerializeable;
import de.cuuky.varo.serialize.serializer.serializeable.serializeables.MapSerializeable;

public class VaroSerialize extends VaroSerializeHandler {

	private static ArrayList<VaroSerialize> serializes;

	private VaroSerializeLoopType loopType;

	static {
		serializes = new ArrayList<>();

		new MapSerializeable();
		new CollectionSerializeable();
		new LocationSerializeable();
		new EnumSerializeable();
		new LongSerializeable();
	}

	public VaroSerialize(VaroSerializeLoopType loopType) {
		this.loopType = loopType;

		serializes.add(this);
	}

	public Object deserialize(Field field, Object obj) {
		return null;
	}

	public Object deserialize(Field field, Object obj, MemorySection section, String path, VaroSerializeObject object) {
		return null;
	}

	public VaroSerializeLoopType getLoopType() {
		return loopType;
	}

	public static ArrayList<VaroSerialize> getSerializes() {
		return serializes;
	}
}