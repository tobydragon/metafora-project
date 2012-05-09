package de.uds.MonitorInterventionMetafora.server.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import de.uds.MonitorInterventionMetafora.server.utils.ErrorUtil;
import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;

//wrapper class for an Element in JDOM
//catches and prints errors, no attributes may be null

public class  XmlFragment {
	static Logger logger = Logger.getLogger(XmlFragment.class);

	
	static SAXBuilder builder = new SAXBuilder();
	
	Document doc;
	Element element;
	
	public static XmlFragment getFragmentFromLocalFile(String filename) {
		logger.debug("[getFragmentFromLocalFile] Working Directory - " + System.getProperty("user.dir"));
		try {
			Document doc = builder.build(new File(filename));
			return getFragment(doc);
		}
		catch (Exception e){
   		 	logger.info("[getFragmentFromLocalFile] returning null (file proabably does not exist) " + e);
   		 	logger.debug("[getFragmentFromLocalFile] " + ErrorUtil.getStackTrace(e));
   		 	return null;
		}
		
	}
	
	public static XmlFragment getFragmentFromRemoteFile(String urlString) {
		try {
			URL url = new URL(urlString);
			InputStream is = url.openStream();
			Document doc = builder.build(is);
			return getFragment(doc);
		}
		catch (Exception e){
	   		logger.info("[getFragmentFromRemoteFile] returning null (file proabably does not exist) " + e);
	   		logger.debug("[getFragmentFromRemoteFile] " + ErrorUtil.getStackTrace(e));
	   		return null;
		}
		
	}
	
	public static synchronized XmlFragment getFragmentFromString(String xmlString){
		try {
			
			System.out.println("XMLLL:"+xmlString);
    		Document doc = builder.build( new StringReader(xmlString ) );
    		return getFragment(doc);
		}
		 catch (Exception e){
			
			logger.info("[getFragmentFromString] returning null (string most likely not proper xml) starts with: [" + GeneralUtil.getStartOfString(xmlString) + "] : error : " + e);
	   		logger.debug("[getFragmentFromString] string=\n" + xmlString + "\n\nError:" + ErrorUtil.getStackTrace(e));
	   		return null;
		 }	
	}
	
	public static synchronized XmlFragment getFragment(Document doc){
			XmlFragment xmlFragment =  new XmlFragment(doc);
			logger.debug("[getFragmentFromFile] xml created - \n" + xmlFragment);
			return xmlFragment;
	}
	
	public XmlFragment(Document doc){
		this.doc = doc;
		element = doc.getRootElement();
	}
	
	public XmlFragment(Document doc, Element childReference){
		this.doc = doc;
		element = childReference;
	}
	
	public XmlFragment(Element rootElement){
		this.element = (Element) rootElement.clone();
		doc = new Document(element);
	}
	
	public XmlFragment(String elementName){
		this( new Element(elementName));
	}
	
	public void setAttribute(String attributeName, String attributeValue){
		element.setAttribute(attributeName, attributeValue);
	}
	
	public void addContent(XmlFragment contentFragment){
		element.addContent(contentFragment.getClone());
	}

	public void addContent(String contentString){
		element.addContent(contentString);
	}
	
	public void addCdataContent(String contentString){
		element.addContent(new CDATA(contentString));
	}
	
	public Element getClone(){
		return (Element)element.clone();
	}
	
	public String getAttributeValue(String attributeName){
		return element.getAttributeValue(attributeName);
	}
	
	public String toString(){
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
	    return outputter.outputString(element);
	}
	
	public String toStringRaw(){
		XMLOutputter outputter = new XMLOutputter(Format.getRawFormat());
	    return outputter.outputString(element);
	}
	
	public String toStringDoc(){
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
	    return outputter.outputString(doc);
	}
	
	public Element getRootElement(){
		return element;
	}
	
	public XmlFragment accessChild(String childName){
		Object childObj = element.getChild(childName);
		if (childObj instanceof Element){
			return new XmlFragment(doc, (Element)childObj);
		}
		return null;
	}
	
	public XmlFragment cloneChild(String childName){
		Object childObj = element.getChild(childName);
		if (childObj instanceof Element){
			return new XmlFragment((Element)childObj);
		}
		return null;
	}
	
	public List<XmlFragment> getChildren(String childName){
		List<XmlFragment> children = new ArrayList<XmlFragment>();
		for (Object childObj : element.getChildren(childName)){
			if (childObj instanceof Element){
				children.add(new XmlFragment((Element)childObj));
			}
		}
		
		return children;
	}
	
	public String getChildValue(String childName){
		Object childObj = element.getChild(childName);
		if (childObj instanceof Element){
			return element.getChild(childName).getText();
		}
		return "";
	}
	
	public boolean overwriteFile(String filename){
		try {
			File f1 = new File(filename);
			f1.delete();
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(filename,false));
			
			return true;
		}
		catch (Exception e){
			logger.error("[overwriteFile] error on filename: " + filename + "\n" + ErrorUtil.getStackTrace(e));
			return false;
		}
        
		
	}
	
}
