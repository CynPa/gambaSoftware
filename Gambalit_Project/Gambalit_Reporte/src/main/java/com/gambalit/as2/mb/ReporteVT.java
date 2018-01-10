package com.gambalit.as2.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.gmb.as2.modelo.PlantillaReporte2;
import com.gmb.as2.modelo.PlantillaReporte3;
import com.gmb.as2.servicio.IGenericReport;

@ManagedBean(name="reportevt")
@ViewScoped
public class ReporteVT implements Serializable {
private static final long serialVersionUID = 1L;
	

	@EJB 
	private IGenericReport reporte;
	
	private List<PlantillaReporte3> lista;
	private PlantillaReporte3 reporteSelected;
	private List<PlantillaReporte3> reporteFilter;
	 
	@PostConstruct
    public void init() {
    	llenarTabla();
    }
	
	public void llenarTabla()
	{
		 List<Object[]> listaObjetos=reporte.getReporte4();
		 lista=new ArrayList<PlantillaReporte3>();
		 for (Object[] objects : listaObjetos) {
		/*	 PlantillaReporte3 plantilla=new PlantillaReporte3();
			 plantilla.setFecha(objects[0].toString());
			 plantilla.setHora(objects[1].toString()); 
			 plantilla.setCodigo(objects[2].toString());
			 plantilla.setNombre_empresa_fiscal(objects[3].toString());
			 plantilla.setDescripcion_factura(objects[4].toString());
			 plantilla.setNumero_cuota(objects[5].toString());
			 plantilla.setCantidad(objects[6].toString());
			 plantilla.setPrecio(objects[7].toString());
			 plantilla.setImpuesto(objects[8].toString());
			 plantilla.setValor(objects[9].toString());
			 plantilla.setSaldo(objects[10].toString());
			 plantilla.setCondicion_pago(objects[11].toString());
			 plantilla.setSubcategoria(objects[12].toString());
			 plantilla.setCategoria(objects[13].toString());
			
			 lista.add(plantilla);*/
		}
		
	}

	public PlantillaReporte3 getReporteSelected() {
		return reporteSelected;
	}

	public void setReporteSelected(PlantillaReporte3 reporteSelected) {
		this.reporteSelected = reporteSelected;
	}
}
