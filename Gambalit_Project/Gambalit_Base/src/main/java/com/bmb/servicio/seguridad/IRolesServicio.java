package com.bmb.servicio.seguridad;

import java.util.List;

import javax.ejb.Local;

import com.gmb.modelo.seguridad.GmbRoles;
@Local
public interface IRolesServicio {
	public List<GmbRoles> consultaRoles();
	public void insertarRol(GmbRoles objeto);
	public void modificarRol(GmbRoles objeto);
	public void eliminarRol(GmbRoles objeto);
}
