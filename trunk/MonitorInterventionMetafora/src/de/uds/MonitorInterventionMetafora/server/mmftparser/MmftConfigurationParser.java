package de.uds.MonitorInterventionMetafora.server.mmftparser;

import java.util.List;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.interactionmodels.Configuration;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.utils.ServerFormatStrings;

public class MmftConfigurationParser {

	
	public static XmlFragment toXml(Configuration configuration){
		return null;
	}
	
	public static Configuration fromXml(XmlFragment confFragment){
		
		String name = confFragment.getAttributeValue(ServerFormatStrings.NAME);
		String communicationMethodType = confFragment.getChildValue(ServerFormatStrings.DATA_SOURCE_TYPE);
		String historyStartTime = confFragment.getChildValue(ServerFormatStrings.HISTORY_START_TIME);
		String defaultServer = confFragment.getChildValue(ServerFormatStrings.DEFAULT_XMPP_SERVER);
		String testServerMonitoring = confFragment.getChildValue(ServerFormatStrings.TEST_SERVER_MONITORING);
		String deployServerMonitoring = confFragment.getChildValue(ServerFormatStrings.DEPLOY_SERVER_MONITORING);
//		XmlFragment  filtersFragment=XmlFragment.getFragmentFromString(confFragment.toString()).accessChild(ServerFormatStrings.FILTERS); 
		
		List<ActionFilter> filters = new Vector<ActionFilter>();
		for(XmlFragment propertyFragment : confFragment.accessChild(ServerFormatStrings.FILTERS).getChildren(ServerFormatStrings.FILTER)){			
			ActionFilter filter=ActionFilterParser.fromXml(propertyFragment);
			filters.add(filter);
		}
		
		Configuration config = new Configuration(name, communicationMethodType, historyStartTime, defaultServer, testServerMonitoring, deployServerMonitoring, filters);
		return config;
	}
}
