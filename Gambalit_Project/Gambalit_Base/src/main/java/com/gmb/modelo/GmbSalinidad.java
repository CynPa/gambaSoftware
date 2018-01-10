package com.gmb.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity  
@Table(name="gmb_salinidad") 
@NamedQuery(name = "findAllSalinidad", query = "SELECT c FROM GmbSalinidad c where c.estado='A' order by c.fecha desc")
public class GmbSalinidad implements Serializable{
 
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private Long idSalinidad;
	private Date fecha;
	private Integer ano;
	private Integer mes;
	private Integer salinidad;
	private Integer temperatura;
	private String estado;
	private GmbNivelOperacion  nivelOperacion;
	
	
	
	@Id
    @Column(name = "id_salinidad", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getIdSalinidad() {
		return idSalinidad;
	}
	public void setIdSalinidad(Long idSalinidad) {
		this.idSalinidad = idSalinidad;
	}
	 @Column(name = "fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	 @Column(name = "año")
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	 @Column(name = "mes")
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	 @Column(name = "salinidad")
	public Integer getSalinidad() {
		return salinidad;
	}
	public void setSalinidad(Integer salinidad) {
		this.salinidad = salinidad;
	}
	 @Column(name = "temperatura")
	public Integer getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(Integer temperatura) {
		this.temperatura = temperatura;
	}
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "salinidadNivelOperacion")
	public GmbNivelOperacion getNivelOperacion() {
		return nivelOperacion;
	}

	public void setNivelOperacion(GmbNivelOperacion nivelOperacion) {
		this.nivelOperacion = nivelOperacion;
	}
	@Column(name = "estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	

}
