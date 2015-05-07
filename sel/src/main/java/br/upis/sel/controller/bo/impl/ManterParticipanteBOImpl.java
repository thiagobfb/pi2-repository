package br.upis.sel.controller.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.upis.sel.controller.ManterParticipanteBO;
import br.upis.sel.model.dao.ParticipanteDAO;

@Service
@Transactional
public class ManterParticipanteBOImpl implements ManterParticipanteBO {
	
	@Autowired
	private ParticipanteDAO participanteDAO;

}
