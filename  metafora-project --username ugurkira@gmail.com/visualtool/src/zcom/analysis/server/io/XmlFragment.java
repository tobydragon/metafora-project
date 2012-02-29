package zcom.analysis.server.io;

import java.io.File;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


//wrapper class for an Element in JDOM
//catches and prints errors, no attributes may be null

public class XmlFragment {
	//static Log logger = LogFactory.getLog(XmlFragment.class);
	
	static SAXBuilder builder = new SAXBuilder();
	
	Document doc;
	Element element;
	
	public static synchronized XmlFragment getFragmentFromFile(String filename){
		try {
			Document doc = builder.build(new File(filename));
			XmlFragment xmlFragment =  new XmlFragment(doc);
			//logger.debug("[getFragmentFromFile] xml created - \n" + xmlFragment);
			
			return xmlFragment;
		}
		catch (Exception e){
   		// logger.error("[getFragmentFromFile] " + e.getMessage());
   		 //logger.debug("[getFragmentFromFile] " + ErrorUtil.getStackTrace(e));
   	 	}
		return null;
	}
	
	public static synchronized XmlFragment getFragmentFromString(String xmlString){
		try {
    		Document doc = builder.build( new StringReader(xmlString ) );
    		return new XmlFragment(doc);
    	 }
    	 catch (Exception e){
    		// logger.info("[XmlFragment constructor] XmlFragment not created - " + e.getMessage());
    		 //logger.debug("[XmlFragment constuctor] " + ErrorUtil.getStackTrace(e));
    		 return null;
    	 }	
	}
	
	public XmlFragment(Document doc){
		this.doc = doc;
		element = doc.getRootElement();
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
	
	private Element getClone(){
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
	
	public XmlFragment getChild(String childName){
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
	
	
	public static String convertSpecialCharactersToDescripitons(String toConvert){
		return toConvert.replaceAll("&", "&amp;");
	}
	
	public  static String convertSpecialCharacterDescriptionsBack(String toConvertBack){
		toConvertBack = toConvertBack.replaceAll("&amp;", "&");
		try {
			return URLDecoder.decode(toConvertBack, "UTF-8");
		}
		catch(Exception e){
		//	logger.error("[convertSpecialCharactersBack] String not changed due to following:\n" + ErrorUtil.getStackTrace(e));
			return toConvertBack;
		}
	}
	
	
}
