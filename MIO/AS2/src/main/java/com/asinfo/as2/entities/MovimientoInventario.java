/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.mantenimiento.OrdenTrabajoMantenimiento;
/*   4:    */ import com.asinfo.as2.entities.produccion.Maquina;
/*   5:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import javax.persistence.Column;
/*  13:    */ import javax.persistence.Entity;
/*  14:    */ import javax.persistence.EnumType;
/*  15:    */ import javax.persistence.Enumerated;
/*  16:    */ import javax.persistence.FetchType;
/*  17:    */ import javax.persistence.GeneratedValue;
/*  18:    */ import javax.persistence.GenerationType;
/*  19:    */ import javax.persistence.Id;
/*  20:    */ import javax.persistence.JoinColumn;
/*  21:    */ import javax.persistence.ManyToOne;
/*  22:    */ import javax.persistence.OneToMany;
/*  23:    */ import javax.persistence.OneToOne;
/*  24:    */ import javax.persistence.OrderBy;
/*  25:    */ import javax.persistence.Table;
/*  26:    */ import javax.persistence.TableGenerator;
/*  27:    */ import javax.persistence.Temporal;
/*  28:    */ import javax.persistence.TemporalType;
/*  29:    */ import javax.persistence.Transient;
/*  30:    */ import javax.validation.constraints.NotNull;
/*  31:    */ import javax.validation.constraints.Size;
/*  32:    */ 
/*  33:    */ @Entity
/*  34:    */ @Table(name="movimiento_inventario", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_documento", "numero"})}, indexes={@javax.persistence.Index(columnList="id_organizacion, fecha")})
/*  35:    */ public class MovimientoInventario
/*  36:    */   extends EntidadBase
/*  37:    */   implements Serializable, Comparable<MovimientoInventario>
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = 1L;
/*  40:    */   @Id
/*  41:    */   @TableGenerator(name="movimiento_inventario", initialValue=0, allocationSize=50)
/*  42:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="movimiento_inventario")
/*  43:    */   @Column(name="id_movimiento_inventario", unique=true, nullable=false)
/*  44:    */   private int idMovimientoInventario;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_documento", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private Documento documento;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_documento_destino", nullable=true)
/*  51:    */   private Documento documentoDestino;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_toma_fisica", nullable=true)
/*  54:    */   private TomaFisica tomaFisica;
/*  55:    */   @Column(name="id_organizacion", nullable=false)
/*  56:    */   private int idOrganizacion;
/*  57:    */   @NotNull
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  60:    */   private Sucursal sucursal;
/*  61:    */   @Column(name="numero", nullable=false, length=20)
/*  62:    */   @NotNull
/*  63:    */   @Size(max=20)
/*  64:    */   private String numero;
/*  65:    */   @Temporal(TemporalType.DATE)
/*  66:    */   @Column(name="fecha", nullable=false)
/*  67:    */   @NotNull
/*  68:    */   private Date fecha;
/*  69:    */   @Temporal(TemporalType.TIMESTAMP)
/*  70:    */   @Column(name="fecha_contabilizacion", nullable=true, length=23)
/*  71:    */   private Date fechaContabilizacion;
/*  72:    */   @Enumerated(EnumType.ORDINAL)
/*  73:    */   @Column(name="estado", nullable=false)
/*  74:    */   @NotNull
/*  75:    */   private Estado estado;
/*  76:    */   @Column(name="descripcion", length=500)
/*  77:    */   @Size(max=500)
/*  78:    */   private String descripcion;
/*  79:    */   @OneToOne(fetch=FetchType.LAZY)
/*  80:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  81:    */   private Asiento asiento;
/*  82:    */   @OneToOne(fetch=FetchType.LAZY, mappedBy="transferenciaBodega")
/*  83:    */   @JoinColumn(name="id_guia_remision", nullable=true)
/*  84:    */   private GuiaRemision guiaRemision;
/*  85:    */   @Column(name="indicador_saldo_inicial", nullable=true)
/*  86:    */   private boolean indicadorSaldoInicial;
/*  87:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="movimientoInventario")
/*  88:    */   @OrderBy("idDetalleMovimientoInventario")
/*  89:121 */   private List<DetalleMovimientoInventario> detalleMovimientosInventario = new ArrayList();
/*  90:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  91:    */   @JoinColumn(name="id_motivo_ajuste_inventario", nullable=true)
/*  92:    */   private MotivoAjusteInventario motivoAjusteInventario;
/*  93:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  94:    */   @JoinColumn(name="id_interfaz_contable_proceso", nullable=true)
/*  95:    */   private InterfazContableProceso interfazContableProceso;
/*  96:    */   @OneToOne(fetch=FetchType.LAZY)
/*  97:    */   @JoinColumn(name="id_movimiento_inventario_padre", nullable=true)
/*  98:    */   private MovimientoInventario movimientoInventarioPadre;
/*  99:    */   @OneToOne(fetch=FetchType.LAZY)
/* 100:    */   @JoinColumn(name="id_transferencia_ajuste", nullable=true)
/* 101:    */   private MovimientoInventario transferenciaAjuste;
/* 102:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="transferenciaAjuste")
/* 103:144 */   private List<MovimientoInventario> listaAjustesTransferencia = new ArrayList();
/* 104:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 105:    */   @JoinColumn(name="id_orden_despacho_cliente", nullable=true)
/* 106:    */   private OrdenDespachoCliente ordenDespachoCliente;
/* 107:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 108:    */   @JoinColumn(name="id_orden_fabricacion", nullable=true)
/* 109:    */   private OrdenFabricacion ordenFabricacion;
/* 110:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 111:    */   @JoinColumn(name="id_producto_terminado_transformacion", nullable=true)
/* 112:    */   private Producto productoTerminadoTransformacion;
/* 113:    */   @Column(name="cantidad_transformacion", nullable=true)
/* 114:159 */   private BigDecimal cantidadTransformacion = BigDecimal.ZERO;
/* 115:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 116:    */   @JoinColumn(name="id_lote_transformacion")
/* 117:    */   private Lote loteTransformacion;
/* 118:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 119:    */   @JoinColumn(name="id_bodega_origen", nullable=true)
/* 120:    */   private Bodega bodegaOrigen;
/* 121:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 122:    */   @JoinColumn(name="id_bodega_destino", nullable=true)
/* 123:    */   private Bodega bodegaDestino;
/* 124:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 125:    */   @JoinColumn(name="id_proyecto", nullable=true)
/* 126:    */   private DimensionContable proyecto;
/* 127:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 128:    */   @JoinColumn(name="id_responsable_salida_mercaderia", nullable=true)
/* 129:    */   private PersonaResponsable responsableSalidaMercaderia;
/* 130:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 131:    */   @JoinColumn(name="id_empresa", nullable=true)
/* 132:    */   private Empresa empresa;
/* 133:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 134:    */   @JoinColumn(name="id_direccion_empresa", nullable=true)
/* 135:    */   private DireccionEmpresa direccionEmpresa;
/* 136:    */   @Column(name="indicador_automatico", nullable=false)
/* 137:    */   private boolean indicadorAutomatico;
/* 138:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 139:    */   @JoinColumn(name="id_devolucion_cliente_transformacion", nullable=true)
/* 140:    */   private FacturaCliente devolucionClienteTransformacion;
/* 141:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 142:    */   @JoinColumn(name="id_orden_trabajo_mantenimiento", nullable=true)
/* 143:    */   private OrdenTrabajoMantenimiento ordenTrabajoMantenimiento;
/* 144:    */   @Transient
/* 145:    */   private BigDecimal total;
/* 146:    */   @Transient
/* 147:    */   private BigDecimal totalPesadas;
/* 148:    */   @Transient
/* 149:    */   private BigDecimal costoTotal;
/* 150:    */   @Transient
/* 151:    */   private AnticipoCliente traAnticipoCliente;
/* 152:    */   @Transient
/* 153:    */   private AnticipoProveedor traAnticipoProveedor;
/* 154:    */   @Transient
/* 155:215 */   private boolean indicadorGenerarCostos = true;
/* 156:    */   @Transient
/* 157:218 */   private boolean indicadorRecepcionTransferencia = false;
/* 158:    */   @Transient
/* 159:    */   private Maquina maquina;
/* 160:    */   @Transient
/* 161:224 */   private boolean indicadorTransferenciaBoom = false;
/* 162:    */   
/* 163:    */   public int getId()
/* 164:    */   {
/* 165:235 */     return this.idMovimientoInventario;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public int getIdMovimientoInventario()
/* 169:    */   {
/* 170:239 */     return this.idMovimientoInventario;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIdMovimientoInventario(int idMovimientoInventario)
/* 174:    */   {
/* 175:243 */     this.idMovimientoInventario = idMovimientoInventario;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Documento getDocumento()
/* 179:    */   {
/* 180:247 */     return this.documento;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setDocumento(Documento documento)
/* 184:    */   {
/* 185:251 */     this.documento = documento;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int getIdOrganizacion()
/* 189:    */   {
/* 190:255 */     return this.idOrganizacion;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setIdOrganizacion(int idOrganizacion)
/* 194:    */   {
/* 195:259 */     this.idOrganizacion = idOrganizacion;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Sucursal getSucursal()
/* 199:    */   {
/* 200:263 */     return this.sucursal;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setSucursal(Sucursal sucursal)
/* 204:    */   {
/* 205:267 */     this.sucursal = sucursal;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String getNumero()
/* 209:    */   {
/* 210:271 */     return this.numero;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setNumero(String numero)
/* 214:    */   {
/* 215:275 */     this.numero = numero;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Date getFecha()
/* 219:    */   {
/* 220:279 */     return this.fecha;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setFecha(Date fecha)
/* 224:    */   {
/* 225:283 */     this.fecha = fecha;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Date getFechaContabilizacion()
/* 229:    */   {
/* 230:287 */     return this.fechaContabilizacion;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 234:    */   {
/* 235:291 */     this.fechaContabilizacion = fechaContabilizacion;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Estado getEstado()
/* 239:    */   {
/* 240:295 */     return this.estado;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setEstado(Estado estado)
/* 244:    */   {
/* 245:299 */     this.estado = estado;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getDescripcion()
/* 249:    */   {
/* 250:303 */     return this.descripcion;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setDescripcion(String descripcion)
/* 254:    */   {
/* 255:307 */     this.descripcion = descripcion;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public List<DetalleMovimientoInventario> getDetalleMovimientosInventario()
/* 259:    */   {
/* 260:316 */     return this.detalleMovimientosInventario;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setDetalleMovimientosInventario(List<DetalleMovimientoInventario> detalleMovimientosInventario)
/* 264:    */   {
/* 265:326 */     this.detalleMovimientosInventario = detalleMovimientosInventario;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public String toString()
/* 269:    */   {
/* 270:336 */     return this.numero;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public TomaFisica getTomaFisica()
/* 274:    */   {
/* 275:345 */     return this.tomaFisica;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setTomaFisica(TomaFisica tomaFisica)
/* 279:    */   {
/* 280:355 */     this.tomaFisica = tomaFisica;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public AnticipoCliente getTraAnticipoCliente()
/* 284:    */   {
/* 285:359 */     return this.traAnticipoCliente;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setTraAnticipoCliente(AnticipoCliente traAnticipoCliente)
/* 289:    */   {
/* 290:363 */     this.traAnticipoCliente = traAnticipoCliente;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public boolean getIndicadorSaldoInicial()
/* 294:    */   {
/* 295:372 */     return this.indicadorSaldoInicial;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setIndicadorSaldoInicial(boolean indicadorSaldoInicial)
/* 299:    */   {
/* 300:382 */     this.indicadorSaldoInicial = indicadorSaldoInicial;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public AnticipoProveedor getTraAnticipoProveedor()
/* 304:    */   {
/* 305:391 */     return this.traAnticipoProveedor;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setTraAnticipoProveedor(AnticipoProveedor traAnticipoProveedor)
/* 309:    */   {
/* 310:401 */     this.traAnticipoProveedor = traAnticipoProveedor;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public Asiento getAsiento()
/* 314:    */   {
/* 315:405 */     return this.asiento;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setAsiento(Asiento asiento)
/* 319:    */   {
/* 320:409 */     this.asiento = asiento;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public BigDecimal getTotal()
/* 324:    */   {
/* 325:413 */     this.total = BigDecimal.ZERO;
/* 326:414 */     for (DetalleMovimientoInventario detalleMovimientoInventario : getDetalleMovimientosInventario()) {
/* 327:415 */       if (!detalleMovimientoInventario.isEliminado()) {
/* 328:416 */         this.total = this.total.add(detalleMovimientoInventario.getCantidad());
/* 329:    */       }
/* 330:    */     }
/* 331:419 */     return this.total;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public BigDecimal getTotalPesadas()
/* 335:    */   {
/* 336:423 */     this.totalPesadas = BigDecimal.ZERO;
/* 337:424 */     for (DetalleMovimientoInventario detalleMovimientoInventario : getDetalleMovimientosInventario()) {
/* 338:425 */       if (!detalleMovimientoInventario.isEliminado()) {
/* 339:426 */         this.totalPesadas = this.totalPesadas.add(detalleMovimientoInventario.getCantidadPesada());
/* 340:    */       }
/* 341:    */     }
/* 342:429 */     return this.totalPesadas;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public BigDecimal getCostoTotal()
/* 346:    */   {
/* 347:438 */     this.costoTotal = BigDecimal.ZERO;
/* 348:439 */     for (DetalleMovimientoInventario detalleMovimientoInventario : getDetalleMovimientosInventario()) {
/* 349:440 */       if (!detalleMovimientoInventario.isEliminado()) {
/* 350:441 */         this.costoTotal = this.costoTotal.add(detalleMovimientoInventario.getCostoLinea());
/* 351:    */       }
/* 352:    */     }
/* 353:444 */     return this.costoTotal;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setTotal(BigDecimal total)
/* 357:    */   {
/* 358:448 */     this.total = total;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public MotivoAjusteInventario getMotivoAjusteInventario()
/* 362:    */   {
/* 363:457 */     return this.motivoAjusteInventario;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setMotivoAjusteInventario(MotivoAjusteInventario motivoAjusteInventario)
/* 367:    */   {
/* 368:467 */     this.motivoAjusteInventario = motivoAjusteInventario;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public InterfazContableProceso getInterfazContableProceso()
/* 372:    */   {
/* 373:476 */     return this.interfazContableProceso;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 377:    */   {
/* 378:486 */     this.interfazContableProceso = interfazContableProceso;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public MovimientoInventario getMovimientoInventarioPadre()
/* 382:    */   {
/* 383:495 */     return this.movimientoInventarioPadre;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void setMovimientoInventarioPadre(MovimientoInventario movimientoInventarioPadre)
/* 387:    */   {
/* 388:505 */     this.movimientoInventarioPadre = movimientoInventarioPadre;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public Bodega getBodegaOrigen()
/* 392:    */   {
/* 393:512 */     return this.bodegaOrigen;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void setBodegaOrigen(Bodega bodegaOrigen)
/* 397:    */   {
/* 398:520 */     this.bodegaOrigen = bodegaOrigen;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public Bodega getBodegaDestino()
/* 402:    */   {
/* 403:527 */     return this.bodegaDestino;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setBodegaDestino(Bodega bodegaDestino)
/* 407:    */   {
/* 408:535 */     this.bodegaDestino = bodegaDestino;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public GuiaRemision getGuiaRemision()
/* 412:    */   {
/* 413:539 */     return this.guiaRemision;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setGuiaRemision(GuiaRemision guiaRemision)
/* 417:    */   {
/* 418:543 */     this.guiaRemision = guiaRemision;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public DimensionContable getProyecto()
/* 422:    */   {
/* 423:550 */     return this.proyecto;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setProyecto(DimensionContable proyecto)
/* 427:    */   {
/* 428:558 */     this.proyecto = proyecto;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public int compareTo(MovimientoInventario o)
/* 432:    */   {
/* 433:563 */     return getId() - o.getId();
/* 434:    */   }
/* 435:    */   
/* 436:    */   public Documento getDocumentoDestino()
/* 437:    */   {
/* 438:570 */     return this.documentoDestino;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void setDocumentoDestino(Documento documentoDestrino)
/* 442:    */   {
/* 443:578 */     this.documentoDestino = documentoDestrino;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public boolean isIndicadorGenerarCostos()
/* 447:    */   {
/* 448:585 */     return this.indicadorGenerarCostos;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public void setIndicadorGenerarCostos(boolean indicadorGenerarCostos)
/* 452:    */   {
/* 453:593 */     this.indicadorGenerarCostos = indicadorGenerarCostos;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public PersonaResponsable getResponsableSalidaMercaderia()
/* 457:    */   {
/* 458:597 */     return this.responsableSalidaMercaderia;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public void setResponsableSalidaMercaderia(PersonaResponsable responsableSalidaMercaderia)
/* 462:    */   {
/* 463:601 */     this.responsableSalidaMercaderia = responsableSalidaMercaderia;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public OrdenDespachoCliente getOrdenDespachoCliente()
/* 467:    */   {
/* 468:605 */     return this.ordenDespachoCliente;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void setOrdenDespachoCliente(OrdenDespachoCliente ordenDespachoCliente)
/* 472:    */   {
/* 473:609 */     this.ordenDespachoCliente = ordenDespachoCliente;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public OrdenFabricacion getOrdenFabricacion()
/* 477:    */   {
/* 478:613 */     return this.ordenFabricacion;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 482:    */   {
/* 483:617 */     this.ordenFabricacion = ordenFabricacion;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public Empresa getEmpresa()
/* 487:    */   {
/* 488:621 */     return this.empresa;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public void setEmpresa(Empresa empresa)
/* 492:    */   {
/* 493:625 */     this.empresa = empresa;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public DireccionEmpresa getDireccionEmpresa()
/* 497:    */   {
/* 498:629 */     return this.direccionEmpresa;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/* 502:    */   {
/* 503:633 */     this.direccionEmpresa = direccionEmpresa;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public boolean isIndicadorAutomatico()
/* 507:    */   {
/* 508:637 */     return this.indicadorAutomatico;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void setIndicadorAutomatico(boolean indicadorAutomatico)
/* 512:    */   {
/* 513:641 */     this.indicadorAutomatico = indicadorAutomatico;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public boolean isIndicadorRecepcionTransferencia()
/* 517:    */   {
/* 518:645 */     return this.indicadorRecepcionTransferencia;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public void setIndicadorRecepcionTransferencia(boolean indicadorRecepcionTransferencia)
/* 522:    */   {
/* 523:649 */     this.indicadorRecepcionTransferencia = indicadorRecepcionTransferencia;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public Producto getProductoTerminadoTransformacion()
/* 527:    */   {
/* 528:653 */     return this.productoTerminadoTransformacion;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public void setProductoTerminadoTransformacion(Producto productoTerminadoTransformacion)
/* 532:    */   {
/* 533:657 */     this.productoTerminadoTransformacion = productoTerminadoTransformacion;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public BigDecimal getCantidadTransformacion()
/* 537:    */   {
/* 538:661 */     return this.cantidadTransformacion;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public void setCantidadTransformacion(BigDecimal cantidadTransformacion)
/* 542:    */   {
/* 543:665 */     this.cantidadTransformacion = cantidadTransformacion;
/* 544:    */   }
/* 545:    */   
/* 546:    */   public Lote getLoteTransformacion()
/* 547:    */   {
/* 548:669 */     return this.loteTransformacion;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public void setLoteTransformacion(Lote loteTransformacion)
/* 552:    */   {
/* 553:673 */     this.loteTransformacion = loteTransformacion;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public FacturaCliente getDevolucionClienteTransformacion()
/* 557:    */   {
/* 558:677 */     return this.devolucionClienteTransformacion;
/* 559:    */   }
/* 560:    */   
/* 561:    */   public void setDevolucionClienteTransformacion(FacturaCliente devolucionClienteTransformacion)
/* 562:    */   {
/* 563:681 */     this.devolucionClienteTransformacion = devolucionClienteTransformacion;
/* 564:    */   }
/* 565:    */   
/* 566:    */   public MovimientoInventario getTransferenciaAjuste()
/* 567:    */   {
/* 568:685 */     return this.transferenciaAjuste;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public void setTransferenciaAjuste(MovimientoInventario transferenciaAjuste)
/* 572:    */   {
/* 573:689 */     this.transferenciaAjuste = transferenciaAjuste;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public List<MovimientoInventario> getListaAjustesTransferencia()
/* 577:    */   {
/* 578:693 */     return this.listaAjustesTransferencia;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public void setListaAjustesTransferencia(List<MovimientoInventario> listaAjustesTransferencia)
/* 582:    */   {
/* 583:697 */     this.listaAjustesTransferencia = listaAjustesTransferencia;
/* 584:    */   }
/* 585:    */   
/* 586:    */   public OrdenTrabajoMantenimiento getOrdenTrabajoMantenimiento()
/* 587:    */   {
/* 588:701 */     return this.ordenTrabajoMantenimiento;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public void setOrdenTrabajoMantenimiento(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 592:    */   {
/* 593:705 */     this.ordenTrabajoMantenimiento = ordenTrabajoMantenimiento;
/* 594:    */   }
/* 595:    */   
/* 596:    */   public Maquina getMaquina()
/* 597:    */   {
/* 598:709 */     return this.maquina;
/* 599:    */   }
/* 600:    */   
/* 601:    */   public void setMaquina(Maquina maquina)
/* 602:    */   {
/* 603:713 */     this.maquina = maquina;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public boolean isIndicadorTransferenciaBoom()
/* 607:    */   {
/* 608:717 */     return this.indicadorTransferenciaBoom;
/* 609:    */   }
/* 610:    */   
/* 611:    */   public void setIndicadorTransferenciaBoom(boolean indicadorTransferenciaBoom)
/* 612:    */   {
/* 613:721 */     this.indicadorTransferenciaBoom = indicadorTransferenciaBoom;
/* 614:    */   }
/* 615:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MovimientoInventario
 * JD-Core Version:    0.7.0.1
 */