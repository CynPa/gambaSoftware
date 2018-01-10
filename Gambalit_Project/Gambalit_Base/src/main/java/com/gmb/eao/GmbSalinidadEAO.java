package com.gmb.eao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.gmb.conexion.GmbConexion;
import com.gmb.modelo.GmbSalinidad;

@Stateless
public class GmbSalinidadEAO extends GmbConexion<GmbSalinidad, Serializable>{

	@SuppressWarnings("unchecked")
	public List<GmbSalinidad> consultarSalinidad() {
	
		List<GmbSalinidad> nivelOperacion=null;
		try {
			nivelOperacion=new ArrayList<>();
			Query query = entityMgr.createNamedQuery("findAllSalinidad");
			nivelOperacion=(List<GmbSalinidad>)  query.getResultList();
		} catch (Exception e) {
            System.out.println(e.toString());
		}
		return nivelOperacion;
	}
	
	
	
   @SuppressWarnings("unchecked")
public GmbSalinidad consultarSalinidadLast13months() {
		
		GmbSalinidad nivelOperacion=null;
		try { 
			nivelOperacion=new GmbSalinidad();
			Query query = entityMgr.createNativeQuery("select * from gmb_salinidad where  estado='A' and fecha in( " + 
					" select max(fecha) from gmb_salinidad where  estado='A' order by 1 asc " + 
					" ) order by fecha desc ",GmbSalinidad.class);
			nivelOperacion=(GmbSalinidad)  query.getSingleResult();
		} catch (Exception e) {
            System.out.println(e.toString());
		}
		return nivelOperacion;
	}
	public String insertarSalinidad(GmbSalinidad objeto) {
		  String valor=null;
		try {
		entityMgr.persist(objeto);
	
		}
		catch (Exception e) {
			valor=e.toString();
		}
		return valor;
	}
	
	
	
	public void eliminarSalinidad(GmbSalinidad objeto)
	{
		entityMgr.merge(objeto);
	}
	
	public void actualizarSalinidad(GmbSalinidad objeto)
	{
		entityMgr.merge(objeto);
	}
}
