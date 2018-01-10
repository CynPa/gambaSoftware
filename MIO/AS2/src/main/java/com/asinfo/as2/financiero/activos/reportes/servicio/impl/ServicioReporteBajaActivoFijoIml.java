/*  1:   */ package com.asinfo.as2.financiero.activos.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.financiero.activos.ReporteActivoFijoDao;
/*  4:   */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteBajaActivoFijo;
/*  5:   */ import java.util.Date;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.EJB;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class ServicioReporteBajaActivoFijoIml
/* 12:   */   implements ServicioReporteBajaActivoFijo
/* 13:   */ {
/* 14:   */   @EJB
/* 15:   */   private ReporteActivoFijoDao reporteActivoFijoDao;
/* 16:   */   
/* 17:   */   public List getReporteBajaActivoFijo(int idMotivoBajaActivo, boolean indicadorDepreciacionFiscal, Date fechaDesde, Date fechaHasta)
/* 18:   */   {
/* 19:44 */     return this.reporteActivoFijoDao.getReporteBajaActivoFijo(idMotivoBajaActivo, indicadorDepreciacionFiscal, fechaDesde, fechaHasta);
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.reportes.servicio.impl.ServicioReporteBajaActivoFijoIml
 * JD-Core Version:    0.7.0.1
 */