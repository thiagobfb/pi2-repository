package br.upis.sel.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.upis.sel.enums.LeilaoStatus;

@Entity
@Table(name="tb_leilao")
public class Leilao implements Serializable {

	private static final long serialVersionUID = -558847129204545L;

	
	public Leilao() {
	}
	
	public Leilao(String nome, String local, Date data, List<Participante> leiloeiros) {
		this.nome = nome;
		this.local = local;
		this.data = data;
		this.leiloeiros = leiloeiros;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "leilao_id")
	private Long idLeilao;
	
	@Column(name = "leilao_nome")
	private String nome;
	
	@Column(name = "leilao_local")
	private String local;
	
	@Column(name = "leilao_data")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date data;
	
	@Column(name = "leilao_status")
	@Enumerated(EnumType.STRING)
	private LeilaoStatus status;
	
	@ManyToMany
	@JoinTable(name = "leilao_leiloeiros", joinColumns = { @JoinColumn(name = "leilao_id") }, inverseJoinColumns = { @JoinColumn(name = "participante_id") })
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<Participante> leiloeiros;
	
	@OneToMany
	@JoinTable(name = "leilao_has_lotes", joinColumns = { @JoinColumn(name = "leilao_id") }, inverseJoinColumns = { @JoinColumn(name = "lote_id") })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Lote> lotes;
	
	@Transient
	private List<Participante> participantes;
		
	public Long getIdLeilao() {
		return idLeilao;
	}

	public void setIdLeilao(Long idLeilao) {
		this.idLeilao = idLeilao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public LeilaoStatus getStatus() {
		return status;
	}

	public void setStatus(LeilaoStatus status) {
		this.status = status;
	}

	public List<Participante> getLeiloeiros() {
		return leiloeiros;
	}

	public void setLeiloeiros(List<Participante> leiloeiros) {
		this.leiloeiros = leiloeiros;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}

	public List<Participante> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Participante> participantes) {
		this.participantes = participantes;
	}
	
	@Transient
	public boolean isEditavel() {
		return this.status.equals(LeilaoStatus.AGENDADO) || this.status.equals(LeilaoStatus.CANCELADO);
	}
	
	@Transient
	public boolean isCancelado() {
		return this.status.equals(LeilaoStatus.CANCELADO);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result	+ ((idLeilao == null) ? 0 : idLeilao.hashCode());
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Leilao))
			return false;
		Leilao other = (Leilao) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (idLeilao == null) {
			if (other.idLeilao != null)
				return false;
		} else if (!idLeilao.equals(other.idLeilao))
			return false;
		if (local == null) {
			if (other.local != null)
				return false;
		} else if (!local.equals(other.local))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}


