package com.datazord.utils;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.datazord.json.tomato.pojo.product.Product;
import com.google.gson.JsonObject;

public class JsonUtils {

//	public static void main(String[] args) {
//		Product product = new Product();
//
//		productCustomOption p = new productCustomOption();
//
//		p.setImage("image");
//		product.setProduct_custom_option(Arrays.asList(p));
//
//		setObjectByValueAndPath(product, "product.product_custom_option.image", "sdd");
//		
//		
//		
//		
//		List<JsonObject> jsonObjectList = null;
//
//		try {
//			jsonObjectList = FileUtils.readJsonObjectsFormXML("C:\\Users\\Mohamed\\Desktop\\Items.xml");
//		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
//			e.printStackTrace();
//			return;
//		}
//
//		for (JsonObject jsonObject : jsonObjectList) {
//			System.out.println("\n\n" + new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject));
//			System.out.println("\n\n\n");
//			System.out.println(getStringValueFromJsonByPath(jsonObject, "#document :: DataTable :: diffgr:diffgram :: DocumentElement :: Items :: group_name"));
//			break;
//		}
//	}

	public static String getStringValueFromJsonByPath(JsonObject jsonObject, String path) {
		if(StringUtils.isAllBlank(path) || jsonObject == null)
			return null;

		String[] jpathArray = path.split(" :: ");
		String propertyName = jpathArray[jpathArray.length - 1];

		JsonObject currentObject = jsonObject;

		for (int i = 0; i < jpathArray.length - 1; i++)
			try {
				currentObject = currentObject.getAsJsonObject(jpathArray[i]);
			} catch (Exception e) {
				System.out.println(path + "   ^^^^^^^^^^^   " + jpathArray[i]);
				return null;
			}

		return currentObject.get(propertyName).getAsString().trim(); //.addProperty(propertyName, textContent)
	}

	public static void setObjectByValueAndPath(Product product, String path, Object value) {
		if(StringUtils.isAllBlank(path) || value == null || product == null)
			return;

		String[] pathArray = path.split("\\.");

		Object currentClass = product;
		Class<?> objClass = currentClass.getClass();

		for(int i = 1; i < pathArray.length; i++) {
			try {
				Field field = objClass.getDeclaredField(pathArray[i]);

				if(i == pathArray.length - 1) {
					field.set(currentClass, value);
					break;
				} else
					currentClass = field.get(currentClass);

				objClass = currentClass.getClass();

				if(currentClass instanceof Collection<?>) {
					Object[] objectArray = ((Collection<?>)currentClass).toArray();
					currentClass = objectArray[objectArray.length - 1];
					objClass = currentClass.getClass();
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
