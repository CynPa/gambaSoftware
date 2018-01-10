/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.EmpleadoDao;
/*   4:    */ import com.asinfo.as2.entities.Departamento;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.Rubro;
/*   7:    */ import com.asinfo.as2.entities.nomina.asistencia.GrupoTrabajo;
/*   8:    */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/*   9:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  10:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleadoRemote;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.ejb.Stateless;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ServicioEmpleadoImpl
/*  19:    */   implements ServicioEmpleado, ServicioEmpleadoRemote
/*  20:    */ {
/*  21:    */   @EJB
/*  22:    */   private EmpleadoDao empleadoDao;
/*  23:    */   
/*  24:    */   public List<Empleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  25:    */   {
/*  26: 48 */     return this.empleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int contarPorCriterio(Map<String, String> filters)
/*  30:    */   {
/*  31: 58 */     return this.empleadoDao.contarPorCriterio(filters);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<Empleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  35:    */   {
/*  36: 68 */     return this.empleadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Empleado cargarDetalle(int idEmpleado)
/*  40:    */   {
/*  41: 79 */     return this.empleadoDao.cargarDetalle(idEmpleado);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Empleado cargarEmpresa(int idEmpleado)
/*  45:    */   {
/*  46: 85 */     return this.empleadoDao.cargarEmpresa(idEmpleado);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Empleado cargarRubros(int idEmpleado)
/*  50:    */   {
/*  51: 96 */     return this.empleadoDao.cargarRubros(idEmpleado);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Empleado bucarEmpleadoPorIdentificacion(String identificacion, int idOrganizacion)
/*  55:    */   {
/*  56:106 */     return this.empleadoDao.bucarEmpleadoPorIdentificacion(identificacion, idOrganizacion);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Empleado buscarPorId(int idEmpleado)
/*  60:    */   {
/*  61:116 */     return (Empleado)this.empleadoDao.buscarPorId(Integer.valueOf(idEmpleado));
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List generarDatosArchivoEntradaIESS(int idRubro, Date desde, Date hasta)
/*  65:    */   {
/*  66:127 */     return this.empleadoDao.generarDatosArchivoEntradaIESS(idRubro, desde, hasta);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public List generarDatosArchivoSalidaIESS(Date desde, Date hasta)
/*  70:    */   {
/*  71:138 */     return this.empleadoDao.generarDatosArchivoSalidaIESS(desde, hasta);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Empleado getEmpleadoPorCargaEmpleado(int idCargaEmpleado)
/*  75:    */   {
/*  76:143 */     return this.empleadoDao.getEmpleadoPorCargaEmpleado(idCargaEmpleado);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<HorarioEmpleado> getListaHorarios(Departamento departamento)
/*  80:    */   {
/*  81:148 */     return this.empleadoDao.getListaHorarios(departamento);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<Rubro> getListaRubros(int idEmpleado)
/*  85:    */   {
/*  86:153 */     return this.empleadoDao.getListaRubros(idEmpleado);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Empleado VerificarGrupoTrabajoEmpleado(Empleado empleado)
/*  90:    */   {
/*  91:157 */     return this.empleadoDao.VerificarGrupoTrabajoEmpleado(empleado);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public GrupoTrabajo cargarDetalleGrupoTrabajo(int idGrupoTrabajoEmpleado)
/*  95:    */   {
/*  96:162 */     return this.empleadoDao.cargarDetalleGrupoTrabajoEmpleado(idGrupoTrabajoEmpleado);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void guardarSoloEntidad(Empleado empleado)
/* 100:    */   {
/* 101:167 */     this.empleadoDao.guardar(empleado);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List<Empleado> obtenerListaComboMultiple(String sortField, boolean sortOrder, Map<String, String> filters)
/* 105:    */   {
/* 106:172 */     return this.empleadoDao.obtenerListaComboMultiple(sortField, sortOrder, filters);
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */