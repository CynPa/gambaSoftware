/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.SubsidioEmpleadoDao;
/*   4:    */ import com.asinfo.as2.entities.Departamento;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.SubsidioEmpleado;
/*   7:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioSubsidioEmpleado;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ServicioSubsidioEmpleadoImpl
/*  16:    */   implements ServicioSubsidioEmpleado
/*  17:    */ {
/*  18:    */   @EJB
/*  19:    */   private transient SubsidioEmpleadoDao subsidioEmpleadoDao;
/*  20:    */   
/*  21:    */   public void guardar(SubsidioEmpleado subsidioEmpleado)
/*  22:    */   {
/*  23: 47 */     this.subsidioEmpleadoDao.guardar(subsidioEmpleado);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void eliminar(SubsidioEmpleado subsidioEmpleado)
/*  27:    */   {
/*  28: 59 */     this.subsidioEmpleadoDao.eliminar(subsidioEmpleado);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public SubsidioEmpleado buscarPorId(int idSubsidioEmpleado)
/*  32:    */   {
/*  33: 71 */     return (SubsidioEmpleado)this.subsidioEmpleadoDao.buscarPorId(Integer.valueOf(idSubsidioEmpleado));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List<SubsidioEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  37:    */   {
/*  38: 83 */     return this.subsidioEmpleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List<SubsidioEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  42:    */   {
/*  43: 94 */     return this.subsidioEmpleadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int contarPorCriterio(Map<String, String> filters)
/*  47:    */   {
/*  48:105 */     return this.subsidioEmpleadoDao.contarPorCriterio(filters);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public SubsidioEmpleado cargarDetalle(int idSubsidioEmpleado)
/*  52:    */   {
/*  53:116 */     return this.subsidioEmpleadoDao.cargarDetalle(idSubsidioEmpleado);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public List<SubsidioEmpleado> getSubsidioEmpleadoPorFecha(int idOrganizacion, Date fecha, Departamento departamento, Empleado empleado)
/*  57:    */   {
/*  58:121 */     return this.subsidioEmpleadoDao.getSubsidioEmpleadoPorFecha(idOrganizacion, fecha, departamento, empleado);
/*  59:    */   }
/*  60:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioSubsidioEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */