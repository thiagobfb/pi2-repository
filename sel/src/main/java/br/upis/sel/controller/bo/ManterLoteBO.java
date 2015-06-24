package br.upis.sel.controller.bo;

import java.util.List;

import br.upis.sel.enums.LoteStatus;
import br.upis.sel.model.entity.Lote;
import br.upis.sel.model.entity.Participante;

public interface ManterLoteBO {

	List<Lote> buscarTodosLotes();
	
	Lote buscarPorId(Long id);
	
	void incluirLote(Lote l);
	
	void alterarLote(Lote l, LoteStatus ls);

	void excluirLote(Lote lote);

	boolean isNaoLeiloado(Lote lote);

	List<Lote> buscarLotesPorFiltro(Participante comitente, Participante arremantante, Long numLeilao);
	
	List<Lote> buscarLotesSemLeilao();
}
