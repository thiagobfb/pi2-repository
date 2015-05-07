package br.upis.sel.controller.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.upis.sel.controller.bo.RealizarLoginBO;
import br.upis.sel.model.dao.ParticipanteDAO;
import br.upis.sel.model.entity.Participante;

@Service
@Transactional
public class RealizarLoginBOImpl implements RealizarLoginBO {
	
	@Autowired
	private ParticipanteDAO participanteDAO;

	@Override
	public Participante login(String cpf, String password)
			throws IllegalArgumentException {
		if (this.isEmptyOrNull(cpf) || this.isEmptyOrNull(password)) {
			throw new IllegalArgumentException("CPF ou senha vazios!");
		}
		
		Participante p = this.participanteDAO.findByUsernameAndPassword(cpf, password);
		
		if (p == null) {
			throw new IllegalArgumentException("CPF ou senha inv�lidos!");
		}
		
		return p;
	}

	private boolean isEmptyOrNull(String s) {
		return s.equals("") || s == null;
	}

	@Override
	public UserDetails loadUserByUsername(String cpf)
			throws UsernameNotFoundException {
		try {
			Participante participante = this.participanteDAO.findByUsername(cpf);
			if (participante != null) {
				return participante;			
			} else {
				throw new UsernameNotFoundException("Usuário Não Encontrado!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
