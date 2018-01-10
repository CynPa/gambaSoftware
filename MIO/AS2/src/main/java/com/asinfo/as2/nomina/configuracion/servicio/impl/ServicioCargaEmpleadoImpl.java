/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CargaEmpleadoDao;
/*   4:    */ import com.asinfo.as2.dao.EmpleadoDao;
/*   5:    */ import com.asinfo.as2.entities.CargaEmpleado;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCargaEmpleado;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ServicioCargaEmpleadoImpl
/*  17:    */   implements ServicioCargaEmpleado
/*  18:    */ {
/*  19:    */   @EJB
/*  20:    */   private CargaEmpleadoDao cargaEmpleadoDao;
/*  21:    */   @EJB
/*  22:    */   private EmpleadoDao empleadoDao;
/*  23:    */   
/*  24:    */   public void guardar(Empleado empleado)
/*  25:    */   {
/*  26: 50 */     int numeroCargasActivas = 0;
/*  27: 51 */     for (CargaEmpleado cargaEmpleado : empleado.getListaCargaEmpleado())
/*  28:    */     {
/*  29: 52 */       if ((cargaEmpleado.isActivo()) && (!cargaEmpleado.isEliminado())) {
/*  30: 53 */         numeroCargasActivas++;
/*  31:    */       }
/*  32: 55 */       if (cargaEmpleado.getTipoDiscapacidad() == null) {
/*  33: 58 */         cargaEmpleado.setPorcentajeDiscapacidad(BigDecimal.ZERO);
/*  34:    */       }
/*  35: 61 */       this.cargaEmpleadoDao.guardar(cargaEmpleado);
/*  36:    */     }
/*  37: 64 */     empleado.setNumeroCargasActivas(numeroCargasActivas);
/*  38: 65 */     this.empleadoDao.guardar(empleado);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void guardar(CargaEmpleado cargaEmpleado)
/*  42:    */   {
/*  43: 69 */     this.cargaEmpleadoDao.guardar(cargaEmpleado);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void eliminar(CargaEmpleado cargaEmpleado)
/*  47:    */   {
/*  48: 81 */     this.cargaEmpleadoDao.eliminar(cargaEmpleado);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public CargaEmpleado buscarPorId(int idCargaEmpleado)
/*  52:    */   {
/*  53: 93 */     return (CargaEmpleado)this.cargaEmpleadoDao.buscarPorId(Integer.valueOf(idCargaEmpleado));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public List<CargaEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  57:    */   {
/*  58:104 */     return this.cargaEmpleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<CargaEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  62:    */   {
/*  63:115 */     return this.cargaEmpleadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int contarPorCriterio(Map<String, String> filters)
/*  67:    */   {
/*  68:126 */     return this.cargaEmpleadoDao.contarPorCriterio(filters);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public CargaEmpleado cargarDetalle(int idCargaEmpleado)
/*  72:    */   {
/*  73:137 */     return this.cargaEmpleadoDao.cargarDetalle(idCargaEmpleado);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void actualizaCargasActivas(int idOrganizacion, Date fecha)
/*  77:    */   {
/*  78:142 */     this.cargaEmpleadoDao.actualizaCargasActivas(idOrganizacion, fecha);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void actualizaIndicadorActivo(int idCargaEmpleado, boolean indicador)
/*  82:    */   {
/*  83:147 */     this.cargaEmpleadoDao.actualizaIndicadorActivo(idCargaEmpleado, indicador);
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioCargaEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */