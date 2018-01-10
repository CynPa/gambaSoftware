package com.gambalit.as2.mb;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.gmb.as2.modelo.PlantillaReporte2;
import com.gmb.as2.modelo.PlantillaSaldoBanco;
import com.gmb.as2.servicio.IGenericReport;

@ManagedBean(name="reporteBanco")
@ViewScoped
public class ReporteSaldoBancos {
	
	@EJB
	private IGenericReport reporte;
	 
	private List<PlantillaSaldoBanco> lista;
	private Date fecha;
	
	@PostConstruct
    public void init() {
		
    }
	
	
	public void llenarTabla() throws ParseException
	{
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
		String fechaString = formato.format(fecha); // Convierte Date a String
		Date miFecha = formato.parse(fechaString); // convierte String a Date
		 List<Object[]> listaObjetos=reporte.getSaldoBanco(miFecha);
		 lista=new ArrayList<PlantillaSaldoBanco>();
		 Double valor =0.0;
		 Double valorsaldo=0.0;
		 Double total=0.0;
		 DecimalFormat formatter = new DecimalFormat("###,###.##");
		 PlantillaSaldoBanco saldoTotal=new PlantillaSaldoBanco();
		 for (Object[] objects : listaObjetos) {
			 PlantillaSaldoBanco plantilla=new PlantillaSaldoBanco();
			 plantilla.setBanco(objects[0].toString());
			 plantilla.setCuenta(objects[1].toString());
			 valorsaldo=Double.valueOf(objects[2].toString());
			 plantilla.setSaldo(formatter.format(valorsaldo));
			 valor=valor+Double.parseDouble(objects[2].toString());
			 lista.add(plantilla);
		}
		 saldoTotal.setBanco("");
		 saldoTotal.setCuenta("Total");
		 total=Double.valueOf(valor);
		 saldoTotal.setSaldo(formatter.format(total));
		lista.add(saldoTotal);
		 
		
	} 


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public List<PlantillaSaldoBanco> getLista() {
		return lista;
	}


	public void setLista(List<PlantillaSaldoBanco> lista) {
		this.lista = lista;
	}
	
	

}
