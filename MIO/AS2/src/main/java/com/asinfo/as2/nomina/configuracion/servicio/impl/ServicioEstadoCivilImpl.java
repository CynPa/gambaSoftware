/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.EstadoCivilDao;
/*   4:    */ import com.asinfo.as2.entities.EstadoCivil;
/*   5:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEstadoCivil;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioEstadoCivilImpl
/*  13:    */   implements ServicioEstadoCivil
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private EstadoCivilDao estadoCivilDao;
/*  17:    */   
/*  18:    */   public void guardar(EstadoCivil estadoCivil)
/*  19:    */   {
/*  20: 44 */     this.estadoCivilDao.guardar(estadoCivil);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(EstadoCivil estadoCivil)
/*  24:    */   {
/*  25: 57 */     this.estadoCivilDao.eliminar(estadoCivil);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public EstadoCivil buscarPorId(int idEstadoCivil)
/*  29:    */   {
/*  30: 71 */     return (EstadoCivil)this.estadoCivilDao.buscarPorId(Integer.valueOf(idEstadoCivil));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<EstadoCivil> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 83 */     return this.estadoCivilDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<EstadoCivil> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 95 */     return this.estadoCivilDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45:107 */     return this.estadoCivilDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public EstadoCivil cargarDetalle(int idEstadoCivil)
/*  49:    */   {
/*  50:119 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public EstadoCivil buscarPorNombre(String nombre)
/*  54:    */   {
/*  55:130 */     return this.estadoCivilDao.buscarPorNombre(nombre);
/*  56:    */   }
/*  57:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioEstadoCivilImpl
 * JD-Core Version:    0.7.0.1
 */