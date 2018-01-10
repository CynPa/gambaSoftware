/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   4:    */ import com.asinfo.as2.utils.EjbUtil;
/*   5:    */ import java.io.Serializable;
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
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.DecimalMin;
/*  22:    */ import javax.validation.constraints.Digits;
/*  23:    */ import javax.validation.constraints.Min;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ import org.hibernate.annotations.ColumnDefault;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="detalle_orden_salida_material")
/*  30:    */ public class DetalleOrdenSalidaMaterial
/*  31:    */   extends EntidadBase
/*  32:    */   implements Serializable
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 571335322178384473L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="detalle_orden_salida_material", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_orden_salida_material")
/*  38:    */   @Column(name="id_detalle_orden_salida_material")
/*  39:    */   private int idDetalleOrdenSalidaMaterial;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private int idOrganizacion;
/*  43:    */   @Column(name="id_sucursal", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private int idSucursal;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_orden_salida_material", nullable=true)
/*  48:    */   private OrdenSalidaMaterial ordenSalidaMaterial;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_producto", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Producto producto;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_bodega", nullable=true)
/*  55:    */   private Bodega bodega;
/*  56:    */   @NotNull
/*  57:    */   @Column(name="cantidad", nullable=false, precision=12, scale=4)
/*  58:    */   @Digits(integer=12, fraction=4)
/*  59:    */   @DecimalMin("0.00")
/*  60: 75 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  61:    */   @Transient
/*  62: 81 */   private BigDecimal cantidadSegunFabricacion = BigDecimal.ZERO;
/*  63:    */   @NotNull
/*  64:    */   @Column(name="cantidad_adicional", nullable=false, precision=12, scale=2)
/*  65:    */   @Digits(integer=10, fraction=2)
/*  66:    */   @DecimalMin("0.00")
/*  67: 85 */   private BigDecimal cantidadAdicional = BigDecimal.ZERO;
/*  68:    */   @NotNull
/*  69:    */   @Column(name="cantidad_despachada", nullable=false, precision=12, scale=2)
/*  70:    */   @Digits(integer=10, fraction=2)
/*  71:    */   @Min(0L)
/*  72: 91 */   private BigDecimal cantidadDespachada = BigDecimal.ZERO;
/*  73:    */   @Column(name="cantidad_a_devolver", nullable=false, precision=12, scale=2)
/*  74:    */   @Digits(integer=10, fraction=2)
/*  75:    */   @Min(0L)
/*  76: 98 */   private BigDecimal cantidadADevolver = BigDecimal.ZERO;
/*  77:    */   @Column(name="cantidad_devuelta", nullable=false, precision=12, scale=2)
/*  78:    */   @Digits(integer=10, fraction=2)
/*  79:    */   @Min(0L)
/*  80:104 */   private BigDecimal cantidadDevuelta = BigDecimal.ZERO;
/*  81:    */   @Column(name="cantidad_anulada_orden_fabricacion", nullable=true, precision=12, scale=2)
/*  82:    */   @Digits(integer=10, fraction=2)
/*  83:    */   @DecimalMin("0.00")
/*  84:109 */   private BigDecimal cantidadAnuladaOrdenFabricacion = BigDecimal.ZERO;
/*  85:    */   @NotNull
/*  86:    */   @Column(name="cantidad_utilizada", nullable=false, precision=12, scale=2)
/*  87:    */   @Digits(integer=10, fraction=2)
/*  88:    */   @Min(0L)
/*  89:114 */   private BigDecimal cantidadUtilizada = BigDecimal.ZERO;
/*  90:    */   @Column(name="cantidad_desecho", nullable=true, precision=12, scale=2)
/*  91:    */   @Digits(integer=10, fraction=2)
/*  92:    */   @Min(0L)
/*  93:120 */   private BigDecimal cantidadDesecho = BigDecimal.ZERO;
/*  94:    */   @Column(name="cantidad_unidad_informativa_descpacho", nullable=true, precision=12, scale=2)
/*  95:    */   @Digits(integer=12, fraction=2)
/*  96:    */   @Min(0L)
/*  97:    */   private BigDecimal cantidadUnidadInformativaDespacho;
/*  98:    */   @Column(name="cantidad_unidad_informativa_devolucion", nullable=true, precision=12, scale=2)
/*  99:    */   @Digits(integer=12, fraction=2)
/* 100:    */   @Min(0L)
/* 101:    */   private BigDecimal cantidadUnidadInformativaDevolucion;
/* 102:    */   @Column(name="cantidad_unidad_informativa_desecho", nullable=true, precision=12, scale=2)
/* 103:    */   @Digits(integer=12, fraction=2)
/* 104:    */   @Min(0L)
/* 105:    */   private BigDecimal cantidadUnidadInformativaDesecho;
/* 106:    */   @Column(name="descripcion", nullable=true, length=200)
/* 107:    */   @Size(max=200)
/* 108:    */   private String descripcion;
/* 109:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 110:    */   @JoinColumn(name="id_orden_fabricacion", nullable=true)
/* 111:    */   private OrdenFabricacion ordenFabricacion;
/* 112:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 113:    */   @JoinColumn(name="id_unidad", nullable=false)
/* 114:    */   private Unidad unidad;
/* 115:    */   @Column(name="indicador_automatico", nullable=false)
/* 116:    */   private boolean indicadorAutomatico;
/* 117:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 118:    */   @JoinColumn(name="id_destino_costo", nullable=true)
/* 119:    */   private DestinoCosto destinoCosto;
/* 120:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleOrdenSalidaMaterial")
/* 121:159 */   private List<DetalleOrdenSalidaMaterialOrdenFabricacion> listaDetalleOrdenSalidaMaterialOrdenFabricacion = new ArrayList();
/* 122:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleOrdenSalidaMaterial")
/* 123:162 */   private List<LecturaBalanza> listaLecturaBalanza = new ArrayList();
/* 124:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleOrdenSalidaMaterial")
/* 125:166 */   private List<DetalleMovimientoInventario> listaDetalleConsumoBodega = new ArrayList();
/* 126:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 127:    */   @JoinColumn(name="id_lote", nullable=true)
/* 128:    */   private Lote lote;
/* 129:    */   @Column(name="indicador_consumo_directo", nullable=false)
/* 130:    */   @NotNull
/* 131:    */   @ColumnDefault("'0'")
/* 132:173 */   private boolean indicadorConsumoDirecto = false;
/* 133:    */   @Transient
/* 134:    */   private BigDecimal saldoBodegaTrabajo;
/* 135:    */   @Transient
/* 136:181 */   private BigDecimal traCantidadDespachada = null;
/* 137:    */   @Transient
/* 138:    */   private boolean traEditarCopia;
/* 139:    */   
/* 140:    */   public int getId()
/* 141:    */   {
/* 142:192 */     return this.idDetalleOrdenSalidaMaterial;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getIdDetalleOrdenSalidaMaterial()
/* 146:    */   {
/* 147:201 */     return this.idDetalleOrdenSalidaMaterial;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setIdDetalleOrdenSalidaMaterial(int idDetalleOrdenSalidaMaterial)
/* 151:    */   {
/* 152:211 */     this.idDetalleOrdenSalidaMaterial = idDetalleOrdenSalidaMaterial;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int getIdOrganizacion()
/* 156:    */   {
/* 157:220 */     return this.idOrganizacion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setIdOrganizacion(int idOrganizacion)
/* 161:    */   {
/* 162:230 */     this.idOrganizacion = idOrganizacion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public int getIdSucursal()
/* 166:    */   {
/* 167:239 */     return this.idSucursal;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setIdSucursal(int idSucursal)
/* 171:    */   {
/* 172:249 */     this.idSucursal = idSucursal;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public OrdenSalidaMaterial getOrdenSalidaMaterial()
/* 176:    */   {
/* 177:258 */     return this.ordenSalidaMaterial;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 181:    */   {
/* 182:268 */     this.ordenSalidaMaterial = ordenSalidaMaterial;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Producto getProducto()
/* 186:    */   {
/* 187:277 */     return this.producto;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setProducto(Producto producto)
/* 191:    */   {
/* 192:287 */     this.producto = producto;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public Bodega getBodega()
/* 196:    */   {
/* 197:294 */     return this.bodega;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setBodega(Bodega bodega)
/* 201:    */   {
/* 202:302 */     this.bodega = bodega;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public BigDecimal getCantidad()
/* 206:    */   {
/* 207:311 */     return this.cantidad;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setCantidad(BigDecimal cantidad)
/* 211:    */   {
/* 212:321 */     this.cantidad = cantidad;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public BigDecimal getCantidadDespachada()
/* 216:    */   {
/* 217:328 */     return this.cantidadDespachada;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setCantidadDespachada(BigDecimal cantidadDespachada)
/* 221:    */   {
/* 222:336 */     this.cantidadDespachada = cantidadDespachada;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public BigDecimal getCantidadDevuelta()
/* 226:    */   {
/* 227:343 */     return this.cantidadDevuelta;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setCantidadDevuelta(BigDecimal cantidadDevuelta)
/* 231:    */   {
/* 232:351 */     this.cantidadDevuelta = cantidadDevuelta;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public BigDecimal getCantidadPorUtilizar()
/* 236:    */   {
/* 237:355 */     BigDecimal cantidadPorUtilizar = this.cantidad.subtract(this.cantidadUtilizada);
/* 238:356 */     return cantidadPorUtilizar.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : cantidadPorUtilizar;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public String getDescripcion()
/* 242:    */   {
/* 243:365 */     return this.descripcion;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setDescripcion(String descripcion)
/* 247:    */   {
/* 248:375 */     this.descripcion = descripcion;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public OrdenFabricacion getOrdenFabricacion()
/* 252:    */   {
/* 253:379 */     return this.ordenFabricacion;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 257:    */   {
/* 258:383 */     this.ordenFabricacion = ordenFabricacion;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public Unidad getUnidad()
/* 262:    */   {
/* 263:392 */     return this.unidad;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setUnidad(Unidad unidad)
/* 267:    */   {
/* 268:402 */     this.unidad = unidad;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public boolean isIndicadorAutomatico()
/* 272:    */   {
/* 273:406 */     return this.indicadorAutomatico;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setIndicadorAutomatico(boolean indicadorAutomatico)
/* 277:    */   {
/* 278:410 */     this.indicadorAutomatico = indicadorAutomatico;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public List<LecturaBalanza> getListaLecturaBalanza()
/* 282:    */   {
/* 283:414 */     return this.listaLecturaBalanza;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setListaLecturaBalanza(List<LecturaBalanza> listaLecturaBalanza)
/* 287:    */   {
/* 288:418 */     this.listaLecturaBalanza = listaLecturaBalanza;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public BigDecimal getCantidadUtilizada()
/* 292:    */   {
/* 293:422 */     return this.cantidadUtilizada;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setCantidadUtilizada(BigDecimal cantidadUtilizada)
/* 297:    */   {
/* 298:426 */     this.cantidadUtilizada = cantidadUtilizada;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List<DetalleOrdenSalidaMaterialOrdenFabricacion> getListaDetalleOrdenSalidaMaterialOrdenFabricacion()
/* 302:    */   {
/* 303:430 */     return this.listaDetalleOrdenSalidaMaterialOrdenFabricacion;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setListaDetalleOrdenSalidaMaterialOrdenFabricacion(List<DetalleOrdenSalidaMaterialOrdenFabricacion> listaDetalleOrdenSalidaMaterialOrdenFabricacion)
/* 307:    */   {
/* 308:435 */     this.listaDetalleOrdenSalidaMaterialOrdenFabricacion = listaDetalleOrdenSalidaMaterialOrdenFabricacion;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public BigDecimal getCantidadADevolver()
/* 312:    */   {
/* 313:439 */     return this.cantidadADevolver;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setCantidadADevolver(BigDecimal cantidadADevolver)
/* 317:    */   {
/* 318:443 */     this.cantidadADevolver = cantidadADevolver;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public BigDecimal getCantidadAdicional()
/* 322:    */   {
/* 323:447 */     return this.cantidadAdicional;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setCantidadAdicional(BigDecimal cantidadAdicional)
/* 327:    */   {
/* 328:451 */     this.cantidadAdicional = cantidadAdicional;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public BigDecimal getSaldoBodegaTrabajo()
/* 332:    */   {
/* 333:455 */     return this.saldoBodegaTrabajo;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setSaldoBodegaTrabajo(BigDecimal saldoBodegaTrabajo)
/* 337:    */   {
/* 338:459 */     this.saldoBodegaTrabajo = saldoBodegaTrabajo;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public BigDecimal getCantidadSegunFabricacion()
/* 342:    */   {
/* 343:463 */     return this.cantidadSegunFabricacion;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setCantidadSegunFabricacion(BigDecimal cantidadSegunFabricacion)
/* 347:    */   {
/* 348:467 */     this.cantidadSegunFabricacion = cantidadSegunFabricacion;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public List<DetalleMovimientoInventario> getListaDetalleConsumoBodega()
/* 352:    */   {
/* 353:471 */     return this.listaDetalleConsumoBodega;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setListaDetalleConsumoBodega(List<DetalleMovimientoInventario> listaDetalleConsumoBodega)
/* 357:    */   {
/* 358:475 */     this.listaDetalleConsumoBodega = listaDetalleConsumoBodega;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public DestinoCosto getDestinoCosto()
/* 362:    */   {
/* 363:479 */     return this.destinoCosto;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setDestinoCosto(DestinoCosto destinoCosto)
/* 367:    */   {
/* 368:483 */     this.destinoCosto = destinoCosto;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public BigDecimal getCantidadAnuladaOrdenFabricacion()
/* 372:    */   {
/* 373:487 */     if (this.cantidadAnuladaOrdenFabricacion == null) {
/* 374:488 */       this.cantidadAnuladaOrdenFabricacion = BigDecimal.ZERO;
/* 375:    */     }
/* 376:490 */     return this.cantidadAnuladaOrdenFabricacion;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setCantidadAnuladaOrdenFabricacion(BigDecimal cantidadAnuladaOrdenFabricacion)
/* 380:    */   {
/* 381:494 */     this.cantidadAnuladaOrdenFabricacion = cantidadAnuladaOrdenFabricacion;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public Lote getLote()
/* 385:    */   {
/* 386:498 */     return this.lote;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void setLote(Lote lote)
/* 390:    */   {
/* 391:502 */     this.lote = lote;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public BigDecimal getCantidadUtilizadaSinDesecho()
/* 395:    */   {
/* 396:506 */     return this.cantidadDespachada.add(this.cantidadAdicional).subtract(this.cantidadDevuelta);
/* 397:    */   }
/* 398:    */   
/* 399:    */   public BigDecimal getCantidadDesecho()
/* 400:    */   {
/* 401:510 */     if (this.cantidadDesecho == null) {
/* 402:511 */       this.cantidadDesecho = BigDecimal.ZERO;
/* 403:    */     }
/* 404:513 */     return this.cantidadDesecho;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public void setCantidadDesecho(BigDecimal cantidadDesecho)
/* 408:    */   {
/* 409:517 */     this.cantidadDesecho = cantidadDesecho;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public BigDecimal getCantidadUnidadInformativaDespacho()
/* 413:    */   {
/* 414:521 */     return this.cantidadUnidadInformativaDespacho;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public void setCantidadUnidadInformativaDespacho(BigDecimal cantidadUnidadInformativaDespacho)
/* 418:    */   {
/* 419:525 */     this.cantidadUnidadInformativaDespacho = cantidadUnidadInformativaDespacho;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public BigDecimal getCantidadUnidadInformativaDevolucion()
/* 423:    */   {
/* 424:529 */     return this.cantidadUnidadInformativaDevolucion;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public void setCantidadUnidadInformativaDevolucion(BigDecimal cantidadUnidadInformativaDevolucion)
/* 428:    */   {
/* 429:533 */     this.cantidadUnidadInformativaDevolucion = cantidadUnidadInformativaDevolucion;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public BigDecimal getTraCantidadDespachada()
/* 433:    */   {
/* 434:537 */     if (this.traCantidadDespachada == null) {
/* 435:538 */       this.traCantidadDespachada = this.cantidadDespachada;
/* 436:    */     }
/* 437:539 */     return this.traCantidadDespachada;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setTraCantidadDespachada(BigDecimal traCantidadDespachada)
/* 441:    */   {
/* 442:543 */     this.traCantidadDespachada = traCantidadDespachada;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public BigDecimal getCantidadUnidadInformativaDesecho()
/* 446:    */   {
/* 447:547 */     return this.cantidadUnidadInformativaDesecho;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setCantidadUnidadInformativaDesecho(BigDecimal cantidadUnidadInformativaDesecho)
/* 451:    */   {
/* 452:551 */     this.cantidadUnidadInformativaDesecho = cantidadUnidadInformativaDesecho;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void setEliminado(boolean eliminado)
/* 456:    */   {
/* 457:556 */     for (DetalleOrdenSalidaMaterialOrdenFabricacion detalleOrdenSalidaMaterialOrdenFabricacion : this.listaDetalleOrdenSalidaMaterialOrdenFabricacion) {
/* 458:557 */       detalleOrdenSalidaMaterialOrdenFabricacion.setEliminado(eliminado);
/* 459:    */     }
/* 460:559 */     super.setEliminado(eliminado);
/* 461:    */   }
/* 462:    */   
/* 463:    */   public List<LecturaBalanza> getListaLecturaBalanzaView()
/* 464:    */   {
/* 465:563 */     List<LecturaBalanza> lista = new ArrayList();
/* 466:564 */     for (LecturaBalanza lecturaBalanza : this.listaLecturaBalanza) {
/* 467:565 */       if (!lecturaBalanza.isEliminado()) {
/* 468:566 */         lista.add(lecturaBalanza);
/* 469:    */       }
/* 470:    */     }
/* 471:569 */     return lista;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public List<DetalleOrdenSalidaMaterialOrdenFabricacion> getListaDetalleOrdenSalidaMaterialOrdenFabricacionView()
/* 475:    */   {
/* 476:573 */     return EjbUtil.getEntidadesNoEliminadas(this.listaDetalleOrdenSalidaMaterialOrdenFabricacion);
/* 477:    */   }
/* 478:    */   
/* 479:    */   public boolean isTraEditarCopia()
/* 480:    */   {
/* 481:577 */     return this.traEditarCopia;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setTraEditarCopia(boolean traEditarCopia)
/* 485:    */   {
/* 486:581 */     this.traEditarCopia = traEditarCopia;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public boolean isIndicadorConsumoDirecto()
/* 490:    */   {
/* 491:585 */     return this.indicadorConsumoDirecto;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void setIndicadorConsumoDirecto(boolean indicadorConsumoDirecto)
/* 495:    */   {
/* 496:589 */     this.indicadorConsumoDirecto = indicadorConsumoDirecto;
/* 497:    */   }
/* 498:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleOrdenSalidaMaterial
 * JD-Core Version:    0.7.0.1
 */