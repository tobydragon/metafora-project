package de.uds.MonitorInterventionMetafora.shared.commonformat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;



public class CfInteractionData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4633999977001757446L;
	
	private List<CfAction> cfActions;
	
	public CfInteractionData(){
		
		
	}
	public CfInteractionData(List<CfAction> cfActions){
		this.cfActions = cfActions;
	}

	public List<CfAction> getCfActions() {
		return cfActions;
	}
	
	public void setCfActions(List<CfAction> _cfActions){
		
		cfActions=_cfActions;
	}

}
