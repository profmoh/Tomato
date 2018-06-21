package com.datazord.comparators;

import java.util.Comparator;

public class JpathComparator implements Comparator<String> {

	@Override
	public int compare(String jpath1, String jpath2) {
		int jpath1Length = jpath1.split(" :: ").length;
		int jpath2Length = jpath2.split(" :: ").length;

		return jpath1Length - jpath2Length;
	}
}