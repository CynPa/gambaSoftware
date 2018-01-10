/*    1:     */ package com.asinfo.as2.entities;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.enumeraciones.TipoProrrateoEnum;
/*    4:     */ import com.asinfo.as2.util.AppUtil;
/*    5:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*    6:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*    7:     */ import java.io.Serializable;
/*    8:     */ import java.math.BigDecimal;
/*    9:     */ import java.math.RoundingMode;
/*   10:     */ import java.util.ArrayList;
/*   11:     */ import java.util.List;
/*   12:     */ import javax.persistence.Column;
/*   13:     */ import javax.persistence.Entity;
/*   14:     */ import javax.persistence.EnumType;
/*   15:     */ import javax.persistence.Enumerated;
/*   16:     */ import javax.persistence.FetchType;
/*   17:     */ import javax.persistence.GeneratedValue;
/*   18:     */ import javax.persistence.GenerationType;
/*   19:     */ import javax.persistence.Id;
/*   20:     */ import javax.persistence.JoinColumn;
/*   21:     */ import javax.persistence.ManyToOne;
/*   22:     */ import javax.persistence.OneToMany;
/*   23:     */ import javax.persistence.OneToOne;
/*   24:     */ import javax.persistence.Table;
/*   25:     */ import javax.persistence.TableGenerator;
/*   26:     */ import javax.persistence.Transient;
/*   27:     */ import javax.validation.constraints.DecimalMin;
/*   28:     */ import javax.validation.constraints.Digits;
/*   29:     */ import javax.validation.constraints.Max;
/*   30:     */ import javax.validation.constraints.Min;
/*   31:     */ import javax.validation.constraints.NotNull;
/*   32:     */ import javax.validation.constraints.Size;
/*   33:     */ import org.hibernate.annotations.ColumnDefault;
/*   34:     */ 
/*   35:     */ @Entity
/*   36:     */ @Table(name="detalle_factura_proveedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_detalle_factura_proveedor"})})
/*   37:     */ public class DetalleFacturaProveedor
/*   38:     */   extends EntidadBase
/*   39:     */   implements Serializable, Cloneable
/*   40:     */ {
/*   41:     */   private static final long serialVersionUID = -1847869211434882233L;
/*   42:     */   @Id
/*   43:     */   @TableGenerator(name="detalle_factura_proveedor", initialValue=0, allocationSize=50)
/*   44:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_factura_proveedor")
/*   45:     */   @Column(name="id_detalle_factura_proveedor")
/*   46:     */   private int idDetalleFacturaProveedor;
/*   47:     */   @Column(name="id_sucursal", nullable=false)
/*   48:     */   @NotNull
/*   49:     */   private int idSucursal;
/*   50:     */   @Column(name="id_organizacion", nullable=false)
/*   51:     */   @NotNull
/*   52:     */   private int idOrganizacion;
/*   53:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   54:     */   @JoinColumn(name="id_factura_proveedor", nullable=true)
/*   55:     */   private FacturaProveedor facturaProveedor;
/*   56:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   57:     */   @JoinColumn(name="id_producto", nullable=false)
/*   58:     */   @NotNull
/*   59:     */   private Producto producto;
/*   60:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   61:     */   @JoinColumn(name="id_unidad_compra", nullable=false)
/*   62:     */   @NotNull
/*   63:     */   private Unidad unidadCompra;
/*   64:     */   @NotNull
/*   65:     */   @Column(name="cantidad", nullable=false, precision=12, scale=4)
/*   66:     */   @Digits(integer=12, fraction=4)
/*   67:     */   @DecimalMin("0.0001")
/*   68:  82 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*   69:     */   @Column(name="precio", nullable=false, precision=12, scale=6)
/*   70:     */   @NotNull
/*   71:     */   @Digits(integer=12, fraction=6)
/*   72:     */   @Min(0L)
/*   73:  88 */   private BigDecimal precio = BigDecimal.ZERO;
/*   74:     */   @Column(name="valor_impuestos_linea", nullable=false, precision=12, scale=6)
/*   75:     */   @NotNull
/*   76:     */   @Digits(integer=12, fraction=6)
/*   77:     */   @Min(0L)
/*   78:  94 */   BigDecimal valorImpuestosLinea = BigDecimal.ZERO;
/*   79:     */   @Column(name="valor_descuento_impuestos_linea", nullable=false, precision=12, scale=6)
/*   80:     */   @NotNull
/*   81:     */   @Digits(integer=12, fraction=6)
/*   82:     */   @Min(0L)
/*   83: 100 */   BigDecimal valorDescuentoImpuestosLinea = BigDecimal.ZERO;
/*   84:     */   @Column(name="descripcion", nullable=true, length=200)
/*   85:     */   @Size(max=200)
/*   86:     */   private String descripcion;
/*   87:     */   @NotNull
/*   88:     */   @Column(name="descuento", nullable=false, precision=12, scale=4)
/*   89:     */   @Digits(integer=12, fraction=4)
/*   90:     */   @Min(0L)
/*   91: 110 */   private BigDecimal descuento = BigDecimal.ZERO;
/*   92:     */   @NotNull
/*   93:     */   @Column(name="porcentaje_descuento", nullable=false, precision=5, scale=2)
/*   94:     */   @Digits(integer=3, fraction=2)
/*   95:     */   @Min(0L)
/*   96:     */   @Max(100L)
/*   97: 116 */   private BigDecimal porcentajeDescuento = BigDecimal.ZERO;
/*   98:     */   @Column(name="indicador_impuestos", nullable=false)
/*   99:     */   @NotNull
/*  100:     */   private boolean indicadorImpuestos;
/*  101:     */   @Column(name="indicador_impuesto_ice", nullable=false)
/*  102:     */   @NotNull
/*  103:     */   private boolean indicadorImpuestoIce;
/*  104:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleFacturaProveedor")
/*  105: 131 */   private List<ImpuestoProductoFacturaProveedor> listaImpuestoProductoFacturaProveedor = new ArrayList();
/*  106:     */   @OneToMany(mappedBy="detalleFacturaProveedor", fetch=FetchType.LAZY)
/*  107: 134 */   private List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor = new ArrayList();
/*  108:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  109:     */   @JoinColumn(name="id_detalle_pedido_proveedor", nullable=true)
/*  110:     */   private DetallePedidoProveedor detallePedidoProveedor;
/*  111:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  112:     */   @JoinColumn(name="id_detalle_factura_proveedor_padre", nullable=true)
/*  113:     */   private DetalleFacturaProveedor detalleFacturaProveedorPadre;
/*  114:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleFacturaProveedor")
/*  115: 145 */   private List<GastoProductoFacturaProveedor> listaGastoProductoFacturaProveedor = new ArrayList();
/*  116:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleFacturaProveedor")
/*  117: 148 */   private List<DetalleFacturaProveedorImportacion> listaDetalleFacturaProveedorImportacion = new ArrayList();
/*  118:     */   @OneToOne(mappedBy="detalleDevolucionProveedor", fetch=FetchType.LAZY)
/*  119:     */   private InventarioProducto inventarioProducto;
/*  120:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  121:     */   @JoinColumn(name="id_empresa", nullable=true)
/*  122:     */   private Bodega bodega;
/*  123:     */   @OneToOne(fetch=FetchType.LAZY)
/*  124:     */   @JoinColumn(name="id_detalle_recepcion_proveedor_devolucion", nullable=true)
/*  125:     */   private DetalleRecepcionProveedor detalleRecepcionProveedorDevolucion;
/*  126:     */   @Transient
/*  127:     */   private BigDecimal traSaldoDevolver;
/*  128:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  129:     */   @JoinColumn(name="id_gasto_importacion", nullable=true)
/*  130:     */   private GastoImportacion gastoImportacion;
/*  131:     */   @Column(name="tipo_prorrateo", nullable=true)
/*  132:     */   @Enumerated(EnumType.ORDINAL)
/*  133:     */   private TipoProrrateoEnum tipoProrrateoEnum;
/*  134:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  135:     */   @JoinColumn(name="id_partida_arancelaria", nullable=true)
/*  136:     */   private PartidaArancelaria partidaArancelaria;
/*  137:     */   @Column(name="gasto_real", nullable=true, precision=12, scale=6)
/*  138:     */   @Digits(integer=12, fraction=6)
/*  139:     */   @Min(0L)
/*  140: 179 */   private BigDecimal gastoReal = BigDecimal.ZERO;
/*  141:     */   @Column(name="gasto_presupuesto", nullable=true, precision=12, scale=6)
/*  142:     */   @Digits(integer=12, fraction=6)
/*  143:     */   @Min(0L)
/*  144: 184 */   private BigDecimal gastoPresupuesto = BigDecimal.ZERO;
/*  145:     */   @Column(name="peso_neto", nullable=true, precision=12, scale=2)
/*  146:     */   @Digits(integer=12, fraction=2)
/*  147:     */   @Min(0L)
/*  148: 189 */   private BigDecimal pesoNeto = BigDecimal.ZERO;
/*  149:     */   @Column(name="cantidad_unidad_informativa", nullable=false, precision=12, scale=2)
/*  150:     */   @Digits(integer=12, fraction=2)
/*  151:     */   @ColumnDefault("0")
/*  152:     */   @Min(0L)
/*  153: 194 */   private BigDecimal cantidadUnidadInformativa = BigDecimal.ZERO;
/*  154:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleFacturaProveedor")
/*  155: 200 */   private List<DetalleFacturaProveedorImportacionProducto> listaDetalleFacturaProveedorImportacionProducto = new ArrayList();
/*  156:     */   @Column(name="indicador_gasto_importacion", nullable=false)
/*  157:     */   private boolean indicadorGastoImportacion;
/*  158:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleFacturaProveedor")
/*  159: 208 */   private List<RegistroPeso> listaRegistroPesoLiquidados = new ArrayList();
/*  160:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  161:     */   @JoinColumn(name="id_transformacion_automatica", nullable=true)
/*  162:     */   private MovimientoInventario transformacionAutomatica;
/*  163:     */   @Transient
/*  164: 215 */   private BigDecimal precioLinea = BigDecimal.ZERO;
/*  165:     */   @Transient
/*  166: 218 */   private BigDecimal traValorGastoImportacion = BigDecimal.ZERO;
/*  167:     */   @Transient
/*  168: 221 */   private BigDecimal traCantidadRecibida = BigDecimal.ZERO;
/*  169:     */   @Transient
/*  170: 224 */   private BigDecimal cantidadADevolver = BigDecimal.ZERO;
/*  171:     */   @Transient
/*  172: 230 */   private BigDecimal traValorImpuestosLineaTotal = BigDecimal.ZERO;
/*  173:     */   @Transient
/*  174: 236 */   private Lote lote = new Lote();
/*  175:     */   
/*  176:     */   public DetalleFacturaProveedor() {}
/*  177:     */   
/*  178:     */   public DetalleFacturaProveedor(int idDetalleFacturaProveedor, Producto producto, BigDecimal cantidad, BigDecimal precio, BigDecimal traSaldoDevolver, BigDecimal descuento)
/*  179:     */   {
/*  180: 257 */     this.idDetalleFacturaProveedor = idDetalleFacturaProveedor;
/*  181: 258 */     this.producto = producto;
/*  182: 259 */     this.cantidad = cantidad;
/*  183: 260 */     this.precio = precio;
/*  184: 261 */     this.traSaldoDevolver = traSaldoDevolver;
/*  185: 262 */     this.descuento = descuento;
/*  186:     */   }
/*  187:     */   
/*  188:     */   public int getId()
/*  189:     */   {
/*  190: 272 */     return this.idDetalleFacturaProveedor;
/*  191:     */   }
/*  192:     */   
/*  193:     */   public void setId(int idDetalleFacturaProveedor)
/*  194:     */   {
/*  195: 277 */     this.idDetalleFacturaProveedor = idDetalleFacturaProveedor;
/*  196:     */   }
/*  197:     */   
/*  198:     */   public List<String> getCamposAuditables()
/*  199:     */   {
/*  200: 287 */     ArrayList<String> lista = new ArrayList();
/*  201: 288 */     lista.add("producto");
/*  202: 289 */     lista.add("cantidad");
/*  203: 290 */     lista.add("precio");
/*  204: 291 */     lista.add("descuento");
/*  205: 292 */     lista.add("indicadorImpuestos");
/*  206:     */     
/*  207: 294 */     return lista;
/*  208:     */   }
/*  209:     */   
/*  210:     */   public BigDecimal getBaseImponible()
/*  211:     */   {
/*  212: 307 */     BigDecimal baseImponibleLinea = getPrecioLinea().subtract(getDescuentoLinea());
/*  213: 309 */     if (getProducto().isTraIndicadorServicio()) {
/*  214: 310 */       baseImponibleLinea = FuncionesUtiles.redondearBigDecimal(baseImponibleLinea, 2);
/*  215:     */     }
/*  216: 313 */     return baseImponibleLinea;
/*  217:     */   }
/*  218:     */   
/*  219:     */   public BigDecimal getPrecioLinea()
/*  220:     */   {
/*  221: 319 */     return FuncionesUtiles.redondearBigDecimal(getPrecio().multiply(getCantidad()), 2);
/*  222:     */   }
/*  223:     */   
/*  224:     */   public void setPrecioLinea(BigDecimal precioLinea)
/*  225:     */   {
/*  226: 331 */     if (getCantidad().compareTo(BigDecimal.ZERO) != 0) {
/*  227: 332 */       this.precio = precioLinea.divide(getCantidad(), ParametrosSistema.getNumeroDecimalesPrecioCompra().intValue(), RoundingMode.HALF_UP);
/*  228:     */     }
/*  229: 334 */     this.precioLinea = precioLinea;
/*  230:     */   }
/*  231:     */   
/*  232:     */   public BigDecimal getValorImpuestosLinea()
/*  233:     */   {
/*  234: 345 */     return this.valorImpuestosLinea;
/*  235:     */   }
/*  236:     */   
/*  237:     */   public void setValorImpuestosLinea(BigDecimal valorImpuestosLinea)
/*  238:     */   {
/*  239: 355 */     this.valorImpuestosLinea = valorImpuestosLinea;
/*  240:     */   }
/*  241:     */   
/*  242:     */   public int getIdDetalleFacturaProveedor()
/*  243:     */   {
/*  244: 364 */     return this.idDetalleFacturaProveedor;
/*  245:     */   }
/*  246:     */   
/*  247:     */   public void setIdDetalleFacturaProveedor(int idDetalleFacturaProveedor)
/*  248:     */   {
/*  249: 374 */     this.idDetalleFacturaProveedor = idDetalleFacturaProveedor;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public int getIdSucursal()
/*  253:     */   {
/*  254: 383 */     return this.idSucursal;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public void setIdSucursal(int idSucursal)
/*  258:     */   {
/*  259: 393 */     this.idSucursal = idSucursal;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public int getIdOrganizacion()
/*  263:     */   {
/*  264: 402 */     return this.idOrganizacion;
/*  265:     */   }
/*  266:     */   
/*  267:     */   public void setIdOrganizacion(int idOrganizacion)
/*  268:     */   {
/*  269: 412 */     this.idOrganizacion = idOrganizacion;
/*  270:     */   }
/*  271:     */   
/*  272:     */   public FacturaProveedor getFacturaProveedor()
/*  273:     */   {
/*  274: 421 */     return this.facturaProveedor;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/*  278:     */   {
/*  279: 431 */     this.facturaProveedor = facturaProveedor;
/*  280:     */   }
/*  281:     */   
/*  282:     */   public Producto getProducto()
/*  283:     */   {
/*  284: 440 */     return this.producto;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public void setProducto(Producto producto)
/*  288:     */   {
/*  289: 450 */     this.producto = producto;
/*  290:     */   }
/*  291:     */   
/*  292:     */   public DetallePedidoProveedor getDetallePedidoProveedor()
/*  293:     */   {
/*  294: 459 */     return this.detallePedidoProveedor;
/*  295:     */   }
/*  296:     */   
/*  297:     */   public void setDetallePedidoProveedor(DetallePedidoProveedor detallePedidoProveedor)
/*  298:     */   {
/*  299: 469 */     this.detallePedidoProveedor = detallePedidoProveedor;
/*  300:     */   }
/*  301:     */   
/*  302:     */   public BigDecimal getCantidad()
/*  303:     */   {
/*  304: 478 */     return this.cantidad;
/*  305:     */   }
/*  306:     */   
/*  307:     */   public void setCantidad(BigDecimal cantidad)
/*  308:     */   {
/*  309: 488 */     this.cantidad = cantidad;
/*  310:     */   }
/*  311:     */   
/*  312:     */   public BigDecimal getPrecio()
/*  313:     */   {
/*  314: 497 */     if (AppUtil.getOrganizacion() != null) {
/*  315: 498 */       return FuncionesUtiles.redondearBigDecimal(this.precio, ParametrosSistema.getNumeroDecimalesPrecioCompra().intValue());
/*  316:     */     }
/*  317: 500 */     return this.precio;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public void setPrecio(BigDecimal precio)
/*  321:     */   {
/*  322: 511 */     this.precio = precio;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public String getDescripcion()
/*  326:     */   {
/*  327: 520 */     return this.descripcion;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public void setDescripcion(String descripcion)
/*  331:     */   {
/*  332: 530 */     this.descripcion = descripcion;
/*  333:     */   }
/*  334:     */   
/*  335:     */   public BigDecimal getDescuento()
/*  336:     */   {
/*  337: 539 */     this.descuento = (this.descuento.compareTo(this.precio) > 0 ? this.precio : this.descuento);
/*  338: 540 */     return this.descuento;
/*  339:     */   }
/*  340:     */   
/*  341:     */   public void setDescuento(BigDecimal descuento)
/*  342:     */   {
/*  343: 550 */     this.descuento = descuento;
/*  344:     */   }
/*  345:     */   
/*  346:     */   public List<ImpuestoProductoFacturaProveedor> getListaImpuestoProductoFacturaProveedor()
/*  347:     */   {
/*  348: 559 */     return this.listaImpuestoProductoFacturaProveedor;
/*  349:     */   }
/*  350:     */   
/*  351:     */   public void setListaImpuestoProductoFacturaProveedor(List<ImpuestoProductoFacturaProveedor> listaImpuestoProductoFacturaProveedor)
/*  352:     */   {
/*  353: 569 */     this.listaImpuestoProductoFacturaProveedor = listaImpuestoProductoFacturaProveedor;
/*  354:     */   }
/*  355:     */   
/*  356:     */   public BigDecimal getDescuentoLinea()
/*  357:     */   {
/*  358: 578 */     return FuncionesUtiles.redondearBigDecimal(getCantidad().multiply(getDescuento()));
/*  359:     */   }
/*  360:     */   
/*  361:     */   public List<DetalleRecepcionProveedor> getListaDetalleRecepcionProveedor()
/*  362:     */   {
/*  363: 582 */     return this.listaDetalleRecepcionProveedor;
/*  364:     */   }
/*  365:     */   
/*  366:     */   public void setListaDetalleRecepcionProveedor(List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor)
/*  367:     */   {
/*  368: 586 */     this.listaDetalleRecepcionProveedor = listaDetalleRecepcionProveedor;
/*  369:     */   }
/*  370:     */   
/*  371:     */   public void setListaDetalleFacturaProveedorImportacion(List<DetalleFacturaProveedorImportacion> listaDetalleFacturaProveedorImportacion)
/*  372:     */   {
/*  373: 590 */     this.listaDetalleFacturaProveedorImportacion = listaDetalleFacturaProveedorImportacion;
/*  374:     */   }
/*  375:     */   
/*  376:     */   public BigDecimal getTraSaldoDevolver()
/*  377:     */   {
/*  378: 599 */     if (this.traSaldoDevolver == null) {
/*  379: 600 */       this.traSaldoDevolver = BigDecimal.ZERO;
/*  380:     */     }
/*  381: 602 */     return this.traSaldoDevolver;
/*  382:     */   }
/*  383:     */   
/*  384:     */   public void setTraSaldoDevolver(BigDecimal traSaldoDevolver)
/*  385:     */   {
/*  386: 612 */     this.traSaldoDevolver = traSaldoDevolver;
/*  387:     */   }
/*  388:     */   
/*  389:     */   public boolean isIndicadorImpuestos()
/*  390:     */   {
/*  391: 616 */     return this.indicadorImpuestos;
/*  392:     */   }
/*  393:     */   
/*  394:     */   public void setIndicadorImpuestos(boolean indicadorImpuestos)
/*  395:     */   {
/*  396: 620 */     this.indicadorImpuestos = indicadorImpuestos;
/*  397:     */   }
/*  398:     */   
/*  399:     */   public List<GastoProductoFacturaProveedor> getListaGastoProductoFacturaProveedor()
/*  400:     */   {
/*  401: 624 */     return this.listaGastoProductoFacturaProveedor;
/*  402:     */   }
/*  403:     */   
/*  404:     */   public void setListaGastoProductoFacturaProveedor(List<GastoProductoFacturaProveedor> listaGastoProductoFacturaProveedor)
/*  405:     */   {
/*  406: 628 */     this.listaGastoProductoFacturaProveedor = listaGastoProductoFacturaProveedor;
/*  407:     */   }
/*  408:     */   
/*  409:     */   public List<GastoProductoFacturaProveedor> getListaGastoProductoFactura()
/*  410:     */   {
/*  411: 639 */     List<GastoProductoFacturaProveedor> lista = new ArrayList();
/*  412: 640 */     for (GastoProductoFacturaProveedor gastoProductoFacturaProveedor : this.listaGastoProductoFacturaProveedor) {
/*  413: 641 */       if (!gastoProductoFacturaProveedor.isEliminado()) {
/*  414: 642 */         lista.add(gastoProductoFacturaProveedor);
/*  415:     */       }
/*  416:     */     }
/*  417: 646 */     return lista;
/*  418:     */   }
/*  419:     */   
/*  420:     */   public BigDecimal getValorGastoContable()
/*  421:     */   {
/*  422: 656 */     BigDecimal valorGastoContable = BigDecimal.ZERO;
/*  423: 658 */     for (GastoProductoFacturaProveedor gastoProductoFacturaProveedor : this.listaGastoProductoFacturaProveedor) {
/*  424: 659 */       if (!gastoProductoFacturaProveedor.isEliminado()) {
/*  425: 660 */         valorGastoContable = valorGastoContable.add(gastoProductoFacturaProveedor.getValor());
/*  426:     */       }
/*  427:     */     }
/*  428: 664 */     return FuncionesUtiles.redondearBigDecimal(valorGastoContable);
/*  429:     */   }
/*  430:     */   
/*  431:     */   public DetalleFacturaProveedor clone()
/*  432:     */   {
/*  433: 668 */     DetalleFacturaProveedor detalleFacturaProveedor = null;
/*  434:     */     try
/*  435:     */     {
/*  436: 670 */       detalleFacturaProveedor = (DetalleFacturaProveedor)super.clone();
/*  437: 671 */       detalleFacturaProveedor.setIdDetalleFacturaProveedor(0);
/*  438:     */     }
/*  439:     */     catch (CloneNotSupportedException localCloneNotSupportedException) {}
/*  440: 674 */     return detalleFacturaProveedor;
/*  441:     */   }
/*  442:     */   
/*  443:     */   public DetalleFacturaProveedor getDetalleFacturaProveedorPadre()
/*  444:     */   {
/*  445: 683 */     return this.detalleFacturaProveedorPadre;
/*  446:     */   }
/*  447:     */   
/*  448:     */   public void setDetalleFacturaProveedorPadre(DetalleFacturaProveedor detalleFacturaProveedorPadre)
/*  449:     */   {
/*  450: 693 */     this.detalleFacturaProveedorPadre = detalleFacturaProveedorPadre;
/*  451:     */   }
/*  452:     */   
/*  453:     */   public Unidad getUnidadCompra()
/*  454:     */   {
/*  455: 702 */     return this.unidadCompra;
/*  456:     */   }
/*  457:     */   
/*  458:     */   public void setUnidadCompra(Unidad unidadCompra)
/*  459:     */   {
/*  460: 712 */     this.unidadCompra = unidadCompra;
/*  461:     */   }
/*  462:     */   
/*  463:     */   public InventarioProducto getInventarioProducto()
/*  464:     */   {
/*  465: 721 */     return this.inventarioProducto;
/*  466:     */   }
/*  467:     */   
/*  468:     */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/*  469:     */   {
/*  470: 731 */     this.inventarioProducto = inventarioProducto;
/*  471:     */   }
/*  472:     */   
/*  473:     */   public Bodega getBodega()
/*  474:     */   {
/*  475: 740 */     return this.bodega;
/*  476:     */   }
/*  477:     */   
/*  478:     */   public void setBodega(Bodega bodega)
/*  479:     */   {
/*  480: 750 */     this.bodega = bodega;
/*  481:     */   }
/*  482:     */   
/*  483:     */   public GastoImportacion getGastoImportacion()
/*  484:     */   {
/*  485: 761 */     return this.gastoImportacion;
/*  486:     */   }
/*  487:     */   
/*  488:     */   public void setGastoImportacion(GastoImportacion gastoImportacion)
/*  489:     */   {
/*  490: 771 */     this.gastoImportacion = gastoImportacion;
/*  491:     */   }
/*  492:     */   
/*  493:     */   public TipoProrrateoEnum getTipoProrrateoEnum()
/*  494:     */   {
/*  495: 780 */     return this.tipoProrrateoEnum;
/*  496:     */   }
/*  497:     */   
/*  498:     */   public void setTipoProrrateoEnum(TipoProrrateoEnum tipoProrrateoEnum)
/*  499:     */   {
/*  500: 790 */     this.tipoProrrateoEnum = tipoProrrateoEnum;
/*  501:     */   }
/*  502:     */   
/*  503:     */   public PartidaArancelaria getPartidaArancelaria()
/*  504:     */   {
/*  505: 799 */     return this.partidaArancelaria;
/*  506:     */   }
/*  507:     */   
/*  508:     */   public void setPartidaArancelaria(PartidaArancelaria partidaArancelaria)
/*  509:     */   {
/*  510: 809 */     this.partidaArancelaria = partidaArancelaria;
/*  511:     */   }
/*  512:     */   
/*  513:     */   public BigDecimal getGastoReal()
/*  514:     */   {
/*  515: 818 */     return this.gastoReal;
/*  516:     */   }
/*  517:     */   
/*  518:     */   public void setGastoReal(BigDecimal gastoReal)
/*  519:     */   {
/*  520: 828 */     this.gastoReal = gastoReal;
/*  521:     */   }
/*  522:     */   
/*  523:     */   public BigDecimal getGastoPresupuesto()
/*  524:     */   {
/*  525: 837 */     return this.gastoPresupuesto;
/*  526:     */   }
/*  527:     */   
/*  528:     */   public void setGastoPresupuesto(BigDecimal gastoPresupuesto)
/*  529:     */   {
/*  530: 847 */     this.gastoPresupuesto = gastoPresupuesto;
/*  531:     */   }
/*  532:     */   
/*  533:     */   public BigDecimal getPesoNeto()
/*  534:     */   {
/*  535: 856 */     return this.pesoNeto;
/*  536:     */   }
/*  537:     */   
/*  538:     */   public void setPesoNeto(BigDecimal pesoNeto)
/*  539:     */   {
/*  540: 866 */     this.pesoNeto = pesoNeto;
/*  541:     */   }
/*  542:     */   
/*  543:     */   public BigDecimal getTraValorImpuestosLineaTotal()
/*  544:     */   {
/*  545: 875 */     return this.traValorImpuestosLineaTotal;
/*  546:     */   }
/*  547:     */   
/*  548:     */   public BigDecimal getPorcentajeDescuento()
/*  549:     */   {
/*  550: 884 */     return this.porcentajeDescuento;
/*  551:     */   }
/*  552:     */   
/*  553:     */   public void setPorcentajeDescuento(BigDecimal porcentajeDescuento)
/*  554:     */   {
/*  555: 894 */     this.porcentajeDescuento = porcentajeDescuento;
/*  556:     */   }
/*  557:     */   
/*  558:     */   public void setTraValorImpuestosLineaTotal(BigDecimal traValorImpuestosLineaTotal)
/*  559:     */   {
/*  560: 904 */     this.traValorImpuestosLineaTotal = traValorImpuestosLineaTotal;
/*  561:     */   }
/*  562:     */   
/*  563:     */   public List<DetalleFacturaProveedorImportacion> getListaDetalleFacturaProveedorImportacionSinEliminados()
/*  564:     */   {
/*  565: 913 */     List<DetalleFacturaProveedorImportacion> lista = new ArrayList();
/*  566: 914 */     for (DetalleFacturaProveedorImportacion detalleFacturaProveedorImportacion : this.listaDetalleFacturaProveedorImportacion) {
/*  567: 915 */       if (!detalleFacturaProveedorImportacion.isEliminado()) {
/*  568: 916 */         lista.add(detalleFacturaProveedorImportacion);
/*  569:     */       }
/*  570:     */     }
/*  571: 920 */     return lista;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public List<DetalleFacturaProveedorImportacion> getListaDetalleFacturaProveedorImportacion()
/*  575:     */   {
/*  576: 929 */     return this.listaDetalleFacturaProveedorImportacion;
/*  577:     */   }
/*  578:     */   
/*  579:     */   public void setListaDetalleFacturaProveedorImportacions(List<DetalleFacturaProveedorImportacion> listaDetalleFacturaProveedorImportacion)
/*  580:     */   {
/*  581: 939 */     this.listaDetalleFacturaProveedorImportacion = listaDetalleFacturaProveedorImportacion;
/*  582:     */   }
/*  583:     */   
/*  584:     */   public BigDecimal getTraValorGastoImportacion()
/*  585:     */   {
/*  586: 948 */     this.traValorGastoImportacion = BigDecimal.ZERO;
/*  587: 949 */     for (DetalleFacturaProveedorImportacion dfpi : this.listaDetalleFacturaProveedorImportacion) {
/*  588: 950 */       if (!dfpi.isEliminado()) {
/*  589: 951 */         this.traValorGastoImportacion = this.traValorGastoImportacion.add(dfpi.getValor());
/*  590:     */       }
/*  591:     */     }
/*  592: 954 */     return this.traValorGastoImportacion;
/*  593:     */   }
/*  594:     */   
/*  595:     */   public void setTraValorGastoImportacion(BigDecimal traValorGastoImportacion)
/*  596:     */   {
/*  597: 964 */     this.traValorGastoImportacion = traValorGastoImportacion;
/*  598:     */   }
/*  599:     */   
/*  600:     */   public DetalleRecepcionProveedor getDetalleRecepcionProveedorDevolucion()
/*  601:     */   {
/*  602: 968 */     return this.detalleRecepcionProveedorDevolucion;
/*  603:     */   }
/*  604:     */   
/*  605:     */   public void setDetalleRecepcionProveedorDevolucion(DetalleRecepcionProveedor detalleRecepcionProveedorDevolucion)
/*  606:     */   {
/*  607: 972 */     this.detalleRecepcionProveedorDevolucion = detalleRecepcionProveedorDevolucion;
/*  608:     */   }
/*  609:     */   
/*  610:     */   public BigDecimal getTraCantidadRecibida()
/*  611:     */   {
/*  612: 976 */     return this.traCantidadRecibida;
/*  613:     */   }
/*  614:     */   
/*  615:     */   public void setTraCantidadRecibida(BigDecimal traCantidadRecibida)
/*  616:     */   {
/*  617: 980 */     this.traCantidadRecibida = traCantidadRecibida;
/*  618:     */   }
/*  619:     */   
/*  620:     */   public List<DetalleFacturaProveedorImportacionProducto> getListaDetalleFacturaProveedorImportacionProducto()
/*  621:     */   {
/*  622: 984 */     return this.listaDetalleFacturaProveedorImportacionProducto;
/*  623:     */   }
/*  624:     */   
/*  625:     */   public void setListaDetalleFacturaProveedorImportacionProducto(List<DetalleFacturaProveedorImportacionProducto> listaDetalleFacturaProveedorImportacionProducto)
/*  626:     */   {
/*  627: 989 */     this.listaDetalleFacturaProveedorImportacionProducto = listaDetalleFacturaProveedorImportacionProducto;
/*  628:     */   }
/*  629:     */   
/*  630:     */   public BigDecimal getCantidadADevolver()
/*  631:     */   {
/*  632: 993 */     return this.cantidadADevolver;
/*  633:     */   }
/*  634:     */   
/*  635:     */   public void setCantidadADevolver(BigDecimal cantidadADevolver)
/*  636:     */   {
/*  637: 997 */     this.cantidadADevolver = cantidadADevolver;
/*  638: 998 */     setCantidad(cantidadADevolver);
/*  639:     */   }
/*  640:     */   
/*  641:     */   public boolean isIndicadorGastoImportacion()
/*  642:     */   {
/*  643:1002 */     return this.indicadorGastoImportacion;
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void setIndicadorGastoImportacion(boolean indicadorGastoImportacion)
/*  647:     */   {
/*  648:1006 */     this.indicadorGastoImportacion = indicadorGastoImportacion;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public List<RegistroPeso> getListaRegistroPesoLiquidados()
/*  652:     */   {
/*  653:1012 */     return this.listaRegistroPesoLiquidados;
/*  654:     */   }
/*  655:     */   
/*  656:     */   public void setListaRegistroPesoLiquidados(List<RegistroPeso> listaRegistroPesoLiquidados)
/*  657:     */   {
/*  658:1016 */     this.listaRegistroPesoLiquidados = listaRegistroPesoLiquidados;
/*  659:     */   }
/*  660:     */   
/*  661:     */   public boolean isIndicadorImpuestoIce()
/*  662:     */   {
/*  663:1020 */     return this.indicadorImpuestoIce;
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void setIndicadorImpuestoIce(boolean indicadorImpuestoIce)
/*  667:     */   {
/*  668:1024 */     if (indicadorImpuestoIce) {
/*  669:1025 */       setCantidad(BigDecimal.ONE);
/*  670:     */     }
/*  671:1027 */     this.indicadorImpuestoIce = indicadorImpuestoIce;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public BigDecimal getValorDescuentoImpuestosLinea()
/*  675:     */   {
/*  676:1031 */     return this.valorDescuentoImpuestosLinea;
/*  677:     */   }
/*  678:     */   
/*  679:     */   public void setValorDescuentoImpuestosLinea(BigDecimal valorDescuentoImpuestosLinea)
/*  680:     */   {
/*  681:1035 */     this.valorDescuentoImpuestosLinea = valorDescuentoImpuestosLinea;
/*  682:     */   }
/*  683:     */   
/*  684:     */   public BigDecimal getCantidadUnidadInformativa()
/*  685:     */   {
/*  686:1039 */     return this.cantidadUnidadInformativa;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public void setCantidadUnidadInformativa(BigDecimal cantidadUnidadInformativa)
/*  690:     */   {
/*  691:1043 */     this.cantidadUnidadInformativa = cantidadUnidadInformativa;
/*  692:     */   }
/*  693:     */   
/*  694:     */   public MovimientoInventario getTransformacionAutomatica()
/*  695:     */   {
/*  696:1047 */     return this.transformacionAutomatica;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public void setTransformacionAutomatica(MovimientoInventario transformacionAutomatica)
/*  700:     */   {
/*  701:1051 */     this.transformacionAutomatica = transformacionAutomatica;
/*  702:     */   }
/*  703:     */   
/*  704:     */   public Lote getLote()
/*  705:     */   {
/*  706:1055 */     return this.lote;
/*  707:     */   }
/*  708:     */   
/*  709:     */   public void setLote(Lote lote)
/*  710:     */   {
/*  711:1059 */     this.lote = lote;
/*  712:     */   }
/*  713:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleFacturaProveedor
 * JD-Core Version:    0.7.0.1
 */