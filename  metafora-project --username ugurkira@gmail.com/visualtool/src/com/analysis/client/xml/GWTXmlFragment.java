package com.analysis.client.xml;


import java.util.ArrayList;
import java.util.List;



import com.analysis.client.communication.objects.CfAction;
import com.analysis.client.communication.objects.CfActionType;
import com.analysis.client.communication.objects.CfContent;

import com.analysis.client.communication.objects.CfObject;
import com.analysis.client.communication.objects.CfProperty;
import com.analysis.client.communication.objects.CfUser;
import com.analysis.client.communication.objects.CommonFormatStrings;
import com.analysis.client.datamodels.ExtendedActionFilter;
import com.analysis.client.datamodels.Configuration;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;






//wrapper class for an Element in JDOM
//catches and prints errors, no attributes may be null

public class GWTXmlFragment {
	
	Document doc;
	Element element;


	
	public GWTXmlFragment(){
		
		
	}
	
	
	 String getInterActionPart(String rawData){
		
		doc=XMLParser.parse(rawData);
		return  doc.getElementsByTagName(CommonFormatStrings.INTERACTION_DATA_STRING).toString();
	}
	
	
	CfObject getActionObject(Node objectNode){
		

		 Element objectEl=(Element) objectNode;
		 String objectID=objectEl.getAttribute(CommonFormatStrings.ID_STRING);
		 String objectType=objectEl.getAttribute(CommonFormatStrings.TYPE_STRING);

		 
		 CfObject object =new CfObject(objectID,objectType);
		 
		 NodeList objectProperties=objectEl.getElementsByTagName(CommonFormatStrings.PROPERTIES_STRING);
		 
		 for(int m=0;m<objectProperties.getLength();m++)
		 {
			 Element objectProperty=(Element) objectProperties.item(m);
			 
			 NodeList op=objectProperty.getElementsByTagName(CommonFormatStrings.PROPERTY_STRING);
			 for(int ds=0;ds<op.getLength();ds++){
			 Element objectPropertyEl=(Element) op.item(ds);
			 String propertyName=objectPropertyEl.getAttribute("name");
			 String propertyValue=objectPropertyEl.getAttribute("value");

			 CfProperty property=new CfProperty(propertyName,propertyValue);
			 object.addProperty(property);
			 }
			 
		 }
		 
	return object;	
		
	}
	CfContent getActionContent(Node actionContentNode){
		
		
		
		Element actionContentEl=(Element) actionContentNode;
		// implement
	//	actionContentEl.getFirstChild()
		
	
	Element desc=(Element) actionContentEl.getElementsByTagName(CommonFormatStrings.DESCRIPTION_STRING).item(0);
	String description= desc.getFirstChild().getNodeValue();

	CfContent content=new CfContent(description);
	
	 NodeList contentProperties=actionContentEl.getElementsByTagName(CommonFormatStrings.PROPERTIES_STRING);

	 for(int kl=0;kl<contentProperties.getLength();kl++){
		 
		 Element contentProperty=(Element) contentProperties.item(kl);
		 
		 NodeList cp=contentProperty.getElementsByTagName(CommonFormatStrings.PROPERTY_STRING);
		 
		 for(int r=0;r<cp.getLength();r++){
			 
		 Element icp=(Element) cp.item(r);					
		 String propertyName=icp.getAttribute("name");
		 String propertyValue=icp.getAttribute("value");

		 CfProperty property=new CfProperty(propertyName,propertyValue);
		 content.addProperty(property);
			 
		 }
		 
	 }
		
	 return content;
	}
	
	
	public List<CfAction> processActions(String actionsXml){
		
		String actionXml=getInterActionPart(actionsXml);
		
		List<CfAction> cfActions=new ArrayList<CfAction>();
		doc=XMLParser.parse(actionXml);
		 NodeList nodeLst =doc.getElementsByTagName(CommonFormatStrings.ACTION_STRING);
	
		 for(int i=0; i< nodeLst.getLength(); i++){
			
			 Node actionNode = nodeLst.item(i);
			 
			 //Creating Action
			 Element actionEl= (Element) actionNode;
			 long time= Long.parseLong(actionEl.getAttribute("time"));
			
			 
			
		     Element	 actionType =(Element) actionEl.getElementsByTagName(CommonFormatStrings.ACTION_TYPE_STRING).item(0);
			 
			 String action_type=actionType.getAttribute("type");
			 String classification=actionType.getAttribute(CommonFormatStrings.CLASSIFICATION_STRING);
			 
			 String succeed=actionType.getAttribute("logged");
			
			 CfActionType CFAT=new CfActionType(action_type,classification,succeed);
			 CfAction myaction=new CfAction(time,CFAT);
			 
			
			 //USER
			 NodeList userNodes=actionEl.getElementsByTagName(CommonFormatStrings.USER_STRING);
			 for(int k=0;k<userNodes.getLength();k++){
				 
				 
				 Element userEl=(Element) userNodes.item(k);
				 String userID=userEl.getAttribute(CommonFormatStrings.ID_STRING);
				 String userRole=userEl.getAttribute("user_role");
				 
				 System.out.println("userid:"+userID);
				 System.out.println("userRole:"+userRole);
				 
				 CfUser user =new CfUser(userID,userRole);
				 myaction.addUser(user);			
				 
			 }
			
			 //OBJECT
			 
			 NodeList objectNodes=actionEl.getElementsByTagName(CommonFormatStrings.OBJECT_STRING);
			 for(int k=0;k<objectNodes.getLength();k++){
				 				 						 
				 myaction.addObject(getActionObject(objectNodes.item(k))); 
			 }
			 
			 //Content
			 NodeList actionContents=actionEl.getElementsByTagName(CommonFormatStrings.CONTENT_STRING);
			 
				for(int n=0;n<actionContents.getLength(); n++)
				{
				
				 myaction.setContent(getActionContent(actionContents.item(n)));
				 
				 
				}
				cfActions.add(myaction);
			
		}
	

	//	 CfInteractionData id=new CfInteractionData(cfActions);
		 return cfActions;
	}
	
	
	
