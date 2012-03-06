package de.uds.visualizer.shared.commonformat;

import java.io.Serializable;
import java.util.List;



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
	
//	public XmlFragmentInterface toXml(){
//		XmlFragmentInterface xmlFragment= new XmlFragment(CommonFormatStrings.INTERACTION_DATA_STRING);
//		XmlFragment actionsFragment = new XmlFragment(CommonFormatStrings.ACTIONS_STRING);
//		for (CfAction cfAction : cfActions){
//			actionsFragment.addContent(cfAction.toXml());
//		}
//		xmlFragment.addContent(actionsFragment);
//		return xmlFragment;
//	}
//	
//	public static CfInteractionData fromXml(XmlFragmentInterface xmlFragment){
//		List<CfAction> cfActions = new ArrayList<CfAction>();
//		
//		XmlFragmentInterface actionsFragment = xmlFragment.cloneChild(CommonFormatStrings.ACTIONS_STRING);
//		for (XmlFragmentInterface cfActionElement : actionsFragment.getChildren(CommonFormatStrings.ACTION_STRING)){
//			cfActions.add(CfAction.fromXml(cfActionElement));
//		}
//		
//		return new CfInteractionData(cfActions);
//	}

}
