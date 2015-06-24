package br.upis.sel.util;

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("moedaConverter")
public class MoedaConverter implements Converter {

	protected final String currencySymbol = "R$ ";
	 
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return (Object) value.replaceAll("[^,0123456789]", "").replaceAll(",", ".");
    }
 
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        NumberFormat nf = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        nf.setMinimumFractionDigits(2);
        return this.currencySymbol + nf.format(value);
    }
}
