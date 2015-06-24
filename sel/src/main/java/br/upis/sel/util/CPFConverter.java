package br.upis.sel.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.caelum.stella.format.CPFFormatter;

@FacesConverter("cpfConverter")
public class CPFConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return "";
		}
		
		
		return formataCPF(value.toString());
	}
	
	private static String formataCPF(String cpf) {
		if (cpf == null || "".equals(cpf)) {
			return "";
		}
		return new CPFFormatter().format(cpf);
	}

}
