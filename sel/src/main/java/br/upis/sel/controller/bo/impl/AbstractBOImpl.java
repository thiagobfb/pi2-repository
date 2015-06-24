package br.upis.sel.controller.bo.impl;

import java.io.Serializable;

public abstract class AbstractBOImpl implements Serializable {

	private static final long serialVersionUID = 7720745247353995553L;

	protected static String formatarCampoPesquisa(String campo) {
		return "%" + campo + "%";
	}
}
