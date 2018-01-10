/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.mantenimiento.old.ProcedimientoActividadDao;
/*   4:    */ import com.asinfo.as2.dao.mantenimiento.old.ProcedimientoDao;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.old.Procedimiento;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.old.ProcedimientoActividad;
/*   7:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioProcedimiento;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioProcedimientoImpl
/*  15:    */   implements ServicioProcedimiento
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private ProcedimientoDao procedimientoDao;
/*  19:    */   @EJB
/*  20:    */   private ProcedimientoActividadDao procedimientoActividadDao;
/*  21:    */   
/*  22:    */   public void guardar(Procedimiento procedimiento)
/*  23:    */   {
/*  24: 47 */     for (ProcedimientoActividad procedimientoActividad : procedimiento.getListaProcedimientoActividad()) {
/*  25: 48 */       this.procedimientoActividadDao.guardar(procedimientoActividad);
/*  26:    */     }
/*  27: 51 */     this.procedimientoDao.guardar(procedimiento);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void eliminar(Procedimiento procedimiento)
/*  31:    */   {
/*  32: 61 */     this.procedimientoDao.eliminar(procedimiento);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Procedimiento buscarPorId(int idServicioMantenimiento)
/*  36:    */   {
/*  37: 72 */     return (Procedimiento)this.procedimientoDao.buscarPorId(Integer.valueOf(idServicioMantenimiento));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<Procedimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 82 */     return this.procedimientoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<Procedimiento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 92 */     return this.procedimientoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int contarPorCriterio(Map<String, String> filters)
/*  51:    */   {
/*  52:102 */     return this.procedimientoDao.contarPorCriterio(filters);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Procedimiento cargarDetalle(int idServicioMantenimiento)
/*  56:    */   {
/*  57:112 */     return this.procedimientoDao.cargarDetalle(idServicioMantenimiento);
/*  58:    */   }
/*  59:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioProcedimientoImpl
 * JD-Core Version:    0.7.0.1
 */