package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel;

public interface LearningObjectSource {
	
	public String getDescription(String learningObjectId);
	public String getLearningObjectType(String learningObjectId);
	
}
