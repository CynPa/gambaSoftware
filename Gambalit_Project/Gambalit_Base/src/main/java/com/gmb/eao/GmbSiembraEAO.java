package com.gmb.eao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.gmb.conexion.GmbConexion;
import com.gmb.modelo.GmbNivelOperacion;
import com.gmb.modelo.GmbSiembra;

@Stateless
public class GmbSiembraEAO  extends GmbConexion<GmbSiembra, Serializable>{
	
	
	@SuppressWarnings("unchecked")
	public List<GmbSiembra> consultarSiembra()
	{
		List<GmbSiembra> siembra=null;
		try {
			siembra=new ArrayList<>();
			Query query = entityMgr.createNamedQuery("findAllSiembra");
			siembra=(List<GmbSiembra>)  query.getResultList();
		} catch (Exception e) {
            System.out.println(e.toString());
		}
		return siembra;
	}
	
	public Integer consultarSiembra(GmbNivelOperacion piscina)
	{
		Long siembra;
		Integer result;
		Query query =entityMgr.createQuery("select count(c) from GmbSiembra c  where c.estado=true and c.nivelOperacion=:piscina");
		query.setParameter("piscina", piscina);
		siembra=(Long) query.getSingleResult();
		result=Integer.valueOf(siembra.intValue());
		return result;
	}
	
	public void ingresarSiembra(GmbSiembra objeto)
	{
		entityMgr.persist(objeto);
	}
	
	public void modificarSiembra(GmbSiembra objeto)
	{
		entityMgr.merge(objeto);
	}
	
	@SuppressWarnings("unchecked")
	public List<GmbSiembra> getSiembraxPiscinaActiva()
	{
		List<GmbSiembra> siembra=new ArrayList<GmbSiembra>();
	    Query query=entityMgr.createQuery("select c from GmbSiembra c where c.estado=true");
		siembra=(List<GmbSiembra>)query.getResultList();
		return siembra;
	}
	
	public GmbSiembra getSiembraxId(Integer IdSiembra) {
		GmbSiembra siembra=new GmbSiembra();
		Query query=entityMgr.createQuery("select c from GmbSiembra c where c.idSiembra=:idSiembra",GmbSiembra.class);
		Long id=IdSiembra.longValue();
		 query.setParameter("idSiembra", id);
		 siembra=(GmbSiembra) query.getSingleResult();
		return siembra;
	}

}
