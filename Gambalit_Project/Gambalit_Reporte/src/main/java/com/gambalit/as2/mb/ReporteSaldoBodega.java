package com.gambalit.as2.mb;

import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.gmb.as2.modelo.PlantillaSaldoBodega;
import com.gmb.as2.servicio.IGenericReport;

@ManagedBean(name="reporteB")
@ViewScoped
public class ReporteSaldoBodega {
	@EJB
	private IGenericReport saldoBancos;
	List<PlantillaSaldoBodega> listaSaldoBodega;
	
	@PostConstruct
    public void init() {
		
    }
	
	
	public void llenarTabla() throws ParseException
	{
		
		
	}
	
}
