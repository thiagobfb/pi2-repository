package br.upis.sel.controller.bo;

import java.util.List;

import br.upis.sel.model.entity.Item;

public interface ManterItemBO {

	List<Item> buscarTodosItens();
	
	void incluirItem(Item i);
	
	void alterarItem(Item i);

	void excluirItem(Item i);
	
	List<Item> buscarPorDescricao(String descricao);

	Item buscarPorId(String itemId);

	Item buscarPorId(Long itemId);
	
	List<Item> buscarPorLoteNulo();

	List<Item> buscarItensPorFiltro(String descricao, Long codLote);
}
