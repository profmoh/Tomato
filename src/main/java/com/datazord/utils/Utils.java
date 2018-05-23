package com.datazord.utils;

import java.util.Collection;
import java.util.Map;

public class Utils {

	public static boolean isEmptyMap(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static boolean isEmptyCollection(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
}
