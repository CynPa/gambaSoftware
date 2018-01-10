package com.bmb.servicio;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.gmb.eao.GmbSiembraEAO;
import com.gmb.modelo.GmbNivelOperacion;
import com.gmb.modelo.GmbSiembra;

@Stateless
public class GmbSiembraServicio  implements IGmbSiembraServicio{

	
	@EJB
	private GmbSiembraEAO siembraEAO;
	
	public List<GmbSiembra> consultarSiembra()
	{
		return siembraEAO.consultarSiembra();
	}
	
	
	public void ingresarSiembra(GmbSiembra objeto)
	{
		siembraEAO.ingresarSiembra(objeto);
	}
	
	public void modificarSiembra(GmbSiembra objeto)
	{
		siembraEAO.modificarSiembra(objeto);
	}
	
	public Integer validarSiembraIngreso(GmbNivelOperacion objeto)
	{
		return siembraEAO.consultarSiembra(objeto);
	}
	public List<GmbSiembra> getSiembraxPiscinaActiva()
	{
		return siembraEAO.getSiembraxPiscinaActiva();
	}

	public GmbSiembra getSiembraxId(Integer id)
	{
		return siembraEAO.getSiembraxId(id);
	}
}
