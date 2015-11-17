package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class ConceptImpl implements Concept{
	private String conceptTitle;
	
	public ConceptImpl() {
		super();
	}
	
	public ConceptImpl(String conceptTitle){
		super();
		this.conceptTitle = conceptTitle;
	}

	public String getConceptTitle() {
		return conceptTitle;
	}

	public void setConceptTitle(String conceptTitle) {
		this.conceptTitle = conceptTitle;
	}

	@Override
	public SummaryInfo getSummaryInfo() {
		return new SummaryInfo();
	}

}
