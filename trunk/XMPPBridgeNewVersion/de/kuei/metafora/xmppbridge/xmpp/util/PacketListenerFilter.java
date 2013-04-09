package de.kuei.metafora.xmppbridge.xmpp.util;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.PacketFilter;

public class PacketListenerFilter {

	private PacketListener listener = null;
	private PacketFilter filter = null;
	
	public PacketListenerFilter(PacketListener listener, PacketFilter filter){
		this.listener = listener;
		this.filter = filter;
	}
	
	public PacketListener getListener(){
		return listener;
	}
	
	public PacketFilter getFilter(){
		return filter;
	}
	
}
