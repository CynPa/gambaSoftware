/*  1:   */ package com.asinfo.as2.produccion.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.produccion.TarifaOperacionDao;
/*  4:   */ import com.asinfo.as2.entities.produccion.TarifaOperacion;
/*  5:   */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioTarifaOperacion;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioTarifaOperacionImpl
/* 13:   */   implements ServicioTarifaOperacion
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private TarifaOperacionDao tarifaOperacionDao;
/* 17:   */   
/* 18:   */   public void guardar(TarifaOperacion tarifaOperacion)
/* 19:   */   {
/* 20:42 */     this.tarifaOperacionDao.guardar(tarifaOperacion);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(TarifaOperacion tarifaOperacion)
/* 24:   */   {
/* 25:52 */     this.tarifaOperacionDao.eliminar(tarifaOperacion);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TarifaOperacion buscarPorId(int idTarifaOperacion)
/* 29:   */   {
/* 30:63 */     return (TarifaOperacion)this.tarifaOperacionDao.buscarPorId(Integer.valueOf(idTarifaOperacion));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<TarifaOperacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:73 */     return this.tarifaOperacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<TarifaOperacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:83 */     return this.tarifaOperacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:93 */     return this.tarifaOperacionDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.servicio.impl.ServicioTarifaOperacionImpl
 * JD-Core Version:    0.7.0.1
 */