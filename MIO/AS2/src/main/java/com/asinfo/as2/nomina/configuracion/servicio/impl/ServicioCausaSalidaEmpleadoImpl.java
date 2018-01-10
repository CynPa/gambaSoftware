/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CausaSalidaEmpleadoDao;
/*   4:    */ import com.asinfo.as2.entities.CausaSalidaEmpleado;
/*   5:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCausaSalidaEmpleado;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioCausaSalidaEmpleadoImpl
/*  13:    */   implements ServicioCausaSalidaEmpleado
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private CausaSalidaEmpleadoDao causaSalidaEmpleadoDao;
/*  17:    */   
/*  18:    */   public void guardar(CausaSalidaEmpleado causaSalidaEmpleado)
/*  19:    */   {
/*  20: 44 */     this.causaSalidaEmpleadoDao.guardar(causaSalidaEmpleado);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(CausaSalidaEmpleado causaSalidaEmpleado)
/*  24:    */   {
/*  25: 57 */     this.causaSalidaEmpleadoDao.eliminar(causaSalidaEmpleado);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public CausaSalidaEmpleado buscarPorId(int idCausaSalidaEmpleado)
/*  29:    */   {
/*  30: 71 */     return (CausaSalidaEmpleado)this.causaSalidaEmpleadoDao.buscarPorId(Integer.valueOf(idCausaSalidaEmpleado));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<CausaSalidaEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 85 */     return this.causaSalidaEmpleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<CausaSalidaEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 98 */     return this.causaSalidaEmpleadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45:111 */     return this.causaSalidaEmpleadoDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public CausaSalidaEmpleado cargarDetalle(int idCausaSalidaEmpleado)
/*  49:    */   {
/*  50:124 */     return null;
/*  51:    */   }
/*  52:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioCausaSalidaEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */