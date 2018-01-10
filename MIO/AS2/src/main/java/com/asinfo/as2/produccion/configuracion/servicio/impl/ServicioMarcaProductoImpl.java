/*  1:   */ package com.asinfo.as2.produccion.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.MarcaProductoDao;
/*  4:   */ import com.asinfo.as2.entities.MarcaProducto;
/*  5:   */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcaProducto;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioMarcaProductoImpl
/* 13:   */   implements ServicioMarcaProducto
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private MarcaProductoDao marcaProductoDao;
/* 17:   */   
/* 18:   */   public void guardar(MarcaProducto marcaProducto)
/* 19:   */   {
/* 20:28 */     this.marcaProductoDao.guardar(marcaProducto);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(MarcaProducto marcaProducto)
/* 24:   */   {
/* 25:34 */     this.marcaProductoDao.eliminar(marcaProducto);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public MarcaProducto buscarPorId(int id)
/* 29:   */   {
/* 30:40 */     return (MarcaProducto)this.marcaProductoDao.buscarPorId(Integer.valueOf(id));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public MarcaProducto cargarDetalle(int id)
/* 34:   */   {
/* 35:45 */     return (MarcaProducto)this.marcaProductoDao.cargarDetalle(id);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<MarcaProducto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean ordenar, Map<String, String> filters)
/* 39:   */   {
/* 40:52 */     return this.marcaProductoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public List<MarcaProducto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 44:   */   {
/* 45:58 */     return this.marcaProductoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int contarPorCriterio(Map<String, String> filters)
/* 49:   */   {
/* 50:63 */     return this.marcaProductoDao.contarPorCriterio(filters);
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.servicio.impl.ServicioMarcaProductoImpl
 * JD-Core Version:    0.7.0.1
 */