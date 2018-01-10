package com.gambalit.as2.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.gmb.as2.modelo.PlantillaReporte1;
import com.gmb.as2.servicio.IGenericReport;

@ManagedBean(name="reporte1")
@ViewScoped
public class Reporte1  implements Serializable{
	
	  
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private IGenericReport  reporte;
	
	private List<PlantillaReporte1> lista;
	private PlantillaReporte1 reporteSelected;
	private List<PlantillaReporte1> reporteFilter;
	
	@PostConstruct
	    public void init() {
	    	llenarTabla();
	    }
	
	 public void llenarTabla()
	 {
		 List<Object[]> listaObjetos=reporte.getReporte1();
		 lista=new ArrayList<PlantillaReporte1>();
		 //PlantillaReporte1 plantilla=new PlantillaReporte1();
		 for (Object[] objects : listaObjetos) {
			 PlantillaReporte1 plantilla=new PlantillaReporte1();
			 plantilla.setCodigo(objects[0].toString());
			 plantilla.setNombre(objects[1].toString()); 
			 plantilla.setCantidad(objects[2].toString());
			 if(objects[3]==null)
			 { 
				 objects[3]="";
			 }
			 plantilla.setSaldo(objects[3].toString());
			 plantilla.setCantidaUltimaCompra(objects[4].toString());
			 plantilla.setFechaUltimaCompra(objects[5].toString());
			 plantilla.setCategoria(objects[7].toString());
			 plantilla.setSubcategoria(objects[6].toString());
			 lista.add(plantilla);
		}
	 }
	 public List<PlantillaReporte1> getLista() {
			return lista;
		}

		public void setLista(List<PlantillaReporte1> lista) {
			this.lista = lista;
		}

		public PlantillaReporte1 getReporteSelected() {
			return reporteSelected;
		}

		public void setReporteSelected(PlantillaReporte1 reporteSelected) {
			this.reporteSelected = reporteSelected;
		}

		public List<PlantillaReporte1> getReporteFilter() {
			return reporteFilter;
		}

		public void setReporteFilter(List<PlantillaReporte1> reporteFilter) {
			this.reporteFilter = reporteFilter;
		}

		
		

}
