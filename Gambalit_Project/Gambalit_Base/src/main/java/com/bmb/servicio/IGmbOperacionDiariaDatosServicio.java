package com.bmb.servicio;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.gmb.modelo.GmbOperacionDiariaDatos;
@Local
public interface IGmbOperacionDiariaDatosServicio {
 
	public List<GmbOperacionDiariaDatos> getListaNivelOperacion();
	public GmbOperacionDiariaDatos getOperacionDiariaDatosxId(Integer idNivelOperacion);
	public void insertOperacionDiariaDatos(GmbOperacionDiariaDatos objeto);
	public void actualizarOperacionDiariaDatos(GmbOperacionDiariaDatos objeto);
	public void eliminarOperacionDiariaDatos(GmbOperacionDiariaDatos objeto);
	public List<Object[]> getReporte1();
	public List<String> getFechaReporte1();
	public List<Object[]> getReporte2();
	public BigInteger getOperacionDiariaxFechaxPiscina(Date fecha,String piscina);
	public List<GmbOperacionDiariaDatos> getOddxFechaDesde(Date fecha,String piscina);
	public List<String> getFechasUltimas();
	public List<GmbOperacionDiariaDatos> getOperacionDiariaDatosxFecha(Date fecha);
	public List<Date> getFechasDistinct();
}
