package de.kuei.metafora.xmppbridge.xmpp.interfaces;

public interface MUCListener {

	public void subjectUpdated(String subject, String from);

	public void joined(String participant);

	public void left(String participant);

	public void nicknameChanged(String participant, String newNickname);

	public void invitationDeclined(String invitee, String reason);
	
}
