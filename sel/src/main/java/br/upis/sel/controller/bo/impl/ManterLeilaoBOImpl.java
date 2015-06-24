package br.upis.sel.controller.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.upis.sel.controller.bo.ManterLeilaoBO;
import br.upis.sel.enums.LeilaoStatus;
import br.upis.sel.enums.LoteStatus;
import br.upis.sel.model.dao.LeilaoDAO;
import br.upis.sel.model.dao.LoteDAO;
import br.upis.sel.model.entity.Leilao;
import br.upis.sel.model.entity.Lote;
import br.upis.sel.util.DateUtils;

import com.google.common.collect.Lists;

@Service
@Transactional
public class ManterLeilaoBOImpl extends AbstractBOImpl implements ManterLeilaoBO {
	
	private static final long serialVersionUID = 5595832694527560359L;
	
	@Autowired
	private LeilaoDAO leilaoDAO;
	
	@Autowired
	private LoteDAO loteDAO;

	/* (non-Javadoc)
	 * @see br.upis.sel.controller.ManterParticipanteBO#buscarPorPerfil(br.upis.sel.model.entity.Perfil)
	 */
//	@Override
//	public List<Leilao> buscarLeilao(Leilao... leilao) {
//		List<Leilao> leilaoParam = new ArrayList<Leilao>();
//		
//		for (int i = 0; i < leilao.length; i++) {
//			leilaoParam.add(leilao[i]);
//		}
//		return this.leilaoDAO.findByPerfis(leilaoParam);
//	}

	@Override
	public Leilao buscarPorId(Long leilaoId) {
		return this.leilaoDAO.findOne(leilaoId);
	}

	@Override
	public List<Leilao> buscarTodosLeiloes() {
		return Lists.newArrayList(this.leilaoDAO.findAll());
	}

	@Override
	public void incluirLeilao(Leilao l) {
		if (l.getNome() == null || l.getNome().isEmpty()) {
			throw new RuntimeException("O leilão deve ter um nome");
		} else if (l.getLocal() == null || l.getLocal().isEmpty()) {
			throw new RuntimeException("O leilão deve ter um local");
		} else if (l.getData() == null) {
			throw new RuntimeException("Selecione uma data para o leilão");
		} else {
			if (l.getStatus() == null) {
				l.setStatus(LeilaoStatus.AGENDADO);
			}
			this.leilaoDAO.save(l);
		}
		
	}

	@Override
	public void alterarLeilao(Leilao l, LeilaoStatus ls) {
		Leilao leilao = this.leilaoDAO.findOne(l.getIdLeilao());
		leilao.setIdLeilao(l.getIdLeilao());
		leilao.setData(l.getData());
		leilao.setLeiloeiros(l.getLeiloeiros());
		leilao.setLocal(l.getLocal());
		leilao.setLotes(l.getLotes());
		leilao.setNome(l.getNome());
		leilao.setStatus(ls);
		
		this.incluirLeilao(leilao);
	}
	
	@Override
	public List<Leilao> buscarLeiloesPorFiltro(String nome, Date data, String local) {
		Specification<Leilao> filtros = gerarFiltroPesquisaLeilao(nome, data, local);
		
		return this.leilaoDAO.findAll(filtros);
	}
	
	private static Specification<Leilao> gerarFiltroPesquisaLeilao(final String nome, final Date data, final String local) {
		return new Specification<Leilao>() {
			
			@Override
			public Predicate toPredicate(Root<Leilao> root, CriteriaQuery<?> query,	CriteriaBuilder cb) {
				List<Predicate> restrictions = new ArrayList<Predicate>();
				
				if (nome != null) {
					Predicate pNome = cb.equal(root.get("nome"), nome);
					restrictions.add(pNome);
				}
				
				if (data != null) {
					Date lowData = DateUtils.lowDateTime(data);
					Date highData = DateUtils.highDateTime(data);
					Predicate pData = cb.between(root.<Date>get("data"), lowData, highData);
					restrictions.add(pData);
				}
				
				if (local != null) {
					String likePattern = this.getLikePattern(local);
					Predicate pLeilao = cb.like(cb.lower(root.<String>get("local")), likePattern);
					restrictions.add(pLeilao);
				}
				
				return cb.and(restrictions.toArray(new Predicate[restrictions.size()]));
			}
			
			private String getLikePattern(final String searchTerm) {
                StringBuilder pattern = new StringBuilder();
                pattern.append(searchTerm.toLowerCase());
                pattern.append("%");
                return pattern.toString();
            }
		};	
	}

	@Override
	public List<Leilao> buscarLeiloesAgendadosPorData(Date inicio, Date agora, LeilaoStatus ls) {
		return this.leilaoDAO.findByDataBetweenAndStatus(inicio, agora, ls);
	}

	@Override
	public void finalizarLeilao(Leilao l, LeilaoStatus ls) {
		List<Lote> lotes = this.loteDAO.findByLeilao(l);
		if (!this.validarLotes(lotes)) {
			throw new RuntimeException("Um ou mais lotes não foram leiloados ou não possuem arrematantes");
		}
		
		this.alterarLeilao(l, ls);
	}

	private boolean validarLotes(List<Lote> lotes) {
		boolean validado = true;
		
		for (Lote lote : lotes) {
			if (lote.getArrematante() != null && lote.getStatus().equals(LoteStatus.LEILOADO)) {
				continue;
			} else {
				validado = false;
				break;
			}
		}
		
		return validado;
	}
	
	@Override
	public List<Leilao> buscarLeiloesPorFiltro(Date data, String local) {
		boolean dataNaoNula = data != null;
		boolean localNaoNuloENaoVazio = local != null && !local.isEmpty();
		Date lowData =  null;
		Date highData = null;
		if (dataNaoNula) {
			lowData = DateUtils.lowDateTime(data);
			highData = DateUtils.highDateTime(data);
		}
		
		if (localNaoNuloENaoVazio && dataNaoNula) {
			return this.leilaoDAO.findByDataBetweenAndLocalContaining(lowData, highData, local);
		} else if (localNaoNuloENaoVazio && !dataNaoNula) {
			return this.leilaoDAO.findByLocalContaining(local);
		} else {
			return this.leilaoDAO.findByDataBetween(lowData, highData);
		}
	
	}
}
