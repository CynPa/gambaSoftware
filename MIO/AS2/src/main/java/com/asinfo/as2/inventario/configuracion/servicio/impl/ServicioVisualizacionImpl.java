/*  1:   */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.VisualizacionDao;
/*  4:   */ import com.asinfo.as2.entities.DetalleVisualizacion;
/*  5:   */ import com.asinfo.as2.entities.Visualizacion;
/*  6:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioDetalleVisualizacion;
/*  7:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioVisualizacion;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.ejb.Stateless;
/* 12:   */ 
/* 13:   */ @Stateless
/* 14:   */ public class ServicioVisualizacionImpl
/* 15:   */   implements ServicioVisualizacion
/* 16:   */ {
/* 17:   */   @EJB
/* 18:   */   private VisualizacionDao visualizacionDao;
/* 19:   */   @EJB
/* 20:   */   private ServicioDetalleVisualizacion servicioDetalleVisualizacion;
/* 21:   */   
/* 22:   */   public void guardar(Visualizacion visualizacion)
/* 23:   */   {
/* 24:39 */     this.visualizacionDao.guardar(visualizacion);
/* 25:41 */     for (DetalleVisualizacion vscp : visualizacion.getListaDetalleVisualizacion()) {
/* 26:42 */       this.servicioDetalleVisualizacion.guardar(vscp);
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void eliminar(Visualizacion visualizacion)
/* 31:   */   {
/* 32:47 */     this.visualizacionDao.eliminar(visualizacion);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Visualizacion buscarPorId(int id)
/* 36:   */   {
/* 37:52 */     return (Visualizacion)this.visualizacionDao.buscarPorId(Integer.valueOf(id));
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Visualizacion cargarDetalle(int idVisualizacion)
/* 41:   */   {
/* 42:58 */     return this.visualizacionDao.cargarDetalle(idVisualizacion);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public List<Visualizacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 46:   */   {
/* 47:63 */     return this.visualizacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public int contarPorCriterio(Map<String, String> filters)
/* 51:   */   {
/* 52:69 */     return this.visualizacionDao.contarPorCriterio(filters);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioVisualizacionImpl
 * JD-Core Version:    0.7.0.1
 */