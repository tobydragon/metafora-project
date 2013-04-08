package de.uds.MonitorInterventionMetafora.server.mmftparser;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.utils.ServerFormatStrings;

public class ActionPropertyRuleParser {
	
	public static XmlFragment toXml(ActionPropertyRule actionPropertyRule){
		XmlFragment filterItemFragment= new XmlFragment(ServerFormatStrings.FILTERITEM);
    	filterItemFragment.setAttribute(ServerFormatStrings.PROPERTYRULENAME, actionPropertyRule.getPropertyName());
    	filterItemFragment.setAttribute(ServerFormatStrings.OPERATION, actionPropertyRule.getOperationType().toString());
    	filterItemFragment.setAttribute(ServerFormatStrings.VALUE, actionPropertyRule.getValue());
    	filterItemFragment.setAttribute(ServerFormatStrings.Type, actionPropertyRule.getType().toString());
    	return filterItemFragment;
	}
	
	public static ActionPropertyRule fromXml(XmlFragment propertyFragment){
			
		String name = (propertyFragment.getAttributeValue(ServerFormatStrings.PROPERTYRULENAME));
		String value = (propertyFragment.getAttributeValue(ServerFormatStrings.VALUE));	

		PropertyLocation location = (PropertyLocation.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.Type).toUpperCase()));
		OperationType operationType = OperationType.getFromString(propertyFragment.getAttributeValue(ServerFormatStrings.OPERATION).toUpperCase());
		
		ActionPropertyRule actionPropertyRule=new ActionPropertyRule(name, value, location, operationType);
		return actionPropertyRule;
	}

}
