package de.uds.visualizer.client.communication.actionresponses;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uds.visualizer.shared.commonformat.CfAction;
import de.uds.visualizer.shared.commonformat.CfInteractionData;

public interface  RequestHistoryCallBack extends AsyncCallback<List<CfAction>>{


}
