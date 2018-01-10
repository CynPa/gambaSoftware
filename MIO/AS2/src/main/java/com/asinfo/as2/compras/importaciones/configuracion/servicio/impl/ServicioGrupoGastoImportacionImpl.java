/*  1:   */ package com.asinfo.as2.compras.importaciones.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioGrupoGastoImportacion;
/*  4:   */ import com.asinfo.as2.dao.GrupoGastoImportacionDao;
/*  5:   */ import com.asinfo.as2.entities.GrupoGastoImportacion;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioGrupoGastoImportacionImpl
/* 13:   */   implements ServicioGrupoGastoImportacion
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private transient GrupoGastoImportacionDao grupoGastoImportacionDao;
/* 17:   */   
/* 18:   */   public void guardar(GrupoGastoImportacion grupoGastoImportacion)
/* 19:   */   {
/* 20:40 */     this.grupoGastoImportacionDao.guardar(grupoGastoImportacion);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(GrupoGastoImportacion grupoGastoImportacion)
/* 24:   */   {
/* 25:49 */     this.grupoGastoImportacionDao.eliminar(grupoGastoImportacion);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public GrupoGastoImportacion buscarPorId(int idGrupoGastoImportacion)
/* 29:   */   {
/* 30:57 */     return (GrupoGastoImportacion)this.grupoGastoImportacionDao.buscarPorId(Integer.valueOf(idGrupoGastoImportacion));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<GrupoGastoImportacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:65 */     return this.grupoGastoImportacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<GrupoGastoImportacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:73 */     return this.grupoGastoImportacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:81 */     return this.grupoGastoImportacionDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public GrupoGastoImportacion cargarDetalle(int idGrupoGastoImportacion)
/* 49:   */   {
/* 50:89 */     return null;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.impl.ServicioGrupoGastoImportacionImpl
 * JD-Core Version:    0.7.0.1
 */