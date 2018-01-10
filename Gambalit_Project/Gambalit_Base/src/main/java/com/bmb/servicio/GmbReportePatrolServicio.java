package com.bmb.servicio;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.gmb.eao.GmbReportPatrolEAO;
import com.gmb.modelo.GmbReportPatrol;

@Stateless
public class GmbReportePatrolServicio implements IGmbReportePatrolServicio {

	@EJB
	private GmbReportPatrolEAO reporteEAO;

	public void insertMasivo(List<GmbReportPatrol> reporte) {
		for (GmbReportPatrol gmbReportPatrol : reporte) {
GmbReportPatrol rp=new GmbReportPatrol();
			if (reporteEAO.getReportePatrullaxFecha(gmbReportPatrol.getTiempoRealArrivo())) {
				System.out.println("1  " + gmbReportPatrol.getTiempoRealArrivo());
				
				if(!reporteEAO.getReportePatrullaxFecha3(gmbReportPatrol.getTiempoPlaneado(), gmbReportPatrol.getPuntoChequeo()))
				{
					rp=reporteEAO.getSingleReportePatrol(gmbReportPatrol.getTiempoPlaneado(), gmbReportPatrol.getPuntoChequeo());
							if(rp.getIdReportPatrol()!=null)
							{
								System.out.println("3 "+gmbReportPatrol.getTiempoRealArrivo() );
								rp.setTiempoRealArrivo(gmbReportPatrol.getTiempoRealArrivo());
								reporteEAO.modificar(rp);
							}
				}
				else
				{
				reporteEAO.insert(gmbReportPatrol);
				}
			}
			// if(gmbReportPatrol.getTiempoRealArrivo().equals(null)
			// ||gmbReportPatrol.getTiempoRealArrivo()==null)
			else
				
			{
				System.out.println("2  " + gmbReportPatrol.getTiempoRealArrivo());
				if (reporteEAO.getReportePatrullaxFecha2(gmbReportPatrol.getTiempoPlaneado(),
						gmbReportPatrol.getPuntoChequeo())) {
					reporteEAO.insert(gmbReportPatrol);
				}
			}

		}

	}

	public List<GmbReportPatrol> consultarReportePatrulla() {
		return reporteEAO.consultarReportePatrulla();
	}

	public List<GmbReportPatrol> getReportePatrullaxFechas(Date fechaIni, Date fechaFin) {
		return reporteEAO.getReportePatrullaxFechas(fechaIni, fechaFin);
	}
}
