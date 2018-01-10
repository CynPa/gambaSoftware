package com.gambalit.mb;

import java.io.Serializable;
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

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

import com.bmb.servicio.IGmbSalinidadServicio;
import com.gambalit.properties.Propiedades;
import com.gmb.eao.IGmbNivelOperacionEAO;
import com.gmb.modelo.GmbNivelOperacion;
import com.gmb.modelo.GmbSalinidad;

import javafx.scene.chart.CategoryAxis;

@ManagedBean(name = "salinidadMb")
@ViewScoped
public class SalinidadMb implements Serializable {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private Integer salinidad; 
	private Integer temperatura;
	private List<GmbSalinidad > listaSalinidad = new ArrayList<GmbSalinidad>();
	private Integer piscina;
	private GmbNivelOperacion obj1NivelOperacion = new GmbNivelOperacion();
	private List<SelectItem> listaObjNivelOperacion;
	private GmbSalinidad salinidadSelected;
	private List<GmbSalinidad> salinidadFilter;
	private Date fechaIni;
	private Date fechaFin;
	private String volver;
	private Date fecha;
	private Date fechaActual;



	@EJB
	private IGmbNivelOperacionEAO nivelOperacion;
	@EJB
	private IGmbSalinidadServicio  opeSalinidad;

	private LineChartModel lineModel2;
	private LineChartModel lineModel3;

	public LineChartModel getLineModel3() {
		return lineModel3;
	}

	public void setLineModel3(LineChartModel lineModel3) {
		this.lineModel3 = lineModel3;
	}

	@PostConstruct
	public void init() {
		llenarCombo();
		llenarLista();
		fechaActual = new Date();
		grafico();

	}

	public void eliminarSalinidad( GmbSalinidad objeto)
	{
		objeto.setEstado("I");
		opeSalinidad.eliminarSalinidad(objeto);

		llenarLista();
		addMessage("registro eliminado exitosamente");
	}

