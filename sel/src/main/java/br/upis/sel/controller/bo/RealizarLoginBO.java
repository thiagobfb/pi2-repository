package br.upis.sel.controller.bo;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.upis.sel.model.entity.Participante;

public interface RealizarLoginBO extends UserDetailsService {
	Participante login(String cpf, String password) throws IllegalArgumentException;
}
