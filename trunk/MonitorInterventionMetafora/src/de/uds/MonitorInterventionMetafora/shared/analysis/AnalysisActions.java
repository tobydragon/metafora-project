package de.uds.MonitorInterventionMetafora.shared.analysis;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.RuleRelation;

public class AnalysisActions {
	public static List<String> PROPERTIES_TO_RETRIEVE = Arrays.asList("GROUP_ID", "CHALLENGE_ID", "CHALLENGE_NAME"); 

	
	public static List<CfProperty> getPropertiesFromActions(List<CfAction> actions) {
		//Assumes properties are the same for all actions in list, does not check
		List <CfProperty> properties = new Vector<CfProperty>();
		
		for (String propertyName : PROPERTIES_TO_RETRIEVE){
			for (CfAction action : actions) {
				String value = action.getCfContent().getPropertyValue(propertyName);
				if (value != null){
					properties.add(new CfProperty(propertyName, value));
					break;
				}
			}
		}
		return properties;
	}

	public static List<String> getInvolvedGroups(List<CfAction> actions) {
		List<String> groups = new Vector<String>();
		
		for (CfAction action : actions){
			if (action.getCfContent() != null){
				String groupName = action.getCfContent().getPropertyValue(MetaforaStrings.PROPERTY_TYPE_GROUP_ID_STRING);
				if (groupName != null){
					if (! groups.contains(groupName)){
						groups.add(groupName);
					}
				}
			}
			for (CfObject object : action.getCfObjects()){
				String groupName = object.getPropertyValue(MetaforaStrings.PROPERTY_TYPE_GROUP_ID_STRING);
				if (groupName != null){
					if (! groups.contains(groupName)){
						groups.add(groupName);
					}
				}
			}
		}
		return groups;
	}

	public static List<CfAction> getGroupActions(String groupName, List<CfAction> actions){
		List<ActionPropertyRule> groupRules1 =new Vector<ActionPropertyRule>();
		groupRules1.add(new ActionPropertyRule("GROUP_ID", groupName, PropertyLocation.OBJECT, OperationType.EQUALS));
		groupRules1.add(new ActionPropertyRule("GROUP_ID", groupName, PropertyLocation.CONTENT, OperationType.EQUALS));
		ActionFilter groupFilter1 = new ActionFilter("GroupFilter", true, null, null, groupRules1, RuleRelation.OR);
		return groupFilter1.getFilteredList(actions);
	}
	
	public static List<String> getOriginatingUsernames(List<CfAction> actions) {
		List<String> usernames = new Vector<String>();
		for (CfAction action : actions){
			for (CfUser user : action.getCfUsers()){
				if (MetaforaStrings.USER_ROLE_ORIGINATOR_STRING.equalsIgnoreCase(user.getrole())){
					//ignore "system" user that sends messages to users
					if ( ! user.getid().equals(MetaforaStrings.ANAYLSIS_SYSTEM_USER)){
						if ( ! usernames.contains(user.getid()) ){
							usernames.add(user.getid());
						}
					}
				}
			}
		}
		return usernames;
	}
	
	
}
