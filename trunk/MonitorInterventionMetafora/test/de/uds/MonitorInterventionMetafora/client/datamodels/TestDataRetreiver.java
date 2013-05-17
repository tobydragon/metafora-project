package de.uds.MonitorInterventionMetafora.client.datamodels;

import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;


import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfActionType;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfContent;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfObject;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfUser;

public class TestDataRetreiver implements AsyncCallback<List<CfAction>>{

//	ClientMonitorDataModel model;
	List<CfAction> actions;
	
	public TestDataRetreiver(){
//		model = new ClientMonitorDataModel();
		actions = createActions();
//		sendStartupMessage();
	}
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
	 	 System.out.println("INFO\t\t[TestDataRetreiver.onFailure]");

		
	}

	@Override
	public void onSuccess(List<CfAction> result) {
	 	 System.out.println("INFO\t\t[TestDataRetreiver.onSuccess] actions :" + result);
		actions = result;
//		if(result!=null){
//			model.addData(result);
//		}
	}
	
//	private void sendStartupMessage() {
//		CfAction startupMessage=new CfAction();
//	 	 startupMessage.setTime(GWTUtils.getTimeStamp());
//	 	  
//	 	 CfActionType _cfActionType=new CfActionType();
//	 	 _cfActionType.setType("START_FILE_INPUT");
//	 	 startupMessage.setCfActionType(_cfActionType);
//	 	 System.out.println("INFO\t\t[MonitorPanelContainer.sendStartupMessage] Sending monitoring start from Client");
//	 	 ServerCommunication.getInstance().processAction("MonitoringClient",startupMessage,this);	
//	}
//	
	
	public List<CfAction> getActions(){
		return actions;
	}
	
//	public ClientMonitorDataModel getModel(){
//		return model;
//	}
	
	private List<CfAction> createActions(){
		List<CfAction> actions = new Vector<CfAction>();
		
		CfActionType cfActionType = new CfActionType("indicator","OTHER", "true");
		
		CfAction myAction = new CfAction(System.currentTimeMillis(), cfActionType);
		myAction.addUser(new CfUser("PlaTo", "originator"));
		myAction.addUser(new CfUser("Ken", "student"));
		
		CfObject myObject = new CfObject("KerstinsMap2001", "planning-map");
		myObject.addProperty(new CfProperty("TOOL", "PlanningTool"));
		myObject.addProperty(new CfProperty("MAP-ID", "KerstinsMap2001"));
		myAction.addObject(myObject);
		
		CfContent myContent = new CfContent("Kerstin and Bob opened map KerstinsMap2001");
		myContent.addProperty(new CfProperty("INDICATOR_TYPE", "activity"));
		myContent.addProperty(new CfProperty("TOOL", "PlanningTool"));
		myContent.addProperty(new CfProperty("ACTIVITY_TYPE", "OPEN_RESOURCE"));
		myContent.addProperty(new CfProperty("GROUP_ID", "group2"));
		myContent.addProperty(new CfProperty("CHALLENGE_ID", "2"));
		myContent.addProperty(new CfProperty("CHALLENGE_NAME", "Default"));
		myAction.setCfContent(myContent);
		
		actions.add(myAction);
		
		//----------------------------
		
		cfActionType = new CfActionType("Indicator","CREATE", "true");
		
		myAction = new CfAction(System.currentTimeMillis(), cfActionType);
		myAction.addUser(new CfUser("Ken", "student"));
		
		myObject = new CfObject("NZKKke4e-FoESvAGyeUK7h", "shape");
		myObject.addProperty(new CfProperty("TOOL", "eXpresser"));
		myObject.addProperty(new CfProperty("name", "Shape 46"));
		myObject.addProperty(new CfProperty("iterations", "1"));
		myAction.addObject(myObject);
		
		myContent = new CfContent("Ken created a pattern shape.");
		myContent.addProperty(new CfProperty("INDICATOR_TYPE", "activity"));
		myContent.addProperty(new CfProperty("TOOL", "eXpresser"));
		myContent.addProperty(new CfProperty("GROUP_ID", "group1"));
		myContent.addProperty(new CfProperty("CHALLENGE_ID", "42"));
		myContent.addProperty(new CfProperty("CHALLENGE_NAME", "Train Track"));
		myAction.setCfContent(myContent);
		
		actions.add(myAction);
		
//		-----------------------------
		
		
		cfActionType = new CfActionType("Indicator","CREATE", "true");
		
		myAction = new CfAction(System.currentTimeMillis(), cfActionType);
		myAction.addUser(new CfUser("Bob", "student"));
		
		myObject = new CfObject("5", "Help Request");
		myObject.addProperty(new CfProperty("TOOL", "LASAD"));
		myObject.addProperty(new CfProperty("MAP-ID", "2"));
		myAction.addObject(myObject);
		
		myContent = new CfContent("Bob has created box 1 of type Help Request");
		myContent.addProperty(new CfProperty("INDICATOR_TYPE", "activity"));
		myContent.addProperty(new CfProperty("TOOL", "LASAD"));
		myContent.addProperty(new CfProperty("GROUP_ID", "group1"));
		myContent.addProperty(new CfProperty("CHALLENGE_ID", "42"));
		myContent.addProperty(new CfProperty("CHALLENGE_NAME", "Train Track"));
		myAction.setCfContent(myContent);
		
		actions.add(myAction);
		
		return actions;
	}

}
