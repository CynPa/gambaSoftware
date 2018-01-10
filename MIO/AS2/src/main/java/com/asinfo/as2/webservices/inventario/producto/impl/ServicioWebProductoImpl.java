/*  1:   */ package com.asinfo.as2.webservices.inventario.producto.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  4:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  5:   */ import com.asinfo.as2.webservices.inventario.producto.ServicioWebProducto;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.TransactionManagement;
/* 10:   */ import javax.ejb.TransactionManagementType;
/* 11:   */ import javax.jws.WebService;
/* 12:   */ 
/* 13:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.inventario.producto.ServicioWebProducto")
/* 14:   */ @TransactionManagement(TransactionManagementType.CONTAINER)
/* 15:   */ public class ServicioWebProductoImpl
/* 16:   */   implements ServicioWebProducto
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   private transient ServicioProducto servicioProducto;
/* 20:   */   
/* 21:   */   public List<com.asinfo.pos.model.Producto> getListaProducto()
/* 22:   */   {
/* 23:31 */     List<com.asinfo.pos.model.Producto> listaProducto = new ArrayList();
/* 24:32 */     for (com.asinfo.as2.entities.Producto productoAs2 : this.servicioProducto.obtenerListaPos("", false, null))
/* 25:   */     {
/* 26:33 */       com.asinfo.pos.model.Producto productoPos = new com.asinfo.pos.model.Producto();
/* 27:34 */       productoPos.setActivo(true);
/* 28:35 */       com.asinfo.pos.model.CategoriaProducto categoriaProducto = new com.asinfo.pos.model.CategoriaProducto();
/* 29:   */       
/* 30:   */ 
/* 31:   */ 
/* 32:   */ 
/* 33:40 */       categoriaProducto.setActivo(true);
/* 34:41 */       categoriaProducto.setCodigo(productoAs2.getSubcategoriaProducto().getCategoriaProducto().getCodigo());
/* 35:42 */       categoriaProducto.setDescripcion(productoAs2.getSubcategoriaProducto().getCategoriaProducto().getDescripcion());
/* 36:43 */       categoriaProducto.setNombre(productoAs2.getSubcategoriaProducto().getCategoriaProducto().getNombre());
/* 37:44 */       categoriaProducto.setPredeterminado(false);
/* 38:45 */       categoriaProducto.setTexto("");
/* 39:46 */       categoriaProducto.setIdCategoriaProductoAs2(productoAs2.getSubcategoriaProducto().getCategoriaProducto().getId());
/* 40:47 */       productoPos.setCategoriaProducto(categoriaProducto);
/* 41:48 */       productoPos.setCodigo(productoAs2.getCodigo());
/* 42:49 */       productoPos.setDescripcion(productoAs2.getDescripcion());
/* 43:50 */       productoPos.setNombre(productoAs2.getNombre());
/* 44:51 */       productoPos.setPrecioUnitario(productoAs2.getPrecioReferencialVenta());
/* 45:52 */       productoPos.setPredeterminado(productoAs2.isPredeterminado());
/* 46:53 */       productoPos.setActivo(productoAs2.isActivo());
/* 47:54 */       productoPos.setIndicadorImpuesto(productoAs2.isIndicadorImpuestos());
/* 48:55 */       productoPos.setNombreComercial(productoAs2.getNombreComercial());
/* 49:   */       
/* 50:   */ 
/* 51:   */ 
/* 52:59 */       com.asinfo.pos.model.Unidad unidad = new com.asinfo.pos.model.Unidad();
/* 53:60 */       unidad.setActivo(true);
/* 54:61 */       unidad.setCodigo(productoAs2.getUnidadVenta().getCodigo());
/* 55:62 */       unidad.setDescripcion(productoAs2.getUnidadVenta().getDescripcion());
/* 56:63 */       unidad.setNombre(productoAs2.getUnidadVenta().getNombre());
/* 57:64 */       unidad.setPredeterminado(false);
/* 58:65 */       unidad.setTexto("");
/* 59:66 */       unidad.setIdUnidadAs2(productoAs2.getUnidadVenta().getId());
/* 60:67 */       productoPos.setUnidad(unidad);
/* 61:68 */       productoPos.setIdProductoAs2(productoAs2.getId());
/* 62:69 */       listaProducto.add(productoPos);
/* 63:   */     }
/* 64:71 */     return listaProducto;
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.inventario.producto.impl.ServicioWebProductoImpl
 * JD-Core Version:    0.7.0.1
 */