package de.cuuky.varo.serialize;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class VaroSerializeHandler {

	protected static Map<String, YamlConfiguration> configs;
	protected static Map<VaroSerializeable, String> enumsRepl;
	protected static Map<String, File> files;
	protected static List<VaroSerializeObject> handler;
	protected static final String NULL_REPLACE;

	static {
		NULL_REPLACE = "nullReplace";

		handler = new ArrayList<VaroSerializeObject>();
		enumsRepl = new HashMap<VaroSerializeable, String>();
		configs = new HashMap<String, YamlConfiguration>();
		files = new HashMap<String, File>();
	}

	protected static VaroSerializeable getEnumByString(String en) {
		for(VaroSerializeable var : enumsRepl.keySet())
			if(en.equals(enumsRepl.get(var)))
				return var;

		return null;
	}

	protected static VaroSerializeObject getHandler(Class<?> clazz) {
		for(VaroSerializeObject handl : handler)
			if(handl.getClazz().equals(clazz))
				return handl;

		return null;
	}

	protected static String getStringByEnum(VaroSerializeable ser) {
		for(VaroSerializeable var : enumsRepl.keySet())
			if(ser.equals(var))
				return enumsRepl.get(var);

		return null;
	}

	protected static void registerClass(Class<? extends VaroSerializeable> clazz) {
		new VaroSerializeObject(clazz);
	}

	protected static void registerEnum(Class<? extends VaroSerializeable> clazz) {
		for(Field field : clazz.getDeclaredFields()) {
			VaroSerializeField anno = field.getAnnotation(VaroSerializeField.class);
			if(anno == null)
				continue;

			try {
				enumsRepl.put((VaroSerializeable) field.get(clazz), anno.enumValue());
			} catch(IllegalArgumentException e) {
				e.printStackTrace();
			} catch(IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveAll() {
		for(VaroSerializeObject handl : handler)
			if(handl.getConfiguration() != null)
				handl.onSave();
	}
}