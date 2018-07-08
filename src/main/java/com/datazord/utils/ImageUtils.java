package com.datazord.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ImageUtils {
	public static void main(String[] args) {

		String imageUrl = "http://www.technicalkeeda.com/img/images/article/spring-file-upload-eclipse-setup.png";
		String downLoadpath = "D:";

		try {
			downloadImage(imageUrl, "spring-file-upload-eclipse-setup.jpg", downLoadpath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String downloadImage(String imageUrl, String imageName, String downLoadpath) throws IOException {
		String imagePath = downLoadpath + File.separator + imageName;

		File file = new File(imagePath);

		if(file.exists())
			return imagePath;

		InputStream inputStream = null;
		OutputStream outputStream = null;

		URL url = new URL(imageUrl);

		inputStream = url.openStream();
		outputStream = new FileOutputStream(imagePath);

		int length;
		byte[] buffer = new byte[2048];

		while ((length = inputStream.read(buffer)) != -1)
			outputStream.write(buffer, 0, length);

		inputStream.close();
		outputStream.close();

		return imagePath;
	}
}
