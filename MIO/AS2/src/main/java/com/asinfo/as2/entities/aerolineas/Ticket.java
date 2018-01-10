/*   1:    */ package com.asinfo.as2.entities.aerolineas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Temporal;
/*  22:    */ import javax.persistence.TemporalType;
/*  23:    */ import javax.persistence.Transient;
/*  24:    */ import javax.validation.constraints.Digits;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ 
/*  27:    */ @Entity
/*  28:    */ @Table(name="ticket")
/*  29:    */ public class Ticket
/*  30:    */   extends EntidadBase
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="ticket", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ticket")
/*  36:    */   @Column(name="id_ticket", unique=true, nullable=false)
/*  37:    */   private int idTicket;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="id_sucursal", nullable=false)
/*  41:    */   private int idSucursal;
/*  42:    */   @Column(name="numero", nullable=true, length=50)
/*  43:    */   @Size(max=50)
/*  44:    */   private String numero;
/*  45:    */   @Column(name="aerolinea", nullable=true, length=50)
/*  46:    */   @Size(max=50)
/*  47:    */   private String aerolinea;
/*  48:    */   @Temporal(TemporalType.DATE)
/*  49:    */   @Column(name="periodo", nullable=true)
/*  50:    */   private Date periodo;
/*  51:    */   @Temporal(TemporalType.DATE)
/*  52:    */   @Column(name="fecha", nullable=true)
/*  53:    */   private Date fecha;
/*  54:    */   @Column(name="tipo", nullable=true, length=50)
/*  55:    */   @Size(max=50)
/*  56:    */   private String tipo;
/*  57:    */   @Column(name="moneda", nullable=true, length=50)
/*  58:    */   @Size(max=50)
/*  59:    */   private String moneda;
/*  60:    */   @Column(name="operacion", nullable=true, length=50)
/*  61:    */   @Size(max=50)
/*  62:    */   private String operacion;
/*  63:    */   @Digits(integer=12, fraction=6)
/*  64:    */   @Column(name="comision", precision=12, scale=2)
/*  65: 79 */   private BigDecimal comision = BigDecimal.ZERO;
/*  66:    */   @Digits(integer=12, fraction=6)
/*  67:    */   @Column(name="iva", precision=12, scale=2)
/*  68: 83 */   private BigDecimal iva = BigDecimal.ZERO;
/*  69:    */   @Digits(integer=12, fraction=6)
/*  70:    */   @Column(name="tarifa", precision=12, scale=2)
/*  71: 87 */   private BigDecimal tarifa = BigDecimal.ZERO;
/*  72:    */   @Column(name="pasajero", nullable=true, length=500)
/*  73:    */   @Size(max=500)
/*  74:    */   private String pasajero;
/*  75:    */   @Column(name="ruta", nullable=true, length=1000)
/*  76:    */   @Size(max=1000)
/*  77:    */   private String ruta;
/*  78:    */   @Digits(integer=12, fraction=6)
/*  79:    */   @Column(name="bruto", precision=12, scale=2)
/*  80: 99 */   private BigDecimal bruto = BigDecimal.ZERO;
/*  81:    */   @Digits(integer=12, fraction=6)
/*  82:    */   @Column(name="credito", precision=12, scale=2)
/*  83:103 */   private BigDecimal credito = BigDecimal.ZERO;
/*  84:    */   @Digits(integer=12, fraction=6)
/*  85:    */   @Column(name="por_comision", precision=12, scale=2)
/*  86:107 */   private BigDecimal porComision = BigDecimal.ZERO;
/*  87:    */   @Digits(integer=12, fraction=6)
/*  88:    */   @Column(name="neto", precision=12, scale=2)
/*  89:111 */   private BigDecimal neto = BigDecimal.ZERO;
/*  90:    */   @Digits(integer=12, fraction=6)
/*  91:    */   @Column(name="tarifa_moneda", precision=12, scale=2)
/*  92:115 */   private BigDecimal tarifaMoneda = BigDecimal.ZERO;
/*  93:    */   @Column(name="emisor", nullable=true, length=50)
/*  94:    */   private String emisor;
/*  95:    */   @Column(name="numero_periodo", nullable=true, length=50)
/*  96:    */   private String numeroPeriodo;
/*  97:    */   @Column(name="periodo_bsp", nullable=true, length=50)
/*  98:    */   private String periodoBSP;
/*  99:    */   @Digits(integer=12, fraction=6)
/* 100:    */   @Column(name="tarjeta", precision=12, scale=2)
/* 101:    */   private BigDecimal tarjeta;
/* 102:    */   @Column(name="usuario", nullable=true, length=50)
/* 103:    */   private String usuario;
/* 104:    */   @Column(name="fecha_computador", nullable=true, length=50)
/* 105:    */   private String fechaComputador;
/* 106:    */   @Column(name="ticketCnj", nullable=true, length=50)
/* 107:    */   private String ticketCnj;
/* 108:    */   @Digits(integer=12, fraction=6)
/* 109:    */   @Column(name="penalty", precision=12, scale=2)
/* 110:141 */   private BigDecimal penalty = BigDecimal.ZERO;
/* 111:    */   @Column(name="referencia", nullable=true, length=50)
/* 112:    */   private String referencia;
/* 113:    */   @Column(name="valex", nullable=true, length=50)
/* 114:    */   private String valex;
/* 115:    */   @Column(name="vale", nullable=true, length=50)
/* 116:    */   private String vale;
/* 117:    */   @Digits(integer=12, fraction=6)
/* 118:    */   @Column(name="retencionIca", precision=12, scale=2)
/* 119:154 */   private BigDecimal retencionIca = BigDecimal.ZERO;
/* 120:    */   @Digits(integer=12, fraction=6)
/* 121:    */   @Column(name="retencionIvaCom", precision=12, scale=2)
/* 122:158 */   private BigDecimal retencionIvaCom = BigDecimal.ZERO;
/* 123:    */   @Digits(integer=12, fraction=6)
/* 124:    */   @Column(name="retencionFte", precision=12, scale=2)
/* 125:162 */   private BigDecimal retencionFte = BigDecimal.ZERO;
/* 126:    */   @Digits(integer=12, fraction=6)
/* 127:    */   @Column(name="wt", precision=12, scale=2)
/* 128:166 */   private BigDecimal wt = BigDecimal.ZERO;
/* 129:    */   @Digits(integer=12, fraction=6)
/* 130:    */   @Column(name="ed", precision=12, scale=2)
/* 131:170 */   private BigDecimal ed = BigDecimal.ZERO;
/* 132:    */   @Digits(integer=12, fraction=6)
/* 133:    */   @Column(name="yq", precision=12, scale=2)
/* 134:174 */   private BigDecimal yq = BigDecimal.ZERO;
/* 135:    */   @Digits(integer=12, fraction=6)
/* 136:    */   @Column(name="qb", precision=12, scale=2)
/* 137:178 */   private BigDecimal qb = BigDecimal.ZERO;
/* 138:    */   @Digits(integer=12, fraction=6)
/* 139:    */   @Column(name="qi", precision=12, scale=2)
/* 140:182 */   private BigDecimal qi = BigDecimal.ZERO;
/* 141:    */   @Digits(integer=12, fraction=6)
/* 142:    */   @Column(name="yr", precision=12, scale=2)
/* 143:186 */   private BigDecimal yr = BigDecimal.ZERO;
/* 144:    */   @Digits(integer=12, fraction=6)
/* 145:    */   @Column(name="e2", precision=12, scale=2)
/* 146:190 */   private BigDecimal e2 = BigDecimal.ZERO;
/* 147:    */   @Temporal(TemporalType.DATE)
/* 148:    */   @Column(name="fecha_viaje", nullable=true, length=23)
/* 149:    */   private Date fechaViaje;
/* 150:    */   @Digits(integer=12, fraction=6)
/* 151:    */   @Column(name="impuesto_extranjero", precision=12, scale=2)
/* 152:198 */   private BigDecimal impuestoExtranjero = BigDecimal.ZERO;
/* 153:    */   @Digits(integer=12, fraction=6)
/* 154:    */   @Column(name="iva_comision", precision=12, scale=2)
/* 155:202 */   private BigDecimal ivaComision = BigDecimal.ZERO;
/* 156:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 157:    */   @JoinColumn(name="id_bsp", nullable=true)
/* 158:    */   private CargaArchivo bsp;
/* 159:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ticket", cascade={javax.persistence.CascadeType.DETACH})
/* 160:210 */   private List<DetalleTicket> listaDetalleTicket = new ArrayList();
/* 161:    */   @Column(name="indicador_ticket_credito", nullable=true)
/* 162:    */   private Boolean indicadorTicketCredito;
/* 163:    */   @Column(name="original_conjuncion", nullable=true)
/* 164:    */   private String originalConjuncion;
/* 165:    */   @Column(name="tipo_de_documento", nullable=true)
/* 166:    */   private String tipoDeDocumento;
/* 167:    */   @Column(name="identificacion_tributaria", nullable=true)
/* 168:    */   private String identificacionTributaria;
/* 169:    */   @Column(name="is_credito", nullable=true)
/* 170:    */   private Boolean isCredito;
/* 171:    */   @Column(name="observaciones", nullable=true)
/* 172:    */   private String observaciones;
/* 173:    */   @Column(name="diferencia", precision=12, scale=2)
/* 174:    */   @Digits(integer=12, fraction=6)
/* 175:    */   private BigDecimal diferencia;
/* 176:    */   @Column(name="valor_total_preliminar", precision=12, scale=2)
/* 177:    */   @Digits(integer=12, fraction=6)
/* 178:    */   private BigDecimal valorTotalPreliminar;
/* 179:    */   @Column(name="valor_total", precision=12, scale=2)
/* 180:    */   @Digits(integer=12, fraction=6)
/* 181:    */   private BigDecimal valorTotal;
/* 182:    */   @Column(name="anticipo", precision=12, scale=2)
/* 183:    */   @Digits(integer=12, fraction=6)
/* 184:    */   private BigDecimal anticipo;
/* 185:    */   @Column(name="descuento", precision=12, scale=2)
/* 186:    */   @Digits(integer=12, fraction=6)
/* 187:    */   private BigDecimal descuento;
/* 188:    */   @Column(name="tarifa_preliminar", precision=12, scale=2)
/* 189:    */   @Digits(integer=12, fraction=6)
/* 190:    */   private BigDecimal tarifaPreliminar;
/* 191:    */   @Column(name="codigo_de_servicio", nullable=true)
/* 192:    */   private String codigoDeServicio;
/* 193:    */   @Column(name="tarifa_diferente_cero", precision=12, scale=2)
/* 194:    */   @Digits(integer=12, fraction=6)
/* 195:    */   private BigDecimal tarifaDiferenteCero;
/* 196:    */   @Column(name="tarifa_cero", precision=12, scale=2)
/* 197:    */   @Digits(integer=12, fraction=6)
/* 198:    */   private BigDecimal tarifaCero;
/* 199:    */   @Column(name="yq_diferente_cero", precision=12, scale=2)
/* 200:    */   @Digits(integer=12, fraction=6)
/* 201:    */   private BigDecimal YqDiferenteCero;
/* 202:    */   @Column(name="yq_cero", precision=12, scale=2)
/* 203:    */   @Digits(integer=12, fraction=6)
/* 204:    */   private BigDecimal YqCero;
/* 205:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 206:    */   @JoinColumn(name="id_punto_venta", nullable=true)
/* 207:    */   private PuntoDeVenta puntoDeVenta;
/* 208:    */   @Temporal(TemporalType.DATE)
/* 209:    */   @Column(name="fecha_reporte", nullable=true)
/* 210:    */   private Date fechaReporte;
/* 211:    */   @Column(name="record", nullable=true, length=1000)
/* 212:    */   private String record;
/* 213:    */   @Column(name="forma_pago", nullable=true, length=1000)
/* 214:    */   private String formaPago;
/* 215:    */   @Column(name="valor_forma_pago", precision=12, scale=2)
/* 216:    */   @Digits(integer=12, fraction=6)
/* 217:293 */   private BigDecimal valorFormaPago = BigDecimal.ZERO;
/* 218:    */   @Column(name="codigo_agente", nullable=true, length=1000)
/* 219:    */   private String codigoAgente;
/* 220:    */   @Temporal(TemporalType.DATE)
/* 221:    */   @Column(name="fecha_emision", nullable=true)
/* 222:    */   private Date fechaEmision;
/* 223:    */   @Column(name="numero_documento_relacionado", nullable=true, length=1000)
/* 224:    */   private String numeroDocumentoRelacionado;
/* 225:    */   @Column(name="tipo_transaccion", nullable=true, length=50)
/* 226:    */   private String tipoTransaccion;
/* 227:    */   @Column(name="tipo_tarjeta_credito", nullable=true, length=1000)
/* 228:    */   private String tipoTarjetaCredito;
/* 229:    */   @Column(name="indicador_contabilizado", nullable=true)
/* 230:    */   private boolean indicadorContabilizado;
/* 231:    */   @Column(name="codigo_disenio_ticket", nullable=true, length=50)
/* 232:    */   private String codigoDisenioTicket;
/* 233:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 234:    */   @JoinColumn(name="id_asiento", nullable=true)
/* 235:    */   private Asiento asiento;
/* 236:    */   @Transient
/* 237:    */   private boolean editado;
/* 238:    */   
/* 239:    */   public String getFormaPago()
/* 240:    */   {
/* 241:327 */     return this.formaPago;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setFormaPago(String formaPago)
/* 245:    */   {
/* 246:331 */     this.formaPago = formaPago;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public BigDecimal getValorFormaPago()
/* 250:    */   {
/* 251:335 */     return this.valorFormaPago;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setValorFormaPago(BigDecimal valorFormaPago)
/* 255:    */   {
/* 256:339 */     this.valorFormaPago = valorFormaPago;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public String getRecord()
/* 260:    */   {
/* 261:343 */     return this.record;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setRecord(String record)
/* 265:    */   {
/* 266:347 */     this.record = record;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getCodigoDeServicio()
/* 270:    */   {
/* 271:351 */     return this.codigoDeServicio;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public BigDecimal getYqDiferenteCero()
/* 275:    */   {
/* 276:355 */     return this.YqDiferenteCero;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setYqDiferenteCero(BigDecimal yqDiferenteCero)
/* 280:    */   {
/* 281:359 */     this.YqDiferenteCero = yqDiferenteCero;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public BigDecimal getYqCero()
/* 285:    */   {
/* 286:363 */     return this.YqCero;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setYqCero(BigDecimal yqCero)
/* 290:    */   {
/* 291:367 */     this.YqCero = yqCero;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setCodigoDeServicio(String codigoDeServicio)
/* 295:    */   {
/* 296:371 */     this.codigoDeServicio = codigoDeServicio;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public BigDecimal getTarifaPreliminar()
/* 300:    */   {
/* 301:375 */     return this.tarifaPreliminar;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setTarifaPreliminar(BigDecimal tarifaPreliminar)
/* 305:    */   {
/* 306:379 */     this.tarifaPreliminar = tarifaPreliminar;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public BigDecimal getDescuento()
/* 310:    */   {
/* 311:383 */     return this.descuento;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public BigDecimal getValorTotalPreliminar()
/* 315:    */   {
/* 316:387 */     return this.valorTotalPreliminar;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setValorTotalPreliminar(BigDecimal valorTotalPreliminar)
/* 320:    */   {
/* 321:391 */     this.valorTotalPreliminar = valorTotalPreliminar;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setDescuento(BigDecimal descuento)
/* 325:    */   {
/* 326:395 */     this.descuento = descuento;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public BigDecimal getTarifaDiferenteCero()
/* 330:    */   {
/* 331:399 */     return this.tarifaDiferenteCero;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setTarifaDiferenteCero(BigDecimal tarifaDiferenteCero)
/* 335:    */   {
/* 336:403 */     this.tarifaDiferenteCero = tarifaDiferenteCero;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public BigDecimal getTarifaCero()
/* 340:    */   {
/* 341:407 */     return this.tarifaCero;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setTarifaCero(BigDecimal tarifaCero)
/* 345:    */   {
/* 346:411 */     this.tarifaCero = tarifaCero;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public BigDecimal getAnticipo()
/* 350:    */   {
/* 351:415 */     return this.anticipo;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setAnticipo(BigDecimal anticipo)
/* 355:    */   {
/* 356:419 */     this.anticipo = anticipo;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void setDiferencia(BigDecimal diferencia)
/* 360:    */   {
/* 361:423 */     this.diferencia = diferencia;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public String getObservaciones()
/* 365:    */   {
/* 366:427 */     return this.observaciones;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setObservaciones(String observaciones)
/* 370:    */   {
/* 371:431 */     this.observaciones = observaciones;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public BigDecimal getDiferencia()
/* 375:    */   {
/* 376:435 */     return this.diferencia;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public BigDecimal getValorTotal()
/* 380:    */   {
/* 381:439 */     return this.valorTotal;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setValorTotal(BigDecimal valorTotal)
/* 385:    */   {
/* 386:443 */     this.valorTotal = valorTotal;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public Boolean getIsCredito()
/* 390:    */   {
/* 391:447 */     return this.isCredito;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setIsCredito(Boolean isCredito)
/* 395:    */   {
/* 396:451 */     this.isCredito = isCredito;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public String getIdentificacionTributaria()
/* 400:    */   {
/* 401:455 */     return this.identificacionTributaria;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setIdentificacionTributaria(String identificacionTributaria)
/* 405:    */   {
/* 406:459 */     this.identificacionTributaria = identificacionTributaria;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public String getTipoDeDocumento()
/* 410:    */   {
/* 411:463 */     return this.tipoDeDocumento;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setTipoDeDocumento(String tipoDeDocumento)
/* 415:    */   {
/* 416:467 */     this.tipoDeDocumento = tipoDeDocumento;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public String getOriginalConjuncion()
/* 420:    */   {
/* 421:472 */     return this.originalConjuncion;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setOriginalConjuncion(String originalConjuncion)
/* 425:    */   {
/* 426:476 */     this.originalConjuncion = originalConjuncion;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public int getId()
/* 430:    */   {
/* 431:481 */     return this.idTicket;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public String getNumero()
/* 435:    */   {
/* 436:485 */     return this.numero;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void setNumero(String numero)
/* 440:    */   {
/* 441:489 */     this.numero = numero;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public String getAerolinea()
/* 445:    */   {
/* 446:493 */     return this.aerolinea;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void setAerolinea(String aerolinea)
/* 450:    */   {
/* 451:497 */     this.aerolinea = aerolinea;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public Date getFecha()
/* 455:    */   {
/* 456:501 */     return this.fecha;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public void setFecha(Date fecha)
/* 460:    */   {
/* 461:505 */     this.fecha = fecha;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public Date getPeriodo()
/* 465:    */   {
/* 466:509 */     return this.periodo;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public void setPeriodo(Date periodo)
/* 470:    */   {
/* 471:513 */     this.periodo = periodo;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public String getTipo()
/* 475:    */   {
/* 476:517 */     return this.tipo;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public void setTipo(String tipo)
/* 480:    */   {
/* 481:521 */     this.tipo = tipo;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public String getMoneda()
/* 485:    */   {
/* 486:525 */     return this.moneda;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public void setMoneda(String moneda)
/* 490:    */   {
/* 491:529 */     this.moneda = moneda;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public String getOperacion()
/* 495:    */   {
/* 496:533 */     return this.operacion;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public void setOperacion(String operacion)
/* 500:    */   {
/* 501:537 */     this.operacion = operacion;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public BigDecimal getComision()
/* 505:    */   {
/* 506:541 */     return this.comision;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public void setComision(BigDecimal comision)
/* 510:    */   {
/* 511:545 */     this.comision = comision;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public BigDecimal getIva()
/* 515:    */   {
/* 516:549 */     return this.iva;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public void setIva(BigDecimal iva)
/* 520:    */   {
/* 521:553 */     this.iva = iva;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public BigDecimal getTarifa()
/* 525:    */   {
/* 526:557 */     return this.tarifa;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void setTarifa(BigDecimal tarifa)
/* 530:    */   {
/* 531:561 */     this.tarifa = tarifa;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public String getPasajero()
/* 535:    */   {
/* 536:565 */     return this.pasajero;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public void setPasajero(String pasajero)
/* 540:    */   {
/* 541:569 */     this.pasajero = pasajero;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public String getRuta()
/* 545:    */   {
/* 546:573 */     return this.ruta;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public void setRuta(String ruta)
/* 550:    */   {
/* 551:577 */     this.ruta = ruta;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public BigDecimal getBruto()
/* 555:    */   {
/* 556:581 */     return this.bruto;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public void setBruto(BigDecimal bruto)
/* 560:    */   {
/* 561:585 */     this.bruto = bruto;
/* 562:    */   }
/* 563:    */   
/* 564:    */   public BigDecimal getCredito()
/* 565:    */   {
/* 566:589 */     return this.credito;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public void setCredito(BigDecimal credito)
/* 570:    */   {
/* 571:593 */     this.credito = credito;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public BigDecimal getPorComision()
/* 575:    */   {
/* 576:597 */     return this.porComision;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public void setPorComision(BigDecimal porComision)
/* 580:    */   {
/* 581:601 */     this.porComision = porComision;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public BigDecimal getNeto()
/* 585:    */   {
/* 586:605 */     return this.neto;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public void setNeto(BigDecimal neto)
/* 590:    */   {
/* 591:609 */     this.neto = neto;
/* 592:    */   }
/* 593:    */   
/* 594:    */   public BigDecimal getTarifaMoneda()
/* 595:    */   {
/* 596:613 */     return this.tarifaMoneda;
/* 597:    */   }
/* 598:    */   
/* 599:    */   public void setTarifaMoneda(BigDecimal tarifaMoneda)
/* 600:    */   {
/* 601:617 */     this.tarifaMoneda = tarifaMoneda;
/* 602:    */   }
/* 603:    */   
/* 604:    */   public String getEmisor()
/* 605:    */   {
/* 606:621 */     return this.emisor;
/* 607:    */   }
/* 608:    */   
/* 609:    */   public void setEmisor(String emisor)
/* 610:    */   {
/* 611:625 */     this.emisor = emisor;
/* 612:    */   }
/* 613:    */   
/* 614:    */   public BigDecimal getTarjeta()
/* 615:    */   {
/* 616:629 */     return this.tarjeta;
/* 617:    */   }
/* 618:    */   
/* 619:    */   public void setTarjeta(BigDecimal tarjeta)
/* 620:    */   {
/* 621:633 */     this.tarjeta = tarjeta;
/* 622:    */   }
/* 623:    */   
/* 624:    */   public String getUsuario()
/* 625:    */   {
/* 626:637 */     return this.usuario;
/* 627:    */   }
/* 628:    */   
/* 629:    */   public void setUsuario(String usuario)
/* 630:    */   {
/* 631:641 */     this.usuario = usuario;
/* 632:    */   }
/* 633:    */   
/* 634:    */   public String getFechaComputador()
/* 635:    */   {
/* 636:645 */     return this.fechaComputador;
/* 637:    */   }
/* 638:    */   
/* 639:    */   public void setFechaComputador(String fechaComputador)
/* 640:    */   {
/* 641:649 */     this.fechaComputador = fechaComputador;
/* 642:    */   }
/* 643:    */   
/* 644:    */   public String getTicketCnj()
/* 645:    */   {
/* 646:653 */     return this.ticketCnj;
/* 647:    */   }
/* 648:    */   
/* 649:    */   public void setTicketCnj(String ticketCnj)
/* 650:    */   {
/* 651:657 */     this.ticketCnj = ticketCnj;
/* 652:    */   }
/* 653:    */   
/* 654:    */   public BigDecimal getPenalty()
/* 655:    */   {
/* 656:661 */     return this.penalty;
/* 657:    */   }
/* 658:    */   
/* 659:    */   public void setPenalty(BigDecimal penalty)
/* 660:    */   {
/* 661:665 */     this.penalty = penalty;
/* 662:    */   }
/* 663:    */   
/* 664:    */   public String getReferencia()
/* 665:    */   {
/* 666:669 */     return this.referencia;
/* 667:    */   }
/* 668:    */   
/* 669:    */   public void setReferencia(String referencia)
/* 670:    */   {
/* 671:673 */     this.referencia = referencia;
/* 672:    */   }
/* 673:    */   
/* 674:    */   public String getValex()
/* 675:    */   {
/* 676:677 */     return this.valex;
/* 677:    */   }
/* 678:    */   
/* 679:    */   public void setValex(String valex)
/* 680:    */   {
/* 681:681 */     this.valex = valex;
/* 682:    */   }
/* 683:    */   
/* 684:    */   public String getVale()
/* 685:    */   {
/* 686:685 */     return this.vale;
/* 687:    */   }
/* 688:    */   
/* 689:    */   public void setVale(String vale)
/* 690:    */   {
/* 691:689 */     this.vale = vale;
/* 692:    */   }
/* 693:    */   
/* 694:    */   public BigDecimal getRetencionIca()
/* 695:    */   {
/* 696:693 */     return this.retencionIca;
/* 697:    */   }
/* 698:    */   
/* 699:    */   public void setRetencionIca(BigDecimal retencionIca)
/* 700:    */   {
/* 701:697 */     this.retencionIca = retencionIca;
/* 702:    */   }
/* 703:    */   
/* 704:    */   public BigDecimal getRetencionIvaCom()
/* 705:    */   {
/* 706:701 */     return this.retencionIvaCom;
/* 707:    */   }
/* 708:    */   
/* 709:    */   public void setRetencionIvaCom(BigDecimal retencionIvaCom)
/* 710:    */   {
/* 711:705 */     this.retencionIvaCom = retencionIvaCom;
/* 712:    */   }
/* 713:    */   
/* 714:    */   public BigDecimal getRetencionFte()
/* 715:    */   {
/* 716:709 */     return this.retencionFte;
/* 717:    */   }
/* 718:    */   
/* 719:    */   public void setRetencionFte(BigDecimal retencionFte)
/* 720:    */   {
/* 721:713 */     this.retencionFte = retencionFte;
/* 722:    */   }
/* 723:    */   
/* 724:    */   public BigDecimal getWt()
/* 725:    */   {
/* 726:717 */     return this.wt;
/* 727:    */   }
/* 728:    */   
/* 729:    */   public void setWt(BigDecimal wt)
/* 730:    */   {
/* 731:721 */     this.wt = wt;
/* 732:    */   }
/* 733:    */   
/* 734:    */   public BigDecimal getEd()
/* 735:    */   {
/* 736:725 */     return this.ed;
/* 737:    */   }
/* 738:    */   
/* 739:    */   public void setEd(BigDecimal ed)
/* 740:    */   {
/* 741:729 */     this.ed = ed;
/* 742:    */   }
/* 743:    */   
/* 744:    */   public BigDecimal getYq()
/* 745:    */   {
/* 746:733 */     return this.yq;
/* 747:    */   }
/* 748:    */   
/* 749:    */   public void setYq(BigDecimal yq)
/* 750:    */   {
/* 751:737 */     this.yq = yq;
/* 752:    */   }
/* 753:    */   
/* 754:    */   public BigDecimal getQb()
/* 755:    */   {
/* 756:741 */     return this.qb;
/* 757:    */   }
/* 758:    */   
/* 759:    */   public void setQb(BigDecimal qb)
/* 760:    */   {
/* 761:745 */     this.qb = qb;
/* 762:    */   }
/* 763:    */   
/* 764:    */   public BigDecimal getQi()
/* 765:    */   {
/* 766:749 */     return this.qi;
/* 767:    */   }
/* 768:    */   
/* 769:    */   public void setQi(BigDecimal qi)
/* 770:    */   {
/* 771:753 */     this.qi = qi;
/* 772:    */   }
/* 773:    */   
/* 774:    */   public BigDecimal getYr()
/* 775:    */   {
/* 776:757 */     return this.yr;
/* 777:    */   }
/* 778:    */   
/* 779:    */   public void setYr(BigDecimal yr)
/* 780:    */   {
/* 781:761 */     this.yr = yr;
/* 782:    */   }
/* 783:    */   
/* 784:    */   public BigDecimal getE2()
/* 785:    */   {
/* 786:765 */     return this.e2;
/* 787:    */   }
/* 788:    */   
/* 789:    */   public void setE2(BigDecimal e2)
/* 790:    */   {
/* 791:769 */     this.e2 = e2;
/* 792:    */   }
/* 793:    */   
/* 794:    */   public Date getFechaViaje()
/* 795:    */   {
/* 796:773 */     return this.fechaViaje;
/* 797:    */   }
/* 798:    */   
/* 799:    */   public void setFechaViaje(Date fechaViaje)
/* 800:    */   {
/* 801:777 */     this.fechaViaje = fechaViaje;
/* 802:    */   }
/* 803:    */   
/* 804:    */   public BigDecimal getImpuestoExtranjero()
/* 805:    */   {
/* 806:781 */     return this.impuestoExtranjero;
/* 807:    */   }
/* 808:    */   
/* 809:    */   public void setImpuestoExtranjero(BigDecimal impuestoExtranjero)
/* 810:    */   {
/* 811:785 */     this.impuestoExtranjero = impuestoExtranjero;
/* 812:    */   }
/* 813:    */   
/* 814:    */   public BigDecimal getIvaComision()
/* 815:    */   {
/* 816:789 */     return this.ivaComision;
/* 817:    */   }
/* 818:    */   
/* 819:    */   public void setIvaComision(BigDecimal ivaComision)
/* 820:    */   {
/* 821:793 */     this.ivaComision = ivaComision;
/* 822:    */   }
/* 823:    */   
/* 824:    */   public int getIdTicket()
/* 825:    */   {
/* 826:797 */     return this.idTicket;
/* 827:    */   }
/* 828:    */   
/* 829:    */   public void setIdTicket(int idTicket)
/* 830:    */   {
/* 831:801 */     this.idTicket = idTicket;
/* 832:    */   }
/* 833:    */   
/* 834:    */   public int getIdOrganizacion()
/* 835:    */   {
/* 836:805 */     return this.idOrganizacion;
/* 837:    */   }
/* 838:    */   
/* 839:    */   public void setIdOrganizacion(int idOrganizacion)
/* 840:    */   {
/* 841:809 */     this.idOrganizacion = idOrganizacion;
/* 842:    */   }
/* 843:    */   
/* 844:    */   public int getIdSucursal()
/* 845:    */   {
/* 846:813 */     return this.idSucursal;
/* 847:    */   }
/* 848:    */   
/* 849:    */   public void setIdSucursal(int idSucursal)
/* 850:    */   {
/* 851:817 */     this.idSucursal = idSucursal;
/* 852:    */   }
/* 853:    */   
/* 854:    */   public CargaArchivo getBsp()
/* 855:    */   {
/* 856:821 */     return this.bsp;
/* 857:    */   }
/* 858:    */   
/* 859:    */   public void setBsp(CargaArchivo bsp)
/* 860:    */   {
/* 861:825 */     this.bsp = bsp;
/* 862:    */   }
/* 863:    */   
/* 864:    */   public List<DetalleTicket> getListaDetalleTicket()
/* 865:    */   {
/* 866:829 */     return this.listaDetalleTicket;
/* 867:    */   }
/* 868:    */   
/* 869:    */   public void setListaDetalleTicket(List<DetalleTicket> listaDetalleTicket)
/* 870:    */   {
/* 871:833 */     this.listaDetalleTicket = listaDetalleTicket;
/* 872:    */   }
/* 873:    */   
/* 874:    */   public Boolean getIndicadorTicketCredito()
/* 875:    */   {
/* 876:837 */     return Boolean.valueOf(this.indicadorTicketCredito == null ? false : this.indicadorTicketCredito.booleanValue());
/* 877:    */   }
/* 878:    */   
/* 879:    */   public void setIndicadorTicketCredito(Boolean indicadorTicketCredito)
/* 880:    */   {
/* 881:841 */     this.indicadorTicketCredito = indicadorTicketCredito;
/* 882:    */   }
/* 883:    */   
/* 884:    */   public PuntoDeVenta getPuntoDeVenta()
/* 885:    */   {
/* 886:845 */     return this.puntoDeVenta;
/* 887:    */   }
/* 888:    */   
/* 889:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 890:    */   {
/* 891:849 */     this.puntoDeVenta = puntoDeVenta;
/* 892:    */   }
/* 893:    */   
/* 894:    */   public Date getFechaReporte()
/* 895:    */   {
/* 896:853 */     return this.fechaReporte;
/* 897:    */   }
/* 898:    */   
/* 899:    */   public void setFechaReporte(Date fechaReporte)
/* 900:    */   {
/* 901:857 */     this.fechaReporte = fechaReporte;
/* 902:    */   }
/* 903:    */   
/* 904:    */   public String getCodigoAgente()
/* 905:    */   {
/* 906:861 */     return this.codigoAgente;
/* 907:    */   }
/* 908:    */   
/* 909:    */   public void setCodigoAgente(String codigoAgente)
/* 910:    */   {
/* 911:865 */     this.codigoAgente = codigoAgente;
/* 912:    */   }
/* 913:    */   
/* 914:    */   public Date getFechaEmision()
/* 915:    */   {
/* 916:869 */     return this.fechaEmision;
/* 917:    */   }
/* 918:    */   
/* 919:    */   public void setFechaEmision(Date fechaEmision)
/* 920:    */   {
/* 921:873 */     this.fechaEmision = fechaEmision;
/* 922:    */   }
/* 923:    */   
/* 924:    */   public String getNumeroDocumentoRelacionado()
/* 925:    */   {
/* 926:877 */     return this.numeroDocumentoRelacionado;
/* 927:    */   }
/* 928:    */   
/* 929:    */   public void setNumeroDocumentoRelacionado(String numeroDocumentoRelacionado)
/* 930:    */   {
/* 931:881 */     this.numeroDocumentoRelacionado = numeroDocumentoRelacionado;
/* 932:    */   }
/* 933:    */   
/* 934:    */   public String getTipoTransaccion()
/* 935:    */   {
/* 936:885 */     return this.tipoTransaccion;
/* 937:    */   }
/* 938:    */   
/* 939:    */   public void setTipoTransaccion(String tipoTransaccion)
/* 940:    */   {
/* 941:889 */     this.tipoTransaccion = tipoTransaccion;
/* 942:    */   }
/* 943:    */   
/* 944:    */   public boolean isIndicadorContabilizado()
/* 945:    */   {
/* 946:893 */     return this.indicadorContabilizado;
/* 947:    */   }
/* 948:    */   
/* 949:    */   public void setIndicadorContabilizado(boolean indicadorContabilizado)
/* 950:    */   {
/* 951:897 */     this.indicadorContabilizado = indicadorContabilizado;
/* 952:    */   }
/* 953:    */   
/* 954:    */   public String getCodigoDisenioTicket()
/* 955:    */   {
/* 956:901 */     return this.codigoDisenioTicket;
/* 957:    */   }
/* 958:    */   
/* 959:    */   public void setCodigoDisenioTicket(String codigoDisenioTicket)
/* 960:    */   {
/* 961:905 */     this.codigoDisenioTicket = codigoDisenioTicket;
/* 962:    */   }
/* 963:    */   
/* 964:    */   public String getNumeroPeriodo()
/* 965:    */   {
/* 966:909 */     return this.numeroPeriodo;
/* 967:    */   }
/* 968:    */   
/* 969:    */   public void setNumeroPeriodo(String numeroPeriodo)
/* 970:    */   {
/* 971:913 */     this.numeroPeriodo = numeroPeriodo;
/* 972:    */   }
/* 973:    */   
/* 974:    */   public String getPeriodoBSP()
/* 975:    */   {
/* 976:917 */     return this.periodoBSP;
/* 977:    */   }
/* 978:    */   
/* 979:    */   public void setPeriodoBSP(String periodoBSP)
/* 980:    */   {
/* 981:921 */     this.periodoBSP = periodoBSP;
/* 982:    */   }
/* 983:    */   
/* 984:    */   public Asiento getAsiento()
/* 985:    */   {
/* 986:925 */     return this.asiento;
/* 987:    */   }
/* 988:    */   
/* 989:    */   public void setAsiento(Asiento asiento)
/* 990:    */   {
/* 991:929 */     this.asiento = asiento;
/* 992:    */   }
/* 993:    */   
/* 994:    */   public boolean isEditado()
/* 995:    */   {
/* 996:933 */     return this.editado;
/* 997:    */   }
/* 998:    */   
/* 999:    */   public void setEditado(boolean editado)
/* :00:    */   {
/* :01:937 */     this.editado = editado;
/* :02:    */   }
/* :03:    */   
/* :04:    */   public String getTipoTarjetaCredito()
/* :05:    */   {
/* :06:941 */     return this.tipoTarjetaCredito;
/* :07:    */   }
/* :08:    */   
/* :09:    */   public void setTipoTarjetaCredito(String tipoTarjetaCredito)
/* :10:    */   {
/* :11:945 */     this.tipoTarjetaCredito = tipoTarjetaCredito;
/* :12:    */   }
/* :13:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.aerolineas.Ticket
 * JD-Core Version:    0.7.0.1
 */