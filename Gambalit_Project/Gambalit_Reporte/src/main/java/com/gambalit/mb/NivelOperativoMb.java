package com.gambalit.mb;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.context.RequestContext;

import com.gmb.eao.IGmbNivelOperacionEAO;
import com.gmb.modelo.GmbNivelOperacion;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;




@ManagedBean(name = "nivelOperacion")
@ViewScoped
public class NivelOperativoMb implements Serializable{

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private String piscina;
	private Double areaHa;
	private Integer nivelOp;
	private GmbNivelOperacion  nivelOperacionSelected;
	private List<GmbNivelOperacion> nivelOperacionsFiltered;
	
	

	public GmbNivelOperacion getNivelOperacionSelected() {
		return nivelOperacionSelected;
	}

	public void setNivelOperacionSelected(GmbNivelOperacion nivelOperacionSelected) {
		this.nivelOperacionSelected = nivelOperacionSelected;
	}

	public String getPiscina() {
		return piscina;
	}

	public void setPiscina(String piscina) {
		this.piscina = piscina;
	}

	public Double getAreaHa() {
		return areaHa;
	}

	public void setAreaHa(Double areaHa) {
		this.areaHa = areaHa;
	}

	public Integer getNivelOp() {
		return nivelOp;
	}

	public void setNivelOp(Integer nivelOp) {
		this.nivelOp = nivelOp;
	}

	

	

	private List<GmbNivelOperacion> nivelOperativo = null;

	@EJB
	private IGmbNivelOperacionEAO nivelOperacion;

	

	@PostConstruct
	public void init() {
		llenarTabla();
		piscina=null;
		areaHa=null;
		nivelOp=null;
      
		
	}

	public void llenarTabla() {
		GmbNivelOperacion nivelOp = null;

		nivelOperativo = new ArrayList<GmbNivelOperacion>();
		List<GmbNivelOperacion> listaNivelOperacion = nivelOperacion.getListaNivelOperacion();
		for (GmbNivelOperacion gmbNivelOperacion : listaNivelOperacion) {
			nivelOp = new GmbNivelOperacion();
			nivelOp.setIdNivelOperacion(gmbNivelOperacion.getIdNivelOperacion());
			nivelOp.setNivelOperacion(gmbNivelOperacion.getNivelOperacion());
			nivelOp.setAreaHa(gmbNivelOperacion.getAreaHa());
			nivelOp.setPiscina(gmbNivelOperacion.getPiscina());
			nivelOperativo.add(nivelOp);

		}
	} 

	public void insertarNivelOperacion() {
		try {
			BigInteger valor=getNivelOperacionxPiscina(piscina.toString());
			Integer valorRepetido=valor.intValue();
			if(piscina ==null ||areaHa==null ||nivelOp==null)
			{
				errorMessage("Valores no pueden estar vacios");
			}
			else
			if(valorRepetido>0)
			{
			errorMessage("Piscina ya configurada");	
			}
			else
			{
			GmbNivelOperacion gmbNivelOperacion = new GmbNivelOperacion();
			gmbNivelOperacion.setPiscina(piscina);
			gmbNivelOperacion.setAreaHa(areaHa);
			gmbNivelOperacion.setNivelOperacion(nivelOp);
			gmbNivelOperacion.setEstado("A");
			nivelOperacion.insertNivelOperacion(gmbNivelOperacion);
			addMessage("Usuario Ingresado");
			RequestContext.getCurrentInstance().execute("PF('dlg2').hide()");
			}
			llenarTabla();
		} catch (Exception e) {
			System.out.println("appGmb "+e.toString());
			e.printStackTrace();
		}
	}
	
	public void eliminarNivelOperacion(GmbNivelOperacion gmbNivelOperacion)
	{
		//agregar validaccion de suma de valores 
		gmbNivelOperacion.setEstado("I");
		nivelOperacion.eliminarNivelOperacion(gmbNivelOperacion);
		addMessage("Registro eliminado");
		llenarTabla();
	}
	public void modificarNivelOperacion()
	{
		GmbNivelOperacion gmbNivelOperacion=new GmbNivelOperacion();
		gmbNivelOperacion.setIdNivelOperacion(nivelOperacionSelected.getIdNivelOperacion());
		gmbNivelOperacion.setPiscina(nivelOperacionSelected.getPiscina());
		gmbNivelOperacion.setAreaHa(nivelOperacionSelected.getAreaHa());
		gmbNivelOperacion.setNivelOperacion(nivelOperacionSelected.getNivelOperacion());
		gmbNivelOperacion.setEstado("A");
		nivelOperacion.actualizarNivelOperacion(gmbNivelOperacion);
		llenarTabla();
		RequestContext.getCurrentInstance().execute("PF('dlgActualizar').hide()");
	}
	public List<GmbNivelOperacion> getNivelOperativo() {
		return nivelOperativo;
	}

	public void setNivelOperativo(List<GmbNivelOperacion> nivelOperativo) {
		this.nivelOperativo = nivelOperativo;
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
	
	public BigInteger getNivelOperacionxPiscina(String piscina)
	{
		return nivelOperacion.getOperacionDiariaxFechaxPiscina(piscina);
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
        pdf.open();
        //pdf.setPageSize(PageSize.A4.rotate());
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") +   "/images/logo-iloveimg-resized.png";
         
        pdf.add(Image.getInstance(logo));
    }

	public List<GmbNivelOperacion> getNivelOperacionsFiltered() {
		return nivelOperacionsFiltered;
	}

	public void setNivelOperacionsFiltered(List<GmbNivelOperacion> nivelOperacionsFiltered) {
		this.nivelOperacionsFiltered = nivelOperacionsFiltered;
	}
	
	

}