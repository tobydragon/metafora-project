package de.uds.MonitorInterventionMetafora.client.datamodels;

import com.google.gwt.junit.client.GWTTestCase;

import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ActionPropertyValueGroupingTable;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.ActionPropertyValueModel;
import de.uds.MonitorInterventionMetafora.shared.commonformat.CfAction;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.ActionElementType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;

public class ClientMonitorDataModelTest extends GWTTestCase{

	@Override
	public String getModuleName() {
		return "de.uds.MonitorInterventionMetafora.MonitorInterventionMetafora";
	}
	
	public void testActionPropertyValueModel(){
		ActionPropertyValueModel myValue = new ActionPropertyValueModel("CREATE", 0);
		myValue.increment();
		myValue.increment();
		assertEquals(3, myValue.getOccurenceCount());
	}
	
	public void testActionPropertyValueGroupingTable(){
		TestDataRetreiver testDataRetreiver = new TestDataRetreiver();
//		System.out.println(testDataRetreiver.getActions());
//		System.out.println(testDataRetreiver.getModel().getAllActions());
		ActionPropertyValueGroupingTable table = new ActionPropertyValueGroupingTable(new ActionPropertyRule(ActionElementType.ACTION_TYPE, "classification"));
		
		for (CfAction action : testDataRetreiver.getActions()){
			table.addAction(action);
		}
		
		assertEquals(2, table.getMaxValue());
		assertEquals(2, table.getDataTable().getNumberOfColumns());
		assertEquals("OTHER", table.getDataTable().getValueString(0, 0));
		assertEquals("CREATE", table.getDataTable().getValueString(0, 0));
	}

}
