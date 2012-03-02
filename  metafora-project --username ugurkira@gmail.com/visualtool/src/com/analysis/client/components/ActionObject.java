package com.analysis.client.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.analysis.shared.communication.objects_old.CfProperty;
import com.analysis.shared.communication.objects_old.CfUser;



public class ActionObject {

	public Map<String, CfProperty> actionObjectProperties = new HashMap<String, CfProperty>();
	public List<CfUser> ActionUsers=new ArrayList<CfUser>();;
	public long time=0;
	public String description="";
	
public CfProperty getProperty(String key){
	
	if(actionObjectProperties.containsKey(key)){
	
		return actionObjectProperties.get(key);
			
	}
	return null;
	
	
}
	
}
