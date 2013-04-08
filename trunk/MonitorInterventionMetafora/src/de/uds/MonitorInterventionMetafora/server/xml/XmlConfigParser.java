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
	
	
	
//	XmlFragment ToXmlFragment(ActionFilter filter){
//		
//
//		XmlFragment xmlFragment= new XmlFragment(ServerFormatStrings.FILTER);
//	    xmlFragment.setAttribute(ServerFormatStrings.NAME, filter.getName());
//	    xmlFragment.setAttribute(ServerFormatStrings.EDITABLE, "true");
//	
//	    for(ActionPropertyRule rule:filter.getActionPropertyRules()){
//	    	XmlFragment filterItemFragment= new XmlFragment(ServerFormatStrings.FILTERITEM);
//	    	filterItemFragment.setAttribute(ServerFormatStrings.PROPERTYRULENAME, rule.getPropertyName());
//	    	filterItemFragment.setAttribute(ServerFormatStrings.OPERATION, rule.getOperationType().toString());
//	    	filterItemFragment.setAttribute(ServerFormatStrings.VALUE, rule.getValue());
//	    	filterItemFragment.setAttribute(ServerFormatStrings.Type, rule.getType().toString());
//	    	xmlFragment.addContent(filterItemFragment);
//	    }
//		return xmlFragment;	
//
//		
//	}
	
	
	public List<Notification> getNotificationList(){
		
		
		
		List<Notification> notifications=new ArrayList<Notification>();
		
		//for(XmlFragmentInterface _filterFragment: configFragment.getChildren(ServerFormatStrings.NOTIFICATIONS)){
		//_filterFragment
			for(XmlFragment filterFragment: configFragment.getChildren(ServerFormatStrings.NOTIFICATION)){
			IndicatorFilter indicatorFilter=new IndicatorFilter();
				indicatorFilter.setName(ServerFormatStrings.NOTIFICATION);
				indicatorFilter.setEditable("false");	
				NotificationType type=NotificationType.getFromString(filterFragment.getAttributeValue(ServerFormatStrings.Type).toUpperCase());
				String color=filterFragment.getAttributeValue(ServerFormatStrings.COLOR);
				
				for(XmlFragment propertyFragment : filterFragment.getChildren(ServerFormatStrings.FILTERITEM))
				{			
					ActionPropertyRule _filterItem=new ActionPropertyRule();
					_filterItem.setType(PropertyLocation.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.Type).toUpperCase()));
					_filterItem.setPropertyName(propertyFragment.getAttributeValue(ServerFormatStrings.PROPERTYRULENAME));
					_filterItem.setOperationType(OperationType.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.OPERATION).toUpperCase()));
					_filterItem.setValue(propertyFragment.getAttributeValue(ServerFormatStrings.VALUE));				
					 indicatorFilter.addIndicatorEntity(_filterItem.getPropertyName(), _filterItem);
				}
				
				notifications.add(renderNotification(type,indicatorFilter,color));	

			}
		
		
		//}
		System.out.println("Notifications are loaded!!");
			return notifications;
		
		
	}
	
//	List<ActionFilter> getActionFilterList(XmlFragment _filtersFragment){
//		
//		List<ActionFilter> actionFilters=new Vector<ActionFilter>();
//		for(XmlFragment filterFragment: _filtersFragment.getChildren(ServerFormatStrings.FILTER)){
//			ActionFilter actionFilter=new ActionFilter();
//			String _filterName=filterFragment.getAttributeValue(ServerFormatStrings.NAME);
//			actionFilter.setName(_filterName);
//			actionFilter.setEditable(Boolean.getBoolean(filterFragment.getAttributeValue(ServerFormatStrings.EDITABLE)));
//			for(XmlFragment propertyFragment : filterFragment.getChildren(ServerFormatStrings.FILTERITEM))
//			{			
//				ActionPropertyRule actionPropertyRule=new ActionPropertyRule();
//				actionPropertyRule.setType(PropertyLocation.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.Type).toUpperCase()));
//				actionPropertyRule.setPropertyName(propertyFragment.getAttributeValue(ServerFormatStrings.PROPERTYRULENAME));
//				actionPropertyRule.setOperationType(OperationType.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.OPERATION).toUpperCase()));
//				actionPropertyRule.setValue(propertyFragment.getAttributeValue(ServerFormatStrings.VALUE));	
//				if (actionPropertyRule.isValid()){
//					actionFilter.addFilterRule(actionPropertyRule);
//				}
//				else {
//					Log.error("[getActionFilterList] Invalid property rule read from xml, ignoring: " + actionPropertyRule.toString());
//				}
//			}
//			actionFilters.add(actionFilter);	
//		}
//	
//		return actionFilters;
//	}
	
	Notification renderNotification(NotificationType _type,IndicatorFilter _filter,String _color){
		
		switch(_type){
		
		case NOWORK:
		//	IndicatorFilterer _filterer=new IndicatorFilterer();
			NoWorkNotification _notification=new NoWorkNotification();
			_notification.setFilter(_filter);
			_notification.setColor(_color);
			//_notification.setFilterer(_filterer);
			_notification.setType(NotificationType.NOWORK);
			
			return _notification;
			
			//return null;
		
		};
		return null;
		
	}
	
	
	
	

}
