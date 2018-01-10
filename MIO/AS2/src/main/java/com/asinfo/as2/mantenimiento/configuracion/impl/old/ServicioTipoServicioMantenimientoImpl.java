/*  1:   */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.mantenimiento.old.TipoServicioMantenimientoDao;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.TipoServicioMantenimiento;
/*  5:   */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioTipoServicioMantenimiento;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioTipoServicioMantenimientoImpl
/* 13:   */   implements ServicioTipoServicioMantenimiento
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private TipoServicioMantenimientoDao tipoServicioMantenimientoDao;
/* 17:   */   
/* 18:   */   public void guardar(TipoServicioMantenimiento tipoServicioMantenimiento)
/* 19:   */   {
/* 20:43 */     this.tipoServicioMantenimientoDao.guardar(tipoServicioMantenimiento);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(TipoServicioMantenimiento tipoServicioMantenimiento)
/* 24:   */   {
/* 25:53 */     this.tipoServicioMantenimientoDao.eliminar(tipoServicioMantenimiento);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TipoServicioMantenimiento buscarPorId(int idTipoServicioMantenimiento)
/* 29:   */   {
/* 30:64 */     return (TipoServicioMantenimiento)this.tipoServicioMantenimientoDao.buscarPorId(Integer.valueOf(idTipoServicioMantenimiento));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<TipoServicioMantenimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:75 */     return this.tipoServicioMantenimientoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<TipoServicioMantenimiento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:85 */     return this.tipoServicioMantenimientoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:95 */     return this.tipoServicioMantenimientoDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioTipoServicioMantenimientoImpl
 * JD-Core Version:    0.7.0.1
 */