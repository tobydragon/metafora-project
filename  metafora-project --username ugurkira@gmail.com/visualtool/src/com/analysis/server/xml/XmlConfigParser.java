package com.analysis.server.xml;

import java.util.List;

import com.analysis.server.utils.ServerFormatStrings;
import com.analysis.shared.interactionmodels.Configuration;
import com.analysis.shared.interactionmodels.IndicatorFilter;
import com.analysis.shared.interactionmodels.IndicatorFilterItem;

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
		
		
		Configuration _conf=new Configuration();
		
		for(XmlFragmentInterface confFragment: configFragment.getChildren(ServerFormatStrings.CONFIGURATION)){
			
		if(confFragment.getAttributeValue("active").equalsIgnoreCase("1") || confFragment.getAttributeValue("active").equalsIgnoreCase("true")){	
			
	   
		_conf.setName(confFragment.getAttributeValue(ServerFormatStrings.NAME));
		_conf.setDataSource(confFragment.getChildValue(ServerFormatStrings.DATA_SOURCE_TYPE));
		for(XmlFragmentInterface filterFragment: confFragment.getChildren(ServerFormatStrings.FILTER)){
			
			IndicatorFilter indicatorFilter=new IndicatorFilter();
			String _filterName=filterFragment.getAttributeValue(ServerFormatStrings.NAME);
			indicatorFilter.setName(_filterName);
			indicatorFilter.setEditable(filterFragment.getAttributeValue(ServerFormatStrings.Active));


			for(XmlFragmentInterface propertyFragment: filterFragment.getChildren(ServerFormatStrings.PROPERTY))
			{			
				IndicatorFilterItem _filterItem=new IndicatorFilterItem();
				_filterItem.setType(propertyFragment.getAttributeValue(ServerFormatStrings.Type));
				_filterItem.setProperty(propertyFragment.getAttributeValue(ServerFormatStrings.NAME));
				_filterItem.setValue(propertyFragment.getAttributeValue(ServerFormatStrings.VALUE));				
				 indicatorFilter.addFilterItem(_filterName, _filterItem);
			}
			
		}

		break;
		}

		}
		
		return _conf;
		
	}
	
	
	
	

}
