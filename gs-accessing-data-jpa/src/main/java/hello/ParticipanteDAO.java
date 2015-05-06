package hello;

import org.springframework.data.repository.CrudRepository;


public interface ParticipanteDAO extends CrudRepository<Participante, Long> {
	
	Participante findByUsernameAndPassword(String nome, String password);

	Participante findByUsername(String nome);	
}
