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
/*  16:    */ @Table(name="v_estado_cuenta_proveedor")
/*  17:    */ public class VEstadoCuentaProveedor
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 7326578415870430809L;
/*  21:    */   @Id
/*  22:    */   @Column(name="id_organizacion")
/*  23:    */   private int idOrganizacion;
/*  24:    */   @Id
/*  25:    */   @Column(name="id_factura_proveedor")
/*  26:    */   private int idFacturaProveedor;
/*  27:    */   @Id
/*  28:    */   @Column(name="id_sucursal")
/*  29:    */   private int idSucursal;
/*  30:    */   @Id
/*  31:    */   @Column(name="nombre_sucursal")
/*  32:    */   private String nombreSucursal;
/*  33:    */   @Id
/*  34:    */   @Column(name="id_empresa")
/*  35:    */   private int idEmpresa;
/*  36:    */   @Id
/*  37:    */   @Column(name="id_tipo_operacion")
/*  38:    */   private int idTipoOperacion;
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
/*  49:    */   @Column(name="numero_factura")
/*  50:    */   private String numeroFactura;
/*  51:    */   @Id
/*  52:    */   @Temporal(TemporalType.DATE)
/*  53:    */   @Column(name="fecha_factura")
/*  54:    */   private Date fechaFactura;
/*  55:    */   @Id
/*  56:    */   @Temporal(TemporalType.DATE)
/*  57:    */   @Column(name="fecha_recepcion_factura")
/*  58:    */   private Date fechaRecepcionFactura;
/*  59:    */   @Id
/*  60:    */   @Column(name="descripcion_documento")
/*  61:    */   private String descripcionDocumento;
/*  62:    */   @Id
/*  63:    */   @Column(name="descripcion_factura")
/*  64:    */   private String descripcionFactura;
/*  65:    */   @Id
/*  66:    */   @Temporal(TemporalType.DATE)
/*  67:    */   @Column(name="fecha_documento")
/*  68:    */   private Date fechaDocumento;
/*  69:    */   @Id
/*  70:    */   @Column(name="numero_documento")
/*  71:    */   private String numeroDocumento;
/*  72:    */   @Id
/*  73:    */   @Column(name="codigo_documento")
/*  74:    */   private String codigoDocumento;
/*  75:    */   @Id
/*  76:    */   @Column(name="codigo_documento_proceso")
/*  77:    */   private String codigoDocumentoProceso;
/*  78:    */   @Id
/*  79:    */   @Column(name="nombre_documento")
/*  80:    */   private String nombreDocumento;
/*  81:    */   @Id
/*  82:    */   @Column(name="id_cuenta_por_pagar")
/*  83:    */   private int idCuentaPorPagar;
/*  84:    */   @Id
/*  85:    */   @Temporal(TemporalType.DATE)
/*  86:    */   @Column(name="fecha_vence")
/*  87:    */   private Date fechaVencimiento;
/*  88:    */   @Id
/*  89:    */   @Column(name="debito")
/*  90:    */   private BigDecimal debito;
/*  91:    */   @Id
/*  92:    */   @Column(name="credito")
/*  93:    */   private BigDecimal credito;
/*  94:    */   @Id
/*  95:    */   @Column(name="tipo_operacion")
/*  96:    */   private String tipoOperacion;
/*  97:    */   @Id
/*  98:    */   @Column(name="asiento_compra")
/*  99:    */   private String asientoCompra;
/* 100:    */   @Id
/* 101:    */   @Column(name="asiento_documento")
/* 102:    */   private String asientoDocumento;
/* 103:    */   @Id
/* 104:    */   @Column(name="valor_retenido")
/* 105:    */   private BigDecimal valorRetenido;
/* 106:    */   @Id
/* 107:    */   @Column(name="referencia1")
/* 108:    */   private String referencia1;
/* 109:    */   @Id
/* 110:    */   @Column(name="referencia2")
/* 111:    */   private String referencia2;
/* 112:    */   @Id
/* 113:    */   @Column(name="valor_referencia1")
/* 114:    */   private BigDecimal valorReferencia1;
/* 115:    */   @Id
/* 116:    */   @Column(name="valor_referencia2")
/* 117:    */   private BigDecimal valorReferencia2;
/* 118:    */   @Id
/* 119:    */   @Column(name="valor_referencia3")
/* 120:    */   private BigDecimal valorReferencia3;
/* 121:    */   @Id
/* 122:    */   @Column(name="id_categoria_empresa")
/* 123:    */   private int idCategoriaEmpresa;
/* 124:    */   @Id
/* 125:    */   @Column(name="nombre_categoria_empresa")
/* 126:    */   private String nombreCategoriaEmpresa;
/* 127:    */   @Id
/* 128:    */   @Column(name="id_pago")
/* 129:    */   private int idPago;
/* 130:    */   @Id
/* 131:    */   @Column(name="documento_base")
/* 132:    */   private DocumentoBase documentoBase;
/* 133:    */   @Id
/* 134:    */   @Temporal(TemporalType.DATE)
/* 135:    */   @Column(name="fecha_emision")
/* 136:    */   private Date fechaEmision;
/* 137:    */   @Transient
/* 138:    */   private BigDecimal saldoCorteFechaResumido;
/* 139:    */   @Transient
/* 140:    */   private BigDecimal saldo;
/* 141:    */   @Transient
/* 142:    */   private BigDecimal traVencido150;
/* 143:    */   @Transient
/* 144:    */   private BigDecimal traVencido120;
/* 145:    */   @Transient
/* 146:    */   private BigDecimal traVencido90;
/* 147:    */   @Transient
/* 148:    */   private BigDecimal traVencido60;
/* 149:    */   @Transient
/* 150:    */   private BigDecimal traVencido45;
/* 151:    */   @Transient
/* 152:    */   private BigDecimal traVencido30;
/* 153:    */   @Transient
/* 154:    */   private BigDecimal traVencido15;
/* 155:    */   @Transient
/* 156:    */   private BigDecimal traPorVencer30;
/* 157:    */   @Transient
/* 158:    */   private BigDecimal traPorVencer60;
/* 159:    */   @Transient
/* 160:    */   private BigDecimal traPorVencer90;
/* 161:    */   @Transient
/* 162:    */   private BigDecimal totalFactura;
/* 163:    */   
/* 164:    */   public VEstadoCuentaProveedor() {}
/* 165:    */   
/* 166:    */   public VEstadoCuentaProveedor(String nombreFiscal, String codigo, String identificacion, BigDecimal saldoCorteFechaResumido)
/* 167:    */   {
/* 168:244 */     this.nombreFiscal = nombreFiscal;
/* 169:245 */     this.codigo = codigo;
/* 170:246 */     this.identificacion = identificacion;
/* 171:247 */     this.saldoCorteFechaResumido = saldoCorteFechaResumido;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public VEstadoCuentaProveedor(String identificacion, String nombreFiscal, Date fechaFactura, String numeroFactura, BigDecimal saldo)
/* 175:    */   {
/* 176:259 */     this.nombreFiscal = nombreFiscal;
/* 177:260 */     this.identificacion = identificacion;
/* 178:261 */     this.numeroFactura = numeroFactura;
/* 179:262 */     this.fechaFactura = fechaFactura;
/* 180:263 */     this.saldo = saldo;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public VEstadoCuentaProveedor(String codigo, String nombreFiscal, String identificacion, String numeroFactura, BigDecimal traVencido150, BigDecimal traVencido120, BigDecimal traVencido90, BigDecimal traVencido60, BigDecimal traVencido45, BigDecimal traVencido30, BigDecimal traVencido15, BigDecimal traPorVencer30, BigDecimal traPorVencer60, BigDecimal traPorVencer90, BigDecimal totalFactura)
/* 184:    */   {
/* 185:284 */     this.codigo = codigo;
/* 186:285 */     this.nombreFiscal = nombreFiscal;
/* 187:286 */     this.identificacion = identificacion;
/* 188:287 */     this.numeroFactura = numeroFactura;
/* 189:288 */     this.traVencido150 = traVencido150;
/* 190:289 */     this.traVencido120 = traVencido120;
/* 191:290 */     this.traVencido90 = traVencido90;
/* 192:291 */     this.traVencido60 = traVencido60;
/* 193:292 */     this.traVencido45 = traVencido45;
/* 194:293 */     this.traVencido30 = traVencido30;
/* 195:294 */     this.traVencido15 = traVencido15;
/* 196:295 */     this.traPorVencer30 = traPorVencer30;
/* 197:296 */     this.traPorVencer60 = traPorVencer60;
/* 198:297 */     this.traPorVencer90 = traPorVencer90;
/* 199:298 */     this.totalFactura = totalFactura;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public int getIdFacturaProveedor()
/* 203:    */   {
/* 204:310 */     return this.idFacturaProveedor;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int getIdSucursal()
/* 208:    */   {
/* 209:317 */     return this.idSucursal;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setIdSucursal(int idSucursal)
/* 213:    */   {
/* 214:325 */     this.idSucursal = idSucursal;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String getNombreSucursal()
/* 218:    */   {
/* 219:332 */     return this.nombreSucursal;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setNombreSucursal(String nombreSucursal)
/* 223:    */   {
/* 224:340 */     this.nombreSucursal = nombreSucursal;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public int getIdEmpresa()
/* 228:    */   {
/* 229:349 */     return this.idEmpresa;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public int getIdTipoOperacion()
/* 233:    */   {
/* 234:358 */     return this.idTipoOperacion;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getNombreFiscal()
/* 238:    */   {
/* 239:367 */     return this.nombreFiscal;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public String getCodigo()
/* 243:    */   {
/* 244:376 */     return this.codigo;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String getIdentificacion()
/* 248:    */   {
/* 249:385 */     return this.identificacion;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public String getDescripcionDocumento()
/* 253:    */   {
/* 254:394 */     return this.descripcionDocumento;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Date getFechaDocumento()
/* 258:    */   {
/* 259:403 */     return this.fechaDocumento;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public String getNumeroDocumento()
/* 263:    */   {
/* 264:412 */     return this.numeroDocumento;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public String getNombreDocumento()
/* 268:    */   {
/* 269:421 */     return this.nombreDocumento;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public int getIdCuentaPorPagar()
/* 273:    */   {
/* 274:430 */     return this.idCuentaPorPagar;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Date getFechaVencimiento()
/* 278:    */   {
/* 279:439 */     return this.fechaVencimiento;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public BigDecimal getDebito()
/* 283:    */   {
/* 284:448 */     if (this.debito == null) {
/* 285:449 */       this.debito = BigDecimal.ZERO;
/* 286:    */     }
/* 287:451 */     return this.debito;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public BigDecimal getCredito()
/* 291:    */   {
/* 292:460 */     if (this.credito == null) {
/* 293:461 */       this.credito = BigDecimal.ZERO;
/* 294:    */     }
/* 295:463 */     return this.credito;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public BigDecimal getSaldoCorteFechaResumido()
/* 299:    */   {
/* 300:472 */     return this.saldoCorteFechaResumido;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setSaldoCorteFechaResumido(BigDecimal saldoCorteFechaResumido)
/* 304:    */   {
/* 305:482 */     this.saldoCorteFechaResumido = saldoCorteFechaResumido;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public BigDecimal getSaldo()
/* 309:    */   {
/* 310:491 */     return this.saldo;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setSaldo(BigDecimal saldo)
/* 314:    */   {
/* 315:501 */     this.saldo = saldo;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public String getNumeroFactura()
/* 319:    */   {
/* 320:510 */     return this.numeroFactura;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public Date getFechaFactura()
/* 324:    */   {
/* 325:519 */     return this.fechaFactura;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public String getTipoOperacion()
/* 329:    */   {
/* 330:528 */     return this.tipoOperacion;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public String getCodigoDocumento()
/* 334:    */   {
/* 335:537 */     return this.codigoDocumento;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public String toString()
/* 339:    */   {
/* 340:547 */     return "VEstadoCuenta [idFacturaCliente=" + this.idFacturaProveedor + ", idEmpresa=" + this.idEmpresa + ", nombreFiscal=" + this.nombreFiscal + ", codigo=" + this.codigo + ", identificacion=" + this.identificacion + ", descripcionDocumento=" + this.descripcionDocumento + ", fechaDocumento=" + this.fechaDocumento + ", numeroDocumento=" + this.numeroDocumento + ", nombreDocumento=" + this.nombreDocumento + ", fechaVencimiento=" + this.fechaVencimiento + ", debito=" + this.debito + ", credito=" + this.credito + ", saldoCorteFechaResumido=" + this.saldoCorteFechaResumido + "]";
/* 341:    */   }
/* 342:    */   
/* 343:    */   public BigDecimal getTraVencido150()
/* 344:    */   {
/* 345:554 */     if (this.traVencido150 == null) {
/* 346:555 */       this.traVencido150 = BigDecimal.ZERO;
/* 347:    */     }
/* 348:557 */     return this.traVencido150;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public BigDecimal getTraVencido120()
/* 352:    */   {
/* 353:561 */     if (this.traVencido120 == null) {
/* 354:562 */       this.traVencido120 = BigDecimal.ZERO;
/* 355:    */     }
/* 356:564 */     return this.traVencido120;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public BigDecimal getTraVencido90()
/* 360:    */   {
/* 361:568 */     if (this.traVencido90 == null) {
/* 362:569 */       this.traVencido90 = BigDecimal.ZERO;
/* 363:    */     }
/* 364:571 */     return this.traVencido90;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public BigDecimal getTraVencido60()
/* 368:    */   {
/* 369:575 */     if (this.traVencido60 == null) {
/* 370:576 */       this.traVencido60 = BigDecimal.ZERO;
/* 371:    */     }
/* 372:578 */     return this.traVencido60;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public BigDecimal getTraVencido45()
/* 376:    */   {
/* 377:582 */     if (this.traVencido45 == null) {
/* 378:583 */       this.traVencido45 = BigDecimal.ZERO;
/* 379:    */     }
/* 380:585 */     return this.traVencido45;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public BigDecimal getTraVencido30()
/* 384:    */   {
/* 385:589 */     if (this.traVencido30 == null) {
/* 386:590 */       this.traVencido30 = BigDecimal.ZERO;
/* 387:    */     }
/* 388:592 */     return this.traVencido30;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public BigDecimal getTraVencido15()
/* 392:    */   {
/* 393:596 */     if (this.traVencido15 == null) {
/* 394:597 */       this.traVencido15 = BigDecimal.ZERO;
/* 395:    */     }
/* 396:599 */     return this.traVencido15;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public BigDecimal getTraPorVencer30()
/* 400:    */   {
/* 401:603 */     if (this.traPorVencer30 == null) {
/* 402:604 */       this.traPorVencer30 = BigDecimal.ZERO;
/* 403:    */     }
/* 404:606 */     return this.traPorVencer30;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public BigDecimal getTraPorVencer60()
/* 408:    */   {
/* 409:610 */     if (this.traPorVencer60 == null) {
/* 410:611 */       this.traPorVencer60 = BigDecimal.ZERO;
/* 411:    */     }
/* 412:613 */     return this.traPorVencer60;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public BigDecimal getTraPorVencer90()
/* 416:    */   {
/* 417:617 */     if (this.traPorVencer90 == null) {
/* 418:618 */       this.traPorVencer90 = BigDecimal.ZERO;
/* 419:    */     }
/* 420:620 */     return this.traPorVencer90;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public BigDecimal getTotalFactura()
/* 424:    */   {
/* 425:624 */     if (this.totalFactura == null) {
/* 426:625 */       this.totalFactura = BigDecimal.ZERO;
/* 427:    */     }
/* 428:627 */     return this.totalFactura;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public String getCodigoDocumentoProceso()
/* 432:    */   {
/* 433:631 */     return this.codigoDocumentoProceso;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public String getAsientoCompra()
/* 437:    */   {
/* 438:635 */     return this.asientoCompra;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void setAsientoCompra(String asientoCompra)
/* 442:    */   {
/* 443:639 */     this.asientoCompra = asientoCompra;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public String getAsientoDocumento()
/* 447:    */   {
/* 448:643 */     return this.asientoDocumento;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public void setAsientoDocumento(String asientoDocumento)
/* 452:    */   {
/* 453:647 */     this.asientoDocumento = asientoDocumento;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public static long getSerialversionuid()
/* 457:    */   {
/* 458:651 */     return 7326578415870430809L;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public int getIdOrganizacion()
/* 462:    */   {
/* 463:655 */     return this.idOrganizacion;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public Date getFechaRecepcionFactura()
/* 467:    */   {
/* 468:659 */     return this.fechaRecepcionFactura;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public String getDescripcionFactura()
/* 472:    */   {
/* 473:663 */     return this.descripcionFactura;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public BigDecimal getValorRetenido()
/* 477:    */   {
/* 478:667 */     return this.valorRetenido;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public String getReferencia1()
/* 482:    */   {
/* 483:671 */     return this.referencia1;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public String getReferencia2()
/* 487:    */   {
/* 488:675 */     return this.referencia2;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public BigDecimal getValorReferencia1()
/* 492:    */   {
/* 493:679 */     return this.valorReferencia1;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public BigDecimal getValorReferencia2()
/* 497:    */   {
/* 498:683 */     return this.valorReferencia2;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public BigDecimal getValorReferencia3()
/* 502:    */   {
/* 503:687 */     return this.valorReferencia3;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public int getIdCategoriaEmpresa()
/* 507:    */   {
/* 508:691 */     return this.idCategoriaEmpresa;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void setIdCategoriaEmpresa(int idCategoriaEmpresa)
/* 512:    */   {
/* 513:695 */     this.idCategoriaEmpresa = idCategoriaEmpresa;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public String getNombreCategoriaEmpresa()
/* 517:    */   {
/* 518:702 */     return this.nombreCategoriaEmpresa;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public void setNombreCategoriaEmpresa(String nombreCategoriaEmpresa)
/* 522:    */   {
/* 523:710 */     this.nombreCategoriaEmpresa = nombreCategoriaEmpresa;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public int getIdPago()
/* 527:    */   {
/* 528:714 */     return this.idPago;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public void setIdPago(int idPago)
/* 532:    */   {
/* 533:718 */     this.idPago = idPago;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public DocumentoBase getDocumentoBase()
/* 537:    */   {
/* 538:722 */     return this.documentoBase;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 542:    */   {
/* 543:726 */     this.documentoBase = documentoBase;
/* 544:    */   }
/* 545:    */   
/* 546:    */   public Date getFechaEmision()
/* 547:    */   {
/* 548:730 */     return this.fechaEmision;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public void setFechaEmision(Date fechaEmision)
/* 552:    */   {
/* 553:734 */     this.fechaEmision = fechaEmision;
/* 554:    */   }
/* 555:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.vista.VEstadoCuentaProveedor
 * JD-Core Version:    0.7.0.1
 */