/*  1:   */ package com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.financiero.contabilidad.ReporteMovimientoBancarioDao;
/*  4:   */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  5:   */ import com.asinfo.as2.entities.MovimientoBancario;
/*  6:   */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteMovimientoBancario;
/*  7:   */ import java.util.Date;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.ejb.Stateless;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ public class ServicioReporteMovimientoBancariompl
/* 15:   */   implements ServicioReporteMovimientoBancario
/* 16:   */ {
/* 17:   */   @EJB
/* 18:   */   private ReporteMovimientoBancarioDao reporteMovimientoBancarioDao;
/* 19:   */   
/* 20:   */   public List<MovimientoBancario> getReporteMovimientoBancario(Date fechaDesde, Date fechaHasta, int idCuentaBancariaOrganizacion, int idFormaPago, int idConceptoContable, boolean indicadorEstadoAnulado, int idOrganizacion, boolean giradosNoCobrados, Map<String, String> filters, String tipoReporte)
/* 21:   */   {
/* 22:41 */     return this.reporteMovimientoBancarioDao.getReporteMovimientoBancario(fechaDesde, fechaHasta, idCuentaBancariaOrganizacion, idFormaPago, idConceptoContable, indicadorEstadoAnulado, idOrganizacion, giradosNoCobrados, filters, tipoReporte);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List getReporteChequesGiradosNoCobrados(Date fechaDesde, Date fechaHasta, CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 26:   */   {
/* 27:47 */     return this.reporteMovimientoBancarioDao.getReporteChequesGiradosNoCobrados(fechaDesde, fechaHasta, cuentaBancariaOrganizacion);
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl.ServicioReporteMovimientoBancariompl
 * JD-Core Version:    0.7.0.1
 */