package br.upis.sel.controller.facade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.upis.sel.controller.bo.ManterItemBO;
import br.upis.sel.controller.bo.ManterLeilaoBO;
import br.upis.sel.controller.bo.ManterLoteBO;
import br.upis.sel.controller.bo.ManterParticipanteBO;
import br.upis.sel.controller.bo.PerfilBO;
import br.upis.sel.controller.bo.RealizarLoginBO;
import br.upis.sel.enums.LeilaoStatus;
import br.upis.sel.enums.LoteStatus;
import br.upis.sel.enums.ParticipanteStatus;
import br.upis.sel.enums.PerfilDescricao;
import br.upis.sel.model.entity.Item;
import br.upis.sel.model.entity.Leilao;
import br.upis.sel.model.entity.Lote;
import br.upis.sel.model.entity.Participante;
import br.upis.sel.model.entity.Perfil;

@Service
@Transactional
public class SELFacade implements Serializable {
	
	private static final long serialVersionUID = -6701772516498933248L;

	@Autowired
	private RealizarLoginBO loginBO;
	
	@Autowired
	private PerfilBO perfilBO;
	
	@Autowired
	private ManterParticipanteBO manterParticipanteBO;
	
	@Autowired
	private ManterItemBO manterItemBO;
	
	@Autowired
	private ManterLoteBO manterLoteBO;
	
	@Autowired
	private ManterLeilaoBO manterLeilaoBO;
	
	public Participante realizarLogin(String cpf, String password) {
		return this.loginBO.login(cpf, password);
	}
	
	public Perfil recuperarPerfilPorDescricao(PerfilDescricao descricao) {
		return this.perfilBO.obterPorDescricao(descricao);
	}
	
	public List<Participante> recuperarParticipantesPorPerfil(Perfil... perfil) {
		return this.manterParticipanteBO.buscarPorPerfil(perfil);
	}
	
	public List<Participante> recuperarTodosParticipantes() {
		return this.manterParticipanteBO.buscarTodosParticipantes();
	}
	
	public List<Perfil> recuperarTodosPerfis() {
		return this.perfilBO.buscarTodosPerfis();
	}
	
	public Participante recuperarParticipantePorId(Long id) {
		return this.manterParticipanteBO.buscarPorId(id);
	}
	
	public void incluirParticipante(Participante p) {
		this.manterParticipanteBO.incluirParticipante(p);
	}
	
	public void alterarParticipante(Participante p, ParticipanteStatus ps) {
		this.manterParticipanteBO.alterarParticipante(p, ps);
	}
	
	public List<Participante> recuperarParticipantesPorFiltro(String nome, String cpf) {
		return this.manterParticipanteBO.buscarParticipantesPorFiltro(nome, cpf);
	}
	
	public List<Participante> recuperarParticipantesPorNome(String query) {
		return this.manterParticipanteBO.buscarParticipantesPorNome(query);
	}

	public Item recuperarItemPorId(String itemId) {
		return this.manterItemBO.buscarPorId(itemId);
	}

	public Item recuperarItemPorId(Long itemId) {
		return this.manterItemBO.buscarPorId(itemId);
	}

	public void incluirItem(Item item) {
		this.manterItemBO.incluirItem(item);
	}
	
	public void alterarItem(Item item) {
		this.manterItemBO.alterarItem(item);
	}

	public void excluirItem(Item item) {
		this.manterItemBO.excluirItem(item);
	}
	
	public List<Item> recuperarTodosItens() {
		return this.manterItemBO.buscarTodosItens();
	}
	
	public List<Item> recuperarItensPorFiltro(String descricao, Long codigoLote) {
		return this.manterItemBO.buscarItensPorFiltro(descricao, codigoLote);
	}
	
	public List<Item> recuperarItensSemLote() {
		return this.manterItemBO.buscarPorLoteNulo();
	}
	
	public List<Lote> recuperarTodosLotes() {
		return this.manterLoteBO.buscarTodosLotes();
	}
	
	public Lote recuperarLotePorId(Long id) {
		return this.manterLoteBO.buscarPorId(id);
	}
	
	public void incluirLote(Lote l) {
		this.manterLoteBO.incluirLote(l);
	}
	
	public void alterarLote(Lote l, LoteStatus ls) {
		this.manterLoteBO.alterarLote(l, ls);
	}
	
	public void excluirLote(Lote lote) {
		this.manterLoteBO.excluirLote(lote);
	}
	
	public boolean isLoteNaoLeiloado(Lote lote) {
		return this.manterLoteBO.isNaoLeiloado(lote);
	}
	
	public List<Lote> recuperarLotesPorFiltro(Participante comitente, Participante arremantante, Long numLeilao) {
		return this.manterLoteBO.buscarLotesPorFiltro(comitente, arremantante, numLeilao);
	}
	
	public List<Lote> recuperarLotesSemLeilao() {
		return this.manterLoteBO.buscarLotesSemLeilao();
	}

	public List<Leilao> recuperarTodosLeiloes() {
		return this.manterLeilaoBO.buscarTodosLeiloes();
	}
		
	public Leilao recuperarLeilaoPorId(Long leilaoId) {
		return this.manterLeilaoBO.buscarPorId(leilaoId);
	}

	public List<Leilao> recuperarLeiloesPorFiltro(String nome, Date data, String local) {
		return this.manterLeilaoBO.buscarLeiloesPorFiltro(nome, data, local);
	}
	
	public List<Leilao> recuperarLeiloesPorFiltro(Date data, String local) {
		return this.manterLeilaoBO.buscarLeiloesPorFiltro(data, local);
	}
	
	public void incluirLeilao(Leilao l) {
		this.manterLeilaoBO.incluirLeilao(l);
	}
	
	public void alterarLeilao(Leilao l, LeilaoStatus ls) {
		this.manterLeilaoBO.alterarLeilao(l, ls);
	}
	
	public List<Leilao> buscarLeiloesAgendadosParaHoje(Date inico, Date agora, LeilaoStatus ls) {
		return this.manterLeilaoBO.buscarLeiloesAgendadosPorData(inico, agora, ls);
	}
	
	public void finalizarLeilao(Leilao l, LeilaoStatus ls) {
		this.manterLeilaoBO.finalizarLeilao(l, ls);
	}
}
