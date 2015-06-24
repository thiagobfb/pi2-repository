package br.upis.sel.view.mb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.DateUtils;

import br.upis.sel.enums.LeilaoStatus;
import br.upis.sel.enums.LoteStatus;
import br.upis.sel.model.entity.Item;
import br.upis.sel.model.entity.Leilao;
import br.upis.sel.model.entity.Lote;
import br.upis.sel.model.entity.Participante;

@Component
@Scope("view")
@ManagedBean
public class RealizarLeilaoMB extends AbstractMB {

	private static final long serialVersionUID = -1190355515393607745L;
	
	private Leilao leilao;
	private Lote lote;
	private Participante arrematante;
	
	private List<Leilao> leiloesDeHoje = null;
	private List<Lote> lotes = null;
	private List<Item> itens = null;

	
	public RealizarLeilaoMB() {
		this.leilao = new Leilao();
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public Participante getArrematante() {
		return arrematante;
	}

	public void setArrematante(Participante arrematante) {
		this.arrematante = arrematante;
	}

	public List<Leilao> getLeiloesDeHoje() {
		return leiloesDeHoje;
	}

	public void setLeiloesDeHoje(List<Leilao> leiloesDeHoje) {
		this.leiloesDeHoje = leiloesDeHoje;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public void adicionarArrematante() {
		if (this.arrematante == null) {
			this.lote.setArrematante(this.arrematante);
		}
	}
	
	public void zerarLote() {
		this.lote = new Lote();
		this.lotes = null;
		this.itens = null;
	}
	
	public void finalizarLeilao() {
		try {
			this.facade.finalizarLeilao(this.leilao, LeilaoStatus.FINALIZADO);
			this.zerarObjetos();
			this.getMessage("Leilão finalizado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			this.getError(e.getMessage());
		}
	}
	
	public void recuperarItens() {
		if (this.lote != null) {
			this.itens = this.lote.getItens();
		}
	}
	
	private void zerarObjetos() {
		this.leilao = new Leilao();
		this.arrematante = null;
		this.leiloesDeHoje = null;
		this.zerarLote();
	}
	
	public void arrematarLote() {
		try {
			this.facade.alterarLote(this.lote, LoteStatus.LEILOADO);
			this.getMessage("Lote arrematado com sucesso!");
			this.lotes = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getError(e.getMessage());
		}
	}
	
	@Override
	public void recuperarObjeto() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String param = request.getParameter("loteID");
		Long loteId = Long.parseLong(param);
		this.lote = this.facade.recuperarLotePorId(loteId);
	}

	public List<Participante> completeParticipantes(String query) {
		List<Participante> filtro = new ArrayList<Participante>();
		filtro = this.facade.recuperarParticipantesPorNome(query);
		return filtro;
	}
	
	@Override
	public void prepararAlteracao() {
		if (this.leiloesDeHoje == null) {
			Calendar inicioDoDia = DateUtils.createToday();
			Calendar agora = DateUtils.createNow(TimeZone.getTimeZone("America/Sao_Paulo"), Locale.getDefault());
			this.leiloesDeHoje = this.facade.buscarLeiloesAgendadosParaHoje(inicioDoDia.getTime(), agora.getTime(), LeilaoStatus.AGENDADO);
		}
	}
	
	public void selecionarLeilao() {
		try {
			Long id = this.leilao.getIdLeilao();
			this.facade.alterarLeilao(this.leilao, LeilaoStatus.EM_REALIZACAO);
			this.leilao = this.facade.recuperarLeilaoPorId(id);
			if (this.lotes == null) {
				this.lotes = this.leilao.getLotes();
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getError(e.getMessage());
		}
	}

}
