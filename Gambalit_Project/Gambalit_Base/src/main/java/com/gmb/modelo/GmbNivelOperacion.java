package com.gmb.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="gmb_nivel_operacion")
@NamedQuery(name="findAllNivelOperacion",
query="SELECT c FROM GmbNivelOperacion c where c.estado='A' order by c.orden asc")
public class GmbNivelOperacion implements Serializable {
	 
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idNivelOperacion;
	private String piscina; 
	private Double  areaHa;
	private Integer nivelOperacion;
	private String estado;
	private Integer orden;
	//private GmbOperacionDiariaDatos opDiariaDatos;
	
	
	@Id
    @Column(name = "id_nivel_operacion", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getIdNivelOperacion() {
		return idNivelOperacion;
	}
	public void setIdNivelOperacion(Long idNivelOperacion) {
		this.idNivelOperacion = idNivelOperacion;
	}
	 @Column(name = "piscina")
	public String getPiscina() {
		return piscina;
	}
	public void setPiscina(String piscina) {
		this.piscina = piscina;
	}
	 @Column(name = "area_ha")
	public Double getAreaHa() {
		return areaHa;
	}
	public void setAreaHa(Double areaHa) {
		this.areaHa = areaHa;
	}
	 @Column(name = "nivel_operacion")
	public Integer getNivelOperacion() {
		return nivelOperacion;
	}
	public void setNivelOperacion(Integer nivelOperacion) {
		this.nivelOperacion = nivelOperacion;
	}
	
	@Column(name = "estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Column(name = "orden")
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	 
	/*@ManyToOne
	@JoinColumn(name="operacionDiariaDatos")
	public GmbOperacionDiariaDatos getOpDiariaDatos() {
		return opDiariaDatos;
	}
	public void setOpDiariaDatos(GmbOperacionDiariaDatos opDiariaDatos) {
		this.opDiariaDatos = opDiariaDatos;
	}*/
	

}
