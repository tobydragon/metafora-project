package de.uds.MonitorInterventionMetafora.server.xml;

import java.util.List;

import org.jdom.Element;

public interface XmlFragmentInterface {

	public abstract void setAttribute(String attributeName, String attributeValue);

	public abstract void addContent(XmlFragmentInterface contentFragment);

	public abstract void addContent(String contentString);

	public abstract void addCdataContent(String contentString);

	public abstract String getAttributeValue(String attributeName);

	public abstract String toStringRaw();

	public abstract String toStringDoc();

	public abstract Element getRootElement();

	public abstract XmlFragmentInterface accessChild(String childName);

	public abstract XmlFragmentInterface cloneChild(String childName);

	public abstract List<XmlFragmentInterface> getChildren(String childName);

	public abstract String getChildValue(String childName);

	public abstract boolean overwriteFile(String filename);
	
	public Element getClone();

}