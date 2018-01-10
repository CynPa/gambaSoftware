package com.gmb.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="gmb_siembra")
@NamedQuery(name = "findAllSiembra", query = "SELECT c FROM GmbSiembra c order by c.fechaSiembra ,c.fehcaAlimentacion,c.fechaCosecha,c.estado asc")
public class GmbSiembra implements Serializable{

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idSiembra;
	private Date fechaSiembra;
	private Date fehcaAlimentacion;
	private Date fechaCosecha;
	private Boolean estado;
	private GmbNivelOperacion nivelOperacion;
	
	@Id
    @Column(name = "id_siembra", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getIdSiembra() {
		return idSiembra;
	}
	public void setIdSiembra(Long idSiembra) {
		this.idSiembra = idSiembra;
	}
	@Column(name = "fecha_siembra")
	public Date getFechaSiembra() {
		return fechaSiembra;
	}
	public void setFechaSiembra(Date fechaSiembra) {
		this.fechaSiembra = fechaSiembra;
	}
	@Column(name = "fecha_alimentacion")
	public Date getFehcaAlimentacion() {
		return fehcaAlimentacion;
	}
	public void setFehcaAlimentacion(Date fehcaAlimentacion) {
		this.fehcaAlimentacion = fehcaAlimentacion;
	}
	@Column(name = "estado")
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	@Column(name = "nivel_operacion")
	public GmbNivelOperacion getNivelOperacion() {
		return nivelOperacion;
	}
	public void setNivelOperacion(GmbNivelOperacion nivelOperacion) {
		this.nivelOperacion = nivelOperacion;
	}
	@Column(name = "fecha_cosecha")
	public Date getFechaCosecha() {
		return fechaCosecha;
	}
	public void setFechaCosecha(Date fechaCosecha) {
		this.fechaCosecha = fechaCosecha;
	}
	
	
	
	
	
	

}
