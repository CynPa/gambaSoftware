/*  1:   */ package com.asinfo.as2.compras.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteRecepcionProveedor;
/*  4:   */ import com.asinfo.as2.dao.reportes.compras.ReporteRecepcionProveedorDao;
/*  5:   */ import com.asinfo.as2.entities.Bodega;
/*  6:   */ import com.asinfo.as2.entities.CategoriaProducto;
/*  7:   */ import com.asinfo.as2.entities.Producto;
/*  8:   */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  9:   */ import java.util.Date;
/* 10:   */ import java.util.List;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.ejb.Stateless;
/* 13:   */ 
/* 14:   */ @Stateless
/* 15:   */ public class ServicioReporteRecepcionProveedorImpl
/* 16:   */   implements ServicioReporteRecepcionProveedor
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   ReporteRecepcionProveedorDao reporteRecepcionProveedorDao;
/* 20:   */   
/* 21:   */   public List getReporteRecepcionProveedor(int idRecepcionProveedor)
/* 22:   */   {
/* 23:40 */     return this.reporteRecepcionProveedorDao.getReporteRecepcionProveedor(idRecepcionProveedor);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List getReporteRecepcionProveedor(int idOrganizacion, int tipoReporte, int idEmpresa, Bodega bodega, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Date fechaDesde, Date fechaHasta)
/* 27:   */   {
/* 28:46 */     return this.reporteRecepcionProveedorDao.getReporteRecepcionProveedor(idOrganizacion, tipoReporte, idEmpresa, bodega, categoriaProducto, subcategoriaProducto, producto, fechaDesde, fechaHasta);
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.servicio.impl.ServicioReporteRecepcionProveedorImpl
 * JD-Core Version:    0.7.0.1
 */