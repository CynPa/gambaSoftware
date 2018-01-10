/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.RubroOtraEmpresaDao;
/*   4:    */ import com.asinfo.as2.entities.RubroOtraEmpresa;
/*   5:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioRubroOtraEmpresa;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioRubroOtraEmpresaImpl
/*  13:    */   implements ServicioRubroOtraEmpresa
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private transient RubroOtraEmpresaDao rubroOtraEmpresaDao;
/*  17:    */   
/*  18:    */   public void guardar(RubroOtraEmpresa entidad)
/*  19:    */   {
/*  20: 42 */     this.rubroOtraEmpresaDao.guardar(entidad);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(RubroOtraEmpresa entidad)
/*  24:    */   {
/*  25: 53 */     this.rubroOtraEmpresaDao.eliminar(entidad);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public RubroOtraEmpresa buscarPorId(int idRubroOtraEmpresa)
/*  29:    */   {
/*  30: 63 */     return (RubroOtraEmpresa)this.rubroOtraEmpresaDao.buscarPorId(Integer.valueOf(idRubroOtraEmpresa));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<RubroOtraEmpresa> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 74 */     return this.rubroOtraEmpresaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<RubroOtraEmpresa> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 84 */     return this.rubroOtraEmpresaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45: 94 */     return this.rubroOtraEmpresaDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public RubroOtraEmpresa cargarDetalle(int idRubroOtraEmpresa)
/*  49:    */   {
/*  50:104 */     return this.rubroOtraEmpresaDao.cargarDetalle(idRubroOtraEmpresa);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public RubroOtraEmpresa buscarPorNombre(String nombre)
/*  54:    */   {
/*  55:115 */     return null;
/*  56:    */   }
/*  57:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioRubroOtraEmpresaImpl
 * JD-Core Version:    0.7.0.1
 */