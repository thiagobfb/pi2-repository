/**
 * 
 */
package br.upis.sel.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.upis.sel.enums.LoteStatus;

/**
 * @author Adriano
 *
 */
@Entity
@Table(name = "tb_lote")
public class Lote implements Serializable {
	
	private static final long serialVersionUID = 8531365504143798137L;
	
	public Lote() {
	}

	public Lote(Double valorTotal, List<Item> itens, Participante comitente) {
		this.valorTotal = valorTotal;
		this.itens = itens;
		this.comitente = comitente;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lote_id")
	private Long idLote;
	
	@Column(name = "lote_valor")
	private Double valorTotal;
	
	@Column(name = "lote_valor_lance")
	private Double valorLanceFinal;
	
	@Column(name = "lote_status")
	@Enumerated(EnumType.STRING)
	private LoteStatus status;

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE })
	@JoinTable(name = "lote_has_itens", joinColumns = { @JoinColumn(name = "lote_id") }, inverseJoinColumns = { @JoinColumn(name = "item_id") })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Item> itens;
	
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE })
	@JoinColumn(name = "comitente_fk")
	private Participante comitente;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "arrematante_fk")
	private Participante arrematante;
	
	@ManyToOne
	@JoinTable(name = "leilao_has_lotes", joinColumns = { @JoinColumn(name = "lote_id") }, inverseJoinColumns = { @JoinColumn(name = "leilao_id") })
	private Leilao leilao;
	
	public Long getIdLote() {
		return idLote;
	}

	public void setIdLote(Long idLote) {
		this.idLote = idLote;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getValorLanceFinal() {
		return valorLanceFinal;
	}

	public void setValorLanceFinal(Double valorLanceFinal) {
		this.valorLanceFinal = valorLanceFinal;
	}

	public LoteStatus getStatus() {
		return status;
	}

	public void setStatus(LoteStatus status) {
		this.status = status;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public Participante getComitente() {
		return comitente;
	}

	public void setComitente(Participante comitente) {
		this.comitente = comitente;
	}

	public Participante getArrematante() {
		return arrematante;
	}

	public void setArrematante(Participante arrematante) {
		this.arrematante = arrematante;
	}
	
	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	@Transient
	public boolean isAlteravelEApagavel() {
		return this.status.equals(LoteStatus.NAO_LEILOADO);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result	+ ((comitente == null) ? 0 : comitente.hashCode());
		result = prime * result + ((idLote == null) ? 0 : idLote.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Lote))
			return false;
		Lote other = (Lote) obj;
		if (comitente == null) {
			if (other.comitente != null)
				return false;
		} else if (!comitente.equals(other.comitente))
			return false;
		if (idLote == null) {
			if (other.idLote != null)
				return false;
		} else if (!idLote.equals(other.idLote))
			return false;
		return true;
	}
	
}
