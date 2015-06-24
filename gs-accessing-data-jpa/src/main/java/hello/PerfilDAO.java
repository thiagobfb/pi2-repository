package hello;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

public interface PerfilDAO extends CrudRepository<Perfil, Long>, Serializable {

	Perfil findByDescricao(PerfilDescricao descricao);
}
