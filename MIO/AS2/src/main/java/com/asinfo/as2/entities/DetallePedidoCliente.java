/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacion;
/*   4:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.math.RoundingMode;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.persistence.Column;
/*  12:    */ import javax.persistence.Entity;
/*  13:    */ import javax.persistence.FetchType;
/*  14:    */ import javax.persistence.GeneratedValue;
/*  15:    */ import javax.persistence.GenerationType;
/*  16:    */ import javax.persistence.Id;
/*  17:    */ import javax.persistence.JoinColumn;
/*  18:    */ import javax.persistence.ManyToOne;
/*  19:    */ import javax.persistence.OneToMany;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Transient;
/*  23:    */ import javax.validation.constraints.DecimalMin;
/*  24:    */ import javax.validation.constraints.Digits;
/*  25:    */ import javax.validation.constraints.Min;
/*  26:    */ import javax.validation.constraints.NotNull;
/*  27:    */ import javax.validation.constraints.Size;
/*  28:    */ import org.hibernate.annotations.ColumnDefault;
/*  29:    */ 
/*  30:    */ @Entity
/*  31:    */ @Table(name="detalle_pedido_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_detalle_pedido_cliente"})}, indexes={@javax.persistence.Index(columnList="id_detalle_pedido_cliente")})
/*  32:    */ public class DetallePedidoCliente
/*  33:    */   extends EntidadBase
/*  34:    */   implements Serializable
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 571335322178384473L;
/*  37:    */   @Id
/*  38:    */   @TableGenerator(name="detalle_pedido_cliente", initialValue=0, allocationSize=50)
/*  39:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_pedido_cliente")
/*  40:    */   @Column(name="id_detalle_pedido_cliente")
/*  41:    */   private int idDetallePedidoCliente;
/*  42:    */   @Column(name="id_organizacion", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private int idOrganizacion;
/*  45:    */   @Column(name="id_sucursal", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private int idSucursal;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_pedido_cliente", nullable=true)
/*  50:    */   private PedidoCliente pedidoCliente;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_producto", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   private Producto producto;
/*  55:    */   @NotNull
/*  56:    */   @Column(name="cantidad", nullable=false, precision=12, scale=4)
/*  57:    */   @Digits(integer=12, fraction=4)
/*  58:    */   @DecimalMin("0.0001")
/*  59: 73 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  60:    */   @Column(name="cantidad_por_facturar", nullable=false, precision=12, scale=4)
/*  61:    */   @Digits(integer=12, fraction=4)
/*  62: 79 */   private BigDecimal cantidadPorFacturar = BigDecimal.ZERO;
/*  63:    */   @Transient
/*  64: 83 */   private BigDecimal cantidadFacturada = BigDecimal.ZERO;
/*  65:    */   @Column(name="cantidad_por_despachar", nullable=false, precision=12, scale=4)
/*  66:    */   @Digits(integer=12, fraction=4)
/*  67: 86 */   private BigDecimal cantidadPorDespachar = BigDecimal.ZERO;
/*  68:    */   @Transient
/*  69:    */   private BigDecimal cantidadPorDespacharUnidadManejo;
/*  70:    */   @Transient
/*  71: 93 */   private BigDecimal cantidadDespachada = BigDecimal.ZERO;
/*  72:    */   @Transient
/*  73: 97 */   private BigDecimal cantidadADespachar = BigDecimal.ZERO;
/*  74:    */   @Transient
/*  75:    */   private BigDecimal cantidadADespacharUnidadManejo;
/*  76:    */   @Column(name="precio", nullable=false, precision=12, scale=6)
/*  77:    */   @NotNull
/*  78:    */   @Digits(integer=12, fraction=6)
/*  79:    */   @Min(0L)
/*  80:103 */   private BigDecimal precio = BigDecimal.ZERO;
/*  81:    */   @Column(name="indicador_porcentaje_ice", nullable=false)
/*  82:    */   @NotNull
/*  83:    */   @ColumnDefault("'0'")
/*  84:    */   private boolean indicadorPorcentajeIce;
/*  85:    */   @Column(name="ice", nullable=false, precision=12, scale=2)
/*  86:    */   @NotNull
/*  87:    */   @ColumnDefault("0")
/*  88:114 */   private BigDecimal ice = BigDecimal.ZERO;
/*  89:    */   @Column(name="codigo_ice", length=10, nullable=true)
/*  90:    */   @Size(max=10)
/*  91:119 */   private String codigoIce = "";
/*  92:    */   @Column(name="ice_linea", nullable=false, precision=12, scale=2)
/*  93:    */   @NotNull
/*  94:    */   @Digits(integer=12, fraction=2)
/*  95:    */   @Min(0L)
/*  96:    */   @ColumnDefault("0")
/*  97:123 */   private BigDecimal iceLinea = BigDecimal.ZERO;
/*  98:    */   @NotNull
/*  99:    */   @Column(name="descuento", nullable=false, precision=12, scale=4)
/* 100:    */   @Digits(integer=12, fraction=4)
/* 101:    */   @Min(0L)
/* 102:130 */   private BigDecimal descuento = BigDecimal.ZERO;
/* 103:    */   @NotNull
/* 104:    */   @Column(name="porcentaje_descuento", nullable=false, precision=5, scale=4)
/* 105:    */   @Digits(integer=3, fraction=4)
/* 106:    */   @Min(0L)
/* 107:136 */   private BigDecimal porcentajeDescuento = BigDecimal.ZERO;
/* 108:    */   @Column(name="indicador_impuesto", nullable=false)
/* 109:    */   @NotNull
/* 110:    */   private boolean indicadorImpuesto;
/* 111:    */   @Column(name="descripcion", nullable=true, length=5000)
/* 112:    */   @Size(max=5000)
/* 113:    */   private String descripcion;
/* 114:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detallePedidoCliente")
/* 115:150 */   private List<ImpuestoProductoPedidoCliente> listaImpuestoProductoPedidoCliente = new ArrayList();
/* 116:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detallePedidoCliente")
/* 117:153 */   private List<DetalleDespachoCliente> listaDetalleDespachoCliente = new ArrayList();
/* 118:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detallePedido")
/* 119:156 */   private List<DetalleOrdenFabricacion> listaDetalleOrdenFabricacion = new ArrayList();
/* 120:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 121:    */   @JoinColumn(name="id_unidad_venta", nullable=false)
/* 122:    */   private Unidad unidadVenta;
/* 123:    */   @Transient
/* 124:    */   @Min(0L)
/* 125:163 */   private BigDecimal traPorcentajeDescuento = BigDecimal.ZERO;
/* 126:    */   @Column(name="saldo_venta", nullable=true, precision=12, scale=4)
/* 127:167 */   private BigDecimal saldoVenta = BigDecimal.ZERO;
/* 128:    */   @Column(name="cantidad_enviada_a_producir", nullable=true, precision=12, scale=2)
/* 129:    */   @Digits(integer=10, fraction=2)
/* 130:    */   @DecimalMin("0.00")
/* 131:171 */   private BigDecimal cantidadEnviadaAProducir = BigDecimal.ZERO;
/* 132:    */   @Column(name="referencia1", nullable=true, length=300)
/* 133:    */   private String referencia1;
/* 134:    */   @Column(name="cantidad_embalaje_despacho", nullable=true)
/* 135:181 */   private BigDecimal cantidadEmbalajeDespacho = BigDecimal.ONE;
/* 136:    */   @Column(name="cantidad_unidad_despacho", nullable=true)
/* 137:    */   private BigDecimal cantidadUnidadDespacho;
/* 138:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detallePedidoCliente")
/* 139:188 */   private List<DetalleOrdenDespachoClientePedidoCliente> listaDetalleOrdenDespachoCliente = new ArrayList();
/* 140:    */   @Transient
/* 141:192 */   private BigDecimal cantidadOriginal = BigDecimal.ZERO;
/* 142:    */   @Transient
/* 143:    */   private boolean indicadorSugerido;
/* 144:    */   
/* 145:    */   public DetallePedidoCliente() {}
/* 146:    */   
/* 147:    */   public DetallePedidoCliente(int idDetallePedidoCliente)
/* 148:    */   {
/* 149:202 */     this.idDetallePedidoCliente = idDetallePedidoCliente;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<DetalleDespachoCliente> getListaDetalleDespachoCliente()
/* 153:    */   {
/* 154:206 */     return this.listaDetalleDespachoCliente;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setListaDetalleDespachoCliente(List<DetalleDespachoCliente> listaDetalleDespachoCliente)
/* 158:    */   {
/* 159:210 */     this.listaDetalleDespachoCliente = listaDetalleDespachoCliente;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public BigDecimal getPrecioLinea()
/* 163:    */   {
/* 164:219 */     return FuncionesUtiles.redondearBigDecimal(getPrecio().multiply(getCantidad()));
/* 165:    */   }
/* 166:    */   
/* 167:    */   public BigDecimal getDescuentoLinea()
/* 168:    */   {
/* 169:228 */     return FuncionesUtiles.redondearBigDecimal(getCantidad().multiply(getDescuento()));
/* 170:    */   }
/* 171:    */   
/* 172:    */   public BigDecimal getBaseImponible()
/* 173:    */   {
/* 174:239 */     BigDecimal baseImponibleLinea = getPrecio().subtract(getDescuento()).multiply(getCantidad()).add(getIceLinea());
/* 175:    */     
/* 176:241 */     return baseImponibleLinea;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public BigDecimal getValorImpuestosLinea()
/* 180:    */   {
/* 181:252 */     BigDecimal valorImpuestosLinea = BigDecimal.ZERO;
/* 182:254 */     for (ImpuestoProductoPedidoCliente ipfp : getListaImpuestoProductoPedidoCliente()) {
/* 183:255 */       if (!ipfp.isEliminado()) {
/* 184:257 */         valorImpuestosLinea = valorImpuestosLinea.add(ipfp.getImpuestoProducto());
/* 185:    */       }
/* 186:    */     }
/* 187:261 */     return valorImpuestosLinea;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public int getId()
/* 191:    */   {
/* 192:272 */     return this.idDetallePedidoCliente;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public int getIdDetallePedidoCliente()
/* 196:    */   {
/* 197:276 */     return this.idDetallePedidoCliente;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setIdDetallePedidoCliente(int idDetallePedidoCliente)
/* 201:    */   {
/* 202:280 */     this.idDetallePedidoCliente = idDetallePedidoCliente;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public int getIdOrganizacion()
/* 206:    */   {
/* 207:284 */     return this.idOrganizacion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setIdOrganizacion(int idOrganizacion)
/* 211:    */   {
/* 212:288 */     this.idOrganizacion = idOrganizacion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public int getIdSucursal()
/* 216:    */   {
/* 217:292 */     return this.idSucursal;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setIdSucursal(int idSucursal)
/* 221:    */   {
/* 222:296 */     this.idSucursal = idSucursal;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public PedidoCliente getPedidoCliente()
/* 226:    */   {
/* 227:300 */     return this.pedidoCliente;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setPedidoCliente(PedidoCliente pedidoCliente)
/* 231:    */   {
/* 232:304 */     this.pedidoCliente = pedidoCliente;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Producto getProducto()
/* 236:    */   {
/* 237:308 */     return this.producto;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setProducto(Producto producto)
/* 241:    */   {
/* 242:312 */     this.producto = producto;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public BigDecimal getCantidad()
/* 246:    */   {
/* 247:316 */     return this.cantidad;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setCantidad(BigDecimal cantidad)
/* 251:    */   {
/* 252:320 */     this.cantidad = cantidad;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public BigDecimal getPrecio()
/* 256:    */   {
/* 257:325 */     return this.precio;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setPrecio(BigDecimal precio)
/* 261:    */   {
/* 262:329 */     this.precio = precio;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public BigDecimal getDescuento()
/* 266:    */   {
/* 267:333 */     this.descuento = (this.descuento.compareTo(this.precio) > 0 ? this.precio : this.descuento);
/* 268:334 */     return this.descuento;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setDescuento(BigDecimal descuento)
/* 272:    */   {
/* 273:338 */     this.descuento = descuento;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public boolean isIndicadorImpuesto()
/* 277:    */   {
/* 278:342 */     return this.indicadorImpuesto;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setIndicadorImpuesto(boolean indicadorImpuesto)
/* 282:    */   {
/* 283:346 */     this.indicadorImpuesto = indicadorImpuesto;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public String getDescripcion()
/* 287:    */   {
/* 288:350 */     return this.descripcion;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setDescripcion(String descripcion)
/* 292:    */   {
/* 293:354 */     this.descripcion = descripcion;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public String getUsuarioCreacion()
/* 297:    */   {
/* 298:358 */     return this.usuarioCreacion;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setUsuarioCreacion(String usuarioCreacion)
/* 302:    */   {
/* 303:362 */     this.usuarioCreacion = usuarioCreacion;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Date getFechaCreacion()
/* 307:    */   {
/* 308:366 */     return this.fechaCreacion;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setFechaCreacion(Date fechaCreacion)
/* 312:    */   {
/* 313:370 */     this.fechaCreacion = fechaCreacion;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public Date getFechaModificacion()
/* 317:    */   {
/* 318:374 */     return this.fechaModificacion;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setFechaModificacion(Date fechaModificacion)
/* 322:    */   {
/* 323:378 */     this.fechaModificacion = fechaModificacion;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public String getUsuarioModificacion()
/* 327:    */   {
/* 328:382 */     return this.usuarioModificacion;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setUsuarioModificacion(String usuarioModificacion)
/* 332:    */   {
/* 333:386 */     this.usuarioModificacion = usuarioModificacion;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public List<ImpuestoProductoPedidoCliente> getListaImpuestoProductoPedidoCliente()
/* 337:    */   {
/* 338:390 */     return this.listaImpuestoProductoPedidoCliente;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setListaImpuestoProductoPedidoCliente(List<ImpuestoProductoPedidoCliente> listaImpuestoProductoPedidoCliente)
/* 342:    */   {
/* 343:394 */     this.listaImpuestoProductoPedidoCliente = listaImpuestoProductoPedidoCliente;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public BigDecimal getCantidadPorFacturar()
/* 347:    */   {
/* 348:403 */     return this.cantidadPorFacturar;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setCantidadPorFacturar(BigDecimal cantidadPorFacturar)
/* 352:    */   {
/* 353:413 */     this.cantidadPorFacturar = cantidadPorFacturar;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public BigDecimal getCantidadPorDespachar()
/* 357:    */   {
/* 358:422 */     return this.cantidadPorDespachar;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setCantidadPorDespachar(BigDecimal cantidadPorDespachar)
/* 362:    */   {
/* 363:432 */     this.cantidadPorDespachar = cantidadPorDespachar;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public BigDecimal getCantidadFacturada()
/* 367:    */   {
/* 368:441 */     this.cantidadFacturada = this.cantidad.subtract(this.cantidadPorFacturar);
/* 369:442 */     return this.cantidadFacturada;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setCantidadFacturada(BigDecimal cantidadFacturada)
/* 373:    */   {
/* 374:452 */     this.cantidadFacturada = cantidadFacturada;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public BigDecimal getCantidadDespachada()
/* 378:    */   {
/* 379:461 */     this.cantidadDespachada = this.cantidad.subtract(this.cantidadPorDespachar);
/* 380:462 */     return this.cantidadDespachada;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setCantidadDespachada(BigDecimal cantidadDespachada)
/* 384:    */   {
/* 385:472 */     this.cantidadDespachada = cantidadDespachada;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public BigDecimal getTraPorcentajeDescuento()
/* 389:    */   {
/* 390:481 */     return FuncionesUtiles.redondearBigDecimal(this.traPorcentajeDescuento);
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setTraPorcentajeDescuento(BigDecimal traPorcentajeDescuento)
/* 394:    */   {
/* 395:491 */     this.traPorcentajeDescuento = traPorcentajeDescuento;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public BigDecimal getPorcentajeDescuento()
/* 399:    */   {
/* 400:500 */     return this.porcentajeDescuento;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void setPorcentajeDescuento(BigDecimal porcentajeDescuento)
/* 404:    */   {
/* 405:510 */     this.porcentajeDescuento = porcentajeDescuento;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public Unidad getUnidadVenta()
/* 409:    */   {
/* 410:519 */     return this.unidadVenta;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setUnidadVenta(Unidad unidadVenta)
/* 414:    */   {
/* 415:529 */     this.unidadVenta = unidadVenta;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public BigDecimal getSaldoVenta()
/* 419:    */   {
/* 420:538 */     return this.saldoVenta;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setSaldoVenta(BigDecimal saldoVenta)
/* 424:    */   {
/* 425:548 */     this.saldoVenta = saldoVenta;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public List<DetalleOrdenFabricacion> getListaDetalleOrdenFabricacion()
/* 429:    */   {
/* 430:552 */     return this.listaDetalleOrdenFabricacion;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setListaDetalleOrdenFabricacion(List<DetalleOrdenFabricacion> listaDetalleOrdenFabricacion)
/* 434:    */   {
/* 435:556 */     this.listaDetalleOrdenFabricacion = listaDetalleOrdenFabricacion;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public BigDecimal getCantidadEnviadaAProducir()
/* 439:    */   {
/* 440:560 */     return this.cantidadEnviadaAProducir == null ? BigDecimal.ZERO : this.cantidadEnviadaAProducir;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void setCantidadEnviadaAProducir(BigDecimal cantidadEnviadaAProducir)
/* 444:    */   {
/* 445:564 */     this.cantidadEnviadaAProducir = cantidadEnviadaAProducir;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public String getReferencia1()
/* 449:    */   {
/* 450:568 */     return this.referencia1;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void setReferencia1(String referencia1)
/* 454:    */   {
/* 455:572 */     this.referencia1 = referencia1;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public BigDecimal getCantidadEmbalajeDespacho()
/* 459:    */   {
/* 460:576 */     if ((this.cantidadEmbalajeDespacho == null) || (this.cantidadEmbalajeDespacho.compareTo(BigDecimal.ZERO) == 0)) {
/* 461:577 */       this.cantidadEmbalajeDespacho = BigDecimal.ONE;
/* 462:    */     }
/* 463:579 */     return this.cantidadEmbalajeDespacho;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setCantidadEmbalajeDespacho(BigDecimal cantidadEmbalajeDespacho)
/* 467:    */   {
/* 468:583 */     this.cantidadEmbalajeDespacho = cantidadEmbalajeDespacho;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public BigDecimal getCantidadUnidadDespacho()
/* 472:    */   {
/* 473:587 */     if (this.cantidadUnidadDespacho == null) {
/* 474:588 */       this.cantidadUnidadDespacho = this.cantidad;
/* 475:    */     }
/* 476:590 */     return this.cantidadUnidadDespacho;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public void setCantidadUnidadDespacho(BigDecimal cantidadUnidadDespacho)
/* 480:    */   {
/* 481:594 */     this.cantidadUnidadDespacho = cantidadUnidadDespacho;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public BigDecimal getCantidadADespachar()
/* 485:    */   {
/* 486:598 */     return this.cantidadADespachar;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public void setCantidadADespachar(BigDecimal cantidadADespachar)
/* 490:    */   {
/* 491:602 */     this.cantidadADespachar = cantidadADespachar;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public BigDecimal getCantidadPorDespacharUnidadManejo()
/* 495:    */   {
/* 496:606 */     this.cantidadPorDespacharUnidadManejo = this.cantidadPorDespachar.divide(getCantidadEmbalajeDespacho(), 2, RoundingMode.HALF_UP);
/* 497:607 */     return this.cantidadPorDespacharUnidadManejo;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public void setCantidadPorDespacharUnidadManejo(BigDecimal cantidadPorDespacharUnidadManejo)
/* 501:    */   {
/* 502:611 */     this.cantidadPorDespacharUnidadManejo = cantidadPorDespacharUnidadManejo;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public BigDecimal getCantidadADespacharUnidadManejo()
/* 506:    */   {
/* 507:615 */     this.cantidadADespacharUnidadManejo = getCantidadADespachar().divide(getCantidadEmbalajeDespacho(), 2, RoundingMode.HALF_UP);
/* 508:616 */     return this.cantidadADespacharUnidadManejo;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void setCantidadADespacharUnidadManejo(BigDecimal cantidadADespacharUnidadManejo)
/* 512:    */   {
/* 513:620 */     this.cantidadADespacharUnidadManejo = cantidadADespacharUnidadManejo;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public List<DetalleOrdenDespachoClientePedidoCliente> getListaDetalleOrdenDespachoCliente()
/* 517:    */   {
/* 518:624 */     return this.listaDetalleOrdenDespachoCliente;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public void setListaDetalleOrdenDespachoCliente(List<DetalleOrdenDespachoClientePedidoCliente> listaDetalleOrdenDespachoCliente)
/* 522:    */   {
/* 523:628 */     this.listaDetalleOrdenDespachoCliente = listaDetalleOrdenDespachoCliente;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public BigDecimal getCantidadOriginal()
/* 527:    */   {
/* 528:632 */     return this.cantidadOriginal;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public void setCantidadOriginal(BigDecimal cantidadOriginal)
/* 532:    */   {
/* 533:636 */     this.cantidadOriginal = cantidadOriginal;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public boolean isIndicadorPorcentajeIce()
/* 537:    */   {
/* 538:640 */     return this.indicadorPorcentajeIce;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public void setIndicadorPorcentajeIce(boolean indicadorPorcentajeIce)
/* 542:    */   {
/* 543:644 */     this.indicadorPorcentajeIce = indicadorPorcentajeIce;
/* 544:    */   }
/* 545:    */   
/* 546:    */   public BigDecimal getIce()
/* 547:    */   {
/* 548:648 */     return this.ice;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public void setIce(BigDecimal ice)
/* 552:    */   {
/* 553:652 */     this.ice = ice;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public String getCodigoIce()
/* 557:    */   {
/* 558:656 */     return this.codigoIce;
/* 559:    */   }
/* 560:    */   
/* 561:    */   public void setCodigoIce(String codigoIce)
/* 562:    */   {
/* 563:660 */     this.codigoIce = codigoIce;
/* 564:    */   }
/* 565:    */   
/* 566:    */   public BigDecimal getIceLinea()
/* 567:    */   {
/* 568:664 */     return this.iceLinea;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public void setIceLinea(BigDecimal iceLinea)
/* 572:    */   {
/* 573:668 */     this.iceLinea = iceLinea;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public boolean isIndicadorSugerido()
/* 577:    */   {
/* 578:672 */     return this.indicadorSugerido;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public void setIndicadorSugerido(boolean indicadorSugerido)
/* 582:    */   {
/* 583:676 */     this.indicadorSugerido = indicadorSugerido;
/* 584:    */   }
/* 585:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallePedidoCliente
 * JD-Core Version:    0.7.0.1
 */