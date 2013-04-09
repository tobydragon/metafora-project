package de.kuei.metafora.xmppbridge.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class CommonFormatCreator {
	private Document doc;

	private boolean hasObject = false;
	private boolean hasProperties = false;
	private boolean hasUsers = false;
	private boolean hasTime = false;
	private boolean hasActiontype = false;
	private boolean hasContent = false;
	private boolean hasContentProperties = false;

	private boolean hasDescription = false;

	private static final String ACTION = "action";
	private static final String ACTIONTYPE = "actiontype";
	private static final String USER = "user";
	private static final String OBJECT = "object";
	private static final String PROPERTIES = "properties";
	private static final String PROPERTY = "property";

	private static final String CONTENT = "content";
	private static final String DESCRIPTION = "description";

	private static final String TIME = "time";
	private static final String CLASSIFICATION = "classification";
	private static final String TYPE = "type";
	private static final String LOGGED = "logged";
	private static final String SUCCEED = "succeed";
	private static final String USERID = "id";
	private static final String IP = "ip";
	private static final String ROLE = "role";
	private static final String OBJECTID = "id";
	private static final String OBJECTTYPE = "type";
	private static final String NAME = "name";
	private static final String VALUE = "value";

	/**
	 * This Class is meant to simplify the Server-side creation of XML-Documents
	 * according to the Metafora-Common Format.
	 */

	/**
	 * Simple constructor with only an action-element created. The document ist
	 * NOT valid after a call of CommonFormatCreator()!
	 * 
	 * @throws XMLException
	 *             Thrown, if there was a problem creating the document
	 * 
	 * @see XMLUtils#createDocument()
	 */
	private CommonFormatCreator() throws XMLException {// was private before
		doc = XMLUtils.createDocument();

		Element elAction = doc.createElement(ACTION);

		doc.appendChild(elAction);
	}

	/**
	 * One of the standard constructors. Should be preferred to
	 * CommonFormatCreator(String document) as is doesn't rely on an existing
	 * Document to be valid CommonFormatCreator(String document)
	 * 
	 * @param time
	 *            Time of the action
	 * @param classification
	 *            Classification of the actiontype
	 * @param type
	 *            type of the actiontype
	 * @param logged
	 *            logged-attribute of the actiontype
	 * @param succeed
	 *            succeed-attribute of the actiontype
	 * @throws XMLException
	 *             Thrown, if there was a problem creating the document
	 * @see XMLUtils#createDocument()
	 */
	public CommonFormatCreator(long time, Classification classification,
			String type, boolean logged, boolean succeed) throws XMLException {

		this();

		setTime(time);
		setActiontype(classification, type, logged, succeed);

	}

	/**
	 * One of the standard constructors. Should be preferred to
	 * CommonFormatCreator(String document) as is doesn't rely on an existing
	 * Document to be valid CommonFormatCreator(String document)
	 * 
	 * @param time
	 *            Time of the action
	 * @param classification
	 *            Classification of the actiontype
	 * @param type
	 *            type of the actiontype
	 * @param logged
	 *            logged-attribute of the actiontype
	 * @throws XMLException
	 *             Thrown, if there was a problem creating the document
	 * @see XMLUtils#createDocument()
	 */
	public CommonFormatCreator(long time, Classification classification,
			String type, boolean logged) throws XMLException {

		this();

		setTime(time);
		setActiontype(classification, type, logged);

	}

	/**
	 * Parses the specified document and sets this
	 * CommonFormatCreator-instance's document to the parsed document. This
	 * constructor does not validate the specified document. Thus the
	 * CommonFormatCreator-instance may return an invalid document
	 * 
	 * @param document
	 *            Document parsed
	 * @throws XMLException
	 *             Thrown when parsing failed
	 * @see XMLUtils#parseXMLString(String, boolean)
	 */
	public CommonFormatCreator(String document) throws XMLException {
		doc = XMLUtils.parseXMLString(document, false);

		if (doc.getElementsByTagName(ACTIONTYPE).getLength() > 0)
			hasActiontype = true;
		if (doc.getElementsByTagName(OBJECT).getLength() > 0)
			hasObject = true;
		if (doc.getElementsByTagName(USER).getLength() > 0)
			hasUsers = true;
		if (doc.getElementsByTagName(CONTENT).getLength() > 0)
			hasContent = true;
		if (doc.getElementsByTagName(DESCRIPTION).getLength() > 0)
			hasDescription = true;

		if (doc.getElementsByTagName(PROPERTIES).getLength() == 1) {
			if (hasContent
					&& doc.getElementsByTagName(PROPERTIES).item(0)
							.getParentNode()
							.equals(doc.getElementsByTagName(CONTENT).item(0))) {
				hasContentProperties = true;
			} else
				hasProperties = true;
		} else if (doc.getElementsByTagName(PROPERTIES).getLength() > 1) {
			hasProperties = true;
			hasContentProperties = true;
		}

		if (doc.getElementsByTagName(ACTION).item(0).hasAttributes()) {
			int i = 0;
			// find out whether the document already has a time-attribute
			while (!hasTime
					&& i < doc.getElementsByTagName(ACTION).item(0)
							.getAttributes().getLength()) {
				if (doc.getElementsByTagName(ACTION).item(0).getAttributes()
						.item(i).getNodeName().equals(TIME)) {
					hasTime = true;
				}
				i++;
			}

		}
	}

	/**
	 * Adds a new user-Node to the document by calling
	 * {@link #addUser(String, Role)}. Afterwards sets the ip to the specified
	 * value if needed
	 * 
	 * @param id
	 *            ID of the User
	 * @param ip
	 *            IP of the User. If ip has value null or "" the attribute will
	 *            be left out in the document
	 * @param role
	 *            Role of the User as specified by the commonformat.dtd
	 * @see #addUser(String, Role)
	 */
	public void addUser(String id, String ip, Role role) {
		Element elUser = addUser(id, role);
		if (ip != null && ip.length() > 0) {
			elUser.setAttribute(IP, ip);
		}
	}

	/**
	 * Adds a new user-Element to the document and returns the newly created
	 * Element for further manipulations
	 * 
	 * @param id
	 *            ID of the User
	 * @param ip
	 *            IP of the User. If ip has value null or "" the attribute will
	 *            be left out
	 * @return The newly created user-Element
	 */
	private Element addUser(String id, Role role) {
		Element elUser = doc.createElement(USER);
		elUser.setAttribute(USERID, id);
		elUser.setAttribute(ROLE, role.toString());

		Node elAction = doc.getElementsByTagName(ACTION).item(0);
		if (hasObject) {
			elAction.insertBefore(elUser, doc.getElementsByTagName(OBJECT)
					.item(0));
		} else if (hasContent) {
			elAction.insertBefore(elUser, doc.getElementsByTagName(CONTENT)
					.item(0));
		} else {
			elAction.appendChild(elUser);
		}

		hasUsers = true;
		return elUser;
	}

	/**
	 * Sets the time of the action
	 * 
	 * @param time
	 *            Timestamp of the action
	 */
	public void setTime(long time) {
		((Element) (doc.getElementsByTagName(ACTION).item(0))).setAttribute(
				TIME, "" + time);
		hasTime = true;
	}

	/**
	 * Creates the action-element (if necessary) and sets its attributes
	 * including the succeed-attribute
	 * 
	 * @param classification
	 *            New classification-value of the actiontype-element
	 * @param type
	 *            New type-value of the actiontype-element
	 * @param logged
	 *            New logged-value of the actiontype-element
	 * @param succeed
	 *            New succeed-value of the actiontype-element
	 */
	public void setActiontype(Classification classification, String type,
			boolean logged, boolean succeed) {

		setActiontype(classification, type, logged);
		((Element) (doc.getElementsByTagName(ACTIONTYPE).item(0)))
				.setAttribute(SUCCEED, "" + succeed);

	}

	/**
	 * Creates the action-element (if necessary) and sets its attributes leaving
	 * out the succeed-attribute
	 * 
	 * @param classification
	 *            New classification-value of the actiontype-element
	 * @param type
	 *            New type-value of the actiontype-element
	 * @param logged
	 *            New logged-value of the actiontype-element
	 */
	public void setActiontype(Classification classification, String type,
			boolean logged) {
		Element elActiontype;
		if (hasActiontype) {
			elActiontype = (Element) (doc.getElementsByTagName(ACTIONTYPE)
					.item(0));
		} else {
			elActiontype = doc.createElement(ACTIONTYPE);
			if (hasUsers) {
				doc.getElementsByTagName(ACTION)
						.item(0)
						.insertBefore(elActiontype,
								doc.getElementsByTagName(USER).item(0));
			} else if (hasObject) {
				doc.getElementsByTagName(ACTION)
						.item(0)
						.insertBefore(elActiontype,
								doc.getElementsByTagName(OBJECT).item(0));
			} else if (hasContent) {
				doc.getElementsByTagName(ACTION)
						.item(0)
						.insertBefore(elActiontype,
								doc.getElementsByTagName(CONTENT).item(0));
			} else {
				doc.getElementsByTagName(ACTION).item(0)
						.appendChild(elActiontype);
			}
			hasActiontype = true;
		}
		elActiontype.setAttribute(CLASSIFICATION, classification.toString());
		elActiontype.setAttribute(TYPE, type);
		elActiontype.setAttribute(LOGGED, "" + logged);

		hasActiontype = true;

	}

	/**
	 * Creates an object-element and sets the attributes id and type to the
	 * specified values or, if an object-element already exists, creates it and
	 * sets the specified attributes
	 * 
	 * @param id
	 *            ID of the object
	 * @param type
	 *            Type of the object
	 */
	public void setObject(String id, String type) {
		Element elObject;
		if (hasObject) {
			elObject = (Element) doc.getElementsByTagName(OBJECT).item(0);
		} else {
			elObject = doc.createElement(OBJECT);
			if (hasContent) {
				doc.getElementsByTagName(ACTION)
						.item(0)
						.insertBefore(elObject,
								doc.getElementsByTagName(CONTENT).item(0));
			} else {
				doc.getElementsByTagName(ACTION).item(0).appendChild(elObject);

			}
			hasObject = true;

		}
		elObject.setAttribute(OBJECTID, id);
		elObject.setAttribute(OBJECTTYPE, type);
	}

	/**
	 * Adds a content-element to the action-element
	 * 
	 * @return content-element of the document
	 */
	private Element setContent() {
		Element elContent;
		if (hasContent) {
			elContent = (Element) doc.getElementsByTagName(CONTENT).item(0);
		} else {
			elContent = doc.createElement(CONTENT);
			doc.getElementsByTagName(ACTION).item(0).appendChild(elContent);

			hasContent = true;

		}
		return elContent;
	}

	/**
	 * Indicates whether or not the document has a content-element
	 * 
	 * @return Returns true if the document has a content-element or false if
	 *         the document hasn't
	 */
	public boolean hasContent() {
		return hasContent;
	}

	/**
	 * Sets the description of the content-element to the specified value
	 * 
	 * @param description
	 *            Description of the element
	 * @throws XMLException
	 *             Thrown when the document has no content-element
	 */
	public void setDescription(String description) {
		Element elDescription;

		Node noContent = setContent();

		if (!hasDescription) {
			elDescription = doc.createElement(DESCRIPTION);
			if (hasContentProperties) {
				noContent.insertBefore(elDescription, noContent.getChildNodes()
						.item(0));
			} else {
				noContent.appendChild(elDescription);
			}
			hasDescription = true;
		} else {
			elDescription = (Element) noContent.getChildNodes().item(0);
		}
		if (elDescription.getChildNodes().getLength() > 0) {
			elDescription.getChildNodes().item(0).setNodeValue(description);
		} else {
			elDescription.appendChild(doc.createTextNode(description));
		}

	}
	
	/**
	 * Sets the description of the content-element to the specified value
	 * 
	 * @param description
	 *            Description of the element
	 * @throws XMLException
	 *             Thrown when the document has no content-element
	 */
	public void setCdataDescription(String description) {
		Element elDescription;

		Node noContent = setContent();

		if (!hasDescription) {
			elDescription = doc.createElement(DESCRIPTION);
			if (hasContentProperties) {
				noContent.insertBefore(elDescription, noContent.getChildNodes()
						.item(0));
			} else {
				noContent.appendChild(elDescription);
			}
			hasDescription = true;
		} else {
			elDescription = (Element) noContent.getChildNodes().item(0);
		}
		if (elDescription.getChildNodes().getLength() > 0) {
			elDescription.getChildNodes().item(0).setNodeValue(description);
		} else {
			elDescription.appendChild(doc.createCDATASection(description));
		}

	}

	/**
	 * Adds a property-element with the specified name and value the
	 * content-properties
	 * 
	 * @param name
	 *            of the property
	 * @param value
	 *            of the property
	 */
	public void addContentProperty(String name, String value) {
		Node noProperties;
		Element noContent = setContent();
		if (!hasContentProperties) {
			// add properties-element
			noProperties = doc.createElement(PROPERTIES);
			noContent.appendChild(noProperties);

			hasContentProperties = true;

		} else {
			// last Child should be properties as long as additional_information
			// is not added to this CommonFormatCreator
			noProperties = (Element) (doc.getElementsByTagName(CONTENT).item(0))
					.getLastChild();
		}

		Element elProperty = doc.createElement(PROPERTY);
		elProperty.setAttribute(NAME, name);
		elProperty.setAttribute(VALUE, value);

		noProperties.appendChild(elProperty);
	}

	/**
	 * Indicates whether or not the document has an object-element
	 * 
	 * @return Returns true if the document has an object-element or false if
	 *         the document hasn't
	 */
	public boolean hasObject() {
		return hasObject;
	}

	/**
	 * Indicates whether or not the document's time-attribute is set
	 * 
	 * @return Returns true if the document's time-attribute is set or false if
	 *         it isn't
	 */
	public boolean hasTime() {
		return hasTime;
	}

	/**
	 * Indicates whether or not the document has a properties-element
	 * 
	 * @return Returns true if the document has a properties-element or false if
	 *         the document hasn't
	 */
	public boolean hasProperties() {
		return hasProperties;
	}

	/**
	 * Indicates whether or not the document has a users-element
	 * 
	 * @return Returns true if the document has at least one users-element or
	 *         false if the document hasn't
	 */
	public boolean hasUsers() {
		return hasUsers;
	}

	/**
	 * Indicates whether or not the document has an actiontype-element
	 * 
	 * @return Returns true if the document has an actiontype-element or false
	 *         if the document hasn't
	 */
	public boolean hasActiontype() {
		return hasActiontype;
	}

	/**
	 * Gets the document from this CommonFormatCreator-instance
	 * 
	 * @return The document created with this CommonFormatCreator-instance as a
	 *         String
	 * @throws XMLException
	 *             Thrown if there was a problem writing the document into a
	 *             String
	 * @see {@link XMLUtils#documentToString(Document)}
	 */
	public String getDocument() throws XMLException {
		return XMLUtils.documentToString(doc);
	}

	/**
	 * Creates a property-element belonging to the documents object-element if
	 * such exists
	 * 
	 * @param name
	 *            Name of the property
	 * @param value
	 *            Value of the property
	 * @throws XMLException
	 *             Thrown if the document has no object-element yet
	 */
	public void addProperty(String name, String value) throws XMLException {
		Node noProperties;
		if (!hasObject) {
			throw new XMLException(
					"Tried to create a properties-Node without an existing object-Node.");
		} else if (!hasProperties) {
			// add properties-element
			noProperties = doc.createElement(PROPERTIES);
			doc.getElementsByTagName(OBJECT).item(0).appendChild(noProperties);

			hasProperties = true;

		} else {
			noProperties = doc.getElementsByTagName(PROPERTIES).item(0);
		}

		Element elProperty = doc.createElement(PROPERTY);
		elProperty.setAttribute(NAME, name);
		elProperty.setAttribute(VALUE, value);

		noProperties.appendChild(elProperty);
	}

}