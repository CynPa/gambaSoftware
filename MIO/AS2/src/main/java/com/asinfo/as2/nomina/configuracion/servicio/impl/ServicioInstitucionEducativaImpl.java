/*  1:   */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.InstitucionEducativaDao;
/*  4:   */ import com.asinfo.as2.entities.InstitucionEducativa;
/*  5:   */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioInstitucionEducativa;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioInstitucionEducativaImpl
/* 13:   */   implements ServicioInstitucionEducativa
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private transient InstitucionEducativaDao institucionEducativaDao;
/* 17:   */   
/* 18:   */   public void guardar(InstitucionEducativa institucionEducativa)
/* 19:   */   {
/* 20:40 */     this.institucionEducativaDao.guardar(institucionEducativa);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(InstitucionEducativa institucionEducativa)
/* 24:   */   {
/* 25:48 */     this.institucionEducativaDao.eliminar(institucionEducativa);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public InstitucionEducativa buscarPorId(int idInstitucionEducativa)
/* 29:   */   {
/* 30:56 */     return (InstitucionEducativa)this.institucionEducativaDao.buscarPorId(Integer.valueOf(idInstitucionEducativa));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<InstitucionEducativa> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:65 */     return this.institucionEducativaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<InstitucionEducativa> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:73 */     return this.institucionEducativaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:81 */     return this.institucionEducativaDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public InstitucionEducativa cargarDetalle(int idInstitucionEducativa)
/* 49:   */   {
/* 50:90 */     return null;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioInstitucionEducativaImpl
 * JD-Core Version:    0.7.0.1
 */