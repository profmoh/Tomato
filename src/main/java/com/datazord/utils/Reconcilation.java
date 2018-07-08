package com.datazord.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.datazord.enums.ReconcilationResult;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class Reconcilation {

	public static <T> List<T> compare(List<T> mainGroup, List<T> disGroup, List<String> compareAtributes)
			throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {

		if (Utils.isEmptyCollection(mainGroup) && Utils.isEmptyCollection(disGroup))
			return null;

		Class<?> tClass = (Utils.isEmptyCollection(disGroup)) ? mainGroup.get(0).getClass() : disGroup.get(0).getClass();

		Field idField = tClass.getDeclaredField("id");
		Field mainIdField = tClass.getDeclaredField("mainId");
		Field reconcileResultField = tClass.getDeclaredField("reconcilationResult");

		List<T> resultList = new ArrayList<T>();

		if (Utils.isEmptyCollection(mainGroup)) {
			for (T t : disGroup) {
				reconcileResultField.set(t, ReconcilationResult.added);

				resultList.add(t);
			}

			return resultList;
		}

		if (Utils.isEmptyCollection(disGroup)) {
			for (T t : mainGroup) {
				reconcileResultField.set(t, ReconcilationResult.removed);

				resultList.add(t);
			}

			return resultList;
		}

		for (T dis : disGroup) {
			Collection<T> equalityCollection = Collections2.filter(mainGroup, find(dis, compareAtributes));

			if (equalityCollection.size() > 0) {
				reconcileResultField.set(dis, ReconcilationResult.identical);

				T t = equalityCollection.iterator().next();

				Object recid = idField.get(t);

				mainIdField.set(dis, recid);

				resultList.add(dis);
				mainGroup.remove(t);
			} else {
				reconcileResultField.set(dis, ReconcilationResult.added);

				resultList.add(dis);
				continue;
			}
		}

		for (T t : mainGroup) {
			reconcileResultField.set(t, ReconcilationResult.removed);

			Object recid = idField.get(t);
			mainIdField.set(t, recid);

			resultList.add(t);
		}

		return resultList;
	}

	private static <T> Predicate<T> find(final T dis, final List<String> fieldNames) {
		return new Predicate<T>() {
			public boolean apply(T obj) {
				String valueV;
				String valueObj;

				boolean condition = true;

				Class<?> tClass = dis.getClass();

				for (String fieldName : fieldNames) {
					try {
						valueV = (String) tClass.getDeclaredField(fieldName).get(dis);
						valueObj = (String) tClass.getDeclaredField(fieldName).get(obj);

						condition = (condition && valueV.equalsIgnoreCase(valueObj));
					} catch (Exception e) {
						return false;
					}
				}

				return condition;
			}
		};
	}
}
