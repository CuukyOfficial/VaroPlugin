package de.cuuky.varo.serialize.serializer.serializeable.serializeables;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.configuration.MemorySection;

import de.cuuky.varo.serialize.VaroSerializeObject;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.serialize.serializer.VaroDeserializer;
import de.cuuky.varo.serialize.serializer.serializeable.VaroSerialize;
import de.cuuky.varo.serialize.serializer.serializeable.VaroSerializeLoopType;

public class CollectionSerializeable extends VaroSerialize {

	public CollectionSerializeable() {
		super(VaroSerializeLoopType.CONTINUE);
	}
	
	@Override
	public Object deserialize(Field field, Object obj, MemorySection section, String path, VaroSerializeObject object) {
		if(Collection.class.isAssignableFrom(field.getType())) {
			Class<?> clazz = object.getFieldLoader().getArrayTypes().get(field);
			if(clazz != null) {
				VaroSerializeObject handl = getHandler(object.getFieldLoader().getArrayTypes().get(field));
				if(handl != null) {
					ArrayList<VaroSerializeable> newList = new ArrayList<VaroSerializeable>();
					if(obj instanceof MemorySection) {
						MemorySection listSection = ((MemorySection) obj);
						for(String listStr : listSection.getKeys(true)) {
							Object listEntry = listSection.get(listStr);
							if(!(listEntry instanceof MemorySection) || listStr.contains("."))
								continue;

							VaroDeserializer des = new VaroDeserializer((MemorySection) listEntry, handl);
							newList.add(des.deserialize());
							continue;
						}
					}

					return newList;
				}
			}
		}
		
		return null;
	}
}
