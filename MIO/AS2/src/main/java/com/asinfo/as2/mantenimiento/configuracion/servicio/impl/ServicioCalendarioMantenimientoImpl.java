/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.dao.mantenimiento.CalendarioMantenimientoDao;
/*   6:    */ import com.asinfo.as2.dao.mantenimiento.EquipoDao;
/*   7:    */ import com.asinfo.as2.dao.mantenimiento.LecturaMantenimientoDao;
/*   8:    */ import com.asinfo.as2.dao.mantenimiento.OrdenTrabajoMantenimientoDao;
/*   9:    */ import com.asinfo.as2.dao.mantenimiento.PlanMantenimientoDao;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.Ubicacion;
/*  13:    */ import com.asinfo.as2.entities.UbicacionActivo;
/*  14:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*  15:    */ import com.asinfo.as2.entities.mantenimiento.CalendarioMantenimiento;
/*  16:    */ import com.asinfo.as2.entities.mantenimiento.CalendarioMantenimientoEntidad;
/*  17:    */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
/*  18:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*  19:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimiento;
/*  20:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimientoFrecuencia;
/*  21:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  22:    */ import com.asinfo.as2.entities.mantenimiento.Frecuencia;
/*  23:    */ import com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento;
/*  24:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimiento;
/*  25:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimientoEquipo;
/*  26:    */ import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
/*  27:    */ import com.asinfo.as2.entities.mantenimiento.TipoActividad;
/*  28:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  29:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  30:    */ import com.asinfo.as2.enumeraciones.TipoFrecuenciaEnum;
/*  31:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*  32:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioCalendarioMantenimiento;
/*  33:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  34:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  35:    */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*  36:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  37:    */ import com.asinfo.as2.util.AppUtil;
/*  38:    */ import com.asinfo.as2.util.RutaArchivo;
/*  39:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  40:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  41:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  42:    */ import com.asinfo.as2.utils.reportes.ReportConfigUtil;
/*  43:    */ import java.io.File;
/*  44:    */ import java.io.PrintStream;
/*  45:    */ import java.math.BigDecimal;
/*  46:    */ import java.math.RoundingMode;
/*  47:    */ import java.text.SimpleDateFormat;
/*  48:    */ import java.util.ArrayList;
/*  49:    */ import java.util.Calendar;
/*  50:    */ import java.util.Date;
/*  51:    */ import java.util.HashMap;
/*  52:    */ import java.util.Iterator;
/*  53:    */ import java.util.List;
/*  54:    */ import java.util.Map;
/*  55:    */ import javax.ejb.EJB;
/*  56:    */ import javax.ejb.SessionContext;
/*  57:    */ import javax.ejb.Stateless;
/*  58:    */ import javax.ejb.TransactionAttribute;
/*  59:    */ import javax.ejb.TransactionAttributeType;
/*  60:    */ import javax.ejb.TransactionManagement;
/*  61:    */ import javax.ejb.TransactionManagementType;
/*  62:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  63:    */ import net.sf.jasperreports.engine.JRException;
/*  64:    */ import net.sf.jasperreports.engine.JasperExportManager;
/*  65:    */ import net.sf.jasperreports.engine.JasperPrint;
/*  66:    */ 
/*  67:    */ @Stateless
/*  68:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  69:    */ public class ServicioCalendarioMantenimientoImpl
/*  70:    */   extends AbstractServicioAS2
/*  71:    */   implements ServicioCalendarioMantenimiento
/*  72:    */ {
/*  73:    */   private static final long serialVersionUID = 1L;
/*  74:    */   @EJB
/*  75:    */   private transient PlanMantenimientoDao planMantenimientoDao;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioGenerico<DetallePlanMantenimiento> servicioDetallePlanMantenimiento;
/*  78:    */   @EJB
/*  79:    */   private transient ServicioGenerico<DetallePlanMantenimientoFrecuencia> servicioDetallePlanMantenimientoFrecuencia;
/*  80:    */   @EJB
/*  81:    */   private transient ServicioGenerico<PlanMantenimientoEquipo> servicioPlanMantenimientoEquipo;
/*  82:    */   @EJB
/*  83:    */   private transient OrdenTrabajoMantenimientoDao ordenTrabajoMantenimientoDao;
/*  84:    */   @EJB
/*  85:    */   private transient CalendarioMantenimientoDao calendarioMantenimientoDao;
/*  86:    */   @EJB
/*  87:    */   private transient LecturaMantenimientoDao lecturaMantenimientoDao;
/*  88:    */   @EJB
/*  89:    */   private transient EquipoDao equipoDao;
/*  90:    */   @EJB
/*  91:    */   private transient ServicioOrganizacion servicioOrganizacion;
/*  92:    */   @EJB
/*  93:    */   private transient ServicioEnvioEmail servicioEnvioEmail;
/*  94:    */   @EJB
/*  95:    */   private transient ServicioSucursal servicioSucursal;
/*  96:    */   
/*  97:    */   public List<CalendarioMantenimientoEntidad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  98:    */   {
/*  99:110 */     return this.calendarioMantenimientoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int contarPorCriterio(Map<String, String> filters)
/* 103:    */   {
/* 104:115 */     return this.calendarioMantenimientoDao.contarPorCriterio(filters);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List<CalendarioMantenimiento> obtenerListaCalendarioPorFecha(Date fechaDesde, Date fechaHasta, int idOrganizacion, CategoriaEquipo categoriaEquipo, SubcategoriaEquipo subcategoriaEquipo, Equipo equipo, ComponenteEquipo componenteEquipo, TipoActividad tipoActividad, ActividadMantenimiento actividad, UbicacionActivo ubicacion, boolean indicadorSoloConParo)
/* 108:    */   {
/* 109:122 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 110:    */     
/* 111:124 */     Map<String, String> filtros = new HashMap();
/* 112:125 */     filtros.put("idOrganizacion", idOrganizacion + "");
/* 113:126 */     filtros.put("fechaModificada", OperacionEnum.BETWEEN.name() + sdf.format(fechaDesde) + "~" + sdf.format(fechaHasta));
/* 114:127 */     if (categoriaEquipo != null) {
/* 115:128 */       filtros.put("equipo.subcategoriaEquipo.categoriaEquipo.idCategoriaEquipo", categoriaEquipo.getId() + "");
/* 116:    */     }
/* 117:130 */     if (subcategoriaEquipo != null) {
/* 118:131 */       filtros.put("equipo.subcategoriaEquipo.idSubcategoriaEquipo", subcategoriaEquipo.getId() + "");
/* 119:    */     }
/* 120:133 */     if (equipo != null) {
/* 121:134 */       filtros.put("equipo.idEquipo", equipo.getId() + "");
/* 122:    */     }
/* 123:136 */     if (ubicacion != null) {
/* 124:137 */       filtros.put("equipo.ubicacion.idUbicacionActivo", ubicacion.getId() + "");
/* 125:    */     }
/* 126:139 */     if (componenteEquipo != null) {
/* 127:140 */       filtros.put("detallePlanMantenimiento.componente.idComponenteEquipo", componenteEquipo.getId() + "");
/* 128:    */     }
/* 129:142 */     if (tipoActividad != null) {
/* 130:143 */       filtros.put("detallePlanMantenimiento.actividad.tipoActividad.idTipoActividad", tipoActividad.getId() + "");
/* 131:    */     }
/* 132:145 */     if (actividad != null) {
/* 133:146 */       filtros.put("detallePlanMantenimiento.actividad.idActividadMantenimiento", actividad.getId() + "");
/* 134:    */     }
/* 135:148 */     if (indicadorSoloConParo) {
/* 136:149 */       filtros.put("detallePlanMantenimiento.requiereParo", "true");
/* 137:    */     }
/* 138:152 */     List<CalendarioMantenimientoEntidad> lista = this.calendarioMantenimientoDao.obtenerListaPorPagina(0, 1000000, null, true, filtros);
/* 139:    */     
/* 140:154 */     Map<String, CalendarioMantenimiento> mapaCalendario = new HashMap();
/* 141:155 */     int cantidadDias = FuncionesUtiles.diferenciasDeFechas(fechaDesde, fechaHasta);
/* 142:156 */     for (Iterator localIterator = lista.iterator(); localIterator.hasNext();)
/* 143:    */     {
/* 144:156 */       entidad = (CalendarioMantenimientoEntidad)localIterator.next();
/* 145:    */       
/* 146:158 */       String key = entidad.getEquipo().getId() + "~" + entidad.getDetallePlanMantenimiento().getComponente().getId() + "~" + entidad.getDetallePlanMantenimiento().getActividad().getId();
/* 147:159 */       CalendarioMantenimiento calendario = (CalendarioMantenimiento)mapaCalendario.get(key);
/* 148:160 */       if (calendario == null)
/* 149:    */       {
/* 150:161 */         calendario = new CalendarioMantenimiento(cantidadDias);
/* 151:162 */         calendario.setEquipo(entidad.getEquipo());
/* 152:163 */         calendario.setDetallePlanMantenimiento(entidad.getDetallePlanMantenimiento());
/* 153:164 */         mapaCalendario.put(key, calendario);
/* 154:    */       }
/* 155:167 */       int posicion = FuncionesUtiles.diferenciasDeFechas(fechaDesde, entidad.getFechaModificada());
/* 156:168 */       calendario.getArregloIndicadorMantenimiento()[(posicion - 1)] = Boolean.valueOf(true);
/* 157:169 */       calendario.getArregloCalendarioMantenimientoEntidad()[(posicion - 1)] = entidad;
/* 158:    */     }
/* 159:    */     CalendarioMantenimientoEntidad entidad;
/* 160:171 */     Object listaCalendario = new ArrayList();
/* 161:172 */     for (CalendarioMantenimiento calendario : mapaCalendario.values()) {
/* 162:173 */       ((List)listaCalendario).add(calendario);
/* 163:    */     }
/* 164:175 */     return listaCalendario;
/* 165:    */   }
/* 166:    */   
/* 167:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 168:    */   public void generarCalendarioMantenimiento(int idOrganizacion, int idSucursal, Date fechaCorte)
/* 169:    */     throws AS2ExceptionMantenimiento
/* 170:    */   {
/* 171:    */     try
/* 172:    */     {
/* 173:184 */       Map<String, String> filtros = new HashMap();
/* 174:185 */       filtros.put("idOrganizacion", idOrganizacion + "");
/* 175:186 */       List<CalendarioMantenimientoEntidad> listaCalendarioBase = this.calendarioMantenimientoDao.obtenerCalendarioMantenimiento(idOrganizacion);
/* 176:    */       
/* 177:188 */       Map<String, CalendarioMantenimientoEntidad> mapaCalendario = new HashMap();
/* 178:189 */       for (Iterator localIterator = listaCalendarioBase.iterator(); localIterator.hasNext();)
/* 179:    */       {
/* 180:189 */         calendario = (CalendarioMantenimientoEntidad)localIterator.next();
/* 181:    */         
/* 182:191 */         String key = calendario.getEquipo().getId() + "~" + calendario.getDetallePlanMantenimiento().getComponente().getId() + "~" + calendario.getDetallePlanMantenimiento().getActividad().getId();
/* 183:192 */         mapaCalendario.put(key, calendario);
/* 184:    */       }
/* 185:    */       CalendarioMantenimientoEntidad calendario;
/* 186:196 */       Object listaCalendariosNuevos = obtenerListaCalendarioMantenimientoNuevo(idOrganizacion);
/* 187:197 */       for (CalendarioMantenimientoEntidad calendario : (List)listaCalendariosNuevos)
/* 188:    */       {
/* 189:199 */         key = calendario.getEquipo().getId() + "~" + calendario.getDetallePlanMantenimiento().getComponente().getId() + "~" + calendario.getDetallePlanMantenimiento().getActividad().getId();
/* 190:200 */         if (!mapaCalendario.containsKey(key))
/* 191:    */         {
/* 192:201 */           calendario.setIdSucursal(idSucursal);
/* 193:202 */           mapaCalendario.put(key, calendario);
/* 194:    */         }
/* 195:    */       }
/* 196:    */       String key;
/* 197:207 */       System.out.println("Inicio Query---------------------->1");
/* 198:208 */       List<LecturaMantenimiento> listaLecturaOrdenTrabajo = this.lecturaMantenimientoDao.obtenerUltimasLecturas(TipoFrecuenciaEnum.FECHA, idOrganizacion);
/* 199:209 */       System.out.println("Fin Query---------------------->1");
/* 200:210 */       hmLecturaOrdenTrabajo = new HashMap();
/* 201:211 */       for (LecturaMantenimiento lecturaMantenimiento : listaLecturaOrdenTrabajo)
/* 202:    */       {
/* 203:212 */         key = lecturaMantenimiento.getIdEquipo() + "~" + lecturaMantenimiento.getIdComponenteEquipo() + "~" + lecturaMantenimiento.getIdFrecuenciaOActividad();
/* 204:213 */         hmLecturaOrdenTrabajo.put(key, lecturaMantenimiento);
/* 205:    */       }
/* 206:    */       String key;
/* 207:217 */       System.out.println("Inicio Query---------------------->2");
/* 208:218 */       List<LecturaMantenimiento> listaLecturaFrecuencia = this.lecturaMantenimientoDao.obtenerUltimasLecturas(TipoFrecuenciaEnum.LECTURA, idOrganizacion);
/* 209:219 */       System.out.println("Fin Query---------------------->2");
/* 210:220 */       hmLecturaFrecuencia = new HashMap();
/* 211:221 */       for (LecturaMantenimiento lecturaMantenimiento : listaLecturaFrecuencia)
/* 212:    */       {
/* 213:222 */         key = lecturaMantenimiento.getIdEquipo() + "~" + lecturaMantenimiento.getIdComponenteEquipo() + "~" + lecturaMantenimiento.getIdFrecuenciaOActividad();
/* 214:223 */         hmLecturaFrecuencia.put(key, lecturaMantenimiento);
/* 215:    */       }
/* 216:227 */       System.out.println("Inicio Query---------------------->3");
/* 217:228 */       List<LecturaMantenimiento> listaLectura = this.lecturaMantenimientoDao.obtenerUltimasLecturas(null, idOrganizacion);
/* 218:229 */       System.out.println("Fin Query---------------------->3");
/* 219:230 */       hmLectura = new HashMap();
/* 220:231 */       for (LecturaMantenimiento lecturaMantenimiento : listaLectura)
/* 221:    */       {
/* 222:232 */         String key = lecturaMantenimiento.getIdEquipo() + "~" + lecturaMantenimiento.getIdComponenteEquipo();
/* 223:233 */         hmLectura.put(key, lecturaMantenimiento);
/* 224:    */       }
/* 225:236 */       for (CalendarioMantenimientoEntidad calendarioBase : mapaCalendario.values())
/* 226:    */       {
/* 227:238 */         Object[] resultado = calcularProximoMantenimiento(calendarioBase.getEquipo(), calendarioBase
/* 228:239 */           .getDetallePlanMantenimiento().getComponente(), calendarioBase.getDetallePlanMantenimiento().getActividad(), calendarioBase
/* 229:240 */           .getDetallePlanMantenimiento().getListaDetallePlanMantenimientoFrecuencia(), fechaCorte, hmLecturaOrdenTrabajo, hmLecturaFrecuencia, hmLectura);
/* 230:    */         
/* 231:242 */         Date fechaProximoMantenimiento = (Date)resultado[0];
/* 232:243 */         LecturaMantenimiento ultimaLectura = (LecturaMantenimiento)resultado[1];
/* 233:245 */         if (fechaProximoMantenimiento != null)
/* 234:    */         {
/* 235:246 */           calendarioBase.setFecha(fechaProximoMantenimiento);
/* 236:247 */           calendarioBase.setFechaModificada(fechaProximoMantenimiento);
/* 237:248 */           calendarioBase.setLecturaMantenimiento(ultimaLectura);
/* 238:    */         }
/* 239:    */         else
/* 240:    */         {
/* 241:250 */           calendarioBase.setEliminado(true);
/* 242:    */         }
/* 243:253 */         this.calendarioMantenimientoDao.guardar(calendarioBase);
/* 244:    */       }
/* 245:    */     }
/* 246:    */     catch (Exception e)
/* 247:    */     {
/* 248:    */       HashMap<String, LecturaMantenimiento> hmLecturaOrdenTrabajo;
/* 249:    */       HashMap<String, LecturaMantenimiento> hmLecturaFrecuencia;
/* 250:    */       String key;
/* 251:    */       HashMap<String, LecturaMantenimiento> hmLectura;
/* 252:257 */       e.printStackTrace();
/* 253:258 */       this.context.setRollbackOnly();
/* 254:259 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   private List<CalendarioMantenimientoEntidad> obtenerListaCalendarioMantenimientoNuevo(int idOrganizacion)
/* 259:    */   {
/* 260:265 */     List<CalendarioMantenimientoEntidad> listaCalendario = new ArrayList();
/* 261:    */     
/* 262:    */ 
/* 263:268 */     List<Object[]> lista = this.equipoDao.obtenerEquiposConPlanMantenimiento(idOrganizacion);
/* 264:269 */     HashMap<String, Equipo> hmEquipo = new HashMap();
/* 265:270 */     for (Iterator localIterator = lista.iterator(); localIterator.hasNext();)
/* 266:    */     {
/* 267:270 */       objects = (Object[])localIterator.next();
/* 268:271 */       Equipo equipo = (Equipo)objects[0];
/* 269:272 */       ComponenteEquipo componenteEquipo = (ComponenteEquipo)objects[1];
/* 270:273 */       PlanMantenimiento planMantenimiento = (PlanMantenimiento)objects[2];
/* 271:274 */       String key = componenteEquipo.getId() + "~" + planMantenimiento.getId();
/* 272:275 */       if (!hmEquipo.containsKey(key)) {
/* 273:276 */         hmEquipo.put(key, equipo);
/* 274:    */       }
/* 275:    */     }
/* 276:    */     Object[] objects;
/* 277:282 */     Object listaDetallePlanMantenimiento = this.planMantenimientoDao.obtenerDetallePlanMantenimiento(idOrganizacion);
/* 278:285 */     for (DetallePlanMantenimiento detallePlanMantenimiento : (List)listaDetallePlanMantenimiento)
/* 279:    */     {
/* 280:287 */       String key = detallePlanMantenimiento.getComponente().getId() + "~" + detallePlanMantenimiento.getPlanMantenimiento().getId();
/* 281:288 */       Equipo equipo = (Equipo)hmEquipo.get(key);
/* 282:290 */       if (equipo != null)
/* 283:    */       {
/* 284:291 */         CalendarioMantenimientoEntidad calendario = new CalendarioMantenimientoEntidad();
/* 285:292 */         calendario.setIdOrganizacion(idOrganizacion);
/* 286:293 */         calendario.setDetallePlanMantenimiento(detallePlanMantenimiento);
/* 287:294 */         calendario.setEquipo(equipo);
/* 288:295 */         listaCalendario.add(calendario);
/* 289:    */       }
/* 290:    */     }
/* 291:299 */     return listaCalendario;
/* 292:    */   }
/* 293:    */   
/* 294:    */   private Object[] calcularProximoMantenimiento(Equipo equipo, ComponenteEquipo componente, ActividadMantenimiento actividad, List<DetallePlanMantenimientoFrecuencia> listaDetalleFrecuencia, Date fechaCorte, HashMap<String, LecturaMantenimiento> hmLecturaOrdenTrabajo, HashMap<String, LecturaMantenimiento> hmLecturaFrecuencia, HashMap<String, LecturaMantenimiento> hmLectura)
/* 295:    */   {
/* 296:307 */     Date fechaProximoMantenimiento = null;
/* 297:308 */     LecturaMantenimiento ultimaLecturaAplicada = null;
/* 298:    */     
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:314 */     LecturaMantenimiento ultimaLecturaPorOrdenTrabajo = (LecturaMantenimiento)hmLecturaOrdenTrabajo.get(equipo.getId() + "~" + componente.getId() + "~" + actividad.getId());
/* 304:315 */     LecturaMantenimiento ultimaLectura = (LecturaMantenimiento)hmLectura.get(equipo.getId() + "~" + componente.getId());
/* 305:317 */     for (DetallePlanMantenimientoFrecuencia detallePlanMantenimientoFrecuencia : listaDetalleFrecuencia) {
/* 306:323 */       if (detallePlanMantenimientoFrecuencia.getFrecuencia().getTipoFrecuenciaEnum().equals(TipoFrecuenciaEnum.LECTURA))
/* 307:    */       {
/* 308:328 */         LecturaMantenimiento ultimaLecturaPorFrecuencia = (LecturaMantenimiento)hmLecturaFrecuencia.get(equipo.getId() + "~" + componente.getId() + "~" + detallePlanMantenimientoFrecuencia.getFrecuencia().getId());
/* 309:331 */         if ((ultimaLecturaPorFrecuencia != null) && (
/* 310:    */         
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:336 */           (ultimaLecturaPorOrdenTrabajo == null) || (!ultimaLecturaPorOrdenTrabajo.getFechaLectura().after(ultimaLecturaPorFrecuencia.getFechaLectura()))))
/* 315:    */         {
/* 316:341 */           BigDecimal porcentaje = detallePlanMantenimientoFrecuencia.getPorcentaje();
/* 317:342 */           BigDecimal valorLectura = detallePlanMantenimientoFrecuencia.getValor();
/* 318:343 */           System.out.println("porcentaje----------->" + porcentaje);
/* 319:344 */           System.out.println("valorLectura--------->" + valorLectura);
/* 320:    */           
/* 321:    */ 
/* 322:347 */           BigDecimal valorProporcion = valorLectura.multiply(porcentaje).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
/* 323:348 */           BigDecimal valorParcialEstimado = valorLectura.subtract(valorProporcion);
/* 324:349 */           System.out.println("valorProporcion-------------->" + valorProporcion);
/* 325:350 */           System.out.println("valorParcialEstimado--------->" + valorParcialEstimado);
/* 326:    */           
/* 327:    */ 
/* 328:353 */           BigDecimal valorUltimaLectura = ultimaLecturaPorOrdenTrabajo != null ? ultimaLecturaPorOrdenTrabajo.getValorAcumulado() : BigDecimal.ZERO;
/* 329:    */           
/* 330:355 */           System.out.println("valorUltimaLectura--------->" + valorUltimaLectura);
/* 331:356 */           System.out.println("ultimaLectura.getValorAcumulado()--------->" + ultimaLecturaPorFrecuencia.getValorAcumulado());
/* 332:357 */           valorUltimaLectura = ultimaLecturaPorFrecuencia.getValorAcumulado().subtract(valorUltimaLectura);
/* 333:    */           
/* 334:359 */           Date fechaProximoTemp = null;
/* 335:360 */           BigDecimal valorEstimado = valorParcialEstimado.subtract(valorUltimaLectura);
/* 336:361 */           System.out.println("valorEstimado--------->" + valorEstimado);
/* 337:363 */           if (valorEstimado.compareTo(BigDecimal.ZERO) >= 0)
/* 338:    */           {
/* 339:366 */             Date fechaLecturaAnterior = ultimaLecturaPorFrecuencia.getFechaLecturaAnterior();
/* 340:367 */             if (fechaLecturaAnterior == null) {
/* 341:369 */               fechaLecturaAnterior = equipo.getFechaUltimoMantenimiento() == null ? equipo.getFechaCompra() : equipo.getFechaUltimoMantenimiento();
/* 342:    */             }
/* 343:373 */             int dias = FuncionesUtiles.diferenciasDeFechas(fechaLecturaAnterior, ultimaLecturaPorFrecuencia.getFechaLectura());
/* 344:374 */             BigDecimal diasEstimados = valorEstimado.multiply(new BigDecimal(dias)).divide(ultimaLecturaPorFrecuencia.getValor(), 2, RoundingMode.HALF_UP);
/* 345:    */             
/* 346:376 */             System.out.println("dias--------->" + dias);
/* 347:377 */             System.out.println("dias--------->" + diasEstimados);
/* 348:    */             
/* 349:379 */             fechaProximoTemp = FuncionesUtiles.sumarFechaDiasMeses(ultimaLecturaPorFrecuencia.getFechaLectura(), diasEstimados.intValue());
/* 350:380 */             System.out.println("1_fechaProximoTemp--------->" + fechaProximoTemp);
/* 351:    */             
/* 352:    */ 
/* 353:383 */             fechaProximoTemp = fechaProximoTemp.before(fechaCorte) ? fechaCorte : fechaProximoTemp;
/* 354:    */           }
/* 355:386 */           else if (ultimaLecturaPorFrecuencia.getFechaLectura().after(fechaCorte))
/* 356:    */           {
/* 357:387 */             fechaProximoTemp = FuncionesUtiles.sumarFechaDiasMeses(ultimaLecturaPorFrecuencia.getFechaLectura(), 1);
/* 358:388 */             System.out.println("2_fechaProximoTemp--------->" + fechaProximoTemp);
/* 359:    */           }
/* 360:    */           else
/* 361:    */           {
/* 362:390 */             fechaProximoTemp = fechaCorte;
/* 363:391 */             System.out.println("3_fechaProximoTemp--------->" + fechaProximoTemp);
/* 364:    */           }
/* 365:396 */           if ((fechaProximoMantenimiento == null) || (
/* 366:397 */             (fechaProximoTemp.before(fechaProximoMantenimiento)) && (fechaProximoTemp.after(ultimaLectura.getFechaLectura()))))
/* 367:    */           {
/* 368:398 */             fechaProximoMantenimiento = fechaProximoTemp;
/* 369:399 */             ultimaLecturaAplicada = ultimaLecturaPorOrdenTrabajo;
/* 370:    */           }
/* 371:    */         }
/* 372:    */       }
/* 373:    */       else
/* 374:    */       {
/* 375:404 */         Date fechaUltimoMantenimiento = null;
/* 376:405 */         if (ultimaLecturaPorOrdenTrabajo == null) {
/* 377:407 */           fechaUltimoMantenimiento = equipo.getFechaUltimoMantenimiento() == null ? equipo.getFechaCompra() : equipo.getFechaUltimoMantenimiento();
/* 378:    */         } else {
/* 379:409 */           fechaUltimoMantenimiento = ultimaLecturaPorOrdenTrabajo.getFechaLectura();
/* 380:    */         }
/* 381:412 */         Date fechaProximoTemp = null;
/* 382:413 */         int valorFrecuencia = detallePlanMantenimientoFrecuencia.getValor().intValue();
/* 383:414 */         switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$FrecuenciaFechaEnum[detallePlanMantenimientoFrecuencia.getFrecuencia().getFrecuenciaFechaEnum().ordinal()])
/* 384:    */         {
/* 385:    */         case 1: 
/* 386:416 */           fechaProximoTemp = FuncionesUtiles.sumarFechaDiasMeses(fechaUltimoMantenimiento, valorFrecuencia);
/* 387:417 */           break;
/* 388:    */         case 2: 
/* 389:419 */           fechaProximoTemp = FuncionesUtiles.sumarFechaDiasMeses(fechaUltimoMantenimiento, 7 * valorFrecuencia);
/* 390:420 */           break;
/* 391:    */         case 3: 
/* 392:422 */           fechaProximoTemp = FuncionesUtiles.sumarFechaMeses(fechaUltimoMantenimiento, valorFrecuencia);
/* 393:423 */           break;
/* 394:    */         case 4: 
/* 395:425 */           fechaProximoTemp = FuncionesUtiles.sumarFechaAnios(fechaUltimoMantenimiento, valorFrecuencia);
/* 396:    */         }
/* 397:430 */         fechaProximoTemp = fechaProximoTemp.before(fechaCorte) ? fechaCorte : fechaProximoTemp;
/* 398:    */         
/* 399:432 */         System.out.println("4_fechaProximoTemp--------->" + fechaProximoTemp);
/* 400:434 */         if ((fechaProximoMantenimiento == null) || (
/* 401:435 */           (fechaProximoTemp.before(fechaProximoMantenimiento)) && (fechaProximoTemp.after(ultimaLectura.getFechaLectura()))))
/* 402:    */         {
/* 403:436 */           fechaProximoMantenimiento = fechaProximoTemp;
/* 404:437 */           ultimaLecturaAplicada = ultimaLecturaPorOrdenTrabajo;
/* 405:    */         }
/* 406:    */       }
/* 407:    */     }
/* 408:441 */     Object[] result = new Object[2];
/* 409:442 */     result[0] = fechaProximoMantenimiento;
/* 410:443 */     result[1] = ultimaLecturaAplicada;
/* 411:444 */     return result;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void guardarInformacionCalendario(CalendarioMantenimientoEntidad calendario)
/* 415:    */   {
/* 416:449 */     this.calendarioMantenimientoDao.guardar(calendario);
/* 417:    */   }
/* 418:    */   
/* 419:    */   public List<Object[]> getReporteCalendarioMantenimiento(int idOrganizacion, Date fechaDesde, Date fechaHasta, CategoriaEquipo categoriaEquipo, SubcategoriaEquipo subcategoriaEquipo, Equipo equipo, ComponenteEquipo componenteEquipo, TipoActividad tipoActividad, ActividadMantenimiento actividad, UbicacionActivo ubicacion, boolean indicadorSoloConParo, boolean indicadorReporteParos)
/* 420:    */   {
/* 421:456 */     return this.calendarioMantenimientoDao.getReporteCalendarioMantenimiento(idOrganizacion, fechaDesde, fechaHasta, categoriaEquipo, subcategoriaEquipo, equipo, componenteEquipo, tipoActividad, actividad, ubicacion, indicadorSoloConParo, indicadorReporteParos);
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void enviarEmail(int idOrganizacion, Date fechaDesde, Date fechaHasta, CategoriaEquipo categoriaEquipo, SubcategoriaEquipo subcategoriaEquipo, Equipo equipo, ComponenteEquipo componenteEquipo, TipoActividad tipoActividad, ActividadMantenimiento actividad, UbicacionActivo ubicacion, boolean indicadorSoloConParo, boolean indicadorReporteParos, String mails)
/* 425:    */   {
/* 426:465 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 427:466 */     Organizacion organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(idOrganizacion));
/* 428:467 */     Sucursal sucursal = this.servicioSucursal.cargarDetalle(AppUtil.getSucursal().getId());
/* 429:    */     
/* 430:469 */     Calendar calFechaDesde = Calendar.getInstance();
/* 431:470 */     calFechaDesde.setTime(fechaDesde);
/* 432:471 */     Calendar calFechaHasta = Calendar.getInstance();
/* 433:472 */     calFechaHasta.setTime(fechaHasta);
/* 434:    */     
/* 435:474 */     String cadenaFechaDesde = sdf.format(fechaDesde);
/* 436:475 */     String cadenaFechaHasta = sdf.format(fechaHasta);
/* 437:476 */     String reporte = indicadorReporteParos ? "reporteCalendarioParosMantenimiento" : "reporteCalendarioMantenimiento";
/* 438:    */     
/* 439:478 */     String bodyText = "Informacion.<br><br> Reporte planificacion de matenimientos desde : " + cadenaFechaDesde + " hasta : " + cadenaFechaHasta;
/* 440:479 */     String asunto = "Reporte planificacion de matenimientos (" + cadenaFechaDesde + " - " + cadenaFechaHasta + ")";
/* 441:481 */     if (indicadorReporteParos)
/* 442:    */     {
/* 443:482 */       bodyText = "Informacion.<br><br> Reporte planificacion de paros desde : " + fechaDesde + " hasta : " + fechaHasta;
/* 444:483 */       asunto = "Reporte paros de matenimientos (" + fechaDesde + " - " + fechaHasta + ")";
/* 445:    */     }
/* 446:486 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 447:487 */     listaDatosReporte = getReporteCalendarioMantenimiento(idOrganizacion, fechaDesde, fechaHasta, categoriaEquipo, subcategoriaEquipo, equipo, componenteEquipo, tipoActividad, actividad, ubicacion, indicadorSoloConParo, indicadorReporteParos);
/* 448:    */     
/* 449:    */ 
/* 450:490 */     JRDataSource ds = new QueryResultDataSource(listaDatosReporte, new String[] { "f_equipo", "f_componente", "f_actividad", "f_dia", "f_valor" });
/* 451:    */     
/* 452:    */ 
/* 453:    */ 
/* 454:494 */     List<String> archivos = new ArrayList();
/* 455:495 */     Map<String, String> mapaCid = new HashMap();
/* 456:    */     
/* 457:497 */     String rutaLogoOrganizacion = RutaArchivo.getUploadDir(organizacion.getId(), "logo") + organizacion.getImagen();
/* 458:498 */     String rutaComprobantesNoElectronicos = RutaArchivo.getDirectorioUpload(organizacion.getId(), 
/* 459:499 */       FuncionesUtiles.completarALaIzquierda('0', 10, organizacion.getId() + ""), "documentos_no_electronicos");
/* 460:500 */     rutaComprobantesNoElectronicos = rutaComprobantesNoElectronicos + File.separator + "CALENDARIO_MANTENIMIENTO" + File.separator;
/* 461:    */     
/* 462:    */ 
/* 463:503 */     File file = new File(rutaComprobantesNoElectronicos);
/* 464:504 */     if (!file.exists()) {
/* 465:505 */       file.mkdirs();
/* 466:    */     }
/* 467:508 */     String rutaReporte = rutaComprobantesNoElectronicos + reporte;
/* 468:    */     
/* 469:510 */     Map<String, Object> parameters = new HashMap();
/* 470:    */     
/* 471:512 */     parameters.put("logoEmpresa", rutaLogoOrganizacion);
/* 472:513 */     parameters.put("nombreOrganizacion", organizacion.getRazonSocial());
/* 473:514 */     parameters.put("p_direccionMatriz", AppUtil.getDireccionMatriz() == null ? "" : AppUtil.getDireccionMatriz());
/* 474:515 */     parameters.put("direccionOrganizacion", sucursal.getUbicacion().getDireccionCompleta());
/* 475:516 */     parameters.put("telefonoOrganizacion", sucursal.getTelefono1().concat(sucursal.getTelefono2() != null ? "   " + sucursal.getTelefono2() : ""));
/* 476:517 */     parameters.put("usuarioImpresion", AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 477:518 */     parameters.put("identificacionOrganizacion", organizacion.getIdentificacion());
/* 478:519 */     parameters.put("p_formatoFecha", ParametrosSistema.getFormatoFecha(organizacion.getIdOrganizacion()));
/* 479:520 */     if (indicadorReporteParos) {
/* 480:521 */       parameters.put("ReportTitle", "Calendario Paros por Mantenimiento");
/* 481:    */     } else {
/* 482:523 */       parameters.put("ReportTitle", "Calendario Mantenimiento");
/* 483:    */     }
/* 484:525 */     String mes = Mes.values()[calFechaDesde.get(2)].getNombre();
/* 485:526 */     parameters.put("p_mes", mes);
/* 486:527 */     parameters.put("p_anio", Integer.valueOf(calFechaDesde.get(1)));
/* 487:528 */     parameters.put("p_categoriaEquipo", categoriaEquipo == null ? "TODOS" : categoriaEquipo.getNombre());
/* 488:529 */     parameters.put("p_subCategoriaEquipo", subcategoriaEquipo == null ? "TODOS" : subcategoriaEquipo.getNombre());
/* 489:530 */     parameters.put("p_equipo", equipo == null ? "TODOS" : equipo.getNombre());
/* 490:531 */     parameters.put("p_componenteEquipo", componenteEquipo == null ? "TODOS" : componenteEquipo.getNombre());
/* 491:532 */     parameters.put("p_tipoActividad", tipoActividad == null ? "TODOS" : tipoActividad.getNombre());
/* 492:533 */     parameters.put("p_actividad", actividad == null ? "TODOS" : actividad.getNombre());
/* 493:534 */     parameters.put("p_ubicacion", ubicacion == null ? "TODOS" : ubicacion.getNombre());
/* 494:535 */     if (!indicadorReporteParos) {
/* 495:536 */       parameters.put("p_soloConParo", Boolean.valueOf(indicadorSoloConParo));
/* 496:    */     }
/* 497:538 */     File reportFile = new File(ReportConfigUtil.getJasperFilePath(ParametrosSistema.getAS2_HOME(organizacion.getId()) + File.separator, "reportes" + File.separator, reporte + ".jasper"));
/* 498:    */     
/* 499:540 */     JasperPrint jasperPrint = null;
/* 500:    */     try
/* 501:    */     {
/* 502:542 */       jasperPrint = ReportConfigUtil.fillReport(reportFile, parameters, ds);
/* 503:543 */       JasperExportManager.exportReportToPdfFile(jasperPrint, rutaReporte + ".pdf");
/* 504:544 */       archivos.add(rutaReporte + ".pdf");
/* 505:    */     }
/* 506:    */     catch (JRException e)
/* 507:    */     {
/* 508:546 */       e.printStackTrace();
/* 509:547 */       System.out.println("No se pudo generar el reporte PDF");
/* 510:    */     }
/* 511:550 */     this.servicioEnvioEmail.enviarEmail(organizacion.getId(), mails, asunto, bodyText, archivos, mapaCid, null, null, null);
/* 512:    */   }
/* 513:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.impl.ServicioCalendarioMantenimientoImpl
 * JD-Core Version:    0.7.0.1
 */