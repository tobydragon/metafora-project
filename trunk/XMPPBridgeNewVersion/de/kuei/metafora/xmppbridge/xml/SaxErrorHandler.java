package de.kuei.metafora.xmppbridge.xml;


import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SaxErrorHandler implements ErrorHandler{

	@Override
	public void error(SAXParseException arg0) throws SAXException {
		System.err.println("SAX Parser error at line "+arg0.getLineNumber()+": "+arg0.getMessage());
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		System.err.println("SAX Parser fatal error at line "+arg0.getLineNumber()+": "+arg0.getMessage());
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		System.err.println("SAX Parser warning at line "+arg0.getLineNumber()+": "+arg0.getMessage());
	}

}
