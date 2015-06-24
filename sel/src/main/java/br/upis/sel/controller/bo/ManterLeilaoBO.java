package br.upis.sel.controller.bo;

import java.util.Date;
import java.util.List;

import br.upis.sel.enums.LeilaoStatus;
import br.upis.sel.model.entity.Leilao;

public interface ManterLeilaoBO {
	
//	List<Leilao> buscarLeilao(Leilao... leilao);

	Leilao buscarPorId(Long leilaoId);

	List<Leilao> buscarTodosLeiloes();

	void incluirLeilao(Leilao l);
	
	void alterarLeilao(Leilao l, LeilaoStatus ls);
	
	List<Leilao> buscarLeiloesPorFiltro(String nome, Date data, String local);
	
	List<Leilao> buscarLeiloesAgendadosPorData(Date inicio, Date agora, LeilaoStatus ls);

	void finalizarLeilao(Leilao l, LeilaoStatus ls);

	List<Leilao> buscarLeiloesPorFiltro(Date data, String local);
}
