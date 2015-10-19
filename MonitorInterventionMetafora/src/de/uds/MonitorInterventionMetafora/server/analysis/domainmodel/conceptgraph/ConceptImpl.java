package de.uds.MonitorInterventionMetafora.server.analysis.domainmodel.conceptgraph;

import java.util.List;

public class ConceptImpl implements Concept{
	private String conceptTitle;
	private List<ConceptImpl> children;
	
	public ConceptImpl() {
		super();
	}

	public ConceptImpl(String conceptTitle, List<ConceptImpl> children) {
		super();
		this.conceptTitle = conceptTitle;
		this.children = children;
	}


	public String getConceptTitle() {
		return conceptTitle;
	}

	public void setConceptTitle(String conceptTitle) {
		this.conceptTitle = conceptTitle;
	}


	public List<ConceptImpl> getChildren() {
		return children;
	}

	public void setChildren(List<ConceptImpl> children) {
		this.children = children;
	}


	@Override
	public SummaryInfo getSummaryInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
