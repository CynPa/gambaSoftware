/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.Producto;
/*  13:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  19:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  20:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  21:    */ import com.asinfo.as2.util.AppUtil;
/*  22:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  23:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  24:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  25:    */ import java.io.IOException;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.Calendar;
/*  28:    */ import java.util.Date;
/*  29:    */ import java.util.HashMap;
/*  30:    */ import java.util.List;
/*  31:    */ import java.util.Map;
/*  32:    */ import javax.annotation.PostConstruct;
/*  33:    */ import javax.ejb.EJB;
/*  34:    */ import javax.faces.bean.ManagedBean;
/*  35:    */ import javax.faces.bean.ViewScoped;
/*  36:    */ import javax.faces.model.SelectItem;
/*  37:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  38:    */ import net.sf.jasperreports.engine.JRException;
/*  39:    */ import org.apache.log4j.Logger;
/*  40:    */ import org.primefaces.event.SelectEvent;
/*  41:    */ 
/*  42:    */ @ManagedBean
/*  43:    */ @ViewScoped
/*  44:    */ public class ReporteFacturacionResumidoProveedorBean
/*  45:    */   extends AbstractBaseReportBean
/*  46:    */ {
/*  47:    */   private static final long serialVersionUID = 3615647209557356681L;
/*  48:    */   @EJB
/*  49:    */   private ServicioEmpresa servicioEmpresa;
/*  50:    */   @EJB
/*  51:    */   private ServicioReporteCompra servicioReporteCompra;
/*  52:    */   @EJB
/*  53:    */   private ServicioDocumento servicioDocumento;
/*  54:    */   @EJB
/*  55:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  56:    */   @EJB
/*  57:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  58:    */   @EJB
/*  59:    */   private ServicioProducto servicioProducto;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioSucursal servicioSucursal;
/*  62:    */   private Empresa empresa;
/*  63:    */   private CategoriaProducto categoriaProducto;
/*  64:    */   private SubcategoriaProducto subcategoriaProducto;
/*  65:    */   private Date fechaDesde;
/*  66:    */   private Date fechaHasta;
/*  67:    */   private String numeroFacturaDesde;
/*  68:    */   private String numeroFacturaHasta;
/*  69:    */   private Documento documento;
/*  70:103 */   private boolean saldoInicial = false;
/*  71:104 */   private boolean creditoTributario = true;
/*  72:105 */   private boolean indicadorResumido = false;
/*  73:    */   private List<Documento> listaDocumento;
/*  74:    */   private List<Producto> listaProductosSeleccionados;
/*  75:    */   private Sucursal sucursal;
/*  76:    */   private DocumentoBase documentoBase;
/*  77:    */   private List<Sucursal> listaSucursal;
/*  78:    */   private List<DocumentoBase> listaDocumentoBase;
/*  79:    */   
/*  80:    */   private static enum EnumTipoCreditoTributario
/*  81:    */   {
/*  82:120 */     TODOS("Todos"),  CREDITO_TRIBUTARIO("Credito Tributario"),  SIN_CREDITO_TRIBUTARIO("Sin Credito Tributario");
/*  83:    */     
/*  84:    */     private String nombre;
/*  85:    */     
/*  86:    */     private EnumTipoCreditoTributario(String nombre)
/*  87:    */     {
/*  88:128 */       this.nombre = nombre;
/*  89:    */     }
/*  90:    */     
/*  91:    */     public String getNombre()
/*  92:    */     {
/*  93:137 */       return this.nombre;
/*  94:    */     }
/*  95:    */     
/*  96:    */     public void setNombre(String nombre)
/*  97:    */     {
/*  98:148 */       this.nombre = nombre;
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void actualizarCategoriaProducto(SelectEvent event)
/* 103:    */   {
/* 104:159 */     setCategoriaProducto((CategoriaProducto)event.getObject());
/* 105:    */   }
/* 106:    */   
/* 107:162 */   private EnumTipoCreditoTributario enumtipocreditoTributario = EnumTipoCreditoTributario.TODOS;
/* 108:    */   private List<SelectItem> listaTipoCreditoTributario;
/* 109:    */   
/* 110:    */   protected JRDataSource getJRDataSource()
/* 111:    */   {
/* 112:174 */     List listaDatosReporte = new ArrayList();
/* 113:175 */     JRDataSource ds = null;
/* 114:    */     try
/* 115:    */     {
/* 116:178 */       listaDatosReporte = this.servicioReporteCompra.getListaReporteFacturacionResumidoCompra(this.fechaDesde, this.fechaHasta, 
/* 117:179 */         getNumeroFacturaDesde(), getNumeroFacturaHasta(), getEmpresa().getIdEmpresa(), getDocumento()
/* 118:180 */         .getId(), this.saldoInicial, AppUtil.getOrganizacion().getId(), getEnumtipocreditoTributario()
/* 119:181 */         .ordinal(), this.indicadorResumido, this.categoriaProducto, this.subcategoriaProducto, this.listaProductosSeleccionados, 
/* 120:182 */         getSucursal(), this.documentoBase);
/* 121:    */       String[] fields;
/* 122:    */       String[] fields;
/* 123:185 */       if (this.indicadorResumido) {
/* 124:186 */         fields = new String[] { "f_numeroFactura", "f_fechaFactura", "f_identificacionEmpresa", "f_empresa", "f_subTotal", "f_descuento", "f_impuestos", "f_descripcion", "f_retencionComercializadora", "f_numeroCompra", "f_codigoEmpresa", "f_nombreCategoriaEmpresa", "f_operacion", "f_SRIfechaEmision", "f_SRIautorizacion", "f_SRIclaveAcceso", "f_SRIbaseImponibleTarifaCero", "f_SRIbaseImponibleDiferenteCero", "f_SRIbaseImponibleNoObjetoIva", "f_SRIautorizacionRetencion", "f_SRIclaveAccesoRetencion", "f_descuentoSolidario", "f_descripcionFactura" };
/* 125:    */       } else {
/* 126:191 */         fields = new String[] { "f_numeroCompra", "f_numeroFactura", "f_fechaFactura", "f_codigoEmpresa", "f_identificacionEmpresa", "f_nombreFiscalEmpresa", "f_nombreComercialEmpresa", "f_codigoProducto", "f_codigoBarras", "f_nombreProducto", "f_nombreComercialProducto", "f_cantidad", "f_precio", "f_descuento", "f_descripcion", "f_operacion", "f_descripcionFactura", "f_precioReferencial" };
/* 127:    */       }
/* 128:196 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 129:    */     }
/* 130:    */     catch (ExcepcionAS2 e)
/* 131:    */     {
/* 132:198 */       LOG.info("Error " + e);
/* 133:199 */       e.printStackTrace();
/* 134:    */     }
/* 135:201 */     return ds;
/* 136:    */   }
/* 137:    */   
/* 138:    */   @PostConstruct
/* 139:    */   public void init()
/* 140:    */   {
/* 141:206 */     Calendar calfechaDesde = Calendar.getInstance();
/* 142:207 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 143:208 */     this.fechaDesde = calfechaDesde.getTime();
/* 144:209 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected String getCompileFileName()
/* 148:    */   {
/* 149:220 */     if (this.indicadorResumido) {
/* 150:221 */       return "reporteComprasResumido";
/* 151:    */     }
/* 152:223 */     return "reporteComprasDetallado";
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected Map<String, Object> getReportParameters()
/* 156:    */   {
/* 157:238 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 158:239 */     if (this.indicadorResumido) {
/* 159:240 */       reportParameters.put("ReportTitle", "Compras Resumido");
/* 160:    */     } else {
/* 161:242 */       reportParameters.put("ReportTitle", "Compras Detallado");
/* 162:    */     }
/* 163:244 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 164:245 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 165:246 */     reportParameters.put("NumeroDesde", this.numeroFacturaDesde);
/* 166:247 */     reportParameters.put("NumeroHasta", this.numeroFacturaHasta);
/* 167:248 */     reportParameters.put("documento", getDocumento().getNombre());
/* 168:249 */     reportParameters.put("Total", "Total");
/* 169:250 */     reportParameters.put("indicadorFecha", Boolean.valueOf(true));
/* 170:251 */     reportParameters.put("indicadorCajaChica", Boolean.valueOf(false));
/* 171:252 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/* 172:253 */     reportParameters.put("p_proveedor", getEmpresa().getId() > 0 ? this.empresa.getNombreComercial() : "Todos");
/* 173:254 */     reportParameters.put("p_documento", getDocumento().getId() > 0 ? this.documento.getNombre() : "Todos");
/* 174:255 */     reportParameters.put("p_documento_base", this.documentoBase != null ? this.documentoBase.getNombre() : "Todos");
/* 175:    */     
/* 176:257 */     return reportParameters;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String execute()
/* 180:    */   {
/* 181:    */     try
/* 182:    */     {
/* 183:268 */       if (this.fechaDesde == null) {
/* 184:269 */         this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 185:    */       }
/* 186:271 */       if (this.fechaHasta == null) {
/* 187:272 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 188:    */       }
/* 189:275 */       super.prepareReport();
/* 190:    */     }
/* 191:    */     catch (JRException e)
/* 192:    */     {
/* 193:277 */       LOG.info("Error JRException");
/* 194:278 */       e.printStackTrace();
/* 195:279 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 196:    */     }
/* 197:    */     catch (IOException e)
/* 198:    */     {
/* 199:281 */       LOG.info("Error IOException");
/* 200:282 */       e.printStackTrace();
/* 201:283 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 202:    */     }
/* 203:286 */     return null;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public List<Producto> autocompletarProducto(String filtro)
/* 207:    */   {
/* 208:291 */     List<Producto> lp = new ArrayList();
/* 209:292 */     Map<String, String> filtros = new HashMap();
/* 210:293 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 211:294 */     filtros.put("nombre", filtro);
/* 212:    */     try
/* 213:    */     {
/* 214:296 */       lp = this.servicioProducto.obtenerProductos("nombre", false, filtros);
/* 215:    */     }
/* 216:    */     catch (ExcepcionAS2Inventario e)
/* 217:    */     {
/* 218:298 */       e.printStackTrace();
/* 219:    */     }
/* 220:300 */     return lp;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Empresa getEmpresa()
/* 224:    */   {
/* 225:311 */     if (this.empresa == null) {
/* 226:312 */       this.empresa = new Empresa();
/* 227:    */     }
/* 228:314 */     return this.empresa;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setEmpresa(Empresa empresa)
/* 232:    */   {
/* 233:324 */     this.empresa = empresa;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Date getFechaDesde()
/* 237:    */   {
/* 238:333 */     return this.fechaDesde;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setFechaDesde(Date fechaDesde)
/* 242:    */   {
/* 243:343 */     this.fechaDesde = fechaDesde;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Date getFechaHasta()
/* 247:    */   {
/* 248:352 */     return this.fechaHasta;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setFechaHasta(Date fechaHasta)
/* 252:    */   {
/* 253:362 */     this.fechaHasta = fechaHasta;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public String getNumeroFacturaDesde()
/* 257:    */   {
/* 258:371 */     if (this.numeroFacturaDesde == null) {
/* 259:372 */       this.numeroFacturaDesde = "";
/* 260:    */     }
/* 261:374 */     return this.numeroFacturaDesde;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setNumeroFacturaDesde(String numeroFacturaDesde)
/* 265:    */   {
/* 266:384 */     this.numeroFacturaDesde = numeroFacturaDesde;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getNumeroFacturaHasta()
/* 270:    */   {
/* 271:393 */     if (this.numeroFacturaHasta == null) {
/* 272:394 */       this.numeroFacturaHasta = "";
/* 273:    */     }
/* 274:396 */     return this.numeroFacturaHasta;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setNumeroFacturaHasta(String numeroFacturaHasta)
/* 278:    */   {
/* 279:406 */     this.numeroFacturaHasta = numeroFacturaHasta;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 283:    */   {
/* 284:416 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/* 288:    */   {
/* 289:420 */     List<SubcategoriaProducto> lista = new ArrayList();
/* 290:    */     
/* 291:422 */     HashMap<String, String> filters = new HashMap();
/* 292:424 */     if (this.categoriaProducto != null) {
/* 293:425 */       filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProducto.getId());
/* 294:    */     }
/* 295:428 */     filters.put("nombre", "%" + consulta.trim());
/* 296:    */     
/* 297:430 */     lista = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 298:    */     
/* 299:432 */     return lista;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public List<CategoriaProducto> autocompletarCategoriaProducto(String consulta)
/* 303:    */   {
/* 304:436 */     List<CategoriaProducto> lista = new ArrayList();
/* 305:    */     
/* 306:438 */     HashMap<String, String> filters = new HashMap();
/* 307:439 */     filters.put("nombre", "%" + consulta.trim());
/* 308:    */     
/* 309:441 */     lista = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 310:    */     
/* 311:443 */     return lista;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public List<Documento> getListaDocumento()
/* 315:    */   {
/* 316:452 */     if (this.listaDocumento == null) {
/* 317:453 */       cargarListaDocumentos();
/* 318:    */     }
/* 319:455 */     return this.listaDocumento;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 323:    */   {
/* 324:465 */     this.listaDocumento = listaDocumento;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public Documento getDocumento()
/* 328:    */   {
/* 329:474 */     if (this.documento == null) {
/* 330:475 */       this.documento = new Documento();
/* 331:    */     }
/* 332:477 */     return this.documento;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setDocumento(Documento documento)
/* 336:    */   {
/* 337:487 */     this.documento = documento;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public boolean isSaldoInicial()
/* 341:    */   {
/* 342:494 */     return this.saldoInicial;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setSaldoInicial(boolean saldoInicial)
/* 346:    */   {
/* 347:502 */     this.saldoInicial = saldoInicial;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public boolean isCreditoTributario()
/* 351:    */   {
/* 352:511 */     return this.creditoTributario;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setCreditoTributario(boolean creditoTributario)
/* 356:    */   {
/* 357:521 */     this.creditoTributario = creditoTributario;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public EnumTipoCreditoTributario getEnumtipocreditoTributario()
/* 361:    */   {
/* 362:530 */     return this.enumtipocreditoTributario;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setEnumtipocreditoTributario(EnumTipoCreditoTributario enumtipocreditoTributario)
/* 366:    */   {
/* 367:540 */     this.enumtipocreditoTributario = enumtipocreditoTributario;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public List<SelectItem> getListaTipoCreditoTributario()
/* 371:    */   {
/* 372:549 */     if (this.listaTipoCreditoTributario == null)
/* 373:    */     {
/* 374:550 */       this.listaTipoCreditoTributario = new ArrayList();
/* 375:551 */       for (EnumTipoCreditoTributario enumTipoCreditoTributario : EnumTipoCreditoTributario.values())
/* 376:    */       {
/* 377:552 */         SelectItem selectItem = new SelectItem(enumTipoCreditoTributario, enumTipoCreditoTributario.getNombre());
/* 378:553 */         this.listaTipoCreditoTributario.add(selectItem);
/* 379:    */       }
/* 380:    */     }
/* 381:556 */     return this.listaTipoCreditoTributario;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setListaTipoCreditoTributario(List<SelectItem> listaTipoCreditoTributario)
/* 385:    */   {
/* 386:566 */     this.listaTipoCreditoTributario = listaTipoCreditoTributario;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public boolean isIndicadorResumido()
/* 390:    */   {
/* 391:570 */     return this.indicadorResumido;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setIndicadorResumido(boolean indicadorResumido)
/* 395:    */   {
/* 396:574 */     this.indicadorResumido = indicadorResumido;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public CategoriaProducto getCategoriaProducto()
/* 400:    */   {
/* 401:578 */     return this.categoriaProducto;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 405:    */   {
/* 406:582 */     this.categoriaProducto = categoriaProducto;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 410:    */   {
/* 411:586 */     return this.subcategoriaProducto;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 415:    */   {
/* 416:590 */     this.subcategoriaProducto = subcategoriaProducto;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public List<Producto> getListaProductosSeleccionados()
/* 420:    */   {
/* 421:594 */     return this.listaProductosSeleccionados;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setListaProductosSeleccionados(List<Producto> listaProductosSeleccionados)
/* 425:    */   {
/* 426:598 */     this.listaProductosSeleccionados = listaProductosSeleccionados;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public Sucursal getSucursal()
/* 430:    */   {
/* 431:605 */     return this.sucursal;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void setSucursal(Sucursal sucursal)
/* 435:    */   {
/* 436:612 */     this.sucursal = sucursal;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public List<Sucursal> getListaSucursal()
/* 440:    */   {
/* 441:619 */     if (this.listaSucursal == null)
/* 442:    */     {
/* 443:620 */       Map<String, String> filters = new HashMap();
/* 444:621 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 445:622 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 446:    */     }
/* 447:625 */     return this.listaSucursal;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 451:    */   {
/* 452:632 */     this.listaSucursal = listaSucursal;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public DocumentoBase getDocumentoBase()
/* 456:    */   {
/* 457:639 */     return this.documentoBase;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 461:    */   {
/* 462:646 */     this.documentoBase = documentoBase;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public List<DocumentoBase> getListaDocumentoBase()
/* 466:    */   {
/* 467:653 */     if (this.listaDocumentoBase == null)
/* 468:    */     {
/* 469:654 */       this.listaDocumentoBase = new ArrayList();
/* 470:655 */       this.listaDocumentoBase.add(DocumentoBase.FACTURA_PROVEEDOR);
/* 471:656 */       this.listaDocumentoBase.add(DocumentoBase.NOTA_CREDITO_PROVEEDOR);
/* 472:657 */       this.listaDocumentoBase.add(DocumentoBase.NOTA_DEBITO_PROVEEDOR);
/* 473:    */     }
/* 474:660 */     return this.listaDocumentoBase;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public void setListaDocumentoBase(List<DocumentoBase> listaDocumentoBase)
/* 478:    */   {
/* 479:667 */     this.listaDocumentoBase = listaDocumentoBase;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void cargarListaDocumentos()
/* 483:    */   {
/* 484:674 */     this.listaDocumento = new ArrayList();
/* 485:    */     try
/* 486:    */     {
/* 487:676 */       if (this.documentoBase == null)
/* 488:    */       {
/* 489:677 */         for (DocumentoBase db : getListaDocumentoBase()) {
/* 490:678 */           this.listaDocumento.addAll(this.servicioDocumento.buscarPorDocumentoBase(db));
/* 491:    */         }
/* 492:681 */         this.listaDocumento.addAll(this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.DEVOLUCION_PROVEEDOR));
/* 493:    */       }
/* 494:    */       else
/* 495:    */       {
/* 496:683 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(this.documentoBase);
/* 497:684 */         if (this.documentoBase.equals(DocumentoBase.NOTA_CREDITO_PROVEEDOR)) {
/* 498:685 */           this.listaDocumento.addAll(this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.DEVOLUCION_PROVEEDOR));
/* 499:    */         }
/* 500:    */       }
/* 501:    */     }
/* 502:    */     catch (ExcepcionAS2 e)
/* 503:    */     {
/* 504:689 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 505:    */     }
/* 506:    */   }
/* 507:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReporteFacturacionResumidoProveedorBean
 * JD-Core Version:    0.7.0.1
 */