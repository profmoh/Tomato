package com.datazord.service.Impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.datazord.json.tomato.pojo.product.Product;
import com.datazord.json.tomato.pojo.product.ProductAttribute;
import com.datazord.json.tomato.pojo.product.ProductAttributeDescription;
import com.datazord.json.tomato.pojo.product.ProductDescription;
import com.datazord.json.tomato.pojo.product.ProductDiscount;
import com.datazord.json.tomato.pojo.product.ProductOption;
import com.datazord.json.tomato.pojo.product.ProductOptionValue;
import com.datazord.json.tomato.pojo.product.ProductSeoUrl;
import com.datazord.json.tomato.pojo.product.ProductSpecial;
import com.datazord.service.ProductService;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class ProductServiceImpl implements ProductService{

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
			
	@Override
	public void getParamterPathFromObject(){
		
	    Field[] fields = Product.class.getDeclaredFields();
	    List<String>productParmaeterPath=new ArrayList<>();

	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {	
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            if(annotationValue.equals("product_seo_url")|| annotationValue.equals("product_description") || annotationValue.equals("product_special")
	            	|| annotationValue.equals("product_discount") || annotationValue.equals("product_attribute")  || annotationValue.equals("product_option"))
	            	continue;
	            String parameterPath="Product."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
		
	    fields = ProductSeoUrl.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_seo_url."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    
	    
	    fields = ProductSpecial.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_special."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    fields = ProductDescription.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_description."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    
	    fields = ProductDiscount.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_discount."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    fields = ProductAttribute.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            if(annotationValue.equals("product_attribute_description"))
	                continue;
	            String parameterPath="Product.product_attribute."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    fields = ProductAttributeDescription.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_attribute.product_attribute_description."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    fields = ProductOption.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            if(annotationValue.equals("product_option_value"))
	            	continue;
	            String parameterPath="Product.product_option."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    fields = ProductOptionValue.class.getDeclaredFields();
	    for (Field field : fields) {
	        if (field.isAnnotationPresent(JsonProperty.class)) {
	            String annotationValue = field.getAnnotation(JsonProperty.class).value();
	            String parameterPath="Product.product_option.product_option_value."+annotationValue;
	            productParmaeterPath.add(parameterPath);
	            
	        }
	    }
	    
	    productParmaeterPath.forEach(param->System.out.println(param));
	}

	
	
	@Override
	public void parseProductXml() {
//		try {
//			/*File xmlFile = new File("E:\\my private Work\\TomatoWorkSpace\\Items.xml");
//			
//			JAXBContext jaxbContext = JAXBContext.newInstance(DocumentElement.class);
//			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//			DocumentElement d =(DocumentElement) jaxbUnmarshaller.unmarshal(xmlFile);
//			System.out.println(d);*/
//			
//			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//			InputStream iStream = new FileInputStream(new File("E:\\my private Work\\TomatoWorkSpace\\Items.xml"));
//			Document document = documentBuilderFactory.newDocumentBuilder().parse(iStream);
//			StringWriter stringWriter = new StringWriter();
//			Transformer transformer = TransformerFactory.newInstance().newTransformer();
//			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "false");
//			transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
//			String output = stringWriter.toString();
//			
//			
//			String xmlDoc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Body>This is my content</Body>";
//	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//	        DocumentBuilder builder = factory.newDocumentBuilder();
//	        String value = builder.parse(new InputSource(new ByteArrayInputStream(xmlDoc.getBytes())))
//	                .getDocumentElement().getTextContent();
//			System.out.println(value);
//		} catch (Exception e) {
//			logger.error("", e);
//		}
		
//		try {
//	         File inputFile = new File("E:\\my private Work\\TomatoWorkSpace\\Items.xml");
//	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//	         DocumentBuilder dBuilder;
//
//	         dBuilder = dbFactory.newDocumentBuilder();
//
//	         Document doc = dBuilder.parse(inputFile);
//	         doc.getDocumentElement().normalize();
//
//	         XPath xPath =  XPathFactory.newInstance().newXPath();
//
//	         String expression = "/DocumentElement/Items";	        
//	         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
//	            doc, XPathConstants.NODESET);
//
//	         for (int i = 0; i < nodeList.getLength(); i++) {
//	            Node nNode = nodeList.item(i);
//	            System.out.println("\nCurrent Element :" + nNode.getNodeName());
//	            
//	         }
//	         
//	         
//	         
//	      } catch (ParserConfigurationException e) {
//	         e.printStackTrace();
//	      } catch (SAXException e) {
//	         e.printStackTrace();
//	      } catch (IOException e) {
//	         e.printStackTrace();
//	      } catch (XPathExpressionException e) {
//	         e.printStackTrace();
//	      }
//		
		
		try{
			
		    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		    docBuilderFactory.setNamespaceAware(true);

		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

		    Document doc = docBuilder.parse("E:\\my private Work\\TomatoWorkSpace\\Items2.xml");

		    XPathFactory fact = XPathFactory.newInstance();
		    XPath xpath = fact.newXPath();

		    NodeList allElements = (NodeList)xpath.evaluate("//*", doc, XPathConstants.NODESET);

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
		        System.out.println("Name: " + elementNames.get(i) + "; value: " + (elementValues.get(i)));
		    }
		    
		}catch (Exception e) {
			 e.printStackTrace();
		}
		
		
		
	}
	
}
