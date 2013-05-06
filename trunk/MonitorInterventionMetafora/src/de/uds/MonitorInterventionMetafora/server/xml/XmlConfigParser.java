package de.uds.MonitorInterventionMetafora.server.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;
import de.uds.MonitorInterventionMetafora.server.analysis.notification.NoWorkNotification;
import de.uds.MonitorInterventionMetafora.server.analysis.notification.Notification;
import de.uds.MonitorInterventionMetafora.server.mmftparser.ActionFilterParser;
import de.uds.MonitorInterventionMetafora.server.mmftparser.MmftConfigurationParser;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.NotificationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.utils.ServerFormatStrings;

public class XmlConfigParser {
	
	XmlFragment configFragment;
	private String sourceFile="";
	
 
	public XmlConfigParser(XmlFragment newStart){
		configFragment = newStart;
	}
	
	public XmlConfigParser(String filename){
		sourceFile=filename;
		configFragment = XmlFragment.getFragmentFromLocalFile(filename);
	}
	
	public XmlConfigParser getfragmentById(String fragmentType, String idAttrName,  String id){
		List<XmlFragment> frags = configFragment.getChildren(fragmentType);
		for (XmlFragment frag : frags){
			if( frag.getAttributeValue(idAttrName).equalsIgnoreCase(id)){
				return new XmlConfigParser(frag);
			}
		}
		return null;
	}
	
	public String getConfigValue(String name){
		try {
			return configFragment.cloneChild(name).getRootElement().getText();
		}
		catch (Exception e){
			return null;
		}
	}
	
	
	public Configuration toActiveConfiguration(){	
		Configuration _conf=null;
		
		for(XmlFragment confFragment: configFragment.getChildren(ServerFormatStrings.CONFIGURATION)){

			if(confFragment.getAttributeValue("active").equalsIgnoreCase("1") || confFragment.getAttributeValue("active").equalsIgnoreCase("true")){	
				_conf = MmftConfigurationParser.fromXml(confFragment);
				break;
			}

		}
		
		return _conf;
		
	}
	
	
	public boolean saveNewFilterToConfiguration(ActionFilter filter){	
		Configuration _conf=null;
		
		for(XmlFragment confFragment: configFragment.getChildren(ServerFormatStrings.CONFIGURATION)){
		
		if(confFragment.getAttributeValue("active").equalsIgnoreCase("1") || confFragment.getAttributeValue("active").equalsIgnoreCase("true")){			
//	    _conf=new Configuration();
//		_conf.setName(confFragment.getAttributeValue(ServerFormatStrings.NAME));
//		_conf.setDataSourceType(confFragment.getChildValue(ServerFormatStrings.DATA_SOURCE_TYPE));
//		_conf.setHistoryStartTime(confFragment.getChildValue(ServerFormatStrings.HISTORY_START_TIME));
		_conf = MmftConfigurationParser.fromXml(confFragment); 
		XmlFragment  filtersFragment=XmlFragment.getFragmentFromString(confFragment.toString()).accessChild(ServerFormatStrings.FILTERS);
		filtersFragment.addContent(ActionFilterParser.toXml(filter));
		confFragment.removeNode(ServerFormatStrings.FILTERS);
		confFragment.addContent(filtersFragment);
		configFragment.removeNode(ServerFormatStrings.CONFIGURATION);
		configFragment.addContent(confFragment);
		configFragment.overwriteFile(sourceFile);
		return true;
		}
		}
		 return false;
		
	}
	
	
	
	
	public boolean removeFilterFromConfiguration( String filterName){	
		Configuration _conf=null;
		
		//TODO: This code is really confusing and doesn't seem to do anything...
		for(XmlFragment confFragment: configFragment.getChildren(ServerFormatStrings.CONFIGURATION)){
		
		if(confFragment.getAttributeValue("active").equalsIgnoreCase("1") || confFragment.getAttributeValue("active").equalsIgnoreCase("true")){			
//	    _conf=new Configuration();
//		_conf.setName(confFragment.getAttributeValue(ServerFormatStrings.NAME));
//		_conf.setDataSourceType(confFragment.getChildValue(ServerFormatStrings.DATA_SOURCE_TYPE));
//		_conf.setHistoryStartTime(confFragment.getChildValue(ServerFormatStrings.HISTORY_START_TIME));
//		
//		 for(XmlFragment filterItem:XmlFragment.getFragmentFromString(confFragment.toString()).accessChild(ServerFormatStrings.FILTERS).getChildren(ServerFormatStrings.FILTER)){
//			 if(filterName.equalsIgnoreCase(filterItem.getAttributeValue(ServerFormatStrings.NAME)))
//			 {
//				 //XmlFragment.getFragmentFromString(confFragment.toString()).accessChild(ServerFormatStrings.FILTERS).r
//			 }
//		 }
		 /*
		 filtersFragment.addContent(ToXmlFragment(filter));
		 confFragment.removeNode(ServerFormatStrings.FILTERS);
		 confFragment.addContent(filtersFragment);
		 configFragment.removeNode(ServerFormatStrings.CONFIGURATION);
		 configFragment.addContent(confFragment);*/
		 configFragment.overwriteFile(sourceFile);
		return true;
		}
		}
		 return false;
		
	}
	

}
