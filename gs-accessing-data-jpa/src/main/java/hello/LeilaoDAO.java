package hello;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;



public interface LeilaoDAO extends JpaRepository<Leilao, Long>, CrudRepository<Leilao, Long>, JpaSpecificationExecutor<Leilao>, Serializable {
	
//	@Query(value = "SELECT p FROM Leilao p INNER JOIN p.leilao e WHERE e IN (:leilao)")
//	List<Leilao> findByPerfis(@Param("leilao") List<Leilao> leilao);
	
	List<Leilao> findByDataBetweenAndStatus(Date inicial, Date agora, LeilaoStatus ls);
}

