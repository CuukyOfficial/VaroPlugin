package de.varoplugin.varo.serialize.serializer.serializeable.serializeables;

import java.lang.reflect.Field;

import de.varoplugin.varo.serialize.serializer.serializeable.VaroSerialize;
import de.varoplugin.varo.serialize.serializer.serializeable.VaroSerializeLoopType;

public class LongSerializeable extends VaroSerialize {

	public LongSerializeable() {
		super(VaroSerializeLoopType.LOOP);
	}

	@Override
	public Object deserialize(Field field, Object obj) {
		if (!field.getType().isPrimitive() || !(obj instanceof String))
			return null;

		return Long.valueOf((String) obj);
	}
}