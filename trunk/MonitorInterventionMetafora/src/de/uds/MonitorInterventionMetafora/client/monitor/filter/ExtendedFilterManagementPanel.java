package de.uds.MonitorInterventionMetafora.client.monitor.filter;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.OperationsComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.PropertyComboBoxItemModel;
import de.uds.MonitorInterventionMetafora.client.monitor.grouping.ActionPropertyComboBox;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModel;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModelType;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;



public class ExtendedFilterManagementPanel extends HorizontalPanel{

	private  ComboBox<PropertyComboBoxItemModel> entityComboBox;
	private ComboBox<OperationsComboBoxModel> operationComboBox;
	private TextField<String> entityValueTextBox; 
	private  Button addButton;
	
	private ClientMonitorController interfaceManager;
	EditorGrid<FilterGridRow> grid;
	
	
	//TODO remove all dependencies on controller, controller is notified through the grid store 
	public ExtendedFilterManagementPanel( ClientMonitorController controller, EditorGrid<FilterGridRow> grid){

		interfaceManager= controller;
		this.grid = grid;
		 FormLayout layout = new FormLayout();
		 
		entityComboBox = new ActionPropertyComboBox(ActionPropertyRuleSelectorModel.getActionPropertyRuleSelectorModel(ActionPropertyRuleSelectorModelType.FILTER));
		entityComboBox.setForceSelection(true);
		entityComboBox.setPosition(0, -2);

		
		 
		operationComboBox=new ComboBox<OperationsComboBoxModel>();
		operationComboBox.setFieldLabel("Operation");
		operationComboBox.setWidth(130);
		
		
		operationComboBox.setDisplayField("displaytext");
		operationComboBox.setValueField("operationtype");
		operationComboBox.setStore(getOperations(false));
		operationComboBox.setPosition(0, -2);
		operationComboBox.setEditable(false);
		operationComboBox.setForceSelection(true);
		operationComboBox.setId("_operationComboBox");
		
		operationComboBox.setTriggerAction(TriggerAction.ALL);
		
		entityValueTextBox= new TextField<String>();
		entityValueTextBox.setFieldLabel("Value");
		entityValueTextBox.setWidth(150);
		entityValueTextBox.setEmptyText("Enter entity value");
		entityValueTextBox.setAllowBlank(false);
		entityValueTextBox.setLayoutData(layout);
		entityValueTextBox.setPosition(0, -2);
		entityValueTextBox.setId("entityValueText");
		
		final SelectionChangedListener<PropertyComboBoxItemModel> comboListenerItem =new SelectionChangedListener<PropertyComboBoxItemModel>(){
	        @Override
	        public void selectionChanged(SelectionChangedEvent<PropertyComboBoxItemModel> se) { 

	        	if(se.getSelectedItem().getActionPropertyRule().getPropertyName().equalsIgnoreCase("TIME")){
		        	operationComboBox.getStore().removeAll();
		        	operationComboBox.setStore(getOperations(true));
	        	}
	        	else{
	        	   	operationComboBox.getStore().removeAll();
		        	operationComboBox.setStore(getOperations(false));
	        	}
	        	operationComboBox.clearSelections();
	        }
	    };
		entityComboBox.addSelectionChangedListener(comboListenerItem);
		
		addButton=new Button("Add Filter",getAddButtonEvent());
		addButton.setIcon(Resources.ICONS.add());
		addButton.setId("ww");
		addButton.setPosition(0,-9);
		addButton.setLayoutData(new FitLayout());
		
		
		this.add(entityComboBox);
		this.add(operationComboBox);
		this.add(entityValueTextBox);
		this.add(addButton);
		this.setBorders(true);
		this.setSpacing(20);
		this.setHeight(53);
		//this.setWidth(600);
				
		this.layout();
		
	     


	}
	
	

	ListStore<OperationsComboBoxModel>	getOperations(boolean _isTimeOperation){
		ListStore<OperationsComboBoxModel> _operations=new ListStore<OperationsComboBoxModel>();
		if(!_isTimeOperation){
	
		OperationsComboBoxModel _equals=new OperationsComboBoxModel("Equals",OperationType.EQUALS);
		OperationsComboBoxModel _contains=new OperationsComboBoxModel("Contains",OperationType.CONTAINS);
		OperationsComboBoxModel _isoneof=new OperationsComboBoxModel("Is One Of",OperationType.ISONEOF);
		OperationsComboBoxModel _containsoneof=new OperationsComboBoxModel("Contains One Of",OperationType.CONTAINSONEOF);
		
		_operations.add(_equals);
		_operations.add(_contains);
		_operations.add(_isoneof);
		_operations.add(_containsoneof);
		
		}
		else{
			OperationsComboBoxModel _occuredWithIn=new OperationsComboBoxModel("OccuredWithIn",OperationType.OCCUREDWITHIN);
			_operations.add(_occuredWithIn);	
			
		}
		return _operations;
	}
	
	SelectionListener<ButtonEvent> getAddButtonEvent(){
		
		/*
		SelectionListener<ButtonEvent> _addBtnEvent=new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				
				
				if(entityComboBox.validate() && operationComboBox.validate()&&entityValueTextBox.validate()){
				
				ComboBox<OperationsComboBoxModel> operationCombo= interfaceManager.getOperationsComboBox();
				TextField<String> entityValue=interfaceManager.getFilterEntityValueTextField();
				
				
				ActionPropertyRule selectedEntity = entityComboBox.getValue().getActionPropertyRule();
				OperationsComboBoxModel selectedOperation=operationCombo.getValue();
				String filterValue=entityValue.getValue();
				if(selectedEntity==null)
					return;
//				
//				if(OperationType.getFromString(selectedOperation.getOperationType())==OperationType.OCCUREDWITHIN&&!GWTUtils.isNumber(filterValue))
//				{
//					
//					 MessageBox.alert("Info", "Time should be an integer value and in minutes",null);
//					 return;
//				}

				FilterGridRow  _newRow=new FilterGridRow(selectedEntity); 
		    	
		        
		        grid.stopEditing();  
		        grid.getStore().insert(_newRow, 0);
		        
//		        interfaceManager.filtersUpdated();
		        grid.startEditing(grid.getStore().indexOf(_newRow), 0); 
		     
				
		        entityComboBox.clearSelections();
		        operationCombo.clearSelections();
		        entityValue.clear();

				}
				
				
			}
			};
		
			*/
		return  null;
	}
	
	
}
