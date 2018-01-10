/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CargoEmpleadoDao;
/*   4:    */ import com.asinfo.as2.entities.CargoEmpleado;
/*   5:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCargoEmpleado;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioCargoEmpleadoImpl
/*  13:    */   implements ServicioCargoEmpleado
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private CargoEmpleadoDao cargoEmpleadoDao;
/*  17:    */   
/*  18:    */   public void guardar(CargoEmpleado cargoEmpleado)
/*  19:    */   {
/*  20: 44 */     this.cargoEmpleadoDao.guardar(cargoEmpleado);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(CargoEmpleado cargoEmpleado)
/*  24:    */   {
/*  25: 57 */     this.cargoEmpleadoDao.eliminar(cargoEmpleado);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public CargoEmpleado buscarPorId(int idCargoEmpleado)
/*  29:    */   {
/*  30: 69 */     return (CargoEmpleado)this.cargoEmpleadoDao.buscarPorId(Integer.valueOf(idCargoEmpleado));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<CargoEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 80 */     return this.cargoEmpleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<CargoEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 91 */     return this.cargoEmpleadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45:102 */     return this.cargoEmpleadoDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public CargoEmpleado cargarDetalle(int idCargoEmpleado)
/*  49:    */   {
/*  50:113 */     return this.cargoEmpleadoDao.cargarDetalle(idCargoEmpleado);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public CargoEmpleado buscarPorNombre(String nombre)
/*  54:    */   {
/*  55:124 */     return this.cargoEmpleadoDao.buscarPorNombre(nombre);
/*  56:    */   }
/*  57:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioCargoEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */