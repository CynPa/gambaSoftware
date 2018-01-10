/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimiento;
/*  11:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimientoFrecuencia;
/*  12:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  13:    */ import com.asinfo.as2.entities.mantenimiento.Frecuencia;
/*  14:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimiento;
/*  15:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimientoEquipo;
/*  16:    */ import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
/*  17:    */ import com.asinfo.as2.enumeraciones.PrioridadEnum;
/*  18:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*  19:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioEquipo;
/*  20:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioPlanMantenimiento;
/*  21:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  22:    */ import com.asinfo.as2.util.AppUtil;
/*  23:    */ import com.asinfo.as2.utils.JsfUtil;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.Iterator;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import javax.annotation.PostConstruct;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.faces.bean.ManagedBean;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import javax.faces.model.SelectItem;
/*  34:    */ import org.primefaces.component.datatable.DataTable;
/*  35:    */ import org.primefaces.model.LazyDataModel;
/*  36:    */ import org.primefaces.model.SortOrder;
/*  37:    */ 
/*  38:    */ @ManagedBean
/*  39:    */ @ViewScoped
/*  40:    */ public class PlanMantenimientoBean
/*  41:    */   extends PageControllerAS2
/*  42:    */ {
/*  43:    */   private static final long serialVersionUID = 1L;
/*  44:    */   @EJB
/*  45:    */   private ServicioPlanMantenimiento servicioPlanMantenimiento;
/*  46:    */   @EJB
/*  47:    */   private ServicioGenerico<ComponenteEquipo> servicioComponenteEquipo;
/*  48:    */   @EJB
/*  49:    */   private ServicioGenerico<ActividadMantenimiento> servicioActividadMantenimiento;
/*  50:    */   @EJB
/*  51:    */   private ServicioGenerico<Frecuencia> servicioFrecuencia;
/*  52:    */   @EJB
/*  53:    */   private ServicioGenerico<CategoriaEquipo> servicioCategoriaEquipo;
/*  54:    */   @EJB
/*  55:    */   private ServicioGenerico<SubcategoriaEquipo> servicioSubcategoriaEquipo;
/*  56:    */   @EJB
/*  57:    */   private ServicioEquipo servicioEquipo;
/*  58:    */   private PlanMantenimiento planMantenimiento;
/*  59:    */   private LazyDataModel<PlanMantenimiento> listaPlanMantenimiento;
/*  60:    */   private List<SelectItem> listaPrioridad;
/*  61:    */   private List<Frecuencia> listaFrecuencia;
/*  62:    */   private List<CategoriaEquipo> listaCategoriaEquipo;
/*  63:    */   private List<SubcategoriaEquipo> listaSubcategoriaEquipo;
/*  64:    */   private Map<Integer, PlanMantenimientoEquipo> mapaEquipo;
/*  65:    */   private List<Equipo> listaEquiposNoAsignados;
/*  66:    */   private DetallePlanMantenimiento detallePlanMantenimiento;
/*  67:    */   private List<Equipo> listaEquiposNoAsignadosFilters;
/*  68:    */   private List<Equipo> listaEquipoSeleccionados;
/*  69:    */   private DataTable dtPlanMantenimiento;
/*  70:    */   private DataTable dtDetallePlanMantenimiento;
/*  71:    */   private DataTable dtDetallePlanMantenimientoFrecuencia;
/*  72:    */   private DataTable dtEquipo;
/*  73:    */   private DataTable dtEquipoNoAsignado;
/*  74:    */   
/*  75:    */   @PostConstruct
/*  76:    */   public void init()
/*  77:    */   {
/*  78: 90 */     this.listaPlanMantenimiento = new LazyDataModel()
/*  79:    */     {
/*  80:    */       private static final long serialVersionUID = 1L;
/*  81:    */       
/*  82:    */       public List<PlanMantenimiento> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  83:    */       {
/*  84: 96 */         filters = PlanMantenimientoBean.this.agregarFiltroOrganizacion(filters);
/*  85:    */         
/*  86: 98 */         List<PlanMantenimiento> lista = new ArrayList();
/*  87: 99 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  88:    */         
/*  89:101 */         lista = PlanMantenimientoBean.this.servicioPlanMantenimiento.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  90:102 */         PlanMantenimientoBean.this.listaPlanMantenimiento.setRowCount(PlanMantenimientoBean.this.servicioPlanMantenimiento.contarPorCriterio(filters));
/*  91:    */         
/*  92:104 */         return lista;
/*  93:    */       }
/*  94:    */     };
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String editar()
/*  98:    */   {
/*  99:117 */     if ((getPlanMantenimiento() != null) && (getPlanMantenimiento().getId() != 0))
/* 100:    */     {
/* 101:118 */       this.planMantenimiento = this.servicioPlanMantenimiento.cargarDetalle(this.planMantenimiento);
/* 102:119 */       setEditado(true);
/* 103:120 */       this.mapaEquipo = new HashMap();
/* 104:121 */       actualizarEquiposNoAsignados();
/* 105:    */     }
/* 106:    */     else
/* 107:    */     {
/* 108:123 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 109:    */     }
/* 110:125 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String guardar()
/* 114:    */   {
/* 115:    */     try
/* 116:    */     {
/* 117:136 */       this.servicioPlanMantenimiento.guardar(this.planMantenimiento);
/* 118:137 */       limpiar();
/* 119:138 */       setEditado(false);
/* 120:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 121:    */     }
/* 122:    */     catch (AS2ExceptionMantenimiento e)
/* 123:    */     {
/* 124:141 */       JsfUtil.addErrorMessage(e, "");
/* 125:    */     }
/* 126:    */     catch (Exception e)
/* 127:    */     {
/* 128:143 */       e.printStackTrace();
/* 129:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 130:    */     }
/* 131:146 */     return "";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String limpiar()
/* 135:    */   {
/* 136:156 */     crearPlanMantenimiento();
/* 137:157 */     this.mapaEquipo = new HashMap();
/* 138:158 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String eliminar()
/* 142:    */   {
/* 143:168 */     if ((getPlanMantenimiento() != null) && (getPlanMantenimiento().getId() != 0)) {
/* 144:    */       try
/* 145:    */       {
/* 146:170 */         this.servicioPlanMantenimiento.eliminar(this.planMantenimiento);
/* 147:171 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 148:    */       }
/* 149:    */       catch (AS2ExceptionMantenimiento e)
/* 150:    */       {
/* 151:173 */         JsfUtil.addErrorMessage(e, "");
/* 152:    */       }
/* 153:    */       catch (Exception e)
/* 154:    */       {
/* 155:175 */         e.printStackTrace();
/* 156:176 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 157:    */       }
/* 158:    */     } else {
/* 159:179 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 160:    */     }
/* 161:181 */     return "";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String cargarDatos()
/* 165:    */   {
/* 166:191 */     return "";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void crearPlanMantenimiento()
/* 170:    */   {
/* 171:198 */     this.planMantenimiento = new PlanMantenimiento();
/* 172:199 */     this.planMantenimiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 173:200 */     this.planMantenimiento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 174:201 */     this.planMantenimiento.setActivo(true);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public PlanMantenimiento getPlanMantenimiento()
/* 178:    */   {
/* 179:210 */     if (this.planMantenimiento == null) {
/* 180:211 */       crearPlanMantenimiento();
/* 181:    */     }
/* 182:213 */     return this.planMantenimiento;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setPlanMantenimiento(PlanMantenimiento PlanMantenimiento)
/* 186:    */   {
/* 187:223 */     this.planMantenimiento = PlanMantenimiento;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public LazyDataModel<PlanMantenimiento> getListaPlanMantenimiento()
/* 191:    */   {
/* 192:232 */     return this.listaPlanMantenimiento;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setListaPlanMantenimiento(LazyDataModel<PlanMantenimiento> listaPlanMantenimiento)
/* 196:    */   {
/* 197:242 */     this.listaPlanMantenimiento = listaPlanMantenimiento;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public DataTable getDtPlanMantenimiento()
/* 201:    */   {
/* 202:251 */     return this.dtPlanMantenimiento;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setDtPlanMantenimiento(DataTable dtPlanMantenimiento)
/* 206:    */   {
/* 207:261 */     this.dtPlanMantenimiento = dtPlanMantenimiento;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public DataTable getDtDetallePlanMantenimiento()
/* 211:    */   {
/* 212:268 */     return this.dtDetallePlanMantenimiento;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setDtDetallePlanMantenimiento(DataTable dtDetallePlanMantenimiento)
/* 216:    */   {
/* 217:276 */     this.dtDetallePlanMantenimiento = dtDetallePlanMantenimiento;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public List<SelectItem> getListaPrioridad()
/* 221:    */   {
/* 222:283 */     if (this.listaPrioridad == null)
/* 223:    */     {
/* 224:284 */       this.listaPrioridad = new ArrayList();
/* 225:285 */       for (PrioridadEnum prioridad : PrioridadEnum.values())
/* 226:    */       {
/* 227:286 */         SelectItem item = new SelectItem(prioridad, prioridad.getNombre());
/* 228:287 */         this.listaPrioridad.add(item);
/* 229:    */       }
/* 230:    */     }
/* 231:291 */     return this.listaPrioridad;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setListaPrioridad(List<SelectItem> listaPrioridad)
/* 235:    */   {
/* 236:299 */     this.listaPrioridad = listaPrioridad;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<ComponenteEquipo> autocompletarComponente(String consulta)
/* 240:    */   {
/* 241:303 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 242:304 */     List<ComponenteEquipo> lista = new ArrayList();
/* 243:305 */     if (consulta != null) {
/* 244:306 */       filters.put("nombre", "%" + consulta + "%");
/* 245:    */     }
/* 246:308 */     lista = this.servicioComponenteEquipo.obtenerListaCombo(ComponenteEquipo.class, "nombre", true, filters);
/* 247:    */     
/* 248:310 */     return lista;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public List<ActividadMantenimiento> autocompletarActividad(String consulta)
/* 252:    */   {
/* 253:314 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 254:315 */     List<ActividadMantenimiento> lista = new ArrayList();
/* 255:316 */     if (consulta != null) {
/* 256:317 */       filters.put("nombre", "%" + consulta + "%");
/* 257:    */     }
/* 258:319 */     lista = this.servicioActividadMantenimiento.obtenerListaCombo(ActividadMantenimiento.class, "nombre", true, filters);
/* 259:    */     
/* 260:321 */     return lista;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public List<Frecuencia> autocompletarFrecuencia(String consulta)
/* 264:    */   {
/* 265:325 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 266:326 */     List<Frecuencia> lista = new ArrayList();
/* 267:327 */     if (consulta != null) {
/* 268:328 */       filters.put("nombre", "%" + consulta + "%");
/* 269:    */     }
/* 270:330 */     lista = this.servicioFrecuencia.obtenerListaCombo(Frecuencia.class, "nombre", true, filters);
/* 271:    */     
/* 272:332 */     return lista;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void agregarDetallePlanMantenimiento()
/* 276:    */   {
/* 277:336 */     DetallePlanMantenimiento dpm = new DetallePlanMantenimiento();
/* 278:337 */     dpm.setIdOrganizacion(this.planMantenimiento.getIdOrganizacion());
/* 279:338 */     dpm.setIdSucursal(this.planMantenimiento.getIdSucursal());
/* 280:339 */     dpm.setPlanMantenimiento(this.planMantenimiento);
/* 281:340 */     this.planMantenimiento.getListaDetallePlanMantenimiento().add(dpm);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public List<DetallePlanMantenimiento> getListaDetallePlanMantenimiento()
/* 285:    */   {
/* 286:344 */     List<DetallePlanMantenimiento> lresult = new ArrayList();
/* 287:345 */     for (DetallePlanMantenimiento dr : this.planMantenimiento.getListaDetallePlanMantenimiento()) {
/* 288:346 */       if (!dr.isEliminado()) {
/* 289:349 */         lresult.add(dr);
/* 290:    */       }
/* 291:    */     }
/* 292:352 */     return lresult;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void eliminarDetalle()
/* 296:    */   {
/* 297:356 */     DetallePlanMantenimiento dpm = (DetallePlanMantenimiento)this.dtDetallePlanMantenimiento.getRowData();
/* 298:357 */     dpm.setEliminado(true);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List<Frecuencia> getListaFrecuencia()
/* 302:    */   {
/* 303:364 */     if (this.listaFrecuencia == null)
/* 304:    */     {
/* 305:365 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 306:366 */       this.listaFrecuencia = this.servicioFrecuencia.obtenerListaCombo(Frecuencia.class, "nombre", true, filters);
/* 307:    */     }
/* 308:369 */     return this.listaFrecuencia;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void agregarDetallePlanMantenimientoFrecuencia()
/* 312:    */   {
/* 313:373 */     DetallePlanMantenimientoFrecuencia dpmf = new DetallePlanMantenimientoFrecuencia();
/* 314:374 */     dpmf.setIdOrganizacion(this.planMantenimiento.getIdOrganizacion());
/* 315:375 */     dpmf.setIdSucursal(this.planMantenimiento.getIdSucursal());
/* 316:376 */     dpmf.setDetallePlanMantenimiento(this.detallePlanMantenimiento);
/* 317:377 */     this.detallePlanMantenimiento.getListaDetallePlanMantenimientoFrecuencia().add(dpmf);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void eliminarDetallePlanMantenimientoFrecuencia()
/* 321:    */   {
/* 322:381 */     DetallePlanMantenimientoFrecuencia rf = (DetallePlanMantenimientoFrecuencia)this.dtDetallePlanMantenimientoFrecuencia.getRowData();
/* 323:382 */     rf.setEliminado(true);
/* 324:    */   }
/* 325:    */   
/* 326:    */   public DataTable getDtDetallePlanMantenimientoFrecuencia()
/* 327:    */   {
/* 328:386 */     return this.dtDetallePlanMantenimientoFrecuencia;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setDtDetallePlanMantenimientoFrecuencia(DataTable dtDetallePlanMantenimientoFrecuencia)
/* 332:    */   {
/* 333:390 */     this.dtDetallePlanMantenimientoFrecuencia = dtDetallePlanMantenimientoFrecuencia;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public List<CategoriaEquipo> getListaCategoriaEquipo()
/* 337:    */   {
/* 338:394 */     if (this.listaCategoriaEquipo == null)
/* 339:    */     {
/* 340:395 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 341:396 */       filtros.put("activo", "true");
/* 342:397 */       this.listaCategoriaEquipo = this.servicioCategoriaEquipo.obtenerListaCombo(CategoriaEquipo.class, "nombre", true, filtros);
/* 343:    */     }
/* 344:399 */     return this.listaCategoriaEquipo;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setListaCategoriaEquipo(List<CategoriaEquipo> listaCategoriaEquipo)
/* 348:    */   {
/* 349:403 */     this.listaCategoriaEquipo = listaCategoriaEquipo;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public List<SubcategoriaEquipo> getListaSubcategoriaEquipo()
/* 353:    */   {
/* 354:407 */     if ((this.listaSubcategoriaEquipo == null) && (this.planMantenimiento.getCategoriaEquipo() != null))
/* 355:    */     {
/* 356:408 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 357:409 */       filtros.put("activo", "true");
/* 358:410 */       filtros.put("categoriaEquipo.idCategoriaEquipo", this.planMantenimiento.getCategoriaEquipo().getId() + "");
/* 359:411 */       this.listaSubcategoriaEquipo = this.servicioSubcategoriaEquipo.obtenerListaCombo(SubcategoriaEquipo.class, "nombre", true, filtros);
/* 360:    */     }
/* 361:413 */     return this.listaSubcategoriaEquipo;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setListaSubcategoriaEquipo(List<SubcategoriaEquipo> listaSubcategoriaEquipo)
/* 365:    */   {
/* 366:417 */     this.listaSubcategoriaEquipo = listaSubcategoriaEquipo;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void actualizarCategoriaEquipo()
/* 370:    */   {
/* 371:421 */     this.planMantenimiento.setSubcategoriaEquipo(null);
/* 372:422 */     this.listaSubcategoriaEquipo = null;
/* 373:423 */     actualizarSubcategoriaEquipo();
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void actualizarSubcategoriaEquipo()
/* 377:    */   {
/* 378:427 */     actualizarListaEquipos();
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void actualizarListaEquipos()
/* 382:    */   {
/* 383:432 */     for (Iterator localIterator = this.planMantenimiento.getListaPlanMantenimientoEquipo().iterator(); localIterator.hasNext();)
/* 384:    */     {
/* 385:432 */       detalle = (PlanMantenimientoEquipo)localIterator.next();
/* 386:433 */       detalle.setEliminado(true);
/* 387:434 */       if (detalle.getEquipo() != null) {
/* 388:435 */         this.mapaEquipo.put(Integer.valueOf(detalle.getEquipo().getId()), detalle);
/* 389:    */       }
/* 390:    */     }
/* 391:    */     PlanMantenimientoEquipo detalle;
/* 392:438 */     Object listaEquipo = obtenerEquiposPorFiltros();
/* 393:439 */     for (Equipo equipo : (List)listaEquipo)
/* 394:    */     {
/* 395:440 */       PlanMantenimientoEquipo detalle = (PlanMantenimientoEquipo)this.mapaEquipo.get(Integer.valueOf(equipo.getId()));
/* 396:441 */       if (detalle == null)
/* 397:    */       {
/* 398:442 */         detalle = new PlanMantenimientoEquipo();
/* 399:443 */         detalle.setIdOrganizacion(this.planMantenimiento.getIdOrganizacion());
/* 400:444 */         detalle.setIdSucursal(this.planMantenimiento.getIdSucursal());
/* 401:445 */         detalle.setPlanMantenimiento(this.planMantenimiento);
/* 402:446 */         detalle.setEquipo(equipo);
/* 403:447 */         this.planMantenimiento.getListaPlanMantenimientoEquipo().add(detalle);
/* 404:448 */         this.mapaEquipo.put(Integer.valueOf(equipo.getId()), detalle);
/* 405:    */       }
/* 406:450 */       detalle.setEliminado(false);
/* 407:    */     }
/* 408:452 */     this.listaEquiposNoAsignados = null;
/* 409:    */   }
/* 410:    */   
/* 411:    */   private List<Equipo> obtenerEquiposPorFiltros()
/* 412:    */   {
/* 413:456 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 414:457 */     filtros.put("activo", "true");
/* 415:458 */     if (this.planMantenimiento.getCategoriaEquipo() != null) {
/* 416:459 */       filtros.put("subcategoriaEquipo.categoriaEquipo.idCategoriaEquipo", this.planMantenimiento.getCategoriaEquipo().getId() + "");
/* 417:    */     }
/* 418:461 */     if (this.planMantenimiento.getSubcategoriaEquipo() != null) {
/* 419:462 */       filtros.put("subcategoriaEquipo.idSubcategoriaEquipo", this.planMantenimiento.getSubcategoriaEquipo().getId() + "");
/* 420:    */     }
/* 421:464 */     List<Equipo> listaEquipo = this.servicioEquipo.obtenerListaPorPagina(0, 10000, "nombre", true, filtros);
/* 422:465 */     return listaEquipo;
/* 423:    */   }
/* 424:    */   
/* 425:    */   private void actualizarEquiposNoAsignados()
/* 426:    */   {
/* 427:469 */     this.listaEquiposNoAsignados = null;
/* 428:    */     
/* 429:471 */     List<Equipo> listaEquipo = obtenerEquiposPorFiltros();
/* 430:472 */     for (Equipo equipo : listaEquipo)
/* 431:    */     {
/* 432:473 */       boolean encontre = false;
/* 433:474 */       for (PlanMantenimientoEquipo detalleEquipo : getListaDetalleEquipo()) {
/* 434:475 */         if ((detalleEquipo.getEquipo() != null) && (detalleEquipo.getEquipo().getId() == equipo.getId()))
/* 435:    */         {
/* 436:476 */           encontre = true;
/* 437:477 */           break;
/* 438:    */         }
/* 439:    */       }
/* 440:480 */       if (!encontre) {
/* 441:481 */         getListaEquiposNoAsignados().add(equipo);
/* 442:    */       }
/* 443:    */     }
/* 444:    */   }
/* 445:    */   
/* 446:    */   public List<PlanMantenimientoEquipo> getListaDetalleEquipo()
/* 447:    */   {
/* 448:487 */     List<PlanMantenimientoEquipo> lista = new ArrayList();
/* 449:488 */     for (PlanMantenimientoEquipo detalle : this.planMantenimiento.getListaPlanMantenimientoEquipo()) {
/* 450:489 */       if (!detalle.isEliminado()) {
/* 451:490 */         lista.add(detalle);
/* 452:    */       }
/* 453:    */     }
/* 454:494 */     return lista;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public void agregarDetalleEquipo()
/* 458:    */   {
/* 459:498 */     PlanMantenimientoEquipo detalle = new PlanMantenimientoEquipo();
/* 460:499 */     detalle.setIdOrganizacion(this.planMantenimiento.getIdOrganizacion());
/* 461:500 */     detalle.setIdSucursal(this.planMantenimiento.getIdSucursal());
/* 462:501 */     detalle.setPlanMantenimiento(this.planMantenimiento);
/* 463:502 */     this.planMantenimiento.getListaPlanMantenimientoEquipo().add(detalle);
/* 464:    */   }
/* 465:    */   
/* 466:    */   public DataTable getDtEquipo()
/* 467:    */   {
/* 468:506 */     return this.dtEquipo;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void setDtEquipo(DataTable dtEquipo)
/* 472:    */   {
/* 473:510 */     this.dtEquipo = dtEquipo;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public List<Equipo> autocompletarEquipo(String consulta)
/* 477:    */   {
/* 478:514 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 479:515 */     filtros.put("activo", "true");
/* 480:516 */     filtros.put("OR~codigo", "%" + consulta + "%");
/* 481:517 */     filtros.put("OR~numeroSerie", "%" + consulta + "%");
/* 482:518 */     filtros.put("OR~codigoBarras", "%" + consulta + "%");
/* 483:519 */     filtros.put("OR~nombre", "%" + consulta + "%");
/* 484:520 */     return this.servicioEquipo.obtenerListaPorPagina(0, 50, "nombre", true, filtros);
/* 485:    */   }
/* 486:    */   
/* 487:    */   public void eliminarDetalleEquipo()
/* 488:    */   {
/* 489:524 */     PlanMantenimientoEquipo detalle = (PlanMantenimientoEquipo)this.dtEquipo.getRowData();
/* 490:525 */     detalle.setEliminado(true);
/* 491:    */     
/* 492:527 */     getListaEquiposNoAsignados().add(detalle.getEquipo());
/* 493:528 */     this.dtEquipoNoAsignado.reset();
/* 494:    */   }
/* 495:    */   
/* 496:    */   public List<Equipo> getListaEquiposNoAsignados()
/* 497:    */   {
/* 498:532 */     if (this.listaEquiposNoAsignados == null) {
/* 499:533 */       this.listaEquiposNoAsignados = new ArrayList();
/* 500:    */     }
/* 501:535 */     return this.listaEquiposNoAsignados;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public void setListaEquiposNoAsignados(List<Equipo> listaEquiposNoAsignados)
/* 505:    */   {
/* 506:539 */     this.listaEquiposNoAsignados = listaEquiposNoAsignados;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public DataTable getDtEquipoNoAsignado()
/* 510:    */   {
/* 511:543 */     return this.dtEquipoNoAsignado;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void setDtEquipoNoAsignado(DataTable dtEquipoNoAsignado)
/* 515:    */   {
/* 516:547 */     this.dtEquipoNoAsignado = dtEquipoNoAsignado;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public void asignarEquipo()
/* 520:    */   {
/* 521:551 */     Equipo equipo = (Equipo)this.dtEquipoNoAsignado.getRowData();
/* 522:552 */     PlanMantenimientoEquipo detalle = new PlanMantenimientoEquipo();
/* 523:553 */     detalle.setIdOrganizacion(this.planMantenimiento.getIdOrganizacion());
/* 524:554 */     detalle.setIdSucursal(this.planMantenimiento.getIdSucursal());
/* 525:555 */     detalle.setPlanMantenimiento(this.planMantenimiento);
/* 526:556 */     detalle.setEquipo(equipo);
/* 527:557 */     this.planMantenimiento.getListaPlanMantenimientoEquipo().add(detalle);
/* 528:558 */     getListaEquiposNoAsignados().remove(equipo);
/* 529:559 */     this.dtEquipoNoAsignado.reset();
/* 530:    */   }
/* 531:    */   
/* 532:    */   public void asignarEquipos()
/* 533:    */   {
/* 534:563 */     for (Equipo equipo : this.listaEquipoSeleccionados)
/* 535:    */     {
/* 536:564 */       PlanMantenimientoEquipo detalle = new PlanMantenimientoEquipo();
/* 537:565 */       detalle.setIdOrganizacion(this.planMantenimiento.getIdOrganizacion());
/* 538:566 */       detalle.setIdSucursal(this.planMantenimiento.getIdSucursal());
/* 539:567 */       detalle.setPlanMantenimiento(this.planMantenimiento);
/* 540:568 */       detalle.setEquipo(equipo);
/* 541:569 */       this.planMantenimiento.getListaPlanMantenimientoEquipo().add(detalle);
/* 542:570 */       getListaEquiposNoAsignados().remove(equipo);
/* 543:    */     }
/* 544:572 */     this.dtEquipoNoAsignado.reset();
/* 545:    */   }
/* 546:    */   
/* 547:    */   public DetallePlanMantenimiento getDetallePlanMantenimiento()
/* 548:    */   {
/* 549:576 */     return this.detallePlanMantenimiento;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void setDetallePlanMantenimiento(DetallePlanMantenimiento detallePlanMantenimiento)
/* 553:    */   {
/* 554:580 */     this.detallePlanMantenimiento = detallePlanMantenimiento;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public List<Equipo> getListaEquiposNoAsignadosFilters()
/* 558:    */   {
/* 559:584 */     return this.listaEquiposNoAsignadosFilters;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public void setListaEquiposNoAsignadosFilters(List<Equipo> listaEquiposNoAsignadosFilters)
/* 563:    */   {
/* 564:588 */     this.listaEquiposNoAsignadosFilters = listaEquiposNoAsignadosFilters;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public List<Equipo> getListaEquipoSeleccionados()
/* 568:    */   {
/* 569:592 */     return this.listaEquipoSeleccionados;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public void setListaEquipoSeleccionados(List<Equipo> listaEquipoSeleccionados)
/* 573:    */   {
/* 574:596 */     this.listaEquipoSeleccionados = listaEquipoSeleccionados;
/* 575:    */   }
/* 576:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.PlanMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */