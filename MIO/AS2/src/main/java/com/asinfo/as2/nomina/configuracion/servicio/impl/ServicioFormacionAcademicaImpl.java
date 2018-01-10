/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.FormacionAcademicaDao;
/*   4:    */ import com.asinfo.as2.entities.FormacionAcademica;
/*   5:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioFormacionAcademica;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioFormacionAcademicaImpl
/*  13:    */   implements ServicioFormacionAcademica
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private FormacionAcademicaDao formacionAcademicaDao;
/*  17:    */   
/*  18:    */   public void guardar(FormacionAcademica formacionAcademica)
/*  19:    */   {
/*  20: 44 */     this.formacionAcademicaDao.guardar(formacionAcademica);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(FormacionAcademica formacionAcademica)
/*  24:    */   {
/*  25: 56 */     this.formacionAcademicaDao.eliminar(formacionAcademica);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public FormacionAcademica buscarPorId(int idformacionAcademica)
/*  29:    */   {
/*  30: 66 */     return (FormacionAcademica)this.formacionAcademicaDao.buscarPorId(Integer.valueOf(idformacionAcademica));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<FormacionAcademica> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 78 */     return this.formacionAcademicaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<FormacionAcademica> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 89 */     return this.formacionAcademicaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45: 99 */     return this.formacionAcademicaDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public FormacionAcademica cargarDetalle(int idFormacionAcademica)
/*  49:    */   {
/*  50:109 */     return null;
/*  51:    */   }
/*  52:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioFormacionAcademicaImpl
 * JD-Core Version:    0.7.0.1
 */