package br.upis.sel.controller.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import br.upis.sel.controller.bo.PerfilBO;
import br.upis.sel.enums.PerfilDescricao;
import br.upis.sel.model.dao.PerfilDAO;
import br.upis.sel.model.entity.Perfil;

@Service
@Transactional
public class PerfilBOImpl extends AbstractBOImpl implements PerfilBO {

	private static final long serialVersionUID = -3780839825428803489L;
	
	@Autowired
	private PerfilDAO perfilDAO;
	
	@Override
	public Perfil obterPorDescricao(PerfilDescricao descricao) {
		return this.perfilDAO.findByDescricao(descricao);
	}

	@Override
	public List<Perfil> buscarTodosPerfis() {
		return Lists.newArrayList(this.perfilDAO.findAll());
	}
}
