/*    1:     */ package com.asinfo.as2.entities;
/*    2:     */ 
/*    3:     */ import java.math.BigDecimal;
/*    4:     */ import java.math.RoundingMode;
/*    5:     */ import java.util.ArrayList;
/*    6:     */ import java.util.Date;
/*    7:     */ import java.util.List;
/*    8:     */ import javax.persistence.Column;
/*    9:     */ import javax.persistence.Entity;
/*   10:     */ import javax.persistence.FetchType;
/*   11:     */ import javax.persistence.GeneratedValue;
/*   12:     */ import javax.persistence.GenerationType;
/*   13:     */ import javax.persistence.Id;
/*   14:     */ import javax.persistence.JoinColumn;
/*   15:     */ import javax.persistence.ManyToOne;
/*   16:     */ import javax.persistence.OneToMany;
/*   17:     */ import javax.persistence.OneToOne;
/*   18:     */ import javax.persistence.Table;
/*   19:     */ import javax.persistence.TableGenerator;
/*   20:     */ import javax.persistence.Temporal;
/*   21:     */ import javax.persistence.TemporalType;
/*   22:     */ import javax.persistence.Transient;
/*   23:     */ import javax.validation.constraints.Digits;
/*   24:     */ import javax.validation.constraints.Min;
/*   25:     */ import javax.validation.constraints.NotNull;
/*   26:     */ import javax.validation.constraints.Size;
/*   27:     */ import org.hibernate.annotations.ColumnDefault;
/*   28:     */ 
/*   29:     */ @Entity
/*   30:     */ @Table(name="inventario_producto", indexes={@javax.persistence.Index(columnList="id_organizacion,fecha,id_bodega,id_producto"), @javax.persistence.Index(columnList="id_detalle_despacho_cliente"), @javax.persistence.Index(columnList="id_detalle_recepcion_proveedor"), @javax.persistence.Index(columnList="id_detalle_movimiento_inventario"), @javax.persistence.Index(columnList="id_detalle_devolucion_cliente")})
/*   31:     */ public class InventarioProducto
/*   32:     */   extends EntidadBase
/*   33:     */ {
/*   34:     */   private static final long serialVersionUID = 1L;
/*   35:     */   @Id
/*   36:     */   @TableGenerator(name="inventario_producto", initialValue=0, allocationSize=50)
/*   37:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="inventario_producto")
/*   38:     */   @Column(name="id_inventario_producto")
/*   39:     */   private int idInventarioProducto;
/*   40:     */   @Column(name="id_organizacion", nullable=false)
/*   41:     */   private int idOrganizacion;
/*   42:     */   @Column(name="id_sucursal", nullable=false)
/*   43:     */   private int idSucursal;
/*   44:     */   @Temporal(TemporalType.TIME)
/*   45:     */   @Column(name="hora_creacion", nullable=true)
/*   46:     */   private Date horaCreacion;
/*   47:     */   @Temporal(TemporalType.DATE)
/*   48:     */   @Column(name="fecha", nullable=true)
/*   49:     */   private Date fecha;
/*   50:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   51:     */   @JoinColumn(name="id_documento", nullable=false)
/*   52:     */   private Documento documento;
/*   53:     */   @Column(name="numero_documento", nullable=false, length=50)
/*   54:     */   @Size(max=50)
/*   55:     */   private String numeroDocumento;
/*   56:     */   @Column(name="unidad_documento", nullable=false, length=50)
/*   57:     */   @Size(max=50)
/*   58:  93 */   private String unidadDocumento = "";
/*   59:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   60:     */   @JoinColumn(name="id_bodega", nullable=true)
/*   61:     */   private Bodega bodega;
/*   62:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   63:     */   @JoinColumn(name="id_producto", nullable=false)
/*   64:     */   private Producto producto;
/*   65:     */   @Column(name="operacion", nullable=false)
/*   66:     */   private int operacion;
/*   67:     */   @Column(name="orden", nullable=false)
/*   68:     */   private int orden;
/*   69:     */   @Column(name="indicador_genera_costo", nullable=false)
/*   70:     */   private boolean indicadorGeneraCosto;
/*   71:     */   @Column(name="indicador_transferencia", nullable=false)
/*   72:     */   private boolean indicadorTransferencia;
/*   73:     */   @Column(name="indicador_anulacion", nullable=false)
/*   74:     */   private boolean indicadorAnulacion;
/*   75:     */   @Column(name="cantidad_documento", nullable=false, precision=18, scale=6)
/*   76: 121 */   private BigDecimal cantidadDocumento = BigDecimal.ZERO;
/*   77:     */   @Column(name="cantidad", nullable=false, precision=18, scale=6)
/*   78: 124 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*   79:     */   @Column(name="peso", nullable=true, precision=12, scale=2)
/*   80:     */   @Digits(integer=12, fraction=2)
/*   81:     */   @Min(0L)
/*   82: 127 */   private BigDecimal peso = BigDecimal.ZERO;
/*   83:     */   @Column(name="costo", precision=18, scale=4)
/*   84: 132 */   private BigDecimal costo = BigDecimal.ZERO;
/*   85:     */   @NotNull
/*   86:     */   @Column(name="costo_materiales", precision=12, scale=4)
/*   87:     */   @Digits(integer=12, fraction=4)
/*   88: 135 */   private BigDecimal costoMateriales = BigDecimal.ZERO;
/*   89:     */   @NotNull
/*   90:     */   @Column(name="costo_mano_de_obra", precision=12, scale=4)
/*   91:     */   @Digits(integer=12, fraction=4)
/*   92: 140 */   private BigDecimal costoManoDeObra = BigDecimal.ZERO;
/*   93:     */   @NotNull
/*   94:     */   @Column(name="costo_depreciaciones", precision=12, scale=4)
/*   95:     */   @Digits(integer=12, fraction=4)
/*   96: 145 */   private BigDecimal costoDepreciaciones = BigDecimal.ZERO;
/*   97:     */   @NotNull
/*   98:     */   @Column(name="costo_indirectos", precision=12, scale=4)
/*   99:     */   @Digits(integer=12, fraction=4)
/*  100: 150 */   private BigDecimal costoIndirectos = BigDecimal.ZERO;
/*  101:     */   @Transient
/*  102: 155 */   private BigDecimal cantidadTotal = BigDecimal.ZERO;
/*  103:     */   @Transient
/*  104: 158 */   private BigDecimal costoTotal = BigDecimal.ZERO;
/*  105:     */   @OneToOne(fetch=FetchType.LAZY)
/*  106:     */   @JoinColumn(name="id_inventario_producto_transferencia", nullable=true)
/*  107:     */   private InventarioProducto inventarioProductoTransferencia;
/*  108:     */   @Column(name="descripcion", length=500)
/*  109:     */   @Size(max=500)
/*  110:     */   private String descripcion;
/*  111:     */   @Column(name="nombre_fiscal_empresa", nullable=true, length=200)
/*  112:     */   @Size(max=200)
/*  113:     */   @ColumnDefault("''")
/*  114:     */   private String nombreFiscalEmpresa;
/*  115:     */   @Transient
/*  116:     */   private boolean indicadorIngresoDespuesEgreso;
/*  117:     */   @OneToOne(fetch=FetchType.LAZY)
/*  118:     */   @JoinColumn(name="id_detalle_movimiento_inventario", nullable=true)
/*  119:     */   private DetalleMovimientoInventario detalleMovimientoInventario;
/*  120:     */   @OneToOne(fetch=FetchType.LAZY)
/*  121:     */   @JoinColumn(name="id_detalle_recepcion_proveedor", nullable=true)
/*  122:     */   private DetalleRecepcionProveedor detalleRecepcionProveedor;
/*  123:     */   @OneToOne(fetch=FetchType.LAZY)
/*  124:     */   @JoinColumn(name="id_detalle_despacho_cliente", nullable=true)
/*  125:     */   private DetalleDespachoCliente detalleDespachoCliente;
/*  126:     */   @OneToOne(fetch=FetchType.LAZY)
/*  127:     */   @JoinColumn(name="id_detalle_despacho_cliente_ajuste", nullable=true)
/*  128:     */   private DetalleDespachoCliente detalleDespachoClienteAjuste;
/*  129:     */   @OneToOne(fetch=FetchType.LAZY)
/*  130:     */   @JoinColumn(name="id_detalle_recepcion_proveedor_ajuste", nullable=true)
/*  131:     */   private DetalleRecepcionProveedor detalleRecepcionProveedorAjuste;
/*  132:     */   @OneToOne(fetch=FetchType.LAZY)
/*  133:     */   @JoinColumn(name="id_detalle_movimiento_inventario_ajuste", nullable=true)
/*  134:     */   private DetalleMovimientoInventario detalleMovimientoInventarioAjuste;
/*  135:     */   @OneToOne(fetch=FetchType.LAZY)
/*  136:     */   @JoinColumn(name="id_inventario_producto_origen_anulacion", nullable=true)
/*  137:     */   private InventarioProducto inventarioProductoOrigenAnulacion;
/*  138:     */   @OneToOne(fetch=FetchType.LAZY)
/*  139:     */   @JoinColumn(name="id_detalle_devolucion_cliente", nullable=true)
/*  140:     */   private DetalleFacturaCliente detalleDevolucionCliente;
/*  141:     */   @OneToOne(fetch=FetchType.LAZY)
/*  142:     */   @JoinColumn(name="id_detalle_devolucion_proveedor", nullable=true)
/*  143:     */   private DetalleFacturaProveedor detalleDevolucionProveedor;
/*  144:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  145:     */   @JoinColumn(name="id_lote")
/*  146:     */   private Lote lote;
/*  147:     */   @OneToMany(mappedBy="inventarioProducto", fetch=FetchType.LAZY)
/*  148: 232 */   private List<SerieInventarioProducto> listaSerieProducto = new ArrayList();
/*  149:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  150:     */   @JoinColumn(name="id_proyecto", nullable=true)
/*  151:     */   private DimensionContable proyecto;
/*  152:     */   @Transient
/*  153: 239 */   private BigDecimal costoUnitario = BigDecimal.ZERO;
/*  154:     */   @Transient
/*  155: 242 */   private List<InventarioProducto> listaInventarioProducto = new ArrayList();
/*  156:     */   @Column(name="cuenta1", nullable=true, length=20)
/*  157:     */   @Size(max=20)
/*  158:     */   private String cuenta1;
/*  159:     */   @Column(name="cuenta2", nullable=true, length=20)
/*  160:     */   @Size(max=20)
/*  161:     */   private String cuenta2;
/*  162:     */   @Column(name="indicador_transformacion")
/*  163: 257 */   private Boolean indicadorTransformacion = Boolean.valueOf(false);
/*  164:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  165:     */   @JoinColumn(name="id_inventario_producto_transformacion", nullable=true)
/*  166:     */   private InventarioProducto inventarioProductoTransformacion;
/*  167:     */   
/*  168:     */   public InventarioProducto() {}
/*  169:     */   
/*  170:     */   public InventarioProducto(int idInventarioProducto)
/*  171:     */   {
/*  172: 270 */     this.idInventarioProducto = idInventarioProducto;
/*  173:     */   }
/*  174:     */   
/*  175:     */   public int getId()
/*  176:     */   {
/*  177: 280 */     return this.idInventarioProducto;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public boolean isAuditable()
/*  181:     */   {
/*  182: 290 */     return false;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public int getIdInventarioProducto()
/*  186:     */   {
/*  187: 299 */     return this.idInventarioProducto;
/*  188:     */   }
/*  189:     */   
/*  190:     */   public void setIdInventarioProducto(int idInventarioProducto)
/*  191:     */   {
/*  192: 309 */     this.idInventarioProducto = idInventarioProducto;
/*  193:     */   }
/*  194:     */   
/*  195:     */   public int getIdOrganizacion()
/*  196:     */   {
/*  197: 318 */     return this.idOrganizacion;
/*  198:     */   }
/*  199:     */   
/*  200:     */   public void setIdOrganizacion(int idOrganizacion)
/*  201:     */   {
/*  202: 328 */     this.idOrganizacion = idOrganizacion;
/*  203:     */   }
/*  204:     */   
/*  205:     */   public int getIdSucursal()
/*  206:     */   {
/*  207: 337 */     return this.idSucursal;
/*  208:     */   }
/*  209:     */   
/*  210:     */   public void setIdSucursal(int idSucursal)
/*  211:     */   {
/*  212: 347 */     this.idSucursal = idSucursal;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public Date getFecha()
/*  216:     */   {
/*  217: 356 */     return this.fecha;
/*  218:     */   }
/*  219:     */   
/*  220:     */   public void setFecha(Date fecha)
/*  221:     */   {
/*  222: 366 */     this.fecha = fecha;
/*  223:     */   }
/*  224:     */   
/*  225:     */   public Documento getDocumento()
/*  226:     */   {
/*  227: 375 */     return this.documento;
/*  228:     */   }
/*  229:     */   
/*  230:     */   public void setDocumento(Documento documento)
/*  231:     */   {
/*  232: 385 */     this.documento = documento;
/*  233:     */   }
/*  234:     */   
/*  235:     */   public String getNumeroDocumento()
/*  236:     */   {
/*  237: 394 */     return this.numeroDocumento;
/*  238:     */   }
/*  239:     */   
/*  240:     */   public void setNumeroDocumento(String numeroDocumento)
/*  241:     */   {
/*  242: 404 */     this.numeroDocumento = numeroDocumento;
/*  243:     */   }
/*  244:     */   
/*  245:     */   public Bodega getBodega()
/*  246:     */   {
/*  247: 413 */     return this.bodega;
/*  248:     */   }
/*  249:     */   
/*  250:     */   public void setBodega(Bodega bodega)
/*  251:     */   {
/*  252: 423 */     this.bodega = bodega;
/*  253:     */   }
/*  254:     */   
/*  255:     */   public Producto getProducto()
/*  256:     */   {
/*  257: 432 */     return this.producto;
/*  258:     */   }
/*  259:     */   
/*  260:     */   public void setProducto(Producto producto)
/*  261:     */   {
/*  262: 442 */     this.producto = producto;
/*  263:     */   }
/*  264:     */   
/*  265:     */   public int getOperacion()
/*  266:     */   {
/*  267: 451 */     return this.operacion;
/*  268:     */   }
/*  269:     */   
/*  270:     */   public void setOperacion(int operacion)
/*  271:     */   {
/*  272: 461 */     this.operacion = operacion;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public boolean isIndicadorGeneraCosto()
/*  276:     */   {
/*  277: 470 */     return this.indicadorGeneraCosto;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public void setIndicadorGeneraCosto(boolean indicadorGeneraCosto)
/*  281:     */   {
/*  282: 480 */     this.indicadorGeneraCosto = indicadorGeneraCosto;
/*  283:     */   }
/*  284:     */   
/*  285:     */   public BigDecimal getCantidad()
/*  286:     */   {
/*  287: 489 */     return this.cantidad;
/*  288:     */   }
/*  289:     */   
/*  290:     */   public void setCantidad(BigDecimal cantidad)
/*  291:     */   {
/*  292: 499 */     this.cantidad = cantidad;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public BigDecimal getCosto()
/*  296:     */   {
/*  297: 508 */     return this.costo;
/*  298:     */   }
/*  299:     */   
/*  300:     */   public void setCosto(BigDecimal costo)
/*  301:     */   {
/*  302: 518 */     this.costo = costo;
/*  303:     */   }
/*  304:     */   
/*  305:     */   public BigDecimal getCantidadTotal()
/*  306:     */   {
/*  307: 527 */     return this.cantidadTotal;
/*  308:     */   }
/*  309:     */   
/*  310:     */   public void setCantidadTotal(BigDecimal cantidadTotal)
/*  311:     */   {
/*  312: 537 */     this.cantidadTotal = cantidadTotal;
/*  313:     */   }
/*  314:     */   
/*  315:     */   public BigDecimal getCostoTotal()
/*  316:     */   {
/*  317: 546 */     return this.costoTotal;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public void setCostoTotal(BigDecimal costoTotal)
/*  321:     */   {
/*  322: 556 */     this.costoTotal = costoTotal;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public DetalleMovimientoInventario getDetalleMovimientoInventario()
/*  326:     */   {
/*  327: 565 */     return this.detalleMovimientoInventario;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public void setDetalleMovimientoInventario(DetalleMovimientoInventario detalleMovimientoInventario)
/*  331:     */   {
/*  332: 575 */     this.detalleMovimientoInventario = detalleMovimientoInventario;
/*  333:     */   }
/*  334:     */   
/*  335:     */   public DetalleRecepcionProveedor getDetalleRecepcionProveedor()
/*  336:     */   {
/*  337: 584 */     return this.detalleRecepcionProveedor;
/*  338:     */   }
/*  339:     */   
/*  340:     */   public void setDetalleRecepcionProveedor(DetalleRecepcionProveedor detalleRecepcionProveedor)
/*  341:     */   {
/*  342: 594 */     this.detalleRecepcionProveedor = detalleRecepcionProveedor;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public int getOrden()
/*  346:     */   {
/*  347: 603 */     return this.orden;
/*  348:     */   }
/*  349:     */   
/*  350:     */   public void setOrden(int orden)
/*  351:     */   {
/*  352: 613 */     this.orden = orden;
/*  353:     */   }
/*  354:     */   
/*  355:     */   public BigDecimal getCostoUnitario()
/*  356:     */   {
/*  357: 622 */     this.costoUnitario = BigDecimal.ZERO;
/*  358: 623 */     if (this.cantidad.compareTo(BigDecimal.ZERO) != 0) {
/*  359: 624 */       this.costoUnitario = this.costo.divide(this.cantidad, 4, RoundingMode.HALF_UP);
/*  360:     */     }
/*  361: 626 */     return this.costoUnitario;
/*  362:     */   }
/*  363:     */   
/*  364:     */   public void setCostoUnitario(BigDecimal costoUnitario)
/*  365:     */   {
/*  366: 636 */     this.costoUnitario = costoUnitario;
/*  367:     */   }
/*  368:     */   
/*  369:     */   public InventarioProducto getInventarioProductoTransferencia()
/*  370:     */   {
/*  371: 645 */     return this.inventarioProductoTransferencia;
/*  372:     */   }
/*  373:     */   
/*  374:     */   public void setInventarioProductoTransferencia(InventarioProducto inventarioProductoPadre)
/*  375:     */   {
/*  376: 655 */     this.inventarioProductoTransferencia = inventarioProductoPadre;
/*  377:     */   }
/*  378:     */   
/*  379:     */   public boolean isIndicadorTransferencia()
/*  380:     */   {
/*  381: 664 */     return this.indicadorTransferencia;
/*  382:     */   }
/*  383:     */   
/*  384:     */   public void setIndicadorTransferencia(boolean indicadorTransferencia)
/*  385:     */   {
/*  386: 674 */     this.indicadorTransferencia = indicadorTransferencia;
/*  387:     */   }
/*  388:     */   
/*  389:     */   public String getUnidadDocumento()
/*  390:     */   {
/*  391: 683 */     return this.unidadDocumento;
/*  392:     */   }
/*  393:     */   
/*  394:     */   public void setUnidadDocumento(String unidadDocumento)
/*  395:     */   {
/*  396: 693 */     this.unidadDocumento = unidadDocumento;
/*  397:     */   }
/*  398:     */   
/*  399:     */   public BigDecimal getCantidadDocumento()
/*  400:     */   {
/*  401: 702 */     return this.cantidadDocumento;
/*  402:     */   }
/*  403:     */   
/*  404:     */   public void setCantidadDocumento(BigDecimal cantidadDocumento)
/*  405:     */   {
/*  406: 712 */     this.cantidadDocumento = cantidadDocumento;
/*  407:     */   }
/*  408:     */   
/*  409:     */   public DetalleDespachoCliente getDetalleDespachoCliente()
/*  410:     */   {
/*  411: 719 */     return this.detalleDespachoCliente;
/*  412:     */   }
/*  413:     */   
/*  414:     */   public void setDetalleDespachoCliente(DetalleDespachoCliente detalleDespachoCliente)
/*  415:     */   {
/*  416: 727 */     this.detalleDespachoCliente = detalleDespachoCliente;
/*  417:     */   }
/*  418:     */   
/*  419:     */   public DetalleFacturaCliente getDetalleDevolucionCliente()
/*  420:     */   {
/*  421: 736 */     return this.detalleDevolucionCliente;
/*  422:     */   }
/*  423:     */   
/*  424:     */   public void setDetalleDevolucionCliente(DetalleFacturaCliente detalleDevolucionCliente)
/*  425:     */   {
/*  426: 746 */     this.detalleDevolucionCliente = detalleDevolucionCliente;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public DetalleFacturaProveedor getDetalleDevolucionProveedor()
/*  430:     */   {
/*  431: 755 */     return this.detalleDevolucionProveedor;
/*  432:     */   }
/*  433:     */   
/*  434:     */   public void setDetalleDevolucionProveedor(DetalleFacturaProveedor detalleDevolucionProveedor)
/*  435:     */   {
/*  436: 765 */     this.detalleDevolucionProveedor = detalleDevolucionProveedor;
/*  437:     */   }
/*  438:     */   
/*  439:     */   public Lote getLote()
/*  440:     */   {
/*  441: 774 */     return this.lote;
/*  442:     */   }
/*  443:     */   
/*  444:     */   public void setLote(Lote lote)
/*  445:     */   {
/*  446: 784 */     this.lote = lote;
/*  447:     */   }
/*  448:     */   
/*  449:     */   public DetalleDespachoCliente getDetalleDespachoClienteAjuste()
/*  450:     */   {
/*  451: 793 */     return this.detalleDespachoClienteAjuste;
/*  452:     */   }
/*  453:     */   
/*  454:     */   public void setDetalleDespachoClienteAjuste(DetalleDespachoCliente detalleDespachoClienteAjuste)
/*  455:     */   {
/*  456: 803 */     this.detalleDespachoClienteAjuste = detalleDespachoClienteAjuste;
/*  457:     */   }
/*  458:     */   
/*  459:     */   public DetalleRecepcionProveedor getDetalleRecepcionProveedorAjuste()
/*  460:     */   {
/*  461: 807 */     return this.detalleRecepcionProveedorAjuste;
/*  462:     */   }
/*  463:     */   
/*  464:     */   public void setDetalleRecepcionProveedorAjuste(DetalleRecepcionProveedor detalleRecepcionProveedorAjuste)
/*  465:     */   {
/*  466: 811 */     this.detalleRecepcionProveedorAjuste = detalleRecepcionProveedorAjuste;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public List<InventarioProducto> getListaInventarioProducto()
/*  470:     */   {
/*  471: 815 */     return this.listaInventarioProducto;
/*  472:     */   }
/*  473:     */   
/*  474:     */   public void setListaInventarioProducto(List<InventarioProducto> listaInventarioProducto)
/*  475:     */   {
/*  476: 819 */     this.listaInventarioProducto = listaInventarioProducto;
/*  477:     */   }
/*  478:     */   
/*  479:     */   public DetalleMovimientoInventario getDetalleMovimientoInventarioAjuste()
/*  480:     */   {
/*  481: 828 */     return this.detalleMovimientoInventarioAjuste;
/*  482:     */   }
/*  483:     */   
/*  484:     */   public void setDetalleMovimientoInventarioAjuste(DetalleMovimientoInventario detalleMovimientoInventarioAjuste)
/*  485:     */   {
/*  486: 838 */     this.detalleMovimientoInventarioAjuste = detalleMovimientoInventarioAjuste;
/*  487:     */   }
/*  488:     */   
/*  489:     */   public boolean isIndicadorAnulacion()
/*  490:     */   {
/*  491: 847 */     return this.indicadorAnulacion;
/*  492:     */   }
/*  493:     */   
/*  494:     */   public void setIndicadorAnulacion(boolean indicadorAnulacion)
/*  495:     */   {
/*  496: 857 */     this.indicadorAnulacion = indicadorAnulacion;
/*  497:     */   }
/*  498:     */   
/*  499:     */   public InventarioProducto getInventarioProductoOrigenAnulacion()
/*  500:     */   {
/*  501: 866 */     return this.inventarioProductoOrigenAnulacion;
/*  502:     */   }
/*  503:     */   
/*  504:     */   public void setInventarioProductoOrigenAnulacion(InventarioProducto inventarioProductoOrigenAnulacion)
/*  505:     */   {
/*  506: 876 */     this.inventarioProductoOrigenAnulacion = inventarioProductoOrigenAnulacion;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public BigDecimal getCostoMateriales()
/*  510:     */   {
/*  511: 880 */     return this.costoMateriales;
/*  512:     */   }
/*  513:     */   
/*  514:     */   public void setCostoMateriales(BigDecimal costoMateriales)
/*  515:     */   {
/*  516: 884 */     this.costoMateriales = costoMateriales;
/*  517:     */   }
/*  518:     */   
/*  519:     */   public BigDecimal getCostoManoDeObra()
/*  520:     */   {
/*  521: 888 */     return this.costoManoDeObra;
/*  522:     */   }
/*  523:     */   
/*  524:     */   public void setCostoManoDeObra(BigDecimal costoManoDeObra)
/*  525:     */   {
/*  526: 892 */     this.costoManoDeObra = costoManoDeObra;
/*  527:     */   }
/*  528:     */   
/*  529:     */   public BigDecimal getCostoDepreciaciones()
/*  530:     */   {
/*  531: 896 */     return this.costoDepreciaciones;
/*  532:     */   }
/*  533:     */   
/*  534:     */   public void setCostoDepreciaciones(BigDecimal costoDepreciaciones)
/*  535:     */   {
/*  536: 900 */     this.costoDepreciaciones = costoDepreciaciones;
/*  537:     */   }
/*  538:     */   
/*  539:     */   public BigDecimal getCostoIndirectos()
/*  540:     */   {
/*  541: 904 */     return this.costoIndirectos;
/*  542:     */   }
/*  543:     */   
/*  544:     */   public void setCostoIndirectos(BigDecimal costoIndirectos)
/*  545:     */   {
/*  546: 908 */     this.costoIndirectos = costoIndirectos;
/*  547:     */   }
/*  548:     */   
/*  549:     */   public Date getHoraCreacion()
/*  550:     */   {
/*  551: 912 */     return this.horaCreacion;
/*  552:     */   }
/*  553:     */   
/*  554:     */   public void setHoraCreacion(Date horaCreacion)
/*  555:     */   {
/*  556: 916 */     this.horaCreacion = horaCreacion;
/*  557:     */   }
/*  558:     */   
/*  559:     */   public BigDecimal getPeso()
/*  560:     */   {
/*  561: 920 */     return this.peso;
/*  562:     */   }
/*  563:     */   
/*  564:     */   public void setPeso(BigDecimal peso)
/*  565:     */   {
/*  566: 924 */     this.peso = peso;
/*  567:     */   }
/*  568:     */   
/*  569:     */   public List<SerieInventarioProducto> getListaSerieProducto()
/*  570:     */   {
/*  571: 928 */     return this.listaSerieProducto;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public void setListaSerieProducto(List<SerieInventarioProducto> listaSerieProducto)
/*  575:     */   {
/*  576: 932 */     this.listaSerieProducto = listaSerieProducto;
/*  577:     */   }
/*  578:     */   
/*  579:     */   public void setEliminado(boolean eliminado)
/*  580:     */   {
/*  581: 937 */     this.eliminado = eliminado;
/*  582: 938 */     if ((this.producto != null) && (this.producto.getIndicadorSerie().booleanValue())) {
/*  583: 939 */       for (SerieInventarioProducto serie : this.listaSerieProducto) {
/*  584: 940 */         serie.setEliminado(eliminado);
/*  585:     */       }
/*  586:     */     }
/*  587:     */   }
/*  588:     */   
/*  589:     */   public String getCuenta1()
/*  590:     */   {
/*  591: 946 */     return this.cuenta1;
/*  592:     */   }
/*  593:     */   
/*  594:     */   public void setCuenta1(String cuenta1)
/*  595:     */   {
/*  596: 950 */     this.cuenta1 = cuenta1;
/*  597:     */   }
/*  598:     */   
/*  599:     */   public String getCuenta2()
/*  600:     */   {
/*  601: 954 */     return this.cuenta2;
/*  602:     */   }
/*  603:     */   
/*  604:     */   public void setCuenta2(String cuenta2)
/*  605:     */   {
/*  606: 958 */     this.cuenta2 = cuenta2;
/*  607:     */   }
/*  608:     */   
/*  609:     */   public DimensionContable getProyecto()
/*  610:     */   {
/*  611: 972 */     return this.proyecto;
/*  612:     */   }
/*  613:     */   
/*  614:     */   public void setProyecto(DimensionContable proyecto)
/*  615:     */   {
/*  616: 980 */     this.proyecto = proyecto;
/*  617:     */   }
/*  618:     */   
/*  619:     */   public Boolean isIndicadorTransformacion()
/*  620:     */   {
/*  621: 987 */     return Boolean.valueOf(this.indicadorTransformacion == null ? false : this.indicadorTransformacion.booleanValue());
/*  622:     */   }
/*  623:     */   
/*  624:     */   public void setIndicadorTransformacion(Boolean indicadorTransformacion)
/*  625:     */   {
/*  626: 995 */     this.indicadorTransformacion = indicadorTransformacion;
/*  627:     */   }
/*  628:     */   
/*  629:     */   public InventarioProducto getInventarioProductoTransformacion()
/*  630:     */   {
/*  631:1002 */     return this.inventarioProductoTransformacion;
/*  632:     */   }
/*  633:     */   
/*  634:     */   public void setInventarioProductoTransformacion(InventarioProducto inventarioProductoTransformacion)
/*  635:     */   {
/*  636:1010 */     this.inventarioProductoTransformacion = inventarioProductoTransformacion;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public String getDescripcion()
/*  640:     */   {
/*  641:1014 */     return this.descripcion;
/*  642:     */   }
/*  643:     */   
/*  644:     */   public void setDescripcion(String descripcion)
/*  645:     */   {
/*  646:1018 */     this.descripcion = descripcion;
/*  647:     */   }
/*  648:     */   
/*  649:     */   public String getNombreFiscalEmpresa()
/*  650:     */   {
/*  651:1022 */     return this.nombreFiscalEmpresa;
/*  652:     */   }
/*  653:     */   
/*  654:     */   public void setNombreFiscalEmpresa(String nombreFiscalEmpresa)
/*  655:     */   {
/*  656:1026 */     this.nombreFiscalEmpresa = nombreFiscalEmpresa;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public boolean isIndicadorIngresoDespuesEgreso()
/*  660:     */   {
/*  661:1030 */     return this.indicadorIngresoDespuesEgreso;
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void setIndicadorIngresoDespuesEgreso(boolean indicadorIngresoDespuesEgreso)
/*  665:     */   {
/*  666:1034 */     this.indicadorIngresoDespuesEgreso = indicadorIngresoDespuesEgreso;
/*  667:     */   }
/*  668:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.InventarioProducto
 * JD-Core Version:    0.7.0.1
 */