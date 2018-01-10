/*  1:   */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.mantenimiento.old.TipoCicloOperacionDao;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.TipoCicloOperacion;
/*  5:   */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioTipoCicloOperacion;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioTipoCicloOperacionImpl
/* 13:   */   implements ServicioTipoCicloOperacion
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private TipoCicloOperacionDao tipoCicloOperacionDao;
/* 17:   */   
/* 18:   */   public void guardar(TipoCicloOperacion tipoCicloOperacion)
/* 19:   */   {
/* 20:43 */     this.tipoCicloOperacionDao.guardar(tipoCicloOperacion);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(TipoCicloOperacion tipoCicloOperacion)
/* 24:   */   {
/* 25:53 */     this.tipoCicloOperacionDao.eliminar(tipoCicloOperacion);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TipoCicloOperacion buscarPorId(int idTipoCicloOperacion)
/* 29:   */   {
/* 30:64 */     return (TipoCicloOperacion)this.tipoCicloOperacionDao.buscarPorId(Integer.valueOf(idTipoCicloOperacion));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<TipoCicloOperacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:74 */     return this.tipoCicloOperacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<TipoCicloOperacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:84 */     return this.tipoCicloOperacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:94 */     return this.tipoCicloOperacionDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioTipoCicloOperacionImpl
 * JD-Core Version:    0.7.0.1
 */