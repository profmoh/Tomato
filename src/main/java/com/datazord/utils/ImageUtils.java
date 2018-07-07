package com.datazord.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUtils {
	public static void main(String[] args) {

		String imageUrl = "http://www.technicalkeeda.com/img/images/article/spring-file-upload-eclipse-setup.png";
		String downLoadpath = "D:\\spring-file-upload-eclipse-setup.jpg";

		downloadImage(imageUrl, downLoadpath);
	}

	private static void downloadImage(String imageUrl, String downLoadpath) {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			URL url = new URL(imageUrl);

			inputStream = url.openStream();
			outputStream = new FileOutputStream(downLoadpath);

			int length;
			byte[] buffer = new byte[2048];

			while ((length = inputStream.read(buffer)) != -1)
				outputStream.write(buffer, 0, length);

		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException :- " + e.getMessage());

		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException :- " + e.getMessage());

		} catch (IOException e) {
			System.out.println("IOException :- " + e.getMessage());

		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				System.out.println("Finally IOException :- " + e.getMessage());
			}
		}
	}
}
