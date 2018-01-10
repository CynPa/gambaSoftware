/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Lote;
/*   5:    */ import com.asinfo.as2.entities.Producto;
/*   6:    */ import com.asinfo.as2.entities.Unidad;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.math.RoundingMode;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.Digits;
/*  22:    */ import javax.validation.constraints.Min;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="detalle_plan_produccion")
/*  27:    */ public class DetallePlanProduccion
/*  28:    */   extends EntidadBase
/*  29:    */   implements Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = -6054909171994634772L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="detalle_plan_produccion", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_plan_produccion")
/*  35:    */   @Column(name="id_detalle_plan_produccion")
/*  36:    */   private int idDetallePlanProduccion;
/*  37:    */   @Column(name="id_organizacion")
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="id_sucursal")
/*  40:    */   private int idSucursal;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_plan_produccion", nullable=false)
/*  43:    */   private PlanProduccion planProduccion;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_producto", nullable=false)
/*  46:    */   private Producto producto;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_unidad_stock", nullable=false)
/*  49:    */   private Unidad unidadStock;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_lote", nullable=true)
/*  52:    */   private Lote lote;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_ruta_fabricacion", nullable=false)
/*  55:    */   private RutaFabricacion rutaFabricacion;
/*  56:    */   @Column(name="factor_cambio", nullable=false, precision=12, scale=4)
/*  57:    */   @NotNull
/*  58:    */   @Digits(integer=12, fraction=4)
/*  59:    */   @Min(0L)
/*  60: 84 */   private BigDecimal factorCambio = BigDecimal.ONE;
/*  61:    */   @Column(name="factor_crecimiento", nullable=false, precision=12, scale=4)
/*  62:    */   @NotNull
/*  63:    */   @Digits(integer=12, fraction=4)
/*  64:    */   @Min(0L)
/*  65: 90 */   private BigDecimal factorCrecimiento = BigDecimal.ONE;
/*  66:    */   @Column(name="venta_lunes_anterior", nullable=false, precision=12, scale=2)
/*  67:    */   @NotNull
/*  68:    */   @Digits(integer=12, fraction=2)
/*  69: 97 */   private BigDecimal ventaLunesAnterior = BigDecimal.ZERO;
/*  70:    */   @Column(name="venta_martes_anterior", nullable=false, precision=12, scale=2)
/*  71:    */   @NotNull
/*  72:    */   @Digits(integer=12, fraction=2)
/*  73:102 */   private BigDecimal ventaMartesAnterior = BigDecimal.ZERO;
/*  74:    */   @Column(name="venta_miercoles_anterior", nullable=false, precision=12, scale=2)
/*  75:    */   @NotNull
/*  76:    */   @Digits(integer=12, fraction=2)
/*  77:107 */   private BigDecimal ventaMiercolesAnterior = BigDecimal.ZERO;
/*  78:    */   @Column(name="venta_jueves_anterior", nullable=false, precision=12, scale=2)
/*  79:    */   @NotNull
/*  80:    */   @Digits(integer=12, fraction=2)
/*  81:112 */   private BigDecimal ventaJuevesAnterior = BigDecimal.ZERO;
/*  82:    */   @Column(name="venta_viernes_anterior", nullable=false, precision=12, scale=2)
/*  83:    */   @NotNull
/*  84:    */   @Digits(integer=12, fraction=2)
/*  85:117 */   private BigDecimal ventaViernesAnterior = BigDecimal.ZERO;
/*  86:    */   @Column(name="venta_sabado_anterior", nullable=false, precision=12, scale=2)
/*  87:    */   @NotNull
/*  88:    */   @Digits(integer=12, fraction=2)
/*  89:122 */   private BigDecimal ventaSabadoAnterior = BigDecimal.ZERO;
/*  90:    */   @Column(name="venta_domingo_anterior", nullable=false, precision=12, scale=2)
/*  91:    */   @NotNull
/*  92:    */   @Digits(integer=12, fraction=2)
/*  93:127 */   private BigDecimal ventaDomingoAnterior = BigDecimal.ZERO;
/*  94:    */   @Column(name="venta_lunes", nullable=false, precision=12, scale=2)
/*  95:    */   @NotNull
/*  96:    */   @Digits(integer=12, fraction=2)
/*  97:133 */   private BigDecimal ventaLunes = BigDecimal.ZERO;
/*  98:    */   @Column(name="venta_martes", nullable=false, precision=12, scale=2)
/*  99:    */   @NotNull
/* 100:    */   @Digits(integer=12, fraction=2)
/* 101:138 */   private BigDecimal ventaMartes = BigDecimal.ZERO;
/* 102:    */   @Column(name="venta_miercoles", nullable=false, precision=12, scale=2)
/* 103:    */   @NotNull
/* 104:    */   @Digits(integer=12, fraction=2)
/* 105:143 */   private BigDecimal ventaMiercoles = BigDecimal.ZERO;
/* 106:    */   @Column(name="venta_jueves", nullable=false, precision=12, scale=2)
/* 107:    */   @NotNull
/* 108:    */   @Digits(integer=12, fraction=2)
/* 109:148 */   private BigDecimal ventaJueves = BigDecimal.ZERO;
/* 110:    */   @Column(name="venta_viernes", nullable=false, precision=12, scale=2)
/* 111:    */   @NotNull
/* 112:    */   @Digits(integer=12, fraction=2)
/* 113:153 */   private BigDecimal ventaViernes = BigDecimal.ZERO;
/* 114:    */   @Column(name="venta_sabado", nullable=false, precision=12, scale=2)
/* 115:    */   @NotNull
/* 116:    */   @Digits(integer=12, fraction=2)
/* 117:158 */   private BigDecimal ventaSabado = BigDecimal.ZERO;
/* 118:    */   @Column(name="venta_domingo", nullable=false, precision=12, scale=2)
/* 119:    */   @NotNull
/* 120:    */   @Digits(integer=12, fraction=2)
/* 121:163 */   private BigDecimal ventaDomingo = BigDecimal.ZERO;
/* 122:    */   @Column(name="batch_lunes", nullable=false)
/* 123:    */   @NotNull
/* 124:    */   private int batchLunes;
/* 125:    */   @Column(name="batch_martes", nullable=false)
/* 126:    */   @NotNull
/* 127:    */   private int batchMartes;
/* 128:    */   @Column(name="batch_miercoles", nullable=false)
/* 129:    */   @NotNull
/* 130:    */   private int batchMiercoles;
/* 131:    */   @Column(name="batch_jueves", nullable=false)
/* 132:    */   @NotNull
/* 133:    */   private int batchJueves;
/* 134:    */   @Column(name="batch_viernes", nullable=false)
/* 135:    */   @NotNull
/* 136:    */   private int batchViernes;
/* 137:    */   @Column(name="batch_sabado", nullable=false)
/* 138:    */   @NotNull
/* 139:    */   private int batchSabado;
/* 140:    */   @Column(name="batch_domingo", nullable=false)
/* 141:    */   @NotNull
/* 142:    */   private int batchDomingo;
/* 143:    */   @Column(name="saldo_inicial", nullable=false, precision=12, scale=2)
/* 144:    */   @NotNull
/* 145:    */   @Digits(integer=12, fraction=2)
/* 146:198 */   private BigDecimal saldoInicial = BigDecimal.ZERO;
/* 147:    */   @Column(name="saldo_lunes", nullable=false, precision=12, scale=2)
/* 148:    */   @NotNull
/* 149:    */   @Digits(integer=12, fraction=2)
/* 150:203 */   private BigDecimal saldoLunes = BigDecimal.ZERO;
/* 151:    */   @Column(name="saldo_martes", nullable=false, precision=12, scale=2)
/* 152:    */   @NotNull
/* 153:    */   @Digits(integer=12, fraction=2)
/* 154:208 */   private BigDecimal saldoMartes = BigDecimal.ZERO;
/* 155:    */   @Column(name="saldo_miercoles", nullable=false, precision=12, scale=2)
/* 156:    */   @NotNull
/* 157:    */   @Digits(integer=12, fraction=2)
/* 158:213 */   private BigDecimal saldoMiercoles = BigDecimal.ZERO;
/* 159:    */   @Column(name="saldo_jueves", nullable=false, precision=12, scale=2)
/* 160:    */   @NotNull
/* 161:    */   @Digits(integer=12, fraction=2)
/* 162:218 */   private BigDecimal saldoJueves = BigDecimal.ZERO;
/* 163:    */   @Column(name="saldo_viernes", nullable=false, precision=12, scale=2)
/* 164:    */   @NotNull
/* 165:    */   @Digits(integer=12, fraction=2)
/* 166:223 */   private BigDecimal saldoViernes = BigDecimal.ZERO;
/* 167:    */   @Column(name="saldo_sabado", nullable=false, precision=12, scale=2)
/* 168:    */   @NotNull
/* 169:    */   @Digits(integer=12, fraction=2)
/* 170:228 */   private BigDecimal saldoSabado = BigDecimal.ZERO;
/* 171:    */   @Column(name="saldo_domingo", nullable=false, precision=12, scale=2)
/* 172:    */   @NotNull
/* 173:    */   @Digits(integer=12, fraction=2)
/* 174:233 */   private BigDecimal saldoDomingo = BigDecimal.ZERO;
/* 175:    */   @Column(name="indicador_plan_maestro_lunes", nullable=false)
/* 176:    */   @NotNull
/* 177:    */   private boolean indicadorPlanMaestroLunes;
/* 178:    */   @Column(name="indicador_plan_maestro_martes", nullable=false)
/* 179:    */   @NotNull
/* 180:    */   private boolean indicadorPlanMaestroMartes;
/* 181:    */   @Column(name="indicador_plan_maestro_miercoles", nullable=false)
/* 182:    */   @NotNull
/* 183:    */   private boolean indicadorPlanMaestroMiercoles;
/* 184:    */   @Column(name="indicador_plan_maestro_jueves", nullable=false)
/* 185:    */   @NotNull
/* 186:    */   private boolean indicadorPlanMaestroJueves;
/* 187:    */   @Column(name="indicador_plan_maestro_viernes", nullable=false)
/* 188:    */   @NotNull
/* 189:    */   private boolean indicadorPlanMaestroViernes;
/* 190:    */   @Column(name="indicador_plan_maestro_sabado", nullable=false)
/* 191:    */   @NotNull
/* 192:    */   private boolean indicadorPlanMaestroSabado;
/* 193:    */   @Column(name="indicador_plan_maestro_domingo", nullable=false)
/* 194:    */   @NotNull
/* 195:    */   private boolean indicadorPlanMaestroDomingo;
/* 196:    */   @Column(name="cantidad_requerida_semana", nullable=false, precision=12, scale=2)
/* 197:    */   @NotNull
/* 198:    */   @Digits(integer=12, fraction=2)
/* 199:267 */   private BigDecimal cantidadRequeridaProduccionSemana = BigDecimal.ZERO;
/* 200:    */   @Column(name="stock_minimo", nullable=false, precision=12, scale=2)
/* 201:    */   @NotNull
/* 202:    */   @Digits(integer=12, fraction=2)
/* 203:272 */   private BigDecimal stockMinimo = BigDecimal.ZERO;
/* 204:    */   @Transient
/* 205:277 */   private BigDecimal totalVentasSemanaPrevia = BigDecimal.ZERO;
/* 206:    */   @Transient
/* 207:280 */   private BigDecimal totalVentasSemana = BigDecimal.ZERO;
/* 208:    */   @Transient
/* 209:    */   @Digits(integer=12, fraction=0)
/* 210:283 */   private BigDecimal totalProduccionSemana = BigDecimal.ZERO;
/* 211:    */   
/* 212:    */   public int getId()
/* 213:    */   {
/* 214:295 */     return this.idDetallePlanProduccion;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public int getIdDetallePlanProduccion()
/* 218:    */   {
/* 219:299 */     return this.idDetallePlanProduccion;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setIdDetallePlanProduccion(int idDetallePlanProduccion)
/* 223:    */   {
/* 224:303 */     this.idDetallePlanProduccion = idDetallePlanProduccion;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public int getIdOrganizacion()
/* 228:    */   {
/* 229:307 */     return this.idOrganizacion;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setIdOrganizacion(int idOrganizacion)
/* 233:    */   {
/* 234:311 */     this.idOrganizacion = idOrganizacion;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public int getIdSucursal()
/* 238:    */   {
/* 239:315 */     return this.idSucursal;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setIdSucursal(int idSucursal)
/* 243:    */   {
/* 244:319 */     this.idSucursal = idSucursal;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public PlanProduccion getPlanProduccion()
/* 248:    */   {
/* 249:323 */     return this.planProduccion;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setPlanProduccion(PlanProduccion planProduccion)
/* 253:    */   {
/* 254:327 */     this.planProduccion = planProduccion;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Producto getProducto()
/* 258:    */   {
/* 259:331 */     return this.producto;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setProducto(Producto producto)
/* 263:    */   {
/* 264:335 */     this.producto = producto;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public Unidad getUnidadStock()
/* 268:    */   {
/* 269:339 */     return this.unidadStock;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setUnidadStock(Unidad unidadStock)
/* 273:    */   {
/* 274:343 */     this.unidadStock = unidadStock;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Lote getLote()
/* 278:    */   {
/* 279:347 */     return this.lote;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setLote(Lote lote)
/* 283:    */   {
/* 284:351 */     this.lote = lote;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public RutaFabricacion getRutaFabricacion()
/* 288:    */   {
/* 289:355 */     return this.rutaFabricacion;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setRutaFabricacion(RutaFabricacion rutaFabricacion)
/* 293:    */   {
/* 294:359 */     this.rutaFabricacion = rutaFabricacion;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public BigDecimal getFactorCambio()
/* 298:    */   {
/* 299:363 */     return this.factorCambio;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setFactorCambio(BigDecimal factorCambio)
/* 303:    */   {
/* 304:367 */     this.factorCambio = factorCambio;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public BigDecimal getFactorCrecimiento()
/* 308:    */   {
/* 309:371 */     return this.factorCrecimiento;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setFactorCrecimiento(BigDecimal factorCrecimiento)
/* 313:    */   {
/* 314:375 */     this.factorCrecimiento = factorCrecimiento;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public BigDecimal getVentaLunesAnterior()
/* 318:    */   {
/* 319:379 */     return this.ventaLunesAnterior;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setVentaLunesAnterior(BigDecimal ventaLunesAnterior)
/* 323:    */   {
/* 324:383 */     this.ventaLunesAnterior = ventaLunesAnterior;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public BigDecimal getVentaMartesAnterior()
/* 328:    */   {
/* 329:387 */     return this.ventaMartesAnterior;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setVentaMartesAnterior(BigDecimal ventaMartesAnterior)
/* 333:    */   {
/* 334:391 */     this.ventaMartesAnterior = ventaMartesAnterior;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public BigDecimal getVentaMiercolesAnterior()
/* 338:    */   {
/* 339:395 */     return this.ventaMiercolesAnterior;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setVentaMiercolesAnterior(BigDecimal ventaMiercolesAnterior)
/* 343:    */   {
/* 344:399 */     this.ventaMiercolesAnterior = ventaMiercolesAnterior;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public BigDecimal getVentaJuevesAnterior()
/* 348:    */   {
/* 349:403 */     return this.ventaJuevesAnterior;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void setVentaJuevesAnterior(BigDecimal ventaJuevesAnterior)
/* 353:    */   {
/* 354:407 */     this.ventaJuevesAnterior = ventaJuevesAnterior;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public BigDecimal getVentaViernesAnterior()
/* 358:    */   {
/* 359:411 */     return this.ventaViernesAnterior;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setVentaViernesAnterior(BigDecimal ventaViernesAnterior)
/* 363:    */   {
/* 364:415 */     this.ventaViernesAnterior = ventaViernesAnterior;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public BigDecimal getVentaSabadoAnterior()
/* 368:    */   {
/* 369:419 */     return this.ventaSabadoAnterior;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setVentaSabadoAnterior(BigDecimal ventaSabadoAnterior)
/* 373:    */   {
/* 374:423 */     this.ventaSabadoAnterior = ventaSabadoAnterior;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public BigDecimal getVentaDomingoAnterior()
/* 378:    */   {
/* 379:427 */     return this.ventaDomingoAnterior;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setVentaDomingoAnterior(BigDecimal ventaDomingoAnterior)
/* 383:    */   {
/* 384:431 */     this.ventaDomingoAnterior = ventaDomingoAnterior;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public BigDecimal getVentaLunes()
/* 388:    */   {
/* 389:435 */     return this.ventaLunes;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setVentaLunes(BigDecimal ventaLunes)
/* 393:    */   {
/* 394:439 */     this.ventaLunes = ventaLunes;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public BigDecimal getVentaMartes()
/* 398:    */   {
/* 399:443 */     return this.ventaMartes;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setVentaMartes(BigDecimal ventaMartes)
/* 403:    */   {
/* 404:447 */     this.ventaMartes = ventaMartes;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public BigDecimal getVentaMiercoles()
/* 408:    */   {
/* 409:451 */     return this.ventaMiercoles;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setVentaMiercoles(BigDecimal ventaMiercoles)
/* 413:    */   {
/* 414:455 */     this.ventaMiercoles = ventaMiercoles;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public BigDecimal getVentaJueves()
/* 418:    */   {
/* 419:459 */     return this.ventaJueves;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void setVentaJueves(BigDecimal ventaJueves)
/* 423:    */   {
/* 424:463 */     this.ventaJueves = ventaJueves;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public BigDecimal getVentaViernes()
/* 428:    */   {
/* 429:467 */     return this.ventaViernes;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void setVentaViernes(BigDecimal ventaViernes)
/* 433:    */   {
/* 434:471 */     this.ventaViernes = ventaViernes;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public BigDecimal getVentaSabado()
/* 438:    */   {
/* 439:475 */     return this.ventaSabado;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public void setVentaSabado(BigDecimal ventaSabado)
/* 443:    */   {
/* 444:479 */     this.ventaSabado = ventaSabado;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public BigDecimal getVentaDomingo()
/* 448:    */   {
/* 449:483 */     return this.ventaDomingo;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void setVentaDomingo(BigDecimal ventaDomingo)
/* 453:    */   {
/* 454:487 */     this.ventaDomingo = ventaDomingo;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public int getBatchLunes()
/* 458:    */   {
/* 459:491 */     return this.batchLunes;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void setBatchLunes(int batchLunes)
/* 463:    */   {
/* 464:495 */     this.batchLunes = batchLunes;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public int getBatchMartes()
/* 468:    */   {
/* 469:499 */     return this.batchMartes;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public void setBatchMartes(int batchMartes)
/* 473:    */   {
/* 474:503 */     this.batchMartes = batchMartes;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public int getBatchMiercoles()
/* 478:    */   {
/* 479:507 */     return this.batchMiercoles;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void setBatchMiercoles(int batchMiercoles)
/* 483:    */   {
/* 484:511 */     this.batchMiercoles = batchMiercoles;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public int getBatchJueves()
/* 488:    */   {
/* 489:515 */     return this.batchJueves;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void setBatchJueves(int batchJueves)
/* 493:    */   {
/* 494:519 */     this.batchJueves = batchJueves;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public int getBatchViernes()
/* 498:    */   {
/* 499:523 */     return this.batchViernes;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void setBatchViernes(int batchViernes)
/* 503:    */   {
/* 504:527 */     this.batchViernes = batchViernes;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public int getBatchSabado()
/* 508:    */   {
/* 509:531 */     return this.batchSabado;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void setBatchSabado(int batchSabado)
/* 513:    */   {
/* 514:535 */     this.batchSabado = batchSabado;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public int getBatchDomingo()
/* 518:    */   {
/* 519:539 */     return this.batchDomingo;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public void setBatchDomingo(int batchDomingo)
/* 523:    */   {
/* 524:543 */     this.batchDomingo = batchDomingo;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public BigDecimal getTotalVentasSemanaPrevia()
/* 528:    */   {
/* 529:548 */     this.totalVentasSemanaPrevia = this.ventaLunesAnterior.add(this.ventaMartesAnterior).add(this.ventaMiercolesAnterior).add(this.ventaJuevesAnterior).add(this.ventaViernesAnterior).add(this.ventaSabadoAnterior).add(this.ventaDomingoAnterior);
/* 530:549 */     return this.totalVentasSemanaPrevia;
/* 531:    */   }
/* 532:    */   
/* 533:    */   public void setTotalVentasSemanaPrevia(BigDecimal totalVentasSemanaPrevia)
/* 534:    */   {
/* 535:553 */     this.totalVentasSemanaPrevia = totalVentasSemanaPrevia;
/* 536:    */   }
/* 537:    */   
/* 538:    */   public BigDecimal getTotalVentasSemana()
/* 539:    */   {
/* 540:557 */     this.totalVentasSemana = this.ventaLunes.add(this.ventaMartes).add(this.ventaMiercoles).add(this.ventaJueves).add(this.ventaViernes).add(this.ventaSabado).add(this.ventaDomingo);
/* 541:558 */     return this.totalVentasSemana;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void setTotalVentasSemana(BigDecimal totalVentasSemana)
/* 545:    */   {
/* 546:562 */     this.totalVentasSemana = totalVentasSemana;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public BigDecimal getSaldoLunes()
/* 550:    */   {
/* 551:566 */     return this.saldoLunes;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public void setSaldoLunes(BigDecimal saldoLunes)
/* 555:    */   {
/* 556:570 */     this.saldoLunes = saldoLunes;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public BigDecimal getSaldoMartes()
/* 560:    */   {
/* 561:574 */     return this.saldoMartes;
/* 562:    */   }
/* 563:    */   
/* 564:    */   public void setSaldoMartes(BigDecimal saldoMartes)
/* 565:    */   {
/* 566:578 */     this.saldoMartes = saldoMartes;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public BigDecimal getSaldoMiercoles()
/* 570:    */   {
/* 571:582 */     return this.saldoMiercoles;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public void setSaldoMiercoles(BigDecimal saldoMiercoles)
/* 575:    */   {
/* 576:586 */     this.saldoMiercoles = saldoMiercoles;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public BigDecimal getSaldoJueves()
/* 580:    */   {
/* 581:590 */     return this.saldoJueves;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public void setSaldoJueves(BigDecimal saldoJueves)
/* 585:    */   {
/* 586:594 */     this.saldoJueves = saldoJueves;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public BigDecimal getSaldoViernes()
/* 590:    */   {
/* 591:598 */     return this.saldoViernes;
/* 592:    */   }
/* 593:    */   
/* 594:    */   public void setSaldoViernes(BigDecimal saldoViernes)
/* 595:    */   {
/* 596:602 */     this.saldoViernes = saldoViernes;
/* 597:    */   }
/* 598:    */   
/* 599:    */   public BigDecimal getSaldoSabado()
/* 600:    */   {
/* 601:606 */     return this.saldoSabado;
/* 602:    */   }
/* 603:    */   
/* 604:    */   public void setSaldoSabado(BigDecimal saldoSabado)
/* 605:    */   {
/* 606:610 */     this.saldoSabado = saldoSabado;
/* 607:    */   }
/* 608:    */   
/* 609:    */   public BigDecimal getSaldoDomingo()
/* 610:    */   {
/* 611:614 */     return this.saldoDomingo;
/* 612:    */   }
/* 613:    */   
/* 614:    */   public void setSaldoDomingo(BigDecimal saldoDomingo)
/* 615:    */   {
/* 616:618 */     this.saldoDomingo = saldoDomingo;
/* 617:    */   }
/* 618:    */   
/* 619:    */   public BigDecimal getSaldoInicial()
/* 620:    */   {
/* 621:622 */     return this.saldoInicial;
/* 622:    */   }
/* 623:    */   
/* 624:    */   public void setSaldoInicial(BigDecimal saldoInicial)
/* 625:    */   {
/* 626:626 */     this.saldoInicial = saldoInicial;
/* 627:    */   }
/* 628:    */   
/* 629:    */   public BigDecimal getCantidadRequeridaProduccionSemana()
/* 630:    */   {
/* 631:630 */     return this.cantidadRequeridaProduccionSemana;
/* 632:    */   }
/* 633:    */   
/* 634:    */   public void setCantidadRequeridaProduccionSemana(BigDecimal cantidadRequeridaProduccionSemana)
/* 635:    */   {
/* 636:634 */     this.cantidadRequeridaProduccionSemana = cantidadRequeridaProduccionSemana;
/* 637:    */   }
/* 638:    */   
/* 639:    */   public BigDecimal getStockMinimo()
/* 640:    */   {
/* 641:638 */     return this.stockMinimo;
/* 642:    */   }
/* 643:    */   
/* 644:    */   public void setStockMinimo(BigDecimal stockMinimo)
/* 645:    */   {
/* 646:642 */     this.stockMinimo = stockMinimo;
/* 647:    */   }
/* 648:    */   
/* 649:    */   public BigDecimal getTotalProduccionSemana()
/* 650:    */   {
/* 651:646 */     this.totalProduccionSemana = BigDecimal.ZERO;
/* 652:647 */     if ((getProducto() != null) && (getProducto().getCantidadProduccion() != null)) {
/* 653:649 */       this.totalProduccionSemana = new BigDecimal(this.batchLunes + this.batchMartes + this.batchMiercoles + this.batchJueves + this.batchViernes + this.batchSabado + this.batchDomingo).multiply(getProducto().getCantidadProduccion()).setScale(2, RoundingMode.HALF_UP);
/* 654:    */     }
/* 655:651 */     return this.totalProduccionSemana;
/* 656:    */   }
/* 657:    */   
/* 658:    */   public void setTotalProduccionSemana(BigDecimal totalProduccionSemana)
/* 659:    */   {
/* 660:655 */     this.totalProduccionSemana = totalProduccionSemana;
/* 661:    */   }
/* 662:    */   
/* 663:    */   public boolean isIndicadorPlanMaestroLunes()
/* 664:    */   {
/* 665:659 */     return this.indicadorPlanMaestroLunes;
/* 666:    */   }
/* 667:    */   
/* 668:    */   public void setIndicadorPlanMaestroLunes(boolean indicadorPlanMaestroLunes)
/* 669:    */   {
/* 670:663 */     this.indicadorPlanMaestroLunes = indicadorPlanMaestroLunes;
/* 671:    */   }
/* 672:    */   
/* 673:    */   public boolean isIndicadorPlanMaestroMartes()
/* 674:    */   {
/* 675:667 */     return this.indicadorPlanMaestroMartes;
/* 676:    */   }
/* 677:    */   
/* 678:    */   public void setIndicadorPlanMaestroMartes(boolean indicadorPlanMaestroMartes)
/* 679:    */   {
/* 680:671 */     this.indicadorPlanMaestroMartes = indicadorPlanMaestroMartes;
/* 681:    */   }
/* 682:    */   
/* 683:    */   public boolean isIndicadorPlanMaestroMiercoles()
/* 684:    */   {
/* 685:675 */     return this.indicadorPlanMaestroMiercoles;
/* 686:    */   }
/* 687:    */   
/* 688:    */   public void setIndicadorPlanMaestroMiercoles(boolean indicadorPlanMaestroMiercoles)
/* 689:    */   {
/* 690:679 */     this.indicadorPlanMaestroMiercoles = indicadorPlanMaestroMiercoles;
/* 691:    */   }
/* 692:    */   
/* 693:    */   public boolean isIndicadorPlanMaestroJueves()
/* 694:    */   {
/* 695:683 */     return this.indicadorPlanMaestroJueves;
/* 696:    */   }
/* 697:    */   
/* 698:    */   public void setIndicadorPlanMaestroJueves(boolean indicadorPlanMaestroJueves)
/* 699:    */   {
/* 700:687 */     this.indicadorPlanMaestroJueves = indicadorPlanMaestroJueves;
/* 701:    */   }
/* 702:    */   
/* 703:    */   public boolean isIndicadorPlanMaestroViernes()
/* 704:    */   {
/* 705:691 */     return this.indicadorPlanMaestroViernes;
/* 706:    */   }
/* 707:    */   
/* 708:    */   public void setIndicadorPlanMaestroViernes(boolean indicadorPlanMaestroViernes)
/* 709:    */   {
/* 710:695 */     this.indicadorPlanMaestroViernes = indicadorPlanMaestroViernes;
/* 711:    */   }
/* 712:    */   
/* 713:    */   public boolean isIndicadorPlanMaestroSabado()
/* 714:    */   {
/* 715:699 */     return this.indicadorPlanMaestroSabado;
/* 716:    */   }
/* 717:    */   
/* 718:    */   public void setIndicadorPlanMaestroSabado(boolean indicadorPlanMaestroSabado)
/* 719:    */   {
/* 720:703 */     this.indicadorPlanMaestroSabado = indicadorPlanMaestroSabado;
/* 721:    */   }
/* 722:    */   
/* 723:    */   public boolean isIndicadorPlanMaestroDomingo()
/* 724:    */   {
/* 725:707 */     return this.indicadorPlanMaestroDomingo;
/* 726:    */   }
/* 727:    */   
/* 728:    */   public void setIndicadorPlanMaestroDomingo(boolean indicadorPlanMaestroDomingo)
/* 729:    */   {
/* 730:711 */     this.indicadorPlanMaestroDomingo = indicadorPlanMaestroDomingo;
/* 731:    */   }
/* 732:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.DetallePlanProduccion
 * JD-Core Version:    0.7.0.1
 */