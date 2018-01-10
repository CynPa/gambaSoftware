/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.mantenimiento.old.OrdenServicioMantenimientoDao;
/*   4:    */ import com.asinfo.as2.entities.mantenimiento.old.OrdenServicioMantenimiento;
/*   5:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioOrdenServicioMantenimiento;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioOrdenServicioMantenimientoImpl
/*  13:    */   implements ServicioOrdenServicioMantenimiento
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private OrdenServicioMantenimientoDao ordenServicioMantenimientoDao;
/*  17:    */   
/*  18:    */   public void guardar(OrdenServicioMantenimiento ordenServicioMantenimiento)
/*  19:    */   {
/*  20: 42 */     this.ordenServicioMantenimientoDao.guardar(ordenServicioMantenimiento);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(OrdenServicioMantenimiento ordenServicioMantenimiento)
/*  24:    */   {
/*  25: 52 */     this.ordenServicioMantenimientoDao.eliminar(ordenServicioMantenimiento);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public OrdenServicioMantenimiento buscarPorId(int idOrdenServicioMantenimiento)
/*  29:    */   {
/*  30: 63 */     return (OrdenServicioMantenimiento)this.ordenServicioMantenimientoDao.buscarPorId(Integer.valueOf(idOrdenServicioMantenimiento));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<OrdenServicioMantenimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 73 */     return this.ordenServicioMantenimientoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<OrdenServicioMantenimiento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 83 */     return this.ordenServicioMantenimientoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45: 93 */     return this.ordenServicioMantenimientoDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public OrdenServicioMantenimiento cargarDetalle(int idOrdenServicioMantenimiento)
/*  49:    */   {
/*  50:103 */     return this.ordenServicioMantenimientoDao.cargarDetalle(idOrdenServicioMantenimiento);
/*  51:    */   }
/*  52:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioOrdenServicioMantenimientoImpl
 * JD-Core Version:    0.7.0.1
 */