package com.bmb.servicio;

import java.util.Date;
import java.util.List;

import com.gmb.modelo.GmbReportPatrol;

public interface IGmbReportePatrolServicio {
	public void insertMasivo(List<GmbReportPatrol> reporte);
	public List<GmbReportPatrol> consultarReportePatrulla();
	public List<GmbReportPatrol> getReportePatrullaxFechas(Date fechaIni ,Date fechaFin);
}
