package com.gmb.as2.servicio;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
@Local
public interface IGenericReport  {
  
	public List<Object[]> getReporte1(); 
	public List<Object[]> getReporte2();
	public List<Object[]> getReporte3();
	public List<Object[]> getReporte4();
	public List<Object[]> getSaldoBanco(Date fecha);
	public List<Object[]> getSaldoBodega(Date fechaIni, Date fechaFin);
}
