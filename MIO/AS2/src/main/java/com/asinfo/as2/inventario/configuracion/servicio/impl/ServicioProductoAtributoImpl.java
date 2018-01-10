/*  1:   */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.ProductoAtributoDao;
/*  4:   */ import com.asinfo.as2.entities.ProductoAtributo;
/*  5:   */ import com.asinfo.as2.entities.ValorAtributo;
/*  6:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProductoAtributo;
/*  7:   */ import java.util.HashMap;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.ejb.Stateless;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ public class ServicioProductoAtributoImpl
/* 15:   */   implements ServicioProductoAtributo
/* 16:   */ {
/* 17:   */   @EJB
/* 18:   */   private ProductoAtributoDao productoAtributoDao;
/* 19:   */   
/* 20:   */   public void guardar(ProductoAtributo productoAtributo)
/* 21:   */   {
/* 22:42 */     this.productoAtributoDao.guardar(productoAtributo);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void eliminar(ProductoAtributo productoAtributo)
/* 26:   */   {
/* 27:52 */     this.productoAtributoDao.eliminar(productoAtributo);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public ProductoAtributo buscarPorId(int id)
/* 31:   */   {
/* 32:62 */     return (ProductoAtributo)this.productoAtributoDao.buscarPorId(Integer.valueOf(id));
/* 33:   */   }
/* 34:   */   
/* 35:   */   public ProductoAtributo buscarPorAtributoProducto(int idProducto, int idAtributo)
/* 36:   */   {
/* 37:68 */     Map<String, String> filtros = new HashMap();
/* 38:69 */     filtros.put("producto.idProducto", "" + idProducto);
/* 39:70 */     filtros.put("atributo.idAtributo", "" + idAtributo);
/* 40:71 */     List<ProductoAtributo> lista = this.productoAtributoDao.obtenerListaCombo("valor", true, filtros);
/* 41:72 */     if (lista.size() == 0) {
/* 42:73 */       return null;
/* 43:   */     }
/* 44:75 */     return (ProductoAtributo)lista.get(0);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public List<ProductoAtributo> obtenerPorProducto(int idProducto)
/* 48:   */   {
/* 49:81 */     Map<String, String> filtros = new HashMap();
/* 50:82 */     filtros.put("producto.idProducto", "" + idProducto);
/* 51:83 */     List<ProductoAtributo> lista = this.productoAtributoDao.obtenerListaCombo("valor", true, filtros);
/* 52:84 */     return lista;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void actualizarProductoAtributo(ValorAtributo valorAtributo)
/* 56:   */   {
/* 57:89 */     this.productoAtributoDao.actualizarProductoAtributo(valorAtributo);
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoAtributoImpl
 * JD-Core Version:    0.7.0.1
 */