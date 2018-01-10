/*  1:   */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.PropinaDao;
/*  4:   */ import com.asinfo.as2.entities.Propina;
/*  5:   */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPropina;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioPropinaImpl
/* 13:   */   implements ServicioPropina
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private PropinaDao propinaDao;
/* 17:   */   
/* 18:   */   public void guardar(Propina propina)
/* 19:   */   {
/* 20:41 */     this.propinaDao.guardar(propina);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(Propina propina) {}
/* 24:   */   
/* 25:   */   public Propina buscarPorId(int id)
/* 26:   */   {
/* 27:54 */     return null;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public List<Propina> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 31:   */   {
/* 32:60 */     return null;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public List<Propina> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 36:   */   {
/* 37:65 */     return this.propinaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int contarPorCriterio(Map<String, String> filters)
/* 41:   */   {
/* 42:70 */     return this.propinaDao.contarPorCriterio(filters);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Propina obtenerPorCodigo(String codigo)
/* 46:   */   {
/* 47:76 */     return null;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public Propina cargarDetalle(int idPropina)
/* 51:   */   {
/* 52:81 */     return this.propinaDao.cargarDetalle(idPropina);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioPropinaImpl
 * JD-Core Version:    0.7.0.1
 */