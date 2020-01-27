package de.cuuky.varo.serialize.serializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.serialize.VaroSerializeHandler;
import de.cuuky.varo.serialize.VaroSerializeObject;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class VaroSerializer extends VaroSerializeHandler {

	private VaroSerializeable instance;
	private YamlConfiguration saveTo;
	private String saveUnder;

	public VaroSerializer(String saveUnder, VaroSerializeable instance, YamlConfiguration saveTo) {
		this.saveUnder = saveUnder;
		this.instance = instance;
		this.saveTo = saveTo;

		serialize();
	}

	public void serialize() {
		VaroSerializeObject handler = getHandler(instance.getClass());
		instance.onSerializeStart();

		ArrayList<String> list1 = new ArrayList<String>();
		list1.addAll(handler.getFieldLoader().getFields().keySet());
		Collections.reverse(list1);
		for(String fieldIdent : list1) {
			try {
				Field field = handler.getFieldLoader().getFields().get(fieldIdent);
				field.setAccessible(true);

				Object obj = field.get(instance);
				if(obj != null) {
					if(obj instanceof List) {
						ArrayList<?> list = (ArrayList<?>) obj;
						if(!list.isEmpty())
							if(list.get(0) instanceof VaroSerializeable) {
								for(int i = 0; i < list.size(); i++) {
									Object listObject = list.get(i);

									VaroSerializeObject handl = getHandler((Class<?>) listObject.getClass());
									if(handl != null) {
										new VaroSerializer(saveUnder + "." + fieldIdent + "." + i, (VaroSerializeable) listObject, saveTo);
										continue;
									}
								}
								continue;
							}

						if(obj instanceof Long)
							obj = String.valueOf((long) obj);
					}

					if(field.getType() == Location.class) {
						Location loc = (Location) obj;
						saveTo.set(saveUnder + "." + fieldIdent + ".world", loc.getWorld().getName());
						saveTo.set(saveUnder + "." + fieldIdent + ".x", loc.getX());
						saveTo.set(saveUnder + "." + fieldIdent + ".y", loc.getY());
						saveTo.set(saveUnder + "." + fieldIdent + ".z", loc.getZ());
						continue;
					}

					VaroSerializeObject handl = getHandler((Class<?>) obj.getClass());
					if(handl != null) {
						new VaroSerializer(saveUnder + "." + fieldIdent, (VaroSerializeable) field.get(instance), saveTo);
						continue;
					}
				}

				if(obj == null)
					obj = NULL_REPLACE;

				saveTo.set(saveUnder + "." + fieldIdent, (obj instanceof Enum ? getStringByEnum((VaroSerializeable) obj) : obj));
			} catch(IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}