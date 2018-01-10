/*  1:   */ package com.asinfo.as2.financiero.activos.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.financiero.activos.ReporteCustodioActivoFijoDao;
/*  4:   */ import com.asinfo.as2.entities.Empresa;
/*  5:   */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteCustodioActivoFijo;
/*  6:   */ import java.util.Date;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioReporteCustodioActivoFijoImpl
/* 13:   */   implements ServicioReporteCustodioActivoFijo
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private ReporteCustodioActivoFijoDao reporteCustodioActivoFijoDao;
/* 17:   */   
/* 18:   */   public List getReporteCustodioActivoFijo(int idActivoFijo, Date fechaDesde, Date fechaHasta, Empresa cliente, Empresa empleado, int orden)
/* 19:   */   {
/* 20:45 */     return this.reporteCustodioActivoFijoDao.getReporteCustodioActivoFijo(idActivoFijo, fechaDesde, fechaHasta, cliente, empleado, orden);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public List getReporteCustodioActivo(int idCustodioActivoFijo)
/* 24:   */   {
/* 25:57 */     return this.reporteCustodioActivoFijoDao.getReporteCustodioActivo(idCustodioActivoFijo);
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.reportes.servicio.impl.ServicioReporteCustodioActivoFijoImpl
 * JD-Core Version:    0.7.0.1
 */