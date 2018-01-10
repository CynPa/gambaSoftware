/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.EnumType;
/*  12:    */ import javax.persistence.Enumerated;
/*  13:    */ import javax.persistence.FetchType;
/*  14:    */ import javax.persistence.GeneratedValue;
/*  15:    */ import javax.persistence.GenerationType;
/*  16:    */ import javax.persistence.Id;
/*  17:    */ import javax.persistence.JoinColumn;
/*  18:    */ import javax.persistence.ManyToOne;
/*  19:    */ import javax.persistence.OneToMany;
/*  20:    */ import javax.persistence.OneToOne;
/*  21:    */ import javax.persistence.Table;
/*  22:    */ import javax.persistence.TableGenerator;
/*  23:    */ import javax.persistence.Temporal;
/*  24:    */ import javax.persistence.TemporalType;
/*  25:    */ import javax.persistence.Transient;
/*  26:    */ import javax.validation.constraints.Min;
/*  27:    */ import javax.validation.constraints.NotNull;
/*  28:    */ import javax.validation.constraints.Size;
/*  29:    */ import org.hibernate.annotations.Fetch;
/*  30:    */ import org.hibernate.annotations.FetchMode;
/*  31:    */ 
/*  32:    */ @Entity
/*  33:    */ @Table(name="despacho_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})}, indexes={@javax.persistence.Index(columnList="fecha"), @javax.persistence.Index(columnList="id_empresa"), @javax.persistence.Index(columnList="id_organizacion, fecha")})
/*  34:    */ public class DespachoCliente
/*  35:    */   extends EntidadBase
/*  36:    */   implements Serializable
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   @Id
/*  40:    */   @TableGenerator(name="despacho_cliente", initialValue=0, allocationSize=50)
/*  41:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="despacho_cliente")
/*  42:    */   @Column(name="id_despacho_cliente", unique=true, nullable=false)
/*  43:    */   private int idDespachoCliente;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_documento", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private Documento documento;
/*  48:    */   @Column(name="id_organizacion", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private int idOrganizacion;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   private Sucursal sucursal;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  57:    */   @NotNull
/*  58:    */   private Empresa empresa;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_subempresa", nullable=true)
/*  61:    */   private Subempresa subempresa;
/*  62:    */   @Temporal(TemporalType.DATE)
/*  63:    */   @Column(name="fecha", nullable=false)
/*  64:    */   @NotNull
/*  65:    */   private Date fecha;
/*  66:    */   @Column(name="numero", nullable=false, length=20)
/*  67:    */   @NotNull
/*  68:    */   @Size(max=20)
/*  69:    */   private String numero;
/*  70:    */   @Column(name="descripcion", length=500, nullable=true)
/*  71:    */   @Size(max=500)
/*  72:    */   private String descripcion;
/*  73:    */   @Column(name="estado", nullable=false)
/*  74:    */   @Enumerated(EnumType.ORDINAL)
/*  75:    */   private Estado estado;
/*  76:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="despachoCliente")
/*  77:111 */   private List<DetalleDespachoCliente> listaDetalleDespachoCliente = new ArrayList();
/*  78:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  79:    */   @JoinColumn(name="id_direccion_empresa", nullable=false)
/*  80:    */   @NotNull
/*  81:    */   private DireccionEmpresa direccionEmpresa;
/*  82:    */   @Temporal(TemporalType.DATE)
/*  83:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  84:    */   private Date fechaContabilizacion;
/*  85:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  86:    */   @JoinColumn(name="id_pedido_cliente", nullable=true)
/*  87:    */   private PedidoCliente pedidoCliente;
/*  88:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  89:    */   @JoinColumn(name="id_prefactura_cliente", nullable=true)
/*  90:    */   private PrefacturaCliente prefacturaCliente;
/*  91:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  92:    */   @JoinColumn(name="id_responsable_salida_mercaderia", nullable=true)
/*  93:    */   private PersonaResponsable responsableSalidaMercaderia;
/*  94:    */   @OneToOne(fetch=FetchType.LAZY, mappedBy="despachoCliente")
/*  95:    */   @Fetch(FetchMode.JOIN)
/*  96:    */   private GuiaRemision guiaRemision;
/*  97:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  98:    */   @JoinColumn(name="id_interfaz_contable_proceso", nullable=true)
/*  99:    */   private InterfazContableProceso interfazContableProceso;
/* 100:    */   @Temporal(TemporalType.DATE)
/* 101:    */   @Column(name="fecha_anulacion", nullable=true)
/* 102:    */   private Date fechaAnulacion;
/* 103:    */   @Column(name="indicador_generado_prefactura", nullable=true)
/* 104:    */   private boolean indicadorGeneradoPrefactura;
/* 105:    */   @Column(name="indicador_generado_factura", nullable=true)
/* 106:    */   private boolean indicadorGeneradoFactura;
/* 107:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 108:    */   @JoinColumn(name="id_proyecto", nullable=true)
/* 109:    */   private DimensionContable proyecto;
/* 110:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 111:    */   @JoinColumn(name="id_orden_despacho_cliente", nullable=true)
/* 112:    */   private OrdenDespachoCliente ordenDespachoCliente;
/* 113:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 114:    */   @JoinColumn(name="id_tipo_orden_despacho", nullable=true)
/* 115:    */   private TipoOrdenDespacho tipoOrdenDespacho;
/* 116:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 117:    */   @JoinColumn(name="id_transportista", nullable=true)
/* 118:    */   private Transportista transportista;
/* 119:    */   @OneToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/* 120:    */   @JoinColumn(name="id_asiento", nullable=true)
/* 121:    */   private Asiento asiento;
/* 122:    */   @Transient
/* 123:    */   @Min(0L)
/* 124:    */   private BigDecimal total;
/* 125:    */   @Transient
/* 126:    */   private String nombreProducto;
/* 127:    */   @Transient
/* 128:    */   private BigDecimal pesoProducto;
/* 129:    */   @Transient
/* 130:    */   private BigDecimal volumenProducto;
/* 131:    */   @Transient
/* 132:    */   private String codigoProducto;
/* 133:    */   @Transient
/* 134:    */   private String nombreBodega;
/* 135:    */   @Transient
/* 136:    */   private FacturaCliente facturaCliente;
/* 137:    */   @Column(name="pdf", nullable=true)
/* 138:    */   @Size(max=50)
/* 139:    */   private String pdf;
/* 140:    */   
/* 141:    */   public DespachoCliente() {}
/* 142:    */   
/* 143:    */   public DespachoCliente(int idDespachoCliente, String numero, Date fecha, String traCliente, Estado estado, String descripcion, String traAsiento, String traTipoAsiento)
/* 144:    */   {
/* 145:211 */     this.idDespachoCliente = idDespachoCliente;
/* 146:212 */     this.numero = numero;
/* 147:213 */     this.fecha = fecha;
/* 148:214 */     this.estado = estado;
/* 149:215 */     this.descripcion = descripcion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public DespachoCliente(int idDespachoCliente, String numero, Date fecha)
/* 153:    */   {
/* 154:223 */     this.idDespachoCliente = idDespachoCliente;
/* 155:224 */     this.numero = numero;
/* 156:225 */     this.fecha = fecha;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public DespachoCliente(String numero, Date fecha, String traCliente, String descripcion, String nombreProducto, BigDecimal pesoProducto, BigDecimal volumenProducto, String codigoProducto, String nombreBodega)
/* 160:    */   {
/* 161:230 */     this.numero = numero;
/* 162:231 */     this.fecha = fecha;
/* 163:232 */     this.descripcion = descripcion;
/* 164:233 */     this.nombreProducto = nombreProducto;
/* 165:234 */     this.pesoProducto = pesoProducto;
/* 166:235 */     this.volumenProducto = volumenProducto;
/* 167:236 */     this.codigoProducto = codigoProducto;
/* 168:237 */     this.nombreBodega = nombreBodega;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getId()
/* 172:    */   {
/* 173:247 */     return this.idDespachoCliente;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int getIdDespachoCliente()
/* 177:    */   {
/* 178:251 */     return this.idDespachoCliente;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setIdDespachoCliente(int idDespachoCliente)
/* 182:    */   {
/* 183:255 */     this.idDespachoCliente = idDespachoCliente;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Documento getDocumento()
/* 187:    */   {
/* 188:259 */     if (this.documento == null) {
/* 189:260 */       this.documento = new Documento();
/* 190:    */     }
/* 191:262 */     return this.documento;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setDocumento(Documento documento)
/* 195:    */   {
/* 196:266 */     this.documento = documento;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public int getIdOrganizacion()
/* 200:    */   {
/* 201:270 */     return this.idOrganizacion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setIdOrganizacion(int idOrganizacion)
/* 205:    */   {
/* 206:274 */     this.idOrganizacion = idOrganizacion;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Sucursal getSucursal()
/* 210:    */   {
/* 211:278 */     if (this.sucursal == null) {
/* 212:279 */       this.sucursal = new Sucursal();
/* 213:    */     }
/* 214:281 */     return this.sucursal;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setSucursal(Sucursal sucursal)
/* 218:    */   {
/* 219:285 */     this.sucursal = sucursal;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public Date getFecha()
/* 223:    */   {
/* 224:289 */     return this.fecha;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setFecha(Date fecha)
/* 228:    */   {
/* 229:293 */     this.fecha = fecha;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String getNumero()
/* 233:    */   {
/* 234:297 */     return this.numero;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setNumero(String numero)
/* 238:    */   {
/* 239:301 */     this.numero = numero;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public String getDescripcion()
/* 243:    */   {
/* 244:305 */     return this.descripcion;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setDescripcion(String descripcion)
/* 248:    */   {
/* 249:309 */     this.descripcion = descripcion;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public List<DetalleDespachoCliente> getListaDetalleDespachoCliente()
/* 253:    */   {
/* 254:313 */     return this.listaDetalleDespachoCliente;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setListaDetalleDespachoCliente(List<DetalleDespachoCliente> listaDetalleDespachoCliente)
/* 258:    */   {
/* 259:317 */     this.listaDetalleDespachoCliente = listaDetalleDespachoCliente;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public Empresa getEmpresa()
/* 263:    */   {
/* 264:321 */     return this.empresa;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setEmpresa(Empresa empresa)
/* 268:    */   {
/* 269:325 */     this.empresa = empresa;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public DireccionEmpresa getDireccionEmpresa()
/* 273:    */   {
/* 274:329 */     return this.direccionEmpresa;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/* 278:    */   {
/* 279:333 */     this.direccionEmpresa = direccionEmpresa;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public Estado getEstado()
/* 283:    */   {
/* 284:337 */     return this.estado;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setEstado(Estado estado)
/* 288:    */   {
/* 289:341 */     this.estado = estado;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public String toString()
/* 293:    */   {
/* 294:346 */     return this.numero;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public String getNombreProducto()
/* 298:    */   {
/* 299:355 */     return this.nombreProducto;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setNombreProducto(String nombreProducto)
/* 303:    */   {
/* 304:365 */     this.nombreProducto = nombreProducto;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public BigDecimal getPesoProducto()
/* 308:    */   {
/* 309:374 */     return this.pesoProducto;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setPesoProducto(BigDecimal pesoProducto)
/* 313:    */   {
/* 314:384 */     this.pesoProducto = pesoProducto;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public BigDecimal getVolumenProducto()
/* 318:    */   {
/* 319:393 */     return this.volumenProducto;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setVolumenProducto(BigDecimal volumenProducto)
/* 323:    */   {
/* 324:403 */     this.volumenProducto = volumenProducto;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public String getCodigoProducto()
/* 328:    */   {
/* 329:412 */     return this.codigoProducto;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setCodigoProducto(String codigoProducto)
/* 333:    */   {
/* 334:422 */     this.codigoProducto = codigoProducto;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public String getNombreBodega()
/* 338:    */   {
/* 339:431 */     return this.nombreBodega;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setNombreBodega(String nombreBodega)
/* 343:    */   {
/* 344:441 */     this.nombreBodega = nombreBodega;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public Date getFechaContabilizacion()
/* 348:    */   {
/* 349:445 */     return this.fechaContabilizacion;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 353:    */   {
/* 354:449 */     this.fechaContabilizacion = fechaContabilizacion;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public BigDecimal getTotal()
/* 358:    */   {
/* 359:453 */     this.total = BigDecimal.ZERO;
/* 360:454 */     for (DetalleDespachoCliente detalleDespachoCliente : getListaDetalleDespachoCliente()) {
/* 361:455 */       if (!detalleDespachoCliente.isEliminado()) {
/* 362:456 */         this.total = this.total.add(detalleDespachoCliente.getCantidad());
/* 363:    */       }
/* 364:    */     }
/* 365:459 */     return this.total;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void setTotal(BigDecimal total)
/* 369:    */   {
/* 370:463 */     this.total = total;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public List<String> getCamposAuditables()
/* 374:    */   {
/* 375:467 */     List<String> lista = new ArrayList();
/* 376:468 */     lista.add("cliente");
/* 377:469 */     lista.add("documento");
/* 378:470 */     lista.add("sucursal");
/* 379:471 */     lista.add("empresa");
/* 380:472 */     lista.add("fecha");
/* 381:473 */     lista.add("numero");
/* 382:474 */     lista.add("descripcion");
/* 383:475 */     lista.add("estado");
/* 384:476 */     lista.add("direccionEmpresa");
/* 385:477 */     lista.add("fechaContabilizacion");
/* 386:478 */     return lista;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public PedidoCliente getPedidoCliente()
/* 390:    */   {
/* 391:485 */     return this.pedidoCliente;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setPedidoCliente(PedidoCliente pedidoCliente)
/* 395:    */   {
/* 396:493 */     this.pedidoCliente = pedidoCliente;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public FacturaCliente getFacturaCliente()
/* 400:    */   {
/* 401:500 */     return this.facturaCliente;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 405:    */   {
/* 406:508 */     this.facturaCliente = facturaCliente;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public GuiaRemision getGuiaRemision()
/* 410:    */   {
/* 411:515 */     return this.guiaRemision;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setGuiaRemision(GuiaRemision guiaRemision)
/* 415:    */   {
/* 416:523 */     this.guiaRemision = guiaRemision;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public PersonaResponsable getResponsableSalidaMercaderia()
/* 420:    */   {
/* 421:532 */     return this.responsableSalidaMercaderia;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setResponsableSalidaMercaderia(PersonaResponsable responsableSalidaMercaderia)
/* 425:    */   {
/* 426:542 */     this.responsableSalidaMercaderia = responsableSalidaMercaderia;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public InterfazContableProceso getInterfazContableProceso()
/* 430:    */   {
/* 431:551 */     return this.interfazContableProceso;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 435:    */   {
/* 436:561 */     this.interfazContableProceso = interfazContableProceso;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public Subempresa getSubempresa()
/* 440:    */   {
/* 441:570 */     return this.subempresa;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void setSubempresa(Subempresa subempresa)
/* 445:    */   {
/* 446:580 */     this.subempresa = subempresa;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public Date getFechaAnulacion()
/* 450:    */   {
/* 451:589 */     return this.fechaAnulacion;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setFechaAnulacion(Date fechaAnulacion)
/* 455:    */   {
/* 456:599 */     this.fechaAnulacion = fechaAnulacion;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public boolean isIndicadorGeneradoPrefactura()
/* 460:    */   {
/* 461:603 */     return this.indicadorGeneradoPrefactura;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setIndicadorGeneradoPrefactura(boolean indicadorGeneradoPrefactura)
/* 465:    */   {
/* 466:607 */     this.indicadorGeneradoPrefactura = indicadorGeneradoPrefactura;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public String getPdf()
/* 470:    */   {
/* 471:611 */     return this.pdf;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setPdf(String pdf)
/* 475:    */   {
/* 476:615 */     this.pdf = pdf;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public PrefacturaCliente getPrefacturaCliente()
/* 480:    */   {
/* 481:619 */     return this.prefacturaCliente;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setPrefacturaCliente(PrefacturaCliente prefacturaCliente)
/* 485:    */   {
/* 486:623 */     this.prefacturaCliente = prefacturaCliente;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public boolean isIndicadorGeneradoFactura()
/* 490:    */   {
/* 491:627 */     return this.indicadorGeneradoFactura;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void setIndicadorGeneradoFactura(boolean indicadorGeneradoFactura)
/* 495:    */   {
/* 496:631 */     this.indicadorGeneradoFactura = indicadorGeneradoFactura;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public DimensionContable getProyecto()
/* 500:    */   {
/* 501:638 */     return this.proyecto;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public void setProyecto(DimensionContable proyecto)
/* 505:    */   {
/* 506:646 */     this.proyecto = proyecto;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public OrdenDespachoCliente getOrdenDespachoCliente()
/* 510:    */   {
/* 511:650 */     return this.ordenDespachoCliente;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void setOrdenDespachoCliente(OrdenDespachoCliente ordenDespachoCliente)
/* 515:    */   {
/* 516:654 */     this.ordenDespachoCliente = ordenDespachoCliente;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public TipoOrdenDespacho getTipoOrdenDespacho()
/* 520:    */   {
/* 521:658 */     return this.tipoOrdenDespacho;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public void setTipoOrdenDespacho(TipoOrdenDespacho tipoOrdenDespacho)
/* 525:    */   {
/* 526:662 */     this.tipoOrdenDespacho = tipoOrdenDespacho;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public Transportista getTransportista()
/* 530:    */   {
/* 531:666 */     return this.transportista;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public void setTransportista(Transportista transportista)
/* 535:    */   {
/* 536:670 */     this.transportista = transportista;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public Asiento getAsiento()
/* 540:    */   {
/* 541:677 */     return this.asiento;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void setAsiento(Asiento asiento)
/* 545:    */   {
/* 546:685 */     this.asiento = asiento;
/* 547:    */   }
/* 548:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DespachoCliente
 * JD-Core Version:    0.7.0.1
 */