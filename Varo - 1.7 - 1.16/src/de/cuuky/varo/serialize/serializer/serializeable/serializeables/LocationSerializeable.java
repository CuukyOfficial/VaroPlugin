package de.cuuky.varo.serialize.serializer.serializeable.serializeables;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.MemorySection;

import de.cuuky.varo.serialize.VaroSerializeObject;
import de.cuuky.varo.serialize.serializer.serializeable.VaroSerialize;
import de.cuuky.varo.serialize.serializer.serializeable.VaroSerializeLoopType;

public class LocationSerializeable extends VaroSerialize {

	public LocationSerializeable() {
		super(VaroSerializeLoopType.LOOP_HANDLE);
	}

	@Override
	public Object deserialize(Field field, Object obj, MemorySection section, String s, VaroSerializeObject object) {
		if (field.getType() == Location.class)
			if (obj != null)
				obj = new Location(Bukkit.getWorld(section.getString(s + ".world")), (double) section.get(s + ".x"), (double) section.get(s + ".y"), (double) section.get(s + ".z"));

		return null;
	}
}