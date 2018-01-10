/*  1:   */ package com.asinfo.as2.compras.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compras.reportes.servicio.ServicioReportePedidoRecepcionFactura;
/*  4:   */ import com.asinfo.as2.dao.reportes.compras.ReportePedidoRecepcionFacturaDao;
/*  5:   */ import com.asinfo.as2.entities.Empresa;
/*  6:   */ import com.asinfo.as2.entities.PedidoProveedor;
/*  7:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  8:   */ import java.util.Date;
/*  9:   */ import java.util.List;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.ejb.Stateless;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ public class ServicioReportePedidoRecepcionFacturaImpl
/* 15:   */   implements ServicioReportePedidoRecepcionFactura
/* 16:   */ {
/* 17:   */   @EJB
/* 18:   */   private transient ReportePedidoRecepcionFacturaDao reportePedidoRecepcionFacturaDao;
/* 19:   */   
/* 20:   */   public List getReportePedidoRecepcionFactura(PedidoProveedor pedidoProveedor, Date fechaDesde, Date fechaHasta, Empresa proveedor)
/* 21:   */     throws ExcepcionAS2
/* 22:   */   {
/* 23:48 */     return this.reportePedidoRecepcionFacturaDao.getReportePedidoRecepcionFactura(pedidoProveedor, fechaDesde, fechaHasta, proveedor);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List getReporteRecepcionSinFactura(PedidoProveedor pedidoProveedor, Date fechaDesde, Date fechaHasta, Empresa proveedor)
/* 27:   */     throws ExcepcionAS2
/* 28:   */   {
/* 29:61 */     return this.reportePedidoRecepcionFacturaDao.getReporteRecepcionSinFactura(pedidoProveedor, fechaDesde, fechaHasta, proveedor);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public List getReportePedidoConFactura(PedidoProveedor pedidoProveedor, Date fechaDesde, Date fechaHasta, Empresa proveedor)
/* 33:   */     throws ExcepcionAS2
/* 34:   */   {
/* 35:74 */     return this.reportePedidoRecepcionFacturaDao.getReportePedidoConFactura(pedidoProveedor, fechaDesde, fechaHasta, proveedor);
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.servicio.impl.ServicioReportePedidoRecepcionFacturaImpl
 * JD-Core Version:    0.7.0.1
 */