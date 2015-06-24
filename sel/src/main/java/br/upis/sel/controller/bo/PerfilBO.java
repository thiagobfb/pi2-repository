package br.upis.sel.controller.bo;

import java.util.List;

import br.upis.sel.enums.PerfilDescricao;
import br.upis.sel.model.entity.Perfil;

public interface PerfilBO {
	Perfil obterPorDescricao(PerfilDescricao descricao);
	
	List<Perfil> buscarTodosPerfis();
}
