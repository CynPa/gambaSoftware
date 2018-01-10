package com.gambalit.mb;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.hibernate.mapping.Array;

import com.bmb.servicio.IGmbOperacionDiariaDatosServicio;
import com.bmb.servicio.IGmbSiembraServicio;
import com.gmb.eao.IGmbNivelOperacionEAO;
import com.gmb.modelo.GmbNivelOperacion;
import com.gmb.modelo.GmbOperacionDiariaDatos;
import com.gmb.modelo.GmbReporteNivel;
import com.gmb.modelo.GmbSiembra;
import com.gmb.modelo.PlantillaReporteSiembra;

@ManagedBean(name = "reporteSiembra")
@ViewScoped
public class ReporteSiembraMb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IGmbOperacionDiariaDatosServicio servicioOperacionDiaria;
	@EJB
	private IGmbSiembraServicio siembraServicio;
	@EJB
	private IGmbNivelOperacionEAO nivelOperacion;
	private Integer objnivelOperacion;
	private List<SelectItem> listaObjNivelOperacion;
	private List<GmbOperacionDiariaDatos> listaOdd;
	private List<PlantillaReporteSiembra> listaRs;
	private GmbSiembra siembra;
	private List<String> piscinas;
	private List<String> indicadores;
	private List<ColumnModel> columns;
	String nivel;
	String peso;
	String incSemAnt;
	String incAlimentacion;
	String incSiembra;
	String semanasSiembra;
	String piscina;
	String fechaNivel;
	String fechaPeso;
	String diasAlimentacion;
	List<String> listafechaNivel = new ArrayList<String>();
	List<String> listaFechaPeso = new ArrayList<String>();
	List<String> piscina1 = new ArrayList<String>();
	List<String> listaNivel = new ArrayList<String>();
	List<String> listaPeso = new ArrayList<String>();
	List<String> listaIncSemAnt = new ArrayList<String>();
	List<String> listaIncAlimentacion = new ArrayList<String>();
	List<String> listaIncSiembra = new ArrayList<String>();
	List<String> listaSemanasSiembra = new ArrayList<String>();
	List<String> listaDiasAlimentacion = new ArrayList<String>();
	List<String> listaFechaSiembra = new ArrayList<String>();
	private List<GmbReporteNivel> reporteFinalPesca = new ArrayList<GmbReporteNivel>();
	Date fechaSiembraInicio = null;
	String fechaString = null;

	@PostConstruct
	public void init() {
		llenarCombo();
		piscinas = new ArrayList<String>();
		getTotalesFinales();
		indicadores = new ArrayList<String>();
		indicadores.add("NIVEL CM");
		indicadores.add("PESO GR");
		indicadores.add("INC SEM ANTERIOR GR");
		indicadores.add("INCREMENTO DESDE ALIMENTACION GR");
		indicadores.add("INCREMENTO DESDE SIEMBRA GR");
		indicadores.add("SEMANAS DESDE SIEMBRA");

	}

	/*
	 * public void llenarCombo() { listaObjNivelOperacion = new
	 * ArrayList<SelectItem>(); List<GmbNivelOperacion> listaNivelOperacion =
	 * nivelOperacion.getListaNivelOperacion(); for (GmbNivelOperacion
	 * gmbNivelOperacion : listaNivelOperacion) { SelectItem nivelSeleccionado = new
	 * SelectItem(gmbNivelOperacion.getIdNivelOperacion(),
	 * gmbNivelOperacion.getPiscina().toString());
	 * listaObjNivelOperacion.add(nivelSeleccionado); } }
	 */

	public void llenarCombo() {
		listaObjNivelOperacion = new ArrayList<SelectItem>();
		List<GmbSiembra> listaSiembraActiva = siembraServicio.getSiembraxPiscinaActiva();
		for (GmbSiembra gmbSiembra : listaSiembraActiva) {
			SelectItem nivelSeleccionado = new SelectItem(gmbSiembra.getIdSiembra(),
					gmbSiembra.getNivelOperacion().getPiscina().toString());
			listaObjNivelOperacion.add(nivelSeleccionado);
		}
	}

	public void getTotalesFinales() {
		Double valorInicial = 0.0;
		Double valorFinal = 0.0;
		Integer valorDiasAlimentacion = 0;
		Double valorCalculoAlimentacion = 0.0;
		Double valorCalculoSiembra = 0.0;
		GmbReporteNivel ListaReportePesca = new GmbReporteNivel();
		GmbReporteNivel reportePesca = null;

		List<GmbSiembra> listaSiembraActiva = siembraServicio.getSiembraxPiscinaActiva();
		List<GmbNivelOperacion> listaNivelOperacion = nivelOperacion.getListaNivelOperacion();
		// for (Integer x = 1; x <= 21; x++) {

		// if (listaSiembraActiva.get(x).getNivelOperacion().getPiscina() ==
		// x.toString()) {
		// if (listaSiembraActiva.get(x).getNivelOperacion().getPiscina().toString() ==
		// x.toString()) {
		for (GmbNivelOperacion gmbNivelOperacion : listaNivelOperacion) {

			Boolean bandera = true;
			piscina = gmbNivelOperacion.getPiscina();
			incAlimentacion = null;
			nivel = null;
			peso = null;
			incSemAnt = null;
			incAlimentacion = null;
			incSiembra = null;
			semanasSiembra = null;
			fechaNivel = null;
			fechaPeso = null;
			String diasSiembra = null;
			Integer contadorFechaSiembra = null;
			Date fechaPesoAnt = new Date();
			for (GmbSiembra gmbSiembra : listaSiembraActiva) {
				fechaSiembraInicio = null;

				if (gmbSiembra.getNivelOperacion().getPiscina() == gmbNivelOperacion.getPiscina()
						|| gmbSiembra.getNivelOperacion().getPiscina().equals(gmbNivelOperacion.getPiscina())) {

					siembra = siembraServicio.getSiembraxId(Integer.parseInt(gmbSiembra.getIdSiembra().toString()));
					listaOdd = servicioOperacionDiaria.getOddxFechaDesde(siembra.getFechaSiembra(),
							siembra.getNivelOperacion().getPiscina());
					if (listaOdd != null) {
						listaRs = new ArrayList<PlantillaReporteSiembra>();
						Integer contador = 0;
						String valorFechaPesoAnt = null;
						String valorPesoAnt = null;

						for (GmbOperacionDiariaDatos odd : listaOdd) {
							if(odd.getPeso() ==null)
							{
								odd.setPeso(0.0);
							}
							if(odd.getNivelDiario()==null)
							{
								odd.setNivelDiario(0);
							}
							fechaNivel = odd.getFecha().toString().substring(0, 11);
							if (fechaNivel == null) {
								fechaNivel = "";
							}
							if (odd.getPeso() > 0.0) {
								fechaPeso = odd.getFecha().toString().substring(0, 11);
								valorFechaPesoAnt = fechaPeso;
								peso = String.format("%.2f", Double.parseDouble(odd.getPeso().toString()));
								valorPesoAnt = peso.toString();
							} else {
								fechaPeso = valorFechaPesoAnt;
								peso = valorPesoAnt;
							}
							
						if(odd.getNivelDiario()>0)
						{
							Integer nivelDiferencia = odd.getNivelDiario()- odd.getNivelOperacion().getNivelOperacion()
									;
							
							if (nivelDiferencia == 0) {
								nivel = "NIVEL";
							} else {
								nivel = nivelDiferencia.toString();
							}
						}
						else
						{
							nivel="0";
						}
							PlantillaReporteSiembra plantillaReporteSiembra = new PlantillaReporteSiembra();
							// **********INI LOGICA PARA VARIACION SEMANA ANTERIOR******************
							if (contador == 0) {
								valorFinal = odd.getPeso();
								valorCalculoSiembra = odd.getPeso();
								fechaSiembraInicio = odd.getFecha();
								fechaPesoAnt = odd.getFecha();
							} else if (contador > 0) {
								if (odd.getPeso() > 0) {
									// valorInicial = odd.getPeso() - valorFinal;
									valorInicial = ((odd.getPeso() - valorFinal)
											/ ((odd.getFecha().getTime() - fechaPesoAnt.getTime()) / 86400000)) * 7;

									// plantillaReporteSiembra.setVariacionSemanal(valorInicial);//VARIACION SEMANA
									// ANTERIOR
									incSemAnt = String.format("%.2f", Double.valueOf(valorInicial.toString()));

									if (valorInicial > 0.0) {
										valorFinal = odd.getPeso();
										fechaPesoAnt = odd.getFecha();
										// valorCalculoAlimentacion = valorFinal;
									}
								}
							}
							// ***********************************************************************************
							// *******************INI LOGICA PARA CRECIMIENTO ALIMENTACION*****************
							if (odd.getFecha().equals(siembra.getFehcaAlimentacion())) {
								valorCalculoAlimentacion = valorFinal;
							}
							if (siembra.getFehcaAlimentacion() != null) {
								Date fechaHoy = new Date();

								if (odd.getFecha().after(siembra.getFehcaAlimentacion())) {
									Long diasParcial = ((odd.getFecha().getTime()
											- siembra.getFehcaAlimentacion().getTime()) / 86400000);
									Integer dias = diasParcial.intValue();
									// Long valorDiasAlimentacion_ = (dias -
									// siembra.getFehcaAlimentacion().getTime())
									// / 86400000;
									valorDiasAlimentacion = dias;
									diasAlimentacion = valorDiasAlimentacion.toString();
									if (dias > 0) {
										if (odd.getPeso() > 0) {
											// System.out.println("Dias:::::" + dias);
											double diasdiv7 = (dias / 7.00);
											Double valor = (odd.getPeso() - valorCalculoAlimentacion) / (diasdiv7);
											// plantillaReporteSiembra.setVariacionAlimentacion(Double.parseDouble(valor.toString()));//INCREMENTO
											// DESDE ALIMENTACION
											incAlimentacion = String.format("%.2f", valor);
										}
									}
								}
							} else {
								plantillaReporteSiembra.setVariacionAlimentacion(0.0);/// INCREMENTO DESDE ALIMENTACION
								diasAlimentacion = "0";
							}
							// ***********************************************************************************
							// ***********************************INI LOGICA SIEMBRA**********************
							if (contador == 0) {
								contadorFechaSiembra = 0;
							} else if (contador > 0) {
								Long diasSiembraIteracion = (odd.getFecha().getTime()
										- fechaSiembraInicio.getTime()) / 86400000;
								if(odd.getPeso()!= null)
								{
								if (odd.getPeso() > 0) {
									// System.out.println("Dias:::::" + contador);
									
									
									Double diasdiv7 = (contador / 7.0);
									Double valor_ = (odd.getPeso() - valorCalculoSiembra) / diasdiv7;
									// System.out.println(odd.getPeso() + "-" + valorCalculoSiembra + "/" +
									// diasdiv7);
									// plantillaReporteSiembra.setVariacionSiembra(valor_);//INCREMENTO DESDE
									// SIEMBRA
									incSiembra = String.format("%.2f", valor_);
								}
								}
								diasSiembra = diasSiembraIteracion.toString();
							}
							// **********************************************************************************
							// plantillaReporteSiembra.setOperacionDiariaDatos(odd);
							// plantillaReporteSiembra.setDiasDesdeSiembra(Integer.parseInt(contador.toString()));//SEMANAS
							// DE SIEMBRA
							semanasSiembra = diasSiembra;
							contador++;
							listaRs.add(plantillaReporteSiembra);
						}
						piscinas.add(piscina);
						listaNivel.add(nivel);
						listaPeso.add(peso);
						listaIncSemAnt.add(incSemAnt);
						listaIncAlimentacion.add(incAlimentacion);
						listaIncSiembra.add(incSiembra);
						listaSemanasSiembra.add(semanasSiembra);
						if (fechaNivel == null) {
							fechaNivel = "N/A";
						}
						listafechaNivel.add(fechaNivel);
						if (fechaPeso == null) {
							fechaPeso = "N/A";
						}
						listaFechaPeso.add(fechaPeso);
						listaDiasAlimentacion.add(diasAlimentacion);
						if (fechaSiembraInicio != null) {
							SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
							fechaString = formato.format(fechaSiembraInicio);
						} else {
							fechaString = "";
						}
						listaFechaSiembra.add(fechaString);
						bandera = false;
						break;
					} else {

						listafechaNivel.add("");
						listaFechaPeso.add("");
						piscinas.add(piscina);
						listaNivel.add("");
						listaPeso.add("");
						listaIncSemAnt.add("");
						listaIncAlimentacion.add("");
						listaIncSiembra.add("");
						listaSemanasSiembra.add("");
						listaDiasAlimentacion.add("");
						if (fechaSiembraInicio != null) {
							SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
							fechaString = formato.format(fechaSiembraInicio);
						} else {
							fechaString = "";
						}
						listaFechaSiembra.add(fechaString);
					}
				}
			}
			if (bandera == true) {
				listafechaNivel.add("");
				listaFechaPeso.add("");
				piscinas.add(piscina);
				listaNivel.add("");
				listaPeso.add("");
				listaIncSemAnt.add("");
				listaIncAlimentacion.add("");
				listaIncSiembra.add("");
				listaSemanasSiembra.add("");
				listaDiasAlimentacion.add("");
				if (fechaSiembraInicio != null) {
					SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
					fechaString = formato.format(fechaSiembraInicio);
				} else {
					fechaString = "";
				}
				listaFechaSiembra.add(fechaString);
				fechaSiembraInicio = null;
			}

		}
		reportePesca = new GmbReporteNivel();
		reportePesca.setMes("PISCINA");
		reportePesca.setPiscina1(piscinas.get(0));
		reportePesca.setPiscina2(piscinas.get(1));
		reportePesca.setPiscina3(piscinas.get(2));
		reportePesca.setPiscina4(piscinas.get(3));
		reportePesca.setPiscina5(piscinas.get(4));
		reportePesca.setPiscina6(piscinas.get(5));
		reportePesca.setPiscina7(piscinas.get(6));
		reportePesca.setPiscina8(piscinas.get(7));
		reportePesca.setPiscina9(piscinas.get(8));
		reportePesca.setPiscina10(piscinas.get(9));
		reportePesca.setPiscina11(piscinas.get(10));
		reportePesca.setPiscina12(piscinas.get(11));
		reportePesca.setPiscina13(piscinas.get(12));
		reportePesca.setPiscina14(piscinas.get(13));
		reportePesca.setPiscina15(piscinas.get(14));
		reportePesca.setPiscina16(piscinas.get(15));
		reportePesca.setPiscina17(piscinas.get(16));
		reportePesca.setPiscina18(piscinas.get(17));
		reportePesca.setPiscina19(piscinas.get(18));
		reportePesca.setPiscina20(piscinas.get(19));
		reportePesca.setPiscina21(piscinas.get(20));
		// terminar

		reporteFinalPesca.add(reportePesca);
		reportePesca = new GmbReporteNivel();
		reportePesca.setMes("FECHA NIVEL");
		reportePesca.setPiscina1(listafechaNivel.get(0).toString());
		reportePesca.setPiscina2(listafechaNivel.get(1).toString());
		reportePesca.setPiscina3(listafechaNivel.get(2).toString());
		reportePesca.setPiscina4(listafechaNivel.get(3).toString());
		reportePesca.setPiscina5(listafechaNivel.get(4).toString());
		reportePesca.setPiscina6(listafechaNivel.get(5).toString());
		reportePesca.setPiscina7(listafechaNivel.get(6).toString());
		reportePesca.setPiscina8(listafechaNivel.get(7).toString());
		reportePesca.setPiscina9(listafechaNivel.get(8).toString());
		reportePesca.setPiscina10(listafechaNivel.get(9).toString());
		reportePesca.setPiscina11(listafechaNivel.get(10).toString());
		reportePesca.setPiscina12(listafechaNivel.get(11).toString());
		reportePesca.setPiscina13(listafechaNivel.get(12).toString());
		reportePesca.setPiscina14(listafechaNivel.get(13).toString());
		reportePesca.setPiscina15(listafechaNivel.get(14).toString());
		reportePesca.setPiscina16(listafechaNivel.get(15).toString());
		reportePesca.setPiscina17(listafechaNivel.get(16).toString());
		reportePesca.setPiscina18(listafechaNivel.get(17).toString());
		reportePesca.setPiscina19(listafechaNivel.get(18).toString());
		reportePesca.setPiscina20(listafechaNivel.get(19).toString());
		reportePesca.setPiscina21(listafechaNivel.get(20).toString());
		reporteFinalPesca.add(reportePesca);
		reportePesca = new GmbReporteNivel();
		reportePesca.setMes("NIVEL CM");
		reportePesca.setPiscina1(listaNivel.get(0));
		reportePesca.setPiscina2(listaNivel.get(1));
		reportePesca.setPiscina3(listaNivel.get(2));
		reportePesca.setPiscina4(listaNivel.get(3));
		reportePesca.setPiscina5(listaNivel.get(4));
		reportePesca.setPiscina6(listaNivel.get(5));
		reportePesca.setPiscina7(listaNivel.get(6));
		reportePesca.setPiscina8(listaNivel.get(7));
		reportePesca.setPiscina9(listaNivel.get(8));
		reportePesca.setPiscina10(listaNivel.get(9));
		reportePesca.setPiscina11(listaNivel.get(10));
		reportePesca.setPiscina12(listaNivel.get(11));
		reportePesca.setPiscina13(listaNivel.get(12));
		reportePesca.setPiscina14(listaNivel.get(13));
		reportePesca.setPiscina15(listaNivel.get(14));
		reportePesca.setPiscina16(listaNivel.get(15));
		reportePesca.setPiscina17(listaNivel.get(16));
		reportePesca.setPiscina18(listaNivel.get(17));
		reportePesca.setPiscina19(listaNivel.get(18));
		reportePesca.setPiscina20(listaNivel.get(19));
		reportePesca.setPiscina21(listaNivel.get(20));
		reporteFinalPesca.add(reportePesca);
		reportePesca = new GmbReporteNivel();
		reportePesca.setMes("FECHA PESO");
		reportePesca.setPiscina1(listaFechaPeso.get(0).toString());
		reportePesca.setPiscina2(listaFechaPeso.get(1).toString());
		reportePesca.setPiscina3(listaFechaPeso.get(2).toString());
		reportePesca.setPiscina4(listaFechaPeso.get(3).toString());
		reportePesca.setPiscina5(listaFechaPeso.get(4).toString());
		reportePesca.setPiscina6(listaFechaPeso.get(5).toString());
		reportePesca.setPiscina7(listaFechaPeso.get(6).toString());
		reportePesca.setPiscina8(listaFechaPeso.get(7).toString());
		reportePesca.setPiscina9(listaFechaPeso.get(8).toString());
		reportePesca.setPiscina10(listaFechaPeso.get(9).toString());
		reportePesca.setPiscina11(listaFechaPeso.get(10).toString());
		reportePesca.setPiscina12(listaFechaPeso.get(11).toString());
		reportePesca.setPiscina13(listaFechaPeso.get(12).toString());
		reportePesca.setPiscina14(listaFechaPeso.get(13).toString());
		reportePesca.setPiscina15(listaFechaPeso.get(14).toString());
		reportePesca.setPiscina16(listaFechaPeso.get(15).toString());
		reportePesca.setPiscina17(listaFechaPeso.get(16).toString());
		reportePesca.setPiscina18(listaFechaPeso.get(17).toString());
		reportePesca.setPiscina19(listaFechaPeso.get(18).toString());
		reportePesca.setPiscina20(listaFechaPeso.get(19).toString());
		reportePesca.setPiscina21(listaFechaPeso.get(20).toString());
		reporteFinalPesca.add(reportePesca);

		reportePesca = new GmbReporteNivel();

		reportePesca.setMes("PESO GR");
		reportePesca.setPiscina1(listaPeso.get(0));
		reportePesca.setPiscina2(listaPeso.get(1));
		reportePesca.setPiscina3(listaPeso.get(2));
		reportePesca.setPiscina4(listaPeso.get(3));
		reportePesca.setPiscina5(listaPeso.get(4));
		reportePesca.setPiscina6(listaPeso.get(5));
		reportePesca.setPiscina7(listaPeso.get(6));
		reportePesca.setPiscina8(listaPeso.get(7));
		reportePesca.setPiscina9(listaPeso.get(8));
		reportePesca.setPiscina10(listaPeso.get(9));
		reportePesca.setPiscina11(listaPeso.get(10));
		reportePesca.setPiscina12(listaPeso.get(11));
		reportePesca.setPiscina13(listaPeso.get(12));
		reportePesca.setPiscina14(listaPeso.get(13));
		reportePesca.setPiscina15(listaPeso.get(14));
		reportePesca.setPiscina16(listaPeso.get(15));
		reportePesca.setPiscina17(listaPeso.get(16));
		reportePesca.setPiscina18(listaPeso.get(17));
		reportePesca.setPiscina19(listaPeso.get(18));
		reportePesca.setPiscina20(listaPeso.get(19));
		reportePesca.setPiscina21(listaPeso.get(20));
		reporteFinalPesca.add(reportePesca);
		reportePesca = new GmbReporteNivel();
		reportePesca.setMes("INC. SEM. ANT. GR");
		reportePesca.setPiscina1(listaIncSemAnt.get(0));
		reportePesca.setPiscina2(listaIncSemAnt.get(1));
		reportePesca.setPiscina3(listaIncSemAnt.get(2));
		reportePesca.setPiscina4(listaIncSemAnt.get(3));
		reportePesca.setPiscina5(listaIncSemAnt.get(4));
		reportePesca.setPiscina6(listaIncSemAnt.get(5));
		reportePesca.setPiscina7(listaIncSemAnt.get(6));
		reportePesca.setPiscina8(listaIncSemAnt.get(7));
		reportePesca.setPiscina9(listaIncSemAnt.get(8));
		reportePesca.setPiscina10(listaIncSemAnt.get(9));
		reportePesca.setPiscina11(listaIncSemAnt.get(10));
		reportePesca.setPiscina12(listaIncSemAnt.get(11));
		reportePesca.setPiscina13(listaIncSemAnt.get(12));
		reportePesca.setPiscina14(listaIncSemAnt.get(13));
		reportePesca.setPiscina15(listaIncSemAnt.get(14));
		reportePesca.setPiscina16(listaIncSemAnt.get(15));
		reportePesca.setPiscina17(listaIncSemAnt.get(16));
		reportePesca.setPiscina18(listaIncSemAnt.get(17));
		reportePesca.setPiscina19(listaIncSemAnt.get(18));
		reportePesca.setPiscina20(listaIncSemAnt.get(19));
		reportePesca.setPiscina21(listaIncSemAnt.get(20));
		reporteFinalPesca.add(reportePesca);
		reportePesca = new GmbReporteNivel();
		reportePesca.setMes("INC. ALIM. GR");
		reportePesca.setPiscina1(listaIncAlimentacion.get(0));
		reportePesca.setPiscina2(listaIncAlimentacion.get(1));
		reportePesca.setPiscina3(listaIncAlimentacion.get(2));
		reportePesca.setPiscina4(listaIncAlimentacion.get(3));
		reportePesca.setPiscina5(listaIncAlimentacion.get(4));
		reportePesca.setPiscina6(listaIncAlimentacion.get(5));
		reportePesca.setPiscina7(listaIncAlimentacion.get(6));
		reportePesca.setPiscina8(listaIncAlimentacion.get(7));
		reportePesca.setPiscina9(listaIncAlimentacion.get(8));
		reportePesca.setPiscina10(listaIncAlimentacion.get(9));
		reportePesca.setPiscina11(listaIncAlimentacion.get(10));
		reportePesca.setPiscina12(listaIncAlimentacion.get(11));
		reportePesca.setPiscina13(listaIncAlimentacion.get(12));
		reportePesca.setPiscina14(listaIncAlimentacion.get(13));
		reportePesca.setPiscina15(listaIncAlimentacion.get(14));
		reportePesca.setPiscina16(listaIncAlimentacion.get(15));
		reportePesca.setPiscina17(listaIncAlimentacion.get(16));
		reportePesca.setPiscina18(listaIncAlimentacion.get(17));
		reportePesca.setPiscina19(listaIncAlimentacion.get(18));
		reportePesca.setPiscina20(listaIncAlimentacion.get(19));
		reportePesca.setPiscina21(listaIncAlimentacion.get(20));
		reporteFinalPesca.add(reportePesca);
		reportePesca = new GmbReporteNivel();
		reportePesca.setMes("FECHA SIEMBRA");
		reportePesca.setPiscina1(listaFechaSiembra.get(0));
		reportePesca.setPiscina2(listaFechaSiembra.get(1));
		reportePesca.setPiscina3(listaFechaSiembra.get(2));
		reportePesca.setPiscina4(listaFechaSiembra.get(3));
		reportePesca.setPiscina5(listaFechaSiembra.get(4));
		reportePesca.setPiscina6(listaFechaSiembra.get(5));
		reportePesca.setPiscina7(listaFechaSiembra.get(6));
		reportePesca.setPiscina8(listaFechaSiembra.get(7));
		reportePesca.setPiscina9(listaFechaSiembra.get(8));
		reportePesca.setPiscina10(listaFechaSiembra.get(9));
		reportePesca.setPiscina11(listaFechaSiembra.get(10));
		reportePesca.setPiscina12(listaFechaSiembra.get(11));
		reportePesca.setPiscina13(listaFechaSiembra.get(12));
		reportePesca.setPiscina14(listaFechaSiembra.get(13));
		reportePesca.setPiscina15(listaFechaSiembra.get(14));
		reportePesca.setPiscina16(listaFechaSiembra.get(15));
		reportePesca.setPiscina17(listaFechaSiembra.get(16));
		reportePesca.setPiscina18(listaFechaSiembra.get(17));
		reportePesca.setPiscina19(listaFechaSiembra.get(18));
		reportePesca.setPiscina20(listaFechaSiembra.get(19));
		reportePesca.setPiscina21(listaFechaSiembra.get(20));
		reporteFinalPesca.add(reportePesca);

		reportePesca = new GmbReporteNivel();
		reportePesca.setMes("INC. SIEM. GR");
		reportePesca.setPiscina1(listaIncSiembra.get(0));
		reportePesca.setPiscina2(listaIncSiembra.get(1));
		reportePesca.setPiscina3(listaIncSiembra.get(2));
		reportePesca.setPiscina4(listaIncSiembra.get(3));
		reportePesca.setPiscina5(listaIncSiembra.get(4));
		reportePesca.setPiscina6(listaIncSiembra.get(5));
		reportePesca.setPiscina7(listaIncSiembra.get(6));
		reportePesca.setPiscina8(listaIncSiembra.get(7));
		reportePesca.setPiscina9(listaIncSiembra.get(8));
		reportePesca.setPiscina10(listaIncSiembra.get(9));
		reportePesca.setPiscina11(listaIncSiembra.get(10));
		reportePesca.setPiscina12(listaIncSiembra.get(11));
		reportePesca.setPiscina13(listaIncSiembra.get(12));
		reportePesca.setPiscina14(listaIncSiembra.get(13));
		reportePesca.setPiscina15(listaIncSiembra.get(14));
		reportePesca.setPiscina16(listaIncSiembra.get(15));
		reportePesca.setPiscina17(listaIncSiembra.get(16));
		reportePesca.setPiscina18(listaIncSiembra.get(17));
		reportePesca.setPiscina19(listaIncSiembra.get(18));
		reportePesca.setPiscina20(listaIncSiembra.get(19));
		reportePesca.setPiscina21(listaIncSiembra.get(20));
		reporteFinalPesca.add(reportePesca);
		reportePesca = new GmbReporteNivel();
		reportePesca.setMes("DIAS SIEM.");
		reportePesca.setPiscina1(listaSemanasSiembra.get(0));
		reportePesca.setPiscina2(listaSemanasSiembra.get(1));
		reportePesca.setPiscina3(listaSemanasSiembra.get(2));
		reportePesca.setPiscina4(listaSemanasSiembra.get(3));
		reportePesca.setPiscina5(listaSemanasSiembra.get(4));
		reportePesca.setPiscina6(listaSemanasSiembra.get(5));
		reportePesca.setPiscina7(listaSemanasSiembra.get(6));
		reportePesca.setPiscina8(listaSemanasSiembra.get(7));
		reportePesca.setPiscina9(listaSemanasSiembra.get(8));
		reportePesca.setPiscina10(listaSemanasSiembra.get(9));
		reportePesca.setPiscina11(listaSemanasSiembra.get(10));
		reportePesca.setPiscina12(listaSemanasSiembra.get(11));
		reportePesca.setPiscina13(listaSemanasSiembra.get(12));
		reportePesca.setPiscina14(listaSemanasSiembra.get(13));
		reportePesca.setPiscina15(listaSemanasSiembra.get(14));
		reportePesca.setPiscina16(listaSemanasSiembra.get(15));
		reportePesca.setPiscina17(listaSemanasSiembra.get(16));
		reportePesca.setPiscina18(listaSemanasSiembra.get(17));
		reportePesca.setPiscina19(listaSemanasSiembra.get(18));
		reportePesca.setPiscina20(listaSemanasSiembra.get(19));
		reportePesca.setPiscina21(listaSemanasSiembra.get(20));
		reporteFinalPesca.add(reportePesca);
		reportePesca = new GmbReporteNivel();
		reportePesca.setMes("DIAS ALIM.");
		reportePesca.setPiscina1(listaDiasAlimentacion.get(0));
		reportePesca.setPiscina2(listaDiasAlimentacion.get(1));
		reportePesca.setPiscina3(listaDiasAlimentacion.get(2));
		reportePesca.setPiscina4(listaDiasAlimentacion.get(3));
		reportePesca.setPiscina5(listaDiasAlimentacion.get(4));
		reportePesca.setPiscina6(listaDiasAlimentacion.get(5));
		reportePesca.setPiscina7(listaDiasAlimentacion.get(6));
		reportePesca.setPiscina8(listaDiasAlimentacion.get(7));
		reportePesca.setPiscina9(listaDiasAlimentacion.get(8));
		reportePesca.setPiscina10(listaDiasAlimentacion.get(9));
		reportePesca.setPiscina11(listaDiasAlimentacion.get(10));
		reportePesca.setPiscina12(listaDiasAlimentacion.get(11));
		reportePesca.setPiscina13(listaDiasAlimentacion.get(12));
		reportePesca.setPiscina14(listaDiasAlimentacion.get(13));
		reportePesca.setPiscina15(listaDiasAlimentacion.get(14));
		reportePesca.setPiscina16(listaDiasAlimentacion.get(15));
		reportePesca.setPiscina17(listaDiasAlimentacion.get(16));
		reportePesca.setPiscina18(listaDiasAlimentacion.get(17));
		reportePesca.setPiscina19(listaDiasAlimentacion.get(18));
		reportePesca.setPiscina20(listaDiasAlimentacion.get(19));
		reportePesca.setPiscina21(listaDiasAlimentacion.get(20));
		reporteFinalPesca.add(reportePesca);
		// reportePesca = new GmbReporteNivel();
	}

	public void llenarTable() throws ParseException {
		Double valorInicial = 0.0;
		Double valorFinal = 0.0;
		Integer contador = 0;
		Double valorCalculoAlimentacion = 0.0;
		Double valorCalculoSiembra = 0.0;
		String fecha = null;
		fechaSiembraInicio = null;
		String diasSiembra = "0";
		Integer valorDiasAlimentacion = 0;
		Date fechaPesoAnt = new Date();
		siembra = siembraServicio.getSiembraxId(objnivelOperacion);
		listaOdd = servicioOperacionDiaria.getOddxFechaDesde(siembra.getFechaSiembra(),
				siembra.getNivelOperacion().getPiscina());
		listaRs = new ArrayList<PlantillaReporteSiembra>();
		for (GmbOperacionDiariaDatos odd : listaOdd) {
			PlantillaReporteSiembra plantillaReporteSiembra = new PlantillaReporteSiembra();
			// ********************INI LOGICA PARA VARIACION SEMANA
			// ANTERIOR******************
			if(odd.getPeso()==null)
			{
				odd.setPeso(0.0);
			}
			if (contador == 0) {
				fechaSiembraInicio = odd.getFecha();
				valorFinal = odd.getPeso();
				fechaPesoAnt = odd.getFecha();
				valorCalculoSiembra = odd.getPeso();
			} else {
				valorInicial = ((odd.getPeso() - valorFinal)
						/ ((odd.getFecha().getTime() - fechaPesoAnt.getTime()) / 86400000)) * 7;
				plantillaReporteSiembra.setVariacionSemanal(valorInicial);
				if (valorInicial > 0.0) {
					valorFinal = odd.getPeso();
					fechaPesoAnt = odd.getFecha();
					// valorCalculoAlimentacion = valorFinal;
				}
			}
			// ***********************************************************************************
			// *******************INI LOGICA PARA CRECIMIENTO ALIMENTACION*****************
			if (odd.getFecha().equals(siembra.getFehcaAlimentacion())) {
				valorCalculoAlimentacion = valorFinal;
			}
			if (siembra.getFehcaAlimentacion() != null) {
				if (odd.getFecha().after(siembra.getFehcaAlimentacion())) {
					Long diasParcial = ((odd.getFecha().getTime() - siembra.getFehcaAlimentacion().getTime())
							/ 86400000);
					Integer dias = diasParcial.intValue();
					valorDiasAlimentacion = dias;
					diasAlimentacion = valorDiasAlimentacion.toString();

					if (dias > 0) {
						double diasdiv7 = (dias / 7.00);
						Double valor = (odd.getPeso() - valorCalculoAlimentacion) / (diasdiv7);
						plantillaReporteSiembra.setVariacionAlimentacion(Double.parseDouble(valor.toString()));
					}
				}
			} else {
				plantillaReporteSiembra.setVariacionAlimentacion(0.0);
				diasAlimentacion = "0";
			}
			// ***********************************************************************************
			// ***********************************INI LOGICA SIEMBRA**********************
			if (contador > 0) {
				Double diasdiv7 = (contador / 7.0);
				Double valor_ = (odd.getPeso() - valorCalculoSiembra) / diasdiv7;
				plantillaReporteSiembra.setVariacionSiembra(valor_);
				Long diasSiembraIteracion = (odd.getFecha().getTime() - fechaSiembraInicio.getTime()) / 86400000;
				diasSiembra = diasSiembraIteracion.toString();
			}
			// **********************************************************************************
			plantillaReporteSiembra.setOperacionDiariaDatos(odd);
			fecha = odd.getFecha().toString().substring(0, 10);
			fecha = getDay(fecha.trim());
			plantillaReporteSiembra.setFecha(fecha);
			plantillaReporteSiembra.setDiasDesdeAlimentaccion(diasAlimentacion);
			plantillaReporteSiembra.setDiasDesdeSiembra(Integer.parseInt(diasSiembra.toString()));
			contador++;
			listaRs.add(plantillaReporteSiembra);
			diasAlimentacion = null;
		}
	}

	// GETTER AND SETTER
	public List<SelectItem> getListaObjNivelOperacion() {
		return listaObjNivelOperacion;
	}

	public void setListaObjNivelOperacion(List<SelectItem> listaObjNivelOperacion) {
		this.listaObjNivelOperacion = listaObjNivelOperacion;
	}

	public Integer getObjnivelOperacion() {
		return objnivelOperacion;
	}

	public void setObjnivelOperacion(Integer objnivelOperacion) {
		this.objnivelOperacion = objnivelOperacion;
	}

	public List<GmbOperacionDiariaDatos> getListaOdd() {
		return listaOdd;
	}

	public void setListaOdd(List<GmbOperacionDiariaDatos> listaOdd) {
		this.listaOdd = listaOdd;
	}

	public List<PlantillaReporteSiembra> getListaRs() {
		return listaRs;
	}

	public void setListaRs(List<PlantillaReporteSiembra> listaRs) {
		this.listaRs = listaRs;
	}

	public GmbSiembra getSiembra() {
		return siembra;
	}

	public void setSiembra(GmbSiembra siembra) {
		this.siembra = siembra;
	}

	public List<String> getPiscinas() {
		return piscinas;
	}

	public void setPiscinas(List<String> piscinas) {
		this.piscinas = piscinas;
	}

	public List<String> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(List<String> indicadores) {
		this.indicadores = indicadores;
	}

	public List<GmbReporteNivel> getReporteFinalPesca() {
		return reporteFinalPesca;
	}

	public void setReporteFinalPesca(List<GmbReporteNivel> reporteFinalPesca) {
		this.reporteFinalPesca = reporteFinalPesca;
	}

	// CLASE NUEVA PARA REPORTE GENERAL
	static public class ColumnModel implements Serializable {

		private String header;
		private String property;

		public ColumnModel(String header, String property) {
			this.header = header;
			this.property = property;
		}

		public String getHeader() {
			return header;
		}

		public String getProperty() {
			return property;
		}
	}

	public String getDay(String fecha) throws ParseException {
		String diaSemana = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha1 = formatter.parse(fecha);
		Calendar c = Calendar.getInstance();
		c.setTime(fecha1);

		int dia = 0;
		dia = c.get(Calendar.DAY_OF_WEEK);
		if (dia == 1) {
			diaSemana = "DOM.";
		}
		if (dia == 2) {
			diaSemana = "LUN.";
		}
		if (dia == 3) {
			diaSemana = "MAR.";
		}
		if (dia == 4) {
			diaSemana = "MIE.";
		}
		if (dia == 5) {
			diaSemana = "JUE.";
		}
		if (dia == 6) {
			diaSemana = "VIE.";

		}
		if (dia == 7) {
			diaSemana = "SAB.";

		}
		return fecha + "  " + diaSemana;
	}

}
