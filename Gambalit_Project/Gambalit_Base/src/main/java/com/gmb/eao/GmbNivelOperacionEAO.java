package com.gmb.eao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import com.gmb.conexion.GmbConexion;
import com.gmb.modelo.GmbNivelOperacion;




@Stateless
public class GmbNivelOperacionEAO extends GmbConexion<GmbNivelOperacion, Serializable>
{

	@SuppressWarnings("unchecked")
	public List<GmbNivelOperacion> consultarNivelOperacion() {
	
		List<GmbNivelOperacion> nivelOperacion=null;
		try {
			nivelOperacion=new ArrayList<>();
		
			Query query = entityMgr.createNamedQuery("findAllNivelOperacion");
			nivelOperacion=(List<GmbNivelOperacion>)  query.getResultList();
		} catch (Exception e) {
            System.out.println(e.toString());
		}
		return nivelOperacion;
	}

	
	public void insertarNivelOperacion(GmbNivelOperacion objeto) {
		entityMgr.persist(objeto);
	}
	
	public GmbNivelOperacion getNivelOperacion() { 
	
		Query query =entityMgr.createNativeQuery("select  * from gmb_nivel_operacion where id_nivel_operacion in "
				+ "(select max(id_nivel_operacion) from gmb_nivel_operacion) ",GmbNivelOperacion.class);
		return(GmbNivelOperacion) query.getSingleResult();
		
		
	}
	
	public BigInteger getOperacionDiariaxFechaxPiscina(String piscina)
	{
		Query query=entityMgr.createNativeQuery(" select count(*) from gmb_nivel_operacion b " + 
				"       where b.estado= 'A'" + 
				"       and b.piscina= ? ");
		query.setParameter(1, piscina);
		return(BigInteger) query.getSingleResult();
	}
	
	public void eliminarNivelOperacion(GmbNivelOperacion objeto)
	{
		entityMgr.merge(objeto);
	}
	
	public void actualizarNivelOperacion(GmbNivelOperacion objeto)
	{
		entityMgr.merge(objeto);
	}
	
	public GmbNivelOperacion getNivelOperacionxId(Integer idNivelOperacion) { 
		
		Query query =entityMgr.createNativeQuery("select  * from gmb_nivel_operacion where id_nivel_operacion =?",GmbNivelOperacion.class);
		query.setParameter(1, idNivelOperacion);
		return(GmbNivelOperacion) query.getSingleResult();
		
		
	}
	
	
		
	

}
