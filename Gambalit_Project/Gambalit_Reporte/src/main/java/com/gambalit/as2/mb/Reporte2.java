package com.gambalit.as2.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.gmb.as2.modelo.PlantillaReporte1;
import com.gmb.as2.modelo.PlantillaReporte2;
import com.gmb.as2.servicio.IGenericReport;

@ManagedBean(name="reporte2")
@ViewScoped
public class Reporte2 implements Serializable {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	

	@EJB
	private IGenericReport   reporte;
	
	private List<PlantillaReporte2> lista;
	private PlantillaReporte2 reporteSelected;
	private List<PlantillaReporte2> reporteFilter;
	
	@PostConstruct
    public void init() {
    	llenarTabla();
    }
	
	public void llenarTabla()
	{
		 List<Object[]> listaObjetos=reporte.getReporte2();
		 lista=new ArrayList<PlantillaReporte2>();
		 for (Object[] objects : listaObjetos) {
			 PlantillaReporte2 plantilla=new PlantillaReporte2();
			 plantilla.setCodigo(objects[0].toString());
			 plantilla.setNombre_comercial(objects[1].toString()); 
			 plantilla.setEmpresa_comercial(objects[2].toString());
			 plantilla.setNombre_empresa_fiscal(objects[3].toString());
			 plantilla.setDescripcion_factura(objects[4].toString());
			 plantilla.setNumero_cuota(objects[5].toString());
			 plantilla.setCantidad(objects[6].toString());
			 plantilla.setPrecio(objects[7].toString());
			 plantilla.setImpuesto(objects[8].toString());
			 plantilla.setValor(objects[9].toString());
			 plantilla.setSaldo(objects[10].toString());
			 plantilla.setCondicion_pago(objects[11].toString());
			 plantilla.setSubcategoria(objects[11].toString());
			 plantilla.setCategoria(objects[12].toString());
			
			 lista.add(plantilla);
		}
		
	}

	public PlantillaReporte2 getReporteSelected() {
		return reporteSelected;
	}

	public void setReporteSelected(PlantillaReporte2 reporteSelected) {
		this.reporteSelected = reporteSelected;
	}

	public List<PlantillaReporte2> getReporteFilter() {
		return reporteFilter;
	}

	public void setReporteFilter(List<PlantillaReporte2> reporteFilter) {
		this.reporteFilter = reporteFilter;
	}

	public List<PlantillaReporte2> getLista() {
		return lista;
	}

	public void setLista(List<PlantillaReporte2> lista) {
		this.lista = lista;
	}
	
	

}
