package com.gmb.seguridad.eao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.gmb.conexion.GmbConexion;
import com.gmb.modelo.seguridad.GmbOpciones;
import com.gmb.modelo.seguridad.GmbRoles;
@Stateless
public class GmbOpcionesEAO  extends GmbConexion<GmbOpciones, Serializable> {
	
	@SuppressWarnings("unchecked")
	public List<GmbOpciones> consultarOpciones() {

		List<GmbOpciones> opciones = null;
		try {
			opciones = new ArrayList<>();
			 Query query = entityMgr.createNamedQuery("findAllOpciones");
			opciones = (List<GmbOpciones>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return opciones;
	}
	@SuppressWarnings("unchecked")
	public List<GmbOpciones> getOpciones()
	{
		Query query =entityMgr.createNativeQuery("select  * from gmb_opciones where id_padre = 0 ",GmbOpciones.class);
		return(List<GmbOpciones>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<GmbOpciones> getOpcionesHijas( Integer id_padre)
	{
		Query query =entityMgr.createNativeQuery("select  * from gmb_opciones  where id_padre = ?",GmbOpciones.class);
		query.setParameter(1, id_padre);
		return(List<GmbOpciones>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<GmbOpciones> getOpcionesHijas( )
	{
		Query query =entityMgr.createQuery("SELECT  c FROM GmbOpciones  c where c.idPadre != 0");
		return(List<GmbOpciones>) query.getResultList();
	}
	
	public GmbOpciones  getOpcionHijas(Integer id_Padre )
	{
		Query query =entityMgr.createQuery("SELECT  c FROM GmbOpciones  c where c.idOpcion= :idPadre");
		Long idPadreL=new Long(id_Padre);
		query.setParameter("idPadre", idPadreL);
		return(GmbOpciones) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<GmbOpciones> getOpcionesxUsuario(String id_Usuario)
	{	
		Query query =entityMgr.createNativeQuery("select * from gmb_opciones where id_opcion in( " + 
				"select id_padre from gmb_usuarios a, gmb_usuarios_roles b,gmb_roles c, gmb_roles_opciones d, gmb_opciones e " + 
				"where a.id_usuario=b.id_usuario and b.id_rol=c.id_rol  and  c.id_rol=d.id_rol " + 
				"and d.id_opcion=e.id_opcion  and a.usuario= ?) and estado='A' order by orden asc ",GmbOpciones.class);
		query.setParameter(1, id_Usuario);
		return(List<GmbOpciones>) query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<GmbOpciones> getOpcionesHijas( Integer id_padre,String usuario)
	{
		Query query =entityMgr.createNativeQuery("select e.* from gmb_usuarios a, gmb_usuarios_roles b,gmb_roles c, gmb_roles_opciones d, gmb_opciones e " + 
				"where a.id_usuario=b.id_usuario and b.id_rol=c.id_rol  and  c.id_rol=d.id_rol " + 
				"and d.id_opcion=e.id_opcion  and a.usuario= ?  and id_padre= ? and a.estado='A' and c.estado='A' and e.estado='A'",GmbOpciones.class);
		query.setParameter(1, usuario);
		query.setParameter(2, id_padre);
		return(List<GmbOpciones>) query.getResultList();
	}
	
	

}
