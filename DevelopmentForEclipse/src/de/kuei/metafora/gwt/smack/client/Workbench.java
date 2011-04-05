package de.kuei.metafora.gwt.smack.client;

import java.io.Serializable;
import java.util.List;

import net.zschech.gwt.comet.client.CometClient;
import net.zschech.gwt.comet.client.CometListener;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Workbench extends HorizontalPanel implements EntryPoint, CometListener {
	
	VerticalPanel p1;
	VerticalPanel p2;
	VerticalPanel p3;
	Development development;
	GroupMembers gm;
		
		
	public void onModuleLoad(){
		p1 = new VerticalPanel();
		p2 = new VerticalPanel();
		p3 = new VerticalPanel();
		p1.setVisible(true);
		p2.setVisible(true);
		p3.setVisible(true);
		
		ListBox task = new ListBox();
		task.addItem("general overview");
		p1.add(task);
		
		development = new Development();
		p2.add(development);
		gm = new GroupMembers();
		p2.add(gm);
		
		CometClient client = new CometClient("http://localhost:8888/development/comet", this);
		client.start();
		
		Development doc = new Development();
		p3.add(doc);
		Development vm = new Development();
		p3.add(vm);
		
		this.add(p1);
		this.add(p2);
		this.add(p3);
		setVisible(true);
		
	    RootPanel.get("moin").add(this);
	}

	@Override
	public void onConnected(int heartbeat) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Throwable exception, boolean connected) {
		Window.alert("Exception: "+exception.getMessage()+", "+exception);
	}

	@Override
	public void onHeartbeat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(List<? extends Serializable> messages) {
		for(int i=0; i<messages.size(); i++){
			development.add(messages.get(i).toString());
		}
	}
	
}	

