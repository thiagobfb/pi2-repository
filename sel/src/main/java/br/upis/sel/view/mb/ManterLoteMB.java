package br.upis.sel.view.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.upis.sel.enums.LoteStatus;
import br.upis.sel.enums.PerfilDescricao;
import br.upis.sel.model.entity.Item;
import br.upis.sel.model.entity.Lote;
import br.upis.sel.model.entity.Participante;
import br.upis.sel.model.entity.Perfil;


@Component
@Scope("view")
@ManagedBean
public class ManterLoteMB extends AbstractMB {
	
	private static final long serialVersionUID = 7859756109388467543L;
	
	private Participante pesquisaComitente;
	private Participante pesquisaArrematante;
	private Long pesquisaNumeroLeilao;
	
	private Lote lote;
	private Participante comitente;
	
	private List<Lote> listaLotes = null;
	private List<Item> itens = null;
	private List<Participante> comitentes = null;
	private Item[] itensSelecionados;
	
	private double valorLote;
	private boolean renderizaTabelaItens;
	
	public ManterLoteMB() {
		this.lote= new Lote();
	}

	public Participante getPesquisaComitente() {
		return pesquisaComitente;
	}

	public void setPesquisaComitente(Participante pesquisaComitente) {
		this.pesquisaComitente = pesquisaComitente;
	}

	public Participante getPesquisaArrematante() {
		return pesquisaArrematante;
	}

	public void setPesquisaArrematante(Participante pesquisaArrematante) {
		this.pesquisaArrematante = pesquisaArrematante;
	}
	
	public Long getPesquisaNumeroLeilao() {
		return pesquisaNumeroLeilao;
	}

	public void setPesquisaNumeroLeilao(Long pesquisaNumeroLeilao) {
		this.pesquisaNumeroLeilao = pesquisaNumeroLeilao;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public Participante getComitente() {
		return comitente;
	}

	public void setComitente(Participante comitente) {
		this.comitente = comitente;
	}

	public List<Lote> getListaLotes() {
		if (listaLotes == null) {
			listaLotes = this.facade.recuperarTodosLotes();
		}
		return listaLotes;
	}

	public void setListaLotes(List<Lote> listaLotes) {
		this.listaLotes = listaLotes;
	}

	public Item[] getItensSelecionados() {
		return itensSelecionados;
	}

	public void setItensSelecionados(Item[] itensSelecionados) {
		this.itensSelecionados = itensSelecionados;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public List<Participante> getComitentes() {
		return comitentes;
	}

	public void setComitentes(List<Participante> comitentes) {
		this.comitentes = comitentes;
	}

	public double getValorLote() {
		return valorLote;
	}

	public void setValorLote(double valorLote) {
		this.valorLote = valorLote;
	}

	public boolean isRenderizaTabelaItens() {
		return renderizaTabelaItens;
	}

	public void setRenderizaTabelaItens(boolean renderizaTabelaItens) {
		this.renderizaTabelaItens = renderizaTabelaItens;
	}

	public void zerarLote() {
		this.lote = new Lote();
		this.listaLotes = null;
		this.itens = null;
	}
	
	public void salvarOuAlterarLote() {
		try {
			if (this.lote.getIdLote() == null) {
				this.lote.setValorTotal(this.valorLote);
				this.facade.incluirLote(this.lote);
				this.getMessage("Lote adicionado com sucesso");
			} else {	
				this.lote.setValorTotal(this.valorLote);
				this.facade.alterarLote(this.lote, LoteStatus.NAO_LEILOADO);
				this.getMessage("Lote alterado com sucesso");
			}
			this.listaLotes = null;
			this.itens = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("Erro ao adicionar ou alterar lote: " + e.getMessage());
		}
	}
	
	public void excluirLote() {
		try {
			this.facade.excluirLote(this.lote);
			this.getMessage("Lote excluido com sucesso");
			this.listaLotes= null;
			this.itens = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("Erro ao excluir lote: " + e.getMessage());
		}
	}
	
	public void recuperarComitentes() {
		if (this.comitentes == null) {
			Perfil perfil = this.facade.recuperarPerfilPorDescricao(PerfilDescricao.ROLE_COMITENTE);
			this.comitentes = this.facade.recuperarParticipantesPorPerfil(perfil);
		}
	}
	
	public void adicionarComitente() {
		if (this.comitente == null) {
			this.lote.setComitente(this.comitente);
		}
	}
	
	public void recuperarItens() {
		if (this.itens == null) {
			this.itens = this.facade.recuperarItensSemLote();
		}
	}
	
	public void adicionarItens() {
		this.valorLote = somarValoresItens(this.itensSelecionados);
		List<Item> selecionados = Arrays.asList(this.itensSelecionados);
		
		if (this.lote.getItens() == null || this.lote.getItens().isEmpty()) {
			this.lote.setItens(new ArrayList<Item>());
		} else {
			this.lote.getItens().clear();
		}
		this.lote.getItens().addAll(selecionados);
	}
	
	private static double somarValoresItens(Item[] itens) {
		double total = 0;
		
		for (Item item : itens) {
			total += item.getValor();
		}
		
		return total;
	}

	public void pesquisarLotes() {
		try {
			this.listaLotes = this.facade.recuperarLotesPorFiltro(this.pesquisaComitente, this.pesquisaArrematante, this.pesquisaNumeroLeilao);
			if (this.listaLotes == null || this.listaLotes.isEmpty()) {
				this.listaLotes = this.facade.recuperarTodosLotes();
				throw new Exception("0 resultados para os filtros declarados");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("A pesquisa não encontrou resultados com os filtros descritos");
		}
	}
	
	public List<Participante> completeParticipantes(String query) {
		List<Participante> filtro = new ArrayList<Participante>();
		filtro = this.facade.recuperarParticipantesPorNome(query);
		return filtro;
	}
	
	public void atualizaListaItens() {
		this.renderizaTabelaItens = true;
	}
	
	@Override
	public void prepararAlteracao() {
		try {
			if (this.facade.isLoteNaoLeiloado(this.lote)) {
				this.itensSelecionados = this.lote.getItens().toArray(new Item[this.lote.getItens().size()]);
				this.valorLote = somarValoresItens(this.itensSelecionados);
			} else {
				throw new RuntimeException("Lotes já leiloados não serão alterados");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getError(e.getMessage());
		}
	}
		
	@Override
	public void recuperarObjeto() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String param = request.getParameter("loteId");
		Long loteId = Long.parseLong(param);
		this.lote = this.facade.recuperarLotePorId(loteId);
	}
}
