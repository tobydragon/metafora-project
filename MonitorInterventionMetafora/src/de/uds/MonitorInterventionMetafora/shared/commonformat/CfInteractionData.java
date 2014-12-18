package de.uds.MonitorInterventionMetafora.shared.commonformat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;



public class CfInteractionData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4633999977001757446L;
	private List<CfAction> cfActions;
	
	public CfInteractionData(){
		
		
	}
	public CfInteractionData(List<CfAction> cfActions){
		this.cfActions = cfActions;
	}

	public List<CfAction> getCfActions() {
		return cfActions;
	}
	
	public void setCfActions(List<CfAction> _cfActions){
		
		cfActions=_cfActions;
	}

	private List<String> findAllUniqueUsers(){
		List<String> uniqueUsers = new Vector<String>();
		for (CfAction action : cfActions){
			List <CfUser> users = action.getCfUsers();
			for (CfUser user : users){
				boolean needToAdd = true;
				for (String existingUsers : uniqueUsers){
					if (existingUsers.equals(user.getid())){
						needToAdd = false;
					}
				}
				if (needToAdd){
					uniqueUsers.add(user.getid());
				}
			}
		}
		return uniqueUsers;
	}
	
	private void replaceAllIds(Map<String, String> old2newId){
		for (CfAction action : cfActions){
			action.replaceUserIds(old2newId);
		}
	}
	
	private Map <String, String> calcNewIds(List<String> oldIds){
		String prefix = "student";
		int countStart = 0;
		Map <String, String> old2newId = new HashMap<String, String>();
		for (String oldId : oldIds){
			old2newId.put(oldId, prefix+countStart);
			countStart++;
		}
		return old2newId;
		
	}
	
	public void replaceAllIds(){
		List<String> ids = findAllUniqueUsers();
		Map <String, String> old2newIds = calcNewIds(ids);
		replaceAllIds(old2newIds);
	}

}
