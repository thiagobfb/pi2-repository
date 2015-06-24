package br.upis.sel.view.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



//import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.upis.sel.enums.ParticipanteStatus;
import br.upis.sel.enums.PerfilDescricao;
import br.upis.sel.model.entity.Participante;
import br.upis.sel.model.entity.Perfil;

@Component
@Scope("view")
//@ManagedBean
public class ManterParticipanteMB extends AbstractMB {
	
	private static final long serialVersionUID = 7859756109388467543L;
	
	private String pesquisaNome;
	private String pesquisaCPF;
	
	private Participante participante;
	
	private List<Perfil> perfis = null;
	private List<Participante> listaParticipantes = null;
	private Perfil[] perfisSelecionados;
	
	private boolean renderizaCampoSenha;
	
	
	public ManterParticipanteMB() {
		this.participante = new Participante();
	}

	public String getPesquisaNome() {
		return pesquisaNome;
	}

	public void setPesquisaNome(String pesquisaNome) {
		this.pesquisaNome = pesquisaNome;
	}

	public String getPesquisaCPF() {
		return pesquisaCPF;
	}

	public void setPesquisaCPF(String pesquisaCPF) {
		this.pesquisaCPF = pesquisaCPF;
	}
	
	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public List<Perfil> getPerfis() {
		if (perfis == null) {
			perfis = this.facade.recuperarTodosPerfis();
		}
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public List<Participante> getListaParticipantes() {
		if (listaParticipantes == null) {
			listaParticipantes = this.facade.recuperarTodosParticipantes();
		}
		return listaParticipantes;
	}

	public void setListaParticipantes(List<Participante> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}

	public Perfil[] getPerfisSelecionados() {
		return perfisSelecionados;
	}

	public void setPerfisSelecionados(Perfil[] perfisSelecionados) {
		this.perfisSelecionados = perfisSelecionados;
	}

	public boolean isRenderizaCampoSenha() {
		return renderizaCampoSenha;
	}

	public void setRenderizaCampoSenha(boolean renderizaCampoSenha) {
		this.renderizaCampoSenha = renderizaCampoSenha;
	}

	public void pesquisarParticipantes() {
		try {
			this.listaParticipantes = this.facade.recuperarParticipantesPorFiltro(this.pesquisaNome, this.pesquisaCPF);
			if (this.listaParticipantes == null || this.listaParticipantes.isEmpty()) {
				this.listaParticipantes = this.facade.recuperarTodosParticipantes();
				throw new Exception("0 resultados para os filtros declarados");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("A pesquisa não encontrou resultados com os filtros descritos");
		}
	}
	
	public void zerarParticipante() {
		System.out.println(participante.getNome());
		this.participante = new Participante();
		this.perfisSelecionados = null;
	}
	
	public void salvarOuAlterarParticipante() {
		try {
			if (this.participante.getIdParticipante() == null) {
				List<Perfil> userRole = Arrays.asList(this.perfisSelecionados);
				this.participante.setPerfis(new ArrayList<Perfil>());
				this.participante.getPerfis().addAll(userRole);
				this.facade.incluirParticipante(this.participante);
				this.getMessage("Participante adicionado com sucesso");
			} else {
				this.participante.getPerfis().clear();
				List<Perfil> userRole = Arrays.asList(this.perfisSelecionados);
				this.participante.getPerfis().addAll(userRole);
				this.facade.alterarParticipante(this.participante, ParticipanteStatus.ATIVO);
				this.getMessage("Participante alterado com sucesso");
			}
			this.listaParticipantes = null;
			this.perfisSelecionados = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("Erro ao adicionar ou alterar participante");
		}
	}
	
	public void desativarParticipante() {
		try {
			this.facade.alterarParticipante(this.participante, ParticipanteStatus.INATIVO);
			this.getMessage("Participante desativado com sucesso");
			this.listaParticipantes = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("Erro ao desativar participante");
		}
	}
	
	public void reativarParticipante() {
		try {
			this.facade.alterarParticipante(this.participante, ParticipanteStatus.ATIVO);
			this.getMessage("Participante reativado com sucesso");
			this.listaParticipantes = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("Erro ao reativar participante");
		}
	}
	
	@Override
	public void prepararAlteracao() {
		this.perfisSelecionados = this.participante.getPerfis().toArray(new Perfil[this.participante.getPerfis().size()]);	
	}
	
	public void renderizarCamposDeSenha() {
		for (int i = 0; i < perfisSelecionados.length; i++) {
			Perfil perfil = this.perfisSelecionados[i];
			if (perfil.getDescricao().equals(PerfilDescricao.ROLE_ADMINISTRADOR)) {
				this.renderizaCampoSenha = true;
				break;
			} else {
				this.renderizaCampoSenha = false;
			}
		}
	}
	
	@Override
	public void recuperarObjeto() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String param = request.getParameter("participanteId");
		Long participanteId = Long.parseLong(param);
		this.participante = this.facade.recuperarParticipantePorId(participanteId);
	}
}
