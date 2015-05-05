package br.upis.sel.model.dao;

import org.springframework.data.repository.CrudRepository;

import br.upis.sel.model.entity.Participante;


public interface ParticipanteDAO extends CrudRepository<Participante, Long> {
	
	Participante findByUsernameAndPassword(String nome, String password);

	Participante findByUsername(String nome);	
}
