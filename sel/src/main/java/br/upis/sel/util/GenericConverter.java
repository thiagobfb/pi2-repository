package br.upis.sel.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

 

@FacesConverter(value = "genericConverter")
public class GenericConverter implements Converter {
	public static final String PREFIXO = "GENERIC_CONVERTR_";
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Object ret =null;
		if (context.getViewRoot() != null) {
			ret = context.getViewRoot().getViewMap().get(value);
		}
		return ret;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,	Object value) {
		String chave = PREFIXO+Integer.toString(value.hashCode() );
		if (context.getViewRoot() != null) {
			context.getViewRoot().getViewMap().put(chave, value);
		} else {
			return null;
		}
		return chave; 
	}

}
