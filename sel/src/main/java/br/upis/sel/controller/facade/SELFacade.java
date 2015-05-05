package br.upis.sel.controller.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.upis.sel.controller.bo.RealizarLoginBO;
import br.upis.sel.model.entity.Participante;

@Service
@Transactional
public class SELFacade {
	
	@Autowired
	private RealizarLoginBO loginBO;
	
	public Participante realizarLogin(String cpf, String password) {
		return this.loginBO.login(cpf, password);
	}
}
