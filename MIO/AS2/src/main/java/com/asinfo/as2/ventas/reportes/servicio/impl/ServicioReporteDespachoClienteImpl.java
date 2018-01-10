/*  1:   */ package com.asinfo.as2.ventas.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.ventas.ReporteDespachoClienteDao;
/*  4:   */ import com.asinfo.as2.entities.Bodega;
/*  5:   */ import com.asinfo.as2.entities.CategoriaProducto;
/*  6:   */ import com.asinfo.as2.entities.Empresa;
/*  7:   */ import com.asinfo.as2.entities.Producto;
/*  8:   */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  9:   */ import com.asinfo.as2.entities.Subempresa;
/* 10:   */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteDespachoCliente;
/* 11:   */ import java.util.Date;
/* 12:   */ import java.util.List;
/* 13:   */ import javax.ejb.EJB;
/* 14:   */ import javax.ejb.Stateless;
/* 15:   */ 
/* 16:   */ @Stateless
/* 17:   */ public class ServicioReporteDespachoClienteImpl
/* 18:   */   implements ServicioReporteDespachoCliente
/* 19:   */ {
/* 20:   */   @EJB
/* 21:   */   private ReporteDespachoClienteDao reporteDespachoClienteDao;
/* 22:   */   
/* 23:   */   public List getReporteDespachoDetallado(int tipoReporte, Date fechaDesde, Date fechaHasta, int idEmpresa, int idResponsableSalidaMercaderia, Bodega bodega, Subempresa subempresa, int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto)
/* 24:   */   {
/* 25:51 */     return this.reporteDespachoClienteDao.getReporteDespachoDetallado(tipoReporte, fechaDesde, fechaHasta, idEmpresa, idResponsableSalidaMercaderia, bodega, subempresa, idOrganizacion, categoriaProducto, subcategoriaProducto, producto);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public List getReporteDespachoPrefacturaFactura(Date fechaDesde, Date fechaHasta, Empresa empresa, boolean indicadorDetallado)
/* 29:   */   {
/* 30:58 */     return this.reporteDespachoClienteDao.getReporteDespachoPrefacturaFactura(fechaDesde, fechaHasta, empresa, indicadorDetallado);
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.servicio.impl.ServicioReporteDespachoClienteImpl
 * JD-Core Version:    0.7.0.1
 */