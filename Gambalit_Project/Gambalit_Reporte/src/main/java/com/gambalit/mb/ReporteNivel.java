package com.gambalit.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import com.bmb.servicio.IGmbOperacionDiariaDatosServicio;
import com.gmb.modelo.GmbReporteNivel;

@ManagedBean(name = "reporte_nivel")
@ViewScoped
public class ReporteNivel implements Serializable {

	/** com.gmb.modelo.GmbSalinidad
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private List<GmbReporteNivel> listaNivel;
	GmbReporteNivel reporteNivel = new GmbReporteNivel();

	public List<GmbReporteNivel> getListaNivel() {
		return listaNivel; 
	}

	public void setListaNivel(List<GmbReporteNivel> listaNivel) {
		this.listaNivel = listaNivel;
	}

	@EJB
	private IGmbOperacionDiariaDatosServicio datos;

	@PostConstruct
	public void init() {
		crearReporte();
		getContexPath();
	}
	
	public void  getContexPath()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		String path = ctx.getExternalContext().getRequestContextPath();
		//System.out.println("PATH "+path);
	}

	public void crearReporte() {
		List<Object[]> lista = datos.getReporte1();
		List<String> listaFecha = datos.getFechaReporte1();

		listaNivel = new ArrayList<GmbReporteNivel>();

		for (String fecha : listaFecha) {
			reporteNivel = new GmbReporteNivel();

			for (Object[] objects2 : lista) {
				if (fecha.equals(objects2[0].toString())) {
					
				
					reporteNivel.setFecha(fecha);
					reporteNivel.setMes(objects2[1].toString());
					if (objects2[2] != null) {
						//piscina1 = objects2[2].toString();
						if(objects2[2].toString().equals("99999"))
						{
							reporteNivel.setPiscina1("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina1(objects2[2].toString());
						}
					}
					if (objects2[3] != null) {
						//piscina2 = objects2[3].toString();
						if(objects2[3].toString().equals("99999"))
						{
							reporteNivel.setPiscina2("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina2(objects2[3].toString());
						}
					}
					if (objects2[4] != null) {
						if(objects2[4].toString().equals("99999"))
						{
							reporteNivel.setPiscina3("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina3(objects2[4].toString());
						}
						//piscina3 = objects2[4].toString();
					}
					if (objects2[5] != null) {
						if(objects2[5].toString().equals("99999"))
						{
							reporteNivel.setPiscina4("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina4(objects2[5].toString());
						}
						//piscina4 = objects2[5].toString();
					}
					if (objects2[6] != null) {
						if(objects2[6].toString().equals("99999"))
						{
							reporteNivel.setPiscina5("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina5(objects2[6].toString());
					}//piscina5 = objects2[6].toString();
					}
					if (objects2[7] != null) {
						if(objects2[7].toString().equals("99999"))
						{
							reporteNivel.setPiscina6("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina6(objects2[7].toString());
						}
						//piscina6 = objects2[7].toString();
					}
					if (objects2[8] != null) {
						if(objects2[8].toString().equals("99999"))
						{
							reporteNivel.setPiscina7("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina7(objects2[8].toString());
						}
					}
					if (objects2[9] != null) {
						if(objects2[9].toString().equals("99999"))
						{
							reporteNivel.setPiscina8("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina8(objects2[9].toString());
					}
					}
					if (objects2[10] != null) {
						if(objects2[10].toString().equals("99999"))
						{
							reporteNivel.setPiscina9("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina9(objects2[10].toString());
						}
					}
					if (objects2[11] != null) {
						if(objects2[11].toString().equals("99999"))
						{
							reporteNivel.setPiscina10("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina10(objects2[11].toString());
						}
					}
					if (objects2[12] != null) {
						if(objects2[12].toString().equals("99999"))
						{
							reporteNivel.setPiscina11("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina11(objects2[12].toString());
						}
					}
					if (objects2[13] != null) {
						if(objects2[13].toString().equals("99999"))
						{
							reporteNivel.setPiscina12("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina12(objects2[13].toString());
						}
					}
					if (objects2[14] != null) {
						if(objects2[14].toString().equals("99999"))
						{
							reporteNivel.setPiscina13("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina13(objects2[14].toString());
						}
					}
					if (objects2[15] != null) {
						if(objects2[15].toString().equals("99999"))
						{
							reporteNivel.setPiscina14("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina14(objects2[15].toString());
						}
					}
					if (objects2[16] != null) {
						if(objects2[16].toString().equals("99999"))
						{
							reporteNivel.setPiscina15("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina15(objects2[16].toString());
						}
					}
					if (objects2[17] != null) {
						if(objects2[17].toString().equals("99999"))
						{
							reporteNivel.setPiscina16("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina16(objects2[17].toString());
						}
					}
					if (objects2[18] != null) {
						if(objects2[18].toString().equals("99999"))
						{
							reporteNivel.setPiscina17("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina17(objects2[18].toString());
						}
					}
					if (objects2[19] != null) {
						if(objects2[19].toString().equals("99999"))
						{
							reporteNivel.setPiscina18("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina18(objects2[19].toString());
						}
					}
					if (objects2[20] != null) {
						if(objects2[20].toString().equals("99999"))
						{
							reporteNivel.setPiscina19("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina19(objects2[20].toString());
						}
					}
					if (objects2[21] != null) {
						if(objects2[21].toString().equals("99999"))
						{
							reporteNivel.setPiscina20("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina20(objects2[21].toString());
						}
					}
					if (objects2[22] != null) {
						if(objects2[22].toString().equals("99999"))
						{
							reporteNivel.setPiscina21("NIVEL");
						}
						else
						{
						reporteNivel.setPiscina21(objects2[22].toString());
						}
					}
				}     
			}
		
			listaNivel.add(reporteNivel);
		             
             
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

}
