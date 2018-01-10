/*  1:   */ package com.asinfo.as2.ventas.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.CanalDao;
/*  4:   */ import com.asinfo.as2.entities.Canal;
/*  5:   */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioCanalImpl
/* 13:   */   implements ServicioCanal
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private CanalDao canalDao;
/* 17:   */   
/* 18:   */   public void guardar(Canal canal)
/* 19:   */   {
/* 20:40 */     this.canalDao.guardar(canal);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(Canal canal)
/* 24:   */   {
/* 25:49 */     this.canalDao.eliminar(canal);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Canal buscarPorId(Integer idCanal)
/* 29:   */   {
/* 30:58 */     return (Canal)this.canalDao.buscarPorId(idCanal);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<Canal> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:66 */     return this.canalDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<Canal> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:74 */     return this.canalDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:82 */     return this.canalDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Canal cargarDetalle(int idCanal)
/* 49:   */   {
/* 50:90 */     return null;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.impl.ServicioCanalImpl
 * JD-Core Version:    0.7.0.1
 */