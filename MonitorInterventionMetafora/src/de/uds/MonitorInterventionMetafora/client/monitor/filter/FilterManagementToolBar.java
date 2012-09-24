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
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

import de.uds.MonitorInterventionMetafora.client.logger.ComponentType;
import de.uds.MonitorInterventionMetafora.client.monitor.ClientMonitorController;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.OperationsComboBoxModel;
import de.uds.MonitorInterventionMetafora.client.monitor.datamodel.PropertyComboBoxItemModel;
import de.uds.MonitorInterventionMetafora.client.monitor.grouping.ActionPropertyComboBox;
import de.uds.MonitorInterventionMetafora.client.resources.Resources;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationType;
import de.uds.MonitorInterventionMetafora.shared.datamodels.attributes.OperationsCategory;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRule;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModel;
import de.uds.MonitorInterventionMetafora.shared.monitor.filter.ActionPropertyRuleSelectorModelType;
import de.uds.MonitorInterventionMetafora.shared.utils.GWTUtils;



public class FilterManagementToolBar extends ToolBar{

	private  ComboBox<PropertyComboBoxItemModel> filterPropertyComboBox;
	private ComboBox<OperationsComboBoxModel> operationComboBox;
	private TextField<String> entityValueTextBox; 
	private SimpleComboBox<String> filterGroupCombo;
	private  Button addButton;
	EditorGrid<FilterGridRow> grid;
	Button applyBtn;
	
	
 
	public FilterManagementToolBar(EditorGrid<FilterGridRow> grid, ActionPropertyRuleSelectorModel filterRuleSelectorModel,final SimpleComboBox<String> filterGroupCombo,Button applyBtn){
	
		this.filterGroupCombo=filterGroupCombo;
		this.applyBtn=applyBtn;

		this.grid = grid;
		 FormLayout layout = new FormLayout();
		 
		filterPropertyComboBox = new ActionPropertyComboBox(filterRuleSelectorModel);
		filterPropertyComboBox.setForceSelection(true);
		filterPropertyComboBox.setPosition(0, -2);

		
		 
		operationComboBox=new ComboBox<OperationsComboBoxModel>();
		operationComboBox.setFieldLabel("Operation");
		operationComboBox.setWidth(130);
		
		
		operationComboBox.setDisplayField("displaytext");
		operationComboBox.setValueField("operationtype");
		operationComboBox.setStore(getOperations(OperationsCategory.STANDARD));
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

	        	OperationsCategory operationsCategory = OperationsCategory.getCategoryFromPropertyName(se.getSelectedItem().getActionPropertyRule().getPropertyName());
	        	
	        	operationComboBox.getStore().removeAll();
	        	operationComboBox.setStore(getOperations(operationsCategory));
	        	operationComboBox.clearSelections();
	        	
	        }
	    };
		filterPropertyComboBox.addSelectionChangedListener(comboListenerItem);
		
		addButton=new Button("Add Filter",getAddButtonEvent());
		addButton.setIcon(Resources.ICONS.add());
		//addButton.setId("ww");
		addButton.setPosition(0,-9);
		addButton.setLayoutData(new FitLayout());
		
		
		this.add(filterPropertyComboBox);
		this.add(operationComboBox);
		this.add(entityValueTextBox);
		this.add(addButton);
		this.setBorders(true);
		this.setSpacing(20);
		this.setHeight(53);
		//this.setWidth(600);
				
		this.layout();
	}
	
	//TODO: Efficiency - these models need only be created once, and saved in attribute, then accessed when needed
	ListStore<OperationsComboBoxModel>	getOperations(OperationsCategory category){
		return buildOperationsComboBoxModel(category);
	}
	
	public ListStore<OperationsComboBoxModel> buildOperationsComboBoxModel(OperationsCategory category){
		ListStore<OperationsComboBoxModel> opsComboBoxModel=new ListStore<OperationsComboBoxModel>();
		for (OperationType operationType : OperationType.getOperations(category)){
			opsComboBoxModel.add(new OperationsComboBoxModel(operationType));
		}
		return opsComboBoxModel;
	}
	
	SelectionListener<ButtonEvent> getAddButtonEvent(){
		SelectionListener<ButtonEvent> _addBtnEvent = new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(filterPropertyComboBox.validate() && operationComboBox.validate()&&entityValueTextBox.validate()){
				
					ActionPropertyRule newFilterRule=new ActionPropertyRule();
					newFilterRule.setDisplayText(filterPropertyComboBox.getValue().getActionPropertyRule().getDisplayText());
					newFilterRule.setPropertyName(filterPropertyComboBox.getValue().getActionPropertyRule().getPropertyName());
					
					newFilterRule.setType(filterPropertyComboBox.getValue().getActionPropertyRule().getType());
					newFilterRule.setOperationType(operationComboBox.getValue().getOperationType());
					newFilterRule.setValue(entityValueTextBox.getValue());
					newFilterRule.setOrigin(ComponentType.FILTER_MANAGEMENT_TOOLBAR);
	
					if(newFilterRule.getOperationType().isTimeOperation() && !GWTUtils.isLong(newFilterRule.getValue())) {
						 MessageBox.alert("Info", "Time should be an integer value and in minutes",null);
						 return;
					}
	
					FilterGridRow  _newRow=new FilterGridRow(newFilterRule); 
			    	
			        grid.stopEditing();  
			        grid.getStore().insert(_newRow, 0);
			        grid.startEditing(grid.getStore().indexOf(_newRow), 0); 
			     
			        filterPropertyComboBox.clearSelections();
			        operationComboBox.clearSelections();
			        entityValueTextBox.clear();
			        filterGroupCombo.clearSelections();
			        filterGroupCombo.setEditable(true);
			        applyBtn.setEnabled(true);
				}
				
			}
		};	
		return  _addBtnEvent;
	}
	
}
