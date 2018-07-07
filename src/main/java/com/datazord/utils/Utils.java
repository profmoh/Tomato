package com.datazord.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

public class Utils {

	public static boolean isEmptyMap(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static boolean isEmptyCollection(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
	
	public static boolean isNotEmpty(String obj) {
		return obj != null && obj.length() != 0;
	}

	public static boolean isNotEmpty(List<?> obj) {
		return obj != null && obj.size() != 0;
	}

	public static boolean isNotEmpty(Object obj) {
		return obj != null;
	}
	
	public static boolean isEmpty(Object obj) {
		return obj == null;
	}

	public static boolean isEmptyString(String string) {
		return string == null || string.isEmpty() || string == "";
	}

	public static boolean isNullorEmpty(String object) {
		if (object != null && !object.equals("") && !object.equals("null") && !object.equals(null)) {
			return false;
		}
		return true;
	}

	public static <T> Set<Object> getDistinctFieldByFieldName(final List<T> objList, final String fieldName) {
		if (objList == null || objList.size() == 0 || StringUtils.isBlank(fieldName))
			return Sets.newHashSet();

		final Class<?> tClass = objList.get(0).getClass();

		return Sets.newHashSet(Lists.transform(objList, new Function<T, Object>() {
			public Object apply(T t) {
				try {
					return tClass.getDeclaredField(fieldName).get(t);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}));
	}

	public static <T, O> Set<O> getDistinctFieldByGetterName(final List<T> objList, final String getterName) {
		if(objList == null || objList.size() == 0 || StringUtils.isBlank(getterName))
			return Sets.newHashSet();

		final Class<?> tClass = objList.get(0).getClass();

		return Sets.newHashSet(
				Lists.transform(objList, new Function<T, O>() {
					@SuppressWarnings("unchecked")
					public O apply(T t) {
						try {
							return (O) tClass.getDeclaredMethod(getterName).invoke(t);
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
				}));
	}

	public static Set<String> getDistinctFieldByFieldNameInJson(final List<JsonObject> objList, final String fieldPath) {
		if (isEmptyCollection(objList) || StringUtils.isBlank(fieldPath))
			return Sets.newHashSet();

		String[] pathArray = fieldPath.split(" :: ");
		String propertyName = pathArray[pathArray.length - 1];

		return Sets.newHashSet(Lists.transform(objList, new Function<JsonObject, String>() {
			public String apply(JsonObject jsonObject) {
				try {
					JsonObject currentObject = jsonObject;

					for (int i = 0; i < pathArray.length - 1; i++)
						currentObject = currentObject.getAsJsonObject(pathArray[i]);

					// currentObject.addProperty(propertyName, textContent);

					return currentObject.get(propertyName).getAsString().trim()/*.toLowerCase()*/;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}));
	}
}
