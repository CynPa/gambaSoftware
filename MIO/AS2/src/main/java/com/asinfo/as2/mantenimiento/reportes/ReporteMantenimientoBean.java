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
/*  20:    */ import java.io.PrintStream;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Calendar;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.HashSet;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import java.util.Set;
/*  28:    */ import javax.annotation.PostConstruct;
/*  29:    */ import javax.ejb.EJB;
/*  30:    */ import javax.faces.bean.ManagedBean;
/*  31:    */ import javax.faces.bean.ViewScoped;
/*  32:    */ import javax.faces.model.SelectItem;
/*  33:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  34:    */ import net.sf.jasperreports.engine.JRException;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ 
/*  37:    */ @ManagedBean
/*  38:    */ @ViewScoped
/*  39:    */ public class ReporteMantenimientoBean
/*  40:    */   extends AbstractBaseReportBean
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 7346351381439272274L;
/*  43:    */   @EJB
/*  44:    */   private ServicioGenerico<CategoriaEquipo> servicioCategoriaEquipo;
/*  45:    */   @EJB
/*  46:    */   private ServicioGenerico<SubcategoriaEquipo> servicioSubcategoriaEquipo;
/*  47:    */   @EJB
/*  48:    */   private ServicioEquipo servicioEquipo;
/*  49:    */   @EJB
/*  50:    */   private ServicioGenerico<ComponenteEquipo> servicioComponenteEquipo;
/*  51:    */   @EJB
/*  52:    */   private ServicioGenerico<DetalleComponenteEquipo> servicioDetalleComponenteEquipo;
/*  53:    */   @EJB
/*  54:    */   private ServicioActividadMantenimiento servicioActividadMantenimiento;
/*  55:    */   @EJB
/*  56:    */   private ServicioOrdenTrabajoMantenimiento servicioOrdenTrabajoMantenimiento;
/*  57:    */   private Date fechaDesde;
/*  58:    */   private Date fechaHasta;
/*  59:    */   private CategoriaEquipo categoriaEquipo;
/*  60:    */   private SubcategoriaEquipo subcategoriaEquipo;
/*  61:    */   private Equipo equipo;
/*  62:    */   private ComponenteEquipo componenteEquipo;
/*  63:    */   private ActividadMantenimiento actividad;
/*  64:    */   private OrdenTrabajoMantenimiento ordenTrabajoMantenimiento;
/*  65:    */   private TipoReporte tipoReporte;
/*  66:    */   private Agrupar agrupar;
/*  67:    */   private List<SelectItem> listaTipoReporte;
/*  68:    */   private List<SelectItem> listaAgrupar;
/*  69:    */   private List<CategoriaEquipo> listaCategoriaEquipo;
/*  70:    */   private List<SubcategoriaEquipo> listaSubcategoriaEquipo;
/*  71:    */   private List<ComponenteEquipo> listaComponenteEquipo;
/*  72:    */   private List<ActividadMantenimiento> listaActividad;
/*  73:    */   
/*  74:    */   static enum TipoReporte
/*  75:    */   {
/*  76: 79 */     COSTO_HISTORICO_MANTENIMIENTO("Reporte Costo Historico Mantenimiento"),  REPORTE_MATERIALES("Reporte de Materiales"),  HORAS_HOMBRE_MANTENIMIENTO("Reporte Horas Hombre Por Mantenimiento");
/*  77:    */     
/*  78:    */     private String nombre;
/*  79:    */     
/*  80:    */     private TipoReporte(String nombre)
/*  81:    */     {
/*  82: 85 */       this.nombre = nombre;
/*  83:    */     }
/*  84:    */     
/*  85:    */     public String getNombre()
/*  86:    */     {
/*  87: 89 */       return this.nombre;
/*  88:    */     }
/*  89:    */     
/*  90:    */     public void setNombre(String nombre)
/*  91:    */     {
/*  92: 93 */       this.nombre = nombre;
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   static enum Agrupar
/*  97:    */   {
/*  98: 99 */     EQUIPO("Equipo"),  ORDEN_TRABAJO_MANTENIMIENTO("Orden Trabajo Mantenimiento");
/*  99:    */     
/* 100:    */     private String nombre;
/* 101:    */     
/* 102:    */     private Agrupar(String nombre)
/* 103:    */     {
/* 104:104 */       this.nombre = nombre;
/* 105:    */     }
/* 106:    */     
/* 107:    */     public String getNombre()
/* 108:    */     {
/* 109:108 */       return this.nombre;
/* 110:    */     }
/* 111:    */     
/* 112:    */     public void setNombre(String nombre)
/* 113:    */     {
/* 114:112 */       this.nombre = nombre;
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:142 */   private final String COMPILE_FILE_NAME = "reporteCostoHistoricoMantenimiento";
/* 119:143 */   private final String COMPILE_FILE_NAME_OTM = "reporteCostoHistoricoMantenimiento_Otm";
/* 120:    */   
/* 121:    */   @PostConstruct
/* 122:    */   public void init()
/* 123:    */   {
/* 124:150 */     Calendar calfechaDesde = Calendar.getInstance();
/* 125:151 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 126:152 */     this.fechaDesde = calfechaDesde.getTime();
/* 127:153 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected JRDataSource getJRDataSource()
/* 131:    */   {
/* 132:160 */     List listaDatosReporte = new ArrayList();
/* 133:161 */     JRDataSource ds = null;
/* 134:    */     try
/* 135:    */     {
/* 136:164 */       if (this.agrupar.equals(Agrupar.EQUIPO)) {
/* 137:165 */         listaDatosReporte = this.servicioOrdenTrabajoMantenimiento.getReporteCostoHistoricoMantenimiento(this.fechaDesde, this.fechaHasta, this.categoriaEquipo, this.subcategoriaEquipo, this.equipo, this.componenteEquipo, this.actividad, this.ordenTrabajoMantenimiento, "costo", Agrupar.EQUIPO
/* 138:166 */           .ordinal());
/* 139:168 */       } else if (this.agrupar.equals(Agrupar.ORDEN_TRABAJO_MANTENIMIENTO)) {
/* 140:169 */         listaDatosReporte = this.servicioOrdenTrabajoMantenimiento.getReporteCostoHistoricoMantenimiento(this.fechaDesde, this.fechaHasta, this.categoriaEquipo, this.subcategoriaEquipo, this.equipo, this.componenteEquipo, this.actividad, this.ordenTrabajoMantenimiento, "costo", Agrupar.ORDEN_TRABAJO_MANTENIMIENTO
/* 141:    */         
/* 142:171 */           .ordinal());
/* 143:    */       }
/* 144:173 */       String[] fields = { "f_fecha", "f_numero_otm", "f_equipo", "f_componente", "f_actividad", "f_horas_paro", "f_tiempo_real", "f_costo_hora_hombre", "f_nocturno", "f_operacion" };
/* 145:    */       
/* 146:175 */       System.out.println(">>>>>El tamaño de la lista es:" + listaDatosReporte.size());
/* 147:176 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 148:    */     }
/* 149:    */     catch (Exception e)
/* 150:    */     {
/* 151:180 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 152:    */     }
/* 153:183 */     return ds;
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected String getCompileFileName()
/* 157:    */   {
/* 158:188 */     if (this.agrupar.equals(Agrupar.EQUIPO)) {
/* 159:189 */       return "reporteCostoHistoricoMantenimiento";
/* 160:    */     }
/* 161:190 */     if (this.agrupar.equals(Agrupar.ORDEN_TRABAJO_MANTENIMIENTO)) {
/* 162:191 */       return "reporteCostoHistoricoMantenimiento_Otm";
/* 163:    */     }
/* 164:193 */     return "";
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected Map<String, Object> getReportParameters()
/* 168:    */   {
/* 169:199 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 170:200 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 171:201 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 172:202 */     reportParameters.put("p_categoria", this.categoriaEquipo == null ? "TODAS" : this.categoriaEquipo.getNombre());
/* 173:203 */     reportParameters.put("p_subcategoria", this.subcategoriaEquipo == null ? "TODAS" : this.subcategoriaEquipo.getNombre());
/* 174:204 */     reportParameters.put("p_equipo", this.equipo == null ? "TODOS" : this.equipo.getNombre());
/* 175:205 */     reportParameters.put("p_componente_equipo", this.componenteEquipo == null ? "TODOS" : this.componenteEquipo.getNombre());
/* 176:206 */     reportParameters.put("p_actividad", this.actividad == null ? "TODOS" : this.actividad.getNombre());
/* 177:207 */     reportParameters.put("p_numero_otm", this.ordenTrabajoMantenimiento == null ? "TODOS" : this.ordenTrabajoMantenimiento.getNumero());
/* 178:208 */     reportParameters.put("ReportTitle", "Costo Historico Mantenimiento");
/* 179:209 */     if (this.agrupar.equals(Agrupar.EQUIPO)) {
/* 180:210 */       reportParameters.put("p_agrupar", "f_equipo");
/* 181:211 */     } else if (this.agrupar.equals(Agrupar.ORDEN_TRABAJO_MANTENIMIENTO)) {
/* 182:212 */       reportParameters.put("p_agrupar", "f_numero_otm");
/* 183:    */     }
/* 184:214 */     return reportParameters;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String execute()
/* 188:    */   {
/* 189:    */     try
/* 190:    */     {
/* 191:220 */       super.prepareReport();
/* 192:    */     }
/* 193:    */     catch (JRException e)
/* 194:    */     {
/* 195:222 */       LOG.info("Error JRException");
/* 196:223 */       e.printStackTrace();
/* 197:224 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 198:    */     }
/* 199:    */     catch (IOException e)
/* 200:    */     {
/* 201:226 */       LOG.info("Error IOException");
/* 202:227 */       e.printStackTrace();
/* 203:228 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 204:    */     }
/* 205:230 */     return "";
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Date getFechaDesde()
/* 209:    */   {
/* 210:234 */     return this.fechaDesde;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setFechaDesde(Date fechaDesde)
/* 214:    */   {
/* 215:238 */     this.fechaDesde = fechaDesde;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Date getFechaHasta()
/* 219:    */   {
/* 220:242 */     return this.fechaHasta;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setFechaHasta(Date fechaHasta)
/* 224:    */   {
/* 225:246 */     this.fechaHasta = fechaHasta;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public CategoriaEquipo getCategoriaEquipo()
/* 229:    */   {
/* 230:250 */     return this.categoriaEquipo;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setCategoriaEquipo(CategoriaEquipo categoriaEquipo)
/* 234:    */   {
/* 235:254 */     this.categoriaEquipo = categoriaEquipo;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public SubcategoriaEquipo getSubcategoriaEquipo()
/* 239:    */   {
/* 240:258 */     return this.subcategoriaEquipo;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setSubcategoriaEquipo(SubcategoriaEquipo subcategoriaEquipo)
/* 244:    */   {
/* 245:262 */     this.subcategoriaEquipo = subcategoriaEquipo;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public Equipo getEquipo()
/* 249:    */   {
/* 250:266 */     return this.equipo;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setEquipo(Equipo equipo)
/* 254:    */   {
/* 255:270 */     this.equipo = equipo;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public ComponenteEquipo getComponenteEquipo()
/* 259:    */   {
/* 260:274 */     return this.componenteEquipo;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setComponenteEquipo(ComponenteEquipo componenteEquipo)
/* 264:    */   {
/* 265:278 */     this.componenteEquipo = componenteEquipo;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public ActividadMantenimiento getActividad()
/* 269:    */   {
/* 270:282 */     return this.actividad;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setActividad(ActividadMantenimiento actividad)
/* 274:    */   {
/* 275:286 */     this.actividad = actividad;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public OrdenTrabajoMantenimiento getOrdenTrabajoMantenimiento()
/* 279:    */   {
/* 280:290 */     return this.ordenTrabajoMantenimiento;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setOrdenTrabajoMantenimiento(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 284:    */   {
/* 285:294 */     this.ordenTrabajoMantenimiento = ordenTrabajoMantenimiento;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public List<CategoriaEquipo> getListaCategoriaEquipo()
/* 289:    */   {
/* 290:298 */     if (this.listaCategoriaEquipo == null)
/* 291:    */     {
/* 292:299 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 293:300 */       filtros.put("activo", "true");
/* 294:301 */       this.listaCategoriaEquipo = this.servicioCategoriaEquipo.obtenerListaCombo(CategoriaEquipo.class, "nombre", true, filtros);
/* 295:    */     }
/* 296:303 */     return this.listaCategoriaEquipo;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<SubcategoriaEquipo> getListaSubcategoriaEquipo()
/* 300:    */   {
/* 301:307 */     if ((this.listaSubcategoriaEquipo == null) && (this.categoriaEquipo != null))
/* 302:    */     {
/* 303:308 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 304:309 */       filtros.put("activo", "true");
/* 305:310 */       filtros.put("categoriaEquipo.idCategoriaEquipo", this.categoriaEquipo.getId() + "");
/* 306:311 */       this.listaSubcategoriaEquipo = this.servicioSubcategoriaEquipo.obtenerListaCombo(SubcategoriaEquipo.class, "nombre", true, filtros);
/* 307:    */     }
/* 308:313 */     return this.listaSubcategoriaEquipo;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public List<Equipo> autocompletarEquipo(String consulta)
/* 312:    */   {
/* 313:317 */     Map<String, String> filtro = agregarFiltroOrganizacion(null);
/* 314:318 */     filtro.put("activo", "true");
/* 315:319 */     filtro.put("OR~codigo", "%" + consulta + "%");
/* 316:320 */     filtro.put("OR~numeroSerie", "%" + consulta + "%");
/* 317:321 */     filtro.put("OR~codigoBarras", "%" + consulta + "%");
/* 318:322 */     filtro.put("OR~nombre", "%" + consulta + "%");
/* 319:323 */     if (this.subcategoriaEquipo != null) {
/* 320:324 */       filtro.put("subcategoriaEquipo.idSubcategoriaEquipo", this.subcategoriaEquipo.getId() + "");
/* 321:325 */     } else if (this.categoriaEquipo != null) {
/* 322:326 */       filtro.put("subcategoriaEquipo.categoriaEquipo.idCategoriaEquipo", this.categoriaEquipo.getId() + "");
/* 323:    */     }
/* 324:328 */     return this.servicioEquipo.obtenerListaPorPagina(0, 50, "nombre", true, filtro);
/* 325:    */   }
/* 326:    */   
/* 327:    */   public List<ComponenteEquipo> getListaComponenteEquipo()
/* 328:    */   {
/* 329:    */     Set<Integer> listaIdComponenteEquipo;
/* 330:332 */     if (this.listaComponenteEquipo == null)
/* 331:    */     {
/* 332:333 */       this.listaComponenteEquipo = new ArrayList();
/* 333:334 */       listaIdComponenteEquipo = new HashSet();
/* 334:    */       
/* 335:336 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 336:337 */       filtros.put("componenteEquipo.activo", "true");
/* 337:338 */       if (this.equipo != null) {
/* 338:339 */         filtros.put("equipo.idEquipo", this.equipo.getId() + "");
/* 339:    */       }
/* 340:342 */       List<String> listaCampos = new ArrayList();
/* 341:343 */       listaCampos.add("equipo");
/* 342:344 */       listaCampos.add("componenteEquipo");
/* 343:345 */       List<DetalleComponenteEquipo> lista = this.servicioDetalleComponenteEquipo.obtenerListaPorPagina(DetalleComponenteEquipo.class, 0, 1000, "componenteEquipo.nombre", true, filtros, listaCampos);
/* 344:347 */       for (DetalleComponenteEquipo detalleComponenteEquipo : lista) {
/* 345:348 */         if (!listaIdComponenteEquipo.contains(Integer.valueOf(detalleComponenteEquipo.getComponenteEquipo().getId())))
/* 346:    */         {
/* 347:349 */           this.listaComponenteEquipo.add(detalleComponenteEquipo.getComponenteEquipo());
/* 348:350 */           listaIdComponenteEquipo.add(Integer.valueOf(detalleComponenteEquipo.getComponenteEquipo().getId()));
/* 349:    */         }
/* 350:    */       }
/* 351:    */     }
/* 352:354 */     return this.listaComponenteEquipo;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public List<ActividadMantenimiento> getListaActividad()
/* 356:    */   {
/* 357:358 */     if (this.listaActividad == null)
/* 358:    */     {
/* 359:359 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 360:360 */       filtros.put("activo", "true");
/* 361:361 */       this.listaActividad = this.servicioActividadMantenimiento.obtenerListaCombo("nombre", true, filtros);
/* 362:    */     }
/* 363:363 */     return this.listaActividad;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public List<OrdenTrabajoMantenimiento> autocompletarOrdenTrabajo(String consulta)
/* 367:    */   {
/* 368:367 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 369:368 */     filtros.put("estado", Estado.CERRADO.toString());
/* 370:369 */     filtros.put("numero", "%" + consulta + "%");
/* 371:370 */     return this.servicioOrdenTrabajoMantenimiento.obtenerListaCombo("numero", true, filtros);
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void actualizarTipoActividad()
/* 375:    */   {
/* 376:374 */     this.actividad = null;
/* 377:375 */     this.listaActividad = null;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void actualizarCategoriaEquipo()
/* 381:    */   {
/* 382:379 */     this.subcategoriaEquipo = null;
/* 383:380 */     this.listaSubcategoriaEquipo = null;
/* 384:381 */     actualizarSubcategoriaEquipo();
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void actualizarSubcategoriaEquipo()
/* 388:    */   {
/* 389:385 */     this.equipo = null;
/* 390:386 */     actualizarEquipo();
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void actualizarEquipo()
/* 394:    */   {
/* 395:390 */     this.componenteEquipo = null;
/* 396:391 */     this.listaComponenteEquipo = null;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public TipoReporte getTipoReporte()
/* 400:    */   {
/* 401:396 */     return this.tipoReporte;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 405:    */   {
/* 406:400 */     this.tipoReporte = tipoReporte;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public List<SelectItem> getListaTipoReporte()
/* 410:    */   {
/* 411:404 */     this.listaTipoReporte = new ArrayList();
/* 412:405 */     for (TipoReporte tipoReporte : TipoReporte.values())
/* 413:    */     {
/* 414:406 */       SelectItem item = new SelectItem(tipoReporte, tipoReporte.getNombre());
/* 415:407 */       this.listaTipoReporte.add(item);
/* 416:    */     }
/* 417:410 */     return this.listaTipoReporte;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public Agrupar getAgrupar()
/* 421:    */   {
/* 422:414 */     return this.agrupar;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void setAgrupar(Agrupar agrupar)
/* 426:    */   {
/* 427:418 */     this.agrupar = agrupar;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public List<SelectItem> getListaAgrupar()
/* 431:    */   {
/* 432:422 */     this.listaAgrupar = new ArrayList();
/* 433:423 */     for (Agrupar agruparCosto : Agrupar.values())
/* 434:    */     {
/* 435:424 */       SelectItem item = new SelectItem(agruparCosto, agruparCosto.getNombre());
/* 436:425 */       this.listaAgrupar.add(item);
/* 437:    */     }
/* 438:427 */     return this.listaAgrupar;
/* 439:    */   }
/* 440:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.reportes.ReporteMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */