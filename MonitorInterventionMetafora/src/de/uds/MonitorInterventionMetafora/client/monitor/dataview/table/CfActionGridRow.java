
package de.uds.MonitorInterventionMetafora.client.monitor.dataview.table;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModel;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CommonFormatStrings;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.PropertyLocation;
import de.uds.MonitorInterventionMetafora.shared.monitor.MonitorConstants;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.StandardRuleBuilder;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.StandardRuleType;
import de.uds.MonitorInterventionMetafora.shared.utils.Logger;

public class CfActionGridRow extends BaseModel {
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 6376988771728912041L;

	private CfAction indicator;

	public CfActionGridRow (CfAction indicator){
		this.indicator = indicator;
		setGridItemProperties();
	}

	private void setGridItemPropertySingleValue(StandardRuleType type){
		ActionPropertyRule rule = StandardRuleBuilder.buildStandardRule(type);
		set(rule.getDisplayText(), getGridItemPropertySingleValue(type));
	}
	
	private void setGridItemPropertyMultipleValues(StandardRuleType type){
		ActionPropertyRule rule = StandardRuleBuilder.buildStandardRule(type);
		set(rule.getDisplayText(), getGridItemPropertyMultipleValues(type));
	}
	
	public String getGridItemPropertySingleValue(StandardRuleType type){
		ActionPropertyRule rule = StandardRuleBuilder.buildStandardRule(type);
	    String value = rule.getSingleActionValue(indicator);
	    if (value == null){
	    	value = MonitorConstants.BLANK_PROPERTY_LABEL;
	    }
		return value;
	}
	
	public String getGridItemPropertyMultipleValues(StandardRuleType type){
		ActionPropertyRule rule = StandardRuleBuilder.buildStandardRule(type);
	    List<String> values = rule.getActionValue(indicator);
	    String valuesString="";
		for (String user : values){
			valuesString += user + "-";
		}
		return valuesString;
	}
	
	
	private void setGridItemProperties(){
		
		try {
			setGridItemPropertyMultipleValues(StandardRuleType.USER_ID);
			
//			set(MonitorConstants.ACTION_TYPE_LABEL, indicator.getCfActionType().getType());
			setGridItemPropertySingleValue(StandardRuleType.ACTION_CLASSIFICATION);
		    setGridItemPropertySingleValue(StandardRuleType.DESCRIPTION);
		    setGridItemPropertySingleValue(StandardRuleType.SENDING_TOOL);
		    setGridItemPropertySingleValue(StandardRuleType.CHALLENGE_NAME);
		    setGridItemPropertySingleValue(StandardRuleType.INDICATOR_TYPE);
		    setGridItemPropertySingleValue(StandardRuleType.TAGS);
		    setGridItemPropertySingleValue(StandardRuleType.TAGS);
		    setGridItemPropertySingleValue(StandardRuleType.WORD_COUNT);
		    setGridItemPropertySingleValue(StandardRuleType.TIME);
			    
		   // set(MonitorConstants.ACTION_TIME_LABEL, DateTimeFormat.getFormat("HH:mm:ss '@' dd'/'MM").format (new Date(indicator.getTime())) );

		}
		catch (Exception e){
			logger.error("[setGridItemProperties] missing attributes, row not added for indicator: " +indicator.toString());
		}
	}
	
	public String getColor(){
		if (indicator.getCfContent() != null){
		  return indicator.getCfContent().getPropertyValue(CommonFormatStrings.COLOR);
		}
		return null;
	}

	public CfAction getIndicator() {
		return indicator;
	}

	
	
//	public String getIndicatorType(){
//		
//		String value=get(MonitorConstants.INDICATOR_TYPE_LABEL);
//		if(value==null)
//			value="";
//	return value;
//			
//	}
//	
//	public String getChallengeName(){
//		
//		String value=get(MonitorConstants.CHALLENGE_NAME_LABEL);
//		if(value==null)
//			value="";
//	return value;
//			
//	}
//	
//	
//	
//	
//	
//	public String getTime(){
//		
//		String value=get(MonitorConstants.ACTION_TIME_LABEL);
//		if(value==null)
//			value="";
//	return value;
//			
//	}
//	
//	public String getTool(){
//		
//		String value=get(MonitorConstants.TOOL_LABEL);
//		if(value==null)
//			value="";
//	return value;
//			
//	}
//	public String getWordCount(){
//		
//		String value=get(MonitorConstants.WORD_COUNT_LABEL);
//		if(value==null)
//			value="";
//	return value;
//			
//	}
//	public String getDescription(){
//		
//		String value=get(MonitorConstants.DESCRIPTION_LABEL);
//		if(value==null)
//			value="";
//	return value;
//		
//	}
//	public String getClassification(){
//		String value=get(MonitorConstants.ACTION_CLASSIFICATION_LABEL);
//		if(value==null)
//			value="";
//	return value;
//	}
//	public String getActionType(){
//		
//		String value= get(MonitorConstants.ACTION_TYPE_LABEL);
//		if(value==null)
//			value="";
//	return value;
//	}
//	public String getUsers(){
//		
//		String value= get(MonitorConstants.USER_ID_LABEL);
//		if(value==null)
//			value="";
//	return value;
//	
//	}
//	public String getTags(){
//		if(indicator.getCfObjects().size()>0){
//		if(indicator.getCfObjects().get(0)!=null){
//		    return indicator.getCfObjects().get(0).getProperty(MonitorConstants.TAGS).getValue();
//		}
//		}
//		return "";
//	}
}
