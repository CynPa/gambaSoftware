/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CentroTrabajoDao;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioCentroTrabajo;
/*   5:    */ import com.asinfo.as2.entities.CentroTrabajo;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioCentroTrabajoImpl
/*  13:    */   implements ServicioCentroTrabajo
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private CentroTrabajoDao centroTrabajoDao;
/*  17:    */   
/*  18:    */   public void guardar(CentroTrabajo centroTrabajo)
/*  19:    */   {
/*  20: 42 */     this.centroTrabajoDao.guardar(centroTrabajo);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(CentroTrabajo centroTrabajo)
/*  24:    */   {
/*  25: 52 */     this.centroTrabajoDao.eliminar(centroTrabajo);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public CentroTrabajo buscarPorId(int idCentroTrabajo)
/*  29:    */   {
/*  30: 63 */     return (CentroTrabajo)this.centroTrabajoDao.buscarPorId(Integer.valueOf(idCentroTrabajo));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<CentroTrabajo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 73 */     return this.centroTrabajoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<CentroTrabajo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 83 */     return this.centroTrabajoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45: 93 */     return this.centroTrabajoDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public CentroTrabajo cargarDetalle(int idCentroTrabajo)
/*  49:    */   {
/*  50:103 */     return this.centroTrabajoDao.cargarDetalle(idCentroTrabajo);
/*  51:    */   }
/*  52:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioCentroTrabajoImpl
 * JD-Core Version:    0.7.0.1
 */