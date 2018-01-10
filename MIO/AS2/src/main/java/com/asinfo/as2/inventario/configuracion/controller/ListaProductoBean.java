/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ProductoEstadoImportacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.FiltroProducto;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.ProductoAtributo;
/*  11:    */ import com.asinfo.as2.entities.ProductoSustituto;
/*  12:    */ import com.asinfo.as2.entities.SaldoProducto;
/*  13:    */ import com.asinfo.as2.entities.SaldoProductoLote;
/*  14:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioFiltroProducto;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  18:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*  19:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  22:    */ import com.asinfo.as2.web.controller.SesionControler;
/*  23:    */ import java.io.Serializable;
/*  24:    */ import java.math.BigDecimal;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Calendar;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.annotation.PostConstruct;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.faces.bean.ManagedBean;
/*  33:    */ import javax.faces.bean.ManagedProperty;
/*  34:    */ import javax.faces.bean.ViewScoped;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ import org.primefaces.component.datatable.DataTable;
/*  37:    */ import org.primefaces.event.SelectEvent;
/*  38:    */ import org.primefaces.model.LazyDataModel;
/*  39:    */ import org.primefaces.model.SortOrder;
/*  40:    */ 
/*  41:    */ @ManagedBean
/*  42:    */ @ViewScoped
/*  43:    */ public class ListaProductoBean
/*  44:    */   extends PageController
/*  45:    */   implements Serializable
/*  46:    */ {
/*  47:    */   private static final long serialVersionUID = -4585439120860530199L;
/*  48:    */   @EJB
/*  49:    */   protected ServicioProducto servicioProducto;
/*  50:    */   @EJB
/*  51:    */   private ServicioFiltroProducto servicioFiltroProducto;
/*  52:    */   @EJB
/*  53:    */   private ServicioRegistroPeso servicioRegistroPeso;
/*  54:    */   @ManagedProperty("#{sesionControler}")
/*  55:    */   private SesionControler sesionControler;
/*  56:    */   private Producto producto;
/*  57:    */   protected LazyDataModel<Producto> listaProducto;
/*  58:    */   private Producto[] productosSeleccionados;
/*  59: 76 */   private List<SaldoProducto> listaSaldoProducto = new ArrayList();
/*  60: 77 */   private List<SaldoProductoLote> listaSaldoProductoLote = new ArrayList();
/*  61: 78 */   private List<ProductoSustituto> listaProductoSustituto = new ArrayList();
/*  62: 79 */   private List<ProductoAtributo> listaProductoAtributo = new ArrayList();
/*  63: 80 */   private List<ProductoEstadoImportacion> listaProductoEstadoImportacion = new ArrayList();
/*  64:    */   private Bodega bodega;
/*  65:    */   private List<Bodega> listaBodega;
/*  66:    */   private List<Object[]> listaRegistroPeso;
/*  67:    */   private SaldoProductoLote saldoProductoLote;
/*  68: 88 */   private BigDecimal totalSaldoStock = BigDecimal.ZERO;
/*  69: 89 */   private BigDecimal totalSaldoStockLote = BigDecimal.ZERO;
/*  70: 90 */   private BigDecimal totalSaldoAlmacenamiento = BigDecimal.ZERO;
/*  71:    */   protected boolean indicadorCompra;
/*  72:    */   protected boolean indicadorVenta;
/*  73:    */   protected boolean indicadorProduccion;
/*  74:    */   protected boolean indicadorPesoBalanza;
/*  75:    */   protected boolean indicadorConsumo;
/*  76:    */   protected boolean activo;
/*  77:    */   protected TipoProducto tipoProducto;
/*  78:    */   protected boolean indicadorMantenimiento;
/*  79:    */   private DataTable dtProducto;
/*  80:    */   
/*  81:    */   @PostConstruct
/*  82:    */   public void init()
/*  83:    */   {
/*  84:108 */     setBodega(AppUtil.getBodega());
/*  85:    */     
/*  86:110 */     this.listaProducto = new LazyDataModel()
/*  87:    */     {
/*  88:    */       private static final long serialVersionUID = 1L;
/*  89:    */       
/*  90:    */       public List<Producto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  91:    */       {
/*  92:121 */         List<Producto> lista = new ArrayList();
/*  93:    */         
/*  94:123 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  95:    */         
/*  96:125 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  97:127 */         if (ListaProductoBean.this.indicadorVenta) {
/*  98:128 */           filters.put("indicadorVenta", String.valueOf(ListaProductoBean.this.indicadorVenta));
/*  99:    */         }
/* 100:131 */         if (ListaProductoBean.this.indicadorCompra) {
/* 101:132 */           filters.put("indicadorCompra", String.valueOf(ListaProductoBean.this.indicadorCompra));
/* 102:    */         }
/* 103:135 */         if (ListaProductoBean.this.indicadorProduccion) {
/* 104:136 */           filters.put("indicadorProduccion", String.valueOf(ListaProductoBean.this.indicadorProduccion));
/* 105:    */         }
/* 106:139 */         if (ListaProductoBean.this.activo) {
/* 107:140 */           filters.put("activo", String.valueOf(true));
/* 108:    */         }
/* 109:143 */         if (ListaProductoBean.this.tipoProducto != null) {
/* 110:144 */           filters.put("tipoProducto", ListaProductoBean.this.tipoProducto.toString());
/* 111:    */         }
/* 112:147 */         if (ListaProductoBean.this.indicadorPesoBalanza) {
/* 113:148 */           filters.put("indicadorPesoBalanza", String.valueOf(ListaProductoBean.this.indicadorPesoBalanza));
/* 114:    */         }
/* 115:151 */         if (ListaProductoBean.this.indicadorMantenimiento) {
/* 116:152 */           filters.put("indicadorMantenimiento", String.valueOf(ListaProductoBean.this.indicadorMantenimiento));
/* 117:    */         }
/* 118:155 */         if (ListaProductoBean.this.indicadorConsumo) {
/* 119:156 */           filters.put("indicadorConsumo", String.valueOf(ListaProductoBean.this.indicadorConsumo));
/* 120:    */         }
/* 121:    */         try
/* 122:    */         {
/* 123:165 */           lista = ListaProductoBean.this.servicioProducto.obtenerListaPorPagina(ListaProductoBean.this.getSesionControler().getFiltroProducto(), startIndex, ListaProductoBean.this
/* 124:166 */             .getSesionControler().getFiltroProducto().getNumeroRegistros(), sortField, ordenar, filters, 
/* 125:167 */             AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 126:    */           
/* 127:169 */           ListaProductoBean.this.listaProducto.setRowCount(lista.size());
/* 128:    */         }
/* 129:    */         catch (Exception e)
/* 130:    */         {
/* 131:174 */           e.printStackTrace();
/* 132:    */         }
/* 133:176 */         return lista;
/* 134:    */       }
/* 135:    */     };
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void actualizarProductosPrecargados(final List<Producto> listaProductosPrecargados)
/* 139:    */   {
/* 140:183 */     setBodega(AppUtil.getBodega());
/* 141:    */     
/* 142:185 */     this.listaProducto = new LazyDataModel()
/* 143:    */     {
/* 144:    */       private static final long serialVersionUID = 1L;
/* 145:    */       
/* 146:    */       public List<Producto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 147:    */       {
/* 148:196 */         List<Producto> lista = new ArrayList();
/* 149:    */         try
/* 150:    */         {
/* 151:199 */           lista = listaProductosPrecargados;
/* 152:    */           
/* 153:201 */           ListaProductoBean.this.listaProducto.setRowCount(lista.size());
/* 154:    */         }
/* 155:    */         catch (Exception e)
/* 156:    */         {
/* 157:203 */           e.printStackTrace();
/* 158:    */         }
/* 159:206 */         return lista;
/* 160:    */       }
/* 161:    */     };
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void onRowSelect(SelectEvent event)
/* 165:    */   {
/* 166:212 */     this.producto = ((Producto)event.getObject());
/* 167:213 */     cargarSaldoProducto(this.producto);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void cargarSaldoProductoListener()
/* 171:    */   {
/* 172:217 */     cargarSaldoProducto(getProducto());
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void cargarSaldoProducto(Producto producto)
/* 176:    */   {
/* 177:228 */     this.saldoProductoLote = null;
/* 178:230 */     if (producto != null)
/* 179:    */     {
/* 180:231 */       this.totalSaldoStock = BigDecimal.ZERO;
/* 181:232 */       this.totalSaldoStockLote = BigDecimal.ZERO;
/* 182:233 */       this.totalSaldoAlmacenamiento = BigDecimal.ZERO;
/* 183:    */       
/* 184:235 */       this.listaSaldoProducto = this.servicioProducto.obtenerSaldos(producto.getId(), Calendar.getInstance().getTime(), this.bodega);
/* 185:    */       
/* 186:237 */       this.listaProductoEstadoImportacion = this.servicioProducto.obtenerListaProductoEstadoImportacion(producto.getId(), 
/* 187:238 */         Calendar.getInstance().getTime());
/* 188:239 */       this.listaSaldoProductoLote = new ArrayList();
/* 189:241 */       for (SaldoProducto sp : this.listaSaldoProducto) {
/* 190:    */         try
/* 191:    */         {
/* 192:245 */           this.totalSaldoStock = this.totalSaldoStock.add(sp.getSaldo());
/* 193:    */           
/* 194:247 */           BigDecimal cantidad = this.servicioProducto.convierteUnidad(producto.getUnidad(), producto.getUnidadAlmacenamiento(), producto.getId(), sp
/* 195:248 */             .getSaldo());
/* 196:249 */           cantidad = FuncionesUtiles.redondearBigDecimal(cantidad, 4);
/* 197:250 */           sp.setSaldoUnidadAlmacenamiento(cantidad);
/* 198:    */           
/* 199:252 */           this.totalSaldoAlmacenamiento = this.totalSaldoAlmacenamiento.add(cantidad);
/* 200:    */         }
/* 201:    */         catch (ExcepcionAS2 localExcepcionAS2) {}
/* 202:    */       }
/* 203:259 */       this.listaProductoSustituto = this.servicioProducto.obtenerProductosSustitutos(producto.getId());
/* 204:260 */       this.listaProductoAtributo = this.servicioProducto.obtenerListaProductoAtributo(producto.getId());
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void cargarSaldoPorLoteListener()
/* 209:    */   {
/* 210:267 */     this.totalSaldoStockLote = BigDecimal.ZERO;
/* 211:268 */     if ((this.producto != null) && (this.producto.isIndicadorLote()))
/* 212:    */     {
/* 213:269 */       this.listaSaldoProductoLote = this.servicioProducto.obtenerProductosConSaldoBodegaLote(this.bodega, new Date(), this.producto, false);
/* 214:270 */       for (SaldoProductoLote s : this.listaSaldoProductoLote) {
/* 215:271 */         this.totalSaldoStockLote = this.totalSaldoStockLote.add(s.getSaldo());
/* 216:    */       }
/* 217:    */     }
/* 218:    */     else
/* 219:    */     {
/* 220:274 */       this.listaSaldoProductoLote = new ArrayList();
/* 221:    */     }
/* 222:276 */     if (this.producto != null) {
/* 223:277 */       this.listaRegistroPeso = this.servicioRegistroPeso.getReporteProductoCuarentena(new Date(), null, this.producto, null);
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void guardarFiltroProducto()
/* 228:    */   {
/* 229:    */     try
/* 230:    */     {
/* 231:283 */       this.servicioFiltroProducto.guardar(getSesionControler().getFiltroProducto());
/* 232:    */     }
/* 233:    */     catch (ExcepcionAS2 e)
/* 234:    */     {
/* 235:285 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 236:286 */       LOG.error("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/* 237:287 */       e.printStackTrace();
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void resetearIndicadores()
/* 242:    */   {
/* 243:292 */     this.indicadorCompra = false;
/* 244:293 */     this.indicadorProduccion = false;
/* 245:294 */     this.indicadorVenta = false;
/* 246:295 */     this.indicadorMantenimiento = false;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public LazyDataModel<Producto> getListaProducto()
/* 250:    */   {
/* 251:304 */     return this.listaProducto;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setListaProducto(LazyDataModel<Producto> listaProducto)
/* 255:    */   {
/* 256:314 */     this.listaProducto = listaProducto;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public Producto[] getProductosSeleccionados()
/* 260:    */   {
/* 261:323 */     return this.productosSeleccionados;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setProductosSeleccionados(Producto[] productosSeleccionados)
/* 265:    */   {
/* 266:333 */     this.productosSeleccionados = productosSeleccionados;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public DataTable getDtProducto()
/* 270:    */   {
/* 271:342 */     return this.dtProducto;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setDtProducto(DataTable dtProducto)
/* 275:    */   {
/* 276:352 */     this.dtProducto = dtProducto;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public Producto getProducto()
/* 280:    */   {
/* 281:361 */     return this.producto;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setProducto(Producto producto)
/* 285:    */   {
/* 286:371 */     this.producto = producto;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public List<SaldoProducto> getListaSaldoProducto()
/* 290:    */   {
/* 291:380 */     return this.listaSaldoProducto;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setListaSaldoProducto(List<SaldoProducto> listaSaldoProducto)
/* 295:    */   {
/* 296:390 */     this.listaSaldoProducto = listaSaldoProducto;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<ProductoSustituto> getListaProductoSustituto()
/* 300:    */   {
/* 301:399 */     return this.listaProductoSustituto;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setListaProductoSustituto(List<ProductoSustituto> listaProductoSustituto)
/* 305:    */   {
/* 306:409 */     this.listaProductoSustituto = listaProductoSustituto;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public List<ProductoAtributo> getListaProductoAtributo()
/* 310:    */   {
/* 311:418 */     if (this.listaProductoAtributo == null) {
/* 312:419 */       this.listaProductoAtributo = new ArrayList();
/* 313:    */     }
/* 314:421 */     return this.listaProductoAtributo;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setListaProductoAtributo(List<ProductoAtributo> listaProductoAtributo)
/* 318:    */   {
/* 319:431 */     this.listaProductoAtributo = listaProductoAtributo;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public BigDecimal getTotalSaldoStock()
/* 323:    */   {
/* 324:438 */     return this.totalSaldoStock;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public BigDecimal getTotalSaldoAlmacenamiento()
/* 328:    */   {
/* 329:445 */     return this.totalSaldoAlmacenamiento;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public List<SaldoProductoLote> getListaSaldoProductoLote()
/* 333:    */   {
/* 334:452 */     return this.listaSaldoProductoLote;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setListaSaldoProductoLote(List<SaldoProductoLote> listaSaldoProductoLote)
/* 338:    */   {
/* 339:460 */     this.listaSaldoProductoLote = listaSaldoProductoLote;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public BigDecimal getTotalSaldoStockLote()
/* 343:    */   {
/* 344:467 */     return this.totalSaldoStockLote;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setTotalSaldoStockLote(BigDecimal totalSaldoStockLote)
/* 348:    */   {
/* 349:475 */     this.totalSaldoStockLote = totalSaldoStockLote;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public boolean isIndicadorCompra()
/* 353:    */   {
/* 354:482 */     return this.indicadorCompra;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setIndicadorCompra(boolean indicadorCompra)
/* 358:    */   {
/* 359:490 */     this.indicadorCompra = indicadorCompra;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public boolean isIndicadorVenta()
/* 363:    */   {
/* 364:497 */     return this.indicadorVenta;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void setIndicadorVenta(boolean indicadorVenta)
/* 368:    */   {
/* 369:505 */     this.indicadorVenta = indicadorVenta;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public List<ProductoEstadoImportacion> getListaProductoEstadoImportacion()
/* 373:    */   {
/* 374:514 */     return this.listaProductoEstadoImportacion;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public boolean isActivo()
/* 378:    */   {
/* 379:518 */     return this.activo;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setActivo(boolean activo)
/* 383:    */   {
/* 384:522 */     this.activo = activo;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public TipoProducto getTipoProducto()
/* 388:    */   {
/* 389:529 */     return this.tipoProducto;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setTipoProducto(TipoProducto tipoProducto)
/* 393:    */   {
/* 394:537 */     this.tipoProducto = tipoProducto;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public List<Bodega> getListaBodega()
/* 398:    */   {
/* 399:541 */     if (this.listaBodega == null) {
/* 400:542 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 401:    */     }
/* 402:544 */     return this.listaBodega;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 406:    */   {
/* 407:548 */     this.listaBodega = listaBodega;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public Bodega getBodega()
/* 411:    */   {
/* 412:552 */     return this.bodega;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void setBodega(Bodega bodega)
/* 416:    */   {
/* 417:556 */     this.bodega = bodega;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public SaldoProductoLote getSaldoProductoLote()
/* 421:    */   {
/* 422:560 */     return this.saldoProductoLote;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void setSaldoProductoLote(SaldoProductoLote saldoProductoLote)
/* 426:    */   {
/* 427:564 */     this.saldoProductoLote = saldoProductoLote;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public boolean isIndicadorProduccion()
/* 431:    */   {
/* 432:568 */     return this.indicadorProduccion;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setIndicadorProduccion(boolean indicadorProduccion)
/* 436:    */   {
/* 437:572 */     this.indicadorProduccion = indicadorProduccion;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public SesionControler getSesionControler()
/* 441:    */   {
/* 442:576 */     return this.sesionControler;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void setSesionControler(SesionControler sesionControler)
/* 446:    */   {
/* 447:580 */     this.sesionControler = sesionControler;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public boolean isIndicadorPesoBalanza()
/* 451:    */   {
/* 452:584 */     return this.indicadorPesoBalanza;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void setIndicadorPesoBalanza(boolean indicadorPesoBalanza)
/* 456:    */   {
/* 457:588 */     this.indicadorPesoBalanza = indicadorPesoBalanza;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public boolean isIndicadorMantenimiento()
/* 461:    */   {
/* 462:592 */     return this.indicadorMantenimiento;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public void setIndicadorMantenimiento(boolean indicadorMantenimiento)
/* 466:    */   {
/* 467:596 */     this.indicadorMantenimiento = indicadorMantenimiento;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public List<Object[]> getListaRegistroPeso()
/* 471:    */   {
/* 472:600 */     return this.listaRegistroPeso;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public void setListaRegistroPeso(List<Object[]> listaRegistroPeso)
/* 476:    */   {
/* 477:604 */     this.listaRegistroPeso = listaRegistroPeso;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public boolean isIndicadorConsumo()
/* 481:    */   {
/* 482:608 */     return this.indicadorConsumo;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public void setIndicadorConsumo(boolean indicadorConsumo)
/* 486:    */   {
/* 487:612 */     this.indicadorConsumo = indicadorConsumo;
/* 488:    */   }
/* 489:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean
 * JD-Core Version:    0.7.0.1
 */