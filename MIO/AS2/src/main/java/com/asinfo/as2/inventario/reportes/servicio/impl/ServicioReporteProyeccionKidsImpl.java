/*  1:   */ package com.asinfo.as2.inventario.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.reportes.inventario.ReporteProyeccionKidsDao;
/*  4:   */ import com.asinfo.as2.entities.Bodega;
/*  5:   */ import com.asinfo.as2.entities.InventarioProducto;
/*  6:   */ import com.asinfo.as2.entities.Producto;
/*  7:   */ import com.asinfo.as2.entities.ProductoMaterial;
/*  8:   */ import com.asinfo.as2.entities.clases.ProyeccionKid;
/*  9:   */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/* 10:   */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteProyeccionKids;
/* 11:   */ import java.math.BigDecimal;
/* 12:   */ import java.math.RoundingMode;
/* 13:   */ import java.util.ArrayList;
/* 14:   */ import java.util.Date;
/* 15:   */ import java.util.List;
/* 16:   */ import javax.ejb.EJB;
/* 17:   */ import javax.ejb.Stateless;
/* 18:   */ 
/* 19:   */ @Stateless
/* 20:   */ public class ServicioReporteProyeccionKidsImpl
/* 21:   */   implements ServicioReporteProyeccionKids
/* 22:   */ {
/* 23:   */   @EJB
/* 24:   */   private transient ReporteProyeccionKidsDao reporteProyeccionKidsDao;
/* 25:   */   @EJB
/* 26:   */   private transient ServicioInventarioProducto servicioInventarioProducto;
/* 27:   */   
/* 28:   */   public List<ProductoMaterial> getListaProductoMaterial(int idOrganizacion, Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta)
/* 29:   */   {
/* 30:52 */     return this.reporteProyeccionKidsDao.getListaProductoMaterial(idOrganizacion, producto, bodega, fechaDesde, fechaHasta);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<ProyeccionKid> getListaProyeccionKids(int idOrganizacion, Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta)
/* 34:   */   {
/* 35:62 */     List<ProyeccionKid> listaProyeccionKid = new ArrayList();
/* 36:63 */     for (ProductoMaterial productoMaterial : getListaProductoMaterial(0, null, null, null, null))
/* 37:   */     {
/* 38:64 */       ProyeccionKid proyeccionKid = new ProyeccionKid();
/* 39:65 */       proyeccionKid.setProductoMaterial(productoMaterial);
/* 40:66 */       proyeccionKid.setStock(getStock(productoMaterial.getMaterial(), null, null, null));
/* 41:67 */       proyeccionKid.setSaldo(proyeccionKid.getStock().divide(productoMaterial.getCantidad(), RoundingMode.HALF_UP));
/* 42:68 */       listaProyeccionKid.add(proyeccionKid);
/* 43:   */     }
/* 44:70 */     return listaProyeccionKid;
/* 45:   */   }
/* 46:   */   
/* 47:   */   private BigDecimal getStock(Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta)
/* 48:   */   {
/* 49:74 */     BigDecimal stock = BigDecimal.ZERO;
/* 50:75 */     for (InventarioProducto inventarioProducto : this.servicioInventarioProducto.obtenerMovimientos(producto.getIdOrganizacion(), producto, bodega, fechaDesde, fechaHasta)) {
/* 51:77 */       if (inventarioProducto.getOperacion() == 1) {
/* 52:78 */         stock = stock.add(inventarioProducto.getCantidad());
/* 53:79 */       } else if (inventarioProducto.getOperacion() == -1) {
/* 54:80 */         stock = stock.subtract(inventarioProducto.getCantidad());
/* 55:   */       }
/* 56:   */     }
/* 57:83 */     return stock;
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.impl.ServicioReporteProyeccionKidsImpl
 * JD-Core Version:    0.7.0.1
 */