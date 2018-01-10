package com.gambalit.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.bmb.servicio.seguridad.IOpcionesServicio;
import com.gmb.modelo.seguridad.GmbOpciones;
@ManagedBean(name="opcionMb")
@ViewScoped
public class OpcionMB  implements Serializable{

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	
	private List<GmbOpciones > listaOpciones;
	 
	@EJB
	private IOpcionesServicio opcionesServicio;

	private List<GmbOpciones> opcionesSelected;
	private List<GmbOpciones> opcionesIng;
	 private int ancho;
	
	@PostConstruct
	public void init() {
		ancho=400;
		listaOpciones=opcionesServicio.getListaOpcionesHijas();
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

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public List<GmbOpciones> getOpcionesIng() {
		return opcionesIng;
	}

	public void setOpcionesIng(List<GmbOpciones> opcionesIng) {
		this.opcionesIng = opcionesIng;
	}

	public void opcionesIngresar()
	{
	
		opcionesIng=opcionesSelected;
		
	}
	
	
	
	
}
