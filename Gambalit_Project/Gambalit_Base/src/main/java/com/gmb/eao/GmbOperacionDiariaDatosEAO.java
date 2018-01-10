package com.gmb.eao;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.mapping.Column;

import com.gmb.conexion.GmbConexion;
import com.gmb.modelo.GmbNivelOperacion;
import com.gmb.modelo.GmbOperacionDiariaDatos;

@Stateless
public class GmbOperacionDiariaDatosEAO extends GmbConexion<GmbOperacionDiariaDatos, Serializable> {

	@SuppressWarnings("unchecked")
	public List<GmbOperacionDiariaDatos> consultarNivelOperacion() {

		List<GmbOperacionDiariaDatos> nivelOperacion = null;
		try {
			nivelOperacion = new ArrayList<>();

			//Query query = entityMgr.createNamedQuery("findAllOperacionDiariaDatos");
			//nivelOperacion = (List<GmbOperacionDiariaDatos>) query.getResultList();
			//GmbOperacionDiariaDatos obj=new GmbOperacionDiariaDatos();
			//getColumnNames(obj);  
			CriteriaBuilder cb = entityMgr.getCriteriaBuilder();
			CriteriaQuery< GmbOperacionDiariaDatos > cq = cb.createQuery(GmbOperacionDiariaDatos.class);
			Root<GmbOperacionDiariaDatos> from = cq.from(GmbOperacionDiariaDatos.class);
			cq.select(from);
			cq.orderBy(
				    cb.desc(from.get("fecha")));
			TypedQuery<GmbOperacionDiariaDatos> q = entityMgr.createQuery(cq);
			
			nivelOperacion = q.getResultList();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return nivelOperacion;
	}

	public GmbOperacionDiariaDatos getOperacionDiariaxId(Integer idNivelOperacion) {
		Query query = entityMgr.createNativeQuery("select  * from gmb_nivel_operacion where id_nivel_operacion =:valor",
				GmbNivelOperacion.class);
		query.setParameter(1, idNivelOperacion);
		return (GmbOperacionDiariaDatos) query.getSingleResult();

	}

	public void insertarNivelOperacion(GmbOperacionDiariaDatos objeto) {
		entityMgr.persist(objeto);
	}
	
	public void eliminarNivelOperacion(GmbOperacionDiariaDatos objeto) {
		entityMgr.merge(objeto);
	}
	
	public void actualizarNivelOperacion(GmbOperacionDiariaDatos objeto) {
		entityMgr.merge(objeto);
	}


	public void getColumnNames(GmbOperacionDiariaDatos objeto) {
		List<String> listaColumnas = new ArrayList<String>();
		
		Class<? extends GmbOperacionDiariaDatos> method = objeto.getClass();
				Annotation[] annotations = method.getDeclaredAnnotations();

				for(Annotation annotation : annotations){
				    if(annotation instanceof Column){
				        Column myAnnotation = (Column) annotation;
				        System.out.println("name: " + myAnnotation.getName());
				        System.out.println("value: " + myAnnotation.getValue());
				    }
				}

	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> getFechaReporte1()
	{
		//Query query=entityMgr.createNativeQuery("select distinct(to_char(fecha,'MM-DD-YYYY')) from gmb_datos_diarios where estado='A' order by 1 desc limit 8");
		Query query=entityMgr.createNativeQuery("select distinct(to_char(fecha,'YYYY-MM-DD')) from gmb_datos_diarios where estado='A' order by 1 asc ");//limit 8
		List<String> lista2 =query.getResultList();
		return lista2;
		
	}
	@SuppressWarnings("unchecked")
	public List<String> getFechasUltimas()
	{
		Query query=entityMgr.createNativeQuery("select distinct(to_char(fecha,'YYYY-MM-DD')) from gmb_datos_diarios where estado='A' order by 1 desc limit 22");
		List<String> lista2 =query.getResultList();
		return lista2;
		
	}
	 
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getReporte1()
	{
		Query query= entityMgr.createNativeQuery("select coalesce(to_char(b.fecha, 'YYYY-MM-DD'), ''), b.mes, (select \r\n" + 
				"                                                             CASE\r\n" + 
				"   WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"  WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END  from    \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where   \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion   \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='1'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_1 ,    \r\n" + 
				"								(select CASE\r\n" + 
				" WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END from    \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where    \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion   \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos    \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina   \r\n" + 
				"								 and a2.piscina='2'   \r\n" + 
				"								and a2.estado='A' and b2.estado='A')piscina_2,   \r\n" + 
				"				                (select CASE\r\n" + 
				"  WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END  from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where   \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='3'   \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_3,  \r\n" + 
				"				                (select CASE\r\n" + 
				" WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END   from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where   \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='4'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_4,   \r\n" + 
				"				                (select CASE\r\n" + 
				" WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END  from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where   \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='5'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_5,   \r\n" + 
				"				                (select CASE\r\n" + 
				" WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END  from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='6'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_6, \r\n" + 
				"				                (select CASE\r\n" + 
				" WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END  from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='7'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_7, \r\n" + 
				"				                (select CASE\r\n" + 
				" WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END   from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='8'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_8, \r\n" + 
				"				                (select CASE\r\n" + 
				" WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END   from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='9'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_9, \r\n" + 
				"				                (select CASE\r\n" + 
				"                                  WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END   from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='10'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_10, \r\n" + 
				"				                (select CASE\r\n" + 
				"  WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END  from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='11'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_11, \r\n" + 
				"				                (select CASE\r\n" + 
				"   WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END   from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='12'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_12, \r\n" + 
				"				                (select CASE\r\n" + 
				"   WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END   from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='13'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_13, \r\n" + 
				"				                (select CASE\r\n" + 
				"  WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END  from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='14'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_14, \r\n" + 
				"				                (select CASE\r\n" + 
				" WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END  from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='15'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_15, \r\n" + 
				"				                (select CASE\r\n" + 
				"  WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END  from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='16'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_16, \r\n" + 
				"				                (select CASE\r\n" + 
				"  WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END   from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='17'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_17, \r\n" + 
				"				                (select CASE\r\n" + 
				"  WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END   from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='18'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_18, \r\n" + 
				"				                (select CASE\r\n" + 
				"  WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='19'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_19, \r\n" + 
				"				                (select CASE\r\n" + 
				"   WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END   from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='20'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_20 , \r\n" + 
				"				                (select CASE\r\n" + 
				" WHEN (b2.nivel_diario >0) and (b2.nivel_diario <> a2.nivel_operacion) THEN (b2.nivel_diario - a2.nivel_operacion)\r\n" + 
				"                                 WHEN b2.nivel_diario=a2.nivel_operacion THEN '99999'\r\n" + 
				"  else '0'\r\n" + 
				" END   from   \r\n" + 
				"								gmb_nivel_operacion a2,gmb_datos_diarios b2 where  \r\n" + 
				"								b2.operaciondiariadatos=a2.id_nivel_operacion    \r\n" + 
				"								 and a2.id_nivel_operacion=a.id_nivel_operacion    \r\n" + 
				"								 and b2.operaciondiariadatos=b.operaciondiariadatos   \r\n" + 
				"								and b2.fecha=b.fecha   \r\n" + 
				"								  and a2.piscina=a.piscina    \r\n" + 
				"								 and a2.piscina='PC01'    \r\n" + 
				"								and a2.estado='A' and b2.estado='A' )piscina_21 \r\n" + 
				"								from gmb_nivel_operacion a,gmb_datos_diarios b--,gmb_salinidad c   \r\n" + 
				"								where b.operaciondiariadatos=a.id_nivel_operacion   \r\n" + 
				"								--and b.operaciondiariadatos=c.id_salinidad   \r\n" + 
				"								and a.estado='A' and b.estado='A' order by b.fecha asc"
				+ "");
		List<Object[]> lista =query.getResultList();
		return lista;
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getReporte2()
	{
		Query query= entityMgr.createNativeQuery("SELECT coalesce(to_char(a.fecha, 'YYYY-MM-DD'), ''), B.PISCINA,A.DURO,A.FLACIDO,A.MUDADO,A.MORTALIDAD_FRESCO,A.MORTALIDAD_ROJO,A.ENFERMOS FROM GMB_DATOS_DIARIOS A, GMB_NIVEL_OPERACION B  \r\n" + 
				"WHERE A.OPERACIONDIARIADATOS=B.ID_NIVEL_OPERACION AND A.ESTADO='A' AND B.ESTADO='A' order by a.fecha asc");
		List<Object[]> lista =query.getResultList();
		return lista;
	}
	
	public BigInteger getOperacionDiariaxFechaxPiscina(Date fecha,String piscina)
	{
		Query query=entityMgr.createNativeQuery(" select count(*) from gmb_datos_diarios a ,gmb_nivel_operacion b " + 
				"       where a.operaciondiariadatos=b.id_nivel_operacion and a.estado='A' " + 
				"       and a.fecha= ?" + 
				"       and b.piscina= ? ");
		query.setParameter(1, fecha);
		query.setParameter(2, piscina);
		return(BigInteger) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<GmbOperacionDiariaDatos> getOperacionDiariaDatosxFechaDesde(Date fecha,String piscina)
	{
		List<GmbOperacionDiariaDatos> listaOdd=new ArrayList<GmbOperacionDiariaDatos>();
		Query query=entityMgr.createQuery("select c from GmbOperacionDiariaDatos c inner join c.nivelOperacion no where c.fecha >= :fecha and no.piscina=:piscina order by c.fecha asc");
		query.setParameter("fecha",fecha);
		query.setParameter("piscina", piscina);
		listaOdd=query.getResultList();
		return listaOdd;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<GmbOperacionDiariaDatos> getOperacionDiariaDatosxFecha(Date fecha)
	{
		List<GmbOperacionDiariaDatos> listaOdd=new ArrayList<GmbOperacionDiariaDatos>();
		Query query=entityMgr.createQuery("select c from GmbOperacionDiariaDatos c  where c.estado='A' and  c.fecha = :fecha order by c.nivelOperacion.orden asc");
		query.setParameter("fecha",fecha);
		listaOdd=query.getResultList();
		return listaOdd;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Date> getFechasDistinct()
	{
		List<Date> listaOdd=new ArrayList<Date>();
		Query query=entityMgr.createQuery("select DISTINCT(c.fecha) from GmbOperacionDiariaDatos c  order by c.fecha desc");
		listaOdd=query.getResultList();
		return listaOdd;
		
	}
	

	
	

}
