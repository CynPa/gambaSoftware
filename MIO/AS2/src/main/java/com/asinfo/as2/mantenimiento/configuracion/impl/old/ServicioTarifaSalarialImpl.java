/*  1:   */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.mantenimiento.old.TarifaSalarialDao;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.TarifaSalarial;
/*  5:   */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioTarifaSalarial;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioTarifaSalarialImpl
/* 13:   */   implements ServicioTarifaSalarial
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private TarifaSalarialDao tarifaSalarialDao;
/* 17:   */   
/* 18:   */   public void guardar(TarifaSalarial tarifaSalarial)
/* 19:   */   {
/* 20:43 */     this.tarifaSalarialDao.guardar(tarifaSalarial);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(TarifaSalarial tarifaSalarial)
/* 24:   */   {
/* 25:53 */     this.tarifaSalarialDao.eliminar(tarifaSalarial);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TarifaSalarial buscarPorId(int idTarifaSalarial)
/* 29:   */   {
/* 30:64 */     return (TarifaSalarial)this.tarifaSalarialDao.buscarPorId(Integer.valueOf(idTarifaSalarial));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<TarifaSalarial> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:74 */     return this.tarifaSalarialDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<TarifaSalarial> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:84 */     return this.tarifaSalarialDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:94 */     return this.tarifaSalarialDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioTarifaSalarialImpl
 * JD-Core Version:    0.7.0.1
 */