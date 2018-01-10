/*  1:   */ package com.asinfo.as2.compras.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compras.reportes.servicio.ServicioReportePedidoProveedor;
/*  4:   */ import com.asinfo.as2.dao.reportes.compras.ReportePedidoProveedorDao;
/*  5:   */ import com.asinfo.as2.entities.DimensionContable;
/*  6:   */ import com.asinfo.as2.entities.Documento;
/*  7:   */ import com.asinfo.as2.entities.Empresa;
/*  8:   */ import com.asinfo.as2.enumeraciones.Estado;
/*  9:   */ import java.util.Date;
/* 10:   */ import java.util.List;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.ejb.Stateless;
/* 13:   */ 
/* 14:   */ @Stateless
/* 15:   */ public class ServicioReportePedidoProveedorImpl
/* 16:   */   implements ServicioReportePedidoProveedor
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   ReportePedidoProveedorDao reportePedidoProveedorDao;
/* 20:   */   
/* 21:   */   public List<Object[]> getReportePedidoProveedor(int idPedidoProveedor)
/* 22:   */   {
/* 23:33 */     return this.reportePedidoProveedorDao.getReportePedidoProveedor(idPedidoProveedor);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List<Object[]> getListaReportePedidoProveedor(int idOrganizacion, Date fechaDesde, Date fechaHasta, String numeroDesde, String numeroHasta, Empresa proveedor, Documento documento, boolean totalizado, boolean porProducto, DimensionContable dimensionContable, Integer numeroDimension, Estado estado)
/* 27:   */   {
/* 28:47 */     return this.reportePedidoProveedorDao.getListaReportePedidoProveedor(idOrganizacion, fechaDesde, fechaHasta, numeroDesde, numeroHasta, proveedor, documento, totalizado, porProducto, dimensionContable, numeroDimension, estado);
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.servicio.impl.ServicioReportePedidoProveedorImpl
 * JD-Core Version:    0.7.0.1
 */