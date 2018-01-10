package com.gmb.modelo.seguridad;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="gmb_opciones")
@NamedQuery(name="findAllOpciones",
query="SELECT c FROM GmbOpciones c where c.estado='A'")
public class GmbOpciones implements Serializable { 
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idOpcion;
	private String ruta;
	private String nombre;
	private String descripcion;
	private String estado;
	private Integer idPadre;
	private List<GmbRoles> listaRoles;
	private Integer orden;
	
	@Id
    @Column(name = "id_opcion", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getIdOpcion() {
		return idOpcion;
	}
	public void setIdOpcion(Long idOpcion) {
		this.idOpcion = idOpcion;
	}
	 @Column(name = "ruta")
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	 @Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	 @Column(name = "descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	 @Column(name = "estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	 @Column(name = "id_padre")
	public Integer getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}
	 @ManyToMany(mappedBy = "opciones")
	public List<GmbRoles> getListaRoles() {
		return listaRoles;
	}
	public void setListaRoles(List<GmbRoles> listaRoles) {
		this.listaRoles = listaRoles;
	}
	 @Column(name = "orden")
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	
	
	
	
	
	
	

}
