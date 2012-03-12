package de.kuei.metafora.xmpp;

public class XMPPWatcher implements Runnable{

	private boolean run = true;
	private XMPPBridgeCurrent bridge;
	
	public XMPPWatcher(XMPPBridgeCurrent bridge){
		this.bridge = bridge;
	}
	
	public void stop(){
		run = false;
	}
	
	@Override
	public void run() {
		while(run){
			try {
				Thread.sleep(10000l);
			} catch (InterruptedException e) {
				System.err.println("XMPPWatcher.run: thread sleep interruption!");
				e.printStackTrace();
			}
			bridge.watcher();
		}
	}

}
