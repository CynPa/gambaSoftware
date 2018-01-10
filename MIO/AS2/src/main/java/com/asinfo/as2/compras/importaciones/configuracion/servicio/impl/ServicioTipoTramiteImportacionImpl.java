/*   1:    */ package com.asinfo.as2.compras.importaciones.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioTipoTramiteImportacion;
/*   4:    */ import com.asinfo.as2.dao.TipoTramiteImportacionDao;
/*   5:    */ import com.asinfo.as2.entities.TipoTramiteImportacion;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioTipoTramiteImportacionImpl
/*  13:    */   implements ServicioTipoTramiteImportacion
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private transient TipoTramiteImportacionDao tipoTramiteImportacionDao;
/*  17:    */   
/*  18:    */   public void guardar(TipoTramiteImportacion tipoTramiteImportacion)
/*  19:    */   {
/*  20: 42 */     this.tipoTramiteImportacionDao.guardar(tipoTramiteImportacion);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(TipoTramiteImportacion tipoTramiteImportacion)
/*  24:    */   {
/*  25: 53 */     this.tipoTramiteImportacionDao.eliminar(tipoTramiteImportacion);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public TipoTramiteImportacion buscarPorId(int idTipoTramiteImportacion)
/*  29:    */   {
/*  30: 63 */     return (TipoTramiteImportacion)this.tipoTramiteImportacionDao.buscarPorId(Integer.valueOf(idTipoTramiteImportacion));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<TipoTramiteImportacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 75 */     return this.tipoTramiteImportacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<TipoTramiteImportacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 85 */     return this.tipoTramiteImportacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45: 95 */     return this.tipoTramiteImportacionDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public TipoTramiteImportacion cargarDetalle(int idTipoTramiteImportacion)
/*  49:    */   {
/*  50:106 */     return null;
/*  51:    */   }
/*  52:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.impl.ServicioTipoTramiteImportacionImpl
 * JD-Core Version:    0.7.0.1
 */