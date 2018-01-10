package com.gambalit.mb;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bmb.servicio.IGmbReportePatrolServicio;
import com.gambalit.properties.Propiedades;
import com.gambalit.utils.Mail;
import com.gmb.modelo.GmbReportPatrol;

@ManagedBean(name = "fileUploadView")
@ViewScoped
public class FileUploadView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<GmbReportPatrol> listaPatrulla = new ArrayList<GmbReportPatrol>();
	@EJB
	private IGmbReportePatrolServicio reportePatrolServicio;

	String filePath = "C:/DESCARGAS2/";
	String nombre = null;
	String extencion = null;
	String fechaActual = null;
	String horaActual = null;
	private Date fechaIni;
	private Date fechaFin;
	List<GmbReportPatrol> patrullas;

	@PostConstruct
	public void init() {
		llenarTabla();
	}

	public void llenarTabla() {
		patrullas = reportePatrolServicio.consultarReportePatrulla();

	}

	public void buscar() {
		patrullas = reportePatrolServicio.getReportePatrullaxFechas(fechaIni, fechaFin);

	}

	public void actualizar() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		String path = context.getExternalContext().getRequestContextPath();
		FacesContext.getCurrentInstance().getExternalContext().redirect(path + Propiedades.valor_pagina_patrullas);
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void uploadPhoto(FileUploadEvent e) throws IOException, ParseException {

		UploadedFile uploadedPhoto = e.getFile();

		// produccion String filePath="/descargas/";

		String filename = null;
		byte[] bytes = null;
		Date fecha = new Date();
		SimpleDateFormat formatoDia = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatoHora = new SimpleDateFormat("hhmmss");
		fechaActual = formatoDia.format(fecha);
		horaActual = formatoHora.format(fecha);
		Integer contador = 0;
		if (null != uploadedPhoto) {
			bytes = uploadedPhoto.getContents();
			filename = FilenameUtils.getName(uploadedPhoto.getFileName());

			StringTokenizer st = new StringTokenizer(filename, ".");
			while (st.hasMoreTokens()) {
				nombre = st.nextToken();
				extencion = st.nextToken();
			}
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(filePath + nombre + fechaActual + horaActual + "." + extencion)));
			stream.write(bytes);
			stream.close();
		}

		FacesContext.getCurrentInstance().addMessage("messages",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Su archivo (Nombre: " + uploadedPhoto.getFileName()
						+ ", Tamaño: " + uploadedPhoto.getSize() + ")  ah sido cargado", ""));
		extractFile(filePath + filename);
	}

	@SuppressWarnings("unlikely-arg-type")
	public void extractFile(String fileName) throws IOException, ParseException {
		// FileInputStream file = new FileInputStream(new File("D:\\patrulla.xlsx"));
		FileInputStream file = new FileInputStream(
				new File(filePath + nombre + fechaActual + horaActual + "." + extencion));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		Row row;
		Integer contadorRow = 0;
		Integer contadorCell = 1;
		String valorCelda1 = "";
		String valorCelda2 = "";
		Date fechaInsert = new Date();
		String fechaDummy = null;
		Integer contador = 0;
		Integer contadorInsert = 1;
		try {
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				Cell celda;
				GmbReportPatrol reportPatrol = new GmbReportPatrol();
				while (cellIterator.hasNext()) {
					celda = cellIterator.next();
					String valor = "";
					Double valorDouble = 0.0;
					if (contadorRow >= 2) {
						if (contadorCell == 1) {
							String valor1 = "";
							if (celda.getStringCellValue() != "") {
								valor1 = celda.getStringCellValue();
								if (valor1.equals("")) {
									valor1 = valorCelda1;
								} else {
									valorCelda1 = valor1;
								}
							}
							reportPatrol.setRuta(valor1);
							contadorCell++;
						} else if (contadorCell == 2) {
							String valor2 = "";
							String valorFecha1 = null;
							String valorFecha2 = null;
							if (celda.getStringCellValue() == "Tiempo planeado"
									|| celda.getStringCellValue().equals("Tiempo planeado")) {
								valor2 = celda.getStringCellValue();
							} else if (celda.getStringCellValue() != "") {
								valor2 = celda.getStringCellValue();
								if (valor2.equals("")) {
									valor2 = valorCelda2;
								} else {
									valorCelda2 = valor2;
								}
								StringTokenizer st = new StringTokenizer(valor2, "para");
								while (st.hasMoreTokens()) {
									valorFecha1 = formateadorFecha(st.nextToken());
									valorFecha2 = formateadorFecha(st.nextToken());
								}
								valor2 = valorFecha1 + " para " + valorFecha2;
							}
							reportPatrol.setTiempoPlaneado(valor2);
							contadorCell++;
						} else if (contadorCell == 3) {
							reportPatrol.setGuardia(celda.getStringCellValue());
							contadorCell++;
						} else if (contadorCell == 4) {
							reportPatrol.setPuntoChequeo(celda.getStringCellValue());
							contadorCell++;
						} else if (contadorCell == 5) {
							String valor1 = "";
							if (celda.getStringCellValue() != null) {
								valor1 = (celda.getStringCellValue());
							}
							reportPatrol.setDevice_number(valor1);
							contadorCell++;
						} else if (contadorCell == 6) {
							String valorFecha = null;
							if (!celda.getStringCellValue().trim().equals("Faltante")
									|| celda.getStringCellValue().trim() != "Faltante") {
								// SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

								/*
								 * if (v==18) { valorDia="0"+valorFecha.substring(0,1);
								 * valorMes=valorFecha.substring(3,4) } else {
								 * valorDia=valorFecha.substring(0,2); }
								 */
								if (celda.getStringCellValue().equals("Tiempo real de arrivo")) {
									valorFecha = celda.getStringCellValue();
								} else {
									valorFecha = formateadorFecha(celda.getStringCellValue());
								}
								contador++;
								if (valorFecha.length() > 9) {
									StringTokenizer st = new StringTokenizer(valorFecha, " ");
									String valor1 = null;
									String valor2 = null;
									while (st.hasMoreElements()) {
										valor1 = st.nextToken();
										valor2 = st.nextToken();
									}
									valorFecha = valor1 + " " + valor2;
									SimpleDateFormat fdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

									fechaInsert = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(valorFecha);
									String fechaString = fdf.format(fechaInsert);
									Date miFecha = fdf.parse(fechaString);

									reportPatrol.setTiempoRealArrivo(miFecha);
								} else {
									reportPatrol.setTiempoRealArrivo(null);
								}

							}
							
							contadorCell++;
						} else if (contadorCell == 7) {
							reportPatrol.setEvento(celda.getStringCellValue());
							contadorCell = 1;
						}

					}
					/*
					 * switch (celda.getCellType()) { case Cell.CELL_TYPE_NUMERIC: if
					 * (DateUtil.isCellDateFormatted(celda)) {
					 * System.out.println(celda.getDateCellValue()); } else {
					 * System.out.println(celda.getNumericCellValue()); } break; case
					 * Cell.CELL_TYPE_STRING: System.out.println(celda.getStringCellValue());
					 * //if(celda.getStringCellValue) break; case Cell.CELL_TYPE_BOOLEAN:
					 * System.out.println(celda.getBooleanCellValue()); break; }
					 */

				} 
				// if (reportPatrol.getTiempoRealArrivo() != null &&
				// reportPatrol.getPuntoChequeo() !=null) {
				if (contadorInsert > 2) {
					if (reportPatrol.getTiempoRealArrivo() != null
							&& reportPatrol.getTiempoRealArrivo().equals("Faltante")) {
						reportPatrol.setTiempoRealArrivo(null);
					}
					listaPatrulla.add(reportPatrol);
					// System.out.println(reportPatrol.getTiempoRealArrivo());
				}
				// }
				contadorInsert++;
				reportPatrol = null;
				contadorRow++;
			}
			reportePatrolServicio.insertMasivo(listaPatrulla);
			workbook.close();
		} catch (Exception e) {

			// System.out.println(" sdfsd" + contador);
		}
	}

	public String formateadorFecha(String valorFecha) {
		// String valorFecha = celda.getStringCellValue();
		Integer v = valorFecha.length();
		String valorDia = null;
		String valorMes = null;
		String valorAñoDummy = null;
		String valorAño = null;
		String valorHora = null;
		if (!valorFecha.equals("Faltante") || valorFecha.equals("") || valorFecha.equals(null)) {
			StringTokenizer st = new StringTokenizer(valorFecha, "/");
			while (st.hasMoreTokens()) {
				valorDia = st.nextToken();
				valorMes = st.nextToken();
				valorAñoDummy = st.nextToken();

			}
			StringTokenizer st2 = new StringTokenizer(valorAñoDummy, " ");
			while (st2.hasMoreTokens()) {
				valorAño = st2.nextToken();
				valorHora = st2.nextToken();
			}
			valorDia = valorDia.trim();
			if (valorDia.length() == 1) {
				valorDia = "0" + valorDia;
			}
			valorFecha = valorAño + "/" + valorMes + "/" + valorDia + "  " + valorHora;
		}
		return valorFecha;
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

	public void enviarMail() {
		Mail mail = new Mail();
		mail.sendEmail();
	}

	public List<GmbReportPatrol> getListaPatrulla() {
		return listaPatrulla;
	}

	public void setListaPatrulla(List<GmbReportPatrol> listaPatrulla) {
		this.listaPatrulla = listaPatrulla;
	}

	public List<GmbReportPatrol> getPatrullas() {
		return patrullas;
	}

	public void setPatrullas(List<GmbReportPatrol> patrullas) {
		this.patrullas = patrullas;
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

}
