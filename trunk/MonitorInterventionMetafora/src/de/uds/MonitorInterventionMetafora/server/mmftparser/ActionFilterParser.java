package de.uds.MonitorInterventionMetafora.server.mmftparser;

import java.util.List;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.RuleRelation;
import de.uds.MonitorInterventionMetafora.shared.utils.ServerFormatStrings;

public class ActionFilterParser {
	public static XmlFragment toXml(ActionFilter actionFilter){
		XmlFragment xmlFragment= new XmlFragment(ServerFormatStrings.FILTER);
	    xmlFragment.setAttribute(ServerFormatStrings.NAME, actionFilter.getName());
	    xmlFragment.setAttribute(ServerFormatStrings.SERVER_SIDE_FILTER, "true");
	
	    for(ActionPropertyRule rule: actionFilter.getActionPropertyRules()){
	    	xmlFragment.addContent(ActionPropertyRuleParser.toXml(rule));
	    }
		return xmlFragment;
	}
	
	public static ActionFilter fromXml(XmlFragment filterFragment){
		
		String filterName=filterFragment.getAttributeValue(ServerFormatStrings.NAME);
		boolean serverSideFilter = Boolean.getBoolean(filterFragment.getAttributeValue(ServerFormatStrings.SERVER_SIDE_FILTER));
		String type = filterFragment.getAttributeValue(ServerFormatStrings.Type);
		String color = filterFragment.getAttributeValue(ServerFormatStrings.COLOR);
		
		List<ActionPropertyRule> actionPropertyRules = new Vector<ActionPropertyRule>();
		for(XmlFragment propertyFragment : filterFragment.getChildren(ServerFormatStrings.FILTERITEM))
		{			
			ActionPropertyRule actionPropertyRule=ActionPropertyRuleParser.fromXml(propertyFragment);
			if (actionPropertyRule.isValid()){
				actionPropertyRules.add(actionPropertyRule);
			}
			else {
				Log.warn("[ActionFilter.fromXml] Invalid property rule read from xml, ignoring: " + actionPropertyRule.toString());
			}
		}
		ActionFilter actionFilter=new ActionFilter(filterName, serverSideFilter, type, color, actionPropertyRules, RuleRelation.AND);
		return actionFilter;
	}
	
	public static List<ActionFilter> listFromXml(XmlFragment filtersFragment){
		List<ActionFilter> filters = new Vector<ActionFilter>();
		for(XmlFragment propertyFragment : filtersFragment.getChildren(ServerFormatStrings.FILTER)){			
			ActionFilter filter=ActionFilterParser.fromXml(propertyFragment);
			filters.add(filter);
		}
		return filters;
	}

}
