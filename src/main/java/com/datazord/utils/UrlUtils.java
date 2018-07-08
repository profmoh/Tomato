package com.datazord.utils;

public class UrlUtils {

	public static String getNameFromUrl(String url) {
		String[] urlArray = url.split("/");
		return urlArray[urlArray.length - 1];
	}
}
