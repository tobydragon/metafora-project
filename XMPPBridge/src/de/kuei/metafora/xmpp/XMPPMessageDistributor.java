package de.kuei.metafora.xmpp;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.packet.DelayInfo;
import org.jivesoftware.smackx.packet.DelayInformation;

public class XMPPMessageDistributor implements Runnable {

	private Vector<XMPPMessageListenerLanguage> languageListeners = null;
	private Vector<XMPPMessageTimeListenerLanguage> timeLanguageListeners = null;
	private Vector<XMPPMessageListener> listeners = null;
	private Vector<XMPPMessageTimeListener> timeListeners = null;
	private Vector<XMPPMessageTimeListenerLanguageSubject> timeLanguageSubjectListeners = null;

	private Vector<XMPPPresenceListener> presenceListeners = null;
	
	private PacketCollector collector = null;

	private boolean run = true;

	private XMPPBridgeCurrent bridge;

	public XMPPMessageDistributor(XMPPBridgeCurrent bridge) {
		this.bridge = bridge;

		languageListeners = new Vector<XMPPMessageListenerLanguage>();
		timeLanguageListeners = new Vector<XMPPMessageTimeListenerLanguage>();
		listeners = new Vector<XMPPMessageListener>();
		timeListeners = new Vector<XMPPMessageTimeListener>();
		timeLanguageSubjectListeners = new Vector<XMPPMessageTimeListenerLanguageSubject>();
		
		presenceListeners = new Vector<XMPPPresenceListener>();
	}

	public void addPresenceListener(XMPPPresenceListener listener) {
		presenceListeners.add(listener);
	}

	public void removePresenceListener(XMPPPresenceListener listener) {
		presenceListeners.remove(listener);
	}
	
	public void addLanguageListener(XMPPMessageListenerLanguage listener) {
		languageListeners.add(listener);
	}

	public void addTimeLanguageListener(XMPPMessageTimeListenerLanguage listener) {
		timeLanguageListeners.add(listener);
	}

	public void addTimeLanguageSubjectListener(XMPPMessageTimeListenerLanguageSubject listener) {
		timeLanguageSubjectListeners.add(listener);
	}
	
	public void removeTimeLanguageSubjectListener(XMPPMessageTimeListenerLanguageSubject listener) {
		timeLanguageSubjectListeners.remove(listener);
	}
	
	public void removeLanguageListener(XMPPMessageListenerLanguage listener) {
		languageListeners.remove(listener);
	}

	public void removeTimeLanguageListener(
			XMPPMessageTimeListenerLanguage listener) {
		timeLanguageListeners.remove(listener);
	}

	public void addListener(XMPPMessageListener listener) {
		listeners.add(listener);
	}

	public void addTimeListener(XMPPMessageTimeListener listener) {
		timeListeners.add(listener);
	}

	public void removeListener(XMPPMessageListener listener) {
		listeners.remove(listener);
	}

	public void removeTimeListener(XMPPMessageTimeListener listener) {
		timeListeners.remove(listener);
	}

	public void stop() {
		run = false;
	}

	public void start(PacketCollector collector) {
		System.out.println("XMPPMessageDistribuor.start: languageListeners: "
				+ languageListeners.size() + " timeLanguageListeners: "
				+ timeLanguageListeners.size() + " listeners: "
				+ listeners.size() + " timelisteners: " + timeListeners.size());
		this.collector = collector;
		run = true;
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (run) {
			if (languageListeners.size() > 0
					|| timeLanguageListeners.size() > 0 || listeners.size() > 0
					|| timeListeners.size() > 0
					|| presenceListeners.size() > 0
					|| timeLanguageSubjectListeners.size() > 0) {
				Packet packet = collector.nextResult();
				processPacket(packet);
			}
		}
	}

