package de.kuei.metafora.xmppbridge.xmpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.Affiliate;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.InvitationRejectionListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.Occupant;
import org.jivesoftware.smackx.muc.ParticipantStatusListener;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.muc.SubjectUpdatedListener;

import de.kuei.metafora.xmppbridge.xmpp.interfaces.MUCListener;
import de.kuei.metafora.xmppbridge.xmpp.util.NotNullMessageFilter;

public class XmppMUC implements InvitationRejectionListener,
		ParticipantStatusListener, SubjectUpdatedListener {

	private static final Logger log = Logger.getLogger(XmppMUC.class);

	private ServerConnection connection = null;
	private MultiUserChat muc = null;
	private String alias = null;
	private String roomname = null;

	private String password = "";

	private Vector<MUCListener> listeners;

	public XmppMUC(String roomname, String alias, ServerConnection connection)
			throws Exception {
		this.connection = connection;
		this.alias = alias;
		this.roomname = roomname;
		muc = connection.getMultiUserChat(roomname);

		XmppMUCManager.getInstance().addXmppMUC(this, connection);

		listeners = new Vector<MUCListener>();

		muc.addInvitationRejectionListener(this);
		muc.addParticipantStatusListener(this);
		muc.addSubjectUpdatedListener(this);
	}

	public ServerConnection getConnection() {
		return connection;
	}

	public void leave() {
		muc.leave();
	}

	public boolean isJoined() {
		if (connection.isConnected())
			return muc.isJoined();
		return false;
	}

	public String getRoomName() {
		return muc.getRoom();
	}
	
	protected String getPassword() {
		return password;
	}

	public int getOccupantsCount() {
		RoomInfo info = connection.getRoomInfo(roomname);
		return info.getOccupantsCount();
	}

	public boolean isPasswordProtected() {
		RoomInfo info = connection.getRoomInfo(roomname);
		return info.isPasswordProtected();
	}

	public void createInstantRoom() {
		try {
			muc.create(alias);
			muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
		} catch (XMPPException e) {
			log.error("Room creation error on " + connection.getServer()
					+ " for " + roomname + "!", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			log.debug("XMPP error: " + e.getXMPPError());
			log.debug("Stream error: " + e.getStreamError());
		}
	}

	public void createPersistentRoom() {
		createPersistentRoom(null);
	}

	public void createPersistentRoom(String password) {
		try {
			muc.create(alias);

			Form form = muc.getConfigurationForm();
			Form submitForm = form.createAnswerForm();

			for (Iterator<FormField> fields = form.getFields(); fields
					.hasNext();) {
				FormField field = (FormField) fields.next();
				if (!FormField.TYPE_HIDDEN.equals(field.getType())
						&& field.getVariable() != null) {
					submitForm.setDefaultAnswer(field.getVariable());
				}
			}

			ArrayList<String> owners = new ArrayList<String>();
			owners.add(connection.getUser());
			submitForm.setAnswer("muc#roomconfig_roomowners", owners);

			if (password != null) {
				submitForm.setAnswer("muc#roomconfig_passwordprotectedroom",
						true);
				submitForm.setAnswer("muc#roomconfig_roomsecret", password);
			}

			muc.sendConfigurationForm(submitForm);
		} catch (XMPPException e) {
			log.error(
					"Persistent room creation error on "
							+ connection.getServer() + " for " + roomname + "!",
					e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			log.debug("XMPP error: " + e.getXMPPError());
			log.debug("Stream error: " + e.getStreamError());
		}
	}

	public void join() {
		try {
			muc.join(alias);
		} catch (XMPPException e) {
			log.error("Room join error on " + connection.getServer() + " for "
					+ roomname + "!", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			log.debug("XMPP error: " + e.getXMPPError());
			log.debug("Stream error: " + e.getStreamError());
		}
	}

	public void join(String password) {
		try {
			muc.join(alias, password);
			this.password = password;
		} catch (XMPPException e) {
			log.error("Room join error on " + connection.getServer() + " for "
					+ roomname + "!", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			log.debug("XMPP error: " + e.getXMPPError());
			log.debug("Stream error: " + e.getStreamError());
		}
	}

	public void join(int historyCount) {
		join("", historyCount);
	}

	public void join(String password, int historyCount) {
		try {
			DiscussionHistory history = new DiscussionHistory();
			history.setMaxStanzas(historyCount);
			muc.join(alias, password, history,
					SmackConfiguration.getPacketReplyTimeout());
			this.password = password;
		} catch (Exception e) {
			log.error("Room join error on " + connection.getServer() + " for "
					+ roomname + " with user " + connection.getUser() + " as "
					+ getAlias() + "!", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			if (e instanceof XMPPException) {
				XMPPException ex = (XMPPException)e;
				log.debug("XMPP error: " + ex.getXMPPError());
				log.debug("Stream error: " + ex.getStreamError());
			}
		}
	}

	protected void reconnect() {
		muc = connection.getMultiUserChat(roomname);
		muc.addInvitationRejectionListener(this);
		muc.addParticipantStatusListener(this);
		muc.addSubjectUpdatedListener(this);
		join(password, 0);
	}

	public void addPacketListener(PacketListener listener) {
		String room = connection.buildMUCName(roomname);
		connection.addPacketListener(listener, new AndFilter(
				new NotNullMessageFilter(), new FromContainsFilter(room)));
	}

	public boolean sendMessage(String text) {
		try {
			muc.sendMessage(text);
			return true;
		} catch (Exception e) {
			log.error("Send message error on " + connection.getServer()
					+ " for " + roomname + "!", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			if (e instanceof XMPPException) {
				XMPPException xe = (XMPPException) e;
				log.debug("XMPP error: " + xe.getXMPPError());
				log.debug("Stream error: " + xe.getStreamError());
			}
		}
		return false;
	}

	public boolean sendMessage(Message message) {
		try {
			muc.sendMessage(message);
			return true;
		} catch (Exception e) {
			log.error("Send message error on " + connection.getServer()
					+ " for " + roomname + "!", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			if(e instanceof XMPPException){
				XMPPException xe = (XMPPException)e;
				log.debug("XMPP error: " + xe.getXMPPError());
				log.debug("Stream error: " + xe.getStreamError());
			}
		}
		return false;
	}

	public Chat openPrivateChat(String alias, MessageListener listener) {
		return muc.createPrivateChat(connection.buildMUCName(roomname) + "/"
				+ alias, listener);
	}

	public Chat openPrivateChat(String alias) {
		return muc.createPrivateChat(connection.buildMUCName(roomname) + "/"
				+ alias, new MessageListener() {

			@Override
			public void processMessage(Chat arg0, Message arg1) {
			}
		});
	}

	public Collection<Occupant> getParticipants() {
		try {
			return muc.getParticipants();
		} catch (XMPPException e) {
			log.error("Get participants error on " + connection.getServer()
					+ " for " + roomname + "!", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			log.debug("XMPP error: " + e.getXMPPError());
			log.debug("Stream error: " + e.getStreamError());
		}
		return null;
	}

	public Collection<Affiliate> getOwners() {
		try {
			return muc.getOwners();
		} catch (XMPPException e) {
			log.error("Get owners error on " + connection.getServer() + " for "
					+ roomname + "!", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			log.debug("XMPP error: " + e.getXMPPError());
			log.debug("Stream error: " + e.getStreamError());
		}
		return null;
	}

	public Presence getOccupantsPresence(String alias) {
		return muc.getOccupantPresence(alias);
	}

	public Iterator<String> getOccupants() {
		return muc.getOccupants();
	}

	public Occupant getOccupant(String alias) {
		return muc.getOccupant(alias);
	}

	public void inviteUser(String user, String reason) {
		muc.invite(user, reason);
	}

	public void inviteUser(String user, String reason, Message message) {
		muc.invite(message, user, reason);
	}

	public String getAlias() {
		return alias;
	}

	public void changeStatus(String statusmessage, Mode status) {
		muc.changeAvailabilityStatus(statusmessage, status);
	}

	public String getSubject() {
		return muc.getSubject();
	}

	public void setSubject(String subject) {
		try {
			muc.changeSubject(subject);
		} catch (XMPPException e) {
			log.error("Change subject error on " + connection.getServer()
					+ " for " + roomname + "!", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			log.debug("XMPP error: " + e.getXMPPError());
			log.debug("Stream error: " + e.getStreamError());
		}
	}

	public void setAlias(String alias) {
		try {
			if (muc != null && muc.isJoined()) {
				muc.changeNickname(alias);
			}
			this.alias = alias;
		} catch (XMPPException e) {
			log.error("Change alias error on " + connection.getServer()
					+ " for " + roomname + "!", e);
			log.debug("Error message: " + e.getMessage());
			log.debug("Localized error message: " + e.getLocalizedMessage());
			log.debug("XMPP error: " + e.getXMPPError());
			log.debug("Stream error: " + e.getStreamError());
		}
	}

	@Override
	public void subjectUpdated(String subject, String from) {
		for (MUCListener listener : listeners) {
			listener.subjectUpdated(subject, from);
		}
	}

	@Override
	public void adminGranted(String arg0) {
	}

	@Override
	public void adminRevoked(String arg0) {
	}

	@Override
	public void banned(String arg0, String arg1, String arg2) {
	}

	@Override
	public void joined(String participant) {
		for (MUCListener listener : listeners) {
			listener.joined(participant);
		}
	}

	@Override
	public void kicked(String arg0, String arg1, String arg2) {
	}

	@Override
	public void left(String participant) {
		for (MUCListener listener : listeners) {
			listener.left(participant);
		}
	}

	@Override
	public void membershipGranted(String arg0) {
	}

	@Override
	public void membershipRevoked(String arg0) {
	}

	@Override
	public void moderatorGranted(String arg0) {
	}

	@Override
	public void moderatorRevoked(String arg0) {
	}

	@Override
	public void nicknameChanged(String participant, String newNickname) {
		for (MUCListener listener : listeners) {
			listener.nicknameChanged(participant, newNickname);
		}
	}

	@Override
	public void ownershipGranted(String arg0) {
	}

	@Override
	public void ownershipRevoked(String arg0) {
	}

	@Override
	public void voiceGranted(String arg0) {
	}

	@Override
	public void voiceRevoked(String arg0) {
	}

	@Override
	public void invitationDeclined(String invitee, String reason) {
		for (MUCListener listener : listeners) {
			listener.invitationDeclined(invitee, reason);
		}
	}
}
