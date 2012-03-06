package de.uds.visualizer.client.communication.actionresponses;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uds.visualizer.shared.commonformat.CfAction;

public interface RequestUpdateCallBack  extends AsyncCallback<List<CfAction>>{

}
