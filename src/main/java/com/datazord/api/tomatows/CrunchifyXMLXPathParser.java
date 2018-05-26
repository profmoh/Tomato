package com.datazord.api.tomatows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @author Crunchify.com
 *
 */

public class CrunchifyXMLXPathParser {
	public static void main(String[] args) {
		String url = "http://41.32.215.227/tomato/TomatoWebService.asmx/GetAllItemsBySeason";
		String xml = crunchifyGetURLContents(url);
System.out.println(xml);
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xml)));

			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();

//			XPathExpression expr = xpath.compile(
//					"/company/company_name/company_item/size/aiow_detail/no_of_downloads[@name='facebook-members']");
//			String numberOfDownloads = expr.evaluate(document, XPathConstants.STRING).toString();
//			System.out.println(numberOfDownloads);

			NodeList allElements = (NodeList) xpath.evaluate("//*", document, XPathConstants.NODESET);

		    ArrayList<String> elementNames = new ArrayList<>();
		    ArrayList<String> elementValues = new ArrayList<>();

		    for (int i = 0; i < allElements.getLength(); i++)
		    {
		        Node currentElement = allElements.item(i);
		        elementNames.add(i, currentElement.getLocalName());
		        elementValues.add(i, xpath.evaluate("*", currentElement, XPathConstants.NODE) != null ? null : currentElement.getTextContent());
		    }

		    for (int i = 0; i < elementNames.size(); i++)
		    {
		        System.out.println(elementNames.get(i) + " : " + elementValues.get(i));
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String crunchifyGetURLContents(String myURL) {
		System.out.println("crunchifyGetURLContents() is hitting : " + myURL);

		InputStreamReader in = null;
		URLConnection urlConn = null;
		StringBuilder sb = new StringBuilder();

		try {
			URL url = new URL(myURL);

			urlConn = url.openConnection();
	
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);

				if (bufferedReader != null) {
					int cp;

					while ((cp = bufferedReader.read()) != -1)
						sb.append((char) cp);

					bufferedReader.close();
				}
			}

			in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:" + myURL, e);
		}

		return sb.toString();
	}
}