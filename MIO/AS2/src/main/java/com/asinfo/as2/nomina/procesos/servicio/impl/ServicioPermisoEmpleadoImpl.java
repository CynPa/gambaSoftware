/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.PermisoEmpleadoDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   6:    */ import com.asinfo.as2.entities.Departamento;
/*   7:    */ import com.asinfo.as2.entities.DetallePermisoEmpleado;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.Empleado;
/*  10:    */ import com.asinfo.as2.entities.PermisoEmpleado;
/*  11:    */ import com.asinfo.as2.entities.TipoPermisoEmpleado;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPermisoEmpleado;
/*  14:    */ import java.math.BigDecimal;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.ejb.Stateless;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class ServicioPermisoEmpleadoImpl
/*  23:    */   implements ServicioPermisoEmpleado
/*  24:    */ {
/*  25:    */   @EJB
/*  26:    */   private transient PermisoEmpleadoDao permisoEmpleadoDao;
/*  27:    */   @EJB
/*  28:    */   private transient ServicioSecuencia servicioSecuencia;
/*  29:    */   @EJB
/*  30:    */   private transient GenericoDao<DetallePermisoEmpleado> detallePermisoEmpleadoDao;
/*  31:    */   
/*  32:    */   public void guardar(PermisoEmpleado permisoEmpleado)
/*  33:    */     throws ExcepcionAS2
/*  34:    */   {
/*  35: 55 */     validar(permisoEmpleado);
/*  36: 56 */     permisoEmpleado.setIndicadorCargoVacacion(permisoEmpleado.getTipoPermisoEmpleado().isIndicadorCargoVacacion());
/*  37:    */     
/*  38: 58 */     cargarSecuencia(permisoEmpleado);
/*  39: 59 */     this.permisoEmpleadoDao.guardar(permisoEmpleado);
/*  40: 60 */     for (DetallePermisoEmpleado det : permisoEmpleado.getListaDetallePermisoEmpleado()) {
/*  41: 61 */       this.detallePermisoEmpleadoDao.guardar(det);
/*  42:    */     }
/*  43: 63 */     this.servicioSecuencia.actualizarSecuencia(permisoEmpleado.getDocumento().getSecuencia(), permisoEmpleado.getNumero());
/*  44:    */   }
/*  45:    */   
/*  46:    */   private void validar(PermisoEmpleado permisoEmpleado)
/*  47:    */     throws ExcepcionAS2
/*  48:    */   {
/*  49: 67 */     for (DetallePermisoEmpleado det : permisoEmpleado.getListaDetallePermisoEmpleado()) {
/*  50: 68 */       if ((!det.isEliminado()) && 
/*  51: 69 */         (det.getHoras().compareTo(BigDecimal.ZERO) <= 0)) {
/*  52: 70 */         throw new ExcepcionAS2("msg_error_detalle_permiso_cero");
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void eliminar(PermisoEmpleado permisoEmpleado)
/*  58:    */   {
/*  59: 83 */     permisoEmpleado = cargarDetalle(permisoEmpleado.getId());
/*  60: 84 */     for (DetallePermisoEmpleado dpe : permisoEmpleado.getListaDetallePermisoEmpleado()) {
/*  61: 85 */       this.detallePermisoEmpleadoDao.eliminar(dpe);
/*  62:    */     }
/*  63: 87 */     this.permisoEmpleadoDao.eliminar(permisoEmpleado);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public PermisoEmpleado buscarPorId(int idPermisoEmpleado)
/*  67:    */   {
/*  68: 97 */     return (PermisoEmpleado)this.permisoEmpleadoDao.buscarPorId(Integer.valueOf(idPermisoEmpleado));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<PermisoEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  72:    */   {
/*  73:107 */     return this.permisoEmpleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public List<PermisoEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  77:    */   {
/*  78:118 */     return this.permisoEmpleadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int contarPorCriterio(Map<String, String> filters)
/*  82:    */   {
/*  83:128 */     return this.permisoEmpleadoDao.contarPorCriterio(filters);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public PermisoEmpleado cargarDetalle(int idPermisoEmpleado)
/*  87:    */   {
/*  88:138 */     return this.permisoEmpleadoDao.cargarDetalle(idPermisoEmpleado);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List getPersmisoEmpleado(int idPermisoEmpleado)
/*  92:    */     throws ExcepcionAS2
/*  93:    */   {
/*  94:149 */     return this.permisoEmpleadoDao.getPersmisoEmpleado(idPermisoEmpleado);
/*  95:    */   }
/*  96:    */   
/*  97:    */   private void cargarSecuencia(PermisoEmpleado permisoEmpleado)
/*  98:    */     throws ExcepcionAS2
/*  99:    */   {
/* 100:159 */     if (permisoEmpleado.getNumero().equals(""))
/* 101:    */     {
/* 102:160 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(permisoEmpleado.getDocumento().getIdDocumento(), permisoEmpleado
/* 103:161 */         .getFechaPermiso());
/* 104:162 */       permisoEmpleado.setNumero(numero);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public List<DetallePermisoEmpleado> getPermisoEmpleadoPorFecha(int idOrganizacion, Date fecha, Departamento departamento, Empleado empleado)
/* 109:    */   {
/* 110:168 */     return this.permisoEmpleadoDao.getPermisoEmpleadoPorFecha(idOrganizacion, fecha, departamento, empleado);
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioPermisoEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */