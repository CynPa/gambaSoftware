package com.gmb.modelo;

import java.io.Serializable;
import java.util.Date;
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

@Table(name = "gmb_datos_diarios")
@NamedQuery(name = "findAllOperacionDiariaDatos", query = "SELECT c FROM GmbOperacionDiariaDatos c  where c.estado='A' order by c.fecha,c.nivelOperacion.orden desc")
public class GmbOperacionDiariaDatos implements Serializable {

	/**  
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idOperacionDiaria;
	private Date fecha;
	private Integer year;
	private Integer mes;
	private GmbNivelOperacion nivelOperacion;
	private Integer nivelDiario;
	private Integer duro;
	private Integer flacido;
	private Integer mudado;
	private String estado;
	private Double peso;
	private Double mortFresco;
	private Double mortRojo;
	private Double enfermos;
	private Double salinidad;
	private Double temperatura;
	private Boolean bocache;

	@Id
	@Column(name = "id_operacion_diaria", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdOperacionDiaria() {
		return idOperacionDiaria;
	}

	public void setIdOperacionDiaria(Long idOperacionDiaria) {
		this.idOperacionDiaria = idOperacionDiaria;
	}

	@Column(name = "fecha")
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "year")
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "mes")
	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	@Column(name = "nivel_diario")
	public Integer getNivelDiario() {
		return nivelDiario;
	}

	public void setNivelDiario(Integer nivelDiario) {
		this.nivelDiario = nivelDiario;
	}

	@Column(name = "duro")
	public Integer getDuro() {
		return duro;
	}

	public void setDuro(Integer duro) {
		this.duro = duro;
	}

	@Column(name = "flacido")
	public Integer getFlacido() {
		return flacido;
	}

	public void setFlacido(Integer flacido) {
		this.flacido = flacido;
	}

	@Column(name = "mudado")
	public Integer getMudado() {
		return mudado;
	}

	public void setMudado(Integer mudado) {
		this.mudado = mudado;
	}
	// @OneToMany(targetEntity=GmbNivelOperacion.class)

	@Column(name = "estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estdo) {
		this.estado = estdo;
	}

	// @Transient
	@ManyToOne
	@JoinColumn(name = "operacionDiariaDatos")
	public GmbNivelOperacion getNivelOperacion() {
		return nivelOperacion;
	}

	public void setNivelOperacion(GmbNivelOperacion nivelOperacion) {
		this.nivelOperacion = nivelOperacion;
	}

	@Column(name = "peso")
	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Column(name = "mortalidad_fresco")
	public Double getMortFresco() {
		return mortFresco;
	}

	public void setMortFresco(Double mortFresco) {
		this.mortFresco = mortFresco;
	}

	@Column(name = "mortalidad_rojo")
	public Double getMortRojo() {
		return mortRojo;
	}

	public void setMortRojo(Double mortRojo) {
		this.mortRojo = mortRojo;
	}

	@Column(name = "enfermos")
	public Double getEnfermos() {
		return enfermos;
	}

	public void setEnfermos(Double enfermos) {
		this.enfermos = enfermos;
	}
	@Column(name = "salinidad")
	public Double getSalinidad() {
		return salinidad;
	}

	public void setSalinidad(Double salinidad) {
		this.salinidad = salinidad;
	}
	@Column(name = "temperatura")
	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}
	@Column(name = "bocache")
	public Boolean getBocache() {
		return bocache;
	}

	public void setBocache(Boolean bocache) {
		this.bocache = bocache;
	}
	

}
