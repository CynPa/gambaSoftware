package com.bmb.servicio;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.gmb.eao.GmbOperacionDiariaDatosEAO;
import com.gmb.modelo.GmbOperacionDiariaDatos;



@Stateless
public class GmbOperacionDiariaDatosServicio implements IGmbOperacionDiariaDatosServicio {
	@EJB
	private GmbOperacionDiariaDatosEAO gmbOperacionDiaraiEAO;

	public List<GmbOperacionDiariaDatos> getListaNivelOperacion() {

		return gmbOperacionDiaraiEAO.consultarNivelOperacion();
	}

	public GmbOperacionDiariaDatos getOperacionDiariaDatosxId(Integer idNivelOperacion) {
		return gmbOperacionDiaraiEAO.getOperacionDiariaxId(idNivelOperacion);
	}
	
	public void insertOperacionDiariaDatos(GmbOperacionDiariaDatos objeto)
	{
		gmbOperacionDiaraiEAO.insertarNivelOperacion(objeto);
	}
	
	public void eliminarOperacionDiariaDatos(GmbOperacionDiariaDatos objeto)
	{
		gmbOperacionDiaraiEAO.eliminarNivelOperacion(objeto);
	}
	
	public void actualizarOperacionDiariaDatos(GmbOperacionDiariaDatos objeto)
	{
		gmbOperacionDiaraiEAO.actualizarNivelOperacion(objeto);
	}
	
	public BigInteger getOperacionDiariaxFechaxPiscina(Date fecha,String piscina)
	{
		return gmbOperacionDiaraiEAO.getOperacionDiariaxFechaxPiscina(fecha, piscina);
	}
	public List<Object[]> getReporte1(){
		return gmbOperacionDiaraiEAO.getReporte1();
	}
	public List<Object[]> getReporte2()
	{
		return gmbOperacionDiaraiEAO.getReporte2();
	}
	public List<String> getFechaReporte1()
	{
		return gmbOperacionDiaraiEAO.getFechaReporte1();
	}
	public List<GmbOperacionDiariaDatos> getOddxFechaDesde(Date fecha,String piscina)
	{
		return gmbOperacionDiaraiEAO.getOperacionDiariaDatosxFechaDesde(fecha,piscina);
	}
	public List<String> getFechasUltimas()
	{
		return gmbOperacionDiaraiEAO.getFechasUltimas();
	}
	
	public List<GmbOperacionDiariaDatos> getOperacionDiariaDatosxFecha(Date fecha)
	{
		return gmbOperacionDiaraiEAO.getOperacionDiariaDatosxFecha(fecha);
	}
	
	public List<Date> getFechasDistinct()
	{
		return gmbOperacionDiaraiEAO.getFechasDistinct();
	}
	
}
