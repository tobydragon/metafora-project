package de.uds.MonitorInterventionMetafora.server.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import de.uds.MonitorInterventionMetafora.server.utils.ErrorUtil;



//wrapper class for an Element in JDOM
//catches and prints errors, no attributes may be null

public class XmlFragment implements XmlFragmentInterface {
	static Log logger = LogFactory.getLog(XmlFragment.class);
	
	static SAXBuilder builder = new SAXBuilder();
	
	Document doc;
	Element element;
	
	public static synchronized XmlFragmentInterface getFragmentFromFile(String filename){
		try {
			Document doc = builder.build(new File(filename));
			XmlFragmentInterface xmlFragment =  new XmlFragment(doc);
			logger.debug("[getFragmentFromFile] xml created - \n" + xmlFragment);
			
			return xmlFragment;
		}
		catch (Exception e){
   		 logger.error("[getFragmentFromFile] " + e.getMessage());
   		 logger.debug("[getFragmentFromFile] " + ErrorUtil.getStackTrace(e));
   	 	}
		return null;
	}
	
	public static synchronized XmlFragmentInterface getFragmentFromString(String xmlString){
		try {
    		Document doc = builder.build( new StringReader(xmlString ) );
    		return new XmlFragment(doc);
    	 }
    	 catch (Exception e){
    		 logger.info("[XmlFragment constructor] XmlFragment not created - " + e.getMessage());
    		 //logger.debug("[XmlFragment constuctor] " + ErrorUtil.getStackTrace(e));
    		 return null;
    	 }	
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
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#setAttribute(java.lang.String, java.lang.String)
	 */
	@Override
	public void setAttribute(String attributeName, String attributeValue){
		element.setAttribute(attributeName, attributeValue);
	}
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#addContent(de.uds.xml.XmlFragment)
	 */
	@Override
	public void addContent(XmlFragmentInterface contentFragment){
		element.addContent(contentFragment.getClone());
	}
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#addContent(java.lang.String)
	 */
	@Override
	public void addContent(String contentString){
		element.addContent(contentString);
	}
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#addCdataContent(java.lang.String)
	 */
	@Override
	public void addCdataContent(String contentString){
		element.addContent(new CDATA(contentString));
	}
	
	public Element getClone(){
		return (Element)element.clone();
	}
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#getAttributeValue(java.lang.String)
	 */
	@Override
	public String getAttributeValue(String attributeName){
		return element.getAttributeValue(attributeName);
	}
	
	public String toString(){
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
	    return outputter.outputString(element);
	}
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#toStringRaw()
	 */
	@Override
	public String toStringRaw(){
		XMLOutputter outputter = new XMLOutputter(Format.getRawFormat());
	    return outputter.outputString(element);
	}
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#toStringDoc()
	 */
	@Override
	public String toStringDoc(){
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
	    return outputter.outputString(doc);
	}
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#getRootElement()
	 */
	@Override
	public Element getRootElement(){
		return element;
	}
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#accessChild(java.lang.String)
	 */
	@Override
	public XmlFragmentInterface accessChild(String childName){
		Object childObj = element.getChild(childName);
		if (childObj instanceof Element){
			return new XmlFragment(doc, (Element)childObj);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#cloneChild(java.lang.String)
	 */
	@Override
	public XmlFragmentInterface cloneChild(String childName){
		Object childObj = element.getChild(childName);
		if (childObj instanceof Element){
			return new XmlFragment((Element)childObj);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#getChildren(java.lang.String)
	 */
	@Override
	public List<XmlFragmentInterface> getChildren(String childName){
		List<XmlFragmentInterface> children = new ArrayList<XmlFragmentInterface>();
		for (Object childObj : element.getChildren(childName)){
			if (childObj instanceof Element){
				children.add(new XmlFragment((Element)childObj));
			}
		}
		
		return children;
	}
	
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#getChildValue(java.lang.String)
	 */
	@Override
	public String getChildValue(String childName){
		Object childObj = element.getChild(childName);
		if (childObj instanceof Element){
			return element.getChild(childName).getText();
		}
		return "";
	}
	
	
	
	/* (non-Javadoc)
	 * @see de.uds.xml.XmlFragmentInterface#overwriteFile(java.lang.String)
	 */
	@Override
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
