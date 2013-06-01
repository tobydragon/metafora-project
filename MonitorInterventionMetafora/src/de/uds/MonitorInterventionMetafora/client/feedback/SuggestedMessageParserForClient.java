package de.uds.MonitorInterventionMetafora.client.feedback;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;

import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.L2L2category;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessage;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesCategory;

public class SuggestedMessageParserForClient {
	
	public static SuggestedMessage fromXmlNode(Node messageItem, SuggestedMessagesCategory suggestedMessagesCategory){
		String msgText = messageItem.getFirstChild().getNodeValue();
		boolean isHighlight = false;
		BehaviorType behaviorType = null;
		if (messageItem.hasAttributes()) {
			NamedNodeMap msgAttributes = messageItem.getAttributes();
			for (int iattribute = 0; iattribute < msgAttributes.getLength(); iattribute++) {
				Node msgAttribute = msgAttributes.item(iattribute);
				if (msgAttribute.getNodeName().equals("highlight") && msgAttribute.getNodeValue().equalsIgnoreCase("true")) {
					isHighlight = true;
				}
				else if (msgAttribute.getNodeName().equals("behaviortag")){
					try {
						behaviorType = BehaviorType.valueOf(msgAttribute.getNodeValue());
					}
					catch(Exception e){
						Log.warn("[SuggestedMessage.fromXmlNode] ignoring uknown behavior tag : "+ msgAttribute.getNodeValue());
					}
				}
			}
		}
		return new SuggestedMessage(msgText, isHighlight, behaviorType, suggestedMessagesCategory);

	}
	
	public static String toXml(SuggestedMessage message){
		StringBuffer b = new StringBuffer();
		String attribute = message.isHighlight() ? " highlight=\"true\"" : "";
		String attribute2 = message.getBehaviorType()!=null ? " behaviortag=\""+ message.getBehaviorType() + "\"" : "";
		b.append("<message " +attribute+attribute2+ " >");
		b.append(message.getText());
		b.append("</message>\n");
		return b.toString();
	}

}
