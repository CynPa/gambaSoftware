/*  1:   */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.DetalleVisualizacionDao;
/*  4:   */ import com.asinfo.as2.entities.DetalleVisualizacion;
/*  5:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioDetalleVisualizacion;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioDetalleVisualizacionImpl
/* 13:   */   implements ServicioDetalleVisualizacion
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private DetalleVisualizacionDao detalleVisualizacionDao;
/* 17:   */   
/* 18:   */   public void guardar(DetalleVisualizacion detalleVisualizacion)
/* 19:   */   {
/* 20:34 */     this.detalleVisualizacionDao.guardar(detalleVisualizacion);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(DetalleVisualizacion detalleVisualizacion)
/* 24:   */   {
/* 25:38 */     this.detalleVisualizacionDao.eliminar(detalleVisualizacion);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public DetalleVisualizacion buscarPorId(int id)
/* 29:   */   {
/* 30:43 */     return (DetalleVisualizacion)this.detalleVisualizacionDao.buscarPorId(Integer.valueOf(id));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public DetalleVisualizacion cargarDetalle(int idVisualizacionSubcategoriaProducto)
/* 34:   */   {
/* 35:49 */     return (DetalleVisualizacion)this.detalleVisualizacionDao.cargarDetalle(idVisualizacionSubcategoriaProducto);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<DetalleVisualizacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:55 */     return this.detalleVisualizacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:60 */     return this.detalleVisualizacionDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioDetalleVisualizacionImpl
 * JD-Core Version:    0.7.0.1
 */