/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.mantenimiento.MaterialOrdenTrabajoMantenimiento;
/*   4:    */ import com.asinfo.as2.entities.produccion.Maquina;
/*   5:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.OneToOne;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Transient;
/*  22:    */ import javax.validation.constraints.DecimalMin;
/*  23:    */ import javax.validation.constraints.Digits;
/*  24:    */ import javax.validation.constraints.Min;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ import org.hibernate.annotations.ColumnDefault;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="detalle_movimiento_inventario", indexes={@javax.persistence.Index(columnList="id_movimiento_inventario"), @javax.persistence.Index(columnList="id_producto")})
/*  31:    */ public class DetalleMovimientoInventario
/*  32:    */   extends EntidadBase
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="detalle_movimiento_inventario", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_movimiento_inventario")
/*  38:    */   @Column(name="id_detalle_movimiento_inventario", unique=true, nullable=false)
/*  39:    */   private int idDetalleMovimientoInventario;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_bodega_destino", nullable=true)
/*  42:    */   private Bodega bodegaDestino;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_bodega_origen", nullable=false)
/*  45:    */   private Bodega bodegaOrigen;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_movimiento_inventario", nullable=true)
/*  48:    */   private MovimientoInventario movimientoInventario;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_producto", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Producto producto;
/*  53:    */   @Column(name="id_organizacion", nullable=false)
/*  54:    */   private int idOrganizacion;
/*  55:    */   @Column(name="id_sucursal", nullable=false)
/*  56:    */   private int idSucursal;
/*  57:    */   @Column(name="cantidad", precision=12, scale=4)
/*  58:    */   @DecimalMin("0.0001")
/*  59: 83 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  60:    */   @Column(name="cantidad_unidad_informativa", nullable=true, precision=12, scale=2)
/*  61:    */   @Digits(integer=12, fraction=2)
/*  62:    */   @Min(0L)
/*  63:    */   private BigDecimal cantidadUnidadInformativa;
/*  64:    */   @Column(name="cantidad_unidad_informativa_recibida", nullable=true, precision=12, scale=2)
/*  65:    */   @Digits(integer=12, fraction=2)
/*  66:    */   @Min(0L)
/*  67:    */   @ColumnDefault("0")
/*  68:    */   private BigDecimal cantidadUnidadInformativaRecibida;
/*  69:    */   @Column(name="cantidad_devuelta", precision=12, scale=2)
/*  70:    */   @DecimalMin("0.00")
/*  71: 99 */   private BigDecimal cantidadDevuelta = BigDecimal.ZERO;
/*  72:    */   @Column(name="cantidad_pesada", precision=12, scale=2)
/*  73:    */   @DecimalMin("0.00")
/*  74:103 */   private BigDecimal cantidadPesada = BigDecimal.ZERO;
/*  75:    */   @Column(name="costo", precision=12, scale=2)
/*  76:107 */   private BigDecimal costo = BigDecimal.ZERO;
/*  77:    */   @Column(name="descripcion", length=500)
/*  78:    */   @Size(max=500)
/*  79:    */   private String descripcion;
/*  80:    */   @OneToOne(mappedBy="detalleMovimientoInventario", fetch=FetchType.LAZY)
/*  81:    */   private InventarioProducto inventarioProducto;
/*  82:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  83:    */   @JoinColumn(name="id_unidad_conversion", nullable=false)
/*  84:    */   private Unidad unidadConversion;
/*  85:    */   @Column(name="cantidad_origen", precision=12, scale=4, nullable=true)
/*  86:    */   @Min(0L)
/*  87:121 */   private BigDecimal cantidadOrigen = BigDecimal.ZERO;
/*  88:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  89:    */   @JoinColumn(name="id_destino_costo", nullable=true)
/*  90:    */   private DestinoCosto destinoCosto;
/*  91:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  92:    */   @JoinColumn(name="id_detalle_orden_salida_material", nullable=true)
/*  93:    */   private DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial;
/*  94:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  95:    */   @JoinColumn(name="id__material_orden_trabajo_mantenimiento", nullable=true)
/*  96:    */   private MaterialOrdenTrabajoMantenimiento materialOrdenTrabajoMantenimiento;
/*  97:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  98:    */   @JoinColumn(name="id_detalle_movimiento_inventario_padre", nullable=true)
/*  99:    */   private DetalleMovimientoInventario detalleMovimientoInventarioPadre;
/* 100:    */   @Column(name="indicador_recibido", nullable=true)
/* 101:143 */   private Boolean indicadorRecibido = Boolean.valueOf(false);
/* 102:    */   @Column(name="indicador_cumple_calidad", nullable=false)
/* 103:    */   @ColumnDefault("'0'")
/* 104:    */   private boolean indicadorCumpleCalidad;
/* 105:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 106:    */   @JoinColumn(name="id_lote")
/* 107:    */   private Lote lote;
/* 108:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleMovimientoInventario")
/* 109:154 */   private List<LecturaBalanza> listaLecturaBalanza = new ArrayList();
/* 110:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 111:    */   @JoinColumn(name="id_transformacion_automatica", nullable=true)
/* 112:    */   private MovimientoInventario transformacionAutomatica;
/* 113:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 114:    */   @JoinColumn(name="id_maquina", nullable=true)
/* 115:    */   private Maquina maquina;
/* 116:    */   @Column(name="descripcion_2", length=500)
/* 117:    */   @Size(max=500)
/* 118:    */   private String descripcion2;
/* 119:    */   @Transient
/* 120:169 */   private List<Unidad> traListaUnidadConversion = new ArrayList();
/* 121:    */   @Transient
/* 122:172 */   private List<Bodega> listaBodegasTrabajoProducto = new ArrayList();
/* 123:    */   @Transient
/* 124:    */   private BigDecimal traPrecio;
/* 125:    */   @Transient
/* 126:    */   private BigDecimal traCantidadADevolver;
/* 127:    */   @Transient
/* 128:    */   private BigDecimal traPrecioDevolucionTotal;
/* 129:    */   @Transient
/* 130:    */   private BigDecimal traImpuestoDevolucionTotal;
/* 131:    */   @Transient
/* 132:    */   private BigDecimal traDescuentoADevolver;
/* 133:    */   @Transient
/* 134:190 */   private BigDecimal traCantidadCoversion = BigDecimal.ZERO;
/* 135:    */   @Transient
/* 136:    */   private boolean seleccionado;
/* 137:    */   @Transient
/* 138:    */   private Lote loteTransformacion;
/* 139:    */   @Transient
/* 140:    */   private BigDecimal cantidadTransformacion;
/* 141:    */   @Transient
/* 142:    */   private Bodega bodegaTransformacion;
/* 143:    */   @Transient
/* 144:206 */   private BigDecimal saldo = BigDecimal.ZERO;
/* 145:    */   
/* 146:    */   public DetalleMovimientoInventario() {}
/* 147:    */   
/* 148:    */   public DetalleMovimientoInventario(Producto producto, BigDecimal cantidad)
/* 149:    */   {
/* 150:213 */     this.producto = producto;
/* 151:214 */     this.cantidad = cantidad;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int getId()
/* 155:    */   {
/* 156:224 */     return this.idDetalleMovimientoInventario;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public int getIdDetalleMovimientoInventario()
/* 160:    */   {
/* 161:228 */     return this.idDetalleMovimientoInventario;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setIdDetalleMovimientoInventario(int idDetalleMovimientoInventario)
/* 165:    */   {
/* 166:232 */     this.idDetalleMovimientoInventario = idDetalleMovimientoInventario;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Bodega getBodegaDestino()
/* 170:    */   {
/* 171:241 */     return this.bodegaDestino;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setBodegaDestino(Bodega bodegaDestino)
/* 175:    */   {
/* 176:251 */     this.bodegaDestino = bodegaDestino;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Bodega getBodegaOrigen()
/* 180:    */   {
/* 181:260 */     return this.bodegaOrigen;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setBodegaOrigen(Bodega bodegaOrigen)
/* 185:    */   {
/* 186:270 */     this.bodegaOrigen = bodegaOrigen;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public MovimientoInventario getMovimientoInventario()
/* 190:    */   {
/* 191:274 */     return this.movimientoInventario;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setMovimientoInventario(MovimientoInventario movimientoInventario)
/* 195:    */   {
/* 196:278 */     this.movimientoInventario = movimientoInventario;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Producto getProducto()
/* 200:    */   {
/* 201:282 */     return this.producto;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setProducto(Producto producto)
/* 205:    */   {
/* 206:286 */     this.producto = producto;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public int getIdOrganizacion()
/* 210:    */   {
/* 211:290 */     return this.idOrganizacion;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setIdOrganizacion(int idOrganizacion)
/* 215:    */   {
/* 216:294 */     this.idOrganizacion = idOrganizacion;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public int getIdSucursal()
/* 220:    */   {
/* 221:298 */     return this.idSucursal;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setIdSucursal(int idSucursal)
/* 225:    */   {
/* 226:302 */     this.idSucursal = idSucursal;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public BigDecimal getCantidad()
/* 230:    */   {
/* 231:306 */     if (this.cantidad == null) {
/* 232:307 */       this.cantidad = BigDecimal.ZERO;
/* 233:    */     }
/* 234:309 */     return this.cantidad;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setCantidad(BigDecimal cantidad)
/* 238:    */   {
/* 239:313 */     this.cantidad = cantidad;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public String getDescripcion()
/* 243:    */   {
/* 244:317 */     return this.descripcion;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setDescripcion(String descripcion)
/* 248:    */   {
/* 249:321 */     this.descripcion = descripcion;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public BigDecimal getTraPrecio()
/* 253:    */   {
/* 254:330 */     if (this.traPrecio == null) {
/* 255:331 */       this.traPrecio = BigDecimal.ZERO;
/* 256:    */     }
/* 257:333 */     return this.traPrecio;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setTraPrecio(BigDecimal traPrecio)
/* 261:    */   {
/* 262:343 */     this.traPrecio = traPrecio;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public BigDecimal getTraCantidadADevolver()
/* 266:    */   {
/* 267:352 */     if (this.traCantidadADevolver == null) {
/* 268:353 */       this.traCantidadADevolver = BigDecimal.ZERO;
/* 269:    */     }
/* 270:355 */     return this.traCantidadADevolver;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setTraCantidadADevolver(BigDecimal traCantidadADevolver)
/* 274:    */   {
/* 275:365 */     this.traCantidadADevolver = traCantidadADevolver;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public BigDecimal getTraPrecioDevolucionTotal()
/* 279:    */   {
/* 280:374 */     this.traPrecioDevolucionTotal = getTraPrecio().subtract(getTraDescuentoADevolver()).multiply(this.cantidad);
/* 281:375 */     if (this.traPrecioDevolucionTotal == null) {
/* 282:376 */       this.traPrecioDevolucionTotal = BigDecimal.ZERO;
/* 283:    */     }
/* 284:378 */     return this.traPrecioDevolucionTotal;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setTraPrecioDevolucionTotal(BigDecimal traPrecioDevolucionTotal)
/* 288:    */   {
/* 289:388 */     this.traPrecioDevolucionTotal = traPrecioDevolucionTotal;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public BigDecimal getTraImpuestoDevolucionTotal()
/* 293:    */   {
/* 294:397 */     if (this.traImpuestoDevolucionTotal == null) {
/* 295:398 */       this.traImpuestoDevolucionTotal = BigDecimal.ZERO;
/* 296:    */     } else {
/* 297:400 */       this.traImpuestoDevolucionTotal = FuncionesUtiles.redondearBigDecimal(this.traImpuestoDevolucionTotal);
/* 298:    */     }
/* 299:402 */     return this.traImpuestoDevolucionTotal;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setTraImpuestoDevolucionTotal(BigDecimal traImpuestoDevolucionTotal)
/* 303:    */   {
/* 304:412 */     this.traImpuestoDevolucionTotal = traImpuestoDevolucionTotal;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public BigDecimal getTraDescuentoADevolver()
/* 308:    */   {
/* 309:421 */     if (this.traDescuentoADevolver == null) {
/* 310:422 */       this.traDescuentoADevolver = BigDecimal.ZERO;
/* 311:    */     }
/* 312:424 */     return this.traDescuentoADevolver;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setTraDescuentoADevolver(BigDecimal traDescuentoADevolver)
/* 316:    */   {
/* 317:434 */     this.traDescuentoADevolver = traDescuentoADevolver;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public InventarioProducto getInventarioProducto()
/* 321:    */   {
/* 322:443 */     return this.inventarioProducto;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/* 326:    */   {
/* 327:453 */     this.inventarioProducto = inventarioProducto;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public List<Unidad> getTraListaUnidadConversion()
/* 331:    */   {
/* 332:457 */     return this.traListaUnidadConversion;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setTraListaUnidadConversion(List<Unidad> traListaUnidadConversion)
/* 336:    */   {
/* 337:461 */     this.traListaUnidadConversion = traListaUnidadConversion;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public Unidad getUnidadConversion()
/* 341:    */   {
/* 342:465 */     return this.unidadConversion;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setUnidadConversion(Unidad unidadConversion)
/* 346:    */   {
/* 347:469 */     this.unidadConversion = unidadConversion;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public BigDecimal getCantidadOrigen()
/* 351:    */   {
/* 352:473 */     return this.cantidadOrigen;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setCantidadOrigen(BigDecimal cantidadOrigen)
/* 356:    */   {
/* 357:477 */     this.cantidadOrigen = cantidadOrigen;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public BigDecimal getCosto()
/* 361:    */   {
/* 362:484 */     return this.costo;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setCosto(BigDecimal costo)
/* 366:    */   {
/* 367:492 */     this.costo = costo;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setEliminado(boolean eliminado)
/* 371:    */   {
/* 372:502 */     super.setEliminado(eliminado);
/* 373:503 */     if (this.inventarioProducto != null) {
/* 374:504 */       this.inventarioProducto.setEliminado(eliminado);
/* 375:    */     }
/* 376:    */   }
/* 377:    */   
/* 378:    */   public BigDecimal getCostoLinea()
/* 379:    */   {
/* 380:510 */     return FuncionesUtiles.redondearBigDecimal(getCosto().multiply(getCantidad()), 4);
/* 381:    */   }
/* 382:    */   
/* 383:    */   public BigDecimal getSaldo()
/* 384:    */   {
/* 385:519 */     return this.saldo;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public void setSaldo(BigDecimal saldo)
/* 389:    */   {
/* 390:529 */     this.saldo = saldo;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public DestinoCosto getDestinoCosto()
/* 394:    */   {
/* 395:533 */     return this.destinoCosto;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void setDestinoCosto(DestinoCosto destinoCosto)
/* 399:    */   {
/* 400:537 */     this.destinoCosto = destinoCosto;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public DetalleOrdenSalidaMaterial getDetalleOrdenSalidaMaterial()
/* 404:    */   {
/* 405:541 */     return this.detalleOrdenSalidaMaterial;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public void setDetalleOrdenSalidaMaterial(DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial)
/* 409:    */   {
/* 410:545 */     this.detalleOrdenSalidaMaterial = detalleOrdenSalidaMaterial;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public BigDecimal getTraCantidadCoversion()
/* 414:    */   {
/* 415:554 */     return this.traCantidadCoversion;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public void setTraCantidadCoversion(BigDecimal traCantidadCoversion)
/* 419:    */   {
/* 420:564 */     this.traCantidadCoversion = traCantidadCoversion;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public boolean isIndicadorRecibido()
/* 424:    */   {
/* 425:568 */     if (this.indicadorRecibido == null) {
/* 426:569 */       this.indicadorRecibido = Boolean.valueOf(false);
/* 427:    */     }
/* 428:571 */     return this.indicadorRecibido.booleanValue();
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void setIndicadorRecibido(boolean indicadorRecibido)
/* 432:    */   {
/* 433:575 */     this.indicadorRecibido = Boolean.valueOf(indicadorRecibido);
/* 434:    */   }
/* 435:    */   
/* 436:    */   public List<LecturaBalanza> getListaLecturaBalanza()
/* 437:    */   {
/* 438:579 */     return this.listaLecturaBalanza;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void setListaLecturaBalanza(List<LecturaBalanza> listaLecturaBalanza)
/* 442:    */   {
/* 443:583 */     this.listaLecturaBalanza = listaLecturaBalanza;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public BigDecimal getCantidadPesada()
/* 447:    */   {
/* 448:587 */     if (this.cantidadPesada == null) {
/* 449:588 */       this.cantidadPesada = BigDecimal.ZERO;
/* 450:    */     }
/* 451:590 */     return this.cantidadPesada;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setCantidadPesada(BigDecimal cantidadPesada)
/* 455:    */   {
/* 456:594 */     this.cantidadPesada = cantidadPesada;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public Lote getLote()
/* 460:    */   {
/* 461:598 */     return this.lote;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setLote(Lote lote)
/* 465:    */   {
/* 466:602 */     this.lote = lote;
/* 467:603 */     if (getInventarioProducto() != null) {
/* 468:604 */       getInventarioProducto().setLote(lote);
/* 469:    */     }
/* 470:    */   }
/* 471:    */   
/* 472:    */   public MaterialOrdenTrabajoMantenimiento getMaterialOrdenTrabajoMantenimiento()
/* 473:    */   {
/* 474:609 */     return this.materialOrdenTrabajoMantenimiento;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public void setMaterialOrdenTrabajoMantenimiento(MaterialOrdenTrabajoMantenimiento materialOrdenTrabajoMantenimiento)
/* 478:    */   {
/* 479:613 */     this.materialOrdenTrabajoMantenimiento = materialOrdenTrabajoMantenimiento;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public BigDecimal getCantidadDevuelta()
/* 483:    */   {
/* 484:617 */     if (this.cantidadDevuelta == null) {
/* 485:618 */       this.cantidadDevuelta = BigDecimal.ZERO;
/* 486:    */     }
/* 487:620 */     return this.cantidadDevuelta;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public void setCantidadDevuelta(BigDecimal cantidadDevuelta)
/* 491:    */   {
/* 492:624 */     this.cantidadDevuelta = cantidadDevuelta;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public BigDecimal getCantidadPorDevolver()
/* 496:    */   {
/* 497:628 */     return getCantidad().subtract(getCantidadDevuelta());
/* 498:    */   }
/* 499:    */   
/* 500:    */   public DetalleMovimientoInventario getDetalleMovimientoInventarioPadre()
/* 501:    */   {
/* 502:632 */     return this.detalleMovimientoInventarioPadre;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void setDetalleMovimientoInventarioPadre(DetalleMovimientoInventario detalleMovimientoInventarioPadre)
/* 506:    */   {
/* 507:636 */     this.detalleMovimientoInventarioPadre = detalleMovimientoInventarioPadre;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public BigDecimal getCantidadUnidadInformativa()
/* 511:    */   {
/* 512:640 */     return this.cantidadUnidadInformativa;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public void setCantidadUnidadInformativa(BigDecimal cantidadUnidadInformativa)
/* 516:    */   {
/* 517:644 */     this.cantidadUnidadInformativa = cantidadUnidadInformativa;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public MovimientoInventario getTransformacionAutomatica()
/* 521:    */   {
/* 522:648 */     return this.transformacionAutomatica;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void setTransformacionAutomatica(MovimientoInventario transformacionAutomatica)
/* 526:    */   {
/* 527:652 */     this.transformacionAutomatica = transformacionAutomatica;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public BigDecimal getCantidadUnidadInformativaRecibida()
/* 531:    */   {
/* 532:656 */     return this.cantidadUnidadInformativaRecibida;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public void setCantidadUnidadInformativaRecibida(BigDecimal cantidadUnidadInformativaRecibida)
/* 536:    */   {
/* 537:660 */     this.cantidadUnidadInformativaRecibida = cantidadUnidadInformativaRecibida;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public List<Bodega> getListaBodegasTrabajoProducto()
/* 541:    */   {
/* 542:664 */     return this.listaBodegasTrabajoProducto;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public void setListaBodegasTrabajoProducto(List<Bodega> listaBodegasTrabajoProducto)
/* 546:    */   {
/* 547:668 */     this.listaBodegasTrabajoProducto = listaBodegasTrabajoProducto;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public Maquina getMaquina()
/* 551:    */   {
/* 552:672 */     return this.maquina;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public void setMaquina(Maquina maquina)
/* 556:    */   {
/* 557:676 */     this.maquina = maquina;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public boolean isSeleccionado()
/* 561:    */   {
/* 562:680 */     return this.seleccionado;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public void setSeleccionado(boolean seleccionado)
/* 566:    */   {
/* 567:684 */     this.seleccionado = seleccionado;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public Lote getLoteTransformacion()
/* 571:    */   {
/* 572:688 */     return this.loteTransformacion;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public void setLoteTransformacion(Lote loteTransformacion)
/* 576:    */   {
/* 577:692 */     this.loteTransformacion = loteTransformacion;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public BigDecimal getCantidadTransformacion()
/* 581:    */   {
/* 582:696 */     return this.cantidadTransformacion;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public void setCantidadTransformacion(BigDecimal cantidadTransformacion)
/* 586:    */   {
/* 587:700 */     this.cantidadTransformacion = cantidadTransformacion;
/* 588:    */   }
/* 589:    */   
/* 590:    */   public Bodega getBodegaTransformacion()
/* 591:    */   {
/* 592:704 */     return this.bodegaTransformacion;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public void setBodegaTransformacion(Bodega bodegaTransformacion)
/* 596:    */   {
/* 597:708 */     this.bodegaTransformacion = bodegaTransformacion;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public boolean isIndicadorCumpleCalidad()
/* 601:    */   {
/* 602:712 */     return this.indicadorCumpleCalidad;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public void setIndicadorCumpleCalidad(boolean indicadorCumpleCalidad)
/* 606:    */   {
/* 607:716 */     this.indicadorCumpleCalidad = indicadorCumpleCalidad;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public BigDecimal getPesoProducto()
/* 611:    */   {
/* 612:720 */     BigDecimal pesoCalculado = this.cantidad;
/* 613:721 */     if ((this.producto != null) && (this.producto.getPeso() != null) && (this.producto.getPeso().compareTo(BigDecimal.ZERO) != 0)) {
/* 614:722 */       pesoCalculado = this.producto.getPeso().multiply(this.cantidad);
/* 615:    */     }
/* 616:724 */     return FuncionesUtiles.redondearBigDecimal(pesoCalculado);
/* 617:    */   }
/* 618:    */   
/* 619:    */   public String getDescripcion2()
/* 620:    */   {
/* 621:728 */     return this.descripcion2;
/* 622:    */   }
/* 623:    */   
/* 624:    */   public void setDescripcion2(String descripcion2)
/* 625:    */   {
/* 626:732 */     this.descripcion2 = descripcion2;
/* 627:    */   }
/* 628:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleMovimientoInventario
 * JD-Core Version:    0.7.0.1
 */