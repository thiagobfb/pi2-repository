package br.upis.sel.view.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.upis.sel.enums.LeilaoStatus;
import br.upis.sel.enums.PerfilDescricao;
import br.upis.sel.model.entity.Leilao;
import br.upis.sel.model.entity.Lote;
import br.upis.sel.model.entity.Participante;
import br.upis.sel.model.entity.Perfil;

@Component
@Scope("view")
@ManagedBean
public class ManterLeilaoMB extends AbstractMB {
	
	private static final long serialVersionUID = -3241224342390358410L;

	private String pesquisaNome;
	private Date pesquisaData;
	private String pesquisaLocal;
		
	private Leilao leilao;
	
	private List<Leilao> listaLeilao = null;
	private List<Participante> leiloeiros = null;
	private List<Lote> lotes = null;
	private Participante[] leiloeirosSelecionados;
	private Lote[] lotesSelecionados;
	
	public ManterLeilaoMB() {
		leilao = new Leilao();
	}

	public String getPesquisaNome() {
		return pesquisaNome;
	}

	public void setPesquisaNome(String pesquisaNome) {
		this.pesquisaNome = pesquisaNome;
	}

	public Date getPesquisaData() {
		return pesquisaData;
	}

	public void setPesquisaData(Date pesquisaData) {
		this.pesquisaData = pesquisaData;
	}

	public String getPesquisaLocal() {
		return pesquisaLocal;
	}

	public void setPesquisaLocal(String pesquisaLocal) {
		this.pesquisaLocal = pesquisaLocal;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public List<Leilao> getListaLeilao() {
		if (listaLeilao == null) {
			listaLeilao = this.facade.recuperarTodosLeiloes();
		}
		return listaLeilao;
	}

	public void setListaLeilao(List<Leilao> listaLeilao) {
		this.listaLeilao = listaLeilao;
	}

	public List<Participante> getLeiloeiros() {
		return leiloeiros;
	}

	public void setLeiloeiros(List<Participante> leiloeiros) {
		this.leiloeiros = leiloeiros;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}

	public Participante[] getLeiloeirosSelecionados() {
		return leiloeirosSelecionados;
	}

	public void setLeiloeirosSelecionados(Participante[] leiloeirosSelecionados) {
		this.leiloeirosSelecionados = leiloeirosSelecionados;
	}

	public Lote[] getLotesSelecionados() {
		return lotesSelecionados;
	}

	public void setLotesSelecionados(Lote[] lotesSelecionados) {
		this.lotesSelecionados = lotesSelecionados;
	}

	public void recuperarLeiloeiros() {
		if (this.leiloeiros == null) {
			Perfil perfil = this.facade.recuperarPerfilPorDescricao(PerfilDescricao.ROLE_LEILOEIRO);
			this.leiloeiros = this.facade.recuperarParticipantesPorPerfil(perfil);
		}
	}
	
	public void recuperarLotes() {
		if (this.lotes == null) {
			this.lotes = this.facade.recuperarLotesSemLeilao();
		}
	}
	
	public void adicionarLeiloeiros() {
		List<Participante> selecionados = Arrays.asList(this.leiloeirosSelecionados);
		
		if (this.leilao.getLeiloeiros() == null || this.leilao.getLeiloeiros().isEmpty()) {
			this.leilao.setLeiloeiros(new ArrayList<Participante>());
		} else {
			this.leilao.getLeiloeiros().clear();
		}
		this.leilao.getLeiloeiros().addAll(selecionados);
	}
	
	public void adicionarLotes() {
		List<Lote> selecionados = Arrays.asList(this.lotesSelecionados);
		
		if (this.leilao.getLotes() == null || this.leilao.getLotes().isEmpty()) {
			this.leilao.setLotes(new ArrayList<Lote>());
		} else {
			this.leilao.getLotes().clear();
		}
		
		this.leilao.getLotes().addAll(selecionados);
	}
	
	public void zerarLeilao() {
		this.leilao = new Leilao();
		this.leiloeiros = null;
		this.lotes = null;
	}
	
	public void salvarOuAlterarLeilao() {
		try {
			if (this.leilao.getIdLeilao() == null) {
				this.leilao.setStatus(LeilaoStatus.AGENDADO);
				this.facade.incluirLeilao(this.leilao);
				this.getMessage("Leilão adicionado com sucesso");
			} else {
				this.facade.alterarLeilao(this.leilao, LeilaoStatus.AGENDADO);
				this.getMessage("Lote alterado com sucesso");
			}
			this.listaLeilao = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("Erro ao adicionar ou alterar leilão: " + e.getMessage());
		}
	}
	
	public void cancelarLeilao() {
		try {
			this.facade.alterarLeilao(this.leilao, LeilaoStatus.CANCELADO);
			this.getMessage("Lote cancelado com sucesso");
			this.listaLeilao = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("Erro ao cancelar leilão: " + e.getMessage());
		}
	}
	
	public void reagendarLeilao() {
		try {
			this.facade.alterarLeilao(this.leilao, LeilaoStatus.AGENDADO);
			this.getMessage("Lote reagendado com sucesso");
			this.listaLeilao = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("Erro ao reagendar leilão: " + e.getMessage());
		}
	}
	
	public void pesquisarLeilao() {
		try {
			this.listaLeilao = this.facade.recuperarLeiloesPorFiltro(this.pesquisaNome, this.pesquisaData, this.pesquisaLocal);
			if (this.listaLeilao == null || this.listaLeilao.isEmpty()) {
				this.listaLeilao = this.facade.recuperarTodosLeiloes();
				throw new Exception("0 resultados para os filtros declarados");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("A pesquisa não encontrou resultados com os filtros descritos");
		}
	}
	
	@Override
	public void recuperarObjeto() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String param = request.getParameter("leilaoId");
		Long leilaoId = Long.parseLong(param);
		this.leilao = this.facade.recuperarLeilaoPorId(leilaoId);
	}

	@Override
	public void prepararAlteracao() {
		try {
			if (this.leilao.isEditavel()) {
				this.leiloeirosSelecionados = this.leilao.getLeiloeiros().toArray(new Participante[this.leilao.getLeiloeiros().size()]);
				this.lotesSelecionados = this.leilao.getLotes().toArray(new Lote[this.leilao.getLotes().size()]);
			} else {
				throw new RuntimeException("Leilões finalizados ou em realização não serão alterados");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getError(e.getMessage());
		}
	}
	
}
