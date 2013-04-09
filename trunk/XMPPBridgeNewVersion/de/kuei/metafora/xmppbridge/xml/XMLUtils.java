package de.kuei.metafora.xmppbridge.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.Charset;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//VipTool class for handling xml
public class XMLUtils {
	public static Document createDocument() throws XMLException {
		DocumentBuilder builder;
		DocumentBuilderFactory factory;

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();

		} catch (ParserConfigurationException exception) {
			throw new XMLException(exception);
		}

		return builder.newDocument();
	}

	//new method, based on storeXMLFile
	public static String documentToString(Document document, String dtd)
			throws XMLException {
		Writer writer;
		Transformer transformer;
		TransformerFactory factory = TransformerFactory.newInstance();

		try {
			// factory.setAttribute("indent-number", new Integer(4));
			transformer = factory.newTransformer();
			// set use whitespaces
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// set output-type xml
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			// Content-Type
			transformer.setOutputProperty(OutputKeys.MEDIA_TYPE, "text/xml");
			// set encoding
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");

			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtd);

			// create file writer
			writer = new StringWriter();

			transformer.transform(new DOMSource(document), new StreamResult(
					writer));

			return writer.toString();
		} catch (TransformerConfigurationException exception) {
			throw new XMLException(exception);
		} catch (TransformerException exception) {
			throw new XMLException(exception);
		}
	}

	//new method, based on storeXMLFile
	public static String documentToString(Document document)
			throws XMLException {
		Writer writer;
		Transformer transformer;
		TransformerFactory factory = TransformerFactory.newInstance();

		try {
			// factory.setAttribute("indent-number", new Integer(4));
			transformer = factory.newTransformer();
			// set use whitespaces
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// set output-type xml
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			// Content-Type
			transformer.setOutputProperty(OutputKeys.MEDIA_TYPE, "text/xml");
			// set encoding
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");

			// create file writer
			writer = new StringWriter();

			transformer.transform(new DOMSource(document), new StreamResult(
					writer));

			return writer.toString();
		} catch (TransformerConfigurationException exception) {
			throw new XMLException(exception);
		} catch (TransformerException exception) {
			throw new XMLException(exception);
		}
	}

	public static void storeXMLFile(URI file, Document document)
			throws XMLException {
		Writer writer;
		Transformer transformer;
		TransformerFactory factory = TransformerFactory.newInstance();

		try {
			factory.setAttribute("indent-number", new Integer(4));
			transformer = factory.newTransformer();
			// set use whitespaces
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// set output-type xml
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			// Content-Type
			transformer.setOutputProperty(OutputKeys.MEDIA_TYPE, "text/xml");
			// set encoding
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");

			// create file writer
			writer = new OutputStreamWriter(
					new FileOutputStream(new File(file)), Charset.forName(
							"utf-8").newEncoder());

			transformer.transform(new DOMSource(document), new StreamResult(
					writer));
		} catch (TransformerConfigurationException exception) {
			throw new XMLException(exception);
		} catch (TransformerException exception) {
			throw new XMLException(exception);
		} catch (FileNotFoundException exception) {
			throw new XMLException(exception);
		}
	}

	public static Document parseXMLFile(URI file) throws XMLException {
		DocumentBuilder documentBuilder;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);

		try {
			documentBuilder = factory.newDocumentBuilder();

			return documentBuilder.parse(file.toString());
		} catch (SAXException exception) {
			throw new XMLException(exception);
		} catch (ParserConfigurationException exception) {
			throw new XMLException(exception);
		} catch (IOException exception) {
			throw new XMLException(exception);
		}
	}

	public static Node findChildNode(Node node, String name) {
		Node child;
		NodeList list;

		list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			child = list.item(i);
			if (child instanceof Element && child.getNodeName().equals(name)) {
				return child;
			}
		}

		return null;
	}

	public static Document parseXMLFile(URI file, URI schema)
			throws XMLException {
		DocumentBuilder documentBuilder;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setNamespaceAware(true);
		factory.setIgnoringElementContentWhitespace(true);

		try {
			factory.setSchema(schemaFactory.newSchema(schema.toURL()));
			documentBuilder = factory.newDocumentBuilder();

			return documentBuilder.parse(file.toString());
		} catch (SAXException exception) {
			throw new XMLException(exception);
		} catch (ParserConfigurationException exception) {
			throw new XMLException(exception);
		} catch (IOException exception) {
			throw new XMLException(exception);
		}
	}

	//new method, based on parseXMLFile
	public static Document parseXMLString(String xml, boolean validating)
			throws XMLException {
		
		validating = false;
		
		DocumentBuilder documentBuilder;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(validating);
		factory.setIgnoringElementContentWhitespace(true);

		try {
			documentBuilder = factory.newDocumentBuilder();
			documentBuilder.setErrorHandler(new SaxErrorHandler());

			ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes());
			return documentBuilder.parse(in);
		} catch (SAXException exception) {
			throw new XMLException(exception);
		} catch (ParserConfigurationException exception) {
			throw new XMLException(exception);
		} catch (IOException exception) {
			throw new XMLException(exception);
		}
	}

	//new method, based on parseXMLFile
	public static Document parseXMLString(String xml, URI schema,
			boolean validating) throws XMLException {
		DocumentBuilder documentBuilder;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setValidating(validating);
		factory.setNamespaceAware(true);
		factory.setIgnoringElementContentWhitespace(true);

		try {
			factory.setSchema(schemaFactory.newSchema(schema.toURL()));
			documentBuilder = factory.newDocumentBuilder();
			documentBuilder.setErrorHandler(new SaxErrorHandler());

			ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes());
			return documentBuilder.parse(in);
		} catch (SAXException exception) {
			throw new XMLException(exception);
		} catch (ParserConfigurationException exception) {
			throw new XMLException(exception);
		} catch (IOException exception) {
			throw new XMLException(exception);
		}
	}

	public static Element findFirstElement(Document document, String name)
			throws XMLException {
		NodeList list;

		list = document.getElementsByTagName(name);
		if (list.getLength() == 0) {
			throw new XMLException("Element '" + name
					+ "' not found in document!");
		}

		return (Element) list.item(0);
	}

	// <><><><><><><><><><><><><><><><><><><><><>
	// supporting different formations of xml files
	// <><><><><><><><><><><><><><><><><><><><><>
	/**
	 * Method provides getting Element objects out of a NodeList. This method is
	 * useful if the xml file is formated with whitespaces because they are
	 * interpreted by the DOM parser as text nodes. So especially calls to<br>
	 * getFirstChild(), getLastChild(), etc<br>
	 * will not surely return elements. To be sure, that you will get an Element
	 * use this method by giving the list of child nodes
	 * (parent.getChildNodes()) and the parent node (necessary so that only the
	 * direct child will be returned and no deeper ones).<br>
	 * If no Element is in the list that is a direct child of parent null will
	 * be returned.
	 * 
	 * @param childs
	 *            a NodeList that contains all nodes which should be checked
	 *            wether they are elements and direct childs of the given
	 *            parent. The first node that matches these criterions will be
	 *            returned
	 * @param parent
	 *            the parent node of the given child nodes
	 * 
	 * @return the first Element of the given NodeList whose parent node is the
	 *         given parent. If no such Element exists null will be returned
	 */
	public static Element getChildElement(NodeList childs, Node parent) {
		Node childNode;
		String parentName;

		parentName = parent.getNodeName();

		for (int i = 0; i < childs.getLength(); i++) {
			childNode = childs.item(i);

			if ((childNode instanceof Element)
					&& (childNode.getParentNode().getNodeName()
							.equals(parentName))) {
				return (Element) childNode;
			}
		}

		return null;
	}
}
