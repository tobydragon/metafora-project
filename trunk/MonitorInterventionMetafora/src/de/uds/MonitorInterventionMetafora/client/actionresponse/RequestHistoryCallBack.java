package de.uds.MonitorInterventionMetafora.client.actionresponse;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfInteractionData;

public interface  RequestHistoryCallBack extends AsyncCallback<List<CfAction>>{


}
