package com.bmb.servicio.seguridad;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.gmb.modelo.seguridad.GmbRoles;
import com.gmb.seguridad.eao.GmbRolesEAO;

@Stateless
public class GmbRolesServicio implements IRolesServicio {
	@EJB
	private GmbRolesEAO rolEAO;
	
	public List<GmbRoles> consultaRoles()
	{
		return rolEAO.consultarRoles();
	}
	
	public void insertarRol(GmbRoles objeto)
	{
		rolEAO.insertarRol(objeto);
	}
	
	public void modificarRol(GmbRoles objeto)
	{
		rolEAO.modificarRol(objeto);
	}
	
	public void eliminarRol(GmbRoles objeto)
	{
		rolEAO.modificarRol(objeto);
	}
	
	
}
