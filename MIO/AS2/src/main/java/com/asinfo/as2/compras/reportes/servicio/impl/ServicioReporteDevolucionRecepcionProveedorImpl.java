/*  1:   */ package com.asinfo.as2.compras.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteDevolucionRecepcionProveedor;
/*  4:   */ import com.asinfo.as2.dao.reportes.compras.ReporteDevolucionRecepcionProveedorDao;
/*  5:   */ import com.asinfo.as2.entities.CategoriaProducto;
/*  6:   */ import com.asinfo.as2.entities.Empresa;
/*  7:   */ import com.asinfo.as2.entities.Producto;
/*  8:   */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  9:   */ import java.util.Date;
/* 10:   */ import java.util.List;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.ejb.Stateless;
/* 13:   */ 
/* 14:   */ @Stateless
/* 15:   */ public class ServicioReporteDevolucionRecepcionProveedorImpl
/* 16:   */   implements ServicioReporteDevolucionRecepcionProveedor
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   ReporteDevolucionRecepcionProveedorDao reporteDevolucionRecepcionProveedorDao;
/* 20:   */   
/* 21:   */   public List getReporteDevolucionRecepcionProveedor(int idOrganizacion, Empresa empresa, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Date fechaDesde, Date fechaHasta)
/* 22:   */   {
/* 23:35 */     return this.reporteDevolucionRecepcionProveedorDao.getReporteDevolucionRecepcionProveedor(idOrganizacion, empresa, categoriaProducto, subcategoriaProducto, producto, fechaDesde, fechaHasta);
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.servicio.impl.ServicioReporteDevolucionRecepcionProveedorImpl
 * JD-Core Version:    0.7.0.1
 */