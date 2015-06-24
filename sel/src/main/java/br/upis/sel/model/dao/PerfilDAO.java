package br.upis.sel.model.dao;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import br.upis.sel.enums.PerfilDescricao;
import br.upis.sel.model.entity.Perfil;

public interface PerfilDAO extends CrudRepository<Perfil, Long>, Serializable {

	Perfil findByDescricao(PerfilDescricao descricao);
}
