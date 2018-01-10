/*  1:   */ package com.asinfo.as2.webservices.inventario.configuracion.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  4:   */ import com.asinfo.as2.webservices.inventario.configuracion.ServicioWebCategoriaProducto;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.jws.WebService;
/* 11:   */ 
/* 12:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.inventario.configuracion.ServicioWebCategoriaProducto")
/* 13:   */ public class ServicioWebCategoriaProductoImpl
/* 14:   */   implements ServicioWebCategoriaProducto
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private transient ServicioCategoriaProducto servicioCategoriaProducto;
/* 18:   */   
/* 19:   */   public List<com.asinfo.pos.model.CategoriaProducto> getListaCategoriaProducto(int idOrganizacion)
/* 20:   */   {
/* 21:23 */     Map<String, String> filtros = new HashMap();
/* 22:24 */     filtros.put("idOrganizacion", String.valueOf(idOrganizacion));
/* 23:   */     
/* 24:26 */     List<com.asinfo.pos.model.CategoriaProducto> lista = new ArrayList();
/* 25:   */     
/* 26:28 */     List<com.asinfo.as2.entities.CategoriaProducto> datos = this.servicioCategoriaProducto.obtenerListaCombo("", false, null);
/* 27:29 */     for (com.asinfo.as2.entities.CategoriaProducto categoriaProducto : datos)
/* 28:   */     {
/* 29:30 */       com.asinfo.pos.model.CategoriaProducto categoriaProductoPos = new com.asinfo.pos.model.CategoriaProducto();
/* 30:31 */       categoriaProductoPos.setActivo(categoriaProducto.getActivo());
/* 31:32 */       categoriaProductoPos.setCodigo(categoriaProducto.getCodigo());
/* 32:33 */       categoriaProductoPos.setDescripcion(categoriaProducto.getDescripcion());
/* 33:34 */       categoriaProductoPos.setIdCategoriaProductoAs2(categoriaProducto.getIdCategoriaProducto());
/* 34:35 */       categoriaProductoPos.setNombre(categoriaProducto.getNombre());
/* 35:36 */       categoriaProductoPos.setPredeterminado(categoriaProducto.getPredeterminado());
/* 36:37 */       lista.add(categoriaProductoPos);
/* 37:   */     }
/* 38:40 */     return lista;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.inventario.configuracion.impl.ServicioWebCategoriaProductoImpl
 * JD-Core Version:    0.7.0.1
 */