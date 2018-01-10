/*  1:   */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.TipoBodegaDao;
/*  4:   */ import com.asinfo.as2.entities.TipoBodega;
/*  5:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTipoBodega;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioTipoBodegaImpl
/* 13:   */   implements ServicioTipoBodega
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private TipoBodegaDao tipoBodegaDao;
/* 17:   */   
/* 18:   */   public void guardar(TipoBodega tipoBodega)
/* 19:   */   {
/* 20:42 */     this.tipoBodegaDao.guardar(tipoBodega);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(TipoBodega tipoBodega)
/* 24:   */   {
/* 25:52 */     this.tipoBodegaDao.eliminar(tipoBodega);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TipoBodega buscarPorId(Integer id)
/* 29:   */   {
/* 30:63 */     return (TipoBodega)this.tipoBodegaDao.buscarPorId(id);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<TipoBodega> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:68 */     return this.tipoBodegaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<TipoBodega> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:78 */     return this.tipoBodegaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:88 */     return this.tipoBodegaDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioTipoBodegaImpl
 * JD-Core Version:    0.7.0.1
 */