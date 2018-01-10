/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import com.asinfo.as2.enumeraciones.Estado;
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
/*  21:    */ import javax.persistence.OneToOne;
/*  22:    */ import javax.persistence.Table;
/*  23:    */ import javax.persistence.TableGenerator;
/*  24:    */ import javax.persistence.Temporal;
/*  25:    */ import javax.persistence.TemporalType;
/*  26:    */ import javax.persistence.Transient;
/*  27:    */ import javax.validation.constraints.Digits;
/*  28:    */ import javax.validation.constraints.Min;
/*  29:    */ import javax.validation.constraints.NotNull;
/*  30:    */ import javax.validation.constraints.Size;
/*  31:    */ import org.hibernate.annotations.ColumnDefault;
/*  32:    */ 
/*  33:    */ @Entity
/*  34:    */ @Table(name="pedido_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})}, indexes={@javax.persistence.Index(columnList="id_empresa"), @javax.persistence.Index(columnList="numero"), @javax.persistence.Index(columnList="fecha_despacho")})
/*  35:    */ public class PedidoCliente
/*  36:    */   extends EntidadBase
/*  37:    */   implements Serializable
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = -133637269446727200L;
/*  40:    */   @Id
/*  41:    */   @TableGenerator(name="pedido_cliente", initialValue=0, allocationSize=50)
/*  42:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="pedido_cliente")
/*  43:    */   @Column(name="id_pedido_cliente")
/*  44:    */   private int idPedidoCliente;
/*  45:    */   @Column(name="id_organizacion", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private int idOrganizacion;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  50:    */   @NotNull
/*  51:    */   private Sucursal sucursal;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_pedido_cliente_padre", nullable=true)
/*  54:    */   private PedidoCliente pedidoClientePadre;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  57:    */   @NotNull
/*  58:    */   private Empresa empresa;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_subempresa", nullable=true)
/*  61:    */   private Subempresa subempresa;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_documento", nullable=false)
/*  64:    */   @NotNull
/*  65:    */   private Documento documento;
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_zona", nullable=true)
/*  68:    */   private Zona zona;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_direccion_empresa", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   private DireccionEmpresa direccionEmpresa;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_canal", nullable=true)
/*  75:    */   private Canal canal;
/*  76:    */   @Temporal(TemporalType.DATE)
/*  77:    */   @Column(name="fecha", nullable=false)
/*  78:    */   @NotNull
/*  79:    */   private Date fecha;
/*  80:    */   @Column(name="numero", nullable=false, length=20)
/*  81:    */   @NotNull
/*  82:    */   @Size(max=20)
/*  83:    */   private String numero;
/*  84:    */   @Column(name="total", nullable=false, precision=12, scale=2)
/*  85:    */   @NotNull
/*  86:    */   @Digits(integer=12, fraction=2)
/*  87:    */   @Min(0L)
/*  88:110 */   private BigDecimal total = BigDecimal.ZERO;
/*  89:    */   @Transient
/*  90:    */   @Min(0L)
/*  91:116 */   private BigDecimal subtotal = BigDecimal.ZERO;
/*  92:    */   @Transient
/*  93:    */   @Min(0L)
/*  94:120 */   private BigDecimal impuestoTotal = BigDecimal.ZERO;
/*  95:    */   @Transient
/*  96:    */   @Min(0L)
/*  97:124 */   private BigDecimal baseImponible = BigDecimal.ZERO;
/*  98:    */   @Column(name="descuento", nullable=false, precision=12, scale=2)
/*  99:    */   @NotNull
/* 100:    */   @Digits(integer=12, fraction=2)
/* 101:    */   @Min(0L)
/* 102:128 */   private BigDecimal descuento = BigDecimal.ZERO;
/* 103:    */   @Column(name="monto_ice", precision=12, scale=2)
/* 104:    */   @Digits(integer=12, fraction=2)
/* 105:    */   @Min(0L)
/* 106:134 */   private BigDecimal montoIce = BigDecimal.ZERO;
/* 107:    */   @Column(name="impuesto", nullable=false, precision=12, scale=2)
/* 108:    */   @NotNull
/* 109:    */   @Digits(integer=12, fraction=2)
/* 110:    */   @Min(0L)
/* 111:139 */   private BigDecimal impuesto = BigDecimal.ZERO;
/* 112:    */   @Column(name="descripcion", nullable=true, length=500)
/* 113:    */   @Size(max=500)
/* 114:    */   private String descripcion;
/* 115:    */   @Column(name="estado", nullable=false)
/* 116:    */   @Enumerated(EnumType.ORDINAL)
/* 117:    */   private Estado estado;
/* 118:    */   @Column(name="usuario_autoriza", nullable=true, length=50)
/* 119:153 */   private String usuarioAutoriza = "";
/* 120:    */   @Column(name="usuarios_autorizacion", nullable=true, length=500)
/* 121:156 */   private String usuariosAutorizacion = "";
/* 122:    */   @Column(name="pdf", nullable=true)
/* 123:    */   @Size(max=50)
/* 124:    */   private String pdf;
/* 125:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="pedidoCliente")
/* 126:163 */   private List<DetallePedidoCliente> listaDetallePedidoCliente = new ArrayList();
/* 127:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 128:    */   @JoinColumn(name="id_condicion_pago", nullable=false)
/* 129:    */   @NotNull
/* 130:    */   private CondicionPago condicionPago;
/* 131:    */   @Column(name="numero_cuotas", nullable=true, precision=12, scale=2)
/* 132:    */   @NotNull
/* 133:    */   @Digits(integer=12, fraction=2)
/* 134:    */   @Min(1L)
/* 135:    */   private int numeroCuotas;
/* 136:    */   @OneToOne(fetch=FetchType.LAZY)
/* 137:    */   @JoinColumn(name="id_agente_comercial", nullable=true)
/* 138:    */   private EntidadUsuario agenteComercial;
/* 139:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 140:    */   @JoinColumn(name="id_motivo_pedido_cliente", nullable=true)
/* 141:    */   private MotivoPedidoCliente motivoPedidoCliente;
/* 142:    */   @NotNull
/* 143:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 144:    */   @JoinColumn(name="id_bodega", nullable=false)
/* 145:    */   private Bodega bodega;
/* 146:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 147:    */   @JoinColumn(name="id_transportista", nullable=true)
/* 148:    */   private Transportista transportista;
/* 149:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 150:    */   @JoinColumn(name="id_chofer", nullable=true)
/* 151:    */   private Chofer chofer;
/* 152:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 153:    */   @JoinColumn(name="id_vehiculo", nullable=true)
/* 154:    */   private Vehiculo vehiculo;
/* 155:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 156:    */   @JoinColumn(name="id_ruta", nullable=true)
/* 157:    */   private Ruta ruta;
/* 158:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 159:    */   @JoinColumn(name="id_proyecto", nullable=true)
/* 160:    */   private DimensionContable proyecto;
/* 161:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 162:    */   @JoinColumn(name="id_contacto", nullable=true)
/* 163:    */   private Contacto contacto;
/* 164:    */   @Column(name="indicador_fijo", nullable=false)
/* 165:    */   private boolean indicadorFijo;
/* 166:    */   @Column(name="referencia1", nullable=true, length=300)
/* 167:    */   private String referencia1;
/* 168:    */   @Column(name="referencia8", nullable=true, length=200)
/* 169:    */   @Size(max=200)
/* 170:222 */   private String referencia8 = "";
/* 171:    */   @Column(name="referencia9", nullable=true, length=200)
/* 172:    */   @Size(max=200)
/* 173:227 */   private String referencia9 = "";
/* 174:    */   @Column(name="indicador_autorizacion_manual", nullable=false)
/* 175:    */   @NotNull
/* 176:    */   @ColumnDefault("'0'")
/* 177:    */   private boolean indicadorAutorizacionManual;
/* 178:    */   @Temporal(TemporalType.TIMESTAMP)
/* 179:    */   @Column(name="fecha_autorizacion", nullable=true)
/* 180:    */   private Date fechaAutorizacion;
/* 181:    */   @Transient
/* 182:    */   private String pedidoToString;
/* 183:    */   @Transient
/* 184:    */   @Min(0L)
/* 185:243 */   private BigDecimal totalPedido = BigDecimal.ZERO;
/* 186:    */   @Transient
/* 187:    */   private String traCliente;
/* 188:    */   @Transient
/* 189:    */   private boolean indicadorAutoriza;
/* 190:    */   @Temporal(TemporalType.DATE)
/* 191:    */   @Column(name="fecha_despacho", nullable=true)
/* 192:    */   private Date fechaDespacho;
/* 193:    */   @Transient
/* 194:    */   private Empresa empresaFinal;
/* 195:    */   @Column(name="codigo_movil", nullable=true, length=200)
/* 196:    */   @Size(max=200)
/* 197:260 */   private String codigoMovil = "";
/* 198:    */   @Column(name="indicador_pedido_preautorizado", nullable=true)
/* 199:265 */   private Boolean indicadorPedidoPreautorizado = Boolean.valueOf(false);
/* 200:    */   @Column(name="indicador_tiene_despacho", nullable=true)
/* 201:268 */   private Boolean indicadorTieneDespacho = Boolean.valueOf(false);
/* 202:    */   @Column(name="descuento_impuesto", nullable=false, precision=12, scale=2)
/* 203:    */   @NotNull
/* 204:    */   @Digits(integer=12, fraction=2)
/* 205:    */   @Min(0L)
/* 206:    */   @ColumnDefault("0.00")
/* 207:270 */   private BigDecimal descuentoImpuesto = BigDecimal.ZERO;
/* 208:    */   @Column(name="descripcion_2", nullable=true, length=500)
/* 209:    */   @Size(max=500)
/* 210:    */   private String descripcion2;
/* 211:    */   @Transient
/* 212:    */   private boolean indicadorAutorizacionPedidoPorCriterio;
/* 213:    */   
/* 214:    */   public boolean isAuditable()
/* 215:    */   {
/* 216:291 */     return true;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public List<String> getCamposAuditables()
/* 220:    */   {
/* 221:301 */     List<String> lista = new ArrayList();
/* 222:302 */     lista.add("empresa");
/* 223:303 */     lista.add("documento");
/* 224:304 */     lista.add("direccionEmpresa");
/* 225:305 */     lista.add("fecha");
/* 226:306 */     lista.add("total");
/* 227:307 */     lista.add("subtotal");
/* 228:308 */     lista.add("impuestoTotal");
/* 229:309 */     lista.add("descuento");
/* 230:310 */     lista.add("descripcion");
/* 231:311 */     lista.add("estado");
/* 232:312 */     lista.add("condicionPago");
/* 233:313 */     lista.add("numeroCuotas");
/* 234:    */     
/* 235:315 */     return lista;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public PedidoCliente() {}
/* 239:    */   
/* 240:    */   public PedidoCliente(int idPedidoCliente, String numero, Date fecha)
/* 241:    */   {
/* 242:331 */     this.idPedidoCliente = idPedidoCliente;
/* 243:332 */     this.numero = numero;
/* 244:333 */     this.fecha = fecha;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public PedidoCliente(int idPedidoCliente, String numero, Date fecha, Integer idEmpresa, String identificacionEmpresa, String nombreFiscalEmpresa)
/* 248:    */   {
/* 249:338 */     this.idPedidoCliente = idPedidoCliente;
/* 250:339 */     this.numero = numero;
/* 251:340 */     this.fecha = fecha;
/* 252:341 */     if (idEmpresa != null)
/* 253:    */     {
/* 254:342 */       this.empresa = new Empresa();
/* 255:343 */       this.empresa.setIdentificacion(identificacionEmpresa);
/* 256:344 */       this.empresa.setNombreFiscal(nombreFiscalEmpresa);
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public PedidoCliente(int idPedidoCliente, Date fecha, String numero, String traCliente)
/* 261:    */   {
/* 262:356 */     this.idPedidoCliente = idPedidoCliente;
/* 263:357 */     this.fecha = fecha;
/* 264:358 */     this.numero = numero;
/* 265:359 */     this.traCliente = traCliente;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public int getId()
/* 269:    */   {
/* 270:369 */     return this.idPedidoCliente;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public BigDecimal getTotalPedido()
/* 274:    */   {
/* 275:375 */     this.totalPedido = getTotal().subtract(getDescuento().add(getDescuentoImpuesto())).add(getImpuesto()).add(getMontoIce());
/* 276:376 */     return this.totalPedido;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public int getIdPedidoCliente()
/* 280:    */   {
/* 281:380 */     return this.idPedidoCliente;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setIdPedidoCliente(int idPedidoCliente)
/* 285:    */   {
/* 286:384 */     this.idPedidoCliente = idPedidoCliente;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public int getIdOrganizacion()
/* 290:    */   {
/* 291:388 */     return this.idOrganizacion;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setIdOrganizacion(int idOrganizacion)
/* 295:    */   {
/* 296:392 */     this.idOrganizacion = idOrganizacion;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public Sucursal getSucursal()
/* 300:    */   {
/* 301:396 */     return this.sucursal;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setSucursal(Sucursal sucursal)
/* 305:    */   {
/* 306:400 */     this.sucursal = sucursal;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public Empresa getEmpresa()
/* 310:    */   {
/* 311:404 */     return this.empresa;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setEmpresa(Empresa empresa)
/* 315:    */   {
/* 316:408 */     this.empresa = empresa;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public Documento getDocumento()
/* 320:    */   {
/* 321:412 */     return this.documento;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setDocumento(Documento documento)
/* 325:    */   {
/* 326:416 */     this.documento = documento;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public Date getFecha()
/* 330:    */   {
/* 331:420 */     return this.fecha;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setFecha(Date fecha)
/* 335:    */   {
/* 336:424 */     this.fecha = fecha;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public String getNumero()
/* 340:    */   {
/* 341:428 */     return this.numero;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setNumero(String numero)
/* 345:    */   {
/* 346:432 */     this.numero = numero;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public BigDecimal getTotal()
/* 350:    */   {
/* 351:436 */     return this.total;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setTotal(BigDecimal total)
/* 355:    */   {
/* 356:440 */     this.total = total;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public BigDecimal getImpuesto()
/* 360:    */   {
/* 361:444 */     return this.impuesto;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setImpuesto(BigDecimal impuesto)
/* 365:    */   {
/* 366:448 */     this.impuesto = impuesto;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public String getDescripcion()
/* 370:    */   {
/* 371:452 */     return this.descripcion;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setDescripcion(String descripcion)
/* 375:    */   {
/* 376:456 */     this.descripcion = descripcion;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public String getUsuarioCreacion()
/* 380:    */   {
/* 381:460 */     return this.usuarioCreacion;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setUsuarioCreacion(String usuarioCreacion)
/* 385:    */   {
/* 386:464 */     this.usuarioCreacion = usuarioCreacion;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public Date getFechaCreacion()
/* 390:    */   {
/* 391:468 */     return this.fechaCreacion;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setFechaCreacion(Date fechaCreacion)
/* 395:    */   {
/* 396:472 */     this.fechaCreacion = fechaCreacion;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public Date getFechaModificacion()
/* 400:    */   {
/* 401:476 */     return this.fechaModificacion;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setFechaModificacion(Date fechaModificacion)
/* 405:    */   {
/* 406:480 */     this.fechaModificacion = fechaModificacion;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public String getUsuarioModificacion()
/* 410:    */   {
/* 411:484 */     return this.usuarioModificacion;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setUsuarioModificacion(String usuarioModificacion)
/* 415:    */   {
/* 416:488 */     this.usuarioModificacion = usuarioModificacion;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public List<DetallePedidoCliente> getListaDetallePedidoCliente()
/* 420:    */   {
/* 421:492 */     return this.listaDetallePedidoCliente;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setListaDetallePedidoCliente(List<DetallePedidoCliente> listaDetallePedidoCliente)
/* 425:    */   {
/* 426:496 */     this.listaDetallePedidoCliente = listaDetallePedidoCliente;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public BigDecimal getSubtotal()
/* 430:    */   {
/* 431:505 */     return this.subtotal;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void setSubtotal(BigDecimal subtotal)
/* 435:    */   {
/* 436:515 */     this.subtotal = subtotal;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public BigDecimal getDescuento()
/* 440:    */   {
/* 441:524 */     return this.descuento;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void setDescuento(BigDecimal descuento)
/* 445:    */   {
/* 446:534 */     this.descuento = descuento;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public DireccionEmpresa getDireccionEmpresa()
/* 450:    */   {
/* 451:543 */     return this.direccionEmpresa;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/* 455:    */   {
/* 456:553 */     this.direccionEmpresa = direccionEmpresa;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public BigDecimal getImpuestoTotal()
/* 460:    */   {
/* 461:557 */     return this.impuestoTotal;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setImpuestoTotal(BigDecimal impuestoTotal)
/* 465:    */   {
/* 466:561 */     this.impuestoTotal = impuestoTotal;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public Estado getEstado()
/* 470:    */   {
/* 471:565 */     return this.estado;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setEstado(Estado estado)
/* 475:    */   {
/* 476:569 */     this.estado = estado;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public String getPedidoToString()
/* 480:    */   {
/* 481:578 */     this.pedidoToString = (getNumero() + " | " + getDescripcion());
/* 482:579 */     return this.pedidoToString;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public void setPedidoToString(String pedidoToString)
/* 486:    */   {
/* 487:589 */     this.pedidoToString = pedidoToString;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public String getTraCliente()
/* 491:    */   {
/* 492:593 */     return this.traCliente;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public void setTraCliente(String traCliente)
/* 496:    */   {
/* 497:597 */     this.traCliente = traCliente;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public CondicionPago getCondicionPago()
/* 501:    */   {
/* 502:606 */     return this.condicionPago;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void setCondicionPago(CondicionPago condicionPago)
/* 506:    */   {
/* 507:616 */     this.condicionPago = condicionPago;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public int getNumeroCuotas()
/* 511:    */   {
/* 512:625 */     return this.numeroCuotas;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public void setNumeroCuotas(int numeroCuotas)
/* 516:    */   {
/* 517:635 */     this.numeroCuotas = numeroCuotas;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public String toString()
/* 521:    */   {
/* 522:640 */     return this.numero;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public BigDecimal getBaseImponible()
/* 526:    */   {
/* 527:644 */     return this.baseImponible;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void setBaseImponible(BigDecimal baseImponible)
/* 531:    */   {
/* 532:648 */     this.baseImponible = baseImponible;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public EntidadUsuario getAgenteComercial()
/* 536:    */   {
/* 537:657 */     return this.agenteComercial;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 541:    */   {
/* 542:667 */     this.agenteComercial = agenteComercial;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public Canal getCanal()
/* 546:    */   {
/* 547:676 */     return this.canal;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void setCanal(Canal canal)
/* 551:    */   {
/* 552:686 */     this.canal = canal;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public Zona getZona()
/* 556:    */   {
/* 557:695 */     return this.zona;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public void setZona(Zona zona)
/* 561:    */   {
/* 562:705 */     this.zona = zona;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public MotivoPedidoCliente getMotivoPedidoCliente()
/* 566:    */   {
/* 567:714 */     return this.motivoPedidoCliente;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public void setMotivoPedidoCliente(MotivoPedidoCliente motivoPedidoCliente)
/* 571:    */   {
/* 572:724 */     this.motivoPedidoCliente = motivoPedidoCliente;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public Bodega getBodega()
/* 576:    */   {
/* 577:733 */     return this.bodega;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public void setBodega(Bodega bodega)
/* 581:    */   {
/* 582:743 */     this.bodega = bodega;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public Subempresa getSubempresa()
/* 586:    */   {
/* 587:752 */     return this.subempresa;
/* 588:    */   }
/* 589:    */   
/* 590:    */   public void setSubempresa(Subempresa subempresa)
/* 591:    */   {
/* 592:762 */     this.subempresa = subempresa;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public Transportista getTransportista()
/* 596:    */   {
/* 597:766 */     return this.transportista;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public void setTransportista(Transportista transportista)
/* 601:    */   {
/* 602:770 */     this.transportista = transportista;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public boolean isIndicadorAutoriza()
/* 606:    */   {
/* 607:774 */     return this.indicadorAutoriza;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public void setIndicadorAutoriza(boolean indicadorAutoriza)
/* 611:    */   {
/* 612:778 */     this.indicadorAutoriza = indicadorAutoriza;
/* 613:    */   }
/* 614:    */   
/* 615:    */   public String getUsuarioAutoriza()
/* 616:    */   {
/* 617:782 */     return this.usuarioAutoriza;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public void setUsuarioAutoriza(String usuarioAutoriza)
/* 621:    */   {
/* 622:786 */     this.usuarioAutoriza = usuarioAutoriza;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public void setTotalPedido(BigDecimal totalPedido)
/* 626:    */   {
/* 627:790 */     this.totalPedido = totalPedido;
/* 628:    */   }
/* 629:    */   
/* 630:    */   public DimensionContable getProyecto()
/* 631:    */   {
/* 632:797 */     return this.proyecto;
/* 633:    */   }
/* 634:    */   
/* 635:    */   public void setProyecto(DimensionContable proyecto)
/* 636:    */   {
/* 637:805 */     this.proyecto = proyecto;
/* 638:    */   }
/* 639:    */   
/* 640:    */   public Contacto getContacto()
/* 641:    */   {
/* 642:812 */     return this.contacto;
/* 643:    */   }
/* 644:    */   
/* 645:    */   public void setContacto(Contacto contacto)
/* 646:    */   {
/* 647:820 */     this.contacto = contacto;
/* 648:    */   }
/* 649:    */   
/* 650:    */   public Date getFechaDespacho()
/* 651:    */   {
/* 652:824 */     return this.fechaDespacho;
/* 653:    */   }
/* 654:    */   
/* 655:    */   public void setFechaDespacho(Date fechaDespacho)
/* 656:    */   {
/* 657:828 */     this.fechaDespacho = fechaDespacho;
/* 658:    */   }
/* 659:    */   
/* 660:    */   public boolean isIndicadorFijo()
/* 661:    */   {
/* 662:832 */     return this.indicadorFijo;
/* 663:    */   }
/* 664:    */   
/* 665:    */   public void setIndicadorFijo(boolean indicadorFijo)
/* 666:    */   {
/* 667:836 */     this.indicadorFijo = indicadorFijo;
/* 668:    */   }
/* 669:    */   
/* 670:    */   public Empresa getEmpresaFinal()
/* 671:    */   {
/* 672:840 */     if (this.subempresa != null) {
/* 673:841 */       this.empresaFinal = this.subempresa.getEmpresa();
/* 674:    */     } else {
/* 675:843 */       this.empresaFinal = this.empresa;
/* 676:    */     }
/* 677:845 */     return this.empresaFinal;
/* 678:    */   }
/* 679:    */   
/* 680:    */   public void setEmpresaFinal(Empresa empresaFinal)
/* 681:    */   {
/* 682:849 */     this.empresaFinal = empresaFinal;
/* 683:    */   }
/* 684:    */   
/* 685:    */   public String getReferencia1()
/* 686:    */   {
/* 687:853 */     return this.referencia1;
/* 688:    */   }
/* 689:    */   
/* 690:    */   public void setReferencia1(String referencia1)
/* 691:    */   {
/* 692:857 */     this.referencia1 = referencia1;
/* 693:    */   }
/* 694:    */   
/* 695:    */   public String getReferencia8()
/* 696:    */   {
/* 697:861 */     return this.referencia8;
/* 698:    */   }
/* 699:    */   
/* 700:    */   public void setReferencia8(String referencia8)
/* 701:    */   {
/* 702:865 */     this.referencia8 = referencia8;
/* 703:    */   }
/* 704:    */   
/* 705:    */   public String getReferencia9()
/* 706:    */   {
/* 707:869 */     return this.referencia9;
/* 708:    */   }
/* 709:    */   
/* 710:    */   public void setReferencia9(String referencia9)
/* 711:    */   {
/* 712:873 */     this.referencia9 = referencia9;
/* 713:    */   }
/* 714:    */   
/* 715:    */   public String getCodigoMovil()
/* 716:    */   {
/* 717:877 */     return this.codigoMovil;
/* 718:    */   }
/* 719:    */   
/* 720:    */   public void setCodigoMovil(String codigoMovil)
/* 721:    */   {
/* 722:881 */     this.codigoMovil = codigoMovil;
/* 723:    */   }
/* 724:    */   
/* 725:    */   public Boolean getIndicadorPedidoPreautorizado()
/* 726:    */   {
/* 727:885 */     return Boolean.valueOf(this.indicadorPedidoPreautorizado == null ? false : this.indicadorPedidoPreautorizado.booleanValue());
/* 728:    */   }
/* 729:    */   
/* 730:    */   public void setIndicadorPedidoPreautorizado(Boolean indicadorPedidoPreautorizado)
/* 731:    */   {
/* 732:889 */     this.indicadorPedidoPreautorizado = indicadorPedidoPreautorizado;
/* 733:    */   }
/* 734:    */   
/* 735:    */   public boolean isIndicadorAutorizacionManual()
/* 736:    */   {
/* 737:893 */     return this.indicadorAutorizacionManual;
/* 738:    */   }
/* 739:    */   
/* 740:    */   public void setIndicadorAutorizacionManual(boolean indicadorAutorizacionManual)
/* 741:    */   {
/* 742:897 */     this.indicadorAutorizacionManual = indicadorAutorizacionManual;
/* 743:    */   }
/* 744:    */   
/* 745:    */   public Chofer getChofer()
/* 746:    */   {
/* 747:901 */     return this.chofer;
/* 748:    */   }
/* 749:    */   
/* 750:    */   public void setChofer(Chofer chofer)
/* 751:    */   {
/* 752:905 */     this.chofer = chofer;
/* 753:    */   }
/* 754:    */   
/* 755:    */   public Vehiculo getVehiculo()
/* 756:    */   {
/* 757:909 */     return this.vehiculo;
/* 758:    */   }
/* 759:    */   
/* 760:    */   public void setVehiculo(Vehiculo vehiculo)
/* 761:    */   {
/* 762:913 */     this.vehiculo = vehiculo;
/* 763:    */   }
/* 764:    */   
/* 765:    */   public PedidoCliente getPedidoClientePadre()
/* 766:    */   {
/* 767:917 */     return this.pedidoClientePadre;
/* 768:    */   }
/* 769:    */   
/* 770:    */   public void setPedidoClientePadre(PedidoCliente pedidoClientePadre)
/* 771:    */   {
/* 772:921 */     this.pedidoClientePadre = pedidoClientePadre;
/* 773:    */   }
/* 774:    */   
/* 775:    */   public Boolean getIndicadorTieneDespacho()
/* 776:    */   {
/* 777:925 */     if (this.indicadorTieneDespacho == null) {
/* 778:926 */       this.indicadorTieneDespacho = Boolean.valueOf(false);
/* 779:    */     }
/* 780:928 */     return this.indicadorTieneDespacho;
/* 781:    */   }
/* 782:    */   
/* 783:    */   public void setIndicadorTieneDespacho(Boolean indicadorTieneDespacho)
/* 784:    */   {
/* 785:932 */     this.indicadorTieneDespacho = indicadorTieneDespacho;
/* 786:    */   }
/* 787:    */   
/* 788:    */   public String getUsuariosAutorizacion()
/* 789:    */   {
/* 790:936 */     return this.usuariosAutorizacion;
/* 791:    */   }
/* 792:    */   
/* 793:    */   public void setUsuariosAutorizacion(String usuariosAutorizacion)
/* 794:    */   {
/* 795:940 */     this.usuariosAutorizacion = usuariosAutorizacion;
/* 796:    */   }
/* 797:    */   
/* 798:    */   public Ruta getRuta()
/* 799:    */   {
/* 800:944 */     return this.ruta;
/* 801:    */   }
/* 802:    */   
/* 803:    */   public void setRuta(Ruta ruta)
/* 804:    */   {
/* 805:948 */     this.ruta = ruta;
/* 806:    */   }
/* 807:    */   
/* 808:    */   public BigDecimal getMontoIce()
/* 809:    */   {
/* 810:952 */     return this.montoIce == null ? BigDecimal.ZERO : this.montoIce;
/* 811:    */   }
/* 812:    */   
/* 813:    */   public void setMontoIce(BigDecimal montoIce)
/* 814:    */   {
/* 815:956 */     this.montoIce = montoIce;
/* 816:    */   }
/* 817:    */   
/* 818:    */   public BigDecimal getDescuentoImpuesto()
/* 819:    */   {
/* 820:960 */     return this.descuentoImpuesto;
/* 821:    */   }
/* 822:    */   
/* 823:    */   public void setDescuentoImpuesto(BigDecimal descuentoImpuesto)
/* 824:    */   {
/* 825:964 */     this.descuentoImpuesto = descuentoImpuesto;
/* 826:    */   }
/* 827:    */   
/* 828:    */   public Date getFechaAutorizacion()
/* 829:    */   {
/* 830:968 */     return this.fechaAutorizacion;
/* 831:    */   }
/* 832:    */   
/* 833:    */   public void setFechaAutorizacion(Date fechaAutorizacion)
/* 834:    */   {
/* 835:972 */     this.fechaAutorizacion = fechaAutorizacion;
/* 836:    */   }
/* 837:    */   
/* 838:    */   public String getPdf()
/* 839:    */   {
/* 840:976 */     return this.pdf;
/* 841:    */   }
/* 842:    */   
/* 843:    */   public void setPdf(String pdf)
/* 844:    */   {
/* 845:980 */     this.pdf = pdf;
/* 846:    */   }
/* 847:    */   
/* 848:    */   public boolean isIndicadorAutorizacionPedidoPorCriterio()
/* 849:    */   {
/* 850:984 */     return this.indicadorAutorizacionPedidoPorCriterio;
/* 851:    */   }
/* 852:    */   
/* 853:    */   public void setIndicadorAutorizacionPedidoPorCriterio(boolean indicadorAutorizacionPedidoPorCriterio)
/* 854:    */   {
/* 855:988 */     this.indicadorAutorizacionPedidoPorCriterio = indicadorAutorizacionPedidoPorCriterio;
/* 856:    */   }
/* 857:    */   
/* 858:    */   public String getDescripcion2()
/* 859:    */   {
/* 860:992 */     return this.descripcion2;
/* 861:    */   }
/* 862:    */   
/* 863:    */   public void setDescripcion2(String descripcion2)
/* 864:    */   {
/* 865:996 */     this.descripcion2 = descripcion2;
/* 866:    */   }
/* 867:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PedidoCliente
 * JD-Core Version:    0.7.0.1
 */