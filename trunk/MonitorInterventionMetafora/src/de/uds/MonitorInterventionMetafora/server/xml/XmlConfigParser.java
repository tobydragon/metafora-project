package de.uds.MonitorInterventionMetafora.server.xml;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

import de.uds.MonitorInterventionMetafora.server.analysis.notification.NoWorkNotification;
import de.uds.MonitorInterventionMetafora.server.analysis.notification.Notification;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.NotificationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilterer;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.utils.ServerFormatStrings;

public class XmlConfigParser {
	
	XmlFragment configFragment;
	
 
	public XmlConfigParser(XmlFragment newStart){
		configFragment = newStart;
	}
	
	public XmlConfigParser(String filename){
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
			
	    _conf=new Configuration();
		_conf.setName(confFragment.getAttributeValue(ServerFormatStrings.NAME));
		_conf.setDataSourceType(confFragment.getChildValue(ServerFormatStrings.DATA_SOURCE_TYPE));
		_conf.setHistoryStartTime(confFragment.getChildValue(ServerFormatStrings.HISTORY_START_TIME));
		 XmlFragment  filtersFragment=XmlFragment.getFragmentFromString(confFragment.toString()).accessChild("filters");
		 _conf.addFilters(getFilterList(filtersFragment));
		// XmlFragmentInterface  notificationFragment=XmlFragment.getFragmentFromString(confFragment.toString());
		 //_conf.addNotifications(getNotificationList(notificationFragment));
		break;
	
		}

		}
		
		return _conf;
		
	}
	
	
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
					_filterItem.setType(ActionElementType.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.Type).toUpperCase()));
					_filterItem.setPropertyName(propertyFragment.getAttributeValue(ServerFormatStrings.ENTITYNAME));
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
	
	List<IndicatorFilter> getFilterList(XmlFragment _filtersFragment){
		
	List<IndicatorFilter> _entityfilters=new ArrayList<IndicatorFilter>();
	for(XmlFragment filterFragment: _filtersFragment.getChildren(ServerFormatStrings.FILTER)){
			IndicatorFilter indicatorFilter=new IndicatorFilter();
			String _filterName=filterFragment.getAttributeValue(ServerFormatStrings.NAME);
			indicatorFilter.setName(_filterName);
			indicatorFilter.setEditable(filterFragment.getAttributeValue(ServerFormatStrings.EDITABLE));
			for(XmlFragment propertyFragment : filterFragment.getChildren(ServerFormatStrings.FILTERITEM))
			{			
				ActionPropertyRule _filterItem=new ActionPropertyRule();
				_filterItem.setType(ActionElementType.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.Type).toUpperCase()));
				_filterItem.setPropertyName(propertyFragment.getAttributeValue(ServerFormatStrings.ENTITYNAME));
				_filterItem.setOperationType(OperationType.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.OPERATION).toUpperCase()));
				_filterItem.setValue(propertyFragment.getAttributeValue(ServerFormatStrings.VALUE));				
				 indicatorFilter.addIndicatorEntity(_filterItem.getPropertyName(), _filterItem);
			}
			_entityfilters.add(indicatorFilter);	
		}
	
		return _entityfilters;
	}
	
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
