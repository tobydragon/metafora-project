package de.uds.MonitorInterventionMetafora.server.analysis;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.analysis.notification.NewIdeaNotDiscussedNotification;
import de.uds.MonitorInterventionMetafora.server.analysis.notification.Notification;
import de.uds.MonitorInterventionMetafora.server.monitor.MonitorController;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class AnalysisController {
	Logger log = Logger.getLogger(this.getClass());
	
	List<Notification> notifications;
	
	MonitorController monitorController;
	
	public AnalysisController(MonitorController monitorController){
		this.monitorController = monitorController;
		
		notifications = new Vector<Notification>();
		notifications.add(new NewIdeaNotDiscussedNotification());
		
	}
	
	public void analyzeGroup(String groupName){
		List<ActionPropertyRule> groupRules1 = new Vector<ActionPropertyRule>();
		groupRules1.add(new ActionPropertyRule("GROUP_ID", groupName, PropertyLocation.OBJECT, OperationType.EQUALS));
		ActionFilter groupFilter1 = new ActionFilter("GroupFilter", true, groupRules1);
		
		List <CfAction> actions = monitorController.getActionList();
		List <CfAction> groupActions = groupFilter1.getFilteredList(actions);
		
		log.info("[analyzeGroup] actions found for group: " + groupActions.size());
		
		for (Notification notification : notifications){
			if (notification.shouldFireNotification(groupActions)){
				log.info("[AnalysisController.analyzeGroup] notification found: \n" + notification.createNotificationCfAction());
			}
		}
	}
	

}
