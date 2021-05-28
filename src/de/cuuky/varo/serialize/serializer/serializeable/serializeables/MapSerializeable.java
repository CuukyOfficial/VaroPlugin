package de.cuuky.varo.serialize.serializer.serializeable.serializeables;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.configuration.MemorySection;

import de.cuuky.varo.serialize.VaroSerializeObject;
import de.cuuky.varo.serialize.serializer.serializeable.VaroSerialize;
import de.cuuky.varo.serialize.serializer.serializeable.VaroSerializeLoopType;

public class MapSerializeable extends VaroSerialize {

	public MapSerializeable() {
		super(VaroSerializeLoopType.LOOP_HANDLE);
	}

	@Override
	public Object deserialize(Field field, Object obj, MemorySection section, String path, VaroSerializeObject object) {
		if (!Map.class.isAssignableFrom(field.getType()))
			return null;

		return section.getConfigurationSection(path).getValues(false);
	}
}
