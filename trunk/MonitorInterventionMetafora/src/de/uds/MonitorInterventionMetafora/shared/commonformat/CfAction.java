package de.uds.MonitorInterventionMetafora.shared.commonformat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CfAction implements Serializable, Comparable<CfAction>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6001855127174847426L;

	long time;
	
	CfActionType cfActionType;
	List<CfUser> cfUsers;
	List<CfObject> cfObjects;
	CfContent cfContent;
	
	public CfAction(){
		this.cfUsers = new ArrayList<CfUser>();
		this.cfObjects = new ArrayList<CfObject>();
	}
	
	public CfAction(long time, CfActionType cfActionType) {
		super();
		this.time = time;
		this.cfActionType = cfActionType;
		this.cfUsers = new ArrayList<CfUser>();
		this.cfObjects = new ArrayList<CfObject>();
	}
	
	
	
	public CfAction(long time, CfActionType cfActionType, List<CfUser> cfUsers,
			List<CfObject> cfObjects) {
		this(time, cfActionType);
		this.cfUsers = cfUsers;
		this.cfObjects = cfObjects;
	}
	
	public CfAction(long time, CfActionType cfActionType, List<CfUser> cfUsers,
			List<CfObject> cfObjects, CfContent cfContent) {
		this(time, cfActionType, cfUsers, cfObjects);
		this.cfContent = cfContent;
	}
	
	public void addUser(CfUser cfUser){
		cfUsers.add(cfUser);
	}

	public void addObject(CfObject cfObject){
		cfObjects.add(cfObject);
	}
	
	public void setTime(long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public CfActionType getCfActionType() {
		return cfActionType;
	}

	
	public void setCfActionType(CfActionType  _cfActionType) {
	 cfActionType= _cfActionType;
	}


	public List<CfUser> getCfUsers() {
		return cfUsers;
	}
	
	public CfUser getUserWithRole(String role){
		for (CfUser user : cfUsers){
			if (user.getrole().equalsIgnoreCase(role)){
				return user;
			}
		}
		return null;
	}

	public String getListofUsersAsStringWithRole(String role){
		String list = "";
		for (CfUser user : cfUsers){
			if (user.getrole().equalsIgnoreCase(role)){
				list = list + user.getid() + ", ";
			}
		}
		if (list.endsWith(", ")){
			list = list.substring(0, list.length()-2);
		}
		return list;
	}
	

	public List<CfObject> getCfObjects() {
		return cfObjects;
	}

	
	public void setCfContent(CfContent cfContent) {
		this.cfContent = cfContent;
	}

	
    public CfContent getCfContent() {
		return cfContent;
	}


	public String toString(){
		String str = Long.toString(time);
		str += "\n" + cfActionType.toString();
		if (cfContent != null){
			str += "\n" + cfContent.toString();
		}
		str += "\n" + cfUsers.toString();
		str += "\n" + cfObjects.toString();
		
		return str;
	}

	@Override
	public int compareTo(CfAction o) {
		int dif=(int) (this.getTime()-o.getTime());
		return dif;
	}
	
	public boolean containsUser(CfUser potentialUser){
		for (CfUser user : cfUsers){
			if (user.equals(potentialUser)){
				return true;
			}
		}
		return false;
	}
	
	public List<CfUser> getUsersWithRole(String role){
		List<CfUser> users = new Vector<CfUser>();
		for (CfUser user : cfUsers){
			if (user.getrole().equals(role)){
				users.add(user);
			}
		}
		return users;
	}

	public void replaceUserIds(Map<String, String> old2newId){
		List <CfUser> users = getCfUsers();
		for (CfUser user : users){
			String newName = old2newId.get(user.getid());
			if (newName != null){
				cfContent.replaceStringInDescription(user.getid(), newName);
				user.setid(newName);
			}
			else {
				System.err.println("No new name for " + user.getid());
			}
		}
	}
	
}
