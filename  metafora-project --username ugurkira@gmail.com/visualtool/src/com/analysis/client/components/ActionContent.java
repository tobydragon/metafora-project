package com.analysis.client.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.analysis.client.communication.objects.CfProperty;
import com.analysis.client.communication.objects.CfUser;

public class ActionContent {
	
	public Map<String, CfProperty> actionContentProperties = new HashMap<String, CfProperty>();
	public List<CfUser> ActionUsers=new ArrayList<CfUser>();
	public long time=0;
	//TODO may delete
	public String Description="";

}
