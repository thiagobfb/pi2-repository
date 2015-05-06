package hello;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_perfil")
public class Perfil implements Serializable {

	private static final long serialVersionUID = -6810097846485870129L;
	
	public Perfil() {
	}
	
	public Perfil(PerfilDescricao descricao) {
		this.descricao = descricao;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "perfil_id")
	private Long idPerfil;
	
	@Column(name = "perfil_descricao")
	@Enumerated(EnumType.STRING)
	private PerfilDescricao descricao;

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public PerfilDescricao getDescricao() {
		return descricao;
	}

	public void setDescricao(PerfilDescricao descricao) {
		this.descricao = descricao;
	}

}