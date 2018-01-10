package com.gmb.modelo.seguridad;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name="gmb_roles")
@NamedQuery(name="findAllRoles",
query="SELECT c FROM GmbRoles c where c.estado='A'")
public class GmbRoles  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idRol;
	private String nombre;
	private String descripcion;
	private String estado;
	private List<GmbUsuarios> listaUsuarios;
	private List<GmbOpciones> opciones;
	
	@Id
    @Column(name = "id_rol", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getIdRol() {
		return idRol;
	}
	public void setIdRol(Long idRol) {
		this.idRol = idRol;
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
	 @ManyToMany(mappedBy = "roles")
	public List<GmbUsuarios> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<GmbUsuarios> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
	@ManyToMany( fetch =FetchType.EAGER,  cascade = { 
	        CascadeType.MERGE
	    })
	    @JoinTable(name = "gmb_roles_opciones",
	        joinColumns = @JoinColumn(name = "id_rol"),
	        inverseJoinColumns = @JoinColumn(name = "id_opcion")
	    )
	public List<GmbOpciones> getOpciones() {
		return opciones;
	}
	public void setOpciones(List<GmbOpciones> opciones) {
		this.opciones = opciones;
	}
	
	

}