	public void actualizarSalinidad() {
		try {
			
			GmbSalinidad op1 = new GmbSalinidad();
			op1.setIdSalinidad(salinidadSelected.getIdSalinidad());
			op1.setFecha(salinidadSelected.getFecha());
			op1.setAno(salinidadSelected.getAno());
			op1.setMes(salinidadSelected.getMes());
			op1.setSalinidad(salinidadSelected.getSalinidad());
			op1.setEstado(salinidadSelected.getEstado());
			op1.setTemperatura(salinidadSelected.getTemperatura());
			op1.setNivelOperacion(salinidadSelected.getNivelOperacion());
			if (op1.getSalinidad()==null || op1.getTemperatura()==null)
			{
				errorMessage("los valores no pueden ser nulos");
			}
			else
			{
			opeSalinidad.actualizarSalinidad(op1);
			addMessage("datos modificados exitosamente");
			RequestContext.getCurrentInstance().execute("PF('dlgActualizarSal').hide()");
			llenarLista();
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage("error " + e.toString());
		}
	}

	public void llenarEtiquetaNivelOperacion() {
		obj1NivelOperacion = nivelOperacion.getNivelOperacionxId(piscina);
		// piscina = obj1NivelOperacion.getPiscina().toString();
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

	GmbSalinidad salinidadObjeto = new GmbSalinidad();

	public void llenarLista() {
		listaSalinidad = new ArrayList<GmbSalinidad>();
		List<GmbSalinidad> listaSalinidadobj = opeSalinidad.getListaSalinidad();
		for (GmbSalinidad gmbOpeSalinidad : listaSalinidadobj) {

			salinidadObjeto = new GmbSalinidad();
			salinidadObjeto.setIdSalinidad(gmbOpeSalinidad.getIdSalinidad());
			salinidadObjeto.setFecha(gmbOpeSalinidad.getFecha());
			salinidadObjeto.setNivelOperacion(gmbOpeSalinidad.getNivelOperacion());
			salinidadObjeto.setSalinidad(gmbOpeSalinidad.getSalinidad());
			salinidadObjeto.setTemperatura(gmbOpeSalinidad.getTemperatura());
			salinidadObjeto.setEstado("A");
			listaSalinidad.add(salinidadObjeto);
		}
	}

	public void insertOperacionDiariaDatos() {
		try {
			if (salinidad == null || temperatura == null) {
				errorMessage("los valores no pueden estar vacios");
			} else {
				llenarEtiquetaNivelOperacion();
				GmbSalinidad op1 = new GmbSalinidad();
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat formato2 = new SimpleDateFormat("MM");
				SimpleDateFormat formato3 = new SimpleDateFormat("yyyy");
				//Date fecha = new Date();
				String fechaString = formato.format(fecha); // Convierte Date a String
				Integer fechaMes = Integer.parseInt(formato2.format(fecha));
				Integer fechaAno = Integer.parseInt(formato3.format(fecha));
				Date miFecha = formato.parse(fechaString); // convierte String a Date
				op1.setFecha(miFecha);
				op1.setAno(fechaAno);
				op1.setMes(fechaMes);
				op1.setSalinidad(salinidad);
				op1.setEstado("A");
				op1.setTemperatura(temperatura);
				op1.setNivelOperacion(obj1NivelOperacion);
				String resultado = opeSalinidad.insertSalinidad(op1);
				if (resultado != null) {
					errorMessage("Error: consulte con administrador-> "+resultado);
					System.out.println(resultado);
				} else {
					llenarLista();
					addMessage("Datos ingresados exitosamente");
					RequestContext.getCurrentInstance().execute("PF('dlgInsertSalinidad').hide()");
				}
			}
		} catch (ParseException e) {
			errorMessage("error" + e.toString());
		}
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		CellStyle style = wb.createCellStyle();
		style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());

		for (Row row : sheet) {
			for (Cell cell : row) {
				cell.setCellValue(cell.getStringCellValue().toUpperCase());
				cell.setCellStyle(style);
			}
		}
	}
 
	public String volverInicio() {
		volver = Propiedades.valor_pagina_grafico1;
		return volver;
	}

	public void grafico() {
		lineModel2 = new LineChartModel();
		lineModel3 = new LineChartModel();

		LineChartSeries salinidad = new LineChartSeries();
		LineChartSeries temperatura = new LineChartSeries();
		LineChartSeries salinidad3 = new LineChartSeries();
		List<GmbSalinidad> listaSalinidad = opeSalinidad.getListaSalinidad();
		GmbSalinidad salinidadFecha=new GmbSalinidad();
		salinidadFecha =opeSalinidad.getListaSalinidadLast13Months();
		Date nuevadesde = new Date();

        Calendar cal = Calendar.getInstance(); 
        cal.setTime(salinidadFecha.getFecha());   
        cal.add(Calendar.MONTH, -13);
        nuevadesde = cal.getTime();

		for (GmbSalinidad objSalinidad : listaSalinidad) {
			if(objSalinidad.getFecha().after(nuevadesde)||objSalinidad.getFecha().equals(nuevadesde))
			{
			temperatura.set(objSalinidad.getFecha().toString().substring(0, 10), objSalinidad.getTemperatura());
			salinidad.set(objSalinidad.getFecha().toString().substring(0, 10), objSalinidad.getSalinidad());
			// salinidad3.set(objSalinidad.getFecha().toString().substring(0,10),objSalinidad.getSalinidad());
			// lineModel2.addSeries(salinidad);
			// lineModel2.addSeries(temperatura);
			}
		}
		salinidad.setLabel("Salinidad");
		temperatura.setLabel("temperatura");
		salinidad.setXaxis(AxisType.X2);
		salinidad.setYaxis(AxisType.Y2);
		lineModel2.addSeries(salinidad);
		lineModel2.addSeries(temperatura);
		// lineModel2.addSeries(salinidad3);

		lineModel2.setTitle("Salinidad vs Temperatura");
		lineModel2.setMouseoverHighlight(false);

		DateAxis axis = new DateAxis("YEAR");
		axis.setTickAngle(-90);
		if (fechaIni != null && fechaFin != null) {
			if (fechaIni.after(fechaFin)) {
				errorMessage("La fecha de Inicio no puede ser mayor a la fecha Fin");
			}
			/* Calendar c1 = Calendar.getInstance();
	            c1.setTime(fecha);
	            System.out.println("--> " + c1.getTime());
	            c1.add(Calendar.DAY_OF_MONTH, diasParaAniadir);
	            System.out.println("--> + " + c1.getTime());
	          */  
	            
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			String fechaInicio = formato.format(fechaIni);
			String fechaFinal = formato.format(fechaFin);
			axis.setMin(fechaInicio);
			axis.setMax(fechaFinal);
		}
		// axis.setMax("2017-07-01");
		//axis.setTickFormat("%b %#d, %y");
		axis.setTickFormat("%Y-%m-%d");
		lineModel2.getAxes().put(AxisType.X, axis);
		lineModel2.getAxes().put(AxisType.X2, axis);
		Axis yAxis = lineModel2.getAxis(AxisType.Y);
		yAxis.setLabel("Temperatura");

		yAxis.setMin(20);
		yAxis.setTickInterval("2");
		yAxis.setMax(32);
		Axis y2Axis = new LinearAxis("Salinidad");
		// y2Axis.setMin(3);
		// y2Axis.setTickInterval("5");
		// y2Axis.setMax(34);
		lineModel2.getAxes().put(AxisType.Y2, y2Axis);
		lineModel2.setZoom(true);
		lineModel2.setResetAxesOnResize(true);
	}

	public LineChartModel getLineModel2() {
		return lineModel2;
	}

	public void setLineModel2(LineChartModel lineModel2) {
		this.lineModel2 = lineModel2;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void errorMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	public String getVolver() {
		return volver;
	}

	public void setVolver(String volver) {
		this.volver = volver;
	}

	public GmbSalinidad getSalinidadSelected() {
		return salinidadSelected;
	}

	public void setSalinidadSelected(GmbSalinidad salinidadSelected) {
		this.salinidadSelected = salinidadSelected;
	}

	public List<GmbSalinidad> getSalinidadFilter() {
		return salinidadFilter;
	}

	public void setSalinidadFilter(List<GmbSalinidad> salinidadFilter) {
		this.salinidadFilter = salinidadFilter;
	}

	public List<SelectItem> getListaObjNivelOperacion() {
		return listaObjNivelOperacion;
	}

	public void setListaObjNivelOperacion(List<SelectItem> listaObjNivelOperacion) {
		this.listaObjNivelOperacion = listaObjNivelOperacion;
	}

	public GmbNivelOperacion getObj1NivelOperacion() {
		return obj1NivelOperacion;
	}

	public void setObj1NivelOperacion(GmbNivelOperacion obj1NivelOperacion) {
		this.obj1NivelOperacion = obj1NivelOperacion;
	}

	public Integer getPiscina() {
		return piscina;
	}

	public void setPiscina(Integer piscina) {
		this.piscina = piscina;
	}

	public List<GmbSalinidad> getListaSalinidad() {
		return listaSalinidad;
	}

	public void setListaSalinidad(List<GmbSalinidad> listaSalinidad) {
		this.listaSalinidad = listaSalinidad;
	}

	public Integer getSalinidad() {
		return salinidad;
	}

	public void setSalinidad(Integer salinidad) {
		this.salinidad = salinidad;
	}

	public Integer getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Integer temperatura) {
		this.temperatura = temperatura;
	}

}
