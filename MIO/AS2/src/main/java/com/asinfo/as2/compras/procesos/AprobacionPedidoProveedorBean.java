/*   1:    */ package com.asinfo.as2.compras.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*  12:    */ import com.asinfo.as2.entities.Bodega;
/*  13:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  14:    */ import com.asinfo.as2.entities.Documento;
/*  15:    */ import com.asinfo.as2.entities.Empresa;
/*  16:    */ import com.asinfo.as2.entities.Organizacion;
/*  17:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*  18:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  19:    */ import com.asinfo.as2.entities.Sucursal;
/*  20:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  21:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  22:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  23:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  24:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  26:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  27:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  28:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  29:    */ import com.asinfo.as2.util.AppUtil;
/*  30:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  31:    */ import com.asinfo.as2.utils.JsfUtil;
/*  32:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  33:    */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*  34:    */ import java.math.BigDecimal;
/*  35:    */ import java.math.RoundingMode;
/*  36:    */ import java.util.ArrayList;
/*  37:    */ import java.util.Arrays;
/*  38:    */ import java.util.Calendar;
/*  39:    */ import java.util.Date;
/*  40:    */ import java.util.HashMap;
/*  41:    */ import java.util.List;
/*  42:    */ import java.util.Map;
/*  43:    */ import javax.annotation.PostConstruct;
/*  44:    */ import javax.ejb.EJB;
/*  45:    */ import javax.faces.bean.ManagedBean;
/*  46:    */ import javax.faces.bean.ViewScoped;
/*  47:    */ import javax.faces.model.ListDataModel;
/*  48:    */ import javax.faces.model.SelectItem;
/*  49:    */ import javax.persistence.Temporal;
/*  50:    */ import javax.persistence.TemporalType;
/*  51:    */ import javax.validation.constraints.NotNull;
/*  52:    */ import org.apache.log4j.Logger;
/*  53:    */ import org.primefaces.component.datatable.DataTable;
/*  54:    */ 
/*  55:    */ @ManagedBean
/*  56:    */ @ViewScoped
/*  57:    */ public class AprobacionPedidoProveedorBean
/*  58:    */   extends PageControllerAS2
/*  59:    */ {
/*  60:    */   private static final long serialVersionUID = 725178972436303995L;
/*  61:    */   @EJB
/*  62:    */   private ServicioPedidoProveedor servicioPedidoProveedor;
/*  63:    */   @EJB
/*  64:    */   private ServicioProducto servicioProducto;
/*  65:    */   @EJB
/*  66:    */   private ServicioDocumento servicioDocumento;
/*  67:    */   @EJB
/*  68:    */   private ServicioEmpresa servicioEmpresa;
/*  69:    */   @EJB
/*  70:    */   private ServicioSucursal servicioSucursal;
/*  71:    */   @EJB
/*  72:    */   private ServicioUsuario servicioUsuario;
/*  73:    */   @EJB
/*  74:    */   private ServicioCategoriaEmpresa servicioCategoriEmpresa;
/*  75:    */   @EJB
/*  76:    */   private ServicioPersonaResponsable servicioPersonaResponsable;
/*  77: 94 */   private PedidoProveedor pedidoProveedor = new PedidoProveedor();
/*  78: 95 */   private Estado estado = Estado.APROBADO;
/*  79:    */   private Sucursal sucursal;
/*  80:    */   private Documento documento;
/*  81:    */   private Empresa proveedor;
/*  82:    */   private List<Sucursal> listaSucursal;
/*  83:    */   private List<Bodega> listaBodega;
/*  84:    */   private List<PedidoProveedor> listaPedidoProveedor;
/*  85:    */   private List<Documento> listaDocumentoProveedor;
/*  86:    */   private CategoriaEmpresa categoriaEmpresa;
/*  87:    */   private List<PersonaResponsable> listaPersonaResponsable;
/*  88:    */   private boolean seleccionarTodos;
/*  89:108 */   private ListDataModel<Object[]> listaResumen = new ListDataModel();
/*  90:109 */   private BigDecimal totalAcumuladoAnio = BigDecimal.ZERO;
/*  91:110 */   private BigDecimal totalAcumuladoMes = BigDecimal.ZERO;
/*  92:111 */   private BigDecimal totalPorAprobar = BigDecimal.ZERO;
/*  93:    */   @Temporal(TemporalType.DATE)
/*  94:    */   @NotNull
/*  95:115 */   private Date fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/*  96:    */   @Temporal(TemporalType.DATE)
/*  97:    */   @NotNull
/*  98:117 */   private Date fechaHasta = new Date();
/*  99:    */   private DataTable dtPedidoProveedor;
/* 100:    */   private SelectItem[] listaEstadoItem;
/* 101:    */   private boolean cargarPedidosAprobados;
/* 102:    */   private boolean indicadorMostrarTodoAprobacion;
/* 103:127 */   private boolean indicadorAprobarCompleto = true;
/* 104:129 */   private Boolean indicadorParametroEditarPrecioPedido = null;
/* 105:    */   
/* 106:    */   public String editar()
/* 107:    */   {
/* 108:138 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 109:139 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   @PostConstruct
/* 113:    */   public void init()
/* 114:    */   {
/* 115:144 */     setDocumentoBase(DocumentoBase.PEDIDO_PROVEEDOR);
/* 116:145 */     cargarPedidos();
/* 117:146 */     actualizarIndicadorAprobacionCompleta();
/* 118:    */   }
/* 119:    */   
/* 120:    */   private void actualizarIndicadorAprobacionCompleta()
/* 121:    */   {
/* 122:151 */     Usuario usuarioSesion = AppUtil.getUsuarioEnSesion();
/* 123:152 */     if (usuarioSesion.isIndicadorAprobador())
/* 124:    */     {
/* 125:153 */       List<EntidadUsuario> listaSuperiores = this.servicioUsuario.buscarJerarquiaInmediata(usuarioSesion.getIdUsuario(), true, DocumentoBase.PEDIDO_PROVEEDOR);
/* 126:155 */       if (listaSuperiores.isEmpty()) {
/* 127:156 */         this.indicadorAprobarCompleto = true;
/* 128:    */       } else {
/* 129:158 */         this.indicadorAprobarCompleto = false;
/* 130:    */       }
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String guardar()
/* 135:    */   {
/* 136:170 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 137:171 */     return "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String eliminar()
/* 141:    */   {
/* 142:181 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 143:182 */     return "";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void seleccionarDetalle(PedidoProveedor pedidoProveedor)
/* 147:    */   {
/* 148:186 */     this.pedidoProveedor = pedidoProveedor;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String limpiar()
/* 152:    */   {
/* 153:196 */     setEditado(false);
/* 154:    */     
/* 155:198 */     return "";
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String cargarDatos()
/* 159:    */   {
/* 160:208 */     return "";
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void cargaDatosPedidoProveedor()
/* 164:    */   {
/* 165:212 */     this.pedidoProveedor = ((PedidoProveedor)getDtPedidoProveedor().getRowData());
/* 166:213 */     if ((this.pedidoProveedor != null) && (this.pedidoProveedor.getId() > 0)) {
/* 167:    */       try
/* 168:    */       {
/* 169:215 */         this.pedidoProveedor = this.servicioPedidoProveedor.cargarDetalle(Integer.valueOf(this.pedidoProveedor.getId()));
/* 170:    */       }
/* 171:    */       catch (ExcepcionAS2Compras e)
/* 172:    */       {
/* 173:217 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 174:218 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 175:219 */         e.printStackTrace();
/* 176:    */       }
/* 177:    */       catch (Exception e)
/* 178:    */       {
/* 179:221 */         addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 180:222 */         LOG.error("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 181:    */       }
/* 182:    */     } else {
/* 183:225 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void cargarPedidos()
/* 188:    */   {
/* 189:231 */     Usuario usuarioSesion = AppUtil.getUsuarioEnSesion();
/* 190:    */     try
/* 191:    */     {
/* 192:234 */       this.listaPedidoProveedor = this.servicioPedidoProveedor.cargarPedidosPorAprobar(AppUtil.getOrganizacion().getIdOrganizacion(), this.sucursal, this.documento, this.proveedor, this.fechaDesde, this.fechaHasta, this.cargarPedidosAprobados, usuarioSesion, this.indicadorMostrarTodoAprobacion, this.categoriaEmpresa);
/* 193:    */     }
/* 194:    */     catch (AS2Exception e)
/* 195:    */     {
/* 196:237 */       JsfUtil.addErrorMessage(e, "");
/* 197:    */     }
/* 198:241 */     actualizarResumen();
/* 199:    */   }
/* 200:    */   
/* 201:    */   private void actualizarResumen()
/* 202:    */   {
/* 203:252 */     Calendar calFechaHasta = Calendar.getInstance();
/* 204:253 */     calFechaHasta.setTime(this.fechaHasta);
/* 205:254 */     int mes = calFechaHasta.get(2) + 1;
/* 206:    */     
/* 207:256 */     List<Object[]> resumenPedido = this.servicioPedidoProveedor.getResumenAnualAprobadasYPorAprobar(AppUtil.getOrganizacion().getIdOrganizacion(), calFechaHasta
/* 208:257 */       .get(1), mes, this.documento);
/* 209:    */     
/* 210:259 */     this.totalAcumuladoAnio = BigDecimal.ZERO;
/* 211:260 */     this.totalAcumuladoMes = BigDecimal.ZERO;
/* 212:261 */     this.totalPorAprobar = BigDecimal.ZERO;
/* 213:262 */     for (Object[] datos : resumenPedido)
/* 214:    */     {
/* 215:264 */       if ("01".equals((String)datos[0])) {
/* 216:265 */         datos[0] = getNombreDimension1();
/* 217:266 */       } else if ("02".equals((String)datos[0])) {
/* 218:267 */         datos[0] = getNombreDimension2();
/* 219:268 */       } else if ("03".equals((String)datos[0])) {
/* 220:269 */         datos[0] = getNombreDimension3();
/* 221:270 */       } else if ("04".equals((String)datos[0])) {
/* 222:271 */         datos[0] = getNombreDimension4();
/* 223:272 */       } else if ("05".equals((String)datos[0])) {
/* 224:273 */         datos[0] = getNombreDimension5();
/* 225:    */       } else {
/* 226:275 */         datos[0] = "N/A";
/* 227:    */       }
/* 228:278 */       this.totalAcumuladoAnio = this.totalAcumuladoAnio.add((BigDecimal)datos[1]);
/* 229:279 */       this.totalAcumuladoMes = this.totalAcumuladoMes.add((BigDecimal)datos[2]);
/* 230:280 */       this.totalPorAprobar = this.totalPorAprobar.add((BigDecimal)datos[3]);
/* 231:    */     }
/* 232:282 */     this.totalAcumuladoAnio = this.totalAcumuladoAnio.setScale(2, RoundingMode.HALF_UP);
/* 233:283 */     this.totalAcumuladoMes = this.totalAcumuladoMes.setScale(2, RoundingMode.HALF_UP);
/* 234:284 */     this.totalPorAprobar = this.totalPorAprobar.setScale(2, RoundingMode.HALF_UP);
/* 235:285 */     this.listaResumen = new ListDataModel(resumenPedido);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void cambiarPedidosProveedor(Estado estado)
/* 239:    */     throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception
/* 240:    */   {
/* 241:289 */     List<PedidoProveedor> listaPedidosNoActualizarUsuariosAprobacion = new ArrayList();
/* 242:290 */     for (PedidoProveedor pedidoProveedor : this.listaPedidoProveedor) {
/* 243:291 */       if ((pedidoProveedor.getUsuariosAutorizacion() != null) && 
/* 244:292 */         (pedidoProveedor.getUsuariosAutorizacion().contains(AppUtil.getUsuarioEnSesion().getNombreUsuario()))) {
/* 245:293 */         listaPedidosNoActualizarUsuariosAprobacion.add(pedidoProveedor);
/* 246:    */       }
/* 247:    */     }
/* 248:298 */     if ((estado.equals(Estado.APROBADO)) && (!this.indicadorAprobarCompleto)) {
/* 249:299 */       estado = Estado.APROBADO_PARCIAL;
/* 250:    */     }
/* 251:301 */     this.servicioPedidoProveedor.cambiarEstadoPedidos(this.listaPedidoProveedor, estado, AppUtil.getUsuarioEnSesion().getNombreUsuario(), new Date(), listaPedidosNoActualizarUsuariosAprobacion);
/* 252:302 */     cargarPedidos();
/* 253:    */   }
/* 254:    */   
/* 255:    */   public PedidoProveedor getPedidoProveedor()
/* 256:    */   {
/* 257:306 */     return this.pedidoProveedor;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 261:    */   {
/* 262:310 */     this.pedidoProveedor = pedidoProveedor;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public DataTable getDtPedidoProveedor()
/* 266:    */   {
/* 267:315 */     return this.dtPedidoProveedor;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setDtPedidoProveedor(DataTable dtPedidoProveedor)
/* 271:    */   {
/* 272:319 */     this.dtPedidoProveedor = dtPedidoProveedor;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 276:    */   {
/* 277:323 */     return this.servicioEmpresa.autocompletarProveedores(consulta, false, this.categoriaEmpresa);
/* 278:    */   }
/* 279:    */   
/* 280:    */   public List<Sucursal> getListaSucursal()
/* 281:    */   {
/* 282:330 */     if (this.listaSucursal == null) {
/* 283:331 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 284:    */     }
/* 285:333 */     return this.listaSucursal;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public List<PersonaResponsable> getListaPersonaResponsable()
/* 289:    */   {
/* 290:337 */     if (this.listaPersonaResponsable == null)
/* 291:    */     {
/* 292:338 */       HashMap<String, String> filters = new HashMap();
/* 293:339 */       filters.put("indicadorCompra", "true");
/* 294:340 */       this.listaPersonaResponsable = this.servicioPersonaResponsable.obtenerListaCombo("nombres", true, filters);
/* 295:    */     }
/* 296:342 */     return this.listaPersonaResponsable;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<Bodega> getListaBodega()
/* 300:    */   {
/* 301:346 */     if (this.listaBodega == null) {
/* 302:347 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 303:    */     }
/* 304:349 */     return this.listaBodega;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public Date getFechaDesde()
/* 308:    */   {
/* 309:353 */     return this.fechaDesde;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setFechaDesde(Date fechaDesde)
/* 313:    */   {
/* 314:357 */     this.fechaDesde = fechaDesde;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public Date getFechaHasta()
/* 318:    */   {
/* 319:361 */     return this.fechaHasta;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setFechaHasta(Date fechaHasta)
/* 323:    */   {
/* 324:365 */     this.fechaHasta = fechaHasta;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public Sucursal getSucursal()
/* 328:    */   {
/* 329:369 */     return this.sucursal;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setSucursal(Sucursal sucursal)
/* 333:    */   {
/* 334:373 */     this.sucursal = sucursal;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public Empresa getProveedor()
/* 338:    */   {
/* 339:377 */     return this.proveedor;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setProveedor(Empresa proveedor)
/* 343:    */   {
/* 344:381 */     this.proveedor = proveedor;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public List<PedidoProveedor> getListaPedidoProveedor()
/* 348:    */   {
/* 349:385 */     return this.listaPedidoProveedor;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void setListaPedidoProveedor(List<PedidoProveedor> listaPedidoProveedor)
/* 353:    */   {
/* 354:389 */     this.listaPedidoProveedor = listaPedidoProveedor;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public Estado getEstado()
/* 358:    */   {
/* 359:393 */     return this.estado;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setEstado(Estado estado)
/* 363:    */   {
/* 364:397 */     this.estado = estado;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public ListDataModel<Object[]> getListaResumen()
/* 368:    */   {
/* 369:406 */     return this.listaResumen;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setListaResumen(ListDataModel<Object[]> listaResumen)
/* 373:    */   {
/* 374:416 */     this.listaResumen = listaResumen;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public BigDecimal getTotalAcumuladoAnio()
/* 378:    */   {
/* 379:425 */     return this.totalAcumuladoAnio;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setTotalAcumuladoAnio(BigDecimal totalAcumuladoAnio)
/* 383:    */   {
/* 384:435 */     this.totalAcumuladoAnio = totalAcumuladoAnio;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public BigDecimal getTotalAcumuladoMes()
/* 388:    */   {
/* 389:444 */     return this.totalAcumuladoMes;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setTotalAcumuladoMes(BigDecimal totalAcumuladoMes)
/* 393:    */   {
/* 394:454 */     this.totalAcumuladoMes = totalAcumuladoMes;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public BigDecimal getTotalPorAprobar()
/* 398:    */   {
/* 399:463 */     return this.totalPorAprobar;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setTotalPorAprobar(BigDecimal totalPorAprobar)
/* 403:    */   {
/* 404:473 */     this.totalPorAprobar = totalPorAprobar;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public List<Documento> getListaDocumentoProveedor()
/* 408:    */   {
/* 409:    */     try
/* 410:    */     {
/* 411:483 */       if (this.listaDocumentoProveedor == null) {
/* 412:484 */         this.listaDocumentoProveedor = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PEDIDO_PROVEEDOR);
/* 413:    */       }
/* 414:    */     }
/* 415:    */     catch (ExcepcionAS2 e)
/* 416:    */     {
/* 417:487 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 418:    */     }
/* 419:489 */     return this.listaDocumentoProveedor;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void setListaDocumentoProveedor(List<Documento> listaDocumentoProveedor)
/* 423:    */   {
/* 424:499 */     this.listaDocumentoProveedor = listaDocumentoProveedor;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public Documento getDocumento()
/* 428:    */   {
/* 429:508 */     return this.documento;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void setDocumento(Documento documento)
/* 433:    */   {
/* 434:518 */     this.documento = documento;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public SelectItem[] getListaEstadoItem()
/* 438:    */   {
/* 439:523 */     List<SelectItem> lista = new ArrayList();
/* 440:    */     
/* 441:    */ 
/* 442:526 */     this.listaEstadoItem = super.getListaEstadoItem();
/* 443:527 */     lista = Arrays.asList(this.listaEstadoItem);
/* 444:528 */     for (SelectItem si : lista) {
/* 445:529 */       if (si.getValue().equals(Estado.ANULADO)) {
/* 446:530 */         si.setDisabled(true);
/* 447:531 */       } else if ((si.getValue().equals(Estado.APROBADO)) && (!this.indicadorAprobarCompleto)) {
/* 448:532 */         si.setDisabled(true);
/* 449:533 */       } else if ((si.getValue().equals(Estado.APROBADO_PARCIAL)) && (this.indicadorAprobarCompleto)) {
/* 450:534 */         si.setDisabled(true);
/* 451:    */       }
/* 452:    */     }
/* 453:542 */     this.listaEstadoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 454:543 */     Arrays.sort(this.listaEstadoItem, new SelectItemComparator());
/* 455:544 */     return this.listaEstadoItem;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void setListaEstadoItem(SelectItem[] listaEstadoItem)
/* 459:    */   {
/* 460:549 */     this.listaEstadoItem = listaEstadoItem;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public boolean isCargarPedidosAprobados()
/* 464:    */   {
/* 465:553 */     return this.cargarPedidosAprobados;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public void setCargarPedidosAprobados(boolean cargarPedidosAprobados)
/* 469:    */   {
/* 470:557 */     this.cargarPedidosAprobados = cargarPedidosAprobados;
/* 471:558 */     if (cargarPedidosAprobados) {
/* 472:559 */       this.indicadorMostrarTodoAprobacion = false;
/* 473:    */     }
/* 474:    */   }
/* 475:    */   
/* 476:    */   public boolean isIndicadorMostrarTodoAprobacion()
/* 477:    */   {
/* 478:564 */     return this.indicadorMostrarTodoAprobacion;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public void setIndicadorMostrarTodoAprobacion(boolean indicadorMostrarTodoAprobacion)
/* 482:    */   {
/* 483:568 */     this.indicadorMostrarTodoAprobacion = indicadorMostrarTodoAprobacion;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public Boolean getIndicadorParametroEditarPrecioPedido()
/* 487:    */   {
/* 488:572 */     if (this.indicadorParametroEditarPrecioPedido == null) {
/* 489:573 */       this.indicadorParametroEditarPrecioPedido = ParametrosSistema.getEditarPrecioFacturaPedidoProveedor(AppUtil.getOrganizacion().getId());
/* 490:    */     }
/* 491:575 */     return this.indicadorParametroEditarPrecioPedido;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 495:    */   {
/* 496:579 */     return this.categoriaEmpresa;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 500:    */   {
/* 501:583 */     this.categoriaEmpresa = categoriaEmpresa;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public List<CategoriaEmpresa> autocompletarCategoriaEmpresa(String consulta)
/* 505:    */   {
/* 506:587 */     List<CategoriaEmpresa> lista = new ArrayList();
/* 507:588 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 508:589 */     filters.put("activo", "true");
/* 509:590 */     if ((consulta != null) && (!consulta.isEmpty())) {
/* 510:591 */       filters.put("nombre", consulta.trim());
/* 511:    */     }
/* 512:593 */     lista = this.servicioCategoriEmpresa.obtenerListaCombo("nombre", true, filters);
/* 513:594 */     return lista;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public boolean isSeleccionarTodos()
/* 517:    */   {
/* 518:598 */     return this.seleccionarTodos;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public void setSeleccionarTodos(boolean seleccionarTodos)
/* 522:    */   {
/* 523:602 */     for (PedidoProveedor pedidoProveedorA : getListaPedidoProveedor()) {
/* 524:603 */       pedidoProveedorA.setTraSeleccionado(seleccionarTodos);
/* 525:    */     }
/* 526:605 */     this.seleccionarTodos = seleccionarTodos;
/* 527:    */   }
/* 528:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.AprobacionPedidoProveedorBean
 * JD-Core Version:    0.7.0.1
 */