package com.gmb.as2.servicio;


import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.gmb.as2.eao.GenericReport;

@Stateless
public class GenericReporteServicio implements IGenericReport {
	
	@EJB 
	private GenericReport reporte;
	 
	public List<Object[]> getReporte1(){
		return reporte.getAs2Reporte1();
	}
	public List<Object[]> getReporte2(){
		return reporte.getAs2Reporte2();
	}
	public List<Object[]> getReporte3(){
		return reporte.getAs2Reporte3();
	}
	public List<Object[]> getReporte4(){
		return reporte.getAs2Reporte4();
	}
	public List<Object[]> getSaldoBanco(Date fecha){
		return reporte.getSaldoBancos(fecha);
	}
	public List<Object[]> getSaldoBodega(Date fechaIni, Date fechaFin)
	{
		return reporte.getSaldoBodega(fechaIni, fechaFin);
	}
}
