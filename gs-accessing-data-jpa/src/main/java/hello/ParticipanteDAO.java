package hello;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface ParticipanteDAO extends CrudRepository<Participante, Long>, Serializable {
	
	Participante findByUsernameAndPassword(String nome, String password);

	Participante findByUsername(String nome);
	
	@Query(value = "SELECT p FROM Participante p INNER JOIN p.perfis e WHERE e IN (:perfis)")
	List<Participante> findByPerfis(@Param("perfis") List<Perfil> perfis);
	
	List<Participante> findByNomeLikeAndUsername(String nome, String username);
	
	List<Participante> findByNomeLike(String nome);
	
	List<Participante> findByUsernameLike(String nome);
	
	List<Participante> findByNomeStartingWith(String nome);
	
	@Query(value = "SELECT p FROM Participante p INNER JOIN p.perfis e WHERE p.nome LIKE :nome AND e IN (:perfis)")
	List<Participante> findByNomeStartingWithAndPerfis(@Param("nome") String nome, @Param("perfis") List<Perfil> perfis);
}
