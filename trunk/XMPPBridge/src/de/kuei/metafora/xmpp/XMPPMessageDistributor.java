package de.kuei.metafora.xmpp;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smackx.packet.DelayInfo;
import org.jivesoftware.smackx.packet.DelayInformation;

public class XMPPMessageDistributor implements Runnable{

	private Vector<XMPPMessageListenerLanguage> languageListeners = null;
	private Vector<XMPPMessageTimeListenerLanguage> timeLanguageListeners = null;
	private Vector<XMPPMessageListener> listeners = null;
	private Vector<XMPPMessageTimeListener> timeListeners = null;
	
	private PacketCollector collector = null;

	private boolean run = true;

	public XMPPMessageDistributor() {
		languageListeners = new Vector<XMPPMessageListenerLanguage>();
		timeLanguageListeners = new Vector<XMPPMessageTimeListenerLanguage>();
		listeners = new Vector<XMPPMessageListener>();
		timeListeners = new Vector<XMPPMessageTimeListener>();
	}

	public void addLanguageListener(XMPPMessageListenerLanguage listener) {
		languageListeners.add(listener);
	}

	public void addTimeLanguageListener(XMPPMessageTimeListenerLanguage listener) {
		timeLanguageListeners.add(listener);
	}

	public void removeLanguageListener(XMPPMessageListenerLanguage listener) {
		languageListeners.remove(listener);
	}

	public void removeTimeLanguageListener(XMPPMessageTimeListenerLanguage listener) {
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

	public void start(PacketCollector collector){
		System.out.println("XMPPMessageDistribuor.start: languageListeners: "+languageListeners.size()+" timeLanguageListeners: "+
					timeLanguageListeners.size()+" listeners: "+listeners.size()+" timelisteners: "+timeListeners.size());
		this.collector = collector;
		run = true;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		while (run) {
			if(languageListeners.size() > 0 || timeLanguageListeners.size() > 0 ||
					listeners.size() > 0 || timeListeners.size() > 0){
				Packet packet = collector.nextResult();
				processPacket(packet);
			}
		}
	}

	public void processPacket(Packet packet) {
		if (packet instanceof Message) {
			Message msg = (Message) packet;
			
			String name = msg.getFrom().substring(packet.getFrom().indexOf('/') + 1);
			String chat = msg.getFrom().substring(0, packet.getFrom().indexOf('/') + 1);

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
			
			for(XMPPMessageListenerLanguage listener : languageListeners){
				if(languages.size() == 0){
					listener.newMessage(name, msg.getBody(), chat, "unknown");
				}else{
					for(String lang : languages){
						listener.newMessage(name, msg.getBody(lang), chat, lang);
					}
				}
			}
			
			for(XMPPMessageTimeListenerLanguage listener : timeLanguageListeners){
				if(languages.size() == 0){
					listener.newMessage(name, msg.getBody(), chat, time, "unknown");
				}else{
					for(String lang : languages){
						listener.newMessage(name, msg.getBody(lang), chat, time, lang);
					}
				}
			}
			
			for(XMPPMessageListener listener : listeners){
				listener.newMessage(name, msg.getBody(), chat);
			}
			
			for(XMPPMessageTimeListener listener : timeListeners){
				listener.newMessage(name, msg.getBody(), chat, time);
			}
		}
	}

}
