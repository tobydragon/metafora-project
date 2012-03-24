package de.uds.MonitorInterventionMetafora.client.view.widgets;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.ComponentManager;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.DOM;


import de.uds.MonitorInterventionMetafora.client.communication.servercommunication.ActionMaintenance;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntitiesComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.EntityViewModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.IndicatorFilterItemGridRowModel;
import de.uds.MonitorInterventionMetafora.client.datamodels.OperationsComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;


public class ExtendedFilterManagementPanel extends HorizontalPanel{

	private  ComboBox<EntitiesComboBoxModel> entityComboBox;
	private ComboBox<OperationsComboBoxModel> operationComboBox;
	private TextField<String> entityValueTextBox; 
	private  Button addButton;
	private ActionMaintenance maintenance;
	private EntityViewModel model;
	
	
	public ExtendedFilterManagementPanel(ActionMaintenance _maintenance){
		maintenance=_maintenance;
		model=new EntityViewModel(maintenance);
		
		 FormLayout layout = new FormLayout();
		entityComboBox = new ComboBox<EntitiesComboBoxModel>();
	//	entityComboBox.setFieldLabel("Entity");
		entityComboBox.setWidth(130);
		entityComboBox.setDisplayField("displaytext");
		entityComboBox.setValueField("entityname");
		entityComboBox.setEditable(false);
		entityComboBox.setId("_entityComboBox");
		
		
		
		entityComboBox.setStore(model.getComboBoxEntities());
		entityComboBox.setForceSelection(true);

		entityComboBox.setPosition(0, -2);
		entityComboBox.setTriggerAction(TriggerAction.ALL);
		
		
		operationComboBox=new ComboBox<OperationsComboBoxModel>();
		operationComboBox.setFieldLabel("Operation");
		operationComboBox.setWidth(130);
		
		
		operationComboBox.setDisplayField("displaytext");
		operationComboBox.setValueField("operationtype");
		operationComboBox.setStore(getOperations());
		//operationComboBox.setLayoutData(layout);
		operationComboBox.setPosition(0, -2);
		operationComboBox.setEditable(false);
		operationComboBox.setForceSelection(true);
		operationComboBox.setId("_operationComboBox");
		
		operationComboBox.setTriggerAction(TriggerAction.ALL);
		
		entityValueTextBox= new TextField<String>();
		entityValueTextBox.setFieldLabel("Value");
		entityValueTextBox.setWidth(150);
		//entityValueTextBox.set
		entityValueTextBox.setEmptyText("Enter entity value");
		entityValueTextBox.setAllowBlank(false);
		entityValueTextBox.setLayoutData(layout);
		entityValueTextBox.setPosition(0, -2);
		entityValueTextBox.setId("entityValueText");
		
		
		addButton=new Button("Add Filter",getAddButtonEvent());
		
		
		addButton.setIcon(Resources.ICONS.add());
		
		addButton.setId("ww");
		addButton.setPosition(0,-9);
		addButton.setLayoutData(new FitLayout());
		
		
	    // this.add(new Label("Entity"));
	    
	     //this.setLayout(layout);
		this.add(entityComboBox);
		 //this.add(new Label("Operation"));
		this.add(operationComboBox);
		this.add(entityValueTextBox);
		this.add(addButton);
		this.setBorders(true);
		this.setSpacing(20);
		//this.setPosition(1, 3);
		this.setHeight(53);
		//this.setWidth(600);
	
	//	this.setLayout(layout);
		
		
		
		
		this.layout();
		
	     


	}
	
	
	ListStore<OperationsComboBoxModel>	getOperations(){
		
		ListStore<OperationsComboBoxModel> _operations=new ListStore<OperationsComboBoxModel>();
		OperationsComboBoxModel _equals=new OperationsComboBoxModel("Equals",OperationType.EQUALS);
		OperationsComboBoxModel _contains=new OperationsComboBoxModel("Contains",OperationType.CONTAINS);
		_operations.add(_equals);
		_operations.add(_contains);
		
		return _operations;
	}
	
	SelectionListener<ButtonEvent> getAddButtonEvent(){
		
		
		SelectionListener<ButtonEvent> _addBtnEvent=new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				
				
				if(entityComboBox.validate() && operationComboBox.validate()&&entityValueTextBox.validate()){
				
				ComboBox<EntitiesComboBoxModel> entitiesCombo= (ComboBox<EntitiesComboBoxModel>) ComponentManager.get().get("_entityComboBox");
				ComboBox<OperationsComboBoxModel> operationCombo= (ComboBox<OperationsComboBoxModel>) ComponentManager.get().get("_operationComboBox");
				TextField<String> entityValue=(TextField<String>) ComponentManager.get().get("entityValueText");
				
				
				 EditorGrid<IndicatorFilterItemGridRowModel> editorGrid = (EditorGrid<IndicatorFilterItemGridRowModel>) ComponentManager.get().get("_filterItemGrid");
				 EditorGrid<IndicatorFilterItemGridRowModel> _grid = editorGrid;
		    	 
				
				
				EntitiesComboBoxModel selectedEntity=entitiesCombo.getValue();
				OperationsComboBoxModel selectedOperation=operationCombo.getValue();
				String filterValue=entityValue.getValue();
				
				
				if(selectedEntity==null)
					return;
				
				
				IndicatorFilterItemGridRowModel  _newRow=new IndicatorFilterItemGridRowModel(selectedEntity.getEntityName().toUpperCase(),filterValue.toUpperCase(),selectedEntity.getItemType().toString(),filterValue.toUpperCase(), selectedOperation.getOperationType().toUpperCase()); 
		    	
		        
		        _grid.stopEditing();  
		        _grid.getStore().insert(_newRow, 0);  
		        _grid.startEditing(_grid.getStore().indexOf(_newRow), 0); 
		     
				
		        entitiesCombo.clear();
		        operationCombo.clear();
		        entityValue.clear();

				}
				
				
			}
			};
		
			
		return  _addBtnEvent;
	}
	
	
}
