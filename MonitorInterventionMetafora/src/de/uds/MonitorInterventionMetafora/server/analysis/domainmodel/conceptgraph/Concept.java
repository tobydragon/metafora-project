package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(as=ConceptImpl.class)
public interface Concept {
	
	public String getConceptTitle();
	public SummaryInfo getSummaryInfo();
	public double getDataImportance();

}
