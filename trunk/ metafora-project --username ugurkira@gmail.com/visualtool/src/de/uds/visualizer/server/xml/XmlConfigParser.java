package de.uds.visualizer.server.xml;

import java.util.List;

import de.uds.visualizer.server.utils.ServerFormatStrings;
import de.uds.visualizer.shared.interactionmodels.Configuration;
import de.uds.visualizer.shared.interactionmodels.IndicatorFilter;
import de.uds.visualizer.shared.interactionmodels.IndicatorFilterItem;

public class XmlConfigParser {
	
	XmlFragmentInterface configFragment;
	
 
	public XmlConfigParser(XmlFragmentInterface newStart){
		configFragment = newStart;
	}
	
	public XmlConfigParser(String filename){
		configFragment = XmlFragment.getFragmentFromFile(filename);
	}
	
	public XmlConfigParser getfragmentById(String fragmentType, String idAttrName,  String id){
		List<XmlFragmentInterface> frags = configFragment.getChildren(fragmentType);
		for (XmlFragmentInterface frag : frags){
			if( frag.getAttributeValue(idAttrName).equalsIgnoreCase(id)){
				return new XmlConfigParser(frag);
			}
		}
		return null;
	}
	
	public String getConfigValue(String name){
		try {
			return configFragment.cloneChild(name).getRootElement().getText();
		}
		catch (Exception e){
			return null;
		}
	}
	
	
	public Configuration toActiveConfiguration(){
		
		
		Configuration _conf=null;
		
		for(XmlFragmentInterface confFragment: configFragment.getChildren(ServerFormatStrings.CONFIGURATION)){
			
		if(confFragment.getAttributeValue("active").equalsIgnoreCase("1") || confFragment.getAttributeValue("active").equalsIgnoreCase("true")){	
			
	    _conf=new Configuration();
		_conf.setName(confFragment.getAttributeValue(ServerFormatStrings.NAME));
		_conf.setDataSource(confFragment.getChildValue(ServerFormatStrings.DATA_SOURCE_TYPE));
		 XmlFragmentInterface  filtersFragment=XmlFragment.getFragmentFromString(confFragment.toString()).accessChild("filters");
		
		for(XmlFragmentInterface filterFragment: filtersFragment.getChildren(ServerFormatStrings.FILTER)){
			
			IndicatorFilter indicatorFilter=new IndicatorFilter();
			String _filterName=filterFragment.getAttributeValue(ServerFormatStrings.NAME);
			indicatorFilter.setName(_filterName);
			indicatorFilter.setEditable(filterFragment.getAttributeValue(ServerFormatStrings.EDITABLE));

			 System.out.println("Filter:"+_filterName);
			for(XmlFragmentInterface propertyFragment : filterFragment.getChildren(ServerFormatStrings.PROPERTY))
			{			
				IndicatorFilterItem _filterItem=new IndicatorFilterItem();
				_filterItem.setType(propertyFragment.getAttributeValue(ServerFormatStrings.Type));
				_filterItem.setProperty(propertyFragment.getAttributeValue(ServerFormatStrings.NAME));
				_filterItem.setValue(propertyFragment.getAttributeValue(ServerFormatStrings.VALUE));				
				 indicatorFilter.addFilterItem(_filterItem.getProperty(), _filterItem);

			}
			
			_conf.addFilter(indicatorFilter);	
		}

		break;
		}

		}
		
		return _conf;
		
	}
	
	
	
	

}
