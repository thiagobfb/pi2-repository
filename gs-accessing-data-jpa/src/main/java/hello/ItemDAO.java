package hello;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ItemDAO extends CrudRepository<Item, Long>, Serializable {

	List<Item> findByDescricaoContaining(String descricao);
	
	List<Item> findByDescricaoContainingAndLote(String descricao, Lote lote);

	@Query(value = "SELECT i FROM Item i LEFT JOIN i.lote l WHERE l is null")
	List<Item> findByLoteIsNull();
	
	@Query(value = "SELECT i FROM Item i INNER JOIN i.lote l WHERE l = (:lote)")
	List<Item> findAllByLote(@Param("lote") Lote lote);
}
