package de.cuuky.varo.serialize.field;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.cuuky.varo.serialize.identifier.NullClass;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class FieldLoader {

	private Map<Field, Class<? extends VaroSerializeable>> arrayTypes;
	private Map<String, Field> fields;

	public FieldLoader(Class<?> clazz) {
		this.fields = new HashMap<String, Field>();
		this.arrayTypes = new HashMap<Field, Class<? extends VaroSerializeable>>();

		Field[] declFields = clazz.getDeclaredFields();
		for (Field field : declFields) {
			if (field.getAnnotation(VaroSerializeField.class) == null)
				continue;

			VaroSerializeField anno = field.getAnnotation(VaroSerializeField.class);

			fields.put(anno.path(), field);

			if (Collection.class.isAssignableFrom(field.getType()) && anno.arrayClass() != NullClass.class)
				arrayTypes.put(field, anno.arrayClass());
		}
	}

	public Map<Field, Class<? extends VaroSerializeable>> getArrayTypes() {
		return arrayTypes;
	}

	public Map<String, Field> getFields() {
		return fields;
	}
}