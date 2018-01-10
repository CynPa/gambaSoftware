package com.gmb.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="gmb_reporte_patrulla") 
@NamedQuery(name = "findAllPatrull", query = "SELECT c FROM GmbReportPatrol c ")
public class GmbReportPatrol  implements Serializable{
	/**
	 *   
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idReportPatrol;
	private String ruta;
	private String tiempoPlaneado;
	private String guardia;
	private String puntoChequeo;
	private String device_number;
	private Date tiempoRealArrivo;
	private String evento;
	private String estado;
	
	@Id
    @Column(name = "id_reporte_patrulla", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getIdReportPatrol() {
		return idReportPatrol;
	}
	public void setIdReportPatrol(Long idReportPatrol) {
		this.idReportPatrol = idReportPatrol;
	}
	@Column(name = "ruta")
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	@Column(name = "tiempo_planeado")
	public String getTiempoPlaneado() {
		return tiempoPlaneado;
	}
	public void setTiempoPlaneado(String tiempoPlaneado) {
		this.tiempoPlaneado = tiempoPlaneado;
	}
	@Column(name = "guardia")
	public String getGuardia() {
		return guardia;
	}
	public void setGuardia(String guardia) {
		this.guardia = guardia;
	}
	@Column(name = "punto_chequeo")
	public String getPuntoChequeo() {
		return puntoChequeo;
	}
	public void setPuntoChequeo(String puntoChequeo) {
		this.puntoChequeo = puntoChequeo;
	}
	@Column(name = "device_number")
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tiempo_real_arrivo")
	public Date getTiempoRealArrivo() {
		return tiempoRealArrivo;
	}
	public void setTiempoRealArrivo(Date tiempoRealArrivo) {
		this.tiempoRealArrivo = tiempoRealArrivo;
	}
	@Column(name = "evento")
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	@Column(name = "estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
