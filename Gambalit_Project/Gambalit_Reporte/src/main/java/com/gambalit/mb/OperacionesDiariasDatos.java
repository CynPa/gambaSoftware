package com.gambalit.mb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.context.RequestContext;

import com.bmb.servicio.IGmbOperacionDiariaDatosServicio;
import com.gambalit.properties.Propiedades;
import com.gmb.eao.IGmbNivelOperacionEAO;
import com.gmb.modelo.GmbNivelOperacion;
import com.gmb.modelo.GmbOperacionDiariaDatos;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
//import net.sf.jasperreports.export.SimpleExporterInput;
//import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
//import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
//import net.sf.jasperreports.view.JasperViewer;

@ManagedBean(name = "operacionesDiarias")
@ViewScoped
public class OperacionesDiariasDatos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer nivelDiario;
	private Integer duro;
	private Integer flacido;
	private Integer mudado;
	private Integer nivelOp; 
	private Double peso;
	private String objnivelOperacion;
	private List<SelectItem> listaObjNivelOperacion;
	private List<GmbOperacionDiariaDatos> listOperacionDiaria; 
	private GmbNivelOperacion obj1NivelOperacion = new GmbNivelOperacion();
	private GmbOperacionDiariaDatos operacionDiariaDatosSelect;
	private List<GmbOperacionDiariaDatos> operacionDiariaDatosFiltered;
	private Date fechaActual;
	private PDFOptions pdfOpt;
	private String valorNoSelected;
	private Date fecha;
	private Double mortRojo;
	private Double mortFresco;
	private Double enfermos;
	private Double salinidad;
	private Double temperatura;
	private Boolean bocache;

	@EJB
	private IGmbNivelOperacionEAO nivelOperacion;
	@EJB
	private IGmbOperacionDiariaDatosServicio opeDiariaDatos;

	@PostConstruct
	public void init() {

		llenarCombo();
		llenarLista();
		fechaActual = new Date();
		nivelDiario = 0;
		peso = null;
		duro = null;
		flacido = null;
		mudado = null;
		mortRojo = null;
		mortFresco = null;
		enfermos = null;
		salinidad=null;
		temperatura=null;
		pdfOpt = new PDFOptions();
		// pdfOpt.setFacetBgColor("#F88017");
		// pdfOpt.setFacetFontColor("#0000ff");
		pdfOpt.setFacetFontStyle("NORMAL");
		pdfOpt.setCellFontSize("10");
	}

	List<GmbNivelOperacion> nivelOperativo = new ArrayList<GmbNivelOperacion>();

	GmbOperacionDiariaDatos operacionDiariaDatos = new GmbOperacionDiariaDatos();
	List<GmbOperacionDiariaDatos> listaOperacionDiariaDatos = new ArrayList<GmbOperacionDiariaDatos>();

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
		nivelDiario = 0;
		peso = null;
		duro = null;
		flacido = null;
		mudado = null;
		mortRojo = null;
		mortFresco = null;
		enfermos = null;
		salinidad=null;
		temperatura=null;
        this.setPeso(null);
        this.setDuro(null);
        this.setFlacido(null);
        this.setMudado(null);
		listaOperacionDiariaDatos  = new ArrayList<GmbOperacionDiariaDatos>();
		//List<GmbOperacionDiariaDatos> listaOpDiariaDatos = opeDiariaDatos.getListaNivelOperacion();
		
		//for (GmbOperacionDiariaDatos gmbOperacionDiariaDatos : listaOpDiariaDatos) {
			
		List<Date> listaFechas = opeDiariaDatos.getFechasDistinct();
		
		for (Date fecha_ : listaFechas) {
			List<GmbOperacionDiariaDatos> odd=new ArrayList<GmbOperacionDiariaDatos>();

			odd=opeDiariaDatos.getOperacionDiariaDatosxFecha(fecha_);
			
			for (GmbOperacionDiariaDatos gmbOperacionDiariaDatos : odd) {
				operacionDiariaDatos = new GmbOperacionDiariaDatos();
				operacionDiariaDatos.setIdOperacionDiaria(gmbOperacionDiariaDatos.getIdOperacionDiaria());
				operacionDiariaDatos.setFecha(gmbOperacionDiariaDatos.getFecha());
				operacionDiariaDatos.setMes(gmbOperacionDiariaDatos.getMes());
				/*if(gmbOperacionDiariaDatos.getNivelDiario() ==0)
				{
					gmbOperacionDiariaDatos.setNivelDiario(0);
				}
				else
				if((gmbOperacionDiariaDatos.getNivelDiario()-gmbOperacionDiariaDatos.getNivelOperacion().getNivelOperacion())==0)
				{
		           gmbOperacionDiariaDatos.setNivelDiario(99999);			
				}
				else
					if(gmbOperacionDiariaDatos.getNivelDiario()!=gmbOperacionDiariaDatos.getNivelOperacion().getNivelOperacion() && gmbOperacionDiariaDatos.getNivelDiario() >0)
				{
					gmbOperacionDiariaDatos.setNivelDiario(gmbOperacionDiariaDatos.getNivelDiario()-gmbOperacionDiariaDatos.getNivelOperacion().getNivelOperacion());
				}
				*/
				operacionDiariaDatos.setNivelDiario(gmbOperacionDiariaDatos.getNivelDiario());
				operacionDiariaDatos.setDuro(gmbOperacionDiariaDatos.getDuro());
				operacionDiariaDatos.setFlacido(gmbOperacionDiariaDatos.getFlacido());
				operacionDiariaDatos.setMudado(gmbOperacionDiariaDatos.getMudado());
				operacionDiariaDatos.setNivelOperacion(gmbOperacionDiariaDatos.getNivelOperacion());
				operacionDiariaDatos.setPeso(gmbOperacionDiariaDatos.getPeso());
				operacionDiariaDatos.setMortFresco(gmbOperacionDiariaDatos.getMortFresco());
				operacionDiariaDatos.setMortRojo(gmbOperacionDiariaDatos.getMortRojo());
				operacionDiariaDatos.setEnfermos(gmbOperacionDiariaDatos.getEnfermos());
				operacionDiariaDatos.setSalinidad(gmbOperacionDiariaDatos.getSalinidad());
				operacionDiariaDatos.setTemperatura(gmbOperacionDiariaDatos.getTemperatura());
				operacionDiariaDatos.setBocache(gmbOperacionDiariaDatos.getBocache());
				listaOperacionDiariaDatos.add(operacionDiariaDatos);
			}
			
			
		}
	}

	public void llenarEtiquetaNivelOperacion() {
		obj1NivelOperacion = nivelOperacion.getNivelOperacionxId(Integer.parseInt(objnivelOperacion));
		objnivelOperacion = obj1NivelOperacion.getNivelOperacion().toString();
	}

	public void insertOperacionDiariaDatos() throws IOException {
		String pagina = "";
		GmbOperacionDiariaDatos op1 = new GmbOperacionDiariaDatos();
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String path = context.getExternalContext().getRequestContextPath();

			if(operacionDiariaDatosSelect.getDuro() != null || operacionDiariaDatosSelect.getFlacido()!= null  || operacionDiariaDatosSelect.getMudado()!=null)
			{
				
				
			}
			
			if (nivelOperacion == null) {
				errorMessage("Ingresar nivel operacion");
			} else if (fecha == null) {
				errorMessage("Ingresar la fecha");
			} else if (obj1NivelOperacion.getPiscina() == null) {
				errorMessage("Seleccione la piscina");
			}

			/*
			 * if (duro == null) { duro = 0; } if (flacido == null) { flacido = 0; } if
			 * (mudado == null) { mudado = 0; } if(mortFresco==null) { mortFresco=0.0; }
			 * if(mortRojo==null) { mortRojo=0.0;
			 * 
			 * } if(enfermos==null) { enfermos=0.0; } if(salinidad==null) { salinidad=0.0; }
			 * if(temperatura==null) { temperatura=0.0; }
			 */
			else if ((duro != null && flacido != null && mudado != null)
					&& ((duro + flacido + mudado) != 100 && (duro + flacido + mudado) > 1)) {
				// if ((duro + flacido + mudado) != 100 && (duro + flacido + mudado) > 1) {
				errorMessage("Los valores de duro, flacido y mudado deben subar 100% o estar en 0");
				pagina = "";
				// }
			} else {
				
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat formato2 = new SimpleDateFormat("MM");
				SimpleDateFormat formato3 = new SimpleDateFormat("yyyy");
				String fechaString = formato.format(fecha); // Convierte Date a String
				Integer fechaMes = Integer.parseInt(formato2.format(fecha));
				Integer fechaAno = Integer.parseInt(formato3.format(fecha));
				Date miFecha = formato.parse(fechaString); // convierte String a Date

				BigInteger contadorRepetido = getOperacionDiariaxFechaxPiscina(miFecha,
						obj1NivelOperacion.getPiscina().toString());
				Integer valorRepetido = contadorRepetido.intValue();
				if (valorRepetido > 0) {
					errorMessage("Ya se encuentra una piscina configurada para el " + fechaString
							+ " escoja modificar para cambiar sus valores.");
				} else
					
					if(mortFresco!=null && mortRojo==null && enfermos==null)
					{
					
					if (mortFresco != null) {
					if (mortRojo == null && enfermos == null) {
						errorMessage("Debe ingresar valores para mortRojo y enfermos, minimo valor 0");
					}else
						if(mortRojo != null && enfermos == null)
						{
							errorMessage("Debe ingresar valor para  enfermos, minimo valor 0");
						}
						else
							if(mortRojo == null && enfermos != null)
							{
								errorMessage("Debe ingresar valor para  mortRojo, minimo valor 0");
							}
							
				} else if (mortRojo != null) {
					if (mortFresco == null && enfermos == null) {
						errorMessage("Debe ingresar valores para mortFresco y enfermos, minimo valor 0");
					}else
						if(mortFresco != null && enfermos == null)
						{
							errorMessage("Debe ingresar valor para enfermos, minimo valor 0");
						}
						else
							if(mortFresco == null && enfermos != null)
							{
								errorMessage("Debe ingresar valor para mortFresco, minimo valor 0");
							}
				} else if (enfermos != null) {
					if (mortRojo == null && mortFresco == null) {
						errorMessage("Debe ingresar valores para mortRojo y mortFresco, minimo valor 0");
					}
					else
						if(mortRojo != null && mortFresco == null)
						{
							errorMessage("Debe ingresar valor para  mortFresco, minimo valor 0");
						}
						else
							if(mortRojo == null && mortFresco != null)
							{
								errorMessage("Debe ingresar valor para  mortRojo, minimo valor 0");
							}
				} }else {
					op1.setFecha(miFecha);
					op1.setNivelDiario(nivelDiario);
					op1.setMes(fechaMes);
					op1.setYear(fechaAno);
					op1.setEstado("A");
					op1.setDuro(duro);
					op1.setFlacido(flacido);
					op1.setMudado(mudado);
					op1.setNivelOperacion(obj1NivelOperacion);
					op1.setPeso(peso);
					op1.setMortFresco(mortFresco);
					op1.setMortRojo(mortRojo);
					op1.setEnfermos(enfermos);
					op1.setSalinidad(salinidad);
					op1.setTemperatura(temperatura);
					op1.setBocache(bocache);
					opeDiariaDatos.insertOperacionDiariaDatos(op1);
					//llenarLista();
                    nivelDiario=null;
                    duro=null;
                    flacido=null;
                    mudado=null;
                    obj1NivelOperacion=new GmbNivelOperacion();
                    peso=null;
                    this.setPeso(null);
                    this.setDuro(null);
                    this.setFlacido(null);
                    this.setMudado(null);
                    mortFresco=null;
                    mortRojo=null;
                    enfermos=null;
					addMessage("datos ingresados exitosamente");
					RequestContext.getCurrentInstance().execute("PF('dlgNewOperacion').hide()");
					llenarLista();
					//RequestContext.getCurrentInstance().execute("PF('dlgNewOperacion').hide()");
					FacesContext.getCurrentInstance().getExternalContext()
						.redirect(path + Propiedades.valor_pagina_operaciones);
					addMessage("datos ingresados exitosamente");
				}

				// pagina = Propiedades.valor_pagina_operaciones;

			}
		} catch (ParseException e) {
			errorMessage("error 1" + e.toString());
			e.printStackTrace();
		}
	}

	public void eliminarOperacionDiaria(GmbOperacionDiariaDatos  gmbOperacionDiariaDatos) {
		gmbOperacionDiariaDatos.setEstado("I");
		opeDiariaDatos.eliminarOperacionDiariaDatos(gmbOperacionDiariaDatos);
		addMessage("registro eliminado exitosamente");
		llenarLista();
	}

	public void validadarRegistroSeleccionado(ActionEvent event) {
		if (operacionDiariaDatosSelect == null) {
			valorNoSelected = "Seleccione un registro para Actualizar";
		}
	}

	public String modificarOperacionDiaria() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String path = context.getExternalContext().getRequestContextPath();
			Integer cantidad = 0;
			
			if ((operacionDiariaDatosSelect.getDuro() != null && operacionDiariaDatosSelect.getFlacido() != null
					&& operacionDiariaDatosSelect.getMudado() != null)) {
				cantidad = operacionDiariaDatosSelect.getDuro() + operacionDiariaDatosSelect.getFlacido()
						+ operacionDiariaDatosSelect.getMudado();

			}
			if(operacionDiariaDatosSelect.getDuro() != null || operacionDiariaDatosSelect.getFlacido()!= null  || operacionDiariaDatosSelect.getMudado()!=null)
			{
				if(operacionDiariaDatosSelect.getDuro() != null && operacionDiariaDatosSelect.getFlacido()==null && operacionDiariaDatosSelect.getMudado()==null)
				{
					cantidad=-1;
				}
				else
				if(operacionDiariaDatosSelect.getDuro() == null && operacionDiariaDatosSelect.getFlacido()!= null && operacionDiariaDatosSelect.getMudado()==null)
				{
					cantidad=-1;
				}
				else
				if(operacionDiariaDatosSelect.getDuro() == null && operacionDiariaDatosSelect.getFlacido()== null &&  operacionDiariaDatosSelect.getMudado()!=null)
				{
					cantidad=-1;
				}
				else
				if(operacionDiariaDatosSelect.getDuro() != null && operacionDiariaDatosSelect.getFlacido()!= null && operacionDiariaDatosSelect.getMudado()==null)
				{
					cantidad=-1;
				}
				else
				if(operacionDiariaDatosSelect.getDuro() == null && operacionDiariaDatosSelect.getFlacido()!= null && operacionDiariaDatosSelect.getMudado()!=null)
				{
					cantidad=-1;
				}
				else
				if(operacionDiariaDatosSelect.getDuro() != null && operacionDiariaDatosSelect.getFlacido()== null && operacionDiariaDatosSelect.getMudado()!=null)
				{
					cantidad=-1;
				}
				else
				if(operacionDiariaDatosSelect.getDuro() == null && operacionDiariaDatosSelect.getFlacido()!= null && operacionDiariaDatosSelect.getMudado()==null)
				{
					cantidad=-1;
				}
				else
				if(operacionDiariaDatosSelect.getDuro() == null && operacionDiariaDatosSelect.getFlacido()!= null && operacionDiariaDatosSelect.getMudado()==null)
				{
					cantidad=100;
				}
			}
			
			
			if ((operacionDiariaDatosSelect.getDuro() != null && operacionDiariaDatosSelect.getFlacido() != null
					&& operacionDiariaDatosSelect.getMudado() != null) && (cantidad != 100 && cantidad > 1)) {
				errorMessage("Los valores de duro, flacido y mudado deben sumar 100% o estar en 0");
			} 
			else 
			if(cantidad==100 || cantidad ==0){
				GmbOperacionDiariaDatos op1 = new GmbOperacionDiariaDatos();
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat formato2 = new SimpleDateFormat("MM");
				SimpleDateFormat formato3 = new SimpleDateFormat("yyyy");
				String fechaString = formato.format(operacionDiariaDatosSelect.getFecha()); // Convierte Date a String
				Integer fechaMes = Integer.parseInt(formato2.format(operacionDiariaDatosSelect.getFecha()));
				Integer fechaAno = Integer.parseInt(formato3.format(operacionDiariaDatosSelect.getFecha()));
				Date miFecha = formato.parse(fechaString); // convierte String a Date
				op1.setIdOperacionDiaria(operacionDiariaDatosSelect.getIdOperacionDiaria());
				op1.setFecha(miFecha);
				op1.setNivelDiario(operacionDiariaDatosSelect.getNivelDiario());
				op1.setMes(fechaMes);
				op1.setYear(fechaAno);
				op1.setDuro(operacionDiariaDatosSelect.getDuro());
				op1.setFlacido(operacionDiariaDatosSelect.getFlacido());
				op1.setMudado(operacionDiariaDatosSelect.getMudado());
				op1.setNivelOperacion(operacionDiariaDatosSelect.getNivelOperacion());
				op1.setPeso(operacionDiariaDatosSelect.getPeso());
				op1.setMortRojo(operacionDiariaDatosSelect.getMortRojo());
				op1.setMortFresco(operacionDiariaDatosSelect.getMortFresco());
				op1.setEnfermos(operacionDiariaDatosSelect.getEnfermos());
				op1.setTemperatura(operacionDiariaDatosSelect.getTemperatura());
				op1.setSalinidad(operacionDiariaDatosSelect.getSalinidad());
				op1.setBocache(operacionDiariaDatosSelect.getBocache());
				op1.setEstado("A");

				/*
				 * if (op1.getNivelDiario() == null || op1.getMudado() == null ||
				 * op1.getFlacido() == null || op1.getDuro() == null || op1.getMortFresco() ==
				 * null || op1.getMortRojo() == null || op1.getEnfermos() == null) {
				 * errorMessage("los valores no deben estar vacios"); } else {
				 */
				opeDiariaDatos.actualizarOperacionDiariaDatos(op1);
				RequestContext.getCurrentInstance().execute("PF('dlgActualizarOd').hide()");
				llenarLista();
				addMessage("Datos Modificados exitosamente");
				//FacesContext.getCurrentInstance().getExternalContext()
					//	.redirect(path + Propiedades.valor_pagina_operaciones);
				// }
			}
			else
			{
				errorMessage("La suma de los valores deben dar 100 0 ser nulos");
				//FacesContext.getCurrentInstance().getExternalContext()
				//.redirect(path + Propiedades.valor_pagina_operaciones);
			}
			
			return "/generales/operacion.jsf";
		} catch (Exception e) {
			errorMessage("error " + e.toString());
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * public void calcularValoresActualilzar() { Integer valor = 100;
	 * operacionDiariaDatosSelect .setMudado((valor -
	 * operacionDiariaDatosSelect.getDuro() -
	 * operacionDiariaDatosSelect.getFlacido())); }
	 */

	public void validadorValores(ActionEvent ev) {
		RequestContext context = RequestContext.getCurrentInstance();
		if (operacionDiariaDatosSelect == null) {

			context.execute("PF('dlgError').show();");
		} else {
			context.execute("PF('dlgActualizarOd').show();");

		}
	}

	public void calcularValores() {
		Integer valor = 100;
		mudado = valor - duro - flacido;
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		CellStyle style = wb.createCellStyle();
		style.setFillBackgroundColor(IndexedColors.BLUE_GREY.index);

		for (Row row : sheet) {
			for (Cell cell : row) {
				cell.setCellValue(cell.getStringCellValue().toUpperCase());
				cell.setCellStyle(style);
			}
		}
	}

	public BigInteger getOperacionDiariaxFechaxPiscina(Date fecha, String piscina) {
		return opeDiariaDatos.getOperacionDiariaxFechaxPiscina(fecha, piscina);
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void errorMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.setPageSize(PageSize.A4.rotate());
		pdf.setMargins(2, 2, 2, 2);
		pdf.open();
		// pdf.setPageSize(PageSize.A4.rotate());
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String logo = externalContext.getRealPath("") + "/images/logo-iloveimg-resized.png";

		pdf.add(Image.getInstance(logo));
	}

	
	
	public static void main(String[] args) {
		OperacionesDiariasDatos d=new OperacionesDiariasDatos();
		d.generarReporte();
	}

	public void generarReporte() {
		try {
            /* User home directory location */
            String userHomeDirectory = "C:/DESCARGAS";
            /* Output file location */
            String outputFile = userHomeDirectory + "\\JasperTableExample.pdf";

         

            /* Convert List to JRBeanCollectionDataSource */
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listaOperacionDiariaDatos);

            /* Map to hold Jasper report Parameters */
            Map<String, Object> parameters = new HashMap<String , Object>();
            parameters.put("EJEMPLO", itemsJRBean);

			/* Using compiled version(.jasper) of Jasper report to generate PDF */
			JasperPrint jasperPrint = JasperFillManager.fillReport("C:\\DESCARGAS\\test.jasper", parameters,
					new JREmptyDataSource());

            /* outputStream to create PDF */
            OutputStream outputStream = new FileOutputStream(new File(outputFile));
            /* Write content to PDF file */
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            System.out.println("File Generated");
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
	
	
	public String getValorNoSelected() {
		return valorNoSelected;
	}

	public void setValorNoSelected(String valorNoSelected) {
		this.valorNoSelected = valorNoSelected;
	}

	public PDFOptions getPdfOpt() {
		return pdfOpt;
	}

	public void setPdfOpt(PDFOptions pdfOpt) {
		this.pdfOpt = pdfOpt;
	}

	public List<GmbOperacionDiariaDatos> getOperacionDiariaDatosFiltered() {
		return operacionDiariaDatosFiltered;
	}

	public void setOperacionDiariaDatosFiltered(List<GmbOperacionDiariaDatos> operacionDiariaDatosFiltered) {
		this.operacionDiariaDatosFiltered = operacionDiariaDatosFiltered;
	}

	public List<GmbOperacionDiariaDatos> getListOperacionDiaria() {
		return listOperacionDiaria;
	}

	public void setListOperacionDiaria(List<GmbOperacionDiariaDatos> listOperacionDiaria) {
		this.listOperacionDiaria = listOperacionDiaria;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public GmbOperacionDiariaDatos getOperacionDiariaDatosSelect() {
		return operacionDiariaDatosSelect;
	}

	public void setOperacionDiariaDatosSelect(GmbOperacionDiariaDatos operacionDiariaDatosSelect) {
		this.operacionDiariaDatosSelect = operacionDiariaDatosSelect;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public GmbNivelOperacion getObj1NivelOperacion() {
		return obj1NivelOperacion;
	}

	public void setObj1NivelOperacion(GmbNivelOperacion obj1NivelOperacion) {
		this.obj1NivelOperacion = obj1NivelOperacion;
	}

	public List<SelectItem> getListaObjNivelOperacion() {
		return listaObjNivelOperacion;
	}

	public void setListaNivelOperacion(List<SelectItem> listaNivelObjOperacion) {
		this.listaObjNivelOperacion = listaNivelObjOperacion;
	}

	public String getObjnivelOperacion() {
		return objnivelOperacion;
	}

	public void setObjnivelOperacion(String objnivelOperacion) {
		this.objnivelOperacion = objnivelOperacion;
	}

	public Integer getNivelDiario() {
		return nivelDiario;
	}

	public void setNivelDiario(Integer nivelDiario) {
		this.nivelDiario = nivelDiario;
	}

	public Integer getDuro() {
		return duro;
	}

	public void setDuro(Integer duro) {
		this.duro = duro;
	}

	public Integer getFlacido() {
		return flacido;
	}

	public void setFlacido(Integer flacido) {
		this.flacido = flacido;
	}

	public Integer getMudado() {
		return mudado;
	}

	public void setMudado(Integer mudado) {
		this.mudado = mudado;
	}

	public List<GmbOperacionDiariaDatos> getListaOperacionDiariaDatos() {
		return listaOperacionDiariaDatos;
	}

	public void setListaOperacionDiariaDatos(List<GmbOperacionDiariaDatos> listaOperacionDiariaDatos) {
		this.listaOperacionDiariaDatos = listaOperacionDiariaDatos;
	}

	public Integer getNivelOp() {
		return nivelOp;
	}

	public void setNivelOp(Integer nivelOp) {
		this.nivelOp = nivelOp;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getMortRojo() {
		return mortRojo;
	}

	public void setMortRojo(Double mortRojo) {
		this.mortRojo = mortRojo;
	}

	public Double getMortFresco() {
		return mortFresco;
	}

	public void setMortFresco(Double mortFresco) {
		this.mortFresco = mortFresco;
	}

	public Double getEnfermos() {
		return enfermos;
	}

	public void setEnfermos(Double enfermos) {
		this.enfermos = enfermos;
	}

	public Double getSalinidad() {
		return salinidad;
	}

	public void setSalinidad(Double salinidad) {
		this.salinidad = salinidad;
	}

	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}

	public Boolean getBocache() {
		return bocache;
	}

	public void setBocache(Boolean bocache) {
		this.bocache = bocache;
	}
	
	
}
