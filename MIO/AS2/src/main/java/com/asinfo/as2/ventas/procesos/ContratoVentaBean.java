/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.dao.ContratoVentaDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Canal;
/*  10:    */ import com.asinfo.as2.entities.Cliente;
/*  11:    */ import com.asinfo.as2.entities.CondicionPago;
/*  12:    */ import com.asinfo.as2.entities.ContratoVenta;
/*  13:    */ import com.asinfo.as2.entities.ContratoVentaFacturaContratoVenta;
/*  14:    */ import com.asinfo.as2.entities.DetalleContratoVenta;
/*  15:    */ import com.asinfo.as2.entities.DetalleValoresContratoVenta;
/*  16:    */ import com.asinfo.as2.entities.DetallesFacturaContratoVenta;
/*  17:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  18:    */ import com.asinfo.as2.entities.Documento;
/*  19:    */ import com.asinfo.as2.entities.Empresa;
/*  20:    */ import com.asinfo.as2.entities.Organizacion;
/*  21:    */ import com.asinfo.as2.entities.Producto;
/*  22:    */ import com.asinfo.as2.entities.Secuencia;
/*  23:    */ import com.asinfo.as2.entities.Subempresa;
/*  24:    */ import com.asinfo.as2.entities.Sucursal;
/*  25:    */ import com.asinfo.as2.entities.Zona;
/*  26:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  27:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  28:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  29:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  30:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  31:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  32:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  33:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  34:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  35:    */ import com.asinfo.as2.util.AppUtil;
/*  36:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  37:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  38:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  39:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioContratoVenta;
/*  40:    */ import java.math.BigDecimal;
/*  41:    */ import java.util.ArrayList;
/*  42:    */ import java.util.Date;
/*  43:    */ import java.util.HashMap;
/*  44:    */ import java.util.List;
/*  45:    */ import java.util.Map;
/*  46:    */ import javax.annotation.PostConstruct;
/*  47:    */ import javax.ejb.EJB;
/*  48:    */ import javax.faces.bean.ManagedBean;
/*  49:    */ import javax.faces.bean.ManagedProperty;
/*  50:    */ import javax.faces.bean.ViewScoped;
/*  51:    */ import javax.faces.component.html.HtmlInputText;
/*  52:    */ import javax.faces.component.html.HtmlSelectOneMenu;
/*  53:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  54:    */ import org.apache.log4j.Logger;
/*  55:    */ import org.primefaces.component.datatable.DataTable;
/*  56:    */ import org.primefaces.event.SelectEvent;
/*  57:    */ import org.primefaces.model.LazyDataModel;
/*  58:    */ import org.primefaces.model.SortOrder;
/*  59:    */ 
/*  60:    */ @ManagedBean
/*  61:    */ @ViewScoped
/*  62:    */ public class ContratoVentaBean
/*  63:    */   extends PageControllerAS2
/*  64:    */ {
/*  65:    */   private static final long serialVersionUID = 1L;
/*  66:    */   @EJB
/*  67:    */   private ServicioEmpresa servicioEmpresa;
/*  68:    */   @EJB
/*  69:    */   private ServicioContratoVenta servicioContratoVenta;
/*  70:    */   @EJB
/*  71:    */   private ContratoVentaDao contratoVentaDao;
/*  72:    */   @EJB
/*  73:    */   private ServicioProducto servicioProducto;
/*  74:    */   @EJB
/*  75:    */   protected transient ServicioUnidadConversion servicioUnidadConversion;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioDocumento servicioDocumento;
/*  78:    */   @EJB
/*  79:    */   protected ServicioCondicionPago servicioCondicionPago;
/*  80:    */   @EJB
/*  81:    */   protected transient ServicioZona servicioZona;
/*  82:    */   @EJB
/*  83:    */   protected transient ServicioCanal servicioCanal;
/*  84:    */   @EJB
/*  85:    */   protected ServicioUsuario servicioUsuario;
/*  86:    */   @ManagedProperty("#{listaProductoBean}")
/*  87:    */   private ListaProductoBean listaProductoBean;
/*  88:    */   private ContratoVenta contratoVenta;
/*  89:    */   private DetallesFacturaContratoVenta detallesFacturaContratoVenta;
/*  90:    */   private DataTable dtContratoVenta;
/*  91:    */   private DataTable dtDetalleContratoVenta;
/*  92:    */   private DataTable dtDetallesFacturaContratoVenta;
/*  93:    */   private DataTable dtDetalleValoresContratoVenta;
/*  94:    */   private LazyDataModel<ContratoVenta> listaContratoVenta;
/*  95:    */   private List<ContratoVentaFacturaContratoVenta> listaContratoVentaFacturaContratoVenta;
/*  96:    */   private List<Documento> listaDocumento;
/*  97:    */   private boolean indicadorEditarValores;
/*  98:    */   private List<CondicionPago> listaCondicionPago;
/*  99:    */   private List<Subempresa> listaSubempresa;
/* 100:    */   private List<DireccionEmpresa> listaDireccionEmpresa;
/* 101:    */   private List<Zona> listaZona;
/* 102:    */   private List<Canal> listaCanal;
/* 103:    */   private List<EntidadUsuario> listaAgenteComercialCombo;
/* 104:    */   private Integer idContratoVenta;
/* 105:    */   
/* 106:    */   @PostConstruct
/* 107:    */   public void init()
/* 108:    */   {
/* 109:132 */     getListaProductoBean().setIndicadorVenta(true);
/* 110:133 */     getListaProductoBean().setActivo(true);
/* 111:    */     
/* 112:135 */     this.listaContratoVenta = new LazyDataModel()
/* 113:    */     {
/* 114:    */       private static final long serialVersionUID = 1L;
/* 115:    */       
/* 116:    */       public List<ContratoVenta> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 117:    */       {
/* 118:142 */         List<ContratoVenta> lista = new ArrayList();
/* 119:    */         
/* 120:144 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 121:146 */         if (ContratoVentaBean.this.idContratoVenta != null) {
/* 122:147 */           filters.put("idContratoVenta", ContratoVentaBean.this.idContratoVenta.toString());
/* 123:    */         }
/* 124:150 */         lista = ContratoVentaBean.this.servicioContratoVenta.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 125:    */         
/* 126:152 */         ContratoVentaBean.this.listaContratoVenta.setRowCount(ContratoVentaBean.this.servicioContratoVenta.contarPorCriterio(filters));
/* 127:153 */         return lista;
/* 128:    */       }
/* 129:    */     };
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String editar()
/* 133:    */   {
/* 134:164 */     if ((getContratoVenta() != null) && (getContratoVenta().getId() != 0)) {
/* 135:    */       try
/* 136:    */       {
/* 137:166 */         this.servicioContratoVenta.esEditable(this.contratoVenta);
/* 138:167 */         this.contratoVenta = this.servicioContratoVenta.cargarDetalle(getContratoVenta());
/* 139:168 */         cargarDirecciones(this.contratoVenta.getEmpresa());
/* 140:169 */         cargarSubempresas();
/* 141:170 */         setEditado(true);
/* 142:    */       }
/* 143:    */       catch (ExcepcionAS2 e)
/* 144:    */       {
/* 145:172 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 146:    */       }
/* 147:    */     } else {
/* 148:175 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 149:    */     }
/* 150:178 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String guardar()
/* 154:    */   {
/* 155:    */     try
/* 156:    */     {
/* 157:187 */       getContratoVenta().setEstado(Estado.ELABORADO);
/* 158:188 */       this.servicioContratoVenta.guardar(getContratoVenta());
/* 159:189 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 160:    */       
/* 161:191 */       setEditado(false);
/* 162:    */       
/* 163:193 */       limpiar();
/* 164:    */     }
/* 165:    */     catch (ExcepcionAS2 e)
/* 166:    */     {
/* 167:196 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 168:197 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 169:    */     }
/* 170:    */     catch (Exception e)
/* 171:    */     {
/* 172:199 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 173:200 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 174:    */     }
/* 175:202 */     return "";
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String copiar()
/* 179:    */   {
/* 180:    */     try
/* 181:    */     {
/* 182:211 */       ContratoVenta auxContratoVenta = this.servicioContratoVenta.cargarDetalle(this.contratoVenta);
/* 183:212 */       this.contratoVenta = this.servicioContratoVenta.copiarContratoVenta(auxContratoVenta);
/* 184:    */       
/* 185:214 */       cargarDirecciones(this.contratoVenta.getEmpresa());
/* 186:215 */       cargarSubempresas();
/* 187:216 */       setEditado(true);
/* 188:    */     }
/* 189:    */     catch (ExcepcionAS2Financiero e)
/* 190:    */     {
/* 191:219 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 192:    */     }
/* 193:    */     catch (ExcepcionAS2 e)
/* 194:    */     {
/* 195:221 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 196:    */     }
/* 197:224 */     return "";
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String eliminar()
/* 201:    */   {
/* 202:    */     try
/* 203:    */     {
/* 204:233 */       anularContratoVenta();
/* 205:234 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 206:    */     }
/* 207:    */     catch (Exception e)
/* 208:    */     {
/* 209:236 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 210:237 */       LOG.error("ERROR AL ANULAR DATOS", e);
/* 211:    */     }
/* 212:239 */     return "";
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String cerrar()
/* 216:    */   {
/* 217:243 */     if ((getContratoVenta() != null) && (getContratoVenta().getId() != 0)) {
/* 218:    */       try
/* 219:    */       {
/* 220:245 */         this.contratoVenta.setEstado(Estado.CERRADO);
/* 221:246 */         this.contratoVenta.setIndicadorCerrado(true);
/* 222:247 */         this.contratoVentaDao.guardar(this.contratoVenta);
/* 223:248 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 224:    */       }
/* 225:    */       catch (Exception e)
/* 226:    */       {
/* 227:250 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 228:251 */         LOG.error("ERROR AL CERRAR CONTRATO", e);
/* 229:    */       }
/* 230:    */     } else {
/* 231:254 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 232:    */     }
/* 233:256 */     return "";
/* 234:    */   }
/* 235:    */   
/* 236:    */   public String limpiar()
/* 237:    */   {
/* 238:264 */     this.contratoVenta = new ContratoVenta();
/* 239:265 */     this.contratoVenta.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 240:266 */     this.contratoVenta.setSucursal(AppUtil.getSucursal());
/* 241:267 */     this.contratoVenta.setListaDetalleContratoVenta(new ArrayList());
/* 242:268 */     Date fechaInicio = FuncionesUtiles.getFechaInicioMes(new Date());
/* 243:269 */     Date fechaFin = FuncionesUtiles.sumarFechaAnios(fechaInicio, 1);
/* 244:270 */     fechaFin = FuncionesUtiles.sumarFechaDiasMeses(fechaFin, -1);
/* 245:271 */     this.contratoVenta.setFecha(fechaInicio);
/* 246:    */     
/* 247:273 */     this.contratoVenta.setFechaVencimiento(fechaFin);
/* 248:274 */     this.contratoVenta.setNumeroCuotas(1);
/* 249:    */     Documento documento;
/* 250:277 */     if (getListaDocumento().size() == 0)
/* 251:    */     {
/* 252:278 */       Documento documento = new Documento();
/* 253:279 */       documento.setSecuencia(new Secuencia());
/* 254:    */     }
/* 255:    */     else
/* 256:    */     {
/* 257:281 */       documento = (Documento)getListaDocumento().get(0);
/* 258:    */     }
/* 259:283 */     this.contratoVenta.setDocumento(documento);
/* 260:284 */     actualizarDocumento();
/* 261:285 */     return "";
/* 262:    */   }
/* 263:    */   
/* 264:    */   public String cargarDatos()
/* 265:    */   {
/* 266:293 */     return "";
/* 267:    */   }
/* 268:    */   
/* 269:    */   public ContratoVenta getContratoVenta()
/* 270:    */   {
/* 271:301 */     if (this.contratoVenta == null)
/* 272:    */     {
/* 273:302 */       this.contratoVenta = new ContratoVenta();
/* 274:303 */       this.contratoVenta.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 275:304 */       this.contratoVenta.setSucursal(AppUtil.getSucursal());
/* 276:305 */       this.contratoVenta.setFecha(new Date());
/* 277:306 */       this.contratoVenta.setFechaVencimiento(new Date());
/* 278:    */     }
/* 279:308 */     return this.contratoVenta;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setContratoVenta(ContratoVenta contratoVenta)
/* 283:    */   {
/* 284:316 */     this.contratoVenta = contratoVenta;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 288:    */   {
/* 289:325 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public List<DetalleContratoVenta> getDetalleContratoVenta()
/* 293:    */   {
/* 294:333 */     List<DetalleContratoVenta> detalle = new ArrayList();
/* 295:334 */     for (DetalleContratoVenta dcv : getContratoVenta().getListaDetalleContratoVenta()) {
/* 296:335 */       if (!dcv.isEliminado()) {
/* 297:336 */         detalle.add(dcv);
/* 298:    */       }
/* 299:    */     }
/* 300:339 */     return detalle;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public List<DetallesFacturaContratoVenta> getListaDetallesFacturaContratoVenta()
/* 304:    */   {
/* 305:347 */     List<DetallesFacturaContratoVenta> detalle = new ArrayList();
/* 306:348 */     for (DetallesFacturaContratoVenta dfcv : getContratoVenta().getListaDetallesFacturaContratoVenta()) {
/* 307:349 */       if (!dfcv.isEliminado()) {
/* 308:350 */         detalle.add(dfcv);
/* 309:    */       }
/* 310:    */     }
/* 311:353 */     return detalle;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public List<DetalleValoresContratoVenta> getDetalleValoresContratoVenta()
/* 315:    */   {
/* 316:361 */     List<DetalleValoresContratoVenta> detalle = new ArrayList();
/* 317:362 */     for (DetalleValoresContratoVenta dvcv : getContratoVenta().getListaDetalleValoresContratoVenta()) {
/* 318:363 */       if (!dvcv.isEliminado()) {
/* 319:364 */         detalle.add(dvcv);
/* 320:    */       }
/* 321:    */     }
/* 322:367 */     return detalle;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public DataTable getDtDetalleContratoVenta()
/* 326:    */   {
/* 327:375 */     return this.dtDetalleContratoVenta;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setDtDetalleContratoVenta(DataTable dtDetalleContratoVenta)
/* 331:    */   {
/* 332:383 */     this.dtDetalleContratoVenta = dtDetalleContratoVenta;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public String eliminarDetalle()
/* 336:    */   {
/* 337:391 */     DetalleContratoVenta detalleContratoVenta = (DetalleContratoVenta)this.dtDetalleContratoVenta.getRowData();
/* 338:392 */     detalleContratoVenta.setEliminado(true);
/* 339:    */     
/* 340:394 */     totalizar();
/* 341:    */     
/* 342:396 */     return "";
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void cargarDetallesFacturaContratoVnta()
/* 346:    */   {
/* 347:    */     try
/* 348:    */     {
/* 349:404 */       if ((getContratoVenta().getFecha() != null) && (getContratoVenta().getFechaVencimiento() != null)) {
/* 350:405 */         this.servicioContratoVenta.generarDetallesFacturaContratoVenta(getContratoVenta());
/* 351:    */       }
/* 352:    */     }
/* 353:    */     catch (ExcepcionAS2 e)
/* 354:    */     {
/* 355:408 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   public LazyDataModel<ContratoVenta> getListaContratoVenta()
/* 360:    */   {
/* 361:417 */     return this.listaContratoVenta;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setListaContratoVenta(LazyDataModel<ContratoVenta> listaContratoVenta)
/* 365:    */   {
/* 366:425 */     this.listaContratoVenta = listaContratoVenta;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public DataTable getDtContratoVenta()
/* 370:    */   {
/* 371:433 */     return this.dtContratoVenta;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setDtContratoVenta(DataTable dtContratoVenta)
/* 375:    */   {
/* 376:441 */     this.dtContratoVenta = dtContratoVenta;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public String agregarDetalle()
/* 380:    */   {
/* 381:448 */     getContratoVenta().getListaDetalleContratoVenta().add(creaDetalleContratoVenta());
/* 382:449 */     return "";
/* 383:    */   }
/* 384:    */   
/* 385:    */   public DetalleContratoVenta creaDetalleContratoVenta()
/* 386:    */   {
/* 387:457 */     DetalleContratoVenta detalleContratoVenta = new DetalleContratoVenta();
/* 388:458 */     detalleContratoVenta.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 389:459 */     detalleContratoVenta.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 390:460 */     detalleContratoVenta.setContratoVenta(getContratoVenta());
/* 391:461 */     detalleContratoVenta.setFechaDesde(new Date());
/* 392:462 */     detalleContratoVenta.setFechaHasta(new Date());
/* 393:463 */     detalleContratoVenta.setProducto(new Producto());
/* 394:464 */     detalleContratoVenta.setCantidad(BigDecimal.ZERO);
/* 395:465 */     detalleContratoVenta.setPrecio(BigDecimal.ZERO);
/* 396:466 */     detalleContratoVenta.setPrecioLinea(BigDecimal.ZERO);
/* 397:    */     
/* 398:468 */     return detalleContratoVenta;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void actualizarDetalleContratoVenta() {}
/* 402:    */   
/* 403:    */   public void actualizarProductoListener(AjaxBehaviorEvent event)
/* 404:    */   {
/* 405:482 */     DetalleContratoVenta dcv = (DetalleContratoVenta)this.dtDetalleContratoVenta.getRowData();
/* 406:483 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/* 407:484 */     Producto producto = null;
/* 408:    */     try
/* 409:    */     {
/* 410:486 */       producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.CONTRATO_VENTA);
/* 411:487 */       dcv.setProducto(producto);
/* 412:    */     }
/* 413:    */     catch (ExcepcionAS2 e)
/* 414:    */     {
/* 415:489 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 416:490 */       dcv.getProducto().setCodigo("");
/* 417:491 */       dcv.getProducto().setNombre("");
/* 418:    */     }
/* 419:    */   }
/* 420:    */   
/* 421:    */   public DetalleContratoVenta cargarProducto()
/* 422:    */   {
/* 423:501 */     DetalleContratoVenta detalleContratoVenta = null;
/* 424:    */     
/* 425:503 */     Producto producto = getListaProductoBean().getProducto();
/* 426:505 */     if (producto != null)
/* 427:    */     {
/* 428:    */       try
/* 429:    */       {
/* 430:507 */         this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 431:    */       }
/* 432:    */       catch (ExcepcionAS2 e)
/* 433:    */       {
/* 434:509 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 435:    */       }
/* 436:511 */       detalleContratoVenta = new DetalleContratoVenta();
/* 437:512 */       detalleContratoVenta.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 438:513 */       detalleContratoVenta.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 439:514 */       detalleContratoVenta.setContratoVenta(getContratoVenta());
/* 440:515 */       detalleContratoVenta.setFechaDesde(this.contratoVenta.getFecha());
/* 441:516 */       detalleContratoVenta.setFechaHasta(this.contratoVenta.getFechaVencimiento());
/* 442:517 */       detalleContratoVenta.setCantidad(producto.getTraCantidad());
/* 443:518 */       detalleContratoVenta.setProducto(producto);
/* 444:    */       
/* 445:520 */       ContratoVentaFacturaContratoVenta contratoVentaFacturaContratoVenta = new ContratoVentaFacturaContratoVenta();
/* 446:521 */       contratoVentaFacturaContratoVenta.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 447:522 */       contratoVentaFacturaContratoVenta.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 448:523 */       contratoVentaFacturaContratoVenta.setValor(detalleContratoVenta.getPrecioLinea());
/* 449:524 */       contratoVentaFacturaContratoVenta.setDetalleContratoVenta(detalleContratoVenta);
/* 450:    */       
/* 451:526 */       detalleContratoVenta.getListaContratoVentaFacturaContratoVenta().add(contratoVentaFacturaContratoVenta);
/* 452:527 */       getContratoVenta().getListaDetalleContratoVenta().add(detalleContratoVenta);
/* 453:    */     }
/* 454:531 */     getListaProductoBean().setProducto(null);
/* 455:532 */     return detalleContratoVenta;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void cargarProducto(Producto producto)
/* 459:    */   {
/* 460:539 */     getListaProductoBean().setProducto(producto);
/* 461:540 */     getListaProductoBean().setSaldoProductoLote(null);
/* 462:541 */     cargarProducto();
/* 463:    */   }
/* 464:    */   
/* 465:    */   public void actualizarTotalFacturaContratoVenta()
/* 466:    */   {
/* 467:548 */     BigDecimal suma = BigDecimal.ZERO;
/* 468:    */     
/* 469:550 */     Map<DetalleContratoVenta, BigDecimal> mapaValoresAsignados = new HashMap();
/* 470:551 */     for (DetallesFacturaContratoVenta dfcv : getListaDetallesFacturaContratoVenta()) {
/* 471:552 */       for (ContratoVentaFacturaContratoVenta cvfcv : dfcv.getListaContratoVentaFacturaContratoVenta())
/* 472:    */       {
/* 473:553 */         BigDecimal valorAsignado = BigDecimal.ZERO;
/* 474:554 */         if (mapaValoresAsignados.containsKey(cvfcv.getDetalleContratoVenta())) {
/* 475:555 */           valorAsignado = (BigDecimal)mapaValoresAsignados.get(cvfcv.getDetalleContratoVenta());
/* 476:    */         }
/* 477:557 */         valorAsignado = valorAsignado.add(cvfcv.getValor());
/* 478:558 */         mapaValoresAsignados.put(cvfcv.getDetalleContratoVenta(), valorAsignado);
/* 479:    */       }
/* 480:    */     }
/* 481:562 */     for (DetallesFacturaContratoVenta detalle : getListaDetallesFacturaContratoVenta()) {
/* 482:563 */       for (ContratoVentaFacturaContratoVenta cvfcv : detalle.getListaContratoVentaFacturaContratoVenta()) {
/* 483:564 */         cvfcv.setValorTotal(cvfcv.getDetalleContratoVenta().getPrecioLinea().subtract((BigDecimal)mapaValoresAsignados.get(cvfcv.getDetalleContratoVenta())));
/* 484:    */       }
/* 485:    */     }
/* 486:568 */     for (ContratoVentaFacturaContratoVenta cvfcv : this.listaContratoVentaFacturaContratoVenta) {
/* 487:569 */       suma = suma.add(cvfcv.getValor());
/* 488:    */     }
/* 489:572 */     this.detallesFacturaContratoVenta.setValor(suma);
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void seleccionarContratoVentaFacturaContratoVenta(DetallesFacturaContratoVenta detallesFacturaContratoVenta)
/* 493:    */   {
/* 494:579 */     this.listaContratoVentaFacturaContratoVenta = new ArrayList();
/* 495:580 */     for (ContratoVentaFacturaContratoVenta cvfcv : detallesFacturaContratoVenta.getListaContratoVentaFacturaContratoVenta()) {
/* 496:581 */       if ((cvfcv.getValorTotal().compareTo(BigDecimal.ZERO) > 0) || (cvfcv.getValor().compareTo(BigDecimal.ZERO) > 0)) {
/* 497:582 */         this.listaContratoVentaFacturaContratoVenta.add(cvfcv);
/* 498:    */       }
/* 499:    */     }
/* 500:585 */     this.detallesFacturaContratoVenta = detallesFacturaContratoVenta;
/* 501:586 */     actualizarTotalFacturaContratoVenta();
/* 502:    */   }
/* 503:    */   
/* 504:    */   public void totalizar()
/* 505:    */   {
/* 506:594 */     this.servicioContratoVenta.totalizar(getContratoVenta());
/* 507:595 */     cargarDetallesFacturaContratoVnta();
/* 508:    */   }
/* 509:    */   
/* 510:    */   public BigDecimal getTotalDetalleFacturaContratoVenta()
/* 511:    */   {
/* 512:602 */     BigDecimal total = BigDecimal.ZERO;
/* 513:603 */     for (DetallesFacturaContratoVenta dfcv : getContratoVenta().getListaDetallesFacturaContratoVenta()) {
/* 514:604 */       if (!dfcv.isEliminado()) {
/* 515:605 */         total = total.add(dfcv.getValor());
/* 516:    */       }
/* 517:    */     }
/* 518:610 */     return total;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public ListaProductoBean getListaProductoBean()
/* 522:    */   {
/* 523:617 */     return this.listaProductoBean;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 527:    */   {
/* 528:625 */     this.listaProductoBean = listaProductoBean;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public DataTable getDtDetallesFacturaContratoVenta()
/* 532:    */   {
/* 533:633 */     return this.dtDetallesFacturaContratoVenta;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public void setDtDetallesFacturaContratoVenta(DataTable dtDetallesFacturaContratoVenta)
/* 537:    */   {
/* 538:641 */     this.dtDetallesFacturaContratoVenta = dtDetallesFacturaContratoVenta;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public DataTable getDtDetalleValoresContratoVenta()
/* 542:    */   {
/* 543:649 */     return this.dtDetalleValoresContratoVenta;
/* 544:    */   }
/* 545:    */   
/* 546:    */   public void setDtDetalleValoresContratoVenta(DataTable dtDetalleValoresContratoVenta)
/* 547:    */   {
/* 548:657 */     this.dtDetalleValoresContratoVenta = dtDetalleValoresContratoVenta;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public List<ContratoVentaFacturaContratoVenta> getListaContratoVentaFacturaContratoVenta()
/* 552:    */   {
/* 553:664 */     return this.listaContratoVentaFacturaContratoVenta;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public void setDetallesFacturaContratoVenta(DetallesFacturaContratoVenta detallesFacturaContratoVenta)
/* 557:    */   {
/* 558:672 */     this.detallesFacturaContratoVenta = detallesFacturaContratoVenta;
/* 559:    */   }
/* 560:    */   
/* 561:    */   public DetallesFacturaContratoVenta getDetallesFacturaContratoVenta()
/* 562:    */   {
/* 563:680 */     return this.detallesFacturaContratoVenta;
/* 564:    */   }
/* 565:    */   
/* 566:    */   public List<Documento> getListaDocumento()
/* 567:    */   {
/* 568:    */     try
/* 569:    */     {
/* 570:689 */       if (this.listaDocumento == null) {
/* 571:690 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.CONTRATO_VENTA);
/* 572:    */       }
/* 573:    */     }
/* 574:    */     catch (ExcepcionAS2 e)
/* 575:    */     {
/* 576:693 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 577:    */     }
/* 578:696 */     return this.listaDocumento;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 582:    */   {
/* 583:704 */     this.listaDocumento = listaDocumento;
/* 584:    */   }
/* 585:    */   
/* 586:    */   public void actualizarDocumento()
/* 587:    */   {
/* 588:711 */     if (getContratoVenta().getFecha() == null) {
/* 589:712 */       getContratoVenta().setFecha(new Date());
/* 590:    */     }
/* 591:    */   }
/* 592:    */   
/* 593:    */   public BigDecimal getTotalDetalleValorContratoVenta()
/* 594:    */   {
/* 595:725 */     BigDecimal total = BigDecimal.ZERO;
/* 596:727 */     for (DetalleValoresContratoVenta dvcv : getContratoVenta().getListaDetalleValoresContratoVenta()) {
/* 597:728 */       if (!dvcv.isEliminado()) {
/* 598:729 */         total = total.add(dvcv.getValor());
/* 599:    */       }
/* 600:    */     }
/* 601:732 */     return total;
/* 602:    */   }
/* 603:    */   
/* 604:    */   public void anularContratoVenta()
/* 605:    */   {
/* 606:736 */     if ((getContratoVenta() != null) && (getContratoVenta().getId() != 0)) {
/* 607:    */       try
/* 608:    */       {
/* 609:738 */         this.servicioContratoVenta.esAnulable(this.contratoVenta);
/* 610:739 */         this.contratoVenta.setEstado(Estado.ANULADO);
/* 611:740 */         this.contratoVentaDao.guardar(this.contratoVenta);
/* 612:    */       }
/* 613:    */       catch (ExcepcionAS2 e)
/* 614:    */       {
/* 615:742 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 616:    */       }
/* 617:    */     } else {
/* 618:745 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 619:    */     }
/* 620:    */   }
/* 621:    */   
/* 622:    */   public void procesarSaldoInicial()
/* 623:    */   {
/* 624:752 */     this.contratoVenta = ((ContratoVenta)this.dtContratoVenta.getRowData());
/* 625:753 */     if ((this.contratoVenta.getEstado().equals(Estado.ELABORADO)) && (this.contratoVenta.isIndicadorSaldoInicial())) {
/* 626:    */       try
/* 627:    */       {
/* 628:755 */         this.servicioContratoVenta.procesarSaldoInicial(this.contratoVenta);
/* 629:    */       }
/* 630:    */       catch (Exception e)
/* 631:    */       {
/* 632:757 */         addInfoMessage(getLanguageController().getMensaje(e.getMessage()));
/* 633:    */       }
/* 634:    */     } else {
/* 635:760 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 636:    */     }
/* 637:    */   }
/* 638:    */   
/* 639:    */   public void actualizarSaldoInicial()
/* 640:    */   {
/* 641:765 */     if (this.contratoVenta.isIndicadorSaldoInicial())
/* 642:    */     {
/* 643:766 */       this.contratoVenta.setNumeroCuotas(0);
/* 644:767 */       for (DetallesFacturaContratoVenta detalle : this.contratoVenta.getListaDetallesFacturaContratoVenta()) {
/* 645:768 */         detalle.setEliminado(true);
/* 646:    */       }
/* 647:    */     }
/* 648:771 */     actualizarIndicadorEditarValores();
/* 649:    */   }
/* 650:    */   
/* 651:    */   public boolean isIndicadorEditarValores()
/* 652:    */   {
/* 653:775 */     return this.indicadorEditarValores;
/* 654:    */   }
/* 655:    */   
/* 656:    */   public void setIndicadorEditarValores(boolean indicadorEditarValores)
/* 657:    */   {
/* 658:779 */     this.indicadorEditarValores = indicadorEditarValores;
/* 659:    */   }
/* 660:    */   
/* 661:    */   public void actualizarIndicadorEditarValores()
/* 662:    */   {
/* 663:783 */     if ((this.contratoVenta.getCuotasAnteriores() > 0) || (this.contratoVenta.isIndicadorSaldoInicial())) {
/* 664:784 */       this.indicadorEditarValores = true;
/* 665:    */     } else {
/* 666:786 */       this.indicadorEditarValores = false;
/* 667:    */     }
/* 668:    */   }
/* 669:    */   
/* 670:    */   public List<CondicionPago> getListaCondicionPago()
/* 671:    */   {
/* 672:791 */     if (this.listaCondicionPago == null) {
/* 673:792 */       this.listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("", false, null);
/* 674:    */     }
/* 675:794 */     return this.listaCondicionPago;
/* 676:    */   }
/* 677:    */   
/* 678:    */   public void setListaCondicionPago(List<CondicionPago> listaCondicionPago)
/* 679:    */   {
/* 680:802 */     this.listaCondicionPago = listaCondicionPago;
/* 681:    */   }
/* 682:    */   
/* 683:    */   public List<Subempresa> getListaSubempresa()
/* 684:    */   {
/* 685:811 */     if (this.listaSubempresa == null) {
/* 686:812 */       this.listaSubempresa = new ArrayList();
/* 687:    */     }
/* 688:814 */     return this.listaSubempresa;
/* 689:    */   }
/* 690:    */   
/* 691:    */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 692:    */   {
/* 693:824 */     this.listaSubempresa = listaSubempresa;
/* 694:    */   }
/* 695:    */   
/* 696:    */   public void actualizarSubclienteListener(AjaxBehaviorEvent event)
/* 697:    */   {
/* 698:829 */     Subempresa subempresa = (Subempresa)((HtmlSelectOneMenu)event.getSource()).getValue();
/* 699:831 */     if (subempresa != null)
/* 700:    */     {
/* 701:832 */       Empresa empresa = subempresa.getEmpresa();
/* 702:    */       
/* 703:834 */       this.contratoVenta.setSubempresa(subempresa);
/* 704:835 */       this.contratoVenta.setDireccionEmpresa(null);
/* 705:    */       
/* 706:    */ 
/* 707:838 */       this.contratoVenta.setZona(empresa.getCliente().getZona());
/* 708:839 */       this.contratoVenta.setAgenteComercial(this.servicioUsuario.buscaAgenteComercialPorIdEmpresa(empresa.getId()));
/* 709:840 */       this.contratoVenta.setCondicionPago(empresa.getCliente().getCondicionPago());
/* 710:841 */       cargarDirecciones(empresa);
/* 711:    */     }
/* 712:    */   }
/* 713:    */   
/* 714:    */   public void cargarDirecciones(Empresa empresa)
/* 715:    */   {
/* 716:847 */     this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(empresa.getId());
/* 717:849 */     for (DireccionEmpresa de : this.listaDireccionEmpresa) {
/* 718:850 */       if (de.isIndicadorDireccionPrincipal())
/* 719:    */       {
/* 720:851 */         this.contratoVenta.setDireccionEmpresa(de);
/* 721:852 */         break;
/* 722:    */       }
/* 723:    */     }
/* 724:    */   }
/* 725:    */   
/* 726:    */   public List<Zona> getListaZona()
/* 727:    */   {
/* 728:863 */     if (this.listaZona == null) {
/* 729:864 */       this.listaZona = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 730:    */     }
/* 731:866 */     return this.listaZona;
/* 732:    */   }
/* 733:    */   
/* 734:    */   public void setListaZona(List<Zona> listaZona)
/* 735:    */   {
/* 736:876 */     this.listaZona = listaZona;
/* 737:    */   }
/* 738:    */   
/* 739:    */   public List<Canal> getListaCanal()
/* 740:    */   {
/* 741:885 */     if (this.listaCanal == null) {
/* 742:886 */       this.listaCanal = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/* 743:    */     }
/* 744:888 */     return this.listaCanal;
/* 745:    */   }
/* 746:    */   
/* 747:    */   public void setListaCanal(List<Canal> listaCanal)
/* 748:    */   {
/* 749:898 */     this.listaCanal = listaCanal;
/* 750:    */   }
/* 751:    */   
/* 752:    */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/* 753:    */   {
/* 754:907 */     if (this.listaDireccionEmpresa == null) {
/* 755:908 */       this.listaDireccionEmpresa = new ArrayList();
/* 756:    */     }
/* 757:910 */     return this.listaDireccionEmpresa;
/* 758:    */   }
/* 759:    */   
/* 760:    */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 761:    */   {
/* 762:920 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 763:    */   }
/* 764:    */   
/* 765:    */   public void actualizarClienteListener(SelectEvent event)
/* 766:    */   {
/* 767:925 */     Empresa empresa = (Empresa)event.getObject();
/* 768:926 */     actualizarCliente(empresa);
/* 769:    */   }
/* 770:    */   
/* 771:    */   public void actualizarCliente(Empresa empresa)
/* 772:    */   {
/* 773:933 */     if (empresa != null) {
/* 774:934 */       this.contratoVenta.setAgenteComercial(this.servicioUsuario.buscaAgenteComercialPorIdEmpresa(empresa.getId()));
/* 775:    */     }
/* 776:937 */     this.contratoVenta.setEmpresa(empresa);
/* 777:938 */     this.contratoVenta.setDireccionEmpresa(null);
/* 778:939 */     this.contratoVenta.setEmail(this.servicioEmpresa
/* 779:940 */       .cargarMails(empresa, DocumentoBase.FACTURA_CLIENTE));
/* 780:943 */     if (this.contratoVenta.getZona() == null) {
/* 781:944 */       this.contratoVenta.setZona(empresa.getCliente().getZona());
/* 782:    */     }
/* 783:947 */     if (this.contratoVenta.getCondicionPago() == null) {
/* 784:948 */       this.contratoVenta.setCondicionPago(empresa.getCliente().getCondicionPago());
/* 785:    */     }
/* 786:951 */     if (this.contratoVenta.getEmail() == null) {
/* 787:952 */       this.contratoVenta.setEmail(empresa.getEmail1());
/* 788:    */     }
/* 789:955 */     cargarDirecciones(empresa);
/* 790:    */     
/* 791:957 */     cargarSubempresas();
/* 792:    */   }
/* 793:    */   
/* 794:    */   public void cargarSubempresas()
/* 795:    */   {
/* 796:962 */     this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(this.contratoVenta.getEmpresa().getId());
/* 797:    */   }
/* 798:    */   
/* 799:    */   public List<EntidadUsuario> getListaAgenteComercialCombo()
/* 800:    */   {
/* 801:966 */     if (this.listaAgenteComercialCombo == null)
/* 802:    */     {
/* 803:967 */       this.listaAgenteComercialCombo = new ArrayList();
/* 804:968 */       this.listaAgenteComercialCombo = this.servicioUsuario.obtenerListaAgenteComercial(AppUtil.getOrganizacion().getId(), Boolean.valueOf(true));
/* 805:    */     }
/* 806:971 */     return this.listaAgenteComercialCombo;
/* 807:    */   }
/* 808:    */   
/* 809:    */   public Integer getIdContratoVenta()
/* 810:    */   {
/* 811:975 */     return this.idContratoVenta;
/* 812:    */   }
/* 813:    */   
/* 814:    */   public void setIdContratoVenta(Integer idContratoVenta)
/* 815:    */   {
/* 816:979 */     this.idContratoVenta = idContratoVenta;
/* 817:    */   }
/* 818:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.ContratoVentaBean
 * JD-Core Version:    0.7.0.1
 */