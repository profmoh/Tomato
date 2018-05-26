package com.datazord.api.tomatows;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {
	public static void main(String[] args) throws Exception {
//		XMLReader myReader = XMLReaderFactory.createXMLReader();
//		myReader.setContentHandler(handler);
//		myReader.parse(new InputSource(new URL("http://41.32.215.227/tomato/TomatoWebService.asmx/GetAllItemsBySeason").openStream()));
		
		getHTTPXml(new URL("http://41.32.215.227/tomato/TomatoWebService.asmx/GetAllItemsBySeason"));
	}

	static void getHTTPXml(URL url) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("ACCEPT", "application/xml");

		InputStream xml = conn.getInputStream();
System.out.println("finished");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document document = builder.parse(xml);

		System.out.println(document);
		String doctype = conn.getContentType();
		System.out.println(doctype);

		XPathFactory pathFactory = XPathFactory.newInstance();
		XPath path = pathFactory.newXPath();

//		XPathExpression expression = path.compile("/DataTable/null:schema");
//
//		NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
//
//		String checkids[] = getNodeValue(nodeList);
//
//		for (String checkid : checkids) {
//			System.out.print(checkid + ", ");
//		}

		
		NodeList allElements = (NodeList) path.evaluate("//*", document, XPathConstants.NODESET);

		ArrayList<String> elementNames = new ArrayList<>();
		ArrayList<String> elementValues = new ArrayList<>();

		for (int i = 0; i < allElements.getLength(); i++) {
			Node currentElement = allElements.item(i);
			elementNames.add(i, currentElement.getLocalName());
			elementValues.add(i, path.evaluate("*", currentElement, XPathConstants.NODE) != null ? null : currentElement.getTextContent());
		}

		for (int i = 0; i < elementNames.size(); i++) {
			System.out.println(elementNames.get(i) + " : " + elementValues.get(i));
		}
		
		
		conn.disconnect();
	}

	static String[] getNodeValue(NodeList nodes) {
		String checkIds[] = new String[nodes.getLength()];
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			checkIds[i] = node.getTextContent();
		}
		return checkIds;
	}
}
