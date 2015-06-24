package br.upis.sel.view.mb;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.upis.sel.model.entity.Leilao;
import br.upis.sel.util.RelatorioUtil;

@Component
@Scope("request")
@ManagedBean
public class RelatorioMB extends AbstractMB {

	private static final long serialVersionUID = 7497080863997755439L;
	
	private Date filtroData;
	private String filtroLocal;

	public Date getFiltroData() {
		return filtroData;
	}

	public void setFiltroData(Date filtroData) {
		this.filtroData = filtroData;
	}

	public String getFiltroLocal() {
		return filtroLocal;
	}

	public void setFiltroLocal(String filtroLocal) {
		this.filtroLocal = filtroLocal;
	}

	public void gerarRelatorio() {
		try {
			List<Leilao> consulta = this.facade.recuperarLeiloesPorFiltro(this.filtroData, this.filtroLocal);
			
			if (consulta != null && !consulta.isEmpty()) {
				String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/relatorio_leiloes.jasper");
				RelatorioUtil.gerarRelatorio(FacesContext.getCurrentInstance(), reportPath, consulta, new HashMap<String, String>());
			} else {
				this.getWarn("Não existem leilões com esses parâmetros");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.getError("Erro ao gerar relatório");
		}
	}
	
	public void zerarCampos() {
		this.filtroData = null;
		this.filtroLocal = null;
	}
	
	@Override
	public void recuperarObjeto() {
		
		
	}

	@Override
	public void prepararAlteracao() {
		
		
	}

}
