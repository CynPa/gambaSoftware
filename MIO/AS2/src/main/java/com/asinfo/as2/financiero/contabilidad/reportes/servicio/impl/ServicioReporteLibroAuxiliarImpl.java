/*  1:   */ package com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.financiero.contabilidad.ReporteLibroAuxiliarDao;
/*  4:   */ import com.asinfo.as2.entities.CentroCosto;
/*  5:   */ import com.asinfo.as2.entities.CuentaContable;
/*  6:   */ import com.asinfo.as2.entities.DetalleAsiento;
/*  7:   */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteLibroAuxiliar;
/*  8:   */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  9:   */ import java.util.Date;
/* 10:   */ import java.util.List;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.ejb.Stateless;
/* 13:   */ 
/* 14:   */ @Stateless
/* 15:   */ public class ServicioReporteLibroAuxiliarImpl
/* 16:   */   implements ServicioReporteLibroAuxiliar
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   private ReporteLibroAuxiliarDao reporteLibroAuxiliarDao;
/* 20:   */   
/* 21:   */   public List<DetalleAsiento> getReporteLibroAuxiliar(int idCuentaContable, Date fechaDesde, Date fechaHasta, boolean indicadorNIIF, int idSucursal, CentroCosto centroCosto, int idOrganizacion)
/* 22:   */     throws ExcepcionAS2Financiero
/* 23:   */   {
/* 24:59 */     return this.reporteLibroAuxiliarDao.getReporteLibroAuxiliar(idCuentaContable, fechaDesde, fechaHasta, indicadorNIIF, idSucursal, centroCosto, idOrganizacion);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public List<DetalleAsiento> getReporteLibroAuxiliar(List<CuentaContable> listaCuentaContable, Date fechaDesde, Date fechaHasta, boolean indicadorNIIF, int idSucursal, String dimension, String valorDimension, int idOrganizacion)
/* 28:   */     throws ExcepcionAS2Financiero
/* 29:   */   {
/* 30:71 */     return this.reporteLibroAuxiliarDao.getReporteLibroAuxiliar(listaCuentaContable, fechaDesde, fechaHasta, indicadorNIIF, idSucursal, dimension, valorDimension, idOrganizacion);
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl.ServicioReporteLibroAuxiliarImpl
 * JD-Core Version:    0.7.0.1
 */