	public Configuration getActiveConfiguration(String rawConf){
		doc=XMLParser.parse(rawConf);
		NodeList nodeLst =doc.getElementsByTagName(CommonFormatStrings.CF_Configuration);
		
		Configuration activeConf=new Configuration();
				
		 for(int i=0; i< nodeLst.getLength(); i++){
				
			 Node actionNode = nodeLst.item(i);
			 
			 
			 Element configurationEl= (Element) actionNode;
			 System.out.println(configurationEl.getAttribute("name"));
			 System.out.println(configurationEl.getAttribute("active"));
			String active=configurationEl.getAttribute("active");
			if(active==null)
				active="";
						 
			 if(active.equalsIgnoreCase("1") || active.equalsIgnoreCase("true")){
				 
				
				
				String confName=configurationEl.getAttribute("name");
				if(confName==null)
					confName="";
				activeConf.setName(confName);
			
				
				 
				 NodeList filters =configurationEl.getElementsByTagName(CommonFormatStrings.CF_Filter);
					
					
					 for(int j=0; j< filters.getLength(); j++)
					 {

						 
						 Node filterNode = filters.item(j);
						 
						 
						 Element filterEl= (Element) filterNode;
						 String filtername=filterEl.getAttribute("name");
						 String filtereditable=filterEl.getAttribute("editable");
						 
						 if(filtername==null)
							 filtername="";
						 
						System.out.println("filtername:"+filtername);
						
						ExtendedActionFilter activeFilter=new ExtendedActionFilter();
						activeFilter.setName(filtername);
						activeFilter.setEditable(filtereditable);
						
						
						NodeList properties =filterEl.getElementsByTagName(CommonFormatStrings.CF_Property);
						
						 for(int k=0; k<properties.getLength(); k ++){
							 
							 Node propertyNode = properties.item(k);
							 Element propertyEl= (Element) propertyNode;
							 
							 String name=propertyEl.getAttribute("name");
							 if(name==null)
								 name="";
							 String value=propertyEl.getAttribute("value");
							 if(value==null)
								 value="";							 							 
							 activeFilter.addProperty(name, value);	 
							 
							 
							 }
						 
						 
						 
						 
						 activeConf.addFilter(activeFilter);
						 
						 
					 }
				 
				 
				
				 break;
			 }
			 
		 }
		
		
		return activeConf;
	}
	

	
	
}
