/*   1:    */ package com.asinfo.as2.financiero.SRI.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GastoDeducibleSRIDao;
/*   4:    */ import com.asinfo.as2.entities.Empleado;
/*   5:    */ import com.asinfo.as2.entities.GastoDeducibleSRI;
/*   6:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioGastoDeducibleSRI;
/*   7:    */ import java.math.BigDecimal;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioGastoDeducibleSRIImpl
/*  15:    */   implements ServicioGastoDeducibleSRI
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private GastoDeducibleSRIDao gastoDeducibleSRIDao;
/*  19:    */   
/*  20:    */   public void guardar(GastoDeducibleSRI gastoDeducibleSRI)
/*  21:    */   {
/*  22: 45 */     this.gastoDeducibleSRIDao.guardar(gastoDeducibleSRI);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void eliminar(GastoDeducibleSRI gastoDeducibleSRI)
/*  26:    */   {
/*  27: 57 */     this.gastoDeducibleSRIDao.eliminar(gastoDeducibleSRI);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public GastoDeducibleSRI buscarPorId(int idGastoDeducibleSRI)
/*  31:    */   {
/*  32: 69 */     return (GastoDeducibleSRI)this.gastoDeducibleSRIDao.buscarPorId(Integer.valueOf(idGastoDeducibleSRI));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<GastoDeducibleSRI> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 81 */     return this.gastoDeducibleSRIDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<GastoDeducibleSRI> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 92 */     return this.gastoDeducibleSRIDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int contarPorCriterio(Map<String, String> filters)
/*  46:    */   {
/*  47:103 */     return this.gastoDeducibleSRIDao.contarPorCriterio(filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public GastoDeducibleSRI cargarDetalle(int idGastoDeducibleSRI)
/*  51:    */   {
/*  52:114 */     return this.gastoDeducibleSRIDao.cargarDetalle(idGastoDeducibleSRI);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<GastoDeducibleSRI> obtenerListaPorAnio(int anio, int idOrganizacion, int idEmpleado)
/*  56:    */   {
/*  57:125 */     for (Empleado empleado : this.gastoDeducibleSRIDao.obtenerGastoDededuciblesNuevos(anio, idOrganizacion))
/*  58:    */     {
/*  59:126 */       GastoDeducibleSRI gastoDeducibleSRI = new GastoDeducibleSRI();
/*  60:127 */       gastoDeducibleSRI.setEmpleado(empleado);
/*  61:128 */       gastoDeducibleSRI.setIdOrganizacion(idOrganizacion);
/*  62:129 */       gastoDeducibleSRI.setAnio(anio);
/*  63:130 */       this.gastoDeducibleSRIDao.guardar(gastoDeducibleSRI);
/*  64:    */     }
/*  65:133 */     return this.gastoDeducibleSRIDao.obtenerListaPorAnio(anio, idOrganizacion, idEmpleado);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public BigDecimal obtenerPorEmpleadoAnio(int idEmpleado, int anio)
/*  69:    */   {
/*  70:144 */     return this.gastoDeducibleSRIDao.obtenerPorEmpleadoAnio(idEmpleado, anio);
/*  71:    */   }
/*  72:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.impl.ServicioGastoDeducibleSRIImpl
 * JD-Core Version:    0.7.0.1
 */