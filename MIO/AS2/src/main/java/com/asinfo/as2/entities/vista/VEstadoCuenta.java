/*   1:    */ package com.asinfo.as2.entities.vista;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.Temporal;
/*  12:    */ import javax.persistence.TemporalType;
/*  13:    */ import javax.persistence.Transient;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="v_estado_cuenta")
/*  17:    */ public class VEstadoCuenta
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 7326578415870430809L;
/*  21:    */   @Id
/*  22:    */   @Column(name="id_factura_cliente")
/*  23:    */   private int idFacturaCliente;
/*  24:    */   @Id
/*  25:    */   @Column(name="id_organizacion")
/*  26:    */   private int idOrganizacion;
/*  27:    */   @Id
/*  28:    */   @Column(name="id_sucursal")
/*  29:    */   private int idSucursal;
/*  30:    */   @Id
/*  31:    */   @Column(name="id_zona")
/*  32:    */   private int idZona;
/*  33:    */   @Id
/*  34:    */   @Column(name="id_empresa")
/*  35:    */   private int idEmpresa;
/*  36:    */   @Id
/*  37:    */   @Column(name="id_agente_comercial")
/*  38:    */   private int idAgenteComercial;
/*  39:    */   @Id
/*  40:    */   @Column(name="nombre_fiscal")
/*  41:    */   private String nombreFiscal;
/*  42:    */   @Id
/*  43:    */   @Column(name="codigo")
/*  44:    */   private String codigo;
/*  45:    */   @Id
/*  46:    */   @Column(name="identificacion")
/*  47:    */   private String identificacion;
/*  48:    */   @Id
/*  49:    */   @Column(name="codigo_tipo_identificacion")
/*  50:    */   private String codigoTipoIdentificacion;
/*  51:    */   @Id
/*  52:    */   @Column(name="numero_factura")
/*  53:    */   private String numeroFactura;
/*  54:    */   @Id
/*  55:    */   @Column(name="fecha_factura")
/*  56:    */   @Temporal(TemporalType.DATE)
/*  57:    */   private Date fechaFactura;
/*  58:    */   @Id
/*  59:    */   @Column(name="descripcion_documento")
/*  60:    */   private String descripcionDocumento;
/*  61:    */   @Id
/*  62:    */   @Temporal(TemporalType.DATE)
/*  63:    */   @Column(name="fecha_documento")
/*  64:    */   private Date fechaDocumento;
/*  65:    */   @Id
/*  66:    */   @Column(name="numero_documento")
/*  67:    */   private String numeroDocumento;
/*  68:    */   @Id
/*  69:    */   @Column(name="codigo_documento")
/*  70:    */   private String codigoDocumento;
/*  71:    */   @Id
/*  72:    */   @Column(name="codigo_documento_proceso")
/*  73:    */   private String codigoDocumentoProceso;
/*  74:    */   @Id
/*  75:    */   @Column(name="nombre_documento")
/*  76:    */   private String nombreDocumento;
/*  77:    */   @Id
/*  78:    */   @Column(name="id_cuenta_por_cobrar")
/*  79:    */   private int idCuentaPorCobrar;
/*  80:    */   @Id
/*  81:    */   @Temporal(TemporalType.DATE)
/*  82:    */   @Column(name="fecha_vence")
/*  83:    */   private Date fechaVencimiento;
/*  84:    */   @Id
/*  85:    */   @Temporal(TemporalType.DATE)
/*  86:    */   @Column(name="fecha_vencimiento_factura")
/*  87:    */   private Date fechaVencimientoFactura;
/*  88:    */   @Id
/*  89:    */   @Column(name="tipo_empresa")
/*  90:    */   private int tipoEmpresa;
/*  91:    */   @Id
/*  92:    */   @Column(name="origen_ingresos_codigo")
/*  93:    */   private String origenIngresosCodigo;
/*  94:    */   @Id
/*  95:    */   @Column(name="dias_plazo_factura")
/*  96:    */   private String diasPlazoFactura;
/*  97:    */   @Id
/*  98:    */   @Column(name="debito")
/*  99:    */   private BigDecimal debito;
/* 100:    */   @Id
/* 101:    */   @Column(name="credito")
/* 102:    */   private BigDecimal credito;
/* 103:    */   @Id
/* 104:    */   @Column(name="valor_bloqueado")
/* 105:    */   private BigDecimal valorBloqueado;
/* 106:    */   @Id
/* 107:    */   @Column(name="id_recaudador")
/* 108:    */   private int idRecaudador;
/* 109:    */   @Column(name="nombre_recaudador")
/* 110:    */   private String nombreRecaudador;
/* 111:    */   @Column(name="nombre_agente_comercial")
/* 112:    */   private String nombreAgenteComercial;
/* 113:    */   @Id
/* 114:    */   @Column(name="id_subempresa")
/* 115:    */   private int idSubempresa;
/* 116:    */   @Id
/* 117:    */   @Column(name="empresa_final")
/* 118:    */   private String empresaFinal;
/* 119:    */   @Id
/* 120:    */   @Column(name="identificacion_subempresa")
/* 121:    */   private String identificacionSubempresa;
/* 122:    */   @Id
/* 123:    */   @Column(name="nombre_fiscal_subempresa")
/* 124:    */   private String nombreFiscalSubempresa;
/* 125:    */   @Id
/* 126:    */   @Column(name="indicador_generada_protesto")
/* 127:    */   private boolean indicadorGeneradaProtesto;
/* 128:    */   @Id
/* 129:    */   @Column(name="cheque_protestado")
/* 130:    */   private String chequeProtestado;
/* 131:    */   @Id
/* 132:    */   @Column(name="asiento_venta")
/* 133:    */   private String asientoVenta;
/* 134:    */   @Id
/* 135:    */   @Column(name="asiento_documento")
/* 136:    */   private String asientoDocumento;
/* 137:    */   @Id
/* 138:    */   @Column(name="id_cobro")
/* 139:    */   private int idCobro;
/* 140:    */   @Id
/* 141:    */   @Column(name="referencia1")
/* 142:    */   private String referencia1;
/* 143:    */   @Id
/* 144:    */   @Column(name="referencia2")
/* 145:    */   private String referencia2;
/* 146:    */   @Id
/* 147:    */   @Column(name="referencia3")
/* 148:    */   private String referencia3;
/* 149:    */   @Id
/* 150:    */   @Column(name="referencia4")
/* 151:    */   private String referencia4;
/* 152:    */   @Id
/* 153:    */   @Column(name="referencia5")
/* 154:    */   private String referencia5;
/* 155:    */   @Id
/* 156:    */   @Column(name="referencia6")
/* 157:    */   private String referencia6;
/* 158:    */   @Id
/* 159:    */   @Column(name="valor_referencia1")
/* 160:    */   private BigDecimal valorReferencia1;
/* 161:    */   @Id
/* 162:    */   @Column(name="valor_referencia2")
/* 163:    */   private BigDecimal valorReferencia2;
/* 164:    */   @Id
/* 165:    */   @Column(name="valor_referencia3")
/* 166:    */   private BigDecimal valorReferencia3;
/* 167:    */   @Id
/* 168:    */   @Column(name="documento_base")
/* 169:    */   private DocumentoBase documentoBase;
/* 170:    */   @Transient
/* 171:    */   private BigDecimal saldoCorteFechaResumido;
/* 172:    */   @Transient
/* 173:    */   private BigDecimal saldo;
/* 174:    */   @Transient
/* 175:    */   private BigDecimal traVencido150;
/* 176:    */   @Transient
/* 177:    */   private BigDecimal traVencido120;
/* 178:    */   @Transient
/* 179:    */   private BigDecimal traVencido90;
/* 180:    */   @Transient
/* 181:    */   private BigDecimal traVencido60;
/* 182:    */   @Transient
/* 183:    */   private BigDecimal traVencido45;
/* 184:    */   @Transient
/* 185:    */   private BigDecimal traVencido30;
/* 186:    */   @Transient
/* 187:    */   private BigDecimal traVencido15;
/* 188:    */   @Transient
/* 189:    */   private BigDecimal traPorVencer30;
/* 190:    */   @Transient
/* 191:    */   private BigDecimal traPorVencer60;
/* 192:    */   @Transient
/* 193:    */   private BigDecimal traPorVencer90;
/* 194:    */   @Transient
/* 195:    */   private BigDecimal totalFactura;
/* 196:    */   
/* 197:    */   public VEstadoCuenta() {}
/* 198:    */   
/* 199:    */   public VEstadoCuenta(String nombreFiscal, String codigo, String identificacion, BigDecimal saldoCorteFechaResumido)
/* 200:    */   {
/* 201:299 */     this.nombreFiscal = nombreFiscal;
/* 202:300 */     this.codigo = codigo;
/* 203:301 */     this.identificacion = identificacion;
/* 204:302 */     this.saldoCorteFechaResumido = saldoCorteFechaResumido;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public VEstadoCuenta(String identificacion, String nombreFiscal, Date fechaFactura, String numeroFactura, BigDecimal saldo)
/* 208:    */   {
/* 209:314 */     this.nombreFiscal = nombreFiscal;
/* 210:315 */     this.identificacion = identificacion;
/* 211:316 */     this.numeroFactura = numeroFactura;
/* 212:317 */     this.fechaFactura = fechaFactura;
/* 213:318 */     this.saldo = saldo;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public VEstadoCuenta(String codigo, String nombreFiscal, String identificacion, String numeroFactura, BigDecimal traVencido150, BigDecimal traVencido120, BigDecimal traVencido90, BigDecimal traVencido60, BigDecimal traVencido45, BigDecimal traVencido30, BigDecimal traVencido15, BigDecimal traPorVencer30, BigDecimal traPorVencer60, BigDecimal traPorVencer90, BigDecimal totalFactura)
/* 217:    */   {
/* 218:339 */     this.codigo = codigo;
/* 219:340 */     this.nombreFiscal = nombreFiscal;
/* 220:341 */     this.identificacion = identificacion;
/* 221:342 */     this.numeroFactura = numeroFactura;
/* 222:343 */     this.traVencido150 = traVencido150;
/* 223:344 */     this.traVencido120 = traVencido120;
/* 224:345 */     this.traVencido90 = traVencido90;
/* 225:346 */     this.traVencido60 = traVencido60;
/* 226:347 */     this.traVencido45 = traVencido45;
/* 227:348 */     this.traVencido30 = traVencido30;
/* 228:349 */     this.traVencido15 = traVencido15;
/* 229:350 */     this.traPorVencer30 = traPorVencer30;
/* 230:351 */     this.traPorVencer60 = traPorVencer60;
/* 231:352 */     this.traPorVencer90 = traPorVencer90;
/* 232:353 */     this.totalFactura = totalFactura;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public int getIdFacturaCliente()
/* 236:    */   {
/* 237:365 */     return this.idFacturaCliente;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public int getIdEmpresa()
/* 241:    */   {
/* 242:374 */     return this.idEmpresa;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public String getNombreFiscal()
/* 246:    */   {
/* 247:383 */     return this.nombreFiscal;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String getCodigo()
/* 251:    */   {
/* 252:392 */     return this.codigo;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public String getIdentificacion()
/* 256:    */   {
/* 257:401 */     return this.identificacion;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String getDescripcionDocumento()
/* 261:    */   {
/* 262:410 */     return this.descripcionDocumento;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Date getFechaDocumento()
/* 266:    */   {
/* 267:419 */     return this.fechaDocumento;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public String getNumeroDocumento()
/* 271:    */   {
/* 272:428 */     return this.numeroDocumento;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public String getNombreDocumento()
/* 276:    */   {
/* 277:437 */     return this.nombreDocumento;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public int getIdCuentaPorCobrar()
/* 281:    */   {
/* 282:446 */     return this.idCuentaPorCobrar;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public Date getFechaVencimiento()
/* 286:    */   {
/* 287:455 */     return this.fechaVencimiento;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public BigDecimal getDebito()
/* 291:    */   {
/* 292:464 */     if (this.debito == null) {
/* 293:465 */       this.debito = BigDecimal.ZERO;
/* 294:    */     }
/* 295:467 */     return this.debito;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public BigDecimal getCredito()
/* 299:    */   {
/* 300:476 */     if (this.credito == null) {
/* 301:477 */       this.credito = BigDecimal.ZERO;
/* 302:    */     }
/* 303:479 */     return this.credito;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public BigDecimal getSaldoCorteFechaResumido()
/* 307:    */   {
/* 308:488 */     return this.saldoCorteFechaResumido;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setSaldoCorteFechaResumido(BigDecimal saldoCorteFechaResumido)
/* 312:    */   {
/* 313:498 */     this.saldoCorteFechaResumido = saldoCorteFechaResumido;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public BigDecimal getSaldo()
/* 317:    */   {
/* 318:507 */     return this.saldo;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setSaldo(BigDecimal saldo)
/* 322:    */   {
/* 323:517 */     this.saldo = saldo;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public String getNumeroFactura()
/* 327:    */   {
/* 328:526 */     return this.numeroFactura;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public Date getFechaFactura()
/* 332:    */   {
/* 333:535 */     return this.fechaFactura;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public String toString()
/* 337:    */   {
/* 338:545 */     return "VEstadoCuenta [idFacturaCliente=" + this.idFacturaCliente + ", idEmpresa=" + this.idEmpresa + ", nombreFiscal=" + this.nombreFiscal + ", codigo=" + this.codigo + ", identificacion=" + this.identificacion + ", descripcionDocumento=" + this.descripcionDocumento + ", fechaDocumento=" + this.fechaDocumento + ", numeroDocumento=" + this.numeroDocumento + ", nombreDocumento=" + this.nombreDocumento + ", fechaVencimiento=" + this.fechaVencimiento + ", debito=" + this.debito + ", credito=" + this.credito + ", saldoCorteFechaResumido=" + this.saldoCorteFechaResumido + "]";
/* 339:    */   }
/* 340:    */   
/* 341:    */   public BigDecimal getTraVencido150()
/* 342:    */   {
/* 343:552 */     if (this.traVencido150 == null) {
/* 344:553 */       this.traVencido150 = BigDecimal.ZERO;
/* 345:    */     }
/* 346:555 */     return this.traVencido150;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public BigDecimal getTraVencido120()
/* 350:    */   {
/* 351:559 */     if (this.traVencido120 == null) {
/* 352:560 */       this.traVencido120 = BigDecimal.ZERO;
/* 353:    */     }
/* 354:562 */     return this.traVencido120;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public BigDecimal getTraVencido90()
/* 358:    */   {
/* 359:566 */     if (this.traVencido90 == null) {
/* 360:567 */       this.traVencido90 = BigDecimal.ZERO;
/* 361:    */     }
/* 362:569 */     return this.traVencido90;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public BigDecimal getTraVencido60()
/* 366:    */   {
/* 367:573 */     if (this.traVencido60 == null) {
/* 368:574 */       this.traVencido60 = BigDecimal.ZERO;
/* 369:    */     }
/* 370:576 */     return this.traVencido60;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public BigDecimal getTraVencido45()
/* 374:    */   {
/* 375:580 */     if (this.traVencido45 == null) {
/* 376:581 */       this.traVencido45 = BigDecimal.ZERO;
/* 377:    */     }
/* 378:583 */     return this.traVencido45;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public BigDecimal getTraVencido30()
/* 382:    */   {
/* 383:587 */     if (this.traVencido30 == null) {
/* 384:588 */       this.traVencido30 = BigDecimal.ZERO;
/* 385:    */     }
/* 386:590 */     return this.traVencido30;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public BigDecimal getTraVencido15()
/* 390:    */   {
/* 391:594 */     if (this.traVencido15 == null) {
/* 392:595 */       this.traVencido15 = BigDecimal.ZERO;
/* 393:    */     }
/* 394:597 */     return this.traVencido15;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public BigDecimal getTraPorVencer30()
/* 398:    */   {
/* 399:601 */     if (this.traPorVencer30 == null) {
/* 400:602 */       this.traPorVencer30 = BigDecimal.ZERO;
/* 401:    */     }
/* 402:604 */     return this.traPorVencer30;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public BigDecimal getTraPorVencer60()
/* 406:    */   {
/* 407:608 */     if (this.traPorVencer60 == null) {
/* 408:609 */       this.traPorVencer60 = BigDecimal.ZERO;
/* 409:    */     }
/* 410:611 */     return this.traPorVencer60;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public BigDecimal getTraPorVencer90()
/* 414:    */   {
/* 415:615 */     if (this.traPorVencer90 == null) {
/* 416:616 */       this.traPorVencer90 = BigDecimal.ZERO;
/* 417:    */     }
/* 418:618 */     return this.traPorVencer90;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public BigDecimal getTotalFactura()
/* 422:    */   {
/* 423:622 */     if (this.totalFactura == null) {
/* 424:623 */       this.totalFactura = BigDecimal.ZERO;
/* 425:    */     }
/* 426:625 */     return this.totalFactura;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public int getIdRecaudador()
/* 430:    */   {
/* 431:634 */     return this.idRecaudador;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public String getNombreRecaudador()
/* 435:    */   {
/* 436:643 */     return this.nombreRecaudador;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public int getIdOrganizacion()
/* 440:    */   {
/* 441:652 */     return this.idOrganizacion;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public int getIdSucursal()
/* 445:    */   {
/* 446:659 */     return this.idSucursal;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void setIdSucursal(int idSucursal)
/* 450:    */   {
/* 451:666 */     this.idSucursal = idSucursal;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public int getIdZona()
/* 455:    */   {
/* 456:673 */     return this.idZona;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public void setIdZona(int idZona)
/* 460:    */   {
/* 461:680 */     this.idZona = idZona;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public int getIdSubempresa()
/* 465:    */   {
/* 466:689 */     return this.idSubempresa;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public String getEmpresaFinal()
/* 470:    */   {
/* 471:698 */     return this.empresaFinal;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public String getIdentificacionSubempresa()
/* 475:    */   {
/* 476:707 */     return this.identificacionSubempresa;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public String getNombreFiscalSubempresa()
/* 480:    */   {
/* 481:716 */     return this.nombreFiscalSubempresa;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public boolean isIndicadorGeneradaProtesto()
/* 485:    */   {
/* 486:720 */     return this.indicadorGeneradaProtesto;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public void setIndicadorGeneradaProtesto(boolean indicadorGeneradaProtesto)
/* 490:    */   {
/* 491:724 */     this.indicadorGeneradaProtesto = indicadorGeneradaProtesto;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public int getIdAgenteComercial()
/* 495:    */   {
/* 496:733 */     return this.idAgenteComercial;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public void setIdAgenteComercial(int idAgenteComercial)
/* 500:    */   {
/* 501:743 */     this.idAgenteComercial = idAgenteComercial;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public String getCodigoDocumento()
/* 505:    */   {
/* 506:752 */     return this.codigoDocumento;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public String getChequeProtestado()
/* 510:    */   {
/* 511:759 */     return this.chequeProtestado;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void setChequeProtestado(String chequeProtestado)
/* 515:    */   {
/* 516:767 */     this.chequeProtestado = chequeProtestado;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public String getCodigoDocumentoProceso()
/* 520:    */   {
/* 521:771 */     return this.codigoDocumentoProceso;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public BigDecimal getValorBloqueado()
/* 525:    */   {
/* 526:775 */     return this.valorBloqueado;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void setValorBloqueado(BigDecimal valorBloqueado)
/* 530:    */   {
/* 531:779 */     this.valorBloqueado = valorBloqueado;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public String getAsientoVenta()
/* 535:    */   {
/* 536:783 */     return this.asientoVenta;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public void setAsientoVenta(String asientoVenta)
/* 540:    */   {
/* 541:787 */     this.asientoVenta = asientoVenta;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public String getAsientoDocumento()
/* 545:    */   {
/* 546:791 */     return this.asientoDocumento;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public void setAsientoDocumento(String asientoDocumento)
/* 550:    */   {
/* 551:795 */     this.asientoDocumento = asientoDocumento;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public String getReferencia2()
/* 555:    */   {
/* 556:799 */     return this.referencia2;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public void setReferencia2(String referencia2)
/* 560:    */   {
/* 561:803 */     this.referencia2 = referencia2;
/* 562:    */   }
/* 563:    */   
/* 564:    */   public String getReferencia3()
/* 565:    */   {
/* 566:807 */     return this.referencia3;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public void setReferencia3(String referencia3)
/* 570:    */   {
/* 571:811 */     this.referencia3 = referencia3;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public BigDecimal getValorReferencia1()
/* 575:    */   {
/* 576:815 */     return this.valorReferencia1;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public void setValorReferencia1(BigDecimal valorReferencia1)
/* 580:    */   {
/* 581:819 */     this.valorReferencia1 = valorReferencia1;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public BigDecimal getValorReferencia2()
/* 585:    */   {
/* 586:823 */     return this.valorReferencia2;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public void setValorReferencia2(BigDecimal valorReferencia2)
/* 590:    */   {
/* 591:827 */     this.valorReferencia2 = valorReferencia2;
/* 592:    */   }
/* 593:    */   
/* 594:    */   public BigDecimal getValorReferencia3()
/* 595:    */   {
/* 596:831 */     return this.valorReferencia3;
/* 597:    */   }
/* 598:    */   
/* 599:    */   public void setValorReferencia3(BigDecimal valorReferencia3)
/* 600:    */   {
/* 601:835 */     this.valorReferencia3 = valorReferencia3;
/* 602:    */   }
/* 603:    */   
/* 604:    */   public Date getFechaVencimientoFactura()
/* 605:    */   {
/* 606:839 */     return this.fechaVencimientoFactura;
/* 607:    */   }
/* 608:    */   
/* 609:    */   public void setFechaVencimientoFactura(Date fechaVencimientoFactura)
/* 610:    */   {
/* 611:843 */     this.fechaVencimientoFactura = fechaVencimientoFactura;
/* 612:    */   }
/* 613:    */   
/* 614:    */   public String getReferencia1()
/* 615:    */   {
/* 616:847 */     return this.referencia1;
/* 617:    */   }
/* 618:    */   
/* 619:    */   public String getReferencia4()
/* 620:    */   {
/* 621:851 */     return this.referencia4;
/* 622:    */   }
/* 623:    */   
/* 624:    */   public void setReferencia4(String referencia4)
/* 625:    */   {
/* 626:855 */     this.referencia4 = referencia4;
/* 627:    */   }
/* 628:    */   
/* 629:    */   public String getReferencia5()
/* 630:    */   {
/* 631:859 */     return this.referencia5;
/* 632:    */   }
/* 633:    */   
/* 634:    */   public void setReferencia5(String referencia5)
/* 635:    */   {
/* 636:863 */     this.referencia5 = referencia5;
/* 637:    */   }
/* 638:    */   
/* 639:    */   public String getReferencia6()
/* 640:    */   {
/* 641:867 */     return this.referencia6;
/* 642:    */   }
/* 643:    */   
/* 644:    */   public void setReferencia6(String referencia6)
/* 645:    */   {
/* 646:871 */     this.referencia6 = referencia6;
/* 647:    */   }
/* 648:    */   
/* 649:    */   public void setReferencia1(String referencia1)
/* 650:    */   {
/* 651:875 */     this.referencia1 = referencia1;
/* 652:    */   }
/* 653:    */   
/* 654:    */   public DocumentoBase getDocumentoBase()
/* 655:    */   {
/* 656:879 */     return this.documentoBase;
/* 657:    */   }
/* 658:    */   
/* 659:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 660:    */   {
/* 661:883 */     this.documentoBase = documentoBase;
/* 662:    */   }
/* 663:    */   
/* 664:    */   public int getIdCobro()
/* 665:    */   {
/* 666:887 */     return this.idCobro;
/* 667:    */   }
/* 668:    */   
/* 669:    */   public void setIdCobro(int idCobro)
/* 670:    */   {
/* 671:891 */     this.idCobro = idCobro;
/* 672:    */   }
/* 673:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.vista.VEstadoCuenta
 * JD-Core Version:    0.7.0.1
 */