package de.kuei.metafora.gwt.smack.client;

public class Student {
	String name;
	boolean online;
	//String project;
	int group;
	
	Student(String n, boolean o, int g){
		name = n;
		online = o;
		group = g;
	}
	
	public boolean isOnline(){
		if(online) return true;
		else return false;
	}
}
