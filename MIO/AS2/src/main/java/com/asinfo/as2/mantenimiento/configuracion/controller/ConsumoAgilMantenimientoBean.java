/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.DestinoCosto;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Lote;
/*  11:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  12:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  15:    */ import com.asinfo.as2.entities.Producto;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*  18:    */ import com.asinfo.as2.entities.mantenimiento.ConsumoAgilMantenimiento;
/*  19:    */ import com.asinfo.as2.entities.mantenimiento.DetalleConsumoAgilMantenimiento;
/*  20:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  21:    */ import com.asinfo.as2.entities.mantenimiento.OrdenTrabajoMantenimiento;
/*  22:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  23:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  24:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  25:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  27:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  28:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  29:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  30:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  31:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioDestinoCosto;
/*  32:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioActividadMantenimiento;
/*  33:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioConsumoAgilMantenimiento;
/*  34:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioEquipo;
/*  35:    */ import com.asinfo.as2.mantenimiento.procesos.servicio.ServicioOrdenTrabajoMantenimiento;
/*  36:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  37:    */ import com.asinfo.as2.util.AppUtil;
/*  38:    */ import com.asinfo.as2.utils.JsfUtil;
/*  39:    */ import java.math.BigDecimal;
/*  40:    */ import java.util.ArrayList;
/*  41:    */ import java.util.Date;
/*  42:    */ import java.util.List;
/*  43:    */ import java.util.Map;
/*  44:    */ import javax.annotation.PostConstruct;
/*  45:    */ import javax.ejb.EJB;
/*  46:    */ import javax.faces.bean.ManagedBean;
/*  47:    */ import javax.faces.bean.ManagedProperty;
/*  48:    */ import javax.faces.bean.ViewScoped;
/*  49:    */ import javax.faces.component.html.HtmlInputText;
/*  50:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  51:    */ import org.apache.log4j.Logger;
/*  52:    */ import org.primefaces.component.datatable.DataTable;
/*  53:    */ 
/*  54:    */ @ManagedBean
/*  55:    */ @ViewScoped
/*  56:    */ public class ConsumoAgilMantenimientoBean
/*  57:    */   extends PageControllerAS2
/*  58:    */ {
/*  59:    */   private static final long serialVersionUID = 1L;
/*  60:    */   @EJB
/*  61:    */   private ServicioDocumento servicioDocumento;
/*  62:    */   @EJB
/*  63:    */   private ServicioPersonaResponsable servicioPersonaResponsable;
/*  64:    */   @EJB
/*  65:    */   private ServicioEquipo servicioEquipo;
/*  66:    */   @EJB
/*  67:    */   private ServicioActividadMantenimiento servicioActividadMantenimiento;
/*  68:    */   @EJB
/*  69:    */   private ServicioProducto servicioProducto;
/*  70:    */   @EJB
/*  71:    */   private ServicioConsumoAgilMantenimiento servicioConsumoAgilMantenimiento;
/*  72:    */   @EJB
/*  73:    */   private ServicioOrdenTrabajoMantenimiento servicioOrdenTrabajoMantenimiento;
/*  74:    */   @EJB
/*  75:    */   private ServicioDestinoCosto servicioDestinoCosto;
/*  76:    */   @EJB
/*  77:    */   private ServicioMovimientoInventario servicioMovimientoInventario;
/*  78:    */   @EJB
/*  79:    */   private ServicioLote servicioLote;
/*  80:    */   private List<PersonaResponsable> listaResponsableCombo;
/*  81:    */   private List<Equipo> listaEquipoCombo;
/*  82:    */   private List<ActividadMantenimiento> listaActividadCombo;
/*  83: 93 */   private boolean requerido = false;
/*  84:    */   private ConsumoAgilMantenimiento consumoAgilMantenimiento;
/*  85:    */   private OrdenTrabajoMantenimiento ordenTrabajoMantenimiento;
/*  86:    */   private OrdenSalidaMaterial ordenSalidaMaterial;
/*  87:    */   private MovimientoInventario movimientoInventario;
/*  88:    */   private List<Documento> listaDocumentoCombo;
/*  89:    */   private List<Documento> listaDocumentoOrden;
/*  90:    */   private List<Bodega> listaBodegaCombo;
/*  91:101 */   private boolean indicadorRefrescarMateriales = false;
/*  92:    */   private DataTable dtDetalle;
/*  93:    */   private DataTable dtMaterial;
/*  94:    */   @ManagedProperty("#{listaProductoBean}")
/*  95:    */   private ListaProductoBean listaProductoBean;
/*  96:    */   
/*  97:    */   @PostConstruct
/*  98:    */   public void init()
/*  99:    */   {
/* 100:112 */     getListaProductoBean().setIndicadorMantenimiento(true);
/* 101:113 */     cargarDatos();
/* 102:114 */     setEditado(true);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public DataTable getDtMaterial()
/* 106:    */   {
/* 107:118 */     return this.dtMaterial;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setDtMaterial(DataTable dtMaterial)
/* 111:    */   {
/* 112:122 */     this.dtMaterial = dtMaterial;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String editar()
/* 116:    */   {
/* 117:127 */     return null;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String guardar()
/* 121:    */   {
/* 122:    */     try
/* 123:    */     {
/* 124:133 */       this.requerido = false;
/* 125:134 */       this.servicioConsumoAgilMantenimiento.guardar(this.consumoAgilMantenimiento);
/* 126:135 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 127:    */     }
/* 128:    */     catch (AS2ExceptionMantenimiento e)
/* 129:    */     {
/* 130:137 */       JsfUtil.addErrorMessage(e, "");
/* 131:    */     }
/* 132:    */     catch (Exception e)
/* 133:    */     {
/* 134:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 135:140 */       e.printStackTrace();
/* 136:    */     }
/* 137:142 */     return "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String eliminar()
/* 141:    */   {
/* 142:    */     try
/* 143:    */     {
/* 144:148 */       this.servicioConsumoAgilMantenimiento.eliminar(this.consumoAgilMantenimiento);
/* 145:    */     }
/* 146:    */     catch (AS2ExceptionMantenimiento e)
/* 147:    */     {
/* 148:150 */       JsfUtil.addErrorMessage(e, "");
/* 149:    */     }
/* 150:    */     catch (Exception e)
/* 151:    */     {
/* 152:152 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 153:    */     }
/* 154:155 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String limpiar()
/* 158:    */   {
/* 159:160 */     creaConsumoAgilMantenimiento();
/* 160:161 */     this.consumoAgilMantenimiento.setResponsableSalidaMercaderia(null);
/* 161:162 */     return "";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String cargarDatos()
/* 165:    */   {
/* 166:167 */     if (this.servicioConsumoAgilMantenimiento.cargarDetalle() != null) {
/* 167:168 */       this.consumoAgilMantenimiento = this.servicioConsumoAgilMantenimiento.cargarDetalle();
/* 168:    */     } else {
/* 169:170 */       creaConsumoAgilMantenimiento();
/* 170:    */     }
/* 171:172 */     return "";
/* 172:    */   }
/* 173:    */   
/* 174:    */   public ConsumoAgilMantenimiento getConsumoAgilMantenimiento()
/* 175:    */   {
/* 176:176 */     if (this.consumoAgilMantenimiento == null) {
/* 177:177 */       creaConsumoAgilMantenimiento();
/* 178:    */     }
/* 179:179 */     return this.consumoAgilMantenimiento;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setConsumoAgilMantenimiento(ConsumoAgilMantenimiento consumoAgilMantenimiento)
/* 183:    */   {
/* 184:183 */     this.consumoAgilMantenimiento = consumoAgilMantenimiento;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public DocumentoBase getDocumentoBase()
/* 188:    */   {
/* 189:187 */     return DocumentoBase.CONSUMO_BODEGA;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<Documento> getListaDocumentoCombo()
/* 193:    */   {
/* 194:    */     try
/* 195:    */     {
/* 196:192 */       if (this.listaDocumentoCombo == null) {
/* 197:193 */         this.listaDocumentoCombo = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.CONSUMO_BODEGA, 
/* 198:194 */           AppUtil.getOrganizacion().getIdOrganizacion());
/* 199:    */       }
/* 200:    */     }
/* 201:    */     catch (ExcepcionAS2 e)
/* 202:    */     {
/* 203:197 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 204:    */     }
/* 205:200 */     return this.listaDocumentoCombo;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setListaDocumentoCombo(List<Documento> listaDocumentoCombo)
/* 209:    */   {
/* 210:204 */     this.listaDocumentoCombo = listaDocumentoCombo;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<Documento> getListaDocumentoOrden()
/* 214:    */   {
/* 215:    */     try
/* 216:    */     {
/* 217:209 */       if (this.listaDocumentoOrden == null) {
/* 218:210 */         this.listaDocumentoOrden = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.ORDEN_TRABAJO_MANTENIMIENTO, 
/* 219:211 */           AppUtil.getOrganizacion().getIdOrganizacion());
/* 220:    */       }
/* 221:    */     }
/* 222:    */     catch (ExcepcionAS2 e)
/* 223:    */     {
/* 224:214 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 225:    */     }
/* 226:217 */     return this.listaDocumentoOrden;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setListaDocumentoOrden(List<Documento> listaDocumentoOrden)
/* 230:    */   {
/* 231:221 */     this.listaDocumentoOrden = listaDocumentoOrden;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public DataTable getDtDetalle()
/* 235:    */   {
/* 236:225 */     return this.dtDetalle;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setDtDetalle(DataTable dtDetalle)
/* 240:    */   {
/* 241:229 */     this.dtDetalle = dtDetalle;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public ListaProductoBean getListaProductoBean()
/* 245:    */   {
/* 246:233 */     return this.listaProductoBean;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 250:    */   {
/* 251:237 */     this.listaProductoBean = listaProductoBean;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setListaEquipoCombo(List<Equipo> listaEquipoCombo)
/* 255:    */   {
/* 256:241 */     this.listaEquipoCombo = listaEquipoCombo;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public List<Equipo> getListaEquipoCombo()
/* 260:    */   {
/* 261:245 */     if (this.listaEquipoCombo == null)
/* 262:    */     {
/* 263:246 */       this.listaEquipoCombo = new ArrayList();
/* 264:    */       
/* 265:248 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 266:249 */       filtros.put("activo", "true");
/* 267:250 */       List<Equipo> lista = this.servicioEquipo.obtenerListaCombo("nombre", true, filtros);
/* 268:251 */       for (Equipo equipo : lista)
/* 269:    */       {
/* 270:252 */         equipo = this.servicioEquipo.cargarDetalle(equipo);
/* 271:253 */         this.listaEquipoCombo.add(equipo);
/* 272:    */       }
/* 273:    */     }
/* 274:256 */     return this.listaEquipoCombo;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public List<ActividadMantenimiento> getListaActividadCombo()
/* 278:    */   {
/* 279:260 */     if (this.listaActividadCombo == null)
/* 280:    */     {
/* 281:261 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 282:262 */       filtros.put("activo", "true");
/* 283:263 */       this.listaActividadCombo = this.servicioActividadMantenimiento.obtenerListaCombo("nombre", true, filtros);
/* 284:    */     }
/* 285:265 */     return this.listaActividadCombo;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setListaActividadCombo(List<ActividadMantenimiento> listaActividadCombo)
/* 289:    */   {
/* 290:269 */     this.listaActividadCombo = listaActividadCombo;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void creaConsumoAgilMantenimiento()
/* 294:    */   {
/* 295:276 */     this.consumoAgilMantenimiento = new ConsumoAgilMantenimiento();
/* 296:277 */     this.consumoAgilMantenimiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 297:278 */     this.consumoAgilMantenimiento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 298:279 */     if (!getListaDocumentoCombo().isEmpty())
/* 299:    */     {
/* 300:280 */       Documento documento = (Documento)getListaDocumentoCombo().get(0);
/* 301:281 */       this.consumoAgilMantenimiento.setDocumento(documento);
/* 302:    */     }
/* 303:283 */     if (!getListaDocumentoOrden().isEmpty())
/* 304:    */     {
/* 305:284 */       Documento doc = (Documento)getListaDocumentoOrden().get(0);
/* 306:285 */       this.consumoAgilMantenimiento.setDocumentoOrden(doc);
/* 307:    */     }
/* 308:287 */     this.consumoAgilMantenimiento.setFecha(new Date());
/* 309:288 */     this.consumoAgilMantenimiento.setNumero("");
/* 310:    */   }
/* 311:    */   
/* 312:    */   public String actualizarDocumento()
/* 313:    */   {
/* 314:297 */     Integer idDocumento = Integer.valueOf(getConsumoAgilMantenimiento().getDocumento().getIdDocumento());
/* 315:298 */     this.consumoAgilMantenimiento.setDocumento(this.servicioDocumento.buscarPorId(idDocumento));
/* 316:    */     
/* 317:300 */     setSecuenciaEditable(!this.consumoAgilMantenimiento.getDocumento().isIndicadorBloqueoSecuencia());
/* 318:    */     
/* 319:302 */     return "";
/* 320:    */   }
/* 321:    */   
/* 322:    */   public List<PersonaResponsable> getListaResponsableCombo()
/* 323:    */   {
/* 324:306 */     if (this.listaResponsableCombo == null)
/* 325:    */     {
/* 326:307 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 327:308 */       filtros.put("activo", "true");
/* 328:309 */       filtros.put("indicadorMantenimiento", "true");
/* 329:310 */       this.listaResponsableCombo = this.servicioPersonaResponsable.obtenerListaCombo("nombres", true, filtros);
/* 330:    */     }
/* 331:312 */     return this.listaResponsableCombo;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public List<DetalleConsumoAgilMantenimiento> getListaDetalleComsumoAgilMantenimietno()
/* 335:    */   {
/* 336:316 */     List<DetalleConsumoAgilMantenimiento> lista = new ArrayList();
/* 337:317 */     if (this.consumoAgilMantenimiento != null) {
/* 338:318 */       for (DetalleConsumoAgilMantenimiento detalle : this.consumoAgilMantenimiento.getListaDetalleConsumoAgilMantenimiento()) {
/* 339:319 */         if (!detalle.isEliminado()) {
/* 340:320 */           lista.add(detalle);
/* 341:    */         }
/* 342:    */       }
/* 343:    */     }
/* 344:324 */     return lista;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void cargarProducto(Producto producto)
/* 348:    */   {
/* 349:328 */     DetalleConsumoAgilMantenimiento detalle = new DetalleConsumoAgilMantenimiento();
/* 350:329 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 351:330 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 352:331 */     detalle.setConsumoAgilMantenimiento(this.consumoAgilMantenimiento);
/* 353:332 */     detalle.setMaterial(producto);
/* 354:333 */     detalle.setCantidad(producto.getTraCantidad());
/* 355:334 */     this.consumoAgilMantenimiento.getListaDetalleConsumoAgilMantenimiento().add(detalle);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public List<DetalleConsumoAgilMantenimiento> getListaMaterialOrdenTrabajo()
/* 359:    */   {
/* 360:338 */     List<DetalleConsumoAgilMantenimiento> lista = new ArrayList();
/* 361:339 */     for (DetalleConsumoAgilMantenimiento detalle : this.consumoAgilMantenimiento.getListaDetalleConsumoAgilMantenimiento()) {
/* 362:340 */       if (!detalle.isEliminado()) {
/* 363:341 */         lista.add(detalle);
/* 364:    */       }
/* 365:    */     }
/* 366:344 */     return lista;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void actualizarProducto(AjaxBehaviorEvent event)
/* 370:    */   {
/* 371:348 */     DetalleConsumoAgilMantenimiento detalle = null;
/* 372:    */     try
/* 373:    */     {
/* 374:350 */       String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 375:351 */       detalle = (DetalleConsumoAgilMantenimiento)this.dtMaterial.getRowData();
/* 376:352 */       Producto producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 377:353 */       if (producto.isIndicadorMantenimiento()) {
/* 378:354 */         detalle.setMaterial(producto);
/* 379:    */       } else {
/* 380:356 */         throw new ExcepcionAS2("msg_producto_no_encontrado", " " + codigo);
/* 381:    */       }
/* 382:    */     }
/* 383:    */     catch (ExcepcionAS2 e)
/* 384:    */     {
/* 385:359 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 386:360 */       detalle.getMaterial().setCodigo("");
/* 387:361 */       detalle.getMaterial().setNombre("");
/* 388:    */     }
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void eliminarMaterialOrdenTrabajo()
/* 392:    */   {
/* 393:366 */     DetalleConsumoAgilMantenimiento detalle = (DetalleConsumoAgilMantenimiento)this.dtMaterial.getRowData();
/* 394:    */     try
/* 395:    */     {
/* 396:368 */       this.servicioConsumoAgilMantenimiento.eliminar(detalle);
/* 397:369 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 398:    */     }
/* 399:    */     catch (AS2ExceptionMantenimiento e)
/* 400:    */     {
/* 401:371 */       JsfUtil.addErrorMessage(e, "");
/* 402:    */     }
/* 403:    */     catch (Exception e)
/* 404:    */     {
/* 405:373 */       addErrorMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 406:374 */       e.printStackTrace();
/* 407:    */     }
/* 408:    */   }
/* 409:    */   
/* 410:    */   public boolean isRequerido()
/* 411:    */   {
/* 412:379 */     return this.requerido;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void setRequerido(boolean requerido)
/* 416:    */   {
/* 417:383 */     this.requerido = requerido;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void guardarDetalle()
/* 421:    */   {
/* 422:    */     try
/* 423:    */     {
/* 424:389 */       DetalleConsumoAgilMantenimiento detalle = (DetalleConsumoAgilMantenimiento)this.dtMaterial.getRowData();
/* 425:390 */       if ((detalle.getEquipo() != null) && (detalle.getMaterial().getCodigo() != null) && (detalle.getCantidad().compareTo(BigDecimal.ZERO) > 0)) {
/* 426:391 */         this.servicioConsumoAgilMantenimiento.guardar(this.consumoAgilMantenimiento, detalle);
/* 427:    */       }
/* 428:393 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 429:    */     }
/* 430:    */     catch (AS2ExceptionMantenimiento e)
/* 431:    */     {
/* 432:395 */       JsfUtil.addErrorMessage(e, "");
/* 433:    */     }
/* 434:    */     catch (Exception e)
/* 435:    */     {
/* 436:397 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 437:398 */       e.printStackTrace();
/* 438:    */     }
/* 439:    */   }
/* 440:    */   
/* 441:    */   public boolean isIndicadorRefrescarMateriales()
/* 442:    */   {
/* 443:404 */     return this.indicadorRefrescarMateriales;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setIndicadorRefrescarMateriales(boolean indicadorRefrescarMateriales)
/* 447:    */   {
/* 448:408 */     this.indicadorRefrescarMateriales = indicadorRefrescarMateriales;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public void crearOrdenTrabajo()
/* 452:    */   {
/* 453:412 */     this.ordenTrabajoMantenimiento = new OrdenTrabajoMantenimiento();
/* 454:413 */     this.ordenTrabajoMantenimiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 455:414 */     this.ordenTrabajoMantenimiento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 456:415 */     this.ordenTrabajoMantenimiento.setEstado(Estado.PROCESADO);
/* 457:416 */     this.ordenTrabajoMantenimiento.setFechaMantenimiento(this.consumoAgilMantenimiento.getFecha());
/* 458:417 */     if (getListaDocumentoOrden().size() > 0) {
/* 459:418 */       this.ordenTrabajoMantenimiento.setDocumento((Documento)getListaDocumentoOrden().get(0));
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   public OrdenSalidaMaterial getOrdenSalidaMaterial()
/* 464:    */   {
/* 465:424 */     return this.ordenSalidaMaterial;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public void setOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 469:    */   {
/* 470:428 */     this.ordenSalidaMaterial = ordenSalidaMaterial;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public MovimientoInventario getMovimientoInventario()
/* 474:    */   {
/* 475:432 */     if (this.movimientoInventario == null) {
/* 476:433 */       creaConsumoBodega();
/* 477:    */     }
/* 478:435 */     return this.movimientoInventario;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public void setMovimientoInventario(MovimientoInventario movimientoInventario)
/* 482:    */   {
/* 483:439 */     this.movimientoInventario = movimientoInventario;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void creaConsumoBodega()
/* 487:    */   {
/* 488:446 */     this.ordenSalidaMaterial = null;
/* 489:447 */     this.movimientoInventario = new MovimientoInventario();
/* 490:448 */     this.movimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 491:449 */     this.movimientoInventario.setSucursal(AppUtil.getSucursal());
/* 492:450 */     this.movimientoInventario.setEstado(Estado.ELABORADO);
/* 493:451 */     this.movimientoInventario.setResponsableSalidaMercaderia(this.consumoAgilMantenimiento.getResponsableSalidaMercaderia());
/* 494:452 */     if (!getListaDocumentoCombo().isEmpty())
/* 495:    */     {
/* 496:453 */       Documento documento = (Documento)getListaDocumentoCombo().get(0);
/* 497:454 */       this.movimientoInventario.setDocumento(documento);
/* 498:455 */       actualizarDocumento();
/* 499:    */     }
/* 500:457 */     this.movimientoInventario.setFecha(this.consumoAgilMantenimiento.getFecha());
/* 501:458 */     this.movimientoInventario.setNumero("");
/* 502:459 */     this.movimientoInventario.setOrdenTrabajoMantenimiento(this.ordenTrabajoMantenimiento);
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void procesar()
/* 506:    */     throws AS2Exception, ExcepcionAS2
/* 507:    */   {
/* 508:    */     try
/* 509:    */     {
/* 510:466 */       crearOrdenTrabajo();
/* 511:467 */       creaConsumoBodega();
/* 512:468 */       this.servicioOrdenTrabajoMantenimiento.procesarConsumoAgilMantenimiento(this.ordenTrabajoMantenimiento, this.consumoAgilMantenimiento, this.movimientoInventario);
/* 513:    */       
/* 514:470 */       this.servicioConsumoAgilMantenimiento.eliminar(this.consumoAgilMantenimiento);
/* 515:471 */       limpiar();
/* 516:472 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 517:    */     }
/* 518:    */     catch (AS2ExceptionMantenimiento e)
/* 519:    */     {
/* 520:474 */       JsfUtil.addErrorMessage(e, "");
/* 521:    */     }
/* 522:    */     catch (Exception e)
/* 523:    */     {
/* 524:476 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 525:477 */       e.printStackTrace();
/* 526:    */     }
/* 527:    */   }
/* 528:    */   
/* 529:    */   public OrdenTrabajoMantenimiento getOrdenTrabajoMantenimiento()
/* 530:    */   {
/* 531:482 */     if (this.ordenTrabajoMantenimiento == null) {
/* 532:483 */       crearOrdenTrabajo();
/* 533:    */     }
/* 534:485 */     return this.ordenTrabajoMantenimiento;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public void setOrdenTrabajoMantenimiento(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 538:    */   {
/* 539:489 */     this.ordenTrabajoMantenimiento = ordenTrabajoMantenimiento;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public List<DestinoCosto> autocompletarDestinoCosto(String consulta)
/* 543:    */   {
/* 544:500 */     consulta = consulta.toUpperCase();
/* 545:501 */     List<DestinoCosto> lista = this.servicioDestinoCosto.autocompletarDestinoCosto(consulta);
/* 546:502 */     return lista;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public List<Bodega> getListaBodegaCombo()
/* 550:    */   {
/* 551:512 */     if (this.listaBodegaCombo == null) {
/* 552:513 */       this.listaBodegaCombo = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 553:    */     }
/* 554:515 */     return this.listaBodegaCombo;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public DetalleConsumoAgilMantenimiento agregarDetalleConsumoAgilMantenimiento()
/* 558:    */   {
/* 559:519 */     DetalleConsumoAgilMantenimiento detalle = new DetalleConsumoAgilMantenimiento();
/* 560:520 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 561:521 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 562:522 */     detalle.setConsumoAgilMantenimiento(this.consumoAgilMantenimiento);
/* 563:523 */     detalle.setMaterial(new Producto());
/* 564:524 */     this.consumoAgilMantenimiento.getListaDetalleConsumoAgilMantenimiento().add(detalle);
/* 565:525 */     return detalle;
/* 566:    */   }
/* 567:    */   
/* 568:    */   public void guardarConsumoAgilMantenimiento()
/* 569:    */   {
/* 570:    */     try
/* 571:    */     {
/* 572:530 */       this.servicioConsumoAgilMantenimiento.guardar(this.consumoAgilMantenimiento);
/* 573:531 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 574:    */     }
/* 575:    */     catch (AS2ExceptionMantenimiento e)
/* 576:    */     {
/* 577:533 */       JsfUtil.addErrorMessage(e, "");
/* 578:    */     }
/* 579:    */     catch (Exception e)
/* 580:    */     {
/* 581:535 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 582:536 */       e.printStackTrace();
/* 583:    */     }
/* 584:    */   }
/* 585:    */   
/* 586:    */   public void actualizarSaldo()
/* 587:    */   {
/* 588:541 */     DetalleConsumoAgilMantenimiento detalle = (DetalleConsumoAgilMantenimiento)this.dtMaterial.getRowData();
/* 589:542 */     Bodega bodega = detalle.getBodegaOrigen();
/* 590:543 */     BigDecimal saldoBodega = this.servicioProducto.getSaldo(detalle.getMaterial().getId(), bodega == null ? 0 : bodega.getId(), this.consumoAgilMantenimiento
/* 591:544 */       .getFecha());
/* 592:545 */     detalle.setSaldo(saldoBodega);
/* 593:    */   }
/* 594:    */   
/* 595:    */   public List<Lote> autocompletarLotes(String consulta)
/* 596:    */   {
/* 597:549 */     DetalleConsumoAgilMantenimiento detalleMovimientoInventario = (DetalleConsumoAgilMantenimiento)this.dtMaterial.getRowData();
/* 598:550 */     return this.servicioLote.autocompletarLote(detalleMovimientoInventario.getMaterial(), consulta);
/* 599:    */   }
/* 600:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.ConsumoAgilMantenimientoBean
 * JD-Core Version:    0.7.0.1
 */