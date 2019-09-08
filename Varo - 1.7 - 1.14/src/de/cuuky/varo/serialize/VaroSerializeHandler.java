package de.cuuky.varo.serialize;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.serialize.identifier.NullClass;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class VaroSerializeHandler {
	
	/*
	 * Pls dont look too close at this class.
	 * I made this withing like 4 hours and it works
	 * so I don't wanna change anything.
	 * I know, this is pretty ugly and too much code
	 * in one class but I'm really happy it works like this
	 */

	private static final String NULL_REPLACE = "nullReplace";
	
	private static List<VaroSerializeHandler> handler;
	private static Map<String, YamlConfiguration> configs;
	private static Map<String, File> files;
	private static Map<VaroSerializeable, String> enumsRepl;

	static {
		handler = new ArrayList<VaroSerializeHandler>();
		configs = new HashMap<String, YamlConfiguration>();
		files = new HashMap<String, File>();
		enumsRepl = new HashMap<VaroSerializeable, String>();
	}

	private Class<? extends VaroSerializeable> clazz;
	private Map<String, Field> fields;
	private Map<Field, Class<? extends VaroSerializeable>> arrayTypes;
	private File file;
	private YamlConfiguration configuration;

	public VaroSerializeHandler(Class<? extends VaroSerializeable> clazz, String fileName) {
		this.clazz = clazz;

		if(files.get(fileName) != null) {
			this.file = files.get(fileName);
			this.configuration = configs.get(fileName);
		} else {
			this.file = new File("plugins/Varo", fileName);
			files.put(fileName, file);
			this.configuration = YamlConfiguration.loadConfiguration(file);
			configs.put(fileName, configuration);
		}

		loadFields();
		handler.add(this);
	}

	public VaroSerializeHandler(Class<? extends VaroSerializeable> clazz) {
		this.clazz = clazz;

		loadFields();
		handler.add(this);
	}

	private void loadFields() {
		this.fields = new HashMap<String, Field>();
		this.arrayTypes = new HashMap<Field, Class<? extends VaroSerializeable>>();

		Field[] declFields = clazz.getDeclaredFields();
		for(Field field : declFields) {
			if(field.getAnnotation(VaroSerializeField.class) == null)
				continue;

			VaroSerializeField anno = field.getAnnotation(VaroSerializeField.class);

			fields.put(anno.path(), field);

			if(Collection.class.isAssignableFrom(field.getType()) && anno.arrayClass() != NullClass.class)
				arrayTypes.put(field, anno.arrayClass());
		}
	}

	public void load() {
		for(String string : configuration.getKeys(true)) {
			Object obj = configuration.get(string);
			if(obj instanceof MemorySection) {
				if(string.contains("."))
					continue;

				deserialize((MemorySection) obj);
			}
		}
	}

	public VaroSerializeable deserialize(MemorySection section) {
		VaroSerializeable instance = null;
		ArrayList<String> handled = new ArrayList<String>();
		sectionLoop: for(String s : section.getKeys(true)) {
			for(String handl : handled)
				if(s.contains(handl))
					continue sectionLoop;

			Field field = fields.get(s);
			if(field != null) {
				if(instance == null)
					try {
						instance = clazz.newInstance();
					} catch(InstantiationException | IllegalAccessException e1) {
						e1.printStackTrace();
						continue;
					}

				try {
					field.setAccessible(true);

					// CHECK FOR OTHER SERIALIZABLE OBJ
					Object obj = section.get(s);
					if(obj instanceof String)
						if(((String) obj).equals(NULL_REPLACE))
							obj = null;

					VaroSerializeHandler handl = getHandler((Class<?>) field.getType());
					if(handl != null && obj != null) {
						handled.add(s);
						field.set(instance, handl.deserialize((MemorySection) obj));
						continue;
					}

					if(Map.class.isAssignableFrom(field.getType())) {
						obj = section.getConfigurationSection(s).getValues(false);
						handled.add(s);
					}

					if(field.getType() == Location.class) {
						if(obj != null)
							obj = new Location(Bukkit.getWorld(section.getString(s + ".world")), (double) section.get(s + ".x"), (double) section.get(s + ".y"), (double) section.get(s + ".z"));

						handled.add(s);
					}

					if(Collection.class.isAssignableFrom(field.getType())) {
						Class<?> clazz = arrayTypes.get(field);
						if(clazz != null) {
							handl = getHandler(arrayTypes.get(field));
							if(handl != null) {
								handled.add(s);
								ArrayList<VaroSerializeable> newList = new ArrayList<VaroSerializeable>();
								if(obj instanceof MemorySection) {
									MemorySection listSection = ((MemorySection) obj);
									for(String listStr : listSection.getKeys(true)) {
										Object listEntry = listSection.get(listStr);
										if(!(listEntry instanceof MemorySection) || listStr.contains("."))
											continue;

										newList.add(handl.deserialize((MemorySection) listEntry));
										continue;
									}
								}

								field.set(instance, newList);
								continue sectionLoop;
							}
						}
					}

					if(field.getType().isEnum() && obj instanceof String) {
						VaroSerializeable ser = getEnumByString((String) obj);
						if(ser != null)
							obj = ser;
					}

					if(field.getType().isPrimitive() && obj instanceof String)
						obj = Long.valueOf((String) obj);

					field.set(instance, obj);
				} catch(IllegalArgumentException | IllegalAccessException | ExceptionInInitializerError | NullPointerException e) {
					e.printStackTrace();
					continue;
				}
			} else
				System.out.println(Main.getConsolePrefix() + "Could not deserialize field " + s + ": [FIELD NOT FOUND]");
		}

		instance.onDeserializeEnd();
		return instance;
	}

	protected void clearOld() {
		Map<String, Object> configValues = configuration.getValues(false);
		for(Map.Entry<String, Object> entry : configValues.entrySet())
			configuration.set(entry.getKey(), null);
	}

	protected void saveFile() {
		try {
			configuration.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public Class<? extends VaroSerializeable> getClazz() {
		return clazz;
	}

	public Map<String, Field> getFields() {
		return fields;
	}

	public YamlConfiguration getConfiguration() {
		return configuration;
	}

	public void onSave() {}

	public void save(String saveUnder, VaroSerializeable instance, YamlConfiguration saveTo) {
		VaroSerializeHandler handler = getHandler(instance.getClass());
		instance.onSerializeStart();

		ArrayList<String> list1 = new ArrayList<String>();
		list1.addAll(handler.getFields().keySet());
		Collections.reverse(list1);
		for(String fieldIdent : list1) {
			try {
				Field field = handler.getFields().get(fieldIdent);
				field.setAccessible(true);

				Object obj = field.get(instance);
				if(obj != null) {
					if(obj instanceof List) {
						ArrayList<?> list = (ArrayList<?>) obj;
						if(!list.isEmpty())
							if(list.get(0) instanceof VaroSerializeable) {
								for(int i = 0; i < list.size(); i++) {
									Object listObject = list.get(i);

									VaroSerializeHandler handl = getHandler((Class<?>) listObject.getClass());
									if(handl != null) {
										handl.save(saveUnder + "." + fieldIdent + "." + i, (VaroSerializeable) listObject, saveTo);
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

					VaroSerializeHandler handl = getHandler((Class<?>) obj.getClass());
					if(handl != null) {
						handl.save(saveUnder + "." + fieldIdent, (VaroSerializeable) field.get(instance), saveTo);
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

	private static String getStringByEnum(VaroSerializeable ser) {
		for(VaroSerializeable var : enumsRepl.keySet())
			if(ser.equals(var))
				return enumsRepl.get(var);

		return null;
	}

	private static VaroSerializeable getEnumByString(String en) {
		for(VaroSerializeable var : enumsRepl.keySet())
			if(en.equals(enumsRepl.get(var)))
				return var;

		return null;
	}

	private static VaroSerializeHandler getHandler(Class<?> clazz) {
		for(VaroSerializeHandler handl : handler)
			if(handl.getClazz().equals(clazz))
				return handl;

		return null;
	}

	public static void saveAll() {
		for(VaroSerializeHandler handl : handler)
			if(handl.getConfiguration() != null)
				handl.onSave();
	}

	public static void registerClass(Class<? extends VaroSerializeable> clazz) {
		new VaroSerializeHandler(clazz);
	}

	public static void registerEnum(Class<? extends VaroSerializeable> clazz) {
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
}