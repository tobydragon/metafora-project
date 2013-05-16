package de.uds.MonitorInterventionMetafora.shared.suggestedmessages;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;

public class InterventionCreator {
	
	public static CfAction createSendSuggestedMessages(List <String> userIds, String XML){
		CfActionType cfActionType = new CfActionType();
		cfActionType.setType(MetaforaStrings.ACTION_TYPE_SUGGESTED_MESSAGES_STRING);
		cfActionType.setClassification("create");
		cfActionType.setLogged("true");

		CfAction cfAction = new CfAction(GWTUtils.getTimeStamp(), cfActionType);

		if (userIds.size() < 1) {
			Log.warn("[MessageCreator.createSendSuggestedMessages()] No users, returning null.");
			return null;
		}
		for (String userId : userIds) {
			cfAction.addUser(new CfUser(userId, "receiver"));
		}
		
		CfContent cfContent = new CfContent(XML);
		cfContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_RECEIVING_TOOL, MetaforaStrings.MONITOR_AND_MESSAGE_TOOL_NAME));
		cfContent.addProperty(new CfProperty(MetaforaStrings.PROPERTY_NAME_SENDING_TOOL, MetaforaStrings.MONITOR_AND_MESSAGE_TOOL_NAME));
		cfAction.setCfContent(cfContent);
		
		return cfAction;
	}

}
