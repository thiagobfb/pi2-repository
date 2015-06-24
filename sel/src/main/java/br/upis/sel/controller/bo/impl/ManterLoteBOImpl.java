package br.upis.sel.controller.bo.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.upis.sel.controller.bo.ManterLoteBO;
import br.upis.sel.enums.LoteStatus;
import br.upis.sel.enums.PerfilDescricao;
import br.upis.sel.model.dao.LeilaoDAO;
import br.upis.sel.model.dao.LoteDAO;
import br.upis.sel.model.dao.ParticipanteDAO;
import br.upis.sel.model.dao.PerfilDAO;
import br.upis.sel.model.entity.Leilao;
import br.upis.sel.model.entity.Lote;
import br.upis.sel.model.entity.Participante;
import br.upis.sel.model.entity.Perfil;

import com.google.common.collect.Lists;

@Service
@Transactional
public class ManterLoteBOImpl extends AbstractBOImpl implements ManterLoteBO {
	
	private static final long serialVersionUID = -4506695081325127089L;
	
	@Autowired
	private LoteDAO loteDAO;
	
	@Autowired
	private LeilaoDAO leilaoDAO;
	
	@Autowired
	private ParticipanteDAO participanteDAO;
	
	@Autowired
	private PerfilDAO perfilDAO;

	@Override
	public List<Lote> buscarTodosLotes() {
		return Lists.newArrayList(this.loteDAO.findAll());
	}

	@Override
	public Lote buscarPorId(Long id) {
		return this.loteDAO.findOne(id);
	}

	@Override
	public void incluirLote(Lote l) {
		if (l.getComitente() == null) {
			throw new RuntimeException("O lote deve ter um comitente");
		} else if (l.getItens() == null || l.getItens().isEmpty()) {
			throw new RuntimeException("O lote deve possuir pelo menos 1 item");
		} else if (l.getValorTotal() == 0) {
			throw new RuntimeException("O lote está com valor total igual a zero");
		} else {
			if (l.getStatus() == null) {
				l.setStatus(LoteStatus.NAO_LEILOADO);
			} 
			
			this.loteDAO.save(l);
		}
	}

	private boolean validaLoteLeiloado(Lote l) {
		if (l.getArrematante() != null) {
			Participante p = this.participanteDAO.findOne(l.getArrematante().getIdParticipante());
			List<Perfil> perfis = p.getPerfis();
			Perfil arrematante = this.perfilDAO.findByDescricao(PerfilDescricao.ROLE_ARREMATANTE);
			if (!perfis.contains(arrematante)) {
				throw new RuntimeException("O arrematante não possui o perfil desejado!");
			}
		}
		
		return l.getArrematante() != null && l.getValorLanceFinal() > l.getValorTotal();
	}

	@Override
	public void alterarLote(Lote l, LoteStatus ls) {
		Lote lote = this.loteDAO.findOne(l.getIdLote());
		lote.setIdLote(l.getIdLote());
		lote.setStatus(ls);
		lote.setComitente(l.getComitente());
		lote.setItens(l.getItens());
		lote.setValorTotal(l.getValorTotal());
		
		if (ls.equals(LoteStatus.LEILOADO) && this.validaLoteLeiloado(l)) {
			lote.setValorLanceFinal(l.getValorLanceFinal());
			lote.setArrematante(l.getArrematante());
		} else {
			throw new RuntimeException("O lote está sem arrematante ou o valor de arremate não é maior que o valor inicial");
		}
		
		this.incluirLote(lote);
	}

	@Override
	public void excluirLote(Lote lote) {
		if (lote.getStatus().equals(LoteStatus.LEILOADO)) {
			throw new RuntimeException("Lotes leiloados não serão excluídos");
		}
		
		this.loteDAO.delete(lote);
	}

	@Override
	public boolean isNaoLeiloado(Lote lote) {
		return lote.getStatus().equals(LoteStatus.NAO_LEILOADO);
	}
	
	@Override
	public List<Lote> buscarLotesPorFiltro(Participante comitente, Participante arrematante, Long numLeilao) {
		Specification<Lote> filtros = gerarFiltroPesquisa(comitente, arrematante, numLeilao, this.leilaoDAO);
		
		return this.loteDAO.findAll(filtros);
	}

	private static Specification<Lote> gerarFiltroPesquisa(final Participante comitente, final Participante arrematante, final Long numLeilao, final LeilaoDAO dao) {
		return new Specification<Lote>() {

			@Override
			public Predicate toPredicate(Root<Lote> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> restrictions = new ArrayList<Predicate>();
				
				if (comitente != null) {
					Predicate pComitente = cb.equal(root.get("comitente"), comitente);
					restrictions.add(pComitente);
				}
				
				if (arrematante != null) {
					Predicate pArrematante = cb.equal(root.get("arrematante"), arrematante);
					restrictions.add(pArrematante);
				}
				
				if (numLeilao != null && numLeilao > 0) {
					Leilao l = dao.findOne(numLeilao);
					
					if (l == null) {
						throw new RuntimeException("Não existe leilão com este código");
					} else {					
						Predicate pLeilao = cb.equal(root.get("leilao"), l);
						restrictions.add(pLeilao);
					}
				}
				
				return cb.and(restrictions.toArray(new Predicate[restrictions.size()]));
			}
		};
	}

	@Override
	public List<Lote> buscarLotesSemLeilao() {
		return this.loteDAO.findByLeilaoIsNull();
	}
}
