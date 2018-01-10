/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import com.asinfo.as2.utils.validacion.email.Emails;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.EnumType;
/*  13:    */ import javax.persistence.Enumerated;
/*  14:    */ import javax.persistence.FetchType;
/*  15:    */ import javax.persistence.GeneratedValue;
/*  16:    */ import javax.persistence.GenerationType;
/*  17:    */ import javax.persistence.Id;
/*  18:    */ import javax.persistence.JoinColumn;
/*  19:    */ import javax.persistence.ManyToOne;
/*  20:    */ import javax.persistence.OneToMany;
/*  21:    */ import javax.persistence.Table;
/*  22:    */ import javax.persistence.TableGenerator;
/*  23:    */ import javax.persistence.Temporal;
/*  24:    */ import javax.persistence.TemporalType;
/*  25:    */ import javax.persistence.Transient;
/*  26:    */ import javax.validation.constraints.Digits;
/*  27:    */ import javax.validation.constraints.Min;
/*  28:    */ import javax.validation.constraints.NotNull;
/*  29:    */ import javax.validation.constraints.Size;
/*  30:    */ 
/*  31:    */ @Entity
/*  32:    */ @Table(name="pedido_proveedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})}, indexes={@javax.persistence.Index(columnList="id_organizacion, estado, fecha")})
/*  33:    */ public class PedidoProveedor
/*  34:    */   extends EntidadBase
/*  35:    */   implements Serializable
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -1L;
/*  38:    */   @Id
/*  39:    */   @TableGenerator(name="pedido_proveedor", initialValue=0, allocationSize=50)
/*  40:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="pedido_proveedor")
/*  41:    */   @Column(name="id_pedido_proveedor")
/*  42:    */   private int idPedidoProveedor;
/*  43:    */   @Column(name="id_organizacion", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private int idOrganizacion;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  48:    */   private Sucursal sucursal;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Empresa empresa;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_documento", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private Documento documento;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_direccion_empresa", nullable=false)
/*  59:    */   @NotNull
/*  60:    */   private DireccionEmpresa direccionEmpresa;
/*  61:    */   @Temporal(TemporalType.DATE)
/*  62:    */   @Column(name="fecha", nullable=false)
/*  63:    */   @NotNull
/*  64:    */   private Date fecha;
/*  65:    */   @Temporal(TemporalType.DATE)
/*  66:    */   @Column(name="fecha_entrega", nullable=false)
/*  67:    */   @NotNull
/*  68:    */   private Date fechaEntrega;
/*  69:    */   @Column(name="numero", nullable=false, length=20)
/*  70:    */   @NotNull
/*  71:    */   @Size(max=20)
/*  72:    */   private String numero;
/*  73:    */   @Column(name="total", nullable=false, precision=12, scale=2)
/*  74:    */   @NotNull
/*  75:    */   @Digits(integer=12, fraction=2)
/*  76:    */   @Min(0L)
/*  77: 95 */   private BigDecimal total = BigDecimal.ZERO;
/*  78:    */   @Column(name="subtotal_anterior", nullable=true, precision=12, scale=2)
/*  79:    */   @Digits(integer=12, fraction=2)
/*  80:    */   @Min(0L)
/*  81:    */   private BigDecimal subtotalAnterior;
/*  82:    */   @Column(name="total_pedido_anterior", nullable=true, precision=12, scale=2)
/*  83:    */   @Digits(integer=12, fraction=2)
/*  84:    */   @Min(0L)
/*  85:    */   private BigDecimal totalPedidoAnterior;
/*  86:    */   @Transient
/*  87:    */   @Min(0L)
/*  88:111 */   private BigDecimal totalPedido = BigDecimal.ZERO;
/*  89:    */   @Transient
/*  90:    */   @Min(0L)
/*  91:115 */   private BigDecimal impuestoTotal = BigDecimal.ZERO;
/*  92:    */   @Transient
/*  93:    */   @Min(0L)
/*  94:119 */   private BigDecimal baseImponibleTotal = BigDecimal.ZERO;
/*  95:    */   @Column(name="descuento", nullable=false, precision=12, scale=2)
/*  96:    */   @NotNull
/*  97:    */   @Digits(integer=12, fraction=2)
/*  98:    */   @Min(0L)
/*  99:123 */   private BigDecimal descuento = BigDecimal.ZERO;
/* 100:    */   @Column(name="impuesto", nullable=false, precision=12, scale=2)
/* 101:    */   @NotNull
/* 102:    */   @Digits(integer=12, fraction=2)
/* 103:    */   @Min(0L)
/* 104:129 */   private BigDecimal impuesto = BigDecimal.ZERO;
/* 105:    */   @Column(name="descripcion", nullable=true, length=500)
/* 106:    */   @Size(max=500)
/* 107:    */   private String descripcion;
/* 108:    */   @Column(name="usuario_cambio_estado", nullable=true, length=50)
/* 109:    */   @Size(max=50)
/* 110:139 */   private String usuarioCambioEstado = "";
/* 111:    */   @Column(name="usuario_aprobacion_cambio_precio", nullable=true, length=50)
/* 112:    */   @Size(max=50)
/* 113:143 */   private String usuarioAprobacionCambioPrecio = "";
/* 114:    */   @Column(name="usuarios_autorizacion", nullable=true, length=500)
/* 115:147 */   private String usuariosAutorizacion = "";
/* 116:    */   @Column(name="descripcion_cambio_estado", nullable=true, length=200)
/* 117:    */   @Size(max=200)
/* 118:150 */   private String descripcionCambioEstado = "";
/* 119:    */   @Temporal(TemporalType.DATE)
/* 120:    */   @Column(name="fecha_cambio_estado", nullable=true)
/* 121:    */   private Date fechaCambioEstado;
/* 122:    */   @Enumerated(EnumType.ORDINAL)
/* 123:    */   @Column(name="estado", nullable=false, length=20)
/* 124:    */   private Estado estado;
/* 125:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="pedidoProveedor")
/* 126:162 */   private List<DetallePedidoProveedor> listaDetallePedidoProveedor = new ArrayList();
/* 127:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 128:    */   @JoinColumn(name="id_condicion_pago", nullable=true)
/* 129:    */   private CondicionPago condicionPago;
/* 130:    */   @Column(name="numero_cuotas", nullable=true, precision=12, scale=2)
/* 131:    */   @NotNull
/* 132:    */   @Digits(integer=12, fraction=2)
/* 133:    */   @Min(1L)
/* 134:    */   private int numeroCuotas;
/* 135:    */   @NotNull
/* 136:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 137:    */   @JoinColumn(name="id_bodega", nullable=false)
/* 138:    */   private Bodega bodega;
/* 139:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 140:    */   @JoinColumn(name="id_proyecto", nullable=true)
/* 141:    */   private DimensionContable proyecto;
/* 142:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 143:    */   @JoinColumn(name="id_solicitud_compra", nullable=true)
/* 144:    */   private SolicitudCompra solicitudCompra;
/* 145:    */   @Column(name="indicador_solicitud_cambio_precio", nullable=true)
/* 146:189 */   private Boolean indicadorSolicitudCambioPrecio = Boolean.valueOf(false);
/* 147:    */   @Transient
/* 148:    */   private String pedidoToString;
/* 149:    */   @Transient
/* 150:    */   private boolean traSeleccionado;
/* 151:    */   @Column(name="numero_pedido_proveedor", nullable=true, length=50)
/* 152:    */   @Size(max=50)
/* 153:198 */   private String numeroPedidoProveedor = "";
/* 154:    */   @Column(name="referencia1", nullable=true, length=50)
/* 155:    */   @Size(max=50)
/* 156:202 */   private String referencia1 = "";
/* 157:    */   @Column(name="referencia2", nullable=true, length=50)
/* 158:    */   @Size(max=50)
/* 159:206 */   private String referencia2 = "";
/* 160:    */   @Column(name="referencia3", nullable=true, length=50)
/* 161:    */   @Size(max=50)
/* 162:210 */   private String referencia3 = "";
/* 163:    */   @Column(name="referencia4", nullable=true, length=50)
/* 164:    */   @Size(max=50)
/* 165:214 */   private String referencia4 = "";
/* 166:    */   @Column(name="direccion_entrega", nullable=true, length=200)
/* 167:    */   @Size(max=200)
/* 168:218 */   private String direccionEntrega = "";
/* 169:    */   @Column(name="referencia", nullable=true, length=50)
/* 170:    */   @Size(max=50)
/* 171:222 */   private String referencia = "";
/* 172:    */   @Emails
/* 173:    */   @Size(max=500)
/* 174:    */   @Column(name="email", nullable=true, length=500)
/* 175:    */   private String email;
/* 176:    */   @Column(name="telefono_respuesta", length=13, nullable=true)
/* 177:    */   @Size(max=13)
/* 178:    */   private String telefonoRespuesta;
/* 179:    */   @Emails
/* 180:    */   @Column(name="email_respuesta", nullable=true, length=100)
/* 181:    */   @Size(max=100)
/* 182:    */   private String emailRespuesta;
/* 183:    */   @Column(name="pdf", nullable=true)
/* 184:    */   @Size(max=50)
/* 185:    */   private String pdf;
/* 186:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 187:    */   @JoinColumn(name="id_persona_responsable", nullable=true)
/* 188:    */   private PersonaResponsable personaResponsable;
/* 189:    */   
/* 190:    */   public PedidoProveedor() {}
/* 191:    */   
/* 192:    */   public boolean isAuditable()
/* 193:    */   {
/* 194:261 */     return false;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public List<String> getCamposAuditables()
/* 198:    */   {
/* 199:271 */     List<String> lista = new ArrayList();
/* 200:272 */     lista.add("empresa");
/* 201:273 */     lista.add("documento");
/* 202:274 */     lista.add("direccionEmpresa");
/* 203:275 */     lista.add("fecha");
/* 204:276 */     lista.add("total");
/* 205:277 */     lista.add("subtotal");
/* 206:278 */     lista.add("impuestoTotal");
/* 207:279 */     lista.add("descuento");
/* 208:280 */     lista.add("descripcion");
/* 209:281 */     lista.add("estado");
/* 210:282 */     lista.add("condicionPago");
/* 211:283 */     lista.add("numeroCuotas");
/* 212:    */     
/* 213:285 */     return lista;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public PedidoProveedor(int idPedidoProveedor, String numero, Date fecha)
/* 217:    */   {
/* 218:290 */     this.idPedidoProveedor = idPedidoProveedor;
/* 219:291 */     this.numero = numero;
/* 220:292 */     this.fecha = fecha;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public BigDecimal getTotalPedido()
/* 224:    */   {
/* 225:296 */     this.totalPedido = getTotal().subtract(getDescuento()).add(getImpuesto());
/* 226:297 */     return this.totalPedido;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public int getId()
/* 230:    */   {
/* 231:307 */     return this.idPedidoProveedor;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public int getIdPedidoProveedor()
/* 235:    */   {
/* 236:312 */     return this.idPedidoProveedor;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setIdPedidoProveedor(int idPedidoProveedor)
/* 240:    */   {
/* 241:316 */     this.idPedidoProveedor = idPedidoProveedor;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public int getIdOrganizacion()
/* 245:    */   {
/* 246:320 */     return this.idOrganizacion;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setIdOrganizacion(int idOrganizacion)
/* 250:    */   {
/* 251:324 */     this.idOrganizacion = idOrganizacion;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public Sucursal getSucursal()
/* 255:    */   {
/* 256:328 */     return this.sucursal;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setSucursal(Sucursal sucursal)
/* 260:    */   {
/* 261:332 */     this.sucursal = sucursal;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public Empresa getEmpresa()
/* 265:    */   {
/* 266:336 */     return this.empresa;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setEmpresa(Empresa empresa)
/* 270:    */   {
/* 271:340 */     this.empresa = empresa;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public Documento getDocumento()
/* 275:    */   {
/* 276:344 */     return this.documento;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setDocumento(Documento documento)
/* 280:    */   {
/* 281:348 */     this.documento = documento;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public Date getFecha()
/* 285:    */   {
/* 286:352 */     return this.fecha;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setFecha(Date fecha)
/* 290:    */   {
/* 291:356 */     this.fecha = fecha;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public String getNumero()
/* 295:    */   {
/* 296:360 */     return this.numero;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setNumero(String numero)
/* 300:    */   {
/* 301:364 */     this.numero = numero;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public BigDecimal getTotal()
/* 305:    */   {
/* 306:368 */     return this.total;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setTotal(BigDecimal total)
/* 310:    */   {
/* 311:372 */     this.total = total;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public BigDecimal getImpuesto()
/* 315:    */   {
/* 316:376 */     return this.impuesto;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setImpuesto(BigDecimal impuesto)
/* 320:    */   {
/* 321:380 */     this.impuesto = impuesto;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public String getDescripcion()
/* 325:    */   {
/* 326:384 */     return this.descripcion;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setDescripcion(String descripcion)
/* 330:    */   {
/* 331:388 */     this.descripcion = descripcion;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public String getUsuarioCreacion()
/* 335:    */   {
/* 336:392 */     return this.usuarioCreacion;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setUsuarioCreacion(String usuarioCreacion)
/* 340:    */   {
/* 341:396 */     this.usuarioCreacion = usuarioCreacion;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public Date getFechaCreacion()
/* 345:    */   {
/* 346:400 */     return this.fechaCreacion;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void setFechaCreacion(Date fechaCreacion)
/* 350:    */   {
/* 351:404 */     this.fechaCreacion = fechaCreacion;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public Date getFechaModificacion()
/* 355:    */   {
/* 356:408 */     return this.fechaModificacion;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void setFechaModificacion(Date fechaModificacion)
/* 360:    */   {
/* 361:412 */     this.fechaModificacion = fechaModificacion;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public String getUsuarioModificacion()
/* 365:    */   {
/* 366:416 */     return this.usuarioModificacion;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setUsuarioModificacion(String usuarioModificacion)
/* 370:    */   {
/* 371:420 */     this.usuarioModificacion = usuarioModificacion;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public List<DetallePedidoProveedor> getListaDetallePedidoProveedor()
/* 375:    */   {
/* 376:424 */     return this.listaDetallePedidoProveedor;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setListaDetallePedidoProveedor(List<DetallePedidoProveedor> listaDetallePedidoProveedor)
/* 380:    */   {
/* 381:428 */     this.listaDetallePedidoProveedor = listaDetallePedidoProveedor;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public BigDecimal getDescuento()
/* 385:    */   {
/* 386:437 */     return this.descuento;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void setDescuento(BigDecimal descuento)
/* 390:    */   {
/* 391:447 */     this.descuento = descuento;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public DireccionEmpresa getDireccionEmpresa()
/* 395:    */   {
/* 396:456 */     return this.direccionEmpresa;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/* 400:    */   {
/* 401:466 */     this.direccionEmpresa = direccionEmpresa;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public BigDecimal getImpuestoTotal()
/* 405:    */   {
/* 406:475 */     return this.impuestoTotal;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public void setImpuestoTotal(BigDecimal impuestoTotal)
/* 410:    */   {
/* 411:485 */     this.impuestoTotal = impuestoTotal;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public BigDecimal getBaseImponibleTotal()
/* 415:    */   {
/* 416:494 */     return this.baseImponibleTotal;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void setBaseImponibleTotal(BigDecimal baseImponibleTotal)
/* 420:    */   {
/* 421:504 */     this.baseImponibleTotal = baseImponibleTotal;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public Estado getEstado()
/* 425:    */   {
/* 426:513 */     return this.estado;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public void setEstado(Estado estado)
/* 430:    */   {
/* 431:523 */     this.estado = estado;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public String getPedidoToString()
/* 435:    */   {
/* 436:532 */     this.pedidoToString = (getNumero() + " | " + getDescripcion());
/* 437:533 */     return this.pedidoToString;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public CondicionPago getCondicionPago()
/* 441:    */   {
/* 442:542 */     return this.condicionPago;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void setCondicionPago(CondicionPago condicionPago)
/* 446:    */   {
/* 447:552 */     this.condicionPago = condicionPago;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public int getNumeroCuotas()
/* 451:    */   {
/* 452:561 */     return this.numeroCuotas;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void setNumeroCuotas(int numeroCuotas)
/* 456:    */   {
/* 457:571 */     this.numeroCuotas = numeroCuotas;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public String toString()
/* 461:    */   {
/* 462:576 */     return this.numero;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public Date getFechaEntrega()
/* 466:    */   {
/* 467:583 */     return this.fechaEntrega;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void setFechaEntrega(Date fechaEntrega)
/* 471:    */   {
/* 472:591 */     this.fechaEntrega = fechaEntrega;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public Bodega getBodega()
/* 476:    */   {
/* 477:598 */     return this.bodega;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void setBodega(Bodega bodega)
/* 481:    */   {
/* 482:606 */     this.bodega = bodega;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public String getDescripcionCambioEstado()
/* 486:    */   {
/* 487:610 */     return this.descripcionCambioEstado;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public void setDescripcionCambioEstado(String descripcionCambioEstado)
/* 491:    */   {
/* 492:614 */     this.descripcionCambioEstado = descripcionCambioEstado;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public boolean isTraSeleccionado()
/* 496:    */   {
/* 497:618 */     return this.traSeleccionado;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public void setTraSeleccionado(boolean traSeleccionado)
/* 501:    */   {
/* 502:622 */     this.traSeleccionado = traSeleccionado;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public String getUsuarioCambioEstado()
/* 506:    */   {
/* 507:626 */     return this.usuarioCambioEstado;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public void setUsuarioCambioEstado(String usuarioCambioEstado)
/* 511:    */   {
/* 512:630 */     this.usuarioCambioEstado = usuarioCambioEstado;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public Date getFechaCambioEstado()
/* 516:    */   {
/* 517:634 */     return this.fechaCambioEstado;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public void setFechaCambioEstado(Date fechaCambioEstado)
/* 521:    */   {
/* 522:638 */     this.fechaCambioEstado = fechaCambioEstado;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public String getNumeroPedidoProveedor()
/* 526:    */   {
/* 527:642 */     return this.numeroPedidoProveedor;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void setNumeroPedidoProveedor(String numeroPedidoProveedor)
/* 531:    */   {
/* 532:646 */     this.numeroPedidoProveedor = numeroPedidoProveedor;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public String getReferencia1()
/* 536:    */   {
/* 537:650 */     return this.referencia1;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void setReferencia1(String referencia1)
/* 541:    */   {
/* 542:654 */     this.referencia1 = referencia1;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public String getReferencia2()
/* 546:    */   {
/* 547:658 */     return this.referencia2;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void setReferencia2(String referencia2)
/* 551:    */   {
/* 552:662 */     this.referencia2 = referencia2;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public String getReferencia3()
/* 556:    */   {
/* 557:666 */     return this.referencia3;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public void setReferencia3(String referencia3)
/* 561:    */   {
/* 562:670 */     this.referencia3 = referencia3;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public String getReferencia4()
/* 566:    */   {
/* 567:674 */     return this.referencia4;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public void setReferencia4(String referencia4)
/* 571:    */   {
/* 572:678 */     this.referencia4 = referencia4;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public String getDireccionEntrega()
/* 576:    */   {
/* 577:682 */     return this.direccionEntrega;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public void setDireccionEntrega(String direccionEntrega)
/* 581:    */   {
/* 582:686 */     this.direccionEntrega = direccionEntrega;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public DimensionContable getProyecto()
/* 586:    */   {
/* 587:693 */     return this.proyecto;
/* 588:    */   }
/* 589:    */   
/* 590:    */   public void setProyecto(DimensionContable proyecto)
/* 591:    */   {
/* 592:701 */     this.proyecto = proyecto;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public String getReferencia()
/* 596:    */   {
/* 597:708 */     return this.referencia;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public void setReferencia(String referencia)
/* 601:    */   {
/* 602:716 */     this.referencia = referencia;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public String getUsuariosAutorizacion()
/* 606:    */   {
/* 607:720 */     return this.usuariosAutorizacion;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public void setUsuariosAutorizacion(String usuariosAutorizacion)
/* 611:    */   {
/* 612:724 */     this.usuariosAutorizacion = usuariosAutorizacion;
/* 613:    */   }
/* 614:    */   
/* 615:    */   public BigDecimal getSubtotalAnterior()
/* 616:    */   {
/* 617:728 */     return this.subtotalAnterior;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public void setSubtotalAnterior(BigDecimal subtotalAnterior)
/* 621:    */   {
/* 622:732 */     this.subtotalAnterior = subtotalAnterior;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public BigDecimal getTotalPedidoAnterior()
/* 626:    */   {
/* 627:736 */     return this.totalPedidoAnterior;
/* 628:    */   }
/* 629:    */   
/* 630:    */   public void setTotalPedidoAnterior(BigDecimal totalPedidoAnterior)
/* 631:    */   {
/* 632:740 */     this.totalPedidoAnterior = totalPedidoAnterior;
/* 633:    */   }
/* 634:    */   
/* 635:    */   public Boolean getIndicadorSolicitudCambioPrecio()
/* 636:    */   {
/* 637:744 */     if (this.indicadorSolicitudCambioPrecio == null) {
/* 638:745 */       this.indicadorSolicitudCambioPrecio = Boolean.valueOf(false);
/* 639:    */     }
/* 640:747 */     return this.indicadorSolicitudCambioPrecio;
/* 641:    */   }
/* 642:    */   
/* 643:    */   public void setIndicadorSolicitudCambioPrecio(Boolean indicadorSolicitudCambioPrecio)
/* 644:    */   {
/* 645:751 */     this.indicadorSolicitudCambioPrecio = indicadorSolicitudCambioPrecio;
/* 646:    */   }
/* 647:    */   
/* 648:    */   public String getUsuarioAprobacionCambioPrecio()
/* 649:    */   {
/* 650:755 */     return this.usuarioAprobacionCambioPrecio;
/* 651:    */   }
/* 652:    */   
/* 653:    */   public void setUsuarioAprobacionCambioPrecio(String usuarioAprobacionCambioPrecio)
/* 654:    */   {
/* 655:759 */     this.usuarioAprobacionCambioPrecio = usuarioAprobacionCambioPrecio;
/* 656:    */   }
/* 657:    */   
/* 658:    */   public SolicitudCompra getSolicitudCompra()
/* 659:    */   {
/* 660:763 */     return this.solicitudCompra;
/* 661:    */   }
/* 662:    */   
/* 663:    */   public void setSolicitudCompra(SolicitudCompra solicitudCompra)
/* 664:    */   {
/* 665:767 */     this.solicitudCompra = solicitudCompra;
/* 666:    */   }
/* 667:    */   
/* 668:    */   public String getEmail()
/* 669:    */   {
/* 670:771 */     return this.email;
/* 671:    */   }
/* 672:    */   
/* 673:    */   public void setEmail(String email)
/* 674:    */   {
/* 675:775 */     this.email = email;
/* 676:    */   }
/* 677:    */   
/* 678:    */   public String getTelefonoRespuesta()
/* 679:    */   {
/* 680:779 */     return this.telefonoRespuesta;
/* 681:    */   }
/* 682:    */   
/* 683:    */   public void setTelefonoRespuesta(String telefonoRespuesta)
/* 684:    */   {
/* 685:783 */     this.telefonoRespuesta = telefonoRespuesta;
/* 686:    */   }
/* 687:    */   
/* 688:    */   public String getEmailRespuesta()
/* 689:    */   {
/* 690:787 */     return this.emailRespuesta;
/* 691:    */   }
/* 692:    */   
/* 693:    */   public void setEmailRespuesta(String emailRespuesta)
/* 694:    */   {
/* 695:791 */     this.emailRespuesta = emailRespuesta;
/* 696:    */   }
/* 697:    */   
/* 698:    */   public String getPdf()
/* 699:    */   {
/* 700:795 */     return this.pdf;
/* 701:    */   }
/* 702:    */   
/* 703:    */   public void setPdf(String pdf)
/* 704:    */   {
/* 705:799 */     this.pdf = pdf;
/* 706:    */   }
/* 707:    */   
/* 708:    */   public PersonaResponsable getPersonaResponsable()
/* 709:    */   {
/* 710:803 */     return this.personaResponsable;
/* 711:    */   }
/* 712:    */   
/* 713:    */   public void setPersonaResponsable(PersonaResponsable personaResponsable)
/* 714:    */   {
/* 715:807 */     this.personaResponsable = personaResponsable;
/* 716:    */   }
/* 717:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PedidoProveedor
 * JD-Core Version:    0.7.0.1
 */