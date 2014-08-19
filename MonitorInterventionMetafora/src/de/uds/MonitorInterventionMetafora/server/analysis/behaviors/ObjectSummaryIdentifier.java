package de.uds.MonitorInterventionMetafora.server.analysis.behaviors;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.google.gwt.dev.jjs.CorrelationFactory;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfProperty;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.commonformat.RunestoneStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.BehaviorType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionFilter;

public class ObjectSummaryIdentifier implements BehaviorIdentifier{
	
	ActionFilter userFilter;
	ActionFilter objectIdFilter;

	@Override
	public List<BehaviorInstance> identifyBehaviors(List<CfAction> actionsToConsider, List<String> involvedUsers,List<CfProperty> groupProperties) {
		List<BehaviorInstance> identifiedBehaviors = new Vector<BehaviorInstance>();
		
		//TODO @Caitlin: this is where we get all the actions (actions to consider) and you return a list of BehaviorInstances, one for each object (problem)
		//create instance for each student each problem
		
		
		
		List<String> objectIds = new Vector<String>();
		for (CfAction action : actionsToConsider){
					
				//This creates a list of all the object Ids
				String currectObjectId = action.getCfObjects().get(0).getId();
				
				if ((objectIds.contains(currectObjectId)) == false){
					objectIds.add(action.getCfObjects().get(0).getId());
				}
		}
		
		
		//goes through each user
		for(String user : involvedUsers){
			
			userFilter = BehaviorFilters.createUserFilter(user);		
			List<CfAction> actionsFilteredByUser = userFilter.getFilteredList(actionsToConsider);
			
			    //goes through each objectId for each user
				for(String objectId : objectIds){	
					
					objectIdFilter = BehaviorFilters.createObjectIdFilter(objectId);
					List<CfAction> actionsFilteredByObjectId = objectIdFilter.getFilteredList(actionsFilteredByUser);
					
					if (actionsFilteredByObjectId.size() > 0){
					
					long startTime = actionsFilteredByObjectId.get(0).getTime();
					long endTime = actionsFilteredByObjectId.get(0).getTime();
					boolean isCorrect = false;
					int numberTimesFalse = 0;
					String falseEntries = "";
					String type = "";
					
					
						//goes through each entry for each objectId for each user
						for (CfAction action : actionsFilteredByObjectId){
							
							long currentTime = action.getTime();
							String correctField = action.getCfContent().getPropertyValue(RunestoneStrings.CORRECT_STRING);
							String act = action.getCfObjects().get(0).getPropertyValue("ACT");
							type = action.getCfObjects().get(0).getType();
							
							
							if(currentTime < startTime){
								startTime = currentTime;
							}
							else if (currentTime > endTime){
								endTime = currentTime;
							}
							
							
							if(correctField == null){
								isCorrect = true;
							}
							else if (correctField.equalsIgnoreCase("true")){
								isCorrect = true;
							}
							else if(correctField.equalsIgnoreCase("false")){
								numberTimesFalse++;
								
								//gets the multiple choice input without the extra characters
								if(action.getCfObjects().get(0).getType().equalsIgnoreCase(RunestoneStrings.MCHOICE_STRING)){
									int startIndex = act.indexOf(":");
									startIndex = startIndex + 1;
									int endIndex = act.indexOf(":", (startIndex));	
									String actSubstring = act.substring(startIndex, endIndex);
									falseEntries = falseEntries + "/" + actSubstring;
								}
								else{
									falseEntries = falseEntries + "/" + act;
								}
							}
							
							
						}
						
						long totalTime = (endTime - startTime) / 1000;
										
						List <CfProperty >instanceProperties = new Vector<CfProperty>();
						instanceProperties.add(new CfProperty(RunestoneStrings.TIME_SPENT_STRING, String.valueOf(totalTime)));
						instanceProperties.add(new CfProperty(RunestoneStrings.IS_EVER_CORRECT_STRING,String.valueOf(isCorrect)));
						instanceProperties.add(new CfProperty(RunestoneStrings.TIMES_FALSE_STRING, String.valueOf(numberTimesFalse)));
						instanceProperties.add(new CfProperty(RunestoneStrings.FALSE_ENTRIES_STRING, falseEntries));
						instanceProperties.add(new CfProperty(RunestoneStrings.OBJECT_ID_STRING, objectId));
						instanceProperties.add(new CfProperty(RunestoneStrings.TYPE_STRING, type));
						
						List <String> userList = new Vector<String>();
						userList.add(user);
						
						BehaviorInstance instance = new BehaviorInstance(BehaviorType.OBJECT_SUMMARY, userList, instanceProperties);
						identifiedBehaviors.add(instance);
					}
				}
		}
		
		
	
		return identifiedBehaviors;
	}
}
	



