/*  1:   */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.mantenimiento.old.CriticidadDao;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.Criticidad;
/*  5:   */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioCriticidad;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioCriticidadImpl
/* 13:   */   implements ServicioCriticidad
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private CriticidadDao criticidadDao;
/* 17:   */   
/* 18:   */   public void guardar(Criticidad criticidad)
/* 19:   */   {
/* 20:43 */     this.criticidadDao.guardar(criticidad);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(Criticidad criticidad)
/* 24:   */   {
/* 25:53 */     this.criticidadDao.eliminar(criticidad);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Criticidad buscarPorId(int idCriticidad)
/* 29:   */   {
/* 30:64 */     return (Criticidad)this.criticidadDao.buscarPorId(Integer.valueOf(idCriticidad));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<Criticidad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:74 */     return this.criticidadDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<Criticidad> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:84 */     return this.criticidadDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:94 */     return this.criticidadDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioCriticidadImpl
 * JD-Core Version:    0.7.0.1
 */