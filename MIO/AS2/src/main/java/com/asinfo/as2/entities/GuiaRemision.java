/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
/*   4:    */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*   5:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   6:    */ import com.asinfo.as2.utils.validacion.email.Emails;
/*   7:    */ import java.math.BigDecimal;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.persistence.Column;
/*  12:    */ import javax.persistence.Entity;
/*  13:    */ import javax.persistence.EnumType;
/*  14:    */ import javax.persistence.Enumerated;
/*  15:    */ import javax.persistence.FetchType;
/*  16:    */ import javax.persistence.GeneratedValue;
/*  17:    */ import javax.persistence.GenerationType;
/*  18:    */ import javax.persistence.Id;
/*  19:    */ import javax.persistence.JoinColumn;
/*  20:    */ import javax.persistence.ManyToOne;
/*  21:    */ import javax.persistence.OneToMany;
/*  22:    */ import javax.persistence.OneToOne;
/*  23:    */ import javax.persistence.Table;
/*  24:    */ import javax.persistence.TableGenerator;
/*  25:    */ import javax.persistence.Temporal;
/*  26:    */ import javax.persistence.TemporalType;
/*  27:    */ import javax.persistence.Transient;
/*  28:    */ import javax.validation.constraints.Digits;
/*  29:    */ import javax.validation.constraints.Min;
/*  30:    */ import javax.validation.constraints.NotNull;
/*  31:    */ import javax.validation.constraints.Size;
/*  32:    */ 
/*  33:    */ @Entity
/*  34:    */ @Table(name="guia_remision", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero"})})
/*  35:    */ public class GuiaRemision
/*  36:    */   extends EntidadBase
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   @Id
/*  40:    */   @TableGenerator(name="guia_remision", initialValue=0, allocationSize=50)
/*  41:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="guia_remision")
/*  42:    */   @Column(name="id_guia_remision")
/*  43:    */   private int idGuiaRemision;
/*  44:    */   @Column(name="id_sucursal", nullable=false)
/*  45:    */   private int idSucursal;
/*  46:    */   @Column(name="id_organizacion", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private int idOrganizacion;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_documento", nullable=true)
/*  51:    */   private Documento documento;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_transportista", nullable=true)
/*  54:    */   private Transportista transportista;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_vehiculo", nullable=true)
/*  57:    */   private Vehiculo vehiculo;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_ciudad_origen", nullable=false)
/*  60:    */   @NotNull
/*  61:    */   private Ciudad ciudadOrigen;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_ciudad_destino", nullable=false)
/*  64:    */   @NotNull
/*  65:    */   private Ciudad ciudadDestino;
/*  66:    */   @Column(name="numero", nullable=false, length=20)
/*  67:    */   @NotNull
/*  68:    */   @Size(max=20)
/*  69:    */   private String numero;
/*  70:    */   @Temporal(TemporalType.DATE)
/*  71:    */   @Column(name="fecha", nullable=false)
/*  72:    */   @NotNull
/*  73:    */   private Date fecha;
/*  74:    */   @Temporal(TemporalType.DATE)
/*  75:    */   @Column(name="fecha_vigencia", nullable=true)
/*  76:    */   private Date fechaVigencia;
/*  77:    */   @Column(name="conductor", nullable=false)
/*  78:    */   @NotNull
/*  79:    */   @Size(min=2, max=50)
/*  80:    */   private String conductor;
/*  81:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="guiaRemision")
/*  82: 96 */   private List<DetalleGuiaRemision> listaDetalleGuiaRemision = new ArrayList();
/*  83:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  84:    */   @JoinColumn(name="id_tipo_identificacion_transportista", nullable=true)
/*  85:    */   private TipoIdentificacion tipoIdentificacionTransportista;
/*  86:    */   @Column(name="licencia", nullable=false, length=20)
/*  87:    */   @NotNull
/*  88:    */   @Size(max=20)
/*  89:    */   private String licencia;
/*  90:    */   @Column(name="tarifa", nullable=false)
/*  91:    */   @Digits(integer=12, fraction=2)
/*  92:    */   @Min(0L)
/*  93:108 */   private BigDecimal tarifa = BigDecimal.ZERO;
/*  94:    */   @Column(name="descripcion", nullable=false)
/*  95:    */   @Size(max=5000)
/*  96:    */   private String descripcion;
/*  97:    */   @OneToOne(fetch=FetchType.LAZY)
/*  98:    */   @JoinColumn(name="id_despacho_cliente", nullable=true)
/*  99:    */   private DespachoCliente despachoCliente;
/* 100:    */   @OneToOne(fetch=FetchType.LAZY)
/* 101:    */   @JoinColumn(name="id_transferencia_bodega", nullable=true)
/* 102:    */   private MovimientoInventario transferenciaBodega;
/* 103:    */   @OneToOne(fetch=FetchType.LAZY)
/* 104:    */   @JoinColumn(name="id_hoja_ruta_transportista", nullable=true)
/* 105:    */   private HojaRuta hojaRutaTransportista;
/* 106:    */   @Emails
/* 107:    */   @Size(max=1000)
/* 108:    */   @Column(name="email", nullable=true, length=1000)
/* 109:    */   private String email;
/* 110:    */   @Emails
/* 111:    */   @Size(max=5000)
/* 112:    */   @Column(name="email_transportista", nullable=true, length=5000)
/* 113:    */   private String emailTransportista;
/* 114:    */   @Emails
/* 115:    */   @Size(max=500)
/* 116:    */   @Column(name="email_cliente", nullable=true, length=500)
/* 117:    */   private String emailCliente;
/* 118:    */   @Column(name="placa", nullable=false, length=10)
/* 119:    */   @NotNull
/* 120:    */   @Size(min=7, max=10)
/* 121:    */   private String placa;
/* 122:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 123:    */   @JoinColumn(name="id_empresa", nullable=true)
/* 124:    */   private Empresa empresa;
/* 125:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 126:    */   @JoinColumn(name="id_direccion_empresa", nullable=true)
/* 127:    */   private DireccionEmpresa direccionEmpresa;
/* 128:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 129:    */   @JoinColumn(name="id_factura_cliente", nullable=true)
/* 130:    */   private FacturaCliente facturaCliente;
/* 131:    */   @Column(name="hora_salida", nullable=true)
/* 132:    */   private Date horaSalida;
/* 133:    */   @Column(name="hora_llegada", nullable=true)
/* 134:    */   private Date horaLlegada;
/* 135:    */   @Column(name="identificacion_transportista", nullable=true, length=20)
/* 136:    */   @Size(max=20)
/* 137:    */   private String identificacionTransportista;
/* 138:    */   @Transient
/* 139:    */   private DocumentoElectronico documentoElectronico;
/* 140:    */   @Column(name="ambiente", nullable=true)
/* 141:    */   private int ambiente;
/* 142:    */   @Column(name="tipo_emision", nullable=true)
/* 143:    */   private int tipoEmision;
/* 144:    */   @Column(name="indicador_documento_electronico", nullable=true)
/* 145:    */   private boolean indicadorDocumentoElectronico;
/* 146:    */   @Column(name="clave_acceso", length=50)
/* 147:    */   private String claveAcceso;
/* 148:    */   @Column(name="codigo_unico", length=50, nullable=true)
/* 149:    */   private String codigoUnico;
/* 150:    */   @Column(name="direccion_matriz", length=200)
/* 151:    */   private String direccionMatriz;
/* 152:    */   @Column(name="direccion_sucursal", length=200)
/* 153:    */   private String direccionSucursal;
/* 154:    */   @Column(name="autorizacion", length=49, nullable=true)
/* 155:    */   @Size(min=10, max=49)
/* 156:    */   private String autorizacion;
/* 157:    */   @Temporal(TemporalType.TIMESTAMP)
/* 158:    */   @Column(name="fecha_autorizacion", nullable=true)
/* 159:    */   private Date fechaAutorizacion;
/* 160:    */   @Column(name="estado", nullable=true)
/* 161:    */   @Enumerated(EnumType.ORDINAL)
/* 162:    */   private Estado estado;
/* 163:    */   @Column(name="mensaje_sri", length=5000, nullable=true)
/* 164:    */   @Size(max=5000)
/* 165:    */   private String mensajeSRI;
/* 166:    */   @Column(name="estado_documento_electronico", nullable=true)
/* 167:    */   @Enumerated(EnumType.ORDINAL)
/* 168:    */   private EstadoDocumentoElectronico estadoDocumentoElectronico;
/* 169:    */   @Column(name="id_hoja_ruta", nullable=true)
/* 170:    */   private int idHojaRuta;
/* 171:    */   @Column(name="ruta", length=300, nullable=true)
/* 172:    */   @Size(max=300)
/* 173:    */   private String ruta;
/* 174:    */   @Column(name="indicador_automatico", nullable=true)
/* 175:    */   private boolean indicadorAutomatico;
/* 176:    */   
/* 177:    */   public DocumentoElectronico getDocumentoElectronico()
/* 178:    */   {
/* 179:175 */     return this.documentoElectronico;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setDocumentoElectronico(DocumentoElectronico documentoElectronico)
/* 183:    */   {
/* 184:179 */     this.documentoElectronico = documentoElectronico;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Estado getEstado()
/* 188:    */   {
/* 189:238 */     return this.estado;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setEstado(Estado estado)
/* 193:    */   {
/* 194:242 */     this.estado = estado;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public TipoIdentificacion getTipoIdentificacionTransportista()
/* 198:    */   {
/* 199:246 */     return this.tipoIdentificacionTransportista;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setTipoIdentificacionTransportista(TipoIdentificacion tipoIdentificacionTransportista)
/* 203:    */   {
/* 204:250 */     this.tipoIdentificacionTransportista = tipoIdentificacionTransportista;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int getId()
/* 208:    */   {
/* 209:255 */     return this.idGuiaRemision;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public int getAmbiente()
/* 213:    */   {
/* 214:259 */     return this.ambiente;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setAmbiente(int ambiente)
/* 218:    */   {
/* 219:263 */     this.ambiente = ambiente;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public int getTipoEmision()
/* 223:    */   {
/* 224:267 */     return this.tipoEmision;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setTipoEmision(int tipoEmision)
/* 228:    */   {
/* 229:271 */     this.tipoEmision = tipoEmision;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean isIndicadorDocumentoElectronico()
/* 233:    */   {
/* 234:275 */     return this.indicadorDocumentoElectronico;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setIndicadorDocumentoElectronico(boolean indicadorDocumentoElectronico)
/* 238:    */   {
/* 239:279 */     this.indicadorDocumentoElectronico = indicadorDocumentoElectronico;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public String getClaveAcceso()
/* 243:    */   {
/* 244:283 */     return this.claveAcceso;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setClaveAcceso(String claveAcceso)
/* 248:    */   {
/* 249:287 */     this.claveAcceso = claveAcceso;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public String getCodigoUnico()
/* 253:    */   {
/* 254:291 */     return this.codigoUnico;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setCodigoUnico(String codigoUnico)
/* 258:    */   {
/* 259:295 */     this.codigoUnico = codigoUnico;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public String getDireccionMatriz()
/* 263:    */   {
/* 264:299 */     return this.direccionMatriz;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setDireccionMatriz(String direccionMatriz)
/* 268:    */   {
/* 269:303 */     this.direccionMatriz = direccionMatriz;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public String getDireccionSucursal()
/* 273:    */   {
/* 274:307 */     return this.direccionSucursal;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setDireccionSucursal(String direccionSucursal)
/* 278:    */   {
/* 279:311 */     this.direccionSucursal = direccionSucursal;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public int getIdGuiaRemision()
/* 283:    */   {
/* 284:315 */     return this.idGuiaRemision;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setIdGuiaRemision(int idGuiaRemision)
/* 288:    */   {
/* 289:319 */     this.idGuiaRemision = idGuiaRemision;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public int getIdSucursal()
/* 293:    */   {
/* 294:323 */     return this.idSucursal;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setIdSucursal(int idSucursal)
/* 298:    */   {
/* 299:327 */     this.idSucursal = idSucursal;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public int getIdOrganizacion()
/* 303:    */   {
/* 304:331 */     return this.idOrganizacion;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setIdOrganizacion(int idOrganizacion)
/* 308:    */   {
/* 309:335 */     this.idOrganizacion = idOrganizacion;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public Transportista getTransportista()
/* 313:    */   {
/* 314:339 */     return this.transportista;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setTransportista(Transportista transportista)
/* 318:    */   {
/* 319:343 */     this.transportista = transportista;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public Ciudad getCiudadOrigen()
/* 323:    */   {
/* 324:347 */     return this.ciudadOrigen;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setCiudadOrigen(Ciudad ciudadOrigen)
/* 328:    */   {
/* 329:351 */     this.ciudadOrigen = ciudadOrigen;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public Ciudad getCiudadDestino()
/* 333:    */   {
/* 334:355 */     return this.ciudadDestino;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setCiudadDestino(Ciudad ciudadDestino)
/* 338:    */   {
/* 339:359 */     this.ciudadDestino = ciudadDestino;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public String getNumero()
/* 343:    */   {
/* 344:363 */     return this.numero;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setNumero(String numero)
/* 348:    */   {
/* 349:367 */     this.numero = numero;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public Date getFecha()
/* 353:    */   {
/* 354:371 */     return this.fecha;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setFecha(Date fecha)
/* 358:    */   {
/* 359:375 */     this.fecha = fecha;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public Date getFechaVigencia()
/* 363:    */   {
/* 364:384 */     return this.fechaVigencia;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void setFechaVigencia(Date fechaVigencia)
/* 368:    */   {
/* 369:394 */     this.fechaVigencia = fechaVigencia;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public String getConductor()
/* 373:    */   {
/* 374:398 */     return this.conductor;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void setConductor(String conductor)
/* 378:    */   {
/* 379:402 */     this.conductor = conductor;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public String getLicencia()
/* 383:    */   {
/* 384:406 */     return this.licencia;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void setLicencia(String licencia)
/* 388:    */   {
/* 389:410 */     this.licencia = licencia;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public String getDescripcion()
/* 393:    */   {
/* 394:414 */     return this.descripcion;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void setDescripcion(String descripcion)
/* 398:    */   {
/* 399:418 */     this.descripcion = descripcion;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public BigDecimal getTarifa()
/* 403:    */   {
/* 404:422 */     return this.tarifa;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public void setTarifa(BigDecimal tarifa)
/* 408:    */   {
/* 409:426 */     this.tarifa = tarifa;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public Vehiculo getVehiculo()
/* 413:    */   {
/* 414:430 */     return this.vehiculo;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public void setVehiculo(Vehiculo vehiculo)
/* 418:    */   {
/* 419:434 */     this.vehiculo = vehiculo;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public DespachoCliente getDespachoCliente()
/* 423:    */   {
/* 424:441 */     return this.despachoCliente;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public void setDespachoCliente(DespachoCliente despachoCliente)
/* 428:    */   {
/* 429:449 */     this.despachoCliente = despachoCliente;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public List<String> getCamposAuditables()
/* 433:    */   {
/* 434:453 */     List<String> lista = new ArrayList();
/* 435:454 */     lista.add("numero");
/* 436:455 */     lista.add("fecha");
/* 437:456 */     lista.add("conductor");
/* 438:457 */     lista.add("licencia");
/* 439:458 */     lista.add("tarifa");
/* 440:459 */     lista.add("descripcion");
/* 441:460 */     return lista;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public Documento getDocumento()
/* 445:    */   {
/* 446:464 */     return this.documento;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void setDocumento(Documento documento)
/* 450:    */   {
/* 451:468 */     this.documento = documento;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public MovimientoInventario getTransferenciaBodega()
/* 455:    */   {
/* 456:472 */     return this.transferenciaBodega;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public void setTransferenciaBodega(MovimientoInventario transferenciaBodega)
/* 460:    */   {
/* 461:476 */     this.transferenciaBodega = transferenciaBodega;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public String getEmail()
/* 465:    */   {
/* 466:480 */     return this.email;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public void setEmail(String email)
/* 470:    */   {
/* 471:484 */     this.email = email;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public String getPlaca()
/* 475:    */   {
/* 476:488 */     return this.placa;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public void setPlaca(String placa)
/* 480:    */   {
/* 481:492 */     this.placa = placa;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public String getAutorizacion()
/* 485:    */   {
/* 486:496 */     return this.autorizacion;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public void setAutorizacion(String autorizacion)
/* 490:    */   {
/* 491:500 */     this.autorizacion = autorizacion;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public Date getFechaAutorizacion()
/* 495:    */   {
/* 496:504 */     return this.fechaAutorizacion;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public void setFechaAutorizacion(Date fechaAutorizacion)
/* 500:    */   {
/* 501:508 */     this.fechaAutorizacion = fechaAutorizacion;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public String getEmailTransportista()
/* 505:    */   {
/* 506:512 */     return this.emailTransportista;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public void setEmailTransportista(String emailTransportista)
/* 510:    */   {
/* 511:516 */     this.emailTransportista = emailTransportista;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public String getEmailCliente()
/* 515:    */   {
/* 516:520 */     return this.emailCliente;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public void setEmailCliente(String emailCliente)
/* 520:    */   {
/* 521:524 */     this.emailCliente = emailCliente;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public String getMensajeSRI()
/* 525:    */   {
/* 526:528 */     return this.mensajeSRI;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void setMensajeSRI(String mensajeSRI)
/* 530:    */   {
/* 531:532 */     this.mensajeSRI = mensajeSRI;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public List<DetalleGuiaRemision> getListaDetalleGuiaRemision()
/* 535:    */   {
/* 536:536 */     return this.listaDetalleGuiaRemision;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public void setListaDetalleGuiaRemision(List<DetalleGuiaRemision> listaDetalleGuiaRemision)
/* 540:    */   {
/* 541:540 */     this.listaDetalleGuiaRemision = listaDetalleGuiaRemision;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public Empresa getEmpresa()
/* 545:    */   {
/* 546:544 */     return this.empresa;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public void setEmpresa(Empresa empresa)
/* 550:    */   {
/* 551:548 */     this.empresa = empresa;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public DireccionEmpresa getDireccionEmpresa()
/* 555:    */   {
/* 556:552 */     return this.direccionEmpresa;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/* 560:    */   {
/* 561:556 */     this.direccionEmpresa = direccionEmpresa;
/* 562:    */   }
/* 563:    */   
/* 564:    */   public FacturaCliente getFacturaCliente()
/* 565:    */   {
/* 566:560 */     return this.facturaCliente;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 570:    */   {
/* 571:564 */     this.facturaCliente = facturaCliente;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public int getIdHojaRuta()
/* 575:    */   {
/* 576:571 */     return this.idHojaRuta;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public void setIdHojaRuta(int idHojaRuta)
/* 580:    */   {
/* 581:579 */     this.idHojaRuta = idHojaRuta;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public String getRuta()
/* 585:    */   {
/* 586:586 */     return this.ruta;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public void setRuta(String ruta)
/* 590:    */   {
/* 591:594 */     this.ruta = ruta;
/* 592:    */   }
/* 593:    */   
/* 594:    */   public HojaRuta getHojaRutaTransportista()
/* 595:    */   {
/* 596:598 */     return this.hojaRutaTransportista;
/* 597:    */   }
/* 598:    */   
/* 599:    */   public void setHojaRutaTransportista(HojaRuta hojaRutaTransportista)
/* 600:    */   {
/* 601:602 */     this.hojaRutaTransportista = hojaRutaTransportista;
/* 602:    */   }
/* 603:    */   
/* 604:    */   public Date getHoraSalida()
/* 605:    */   {
/* 606:606 */     return this.horaSalida;
/* 607:    */   }
/* 608:    */   
/* 609:    */   public void setHoraSalida(Date horaSalida)
/* 610:    */   {
/* 611:610 */     this.horaSalida = horaSalida;
/* 612:    */   }
/* 613:    */   
/* 614:    */   public Date getHoraLlegada()
/* 615:    */   {
/* 616:614 */     return this.horaLlegada;
/* 617:    */   }
/* 618:    */   
/* 619:    */   public void setHoraLlegada(Date horaLlegada)
/* 620:    */   {
/* 621:618 */     this.horaLlegada = horaLlegada;
/* 622:    */   }
/* 623:    */   
/* 624:    */   public boolean isIndicadorAutomatico()
/* 625:    */   {
/* 626:622 */     return this.indicadorAutomatico;
/* 627:    */   }
/* 628:    */   
/* 629:    */   public void setIndicadorAutomatico(boolean indicadorAutomatico)
/* 630:    */   {
/* 631:626 */     this.indicadorAutomatico = indicadorAutomatico;
/* 632:    */   }
/* 633:    */   
/* 634:    */   public String getIdentificacionTransportista()
/* 635:    */   {
/* 636:630 */     return this.identificacionTransportista;
/* 637:    */   }
/* 638:    */   
/* 639:    */   public void setIdentificacionTransportista(String identificacionTransportista)
/* 640:    */   {
/* 641:634 */     this.identificacionTransportista = identificacionTransportista;
/* 642:    */   }
/* 643:    */   
/* 644:    */   public EstadoDocumentoElectronico getEstadoDocumentoElectronico()
/* 645:    */   {
/* 646:638 */     return this.estadoDocumentoElectronico;
/* 647:    */   }
/* 648:    */   
/* 649:    */   public void setEstadoDocumentoElectronico(EstadoDocumentoElectronico estadoDocumentoElectronico)
/* 650:    */   {
/* 651:642 */     this.estadoDocumentoElectronico = estadoDocumentoElectronico;
/* 652:    */   }
/* 653:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.GuiaRemision
 * JD-Core Version:    0.7.0.1
 */