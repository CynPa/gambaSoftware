/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   4:    */ import com.asinfo.as2.utils.ParametrosSistema;
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
/*  31:    */ @Table(name="detalle_pedido_proveedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_detalle_pedido_proveedor"})})
/*  32:    */ public class DetallePedidoProveedor
/*  33:    */   extends EntidadBase
/*  34:    */   implements Serializable
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 571335322178384473L;
/*  37:    */   @Id
/*  38:    */   @TableGenerator(name="detalle_pedido_proveedor", initialValue=0, allocationSize=50)
/*  39:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_pedido_proveedor")
/*  40:    */   @Column(name="id_detalle_pedido_proveedor")
/*  41:    */   private int idDetallePedidoProveedor;
/*  42:    */   @Column(name="id_organizacion", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private int idOrganizacion;
/*  45:    */   @Column(name="id_sucursal", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private int idSucursal;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_pedido_proveedor", nullable=true)
/*  50:    */   private PedidoProveedor pedidoProveedor;
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
/*  61:    */   @Digits(integer=10, fraction=4)
/*  62: 79 */   private BigDecimal cantidadPorFacturar = BigDecimal.ZERO;
/*  63:    */   @Transient
/*  64: 83 */   private BigDecimal cantidadFacturada = BigDecimal.ZERO;
/*  65:    */   @Column(name="cantidad_por_recibir", nullable=false, precision=12, scale=4)
/*  66:    */   @Digits(integer=12, fraction=4)
/*  67: 86 */   private BigDecimal cantidadPorRecibir = BigDecimal.ZERO;
/*  68:    */   @Transient
/*  69: 90 */   private BigDecimal cantidadRecibida = BigDecimal.ZERO;
/*  70:    */   @Column(name="precio", nullable=false, precision=12, scale=6)
/*  71:    */   @NotNull
/*  72:    */   @Digits(integer=12, fraction=6)
/*  73:    */   @Min(0L)
/*  74: 93 */   private BigDecimal precio = BigDecimal.ZERO;
/*  75:    */   @Column(name="precio_anterior", nullable=true, precision=12, scale=6)
/*  76:    */   @Digits(integer=12, fraction=6)
/*  77:    */   @Min(0L)
/*  78:    */   private BigDecimal precioAnterior;
/*  79:    */   @Column(name="precio_linea_anterior", nullable=true, precision=12, scale=6)
/*  80:    */   @Digits(integer=12, fraction=6)
/*  81:    */   @Min(0L)
/*  82:    */   private BigDecimal precioLineaAnterior;
/*  83:    */   @Column(name="precio_nuevo", nullable=true, precision=12, scale=6)
/*  84:    */   @Digits(integer=12, fraction=6)
/*  85:    */   @Min(0L)
/*  86:    */   @ColumnDefault("0.00")
/*  87:    */   private BigDecimal precioNuevo;
/*  88:    */   @NotNull
/*  89:    */   @Column(name="descuento", nullable=false, precision=12, scale=4)
/*  90:    */   @Digits(integer=12, fraction=4)
/*  91:    */   @Min(0L)
/*  92:115 */   private BigDecimal descuento = BigDecimal.ZERO;
/*  93:    */   @NotNull
/*  94:    */   @Column(name="porcentaje_descuento", nullable=false, precision=5, scale=2)
/*  95:    */   @Digits(integer=3, fraction=2)
/*  96:    */   @Min(0L)
/*  97:121 */   private BigDecimal porcentajeDescuento = BigDecimal.ZERO;
/*  98:    */   @Column(name="indicador_impuestos", nullable=false)
/*  99:    */   @NotNull
/* 100:    */   private boolean indicadorImpuestos;
/* 101:    */   @Column(name="descripcion", nullable=true, length=200)
/* 102:    */   @Size(max=200)
/* 103:    */   private String descripcion;
/* 104:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 105:    */   @JoinColumn(name="id_dimension_contable1")
/* 106:    */   private DimensionContable dimensionContable1;
/* 107:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 108:    */   @JoinColumn(name="id_dimension_contable2")
/* 109:    */   private DimensionContable dimensionContable2;
/* 110:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 111:    */   @JoinColumn(name="id_dimension_contable3")
/* 112:    */   private DimensionContable dimensionContable3;
/* 113:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 114:    */   @JoinColumn(name="id_dimension_contable4")
/* 115:    */   private DimensionContable dimensionContable4;
/* 116:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 117:    */   @JoinColumn(name="id_dimension_contable5")
/* 118:    */   private DimensionContable dimensionContable5;
/* 119:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detallePedidoProveedor")
/* 120:155 */   private List<ImpuestoProductoPedidoProveedor> listaImpuestoProductoPedidoProveedor = new ArrayList();
/* 121:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detallePedidoProveedor")
/* 122:158 */   private List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor = new ArrayList();
/* 123:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 124:    */   @JoinColumn(name="id_unidad_compra", nullable=false)
/* 125:    */   private Unidad unidadCompra;
/* 126:    */   @NotNull
/* 127:    */   @ColumnDefault("'0'")
/* 128:    */   @Column(name="indicador_aprobado", nullable=false)
/* 129:    */   private boolean indicadorAprobado;
/* 130:    */   @Transient
/* 131:170 */   private BigDecimal precioLinea = BigDecimal.ZERO;
/* 132:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 133:    */   @JoinColumn(name="id_detalle_solicitud_compra", nullable=true)
/* 134:    */   private DetalleSolicitudCompra detalleSolicitudCompra;
/* 135:    */   @Transient
/* 136:179 */   private List<RegistroPeso> listaRegistroPeso = new ArrayList();
/* 137:    */   @Transient
/* 138:182 */   private BigDecimal porcentajePorRecibir = BigDecimal.ZERO;
/* 139:    */   
/* 140:    */   public int getId()
/* 141:    */   {
/* 142:198 */     return this.idDetallePedidoProveedor;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public BigDecimal getDescuentoLinea()
/* 146:    */   {
/* 147:207 */     return FuncionesUtiles.redondearBigDecimal(getCantidad().multiply(getDescuento()));
/* 148:    */   }
/* 149:    */   
/* 150:    */   public BigDecimal getPrecioLinea()
/* 151:    */   {
/* 152:216 */     return FuncionesUtiles.redondearBigDecimal(getPrecio().multiply(getCantidad()));
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setPrecioLinea(BigDecimal precioLinea)
/* 156:    */   {
/* 157:226 */     if (getCantidad().compareTo(BigDecimal.ZERO) != 0) {
/* 158:227 */       this.precio = precioLinea.divide(getCantidad(), ParametrosSistema.getNumeroDecimalesPrecioCompra().intValue(), RoundingMode.HALF_UP);
/* 159:    */     }
/* 160:229 */     this.precioLinea = precioLinea;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public BigDecimal getBaseImponible()
/* 164:    */   {
/* 165:240 */     BigDecimal baseImponibleLinea = getPrecioLinea().subtract(getDescuentoLinea());
/* 166:    */     
/* 167:242 */     return baseImponibleLinea;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public BigDecimal getValorImpuestosLinea()
/* 171:    */   {
/* 172:253 */     BigDecimal valorImpuestosLinea = BigDecimal.ZERO;
/* 173:255 */     for (ImpuestoProductoPedidoProveedor ipfp : getListaImpuestoProductoPedidoProveedor()) {
/* 174:256 */       if (!ipfp.isEliminado()) {
/* 175:258 */         valorImpuestosLinea = valorImpuestosLinea.add(ipfp.getImpuestoProducto());
/* 176:    */       }
/* 177:    */     }
/* 178:262 */     return valorImpuestosLinea;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int getIdDetallePedidoProveedor()
/* 182:    */   {
/* 183:267 */     return this.idDetallePedidoProveedor;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setIdDetallePedidoProveedor(int idDetallePedidoProveedor)
/* 187:    */   {
/* 188:271 */     this.idDetallePedidoProveedor = idDetallePedidoProveedor;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public int getIdOrganizacion()
/* 192:    */   {
/* 193:275 */     return this.idOrganizacion;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setIdOrganizacion(int idOrganizacion)
/* 197:    */   {
/* 198:279 */     this.idOrganizacion = idOrganizacion;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public int getIdSucursal()
/* 202:    */   {
/* 203:283 */     return this.idSucursal;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setIdSucursal(int idSucursal)
/* 207:    */   {
/* 208:287 */     this.idSucursal = idSucursal;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public PedidoProveedor getPedidoProveedor()
/* 212:    */   {
/* 213:291 */     return this.pedidoProveedor;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 217:    */   {
/* 218:295 */     this.pedidoProveedor = pedidoProveedor;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Producto getProducto()
/* 222:    */   {
/* 223:299 */     return this.producto;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setProducto(Producto producto)
/* 227:    */   {
/* 228:303 */     this.producto = producto;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public BigDecimal getCantidad()
/* 232:    */   {
/* 233:307 */     return this.cantidad;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setCantidad(BigDecimal cantidad)
/* 237:    */   {
/* 238:311 */     this.cantidad = cantidad;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public BigDecimal getPrecio()
/* 242:    */   {
/* 243:315 */     return FuncionesUtiles.redondearBigDecimal(this.precio, ParametrosSistema.getNumeroDecimalesPrecioCompra().intValue());
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setPrecio(BigDecimal precio)
/* 247:    */   {
/* 248:319 */     this.precio = precio;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public BigDecimal getDescuento()
/* 252:    */   {
/* 253:323 */     return this.descuento;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setDescuento(BigDecimal descuento)
/* 257:    */   {
/* 258:327 */     this.descuento = FuncionesUtiles.redondearBigDecimal(descuento, 4);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String getDescripcion()
/* 262:    */   {
/* 263:331 */     return this.descripcion;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setDescripcion(String descripcion)
/* 267:    */   {
/* 268:335 */     this.descripcion = descripcion;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String getUsuarioCreacion()
/* 272:    */   {
/* 273:339 */     return this.usuarioCreacion;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setUsuarioCreacion(String usuarioCreacion)
/* 277:    */   {
/* 278:343 */     this.usuarioCreacion = usuarioCreacion;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public Date getFechaCreacion()
/* 282:    */   {
/* 283:347 */     return this.fechaCreacion;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setFechaCreacion(Date fechaCreacion)
/* 287:    */   {
/* 288:351 */     this.fechaCreacion = fechaCreacion;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Date getFechaModificacion()
/* 292:    */   {
/* 293:355 */     return this.fechaModificacion;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setFechaModificacion(Date fechaModificacion)
/* 297:    */   {
/* 298:359 */     this.fechaModificacion = fechaModificacion;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public String getUsuarioModificacion()
/* 302:    */   {
/* 303:363 */     return this.usuarioModificacion;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setUsuarioModificacion(String usuarioModificacion)
/* 307:    */   {
/* 308:367 */     this.usuarioModificacion = usuarioModificacion;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public List<ImpuestoProductoPedidoProveedor> getListaImpuestoProductoPedidoProveedor()
/* 312:    */   {
/* 313:376 */     return this.listaImpuestoProductoPedidoProveedor;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setListaImpuestoProductoPedidoProveedor(List<ImpuestoProductoPedidoProveedor> listaImpuestoProductoPedidoProveedor)
/* 317:    */   {
/* 318:386 */     this.listaImpuestoProductoPedidoProveedor = listaImpuestoProductoPedidoProveedor;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public List<DetalleRecepcionProveedor> getListaDetalleRecepcionProveedor()
/* 322:    */   {
/* 323:390 */     return this.listaDetalleRecepcionProveedor;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setListaDetalleRecepcionProveedor(List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor)
/* 327:    */   {
/* 328:394 */     this.listaDetalleRecepcionProveedor = listaDetalleRecepcionProveedor;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public boolean isIndicadorImpuestos()
/* 332:    */   {
/* 333:398 */     return this.indicadorImpuestos;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setIndicadorImpuestos(boolean indicadorImpuestos)
/* 337:    */   {
/* 338:402 */     this.indicadorImpuestos = indicadorImpuestos;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public BigDecimal getCantidadPorFacturar()
/* 342:    */   {
/* 343:411 */     return this.cantidadPorFacturar;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setCantidadPorFacturar(BigDecimal cantidadPorFacturar)
/* 347:    */   {
/* 348:421 */     this.cantidadPorFacturar = cantidadPorFacturar;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public BigDecimal getCantidadFacturada()
/* 352:    */   {
/* 353:430 */     this.cantidadFacturada = this.cantidad.subtract(this.cantidadPorFacturar);
/* 354:431 */     return this.cantidadFacturada;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setCantidadFacturada(BigDecimal cantidadFacturada)
/* 358:    */   {
/* 359:441 */     this.cantidadFacturada = cantidadFacturada;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public BigDecimal getCantidadPorRecibir()
/* 363:    */   {
/* 364:450 */     return this.cantidadPorRecibir;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void setCantidadPorRecibir(BigDecimal cantidadPorRecibir)
/* 368:    */   {
/* 369:460 */     this.cantidadPorRecibir = cantidadPorRecibir;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public BigDecimal getCantidadRecibida()
/* 373:    */   {
/* 374:469 */     this.cantidadRecibida = this.cantidad.subtract(this.cantidadPorRecibir);
/* 375:470 */     return this.cantidadRecibida;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public void setCantidadRecibida(BigDecimal cantidadRecibida)
/* 379:    */   {
/* 380:480 */     this.cantidadRecibida = cantidadRecibida;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public Unidad getUnidadCompra()
/* 384:    */   {
/* 385:489 */     return this.unidadCompra;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public void setUnidadCompra(Unidad unidadCompra)
/* 389:    */   {
/* 390:499 */     this.unidadCompra = unidadCompra;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public BigDecimal getPorcentajeDescuento()
/* 394:    */   {
/* 395:508 */     return this.porcentajeDescuento;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void setPorcentajeDescuento(BigDecimal porcentajeDescuento)
/* 399:    */   {
/* 400:518 */     this.porcentajeDescuento = porcentajeDescuento;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public DimensionContable getDimensionContable1()
/* 404:    */   {
/* 405:522 */     return this.dimensionContable1;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public void setDimensionContable1(DimensionContable dimensionContable1)
/* 409:    */   {
/* 410:526 */     this.dimensionContable1 = dimensionContable1;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public DimensionContable getDimensionContable2()
/* 414:    */   {
/* 415:530 */     return this.dimensionContable2;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public void setDimensionContable2(DimensionContable dimensionContable2)
/* 419:    */   {
/* 420:534 */     this.dimensionContable2 = dimensionContable2;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public DimensionContable getDimensionContable3()
/* 424:    */   {
/* 425:538 */     return this.dimensionContable3;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void setDimensionContable3(DimensionContable dimensionContable3)
/* 429:    */   {
/* 430:542 */     this.dimensionContable3 = dimensionContable3;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public DimensionContable getDimensionContable4()
/* 434:    */   {
/* 435:546 */     return this.dimensionContable4;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void setDimensionContable4(DimensionContable dimensionContable4)
/* 439:    */   {
/* 440:550 */     this.dimensionContable4 = dimensionContable4;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public DimensionContable getDimensionContable5()
/* 444:    */   {
/* 445:554 */     return this.dimensionContable5;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void setDimensionContable5(DimensionContable dimensionContable5)
/* 449:    */   {
/* 450:558 */     this.dimensionContable5 = dimensionContable5;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public BigDecimal getPrecioAnterior()
/* 454:    */   {
/* 455:562 */     return this.precioAnterior;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void setPrecioAnterior(BigDecimal precioAnterior)
/* 459:    */   {
/* 460:566 */     this.precioAnterior = precioAnterior;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public BigDecimal getPrecioLineaAnterior()
/* 464:    */   {
/* 465:570 */     return this.precioLineaAnterior;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public void setPrecioLineaAnterior(BigDecimal precioLineaAnterior)
/* 469:    */   {
/* 470:574 */     this.precioLineaAnterior = precioLineaAnterior;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public DetalleSolicitudCompra getDetalleSolicitudCompra()
/* 474:    */   {
/* 475:578 */     return this.detalleSolicitudCompra;
/* 476:    */   }
/* 477:    */   
/* 478:    */   public void setDetalleSolicitudCompra(DetalleSolicitudCompra detalleSolicitudCompra)
/* 479:    */   {
/* 480:582 */     this.detalleSolicitudCompra = detalleSolicitudCompra;
/* 481:    */   }
/* 482:    */   
/* 483:    */   public int getCantidadRecepcionesMostrar()
/* 484:    */   {
/* 485:586 */     int cantidad = 0;
/* 486:587 */     if (this.listaDetalleRecepcionProveedor != null) {
/* 487:588 */       if (this.listaDetalleRecepcionProveedor.size() > 10) {
/* 488:589 */         cantidad = 10;
/* 489:    */       } else {
/* 490:591 */         cantidad = this.listaDetalleRecepcionProveedor.size();
/* 491:    */       }
/* 492:    */     }
/* 493:594 */     return cantidad;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public List<RegistroPeso> getListaRegistroPeso()
/* 497:    */   {
/* 498:598 */     return this.listaRegistroPeso;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public void setListaRegistroPeso(List<RegistroPeso> listaRegistroPeso)
/* 502:    */   {
/* 503:602 */     this.listaRegistroPeso = listaRegistroPeso;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public BigDecimal getPorcentajePorRecibir()
/* 507:    */   {
/* 508:606 */     if (this.cantidadPorRecibir.compareTo(BigDecimal.ZERO) > 0) {
/* 509:607 */       this.porcentajePorRecibir = this.cantidadPorRecibir.multiply(new BigDecimal(100)).divide(this.cantidad, 2, RoundingMode.HALF_UP);
/* 510:    */     }
/* 511:609 */     return this.porcentajePorRecibir;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void setPorcentajePorRecibir(BigDecimal porcentajePorRecibir)
/* 515:    */   {
/* 516:613 */     this.porcentajePorRecibir = porcentajePorRecibir;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public BigDecimal getPrecioNuevo()
/* 520:    */   {
/* 521:617 */     return this.precioNuevo;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public void setPrecioNuevo(BigDecimal precioNuevo)
/* 525:    */   {
/* 526:621 */     this.precioNuevo = precioNuevo;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public boolean isIndicadorAprobado()
/* 530:    */   {
/* 531:625 */     return this.indicadorAprobado;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public void setIndicadorAprobado(boolean indicadorAprobado)
/* 535:    */   {
/* 536:629 */     this.indicadorAprobado = indicadorAprobado;
/* 537:    */   }
/* 538:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallePedidoProveedor
 * JD-Core Version:    0.7.0.1
 */