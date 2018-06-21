package com.datazord.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.datazord.comparators.JpathComparator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class FileUtils {

	public static void main(String[] args) {
		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			dbfac.setValidating(false);

			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.parse(new FileInputStream(new File("C:\\Users\\Mohamed\\Desktop\\Items.xml")));

//			Set<String> resultList = extractXPathList(doc);
//			
//			for (String string : resultList) {
//				System.out.println(string);
//			}

			List<String> jpathList = new ArrayList<>(extractXPathList(doc));

			String objectMainPath = getXPathIntersection(jpathList);

			List<JsonObject> jsonObjectList = extractNodeMapByPathList(doc, jpathList, objectMainPath);

			for(JsonObject jsonObject : jsonObjectList)
				System.out.println("\n\n" + new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static Set<String> extractXPathList(Document doc) {
		Set<String> resultList = new HashSet<>();

		try {
			XPath xPath = XPathFactory.newInstance().newXPath();

			NodeList nodeList = (NodeList) xPath.evaluate("//*", doc, XPathConstants.NODESET);

			for (int i = 0; i < nodeList.getLength(); i++)
				if(! nodeList.item(i).hasChildNodes() || nodeList.item(i).getFirstChild().getNodeType() == Node.TEXT_NODE) {
					String fullXPath = getFullPath(nodeList.item(i))/* + " :: " + nodeList.item(i).getTextContent())*/;
					
					if(fullXPath.contains(":: Items ::"))
						resultList.add(fullXPath);
				}
		} catch (Exception err) {
			throw new RuntimeException(err);
		}

		return resultList;
	}

	public static List<JsonObject> extractNodeMapByPathList(Document doc, List<String> xpathList, String objectMainPath) {
		xpathList.sort(new JpathComparator());

		JsonObject rootObject = new JsonObject();

		for(String xpath : xpathList)
			generateJsonObjectFromXPath(rootObject, xpath);

		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(rootObject));

		try {
			List<JsonObject> jsonObjectList = new ArrayList<>();
			XPath xPath = XPathFactory.newInstance().newXPath();

			NodeList nodeList = (NodeList) xPath.evaluate("//*", doc, XPathConstants.NODESET);

			JsonObject instanceObject = null;

			for (int i = 0; i < nodeList.getLength(); i++) {
				String fullXPath = getFullPath(nodeList.item(i))/* + " :: " + nodeList.item(i).getTextContent())*/;

				if(! xpathList.contains(fullXPath))
					continue;

				if(fullXPath.equals(objectMainPath)) {
					if(instanceObject != null)
						jsonObjectList.add(rootObject);

					instanceObject = deepCopy(rootObject, JsonObject.class);

					continue;
				} else
					setObjectValue(instanceObject, fullXPath, nodeList.item(i).getTextContent());
			}

			if(instanceObject != null)
				jsonObjectList.add(rootObject);

			return jsonObjectList;
		} catch (Exception err) {
			err.printStackTrace();
			throw new RuntimeException(err);
		}
	}

	private static void setObjectValue(JsonObject instanceObject, String xpath, String textContent) {
		String[] xpathArray = xpath.split(" :: ");
		String propertyName = xpathArray[xpathArray.length - 1];

		JsonObject currentObject = instanceObject;

		for(int i = 0; i < xpathArray.length - 2; i++)
			try {
				currentObject = currentObject.getAsJsonObject(xpathArray[i]);
			} catch (Exception e) {
				System.out.println(xpath + "   ^^^^^^^^^^^   " + textContent);
				return;
			}
//			currentObject = currentObject.getAsJsonObject(xpathArray[i]);

		currentObject.addProperty(propertyName, textContent);
	}

	private static void generateJsonObjectFromXPath(JsonObject rootObject, String xpath) {
		String[] xpathArray = xpath.split(" :: ");

		JsonObject parentJsonObject = rootObject;

		for(int i = 0; i < xpathArray.length; i++) {
			String itemName = xpathArray[i];

			if(i == xpathArray.length - 1) {
				parentJsonObject.addProperty(itemName, "");						
			} else {
				JsonObject jsonObject;

				try {
					jsonObject = parentJsonObject.getAsJsonObject(itemName);
				} catch (Exception e) {
					parentJsonObject.remove(itemName);
					jsonObject = null;
				}
				
				if(jsonObject == null) {
					parentJsonObject.add(itemName, new JsonObject());
					jsonObject = parentJsonObject.getAsJsonObject(itemName);
				}
				
				parentJsonObject = jsonObject;
			}
		}
	}

	private static String getXPathIntersection(List<String> xpathList) {
		if(Utils.isEmptyCollection(xpathList))
			return null;

		String firstString = xpathList.get(0);
		String[] firstStringSplit = firstString.split(" :: ");

		if(xpathList.size() == 1) {
			String[] result = removeLastElementOfArray(firstStringSplit);
			return getPathFromArray(result);
		}

		String[] result = firstStringSplit;

		for(int i = 1; i < xpathList.size(); i++) {
			String string = xpathList.get(i);
			String[] stringSplit = string.split(" :: ");

			List<String> currentResult = new ArrayList<>();

			for(int j = 0; j < stringSplit.length; j++) {
				if(j >= result.length || ! result[j].equals(stringSplit[j]))
					break;

				currentResult.add(stringSplit[j]);
			}

			result = new String[currentResult.size()];
			result = currentResult.toArray(result);
		}

		return getPathFromArray(result);
	}

	private static String getPathFromArray(String[] pathArray) {
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < pathArray.length - 1; i++)
			sb.append(pathArray[i] + " :: ");

		sb.append(pathArray[pathArray.length - 1]);

		return sb.toString();
	}

	public static String[] removeLastElementOfArray(String[] array) {
		String[] result = new String[array.length - 1];

		for(int i = 0; i < result.length; i++)
			result[i] = array[i];

		return result;
	}

	private static String getFullPath(Node node) {
		if(node.getParentNode() == null)
			return node.getNodeName();

		return getFullPath(node.getParentNode()) + " :: " + node.getNodeName();
	}

	public static <T> T deepCopy(T object, Class<T> type) {
	    try {
	        Gson gson = new Gson();
	        return gson.fromJson(gson.toJson(object, type), type);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
