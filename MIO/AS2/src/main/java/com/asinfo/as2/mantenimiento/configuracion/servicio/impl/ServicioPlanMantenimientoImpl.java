/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.mantenimiento.LecturaMantenimientoDao;
/*   4:    */ import com.asinfo.as2.dao.mantenimiento.PlanMantenimientoDao;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimiento;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimientoFrecuencia;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.Frecuencia;
/*  11:    */ import com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento;
/*  12:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimiento;
/*  13:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimientoEquipo;
/*  14:    */ import com.asinfo.as2.enumeraciones.TipoFrecuenciaEnum;
/*  15:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*  16:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioLecturaMantenimiento;
/*  17:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioPlanMantenimiento;
/*  18:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  19:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Date;
/*  22:    */ import java.util.HashSet;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import java.util.Set;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.ejb.SessionContext;
/*  28:    */ import javax.ejb.Stateless;
/*  29:    */ import javax.ejb.TransactionAttribute;
/*  30:    */ import javax.ejb.TransactionAttributeType;
/*  31:    */ import javax.ejb.TransactionManagement;
/*  32:    */ import javax.ejb.TransactionManagementType;
/*  33:    */ 
/*  34:    */ @Stateless
/*  35:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  36:    */ public class ServicioPlanMantenimientoImpl
/*  37:    */   extends AbstractServicioAS2
/*  38:    */   implements ServicioPlanMantenimiento
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 1L;
/*  41:    */   @EJB
/*  42:    */   private transient PlanMantenimientoDao planMantenimientoDao;
/*  43:    */   @EJB
/*  44:    */   private transient ServicioGenerico<DetallePlanMantenimiento> servicioDetallePlanMantenimiento;
/*  45:    */   @EJB
/*  46:    */   private transient ServicioGenerico<DetallePlanMantenimientoFrecuencia> servicioDetallePlanMantenimientoFrecuencia;
/*  47:    */   @EJB
/*  48:    */   private transient ServicioGenerico<PlanMantenimientoEquipo> servicioPlanMantenimientoEquipo;
/*  49:    */   @EJB
/*  50:    */   private transient LecturaMantenimientoDao lecturaMantenimientoDao;
/*  51:    */   @EJB
/*  52:    */   private transient ServicioLecturaMantenimiento servicioLecturaMantenimiento;
/*  53:    */   
/*  54:    */   public List<PlanMantenimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  55:    */   {
/*  56: 69 */     return this.planMantenimientoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  57:    */   }
/*  58:    */   
/*  59:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  60:    */   public void guardar(PlanMantenimiento planMantenimiento)
/*  61:    */     throws AS2ExceptionMantenimiento
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65: 76 */       validar(planMantenimiento);
/*  66: 77 */       this.planMantenimientoDao.guardar(planMantenimiento);
/*  67: 78 */       for (DetallePlanMantenimiento dpm : planMantenimiento.getListaDetallePlanMantenimiento())
/*  68:    */       {
/*  69: 80 */         if (!dpm.isEliminado()) {
/*  70: 81 */           this.servicioDetallePlanMantenimiento.guardar(dpm);
/*  71:    */         }
/*  72: 83 */         for (DetallePlanMantenimientoFrecuencia dpmf : dpm.getListaDetallePlanMantenimientoFrecuencia())
/*  73:    */         {
/*  74: 84 */           if (dpm.isEliminado()) {
/*  75: 85 */             dpmf.setEliminado(true);
/*  76:    */           }
/*  77: 87 */           this.servicioDetallePlanMantenimientoFrecuencia.guardar(dpmf);
/*  78:    */         }
/*  79: 90 */         if (dpm.isEliminado()) {
/*  80: 91 */           this.servicioDetallePlanMantenimiento.guardar(dpm);
/*  81:    */         }
/*  82:    */       }
/*  83: 94 */       for (PlanMantenimientoEquipo detalle : planMantenimiento.getListaPlanMantenimientoEquipo()) {
/*  84: 95 */         this.servicioPlanMantenimientoEquipo.guardar(detalle);
/*  85:    */       }
/*  86: 97 */       for (PlanMantenimientoEquipo detalle : planMantenimiento.getListaPlanMantenimientoEquipo()) {
/*  87: 98 */         this.servicioPlanMantenimientoEquipo.guardar(detalle);
/*  88:    */       }
/*  89:    */     }
/*  90:    */     catch (Exception e)
/*  91:    */     {
/*  92:101 */       this.context.setRollbackOnly();
/*  93:102 */       e.printStackTrace();
/*  94:103 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  99:    */   public void eliminar(PlanMantenimiento planMantenimiento)
/* 100:    */     throws AS2ExceptionMantenimiento
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:111 */       planMantenimiento = cargarDetalle(planMantenimiento);
/* 105:112 */       for (DetallePlanMantenimiento dpm : planMantenimiento.getListaDetallePlanMantenimiento())
/* 106:    */       {
/* 107:113 */         for (DetallePlanMantenimientoFrecuencia dpmf : dpm.getListaDetallePlanMantenimientoFrecuencia()) {
/* 108:114 */           this.servicioDetallePlanMantenimientoFrecuencia.eliminar(dpmf);
/* 109:    */         }
/* 110:116 */         this.servicioDetallePlanMantenimiento.eliminar(dpm);
/* 111:    */       }
/* 112:118 */       this.planMantenimientoDao.eliminar(planMantenimiento);
/* 113:    */     }
/* 114:    */     catch (Exception e)
/* 115:    */     {
/* 116:120 */       this.context.setRollbackOnly();
/* 117:121 */       e.printStackTrace();
/* 118:122 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public PlanMantenimiento buscarPorId(Integer id)
/* 123:    */   {
/* 124:128 */     return (PlanMantenimiento)this.planMantenimientoDao.buscarPorId(id);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public PlanMantenimiento cargarDetalle(PlanMantenimiento planMantenimiento)
/* 128:    */   {
/* 129:133 */     return this.planMantenimientoDao.cargarDetalle(planMantenimiento);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int contarPorCriterio(Map<String, String> filters)
/* 133:    */   {
/* 134:138 */     return this.planMantenimientoDao.contarPorCriterio(filters);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List<PlanMantenimiento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/* 138:    */   {
/* 139:143 */     return this.planMantenimientoDao.obtenerListaCombo(sortField, sortOrder, filtros);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public List<DetallePlanMantenimientoFrecuencia> cargarDetallePlan(ComponenteEquipo componenteEquipo, Equipo equipo)
/* 143:    */   {
/* 144:148 */     return this.planMantenimientoDao.cargarDetallaPlan(componenteEquipo, equipo);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Equipo cargarEquipoAsignadoPlanMantenimiento(Equipo equipo)
/* 148:    */   {
/* 149:153 */     return this.planMantenimientoDao.cargarEquipoAsignadoPlanMantenimiento(equipo);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<DetallePlanMantenimientoFrecuencia> obtenerDetallePlanMantenimientoFrecuencia(Equipo equipo, ComponenteEquipo componenteEquipo, ActividadMantenimiento actividadMantenimiento, TipoFrecuenciaEnum tipoFrecuencia)
/* 153:    */   {
/* 154:159 */     return this.planMantenimientoDao.obtenerDetallePlanMantenimientoFrecuencia(equipo, componenteEquipo, actividadMantenimiento, tipoFrecuencia);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List<LecturaMantenimiento> crearLecturasMantenimiento(Date fechaLectura, boolean indicadorAutomatico, Equipo equipo, ComponenteEquipo componenteEquipo, ActividadMantenimiento actividadMantenimiento, TipoFrecuenciaEnum tipoFrecuencia)
/* 158:    */   {
/* 159:167 */     List<DetallePlanMantenimientoFrecuencia> listaDetalleFrecuencia = this.planMantenimientoDao.obtenerDetallePlanMantenimientoFrecuencia(equipo, componenteEquipo, actividadMantenimiento, tipoFrecuencia);
/* 160:    */     
/* 161:    */ 
/* 162:170 */     List<LecturaMantenimiento> listaLectura = new ArrayList();
/* 163:171 */     Set<String> frecuenciasAsignadas = new HashSet();
/* 164:172 */     for (DetallePlanMantenimientoFrecuencia detalleFrecuencia : listaDetalleFrecuencia)
/* 165:    */     {
/* 166:173 */       String key = detalleFrecuencia.getDetallePlanMantenimiento().getComponente().getId() + "~" + detalleFrecuencia.getFrecuencia().getId();
/* 167:174 */       if (detalleFrecuencia.getFrecuencia().getTipoFrecuenciaEnum().equals(TipoFrecuenciaEnum.FECHA)) {
/* 168:175 */         key = key + "~" + detalleFrecuencia.getDetallePlanMantenimiento().getActividad().getId() + "~";
/* 169:    */       }
/* 170:178 */       if (!frecuenciasAsignadas.contains(key))
/* 171:    */       {
/* 172:180 */         LecturaMantenimiento lecturaMantenimiento = new LecturaMantenimiento();
/* 173:181 */         lecturaMantenimiento.setIdOrganizacion(detalleFrecuencia.getIdOrganizacion());
/* 174:182 */         lecturaMantenimiento.setIdSucursal(detalleFrecuencia.getIdSucursal());
/* 175:183 */         lecturaMantenimiento.setComponenteEquipo(detalleFrecuencia.getDetallePlanMantenimiento().getComponente());
/* 176:184 */         lecturaMantenimiento.setEquipo(equipo);
/* 177:185 */         lecturaMantenimiento.setFechaLectura(fechaLectura);
/* 178:186 */         lecturaMantenimiento.setIndicadorAutomatico(indicadorAutomatico);
/* 179:187 */         if (detalleFrecuencia.getFrecuencia().getTipoFrecuenciaEnum().equals(TipoFrecuenciaEnum.LECTURA))
/* 180:    */         {
/* 181:188 */           lecturaMantenimiento.setFrecuencia(detalleFrecuencia.getFrecuencia());
/* 182:    */         }
/* 183:    */         else
/* 184:    */         {
/* 185:190 */           lecturaMantenimiento.setIndicadorTiempo(true);
/* 186:191 */           lecturaMantenimiento.setActividadMantenimiento(detalleFrecuencia.getDetallePlanMantenimiento().getActividad());
/* 187:    */         }
/* 188:194 */         this.servicioLecturaMantenimiento.actualizarValoresLecturaMantenimiento(lecturaMantenimiento, true);
/* 189:    */         
/* 190:196 */         listaLectura.add(lecturaMantenimiento);
/* 191:197 */         frecuenciasAsignadas.add(key);
/* 192:    */       }
/* 193:    */     }
/* 194:200 */     return listaLectura;
/* 195:    */   }
/* 196:    */   
/* 197:    */   private void validar(PlanMantenimiento planMantenimiento)
/* 198:    */     throws AS2ExceptionMantenimiento
/* 199:    */   {
/* 200:204 */     for (DetallePlanMantenimiento detallePlanMantenimiento : planMantenimiento.getListaDetallePlanMantenimiento()) {
/* 201:205 */       if (detallePlanMantenimiento.getListaDetallePlanMantenimientoFrecuenciaView().size() == 0) {
/* 202:206 */         throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.configuracion.servicio.impl.ServicioPlanMantenimientoImpl.ERROR_NO_EXISTE_FRECUENCIA_DETALLE_MANTENIMIENTO", new String[] { "" });
/* 203:    */       }
/* 204:    */     }
/* 205:    */   }
/* 206:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.impl.ServicioPlanMantenimientoImpl
 * JD-Core Version:    0.7.0.1
 */