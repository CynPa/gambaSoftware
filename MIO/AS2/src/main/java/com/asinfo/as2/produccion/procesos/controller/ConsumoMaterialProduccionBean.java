/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  11:    */ import com.asinfo.as2.entities.Lote;
/*  12:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*  13:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  14:    */ import com.asinfo.as2.entities.Organizacion;
/*  15:    */ import com.asinfo.as2.entities.Producto;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  18:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  19:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  22:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioMotivoAjusteInventario;
/*  23:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  24:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  25:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  26:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  27:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  28:    */ import com.asinfo.as2.util.AppUtil;
/*  29:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  30:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  31:    */ import java.io.BufferedInputStream;
/*  32:    */ import java.io.InputStream;
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
/*  43:    */ import org.apache.log4j.Logger;
/*  44:    */ import org.primefaces.component.datatable.DataTable;
/*  45:    */ import org.primefaces.event.FileUploadEvent;
/*  46:    */ import org.primefaces.model.LazyDataModel;
/*  47:    */ import org.primefaces.model.SortOrder;
/*  48:    */ import org.primefaces.model.UploadedFile;
/*  49:    */ 
/*  50:    */ @ManagedBean
/*  51:    */ @ViewScoped
/*  52:    */ public class ConsumoMaterialProduccionBean
/*  53:    */   extends PageControllerAS2
/*  54:    */ {
/*  55:    */   private static final long serialVersionUID = 2722663566489603802L;
/*  56:    */   @EJB
/*  57:    */   private ServicioMovimientoInventario servicioMovimientoInventario;
/*  58:    */   @EJB
/*  59:    */   private ServicioProducto servicioProducto;
/*  60:    */   @EJB
/*  61:    */   private ServicioDocumento servicioDocumento;
/*  62:    */   @EJB
/*  63:    */   private ServicioMotivoAjusteInventario servicioMotivoAjusteInventario;
/*  64:    */   @EJB
/*  65:    */   private ServicioLote servicioLote;
/*  66:    */   @EJB
/*  67:    */   private ServicioUnidadConversion servicioUnidadConversion;
/*  68:    */   private MovimientoInventario ajusteInventario;
/*  69:    */   private LazyDataModel<MovimientoInventario> listaAjusteInventario;
/*  70:    */   private List<Documento> listaDocumentosAjuste;
/*  71:    */   private Producto[] productosSeleccionados;
/*  72:    */   private List<MotivoAjusteInventario> listaMotivoAjusteInventario;
/*  73:    */   private InventarioProducto inventarioProducto;
/*  74:    */   private DataTable dtListaAjuste;
/*  75:    */   private DataTable dtDetalleAjuste;
/*  76:    */   private DataTable dtInventarioProductoLote;
/*  77:    */   private List<Bodega> listaBodega;
/*  78:    */   private boolean mostrarSaldoInicial;
/*  79:    */   
/*  80:    */   @PostConstruct
/*  81:    */   public void init()
/*  82:    */   {
/*  83:106 */     crearAjuste();
/*  84:    */     
/*  85:108 */     this.listaAjusteInventario = new LazyDataModel()
/*  86:    */     {
/*  87:    */       private static final long serialVersionUID = 1L;
/*  88:    */       
/*  89:    */       public List<MovimientoInventario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  90:    */       {
/*  91:115 */         List<MovimientoInventario> lista = new ArrayList();
/*  92:    */         
/*  93:117 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  94:119 */         if (filters == null) {
/*  95:120 */           filters = new HashMap();
/*  96:    */         }
/*  97:123 */         filters.put("documento.documentoBase", DocumentoBase.AJUSTE_INVENTARIO.toString());
/*  98:124 */         lista.addAll(ConsumoMaterialProduccionBean.this.servicioMovimientoInventario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters));
/*  99:    */         
/* 100:126 */         ConsumoMaterialProduccionBean.this.listaAjusteInventario.setRowCount(ConsumoMaterialProduccionBean.this.servicioMovimientoInventario.contarPorCirterio(filters));
/* 101:    */         
/* 102:128 */         return lista;
/* 103:    */       }
/* 104:    */     };
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void actualizarProducto()
/* 108:    */   {
/* 109:136 */     DetalleMovimientoInventario d = (DetalleMovimientoInventario)this.dtDetalleAjuste.getRowData();
/* 110:137 */     String codigo = d.getProducto().getCodigo();
/* 111:    */     try
/* 112:    */     {
/* 113:140 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/* 114:    */       try
/* 115:    */       {
/* 116:142 */         this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 117:    */       }
/* 118:    */       catch (ExcepcionAS2 e)
/* 119:    */       {
/* 120:144 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 121:    */       }
/* 122:146 */       d.setProducto(producto);
/* 123:147 */       cargarBodega(d);
/* 124:148 */       d.setUnidadConversion(producto.getUnidad());
/* 125:149 */       InventarioProducto inventarioProducto = new InventarioProducto();
/* 126:150 */       d.setInventarioProducto(inventarioProducto);
/* 127:151 */       inventarioProducto.setProducto(producto);
/* 128:    */     }
/* 129:    */     catch (Exception ex)
/* 130:    */     {
/* 131:153 */       addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   private void cargarBodega(DetalleMovimientoInventario detalleMovimientoInventario)
/* 136:    */   {
/* 137:158 */     if (AppUtil.getBodega() != null)
/* 138:    */     {
/* 139:159 */       detalleMovimientoInventario.setBodegaOrigen(AppUtil.getBodega());
/* 140:    */     }
/* 141:    */     else
/* 142:    */     {
/* 143:161 */       Bodega bodegaTrabajo = this.servicioProducto.obtenerBodegaTrabajoProducto(detalleMovimientoInventario.getProducto(), Integer.valueOf(AppUtil.getSucursal()
/* 144:162 */         .getId()));
/* 145:163 */       detalleMovimientoInventario.setBodegaOrigen(bodegaTrabajo);
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String editar()
/* 150:    */   {
/* 151:175 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 152:    */     
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:197 */     return "";
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String limpiar()
/* 177:    */   {
/* 178:207 */     crearAjuste();
/* 179:208 */     return "";
/* 180:    */   }
/* 181:    */   
/* 182:    */   private void crearAjuste()
/* 183:    */   {
/* 184:212 */     this.ajusteInventario = new MovimientoInventario();
/* 185:213 */     this.ajusteInventario.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 186:214 */     this.ajusteInventario.setSucursal(AppUtil.getSucursal());
/* 187:    */     
/* 188:216 */     Documento documento = null;
/* 189:217 */     if ((getListaDocumentosAjuste() != null) && (!getListaDocumentosAjuste().isEmpty()))
/* 190:    */     {
/* 191:218 */       documento = (Documento)getListaDocumentosAjuste().get(0);
/* 192:219 */       this.ajusteInventario.setDocumento(documento);
/* 193:220 */       actualizarDocumento();
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:222 */       documento = new Documento();
/* 198:223 */       this.ajusteInventario.setDocumento(documento);
/* 199:    */     }
/* 200:226 */     this.ajusteInventario.setEstado(Estado.ELABORADO);
/* 201:227 */     this.ajusteInventario.setFecha(new Date());
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String guardar()
/* 205:    */   {
/* 206:    */     try
/* 207:    */     {
/* 208:238 */       this.servicioMovimientoInventario.guardar(getAjusteInventario());
/* 209:    */       
/* 210:240 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 211:    */       
/* 212:242 */       setEditado(false);
/* 213:    */     }
/* 214:    */     catch (ExcepcionAS2Inventario e)
/* 215:    */     {
/* 216:245 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 217:246 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 218:    */     }
/* 219:    */     catch (ExcepcionAS2Financiero e)
/* 220:    */     {
/* 221:248 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 222:249 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 223:    */     }
/* 224:    */     catch (ExcepcionAS2 e)
/* 225:    */     {
/* 226:251 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 227:252 */       LOG.error("ERROR AL GUARDAR UN AJUSTE", e);
/* 228:    */     }
/* 229:    */     catch (Exception e)
/* 230:    */     {
/* 231:254 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 232:255 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 233:    */     }
/* 234:257 */     return "";
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String agregarDetalle()
/* 238:    */   {
/* 239:267 */     DetalleMovimientoInventario d = new DetalleMovimientoInventario();
/* 240:268 */     d.setMovimientoInventario(getAjusteInventario());
/* 241:269 */     d.setProducto(new Producto());
/* 242:270 */     cargarBodega(d);
/* 243:271 */     InventarioProducto inventarioProducto = new InventarioProducto();
/* 244:272 */     inventarioProducto.setDetalleMovimientoInventario(d);
/* 245:273 */     d.setInventarioProducto(inventarioProducto);
/* 246:274 */     getAjusteInventario().getDetalleMovimientosInventario().add(d);
/* 247:275 */     return "";
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String eliminarDetalle()
/* 251:    */   {
/* 252:279 */     DetalleMovimientoInventario d = (DetalleMovimientoInventario)this.dtDetalleAjuste.getRowData();
/* 253:280 */     d.setEliminado(true);
/* 254:281 */     return "";
/* 255:    */   }
/* 256:    */   
/* 257:    */   public List<Lote> autocompletarLotes(String consulta)
/* 258:    */   {
/* 259:286 */     return this.servicioLote.autocompletarLote(this.inventarioProducto.getProducto(), consulta);
/* 260:    */   }
/* 261:    */   
/* 262:    */   public String actualizarDocumento()
/* 263:    */   {
/* 264:296 */     getAjusteInventario().setDocumento(this.servicioDocumento.buscarPorId(Integer.valueOf(getAjusteInventario().getDocumento().getId())));
/* 265:297 */     return "";
/* 266:    */   }
/* 267:    */   
/* 268:    */   public String eliminar()
/* 269:    */   {
/* 270:307 */     if (getAjusteInventario().getId() > 0) {
/* 271:    */       try
/* 272:    */       {
/* 273:310 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 274:    */       }
/* 275:    */       catch (Exception e)
/* 276:    */       {
/* 277:322 */         LOG.error("ERROR AL ANULAR AJUSTE INVENTARIO:", e);
/* 278:    */       }
/* 279:    */     } else {
/* 280:325 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 281:    */     }
/* 282:327 */     return "";
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String cargarDatos()
/* 286:    */   {
/* 287:337 */     return "";
/* 288:    */   }
/* 289:    */   
/* 290:    */   public List<Documento> getListaDocumentosAjuste()
/* 291:    */   {
/* 292:345 */     if (this.listaDocumentosAjuste == null) {
/* 293:    */       try
/* 294:    */       {
/* 295:347 */         this.listaDocumentosAjuste = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.AJUSTE_INVENTARIO);
/* 296:    */       }
/* 297:    */       catch (ExcepcionAS2 e)
/* 298:    */       {
/* 299:349 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 300:    */       }
/* 301:    */     }
/* 302:354 */     return this.listaDocumentosAjuste;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void cargarProducto()
/* 306:    */   {
/* 307:    */     try
/* 308:    */     {
/* 309:361 */       if (this.productosSeleccionados != null) {
/* 310:363 */         for (Producto producto : this.productosSeleccionados)
/* 311:    */         {
/* 312:    */           try
/* 313:    */           {
/* 314:366 */             this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/* 315:    */           }
/* 316:    */           catch (ExcepcionAS2 e)
/* 317:    */           {
/* 318:368 */             addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 319:    */           }
/* 320:371 */           DetalleMovimientoInventario detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 321:372 */           detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 322:373 */           detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 323:374 */           detalleMovimientoInventario.setProducto(producto);
/* 324:375 */           detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/* 325:376 */           cargarBodega(detalleMovimientoInventario);
/* 326:377 */           InventarioProducto inventarioProducto = new InventarioProducto();
/* 327:378 */           inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/* 328:379 */           inventarioProducto.setProducto(producto);
/* 329:380 */           detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/* 330:381 */           detalleMovimientoInventario.setMovimientoInventario(this.ajusteInventario);
/* 331:    */           
/* 332:383 */           this.ajusteInventario.getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/* 333:    */         }
/* 334:    */       }
/* 335:    */     }
/* 336:    */     catch (Exception e)
/* 337:    */     {
/* 338:388 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 339:389 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 340:    */     }
/* 341:    */   }
/* 342:    */   
/* 343:    */   public String cargarDetalleAjusteInventario(FileUploadEvent event)
/* 344:    */   {
/* 345:    */     try
/* 346:    */     {
/* 347:396 */       String fileName = "ajuste_inventario" + event.getFile().getFileName();
/* 348:397 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 349:398 */       this.servicioMovimientoInventario.cargarDetalleAjusteInventario(this.ajusteInventario, fileName, input, 5);
/* 350:399 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 351:    */     }
/* 352:    */     catch (ExcepcionAS2Financiero e)
/* 353:    */     {
/* 354:402 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 355:403 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 356:    */     }
/* 357:    */     catch (ExcepcionAS2Compras e)
/* 358:    */     {
/* 359:406 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 360:407 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 361:    */     }
/* 362:    */     catch (ExcepcionAS2 e)
/* 363:    */     {
/* 364:410 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 365:411 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 366:    */     }
/* 367:    */     catch (Exception e)
/* 368:    */     {
/* 369:414 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 370:    */     }
/* 371:416 */     return null;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setListaDocumentosAjuste(List<Documento> listaDocumentosTransferencia)
/* 375:    */   {
/* 376:424 */     this.listaDocumentosAjuste = listaDocumentosTransferencia;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setDetalleMovimientoInventario(List<DetalleMovimientoInventario> detalleMovimientoInventario)
/* 380:    */   {
/* 381:434 */     getAjusteInventario().setDetalleMovimientosInventario(detalleMovimientoInventario);
/* 382:    */   }
/* 383:    */   
/* 384:    */   public DataTable getDtDetalleAjuste()
/* 385:    */   {
/* 386:443 */     return this.dtDetalleAjuste;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void setDtDetalleAjuste(DataTable dtDetalleAjuste)
/* 390:    */   {
/* 391:453 */     this.dtDetalleAjuste = dtDetalleAjuste;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public MovimientoInventario getAjusteInventario()
/* 395:    */   {
/* 396:462 */     return this.ajusteInventario;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void setAjusteInventario(MovimientoInventario ajusteInventario)
/* 400:    */   {
/* 401:472 */     this.ajusteInventario = ajusteInventario;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public LazyDataModel<MovimientoInventario> getListaAjusteInventario()
/* 405:    */   {
/* 406:481 */     return this.listaAjusteInventario;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public void setListaAjusteInventario(LazyDataModel<MovimientoInventario> listaAjusteInventario)
/* 410:    */   {
/* 411:491 */     this.listaAjusteInventario = listaAjusteInventario;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public DataTable getDtListaAjuste()
/* 415:    */   {
/* 416:500 */     return this.dtListaAjuste;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void setDtListaAjuste(DataTable dtListaAjuste)
/* 420:    */   {
/* 421:510 */     this.dtListaAjuste = dtListaAjuste;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public List<DetalleMovimientoInventario> getDetalleAjusteMovimiento()
/* 425:    */   {
/* 426:520 */     List<DetalleMovimientoInventario> detalle = new ArrayList();
/* 427:521 */     for (DetalleMovimientoInventario dm : getAjusteInventario().getDetalleMovimientosInventario()) {
/* 428:523 */       if (!dm.isEliminado()) {
/* 429:524 */         detalle.add(dm);
/* 430:    */       }
/* 431:    */     }
/* 432:528 */     return detalle;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public boolean isMostrarSaldoInicial()
/* 436:    */   {
/* 437:537 */     Calendar calendario = Calendar.getInstance();
/* 438:538 */     calendario.setTime(ParametrosSistema.getFechaMaximaSaldosIniciales(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 439:539 */     int day = calendario.get(5);
/* 440:540 */     int month = calendario.get(2) + 1;
/* 441:541 */     int year = calendario.get(1);
/* 442:    */     
/* 443:543 */     Date fecha = FuncionesUtiles.getFecha(day, month, year);
/* 444:    */     
/* 445:545 */     this.mostrarSaldoInicial = FuncionesUtiles.compararFechaAnteriorOIgual(new Date(), fecha);
/* 446:    */     
/* 447:547 */     return this.mostrarSaldoInicial;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setMostrarSaldoInicial(boolean mostrarSaldoInicial)
/* 451:    */   {
/* 452:557 */     this.mostrarSaldoInicial = mostrarSaldoInicial;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public List<Bodega> getListaBodega()
/* 456:    */   {
/* 457:566 */     if (this.listaBodega == null) {
/* 458:567 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 459:    */     }
/* 460:569 */     return this.listaBodega;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 464:    */   {
/* 465:579 */     this.listaBodega = listaBodega;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public Producto[] getProductosSeleccionados()
/* 469:    */   {
/* 470:583 */     return this.productosSeleccionados;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void setProductosSeleccionados(Producto[] productosSeleccionados)
/* 474:    */   {
/* 475:587 */     this.productosSeleccionados = productosSeleccionados;
/* 476:    */   }
/* 477:    */   
/* 478:    */   public List<MotivoAjusteInventario> getListaMotivoAjusteInventario()
/* 479:    */   {
/* 480:596 */     if (this.listaMotivoAjusteInventario == null) {
/* 481:597 */       this.listaMotivoAjusteInventario = this.servicioMotivoAjusteInventario.obtenerListaCombo("nombre", true, null);
/* 482:    */     }
/* 483:599 */     return this.listaMotivoAjusteInventario;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void setListaMotivoAjusteInventario(List<MotivoAjusteInventario> listaMotivoAjusteInventario)
/* 487:    */   {
/* 488:609 */     this.listaMotivoAjusteInventario = listaMotivoAjusteInventario;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public DataTable getDtInventarioProductoLote()
/* 492:    */   {
/* 493:618 */     return this.dtInventarioProductoLote;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void setDtInventarioProductoLote(DataTable dtInventarioProductoLote)
/* 497:    */   {
/* 498:628 */     this.dtInventarioProductoLote = dtInventarioProductoLote;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public InventarioProducto getInventarioProducto()
/* 502:    */   {
/* 503:637 */     if (this.inventarioProducto == null) {
/* 504:638 */       this.inventarioProducto = new InventarioProducto();
/* 505:    */     }
/* 506:640 */     return this.inventarioProducto;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/* 510:    */   {
/* 511:650 */     this.inventarioProducto = inventarioProducto;
/* 512:    */   }
/* 513:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.ConsumoMaterialProduccionBean
 * JD-Core Version:    0.7.0.1
 */