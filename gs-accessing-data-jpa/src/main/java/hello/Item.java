package hello;

import java.io.Serializable;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_item")
public class Item implements Serializable {
	
	private static final long serialVersionUID = 1141428297614448803L;
	
	public Item() {
	}
	
	public Item(String descricao, double valor, Lote lote) {
		this.descricao = descricao;
		this.valor = valor;
		this.lote = lote;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_id")
	private Long idItem;
	
	@Lob
	@Column(name = "item_descricao")
	private String descricao;
	
	@Column(name = "item_valor")
	private Double valor;
	
	@Column(name = "item_status")
	@Enumerated(EnumType.STRING)
	private ItemStatus status;
	
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
	@JoinTable(name = "lote_has_itens", joinColumns = { @JoinColumn(name = "item_id", insertable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "lote_id", insertable = false, updatable = false) })
	private Lote lote;

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public ItemStatus getStatus() {
		return status;
	}

	public void setStatus(ItemStatus status) {
		this.status = status;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}
	
	@Transient
	public boolean isEditavel() {
		return this.status.equals(ItemStatus.CADASTRADO) || this.status.equals(ItemStatus.ATRIBUIDO);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result	+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Item))
			return false;
		Item other = (Item) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idItem == null) {
			if (other.idItem != null)
				return false;
		} else if (!idItem.equals(other.idItem))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
}
