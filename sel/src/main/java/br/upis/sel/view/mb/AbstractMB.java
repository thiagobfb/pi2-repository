package br.upis.sel.view.mb;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.upis.sel.controller.facade.SELFacade;

@Component
public abstract class AbstractMB implements Serializable {
	
	private static final long serialVersionUID = 236960636151715993L;
	
	@Autowired
	protected SELFacade facade;
	
	protected void getMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
	}
	
	protected void getError(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
	}
	
	protected void getWarn(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null));
	}
	
	public abstract void recuperarObjeto();

	public abstract void prepararAlteracao();
}
