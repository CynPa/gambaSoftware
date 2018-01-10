/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.TipoDiscapacidadDao;
/*   4:    */ import com.asinfo.as2.entities.TipoDiscapacidad;
/*   5:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoDiscapacidad;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioTipoDiscapacidadImpl
/*  13:    */   implements ServicioTipoDiscapacidad
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private TipoDiscapacidadDao tipoDiscapacidadDao;
/*  17:    */   
/*  18:    */   public void guardar(TipoDiscapacidad tipoDiscapacidad)
/*  19:    */   {
/*  20: 44 */     this.tipoDiscapacidadDao.guardar(tipoDiscapacidad);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(TipoDiscapacidad tipoDiscapacidad)
/*  24:    */   {
/*  25: 56 */     this.tipoDiscapacidadDao.eliminar(tipoDiscapacidad);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public TipoDiscapacidad buscarPorId(int idTipoDiscapacidad)
/*  29:    */   {
/*  30: 68 */     return (TipoDiscapacidad)this.tipoDiscapacidadDao.buscarPorId(Integer.valueOf(idTipoDiscapacidad));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<TipoDiscapacidad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 81 */     return this.tipoDiscapacidadDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<TipoDiscapacidad> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 93 */     return this.tipoDiscapacidadDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45:105 */     return this.tipoDiscapacidadDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public TipoDiscapacidad cargarDetalle(int idTipoDiscapacidad)
/*  49:    */   {
/*  50:117 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public TipoDiscapacidad buscarPorNombre(String nombre)
/*  54:    */   {
/*  55:125 */     return this.tipoDiscapacidadDao.buscarPorNombre(nombre);
/*  56:    */   }
/*  57:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioTipoDiscapacidadImpl
 * JD-Core Version:    0.7.0.1
 */