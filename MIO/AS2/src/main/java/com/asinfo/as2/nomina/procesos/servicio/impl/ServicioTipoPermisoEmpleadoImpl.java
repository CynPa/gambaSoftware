/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.TipoPermisoEmpleadoDao;
/*   4:    */ import com.asinfo.as2.entities.TipoPermisoEmpleado;
/*   5:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioTipoPermisoEmpleado;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioTipoPermisoEmpleadoImpl
/*  13:    */   implements ServicioTipoPermisoEmpleado
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private TipoPermisoEmpleadoDao tipoPermisoEmpleadoDao;
/*  17:    */   
/*  18:    */   public void guardar(TipoPermisoEmpleado tipoPermisoEmpleado)
/*  19:    */   {
/*  20: 45 */     this.tipoPermisoEmpleadoDao.guardar(tipoPermisoEmpleado);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(TipoPermisoEmpleado tipoPermisoEmpleado)
/*  24:    */   {
/*  25: 57 */     this.tipoPermisoEmpleadoDao.eliminar(tipoPermisoEmpleado);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public TipoPermisoEmpleado buscarPorId(int idTipoPermisoEmpleado)
/*  29:    */   {
/*  30: 69 */     return (TipoPermisoEmpleado)this.tipoPermisoEmpleadoDao.buscarPorId(Integer.valueOf(idTipoPermisoEmpleado));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<TipoPermisoEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 80 */     return this.tipoPermisoEmpleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<TipoPermisoEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 92 */     return this.tipoPermisoEmpleadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45:103 */     return this.tipoPermisoEmpleadoDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioTipoPermisoEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */