package com.gmb.eao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.gmb.conexion.GmbConexion;
import com.gmb.modelo.GmbReportPatrol;

@Stateless
public class GmbReportPatrolEAO extends GmbConexion<GmbReportPatrol, Serializable> {

	public void insert(GmbReportPatrol reporte) {
		// for (GmbReportPatrol t : reporte) {
		entityMgr.persist(reporte);
		// }

	}

	public void modificar(GmbReportPatrol reporte) {
		entityMgr.merge(reporte);
	}

	@SuppressWarnings("unchecked")
	public List<GmbReportPatrol> consultarReportePatrulla() {

		List<GmbReportPatrol> patrullas = null;
		try {
			patrullas = new ArrayList<>();
			Query query = entityMgr.createNamedQuery("findAllPatrull");
			patrullas = (List<GmbReportPatrol>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return patrullas;
	}

	@SuppressWarnings("unchecked")
	public List<GmbReportPatrol> getReportePatrullaxFechas(Date fechaIni, Date fechaFin) {
		List<GmbReportPatrol> patrullas = null;
		try {
			patrullas = new ArrayList<>();
			Query query = entityMgr.createQuery(
					"select c from GmbReportPatrol c where c.tiempoRealArrivo between :FechaIni and :FechaFin");
			query.setParameter("FechaIni", fechaIni);
			query.setParameter("FechaFin", fechaFin);
			patrullas = (List<GmbReportPatrol>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return patrullas;
	}

	public Boolean getReportePatrullaxFecha2(String token, String puntochequeo) {
		Boolean bandera = true;
		Long patrulla;
		Integer patrulla2 = 0;
		try {
			if (puntochequeo != null) {
				Query query = entityMgr.createQuery(
						"select count(c) from GmbReportPatrol c where c.tiempoPlaneado = :token and c.puntoChequeo= :puntochequeo ");
				query.setParameter("token", token);
				query.setParameter("puntochequeo", puntochequeo);
				patrulla = (Long) query.getSingleResult();
				patrulla2 = patrulla.intValue();
				if (patrulla2 > 0) {
					bandera = false;

					// Query query2=entityMgr.createQuery()
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			bandera = false;
		}
		return bandera;

	}

	public Boolean getReportePatrullaxFecha3(String token, String puntochequeo) {
		Boolean bandera = true;
		Long patrulla;
		Integer patrulla2 = 0;
		try {
			if (puntochequeo != null) {
				Query query = entityMgr.createQuery(
						"select count(c) from GmbReportPatrol c where c.tiempoPlaneado = :token and c.puntoChequeo= :puntochequeo and c.tiempoRealArrivo is null");
				query.setParameter("token", token);
				query.setParameter("puntochequeo", puntochequeo);
				patrulla = (Long) query.getSingleResult();
				patrulla2 = patrulla.intValue();
				if (patrulla2 > 0) {
					bandera = false;

					// Query query2=entityMgr.createQuery()
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			bandera = false;
		}
		return bandera;

	}

	public Boolean getReportePatrullaxFecha(Date fecha) {
		Boolean bandera = true;
		Long patrulla;
		Integer patrulla2 = 0;
		try {

			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			String fechaString = formato.format(fecha);
			Date miFecha = formato.parse(fechaString);

			Query query = entityMgr
					.createQuery("select count(c) from GmbReportPatrol c where c.tiempoRealArrivo = :FechaIni ");
			query.setParameter("FechaIni", miFecha);
			// query.setParameter("token", token);
			patrulla = (Long) query.getSingleResult();
			patrulla2 = patrulla.intValue();
			if (patrulla2 > 0) {
				bandera = false;
			}
		} catch (Exception e) {
			bandera = false;
		}
		return bandera;
	}

	public GmbReportPatrol getSingleReportePatrol(String token, String punto) {
		GmbReportPatrol rp = new GmbReportPatrol();
		try { 
			if (punto != null) {
				Query query = entityMgr.createQuery(
						"select c from GmbReportPatrol c where c.tiempoPlaneado = :token and c.puntoChequeo= :puntochequeo");
				query.setParameter("token", token);
				query.setParameter("puntochequeo", punto);
				rp = (GmbReportPatrol) query.getSingleResult();
			}
		} catch (Exception e) {
			System.out.println("Error GmbReportPatrol.getSingleReportPatrol: " + e.toString());
		}
		return rp;
	}
}
