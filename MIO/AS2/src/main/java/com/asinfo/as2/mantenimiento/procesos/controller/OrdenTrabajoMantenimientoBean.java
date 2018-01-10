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
/*  12:    */ import com.asinfo.as2.entities.mantenimiento.ActividadImagenOrdenTrabajoMantenimiento;
/*  13:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*  14:    */ import com.asinfo.as2.entities.mantenimiento.CalendarioMantenimientoEntidad;
/*  15:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*  16:    */ import com.asinfo.as2.entities.mantenimiento.DetalleOrdenTrabajoMantenimiento;
/*  17:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimiento;
/*  18:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  19:    */ import com.asinfo.as2.entities.mantenimiento.Herramienta;
/*  20:    */ import com.asinfo.as2.entities.mantenimiento.HerramientaOrdenTrabajoMantenimiento;
/*  21:    */ import com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento;
/*  22:    */ import com.asinfo.as2.entities.mantenimiento.MaterialOrdenTrabajoMantenimiento;
/*  23:    */ import com.asinfo.as2.entities.mantenimiento.OrdenTrabajoMantenimiento;
/*  24:    */ import com.asinfo.as2.entities.mantenimiento.ResponsableOrdenTrabajoMantenimiento;
/*  25:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  26:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  27:    */ import com.asinfo.as2.enumeraciones.TipoFrecuenciaEnum;
/*  28:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*  29:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  30:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  31:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  32:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioActividadMantenimiento;
/*  33:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioEquipo;
/*  34:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioLecturaMantenimiento;
/*  35:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioPlanMantenimiento;
/*  36:    */ import com.asinfo.as2.mantenimiento.procesos.servicio.ServicioOrdenTrabajoMantenimiento;
/*  37:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  38:    */ import com.asinfo.as2.util.AppUtil;
/*  39:    */ import com.asinfo.as2.util.RutaArchivo;
/*  40:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  41:    */ import com.asinfo.as2.utils.JsfUtil;
/*  42:    */ import java.math.BigDecimal;
/*  43:    */ import java.util.ArrayList;
/*  44:    */ import java.util.Date;
/*  45:    */ import java.util.List;
/*  46:    */ import java.util.Map;
/*  47:    */ import javax.annotation.PostConstruct;
/*  48:    */ import javax.ejb.EJB;
/*  49:    */ import javax.faces.bean.ManagedBean;
/*  50:    */ import javax.faces.bean.ManagedProperty;
/*  51:    */ import javax.faces.bean.ViewScoped;
/*  52:    */ import javax.faces.component.html.HtmlInputText;
/*  53:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  54:    */ import org.apache.log4j.Logger;
/*  55:    */ import org.primefaces.component.datatable.DataTable;
/*  56:    */ import org.primefaces.context.RequestContext;
/*  57:    */ import org.primefaces.event.FileUploadEvent;
/*  58:    */ import org.primefaces.model.LazyDataModel;
/*  59:    */ import org.primefaces.model.SortOrder;
/*  60:    */ 
/*  61:    */ @ManagedBean
/*  62:    */ @ViewScoped
/*  63:    */ public class OrdenTrabajoMantenimientoBean
/*  64:    */   extends PageControllerAS2
/*  65:    */ {
/*  66:    */   private static final long serialVersionUID = 1L;
/*  67:    */   @EJB
/*  68:    */   private ServicioOrdenTrabajoMantenimiento servicioOrdenTrabajoMantenimiento;
/*  69:    */   @EJB
/*  70:    */   private ServicioEquipo servicioEquipo;
/*  71:    */   @EJB
/*  72:    */   private ServicioPersonaResponsable servicioPersonaResponsable;
/*  73:    */   @EJB
/*  74:    */   private ServicioGenerico<ComponenteEquipo> servicioComponenteEquipo;
/*  75:    */   @EJB
/*  76:    */   private ServicioActividadMantenimiento servicioActividadMantenimiento;
/*  77:    */   @EJB
/*  78:    */   private ServicioDocumento servicioDocumento;
/*  79:    */   @EJB
/*  80:    */   private ServicioGenerico<Herramienta> servicioHerramienta;
/*  81:    */   @EJB
/*  82:    */   private ServicioProducto servicioProducto;
/*  83:    */   @EJB
/*  84:    */   private ServicioPlanMantenimiento servicioPlanMantenimiento;
/*  85:    */   @EJB
/*  86:    */   private ServicioLecturaMantenimiento servicioLecturaMantenimiento;
/*  87:    */   private OrdenTrabajoMantenimiento ordenTrabajoMantenimiento;
/*  88:    */   private LazyDataModel<OrdenTrabajoMantenimiento> listaOrdenTrabajoMantenimiento;
/*  89:    */   private List<Equipo> listaEquipoCombo;
/*  90:    */   private List<ActividadMantenimiento> listaActividadCombo;
/*  91:    */   private List<PersonaResponsable> listaResponsableCombo;
/*  92:    */   private List<Documento> listaDocumento;
/*  93:    */   private List<Herramienta> listaHerramientaCombo;
/*  94:    */   private DetalleOrdenTrabajoMantenimiento detalleCerrar;
/*  95:    */   private ActividadImagenOrdenTrabajoMantenimiento imagenDetalle;
/*  96:    */   private DataTable dtOrdenTrabajoMantenimiento;
/*  97:    */   private DataTable dtDetalle;
/*  98:    */   private DataTable dtDetalleCerrar;
/*  99:    */   private DataTable dtResponsable;
/* 100:    */   private DataTable dtHerramienta;
/* 101:    */   private DataTable dtMaterial;
/* 102:    */   private DataTable dtMaterialActividad;
/* 103:    */   private DataTable dtResponsableActividad;
/* 104:    */   private DataTable dtImagenActividad;
/* 105:    */   private DataTable dtLecturaActividad;
/* 106:118 */   private boolean indicadorRefrescarHerramientas = false;
/* 107:119 */   private boolean indicadorRefrescarMateriales = false;
/* 108:120 */   private boolean indicadorTodoCerrado = false;
/* 109:    */   @ManagedProperty("#{listaProductoBean}")
/* 110:    */   private ListaProductoBean listaProductoBean;
/* 111:    */   private Integer idOrdenTrabajoMantenimiento;
/* 112:    */   private boolean indicadorPanelCerrarOT;
/* 113:    */   
/* 114:    */   @PostConstruct
/* 115:    */   public void init()
/* 116:    */   {
/* 117:130 */     getListaProductoBean().setIndicadorMantenimiento(true);
/* 118:131 */     this.listaOrdenTrabajoMantenimiento = new LazyDataModel()
/* 119:    */     {
/* 120:    */       private static final long serialVersionUID = 1L;
/* 121:    */       
/* 122:    */       public List<OrdenTrabajoMantenimiento> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 123:    */       {
/* 124:138 */         filters = OrdenTrabajoMantenimientoBean.this.agregarFiltroOrganizacion(filters);
/* 125:139 */         if (OrdenTrabajoMantenimientoBean.this.idOrdenTrabajoMantenimiento != null)
/* 126:    */         {
/* 127:140 */           filters.put("idOrdenTrabajoMantenimiento", OrdenTrabajoMantenimientoBean.this.idOrdenTrabajoMantenimiento + "");
/* 128:141 */           OrdenTrabajoMantenimientoBean.this.idOrdenTrabajoMantenimiento = null;
/* 129:    */         }
/* 130:144 */         List<OrdenTrabajoMantenimiento> lista = new ArrayList();
/* 131:145 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 132:    */         
/* 133:147 */         lista = OrdenTrabajoMantenimientoBean.this.servicioOrdenTrabajoMantenimiento.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 134:148 */         OrdenTrabajoMantenimientoBean.this.listaOrdenTrabajoMantenimiento.setRowCount(OrdenTrabajoMantenimientoBean.this.servicioOrdenTrabajoMantenimiento.contarPorCriterio(filters));
/* 135:    */         
/* 136:150 */         return lista;
/* 137:    */       }
/* 138:    */     };
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String editar()
/* 142:    */   {
/* 143:163 */     if ((getOrdenTrabajoMantenimiento() != null) && (getOrdenTrabajoMantenimiento().getId() != 0))
/* 144:    */     {
/* 145:164 */       if ((!getOrdenTrabajoMantenimiento().getEstado().equals(Estado.ANULADO)) && 
/* 146:165 */         (!getOrdenTrabajoMantenimiento().getEstado().equals(Estado.CERRADO)))
/* 147:    */       {
/* 148:166 */         this.ordenTrabajoMantenimiento = this.servicioOrdenTrabajoMantenimiento.cargarDetalle(this.ordenTrabajoMantenimiento);
/* 149:167 */         setEditado(true);
/* 150:    */       }
/* 151:    */       else
/* 152:    */       {
/* 153:169 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " - " + 
/* 154:170 */           getOrdenTrabajoMantenimiento().getEstado().toString());
/* 155:    */       }
/* 156:    */     }
/* 157:    */     else {
/* 158:173 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 159:    */     }
/* 160:175 */     return "";
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void actualizarGuardar()
/* 164:    */   {
/* 165:179 */     if (this.indicadorRefrescarHerramientas) {
/* 166:180 */       actualizarHerramientas();
/* 167:    */     }
/* 168:182 */     if (this.indicadorRefrescarMateriales) {
/* 169:183 */       actualizarMateriales();
/* 170:    */     }
/* 171:185 */     guardarCompleto();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String guardar()
/* 175:    */   {
/* 176:195 */     if ((!this.indicadorRefrescarHerramientas) && (!this.indicadorRefrescarMateriales))
/* 177:    */     {
/* 178:196 */       guardarCompleto();
/* 179:    */     }
/* 180:    */     else
/* 181:    */     {
/* 182:198 */       RequestContext context = RequestContext.getCurrentInstance();
/* 183:199 */       context.execute("PF('refrescarAutomaticoDialog').show()");
/* 184:    */     }
/* 185:201 */     return "";
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void guardarCompleto()
/* 189:    */   {
/* 190:    */     try
/* 191:    */     {
/* 192:206 */       this.servicioOrdenTrabajoMantenimiento.guardar(this.ordenTrabajoMantenimiento);
/* 193:207 */       limpiar();
/* 194:208 */       setEditado(false);
/* 195:209 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 196:    */     }
/* 197:    */     catch (AS2ExceptionMantenimiento e)
/* 198:    */     {
/* 199:211 */       JsfUtil.addErrorMessage(e, "");
/* 200:    */     }
/* 201:    */     catch (Exception e)
/* 202:    */     {
/* 203:213 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 204:214 */       e.printStackTrace();
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String limpiar()
/* 209:    */   {
/* 210:225 */     crearOrdenTrabajo();
/* 211:226 */     this.indicadorRefrescarMateriales = false;
/* 212:227 */     this.indicadorRefrescarHerramientas = false;
/* 213:228 */     return "";
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String eliminar()
/* 217:    */   {
/* 218:238 */     if ((getOrdenTrabajoMantenimiento() != null) && (getOrdenTrabajoMantenimiento().getId() != 0)) {
/* 219:    */       try
/* 220:    */       {
/* 221:240 */         this.servicioOrdenTrabajoMantenimiento.anular(this.ordenTrabajoMantenimiento);
/* 222:241 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 223:    */       }
/* 224:    */       catch (AS2ExceptionMantenimiento e)
/* 225:    */       {
/* 226:243 */         JsfUtil.addErrorMessage(e, "");
/* 227:    */       }
/* 228:    */       catch (Exception e)
/* 229:    */       {
/* 230:245 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 231:246 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 232:    */       }
/* 233:    */     } else {
/* 234:249 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 235:    */     }
/* 236:251 */     return "";
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String cargarDatos()
/* 240:    */   {
/* 241:261 */     return "";
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void crearOrdenTrabajo()
/* 245:    */   {
/* 246:268 */     this.ordenTrabajoMantenimiento = new OrdenTrabajoMantenimiento();
/* 247:269 */     this.ordenTrabajoMantenimiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 248:270 */     this.ordenTrabajoMantenimiento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 249:271 */     this.ordenTrabajoMantenimiento.setEstado(Estado.ELABORADO);
/* 250:273 */     if (getListaDocumento().size() > 0) {
/* 251:274 */       this.ordenTrabajoMantenimiento.setDocumento((Documento)getListaDocumento().get(0));
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   public List<Documento> getListaDocumento()
/* 256:    */   {
/* 257:    */     try
/* 258:    */     {
/* 259:280 */       if (this.listaDocumento == null) {
/* 260:281 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.ORDEN_TRABAJO_MANTENIMIENTO, 
/* 261:282 */           AppUtil.getOrganizacion().getIdOrganizacion());
/* 262:    */       }
/* 263:    */     }
/* 264:    */     catch (ExcepcionAS2 e)
/* 265:    */     {
/* 266:285 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 267:    */     }
/* 268:288 */     return this.listaDocumento;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public OrdenTrabajoMantenimiento getOrdenTrabajoMantenimiento()
/* 272:    */   {
/* 273:292 */     if (this.ordenTrabajoMantenimiento == null) {
/* 274:293 */       crearOrdenTrabajo();
/* 275:    */     }
/* 276:295 */     return this.ordenTrabajoMantenimiento;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setOrdenTrabajoMantenimiento(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 280:    */   {
/* 281:299 */     this.ordenTrabajoMantenimiento = ordenTrabajoMantenimiento;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public LazyDataModel<OrdenTrabajoMantenimiento> getListaOrdenTrabajoMantenimiento()
/* 285:    */   {
/* 286:303 */     return this.listaOrdenTrabajoMantenimiento;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setListaOrdenTrabajoMantenimiento(LazyDataModel<OrdenTrabajoMantenimiento> listaOrdenTrabajoMantenimiento)
/* 290:    */   {
/* 291:307 */     this.listaOrdenTrabajoMantenimiento = listaOrdenTrabajoMantenimiento;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public List<Equipo> getListaEquipoCombo()
/* 295:    */   {
/* 296:311 */     if (this.listaEquipoCombo == null)
/* 297:    */     {
/* 298:312 */       this.listaEquipoCombo = new ArrayList();
/* 299:    */       
/* 300:314 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 301:315 */       filtros.put("activo", "true");
/* 302:316 */       List<Equipo> lista = this.servicioEquipo.obtenerListaCombo("nombre", true, filtros);
/* 303:317 */       for (Equipo equipo : lista)
/* 304:    */       {
/* 305:318 */         equipo = this.servicioEquipo.cargarDetalle(equipo);
/* 306:319 */         this.listaEquipoCombo.add(equipo);
/* 307:    */       }
/* 308:    */     }
/* 309:322 */     return this.listaEquipoCombo;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setListaEquipoCombo(List<Equipo> listaEquipoCombo)
/* 313:    */   {
/* 314:326 */     this.listaEquipoCombo = listaEquipoCombo;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public List<ActividadMantenimiento> getListaActividadCombo()
/* 318:    */   {
/* 319:330 */     if (this.listaActividadCombo == null)
/* 320:    */     {
/* 321:331 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 322:332 */       filtros.put("activo", "true");
/* 323:333 */       this.listaActividadCombo = this.servicioActividadMantenimiento.obtenerListaCombo("nombre", true, filtros);
/* 324:    */     }
/* 325:335 */     return this.listaActividadCombo;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setListaActividadCombo(List<ActividadMantenimiento> listaActividadCombo)
/* 329:    */   {
/* 330:339 */     this.listaActividadCombo = listaActividadCombo;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public List<PersonaResponsable> getListaResponsableCombo()
/* 334:    */   {
/* 335:343 */     if (this.listaResponsableCombo == null)
/* 336:    */     {
/* 337:344 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 338:345 */       filtros.put("activo", "true");
/* 339:346 */       filtros.put("indicadorMantenimiento", "true");
/* 340:347 */       this.listaResponsableCombo = this.servicioPersonaResponsable.obtenerListaCombo("nombres", true, filtros);
/* 341:    */     }
/* 342:349 */     return this.listaResponsableCombo;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setListaResponsableCombo(List<PersonaResponsable> listaResponsableCombo)
/* 346:    */   {
/* 347:353 */     this.listaResponsableCombo = listaResponsableCombo;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public DataTable getDtOrdenTrabajoMantenimiento()
/* 351:    */   {
/* 352:357 */     return this.dtOrdenTrabajoMantenimiento;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setDtOrdenTrabajoMantenimiento(DataTable dtOrdenTrabajoMantenimiento)
/* 356:    */   {
/* 357:361 */     this.dtOrdenTrabajoMantenimiento = dtOrdenTrabajoMantenimiento;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public DataTable getDtDetalle()
/* 361:    */   {
/* 362:365 */     return this.dtDetalle;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setDtDetalle(DataTable dtDetalle)
/* 366:    */   {
/* 367:369 */     this.dtDetalle = dtDetalle;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public DataTable getDtResponsable()
/* 371:    */   {
/* 372:373 */     return this.dtResponsable;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setDtResponsable(DataTable dtResponsable)
/* 376:    */   {
/* 377:377 */     this.dtResponsable = dtResponsable;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public DataTable getDtHerramienta()
/* 381:    */   {
/* 382:381 */     return this.dtHerramienta;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void setDtHerramienta(DataTable dtHerramienta)
/* 386:    */   {
/* 387:385 */     this.dtHerramienta = dtHerramienta;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public DataTable getDtMaterial()
/* 391:    */   {
/* 392:389 */     return this.dtMaterial;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setDtMaterial(DataTable dtMaterial)
/* 396:    */   {
/* 397:393 */     this.dtMaterial = dtMaterial;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public List<Herramienta> getListaHerramientaCombo()
/* 401:    */   {
/* 402:397 */     if (this.listaHerramientaCombo == null)
/* 403:    */     {
/* 404:398 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 405:399 */       filtros.put("activo", "true");
/* 406:400 */       this.listaHerramientaCombo = this.servicioHerramienta.obtenerListaCombo(Herramienta.class, "nombre", true, filtros);
/* 407:    */     }
/* 408:402 */     return this.listaHerramientaCombo;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public void setListaHerramientaCombo(List<Herramienta> listaHerramientaCombo)
/* 412:    */   {
/* 413:406 */     this.listaHerramientaCombo = listaHerramientaCombo;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public List<DetalleOrdenTrabajoMantenimiento> getListaDetalleOrdenTrabajo()
/* 417:    */   {
/* 418:410 */     List<DetalleOrdenTrabajoMantenimiento> lista = new ArrayList();
/* 419:411 */     if (this.ordenTrabajoMantenimiento != null) {
/* 420:412 */       for (DetalleOrdenTrabajoMantenimiento detalle : this.ordenTrabajoMantenimiento.getListaDetalleOrdenTrabajoMantenimiento()) {
/* 421:413 */         if (!detalle.isEliminado()) {
/* 422:414 */           lista.add(detalle);
/* 423:    */         }
/* 424:    */       }
/* 425:    */     }
/* 426:418 */     return lista;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public List<ResponsableOrdenTrabajoMantenimiento> getListaResponsableOrdenTrabajo()
/* 430:    */   {
/* 431:422 */     List<ResponsableOrdenTrabajoMantenimiento> lista = new ArrayList();
/* 432:423 */     for (ResponsableOrdenTrabajoMantenimiento detalle : this.ordenTrabajoMantenimiento.getListaResponsableOrdenTrabajoMantenimiento()) {
/* 433:424 */       if (!detalle.isEliminado()) {
/* 434:425 */         lista.add(detalle);
/* 435:    */       }
/* 436:    */     }
/* 437:428 */     return lista;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public List<HerramientaOrdenTrabajoMantenimiento> getListaHerramientaOrdenTrabajo()
/* 441:    */   {
/* 442:432 */     List<HerramientaOrdenTrabajoMantenimiento> lista = new ArrayList();
/* 443:433 */     for (HerramientaOrdenTrabajoMantenimiento detalle : this.ordenTrabajoMantenimiento.getListaHerramientaOrdenTrabajoMantenimiento()) {
/* 444:434 */       if (!detalle.isEliminado()) {
/* 445:435 */         lista.add(detalle);
/* 446:    */       }
/* 447:    */     }
/* 448:438 */     return lista;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public List<MaterialOrdenTrabajoMantenimiento> getListaMaterialOrdenTrabajo()
/* 452:    */   {
/* 453:442 */     List<MaterialOrdenTrabajoMantenimiento> lista = new ArrayList();
/* 454:443 */     for (MaterialOrdenTrabajoMantenimiento detalle : this.ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento()) {
/* 455:444 */       if (!detalle.isEliminado()) {
/* 456:445 */         lista.add(detalle);
/* 457:    */       }
/* 458:    */     }
/* 459:448 */     return lista;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void agregarDetalleOrdenTrabajo()
/* 463:    */   {
/* 464:452 */     DetalleOrdenTrabajoMantenimiento detalle = new DetalleOrdenTrabajoMantenimiento();
/* 465:453 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 466:454 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 467:455 */     detalle.setOrdenTrabajoMantenimiento(this.ordenTrabajoMantenimiento);
/* 468:456 */     this.ordenTrabajoMantenimiento.getListaDetalleOrdenTrabajoMantenimiento().add(detalle);
/* 469:457 */     this.indicadorRefrescarMateriales = true;
/* 470:458 */     this.indicadorRefrescarHerramientas = true;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void agregarResponsableOrdenTrabajo()
/* 474:    */   {
/* 475:462 */     ResponsableOrdenTrabajoMantenimiento detalle = new ResponsableOrdenTrabajoMantenimiento();
/* 476:463 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 477:464 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 478:465 */     detalle.setOrdenTrabajoMantenimiento(this.ordenTrabajoMantenimiento);
/* 479:466 */     this.ordenTrabajoMantenimiento.getListaResponsableOrdenTrabajoMantenimiento().add(detalle);
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void agregarHerramientaOrdenTrabajo()
/* 483:    */   {
/* 484:470 */     HerramientaOrdenTrabajoMantenimiento detalle = new HerramientaOrdenTrabajoMantenimiento();
/* 485:471 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 486:472 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 487:473 */     detalle.setOrdenTrabajoMantenimiento(this.ordenTrabajoMantenimiento);
/* 488:474 */     this.ordenTrabajoMantenimiento.getListaHerramientaOrdenTrabajoMantenimiento().add(detalle);
/* 489:475 */     this.indicadorRefrescarHerramientas = false;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public MaterialOrdenTrabajoMantenimiento agregarMaterialOrdenTrabajo()
/* 493:    */   {
/* 494:479 */     MaterialOrdenTrabajoMantenimiento detalle = new MaterialOrdenTrabajoMantenimiento();
/* 495:480 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 496:481 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 497:482 */     detalle.setOrdenTrabajoMantenimiento(this.ordenTrabajoMantenimiento);
/* 498:483 */     detalle.setMaterial(new Producto());
/* 499:484 */     this.ordenTrabajoMantenimiento.getListaMaterialOrdenTrabajoMantenimiento().add(detalle);
/* 500:485 */     this.indicadorRefrescarMateriales = false;
/* 501:486 */     return detalle;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public void eliminarDetalleOrdenTrabajo()
/* 505:    */   {
/* 506:490 */     DetalleOrdenTrabajoMantenimiento detalle = (DetalleOrdenTrabajoMantenimiento)this.dtDetalle.getRowData();
/* 507:491 */     detalle.setEliminado(true);
/* 508:492 */     this.indicadorRefrescarMateriales = true;
/* 509:493 */     this.indicadorRefrescarHerramientas = true;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void eliminarResponsableOrdenTrabajo()
/* 513:    */   {
/* 514:497 */     ResponsableOrdenTrabajoMantenimiento detalle = (ResponsableOrdenTrabajoMantenimiento)this.dtResponsable.getRowData();
/* 515:498 */     detalle.setEliminado(true);
/* 516:    */   }
/* 517:    */   
/* 518:    */   public void eliminarHerramientaOrdenTrabajo()
/* 519:    */   {
/* 520:502 */     HerramientaOrdenTrabajoMantenimiento detalle = (HerramientaOrdenTrabajoMantenimiento)this.dtHerramienta.getRowData();
/* 521:503 */     detalle.setEliminado(true);
/* 522:504 */     this.indicadorRefrescarHerramientas = false;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void eliminarMaterialOrdenTrabajo()
/* 526:    */   {
/* 527:508 */     MaterialOrdenTrabajoMantenimiento detalle = (MaterialOrdenTrabajoMantenimiento)this.dtMaterial.getRowData();
/* 528:509 */     detalle.setEliminado(true);
/* 529:510 */     this.indicadorRefrescarMateriales = false;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public void actualizarActividad()
/* 533:    */   {
/* 534:514 */     this.indicadorRefrescarMateriales = true;
/* 535:515 */     this.indicadorRefrescarHerramientas = true;
/* 536:    */   }
/* 537:    */   
/* 538:    */   public void actualizarMateriales()
/* 539:    */   {
/* 540:519 */     this.servicioOrdenTrabajoMantenimiento.actualizarMateriales(this.ordenTrabajoMantenimiento);
/* 541:520 */     this.indicadorRefrescarMateriales = false;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void actualizarHerramientas()
/* 545:    */   {
/* 546:524 */     this.servicioOrdenTrabajoMantenimiento.actualizarHerramientas(this.ordenTrabajoMantenimiento, true);
/* 547:525 */     this.indicadorRefrescarHerramientas = false;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void cargarProducto(Producto producto)
/* 551:    */   {
/* 552:529 */     MaterialOrdenTrabajoMantenimiento detalle = agregarMaterialOrdenTrabajo();
/* 553:530 */     detalle.setMaterial(producto);
/* 554:531 */     detalle.setCantidadRequerida(producto.getTraCantidad());
/* 555:532 */     this.indicadorRefrescarMateriales = false;
/* 556:    */   }
/* 557:    */   
/* 558:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 559:    */   {
/* 560:536 */     MaterialOrdenTrabajoMantenimiento detalle = null;
/* 561:    */     try
/* 562:    */     {
/* 563:538 */       String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 564:539 */       detalle = (MaterialOrdenTrabajoMantenimiento)this.dtMaterial.getRowData();
/* 565:540 */       Producto producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 566:541 */       detalle.setMaterial(producto);
/* 567:    */     }
/* 568:    */     catch (ExcepcionAS2 e)
/* 569:    */     {
/* 570:543 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 571:544 */       detalle.getMaterial().setCodigo("");
/* 572:545 */       detalle.getMaterial().setNombre("");
/* 573:    */     }
/* 574:547 */     this.indicadorRefrescarMateriales = false;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public ListaProductoBean getListaProductoBean()
/* 578:    */   {
/* 579:551 */     return this.listaProductoBean;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 583:    */   {
/* 584:555 */     this.listaProductoBean = listaProductoBean;
/* 585:    */   }
/* 586:    */   
/* 587:    */   public boolean isIndicadorRefrescarHerramientas()
/* 588:    */   {
/* 589:559 */     return this.indicadorRefrescarHerramientas;
/* 590:    */   }
/* 591:    */   
/* 592:    */   public void setIndicadorRefrescarHerramientas(boolean indicadorRefrescarHerramientas)
/* 593:    */   {
/* 594:563 */     this.indicadorRefrescarHerramientas = indicadorRefrescarHerramientas;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public boolean isIndicadorRefrescarMateriales()
/* 598:    */   {
/* 599:567 */     return this.indicadorRefrescarMateriales;
/* 600:    */   }
/* 601:    */   
/* 602:    */   public void setIndicadorRefrescarMateriales(boolean indicadorRefrescarMateriales)
/* 603:    */   {
/* 604:571 */     this.indicadorRefrescarMateriales = indicadorRefrescarMateriales;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public void procesar()
/* 608:    */   {
/* 609:575 */     this.ordenTrabajoMantenimiento = ((OrdenTrabajoMantenimiento)this.dtOrdenTrabajoMantenimiento.getRowData());
/* 610:    */     try
/* 611:    */     {
/* 612:577 */       this.servicioOrdenTrabajoMantenimiento.procesar(this.ordenTrabajoMantenimiento);
/* 613:    */     }
/* 614:    */     catch (AS2ExceptionMantenimiento e)
/* 615:    */     {
/* 616:579 */       JsfUtil.addErrorMessage(e, "");
/* 617:    */     }
/* 618:    */   }
/* 619:    */   
/* 620:    */   public Integer getIdOrdenTrabajoMantenimiento()
/* 621:    */   {
/* 622:584 */     return this.idOrdenTrabajoMantenimiento;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public void setIdOrdenTrabajoMantenimiento(Integer idOrdenTrabajoMantenimiento)
/* 626:    */   {
/* 627:588 */     this.idOrdenTrabajoMantenimiento = idOrdenTrabajoMantenimiento;
/* 628:    */   }
/* 629:    */   
/* 630:    */   public void cargarCierreOrdenTrabajo()
/* 631:    */   {
/* 632:592 */     if ((getOrdenTrabajoMantenimiento() != null) && (getOrdenTrabajoMantenimiento().getId() != 0))
/* 633:    */     {
/* 634:593 */       this.ordenTrabajoMantenimiento = this.servicioOrdenTrabajoMantenimiento.cargarDetalle(this.ordenTrabajoMantenimiento);
/* 635:594 */       actualizarIndicadorTodoCerrado();
/* 636:595 */       if (getOrdenTrabajoMantenimiento().getEstado().equals(Estado.PROCESADO))
/* 637:    */       {
/* 638:596 */         this.indicadorPanelCerrarOT = true;
/* 639:597 */         RequestContext context = RequestContext.getCurrentInstance();
/* 640:598 */         context.execute("PF('cerrarDialog').show();");
/* 641:    */       }
/* 642:    */       else
/* 643:    */       {
/* 644:600 */         addErrorMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " " + 
/* 645:601 */           getOrdenTrabajoMantenimiento().getEstado().getNombre());
/* 646:    */       }
/* 647:    */     }
/* 648:    */     else
/* 649:    */     {
/* 650:604 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 651:    */     }
/* 652:    */   }
/* 653:    */   
/* 654:    */   public void cargarDetalleACerrar()
/* 655:    */   {
/* 656:609 */     this.detalleCerrar = ((DetalleOrdenTrabajoMantenimiento)this.dtDetalleCerrar.getRowData());
/* 657:610 */     this.detalleCerrar.setFechaCierre(new Date());
/* 658:611 */     if ((this.detalleCerrar.getTiempoReal() == null) || (this.detalleCerrar.getTiempoReal().compareTo(BigDecimal.ZERO) == 0)) {
/* 659:612 */       if ((this.detalleCerrar.getCalendarioMantenimientoEntidad() != null) && 
/* 660:613 */         (this.detalleCerrar.getCalendarioMantenimientoEntidad().getDetallePlanMantenimiento() != null)) {
/* 661:614 */         this.detalleCerrar.setTiempoReal(this.detalleCerrar.getCalendarioMantenimientoEntidad().getDetallePlanMantenimiento().getDuracion());
/* 662:    */       } else {
/* 663:616 */         this.detalleCerrar.setTiempoReal(BigDecimal.ZERO);
/* 664:    */       }
/* 665:    */     }
/* 666:620 */     this.servicioOrdenTrabajoMantenimiento.cargarResponsablesporActividad(this.ordenTrabajoMantenimiento, this.detalleCerrar);
/* 667:621 */     this.servicioOrdenTrabajoMantenimiento.cargarMaterialesporActividad(this.ordenTrabajoMantenimiento, this.detalleCerrar);
/* 668:624 */     if (!this.detalleCerrar.isIndicadorCerrada())
/* 669:    */     {
/* 670:625 */       this.detalleCerrar.setListaLecturaMantenimiento(this.servicioPlanMantenimiento.crearLecturasMantenimiento(this.detalleCerrar.getFechaCierre(), true, this.detalleCerrar
/* 671:626 */         .getEquipo(), this.detalleCerrar.getComponenteEquipo(), this.detalleCerrar.getActividadMantenimiento(), TipoFrecuenciaEnum.FECHA));
/* 672:628 */       for (LecturaMantenimiento lecturaMantenimiento : this.detalleCerrar.getListaLecturaMantenimiento()) {
/* 673:629 */         lecturaMantenimiento.setDetalleOrdenTrabajoMantenimiento(this.detalleCerrar);
/* 674:    */       }
/* 675:    */     }
/* 676:633 */     RequestContext context = RequestContext.getCurrentInstance();
/* 677:634 */     context.execute("PF('cerrarDetalleDialog').show();");
/* 678:    */   }
/* 679:    */   
/* 680:    */   public DataTable getDtDetalleCerrar()
/* 681:    */   {
/* 682:638 */     return this.dtDetalleCerrar;
/* 683:    */   }
/* 684:    */   
/* 685:    */   public void setDtDetalleCerrar(DataTable dtDetalleCerrar)
/* 686:    */   {
/* 687:642 */     this.dtDetalleCerrar = dtDetalleCerrar;
/* 688:    */   }
/* 689:    */   
/* 690:    */   public DetalleOrdenTrabajoMantenimiento getDetalleCerrar()
/* 691:    */   {
/* 692:646 */     return this.detalleCerrar;
/* 693:    */   }
/* 694:    */   
/* 695:    */   public void setDetalleCerrar(DetalleOrdenTrabajoMantenimiento detalleCerrar)
/* 696:    */   {
/* 697:650 */     this.detalleCerrar = detalleCerrar;
/* 698:    */   }
/* 699:    */   
/* 700:    */   public boolean isIndicadorPanelCerrarOT()
/* 701:    */   {
/* 702:654 */     return this.indicadorPanelCerrarOT;
/* 703:    */   }
/* 704:    */   
/* 705:    */   public void setIndicadorPanelCerrarOT(boolean indicadorPanelCerrarOT)
/* 706:    */   {
/* 707:658 */     this.indicadorPanelCerrarOT = indicadorPanelCerrarOT;
/* 708:    */   }
/* 709:    */   
/* 710:    */   public void guardarCierreDetalleOT()
/* 711:    */   {
/* 712:    */     try
/* 713:    */     {
/* 714:663 */       this.servicioOrdenTrabajoMantenimiento.cerrarDetalleOrdenTrabajo(this.detalleCerrar);
/* 715:    */       
/* 716:665 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 717:    */       
/* 718:667 */       RequestContext context = RequestContext.getCurrentInstance();
/* 719:668 */       context.execute("PF('cerrarDetalleDialog').hide();");
/* 720:    */       
/* 721:    */ 
/* 722:671 */       this.ordenTrabajoMantenimiento = this.servicioOrdenTrabajoMantenimiento.cargarDetalle(this.ordenTrabajoMantenimiento);
/* 723:672 */       actualizarIndicadorTodoCerrado();
/* 724:673 */       if (this.indicadorTodoCerrado) {
/* 725:674 */         context.execute("PF('dialogConfirmarCerrarOTDialog').show();");
/* 726:    */       }
/* 727:    */     }
/* 728:    */     catch (AS2ExceptionMantenimiento e)
/* 729:    */     {
/* 730:677 */       JsfUtil.addErrorMessage(e, "");
/* 731:    */     }
/* 732:    */     catch (Exception e)
/* 733:    */     {
/* 734:679 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 735:680 */       e.printStackTrace();
/* 736:    */     }
/* 737:    */   }
/* 738:    */   
/* 739:    */   private void actualizarIndicadorTodoCerrado()
/* 740:    */   {
/* 741:685 */     this.indicadorTodoCerrado = true;
/* 742:686 */     for (DetalleOrdenTrabajoMantenimiento detalle : this.ordenTrabajoMantenimiento.getListaDetalleOrdenTrabajoMantenimiento()) {
/* 743:687 */       if (!detalle.isIndicadorCerrada())
/* 744:    */       {
/* 745:688 */         this.indicadorTodoCerrado = false;
/* 746:689 */         break;
/* 747:    */       }
/* 748:    */     }
/* 749:    */   }
/* 750:    */   
/* 751:    */   public void cerrarOrdenTrabajo()
/* 752:    */   {
/* 753:695 */     RequestContext context = RequestContext.getCurrentInstance();
/* 754:    */     try
/* 755:    */     {
/* 756:697 */       this.servicioOrdenTrabajoMantenimiento.cerrarOrdenTrabajo(this.ordenTrabajoMantenimiento);
/* 757:    */       
/* 758:699 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 759:    */       
/* 760:701 */       context.execute("PF('cerrarDialog').hide();");
/* 761:    */     }
/* 762:    */     catch (AS2ExceptionMantenimiento e)
/* 763:    */     {
/* 764:703 */       JsfUtil.addErrorMessage(e, "");
/* 765:    */     }
/* 766:    */     catch (Exception e)
/* 767:    */     {
/* 768:705 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 769:706 */       e.printStackTrace();
/* 770:    */     }
/* 771:    */     finally
/* 772:    */     {
/* 773:708 */       context.execute("PF('dialogConfirmarCerrarOTDialog').hide();");
/* 774:    */     }
/* 775:    */   }
/* 776:    */   
/* 777:    */   public boolean isIndicadorTodoCerrado()
/* 778:    */   {
/* 779:713 */     return this.indicadorTodoCerrado;
/* 780:    */   }
/* 781:    */   
/* 782:    */   public void setIndicadorTodoCerrado(boolean indicadorTodoCerrado)
/* 783:    */   {
/* 784:717 */     this.indicadorTodoCerrado = indicadorTodoCerrado;
/* 785:    */   }
/* 786:    */   
/* 787:    */   public DataTable getDtMaterialActividad()
/* 788:    */   {
/* 789:721 */     return this.dtMaterialActividad;
/* 790:    */   }
/* 791:    */   
/* 792:    */   public void setDtMaterialActividad(DataTable dtMaterialActividad)
/* 793:    */   {
/* 794:725 */     this.dtMaterialActividad = dtMaterialActividad;
/* 795:    */   }
/* 796:    */   
/* 797:    */   public DataTable getDtResponsableActividad()
/* 798:    */   {
/* 799:729 */     return this.dtResponsableActividad;
/* 800:    */   }
/* 801:    */   
/* 802:    */   public void setDtResponsableActividad(DataTable dtResponsableActividad)
/* 803:    */   {
/* 804:733 */     this.dtResponsableActividad = dtResponsableActividad;
/* 805:    */   }
/* 806:    */   
/* 807:    */   public DataTable getDtImagenActividad()
/* 808:    */   {
/* 809:737 */     return this.dtImagenActividad;
/* 810:    */   }
/* 811:    */   
/* 812:    */   public void setDtImagenActividad(DataTable dtImagenActividad)
/* 813:    */   {
/* 814:741 */     this.dtImagenActividad = dtImagenActividad;
/* 815:    */   }
/* 816:    */   
/* 817:    */   public ActividadImagenOrdenTrabajoMantenimiento getImagenDetalle()
/* 818:    */   {
/* 819:745 */     return this.imagenDetalle;
/* 820:    */   }
/* 821:    */   
/* 822:    */   public void setImagenDetalle(ActividadImagenOrdenTrabajoMantenimiento imagenDetalle)
/* 823:    */   {
/* 824:749 */     this.imagenDetalle = imagenDetalle;
/* 825:    */   }
/* 826:    */   
/* 827:    */   public void processUpload(FileUploadEvent event)
/* 828:    */   {
/* 829:    */     try
/* 830:    */     {
/* 831:754 */       if (this.imagenDetalle != null)
/* 832:    */       {
/* 833:755 */         String uploadDir = getDirectorioDescarga();
/* 834:756 */         String nombre = AppUtil.getOrganizacion().getId() + "_" + this.imagenDetalle.getNombre();
/* 835:757 */         String fileName = FuncionesUtiles.uploadArchivo(event, String.valueOf(FuncionesUtiles.obtenerNumeroRandomico(999999999)), nombre, uploadDir);
/* 836:    */         
/* 837:759 */         this.imagenDetalle.setArchivo(fileName);
/* 838:    */       }
/* 839:761 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 840:    */     }
/* 841:    */     catch (Exception e)
/* 842:    */     {
/* 843:763 */       e.printStackTrace();
/* 844:764 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_fichero"));
/* 845:765 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 846:    */     }
/* 847:    */   }
/* 848:    */   
/* 849:    */   public void processDownload()
/* 850:    */   {
/* 851:    */     try
/* 852:    */     {
/* 853:772 */       String fileName = this.imagenDetalle.getArchivo();
/* 854:773 */       String downloadDirectorio = getDirectorioDescarga();
/* 855:775 */       if (fileName == null) {
/* 856:776 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 857:    */       } else {
/* 858:778 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/* 859:    */       }
/* 860:    */     }
/* 861:    */     catch (Exception e)
/* 862:    */     {
/* 863:782 */       e.printStackTrace();
/* 864:783 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 865:    */     }
/* 866:    */   }
/* 867:    */   
/* 868:    */   public String getDirectorioDescarga()
/* 869:    */   {
/* 870:789 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "imagenes", "orden_trabajo");
/* 871:    */   }
/* 872:    */   
/* 873:    */   public void agregarImagen()
/* 874:    */   {
/* 875:793 */     ActividadImagenOrdenTrabajoMantenimiento detalleImagen = new ActividadImagenOrdenTrabajoMantenimiento();
/* 876:794 */     detalleImagen.setIdOrganizacion(this.detalleCerrar.getIdOrganizacion());
/* 877:795 */     detalleImagen.setIdSucursal(this.detalleCerrar.getIdSucursal());
/* 878:796 */     detalleImagen.setDetalleOrdenTrabajoMantenimiento(this.detalleCerrar);
/* 879:    */     
/* 880:798 */     this.detalleCerrar.getListaImagenOrdenTrabajoMantenimiento().add(detalleImagen);
/* 881:    */   }
/* 882:    */   
/* 883:    */   public void eliminarImagen()
/* 884:    */   {
/* 885:802 */     ActividadImagenOrdenTrabajoMantenimiento detalleImagen = (ActividadImagenOrdenTrabajoMantenimiento)this.dtImagenActividad.getRowData();
/* 886:803 */     detalleImagen.setEliminado(true);
/* 887:    */   }
/* 888:    */   
/* 889:    */   public List<ActividadImagenOrdenTrabajoMantenimiento> getListaDetalleImagen()
/* 890:    */   {
/* 891:807 */     List<ActividadImagenOrdenTrabajoMantenimiento> lista = new ArrayList();
/* 892:808 */     if (this.detalleCerrar != null) {
/* 893:809 */       for (ActividadImagenOrdenTrabajoMantenimiento detalleImagen : this.detalleCerrar.getListaImagenOrdenTrabajoMantenimiento()) {
/* 894:810 */         if (!detalleImagen.isEliminado()) {
/* 895:811 */           lista.add(detalleImagen);
/* 896:    */         }
/* 897:    */       }
/* 898:    */     }
/* 899:815 */     return lista;
/* 900:    */   }
/* 901:    */   
/* 902:    */   public DataTable getDtLecturaActividad()
/* 903:    */   {
/* 904:819 */     return this.dtLecturaActividad;
/* 905:    */   }
/* 906:    */   
/* 907:    */   public void setDtLecturaActividad(DataTable dtLecturaActividad)
/* 908:    */   {
/* 909:823 */     this.dtLecturaActividad = dtLecturaActividad;
/* 910:    */   }
/* 911:    */   
/* 912:    */   public void calcularValorAcumulado(LecturaMantenimiento lecturaMantenimiento)
/* 913:    */   {
/* 914:827 */     this.servicioLecturaMantenimiento.actualizarValoresLecturaMantenimiento(lecturaMantenimiento, true);
/* 915:    */   }
/* 916:    */   
/* 917:    */   public void actualizarFechaCierreDetalle()
/* 918:    */   {
/* 919:831 */     for (LecturaMantenimiento lecturaMantenimiento : this.detalleCerrar.getListaLecturaMantenimiento())
/* 920:    */     {
/* 921:832 */       lecturaMantenimiento.setFechaLectura(this.detalleCerrar.getFechaCierre());
/* 922:833 */       this.servicioLecturaMantenimiento.actualizarValoresLecturaMantenimiento(lecturaMantenimiento, true);
/* 923:    */     }
/* 924:    */   }
/* 925:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.controller.OrdenTrabajoMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */