package com.gambalit.mb;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

import com.bmb.servicio.seguridad.IOpcionesServicio;
import com.bmb.servicio.seguridad.IRolesServicio;
import com.gambalit.properties.Propiedades;
import com.gmb.modelo.GmbOperacionDiariaDatos;
import com.gmb.modelo.seguridad.GmbOpciones;
import com.gmb.modelo.seguridad.GmbRoles;

@ManagedBean(name = "rolMb")
@SessionScoped
public class RolMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IRolesServicio rolServicio;

	private String descripcion;
	private String nombre;
	private List<GmbRoles> listaRoles;
	private List<GmbOpciones> listaOpciones;
	@EJB
	private IOpcionesServicio opcionesServicio;
	private List<GmbOpciones> opcionesSelected;
	private List<GmbOpciones> opcionesModificar;
	private List<GmbOpciones> opcionesIng;
	private GmbRoles rolesSelected;
	private List<GmbOpciones> listaOpcionesNoSelected;

	@PostConstruct
	public void init() {

		llenarRoles();
		listaOpciones = opcionesServicio.getListaOpcionesHijas();

	}

	public void insertarRol() {
		GmbRoles rol = new GmbRoles();
		try {

			if (nombre == null || opcionesIng.size() < 1) {
				errorMessage("El campo nombre no debe estar vacio y debe escoger las opciones a agregar.");
			} else {
				rol.setDescripcion(descripcion);
				rol.setNombre(nombre);
				rol.setEstado("A");
				rol.setOpciones(opcionesIng);
				rolServicio.insertarRol(rol);
				llenarRoles();
				addMessage("Rol ingresado exitosamente");
			}
			// return Propiedades.valor_pagina_regresar_rol;
		} catch (Exception e) {
			errorMessage("Error al ingresar Rol " + e.toString());
			// return "";
		}
	}

	public void llenarRoles() {
		listaRoles = rolServicio.consultaRoles();
	}

	public String modificarRol() {
		return Propiedades.valor_pagina_modificar_rol;
	}

	public String irCrearRol() {
		return Propiedades.valor_pagina_crear_rol;
	}

	public String volverRol() {
		return Propiedades.valor_pagina_regresar_rol;
	}

	

	public void opcionesIngresar() {
		opcionesIng = opcionesSelected;

	}
	
	
	public void opcionesModificar() {
		rolServicio.modificarRol(rolesSelected);
		opcionesModificar = null;
		addMessage("Rol modificado exitosamente");
	}

	public void opcionesAdd() {
		String nombre;
		String descripcion;
		for (GmbOpciones gmbOpciones : rolesSelected.getOpciones()) {
			opcionesModificar.add(gmbOpciones);	
		}
		if (opcionesModificar!=null) {
			rolesSelected.setOpciones(opcionesModificar);
		}
		nombre = rolesSelected.getNombre();
		descripcion = rolesSelected.getDescripcion();
		rolesSelected.setNombre(nombre);
		rolesSelected.setDescripcion(descripcion);
	}

	public void opcionesNoSelected() {
		System.out.println("ingreso opciones no selected");
		List<GmbOpciones> op = new ArrayList<GmbOpciones>();
		for (GmbOpciones gmbOp : rolesSelected.getOpciones()) {
			op.add(gmbOp);
		}
		Iterator<GmbOpciones> it = op.iterator();
		listaOpcionesNoSelected = new ArrayList<GmbOpciones>();
		listaOpcionesNoSelected = listaOpciones;
		while (it.hasNext()) {
			GmbOpciones opcione = new GmbOpciones();
			opcione = it.next();
			for (int i = 0; i < listaOpciones.size(); i++) {
				GmbOpciones gmbOpciones = new GmbOpciones();
				gmbOpciones = listaOpciones.get(i);
				if (gmbOpciones.getIdOpcion() == opcione.getIdOpcion()) {
					listaOpcionesNoSelected.remove(gmbOpciones);
				}
			}
		}
	}

	public void deleteFromList(GmbOpciones objeto) {
		GmbRoles listaDummy = new GmbRoles();
		listaDummy = rolesSelected;
		for (int i = 0; i <= listaDummy.getOpciones().size(); i++) {
			if (listaDummy.getOpciones().get(i).getIdOpcion() == objeto.getIdOpcion()) {
				listaDummy.getOpciones().remove(i);
				break;
			}
		}
		rolesSelected=listaDummy;
	}

	

	public void eliminarRol(GmbRoles gmbRoles) {
		gmbRoles.setEstado("I");
		rolServicio.eliminarRol(gmbRoles);
		addMessage("registro eliminado exitosamente");
		llenarRoles();
	}

	
	//GETTER AND SETTER
	public GmbRoles getRolesSelected() {
		return rolesSelected;
	}

	public void setRolesSelected(GmbRoles rolesSelected) {
		this.rolesSelected = rolesSelected;
	}

	public List<GmbOpciones> getOpcionesModificar() {
		return opcionesModificar;
	}

	public void setOpcionesModificar(List<GmbOpciones> opcionesModificar) {
		this.opcionesModificar = opcionesModificar;
	}

	public List<GmbOpciones> getListaOpcionesNoSelected() {
		return listaOpcionesNoSelected;
	}

	public void setListaOpcionesNoSelected(List<GmbOpciones> listaOpcionesNoSelected) {
		this.listaOpcionesNoSelected = listaOpcionesNoSelected;
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void errorMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<GmbRoles> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<GmbRoles> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public List<GmbOpciones> getListaOpciones() {
		return listaOpciones;
	}

	public void setListaOpciones(List<GmbOpciones> listaOpciones) {
		this.listaOpciones = listaOpciones;
	}

	public List<GmbOpciones> getOpcionesSelected() {
		return opcionesSelected;
	}

	public void setOpcionesSelected(List<GmbOpciones> opcionesSelected) {
		this.opcionesSelected = opcionesSelected;

	}

	public List<GmbOpciones> getOpcionesIng() {
		return opcionesIng;
	}

	public void setOpcionesIng(List<GmbOpciones> opcionesIng) {
		this.opcionesIng = opcionesIng;
	}

}
