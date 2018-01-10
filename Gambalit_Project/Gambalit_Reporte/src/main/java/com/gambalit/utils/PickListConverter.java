package com.gambalit.utils;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.gmb.modelo.seguridad.GmbRoles;

@FacesConverter(value = "pickListConverter")
public class PickListConverter  implements Converter{

	 
	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String s) {
		return getObjectFromUIPickListComponent(component,s);
	}

	@Override 
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String string;
		
		if(object == null){
			string="";
		}else{
			try{
				string = String.valueOf(((GmbRoles)object).getIdRol());
			}catch(ClassCastException cce){
				throw new ConverterException();
			}
		}
		return string;
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	private GmbRoles getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<GmbRoles> dualList;
		try{
			dualList = (DualListModel<GmbRoles>) ((PickList)component).getValue();
			GmbRoles roles = getObjectFromList(dualList.getSource(),Integer.valueOf(value));
			if(roles==null){
				roles = getObjectFromList(dualList.getTarget(),Integer.valueOf(value));
			}
			
			return roles;
		}catch(ClassCastException cce){
			throw new ConverterException();
		}catch(NumberFormatException nfe){
			throw new ConverterException();
		}
	}
	private GmbRoles getObjectFromList(final List<?> list, final Integer identifier) {
		for(final Object object:list){
			final GmbRoles roles = (GmbRoles) object;
			if(roles.getIdRol().toString().equals(identifier.toString())){
				return roles;
			}
		}
		return null;
	}
}
