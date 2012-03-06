package zcom.analysis.server.io;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;

//import com.analysis.server.utils.ServerFormatStrings;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

import de.uds.visualizer.server.utils.ServerFormatStrings;








//wrapper class for an Element in JDOM
//catches and prints errors, no attributes may be null

public class SourceManager {
	
	static Document doc;
	Element element;
	static String conf="conf/configuration.xml";
	static String filter="conf/filterconfiguration.xml";


	
	public SourceManager(){
		
		
	}
	
	
	 public static Map<String, String> getConfiguration(){		 
		Map<String, String> configurations= new HashMap<String,String>();; 
		for(XmlFragment xf : XmlFragment.getFragmentFromFile(conf).getChildren(ServerFormatStrings.HISTORY_SOURCE)){
			if(xf.getAttributeValue(ServerFormatStrings.Active).equalsIgnoreCase("true") || xf.getAttributeValue(ServerFormatStrings.Active).equalsIgnoreCase("1")){
										
				 List attributes = xf.getRootElement().getAttributes();			 
				 Iterator itr = attributes.iterator();
				 while (itr.hasNext()){
					 Attribute att=(Attribute)itr.next();				
					 configurations.put(att.getName(),att.getValue());					 
				}
				 break;
			}
		}
		 return configurations;
	 }
	 
	 public static String getConfigurations(String rawData){
		/* 
		// doc=XMLParser.createDocument();
		 doc=XMLParser.parse(rawData);
		// NodeList nodeLst =doc.getElementsByTagName(ServerFormatStrings.HISTORY_SOURCE);
			
		 for(int i=0; i< nodeLst.getLength(); i++){
			
			 Node actionNode = nodeLst.item(i);
			
			Element actionEl= (Element) actionNode;
			System.out.println("Source Type"+actionEl.getAttribute(ServerFormatStrings.Type));
			System.out.println("Active"+actionEl.getAttribute(ServerFormatStrings.Active));
			
		 }*/
		 return "";
	 }
	
	
	
}
