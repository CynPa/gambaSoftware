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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import com.bmb.servicio.IGmbOperacionDiariaDatosServicio;
import com.gmb.modelo.GmbReporteValores;
import com.gmb.modelo.PlantillaReporte2;

@ManagedBean(name = "reporte_valor")
@ViewScoped
public class ReporteValores implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;

	private List<GmbReporteValores> listaValor;
	GmbReporteValores reporteValores = new GmbReporteValores();
	private List<GmbReporteValores> listaValor2;

	public List<GmbReporteValores> getListaValor() {
		return listaValor;
	}

	public void setListaValor(List<GmbReporteValores> listaValor) {
		this.listaValor = listaValor;
	}

	@EJB
	private IGmbOperacionDiariaDatosServicio datos;

	@PostConstruct
	public void init() {
		try {
			crearReporte();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void crearReporte() throws ParseException {
		List<Object[]> lista = datos.getReporte2();
		List<String> listaFecha = datos.getFechasUltimas();
		List<String> listaFechaOrdena = new ArrayList<String>();
		for (int i = 21; i <= listaFecha.size(); i--) {
			if (i < 0) {
				break;
			}
			listaFechaOrdena.add(listaFecha.get(i));
			// System.out.println("ITERACION " + i);
		}
		listaValor = new ArrayList<GmbReporteValores>();
		listaValor2 = new ArrayList<GmbReporteValores>();
		for (String fecha : listaFechaOrdena) {
			List<PlantillaReporte2> listaPlantilla = new ArrayList<PlantillaReporte2>();
			for (Object[] objects2 : lista) {
				if (fecha.equals(objects2[0].toString())) {
					PlantillaReporte2 plantilla = new PlantillaReporte2();
					String diaSemana = getDay(fecha);
					plantilla.setFecha(diaSemana);
					plantilla.setPiscina(objects2[1].toString());
					if (objects2[2] != null) {
						plantilla.setDuro(objects2[2].toString());
					} else {
						plantilla.setDuro(null);
					}
					if (objects2[3] != null) {
						plantilla.setFlacido(objects2[3].toString());
					} else {
						plantilla.setFlacido(null);
					}
					if (objects2[4] != null) {
						plantilla.setMudado(objects2[4].toString());
					} else {
						plantilla.setMudado(null);
					}
					if (objects2[5] != null) {
						plantilla.setMortFresco(objects2[5].toString());
					} else {
						plantilla.setMortFresco(null);
					}
					if (objects2[6] != null) {
						plantilla.setMortRojo(objects2[6].toString());
					} else {
						plantilla.setMortRojo(null);
					}
					if (objects2[7] != null) {
						plantilla.setMorEnfermo(objects2[7].toString());
					} else {
						plantilla.setMorEnfermo(null);
					}

					listaPlantilla.add(plantilla);
				}
			}
			recordRegistros(listaPlantilla);
			recordRegistrosMortandad(listaPlantilla);
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
		// System.out.println(fecha+" "+fecha1+" "+dia);
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

	public void recordRegistros(List<PlantillaReporte2> plantilla) {
		Boolean banderaDuro = false;
		Boolean banderaFlacido = true;
		Boolean banderaMudado = true;

		for (int i = 1; i <= 3; i++) {
			reporteValores = new GmbReporteValores();
			if (banderaDuro == false) {
				for (PlantillaReporte2 obj1 : plantilla) {
					reporteValores.setFecha(obj1.getFecha());
					reporteValores.setValor("DURO");
					if (obj1.getPiscina().equals("1")) {
						reporteValores.setPiscina1(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("2")) {
						reporteValores.setPiscina2(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("3")) {
						reporteValores.setPiscina3(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("4")) {
						reporteValores.setPiscina4(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("5")) {
						reporteValores.setPiscina5(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("6")) {
						reporteValores.setPiscina6(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("7")) {
						reporteValores.setPiscina7(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("8")) {
						reporteValores.setPiscina8(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("9")) {
						reporteValores.setPiscina9(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("10")) {
						reporteValores.setPiscina10(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("11")) {
						reporteValores.setPiscina11(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("12")) {
						reporteValores.setPiscina12(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("13")) {
						reporteValores.setPiscina13(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("14")) {
						reporteValores.setPiscina14(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("15")) {
						reporteValores.setPiscina15(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("16")) {
						reporteValores.setPiscina16(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("17")) {
						reporteValores.setPiscina17(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("18")) {
						reporteValores.setPiscina18(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("19")) {
						reporteValores.setPiscina19(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("20")) {
						reporteValores.setPiscina20(obj1.getDuro());
					}
					if (obj1.getPiscina().equals("PC01")) {
						reporteValores.setC1(obj1.getDuro());
					}
					reporteValores.setColor("yellow");
				}
				banderaDuro = true;
				banderaFlacido = false;
				banderaMudado = true;
				listaValor.add(reporteValores);
			}
			///// --------------------------FLACIDO-------------------------
			if (banderaFlacido == false) {
				reporteValores = new GmbReporteValores();
				for (PlantillaReporte2 obj1 : plantilla) {

					reporteValores.setFecha(obj1.getFecha());
					reporteValores.setValor("FLACIDO");
					if (obj1.getPiscina().equals("1")) {
						reporteValores.setPiscina1(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("2")) {
						reporteValores.setPiscina2(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("3")) {
						reporteValores.setPiscina3(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("4")) {
						reporteValores.setPiscina4(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("5")) {
						reporteValores.setPiscina5(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("6")) {
						reporteValores.setPiscina6(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("7")) {
						reporteValores.setPiscina7(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("8")) {
						reporteValores.setPiscina8(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("9")) {
						reporteValores.setPiscina9(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("10")) {
						reporteValores.setPiscina10(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("11")) {
						reporteValores.setPiscina11(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("12")) {
						reporteValores.setPiscina12(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("13")) {
						reporteValores.setPiscina13(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("14")) {
						reporteValores.setPiscina14(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("15")) {
						reporteValores.setPiscina15(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("16")) {
						reporteValores.setPiscina16(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("17")) {
						reporteValores.setPiscina17(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("18")) {
						reporteValores.setPiscina18(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("19")) {
						reporteValores.setPiscina19(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("20")) {
						reporteValores.setPiscina20(obj1.getFlacido());
					}
					if (obj1.getPiscina().equals("PC01")) {
						reporteValores.setC1(obj1.getFlacido());
					}
					reporteValores.setColor("white");
				}
				banderaDuro = true;
				banderaFlacido = true;
				banderaMudado = false;
				listaValor.add(reporteValores);
			}
			/// ---------------------------MUDADO
			if (banderaMudado == false) {

				reporteValores = new GmbReporteValores();
				for (PlantillaReporte2 obj1 : plantilla) {
					reporteValores.setFecha(obj1.getFecha());
					reporteValores.setValor("MUDADO");
					if (obj1.getPiscina().equals("1")) {
						reporteValores.setPiscina1(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("2")) {
						reporteValores.setPiscina2(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("3")) {
						reporteValores.setPiscina3(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("4")) {
						reporteValores.setPiscina4(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("5")) {
						reporteValores.setPiscina5(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("6")) {
						reporteValores.setPiscina6(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("7")) {
						reporteValores.setPiscina7(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("8")) {
						reporteValores.setPiscina8(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("9")) {
						reporteValores.setPiscina9(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("10")) {
						reporteValores.setPiscina10(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("11")) {
						reporteValores.setPiscina11(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("12")) {
						reporteValores.setPiscina12(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("13")) {
						reporteValores.setPiscina13(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("14")) {
						reporteValores.setPiscina14(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("15")) {
						reporteValores.setPiscina15(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("16")) {
						reporteValores.setPiscina16(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("17")) {
						reporteValores.setPiscina17(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("18")) {
						reporteValores.setPiscina18(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("19")) {
						reporteValores.setPiscina19(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("20")) {
						reporteValores.setPiscina20(obj1.getMudado());
					}
					if (obj1.getPiscina().equals("PC01")) {
						reporteValores.setC1(obj1.getMudado());
					}
					reporteValores.setColor("white");
				}
				banderaFlacido = true;
				banderaMudado = true;
				banderaDuro = true;
				listaValor.add(reporteValores);
			}
		}

	}

	public void recordRegistrosMortandad(List<PlantillaReporte2> plantilla) {
		Boolean banderaFresco = false;
		Boolean banderaRojo = true;
		Boolean banderaEnfermo = true;
		Integer contador = 1;
		for (int i = 1; i <= 3; i++) {
			if (banderaFresco == false) {
				for (PlantillaReporte2 obj1 : plantilla) {

					reporteValores.setFecha(obj1.getFecha());
					reporteValores.setValor("MORT. FRESCO");
					if (obj1.getPiscina().equals("1")) {
						reporteValores.setPiscina1(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("2")) {
						reporteValores.setPiscina2(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("3")) {
						reporteValores.setPiscina3(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("4")) {
						reporteValores.setPiscina4(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("5")) {
						reporteValores.setPiscina5(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("6")) {
						reporteValores.setPiscina6(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("7")) {
						reporteValores.setPiscina7(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("8")) {
						reporteValores.setPiscina8(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("9")) {
						reporteValores.setPiscina9(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("10")) {
						reporteValores.setPiscina10(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("11")) {
						reporteValores.setPiscina11(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("12")) {
						reporteValores.setPiscina12(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("13")) {
						reporteValores.setPiscina13(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("14")) {
						reporteValores.setPiscina14(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("15")) {
						reporteValores.setPiscina15(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("16")) {
						reporteValores.setPiscina16(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("17")) {
						reporteValores.setPiscina17(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("18")) {
						reporteValores.setPiscina18(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("19")) {
						reporteValores.setPiscina19(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("20")) {
						reporteValores.setPiscina20(obj1.getMortFresco());
					}
					if (obj1.getPiscina().equals("CANAL")) {
						reporteValores.setC1(obj1.getMortFresco());
					}
					reporteValores.setColor("yellow");
				}
				banderaFresco = true;
				banderaRojo = false;
				banderaEnfermo = true;
				listaValor2.add(reporteValores);
			}
			///// --------------------------FLACIDO-------------------------
			if (banderaRojo == false) {
				reporteValores = new GmbReporteValores();
				for (PlantillaReporte2 obj1 : plantilla) {

					reporteValores.setFecha(obj1.getFecha());
					reporteValores.setValor("MORT. ROJO");
					if (obj1.getPiscina().equals("1")) {
						reporteValores.setPiscina1(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("2")) {
						reporteValores.setPiscina2(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("3")) {
						reporteValores.setPiscina3(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("4")) {
						reporteValores.setPiscina4(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("5")) {
						reporteValores.setPiscina5(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("6")) {
						reporteValores.setPiscina6(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("7")) {
						reporteValores.setPiscina7(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("8")) {
						reporteValores.setPiscina8(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("9")) {
						reporteValores.setPiscina9(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("10")) {
						reporteValores.setPiscina10(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("11")) {
						reporteValores.setPiscina11(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("12")) {
						reporteValores.setPiscina12(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("13")) {
						reporteValores.setPiscina13(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("14")) {
						reporteValores.setPiscina14(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("15")) {
						reporteValores.setPiscina15(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("16")) {
						reporteValores.setPiscina16(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("17")) {
						reporteValores.setPiscina17(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("18")) {
						reporteValores.setPiscina18(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("19")) {
						reporteValores.setPiscina19(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("20")) {
						reporteValores.setPiscina20(obj1.getMortRojo());
					}
					if (obj1.getPiscina().equals("CANAL")) {
						reporteValores.setC1(obj1.getMortRojo());
					}
					reporteValores.setColor("white");
				}
				banderaFresco = true;
				banderaRojo = true;
				banderaEnfermo = false;
				listaValor2.add(reporteValores);
			}

		}
		if (banderaEnfermo == false) {

			reporteValores = new GmbReporteValores();
			for (PlantillaReporte2 obj1 : plantilla) {
				reporteValores.setFecha(obj1.getFecha());
				reporteValores.setValor("ENFERMO");
				if (obj1.getPiscina().equals("1")) {
					reporteValores.setPiscina1(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("2")) {
					reporteValores.setPiscina2(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("3")) {
					reporteValores.setPiscina3(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("4")) {
					reporteValores.setPiscina4(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("5")) {
					reporteValores.setPiscina5(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("6")) {
					reporteValores.setPiscina6(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("7")) {
					reporteValores.setPiscina7(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("8")) {
					reporteValores.setPiscina8(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("9")) {
					reporteValores.setPiscina9(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("10")) {
					reporteValores.setPiscina10(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("11")) {
					reporteValores.setPiscina11(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("12")) {
					reporteValores.setPiscina12(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("13")) {
					reporteValores.setPiscina13(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("14")) {
					reporteValores.setPiscina14(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("15")) {
					reporteValores.setPiscina15(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("16")) {
					reporteValores.setPiscina16(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("17")) {
					reporteValores.setPiscina17(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("18")) {
					reporteValores.setPiscina18(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("19")) {
					reporteValores.setPiscina19(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("20")) {
					reporteValores.setPiscina20(obj1.getMorEnfermo());
				}
				if (obj1.getPiscina().equals("CANAL")) {
					reporteValores.setC1(obj1.getMorEnfermo());
				}
				reporteValores.setColor("white");
			}
			banderaFresco = true;
			banderaRojo = true;
			banderaEnfermo = true;
			listaValor2.add(reporteValores);
		}

	}

	public List<GmbReporteValores> getListaValor2() {
		return listaValor2;
	}

	public void setListaValor2(List<GmbReporteValores> listaValor2) {
		this.listaValor2 = listaValor2;
	}




	
}

/*
 * public void crearReporte() { List<Object[]> lista = datos.getReporte2();
 * List<String> listaFecha = datos.getFechaReporte1();
 * 
 * listaValor = new ArrayList<GmbReporteValores>(); Integer i=1; for (String
 * fecha : listaFecha) { reporteValores = new GmbReporteValores();
 * 
 * for (Object[] objects2 : lista) { if (fecha.equals(objects2[0].toString())) {
 * reporteValores.setFecha(fecha); reporteValores.setValor("DURO");
 * if(objects2[2]!=null) { if(Integer.parseInt(objects2[2].toString()) <10)
 * objects2[2]="0"+objects2[2]; } if(objects2[3]!=null) {
 * if(Integer.parseInt(objects2[3].toString()) <10) objects2[3]="0"+objects2[3];
 * } if(objects2[4]!=null) { if(Integer.parseInt(objects2[4].toString()) <10)
 * objects2[4]="0"+objects2[4]; } if (objects2[1].toString().equals("1")) {
 * 
 * reporteValores.setPiscina1("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if (objects2[1].equals("2"))
 * {
 * reporteValores.setPiscina2("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if (objects2[1].equals("3"))
 * {
 * reporteValores.setPiscina3("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if (objects2[1].equals("4"))
 * {
 * reporteValores.setPiscina4("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if (objects2[1].equals("5"))
 * {
 * reporteValores.setPiscina5("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if (objects2[1].equals("6"))
 * {
 * reporteValores.setPiscina6("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if (objects2[1].equals("7"))
 * {
 * reporteValores.setPiscina7("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if (objects2[1].equals("8"))
 * {
 * reporteValores.setPiscina8("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if (objects2[1].equals("9"))
 * {
 * reporteValores.setPiscina9("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("10")) {
 * reporteValores.setPiscina10("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("11")) {
 * reporteValores.setPiscina11("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("12")) {
 * reporteValores.setPiscina12("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("13")) {
 * reporteValores.setPiscina13("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("14")) {
 * reporteValores.setPiscina14("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("15")) {
 * reporteValores.setPiscina15("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("16")) {
 * reporteValores.setPiscina16("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("17")) {
 * reporteValores.setPiscina17("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("18")) {
 * reporteValores.setPiscina18("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("19")) {
 * reporteValores.setPiscina19("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("20")) {
 * reporteValores.setPiscina20("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } if
 * (objects2[1].equals("C1")) {
 * reporteValores.setC1("-"+objects2[2].toString()+" \t \n-"+objects2[3].
 * toString()+"  \t \n-"+objects2[4].toString()); } i++; } }
 * listaValor.add(reporteValores); } }
 * 
 */
