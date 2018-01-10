/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.mantenimiento.ActividadMantenimientoDao;
/*   5:    */ import com.asinfo.as2.entities.Especialidad;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoEspecialidad;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoHerramienta;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoMaterial;
/*  11:    */ import com.asinfo.as2.entities.mantenimiento.Herramienta;
/*  12:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*  13:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioActividadMantenimiento;
/*  14:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  15:    */ import java.math.BigDecimal;
/*  16:    */ import java.util.HashSet;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Set;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.ejb.SessionContext;
/*  22:    */ import javax.ejb.Stateless;
/*  23:    */ 
/*  24:    */ @Stateless
/*  25:    */ public class ServicioActividadMantenimientoImpl
/*  26:    */   extends AbstractServicioAS2
/*  27:    */   implements ServicioActividadMantenimiento
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private transient ActividadMantenimientoDao actividadMantenimientoDao;
/*  32:    */   @EJB
/*  33:    */   private transient GenericoDao<ActividadMantenimientoHerramienta> actividadMantenimientoHerramientaDao;
/*  34:    */   @EJB
/*  35:    */   private transient GenericoDao<ActividadMantenimientoMaterial> actividadMantenimientoMaterialDao;
/*  36:    */   @EJB
/*  37:    */   private transient GenericoDao<ActividadMantenimientoEspecialidad> actividadMantenimientoEspecialidadDao;
/*  38:    */   
/*  39:    */   public List<ActividadMantenimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  40:    */   {
/*  41: 50 */     return this.actividadMantenimientoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void guardar(ActividadMantenimiento actividadMantenimiento)
/*  45:    */     throws AS2ExceptionMantenimiento
/*  46:    */   {
/*  47:    */     try
/*  48:    */     {
/*  49: 56 */       validar(actividadMantenimiento);
/*  50:    */       
/*  51: 58 */       this.actividadMantenimientoDao.guardar(actividadMantenimiento);
/*  52: 59 */       for (ActividadMantenimientoHerramienta herramienta : actividadMantenimiento.getListaHerramienta()) {
/*  53: 60 */         this.actividadMantenimientoHerramientaDao.guardar(herramienta);
/*  54:    */       }
/*  55: 62 */       for (ActividadMantenimientoMaterial material : actividadMantenimiento.getListaMaterial()) {
/*  56: 63 */         this.actividadMantenimientoMaterialDao.guardar(material);
/*  57:    */       }
/*  58: 65 */       for (ActividadMantenimientoEspecialidad especialidad : actividadMantenimiento.getListaEspecialidad()) {
/*  59: 66 */         this.actividadMantenimientoEspecialidadDao.guardar(especialidad);
/*  60:    */       }
/*  61:    */     }
/*  62:    */     catch (Exception e)
/*  63:    */     {
/*  64: 69 */       this.context.setRollbackOnly();
/*  65: 70 */       e.printStackTrace();
/*  66: 71 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   private void validar(ActividadMantenimiento actividadMantenimiento)
/*  71:    */     throws AS2ExceptionMantenimiento
/*  72:    */   {
/*  73: 76 */     Set<String> herramientas = new HashSet();
/*  74: 77 */     Set<String> materiales = new HashSet();
/*  75: 78 */     Set<String> especialidades = new HashSet();
/*  76: 79 */     for (ActividadMantenimientoHerramienta detalle : actividadMantenimiento.getListaHerramienta()) {
/*  77: 80 */       if (!detalle.isEliminado())
/*  78:    */       {
/*  79: 81 */         String key = detalle.getHerramienta().getId() + "";
/*  80: 82 */         if (herramientas.contains(key)) {
/*  81: 83 */           throw new AS2ExceptionMantenimiento("msg_error_detalle_repetido", new String[] { "HERRAMIENTA", detalle.getHerramienta().getNombre() });
/*  82:    */         }
/*  83: 85 */         if (detalle.getCantidad().compareTo(BigDecimal.ZERO) == 0) {
/*  84: 86 */           throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.configuracion.servicio.impl.ServicioPlanMantenimientoImpl.ERROR_CANTIDAD_MAYOR_CERO", new String[] { "HERRAMIENTA: " + detalle.getHerramienta().getNombre(), "0" });
/*  85:    */         }
/*  86: 88 */         herramientas.add(key);
/*  87:    */       }
/*  88:    */     }
/*  89: 91 */     for (ActividadMantenimientoMaterial detalle : actividadMantenimiento.getListaMaterial()) {
/*  90: 92 */       if (!detalle.isEliminado())
/*  91:    */       {
/*  92: 93 */         String key = detalle.getProducto().getId() + "";
/*  93: 94 */         if (materiales.contains(key)) {
/*  94: 95 */           throw new AS2ExceptionMantenimiento("msg_error_detalle_repetido", new String[] { "MATERIAL", detalle.getProducto().getNombre() });
/*  95:    */         }
/*  96: 97 */         if (detalle.getCantidad().compareTo(BigDecimal.ZERO) == 0) {
/*  97: 98 */           throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.configuracion.servicio.impl.ServicioPlanMantenimientoImpl.ERROR_CANTIDAD_MAYOR_CERO", new String[] { "MATERIAL: " + detalle.getProducto().getNombre(), "0" });
/*  98:    */         }
/*  99:100 */         materiales.add(key);
/* 100:    */       }
/* 101:    */     }
/* 102:103 */     for (ActividadMantenimientoEspecialidad detalle : actividadMantenimiento.getListaEspecialidad()) {
/* 103:104 */       if (!detalle.isEliminado())
/* 104:    */       {
/* 105:105 */         String key = detalle.getEspecialidad().getId() + "";
/* 106:106 */         if (especialidades.contains(key)) {
/* 107:107 */           throw new AS2ExceptionMantenimiento("msg_error_detalle_repetido", new String[] { "ESPECIALIDAD", detalle.getEspecialidad().getNombre() });
/* 108:    */         }
/* 109:109 */         especialidades.add(key);
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void eliminar(ActividadMantenimiento actividadMantenimiento)
/* 115:    */     throws AS2ExceptionMantenimiento
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:117 */       actividadMantenimiento = cargarDetalle(actividadMantenimiento);
/* 120:118 */       for (ActividadMantenimientoHerramienta herramienta : actividadMantenimiento.getListaHerramienta()) {
/* 121:119 */         this.actividadMantenimientoHerramientaDao.eliminar(herramienta);
/* 122:    */       }
/* 123:121 */       for (ActividadMantenimientoMaterial material : actividadMantenimiento.getListaMaterial()) {
/* 124:122 */         this.actividadMantenimientoMaterialDao.eliminar(material);
/* 125:    */       }
/* 126:124 */       for (ActividadMantenimientoEspecialidad especialidad : actividadMantenimiento.getListaEspecialidad()) {
/* 127:125 */         this.actividadMantenimientoEspecialidadDao.eliminar(especialidad);
/* 128:    */       }
/* 129:127 */       this.actividadMantenimientoDao.eliminar(actividadMantenimiento);
/* 130:    */     }
/* 131:    */     catch (Exception e)
/* 132:    */     {
/* 133:129 */       this.context.setRollbackOnly();
/* 134:130 */       e.printStackTrace();
/* 135:131 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public ActividadMantenimiento buscarPorId(Integer id)
/* 140:    */   {
/* 141:137 */     return (ActividadMantenimiento)this.actividadMantenimientoDao.buscarPorId(id);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public ActividadMantenimiento cargarDetalle(ActividadMantenimiento actividadMantenimiento)
/* 145:    */   {
/* 146:142 */     return this.actividadMantenimientoDao.cargarDetalle(actividadMantenimiento);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public int contarPorCriterio(Map<String, String> filters)
/* 150:    */   {
/* 151:147 */     return this.actividadMantenimientoDao.contarPorCriterio(filters);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<ActividadMantenimiento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/* 155:    */   {
/* 156:152 */     return this.actividadMantenimientoDao.obtenerListaCombo(sortField, sortOrder, filtros);
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.impl.ServicioActividadMantenimientoImpl
 * JD-Core Version:    0.7.0.1
 */