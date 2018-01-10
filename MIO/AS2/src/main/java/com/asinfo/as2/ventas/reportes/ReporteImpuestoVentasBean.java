/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Canal;
/*   9:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  10:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  11:    */ import com.asinfo.as2.entities.Empresa;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.Producto;
/*  14:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  15:    */ import com.asinfo.as2.entities.Sucursal;
/*  16:    */ import com.asinfo.as2.entities.Zona;
/*  17:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  18:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  19:    */ import com.asinfo.as2.enumeraciones.TipoVentaEnum;
/*  20:    */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
/*  21:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  23:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  24:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*  25:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  26:    */ import com.asinfo.as2.util.AppUtil;
/*  27:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  28:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  29:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  30:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  31:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  32:    */ import java.io.IOException;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.Calendar;
/*  35:    */ import java.util.Date;
/*  36:    */ import java.util.HashMap;
/*  37:    */ import java.util.List;
/*  38:    */ import java.util.Map;
/*  39:    */ import javax.annotation.PostConstruct;
/*  40:    */ import javax.ejb.EJB;
/*  41:    */ import javax.faces.bean.ManagedBean;
/*  42:    */ import javax.faces.bean.ViewScoped;
/*  43:    */ import javax.faces.model.SelectItem;
/*  44:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  45:    */ import net.sf.jasperreports.engine.JRException;
/*  46:    */ import org.apache.log4j.Logger;
/*  47:    */ 
/*  48:    */ @ManagedBean
/*  49:    */ @ViewScoped
/*  50:    */ public class ReporteImpuestoVentasBean
/*  51:    */   extends AbstractInventarioReportBean
/*  52:    */ {
/*  53:    */   private static final long serialVersionUID = 1584134027790279238L;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioReporteVenta servicioReporteVenta;
/*  56:    */   @EJB
/*  57:    */   private transient ServicioEmpresa servicioEmpresa;
/*  58:    */   @EJB
/*  59:    */   private transient ServicioUsuario servicioUsuario;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioCanal servicioCanal;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioZona servicioZona;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioSucursal servicioSucursal;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioPuntoDeVenta ServicioPuntoDeVenta;
/*  68:    */   @EJB
/*  69:    */   private transient ServicioMotivoNotaCreditoCliente servicioMotivoNotaCreditoCliente;
/*  70:    */   @EJB
/*  71:    */   private transient ServicioCategoriaProducto servicioCategoriaProducto;
/*  72:    */   @EJB
/*  73:    */   private transient ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  74:    */   @EJB
/*  75:    */   private transient ServicioAtributo servicioAtributo;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioValorAtributo servicioValorAtributo;
/*  78:    */   @EJB
/*  79:    */   private transient ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  80:    */   private Empresa cliente;
/*  81:    */   private CategoriaEmpresa categoriaEmpresa;
/*  82:    */   private EntidadUsuario agenteComercial;
/*  83:    */   private Zona zona;
/*  84:    */   private Canal canal;
/*  85:    */   private boolean anuladas;
/*  86:    */   private CategoriaProducto categoriaProducto;
/*  87:    */   private Producto producto;
/*  88:    */   private SubcategoriaProducto subcategoriaProducto;
/*  89:    */   private Sucursal sucursal;
/*  90:    */   private Date fechaDesde;
/*  91:    */   private Date fechaHasta;
/*  92:113 */   private DocumentoBase documentoBase = DocumentoBase.FACTURA_CLIENTE;
/*  93:    */   private List<Empresa> listaClienteCombo;
/*  94:    */   private List<Zona> listaZonaCombo;
/*  95:    */   private List<Canal> listaCanalCombo;
/*  96:    */   private List<EntidadUsuario> listaVendedorCombo;
/*  97:    */   private List<DocumentoBase> listaDocumentoBase;
/*  98:    */   private List<SubcategoriaProducto> listaSubcategoriaProductos;
/*  99:    */   private List<CategoriaProducto> listaCategoriaProductos;
/* 100:    */   private List<Sucursal> listaSucursal;
/* 101:    */   private boolean indicadorResumen;
/* 102:126 */   private TipoVentaEnum tipoVenta = TipoVentaEnum.LOCAL;
/* 103:    */   
/* 104:    */   private static enum enumTipoReporte
/* 105:    */   {
/* 106:130 */     CLIENTE,  PRODUCTO;
/* 107:    */     
/* 108:    */     private enumTipoReporte() {}
/* 109:    */   }
/* 110:    */   
/* 111:133 */   private enumTipoReporte tipoReporte = enumTipoReporte.CLIENTE;
/* 112:    */   private List<SelectItem> listaTipoReporte;
/* 113:    */   
/* 114:    */   protected JRDataSource getJRDataSource()
/* 115:    */   {
/* 116:144 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 117:145 */     JRDataSource ds = null;
/* 118:    */     
/* 119:    */ 
/* 120:148 */     listaDatosReporte = this.servicioReporteVenta.getReporteImpuestoVenta(this.fechaDesde, this.fechaHasta, this.cliente, this.categoriaEmpresa, this.agenteComercial, this.anuladas, this.canal, this.zona, 
/* 121:149 */       AppUtil.getOrganizacion().getId(), this.documentoBase, this.categoriaProducto, this.subcategoriaProducto, this.producto, this.indicadorResumen, enumTipoReporte.CLIENTE
/* 122:150 */       .equals(this.tipoReporte), this.sucursal);
/* 123:    */     
/* 124:152 */     String[] fields = { "f_identificacion", "f_nombreFiscal", "f_nombreCategoriaEmpresa", "f_nombreCategoriaProducto", "f_nombreSubCategoriaProducto", "f_nombreProducto", "f_nombreZona", "f_codigoZona", "f_codigoCanal", "f_nombreCanal", "f_nombreAgenteComercial", "f_autorizacion", "f_codigoAcceso", "f_impuestoLinea", "f_nombreImpuesto", "f_fechaFactura", "f_numeroFactura", "f_detalleid", "f_motivoNotaCredito" };
/* 125:    */     
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:157 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 130:    */     
/* 131:159 */     return ds;
/* 132:    */   }
/* 133:    */   
/* 134:    */   @PostConstruct
/* 135:    */   public void init()
/* 136:    */   {
/* 137:164 */     Calendar calfechaDesde = Calendar.getInstance();
/* 138:165 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 139:166 */     this.fechaDesde = calfechaDesde.getTime();
/* 140:167 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected String getCompileFileName()
/* 144:    */   {
/* 145:177 */     if (enumTipoReporte.CLIENTE.equals(this.tipoReporte))
/* 146:    */     {
/* 147:178 */       if (this.indicadorResumen) {
/* 148:179 */         return "reporteImpuestoVentasCliente";
/* 149:    */       }
/* 150:181 */       return "reporteImpuestoVentasClienteFactura";
/* 151:    */     }
/* 152:184 */     if (this.indicadorResumen) {
/* 153:185 */       return "reporteImpuestoVentasProducto";
/* 154:    */     }
/* 155:187 */     return "reporteImpuestoVentasProductoFactura";
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected Map<String, Object> getReportParameters()
/* 159:    */   {
/* 160:199 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 161:200 */     reportParameters.put("ReportTitle", "Resumen de Ventas");
/* 162:201 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 163:202 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 164:203 */     reportParameters.put("Total", "Total");
/* 165:204 */     reportParameters.put("p_canal", getCanal() != null ? getCanal().getNombre() : "Todos");
/* 166:205 */     reportParameters.put("p_zona", getZona() != null ? getZona().getNombre() : "Todos");
/* 167:206 */     reportParameters.put("p_documentoBase", getDocumentoBase() != null ? getDocumentoBase().getNombre() : "Todos");
/* 168:207 */     reportParameters.put("p_sucursal", getSucursal() != null ? getSucursal().getNombre() : "Todos");
/* 169:208 */     return reportParameters;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void cargarListaSubcategoriaProducto()
/* 173:    */   {
/* 174:212 */     HashMap<String, String> filters = new HashMap();
/* 175:213 */     filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProducto.getId());
/* 176:214 */     this.listaSubcategoriaProductos = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String execute()
/* 180:    */   {
/* 181:    */     try
/* 182:    */     {
/* 183:224 */       validaDatos();
/* 184:225 */       super.prepareReport();
/* 185:    */     }
/* 186:    */     catch (JRException e)
/* 187:    */     {
/* 188:227 */       LOG.info("Error JRException");
/* 189:228 */       e.printStackTrace();
/* 190:229 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 191:    */     }
/* 192:    */     catch (IOException e)
/* 193:    */     {
/* 194:231 */       LOG.info("Error IOException");
/* 195:232 */       e.printStackTrace();
/* 196:233 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 197:    */     }
/* 198:236 */     return null;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void cargarProducto() {}
/* 202:    */   
/* 203:    */   public void validaDatos()
/* 204:    */   {
/* 205:244 */     if (this.fechaDesde == null) {
/* 206:245 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 207:    */     }
/* 208:247 */     if (this.fechaHasta == null) {
/* 209:248 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Date getFechaDesde()
/* 214:    */   {
/* 215:258 */     return this.fechaDesde;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setFechaDesde(Date fechaDesde)
/* 219:    */   {
/* 220:268 */     this.fechaDesde = fechaDesde;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Date getFechaHasta()
/* 224:    */   {
/* 225:277 */     return this.fechaHasta;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setFechaHasta(Date fechaHasta)
/* 229:    */   {
/* 230:287 */     this.fechaHasta = fechaHasta;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Empresa getCliente()
/* 234:    */   {
/* 235:291 */     return this.cliente;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setCliente(Empresa cliente)
/* 239:    */   {
/* 240:295 */     this.cliente = cliente;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public EntidadUsuario getAgenteComercial()
/* 244:    */   {
/* 245:299 */     return this.agenteComercial;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 249:    */   {
/* 250:303 */     this.agenteComercial = agenteComercial;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public Zona getZona()
/* 254:    */   {
/* 255:310 */     return this.zona;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setZona(Zona zona)
/* 259:    */   {
/* 260:318 */     this.zona = zona;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Canal getCanal()
/* 264:    */   {
/* 265:325 */     return this.canal;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setCanal(Canal canal)
/* 269:    */   {
/* 270:333 */     this.canal = canal;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public boolean isIndicadorResumen()
/* 274:    */   {
/* 275:342 */     return this.indicadorResumen;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 279:    */   {
/* 280:352 */     this.indicadorResumen = indicadorResumen;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 284:    */   {
/* 285:356 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public List<Empresa> getListaClienteCombo()
/* 289:    */   {
/* 290:365 */     if (this.listaClienteCombo == null) {
/* 291:366 */       this.listaClienteCombo = this.servicioEmpresa.obtenerClientes();
/* 292:    */     }
/* 293:368 */     return this.listaClienteCombo;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setListaClienteCombo(List<Empresa> listaClienteCombo)
/* 297:    */   {
/* 298:378 */     this.listaClienteCombo = listaClienteCombo;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List<Zona> getListaZonaCombo()
/* 302:    */   {
/* 303:387 */     if (this.listaZonaCombo == null) {
/* 304:388 */       this.listaZonaCombo = this.servicioZona.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 305:    */     }
/* 306:390 */     return this.listaZonaCombo;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setListaZonaCombo(List<Zona> listaZonaCombo)
/* 310:    */   {
/* 311:400 */     this.listaZonaCombo = listaZonaCombo;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public List<Canal> getListaCanalCombo()
/* 315:    */   {
/* 316:409 */     if (this.listaCanalCombo == null) {
/* 317:410 */       this.listaCanalCombo = this.servicioCanal.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 318:    */     }
/* 319:412 */     return this.listaCanalCombo;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setListaCanalCombo(List<Canal> listaCanalCombo)
/* 323:    */   {
/* 324:422 */     this.listaCanalCombo = listaCanalCombo;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public List<EntidadUsuario> getListaVendedorCombo()
/* 328:    */   {
/* 329:431 */     if (this.listaVendedorCombo == null) {
/* 330:432 */       this.listaVendedorCombo = this.servicioUsuario.obtenerListaAgenteComercial(AppUtil.getOrganizacion().getId());
/* 331:    */     }
/* 332:434 */     return this.listaVendedorCombo;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setListaVendedorCombo(List<EntidadUsuario> listaVendedorCombo)
/* 336:    */   {
/* 337:444 */     this.listaVendedorCombo = listaVendedorCombo;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public boolean isAnuladas()
/* 341:    */   {
/* 342:448 */     return this.anuladas;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setAnuladas(boolean anuladas)
/* 346:    */   {
/* 347:452 */     this.anuladas = anuladas;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public TipoVentaEnum getTipoVenta()
/* 351:    */   {
/* 352:461 */     return this.tipoVenta;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setTipoVenta(TipoVentaEnum tipoVenta)
/* 356:    */   {
/* 357:471 */     this.tipoVenta = tipoVenta;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public enumTipoReporte getTipoReporte()
/* 361:    */   {
/* 362:480 */     return this.tipoReporte;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setTipoReporte(enumTipoReporte tipoReporte)
/* 366:    */   {
/* 367:490 */     this.tipoReporte = tipoReporte;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public List<SelectItem> getListaTipoReporte()
/* 371:    */   {
/* 372:499 */     if (this.listaTipoReporte == null)
/* 373:    */     {
/* 374:500 */       this.listaTipoReporte = new ArrayList();
/* 375:501 */       for (enumTipoReporte tr : enumTipoReporte.values()) {
/* 376:502 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 377:    */       }
/* 378:    */     }
/* 379:505 */     return this.listaTipoReporte;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 383:    */   {
/* 384:515 */     this.listaTipoReporte = listaTipoReporte;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public DocumentoBase getDocumentoBase()
/* 388:    */   {
/* 389:524 */     return this.documentoBase;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 393:    */   {
/* 394:534 */     this.documentoBase = documentoBase;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public List<DocumentoBase> getListaDocumentoBase()
/* 398:    */   {
/* 399:543 */     if (this.listaDocumentoBase == null)
/* 400:    */     {
/* 401:544 */       this.listaDocumentoBase = new ArrayList();
/* 402:545 */       this.listaDocumentoBase.add(DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 403:546 */       this.listaDocumentoBase.add(DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 404:547 */       this.listaDocumentoBase.add(DocumentoBase.FACTURA_CLIENTE);
/* 405:    */     }
/* 406:549 */     return this.listaDocumentoBase;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public void setListaDocumentoBase(List<DocumentoBase> listaDocumentoBase)
/* 410:    */   {
/* 411:559 */     this.listaDocumentoBase = listaDocumentoBase;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public boolean isProductoResumen()
/* 415:    */   {
/* 416:563 */     return (this.tipoReporte.equals(enumTipoReporte.PRODUCTO)) && (this.indicadorResumen);
/* 417:    */   }
/* 418:    */   
/* 419:    */   public CategoriaProducto getCategoriaProducto()
/* 420:    */   {
/* 421:567 */     return this.categoriaProducto;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 425:    */   {
/* 426:571 */     this.categoriaProducto = categoriaProducto;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 430:    */   {
/* 431:575 */     return this.subcategoriaProducto;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 435:    */   {
/* 436:579 */     this.subcategoriaProducto = subcategoriaProducto;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 440:    */   {
/* 441:583 */     if (this.listaCategoriaProductos == null) {
/* 442:584 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 443:    */     }
/* 444:586 */     return this.listaCategoriaProductos;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public List<SubcategoriaProducto> getListaSubcategoriaProductos()
/* 448:    */   {
/* 449:590 */     return this.listaSubcategoriaProductos;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public Producto getProducto()
/* 453:    */   {
/* 454:594 */     return this.producto;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public void setProducto(Producto producto)
/* 458:    */   {
/* 459:598 */     this.producto = producto;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 463:    */   {
/* 464:602 */     List<CategoriaEmpresa> listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 465:603 */     return listaCategoriaEmpresa;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 469:    */   {
/* 470:607 */     return this.categoriaEmpresa;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 474:    */   {
/* 475:611 */     this.categoriaEmpresa = categoriaEmpresa;
/* 476:    */   }
/* 477:    */   
/* 478:    */   public Sucursal getSucursal()
/* 479:    */   {
/* 480:615 */     return this.sucursal;
/* 481:    */   }
/* 482:    */   
/* 483:    */   public void setSucursal(Sucursal sucursal)
/* 484:    */   {
/* 485:619 */     this.sucursal = sucursal;
/* 486:    */   }
/* 487:    */   
/* 488:    */   public List<Sucursal> getListaSucursal()
/* 489:    */   {
/* 490:623 */     if (this.listaSucursal == null) {
/* 491:624 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 492:    */     }
/* 493:626 */     return this.listaSucursal;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 497:    */   {
/* 498:630 */     this.listaSucursal = listaSucursal;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public void actualizarZona()
/* 502:    */   {
/* 503:634 */     if (this.listaZonaCombo == null)
/* 504:    */     {
/* 505:635 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 506:636 */       if (this.sucursal != null) {
/* 507:637 */         filters.put("idSucursal", "=" + String.valueOf(this.sucursal.getIdSucursal()));
/* 508:    */       }
/* 509:639 */       this.listaZonaCombo = this.servicioZona.obtenerListaCombo("nombre", true, filters);
/* 510:    */     }
/* 511:    */   }
/* 512:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteImpuestoVentasBean
 * JD-Core Version:    0.7.0.1
 */