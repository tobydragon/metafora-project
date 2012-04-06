package de.uds.MonitorInterventionMetafora.server.xml;

import java.util.ArrayList;
import java.util.List;

import de.uds.MonitorInterventionMetafora.server.analysis.NoWorkNotification;
import de.uds.MonitorInterventionMetafora.server.utils.ServerFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.FilterItemType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.NotificationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorEntity;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.IndicatorFilter;
import de.uds.MonitorInterventionMetafora.shared.notifications.Notification;

public class XmlConfigParser {
	
	XmlFragmentInterface configFragment;
	
 
	public XmlConfigParser(XmlFragmentInterface newStart){
		configFragment = newStart;
	}
	
	public XmlConfigParser(String filename){
		configFragment = XmlFragment.getFragmentFromFile(filename);
	}
	
	public XmlConfigParser getfragmentById(String fragmentType, String idAttrName,  String id){
		List<XmlFragmentInterface> frags = configFragment.getChildren(fragmentType);
		for (XmlFragmentInterface frag : frags){
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
		
		for(XmlFragmentInterface confFragment: configFragment.getChildren(ServerFormatStrings.CONFIGURATION)){
		
		if(confFragment.getAttributeValue("active").equalsIgnoreCase("1") || confFragment.getAttributeValue("active").equalsIgnoreCase("true")){	
			
	    _conf=new Configuration();
		_conf.setName(confFragment.getAttributeValue(ServerFormatStrings.NAME));
		_conf.setDataSource(confFragment.getChildValue(ServerFormatStrings.DATA_SOURCE_TYPE));
		 XmlFragmentInterface  filtersFragment=XmlFragment.getFragmentFromString(confFragment.toString()).accessChild("filters");
		 _conf.addFilters(getFilterList(filtersFragment));
		 _conf.addNotifications(getNotificationList(filtersFragment));
		break;
	
		}

		}
		
		return _conf;
		
	}
	
	
	List<Notification> getNotificationList(XmlFragmentInterface _filtersFragment){
		
		
		
		List<Notification> notifications=new ArrayList<Notification>();
		
		for(XmlFragmentInterface filterFragment: _filtersFragment.getChildren(ServerFormatStrings.NOTIFICATION)){
				IndicatorFilter indicatorFilter=new IndicatorFilter();
				indicatorFilter.setName(ServerFormatStrings.NOTIFICATION);
				indicatorFilter.setEditable("false");	
				NotificationType type=NotificationType.getFromString(filterFragment.getAttributeValue(ServerFormatStrings.Type).toUpperCase());
			
				for(XmlFragmentInterface propertyFragment : filterFragment.getChildren(ServerFormatStrings.FILTERITEM))
				{			
					IndicatorEntity _filterItem=new IndicatorEntity();
					_filterItem.setType(FilterItemType.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.Type).toUpperCase()));
					_filterItem.setEntityName(propertyFragment.getAttributeValue(ServerFormatStrings.ENTITYNAME));
					_filterItem.setOperationType(OperationType.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.OPERATION).toUpperCase()));
					_filterItem.setValue(propertyFragment.getAttributeValue(ServerFormatStrings.VALUE));				
					 indicatorFilter.addIndicatorEntity(_filterItem.getEntityName(), _filterItem);
				}
				
				notifications.add(renderNotification(type,indicatorFilter));	
			}
		
			return notifications;
		
		
	}
	
	List<IndicatorFilter> getFilterList(XmlFragmentInterface _filtersFragment){
		
	List<IndicatorFilter> _entityfilters=new ArrayList<IndicatorFilter>();
	for(XmlFragmentInterface filterFragment: _filtersFragment.getChildren(ServerFormatStrings.FILTER)){
			IndicatorFilter indicatorFilter=new IndicatorFilter();
			String _filterName=filterFragment.getAttributeValue(ServerFormatStrings.NAME);
			indicatorFilter.setName(_filterName);
			indicatorFilter.setEditable(filterFragment.getAttributeValue(ServerFormatStrings.EDITABLE));
			for(XmlFragmentInterface propertyFragment : filterFragment.getChildren(ServerFormatStrings.FILTERITEM))
			{			
				IndicatorEntity _filterItem=new IndicatorEntity();
				_filterItem.setType(FilterItemType.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.Type).toUpperCase()));
				_filterItem.setEntityName(propertyFragment.getAttributeValue(ServerFormatStrings.ENTITYNAME));
				_filterItem.setOperationType(OperationType.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.OPERATION).toUpperCase()));
				_filterItem.setValue(propertyFragment.getAttributeValue(ServerFormatStrings.VALUE));				
				 indicatorFilter.addIndicatorEntity(_filterItem.getEntityName(), _filterItem);
			}
			_entityfilters.add(indicatorFilter);	
		}
	
		return _entityfilters;
	}
	
	Notification renderNotification(NotificationType _type,IndicatorFilter _filter){
		
		switch(_type){
		
		case NOWORK:
			NoWorkNotification _notification=new NoWorkNotification();
			_notification.setType(NotificationType.NOWORK);
			_notification.setFilter(_filter);
			return _notification;
		
		};
		return null;
		
	}
	
	
	
	

}
