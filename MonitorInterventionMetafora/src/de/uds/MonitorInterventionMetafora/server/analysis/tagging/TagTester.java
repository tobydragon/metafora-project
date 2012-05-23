package de.uds.MonitorInterventionMetafora.server.analysis.tagging;

import de.uds.MonitorInterventionMetafora.server.analysis.manager.TextManager;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;



public class TagTester {

	public static void main(String[] args) 
    {
		
		
CfActionType cfActionType = new CfActionType("indicator","OTHER", "true");
		
		CfAction myAction = new CfAction(System.currentTimeMillis(), cfActionType);
		myAction.addUser(new CfUser("PlaTo", "originator"));
		myAction.addUser(new CfUser("Ken", "student"));
		
		CfObject myObject = new CfObject("KerstinsMap2001", "planning-map");
		myObject.addProperty(new CfProperty("TOOLTEXT", "ok, you can go"));
		myObject.addProperty(new CfProperty("MAP-ID", "KerstinsMap2001"));
		myAction.addObject(myObject);
		
		myObject = new CfObject("KerstinsMap2001", "planning-map");
		myObject.addProperty(new CfProperty("TOOL", "PlanningTool"));
		myObject.addProperty(new CfProperty("MAP-ID_TEXT", " i will not go but sure"));
		myAction.addObject(myObject);
		
		CfContent myContent = new CfContent("Kerstin and Bob opened map KerstinsMap2001");
		myContent.addProperty(new CfProperty("INDICATOR_TEXT", " i will not"));
		myContent.addProperty(new CfProperty("TOOL_TEXT", "i will fuck but ok"));
		myContent.addProperty(new CfProperty("ACTIVITY_TYPE", "OPEN_RESOURCE"));
		myContent.addProperty(new CfProperty("GROUP_ID", "group2"));
		myContent.addProperty(new CfProperty("CHALLENGE_ID", "2"));
		myContent.addProperty(new CfProperty("CHALLENGE_NAME", "Default"));
		myAction.setCfContent(myContent);
	
		
		TextManager agent=new TextManager(true);
		agent.tagAction(myAction);
		//agent.tag("ok, i will not go to cinema, but");

		
    }

}
