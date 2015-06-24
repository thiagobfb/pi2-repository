/**
 * 
 */
package br.upis.sel.model.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.upis.sel.enums.LoteStatus;
import br.upis.sel.model.entity.Leilao;
import br.upis.sel.model.entity.Lote;
import br.upis.sel.model.entity.Participante;

/**
 * @author ADRIANO
 *
 */
public interface LoteDAO extends JpaRepository<Lote, Long>, CrudRepository<Lote, Long>, Serializable, JpaSpecificationExecutor<Lote> {

	Lote findByStatus(LoteStatus status);
	
	List<Lote> findByComitente(Participante c);
	
	List<Lote> findByArrematante(Participante a);
	
	List<Lote> findByLeilao(Leilao l);
	
	List<Lote> findByComitenteAndArrematante(Participante c, Participante a);
	
	List<Lote> findByComitenteAndLeilao(Participante c, Leilao l);
	
	List<Lote> findByArrematanteAndLeilao(Participante a, Leilao l);
	
	List<Lote> findByComitenteAndArrematanteAndLeilao(Participante c, Participante a, Leilao l);
	
	@Query(value = "SELECT l FROM Lote l LEFT JOIN l.leilao e WHERE e is null")
	List<Lote> findByLeilaoIsNull();
	
}
