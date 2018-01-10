/*   1:    */ package com.asinfo.as2.mantenimiento.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.UbicacionActivo;
/*  13:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*  14:    */ import com.asinfo.as2.entities.mantenimiento.CalendarioMantenimiento;
/*  15:    */ import com.asinfo.as2.entities.mantenimiento.CalendarioMantenimientoEntidad;
/*  16:    */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
/*  17:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*  18:    */ import com.asinfo.as2.entities.mantenimiento.DetalleComponenteEquipo;
/*  19:    */ import com.asinfo.as2.entities.mantenimiento.DetalleOrdenTrabajoMantenimiento;
/*  20:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimiento;
/*  21:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  22:    */ import com.asinfo.as2.entities.mantenimiento.Herramienta;
/*  23:    */ import com.asinfo.as2.entities.mantenimiento.HerramientaOrdenTrabajoMantenimiento;
/*  24:    */ import com.asinfo.as2.entities.mantenimiento.MaterialOrdenTrabajoMantenimiento;
/*  25:    */ import com.asinfo.as2.entities.mantenimiento.OrdenTrabajoMantenimiento;
/*  26:    */ import com.asinfo.as2.entities.mantenimiento.ResponsableOrdenTrabajoMantenimiento;
/*  27:    */ import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
/*  28:    */ import com.asinfo.as2.entities.mantenimiento.TipoActividad;
/*  29:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  30:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  31:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  32:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*  33:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  34:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioUbicacionActivo;
/*  35:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  36:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  37:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioActividadMantenimiento;
/*  38:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioCalendarioMantenimiento;
/*  39:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioEquipo;
/*  40:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioPlanMantenimiento;
/*  41:    */ import com.asinfo.as2.mantenimiento.procesos.servicio.ServicioOrdenTrabajoMantenimiento;
/*  42:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  43:    */ import com.asinfo.as2.util.AppUtil;
/*  44:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  45:    */ import com.asinfo.as2.utils.JsfUtil;
/*  46:    */ import java.util.ArrayList;
/*  47:    */ import java.util.Calendar;
/*  48:    */ import java.util.Date;
/*  49:    */ import java.util.HashSet;
/*  50:    */ import java.util.List;
/*  51:    */ import java.util.Map;
/*  52:    */ import java.util.Set;
/*  53:    */ import javax.annotation.PostConstruct;
/*  54:    */ import javax.ejb.EJB;
/*  55:    */ import javax.faces.bean.ManagedBean;
/*  56:    */ import javax.faces.bean.ManagedProperty;
/*  57:    */ import javax.faces.bean.ViewScoped;
/*  58:    */ import javax.faces.component.html.HtmlInputText;
/*  59:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  60:    */ import org.primefaces.component.datatable.DataTable;
/*  61:    */ import org.primefaces.context.RequestContext;
/*  62:    */ 
/*  63:    */ @ManagedBean
/*  64:    */ @ViewScoped
/*  65:    */ public class CalendarioMantenimientoBean
/*  66:    */   extends PageControllerAS2
/*  67:    */ {
/*  68:    */   private static final long serialVersionUID = 1L;
/*  69:    */   @EJB
/*  70:    */   private ServicioOrdenTrabajoMantenimiento servicioOrdenTrabajoMantenimiento;
/*  71:    */   @EJB
/*  72:    */   private ServicioEquipo servicioEquipo;
/*  73:    */   @EJB
/*  74:    */   private ServicioPersonaResponsable servicioPersonaResponsable;
/*  75:    */   @EJB
/*  76:    */   private ServicioGenerico<ComponenteEquipo> servicioComponenteEquipo;
/*  77:    */   @EJB
/*  78:    */   private ServicioGenerico<DetalleComponenteEquipo> servicioDetalleComponenteEquipo;
/*  79:    */   @EJB
/*  80:    */   private ServicioActividadMantenimiento servicioActividadMantenimiento;
/*  81:    */   @EJB
/*  82:    */   private ServicioDocumento servicioDocumento;
/*  83:    */   @EJB
/*  84:    */   private ServicioGenerico<Herramienta> servicioHerramienta;
/*  85:    */   @EJB
/*  86:    */   private ServicioProducto servicioProducto;
/*  87:    */   @EJB
/*  88:    */   private ServicioGenerico<CategoriaEquipo> servicioCategoriaEquipo;
/*  89:    */   @EJB
/*  90:    */   private ServicioGenerico<SubcategoriaEquipo> servicioSubcategoriaEquipo;
/*  91:    */   @EJB
/*  92:    */   private ServicioGenerico<TipoActividad> servicioTipoActividad;
/*  93:    */   @EJB
/*  94:    */   private ServicioUbicacionActivo servicioUbicacionActivo;
/*  95:    */   @EJB
/*  96:    */   private ServicioPlanMantenimiento servicioPlanMantenimiento;
/*  97:    */   @EJB
/*  98:    */   private ServicioCalendarioMantenimiento servicioCalendarioMantenimiento;
/*  99:    */   private List<CategoriaEquipo> listaCategoriaEquipo;
/* 100:    */   private List<SubcategoriaEquipo> listaSubcategoriaEquipo;
/* 101:    */   private List<ComponenteEquipo> listaComponenteEquipo;
/* 102:    */   private List<TipoActividad> listaTipoActividad;
/* 103:    */   private List<ActividadMantenimiento> listaActividad;
/* 104:    */   private List<UbicacionActivo> listaUbicacion;
/* 105:    */   List<CalendarioMantenimiento> listaCalendario;
/* 106:    */   private CategoriaEquipo categoriaEquipo;
/* 107:    */   private SubcategoriaEquipo subcategoriaEquipo;
/* 108:    */   private Equipo equipo;
/* 109:    */   private ComponenteEquipo componenteEquipo;
/* 110:    */   private TipoActividad tipoActividad;
/* 111:    */   private ActividadMantenimiento actividad;
/* 112:    */   private UbicacionActivo ubicacion;
/* 113:    */   private boolean indicadorSoloConParo;
/* 114:    */   private CalendarioMantenimientoEntidad calendarioInformacion;
/* 115:127 */   private boolean[] indicadorMarcadosTodos = new boolean[16];
/* 116:    */   private OrdenTrabajoMantenimiento ordenTrabajoMantenimiento;
/* 117:    */   private List<PersonaResponsable> listaResponsableCombo;
/* 118:    */   private List<Herramienta> listaHerramientaCombo;
/* 119:    */   private DataTable dtCalendario;
/* 120:    */   private DataTable dtDetalle;
/* 121:    */   private DataTable dtResponsable;
/* 122:    */   private DataTable dtHerramienta;
/* 123:    */   private DataTable dtMaterial;
/* 124:    */   private int mes;
/* 125:    */   private int anio;
/* 126:    */   private boolean primeraQuincena;
/* 127:    */   private Mes mesEnum;
/* 128:    */   private Calendar fechaInicial;
/* 129:    */   private Calendar fechaFinal;
/* 130:    */   private int cantidadDias;
/* 131:    */   private String mails;
/* 132:    */   @ManagedProperty("#{listaProductoBean}")
/* 133:    */   private ListaProductoBean listaProductoBean;
/* 134:    */   
/* 135:    */   @PostConstruct
/* 136:    */   public void init()
/* 137:    */   {
/* 138:154 */     Calendar hoy = Calendar.getInstance();
/* 139:155 */     this.anio = hoy.get(1);
/* 140:156 */     this.mes = hoy.get(2);
/* 141:157 */     int diaMes = hoy.get(5);
/* 142:158 */     this.primeraQuincena = (diaMes <= 15);
/* 143:159 */     actualizarFechas();
/* 144:160 */     getListaProductoBean().setIndicadorMantenimiento(true);
/* 145:161 */     filtrar();
/* 146:    */   }
/* 147:    */   
/* 148:    */   private void actualizarFechas()
/* 149:    */   {
/* 150:166 */     this.fechaInicial = Calendar.getInstance();
/* 151:167 */     this.fechaInicial.clear();
/* 152:168 */     this.fechaInicial.set(this.anio, this.mes, this.primeraQuincena ? 1 : 16);
/* 153:169 */     this.cantidadDias = 15;
/* 154:    */     
/* 155:171 */     this.fechaFinal = Calendar.getInstance();
/* 156:172 */     if (!this.primeraQuincena)
/* 157:    */     {
/* 158:173 */       this.fechaFinal.setTime(FuncionesUtiles.getFechaFinMes(this.anio, this.mes + 1));
/* 159:174 */       this.cantidadDias = (this.fechaFinal.get(5) - 15);
/* 160:    */     }
/* 161:    */     else
/* 162:    */     {
/* 163:176 */       this.fechaFinal = ((Calendar)this.fechaInicial.clone());
/* 164:177 */       this.fechaFinal.set(5, 15);
/* 165:    */     }
/* 166:180 */     this.indicadorMarcadosTodos = new boolean[this.cantidadDias];
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String editar()
/* 170:    */   {
/* 171:190 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 172:191 */     return "";
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String guardar()
/* 176:    */   {
/* 177:201 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 178:202 */     return "";
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String limpiar()
/* 182:    */   {
/* 183:212 */     return "";
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String eliminar()
/* 187:    */   {
/* 188:222 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 189:223 */     return "";
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String cargarDatos()
/* 193:    */   {
/* 194:233 */     return "";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void enviarMail()
/* 198:    */   {
/* 199:    */     try
/* 200:    */     {
/* 201:238 */       Calendar fechaInicio = Calendar.getInstance();
/* 202:239 */       fechaInicio.setTime(this.fechaFinal.getTime());
/* 203:240 */       fechaInicio.set(5, 1);
/* 204:241 */       Date fechaDesde = fechaInicio.getTime();
/* 205:242 */       Date fechaHasta = FuncionesUtiles.getFechaFinMes(fechaDesde);
/* 206:243 */       this.servicioCalendarioMantenimiento.enviarEmail(AppUtil.getOrganizacion().getId(), fechaDesde, fechaHasta, this.categoriaEquipo, this.subcategoriaEquipo, this.equipo, this.componenteEquipo, this.tipoActividad, this.actividad, this.ubicacion, this.indicadorSoloConParo, false, this.mails);
/* 207:    */       
/* 208:245 */       addInfoMessage(getLanguageController().getMensaje("msg_info_emails_enviados"));
/* 209:    */     }
/* 210:    */     catch (Exception e)
/* 211:    */     {
/* 212:247 */       addInfoMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 213:248 */       e.printStackTrace();
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   public List<CategoriaEquipo> getListaCategoriaEquipo()
/* 218:    */   {
/* 219:253 */     if (this.listaCategoriaEquipo == null)
/* 220:    */     {
/* 221:254 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 222:255 */       filtros.put("activo", "true");
/* 223:256 */       this.listaCategoriaEquipo = this.servicioCategoriaEquipo.obtenerListaCombo(CategoriaEquipo.class, "nombre", true, filtros);
/* 224:    */     }
/* 225:258 */     return this.listaCategoriaEquipo;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<SubcategoriaEquipo> getListaSubcategoriaEquipo()
/* 229:    */   {
/* 230:262 */     if ((this.listaSubcategoriaEquipo == null) && (this.categoriaEquipo != null))
/* 231:    */     {
/* 232:263 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 233:264 */       filtros.put("activo", "true");
/* 234:265 */       filtros.put("categoriaEquipo.idCategoriaEquipo", this.categoriaEquipo.getId() + "");
/* 235:266 */       this.listaSubcategoriaEquipo = this.servicioSubcategoriaEquipo.obtenerListaCombo(SubcategoriaEquipo.class, "nombre", true, filtros);
/* 236:    */     }
/* 237:268 */     return this.listaSubcategoriaEquipo;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public List<ComponenteEquipo> getListaComponenteEquipo()
/* 241:    */   {
/* 242:    */     Set<Integer> listaIdComponenteEquipo;
/* 243:272 */     if (this.listaComponenteEquipo == null)
/* 244:    */     {
/* 245:273 */       this.listaComponenteEquipo = new ArrayList();
/* 246:274 */       listaIdComponenteEquipo = new HashSet();
/* 247:    */       
/* 248:276 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 249:277 */       filtros.put("componenteEquipo.activo", "true");
/* 250:278 */       if (this.equipo != null) {
/* 251:279 */         filtros.put("equipo.idEquipo", this.equipo.getId() + "");
/* 252:    */       }
/* 253:282 */       List<String> listaCampos = new ArrayList();
/* 254:283 */       listaCampos.add("equipo");
/* 255:284 */       listaCampos.add("componenteEquipo");
/* 256:285 */       List<DetalleComponenteEquipo> lista = this.servicioDetalleComponenteEquipo.obtenerListaPorPagina(DetalleComponenteEquipo.class, 0, 1000, "componenteEquipo.nombre", true, filtros, listaCampos);
/* 257:287 */       for (DetalleComponenteEquipo detalleComponenteEquipo : lista) {
/* 258:288 */         if (!listaIdComponenteEquipo.contains(Integer.valueOf(detalleComponenteEquipo.getComponenteEquipo().getId())))
/* 259:    */         {
/* 260:289 */           this.listaComponenteEquipo.add(detalleComponenteEquipo.getComponenteEquipo());
/* 261:290 */           listaIdComponenteEquipo.add(Integer.valueOf(detalleComponenteEquipo.getComponenteEquipo().getId()));
/* 262:    */         }
/* 263:    */       }
/* 264:    */     }
/* 265:294 */     return this.listaComponenteEquipo;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public List<TipoActividad> getListaTipoActividad()
/* 269:    */   {
/* 270:298 */     if (this.listaTipoActividad == null)
/* 271:    */     {
/* 272:299 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 273:300 */       filtros.put("activo", "true");
/* 274:301 */       this.listaTipoActividad = this.servicioTipoActividad.obtenerListaCombo(TipoActividad.class, "nombre", true, filtros);
/* 275:    */     }
/* 276:303 */     return this.listaTipoActividad;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public List<ActividadMantenimiento> getListaActividad()
/* 280:    */   {
/* 281:307 */     if (this.listaActividad == null)
/* 282:    */     {
/* 283:308 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 284:309 */       filtros.put("activo", "true");
/* 285:310 */       if (this.tipoActividad != null) {
/* 286:311 */         filtros.put("tipoActividad.idTipoActividad", this.tipoActividad.getId() + "");
/* 287:    */       }
/* 288:313 */       this.listaActividad = this.servicioActividadMantenimiento.obtenerListaCombo("nombre", true, filtros);
/* 289:    */     }
/* 290:315 */     return this.listaActividad;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public List<UbicacionActivo> getListaUbicacion()
/* 294:    */   {
/* 295:319 */     if (this.listaUbicacion == null)
/* 296:    */     {
/* 297:320 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 298:321 */       filtros.put("activo", "true");
/* 299:322 */       this.listaUbicacion = this.servicioUbicacionActivo.obtenerListaCombo("nombre", true, filtros);
/* 300:    */     }
/* 301:324 */     return this.listaUbicacion;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void actualizarTipoActividad()
/* 305:    */   {
/* 306:328 */     this.actividad = null;
/* 307:329 */     this.listaActividad = null;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void actualizarCategoriaEquipo()
/* 311:    */   {
/* 312:333 */     this.subcategoriaEquipo = null;
/* 313:334 */     this.listaSubcategoriaEquipo = null;
/* 314:335 */     actualizarSubcategoriaEquipo();
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void actualizarSubcategoriaEquipo()
/* 318:    */   {
/* 319:339 */     this.equipo = null;
/* 320:340 */     actualizarEquipo();
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void actualizarEquipo()
/* 324:    */   {
/* 325:344 */     this.componenteEquipo = null;
/* 326:345 */     this.listaComponenteEquipo = null;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public List<Equipo> autocompletarEquipo(String consulta)
/* 330:    */   {
/* 331:349 */     Map<String, String> filtro = agregarFiltroOrganizacion(null);
/* 332:350 */     filtro.put("activo", "true");
/* 333:351 */     filtro.put("OR~codigo", "%" + consulta + "%");
/* 334:352 */     filtro.put("OR~numeroSerie", "%" + consulta + "%");
/* 335:353 */     filtro.put("OR~codigoBarras", "%" + consulta + "%");
/* 336:354 */     filtro.put("OR~nombre", "%" + consulta + "%");
/* 337:355 */     if (this.subcategoriaEquipo != null) {
/* 338:356 */       filtro.put("subcategoriaEquipo.idSubcategoriaEquipo", this.subcategoriaEquipo.getId() + "");
/* 339:357 */     } else if (this.categoriaEquipo != null) {
/* 340:358 */       filtro.put("subcategoriaEquipo.categoriaEquipo.idCategoriaEquipo", this.categoriaEquipo.getId() + "");
/* 341:    */     }
/* 342:360 */     return this.servicioEquipo.obtenerListaPorPagina(0, 50, "nombre", true, filtro);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public CategoriaEquipo getCategoriaEquipo()
/* 346:    */   {
/* 347:364 */     return this.categoriaEquipo;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setCategoriaEquipo(CategoriaEquipo categoriaEquipo)
/* 351:    */   {
/* 352:368 */     this.categoriaEquipo = categoriaEquipo;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public SubcategoriaEquipo getSubcategoriaEquipo()
/* 356:    */   {
/* 357:372 */     return this.subcategoriaEquipo;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setSubcategoriaEquipo(SubcategoriaEquipo subcategoriaEquipo)
/* 361:    */   {
/* 362:376 */     this.subcategoriaEquipo = subcategoriaEquipo;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public DataTable getDtCalendario()
/* 366:    */   {
/* 367:380 */     return this.dtCalendario;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setDtCalendario(DataTable dtCalendario)
/* 371:    */   {
/* 372:384 */     this.dtCalendario = dtCalendario;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public ListaProductoBean getListaProductoBean()
/* 376:    */   {
/* 377:388 */     return this.listaProductoBean;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 381:    */   {
/* 382:392 */     this.listaProductoBean = listaProductoBean;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public Equipo getEquipo()
/* 386:    */   {
/* 387:396 */     return this.equipo;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setEquipo(Equipo equipo)
/* 391:    */   {
/* 392:400 */     this.equipo = equipo;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public ComponenteEquipo getComponenteEquipo()
/* 396:    */   {
/* 397:404 */     return this.componenteEquipo;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setComponenteEquipo(ComponenteEquipo componenteEquipo)
/* 401:    */   {
/* 402:408 */     this.componenteEquipo = componenteEquipo;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public TipoActividad getTipoActividad()
/* 406:    */   {
/* 407:412 */     return this.tipoActividad;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setTipoActividad(TipoActividad tipoActividad)
/* 411:    */   {
/* 412:416 */     this.tipoActividad = tipoActividad;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public ActividadMantenimiento getActividad()
/* 416:    */   {
/* 417:420 */     return this.actividad;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void setActividad(ActividadMantenimiento actividad)
/* 421:    */   {
/* 422:424 */     this.actividad = actividad;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public UbicacionActivo getUbicacion()
/* 426:    */   {
/* 427:428 */     return this.ubicacion;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setUbicacion(UbicacionActivo ubicacion)
/* 431:    */   {
/* 432:432 */     this.ubicacion = ubicacion;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public boolean isIndicadorSoloConParo()
/* 436:    */   {
/* 437:436 */     return this.indicadorSoloConParo;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setIndicadorSoloConParo(boolean indicadorSoloConParo)
/* 441:    */   {
/* 442:440 */     this.indicadorSoloConParo = indicadorSoloConParo;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void filtrar()
/* 446:    */   {
/* 447:444 */     this.listaCalendario = this.servicioCalendarioMantenimiento.obtenerListaCalendarioPorFecha(this.fechaInicial.getTime(), this.fechaFinal.getTime(), 
/* 448:445 */       AppUtil.getOrganizacion().getId(), this.categoriaEquipo, this.subcategoriaEquipo, this.equipo, this.componenteEquipo, this.tipoActividad, this.actividad, this.ubicacion, this.indicadorSoloConParo);
/* 449:    */   }
/* 450:    */   
/* 451:    */   public List<CalendarioMantenimiento> getListaCalendario()
/* 452:    */   {
/* 453:450 */     return this.listaCalendario;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setListaCalendario(List<CalendarioMantenimiento> listaCalendario)
/* 457:    */   {
/* 458:454 */     this.listaCalendario = listaCalendario;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public int getMes()
/* 462:    */   {
/* 463:458 */     return this.mes;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setMes(int mes)
/* 467:    */   {
/* 468:462 */     this.mes = mes;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public int getAnio()
/* 472:    */   {
/* 473:466 */     return this.anio;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void setAnio(int anio)
/* 477:    */   {
/* 478:470 */     this.anio = anio;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public boolean isPrimeraQuincena()
/* 482:    */   {
/* 483:474 */     return this.primeraQuincena;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void setPrimeraQuincena(boolean primeraQuincena)
/* 487:    */   {
/* 488:478 */     this.primeraQuincena = primeraQuincena;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public Mes getMesEnum()
/* 492:    */   {
/* 493:482 */     this.mesEnum = Mes.values()[this.mes];
/* 494:483 */     return this.mesEnum;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public void setMesEnum(Mes mesEnum)
/* 498:    */   {
/* 499:487 */     this.mesEnum = mesEnum;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public List<Columna> getListaColumnas()
/* 503:    */   {
/* 504:491 */     List<Columna> lista = new ArrayList();
/* 505:    */     
/* 506:493 */     int primerDia = this.fechaInicial.get(5);
/* 507:494 */     for (int i = primerDia; i < primerDia + this.cantidadDias; i++)
/* 508:    */     {
/* 509:495 */       Columna columna = new Columna();
/* 510:496 */       columna.setValorMostrar(i);
/* 511:497 */       columna.setPosicionArreglo(i - primerDia);
/* 512:498 */       lista.add(columna);
/* 513:    */     }
/* 514:501 */     for (int i = 0; i < 16 - this.cantidadDias; i++) {
/* 515:502 */       lista.add(null);
/* 516:    */     }
/* 517:504 */     return lista;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public void quincenaAnterior()
/* 521:    */   {
/* 522:508 */     if (this.primeraQuincena)
/* 523:    */     {
/* 524:509 */       this.primeraQuincena = false;
/* 525:510 */       if (this.mes != 0)
/* 526:    */       {
/* 527:511 */         this.mes -= 1;
/* 528:    */       }
/* 529:    */       else
/* 530:    */       {
/* 531:513 */         this.mes = 11;
/* 532:514 */         this.anio -= 1;
/* 533:    */       }
/* 534:    */     }
/* 535:    */     else
/* 536:    */     {
/* 537:517 */       this.primeraQuincena = true;
/* 538:    */     }
/* 539:519 */     actualizarFechas();
/* 540:520 */     filtrar();
/* 541:    */   }
/* 542:    */   
/* 543:    */   public void quincenaSiguiente()
/* 544:    */   {
/* 545:524 */     if (this.primeraQuincena)
/* 546:    */     {
/* 547:525 */       this.primeraQuincena = false;
/* 548:    */     }
/* 549:    */     else
/* 550:    */     {
/* 551:527 */       this.primeraQuincena = true;
/* 552:528 */       if (this.mes != 11)
/* 553:    */       {
/* 554:529 */         this.mes += 1;
/* 555:    */       }
/* 556:    */       else
/* 557:    */       {
/* 558:531 */         this.mes = 0;
/* 559:532 */         this.anio += 1;
/* 560:    */       }
/* 561:    */     }
/* 562:535 */     actualizarFechas();
/* 563:536 */     filtrar();
/* 564:    */   }
/* 565:    */   
/* 566:    */   public void generarOrdenTrabajo()
/* 567:    */   {
/* 568:540 */     this.ordenTrabajoMantenimiento = new OrdenTrabajoMantenimiento();
/* 569:541 */     this.ordenTrabajoMantenimiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 570:542 */     this.ordenTrabajoMantenimiento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 571:543 */     this.ordenTrabajoMantenimiento.setEstado(Estado.ELABORADO);
/* 572:544 */     this.ordenTrabajoMantenimiento.setIndicadorPlanificada(true);
/* 573:    */     try
/* 574:    */     {
/* 575:547 */       List<Documento> listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.ORDEN_TRABAJO_MANTENIMIENTO, 
/* 576:548 */         AppUtil.getOrganizacion().getIdOrganizacion());
/* 577:549 */       if (listaDocumento.size() > 0) {
/* 578:550 */         this.ordenTrabajoMantenimiento.setDocumento((Documento)listaDocumento.get(0));
/* 579:    */       }
/* 580:    */     }
/* 581:    */     catch (ExcepcionAS2 e)
/* 582:    */     {
/* 583:553 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 584:    */     }
/* 585:557 */     for (CalendarioMantenimiento calendario : getListaCalendario()) {
/* 586:558 */       for (int i = 0; i < calendario.getArregloIndicadorGenerarOT().length; i++) {
/* 587:559 */         if ((Boolean.valueOf(true).equals(calendario.getArregloIndicadorGenerarOT()[i])) && 
/* 588:560 */           (calendario.getArregloCalendarioMantenimientoEntidad()[i] != null) && 
/* 589:561 */           (calendario.getArregloCalendarioMantenimientoEntidad()[i].getDetalleOrdenTrabajoMantenimiento() == null))
/* 590:    */         {
/* 591:562 */           DetalleOrdenTrabajoMantenimiento detalle = new DetalleOrdenTrabajoMantenimiento();
/* 592:563 */           detalle.setIdOrganizacion(this.ordenTrabajoMantenimiento.getIdOrganizacion());
/* 593:564 */           detalle.setIdSucursal(this.ordenTrabajoMantenimiento.getIdSucursal());
/* 594:565 */           detalle.setOrdenTrabajoMantenimiento(this.ordenTrabajoMantenimiento);
/* 595:566 */           detalle.setEquipo(calendario.getEquipo());
/* 596:567 */           detalle.setComponenteEquipo(calendario.getDetallePlanMantenimiento().getComponente());
/* 597:568 */           detalle.setActividadMantenimiento(calendario.getDetallePlanMantenimiento().getActividad());
/* 598:569 */           detalle.setHorasParo(calendario.getDetallePlanMantenimiento().getHorasParo());
/* 599:570 */           detalle.setFechaPlanificadaOriginal(calendario.getArregloCalendarioMantenimientoEntidad()[i].getFecha());
/* 600:571 */           detalle.setPlanMantenimiento(calendario
/* 601:572 */             .getArregloCalendarioMantenimientoEntidad()[i].getDetallePlanMantenimiento().getPlanMantenimiento());
/* 602:573 */           detalle.setCalendarioMantenimientoEntidad(calendario.getArregloCalendarioMantenimientoEntidad()[i]);
/* 603:574 */           this.ordenTrabajoMantenimiento.getListaDetalleOrdenTrabajoMantenimiento().add(detalle);
/* 604:575 */           this.ordenTrabajoMantenimiento.setFechaMantenimiento(calendario.getArregloCalendarioMantenimientoEntidad()[i].getFechaModificada());
/* 605:    */         }
/* 606:    */       }
/* 607:    */     }
/* 608:580 */     this.servicioOrdenTrabajoMantenimiento.actualizarMateriales(this.ordenTrabajoMantenimiento);
/* 609:581 */     this.servicioOrdenTrabajoMantenimiento.actualizarHerramientas(this.ordenTrabajoMantenimiento, true);
/* 610:    */   }
/* 611:    */   
/* 612:    */   public void guardarOrdenTrabajo()
/* 613:    */   {
/* 614:585 */     boolean error = true;
/* 615:    */     try
/* 616:    */     {
/* 617:587 */       this.servicioOrdenTrabajoMantenimiento.guardar(this.ordenTrabajoMantenimiento);
/* 618:588 */       RequestContext context = RequestContext.getCurrentInstance();
/* 619:589 */       context.execute("PF('generarOTDialog').hide();");
/* 620:    */       
/* 621:591 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 622:592 */       error = false;
/* 623:593 */       this.ordenTrabajoMantenimiento = null;
/* 624:    */     }
/* 625:    */     catch (AS2ExceptionMantenimiento e)
/* 626:    */     {
/* 627:595 */       JsfUtil.addErrorMessage(e, "");
/* 628:    */     }
/* 629:    */     catch (Exception e)
/* 630:    */     {
/* 631:597 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 632:598 */       e.printStackTrace();
/* 633:    */     }
/* 634:600 */     if (!error) {
/* 635:601 */       filtrar();
/* 636:    */     }
/* 637:    */   }
/* 638:    */   
/* 639:    */   public void actualizarCalendarioMantenimiento()
/* 640:    */   {
/* 641:    */     try
/* 642:    */     {
/* 643:607 */       this.servicioCalendarioMantenimiento.generarCalendarioMantenimiento(AppUtil.getOrganizacion().getId(), AppUtil.getSucursal().getId(), new Date());
/* 644:    */       
/* 645:609 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 646:610 */       filtrar();
/* 647:    */     }
/* 648:    */     catch (AS2ExceptionMantenimiento e)
/* 649:    */     {
/* 650:612 */       JsfUtil.addErrorMessage(e, "");
/* 651:    */     }
/* 652:    */   }
/* 653:    */   
/* 654:    */   public CalendarioMantenimientoEntidad getCalendarioInformacion()
/* 655:    */   {
/* 656:617 */     return this.calendarioInformacion;
/* 657:    */   }
/* 658:    */   
/* 659:    */   public void setCalendarioInformacion(CalendarioMantenimientoEntidad calendarioInformacion)
/* 660:    */   {
/* 661:621 */     this.calendarioInformacion = calendarioInformacion;
/* 662:    */   }
/* 663:    */   
/* 664:    */   public void guardarInformacionCalendario()
/* 665:    */   {
/* 666:625 */     this.servicioCalendarioMantenimiento.guardarInformacionCalendario(this.calendarioInformacion);
/* 667:626 */     this.calendarioInformacion = null;
/* 668:627 */     filtrar();
/* 669:    */   }
/* 670:    */   
/* 671:    */   public boolean[] getIndicadorMarcadosTodos()
/* 672:    */   {
/* 673:631 */     return this.indicadorMarcadosTodos;
/* 674:    */   }
/* 675:    */   
/* 676:    */   public void setIndicadorMarcadosTodos(boolean[] indicadorMarcadosTodos)
/* 677:    */   {
/* 678:635 */     this.indicadorMarcadosTodos = indicadorMarcadosTodos;
/* 679:    */   }
/* 680:    */   
/* 681:    */   public void marcarColumna(int posicion)
/* 682:    */   {
/* 683:639 */     boolean valor = this.indicadorMarcadosTodos[posicion];
/* 684:640 */     for (CalendarioMantenimiento calendario : this.listaCalendario) {
/* 685:641 */       if ((calendario.getArregloCalendarioMantenimientoEntidad()[posicion] != null) && 
/* 686:642 */         (calendario.getArregloCalendarioMantenimientoEntidad()[posicion].getDetalleOrdenTrabajoMantenimiento() == null)) {
/* 687:643 */         calendario.getArregloIndicadorGenerarOT()[posicion] = Boolean.valueOf(valor);
/* 688:    */       }
/* 689:    */     }
/* 690:    */   }
/* 691:    */   
/* 692:    */   public OrdenTrabajoMantenimiento getOrdenTrabajoMantenimiento()
/* 693:    */   {
/* 694:649 */     return this.ordenTrabajoMantenimiento;
/* 695:    */   }
/* 696:    */   
/* 697:    */   public void setOrdenTrabajoMantenimiento(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 698:    */   {
/* 699:653 */     this.ordenTrabajoMantenimiento = ordenTrabajoMantenimiento;
/* 700:    */   }
/* 701:    */   
/* 702:    */   public List<DetalleOrdenTrabajoMantenimiento> getListaDetalleOrdenTrabajo()
/* 703:    */   {
/* 704:657 */     List<DetalleOrdenTrabajoMantenimiento> lista = new ArrayList();
/* 705:658 */     if (this.ordenTrabajoMantenimiento != null) {
/* 706:659 */       for (DetalleOrdenTrabajoMantenimiento detalle : this.ordenTrabajoMantenimiento.getListaDetalleOrdenTrabajoMantenimiento()) {
/* 707:660 */         if (!detalle.isEliminado()) {
/* 708:661 */           lista.add(detalle);
/* 709:    */         }
/* 710:    */       }
/* 711:    */     }
/* 712:665 */     return lista;
/* 713:    */   }
/* 714:    */   
/* 715:    */   public List<ResponsableOrdenTrabajoMantenimiento> getListaResponsableOrdenTrabajo()
/* 716:    */   {
/* 717:669 */     List<ResponsableOrdenTrabajoMantenimiento> lista = new ArrayList();
/* 718:670 */     if (this.ordenTrabajoMantenimiento != null) {
/* 719:671 */       for (ResponsableOrdenTrabajoMantenimiento detalle : this.ordenTrabajoMantenimiento.getListaResponsableOrdenTrabajoMantenimiento()) {
/* 720:672 */         if (!detalle.isEliminado()) {
/* 721:673 */           lista.add(detalle);
/* 722:    */         }
/* 723:    */       }
/* 724:    */     }
/* 725:677 */     return lista;
/* 726:    */   }
/* 727:    */   
/* 728:    */   public List<HerramientaOrdenTrabajoMantenimiento> getListaHerramientaOrdenTrabajo()
/* 729:    */   {
/* 730:681 */     List<HerramientaOrdenTrabajoMantenimiento> lista = new ArrayList();
/* 731:682 */     if (this.ordenTrabajoMantenimiento != null) {
/* 732:683 */       for (HerramientaOrdenTrabajoMantenimiento detalle : this.ordenTrabajoMantenimiento.getListaHerramientaOrdenTrabajoMantenimiento()) {
/* 733:684 */         if (!detalle.isEliminado()) {
/* 734:685 */           lista.add(detalle);
/* 735:    */         }
/* 736:    */       }
/* 737:    */     }
/* 738:689 */     return lista;
/* 739:    */   }
/* 740:    */   
/* 741:    */   public List<MaterialOrdenTrabajoMantenimiento> getListaMaterialOrdenTrabajo()
/* 742:    */   {
/* 743:693 */     List<MaterialOrdenTrabajoMantenimiento> lista = new ArrayList();
/* 744:694 */     if (this.ordenTrabajoMantenimiento != null) {
/* 745:695 */       for (MaterialOrdenTrabajoMantenimiento detalle : this.ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento()) {
/* 746:696 */         if (!detalle.isEliminado()) {
/* 747:697 */           lista.add(detalle);
/* 748:    */         }
/* 749:    */       }
/* 750:    */     }
/* 751:701 */     return lista;
/* 752:    */   }
/* 753:    */   
/* 754:    */   public DataTable getDtDetalle()
/* 755:    */   {
/* 756:705 */     return this.dtDetalle;
/* 757:    */   }
/* 758:    */   
/* 759:    */   public void setDtDetalle(DataTable dtDetalle)
/* 760:    */   {
/* 761:709 */     this.dtDetalle = dtDetalle;
/* 762:    */   }
/* 763:    */   
/* 764:    */   public DataTable getDtResponsable()
/* 765:    */   {
/* 766:713 */     return this.dtResponsable;
/* 767:    */   }
/* 768:    */   
/* 769:    */   public void setDtResponsable(DataTable dtResponsable)
/* 770:    */   {
/* 771:717 */     this.dtResponsable = dtResponsable;
/* 772:    */   }
/* 773:    */   
/* 774:    */   public DataTable getDtHerramienta()
/* 775:    */   {
/* 776:721 */     return this.dtHerramienta;
/* 777:    */   }
/* 778:    */   
/* 779:    */   public void setDtHerramienta(DataTable dtHerramienta)
/* 780:    */   {
/* 781:725 */     this.dtHerramienta = dtHerramienta;
/* 782:    */   }
/* 783:    */   
/* 784:    */   public DataTable getDtMaterial()
/* 785:    */   {
/* 786:729 */     return this.dtMaterial;
/* 787:    */   }
/* 788:    */   
/* 789:    */   public void setDtMaterial(DataTable dtMaterial)
/* 790:    */   {
/* 791:733 */     this.dtMaterial = dtMaterial;
/* 792:    */   }
/* 793:    */   
/* 794:    */   public void agregarResponsableOrdenTrabajo()
/* 795:    */   {
/* 796:737 */     ResponsableOrdenTrabajoMantenimiento detalle = new ResponsableOrdenTrabajoMantenimiento();
/* 797:738 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 798:739 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 799:740 */     detalle.setOrdenTrabajoMantenimiento(this.ordenTrabajoMantenimiento);
/* 800:741 */     this.ordenTrabajoMantenimiento.getListaResponsableOrdenTrabajoMantenimiento().add(detalle);
/* 801:    */   }
/* 802:    */   
/* 803:    */   public void agregarHerramientaOrdenTrabajo()
/* 804:    */   {
/* 805:745 */     HerramientaOrdenTrabajoMantenimiento detalle = new HerramientaOrdenTrabajoMantenimiento();
/* 806:746 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 807:747 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 808:748 */     detalle.setOrdenTrabajoMantenimiento(this.ordenTrabajoMantenimiento);
/* 809:749 */     this.ordenTrabajoMantenimiento.getListaHerramientaOrdenTrabajoMantenimiento().add(detalle);
/* 810:    */   }
/* 811:    */   
/* 812:    */   public MaterialOrdenTrabajoMantenimiento agregarMaterialOrdenTrabajo()
/* 813:    */   {
/* 814:753 */     MaterialOrdenTrabajoMantenimiento detalle = new MaterialOrdenTrabajoMantenimiento();
/* 815:754 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 816:755 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 817:756 */     detalle.setOrdenTrabajoMantenimiento(this.ordenTrabajoMantenimiento);
/* 818:757 */     detalle.setMaterial(new Producto());
/* 819:758 */     this.ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento().add(detalle);
/* 820:759 */     return detalle;
/* 821:    */   }
/* 822:    */   
/* 823:    */   public void eliminarResponsableOrdenTrabajo()
/* 824:    */   {
/* 825:763 */     ResponsableOrdenTrabajoMantenimiento detalle = (ResponsableOrdenTrabajoMantenimiento)this.dtResponsable.getRowData();
/* 826:764 */     detalle.setEliminado(true);
/* 827:    */   }
/* 828:    */   
/* 829:    */   public void eliminarHerramientaOrdenTrabajo()
/* 830:    */   {
/* 831:768 */     HerramientaOrdenTrabajoMantenimiento detalle = (HerramientaOrdenTrabajoMantenimiento)this.dtHerramienta.getRowData();
/* 832:769 */     detalle.setEliminado(true);
/* 833:    */   }
/* 834:    */   
/* 835:    */   public void eliminarMaterialOrdenTrabajo()
/* 836:    */   {
/* 837:773 */     MaterialOrdenTrabajoMantenimiento detalle = (MaterialOrdenTrabajoMantenimiento)this.dtMaterial.getRowData();
/* 838:774 */     detalle.setEliminado(true);
/* 839:    */   }
/* 840:    */   
/* 841:    */   public List<PersonaResponsable> getListaResponsableCombo()
/* 842:    */   {
/* 843:778 */     if (this.listaResponsableCombo == null)
/* 844:    */     {
/* 845:779 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 846:780 */       filtros.put("activo", "true");
/* 847:781 */       filtros.put("indicadorMantenimiento", "true");
/* 848:782 */       this.listaResponsableCombo = this.servicioPersonaResponsable.obtenerListaCombo("nombres", true, filtros);
/* 849:    */     }
/* 850:784 */     return this.listaResponsableCombo;
/* 851:    */   }
/* 852:    */   
/* 853:    */   public List<Herramienta> getListaHerramientaCombo()
/* 854:    */   {
/* 855:788 */     if (this.listaHerramientaCombo == null)
/* 856:    */     {
/* 857:789 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 858:790 */       filtros.put("activo", "true");
/* 859:791 */       this.listaHerramientaCombo = this.servicioHerramienta.obtenerListaCombo(Herramienta.class, "nombre", true, filtros);
/* 860:    */     }
/* 861:793 */     return this.listaHerramientaCombo;
/* 862:    */   }
/* 863:    */   
/* 864:    */   public void cargarProducto(Producto producto)
/* 865:    */   {
/* 866:797 */     MaterialOrdenTrabajoMantenimiento detalle = agregarMaterialOrdenTrabajo();
/* 867:798 */     detalle.setMaterial(producto);
/* 868:799 */     detalle.setCantidadRequerida(producto.getTraCantidad());
/* 869:    */   }
/* 870:    */   
/* 871:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 872:    */   {
/* 873:803 */     MaterialOrdenTrabajoMantenimiento detalle = null;
/* 874:    */     try
/* 875:    */     {
/* 876:805 */       String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 877:806 */       detalle = (MaterialOrdenTrabajoMantenimiento)this.dtMaterial.getRowData();
/* 878:807 */       Producto producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 879:808 */       detalle.setMaterial(producto);
/* 880:    */     }
/* 881:    */     catch (ExcepcionAS2 e)
/* 882:    */     {
/* 883:810 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 884:811 */       detalle.getMaterial().setCodigo("");
/* 885:812 */       detalle.getMaterial().setNombre("");
/* 886:    */     }
/* 887:    */   }
/* 888:    */   
/* 889:    */   public Calendar getFechaInicial()
/* 890:    */   {
/* 891:817 */     return this.fechaInicial;
/* 892:    */   }
/* 893:    */   
/* 894:    */   public void setFechaInicial(Calendar fechaInicial)
/* 895:    */   {
/* 896:821 */     this.fechaInicial = fechaInicial;
/* 897:    */   }
/* 898:    */   
/* 899:    */   public class Columna
/* 900:    */   {
/* 901:    */     int valorMostrar;
/* 902:    */     int posicionArreglo;
/* 903:    */     
/* 904:    */     public Columna() {}
/* 905:    */     
/* 906:    */     public int getValorMostrar()
/* 907:    */     {
/* 908:829 */       return this.valorMostrar;
/* 909:    */     }
/* 910:    */     
/* 911:    */     public void setValorMostrar(int valorMostrar)
/* 912:    */     {
/* 913:833 */       this.valorMostrar = valorMostrar;
/* 914:    */     }
/* 915:    */     
/* 916:    */     public int getPosicionArreglo()
/* 917:    */     {
/* 918:837 */       return this.posicionArreglo;
/* 919:    */     }
/* 920:    */     
/* 921:    */     public void setPosicionArreglo(int posicionArreglo)
/* 922:    */     {
/* 923:841 */       this.posicionArreglo = posicionArreglo;
/* 924:    */     }
/* 925:    */   }
/* 926:    */   
/* 927:    */   public String getMails()
/* 928:    */   {
/* 929:846 */     return this.mails;
/* 930:    */   }
/* 931:    */   
/* 932:    */   public void setMails(String mails)
/* 933:    */   {
/* 934:850 */     this.mails = mails;
/* 935:    */   }
/* 936:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.controller.CalendarioMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */