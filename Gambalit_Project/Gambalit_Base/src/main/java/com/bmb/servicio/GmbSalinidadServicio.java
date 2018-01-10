package com.bmb.servicio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.gmb.eao.GmbSalinidadEAO;
import com.gmb.modelo.GmbSalinidad;

@Stateless
public class GmbSalinidadServicio implements IGmbSalinidadServicio{
	@EJB
	private GmbSalinidadEAO gmbSalinidadEAO;

	public List<GmbSalinidad> getListaSalinidad() {

		return gmbSalinidadEAO.consultarSalinidad();
	}
	
	public GmbSalinidad getListaSalinidadLast13Months() {

		return gmbSalinidadEAO.consultarSalinidadLast13months();
	} 

	
	
	public String insertSalinidad(GmbSalinidad objeto)
	{
		 return gmbSalinidadEAO.insertarSalinidad(objeto);
		 
	}
	
	public void eliminarSalinidad(GmbSalinidad objeto)
	{
		gmbSalinidadEAO.eliminarSalinidad(objeto);
	}
	
	public void actualizarSalinidad(GmbSalinidad objeto)
	{
		gmbSalinidadEAO.actualizarSalinidad(objeto);
	}

}
