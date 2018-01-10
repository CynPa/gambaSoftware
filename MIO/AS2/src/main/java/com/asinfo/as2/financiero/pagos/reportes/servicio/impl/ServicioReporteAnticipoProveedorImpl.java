/*  1:   */ package com.asinfo.as2.financiero.pagos.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.financiero.pagos.ReporteAnticipoProveedorDao;
/*  4:   */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  5:   */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReporteAnticipoProveedor;
/*  6:   */ import java.util.Date;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioReporteAnticipoProveedorImpl
/* 13:   */   implements ServicioReporteAnticipoProveedor
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private ReporteAnticipoProveedorDao reporteAnticipoProveedorDao;
/* 17:   */   
/* 18:   */   public List getReporteAnticipo(int idAnticipo)
/* 19:   */   {
/* 20:36 */     return this.reporteAnticipoProveedorDao.getReporteAnticipo(idAnticipo);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public List getReporteCorteFechaAnticipoProveedores(Date fechaDesde, Date fechaHasta, int idProveedor, boolean indicadorSaldoDiferenciaCero, int idOrganizacion, CategoriaEmpresa categoriaEmpresa, int idSucursal)
/* 24:   */   {
/* 25:43 */     return this.reporteAnticipoProveedorDao.getAnticipoProveedor(fechaDesde, fechaHasta, idProveedor, indicadorSaldoDiferenciaCero, idOrganizacion, categoriaEmpresa, idSucursal);
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.reportes.servicio.impl.ServicioReporteAnticipoProveedorImpl
 * JD-Core Version:    0.7.0.1
 */