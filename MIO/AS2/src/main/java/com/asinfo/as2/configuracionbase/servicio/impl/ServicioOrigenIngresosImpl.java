/*  1:   */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrigenIngresos;
/*  4:   */ import com.asinfo.as2.dao.OrigenIngresosDao;
/*  5:   */ import com.asinfo.as2.entities.OrigenIngresos;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioOrigenIngresosImpl
/* 13:   */   implements ServicioOrigenIngresos
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private OrigenIngresosDao origenIngresosDao;
/* 17:   */   
/* 18:   */   public void guardar(OrigenIngresos entidad)
/* 19:   */   {
/* 20:37 */     this.origenIngresosDao.guardar(entidad);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(OrigenIngresos entidad)
/* 24:   */   {
/* 25:48 */     this.origenIngresosDao.eliminar(entidad);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public OrigenIngresos buscarPorId(Integer id)
/* 29:   */   {
/* 30:59 */     return (OrigenIngresos)this.origenIngresosDao.buscarPorId(id);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<OrigenIngresos> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:65 */     return this.origenIngresosDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<OrigenIngresos> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:76 */     return this.origenIngresosDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:86 */     return this.origenIngresosDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioOrigenIngresosImpl
 * JD-Core Version:    0.7.0.1
 */