package com.datazord.comparators;

import java.util.Comparator;

public class JpathComparator implements Comparator<String> {

	private String spliter = "";

	public JpathComparator(String spliter) {
		this.spliter = spliter;
	}

	@Override
	public int compare(String jpath1, String jpath2) {
		int jpath1Length = jpath1.split(spliter).length;
		int jpath2Length = jpath2.split(spliter).length;

		return jpath1Length - jpath2Length;
	}
}