package com.analysis.server.xmpp;

import java.util.Date;


public class XMPPListener implements XMPPMessageTimeListener {

	/**
	 * <Action time="1304954004228"> 
	 * 		<ActionType type="DISPLAY_STATE_URL" classification="USER_INTERACTION" succeeded="UNKNOWN" /> 
	 * 		<users> 
	 * 			<User id="Toby" role="controller" /> 
	 * 		</users> 
	 * 		<objects> 
	 * 			<Object id="0" type="element"> 
	 * 				<properties> 
	 * 					<Property name="MAP_ID" value="1" />
	 * 					<Property name="ELEMENT_TYPE" value="Reference" /> 
	 * 					<Property name="REFERENCE_URL" value="http://web-expresser.appspot.com/?userKey=x5Spdu9XABVAVTSKxzQl67" />
	 * 				</properties> 
	 * 			</Object> 
	 * 		</objects> 
	 * </Action>
	 * 
	 * TODO: tab title
	 */

	@Override
	public void newMessage(String user, String message, String chat, Date time) {
		if (message.toLowerCase().contains("DISPLAY_STATE_URL".toLowerCase())) {
			int idx = message.indexOf("REFERENCE_URL");
			if (idx > 0) {
				int start = message.indexOf("value=", idx);
				start += "value=".length() + 1;
				int end = message.indexOf("\"", start);
				String url = message.substring(start, end);
				//UserManager.getInstance().sendToClients("url:" + url);
			}
		}
	}

}
