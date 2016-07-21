package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

public class IDLink {
	
	private String parent;
	private String child;
	
	public IDLink(){}
	
	public IDLink(String parentIn, String childIn){
		this.parent = parentIn;
		this.child = childIn;
	}
	
	public String getParent(){
		return this.parent;
	}
	
	public String getChild(){
		return this.child;
	}
	
	public void setParent(String parentIn){
		this.parent = parentIn;
	}
	
	public void setChild(String childIn){
		this.child = childIn;
	}
	
	public String toString(){
		return "(Parent: " + this.parent + " Child: " + this.child + ")\n";
	}

}
