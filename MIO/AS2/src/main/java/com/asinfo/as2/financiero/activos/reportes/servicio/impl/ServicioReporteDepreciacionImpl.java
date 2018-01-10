/*  1:   */ package com.asinfo.as2.financiero.activos.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.financiero.activos.ReporteDepreciacionDao;
/*  4:   */ import com.asinfo.as2.entities.ActivoFijo;
/*  5:   */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteDepreciacion;
/*  6:   */ import java.util.Date;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioReporteDepreciacionImpl
/* 13:   */   implements ServicioReporteDepreciacion
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private ReporteDepreciacionDao reporteDepreciacionDao;
/* 17:   */   
/* 18:   */   public List getReporteDepreciacionMensualNIIF(ActivoFijo activoFijo, Date fechaDesde, Date fechaHasta, boolean indicadorDepreciacionFiscal, boolean activo)
/* 19:   */   {
/* 20:45 */     return this.reporteDepreciacionDao.getReporteDepreciacionMensualNIIF(activoFijo, fechaDesde, fechaHasta, indicadorDepreciacionFiscal, activo);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public List getReporteDepreciacionFiscalVsNIIF(ActivoFijo activoFijo, Date fechaDesde, Date fechaHasta, boolean activo, String dimension, String codigoDimension)
/* 24:   */   {
/* 25:58 */     return this.reporteDepreciacionDao.getReporteDepreciacionFiscalVsNIIF(activoFijo, fechaDesde, fechaHasta, activo, dimension, codigoDimension);
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.reportes.servicio.impl.ServicioReporteDepreciacionImpl
 * JD-Core Version:    0.7.0.1
 */