/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.calidad.MotivoCastigoCalidad;
/*   4:    */ import com.asinfo.as2.entities.calidad.VariableCalidadRegistroPeso;
/*   5:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*   6:    */ import com.asinfo.as2.enumeraciones.EstadoRegistroPeso;
/*   7:    */ import com.asinfo.as2.enumeraciones.TipoRegistroPeso;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.persistence.Column;
/*  15:    */ import javax.persistence.Entity;
/*  16:    */ import javax.persistence.EnumType;
/*  17:    */ import javax.persistence.Enumerated;
/*  18:    */ import javax.persistence.FetchType;
/*  19:    */ import javax.persistence.GeneratedValue;
/*  20:    */ import javax.persistence.GenerationType;
/*  21:    */ import javax.persistence.Id;
/*  22:    */ import javax.persistence.JoinColumn;
/*  23:    */ import javax.persistence.ManyToOne;
/*  24:    */ import javax.persistence.OneToMany;
/*  25:    */ import javax.persistence.Table;
/*  26:    */ import javax.persistence.TableGenerator;
/*  27:    */ import javax.persistence.Temporal;
/*  28:    */ import javax.persistence.TemporalType;
/*  29:    */ import javax.persistence.Transient;
/*  30:    */ import javax.validation.constraints.DecimalMin;
/*  31:    */ import javax.validation.constraints.Digits;
/*  32:    */ import javax.validation.constraints.NotNull;
/*  33:    */ import javax.validation.constraints.Size;
/*  34:    */ 
/*  35:    */ @Entity
/*  36:    */ @Table(name="registro_peso", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})})
/*  37:    */ public class RegistroPeso
/*  38:    */   extends EntidadBase
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 1L;
/*  41:    */   @Id
/*  42:    */   @TableGenerator(name="registro_peso", initialValue=0, allocationSize=50)
/*  43:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="registro_peso")
/*  44:    */   @Column(name="id_registro_peso")
/*  45:    */   private int idRegistroPeso;
/*  46:    */   @Column(name="id_organizacion", nullable=false)
/*  47:    */   private int idOrganizacion;
/*  48:    */   @Column(name="id_sucursal", nullable=false)
/*  49:    */   private int idSucursal;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_documento", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private Documento documento;
/*  54:    */   @Temporal(TemporalType.DATE)
/*  55:    */   @Column(name="fecha", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private Date fecha;
/*  58:    */   @Column(name="numero", nullable=false, length=20)
/*  59:    */   @NotNull
/*  60:    */   @Size(max=20)
/*  61:    */   private String numero;
/*  62:    */   @Column(name="estado", nullable=false)
/*  63:    */   @Enumerated(EnumType.ORDINAL)
/*  64:    */   private EstadoRegistroPeso estado;
/*  65:    */   @Column(name="tipo_registro_peso", nullable=false)
/*  66:    */   @Enumerated(EnumType.ORDINAL)
/*  67:    */   private TipoRegistroPeso tipoRegistroPeso;
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_detalle_pedido_proveedor", nullable=true)
/*  70:    */   private DetallePedidoProveedor detallePedidoProveedor;
/*  71:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  72:    */   @JoinColumn(name="id_detalle_pedido_proveedor_original", nullable=true)
/*  73:    */   private DetallePedidoProveedor detallePedidoProveedorOriginal;
/*  74:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  75:    */   @JoinColumn(name="id__detalle_transferencia_bodega", nullable=true)
/*  76:    */   private DetalleMovimientoInventario detalleTransferenciaBodega;
/*  77:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  78:    */   @JoinColumn(name="id_detalle_factura_proveedor", nullable=true)
/*  79:    */   private DetalleFacturaProveedor detalleFacturaProveedor;
/*  80:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  81:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  82:    */   private Empresa empresa;
/*  83:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  84:    */   @JoinColumn(name="id_transportista", nullable=false)
/*  85:    */   @NotNull
/*  86:    */   private Transportista transportista;
/*  87:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  88:    */   @JoinColumn(name="id_chofer", nullable=false)
/*  89:    */   @NotNull
/*  90:    */   private Chofer chofer;
/*  91:    */   @Column(name="nombre_chofer", nullable=true)
/*  92:    */   @Size(max=200)
/*  93:121 */   private String nombreChofer = "";
/*  94:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  95:    */   @JoinColumn(name="id_vehiculo", nullable=false)
/*  96:    */   @NotNull
/*  97:    */   private Vehiculo vehiculo;
/*  98:    */   @Size(max=10)
/*  99:    */   @Column(name="placa_vehiculo", nullable=true)
/* 100:131 */   private String placaVehiculo = "";
/* 101:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 102:    */   @JoinColumn(name="id_ruta", nullable=true)
/* 103:    */   private Ruta ruta;
/* 104:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 105:    */   @JoinColumn(name="id_producto", nullable=true)
/* 106:    */   private Producto producto;
/* 107:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 108:    */   @JoinColumn(name="id_lote", nullable=true)
/* 109:    */   private Lote lote;
/* 110:    */   @Column(name="indicador_liquidado_flete", nullable=false)
/* 111:    */   private boolean indicadorLiquidadoFlete;
/* 112:    */   @Column(name="descripcion", nullable=true, length=200)
/* 113:    */   @Size(max=200)
/* 114:    */   private String descripcion;
/* 115:    */   @Column(name="direccion", nullable=false, length=500)
/* 116:    */   @Size(max=500)
/* 117:155 */   private String direccion = "";
/* 118:    */   @Column(name="cargas_anteriores", nullable=true, length=5000)
/* 119:    */   @NotNull
/* 120:    */   @Size(max=5000)
/* 121:160 */   private String cargasAnteriores = "";
/* 122:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 123:    */   @JoinColumn(name="id_bodega", nullable=true)
/* 124:    */   private Bodega bodega;
/* 125:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 126:    */   @JoinColumn(name="id_bodega_liberar", nullable=true)
/* 127:    */   private Bodega bodegaLiberar;
/* 128:    */   @Column(name="carga_otros", nullable=true, length=5000)
/* 129:    */   @Size(max=5000)
/* 130:    */   private String cargaOtros;
/* 131:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 132:    */   @JoinColumn(name="id_dispositivo", nullable=true)
/* 133:    */   private Dispositivo dispositivo;
/* 134:    */   @Temporal(TemporalType.TIMESTAMP)
/* 135:    */   @Column(name="fecha_entrada", nullable=true)
/* 136:    */   private Date fechaEntrada;
/* 137:    */   @Temporal(TemporalType.TIMESTAMP)
/* 138:    */   @Column(name="fecha_salida", nullable=true)
/* 139:    */   private Date fechaSalida;
/* 140:    */   @Column(name="peso_entrada", nullable=true, precision=12, scale=2)
/* 141:    */   @Digits(integer=12, fraction=2)
/* 142:    */   @DecimalMin("0.00")
/* 143:191 */   private BigDecimal pesoEntrada = BigDecimal.ZERO;
/* 144:    */   @Column(name="peso_salida", nullable=true, precision=12, scale=2)
/* 145:    */   @Digits(integer=12, fraction=2)
/* 146:    */   @DecimalMin("0.00")
/* 147:196 */   private BigDecimal pesoSalida = BigDecimal.ZERO;
/* 148:    */   @Column(name="cantidad", nullable=true, precision=12, scale=2)
/* 149:    */   @Digits(integer=12, fraction=2)
/* 150:    */   @DecimalMin("0.00")
/* 151:201 */   private BigDecimal cantidad = BigDecimal.ZERO;
/* 152:    */   @Column(name="peso_destare_total", nullable=true, precision=12, scale=2)
/* 153:    */   @Digits(integer=12, fraction=2)
/* 154:206 */   private BigDecimal pesoDestareTotal = BigDecimal.ZERO;
/* 155:    */   @Column(name="peso_neto", nullable=true, precision=12, scale=2)
/* 156:    */   @Digits(integer=12, fraction=2)
/* 157:    */   @DecimalMin("0.00")
/* 158:210 */   private BigDecimal pesoNeto = BigDecimal.ZERO;
/* 159:    */   @Column(name="numero_guia_remision", nullable=true, length=20)
/* 160:    */   @Size(max=20)
/* 161:    */   private String numeroGuiaRemision;
/* 162:    */   @OneToMany(mappedBy="registroPeso", fetch=FetchType.LAZY)
/* 163:221 */   private List<RecepcionProveedor> listaRecepcionProveedor = new ArrayList();
/* 164:    */   @OneToMany(mappedBy="registroPeso", fetch=FetchType.LAZY)
/* 165:224 */   private List<DetalleRegistroPesoLote> listaDetalleRegistroPesoLote = new ArrayList();
/* 166:    */   @OneToMany(mappedBy="registroPeso", fetch=FetchType.LAZY)
/* 167:227 */   private List<VariableCalidadRegistroPeso> listaVariableCalidadRegistroPeso = new ArrayList();
/* 168:    */   @OneToMany(mappedBy="registroPeso", fetch=FetchType.LAZY)
/* 169:230 */   private List<DetalleRegistroPeso> listaDetalleRegistroPeso = new ArrayList();
/* 170:    */   @Column(name="peso_referencia", nullable=true, precision=12, scale=2)
/* 171:    */   @Digits(integer=10, fraction=2)
/* 172:    */   @DecimalMin("0.00")
/* 173:233 */   private BigDecimal pesoReferencia = BigDecimal.ZERO;
/* 174:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 175:    */   @JoinColumn(name="id_transferencia_bodega", nullable=true)
/* 176:    */   private MovimientoInventario transferenciaBodega;
/* 177:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 178:    */   @JoinColumn(name="id_despacho_cliente", nullable=true)
/* 179:    */   private DespachoCliente despachoCliente;
/* 180:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 181:    */   @JoinColumn(name="id_devolucion_cliente", nullable=true)
/* 182:    */   private FacturaCliente devolucionCliente;
/* 183:    */   @Column(name="estado_control_calidad", nullable=true)
/* 184:    */   @Enumerated(EnumType.ORDINAL)
/* 185:    */   private EstadoControlCalidad estadoControlCalidad;
/* 186:    */   @Column(name="control_calidad_finalizado", nullable=true)
/* 187:    */   private Boolean controlCalidadFinalizado;
/* 188:    */   @Column(name="liberado_castigo", nullable=true)
/* 189:    */   private Boolean liberadoCastigo;
/* 190:    */   @Column(name="observacion", nullable=true, length=200)
/* 191:    */   @Size(max=200)
/* 192:    */   private String observacion;
/* 193:    */   @Column(name="observacion_calidad", nullable=true, length=200)
/* 194:    */   @Size(max=200)
/* 195:    */   private String observacionCalidad;
/* 196:    */   @Column(name="presentacion", nullable=true, length=200)
/* 197:    */   @Size(max=200)
/* 198:    */   private String presentacion;
/* 199:    */   @Column(name="condiciones_empaque", nullable=true, length=200)
/* 200:    */   @Size(max=200)
/* 201:    */   private String condicionesEmpaque;
/* 202:    */   @Column(name="tipo_registro_sanitario", nullable=true, length=200)
/* 203:    */   @Size(max=200)
/* 204:    */   private String tipoRegistroSanitario;
/* 205:    */   @Column(name="numero_registro_sanitario", nullable=true, length=200)
/* 206:    */   @Size(max=200)
/* 207:    */   private String numeroRegistroSanitario;
/* 208:    */   @Temporal(TemporalType.DATE)
/* 209:    */   @Column(name="fecha_elaboracion", nullable=true)
/* 210:    */   private Date fechaElaboracion;
/* 211:    */   @Temporal(TemporalType.DATE)
/* 212:    */   @Column(name="fecha_caducidad", nullable=true)
/* 213:    */   private Date fechaCaducidad;
/* 214:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 215:    */   @JoinColumn(name="id_motivo_castigo_calidad", nullable=true)
/* 216:    */   private MotivoCastigoCalidad motivoCastigoCalidad;
/* 217:    */   @Column(name="peso_destare_unidad", nullable=true, precision=12, scale=2)
/* 218:303 */   private BigDecimal pesoDestareUnidad = BigDecimal.ZERO;
/* 219:    */   @Transient
/* 220:    */   private OrdenSalidaMaterial ordenSalidaMaterial;
/* 221:    */   @Transient
/* 222:    */   private PuntoDeVenta puntoDeVenta;
/* 223:    */   @Transient
/* 224:312 */   private BigDecimal totalDetalles = BigDecimal.ZERO;
/* 225:    */   @Transient
/* 226:315 */   private BigDecimal totalPesoDetalles = BigDecimal.ZERO;
/* 227:    */   @Transient
/* 228:318 */   private Map<String, BigDecimal> mapaTotalDetalles = new HashMap();
/* 229:    */   
/* 230:    */   public int getId()
/* 231:    */   {
/* 232:323 */     return this.idRegistroPeso;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public int getIdRegistroPeso()
/* 236:    */   {
/* 237:330 */     return this.idRegistroPeso;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setIdRegistroPeso(int idRegistroPeso)
/* 241:    */   {
/* 242:334 */     this.idRegistroPeso = idRegistroPeso;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public int getIdOrganizacion()
/* 246:    */   {
/* 247:338 */     return this.idOrganizacion;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setIdOrganizacion(int idOrganizacion)
/* 251:    */   {
/* 252:342 */     this.idOrganizacion = idOrganizacion;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public int getIdSucursal()
/* 256:    */   {
/* 257:346 */     return this.idSucursal;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setIdSucursal(int idSucursal)
/* 261:    */   {
/* 262:350 */     this.idSucursal = idSucursal;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Documento getDocumento()
/* 266:    */   {
/* 267:354 */     return this.documento;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setDocumento(Documento documento)
/* 271:    */   {
/* 272:358 */     this.documento = documento;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public String getNumero()
/* 276:    */   {
/* 277:362 */     return this.numero;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setNumero(String numero)
/* 281:    */   {
/* 282:366 */     this.numero = numero;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public EstadoRegistroPeso getEstado()
/* 286:    */   {
/* 287:370 */     return this.estado;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setEstado(EstadoRegistroPeso estado)
/* 291:    */   {
/* 292:374 */     this.estado = estado;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public EstadoControlCalidad getEstadoControlCalidad()
/* 296:    */   {
/* 297:381 */     return this.estadoControlCalidad;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setEstadoControlCalidad(EstadoControlCalidad estadoControlCalidad)
/* 301:    */   {
/* 302:389 */     this.estadoControlCalidad = estadoControlCalidad;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public TipoRegistroPeso getTipoRegistroPeso()
/* 306:    */   {
/* 307:393 */     return this.tipoRegistroPeso;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setTipoRegistroPeso(TipoRegistroPeso tipoRegistroPeso)
/* 311:    */   {
/* 312:397 */     this.tipoRegistroPeso = tipoRegistroPeso;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public DetalleMovimientoInventario getDetalleTransferenciaBodega()
/* 316:    */   {
/* 317:401 */     return this.detalleTransferenciaBodega;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setDetalleTransferenciaBodega(DetalleMovimientoInventario detalleTransferenciaBodega)
/* 321:    */   {
/* 322:405 */     this.detalleTransferenciaBodega = detalleTransferenciaBodega;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public Transportista getTransportista()
/* 326:    */   {
/* 327:409 */     return this.transportista;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setTransportista(Transportista transportista)
/* 331:    */   {
/* 332:413 */     this.transportista = transportista;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public Chofer getChofer()
/* 336:    */   {
/* 337:417 */     return this.chofer;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setChofer(Chofer chofer)
/* 341:    */   {
/* 342:421 */     this.chofer = chofer;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public Vehiculo getVehiculo()
/* 346:    */   {
/* 347:425 */     return this.vehiculo;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setVehiculo(Vehiculo vehiculo)
/* 351:    */   {
/* 352:429 */     this.vehiculo = vehiculo;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public String getDireccion()
/* 356:    */   {
/* 357:433 */     return this.direccion;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setDireccion(String direccion)
/* 361:    */   {
/* 362:437 */     this.direccion = direccion;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public String getCargasAnteriores()
/* 366:    */   {
/* 367:441 */     return this.cargasAnteriores;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setCargasAnteriores(String cargasAnteriores)
/* 371:    */   {
/* 372:445 */     this.cargasAnteriores = cargasAnteriores;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public Bodega getBodega()
/* 376:    */   {
/* 377:449 */     return this.bodega;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setBodega(Bodega bodega)
/* 381:    */   {
/* 382:453 */     this.bodega = bodega;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public String getNumeroGuiaRemision()
/* 386:    */   {
/* 387:457 */     return this.numeroGuiaRemision;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setNumeroGuiaRemision(String numeroGuiaRemision)
/* 391:    */   {
/* 392:461 */     this.numeroGuiaRemision = numeroGuiaRemision;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public BigDecimal getPesoReferencia()
/* 396:    */   {
/* 397:465 */     return this.pesoReferencia;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setPesoReferencia(BigDecimal pesoReferencia)
/* 401:    */   {
/* 402:469 */     this.pesoReferencia = pesoReferencia;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public Date getFecha()
/* 406:    */   {
/* 407:473 */     return this.fecha;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setFecha(Date fecha)
/* 411:    */   {
/* 412:477 */     this.fecha = fecha;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public Empresa getEmpresa()
/* 416:    */   {
/* 417:481 */     return this.empresa;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void setEmpresa(Empresa empresa)
/* 421:    */   {
/* 422:485 */     this.empresa = empresa;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public DetallePedidoProveedor getDetallePedidoProveedor()
/* 426:    */   {
/* 427:489 */     return this.detallePedidoProveedor;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setDetallePedidoProveedor(DetallePedidoProveedor detallePedidoProveedor)
/* 431:    */   {
/* 432:493 */     this.detallePedidoProveedor = detallePedidoProveedor;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public DetallePedidoProveedor getDetallePedidoProveedorOriginal()
/* 436:    */   {
/* 437:500 */     return this.detallePedidoProveedorOriginal;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setDetallePedidoProveedorOriginal(DetallePedidoProveedor detallePedidoProveedorOriginal)
/* 441:    */   {
/* 442:508 */     this.detallePedidoProveedorOriginal = detallePedidoProveedorOriginal;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public Dispositivo getDispositivo()
/* 446:    */   {
/* 447:512 */     return this.dispositivo;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setDispositivo(Dispositivo dispositivo)
/* 451:    */   {
/* 452:516 */     this.dispositivo = dispositivo;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public Date getFechaEntrada()
/* 456:    */   {
/* 457:520 */     return this.fechaEntrada;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public void setFechaEntrada(Date fechaEntrada)
/* 461:    */   {
/* 462:524 */     this.fechaEntrada = fechaEntrada;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public Date getFechaSalida()
/* 466:    */   {
/* 467:528 */     return this.fechaSalida;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void setFechaSalida(Date fechaSalida)
/* 471:    */   {
/* 472:532 */     this.fechaSalida = fechaSalida;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public BigDecimal getPesoEntrada()
/* 476:    */   {
/* 477:536 */     return this.pesoEntrada;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void setPesoEntrada(BigDecimal pesoEntrada)
/* 481:    */   {
/* 482:540 */     this.pesoEntrada = pesoEntrada;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public BigDecimal getPesoSalida()
/* 486:    */   {
/* 487:544 */     return this.pesoSalida;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public void setPesoSalida(BigDecimal pesoSalida)
/* 491:    */   {
/* 492:548 */     this.pesoSalida = pesoSalida;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public BigDecimal getCantidad()
/* 496:    */   {
/* 497:552 */     return this.cantidad;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public void setCantidad(BigDecimal cantidad)
/* 501:    */   {
/* 502:556 */     this.cantidad = cantidad;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public BigDecimal getPesoDestareTotal()
/* 506:    */   {
/* 507:560 */     return this.pesoDestareTotal;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public void setPesoDestareTotal(BigDecimal pesoDestareTotal)
/* 511:    */   {
/* 512:564 */     this.pesoDestareTotal = pesoDestareTotal;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public BigDecimal getPesoNeto()
/* 516:    */   {
/* 517:568 */     return this.pesoNeto;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public void setPesoNeto(BigDecimal pesoNeto)
/* 521:    */   {
/* 522:572 */     this.pesoNeto = pesoNeto;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public Producto getProducto()
/* 526:    */   {
/* 527:576 */     return this.producto;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void setProducto(Producto producto)
/* 531:    */   {
/* 532:580 */     this.producto = producto;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public Lote getLote()
/* 536:    */   {
/* 537:584 */     return this.lote;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void setLote(Lote lote)
/* 541:    */   {
/* 542:588 */     this.lote = lote;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public String getDescripcion()
/* 546:    */   {
/* 547:592 */     return this.descripcion;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void setDescripcion(String descripcion)
/* 551:    */   {
/* 552:596 */     this.descripcion = descripcion;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public List<RecepcionProveedor> getListaRecepcionProveedor()
/* 556:    */   {
/* 557:600 */     return this.listaRecepcionProveedor;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public void setListaRecepcionProveedor(List<RecepcionProveedor> listaRecepcionProveedor)
/* 561:    */   {
/* 562:604 */     this.listaRecepcionProveedor = listaRecepcionProveedor;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public String getCargaOtros()
/* 566:    */   {
/* 567:608 */     return this.cargaOtros;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public void setCargaOtros(String cargaOtros)
/* 571:    */   {
/* 572:612 */     this.cargaOtros = cargaOtros;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public MovimientoInventario getTransferenciaBodega()
/* 576:    */   {
/* 577:616 */     return this.transferenciaBodega;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public void setTransferenciaBodega(MovimientoInventario transferenciaBodega)
/* 581:    */   {
/* 582:620 */     this.transferenciaBodega = transferenciaBodega;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public List<DetalleRegistroPesoLote> getListaDetalleRegistroPesoLote()
/* 586:    */   {
/* 587:624 */     return this.listaDetalleRegistroPesoLote;
/* 588:    */   }
/* 589:    */   
/* 590:    */   public void setListaDetalleRegistroPesoLote(List<DetalleRegistroPesoLote> listaDetalleRegistroPesoLote)
/* 591:    */   {
/* 592:628 */     this.listaDetalleRegistroPesoLote = listaDetalleRegistroPesoLote;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public BigDecimal getTotalDetalles()
/* 596:    */   {
/* 597:632 */     return this.totalDetalles;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public void setTotalDetalles(BigDecimal totalDetalles)
/* 601:    */   {
/* 602:636 */     this.totalDetalles = totalDetalles;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public DespachoCliente getDespachoCliente()
/* 606:    */   {
/* 607:640 */     return this.despachoCliente;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public void setDespachoCliente(DespachoCliente despachoCliente)
/* 611:    */   {
/* 612:644 */     this.despachoCliente = despachoCliente;
/* 613:    */   }
/* 614:    */   
/* 615:    */   public Ruta getRuta()
/* 616:    */   {
/* 617:648 */     return this.ruta;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public void setRuta(Ruta ruta)
/* 621:    */   {
/* 622:652 */     this.ruta = ruta;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public boolean isIndicadorLiquidadoFlete()
/* 626:    */   {
/* 627:656 */     return this.indicadorLiquidadoFlete;
/* 628:    */   }
/* 629:    */   
/* 630:    */   public void setIndicadorLiquidadoFlete(boolean indicadorLiquidadoFlete)
/* 631:    */   {
/* 632:660 */     this.indicadorLiquidadoFlete = indicadorLiquidadoFlete;
/* 633:    */   }
/* 634:    */   
/* 635:    */   public DetalleFacturaProveedor getDetalleFacturaProveedor()
/* 636:    */   {
/* 637:664 */     return this.detalleFacturaProveedor;
/* 638:    */   }
/* 639:    */   
/* 640:    */   public void setDetalleFacturaProveedor(DetalleFacturaProveedor detalleFacturaProveedor)
/* 641:    */   {
/* 642:668 */     this.detalleFacturaProveedor = detalleFacturaProveedor;
/* 643:    */   }
/* 644:    */   
/* 645:    */   public Boolean getControlCalidadFinalizado()
/* 646:    */   {
/* 647:675 */     return this.controlCalidadFinalizado;
/* 648:    */   }
/* 649:    */   
/* 650:    */   public void setControlCalidadFinalizado(Boolean controlCalidadFinalizado)
/* 651:    */   {
/* 652:683 */     this.controlCalidadFinalizado = controlCalidadFinalizado;
/* 653:    */   }
/* 654:    */   
/* 655:    */   public List<VariableCalidadRegistroPeso> getListaVariableCalidadRegistroPeso()
/* 656:    */   {
/* 657:690 */     return this.listaVariableCalidadRegistroPeso;
/* 658:    */   }
/* 659:    */   
/* 660:    */   public void setListaVariableCalidadRegistroPeso(List<VariableCalidadRegistroPeso> listaVariableCalidadRegistroPeso)
/* 661:    */   {
/* 662:698 */     this.listaVariableCalidadRegistroPeso = listaVariableCalidadRegistroPeso;
/* 663:    */   }
/* 664:    */   
/* 665:    */   public Boolean getLiberadoCastigo()
/* 666:    */   {
/* 667:705 */     if (this.liberadoCastigo == null) {
/* 668:706 */       this.liberadoCastigo = Boolean.valueOf(false);
/* 669:    */     }
/* 670:708 */     return this.liberadoCastigo;
/* 671:    */   }
/* 672:    */   
/* 673:    */   public void setLiberadoCastigo(Boolean liberadoCastigo)
/* 674:    */   {
/* 675:716 */     this.liberadoCastigo = liberadoCastigo;
/* 676:    */   }
/* 677:    */   
/* 678:    */   public String getObservacion()
/* 679:    */   {
/* 680:723 */     return this.observacion;
/* 681:    */   }
/* 682:    */   
/* 683:    */   public void setObservacion(String observacion)
/* 684:    */   {
/* 685:731 */     this.observacion = observacion;
/* 686:    */   }
/* 687:    */   
/* 688:    */   public String getPresentacion()
/* 689:    */   {
/* 690:738 */     return this.presentacion;
/* 691:    */   }
/* 692:    */   
/* 693:    */   public void setPresentacion(String presentacion)
/* 694:    */   {
/* 695:746 */     this.presentacion = presentacion;
/* 696:    */   }
/* 697:    */   
/* 698:    */   public String getCondicionesEmpaque()
/* 699:    */   {
/* 700:753 */     return this.condicionesEmpaque;
/* 701:    */   }
/* 702:    */   
/* 703:    */   public void setCondicionesEmpaque(String condicionesEmpaque)
/* 704:    */   {
/* 705:761 */     this.condicionesEmpaque = condicionesEmpaque;
/* 706:    */   }
/* 707:    */   
/* 708:    */   public String getTipoRegistroSanitario()
/* 709:    */   {
/* 710:768 */     return this.tipoRegistroSanitario;
/* 711:    */   }
/* 712:    */   
/* 713:    */   public void setTipoRegistroSanitario(String tipoRegistroSanitario)
/* 714:    */   {
/* 715:776 */     this.tipoRegistroSanitario = tipoRegistroSanitario;
/* 716:    */   }
/* 717:    */   
/* 718:    */   public String getNumeroRegistroSanitario()
/* 719:    */   {
/* 720:783 */     return this.numeroRegistroSanitario;
/* 721:    */   }
/* 722:    */   
/* 723:    */   public void setNumeroRegistroSanitario(String numeroRegistroSanitario)
/* 724:    */   {
/* 725:791 */     this.numeroRegistroSanitario = numeroRegistroSanitario;
/* 726:    */   }
/* 727:    */   
/* 728:    */   public Date getFechaElaboracion()
/* 729:    */   {
/* 730:798 */     return this.fechaElaboracion;
/* 731:    */   }
/* 732:    */   
/* 733:    */   public void setFechaElaboracion(Date fechaElaboracion)
/* 734:    */   {
/* 735:806 */     this.fechaElaboracion = fechaElaboracion;
/* 736:    */   }
/* 737:    */   
/* 738:    */   public Date getFechaCaducidad()
/* 739:    */   {
/* 740:813 */     return this.fechaCaducidad;
/* 741:    */   }
/* 742:    */   
/* 743:    */   public void setFechaCaducidad(Date fechaCaducidad)
/* 744:    */   {
/* 745:821 */     this.fechaCaducidad = fechaCaducidad;
/* 746:    */   }
/* 747:    */   
/* 748:    */   public MotivoCastigoCalidad getMotivoCastigoCalidad()
/* 749:    */   {
/* 750:828 */     return this.motivoCastigoCalidad;
/* 751:    */   }
/* 752:    */   
/* 753:    */   public void setMotivoCastigoCalidad(MotivoCastigoCalidad motivoCastigoCalidad)
/* 754:    */   {
/* 755:836 */     this.motivoCastigoCalidad = motivoCastigoCalidad;
/* 756:    */   }
/* 757:    */   
/* 758:    */   public String getObservacionCalidad()
/* 759:    */   {
/* 760:840 */     return this.observacionCalidad;
/* 761:    */   }
/* 762:    */   
/* 763:    */   public void setObservacionCalidad(String observacionCalidad)
/* 764:    */   {
/* 765:844 */     this.observacionCalidad = observacionCalidad;
/* 766:    */   }
/* 767:    */   
/* 768:    */   public List<DetalleRegistroPeso> getListaDetalleRegistroPeso()
/* 769:    */   {
/* 770:848 */     return this.listaDetalleRegistroPeso;
/* 771:    */   }
/* 772:    */   
/* 773:    */   public void setListaDetalleRegistroPeso(List<DetalleRegistroPeso> listaDetalleRegistroPeso)
/* 774:    */   {
/* 775:852 */     this.listaDetalleRegistroPeso = listaDetalleRegistroPeso;
/* 776:    */   }
/* 777:    */   
/* 778:    */   public Map<String, BigDecimal> getMapaTotalDetalles()
/* 779:    */   {
/* 780:856 */     return this.mapaTotalDetalles;
/* 781:    */   }
/* 782:    */   
/* 783:    */   public void setMapaTotalDetalles(Map<String, BigDecimal> mapaTotalDetalles)
/* 784:    */   {
/* 785:860 */     this.mapaTotalDetalles = mapaTotalDetalles;
/* 786:    */   }
/* 787:    */   
/* 788:    */   public BigDecimal getTotalPesoDetalles()
/* 789:    */   {
/* 790:864 */     return this.totalPesoDetalles;
/* 791:    */   }
/* 792:    */   
/* 793:    */   public void setTotalPesoDetalles(BigDecimal totalPesoDetalles)
/* 794:    */   {
/* 795:868 */     this.totalPesoDetalles = totalPesoDetalles;
/* 796:    */   }
/* 797:    */   
/* 798:    */   public BigDecimal getPesoDestareUnidad()
/* 799:    */   {
/* 800:872 */     return this.pesoDestareUnidad;
/* 801:    */   }
/* 802:    */   
/* 803:    */   public void setPesoDestareUnidad(BigDecimal pesoDestareUnidad)
/* 804:    */   {
/* 805:876 */     this.pesoDestareUnidad = pesoDestareUnidad;
/* 806:    */   }
/* 807:    */   
/* 808:    */   public Bodega getBodegaLiberar()
/* 809:    */   {
/* 810:880 */     return this.bodegaLiberar;
/* 811:    */   }
/* 812:    */   
/* 813:    */   public void setBodegaLiberar(Bodega bodegaLiberar)
/* 814:    */   {
/* 815:884 */     this.bodegaLiberar = bodegaLiberar;
/* 816:    */   }
/* 817:    */   
/* 818:    */   public OrdenSalidaMaterial getOrdenSalidaMaterial()
/* 819:    */   {
/* 820:888 */     return this.ordenSalidaMaterial;
/* 821:    */   }
/* 822:    */   
/* 823:    */   public void setOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 824:    */   {
/* 825:892 */     this.ordenSalidaMaterial = ordenSalidaMaterial;
/* 826:    */   }
/* 827:    */   
/* 828:    */   public FacturaCliente getDevolucionCliente()
/* 829:    */   {
/* 830:896 */     return this.devolucionCliente;
/* 831:    */   }
/* 832:    */   
/* 833:    */   public void setDevolucionCliente(FacturaCliente devolucionCliente)
/* 834:    */   {
/* 835:900 */     this.devolucionCliente = devolucionCliente;
/* 836:    */   }
/* 837:    */   
/* 838:    */   public PuntoDeVenta getPuntoDeVenta()
/* 839:    */   {
/* 840:904 */     return this.puntoDeVenta;
/* 841:    */   }
/* 842:    */   
/* 843:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 844:    */   {
/* 845:908 */     this.puntoDeVenta = puntoDeVenta;
/* 846:    */   }
/* 847:    */   
/* 848:    */   public String getNombreChofer()
/* 849:    */   {
/* 850:912 */     return this.nombreChofer;
/* 851:    */   }
/* 852:    */   
/* 853:    */   public void setNombreChofer(String nombreChofer)
/* 854:    */   {
/* 855:916 */     this.nombreChofer = nombreChofer;
/* 856:    */   }
/* 857:    */   
/* 858:    */   public String getPlacaVehiculo()
/* 859:    */   {
/* 860:920 */     return this.placaVehiculo;
/* 861:    */   }
/* 862:    */   
/* 863:    */   public void setPlacaVehiculo(String placaVehiculo)
/* 864:    */   {
/* 865:924 */     this.placaVehiculo = placaVehiculo;
/* 866:    */   }
/* 867:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RegistroPeso
 * JD-Core Version:    0.7.0.1
 */