/*  1:   */ package com.asinfo.as2.compras.importaciones.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioGastoImportacion;
/*  4:   */ import com.asinfo.as2.dao.GastoImportacionDao;
/*  5:   */ import com.asinfo.as2.entities.GastoImportacion;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioGastoImportacionImpl
/* 13:   */   implements ServicioGastoImportacion
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private transient GastoImportacionDao gastoImportacionDao;
/* 17:   */   
/* 18:   */   public void guardar(GastoImportacion gastoImportacion)
/* 19:   */   {
/* 20:40 */     this.gastoImportacionDao.guardar(gastoImportacion);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(GastoImportacion gastoImportacion)
/* 24:   */   {
/* 25:48 */     this.gastoImportacionDao.eliminar(gastoImportacion);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public GastoImportacion buscarPorId(int idGastoImportacion)
/* 29:   */   {
/* 30:56 */     return (GastoImportacion)this.gastoImportacionDao.buscarPorId(Integer.valueOf(idGastoImportacion));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<GastoImportacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:64 */     return this.gastoImportacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<GastoImportacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:72 */     return this.gastoImportacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:80 */     return this.gastoImportacionDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public GastoImportacion cargarDetalle(int idDocumentoGastoImportacion)
/* 49:   */   {
/* 50:88 */     return null;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.impl.ServicioGastoImportacionImpl
 * JD-Core Version:    0.7.0.1
 */