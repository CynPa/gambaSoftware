/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import com.asinfo.as2.enumeraciones.MetodoFacturacionEnum;
/*   5:    */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*   6:    */ import com.asinfo.as2.utils.validacion.horario.Horario;
/*   7:    */ import java.math.BigDecimal;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToOne;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Transient;
/*  22:    */ import javax.validation.constraints.Max;
/*  23:    */ import javax.validation.constraints.Min;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ import org.hibernate.annotations.ColumnDefault;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_empresa"})})
/*  30:    */ public class Cliente
/*  31:    */   extends EntidadBase
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = -4318372801168600812L;
/*  34:    */   @Id
/*  35:    */   @TableGenerator(name="cliente", initialValue=0, allocationSize=50)
/*  36:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cliente")
/*  37:    */   @Column(name="id_cliente", unique=true, nullable=false)
/*  38:    */   private int idCliente;
/*  39:    */   @Column(name="id_organizacion", nullable=false)
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="id_sucursal", nullable=false)
/*  42:    */   private int idSucursal;
/*  43:    */   @Column(name="credito_maximo", nullable=false, precision=12, scale=2)
/*  44:    */   @NotNull
/*  45: 69 */   private BigDecimal creditoMaximo = BigDecimal.ZERO;
/*  46:    */   @Column(name="credito_utilizado", nullable=false, precision=12, scale=2)
/*  47:    */   @NotNull
/*  48: 73 */   private BigDecimal creditoUtilizado = BigDecimal.ZERO;
/*  49:    */   @Column(name="cantidad_facturas_pendientes_sin_garantia", nullable=true)
/*  50: 78 */   private Integer cantidadFacturasPendientesSinGarantia = Integer.valueOf(0);
/*  51:    */   @Column(name="excento_impuestos", nullable=false)
/*  52:    */   private boolean excentoImpuestos;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_forma_pago", nullable=false)
/*  55:    */   private FormaPago formaPago;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_condicion_pago", nullable=false)
/*  58:    */   private CondicionPago condicionPago;
/*  59:    */   @OneToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  61:    */   private Empresa empresa;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_lista_precios", nullable=true)
/*  64:    */   private ListaPrecios listaPrecios;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_lista_descuentos", nullable=true)
/*  67:    */   private ListaDescuentos listaDescuentos;
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_agente_comercial", nullable=true)
/*  70:    */   private EntidadUsuario agenteComercial;
/*  71:    */   @Column(name="numero_cuotas", nullable=false)
/*  72:    */   @Min(1L)
/*  73:    */   @Max(1000L)
/*  74:    */   private int numeroCuotas;
/*  75:    */   @Enumerated(EnumType.ORDINAL)
/*  76:    */   @Column(name="metodo_facturacion", nullable=false)
/*  77:    */   private MetodoFacturacionEnum metodoFacturacion;
/*  78:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  79:    */   @JoinColumn(name="id_zona", nullable=true)
/*  80:    */   private Zona zona;
/*  81:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  82:    */   @JoinColumn(name="id_sector", nullable=true)
/*  83:    */   private Sector sector;
/*  84:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  85:    */   @JoinColumn(name="id_recaudador", nullable=true)
/*  86:    */   private Recaudador recaudador;
/*  87:    */   @Column(name="tipo_cliente", nullable=false)
/*  88:    */   @Enumerated(EnumType.ORDINAL)
/*  89:    */   private TipoEmpresaEnum tipoCliente;
/*  90:    */   @Column(name="indicador_iva_presuntivo", nullable=false)
/*  91:    */   private boolean indicadorIvaPresuntivo;
/*  92:    */   @Column(name="indicador_3x1000", nullable=false)
/*  93:    */   private boolean indicador3X1000;
/*  94:    */   @Column(name="indicador_exterior", nullable=true)
/*  95:    */   private Boolean indicadorExterior;
/*  96:    */   @Column(name="ordenante", nullable=true, length=200)
/*  97:    */   @Size(max=200)
/*  98:141 */   private String ordenante = "";
/*  99:    */   @Deprecated
/* 100:    */   @Column(name="contacto", nullable=true, length=100)
/* 101:    */   @Size(max=100)
/* 102:148 */   private String contacto = "";
/* 103:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 104:    */   @JoinColumn(name="id_transportista", nullable=true)
/* 105:    */   private Transportista transportista;
/* 106:    */   @Column(name="referencia", nullable=true, length=200)
/* 107:    */   @Size(max=200)
/* 108:157 */   private String referencia = "";
/* 109:    */   @Column(name="referencia2", nullable=true, length=200)
/* 110:    */   @Size(max=200)
/* 111:161 */   private String referencia2 = "";
/* 112:    */   @Column(name="indicador_envio", nullable=true)
/* 113:    */   private Boolean indicadorEnvio;
/* 114:    */   @ColumnDefault("'1'")
/* 115:    */   @Column(name="indicador_facturar_al_despachar", nullable=false)
/* 116:    */   @NotNull
/* 117:168 */   private boolean indicadorFacturarAlDespachar = true;
/* 118:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 119:    */   @JoinColumn(name="id_tipo_orden_despacho", nullable=true)
/* 120:    */   private TipoOrdenDespacho tipoOrdenDespacho;
/* 121:    */   @Column(name="indicador_emite_retencion", nullable=false)
/* 122:    */   @NotNull
/* 123:    */   private boolean indicadorEmiteRetencion;
/* 124:    */   @Column(name="indicador_cliente_bloqueado", nullable=false)
/* 125:    */   @NotNull
/* 126:    */   private boolean indicadorClienteBloqueado;
/* 127:    */   @Column(name="indicador_pedido_preautorizado", nullable=true)
/* 128:186 */   private Boolean indicadorPedidoPreautorizado = Boolean.valueOf(false);
/* 129:    */   @Column(name="indicador_editar_precio", nullable=true)
/* 130:189 */   private Boolean indicadorEditarPrecio = Boolean.valueOf(true);
/* 131:    */   @Column(name="indicador_cargar_pedido_sugerido", nullable=true)
/* 132:    */   private Boolean indicadorCargarPedidoSugerido;
/* 133:    */   @ColumnDefault("0")
/* 134:    */   @Column(name="porcentaje_crecimiento_ventas", nullable=false, precision=12, scale=2)
/* 135:    */   @NotNull
/* 136:194 */   private BigDecimal porcentajeCrecimientoVentas = BigDecimal.ZERO;
/* 137:    */   @Transient
/* 138:    */   private BigDecimal creditoDisponible;
/* 139:    */   @Column(name="version", nullable=true, length=20)
/* 140:    */   @Size(max=20)
/* 141:202 */   private String version = "1.1.0";
/* 142:    */   @Horario
/* 143:    */   @Size(max=500)
/* 144:    */   @Column(name="horarios_visitas", nullable=true, length=500)
/* 145:    */   private String horariosVisitas;
/* 146:    */   @Horario
/* 147:    */   @Size(max=500)
/* 148:    */   @Column(name="horarios_despachos", nullable=true, length=500)
/* 149:    */   private String horariosDespachos;
/* 150:    */   @Transient
/* 151:    */   private BigDecimal porcentajeMorosidad;
/* 152:    */   @Transient
/* 153:    */   private Boolean indicadorCumpleDocumentacion;
/* 154:    */   
/* 155:    */   public Cliente() {}
/* 156:    */   
/* 157:    */   public Cliente(Empresa empresa)
/* 158:    */   {
/* 159:228 */     this.empresa = empresa;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public int getId()
/* 163:    */   {
/* 164:238 */     return this.idCliente;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int getIdCliente()
/* 168:    */   {
/* 169:247 */     return this.idCliente;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setIdCliente(int idCliente)
/* 173:    */   {
/* 174:257 */     this.idCliente = idCliente;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int getIdOrganizacion()
/* 178:    */   {
/* 179:266 */     return this.idOrganizacion;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setIdOrganizacion(int idOrganizacion)
/* 183:    */   {
/* 184:276 */     this.idOrganizacion = idOrganizacion;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int getIdSucursal()
/* 188:    */   {
/* 189:285 */     return this.idSucursal;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setIdSucursal(int idSucursal)
/* 193:    */   {
/* 194:295 */     this.idSucursal = idSucursal;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public BigDecimal getCreditoMaximo()
/* 198:    */   {
/* 199:304 */     return this.creditoMaximo;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setCreditoMaximo(BigDecimal creditoMaximo)
/* 203:    */   {
/* 204:314 */     this.creditoMaximo = creditoMaximo;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public BigDecimal getCreditoUtilizado()
/* 208:    */   {
/* 209:323 */     return this.creditoUtilizado;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setCreditoUtilizado(BigDecimal creditoUtilizado)
/* 213:    */   {
/* 214:333 */     this.creditoUtilizado = creditoUtilizado;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public boolean isExcentoImpuestos()
/* 218:    */   {
/* 219:342 */     return this.excentoImpuestos;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setExcentoImpuestos(boolean excentoImpuestos)
/* 223:    */   {
/* 224:352 */     this.excentoImpuestos = excentoImpuestos;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public FormaPago getFormaPago()
/* 228:    */   {
/* 229:361 */     return this.formaPago;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setFormaPago(FormaPago formaPago)
/* 233:    */   {
/* 234:371 */     this.formaPago = formaPago;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Empresa getEmpresa()
/* 238:    */   {
/* 239:380 */     return this.empresa;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setEmpresa(Empresa empresa)
/* 243:    */   {
/* 244:390 */     this.empresa = empresa;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public ListaPrecios getListaPrecios()
/* 248:    */   {
/* 249:399 */     return this.listaPrecios;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setListaPrecios(ListaPrecios listaPrecios)
/* 253:    */   {
/* 254:409 */     this.listaPrecios = listaPrecios;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public CondicionPago getCondicionPago()
/* 258:    */   {
/* 259:418 */     return this.condicionPago;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setCondicionPago(CondicionPago condicionPago)
/* 263:    */   {
/* 264:428 */     this.condicionPago = condicionPago;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public EntidadUsuario getAgenteComercial()
/* 268:    */   {
/* 269:437 */     return this.agenteComercial;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 273:    */   {
/* 274:447 */     this.agenteComercial = agenteComercial;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Zona getZona()
/* 278:    */   {
/* 279:456 */     return this.zona;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setZona(Zona zona)
/* 283:    */   {
/* 284:466 */     this.zona = zona;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public Recaudador getRecaudador()
/* 288:    */   {
/* 289:475 */     return this.recaudador;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setRecaudador(Recaudador recaudador)
/* 293:    */   {
/* 294:485 */     this.recaudador = recaudador;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public int getNumeroCuotas()
/* 298:    */   {
/* 299:492 */     return this.numeroCuotas;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setNumeroCuotas(int numeroCuotas)
/* 303:    */   {
/* 304:500 */     this.numeroCuotas = numeroCuotas;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public MetodoFacturacionEnum getMetodoFacturacion()
/* 308:    */   {
/* 309:507 */     return this.metodoFacturacion;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setMetodoFacturacion(MetodoFacturacionEnum metodoFacturacion)
/* 313:    */   {
/* 314:515 */     this.metodoFacturacion = metodoFacturacion;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public TipoEmpresaEnum getTipoCliente()
/* 318:    */   {
/* 319:524 */     return this.tipoCliente;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setTipoCliente(TipoEmpresaEnum tipoCliente)
/* 323:    */   {
/* 324:534 */     this.tipoCliente = tipoCliente;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public boolean isIndicadorIvaPresuntivo()
/* 328:    */   {
/* 329:543 */     return this.indicadorIvaPresuntivo;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setIndicadorIvaPresuntivo(boolean indicadorIvaPresuntivo)
/* 333:    */   {
/* 334:553 */     this.indicadorIvaPresuntivo = indicadorIvaPresuntivo;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public boolean isIndicador3X1000()
/* 338:    */   {
/* 339:562 */     return this.indicador3X1000;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setIndicador3X1000(boolean indicador3x1000)
/* 343:    */   {
/* 344:572 */     this.indicador3X1000 = indicador3x1000;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public String getContacto()
/* 348:    */   {
/* 349:576 */     return this.contacto;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void setContacto(String contacto)
/* 353:    */   {
/* 354:580 */     this.contacto = contacto;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public Transportista getTransportista()
/* 358:    */   {
/* 359:584 */     return this.transportista;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setTransportista(Transportista transportista)
/* 363:    */   {
/* 364:588 */     this.transportista = transportista;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public ListaDescuentos getListaDescuentos()
/* 368:    */   {
/* 369:592 */     return this.listaDescuentos;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setListaDescuentos(ListaDescuentos listaDescuentos)
/* 373:    */   {
/* 374:596 */     this.listaDescuentos = listaDescuentos;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public Boolean getIndicadorExterior()
/* 378:    */   {
/* 379:603 */     return Boolean.valueOf(this.indicadorExterior != null);
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setIndicadorExterior(Boolean indicadorExterior)
/* 383:    */   {
/* 384:611 */     this.indicadorExterior = indicadorExterior;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public String getOrdenante()
/* 388:    */   {
/* 389:615 */     return this.ordenante;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setOrdenante(String ordenante)
/* 393:    */   {
/* 394:619 */     this.ordenante = ordenante;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public String getReferencia()
/* 398:    */   {
/* 399:623 */     return this.referencia;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setReferencia(String referencia)
/* 403:    */   {
/* 404:627 */     this.referencia = referencia;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public Boolean getIndicadorEnvio()
/* 408:    */   {
/* 409:631 */     return Boolean.valueOf(this.indicadorEnvio == null ? false : this.indicadorEnvio.booleanValue());
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setIndicadorEnvio(Boolean indicadorEnvio)
/* 413:    */   {
/* 414:635 */     this.indicadorEnvio = indicadorEnvio;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public boolean isIndicadorFacturarAlDespachar()
/* 418:    */   {
/* 419:639 */     return this.indicadorFacturarAlDespachar;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void setIndicadorFacturarAlDespachar(boolean indicadorFacturarAlDespachar)
/* 423:    */   {
/* 424:643 */     this.indicadorFacturarAlDespachar = indicadorFacturarAlDespachar;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public String getReferencia2()
/* 428:    */   {
/* 429:647 */     return this.referencia2;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void setReferencia2(String referencia2)
/* 433:    */   {
/* 434:651 */     this.referencia2 = referencia2;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public TipoOrdenDespacho getTipoOrdenDespacho()
/* 438:    */   {
/* 439:655 */     return this.tipoOrdenDespacho;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public void setTipoOrdenDespacho(TipoOrdenDespacho tipoOrdenDespacho)
/* 443:    */   {
/* 444:659 */     this.tipoOrdenDespacho = tipoOrdenDespacho;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public boolean isIndicadorEmiteRetencion()
/* 448:    */   {
/* 449:663 */     return this.indicadorEmiteRetencion;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void setIndicadorEmiteRetencion(boolean indicadorEmiteRetencion)
/* 453:    */   {
/* 454:667 */     this.indicadorEmiteRetencion = indicadorEmiteRetencion;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public boolean isIndicadorClienteBloqueado()
/* 458:    */   {
/* 459:671 */     return this.indicadorClienteBloqueado;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void setIndicadorClienteBloqueado(boolean indicadorClienteBloqueado)
/* 463:    */   {
/* 464:675 */     this.indicadorClienteBloqueado = indicadorClienteBloqueado;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public BigDecimal getCreditoDisponible()
/* 468:    */   {
/* 469:682 */     return getCreditoMaximo().subtract(getCreditoUtilizado());
/* 470:    */   }
/* 471:    */   
/* 472:    */   public void setCreditoDisponible(BigDecimal creditoDisponible)
/* 473:    */   {
/* 474:690 */     this.creditoDisponible = creditoDisponible;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public String getVersion()
/* 478:    */   {
/* 479:694 */     return this.version;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void setVersion(String version)
/* 483:    */   {
/* 484:698 */     this.version = version;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public Boolean getIndicadorPedidoPreautorizado()
/* 488:    */   {
/* 489:702 */     if (this.indicadorPedidoPreautorizado == null) {
/* 490:703 */       this.indicadorPedidoPreautorizado = Boolean.valueOf(false);
/* 491:    */     }
/* 492:705 */     return this.indicadorPedidoPreautorizado;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public void setIndicadorPedidoPreautorizado(Boolean indicadorPedidoPreautorizado)
/* 496:    */   {
/* 497:709 */     if (indicadorPedidoPreautorizado == null) {
/* 498:710 */       indicadorPedidoPreautorizado = Boolean.valueOf(false);
/* 499:    */     }
/* 500:712 */     this.indicadorPedidoPreautorizado = indicadorPedidoPreautorizado;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public Boolean getIndicadorEditarPrecio()
/* 504:    */   {
/* 505:716 */     if (this.indicadorEditarPrecio == null) {
/* 506:717 */       this.indicadorEditarPrecio = Boolean.valueOf(true);
/* 507:    */     }
/* 508:719 */     return this.indicadorEditarPrecio;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void setIndicadorEditarPrecio(Boolean indicadorEditarPrecio)
/* 512:    */   {
/* 513:723 */     if (indicadorEditarPrecio == null) {
/* 514:724 */       indicadorEditarPrecio = Boolean.valueOf(true);
/* 515:    */     }
/* 516:726 */     this.indicadorEditarPrecio = indicadorEditarPrecio;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public Sector getSector()
/* 520:    */   {
/* 521:730 */     return this.sector;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public void setSector(Sector sector)
/* 525:    */   {
/* 526:734 */     this.sector = sector;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public String getHorariosVisitas()
/* 530:    */   {
/* 531:738 */     return this.horariosVisitas;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public void setHorariosVisitas(String horariosVisitas)
/* 535:    */   {
/* 536:742 */     this.horariosVisitas = horariosVisitas;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public String getHorariosDespachos()
/* 540:    */   {
/* 541:746 */     return this.horariosDespachos;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void setHorariosDespachos(String horariosDespachos)
/* 545:    */   {
/* 546:750 */     this.horariosDespachos = horariosDespachos;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public Integer getCantidadFacturasPendientesSinGarantia()
/* 550:    */   {
/* 551:754 */     if (this.cantidadFacturasPendientesSinGarantia == null) {
/* 552:755 */       this.cantidadFacturasPendientesSinGarantia = Integer.valueOf(0);
/* 553:    */     }
/* 554:757 */     return this.cantidadFacturasPendientesSinGarantia;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public void setCantidadFacturasPendientesSinGarantia(Integer cantidadFacturasPendientesSinGarantia)
/* 558:    */   {
/* 559:761 */     this.cantidadFacturasPendientesSinGarantia = cantidadFacturasPendientesSinGarantia;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public BigDecimal getPorcentajeMorosidad()
/* 563:    */   {
/* 564:765 */     return this.porcentajeMorosidad;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void setPorcentajeMorosidad(BigDecimal porcentajeMorosidad)
/* 568:    */   {
/* 569:769 */     this.porcentajeMorosidad = porcentajeMorosidad;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public Boolean getIndicadorCumpleDocumentacion()
/* 573:    */   {
/* 574:773 */     return this.indicadorCumpleDocumentacion;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void setIndicadorCumpleDocumentacion(Boolean indicadorCumpleDocumentacion)
/* 578:    */   {
/* 579:777 */     this.indicadorCumpleDocumentacion = indicadorCumpleDocumentacion;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public Boolean getIndicadorCargarPedidoSugerido()
/* 583:    */   {
/* 584:781 */     return this.indicadorCargarPedidoSugerido;
/* 585:    */   }
/* 586:    */   
/* 587:    */   public void setIndicadorCargarPedidoSugerido(Boolean indicadorCargarPedidoSugerido)
/* 588:    */   {
/* 589:785 */     this.indicadorCargarPedidoSugerido = indicadorCargarPedidoSugerido;
/* 590:    */   }
/* 591:    */   
/* 592:    */   public BigDecimal getPorcentajeCrecimientoVentas()
/* 593:    */   {
/* 594:789 */     return this.porcentajeCrecimientoVentas;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public void setPorcentajeCrecimientoVentas(BigDecimal porcentajeCrecimientoVentas)
/* 598:    */   {
/* 599:793 */     this.porcentajeCrecimientoVentas = porcentajeCrecimientoVentas;
/* 600:    */   }
/* 601:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Cliente
 * JD-Core Version:    0.7.0.1
 */