	public void processPacket(Packet packet) {
		if (packet instanceof Message) {
			Message msg = (Message) packet;

			String from = msg.getFrom();
			String name = "";
			String chat = from;
			
			int splitPos = from.indexOf('/');
			if(splitPos > 0){
				name = from.substring(splitPos+1, from.length());
				chat = from.substring(0, splitPos);
			}
			
			String subject = msg.getSubject();
			if(subject == null){
				subject = "";
			}
			
			Collection<String> languages = msg.getBodyLanguages();

			Date time = new Date();

			Collection<PacketExtension> extensions = packet.getExtensions();
			for (PacketExtension e : extensions) {
				if (e instanceof DelayInfo) {
					DelayInfo d = (DelayInfo) e;
					time = d.getStamp();
					break;
				} else if (e instanceof DelayInformation) {
					DelayInformation d = (DelayInformation) e;
					time = d.getStamp();
					break;
				}
			}

			for (XMPPMessageListenerLanguage listener : languageListeners) {
				if (languages.size() == 0) {
					listener.newMessage(name, msg.getBody(), chat, "unknown");
				} else {
					for (String lang : languages) {
						listener.newMessage(name, msg.getBody(lang), chat, lang);
					}
				}
			}

			for (XMPPMessageTimeListenerLanguage listener : timeLanguageListeners) {
				if (languages.size() == 0) {
					listener.newMessage(name, msg.getBody(), chat, time,
							"unknown");
				} else {
					for (String lang : languages) {
						listener.newMessage(name, msg.getBody(lang), chat,
								time, lang);
					}
				}
			}
			
			for (XMPPMessageTimeListenerLanguageSubject listener : timeLanguageSubjectListeners) {
				if (languages.size() == 0) {
					listener.newMessage(name, msg.getBody(), chat, time,
							"unknown", subject);
				} else {
					for (String lang : languages) {
						listener.newMessage(name, msg.getBody(lang), chat,
								time, lang, subject);
					}
				}
			}

			for (XMPPMessageListener listener : listeners) {
				listener.newMessage(name, msg.getBody(), chat);
			}

			for (XMPPMessageTimeListener listener : timeListeners) {
				listener.newMessage(name, msg.getBody(), chat, time);
			}
		} else if (packet instanceof Presence) {
			processPresence((Presence)packet);
		}
	}

	private void processPresence(Presence presence){
		String user = presence.getFrom();
		String state = null;
		
		if(user != null && user.contains("conference")){
			String chat = null;
			String alias = null;
			
			int pos = user.indexOf('/');
			if(pos > 0){
				chat = user.substring(0, pos);
				alias = user.substring(pos+1, user.length());
			}else{
				chat = user;
				alias = "unknown";
			}
			
			if(presence.getType() == Presence.Type.available){
				if(presence.getMode() == Presence.Mode.available){
					state = XMPPBridgeCurrent.available;
				}else if(presence.getMode() == Presence.Mode.dnd){
					state = XMPPBridgeCurrent.donotdisturb;
				}else if(presence.getMode() == Presence.Mode.away){
					state = XMPPBridgeCurrent.away;
				}else if(presence.getMode() == Presence.Mode.chat){
					state = XMPPBridgeCurrent.freetochat;
				}else if(presence.getMode() == Presence.Mode.xa){
					state = XMPPBridgeCurrent.longtimeaway;
				}else{
					state = XMPPBridgeCurrent.unknown;
				}
			}else if(presence.getType() == Presence.Type.unavailable){
				state = XMPPBridgeCurrent.offline;
			}
			
			for(XMPPPresenceListener listener : presenceListeners){
				listener.presenceChangedChat(alias, chat, state);
			}
		}else{	
			state = bridge.getPresenceString(user);
			
			for(XMPPPresenceListener listener : presenceListeners){
				String uname = null;
				String device = null;
				
				int pos = user.indexOf('/');
				if(pos > 0){
					uname = user.substring(0, pos);
					device = user.substring(pos+1, user.length());
				}else{
					uname = user;
					device = "unknown";
				}
				
				listener.presenceChanged(uname, device, state);
			}
		}
	}
}
