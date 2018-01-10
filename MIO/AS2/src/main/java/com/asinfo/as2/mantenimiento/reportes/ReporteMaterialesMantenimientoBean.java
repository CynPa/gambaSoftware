/*   1:    */ package com.asinfo.as2.mantenimiento.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.DetalleComponenteEquipo;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.OrdenTrabajoMantenimiento;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
/*  11:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  12:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioActividadMantenimiento;
/*  13:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioEquipo;
/*  14:    */ import com.asinfo.as2.mantenimiento.procesos.servicio.ServicioOrdenTrabajoMantenimiento;
/*  15:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  18:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  19:    */ import java.io.IOException;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Calendar;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.HashSet;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import java.util.Set;
/*  27:    */ import javax.annotation.PostConstruct;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import javax.faces.model.SelectItem;
/*  32:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  33:    */ import net.sf.jasperreports.engine.JRException;
/*  34:    */ import org.apache.log4j.Logger;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class ReporteMaterialesMantenimientoBean
/*  39:    */   extends AbstractBaseReportBean
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = 7346351381439272274L;
/*  42:    */   @EJB
/*  43:    */   private ServicioGenerico<CategoriaEquipo> servicioCategoriaEquipo;
/*  44:    */   @EJB
/*  45:    */   private ServicioGenerico<SubcategoriaEquipo> servicioSubcategoriaEquipo;
/*  46:    */   @EJB
/*  47:    */   private ServicioEquipo servicioEquipo;
/*  48:    */   @EJB
/*  49:    */   private ServicioGenerico<ComponenteEquipo> servicioComponenteEquipo;
/*  50:    */   @EJB
/*  51:    */   private ServicioGenerico<DetalleComponenteEquipo> servicioDetalleComponenteEquipo;
/*  52:    */   @EJB
/*  53:    */   private ServicioActividadMantenimiento servicioActividadMantenimiento;
/*  54:    */   @EJB
/*  55:    */   private ServicioOrdenTrabajoMantenimiento servicioOrdenTrabajoMantenimiento;
/*  56:    */   private Date fechaDesde;
/*  57:    */   private Date fechaHasta;
/*  58:    */   private CategoriaEquipo categoriaEquipo;
/*  59:    */   private SubcategoriaEquipo subcategoriaEquipo;
/*  60:    */   private Equipo equipo;
/*  61:    */   private ComponenteEquipo componenteEquipo;
/*  62:    */   private ActividadMantenimiento actividad;
/*  63:    */   private OrdenTrabajoMantenimiento ordenTrabajoMantenimiento;
/*  64:    */   private TipoReporte tipoReporte;
/*  65:    */   private Agrupar agrupar;
/*  66:    */   private List<SelectItem> listaTipoReporte;
/*  67:    */   private List<SelectItem> listaAgrupar;
/*  68:    */   private List<CategoriaEquipo> listaCategoriaEquipo;
/*  69:    */   private List<SubcategoriaEquipo> listaSubcategoriaEquipo;
/*  70:    */   private List<ComponenteEquipo> listaComponenteEquipo;
/*  71:    */   private List<ActividadMantenimiento> listaActividad;
/*  72:    */   
/*  73:    */   static enum TipoReporte
/*  74:    */   {
/*  75: 79 */     COSTO_HISTORICO_MANTENIMIENTO("Reporte Costo Historico Mantenimiento"),  REPORTE_MATERIALES("Reporte de Materiales"),  HORAS_HOMBRE_MANTENIMIENTO("Reporte Horas Hombre Por Mantenimiento");
/*  76:    */     
/*  77:    */     private String nombre;
/*  78:    */     
/*  79:    */     private TipoReporte(String nombre)
/*  80:    */     {
/*  81: 85 */       this.nombre = nombre;
/*  82:    */     }
/*  83:    */     
/*  84:    */     public String getNombre()
/*  85:    */     {
/*  86: 89 */       return this.nombre;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public void setNombre(String nombre)
/*  90:    */     {
/*  91: 93 */       this.nombre = nombre;
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   static enum Agrupar
/*  96:    */   {
/*  97: 99 */     EQUIPO("Equipo"),  ORDEN_TRABAJO_MANTENIMIENTO("Orden Trabajo Mantenimiento"),  MATERIAL("Materiales");
/*  98:    */     
/*  99:    */     private String nombre;
/* 100:    */     
/* 101:    */     private Agrupar(String nombre)
/* 102:    */     {
/* 103:104 */       this.nombre = nombre;
/* 104:    */     }
/* 105:    */     
/* 106:    */     public String getNombre()
/* 107:    */     {
/* 108:108 */       return this.nombre;
/* 109:    */     }
/* 110:    */     
/* 111:    */     public void setNombre(String nombre)
/* 112:    */     {
/* 113:112 */       this.nombre = nombre;
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:142 */   private final String COMPILE_FILE_MATERIAL_EQUIPO = "reporteMaterialesMantenimiento_Equipo";
/* 118:143 */   private final String COMPILE_FILE_MATERIAL_OTM = "reporteMaterialesMantenimiento_Otm";
/* 119:144 */   private final String COMPILE_FILE_MATERIAL = "reporteMaterialesMantenimiento";
/* 120:146 */   private final String TIPO_REPORTE = "materiales";
/* 121:    */   
/* 122:    */   @PostConstruct
/* 123:    */   public void init()
/* 124:    */   {
/* 125:150 */     Calendar calfechaDesde = Calendar.getInstance();
/* 126:151 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 127:152 */     this.fechaDesde = calfechaDesde.getTime();
/* 128:153 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected JRDataSource getJRDataSource()
/* 132:    */   {
/* 133:160 */     List listaDatosReporte = new ArrayList();
/* 134:161 */     JRDataSource ds = null;
/* 135:    */     try
/* 136:    */     {
/* 137:164 */       if (this.agrupar.equals(Agrupar.EQUIPO)) {
/* 138:165 */         listaDatosReporte = this.servicioOrdenTrabajoMantenimiento.getReporteCostoHistoricoMantenimiento(this.fechaDesde, this.fechaHasta, this.categoriaEquipo, this.subcategoriaEquipo, this.equipo, this.componenteEquipo, this.actividad, this.ordenTrabajoMantenimiento, "materiales", Agrupar.EQUIPO
/* 139:166 */           .ordinal());
/* 140:167 */       } else if (this.agrupar.equals(Agrupar.MATERIAL)) {
/* 141:168 */         listaDatosReporte = this.servicioOrdenTrabajoMantenimiento.getReporteCostoHistoricoMantenimiento(this.fechaDesde, this.fechaHasta, this.categoriaEquipo, this.subcategoriaEquipo, this.equipo, this.componenteEquipo, this.actividad, this.ordenTrabajoMantenimiento, "materiales", Agrupar.MATERIAL
/* 142:169 */           .ordinal());
/* 143:170 */       } else if (this.agrupar.equals(Agrupar.ORDEN_TRABAJO_MANTENIMIENTO)) {
/* 144:171 */         listaDatosReporte = this.servicioOrdenTrabajoMantenimiento.getReporteCostoHistoricoMantenimiento(this.fechaDesde, this.fechaHasta, this.categoriaEquipo, this.subcategoriaEquipo, this.equipo, this.componenteEquipo, this.actividad, this.ordenTrabajoMantenimiento, "materiales", Agrupar.ORDEN_TRABAJO_MANTENIMIENTO
/* 145:    */         
/* 146:173 */           .ordinal());
/* 147:    */       }
/* 148:175 */       String[] fields = { "f_fecha", "f_numero_otm", "f_equipo", "f_material", "f_componente", "f_actividad", "f_unidad", "f_cantidad_requerida", "f_cantidad_despachada", "f_cantidad_devuelta", "f_cantidad", "f_cantidad_consumida", "f_cantidad_planificada" };
/* 149:    */       
/* 150:    */ 
/* 151:178 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 152:    */     }
/* 153:    */     catch (Exception e)
/* 154:    */     {
/* 155:182 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 156:    */     }
/* 157:185 */     return ds;
/* 158:    */   }
/* 159:    */   
/* 160:    */   protected String getCompileFileName()
/* 161:    */   {
/* 162:190 */     if (this.agrupar.equals(Agrupar.EQUIPO)) {
/* 163:191 */       return "reporteMaterialesMantenimiento_Equipo";
/* 164:    */     }
/* 165:192 */     if (this.agrupar.equals(Agrupar.ORDEN_TRABAJO_MANTENIMIENTO)) {
/* 166:193 */       return "reporteMaterialesMantenimiento_Otm";
/* 167:    */     }
/* 168:194 */     if (this.agrupar.equals(Agrupar.MATERIAL)) {
/* 169:195 */       return "reporteMaterialesMantenimiento";
/* 170:    */     }
/* 171:197 */     return "";
/* 172:    */   }
/* 173:    */   
/* 174:    */   protected Map<String, Object> getReportParameters()
/* 175:    */   {
/* 176:203 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 177:204 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 178:205 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 179:206 */     reportParameters.put("p_categoria", this.categoriaEquipo == null ? "TODAS" : this.categoriaEquipo.getNombre());
/* 180:207 */     reportParameters.put("p_subcategoria", this.subcategoriaEquipo == null ? "TODAS" : this.subcategoriaEquipo.getNombre());
/* 181:208 */     reportParameters.put("p_equipo", this.equipo == null ? "TODOS" : this.equipo.getNombre());
/* 182:209 */     reportParameters.put("p_componente_equipo", this.componenteEquipo == null ? "TODOS" : this.componenteEquipo.getNombre());
/* 183:210 */     reportParameters.put("p_actividad", this.actividad == null ? "TODOS" : this.actividad.getNombre());
/* 184:211 */     reportParameters.put("p_numero_otm", this.ordenTrabajoMantenimiento == null ? "TODOS" : this.ordenTrabajoMantenimiento.getNumero());
/* 185:212 */     reportParameters.put("ReportTitle", "Reporte Materiales");
/* 186:213 */     if (this.agrupar.equals(Agrupar.EQUIPO)) {
/* 187:214 */       reportParameters.put("p_agrupar", "f_equipo");
/* 188:215 */     } else if (this.agrupar.equals(Agrupar.ORDEN_TRABAJO_MANTENIMIENTO)) {
/* 189:216 */       reportParameters.put("p_agrupar", "f_numero_otm");
/* 190:217 */     } else if (this.agrupar.equals(Agrupar.MATERIAL)) {
/* 191:218 */       reportParameters.put("p_agrupar", "f_material");
/* 192:    */     }
/* 193:220 */     return reportParameters;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String execute()
/* 197:    */   {
/* 198:    */     try
/* 199:    */     {
/* 200:226 */       super.prepareReport();
/* 201:    */     }
/* 202:    */     catch (JRException e)
/* 203:    */     {
/* 204:228 */       LOG.info("Error JRException");
/* 205:229 */       e.printStackTrace();
/* 206:230 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 207:    */     }
/* 208:    */     catch (IOException e)
/* 209:    */     {
/* 210:232 */       LOG.info("Error IOException");
/* 211:233 */       e.printStackTrace();
/* 212:234 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 213:    */     }
/* 214:236 */     return "";
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Date getFechaDesde()
/* 218:    */   {
/* 219:240 */     return this.fechaDesde;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setFechaDesde(Date fechaDesde)
/* 223:    */   {
/* 224:244 */     this.fechaDesde = fechaDesde;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public Date getFechaHasta()
/* 228:    */   {
/* 229:248 */     return this.fechaHasta;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setFechaHasta(Date fechaHasta)
/* 233:    */   {
/* 234:252 */     this.fechaHasta = fechaHasta;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public CategoriaEquipo getCategoriaEquipo()
/* 238:    */   {
/* 239:256 */     return this.categoriaEquipo;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setCategoriaEquipo(CategoriaEquipo categoriaEquipo)
/* 243:    */   {
/* 244:260 */     this.categoriaEquipo = categoriaEquipo;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public SubcategoriaEquipo getSubcategoriaEquipo()
/* 248:    */   {
/* 249:264 */     return this.subcategoriaEquipo;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setSubcategoriaEquipo(SubcategoriaEquipo subcategoriaEquipo)
/* 253:    */   {
/* 254:268 */     this.subcategoriaEquipo = subcategoriaEquipo;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Equipo getEquipo()
/* 258:    */   {
/* 259:272 */     return this.equipo;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setEquipo(Equipo equipo)
/* 263:    */   {
/* 264:276 */     this.equipo = equipo;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public ComponenteEquipo getComponenteEquipo()
/* 268:    */   {
/* 269:280 */     return this.componenteEquipo;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setComponenteEquipo(ComponenteEquipo componenteEquipo)
/* 273:    */   {
/* 274:284 */     this.componenteEquipo = componenteEquipo;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public ActividadMantenimiento getActividad()
/* 278:    */   {
/* 279:288 */     return this.actividad;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setActividad(ActividadMantenimiento actividad)
/* 283:    */   {
/* 284:292 */     this.actividad = actividad;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public OrdenTrabajoMantenimiento getOrdenTrabajoMantenimiento()
/* 288:    */   {
/* 289:296 */     return this.ordenTrabajoMantenimiento;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setOrdenTrabajoMantenimiento(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 293:    */   {
/* 294:300 */     this.ordenTrabajoMantenimiento = ordenTrabajoMantenimiento;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<CategoriaEquipo> getListaCategoriaEquipo()
/* 298:    */   {
/* 299:304 */     if (this.listaCategoriaEquipo == null)
/* 300:    */     {
/* 301:305 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 302:306 */       filtros.put("activo", "true");
/* 303:307 */       this.listaCategoriaEquipo = this.servicioCategoriaEquipo.obtenerListaCombo(CategoriaEquipo.class, "nombre", true, filtros);
/* 304:    */     }
/* 305:309 */     return this.listaCategoriaEquipo;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public List<SubcategoriaEquipo> getListaSubcategoriaEquipo()
/* 309:    */   {
/* 310:313 */     if ((this.listaSubcategoriaEquipo == null) && (this.categoriaEquipo != null))
/* 311:    */     {
/* 312:314 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 313:315 */       filtros.put("activo", "true");
/* 314:316 */       filtros.put("categoriaEquipo.idCategoriaEquipo", this.categoriaEquipo.getId() + "");
/* 315:317 */       this.listaSubcategoriaEquipo = this.servicioSubcategoriaEquipo.obtenerListaCombo(SubcategoriaEquipo.class, "nombre", true, filtros);
/* 316:    */     }
/* 317:319 */     return this.listaSubcategoriaEquipo;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public List<Equipo> autocompletarEquipo(String consulta)
/* 321:    */   {
/* 322:323 */     Map<String, String> filtro = agregarFiltroOrganizacion(null);
/* 323:324 */     filtro.put("activo", "true");
/* 324:325 */     filtro.put("OR~codigo", "%" + consulta + "%");
/* 325:326 */     filtro.put("OR~numeroSerie", "%" + consulta + "%");
/* 326:327 */     filtro.put("OR~codigoBarras", "%" + consulta + "%");
/* 327:328 */     filtro.put("OR~nombre", "%" + consulta + "%");
/* 328:329 */     if (this.subcategoriaEquipo != null) {
/* 329:330 */       filtro.put("subcategoriaEquipo.idSubcategoriaEquipo", this.subcategoriaEquipo.getId() + "");
/* 330:331 */     } else if (this.categoriaEquipo != null) {
/* 331:332 */       filtro.put("subcategoriaEquipo.categoriaEquipo.idCategoriaEquipo", this.categoriaEquipo.getId() + "");
/* 332:    */     }
/* 333:334 */     return this.servicioEquipo.obtenerListaPorPagina(0, 50, "nombre", true, filtro);
/* 334:    */   }
/* 335:    */   
/* 336:    */   public List<ComponenteEquipo> getListaComponenteEquipo()
/* 337:    */   {
/* 338:    */     Set<Integer> listaIdComponenteEquipo;
/* 339:338 */     if (this.listaComponenteEquipo == null)
/* 340:    */     {
/* 341:339 */       this.listaComponenteEquipo = new ArrayList();
/* 342:340 */       listaIdComponenteEquipo = new HashSet();
/* 343:    */       
/* 344:342 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 345:343 */       filtros.put("componenteEquipo.activo", "true");
/* 346:344 */       if (this.equipo != null) {
/* 347:345 */         filtros.put("equipo.idEquipo", this.equipo.getId() + "");
/* 348:    */       }
/* 349:348 */       List<String> listaCampos = new ArrayList();
/* 350:349 */       listaCampos.add("equipo");
/* 351:350 */       listaCampos.add("componenteEquipo");
/* 352:351 */       List<DetalleComponenteEquipo> lista = this.servicioDetalleComponenteEquipo.obtenerListaPorPagina(DetalleComponenteEquipo.class, 0, 1000, "componenteEquipo.nombre", true, filtros, listaCampos);
/* 353:353 */       for (DetalleComponenteEquipo detalleComponenteEquipo : lista) {
/* 354:354 */         if (!listaIdComponenteEquipo.contains(Integer.valueOf(detalleComponenteEquipo.getComponenteEquipo().getId())))
/* 355:    */         {
/* 356:355 */           this.listaComponenteEquipo.add(detalleComponenteEquipo.getComponenteEquipo());
/* 357:356 */           listaIdComponenteEquipo.add(Integer.valueOf(detalleComponenteEquipo.getComponenteEquipo().getId()));
/* 358:    */         }
/* 359:    */       }
/* 360:    */     }
/* 361:360 */     return this.listaComponenteEquipo;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public List<ActividadMantenimiento> getListaActividad()
/* 365:    */   {
/* 366:364 */     if (this.listaActividad == null)
/* 367:    */     {
/* 368:365 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 369:366 */       filtros.put("activo", "true");
/* 370:367 */       this.listaActividad = this.servicioActividadMantenimiento.obtenerListaCombo("nombre", true, filtros);
/* 371:    */     }
/* 372:369 */     return this.listaActividad;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public List<OrdenTrabajoMantenimiento> autocompletarOrdenTrabajo(String consulta)
/* 376:    */   {
/* 377:373 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 378:374 */     filtros.put("estado", Estado.CERRADO.toString());
/* 379:375 */     filtros.put("numero", "%" + consulta + "%");
/* 380:376 */     return this.servicioOrdenTrabajoMantenimiento.obtenerListaCombo("numero", true, filtros);
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void actualizarTipoActividad()
/* 384:    */   {
/* 385:380 */     this.actividad = null;
/* 386:381 */     this.listaActividad = null;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void actualizarCategoriaEquipo()
/* 390:    */   {
/* 391:385 */     this.subcategoriaEquipo = null;
/* 392:386 */     this.listaSubcategoriaEquipo = null;
/* 393:387 */     actualizarSubcategoriaEquipo();
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void actualizarSubcategoriaEquipo()
/* 397:    */   {
/* 398:391 */     this.equipo = null;
/* 399:392 */     actualizarEquipo();
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void actualizarEquipo()
/* 403:    */   {
/* 404:396 */     this.componenteEquipo = null;
/* 405:397 */     this.listaComponenteEquipo = null;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public TipoReporte getTipoReporte()
/* 409:    */   {
/* 410:402 */     return this.tipoReporte;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 414:    */   {
/* 415:406 */     this.tipoReporte = tipoReporte;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public List<SelectItem> getListaTipoReporte()
/* 419:    */   {
/* 420:410 */     this.listaTipoReporte = new ArrayList();
/* 421:411 */     for (TipoReporte tipoReporte : TipoReporte.values())
/* 422:    */     {
/* 423:412 */       SelectItem item = new SelectItem(tipoReporte, tipoReporte.getNombre());
/* 424:413 */       this.listaTipoReporte.add(item);
/* 425:    */     }
/* 426:416 */     return this.listaTipoReporte;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public Agrupar getAgrupar()
/* 430:    */   {
/* 431:420 */     return this.agrupar;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void setAgrupar(Agrupar agrupar)
/* 435:    */   {
/* 436:424 */     this.agrupar = agrupar;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public List<SelectItem> getListaAgrupar()
/* 440:    */   {
/* 441:428 */     this.listaAgrupar = new ArrayList();
/* 442:429 */     for (Agrupar agruparCosto : Agrupar.values())
/* 443:    */     {
/* 444:430 */       SelectItem item = new SelectItem(agruparCosto, agruparCosto.getNombre());
/* 445:431 */       this.listaAgrupar.add(item);
/* 446:    */     }
/* 447:433 */     return this.listaAgrupar;
/* 448:    */   }
/* 449:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.reportes.ReporteMaterialesMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */