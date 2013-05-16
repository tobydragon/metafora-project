package de.uds.MonitorInterventionMetafora.server.mmftparser;


import org.apache.log4j.Logger;

import de.uds.MonitorInterventionMetafora.server.xml.XmlFragment;
import de.uds.MonitorInterventionMetafora.shared.commonformat.MetaforaStrings;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesCategory;
import de.uds.MonitorInterventionMetafora.shared.suggestedmessages.SuggestedMessagesModel;

public class SuggestedMessagesModelParserForServer {
	static Logger logger = Logger.getLogger(SuggestedMessagesModelParserForServer.class);
	
	public static XmlFragment toXml(SuggestedMessagesModel messageModel){
		XmlFragment xmlFragment= new XmlFragment(MetaforaStrings.SUGGESTED_MESSAGES);
		
		for (SuggestedMessagesCategory suggestedMessageCategory : messageModel.getSuggestionCategories()){
			xmlFragment.addContent(SuggestedMessagesCategoryParserForServer.toXml(suggestedMessageCategory));
		}
		return xmlFragment;
	}
	
	public static SuggestedMessagesModel fromXml(XmlFragment xmlFragment){
		if (xmlFragment != null){
			SuggestedMessagesModel model = new SuggestedMessagesModel();
			for (XmlFragment categoryFragment : xmlFragment.getChildren(MetaforaStrings.SUGGESTED_MESSAGE_CATEGORY)){
				SuggestedMessagesCategory suggestedMessage = SuggestedMessagesCategoryParserForServer.fromXml(categoryFragment);
				if (suggestedMessage != null){
					model.addCategory(suggestedMessage);
				}
			}
			return model;
		}
		else {
			logger.warn("[fromXml] Null fragement sent, returning null");
			return null;
		}
	}

}
