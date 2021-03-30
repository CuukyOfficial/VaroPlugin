package de.cuuky.varo.serialize.serializer.serializeable.serializeables;

import java.lang.reflect.Field;

import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.serialize.serializer.serializeable.VaroSerialize;
import de.cuuky.varo.serialize.serializer.serializeable.VaroSerializeLoopType;

public class EnumSerializeable extends VaroSerialize {

	public EnumSerializeable() {
		super(VaroSerializeLoopType.LOOP);
	}

	@Override
	public Object deserialize(Field field, Object obj) {
		if (!field.getType().isEnum() || !(obj instanceof String))
			return null;

		VaroSerializeable ser = getEnumByString((String) obj);
		if (ser == null)
			return null;

		return ser;
	}
}