/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.OneToMany;
/*  17:    */ import javax.persistence.OneToOne;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Temporal;
/*  21:    */ import javax.persistence.TemporalType;
/*  22:    */ import javax.persistence.Transient;
/*  23:    */ import javax.validation.constraints.DecimalMin;
/*  24:    */ import javax.validation.constraints.Digits;
/*  25:    */ import javax.validation.constraints.Min;
/*  26:    */ import javax.validation.constraints.NotNull;
/*  27:    */ import javax.validation.constraints.Size;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="detalle_forma_cobro", indexes={@javax.persistence.Index(columnList="lote"), @javax.persistence.Index(columnList="id_cobro_tarjeta")})
/*  31:    */ public class DetalleFormaCobro
/*  32:    */   extends EntidadBase
/*  33:    */   implements Serializable
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @Id
/*  37:    */   @TableGenerator(name="detalle_forma_cobro", initialValue=0, allocationSize=50)
/*  38:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_forma_cobro")
/*  39:    */   @Column(name="id_detalle_forma_cobro", unique=true, nullable=false)
/*  40:    */   private int idDetalleFormaCobro;
/*  41:    */   @Column(name="id_organizacion", nullable=false)
/*  42:    */   private int idOrganizacion;
/*  43:    */   @Column(name="id_sucursal", nullable=false)
/*  44:    */   private int idSucursal;
/*  45:    */   @Column(name="descripcion", length=200, nullable=false)
/*  46:    */   @Size(max=200)
/*  47: 65 */   private String descripcion = "";
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_cobro", nullable=true)
/*  50:    */   private Cobro cobro;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_forma_pago", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   private FormaPago formaPago;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=true)
/*  57:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion_protesto", nullable=true)
/*  60:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacionProtesto;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_forma_pago_protesto", nullable=true)
/*  63:    */   private FormaPago formaPagoProtesto;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_caja", nullable=true)
/*  66:    */   private Caja caja;
/*  67:    */   @OneToOne(mappedBy="detalleFormaCobro", fetch=FetchType.LAZY)
/*  68:    */   private GarantiaCliente garantiaCliente;
/*  69:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  70:    */   @Digits(integer=12, fraction=2)
/*  71: 97 */   private BigDecimal valor = BigDecimal.ZERO;
/*  72:    */   @Column(name="documento_referencia", nullable=true, length=20)
/*  73:    */   @Size(max=20)
/*  74:    */   private String documentoReferencia;
/*  75:    */   @Column(name="autorizacion", nullable=true, length=50)
/*  76:    */   private String autorizacion;
/*  77:    */   @Column(name="documento_referencia_protesto", nullable=false, length=20)
/*  78:    */   @Size(max=20)
/*  79:109 */   private String documentoReferenciaProtesto = "";
/*  80:    */   @Column(name="indicador_cheque_posfechado", nullable=false)
/*  81:    */   private boolean indicadorChequePosfechado;
/*  82:    */   @Column(name="valor_protestado", precision=12, scale=2, nullable=true)
/*  83:    */   @Digits(integer=12, fraction=2)
/*  84:    */   @Min(0L)
/*  85:    */   private BigDecimal valorProtestado;
/*  86:    */   @Column(name="indicador_cheque_protestado", nullable=false)
/*  87:    */   private boolean indicadorChequeProtestado;
/*  88:    */   @Column(name="indicador_cargar_cliente_protesto", nullable=false)
/*  89:    */   private boolean indicadorCargarClienteProtesto;
/*  90:    */   @Column(name="id_dispositivo_sincronizacion", nullable=true)
/*  91:    */   private Integer idDispositivoSincronizacion;
/*  92:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  93:    */   @JoinColumn(name="id_cuenta_contable_forma_cobro", nullable=true)
/*  94:    */   private CuentaContable cuentaContableFormaCobro;
/*  95:    */   @OneToOne(fetch=FetchType.LAZY, mappedBy="detalleFormaCobro")
/*  96:    */   private DetalleCierreCaja detalleCierreCaja;
/*  97:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  98:    */   @JoinColumn(name="id_banco", nullable=true)
/*  99:    */   private Banco banco;
/* 100:    */   @Temporal(TemporalType.DATE)
/* 101:    */   @Column(name="fecha_protesto", nullable=true)
/* 102:    */   private Date fechaProtesto;
/* 103:    */   @OneToMany(mappedBy="detalleFormaCobroProtesto", fetch=FetchType.LAZY)
/* 104:145 */   private List<CuentaPorCobrar> listaCuentaPorCobrar = new ArrayList();
/* 105:    */   @OneToMany(mappedBy="detalleFormaCobro", fetch=FetchType.LAZY)
/* 106:148 */   private List<DetalleCobroFormaCobro> listaDetalleCobroFormaCobro = new ArrayList();
/* 107:    */   @Transient
/* 108:    */   private FacturaCliente facturaCliente;
/* 109:    */   @Transient
/* 110:154 */   private BigDecimal valorFuente = BigDecimal.ZERO;
/* 111:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 112:    */   @JoinColumn(name="id_cobro_tarjeta", nullable=true)
/* 113:    */   private Cobro cobroTarjeta;
/* 114:    */   @Column(name="secuencial", nullable=true)
/* 115:    */   private int secuencial;
/* 116:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 117:    */   @JoinColumn(name="id_tarjeta_credito", nullable=true)
/* 118:    */   private TarjetaCredito tarjetaCredito;
/* 119:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 120:    */   @JoinColumn(name="id_punto_venta", nullable=true)
/* 121:    */   private PuntoDeVenta puntoVenta;
/* 122:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 123:    */   @JoinColumn(name="id_plan_tarjeta_credito", nullable=true)
/* 124:    */   private PlanTarjetaCredito planTarjetaCredito;
/* 125:    */   @Column(name="numero_tarjeta", nullable=true, length=50)
/* 126:    */   private String numeroTarjeta;
/* 127:    */   @NotNull
/* 128:    */   @Column(name="base_imponible_diferente_cero", precision=12, scale=2)
/* 129:    */   @Digits(integer=12, fraction=2)
/* 130:    */   @Min(0L)
/* 131:180 */   private BigDecimal baseImponibleDiferenteCero = BigDecimal.ZERO;
/* 132:    */   @NotNull
/* 133:    */   @Column(name="base_imponible_tarifa_cero", precision=12, scale=2)
/* 134:    */   @Digits(integer=12, fraction=2)
/* 135:    */   @Min(0L)
/* 136:186 */   private BigDecimal baseImponibleTarifaCero = BigDecimal.ZERO;
/* 137:    */   @Column(name="monto_iva", precision=12, scale=2)
/* 138:    */   @Digits(integer=12, fraction=2)
/* 139:    */   @DecimalMin("0.00")
/* 140:192 */   private BigDecimal montoIva = BigDecimal.ZERO;
/* 141:    */   @Column(name="interes", precision=12, scale=2)
/* 142:    */   @Digits(integer=12, fraction=2)
/* 143:197 */   private BigDecimal interes = BigDecimal.ZERO;
/* 144:    */   @Column(name="lote", nullable=true, length=20)
/* 145:    */   private String lote;
/* 146:    */   @Temporal(TemporalType.DATE)
/* 147:    */   @Column(name="fecha_voucher", nullable=true)
/* 148:    */   private Date fechaVoucher;
/* 149:    */   @DecimalMin("0.00")
/* 150:    */   @Column(name="valor_pagado_calculado", precision=12, scale=2)
/* 151:    */   @Digits(integer=12, fraction=2)
/* 152:209 */   private BigDecimal valorPagadoCalculado = BigDecimal.ZERO;
/* 153:    */   @DecimalMin("0.00")
/* 154:    */   @Column(name="valor_pagado", precision=12, scale=2)
/* 155:    */   @Digits(integer=12, fraction=2)
/* 156:214 */   private BigDecimal valorPagado = BigDecimal.ZERO;
/* 157:    */   @Column(name="porcentaje_comision", precision=12, scale=2)
/* 158:    */   @Digits(integer=12, fraction=2)
/* 159:219 */   private BigDecimal porcentajeComision = BigDecimal.ZERO;
/* 160:    */   @Column(name="valor_comision", precision=12, scale=2)
/* 161:    */   @Digits(integer=12, fraction=2)
/* 162:223 */   private BigDecimal valorComision = BigDecimal.ZERO;
/* 163:    */   @Column(name="impuesto_comision", precision=12, scale=2)
/* 164:    */   @Digits(integer=12, fraction=2)
/* 165:228 */   private BigDecimal impuestoComision = BigDecimal.ZERO;
/* 166:    */   @Column(name="indicador_cobrado", nullable=false)
/* 167:    */   private boolean indicadorCobrado;
/* 168:    */   @Column(name="retencion_iva", precision=12, scale=2)
/* 169:    */   @Digits(integer=12, fraction=2)
/* 170:    */   @Min(0L)
/* 171:235 */   private BigDecimal retencionIva = BigDecimal.ZERO;
/* 172:    */   @Column(name="retencion_fuente", precision=12, scale=2)
/* 173:    */   @Digits(integer=12, fraction=2)
/* 174:    */   @Min(0L)
/* 175:240 */   private BigDecimal retencionFuente = BigDecimal.ZERO;
/* 176:    */   @OneToOne(fetch=FetchType.LAZY)
/* 177:    */   @JoinColumn(name="id_asiento", nullable=true)
/* 178:    */   private Asiento asiento;
/* 179:    */   
/* 180:    */   public int getId()
/* 181:    */   {
/* 182:258 */     return this.idDetalleFormaCobro;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public int getIdDetalleFormaCobro()
/* 186:    */   {
/* 187:273 */     return this.idDetalleFormaCobro;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setIdDetalleFormaCobro(int idDetalleFormaCobro)
/* 191:    */   {
/* 192:283 */     this.idDetalleFormaCobro = idDetalleFormaCobro;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public int getIdOrganizacion()
/* 196:    */   {
/* 197:292 */     return this.idOrganizacion;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setIdOrganizacion(int idOrganizacion)
/* 201:    */   {
/* 202:302 */     this.idOrganizacion = idOrganizacion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public int getIdSucursal()
/* 206:    */   {
/* 207:311 */     return this.idSucursal;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setIdSucursal(int idSucursal)
/* 211:    */   {
/* 212:321 */     this.idSucursal = idSucursal;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public FormaPago getFormaPago()
/* 216:    */   {
/* 217:330 */     return this.formaPago;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setFormaPago(FormaPago formaPago)
/* 221:    */   {
/* 222:340 */     this.formaPago = formaPago;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 226:    */   {
/* 227:349 */     return this.cuentaBancariaOrganizacion;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 231:    */   {
/* 232:359 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public BigDecimal getValor()
/* 236:    */   {
/* 237:368 */     return this.valor;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setValor(BigDecimal valor)
/* 241:    */   {
/* 242:378 */     this.valor = valor;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public Cobro getCobro()
/* 246:    */   {
/* 247:387 */     return this.cobro;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setCobro(Cobro cobro)
/* 251:    */   {
/* 252:397 */     this.cobro = cobro;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public String getDocumentoReferencia()
/* 256:    */   {
/* 257:406 */     return this.documentoReferencia;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 261:    */   {
/* 262:416 */     this.documentoReferencia = documentoReferencia;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public GarantiaCliente getGarantiaCliente()
/* 266:    */   {
/* 267:420 */     return this.garantiaCliente;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setGarantiaCliente(GarantiaCliente garantiaCliente)
/* 271:    */   {
/* 272:424 */     this.garantiaCliente = garantiaCliente;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public Caja getCaja()
/* 276:    */   {
/* 277:433 */     return this.caja;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setCaja(Caja caja)
/* 281:    */   {
/* 282:443 */     this.caja = caja;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getDescripcion()
/* 286:    */   {
/* 287:452 */     return this.descripcion;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setDescripcion(String descripcion)
/* 291:    */   {
/* 292:462 */     this.descripcion = descripcion;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public boolean isIndicadorChequePosfechado()
/* 296:    */   {
/* 297:469 */     return this.indicadorChequePosfechado;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setIndicadorChequePosfechado(boolean indicadorChequePosfechado)
/* 301:    */   {
/* 302:477 */     this.indicadorChequePosfechado = indicadorChequePosfechado;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public BigDecimal getValorProtestado()
/* 306:    */   {
/* 307:486 */     return this.valorProtestado;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setValorProtestado(BigDecimal valorProtestado)
/* 311:    */   {
/* 312:496 */     this.valorProtestado = valorProtestado;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public FacturaCliente getFacturaCliente()
/* 316:    */   {
/* 317:505 */     return this.facturaCliente;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 321:    */   {
/* 322:515 */     this.facturaCliente = facturaCliente;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public BigDecimal getValorFuente()
/* 326:    */   {
/* 327:524 */     return this.valorFuente;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setValorFuente(BigDecimal valorFuente)
/* 331:    */   {
/* 332:534 */     this.valorFuente = valorFuente;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public boolean isIndicadorChequeProtestado()
/* 336:    */   {
/* 337:538 */     return this.indicadorChequeProtestado;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setIndicadorChequeProtestado(boolean indicadorChequeProtestado)
/* 341:    */   {
/* 342:542 */     this.indicadorChequeProtestado = indicadorChequeProtestado;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacionProtesto()
/* 346:    */   {
/* 347:551 */     return this.cuentaBancariaOrganizacionProtesto;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setCuentaBancariaOrganizacionProtesto(CuentaBancariaOrganizacion cuentaBancariaOrganizacionProtesto)
/* 351:    */   {
/* 352:561 */     this.cuentaBancariaOrganizacionProtesto = cuentaBancariaOrganizacionProtesto;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public FormaPago getFormaPagoProtesto()
/* 356:    */   {
/* 357:570 */     return this.formaPagoProtesto;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setFormaPagoProtesto(FormaPago formaPagoProtesto)
/* 361:    */   {
/* 362:580 */     this.formaPagoProtesto = formaPagoProtesto;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public String getDocumentoReferenciaProtesto()
/* 366:    */   {
/* 367:589 */     return this.documentoReferenciaProtesto;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setDocumentoReferenciaProtesto(String documentoReferenciaProtesto)
/* 371:    */   {
/* 372:599 */     this.documentoReferenciaProtesto = documentoReferenciaProtesto;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public boolean isIndicadorCargarClienteProtesto()
/* 376:    */   {
/* 377:608 */     return this.indicadorCargarClienteProtesto;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setIndicadorCargarClienteProtesto(boolean indicadorCargarClienteProtesto)
/* 381:    */   {
/* 382:618 */     this.indicadorCargarClienteProtesto = indicadorCargarClienteProtesto;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public CuentaContable getCuentaContableFormaCobro()
/* 386:    */   {
/* 387:622 */     return this.cuentaContableFormaCobro;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setCuentaContableFormaCobro(CuentaContable cuentaContableFormaCobro)
/* 391:    */   {
/* 392:626 */     this.cuentaContableFormaCobro = cuentaContableFormaCobro;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public DetalleCierreCaja getDetalleCierreCaja()
/* 396:    */   {
/* 397:635 */     return this.detalleCierreCaja;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setDetalleCierreCaja(DetalleCierreCaja detalleCierreCaja)
/* 401:    */   {
/* 402:645 */     this.detalleCierreCaja = detalleCierreCaja;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public Banco getBanco()
/* 406:    */   {
/* 407:652 */     return this.banco;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setBanco(Banco banco)
/* 411:    */   {
/* 412:660 */     this.banco = banco;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public Date getFechaProtesto()
/* 416:    */   {
/* 417:664 */     return this.fechaProtesto;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void setFechaProtesto(Date fechaProtesto)
/* 421:    */   {
/* 422:668 */     this.fechaProtesto = fechaProtesto;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public List<CuentaPorCobrar> getListaCuentaPorCobrar()
/* 426:    */   {
/* 427:672 */     return this.listaCuentaPorCobrar;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setListaCuentaPorCobrar(List<CuentaPorCobrar> listaCuentaPorCobrar)
/* 431:    */   {
/* 432:676 */     this.listaCuentaPorCobrar = listaCuentaPorCobrar;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public List<DetalleCobroFormaCobro> getListaDetalleCobroFormaCobro()
/* 436:    */   {
/* 437:680 */     return this.listaDetalleCobroFormaCobro;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setListaDetalleCobroFormaCobro(List<DetalleCobroFormaCobro> listaDetalleCobroFormaCobro)
/* 441:    */   {
/* 442:684 */     this.listaDetalleCobroFormaCobro = listaDetalleCobroFormaCobro;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public TarjetaCredito getTarjetaCredito()
/* 446:    */   {
/* 447:688 */     return this.tarjetaCredito;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setTarjetaCredito(TarjetaCredito tarjetaCredito)
/* 451:    */   {
/* 452:692 */     this.tarjetaCredito = tarjetaCredito;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public int getSecuencial()
/* 456:    */   {
/* 457:696 */     return this.secuencial;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public void setSecuencial(int secuencial)
/* 461:    */   {
/* 462:700 */     this.secuencial = secuencial;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public PuntoDeVenta getPuntoVenta()
/* 466:    */   {
/* 467:704 */     return this.puntoVenta;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void setPuntoVenta(PuntoDeVenta puntoVenta)
/* 471:    */   {
/* 472:708 */     this.puntoVenta = puntoVenta;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public PlanTarjetaCredito getPlanTarjetaCredito()
/* 476:    */   {
/* 477:712 */     return this.planTarjetaCredito;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void setPlanTarjetaCredito(PlanTarjetaCredito planTarjetaCredito)
/* 481:    */   {
/* 482:716 */     this.planTarjetaCredito = planTarjetaCredito;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public String getNumeroTarjeta()
/* 486:    */   {
/* 487:720 */     return this.numeroTarjeta;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public void setNumeroTarjeta(String numeroTarjeta)
/* 491:    */   {
/* 492:724 */     this.numeroTarjeta = numeroTarjeta;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public BigDecimal getBaseImponibleDiferenteCero()
/* 496:    */   {
/* 497:728 */     return this.baseImponibleDiferenteCero;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public void setBaseImponibleDiferenteCero(BigDecimal baseImponibleDiferenteCero)
/* 501:    */   {
/* 502:732 */     this.baseImponibleDiferenteCero = baseImponibleDiferenteCero;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public BigDecimal getBaseImponibleTarifaCero()
/* 506:    */   {
/* 507:736 */     return this.baseImponibleTarifaCero;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public void setBaseImponibleTarifaCero(BigDecimal baseImponibleTarifaCero)
/* 511:    */   {
/* 512:740 */     this.baseImponibleTarifaCero = baseImponibleTarifaCero;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public BigDecimal getInteres()
/* 516:    */   {
/* 517:744 */     return this.interes;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public void setInteres(BigDecimal interes)
/* 521:    */   {
/* 522:748 */     this.interes = interes;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public String getLote()
/* 526:    */   {
/* 527:752 */     return this.lote;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void setLote(String lote)
/* 531:    */   {
/* 532:756 */     this.lote = lote;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public Date getFechaVoucher()
/* 536:    */   {
/* 537:760 */     return this.fechaVoucher;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void setFechaVoucher(Date fechaVoucher)
/* 541:    */   {
/* 542:764 */     this.fechaVoucher = fechaVoucher;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public BigDecimal getValorPagado()
/* 546:    */   {
/* 547:768 */     return this.valorPagado;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void setValorPagado(BigDecimal valorPagado)
/* 551:    */   {
/* 552:772 */     this.valorPagado = valorPagado;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public BigDecimal getValorPagadoCalculado()
/* 556:    */   {
/* 557:776 */     return this.valorPagadoCalculado;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public void setValorPagadoCalculado(BigDecimal valorPagadoCalculado)
/* 561:    */   {
/* 562:780 */     this.valorPagadoCalculado = valorPagadoCalculado;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public BigDecimal getPorcentajeComision()
/* 566:    */   {
/* 567:784 */     return this.porcentajeComision;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public void setPorcentajeComision(BigDecimal porcentajeComision)
/* 571:    */   {
/* 572:788 */     this.porcentajeComision = porcentajeComision;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public BigDecimal getValorComision()
/* 576:    */   {
/* 577:792 */     return this.valorComision;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public void setValorComision(BigDecimal valorComision)
/* 581:    */   {
/* 582:796 */     this.valorComision = valorComision;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public BigDecimal getImpuestoComision()
/* 586:    */   {
/* 587:800 */     return this.impuestoComision;
/* 588:    */   }
/* 589:    */   
/* 590:    */   public void setImpuestoComision(BigDecimal impuestoComision)
/* 591:    */   {
/* 592:804 */     this.impuestoComision = impuestoComision;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public Cobro getCobroTarjeta()
/* 596:    */   {
/* 597:808 */     return this.cobroTarjeta;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public void setCobroTarjeta(Cobro cobroTarjeta)
/* 601:    */   {
/* 602:812 */     this.cobroTarjeta = cobroTarjeta;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public BigDecimal getMontoIva()
/* 606:    */   {
/* 607:816 */     return this.montoIva;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public void setMontoIva(BigDecimal montoIva)
/* 611:    */   {
/* 612:820 */     this.montoIva = montoIva;
/* 613:    */   }
/* 614:    */   
/* 615:    */   public Asiento getAsiento()
/* 616:    */   {
/* 617:824 */     return this.asiento;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public void setAsiento(Asiento asiento)
/* 621:    */   {
/* 622:828 */     this.asiento = asiento;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public boolean isIndicadorCobrado()
/* 626:    */   {
/* 627:832 */     return this.indicadorCobrado;
/* 628:    */   }
/* 629:    */   
/* 630:    */   public void setIndicadorCobrado(boolean indicadorCobrado)
/* 631:    */   {
/* 632:836 */     this.indicadorCobrado = indicadorCobrado;
/* 633:    */   }
/* 634:    */   
/* 635:    */   public Integer getIdDispositivoSincronizacion()
/* 636:    */   {
/* 637:840 */     return this.idDispositivoSincronizacion;
/* 638:    */   }
/* 639:    */   
/* 640:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 641:    */   {
/* 642:844 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 643:    */   }
/* 644:    */   
/* 645:    */   public BigDecimal getRetencionIva()
/* 646:    */   {
/* 647:848 */     return this.retencionIva;
/* 648:    */   }
/* 649:    */   
/* 650:    */   public void setRetencionIva(BigDecimal retencionIva)
/* 651:    */   {
/* 652:852 */     this.retencionIva = retencionIva;
/* 653:    */   }
/* 654:    */   
/* 655:    */   public BigDecimal getRetencionFuente()
/* 656:    */   {
/* 657:856 */     return this.retencionFuente;
/* 658:    */   }
/* 659:    */   
/* 660:    */   public void setRetencionFuente(BigDecimal retencionFuente)
/* 661:    */   {
/* 662:860 */     this.retencionFuente = retencionFuente;
/* 663:    */   }
/* 664:    */   
/* 665:    */   public String getAutorizacion()
/* 666:    */   {
/* 667:864 */     return this.autorizacion;
/* 668:    */   }
/* 669:    */   
/* 670:    */   public void setAutorizacion(String autorizacion)
/* 671:    */   {
/* 672:868 */     this.autorizacion = autorizacion;
/* 673:    */   }
/* 674:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleFormaCobro
 * JD-Core Version:    0.7.0.1
 */