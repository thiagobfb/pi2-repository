package br.upis.sel.view.mb;

import java.util.List;


//import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.upis.sel.model.entity.Item;

@Component
@Scope("view")
//@ManagedBean
public class ManterItemMB extends AbstractMB {

	private static final long serialVersionUID = 7768261390306053695L;
	
	private String pesquisaDescricao;
	private Long pesquisaNumeroLote;
	
	private Item item;
	
	private List<Item> itens = null;
	
	public ManterItemMB() {
		this.item = new Item();
	}

	public String getPesquisaDescricao() {
		return pesquisaDescricao;
	}

	public void setPesquisaDescricao(String pesquisaDescricao) {
		this.pesquisaDescricao = pesquisaDescricao;
	}

	public Long getPesquisaNumeroLote() {
		return pesquisaNumeroLote;
	}

	public void setPesquisaNumeroLote(Long pesquisaNumeroLote) {
		this.pesquisaNumeroLote = pesquisaNumeroLote;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<Item> getItens() {
		if (itens == null) {
			itens = this.facade.recuperarTodosItens();
		}
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
	public void zerarItem() {
		this.item = new Item();
		this.itens = null;
	}
	
	public void pesquisarItens() {
		try {
			this.itens = this.facade.recuperarItensPorFiltro(this.pesquisaDescricao, this.pesquisaNumeroLote);
			if (this.itens == null || this.itens.isEmpty()) {
				this.itens = this.facade.recuperarTodosItens();
				this.getWarn("0 resultados para os filtros declarados");
				throw new Exception("0 resultados para os filtros declarados");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("A pesquisa não encontrou resultados com os filtros descritos");
		}
	}
	
	public void salvarOuAlterarItem() {
		try {
			if (this.item.getIdItem() == null) {
				this.facade.incluirItem(this.item);
				this.getMessage("Item adicionado com sucesso");
			} else {
				this.facade.alterarItem(this.item);
				this.getMessage("Item alterado com sucesso");
			}
			this.itens = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("Erro ao adicionar ou alterar item");
		}
	}

	public void excluirItem() {
		try {
			this.facade.excluirItem(this.item);
			this.itens = null;
			this.zerarItem();
			this.getMessage("Item excluido com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("Erro ao excluir item");
		}
	}
	
	@Override
	public void recuperarObjeto() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String param = request.getParameter("itemId");
		Long itemId = Long.parseLong(param);
		this.item = this.facade.recuperarItemPorId(itemId);
	}

	@Override
	public void prepararAlteracao() {
		//sem corpo aqui
	}

}
