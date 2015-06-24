package br.upis.sel.controller.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.upis.sel.controller.bo.ManterItemBO;
import br.upis.sel.enums.ItemStatus;
import br.upis.sel.model.dao.ItemDAO;
import br.upis.sel.model.dao.LoteDAO;
import br.upis.sel.model.entity.Item;
import br.upis.sel.model.entity.Lote;

import com.google.common.collect.Lists;

@Service
@Transactional
public class ManterItemBOImpl extends AbstractBOImpl implements ManterItemBO {

	private static final long serialVersionUID = 6015085285833508393L;
	
	@Autowired
	private ItemDAO itemDAO;
	
	@Autowired
	private LoteDAO loteDAO;
	
	@Override
	public List<Item> buscarTodosItens() {
		return Lists.newArrayList(this.itemDAO.findAll());
	}
	
	@Override
	public void incluirItem(Item i) {
		if (i.getDescricao() == null || i.getValor() == null || i.getDescricao().isEmpty() || i.getValor() == 0) {
			throw new RuntimeException("O item não possui dados adicionados");
		}
		
		i.setStatus(ItemStatus.CADASTRADO);
		this.itemDAO.save(i);
	}
	
	@Override
	public void alterarItem(Item i) {
		Item item = this.itemDAO.findOne(i.getIdItem());
		item.setDescricao(i.getDescricao());
		item.setValor(i.getValor());
		
		if (i.getLote() != null) {
			item.setLote(i.getLote());
		}
		
		this.incluirItem(item);
	}
	
	@Override
	public void excluirItem(Item i) {
		Item item = this.itemDAO.findOne(i.getIdItem());
		this.itemDAO.delete(item);
	}
	
	@Override
	public List<Item> buscarPorDescricao(String descricao) {
		return this.itemDAO.findByDescricaoContaining(descricao);
	}

	@Override
	public Item buscarPorId(String itemId) {
		Long id = Long.parseLong(itemId);
		return this.itemDAO.findOne(id);
	}

	@Override
	public Item buscarPorId(Long itemId) {
		return this.itemDAO.findOne(itemId);
	}

	@Override
	public List<Item> buscarPorLoteNulo() {
		List<Item> result = this.itemDAO.findByLoteIsNull(); 
		return result;
	}
	
	@Override
	public List<Item> buscarItensPorFiltro(String descricao, Long codLote) {
		Lote lote = null;
		boolean descricaoNaoNuloENaoVazio = descricao != null && !descricao.isEmpty();
		boolean codLoteNaoNulo = codLote != null && codLote > 0;
		
		if (descricaoNaoNuloENaoVazio && codLoteNaoNulo) {
			lote = this.buscarLote(codLote);
			return this.itemDAO.findByDescricaoContainingAndLote(descricao, lote);
		} else if (descricaoNaoNuloENaoVazio && !codLoteNaoNulo) {
			return this.itemDAO.findByDescricaoContaining(descricao);
		} else if (!descricaoNaoNuloENaoVazio && codLoteNaoNulo) {
			lote = this.buscarLote(codLote);
			return this.itemDAO.findAllByLote(lote);
		} else {
			return null;			
		}
	}
	
	private Lote buscarLote(Long cod) {
		Lote l = this.loteDAO.findOne(cod);
		
		if (l == null) {
			throw new RuntimeException("Não existe lote com este código");
		}
		
		return l;
	}
}
