package com.gmb.modelo.seguridad;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name="gmb_usuarios")
@NamedQuery(name="findAllUsuarios",
query="SELECT c FROM GmbUsuarios c where c.estado='A' order by c.usuario asc")
public class GmbUsuarios implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idUsuario;
	private String usuario;
	private String nombre;
	private String clave;
	private String estado;
	private Date fechaIngreso;
	private Date fechaultimaModificacion;
	private List<GmbRoles> roles;
	
	@Id
    @Column(name = "id_usuario", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	 @Column(name = "usuario")
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	 @Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	 @Column(name = "clave")
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	 @Column(name = "estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	 @Column(name = "fecha_ingreso")
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	 @Column(name = "fecha_ultima_modificacion")
	public Date getFechaultimaModificacion() {
		return fechaultimaModificacion;
	}
	public void setFechaultimaModificacion(Date fechaultimaModificacion) {
		this.fechaultimaModificacion = fechaultimaModificacion;
	}
	@ManyToMany( fetch =FetchType.EAGER,  cascade = { 
	        CascadeType.MERGE
	    })
	    @JoinTable(name = "gmb_usuarios_roles",
	        joinColumns = @JoinColumn(name = "id_usuario"),
	        inverseJoinColumns = @JoinColumn(name = "id_rol")
	    )
	public List<GmbRoles> getRoles() {
		return roles;
	}
	public void setRoles(List<GmbRoles> roles) {
		this.roles = roles;
	}
	
	
	
	

}
