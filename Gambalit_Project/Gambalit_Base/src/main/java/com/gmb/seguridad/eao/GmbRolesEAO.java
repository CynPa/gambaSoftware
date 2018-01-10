package com.gmb.seguridad.eao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.gmb.conexion.GmbConexion;
import com.gmb.modelo.seguridad.GmbRoles;
import com.gmb.modelo.seguridad.GmbUsuarios;

@Stateless 
public class GmbRolesEAO extends GmbConexion<GmbRoles, Serializable> {
	
	@SuppressWarnings("unchecked")
	public List<GmbRoles> consultarRoles() {

		List<GmbRoles> roles = null;
		try {
			roles = new ArrayList<>();
			 Query query = entityMgr.createNamedQuery("findAllRoles");
			roles = (List<GmbRoles>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return roles;
	}
	
	public void insertarRol(GmbRoles objeto)
	{
		entityMgr.persist(objeto);
	}
	public void modificarRol(GmbRoles objeto)
	{
		entityMgr.merge(objeto);
	}

}
