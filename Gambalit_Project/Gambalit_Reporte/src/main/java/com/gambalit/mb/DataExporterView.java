package com.gambalit.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import com.gmb.eao.IGmbNivelOperacionEAO;
import com.gmb.modelo.GmbNivelOperacion;





@ManagedBean(name="nivelOperativoMb")
@ViewScoped
public class DataExporterView implements Serializable {
     
    private List<GmbNivelOperacion> nivelOperativo=null;
         
   // @ManagedProperty("#{carService}")
   // private CarService service;
     
    @EJB
    private IGmbNivelOperacionEAO nivelOperacion;
    
    
    @PostConstruct
    public void init() {
    	llenarTabla();
    }

    
    
    public void  llenarTabla()
    {
    	GmbNivelOperacion  nivelOp=new GmbNivelOperacion();
    	
    	nivelOperativo=new ArrayList<GmbNivelOperacion>();
    	int contador=1;
    	/*for (int i=1;i<100;i++) 
    	{
    	nivelOp.setIdNivelOperacion(contador);
    	nivelOp.setNivelOperacion(contador+1);
    	nivelOp.setAreaHa(10.0+contador);
    	nivelOp.setPiscina(contador);
    	nivelOperativo.add(nivelOp);
    	contador++;
		}*/
    	
    	List<GmbNivelOperacion> listaNivelOperacion=nivelOperacion.getListaNivelOperacion();
    	for (GmbNivelOperacion gmbNivelOperacion : listaNivelOperacion) {
    		nivelOperativo=new ArrayList<GmbNivelOperacion>();
			System.out.println(gmbNivelOperacion.getAreaHa());
			nivelOp.setIdNivelOperacion(gmbNivelOperacion.getIdNivelOperacion());
	    	nivelOp.setNivelOperacion(gmbNivelOperacion.getNivelOperacion());
	    	nivelOp.setAreaHa(gmbNivelOperacion.getAreaHa());
	    	nivelOp.setPiscina(gmbNivelOperacion.getPiscina());
	    	nivelOperativo.add(nivelOp);
		}
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
    
}