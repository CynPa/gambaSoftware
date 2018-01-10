package com.gambalit.mb;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import com.bmb.servicio.IGmbSiembraServicio;
import com.gambalit.properties.Propiedades;
import com.gmb.eao.IGmbNivelOperacionEAO;
import com.gmb.modelo.GmbNivelOperacion;
import com.gmb.modelo.GmbSiembra;

@ManagedBean(name = "siembraMb")
@ViewScoped
public class SiembraMb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IGmbSiembraServicio servicioSiembra;
	@EJB
	private IGmbNivelOperacionEAO servicioNivelOperacion;
	private List<GmbSiembra> ListaSiembra;
	private Date fechaSiembra;
	private Date fehaAlimentacion;
	private List<GmbNivelOperacion> ListaNivelOperacion;
	private List<SelectItem> listaObjNivelOperacion;
	private GmbSiembra siembraSelected;
	private List<GmbSiembra> siembraFilter;
	@EJB
	private IGmbNivelOperacionEAO nivelOperacion;

	private GmbNivelOperacion obj1NivelOperacion = new GmbNivelOperacion();
	private String objnivelOperacion;

	private Date fechaCosecha;
	private Date fechaActual;
	private int ancho;

	@PostConstruct
	public void init() {
		ancho = 400;
		fechaActual = new Date();
		llenarLista();
		llenarCombo();
		ListaNivelOperacion = servicioNivelOperacion.getListaNivelOperacion();
	}

	public void llenarCombo() {
		listaObjNivelOperacion = new ArrayList<SelectItem>();
		List<GmbNivelOperacion> listaNivelOperacion = nivelOperacion.getListaNivelOperacion();
		for (GmbNivelOperacion gmbNivelOperacion : listaNivelOperacion) {
			SelectItem nivelSeleccionado = new SelectItem(gmbNivelOperacion.getIdNivelOperacion(),
					gmbNivelOperacion.getPiscina().toString());
			listaObjNivelOperacion.add(nivelSeleccionado);
		}
	}

	public void llenarLista() {
		ListaSiembra = servicioSiembra.consultarSiembra();

	}

	public void guardarSiembra() throws ParseException, IOException {
		GmbSiembra gmbSiembra = new GmbSiembra();
		Integer validadorSiembra;
		FacesContext context = FacesContext.getCurrentInstance();
		String path = context.getExternalContext().getRequestContextPath();
		try {
			System.out.println("OBJNIVELOPERACION " + objnivelOperacion);
			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");

			if (fechaSiembra == null) {
				errorMessage("Debe ingresar la fecha de siembra");
			} else {
				obj1NivelOperacion = nivelOperacion.getNivelOperacionxId(Integer.parseInt(objnivelOperacion));
				validadorSiembra = servicioSiembra.validarSiembraIngreso(obj1NivelOperacion);
				if (validadorSiembra > 0) {
					errorMessage("La piscina ya se encuentra aperturada");
				} else {
					String fechaString = formato.format(fechaSiembra); // Convierte Date a Strin
					Date mifechaSiembra = formato.parse(fechaString); // convierte String a Date
					gmbSiembra.setEstado(true);
					gmbSiembra.setFechaSiembra(mifechaSiembra);
					gmbSiembra.setNivelOperacion(obj1NivelOperacion);
					servicioSiembra.ingresarSiembra(gmbSiembra);
					addMessage("datos ingresados exitosamente");
					RequestContext.getCurrentInstance().execute("PF('dlgIngresarSiembra').hide()");
					llenarLista();
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect(path + Propiedades.valor_pagina_siembra);
				}
			}
		} catch (Exception e) {
			errorMessage("error " + e.toString());
			FacesContext.getCurrentInstance().getExternalContext().redirect(path + Propiedades.valor_pagina_siembra);
		}
	}

	public void modificar() {
		try {
			if (siembraSelected.getEstado() == true) {
				servicioSiembra.modificarSiembra(siembraSelected);
				RequestContext.getCurrentInstance().execute("PF('dlgActualizarSiembra').hide()");
				llenarLista();
				addMessage("datos modificados exitosamente");
			}

			else {
				addMessage("La pisina ya se encuentra cerrada");
			}
		} catch (Exception e) {
			errorMessage(e.toString());
		}
	}

	public void cierrePiscina() throws ParseException {
		if (siembraSelected.getEstado() == true) {
		
			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
			String fechaString2 = formato.format(fechaCosecha);

			Calendar cal = Calendar.getInstance();
			
			
			
			fechaSiembra=siembraSelected.getFechaSiembra();
			Date mifechaCosecha = formato.parse(fechaString2); // convierte String a Date
			cal.setTime(mifechaCosecha);
			Timestamp fechaTs = new Timestamp(mifechaCosecha.getTime());
			if (siembraSelected.getFechaSiembra().after(fechaTs)) {
				errorMessage("Fecha de Cosecha debe ser posterior a la fecha de siembra");
			} else if (siembraSelected.getFehcaAlimentacion() == null) {
				errorMessage("Fecha de Alimentacion no se ha ingresado");
			} else if (siembraSelected.getFehcaAlimentacion().after(fechaTs)
					|| siembraSelected.getFehcaAlimentacion().equals(fechaTs)) {
				errorMessage("Fecha de Cosecha debe ser posterior a la fecha de alimentacion");
			} else if (siembraSelected.getFehcaAlimentacion() != null) {
				if (mifechaCosecha.after(siembraSelected.getFechaSiembra())) {
					siembraSelected.setFechaCosecha(mifechaCosecha);
					siembraSelected.setEstado(false);
					servicioSiembra.modificarSiembra(siembraSelected);
					RequestContext.getCurrentInstance().execute("PF('dlgCierre').hide()");
					llenarLista();
				} else {
					errorMessage("Fecha de Cosecha debe ser mayor que la fecha de Alimentación");
				}
			} else {
				errorMessage("Fecha de Alimentacion no se ha ingresado");
			}

		} else {
			errorMessage("registro ya se encuentra cerrado");
		}
	}

	public void llenarSiembraSelec(GmbSiembra siembra) {
		siembraSelected = siembra;
	}
 
	public void siembraAlimentacion() throws ParseException {

		if (siembraSelected.getEstado() == true) {
			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
			String fechaString = formato.format(fehaAlimentacion);
			Date mifechaAlimentacion = formato.parse(fechaString);
			if (mifechaAlimentacion.after(siembraSelected.getFechaSiembra())) {
				siembraSelected.setFehcaAlimentacion(mifechaAlimentacion);
				servicioSiembra.modificarSiembra(siembraSelected);
				RequestContext.getCurrentInstance().execute("PF('dlgAlimentacion').hide()");
				llenarLista();
			} else {
				errorMessage("Fecha de Alimentación debe ser mayor a fecha de Siembra");
			}
		} else {
			errorMessage("El registro ya se encuentra cerrado");
		}
	}

	public List<GmbSiembra> getListaSiembra() {
		return ListaSiembra;
	}

	public void setListaSiembra(List<GmbSiembra> listaSiembra) {
		ListaSiembra = listaSiembra;
	}

	public Date getFechaSiembra() {
		return fechaSiembra;
	}

	public void setFechaSiembra(Date fechaSiembra) {
		this.fechaSiembra = fechaSiembra;
	}

	public Date getFehaAlimentacion() {
		return fehaAlimentacion;
	}

	public void setFehaAlimentacion(Date fehaAlimentacion) {
		this.fehaAlimentacion = fehaAlimentacion;
	}

	public List<SelectItem> getListaObjNivelOperacion() {
		return listaObjNivelOperacion;
	}

	public void setListaObjNivelOperacion(List<SelectItem> listaObjNivelOperacion) {
		this.listaObjNivelOperacion = listaObjNivelOperacion;
	}

	public String getObjnivelOperacion() {
		return objnivelOperacion;
	}

	public void setObjnivelOperacion(String objnivelOperacion) {
		this.objnivelOperacion = objnivelOperacion;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public Date getFechaCosecha() {
		return fechaCosecha;
	}

	public void setFechaCosecha(Date fechaCosecha) {
		this.fechaCosecha = fechaCosecha;
	}

	public GmbSiembra getSiembraSelected() {
		return siembraSelected;
	}

	public void setSiembraSelected(GmbSiembra siembraSelected) {
		this.siembraSelected = siembraSelected;
	}

	public List<GmbSiembra> getSiembraFilter() {
		return siembraFilter;
	}

	public void setSiembraFilter(List<GmbSiembra> siembraFilter) {
		this.siembraFilter = siembraFilter;
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void errorMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

}
