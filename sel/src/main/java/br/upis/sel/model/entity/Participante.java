package br.upis.sel.model.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.upis.sel.enums.ParticipanteStatus;

@Entity
@Table(name="tb_participante")
public class Participante implements UserDetails {

	private static final long serialVersionUID = -558847129204545L;
	
	public Participante() {
	}
	
	public Participante(String nome, String password, String username,
			ParticipanteStatus status, List<Perfil> perfis) {
		this.nome = nome;
		this.password = password;
		this.username = username;
		this.status = status;
		this.perfis = perfis;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "participante_id")
	private Long idParticipante;
	
	@Column(name = "participante_nome")
	private String nome;
	
	@Column(name = "participante_senha")
	private String password;
	
	@Column(name = "participante_cpf")
	private String username;
	
	@Column(name = "participante_status")
	@Enumerated(EnumType.STRING)
	private ParticipanteStatus status;
	
	@ManyToMany
    @JoinTable(name = "participante_perfil", joinColumns = { @JoinColumn(name = "participante_id") }, inverseJoinColumns = { @JoinColumn(name = "perfil_id") })
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<Perfil> perfis;

	public Long getIdParticipante() {
		return idParticipante;
	}

	public void setIdParticipante(Long idParticipante) {
		this.idParticipante = idParticipante;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ParticipanteStatus getStatus() {
		return status;
	}

	public void setStatus(ParticipanteStatus status) {
		this.status = status;
	}
	
	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return perfis;
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return this.status.equals(ParticipanteStatus.ATIVO);
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return this.status.equals(ParticipanteStatus.ATIVO);
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return this.status.equals(ParticipanteStatus.ATIVO);
	}

	@Override
	@Transient
	public boolean isEnabled() {
		return this.status.equals(ParticipanteStatus.ATIVO);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idParticipante == null) ? 0 : idParticipante.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((perfis == null) ? 0 : perfis.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Participante))
			return false;
		Participante other = (Participante) obj;
		if (idParticipante == null) {
			if (other.idParticipante != null)
				return false;
		} else if (!idParticipante.equals(other.idParticipante))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (perfis == null) {
			if (other.perfis != null)
				return false;
		} else if (!perfis.equals(other.perfis))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
