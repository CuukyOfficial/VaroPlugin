package de.varoplugin.varo.serialize.serializer;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.MemorySection;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.serialize.VaroSerializeHandler;
import de.varoplugin.varo.serialize.VaroSerializeObject;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;

public class VaroDeserializer extends VaroSerializeHandler {

	private VaroSerializeObject object;
	private MemorySection section;

	public VaroDeserializer(MemorySection section, VaroSerializeObject object) {
		this.section = section;
		this.object = object;
	}

	public VaroSerializeable deserialize() {
		VaroSerializeable instance = null;
		ArrayList<String> handled = new ArrayList<String>();
		sectionLoop: for (String s : section.getKeys(true)) {
			for (String handl : handled)
				if (s.contains(handl))
					continue sectionLoop;

			Field field = object.getFieldLoader().getFields().get(s);
			if (field != null) {
				if (instance == null)
					try {
						instance = object.getClazz().newInstance();
					} catch (InstantiationException | IllegalAccessException e1) {
						e1.printStackTrace();
						continue;
					}

				try {
					field.setAccessible(true);

					// CHECK FOR OTHER SERIALIZABLE OBJ
					Object obj = section.get(s);
					if (obj instanceof String)
						if (((String) obj).equals(NULL_REPLACE))
							obj = null;

					VaroSerializeObject handl = getHandler(field.getType());
					if (handl != null && obj != null) {
						handled.add(s);
						field.set(instance, new VaroDeserializer((MemorySection) obj, handl).deserialize());
						continue;
					}

					if (Map.class.isAssignableFrom(field.getType())) {
						obj = section.getConfigurationSection(s).getValues(false);
						handled.add(s);
					}

					if (field.getType() == Location.class) {
						if (obj != null)
							if (Bukkit.getWorld(section.getString(s + ".world")) != null)
								obj = new Location(Bukkit.getWorld(section.getString(s + ".world")), (double) section.get(s + ".x"), (double) section.get(s + ".y"), (double) section.get(s + ".z"));
							else
								obj = null;

						handled.add(s);
					}

					if (Collection.class.isAssignableFrom(field.getType())) {
						Class<?> clazz = object.getFieldLoader().getArrayTypes().get(field);
						if (clazz != null) {
							handl = getHandler(object.getFieldLoader().getArrayTypes().get(field));
							if (handl != null) {
								handled.add(s);
								ArrayList<VaroSerializeable> newList = new ArrayList<VaroSerializeable>();
								if (obj instanceof MemorySection) {
									MemorySection listSection = ((MemorySection) obj);
									for (String listStr : listSection.getKeys(true)) {
										Object listEntry = listSection.get(listStr);
										if (!(listEntry instanceof MemorySection) || listStr.contains("."))
											continue;

										newList.add(new VaroDeserializer((MemorySection) listEntry, handl).deserialize());
										continue;
									}
								}

								field.set(instance, newList);
								continue sectionLoop;
							}
						}
					}

					if (field.getType().isEnum() && obj instanceof String) {
						VaroSerializeable ser = getEnumByString((String) obj);
						if (ser != null)
							obj = ser;
					}

					if (field.getType().isPrimitive() && obj instanceof String)
						obj = Long.valueOf((String) obj);
					
					if (field.getType() == BigDecimal.class && obj instanceof String)
                        obj = new BigDecimal((String) obj);

					if (field.getType() == OffsetDateTime.class && obj instanceof String)
						obj = OffsetDateTime.parse((String) obj);

					field.set(instance, obj);
				} catch (IllegalArgumentException | IllegalAccessException | ExceptionInInitializerError | NullPointerException e) {
					e.printStackTrace();
					continue;
				}
			} else
			    Main.getInstance().getLogger().log(Level.SEVERE, "Could not deserialize field " + s + ": [FIELD NOT FOUND]");
		}

		instance.onDeserializeEnd();
		return instance;
	}
}