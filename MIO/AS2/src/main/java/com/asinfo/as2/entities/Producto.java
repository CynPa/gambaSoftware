/*    1:     */ package com.asinfo.as2.entities;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.calidad.VariableCalidadProducto;
/*    4:     */ import com.asinfo.as2.entities.produccion.DetalleCostoFabricacion;
/*    5:     */ import com.asinfo.as2.entities.produccion.MezclaProducto;
/*    6:     */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacion;
/*    7:     */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacionSucursal;
/*    8:     */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*    9:     */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*   10:     */ import com.asinfo.as2.entities.sri.IBPCapacidad;
/*   11:     */ import com.asinfo.as2.entities.sri.IBPClasificacion;
/*   12:     */ import com.asinfo.as2.entities.sri.IBPMarca;
/*   13:     */ import com.asinfo.as2.entities.sri.IBPUnidad;
/*   14:     */ import com.asinfo.as2.enumeraciones.TipoCosto;
/*   15:     */ import com.asinfo.as2.enumeraciones.TipoMaterialEnum;
/*   16:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   17:     */ import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
/*   18:     */ import com.asinfo.as2.utils.NodoArbol;
/*   19:     */ import java.io.Serializable;
/*   20:     */ import java.math.BigDecimal;
/*   21:     */ import java.math.RoundingMode;
/*   22:     */ import java.util.ArrayList;
/*   23:     */ import java.util.Date;
/*   24:     */ import java.util.List;
/*   25:     */ import java.util.TreeMap;
/*   26:     */ import javax.persistence.Column;
/*   27:     */ import javax.persistence.Entity;
/*   28:     */ import javax.persistence.EnumType;
/*   29:     */ import javax.persistence.Enumerated;
/*   30:     */ import javax.persistence.FetchType;
/*   31:     */ import javax.persistence.GeneratedValue;
/*   32:     */ import javax.persistence.GenerationType;
/*   33:     */ import javax.persistence.Id;
/*   34:     */ import javax.persistence.JoinColumn;
/*   35:     */ import javax.persistence.ManyToOne;
/*   36:     */ import javax.persistence.OneToMany;
/*   37:     */ import javax.persistence.Table;
/*   38:     */ import javax.persistence.TableGenerator;
/*   39:     */ import javax.persistence.Temporal;
/*   40:     */ import javax.persistence.TemporalType;
/*   41:     */ import javax.persistence.Transient;
/*   42:     */ import javax.validation.constraints.DecimalMin;
/*   43:     */ import javax.validation.constraints.Min;
/*   44:     */ import javax.validation.constraints.NotNull;
/*   45:     */ import javax.validation.constraints.Size;
/*   46:     */ import org.hibernate.annotations.ColumnDefault;
/*   47:     */ 
/*   48:     */ @Entity
/*   49:     */ @Table(name="producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})}, indexes={@javax.persistence.Index(columnList="id_organizacion, activo"), @javax.persistence.Index(columnList="id_producto, id_subcategoria_producto"), @javax.persistence.Index(columnList="id_subcategoria_producto")})
/*   50:     */ public class Producto
/*   51:     */   extends EntidadBase
/*   52:     */   implements Serializable
/*   53:     */ {
/*   54:     */   private static final long serialVersionUID = 1L;
/*   55:     */   @Id
/*   56:     */   @TableGenerator(name="producto", initialValue=0, allocationSize=50)
/*   57:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="producto")
/*   58:     */   @Column(name="id_producto", unique=true, nullable=false)
/*   59:     */   private int idProducto;
/*   60:     */   @Column(name="id_organizacion", nullable=false)
/*   61:     */   @NotNull
/*   62:     */   private int idOrganizacion;
/*   63:     */   @Column(name="id_sucursal", nullable=false)
/*   64:     */   @NotNull
/*   65:     */   private int idSucursal;
/*   66:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   67:     */   @JoinColumn(name="id_subcategoria_producto", nullable=false)
/*   68:     */   @NotNull
/*   69:     */   private SubcategoriaProducto subcategoriaProducto;
/*   70:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   71:     */   @JoinColumn(name="id_categoria_impuesto", nullable=true)
/*   72:     */   private CategoriaImpuesto categoriaImpuesto;
/*   73:     */   @Column(name="codigo", nullable=false, length=20)
/*   74:     */   @NotNull
/*   75:     */   @Size(min=1, max=20)
/*   76:     */   private String codigo;
/*   77:     */   @Column(name="codigo_alterno", length=50, nullable=true)
/*   78:     */   @Size(max=50)
/*   79:     */   private String codigoAlterno;
/*   80:     */   @Column(name="codigo_comercial", length=20, nullable=true)
/*   81:     */   @Size(max=20)
/*   82:     */   private String codigoComercial;
/*   83:     */   @Column(name="codigo_barras", length=20, nullable=true)
/*   84:     */   @Size(max=20)
/*   85:     */   private String codigoBarras;
/*   86:     */   @Column(name="nombre", nullable=false, length=100)
/*   87:     */   @NotNull
/*   88:     */   @Size(min=2, max=100)
/*   89:     */   private String nombre;
/*   90:     */   @Column(name="descripcion", nullable=true, length=500)
/*   91:     */   @Size(max=500)
/*   92:     */   private String descripcion;
/*   93:     */   @Column(name="informacion_adicional", nullable=true, length=5000)
/*   94:     */   @Size(max=5000)
/*   95: 127 */   private String informacionAdicional = "";
/*   96:     */   @Column(name="nombre_comercial", nullable=false, length=100)
/*   97:     */   @NotNull
/*   98:     */   @Size(max=100)
/*   99:     */   private String nombreComercial;
/*  100:     */   @Enumerated(EnumType.ORDINAL)
/*  101:     */   @Column(name="tipo_producto", nullable=false)
/*  102:     */   @NotNull
/*  103:     */   private TipoProducto tipoProducto;
/*  104:     */   @Column(name="indicador_produccion", nullable=false)
/*  105:     */   @NotNull
/*  106:     */   private boolean indicadorProduccion;
/*  107:     */   @Column(name="indicador_venta", nullable=false)
/*  108:     */   @NotNull
/*  109:     */   private boolean indicadorVenta;
/*  110:     */   @Column(name="indicador_compra", nullable=false)
/*  111:     */   @NotNull
/*  112:     */   private boolean indicadorCompra;
/*  113:     */   @Column(name="indicador_impuestos", nullable=false)
/*  114:     */   @NotNull
/*  115:     */   private boolean indicadorImpuestos;
/*  116:     */   @Column(name="indicador_porcentaje_ice", nullable=false)
/*  117:     */   @NotNull
/*  118:     */   @ColumnDefault("'0'")
/*  119:     */   private boolean indicadorPorcentajeIce;
/*  120:     */   @Column(name="ice", nullable=false, precision=12, scale=2)
/*  121:     */   @Min(0L)
/*  122:     */   @NotNull
/*  123:     */   @ColumnDefault("0")
/*  124: 162 */   private BigDecimal ice = BigDecimal.ZERO;
/*  125:     */   @Column(name="codigo_ice", length=10, nullable=true)
/*  126:     */   @Size(max=10)
/*  127: 168 */   private String codigoIce = "";
/*  128:     */   @Column(name="indicador_lote", nullable=false)
/*  129:     */   @NotNull
/*  130:     */   private boolean indicadorLote;
/*  131:     */   @Column(name="indicador_serie")
/*  132:     */   private Boolean indicadorSerie;
/*  133:     */   @Column(name="imagen", nullable=true)
/*  134:     */   @Size(max=50)
/*  135:     */   private String imagen;
/*  136:     */   @Column(name="pdf", nullable=true)
/*  137:     */   @Size(max=50)
/*  138:     */   private String pdf;
/*  139:     */   @Column(name="activo", nullable=false)
/*  140:     */   @NotNull
/*  141:     */   private boolean activo;
/*  142:     */   @Column(name="predeterminado", nullable=false)
/*  143:     */   @NotNull
/*  144:     */   private boolean predeterminado;
/*  145:     */   @Column(name="indicador_tiene_movimientos", nullable=false)
/*  146:     */   @NotNull
/*  147:     */   private boolean indicadorTieneMovimientos;
/*  148:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  149:     */   @JoinColumn(name="id_conjunto_atributo", nullable=true)
/*  150:     */   private ConjuntoAtributo conjuntoAtributo;
/*  151:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="producto")
/*  152: 203 */   private List<ProductoAtributo> listaProductoAtributo = new ArrayList();
/*  153:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="producto")
/*  154: 206 */   private List<ProductoSustituto> listaProductoSustituto = new ArrayList();
/*  155:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="producto")
/*  156: 209 */   private List<UnidadConversion> listaUnidadConversion = new ArrayList();
/*  157:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="producto")
/*  158: 212 */   private List<ProductoBodega> listaProductoBodega = new ArrayList();
/*  159:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="producto")
/*  160: 215 */   private List<MezclaProducto> listaMezclaProducto = new ArrayList();
/*  161:     */   @NotNull
/*  162:     */   @Column(name="indicador_mezcla", nullable=false)
/*  163:     */   @ColumnDefault("'0'")
/*  164:     */   private boolean indicadorMezcla;
/*  165:     */   @ManyToOne(fetch=FetchType.EAGER)
/*  166:     */   @JoinColumn(name="id_unidad", nullable=false)
/*  167:     */   @NotNull
/*  168:     */   private Unidad unidad;
/*  169:     */   @Column(name="peso", nullable=false, precision=12, scale=2)
/*  170:     */   @NotNull
/*  171:     */   @Min(0L)
/*  172: 229 */   private BigDecimal peso = BigDecimal.ZERO;
/*  173:     */   @Column(name="peso_minimo", nullable=false, precision=12, scale=2)
/*  174:     */   @NotNull
/*  175:     */   @Min(0L)
/*  176: 234 */   private BigDecimal pesoMinimo = BigDecimal.ZERO;
/*  177:     */   @Column(name="peso_maximo", nullable=false, precision=12, scale=2)
/*  178:     */   @NotNull
/*  179:     */   @Min(0L)
/*  180: 239 */   private BigDecimal pesoMaximo = BigDecimal.ZERO;
/*  181:     */   @Column(name="volumen", nullable=false, precision=12, scale=4)
/*  182:     */   @NotNull
/*  183: 244 */   private BigDecimal volumen = BigDecimal.ZERO;
/*  184:     */   @Column(name="cantidad_minima", nullable=false, precision=12, scale=2)
/*  185:     */   @NotNull
/*  186: 248 */   private BigDecimal cantidadMinima = BigDecimal.ZERO;
/*  187:     */   @Column(name="cantidad_maxima", nullable=false, precision=12, scale=2)
/*  188:     */   @NotNull
/*  189: 252 */   private BigDecimal cantidadMaxima = BigDecimal.ZERO;
/*  190:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  191:     */   @JoinColumn(name="id_unidad_almacenamiento", nullable=true)
/*  192:     */   private Unidad unidadAlmacenamiento;
/*  193:     */   @Column(name="indicador_manejo_peso", nullable=true)
/*  194:     */   private boolean indicadorManejoPeso;
/*  195:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  196:     */   @JoinColumn(name="id_unidad_venta", nullable=true)
/*  197:     */   private Unidad unidadVenta;
/*  198:     */   @Column(name="precio_referencial_venta", nullable=false, precision=12, scale=4)
/*  199: 268 */   private BigDecimal precioReferencialVenta = BigDecimal.ZERO;
/*  200:     */   @Column(name="cantidad_minima_venta", nullable=false)
/*  201: 271 */   private int cantidadMinimaVenta = 0;
/*  202:     */   @Column(name="multiplo_pedido", nullable=true)
/*  203: 275 */   private Integer multiploPedido = Integer.valueOf(0);
/*  204:     */   @Column(name="precio_ultima_venta", nullable=false, precision=12, scale=4)
/*  205: 277 */   private BigDecimal precioUltimaVenta = BigDecimal.ZERO;
/*  206:     */   @Temporal(TemporalType.DATE)
/*  207:     */   @Column(name="fecha_ultima_venta", nullable=true)
/*  208:     */   private Date fechaUltimaVenta;
/*  209:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  210:     */   @JoinColumn(name="id_bodega_venta", nullable=true)
/*  211:     */   private Bodega bodegaVenta;
/*  212:     */   @Column(name="codigo_fiscal", length=20, nullable=true)
/*  213:     */   @Size(max=20)
/*  214:     */   private String codigoFiscal;
/*  215:     */   @Column(name="indicador_incluir_pedido_sugerido", nullable=false)
/*  216:     */   @NotNull
/*  217:     */   @ColumnDefault("'0'")
/*  218: 292 */   private boolean indicadorIncluirPedidoSugerido = false;
/*  219:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  220:     */   @JoinColumn(name="id_unidad_compra", nullable=true)
/*  221:     */   private Unidad unidadCompra;
/*  222:     */   @Column(name="precio_referencial_compra", nullable=false, precision=12, scale=6)
/*  223: 302 */   private BigDecimal precioReferencialCompra = BigDecimal.ZERO;
/*  224:     */   @Column(name="precio_ultima_compra", nullable=false, precision=12, scale=4)
/*  225: 305 */   private BigDecimal precioUltimaCompra = BigDecimal.ZERO;
/*  226:     */   @Temporal(TemporalType.DATE)
/*  227:     */   @Column(name="fecha_ultima_compra", nullable=true)
/*  228:     */   private Date fechaUltimaCompra;
/*  229:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  230:     */   @JoinColumn(name="id_empresa", nullable=true)
/*  231:     */   private Empresa empresa;
/*  232:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  233:     */   @JoinColumn(name="id_bodega_compra", nullable=true)
/*  234:     */   private Bodega bodegaCompra;
/*  235:     */   @Column(name="indicador_califica_proveedor", nullable=false)
/*  236:     */   @NotNull
/*  237:     */   private boolean indicadorCalificaProveedor;
/*  238:     */   @Column(name="tipo_costo", nullable=false)
/*  239:     */   @Enumerated(EnumType.ORDINAL)
/*  240:     */   private TipoCosto tipoCosto;
/*  241:     */   @Column(name="costo_estandar", nullable=true, precision=12, scale=4)
/*  242: 332 */   private BigDecimal costoEstandar = BigDecimal.ZERO;
/*  243:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="producto")
/*  244: 335 */   private List<ProductoMaterial> listaProductoMaterial = new ArrayList();
/*  245:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="producto")
/*  246: 338 */   private List<VariableCalidadProducto> listaVariableCalidadProducto = new ArrayList();
/*  247:     */   @Transient
/*  248: 341 */   private BigDecimal cantidadProducir = BigDecimal.ZERO;
/*  249:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="productoPadre")
/*  250: 344 */   private List<SubProducto> listaSubProducto = new ArrayList();
/*  251:     */   @Column(name="indicador_post_consumo", nullable=false)
/*  252:     */   @NotNull
/*  253:     */   private boolean indicadorPostConsumo;
/*  254:     */   @Column(name="indicador_horas_post_consumo", nullable=false)
/*  255:     */   @NotNull
/*  256:     */   private boolean indicadorHorasPostConsumo;
/*  257:     */   @Column(name="indicador_consumo_directo", nullable=false)
/*  258:     */   @NotNull
/*  259:     */   private boolean indicadorConsumoDirecto;
/*  260:     */   @DecimalMin("0.00")
/*  261:     */   @Column(name="cantidad_produccion", nullable=false, precision=12, scale=2)
/*  262:     */   @NotNull
/*  263: 362 */   private BigDecimal cantidadProduccion = BigDecimal.ZERO;
/*  264:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  265:     */   @JoinColumn(name="id_ruta_fabricacion", nullable=true)
/*  266:     */   private RutaFabricacion rutaFabricacion;
/*  267:     */   @Column(name="factor_perdida", nullable=false, precision=12, scale=2)
/*  268: 377 */   private BigDecimal factorPerdida = BigDecimal.ZERO;
/*  269:     */   @Column(name="cantidad_perdida", nullable=false, precision=12, scale=2)
/*  270: 380 */   private BigDecimal cantidadPerdida = BigDecimal.ZERO;
/*  271:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="producto")
/*  272: 383 */   private List<ProductoRutaFabricacion> listaProductoRutaFabricacion = new ArrayList();
/*  273:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  274:     */   @JoinColumn(name="id_presentacion_producto", nullable=true)
/*  275:     */   private PresentacionProducto presentacionProducto;
/*  276:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  277:     */   @JoinColumn(name="id_marca_producto", nullable=true)
/*  278:     */   private MarcaProducto marcaProducto;
/*  279:     */   @Column(name="indicador_produccion_rapida", nullable=false)
/*  280:     */   @NotNull
/*  281:     */   private boolean indicadorProduccionRapida;
/*  282:     */   @Column(name="indicador_planificar_produccion", nullable=false)
/*  283:     */   @NotNull
/*  284:     */   private boolean indicadorPlanificarProduccion;
/*  285:     */   @Column(name="indicador_despacho_gaveta", nullable=false)
/*  286:     */   @NotNull
/*  287:     */   private boolean indicadorPesoBalanza;
/*  288:     */   @Column(name="tiempo_reposicion", nullable=false)
/*  289:     */   @NotNull
/*  290:     */   private int tiempoReposicion;
/*  291:     */   @Column(name="porciento_despacho_supera_pedido", nullable=false)
/*  292:     */   @NotNull
/*  293:     */   @Min(0L)
/*  294:     */   private int porCientoDespachoSuperaPedido;
/*  295:     */   @Column(name="porciento_tolerancia_transferencia", nullable=false)
/*  296:     */   @NotNull
/*  297:     */   @Min(0L)
/*  298:     */   private int porcientoToleranciaTransferencia;
/*  299:     */   @NotNull
/*  300:     */   @ColumnDefault("'0'")
/*  301:     */   @Column(name="indicador_control_calidad", nullable=false)
/*  302: 425 */   private Boolean indicadorControlCalidad = Boolean.valueOf(false);
/*  303:     */   @Column(name="peso_destare_unidad", nullable=true, precision=12, scale=2)
/*  304: 427 */   private BigDecimal pesoDestareUnidad = BigDecimal.ZERO;
/*  305:     */   @Column(name="ibp_codigo_impuesto", nullable=true, length=4)
/*  306:     */   @Size(min=4, max=4)
/*  307: 431 */   private String ibpCodigoImpuesto = "5001";
/*  308:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  309:     */   @JoinColumn(name="id_ibp_marca", nullable=true)
/*  310:     */   private IBPMarca ibpMarca;
/*  311:     */   @Column(name="ibp_presentacion", nullable=true, length=3)
/*  312:     */   @Size(min=3, max=3)
/*  313: 439 */   private String ibpPresentacion = "025";
/*  314:     */   @Column(name="ice_presentacion", nullable=false, length=3)
/*  315:     */   @ColumnDefault("'000'")
/*  316:     */   @NotNull
/*  317:     */   @Size(min=3, max=3)
/*  318: 443 */   private String icePresentacion = "000";
/*  319:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  320:     */   @JoinColumn(name="id_ibp_capacidad", nullable=true)
/*  321:     */   private IBPCapacidad ibpCapacidad;
/*  322:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  323:     */   @JoinColumn(name="id_ibp_unidad", nullable=true)
/*  324:     */   private IBPUnidad ibpUnidad;
/*  325:     */   @Column(name="ibp_grado_alcohol", nullable=true, length=6)
/*  326:     */   @Size(min=6, max=6)
/*  327: 457 */   private String ibpGradoAlcohol = "000000";
/*  328:     */   @Transient
/*  329:     */   private IBPClasificacion ibpClasificacion;
/*  330:     */   @Transient
/*  331: 464 */   private List<IBPMarca> listaIBPMarca = new ArrayList();
/*  332:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  333:     */   @JoinColumn(name="id_partida_arancelaria", nullable=true)
/*  334:     */   private PartidaArancelaria partidaArancelaria;
/*  335:     */   @Min(0L)
/*  336:     */   @Column(name="orden_costeo")
/*  337: 474 */   private Integer ordenCosteo = Integer.valueOf(0);
/*  338:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="producto")
/*  339: 476 */   private List<BodegaTrabajoProductoSucursal> listaBodegaTrabajoSucursal = new ArrayList();
/*  340:     */   @Min(0L)
/*  341:     */   @Column(name="version_costeo", nullable=false)
/*  342:     */   @NotNull
/*  343: 479 */   private int versionCosteo = 0;
/*  344:     */   @Column(name="indicador_explota_devolucion", nullable=true)
/*  345:     */   private Boolean indicadorExplotaDevolucion;
/*  346:     */   @Column(name="indicador_consumo", nullable=false)
/*  347:     */   @NotNull
/*  348:     */   @ColumnDefault("'1'")
/*  349:     */   private boolean indicadorConsumo;
/*  350:     */   @DecimalMin("0.00")
/*  351:     */   @Column(name="subsidio", nullable=false, precision=12, scale=6)
/*  352:     */   @NotNull
/*  353: 493 */   private BigDecimal subsidio = BigDecimal.ZERO;
/*  354:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  355:     */   @JoinColumn(name="id_credito_tributarioSRI", nullable=true)
/*  356:     */   private CreditoTributarioSRI creditoTributarioSRI;
/*  357:     */   @Column(name="ice_gramos_azucar", nullable=false, precision=12, scale=2)
/*  358:     */   @DecimalMin("0.00")
/*  359:     */   @NotNull
/*  360:     */   @ColumnDefault("0.00")
/*  361: 503 */   private BigDecimal iceGramosAzucar = BigDecimal.ZERO;
/*  362:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="producto")
/*  363: 509 */   private List<ProductoRutaFabricacionSucursal> listaProductoRutaFabricacionSucursal = new ArrayList();
/*  364:     */   @Column(name="indicador_produccion_kit", nullable=false)
/*  365:     */   @NotNull
/*  366:     */   @ColumnDefault("'0'")
/*  367:     */   private boolean indicadorProduccionKit;
/*  368:     */   @Column(name="prefijos_lote", nullable=true, length=50)
/*  369:     */   @Size(max=50)
/*  370:     */   private String prefijosLote;
/*  371:     */   @Transient
/*  372:     */   private String prefijoLote;
/*  373:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  374:     */   @JoinColumn(name="id_atributo_produccion_1", nullable=true)
/*  375:     */   private Atributo atributoProduccion1;
/*  376:     */   @Column(name="coeficiente_produccion", nullable=false, precision=12, scale=4)
/*  377:     */   @NotNull
/*  378:     */   @Min(0L)
/*  379:     */   @ColumnDefault("0")
/*  380: 529 */   private BigDecimal coeficienteProduccion = BigDecimal.ZERO;
/*  381:     */   @Transient
/*  382:     */   private Atributo traAtributo1;
/*  383:     */   @Transient
/*  384:     */   private boolean indicadorAtributo1;
/*  385:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  386:     */   @JoinColumn(name="id_atributo_produccion_2", nullable=true)
/*  387:     */   private Atributo atributoProduccion2;
/*  388:     */   @Transient
/*  389:     */   private Atributo traAtributo2;
/*  390:     */   @Transient
/*  391:     */   private boolean indicadorAtributo2;
/*  392:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  393:     */   @JoinColumn(name="id_atributo_produccion_3", nullable=true)
/*  394:     */   private Atributo atributoProduccion3;
/*  395:     */   @Transient
/*  396:     */   private Atributo traAtributo3;
/*  397:     */   @Transient
/*  398:     */   private boolean indicadorAtributo3;
/*  399:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  400:     */   @JoinColumn(name="id_atributo_produccion_4", nullable=true)
/*  401:     */   private Atributo atributoProduccion4;
/*  402:     */   @Transient
/*  403:     */   private Atributo traAtributo4;
/*  404:     */   @Transient
/*  405:     */   private boolean indicadorAtributo4;
/*  406:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  407:     */   @JoinColumn(name="id_atributo_produccion_5", nullable=true)
/*  408:     */   private Atributo atributoProduccion5;
/*  409:     */   @Transient
/*  410:     */   private Atributo traAtributo5;
/*  411:     */   @Transient
/*  412:     */   private boolean indicadorAtributo5;
/*  413:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  414:     */   @JoinColumn(name="id_atributo_produccion_6", nullable=true)
/*  415:     */   private Atributo atributoProduccion6;
/*  416:     */   @Transient
/*  417:     */   private Atributo traAtributo6;
/*  418:     */   @Transient
/*  419:     */   private boolean indicadorAtributo6;
/*  420:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  421:     */   @JoinColumn(name="id_atributo_produccion_7", nullable=true)
/*  422:     */   private Atributo atributoProduccion7;
/*  423:     */   @Transient
/*  424:     */   private Atributo traAtributo7;
/*  425:     */   @Transient
/*  426:     */   private boolean indicadorAtributo7;
/*  427:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  428:     */   @JoinColumn(name="id_atributo_produccion_8", nullable=true)
/*  429:     */   private Atributo atributoProduccion8;
/*  430:     */   @Transient
/*  431:     */   private Atributo traAtributo8;
/*  432:     */   @Transient
/*  433:     */   private boolean indicadorAtributo8;
/*  434:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  435:     */   @JoinColumn(name="id_atributo_produccion_9", nullable=true)
/*  436:     */   private Atributo atributoProduccion9;
/*  437:     */   @Transient
/*  438:     */   private Atributo traAtributo9;
/*  439:     */   @Transient
/*  440:     */   private boolean indicadorAtributo9;
/*  441:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  442:     */   @JoinColumn(name="id_atributo_produccion_10", nullable=true)
/*  443:     */   private Atributo atributoProduccion10;
/*  444:     */   @Transient
/*  445:     */   private Atributo traAtributo10;
/*  446:     */   @Transient
/*  447:     */   private boolean indicadorAtributo10;
/*  448:     */   @Transient
/*  449: 633 */   private BigDecimal traDescuentoPorcentajeMaximo = BigDecimal.ZERO;
/*  450:     */   @Transient
/*  451: 636 */   private List<Unidad> traListaUnidadConversion = new ArrayList();
/*  452:     */   @Transient
/*  453: 639 */   private BigDecimal traCantidad = BigDecimal.ZERO;
/*  454:     */   @Transient
/*  455:     */   private Lote lote;
/*  456:     */   @Transient
/*  457:     */   private Bodega traBodega;
/*  458:     */   @Transient
/*  459:     */   private BigDecimal saldo;
/*  460:     */   @Transient
/*  461:     */   private String codigoBodega;
/*  462:     */   @Transient
/*  463:     */   private String nombreBodega;
/*  464:     */   @Transient
/*  465:     */   private Integer idCategoriaProducto;
/*  466:     */   @Transient
/*  467:     */   private String codigoCategoriaProducto;
/*  468:     */   @Transient
/*  469:     */   private String nombreCategoriaProducto;
/*  470:     */   @Transient
/*  471:     */   private Integer idSubcategoriaProducto;
/*  472:     */   @Transient
/*  473:     */   private String codigoSubcategoriaProducto;
/*  474:     */   @Transient
/*  475:     */   private String nombreSubcategoriaProducto;
/*  476:     */   @Transient
/*  477:     */   private Integer idCategoriaImpuesto;
/*  478:     */   @Transient
/*  479:     */   private String codigoCategoriaImpuesto;
/*  480:     */   @Transient
/*  481:     */   private String nombreCategoriaImpuesto;
/*  482:     */   @Transient
/*  483:     */   private Integer idUnidad;
/*  484:     */   @Transient
/*  485:     */   private String codigoUnidad;
/*  486:     */   @Transient
/*  487:     */   private String nombreUnidad;
/*  488:     */   @Transient
/*  489:     */   private Integer idUnidadAlmacenamiento;
/*  490:     */   @Transient
/*  491:     */   private String codigoUnidadAlmacenamiento;
/*  492:     */   @Transient
/*  493:     */   private String nombreUnidadAlmacenamiento;
/*  494:     */   @Transient
/*  495:     */   private Integer idUnidadVenta;
/*  496:     */   @Transient
/*  497:     */   private String codigoUnidadVenta;
/*  498:     */   @Transient
/*  499:     */   private String nombreUnidadVenta;
/*  500:     */   @Transient
/*  501:     */   private Integer idUnidadCompra;
/*  502:     */   @Transient
/*  503:     */   private String codigoUnidadCompra;
/*  504:     */   @Transient
/*  505:     */   private String nombreUnidadCompra;
/*  506:     */   @Transient
/*  507:     */   private Integer idBodegaVenta;
/*  508:     */   @Transient
/*  509:     */   private String codigoBodegaVenta;
/*  510:     */   @Transient
/*  511:     */   private String nombreBodegaVenta;
/*  512:     */   @Transient
/*  513:     */   private Integer idBodegaCompra;
/*  514:     */   @Transient
/*  515:     */   private String codigoBodegaCompra;
/*  516:     */   @Transient
/*  517:     */   private String nombreBodegaCompra;
/*  518:     */   @Transient
/*  519:     */   private Integer idPartidaArancelaria;
/*  520:     */   @Transient
/*  521:     */   private String codigoPartidaArancelaria;
/*  522:     */   @Transient
/*  523:     */   private String nombrePartidaArancelaria;
/*  524:     */   @Transient
/*  525:     */   private String atributo1;
/*  526:     */   @Transient
/*  527:     */   private String atributo2;
/*  528:     */   @Transient
/*  529:     */   private String atributo3;
/*  530:     */   @Transient
/*  531:     */   private String atributo4;
/*  532:     */   @Transient
/*  533:     */   private String atributo5;
/*  534:     */   @Transient
/*  535:     */   private String atributo6;
/*  536:     */   @Transient
/*  537:     */   private String atributo7;
/*  538:     */   @Transient
/*  539:     */   private String atributo8;
/*  540:     */   @Transient
/*  541:     */   private String atributo9;
/*  542:     */   @Transient
/*  543:     */   private String atributo10;
/*  544:     */   @Transient
/*  545:     */   private NodoArbol<Producto> arbolComponentes;
/*  546:     */   @Transient
/*  547: 749 */   private BigDecimal pesoMaterialPrincipal = null;
/*  548:     */   @Transient
/*  549: 751 */   private Bodega bodegaDevolucion = null;
/*  550:     */   @Transient
/*  551: 755 */   private List<DetalleCostoFabricacion> listaDetalleCostoFabricacion = new ArrayList();
/*  552:     */   @Transient
/*  553: 757 */   private BigDecimal cantidadFabricada = BigDecimal.ZERO;
/*  554:     */   @Transient
/*  555: 759 */   private BigDecimal costoMateriales = BigDecimal.ZERO;
/*  556:     */   @Transient
/*  557: 761 */   private BigDecimal costoManoObra = BigDecimal.ZERO;
/*  558:     */   @Transient
/*  559: 763 */   private BigDecimal costoDepreciaciones = BigDecimal.ZERO;
/*  560:     */   @Transient
/*  561: 765 */   private BigDecimal costoIndirecto = BigDecimal.ZERO;
/*  562:     */   @Transient
/*  563: 767 */   private BigDecimal costoTotalAsignado = BigDecimal.ZERO;
/*  564:     */   @Column(name="impuesto_aviacion")
/*  565:     */   private Boolean impuestoAviacion;
/*  566:     */   @Column(name="indicador_nacional")
/*  567:     */   private Boolean indicadorNacional;
/*  568:     */   @Transient
/*  569: 779 */   private BigDecimal valorReceta = BigDecimal.ZERO;
/*  570:     */   @Transient
/*  571: 782 */   private BigDecimal porcentajeReceta = BigDecimal.ZERO;
/*  572:     */   @Column(name="indicador_mantenimiento", nullable=false)
/*  573:     */   @NotNull
/*  574:     */   @ColumnDefault("'0'")
/*  575:     */   private boolean indicadorMantenimiento;
/*  576:     */   @Enumerated(EnumType.ORDINAL)
/*  577:     */   @Column(name="tipo_producto_formulacion", nullable=true)
/*  578:     */   private TipoMaterialEnum tipoMaterialEnum;
/*  579:     */   @Column(name="maximo_desvio", nullable=false, precision=12, scale=2)
/*  580:     */   @NotNull
/*  581:     */   @ColumnDefault("0")
/*  582: 796 */   private BigDecimal maximoDesvio = BigDecimal.ZERO;
/*  583:     */   @Column(name="dias_caducidad", nullable=true)
/*  584:     */   @Min(0L)
/*  585: 803 */   private Integer diasCaducidad = Integer.valueOf(0);
/*  586:     */   @Column(name="indicador_maneja_unidad_informativa", nullable=false)
/*  587:     */   @NotNull
/*  588:     */   @ColumnDefault("'0'")
/*  589:     */   private boolean indicadorManejaUnidadInformativa;
/*  590:     */   @ManyToOne(fetch=FetchType.EAGER)
/*  591:     */   @JoinColumn(name="id_unidad_informativa", nullable=true)
/*  592:     */   private Unidad unidadInformativa;
/*  593:     */   @ManyToOne(fetch=FetchType.EAGER)
/*  594:     */   @JoinColumn(name="id_producto_transformacion_automatica", nullable=true)
/*  595:     */   private Producto productoTransformacionAutomatica;
/*  596:     */   @Column(name="indicador_lista_material", nullable=false)
/*  597:     */   @NotNull
/*  598:     */   @ColumnDefault("'0'")
/*  599: 820 */   private boolean indicadorListaMaterial = false;
/*  600:     */   @Transient
/*  601:     */   private Boolean migracionExisteNombreProducto;
/*  602:     */   @Transient
/*  603:     */   private ValorAtributo valorAtributo1;
/*  604:     */   @Transient
/*  605:     */   private ValorAtributo valorAtributo2;
/*  606:     */   @Transient
/*  607:     */   private ValorAtributo valorAtributo3;
/*  608:     */   @Transient
/*  609:     */   private ValorAtributo valorAtributo4;
/*  610:     */   @Transient
/*  611:     */   private ValorAtributo valorAtributo5;
/*  612:     */   @Transient
/*  613:     */   private ValorAtributo valorAtributo6;
/*  614:     */   @Transient
/*  615:     */   private ValorAtributo valorAtributo7;
/*  616:     */   @Transient
/*  617:     */   private ValorAtributo valorAtributo8;
/*  618:     */   @Transient
/*  619:     */   private ValorAtributo valorAtributo9;
/*  620:     */   @Transient
/*  621:     */   private ValorAtributo valorAtributo10;
/*  622:     */   @Transient
/*  623:     */   private boolean indicadorGeneraSuborden;
/*  624:     */   @Transient
/*  625:     */   private boolean traConsumoDirecto;
/*  626:     */   
/*  627:     */   public Producto(int idProducto, String codigo, String codigoAlterno, String nombre, String nombreComercial, String descripcion, TipoProducto tipoProducto, TipoCosto tipoCosto, boolean indicadorProduccion, boolean indicadorVenta, boolean indicadorCompra, boolean indicadorImpuestos, boolean indicadorLote, int idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaImpuesto, String codigoCategoriaImpuesto, String nombreCategoriaImpuesto, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadCompra, String codigoUnidadCompra, String nombreUnidadCompra, Integer idBodegaVenta, String codigoBodegaVenta, String nombreBodegaVenta, Integer idBodegaCompra, String codigoBodegaCompra, String nombreBodegaCompra, Integer idPartidaArancelaria, String codigoPartidaArancelaria, String nombrePartidaArancelaria, BigDecimal precioUltimaCompra, BigDecimal precioUltimaVenta, boolean indicadorManejoPeso, Boolean indicadorSerie, BigDecimal cantidadProduccion, Integer idPresentacionProducto, String nombrePresentacionProducto, BigDecimal cantidadUnidadesPresentacionProducto, Integer numeroDecimalesUnidad, Boolean indicadorConsumoDirecto, Boolean indicadorPesoBalanza, BigDecimal subsidio, boolean indicadorPorcentajeIce, BigDecimal ice, String codigoIce, boolean indicadorManejaUnidadInformativa, Integer idUnidadInformativa, String codigoUnidadInformativa, String nombreUnidadInformativa, TipoUnidadMedida tipoUnidadInformativa, String imagen, String codigoBarras, String prefijosLote)
/*  628:     */   {
/*  629: 883 */     this(idProducto, codigo, codigoAlterno, nombre, nombreComercial, descripcion, tipoProducto, tipoCosto, indicadorProduccion, indicadorVenta, indicadorCompra, indicadorImpuestos, indicadorLote, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaImpuesto, codigoCategoriaImpuesto, nombreCategoriaImpuesto, idUnidad, codigoUnidad, nombreUnidad, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadCompra, codigoUnidadCompra, nombreUnidadCompra, idBodegaVenta, codigoBodegaVenta, nombreBodegaVenta, idBodegaCompra, codigoBodegaCompra, nombreBodegaCompra, idPartidaArancelaria, codigoPartidaArancelaria, nombrePartidaArancelaria, precioUltimaCompra, precioUltimaVenta, indicadorManejoPeso, indicadorSerie, cantidadProduccion, idPresentacionProducto, nombrePresentacionProducto, cantidadUnidadesPresentacionProducto, numeroDecimalesUnidad, indicadorConsumoDirecto, indicadorPesoBalanza, subsidio, indicadorPorcentajeIce, ice, codigoIce);
/*  630:     */     
/*  631:     */ 
/*  632:     */ 
/*  633:     */ 
/*  634:     */ 
/*  635:     */ 
/*  636:     */ 
/*  637:     */ 
/*  638: 892 */     this.codigoBarras = codigoBarras;
/*  639: 893 */     this.imagen = imagen;
/*  640: 894 */     this.prefijosLote = prefijosLote;
/*  641: 895 */     this.indicadorManejaUnidadInformativa = indicadorManejaUnidadInformativa;
/*  642: 897 */     if (idUnidadInformativa != null)
/*  643:     */     {
/*  644: 898 */       this.unidadInformativa = new Unidad();
/*  645: 899 */       this.unidadInformativa.setIdUnidad(idUnidadInformativa.intValue());
/*  646: 900 */       this.unidadInformativa.setCodigo(codigoUnidadInformativa);
/*  647: 901 */       this.unidadInformativa.setNombre(nombreUnidadInformativa);
/*  648: 902 */       this.unidadInformativa.setTipoUnidadMedida(tipoUnidadInformativa);
/*  649:     */     }
/*  650:     */   }
/*  651:     */   
/*  652:     */   public Producto(int idProducto, String codigo, String codigoAlterno, String nombre, String nombreComercial, String descripcion, TipoProducto tipoProducto, TipoCosto tipoCosto, boolean indicadorProduccion, boolean indicadorVenta, boolean indicadorCompra, boolean indicadorImpuestos, boolean indicadorLote, int idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaImpuesto, String codigoCategoriaImpuesto, String nombreCategoriaImpuesto, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadCompra, String codigoUnidadCompra, String nombreUnidadCompra, Integer idBodegaVenta, String codigoBodegaVenta, String nombreBodegaVenta, Integer idBodegaCompra, String codigoBodegaCompra, String nombreBodegaCompra, Integer idPartidaArancelaria, String codigoPartidaArancelaria, String nombrePartidaArancelaria, BigDecimal precioUltimaCompra, BigDecimal precioUltimaVenta, boolean indicadorManejoPeso, Boolean indicadorSerie, BigDecimal cantidadProduccion, Integer idPresentacionProducto, String nombrePresentacionProducto, BigDecimal cantidadUnidadesPresentacionProducto, Integer numeroDecimalesUnidad, Boolean indicadorConsumoDirecto, Boolean indicadorPesoBalanza, BigDecimal subsidio, boolean indicadorPorcentajeIce, BigDecimal ice, String codigoIce)
/*  653:     */   {
/*  654: 920 */     this.idProducto = idProducto;
/*  655: 921 */     this.codigo = codigo;
/*  656: 922 */     this.codigoAlterno = codigoAlterno;
/*  657: 923 */     this.nombre = nombre;
/*  658: 924 */     this.nombreComercial = nombreComercial;
/*  659: 925 */     this.descripcion = descripcion;
/*  660: 926 */     this.tipoProducto = tipoProducto;
/*  661: 927 */     this.tipoCosto = tipoCosto;
/*  662: 928 */     this.indicadorProduccion = indicadorProduccion;
/*  663: 929 */     this.indicadorVenta = indicadorVenta;
/*  664: 930 */     this.indicadorCompra = indicadorCompra;
/*  665: 931 */     this.indicadorImpuestos = indicadorImpuestos;
/*  666: 932 */     this.indicadorLote = indicadorLote;
/*  667:     */     
/*  668: 934 */     this.idCategoriaProducto = Integer.valueOf(idCategoriaProducto);
/*  669: 935 */     this.codigoCategoriaProducto = codigoCategoriaProducto;
/*  670: 936 */     this.nombreCategoriaProducto = nombreCategoriaProducto;
/*  671:     */     
/*  672: 938 */     this.idSubcategoriaProducto = idSubcategoriaProducto;
/*  673: 939 */     this.codigoSubcategoriaProducto = codigoSubcategoriaProducto;
/*  674: 940 */     this.nombreSubcategoriaProducto = nombreSubcategoriaProducto;
/*  675:     */     
/*  676: 942 */     this.idCategoriaImpuesto = idCategoriaImpuesto;
/*  677: 943 */     this.codigoCategoriaImpuesto = codigoCategoriaImpuesto;
/*  678: 944 */     this.nombreCategoriaImpuesto = nombreCategoriaImpuesto;
/*  679:     */     
/*  680: 946 */     this.idUnidad = idUnidad;
/*  681: 947 */     this.codigoUnidad = codigoUnidad;
/*  682: 948 */     this.nombreUnidad = nombreUnidad;
/*  683: 949 */     setUnidad(new Unidad(idUnidad.intValue(), codigoUnidad, nombreUnidad));
/*  684: 950 */     getUnidad().setNumeroDecimales(numeroDecimalesUnidad);
/*  685: 951 */     this.idUnidadAlmacenamiento = idUnidadAlmacenamiento;
/*  686: 952 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/*  687: 953 */     this.nombreUnidadAlmacenamiento = nombreUnidadAlmacenamiento;
/*  688:     */     
/*  689: 955 */     this.idUnidadVenta = idUnidadVenta;
/*  690: 956 */     this.codigoUnidadVenta = codigoUnidadVenta;
/*  691: 957 */     this.nombreUnidadVenta = nombreUnidadVenta;
/*  692:     */     
/*  693: 959 */     this.idUnidadCompra = idUnidadCompra;
/*  694: 960 */     this.codigoUnidadCompra = codigoUnidadCompra;
/*  695: 961 */     this.nombreUnidadCompra = nombreUnidadCompra;
/*  696:     */     
/*  697: 963 */     this.idBodegaVenta = idBodegaVenta;
/*  698: 964 */     this.codigoBodegaVenta = codigoBodegaVenta;
/*  699: 965 */     this.nombreBodegaVenta = nombreBodegaVenta;
/*  700:     */     
/*  701: 967 */     this.idBodegaCompra = idBodegaCompra;
/*  702: 968 */     this.codigoBodegaCompra = codigoBodegaCompra;
/*  703: 969 */     this.nombreBodegaCompra = nombreBodegaCompra;
/*  704:     */     
/*  705: 971 */     this.idPartidaArancelaria = idPartidaArancelaria;
/*  706: 972 */     this.codigoPartidaArancelaria = codigoPartidaArancelaria;
/*  707: 973 */     this.nombrePartidaArancelaria = nombrePartidaArancelaria;
/*  708:     */     
/*  709: 975 */     this.indicadorManejoPeso = indicadorManejoPeso;
/*  710:     */     
/*  711: 977 */     this.subcategoriaProducto = new SubcategoriaProducto(idSubcategoriaProducto.intValue(), codigoSubcategoriaProducto, nombreSubcategoriaProducto);
/*  712: 978 */     this.subcategoriaProducto.setCategoriaProducto(new CategoriaProducto(idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto));
/*  713:     */     
/*  714: 980 */     this.categoriaImpuesto = (idCategoriaImpuesto != null ? new CategoriaImpuesto(idCategoriaImpuesto.intValue(), codigoCategoriaImpuesto, nombreCategoriaImpuesto) : null);
/*  715: 981 */     this.unidad = new Unidad(idUnidad.intValue(), codigoUnidad, nombreUnidad);
/*  716:     */     
/*  717: 983 */     this.unidadAlmacenamiento = (idUnidadAlmacenamiento != null ? new Unidad(idUnidadAlmacenamiento.intValue(), codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento) : null);
/*  718: 984 */     this.unidadVenta = (idUnidadVenta != null ? new Unidad(idUnidadVenta.intValue(), codigoUnidadVenta, nombreUnidadVenta) : null);
/*  719: 985 */     this.unidadCompra = (idUnidadCompra != null ? new Unidad(idUnidadCompra.intValue(), codigoUnidadCompra, nombreUnidadCompra) : null);
/*  720: 986 */     this.bodegaVenta = (idBodegaVenta != null ? new Bodega(idBodegaVenta.intValue(), codigoBodegaVenta, nombreBodegaVenta) : null);
/*  721: 987 */     this.bodegaCompra = (idBodegaCompra != null ? new Bodega(idBodegaCompra.intValue(), codigoBodegaCompra, nombreBodegaCompra) : null);
/*  722:     */     
/*  723: 989 */     this.partidaArancelaria = (idPartidaArancelaria != null ? new PartidaArancelaria(idPartidaArancelaria.intValue(), codigoPartidaArancelaria, nombrePartidaArancelaria) : null);
/*  724:     */     
/*  725: 991 */     this.precioUltimaCompra = precioUltimaCompra;
/*  726: 992 */     this.precioUltimaVenta = precioUltimaVenta;
/*  727: 993 */     this.indicadorSerie = Boolean.valueOf(indicadorSerie == null ? false : indicadorSerie.booleanValue());
/*  728: 994 */     this.cantidadProduccion = cantidadProduccion;
/*  729:1000 */     if (idPresentacionProducto != null)
/*  730:     */     {
/*  731:1001 */       this.presentacionProducto = new PresentacionProducto();
/*  732:1002 */       this.presentacionProducto.setIdPresentacionProducto(idPresentacionProducto.intValue());
/*  733:1003 */       this.presentacionProducto.setNombre(nombrePresentacionProducto);
/*  734:1004 */       this.presentacionProducto.setCantidadUnidades(cantidadUnidadesPresentacionProducto);
/*  735:     */     }
/*  736:1007 */     this.indicadorConsumoDirecto = indicadorConsumoDirecto.booleanValue();
/*  737:1008 */     this.indicadorPesoBalanza = indicadorPesoBalanza.booleanValue();
/*  738:1009 */     this.subsidio = subsidio;
/*  739:1010 */     this.indicadorPorcentajeIce = indicadorPorcentajeIce;
/*  740:1011 */     this.ice = ice;
/*  741:1012 */     this.codigoIce = codigoIce;
/*  742:     */   }
/*  743:     */   
/*  744:     */   public Producto(int idProducto, String codigo, String codigoAlterno, String nombre, String nombreComercial, String descripcion, TipoProducto tipoProducto, TipoCosto tipoCosto, boolean indicadorProduccion, boolean indicadorVenta, boolean indicadorCompra, boolean indicadorImpuestos, boolean indicadorLote, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaImpuesto, String codigoCategoriaImpuesto, String nombreCategoriaImpuesto, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadCompra, String codigoUnidadCompra, String nombreUnidadCompra, Integer idBodegaVenta, String codigoBodegaVenta, String nombreBodegaVenta, Integer idBodegaCompra, String codigoBodegaCompra, String nombreBodegaCompra, Integer idPartidaArancelaria, String codigoPartidaArancelaria, String nombrePartidaArancelaria, BigDecimal precioUltimaCompra, BigDecimal precioUltimaVenta, Boolean indicadorManejoPeso, Boolean indicadorSerie, BigDecimal cantidadProduccion, Integer idPresentacionProducto, String nombrePresentacionProducto, BigDecimal cantidadUnidadesPresentacionProducto, Integer numeroDecimalesUnidad, Boolean indicadorConsumoDirecto, Boolean indicadorPesoBalanza, BigDecimal subsidio, boolean indicadorPorcentajeIce, BigDecimal ice, String codigoIce, boolean indicadorManejaUnidadInformativa, Integer idUnidadInformativa, String codigoUnidadInformativa, String nombreUnidadInformativa, TipoUnidadMedida tipoUnidadInformativa, String imagen, String codigoBarras, String prefijosLote, String atributo1, String atributo2, String atributo3, String atributo4, String atributo5, String atributo6, String atributo7, String atributo8, String atributo9, String atributo10)
/*  745:     */   {
/*  746:1037 */     this(idProducto, codigo, codigoAlterno, nombre, nombreComercial, descripcion, tipoProducto, tipoCosto, indicadorProduccion, indicadorVenta, indicadorCompra, indicadorImpuestos, indicadorLote, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaImpuesto, codigoCategoriaImpuesto, nombreCategoriaImpuesto, idUnidad, codigoUnidad, nombreUnidad, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadCompra, codigoUnidadCompra, nombreUnidadCompra, idBodegaVenta, codigoBodegaVenta, nombreBodegaVenta, idBodegaCompra, codigoBodegaCompra, nombreBodegaCompra, idPartidaArancelaria, codigoPartidaArancelaria, nombrePartidaArancelaria, precioUltimaCompra, precioUltimaVenta, indicadorManejoPeso, indicadorSerie, cantidadProduccion, idPresentacionProducto, nombrePresentacionProducto, cantidadUnidadesPresentacionProducto, numeroDecimalesUnidad, indicadorConsumoDirecto, indicadorPesoBalanza, subsidio, indicadorPorcentajeIce, ice, codigoIce, atributo1, atributo2, atributo3, atributo4, atributo5, atributo6, atributo7, atributo8, atributo9, atributo10);
/*  747:     */     
/*  748:     */ 
/*  749:     */ 
/*  750:     */ 
/*  751:     */ 
/*  752:     */ 
/*  753:     */ 
/*  754:     */ 
/*  755:     */ 
/*  756:     */ 
/*  757:1048 */     this.indicadorManejaUnidadInformativa = indicadorManejaUnidadInformativa;
/*  758:1049 */     this.imagen = imagen;
/*  759:1050 */     this.codigoBarras = codigoBarras;
/*  760:1051 */     this.prefijosLote = prefijosLote;
/*  761:1052 */     if (idUnidadInformativa != null)
/*  762:     */     {
/*  763:1053 */       this.unidadInformativa = new Unidad();
/*  764:1054 */       this.unidadInformativa.setIdUnidad(idUnidadInformativa.intValue());
/*  765:1055 */       this.unidadInformativa.setCodigo(codigoUnidadInformativa);
/*  766:1056 */       this.unidadInformativa.setNombre(nombreUnidadInformativa);
/*  767:1057 */       this.unidadInformativa.setTipoUnidadMedida(tipoUnidadInformativa);
/*  768:     */     }
/*  769:     */   }
/*  770:     */   
/*  771:     */   public Producto(int idProducto, String codigo, String codigoAlterno, String nombre, String nombreComercial, String descripcion, TipoProducto tipoProducto, TipoCosto tipoCosto, boolean indicadorProduccion, boolean indicadorVenta, boolean indicadorCompra, boolean indicadorImpuestos, boolean indicadorLote, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaImpuesto, String codigoCategoriaImpuesto, String nombreCategoriaImpuesto, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadCompra, String codigoUnidadCompra, String nombreUnidadCompra, Integer idBodegaVenta, String codigoBodegaVenta, String nombreBodegaVenta, Integer idBodegaCompra, String codigoBodegaCompra, String nombreBodegaCompra, Integer idPartidaArancelaria, String codigoPartidaArancelaria, String nombrePartidaArancelaria, BigDecimal precioUltimaCompra, BigDecimal precioUltimaVenta, Boolean indicadorManejoPeso, Boolean indicadorSerie, BigDecimal cantidadProduccion, Integer idPresentacionProducto, String nombrePresentacionProducto, BigDecimal cantidadUnidadesPresentacionProducto, Integer numeroDecimalesUnidad, Boolean indicadorConsumoDirecto, Boolean indicadorPesoBalanza, BigDecimal subsidio, boolean indicadorPorcentajeIce, BigDecimal ice, String codigoIce, String atributo1, String atributo2, String atributo3, String atributo4, String atributo5, String atributo6, String atributo7, String atributo8, String atributo9, String atributo10)
/*  772:     */   {
/*  773:1079 */     this(idProducto, codigo, codigoAlterno, nombre, nombreComercial, descripcion, tipoProducto, tipoCosto, indicadorProduccion, indicadorVenta, indicadorCompra, indicadorImpuestos, indicadorLote, idCategoriaProducto
/*  774:1080 */       .intValue(), codigoCategoriaProducto, nombreCategoriaProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaImpuesto, codigoCategoriaImpuesto, nombreCategoriaImpuesto, idUnidad, codigoUnidad, nombreUnidad, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadCompra, codigoUnidadCompra, nombreUnidadCompra, idBodegaVenta, codigoBodegaVenta, nombreBodegaVenta, idBodegaCompra, codigoBodegaCompra, nombreBodegaCompra, idPartidaArancelaria, codigoPartidaArancelaria, nombrePartidaArancelaria, precioUltimaCompra, precioUltimaVenta, indicadorManejoPeso
/*  775:     */       
/*  776:     */ 
/*  777:     */ 
/*  778:     */ 
/*  779:1085 */       .booleanValue(), indicadorSerie, cantidadProduccion, idPresentacionProducto, nombrePresentacionProducto, cantidadUnidadesPresentacionProducto, numeroDecimalesUnidad, indicadorConsumoDirecto, indicadorPesoBalanza, subsidio, indicadorPorcentajeIce, ice, codigoIce);
/*  780:     */     
/*  781:     */ 
/*  782:     */ 
/*  783:     */ 
/*  784:     */ 
/*  785:     */ 
/*  786:     */ 
/*  787:     */ 
/*  788:1094 */     this.atributo1 = atributo1;
/*  789:1095 */     this.atributo2 = atributo2;
/*  790:1096 */     this.atributo3 = atributo3;
/*  791:1097 */     this.atributo4 = atributo4;
/*  792:1098 */     this.atributo5 = atributo5;
/*  793:1099 */     this.atributo6 = atributo6;
/*  794:1100 */     this.atributo7 = atributo7;
/*  795:1101 */     this.atributo8 = atributo8;
/*  796:1102 */     this.atributo9 = atributo9;
/*  797:1103 */     this.atributo10 = atributo10;
/*  798:1105 */     if (idPresentacionProducto != null)
/*  799:     */     {
/*  800:1106 */       this.presentacionProducto = new PresentacionProducto();
/*  801:1107 */       this.presentacionProducto.setIdPresentacionProducto(idPresentacionProducto.intValue());
/*  802:1108 */       this.presentacionProducto.setNombre(nombrePresentacionProducto);
/*  803:1109 */       this.presentacionProducto.setCantidadUnidades(cantidadUnidadesPresentacionProducto);
/*  804:     */     }
/*  805:     */   }
/*  806:     */   
/*  807:     */   public Producto(int idProducto, String codigo, String codigoAlterno, String nombre, String nombreComercial, String descripcion, String nombreCategoriaProducto, String nombreSubcategoriaProducto, String codigoUnidad, String codigoUnidadAlmacenamiento, String codigoUnidadVenta, String codigoUnidadCompra)
/*  808:     */   {
/*  809:1118 */     this.idProducto = idProducto;
/*  810:1119 */     this.codigo = codigo;
/*  811:1120 */     this.codigoAlterno = codigoAlterno;
/*  812:1121 */     this.nombre = nombre;
/*  813:1122 */     this.nombreComercial = nombreComercial;
/*  814:1123 */     this.descripcion = descripcion;
/*  815:1124 */     this.nombreCategoriaProducto = nombreCategoriaProducto;
/*  816:1125 */     this.nombreSubcategoriaProducto = nombreSubcategoriaProducto;
/*  817:1126 */     this.codigoUnidad = codigoUnidad;
/*  818:1127 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/*  819:1128 */     this.codigoUnidadVenta = codigoUnidadVenta;
/*  820:1129 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/*  821:1130 */     this.codigoUnidadCompra = codigoUnidadCompra;
/*  822:     */   }
/*  823:     */   
/*  824:     */   public Producto(int idProducto, String codigo, String codigoAlterno, String nombre, String nombreComercial, String descripcion, String nombreCategoriaProducto, String nombreSubcategoriaProducto, String codigoUnidad, String codigoUnidadAlmacenamiento, String codigoUnidadVenta, String codigoUnidadCompra, String atributo1, String atributo2, String atributo3, String atributo4, String atributo5, String atributo6, String atributo7, String atributo8, String atributo9, String atributo10)
/*  825:     */   {
/*  826:1139 */     this.idProducto = idProducto;
/*  827:1140 */     this.codigo = codigo;
/*  828:1141 */     this.codigoAlterno = codigoAlterno;
/*  829:1142 */     this.nombre = nombre;
/*  830:1143 */     this.nombreComercial = nombreComercial;
/*  831:1144 */     this.descripcion = descripcion;
/*  832:1145 */     this.nombreCategoriaProducto = nombreCategoriaProducto;
/*  833:1146 */     this.nombreSubcategoriaProducto = nombreSubcategoriaProducto;
/*  834:1147 */     this.codigoUnidad = codigoUnidad;
/*  835:1148 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/*  836:1149 */     this.codigoUnidadVenta = codigoUnidadVenta;
/*  837:1150 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/*  838:1151 */     this.codigoUnidadCompra = codigoUnidadCompra;
/*  839:1152 */     this.atributo1 = atributo1;
/*  840:1153 */     this.atributo2 = atributo2;
/*  841:1154 */     this.atributo3 = atributo3;
/*  842:1155 */     this.atributo4 = atributo4;
/*  843:1156 */     this.atributo5 = atributo5;
/*  844:1157 */     this.atributo6 = atributo6;
/*  845:1158 */     this.atributo7 = atributo7;
/*  846:1159 */     this.atributo8 = atributo8;
/*  847:1160 */     this.atributo9 = atributo9;
/*  848:1161 */     this.atributo10 = atributo10;
/*  849:     */   }
/*  850:     */   
/*  851:     */   public Producto() {}
/*  852:     */   
/*  853:     */   public Producto(int idProducto)
/*  854:     */   {
/*  855:1169 */     this.idProducto = idProducto;
/*  856:     */   }
/*  857:     */   
/*  858:     */   public Producto(int idProducto, String codigo, Integer versionCosteo)
/*  859:     */   {
/*  860:1173 */     this.idProducto = idProducto;
/*  861:1174 */     this.codigo = codigo;
/*  862:1175 */     this.versionCosteo = versionCosteo.intValue();
/*  863:     */   }
/*  864:     */   
/*  865:     */   public List<String> getCamposAuditables()
/*  866:     */   {
/*  867:1179 */     ArrayList<String> lista = new ArrayList();
/*  868:1180 */     lista.add("unidad");
/*  869:1181 */     lista.add("subcategoriaProducto");
/*  870:1182 */     lista.add("tipoProducto");
/*  871:1183 */     lista.add("tipoCosto");
/*  872:1184 */     lista.add("codigo");
/*  873:1185 */     lista.add("codigoAlterno");
/*  874:1186 */     lista.add("codigoComercial");
/*  875:1187 */     lista.add("codigoBarras");
/*  876:1188 */     lista.add("nombre");
/*  877:1189 */     lista.add("nombre_comercial");
/*  878:1190 */     lista.add("costoEstandar");
/*  879:1191 */     lista.add("precioReferencial");
/*  880:1192 */     lista.add("volumen");
/*  881:1193 */     lista.add("indicadorProduccion");
/*  882:1194 */     lista.add("indicadorVenta");
/*  883:1195 */     lista.add("indicadorCompra");
/*  884:1196 */     lista.add("indicadorImpuestos");
/*  885:1197 */     lista.add("imagen");
/*  886:1198 */     lista.add("activo");
/*  887:1199 */     lista.add("cantidadMinima");
/*  888:1200 */     lista.add("cantidadMaxima");
/*  889:     */     
/*  890:1202 */     return lista;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public int getId()
/*  894:     */   {
/*  895:1207 */     return getIdProducto();
/*  896:     */   }
/*  897:     */   
/*  898:     */   public int getIdProducto()
/*  899:     */   {
/*  900:1216 */     return this.idProducto;
/*  901:     */   }
/*  902:     */   
/*  903:     */   public void setIdProducto(int idProducto)
/*  904:     */   {
/*  905:1226 */     this.idProducto = idProducto;
/*  906:     */   }
/*  907:     */   
/*  908:     */   public Unidad getUnidad()
/*  909:     */   {
/*  910:1235 */     return this.unidad;
/*  911:     */   }
/*  912:     */   
/*  913:     */   public void setUnidad(Unidad unidad)
/*  914:     */   {
/*  915:1245 */     this.unidad = unidad;
/*  916:     */   }
/*  917:     */   
/*  918:     */   public SubcategoriaProducto getSubcategoriaProducto()
/*  919:     */   {
/*  920:1254 */     return this.subcategoriaProducto;
/*  921:     */   }
/*  922:     */   
/*  923:     */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/*  924:     */   {
/*  925:1264 */     this.subcategoriaProducto = subcategoriaProducto;
/*  926:     */   }
/*  927:     */   
/*  928:     */   public int getIdOrganizacion()
/*  929:     */   {
/*  930:1273 */     return this.idOrganizacion;
/*  931:     */   }
/*  932:     */   
/*  933:     */   public void setIdOrganizacion(int idOrganizacion)
/*  934:     */   {
/*  935:1283 */     this.idOrganizacion = idOrganizacion;
/*  936:     */   }
/*  937:     */   
/*  938:     */   public int getIdSucursal()
/*  939:     */   {
/*  940:1292 */     return this.idSucursal;
/*  941:     */   }
/*  942:     */   
/*  943:     */   public void setIdSucursal(int idSucursal)
/*  944:     */   {
/*  945:1302 */     this.idSucursal = idSucursal;
/*  946:     */   }
/*  947:     */   
/*  948:     */   public TipoProducto getTipoProducto()
/*  949:     */   {
/*  950:1311 */     return this.tipoProducto;
/*  951:     */   }
/*  952:     */   
/*  953:     */   public void setTipoProducto(TipoProducto tipoProducto)
/*  954:     */   {
/*  955:1321 */     this.tipoProducto = tipoProducto;
/*  956:     */   }
/*  957:     */   
/*  958:     */   public TipoCosto getTipoCosto()
/*  959:     */   {
/*  960:1330 */     return this.tipoCosto;
/*  961:     */   }
/*  962:     */   
/*  963:     */   public void setTipoCosto(TipoCosto tipoCosto)
/*  964:     */   {
/*  965:1340 */     this.tipoCosto = tipoCosto;
/*  966:     */   }
/*  967:     */   
/*  968:     */   public String getCodigo()
/*  969:     */   {
/*  970:1349 */     return this.codigo;
/*  971:     */   }
/*  972:     */   
/*  973:     */   public void setCodigo(String codigo)
/*  974:     */   {
/*  975:1359 */     this.codigo = codigo;
/*  976:     */   }
/*  977:     */   
/*  978:     */   public String getCodigoAlterno()
/*  979:     */   {
/*  980:1368 */     return this.codigoAlterno;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public void setCodigoAlterno(String codigoAlterno)
/*  984:     */   {
/*  985:1378 */     this.codigoAlterno = codigoAlterno;
/*  986:     */   }
/*  987:     */   
/*  988:     */   public String getCodigoBarras()
/*  989:     */   {
/*  990:1387 */     return this.codigoBarras;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public void setCodigoBarras(String codigoBarras)
/*  994:     */   {
/*  995:1397 */     this.codigoBarras = codigoBarras;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public String getNombre()
/*  999:     */   {
/* 1000:1406 */     return this.nombre;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public void setNombre(String nombre)
/* 1004:     */   {
/* 1005:1416 */     this.nombre = nombre;
/* 1006:     */   }
/* 1007:     */   
/* 1008:     */   public String getNombreComercial()
/* 1009:     */   {
/* 1010:1425 */     return this.nombreComercial;
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public void setNombreComercial(String nombreComercial)
/* 1014:     */   {
/* 1015:1435 */     this.nombreComercial = nombreComercial;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public BigDecimal getCostoEstandar()
/* 1019:     */   {
/* 1020:1444 */     return this.costoEstandar;
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public void setCostoEstandar(BigDecimal costoEstandar)
/* 1024:     */   {
/* 1025:1454 */     this.costoEstandar = costoEstandar;
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public boolean isIndicadorProduccion()
/* 1029:     */   {
/* 1030:1463 */     return this.indicadorProduccion;
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   public void setIndicadorProduccion(boolean indicadorProduccion)
/* 1034:     */   {
/* 1035:1473 */     this.indicadorProduccion = indicadorProduccion;
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   public boolean isIndicadorVenta()
/* 1039:     */   {
/* 1040:1482 */     return this.indicadorVenta;
/* 1041:     */   }
/* 1042:     */   
/* 1043:     */   public void setIndicadorVenta(boolean indicadorVenta)
/* 1044:     */   {
/* 1045:1492 */     this.indicadorVenta = indicadorVenta;
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public boolean isIndicadorCompra()
/* 1049:     */   {
/* 1050:1501 */     return this.indicadorCompra;
/* 1051:     */   }
/* 1052:     */   
/* 1053:     */   public void setIndicadorCompra(boolean indicadorCompra)
/* 1054:     */   {
/* 1055:1511 */     this.indicadorCompra = indicadorCompra;
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   public boolean isIndicadorImpuestos()
/* 1059:     */   {
/* 1060:1520 */     return this.indicadorImpuestos;
/* 1061:     */   }
/* 1062:     */   
/* 1063:     */   public void setIndicadorImpuestos(boolean indicadorImpuestos)
/* 1064:     */   {
/* 1065:1530 */     this.indicadorImpuestos = indicadorImpuestos;
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   public boolean isActivo()
/* 1069:     */   {
/* 1070:1539 */     return this.activo;
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public void setActivo(boolean activo)
/* 1074:     */   {
/* 1075:1549 */     this.activo = activo;
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   public boolean isPredeterminado()
/* 1079:     */   {
/* 1080:1558 */     return this.predeterminado;
/* 1081:     */   }
/* 1082:     */   
/* 1083:     */   public void setPredeterminado(boolean predeterminado)
/* 1084:     */   {
/* 1085:1568 */     this.predeterminado = predeterminado;
/* 1086:     */   }
/* 1087:     */   
/* 1088:     */   public CategoriaImpuesto getCategoriaImpuesto()
/* 1089:     */   {
/* 1090:1577 */     return this.categoriaImpuesto;
/* 1091:     */   }
/* 1092:     */   
/* 1093:     */   public void setCategoriaImpuesto(CategoriaImpuesto categoriaImpuesto)
/* 1094:     */   {
/* 1095:1587 */     this.categoriaImpuesto = categoriaImpuesto;
/* 1096:     */   }
/* 1097:     */   
/* 1098:     */   public String getCodigoComercial()
/* 1099:     */   {
/* 1100:1596 */     return this.codigoComercial;
/* 1101:     */   }
/* 1102:     */   
/* 1103:     */   public void setCodigoComercial(String codigoComercial)
/* 1104:     */   {
/* 1105:1606 */     this.codigoComercial = codigoComercial;
/* 1106:     */   }
/* 1107:     */   
/* 1108:     */   public void setPeso(BigDecimal peso)
/* 1109:     */   {
/* 1110:1616 */     this.peso = peso;
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   public void setVolumen(BigDecimal volumen)
/* 1114:     */   {
/* 1115:1626 */     this.volumen = volumen;
/* 1116:     */   }
/* 1117:     */   
/* 1118:     */   public BigDecimal getPeso()
/* 1119:     */   {
/* 1120:1635 */     return this.peso;
/* 1121:     */   }
/* 1122:     */   
/* 1123:     */   public BigDecimal getVolumen()
/* 1124:     */   {
/* 1125:1644 */     return this.volumen;
/* 1126:     */   }
/* 1127:     */   
/* 1128:     */   public ConjuntoAtributo getConjuntoAtributo()
/* 1129:     */   {
/* 1130:1653 */     return this.conjuntoAtributo;
/* 1131:     */   }
/* 1132:     */   
/* 1133:     */   public void setConjuntoAtributo(ConjuntoAtributo conjuntoAtributo)
/* 1134:     */   {
/* 1135:1663 */     this.conjuntoAtributo = conjuntoAtributo;
/* 1136:     */   }
/* 1137:     */   
/* 1138:     */   public List<ProductoAtributo> getListaProductoAtributo()
/* 1139:     */   {
/* 1140:1672 */     return this.listaProductoAtributo;
/* 1141:     */   }
/* 1142:     */   
/* 1143:     */   public void setListaProductoAtributo(List<ProductoAtributo> listaProductoAtributo)
/* 1144:     */   {
/* 1145:1682 */     this.listaProductoAtributo = listaProductoAtributo;
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   public String toString()
/* 1149:     */   {
/* 1150:1687 */     return this.nombre;
/* 1151:     */   }
/* 1152:     */   
/* 1153:     */   public String getImagen()
/* 1154:     */   {
/* 1155:1691 */     return this.imagen;
/* 1156:     */   }
/* 1157:     */   
/* 1158:     */   public void setImagen(String imagen)
/* 1159:     */   {
/* 1160:1695 */     this.imagen = imagen;
/* 1161:     */   }
/* 1162:     */   
/* 1163:     */   public boolean isTraIndicadorServicio()
/* 1164:     */   {
/* 1165:1699 */     return (this.tipoProducto == TipoProducto.SERVICIO) || (this.tipoProducto == TipoProducto.ARTICULO_NO_INVENTARIABLE);
/* 1166:     */   }
/* 1167:     */   
/* 1168:     */   public BigDecimal getTraDescuentoPorcentajeMaximo()
/* 1169:     */   {
/* 1170:1708 */     return this.traDescuentoPorcentajeMaximo;
/* 1171:     */   }
/* 1172:     */   
/* 1173:     */   public void setTraDescuentoPorcentajeMaximo(BigDecimal traDescuentoPorcentajeMaximo)
/* 1174:     */   {
/* 1175:1718 */     this.traDescuentoPorcentajeMaximo = traDescuentoPorcentajeMaximo;
/* 1176:     */   }
/* 1177:     */   
/* 1178:     */   public BigDecimal getCantidadMinima()
/* 1179:     */   {
/* 1180:1727 */     return this.cantidadMinima;
/* 1181:     */   }
/* 1182:     */   
/* 1183:     */   public void setCantidadMinima(BigDecimal cantidadMinima)
/* 1184:     */   {
/* 1185:1737 */     this.cantidadMinima = cantidadMinima;
/* 1186:     */   }
/* 1187:     */   
/* 1188:     */   public BigDecimal getCantidadMaxima()
/* 1189:     */   {
/* 1190:1746 */     return this.cantidadMaxima;
/* 1191:     */   }
/* 1192:     */   
/* 1193:     */   public void setCantidadMaxima(BigDecimal cantidadMaxima)
/* 1194:     */   {
/* 1195:1756 */     this.cantidadMaxima = cantidadMaxima;
/* 1196:     */   }
/* 1197:     */   
/* 1198:     */   public List<ProductoMaterial> getListaProductoMaterial()
/* 1199:     */   {
/* 1200:1763 */     return this.listaProductoMaterial;
/* 1201:     */   }
/* 1202:     */   
/* 1203:     */   public void setListaProductoMaterial(List<ProductoMaterial> listaProductoMaterial)
/* 1204:     */   {
/* 1205:1771 */     this.listaProductoMaterial = listaProductoMaterial;
/* 1206:     */   }
/* 1207:     */   
/* 1208:     */   public List<ProductoSustituto> getListaProductoSustituto()
/* 1209:     */   {
/* 1210:1780 */     return this.listaProductoSustituto;
/* 1211:     */   }
/* 1212:     */   
/* 1213:     */   public void setListaProductoSustituto(List<ProductoSustituto> listaProductoSustituto)
/* 1214:     */   {
/* 1215:1790 */     this.listaProductoSustituto = listaProductoSustituto;
/* 1216:     */   }
/* 1217:     */   
/* 1218:     */   public PartidaArancelaria getPartidaArancelaria()
/* 1219:     */   {
/* 1220:1799 */     return this.partidaArancelaria;
/* 1221:     */   }
/* 1222:     */   
/* 1223:     */   public void setPartidaArancelaria(PartidaArancelaria partidaArancelaria)
/* 1224:     */   {
/* 1225:1809 */     this.partidaArancelaria = partidaArancelaria;
/* 1226:     */   }
/* 1227:     */   
/* 1228:     */   public boolean isIndicadorLote()
/* 1229:     */   {
/* 1230:1818 */     return this.indicadorLote;
/* 1231:     */   }
/* 1232:     */   
/* 1233:     */   public void setIndicadorLote(boolean indicadorLote)
/* 1234:     */   {
/* 1235:1828 */     this.indicadorLote = indicadorLote;
/* 1236:     */   }
/* 1237:     */   
/* 1238:     */   public boolean isIndicadorPostConsumo()
/* 1239:     */   {
/* 1240:1837 */     return this.indicadorPostConsumo;
/* 1241:     */   }
/* 1242:     */   
/* 1243:     */   public void setIndicadorPostConsumo(boolean indicadorPostConsumo)
/* 1244:     */   {
/* 1245:1847 */     this.indicadorPostConsumo = indicadorPostConsumo;
/* 1246:     */   }
/* 1247:     */   
/* 1248:     */   public Unidad getUnidadVenta()
/* 1249:     */   {
/* 1250:1856 */     return this.unidadVenta;
/* 1251:     */   }
/* 1252:     */   
/* 1253:     */   public void setUnidadVenta(Unidad unidadVenta)
/* 1254:     */   {
/* 1255:1866 */     this.unidadVenta = unidadVenta;
/* 1256:     */   }
/* 1257:     */   
/* 1258:     */   public Unidad getUnidadCompra()
/* 1259:     */   {
/* 1260:1875 */     return this.unidadCompra;
/* 1261:     */   }
/* 1262:     */   
/* 1263:     */   public void setUnidadCompra(Unidad unidadCompra)
/* 1264:     */   {
/* 1265:1885 */     this.unidadCompra = unidadCompra;
/* 1266:     */   }
/* 1267:     */   
/* 1268:     */   public int getCantidadMinimaVenta()
/* 1269:     */   {
/* 1270:1894 */     return this.cantidadMinimaVenta;
/* 1271:     */   }
/* 1272:     */   
/* 1273:     */   public void setCantidadMinimaVenta(int cantidadMinimaVenta)
/* 1274:     */   {
/* 1275:1904 */     this.cantidadMinimaVenta = cantidadMinimaVenta;
/* 1276:     */   }
/* 1277:     */   
/* 1278:     */   public BigDecimal getPrecioReferencialVenta()
/* 1279:     */   {
/* 1280:1913 */     return this.precioReferencialVenta;
/* 1281:     */   }
/* 1282:     */   
/* 1283:     */   public void setPrecioReferencialVenta(BigDecimal precioReferencialVenta)
/* 1284:     */   {
/* 1285:1923 */     this.precioReferencialVenta = precioReferencialVenta;
/* 1286:     */   }
/* 1287:     */   
/* 1288:     */   public BigDecimal getPrecioUltimaVenta()
/* 1289:     */   {
/* 1290:1932 */     return this.precioUltimaVenta;
/* 1291:     */   }
/* 1292:     */   
/* 1293:     */   public void setPrecioUltimaVenta(BigDecimal precioUltimaVenta)
/* 1294:     */   {
/* 1295:1942 */     this.precioUltimaVenta = precioUltimaVenta;
/* 1296:     */   }
/* 1297:     */   
/* 1298:     */   public Bodega getBodegaVenta()
/* 1299:     */   {
/* 1300:1951 */     return this.bodegaVenta;
/* 1301:     */   }
/* 1302:     */   
/* 1303:     */   public void setBodegaVenta(Bodega bodegaVenta)
/* 1304:     */   {
/* 1305:1961 */     this.bodegaVenta = bodegaVenta;
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   public String getCodigoFiscal()
/* 1309:     */   {
/* 1310:1970 */     return this.codigoFiscal;
/* 1311:     */   }
/* 1312:     */   
/* 1313:     */   public void setCodigoFiscal(String codigoFiscal)
/* 1314:     */   {
/* 1315:1980 */     this.codigoFiscal = codigoFiscal;
/* 1316:     */   }
/* 1317:     */   
/* 1318:     */   public BigDecimal getPrecioReferencialCompra()
/* 1319:     */   {
/* 1320:1989 */     return this.precioReferencialCompra;
/* 1321:     */   }
/* 1322:     */   
/* 1323:     */   public void setPrecioReferencialCompra(BigDecimal precioReferencialCompra)
/* 1324:     */   {
/* 1325:1999 */     this.precioReferencialCompra = precioReferencialCompra;
/* 1326:     */   }
/* 1327:     */   
/* 1328:     */   public Bodega getBodegaCompra()
/* 1329:     */   {
/* 1330:2008 */     return this.bodegaCompra;
/* 1331:     */   }
/* 1332:     */   
/* 1333:     */   public void setBodegaCompra(Bodega bodegaCompra)
/* 1334:     */   {
/* 1335:2018 */     this.bodegaCompra = bodegaCompra;
/* 1336:     */   }
/* 1337:     */   
/* 1338:     */   public BigDecimal getPrecioUltimaCompra()
/* 1339:     */   {
/* 1340:2065 */     return this.precioUltimaCompra;
/* 1341:     */   }
/* 1342:     */   
/* 1343:     */   public void setPrecioUltimaCompra(BigDecimal precioUltimaCompra)
/* 1344:     */   {
/* 1345:2075 */     this.precioUltimaCompra = precioUltimaCompra;
/* 1346:     */   }
/* 1347:     */   
/* 1348:     */   public Empresa getEmpresa()
/* 1349:     */   {
/* 1350:2084 */     return this.empresa;
/* 1351:     */   }
/* 1352:     */   
/* 1353:     */   public void setEmpresa(Empresa empresa)
/* 1354:     */   {
/* 1355:2094 */     this.empresa = empresa;
/* 1356:     */   }
/* 1357:     */   
/* 1358:     */   public boolean isIndicadorCalificaProveedor()
/* 1359:     */   {
/* 1360:2103 */     return this.indicadorCalificaProveedor;
/* 1361:     */   }
/* 1362:     */   
/* 1363:     */   public void setIndicadorCalificaProveedor(boolean indicadorCalificaProveedor)
/* 1364:     */   {
/* 1365:2113 */     this.indicadorCalificaProveedor = indicadorCalificaProveedor;
/* 1366:     */   }
/* 1367:     */   
/* 1368:     */   public List<UnidadConversion> getListaUnidadConversion()
/* 1369:     */   {
/* 1370:2122 */     return this.listaUnidadConversion;
/* 1371:     */   }
/* 1372:     */   
/* 1373:     */   public void setListaUnidadConversion(List<UnidadConversion> listaUnidadConversion)
/* 1374:     */   {
/* 1375:2132 */     this.listaUnidadConversion = listaUnidadConversion;
/* 1376:     */   }
/* 1377:     */   
/* 1378:     */   public Unidad getUnidadAlmacenamiento()
/* 1379:     */   {
/* 1380:2141 */     return this.unidadAlmacenamiento;
/* 1381:     */   }
/* 1382:     */   
/* 1383:     */   public void setUnidadAlmacenamiento(Unidad unidadAlmacenamiento)
/* 1384:     */   {
/* 1385:2151 */     this.unidadAlmacenamiento = unidadAlmacenamiento;
/* 1386:     */   }
/* 1387:     */   
/* 1388:     */   public List<Unidad> getTraListaUnidadConversion()
/* 1389:     */   {
/* 1390:2160 */     return this.traListaUnidadConversion;
/* 1391:     */   }
/* 1392:     */   
/* 1393:     */   public void setTraListaUnidadConversion(List<Unidad> traListaUnidadConversion)
/* 1394:     */   {
/* 1395:2170 */     this.traListaUnidadConversion = traListaUnidadConversion;
/* 1396:     */   }
/* 1397:     */   
/* 1398:     */   public BigDecimal getCantidadProduccion()
/* 1399:     */   {
/* 1400:2179 */     return this.cantidadProduccion;
/* 1401:     */   }
/* 1402:     */   
/* 1403:     */   public void setCantidadProduccion(BigDecimal cantidadProduccion)
/* 1404:     */   {
/* 1405:2189 */     this.cantidadProduccion = cantidadProduccion;
/* 1406:     */   }
/* 1407:     */   
/* 1408:     */   public boolean isIndicadorHorasPostConsumo()
/* 1409:     */   {
/* 1410:2198 */     return this.indicadorHorasPostConsumo;
/* 1411:     */   }
/* 1412:     */   
/* 1413:     */   public void setIndicadorHorasPostConsumo(boolean indicadorHorasPostConsumo)
/* 1414:     */   {
/* 1415:2208 */     this.indicadorHorasPostConsumo = indicadorHorasPostConsumo;
/* 1416:     */   }
/* 1417:     */   
/* 1418:     */   public boolean isIndicadorConsumoDirecto()
/* 1419:     */   {
/* 1420:2217 */     return this.indicadorConsumoDirecto;
/* 1421:     */   }
/* 1422:     */   
/* 1423:     */   public void setIndicadorConsumoDirecto(boolean indicadorConsumoDirecto)
/* 1424:     */   {
/* 1425:2227 */     this.indicadorConsumoDirecto = indicadorConsumoDirecto;
/* 1426:     */   }
/* 1427:     */   
/* 1428:     */   public BigDecimal getFactorPerdida()
/* 1429:     */   {
/* 1430:2236 */     return this.factorPerdida;
/* 1431:     */   }
/* 1432:     */   
/* 1433:     */   public void setFactorPerdida(BigDecimal factorPerdida)
/* 1434:     */   {
/* 1435:2246 */     this.factorPerdida = factorPerdida;
/* 1436:     */   }
/* 1437:     */   
/* 1438:     */   public BigDecimal getCantidadPerdida()
/* 1439:     */   {
/* 1440:2255 */     return this.cantidadPerdida;
/* 1441:     */   }
/* 1442:     */   
/* 1443:     */   public void setCantidadPerdida(BigDecimal cantidadPerdida)
/* 1444:     */   {
/* 1445:2265 */     this.cantidadPerdida = cantidadPerdida;
/* 1446:     */   }
/* 1447:     */   
/* 1448:     */   public List<ProductoRutaFabricacion> getListaProductoRutaFabricacion()
/* 1449:     */   {
/* 1450:2274 */     return this.listaProductoRutaFabricacion;
/* 1451:     */   }
/* 1452:     */   
/* 1453:     */   public void setListaProductoRutaFabricacion(List<ProductoRutaFabricacion> listaProductoRutaFabricacion)
/* 1454:     */   {
/* 1455:2284 */     this.listaProductoRutaFabricacion = listaProductoRutaFabricacion;
/* 1456:     */   }
/* 1457:     */   
/* 1458:     */   public String getDescripcion()
/* 1459:     */   {
/* 1460:2288 */     return this.descripcion;
/* 1461:     */   }
/* 1462:     */   
/* 1463:     */   public void setDescripcion(String descripcion)
/* 1464:     */   {
/* 1465:2292 */     this.descripcion = descripcion;
/* 1466:     */   }
/* 1467:     */   
/* 1468:     */   public BigDecimal getTraCantidad()
/* 1469:     */   {
/* 1470:2296 */     return this.traCantidad;
/* 1471:     */   }
/* 1472:     */   
/* 1473:     */   public void setTraCantidad(BigDecimal traCantidad)
/* 1474:     */   {
/* 1475:2300 */     this.traCantidad = traCantidad;
/* 1476:     */   }
/* 1477:     */   
/* 1478:     */   public RutaFabricacion getRutaFabricacion()
/* 1479:     */   {
/* 1480:2309 */     return this.rutaFabricacion;
/* 1481:     */   }
/* 1482:     */   
/* 1483:     */   public void setRutaFabricacion(RutaFabricacion rutaFabricacion)
/* 1484:     */   {
/* 1485:2319 */     this.rutaFabricacion = rutaFabricacion;
/* 1486:     */   }
/* 1487:     */   
/* 1488:     */   public List<SubProducto> getListaSubProducto()
/* 1489:     */   {
/* 1490:2328 */     return this.listaSubProducto;
/* 1491:     */   }
/* 1492:     */   
/* 1493:     */   public void setListaSubProducto(List<SubProducto> listaSubProducto)
/* 1494:     */   {
/* 1495:2338 */     this.listaSubProducto = listaSubProducto;
/* 1496:     */   }
/* 1497:     */   
/* 1498:     */   public BigDecimal getSaldo()
/* 1499:     */   {
/* 1500:2347 */     return this.saldo;
/* 1501:     */   }
/* 1502:     */   
/* 1503:     */   public void setSaldo(BigDecimal saldo)
/* 1504:     */   {
/* 1505:2357 */     this.saldo = saldo;
/* 1506:     */   }
/* 1507:     */   
/* 1508:     */   public String getCodigoBodega()
/* 1509:     */   {
/* 1510:2366 */     return this.codigoBodega;
/* 1511:     */   }
/* 1512:     */   
/* 1513:     */   public void setCodigoBodega(String codigoBodega)
/* 1514:     */   {
/* 1515:2376 */     this.codigoBodega = codigoBodega;
/* 1516:     */   }
/* 1517:     */   
/* 1518:     */   public String getNombreBodega()
/* 1519:     */   {
/* 1520:2385 */     return this.nombreBodega;
/* 1521:     */   }
/* 1522:     */   
/* 1523:     */   public void setNombreBodega(String nombreBodega)
/* 1524:     */   {
/* 1525:2395 */     this.nombreBodega = nombreBodega;
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   public Date getFechaUltimaVenta()
/* 1529:     */   {
/* 1530:2404 */     return this.fechaUltimaVenta;
/* 1531:     */   }
/* 1532:     */   
/* 1533:     */   public void setFechaUltimaVenta(Date fechaUltimaVenta)
/* 1534:     */   {
/* 1535:2414 */     this.fechaUltimaVenta = fechaUltimaVenta;
/* 1536:     */   }
/* 1537:     */   
/* 1538:     */   public Date getFechaUltimaCompra()
/* 1539:     */   {
/* 1540:2423 */     return this.fechaUltimaCompra;
/* 1541:     */   }
/* 1542:     */   
/* 1543:     */   public void setFechaUltimaCompra(Date fechaUltimaCompra)
/* 1544:     */   {
/* 1545:2433 */     this.fechaUltimaCompra = fechaUltimaCompra;
/* 1546:     */   }
/* 1547:     */   
/* 1548:     */   public boolean isIndicadorTieneMovimientos()
/* 1549:     */   {
/* 1550:2442 */     return this.indicadorTieneMovimientos;
/* 1551:     */   }
/* 1552:     */   
/* 1553:     */   public void setIndicadorTieneMovimientos(boolean indicadorTieneMovimientos)
/* 1554:     */   {
/* 1555:2452 */     this.indicadorTieneMovimientos = indicadorTieneMovimientos;
/* 1556:     */   }
/* 1557:     */   
/* 1558:     */   public Lote getLote()
/* 1559:     */   {
/* 1560:2461 */     return this.lote;
/* 1561:     */   }
/* 1562:     */   
/* 1563:     */   public void setLote(Lote lote)
/* 1564:     */   {
/* 1565:2471 */     this.lote = lote;
/* 1566:     */   }
/* 1567:     */   
/* 1568:     */   public String getPdf()
/* 1569:     */   {
/* 1570:2478 */     return this.pdf;
/* 1571:     */   }
/* 1572:     */   
/* 1573:     */   public void setPdf(String pdf)
/* 1574:     */   {
/* 1575:2486 */     this.pdf = pdf;
/* 1576:     */   }
/* 1577:     */   
/* 1578:     */   public Bodega getTraBodega()
/* 1579:     */   {
/* 1580:2490 */     return this.traBodega;
/* 1581:     */   }
/* 1582:     */   
/* 1583:     */   public void setTraBodega(Bodega traBodega)
/* 1584:     */   {
/* 1585:2494 */     this.traBodega = traBodega;
/* 1586:     */   }
/* 1587:     */   
/* 1588:     */   public String getNombreCategoriaProducto()
/* 1589:     */   {
/* 1590:2505 */     return this.nombreCategoriaProducto;
/* 1591:     */   }
/* 1592:     */   
/* 1593:     */   public void setNombreCategoriaProducto(String nombreCategoriaProducto)
/* 1594:     */   {
/* 1595:2515 */     this.nombreCategoriaProducto = nombreCategoriaProducto;
/* 1596:     */   }
/* 1597:     */   
/* 1598:     */   public Integer getIdCategoriaProducto()
/* 1599:     */   {
/* 1600:2524 */     return this.idCategoriaProducto;
/* 1601:     */   }
/* 1602:     */   
/* 1603:     */   public void setIdCategoriaProducto(Integer idCategoriaProducto)
/* 1604:     */   {
/* 1605:2534 */     this.idCategoriaProducto = idCategoriaProducto;
/* 1606:     */   }
/* 1607:     */   
/* 1608:     */   public String getCodigoCategoriaProducto()
/* 1609:     */   {
/* 1610:2543 */     return this.codigoCategoriaProducto;
/* 1611:     */   }
/* 1612:     */   
/* 1613:     */   public void setCodigoCategoriaProducto(String codigoCategoriaProducto)
/* 1614:     */   {
/* 1615:2553 */     this.codigoCategoriaProducto = codigoCategoriaProducto;
/* 1616:     */   }
/* 1617:     */   
/* 1618:     */   public Integer getIdSubcategoriaProducto()
/* 1619:     */   {
/* 1620:2562 */     return this.idSubcategoriaProducto;
/* 1621:     */   }
/* 1622:     */   
/* 1623:     */   public void setIdSubcategoriaProducto(Integer idSubcategoriaProducto)
/* 1624:     */   {
/* 1625:2572 */     this.idSubcategoriaProducto = idSubcategoriaProducto;
/* 1626:     */   }
/* 1627:     */   
/* 1628:     */   public String getCodigoSubcategoriaProducto()
/* 1629:     */   {
/* 1630:2581 */     return this.codigoSubcategoriaProducto;
/* 1631:     */   }
/* 1632:     */   
/* 1633:     */   public void setCodigoSubcategoriaProducto(String codigoSubcategoriaProducto)
/* 1634:     */   {
/* 1635:2591 */     this.codigoSubcategoriaProducto = codigoSubcategoriaProducto;
/* 1636:     */   }
/* 1637:     */   
/* 1638:     */   public String getNombreSubcategoriaProducto()
/* 1639:     */   {
/* 1640:2600 */     return this.nombreSubcategoriaProducto;
/* 1641:     */   }
/* 1642:     */   
/* 1643:     */   public void setNombreSubcategoriaProducto(String nombreSubcategoriaProducto)
/* 1644:     */   {
/* 1645:2610 */     this.nombreSubcategoriaProducto = nombreSubcategoriaProducto;
/* 1646:     */   }
/* 1647:     */   
/* 1648:     */   public Integer getIdCategoriaImpuesto()
/* 1649:     */   {
/* 1650:2619 */     return this.idCategoriaImpuesto;
/* 1651:     */   }
/* 1652:     */   
/* 1653:     */   public void setIdCategoriaImpuesto(Integer idCategoriaImpuesto)
/* 1654:     */   {
/* 1655:2629 */     this.idCategoriaImpuesto = idCategoriaImpuesto;
/* 1656:     */   }
/* 1657:     */   
/* 1658:     */   public String getCodigoCategoriaImpuesto()
/* 1659:     */   {
/* 1660:2638 */     return this.codigoCategoriaImpuesto;
/* 1661:     */   }
/* 1662:     */   
/* 1663:     */   public void setCodigoCategoriaImpuesto(String codigoCategoriaImpuesto)
/* 1664:     */   {
/* 1665:2648 */     this.codigoCategoriaImpuesto = codigoCategoriaImpuesto;
/* 1666:     */   }
/* 1667:     */   
/* 1668:     */   public String getNombreCategoriaImpuesto()
/* 1669:     */   {
/* 1670:2657 */     return this.nombreCategoriaImpuesto;
/* 1671:     */   }
/* 1672:     */   
/* 1673:     */   public void setNombreCategoriaImpuesto(String nombreCategoriaImpuesto)
/* 1674:     */   {
/* 1675:2667 */     this.nombreCategoriaImpuesto = nombreCategoriaImpuesto;
/* 1676:     */   }
/* 1677:     */   
/* 1678:     */   public Integer getIdUnidad()
/* 1679:     */   {
/* 1680:2676 */     return this.idUnidad;
/* 1681:     */   }
/* 1682:     */   
/* 1683:     */   public void setIdUnidad(Integer idUnidad)
/* 1684:     */   {
/* 1685:2686 */     this.idUnidad = idUnidad;
/* 1686:     */   }
/* 1687:     */   
/* 1688:     */   public String getCodigoUnidad()
/* 1689:     */   {
/* 1690:2695 */     return this.codigoUnidad;
/* 1691:     */   }
/* 1692:     */   
/* 1693:     */   public void setCodigoUnidad(String codigoUnidad)
/* 1694:     */   {
/* 1695:2705 */     this.codigoUnidad = codigoUnidad;
/* 1696:     */   }
/* 1697:     */   
/* 1698:     */   public String getNombreUnidad()
/* 1699:     */   {
/* 1700:2714 */     return this.nombreUnidad;
/* 1701:     */   }
/* 1702:     */   
/* 1703:     */   public void setNombreUnidad(String nombreUnidad)
/* 1704:     */   {
/* 1705:2724 */     this.nombreUnidad = nombreUnidad;
/* 1706:     */   }
/* 1707:     */   
/* 1708:     */   public Integer getIdUnidadAlmacenamiento()
/* 1709:     */   {
/* 1710:2733 */     return this.idUnidadAlmacenamiento;
/* 1711:     */   }
/* 1712:     */   
/* 1713:     */   public void setIdUnidadAlmacenamiento(Integer idUnidadAlmacenamiento)
/* 1714:     */   {
/* 1715:2743 */     this.idUnidadAlmacenamiento = idUnidadAlmacenamiento;
/* 1716:     */   }
/* 1717:     */   
/* 1718:     */   public String getCodigoUnidadAlmacenamiento()
/* 1719:     */   {
/* 1720:2752 */     return this.codigoUnidadAlmacenamiento;
/* 1721:     */   }
/* 1722:     */   
/* 1723:     */   public void setCodigoUnidadAlmacenamiento(String codigoUnidadAlmacenamiento)
/* 1724:     */   {
/* 1725:2762 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/* 1726:     */   }
/* 1727:     */   
/* 1728:     */   public String getNombreUnidadAlmacenamiento()
/* 1729:     */   {
/* 1730:2771 */     return this.nombreUnidadAlmacenamiento;
/* 1731:     */   }
/* 1732:     */   
/* 1733:     */   public void setNombreUnidadAlmacenamiento(String nombreUnidadAlmacenamiento)
/* 1734:     */   {
/* 1735:2781 */     this.nombreUnidadAlmacenamiento = nombreUnidadAlmacenamiento;
/* 1736:     */   }
/* 1737:     */   
/* 1738:     */   public Integer getIdUnidadVenta()
/* 1739:     */   {
/* 1740:2790 */     return this.idUnidadVenta;
/* 1741:     */   }
/* 1742:     */   
/* 1743:     */   public void setIdUnidadVenta(Integer idUnidadVenta)
/* 1744:     */   {
/* 1745:2800 */     this.idUnidadVenta = idUnidadVenta;
/* 1746:     */   }
/* 1747:     */   
/* 1748:     */   public String getCodigoUnidadVenta()
/* 1749:     */   {
/* 1750:2809 */     return this.codigoUnidadVenta;
/* 1751:     */   }
/* 1752:     */   
/* 1753:     */   public void setCodigoUnidadVenta(String codigoUnidadVenta)
/* 1754:     */   {
/* 1755:2819 */     this.codigoUnidadVenta = codigoUnidadVenta;
/* 1756:     */   }
/* 1757:     */   
/* 1758:     */   public String getNombreUnidadVenta()
/* 1759:     */   {
/* 1760:2828 */     return this.nombreUnidadVenta;
/* 1761:     */   }
/* 1762:     */   
/* 1763:     */   public void setNombreUnidadVenta(String nombreUnidadVenta)
/* 1764:     */   {
/* 1765:2838 */     this.nombreUnidadVenta = nombreUnidadVenta;
/* 1766:     */   }
/* 1767:     */   
/* 1768:     */   public Integer getIdUnidadCompra()
/* 1769:     */   {
/* 1770:2847 */     return this.idUnidadCompra;
/* 1771:     */   }
/* 1772:     */   
/* 1773:     */   public void setIdUnidadCompra(Integer idUnidadCompra)
/* 1774:     */   {
/* 1775:2857 */     this.idUnidadCompra = idUnidadCompra;
/* 1776:     */   }
/* 1777:     */   
/* 1778:     */   public String getCodigoUnidadCompra()
/* 1779:     */   {
/* 1780:2866 */     return this.codigoUnidadCompra;
/* 1781:     */   }
/* 1782:     */   
/* 1783:     */   public void setCodigoUnidadCompra(String codigoUnidadCompra)
/* 1784:     */   {
/* 1785:2876 */     this.codigoUnidadCompra = codigoUnidadCompra;
/* 1786:     */   }
/* 1787:     */   
/* 1788:     */   public String getNombreUnidadCompra()
/* 1789:     */   {
/* 1790:2885 */     return this.nombreUnidadCompra;
/* 1791:     */   }
/* 1792:     */   
/* 1793:     */   public void setNombreUnidadCompra(String nombreUnidadCompra)
/* 1794:     */   {
/* 1795:2895 */     this.nombreUnidadCompra = nombreUnidadCompra;
/* 1796:     */   }
/* 1797:     */   
/* 1798:     */   public Integer getIdBodegaVenta()
/* 1799:     */   {
/* 1800:2904 */     return this.idBodegaVenta;
/* 1801:     */   }
/* 1802:     */   
/* 1803:     */   public void setIdBodegaVenta(Integer idBodegaVenta)
/* 1804:     */   {
/* 1805:2914 */     this.idBodegaVenta = idBodegaVenta;
/* 1806:     */   }
/* 1807:     */   
/* 1808:     */   public String getCodigoBodegaVenta()
/* 1809:     */   {
/* 1810:2923 */     return this.codigoBodegaVenta;
/* 1811:     */   }
/* 1812:     */   
/* 1813:     */   public void setCodigoBodegaVenta(String codigoBodegaVenta)
/* 1814:     */   {
/* 1815:2933 */     this.codigoBodegaVenta = codigoBodegaVenta;
/* 1816:     */   }
/* 1817:     */   
/* 1818:     */   public String getNombreBodegaVenta()
/* 1819:     */   {
/* 1820:2942 */     return this.nombreBodegaVenta;
/* 1821:     */   }
/* 1822:     */   
/* 1823:     */   public void setNombreBodegaVenta(String nombreBodegaVenta)
/* 1824:     */   {
/* 1825:2952 */     this.nombreBodegaVenta = nombreBodegaVenta;
/* 1826:     */   }
/* 1827:     */   
/* 1828:     */   public Integer getIdBodegaCompra()
/* 1829:     */   {
/* 1830:2961 */     return this.idBodegaCompra;
/* 1831:     */   }
/* 1832:     */   
/* 1833:     */   public void setIdBodegaCompra(Integer idBodegaCompra)
/* 1834:     */   {
/* 1835:2971 */     this.idBodegaCompra = idBodegaCompra;
/* 1836:     */   }
/* 1837:     */   
/* 1838:     */   public String getCodigoBodegaCompra()
/* 1839:     */   {
/* 1840:2980 */     return this.codigoBodegaCompra;
/* 1841:     */   }
/* 1842:     */   
/* 1843:     */   public void setCodigoBodegaCompra(String codigoBodegaCompra)
/* 1844:     */   {
/* 1845:2990 */     this.codigoBodegaCompra = codigoBodegaCompra;
/* 1846:     */   }
/* 1847:     */   
/* 1848:     */   public String getNombreBodegaCompra()
/* 1849:     */   {
/* 1850:2999 */     return this.nombreBodegaCompra;
/* 1851:     */   }
/* 1852:     */   
/* 1853:     */   public void setNombreBodegaCompra(String nombreBodegaCompra)
/* 1854:     */   {
/* 1855:3009 */     this.nombreBodegaCompra = nombreBodegaCompra;
/* 1856:     */   }
/* 1857:     */   
/* 1858:     */   public Integer getIdPartidaArancelaria()
/* 1859:     */   {
/* 1860:3018 */     return this.idPartidaArancelaria;
/* 1861:     */   }
/* 1862:     */   
/* 1863:     */   public void setIdPartidaArancelaria(Integer idPartidaArancelaria)
/* 1864:     */   {
/* 1865:3028 */     this.idPartidaArancelaria = idPartidaArancelaria;
/* 1866:     */   }
/* 1867:     */   
/* 1868:     */   public String getCodigoPartidaArancelaria()
/* 1869:     */   {
/* 1870:3037 */     return this.codigoPartidaArancelaria;
/* 1871:     */   }
/* 1872:     */   
/* 1873:     */   public void setCodigoPartidaArancelaria(String codigoPartidaArancelaria)
/* 1874:     */   {
/* 1875:3047 */     this.codigoPartidaArancelaria = codigoPartidaArancelaria;
/* 1876:     */   }
/* 1877:     */   
/* 1878:     */   public String getNombrePartidaArancelaria()
/* 1879:     */   {
/* 1880:3056 */     return this.nombrePartidaArancelaria;
/* 1881:     */   }
/* 1882:     */   
/* 1883:     */   public void setNombrePartidaArancelaria(String nombrePartidaArancelaria)
/* 1884:     */   {
/* 1885:3066 */     this.nombrePartidaArancelaria = nombrePartidaArancelaria;
/* 1886:     */   }
/* 1887:     */   
/* 1888:     */   public String getAtributo1()
/* 1889:     */   {
/* 1890:3075 */     return this.atributo1;
/* 1891:     */   }
/* 1892:     */   
/* 1893:     */   public void setAtributo1(String atributo1)
/* 1894:     */   {
/* 1895:3085 */     this.atributo1 = atributo1;
/* 1896:     */   }
/* 1897:     */   
/* 1898:     */   public String getAtributo2()
/* 1899:     */   {
/* 1900:3094 */     return this.atributo2;
/* 1901:     */   }
/* 1902:     */   
/* 1903:     */   public void setAtributo2(String atributo2)
/* 1904:     */   {
/* 1905:3104 */     this.atributo2 = atributo2;
/* 1906:     */   }
/* 1907:     */   
/* 1908:     */   public String getAtributo3()
/* 1909:     */   {
/* 1910:3113 */     return this.atributo3;
/* 1911:     */   }
/* 1912:     */   
/* 1913:     */   public void setAtributo3(String atributo3)
/* 1914:     */   {
/* 1915:3123 */     this.atributo3 = atributo3;
/* 1916:     */   }
/* 1917:     */   
/* 1918:     */   public String getAtributo4()
/* 1919:     */   {
/* 1920:3132 */     return this.atributo4;
/* 1921:     */   }
/* 1922:     */   
/* 1923:     */   public void setAtributo4(String atributo4)
/* 1924:     */   {
/* 1925:3142 */     this.atributo4 = atributo4;
/* 1926:     */   }
/* 1927:     */   
/* 1928:     */   public String getAtributo5()
/* 1929:     */   {
/* 1930:3151 */     return this.atributo5;
/* 1931:     */   }
/* 1932:     */   
/* 1933:     */   public void setAtributo5(String atributo5)
/* 1934:     */   {
/* 1935:3161 */     this.atributo5 = atributo5;
/* 1936:     */   }
/* 1937:     */   
/* 1938:     */   public String getAtributo6()
/* 1939:     */   {
/* 1940:3170 */     return this.atributo6;
/* 1941:     */   }
/* 1942:     */   
/* 1943:     */   public void setAtributo6(String atributo6)
/* 1944:     */   {
/* 1945:3180 */     this.atributo6 = atributo6;
/* 1946:     */   }
/* 1947:     */   
/* 1948:     */   public String getAtributo7()
/* 1949:     */   {
/* 1950:3189 */     return this.atributo7;
/* 1951:     */   }
/* 1952:     */   
/* 1953:     */   public void setAtributo7(String atributo7)
/* 1954:     */   {
/* 1955:3199 */     this.atributo7 = atributo7;
/* 1956:     */   }
/* 1957:     */   
/* 1958:     */   public String getAtributo8()
/* 1959:     */   {
/* 1960:3208 */     return this.atributo8;
/* 1961:     */   }
/* 1962:     */   
/* 1963:     */   public void setAtributo8(String atributo8)
/* 1964:     */   {
/* 1965:3218 */     this.atributo8 = atributo8;
/* 1966:     */   }
/* 1967:     */   
/* 1968:     */   public String getAtributo9()
/* 1969:     */   {
/* 1970:3227 */     return this.atributo9;
/* 1971:     */   }
/* 1972:     */   
/* 1973:     */   public void setAtributo9(String atributo9)
/* 1974:     */   {
/* 1975:3237 */     this.atributo9 = atributo9;
/* 1976:     */   }
/* 1977:     */   
/* 1978:     */   public String getAtributo10()
/* 1979:     */   {
/* 1980:3246 */     return this.atributo10;
/* 1981:     */   }
/* 1982:     */   
/* 1983:     */   public void setAtributo10(String atributo10)
/* 1984:     */   {
/* 1985:3256 */     this.atributo10 = atributo10;
/* 1986:     */   }
/* 1987:     */   
/* 1988:     */   public List<ProductoBodega> getListaProductoBodega()
/* 1989:     */   {
/* 1990:3260 */     return this.listaProductoBodega;
/* 1991:     */   }
/* 1992:     */   
/* 1993:     */   public void setListaProductoBodega(List<ProductoBodega> listaProductoBodega)
/* 1994:     */   {
/* 1995:3264 */     this.listaProductoBodega = listaProductoBodega;
/* 1996:     */   }
/* 1997:     */   
/* 1998:     */   public boolean isIndicadorManejoPeso()
/* 1999:     */   {
/* 2000:3268 */     return this.indicadorManejoPeso;
/* 2001:     */   }
/* 2002:     */   
/* 2003:     */   public void setIndicadorManejoPeso(boolean indicadorManejoPeso)
/* 2004:     */   {
/* 2005:3272 */     this.indicadorManejoPeso = indicadorManejoPeso;
/* 2006:     */   }
/* 2007:     */   
/* 2008:     */   public Boolean getIndicadorSerie()
/* 2009:     */   {
/* 2010:3276 */     return Boolean.valueOf(this.indicadorSerie == null ? false : this.indicadorSerie.booleanValue());
/* 2011:     */   }
/* 2012:     */   
/* 2013:     */   public void setIndicadorSerie(Boolean indicadorSerie)
/* 2014:     */   {
/* 2015:3280 */     this.indicadorSerie = indicadorSerie;
/* 2016:     */   }
/* 2017:     */   
/* 2018:     */   public Integer getOrdenCosteo()
/* 2019:     */   {
/* 2020:3287 */     return Integer.valueOf(this.ordenCosteo == null ? 0 : this.ordenCosteo.intValue());
/* 2021:     */   }
/* 2022:     */   
/* 2023:     */   public void setOrdenCosteo(Integer ordenCosteo)
/* 2024:     */   {
/* 2025:3295 */     this.ordenCosteo = Integer.valueOf(ordenCosteo == null ? 0 : ordenCosteo.intValue());
/* 2026:     */   }
/* 2027:     */   
/* 2028:     */   public PresentacionProducto getPresentacionProducto()
/* 2029:     */   {
/* 2030:3299 */     return this.presentacionProducto;
/* 2031:     */   }
/* 2032:     */   
/* 2033:     */   public void setPresentacionProducto(PresentacionProducto presentacionProducto)
/* 2034:     */   {
/* 2035:3303 */     this.presentacionProducto = presentacionProducto;
/* 2036:     */   }
/* 2037:     */   
/* 2038:     */   public MarcaProducto getMarcaProducto()
/* 2039:     */   {
/* 2040:3307 */     return this.marcaProducto;
/* 2041:     */   }
/* 2042:     */   
/* 2043:     */   public void setMarcaProducto(MarcaProducto marcaProducto)
/* 2044:     */   {
/* 2045:3311 */     this.marcaProducto = marcaProducto;
/* 2046:     */   }
/* 2047:     */   
/* 2048:     */   public NodoArbol<Producto> getArbolComponentes()
/* 2049:     */   {
/* 2050:3315 */     return this.arbolComponentes;
/* 2051:     */   }
/* 2052:     */   
/* 2053:     */   public void setArbolComponentes(NodoArbol<Producto> arbolComponentes)
/* 2054:     */   {
/* 2055:3319 */     this.arbolComponentes = arbolComponentes;
/* 2056:     */   }
/* 2057:     */   
/* 2058:     */   public BigDecimal getCantidadProducir()
/* 2059:     */   {
/* 2060:3323 */     return this.cantidadProducir;
/* 2061:     */   }
/* 2062:     */   
/* 2063:     */   public void setCantidadProducir(BigDecimal cantidadProducir)
/* 2064:     */   {
/* 2065:3327 */     this.cantidadProducir = cantidadProducir;
/* 2066:     */   }
/* 2067:     */   
/* 2068:     */   public boolean isIndicadorPesoBalanza()
/* 2069:     */   {
/* 2070:3331 */     return this.indicadorPesoBalanza;
/* 2071:     */   }
/* 2072:     */   
/* 2073:     */   public void setIndicadorPesoBalanza(boolean indicadorPesoBalanza)
/* 2074:     */   {
/* 2075:3335 */     this.indicadorPesoBalanza = indicadorPesoBalanza;
/* 2076:     */   }
/* 2077:     */   
/* 2078:     */   public int getTiempoReposicion()
/* 2079:     */   {
/* 2080:3339 */     return this.tiempoReposicion;
/* 2081:     */   }
/* 2082:     */   
/* 2083:     */   public void setTiempoReposicion(int tiempoReposicion)
/* 2084:     */   {
/* 2085:3343 */     this.tiempoReposicion = tiempoReposicion;
/* 2086:     */   }
/* 2087:     */   
/* 2088:     */   public String getIbpCodigoImpuesto()
/* 2089:     */   {
/* 2090:3347 */     if ((this.ibpCodigoImpuesto == null) || (this.ibpCodigoImpuesto.trim().isEmpty())) {
/* 2091:3348 */       this.ibpCodigoImpuesto = "5001";
/* 2092:     */     }
/* 2093:3350 */     return this.ibpCodigoImpuesto;
/* 2094:     */   }
/* 2095:     */   
/* 2096:     */   public void setIbpCodigoImpuesto(String ibpCodigoImpuesto)
/* 2097:     */   {
/* 2098:3354 */     this.ibpCodigoImpuesto = ibpCodigoImpuesto;
/* 2099:     */   }
/* 2100:     */   
/* 2101:     */   public IBPMarca getIbpMarca()
/* 2102:     */   {
/* 2103:3358 */     return this.ibpMarca;
/* 2104:     */   }
/* 2105:     */   
/* 2106:     */   public void setIbpMarca(IBPMarca ibpMarca)
/* 2107:     */   {
/* 2108:3362 */     this.ibpMarca = ibpMarca;
/* 2109:     */   }
/* 2110:     */   
/* 2111:     */   public String getIbpPresentacion()
/* 2112:     */   {
/* 2113:3366 */     if ((this.ibpPresentacion == null) || (this.ibpPresentacion.trim().isEmpty())) {
/* 2114:3367 */       this.ibpPresentacion = "025";
/* 2115:     */     }
/* 2116:3369 */     return this.ibpPresentacion;
/* 2117:     */   }
/* 2118:     */   
/* 2119:     */   public void setIbpPresentacion(String ibpPresentacion)
/* 2120:     */   {
/* 2121:3373 */     this.ibpPresentacion = ibpPresentacion;
/* 2122:     */   }
/* 2123:     */   
/* 2124:     */   public IBPCapacidad getIbpCapacidad()
/* 2125:     */   {
/* 2126:3377 */     return this.ibpCapacidad;
/* 2127:     */   }
/* 2128:     */   
/* 2129:     */   public void setIbpCapacidad(IBPCapacidad ibpCapacidad)
/* 2130:     */   {
/* 2131:3381 */     this.ibpCapacidad = ibpCapacidad;
/* 2132:     */   }
/* 2133:     */   
/* 2134:     */   public IBPUnidad getIbpUnidad()
/* 2135:     */   {
/* 2136:3385 */     return this.ibpUnidad;
/* 2137:     */   }
/* 2138:     */   
/* 2139:     */   public void setIbpUnidad(IBPUnidad ibpUnidad)
/* 2140:     */   {
/* 2141:3389 */     this.ibpUnidad = ibpUnidad;
/* 2142:     */   }
/* 2143:     */   
/* 2144:     */   public String getIbpGradoAlcohol()
/* 2145:     */   {
/* 2146:3393 */     if (this.ibpGradoAlcohol == null) {
/* 2147:3394 */       this.ibpGradoAlcohol = "000000";
/* 2148:     */     }
/* 2149:3396 */     return this.ibpGradoAlcohol;
/* 2150:     */   }
/* 2151:     */   
/* 2152:     */   public void setIbpGradoAlcohol(String ibpGradoAlcohol)
/* 2153:     */   {
/* 2154:3400 */     this.ibpGradoAlcohol = ibpGradoAlcohol;
/* 2155:     */   }
/* 2156:     */   
/* 2157:     */   public IBPClasificacion getIbpClasificacion()
/* 2158:     */   {
/* 2159:3404 */     if ((this.ibpClasificacion == null) && (this.ibpMarca != null)) {
/* 2160:3405 */       this.ibpClasificacion = this.ibpMarca.getIbpClasificacion();
/* 2161:     */     }
/* 2162:3407 */     return this.ibpClasificacion;
/* 2163:     */   }
/* 2164:     */   
/* 2165:     */   public void setIbpClasificacion(IBPClasificacion ibpClasificacion)
/* 2166:     */   {
/* 2167:3411 */     this.ibpClasificacion = ibpClasificacion;
/* 2168:     */   }
/* 2169:     */   
/* 2170:     */   public List<IBPMarca> getListaIBPMarca()
/* 2171:     */   {
/* 2172:3415 */     return this.listaIBPMarca;
/* 2173:     */   }
/* 2174:     */   
/* 2175:     */   public void setListaIBPMarca(List<IBPMarca> listaIBPMarca)
/* 2176:     */   {
/* 2177:3419 */     this.listaIBPMarca = listaIBPMarca;
/* 2178:     */   }
/* 2179:     */   
/* 2180:     */   public boolean isIndicadorProduccionRapida()
/* 2181:     */   {
/* 2182:3423 */     return this.indicadorProduccionRapida;
/* 2183:     */   }
/* 2184:     */   
/* 2185:     */   public void setIndicadorProduccionRapida(boolean indicadorProduccionRapida)
/* 2186:     */   {
/* 2187:3427 */     this.indicadorProduccionRapida = indicadorProduccionRapida;
/* 2188:     */   }
/* 2189:     */   
/* 2190:     */   public int getPorCientoDespachoSuperaPedido()
/* 2191:     */   {
/* 2192:3431 */     return this.porCientoDespachoSuperaPedido;
/* 2193:     */   }
/* 2194:     */   
/* 2195:     */   public void setPorCientoDespachoSuperaPedido(int porCientoDespachoSuperaPedido)
/* 2196:     */   {
/* 2197:3435 */     this.porCientoDespachoSuperaPedido = porCientoDespachoSuperaPedido;
/* 2198:     */   }
/* 2199:     */   
/* 2200:     */   public Boolean getImpuestoAviacion()
/* 2201:     */   {
/* 2202:3439 */     return Boolean.valueOf(this.impuestoAviacion == null ? false : this.impuestoAviacion.booleanValue());
/* 2203:     */   }
/* 2204:     */   
/* 2205:     */   public void setImpuestoAviacion(Boolean impuestoAviacion)
/* 2206:     */   {
/* 2207:3443 */     this.impuestoAviacion = impuestoAviacion;
/* 2208:     */   }
/* 2209:     */   
/* 2210:     */   public Boolean getIndicadorNacional()
/* 2211:     */   {
/* 2212:3447 */     return Boolean.valueOf(this.indicadorNacional == null ? false : this.indicadorNacional.booleanValue());
/* 2213:     */   }
/* 2214:     */   
/* 2215:     */   public void setIndicadorNacional(Boolean indicadorNacional)
/* 2216:     */   {
/* 2217:3451 */     this.indicadorNacional = indicadorNacional;
/* 2218:     */   }
/* 2219:     */   
/* 2220:     */   public boolean isIndicadorPlanificarProduccion()
/* 2221:     */   {
/* 2222:3455 */     return this.indicadorPlanificarProduccion;
/* 2223:     */   }
/* 2224:     */   
/* 2225:     */   public void setIndicadorPlanificarProduccion(boolean indicadorPlanificarProduccion)
/* 2226:     */   {
/* 2227:3459 */     this.indicadorPlanificarProduccion = indicadorPlanificarProduccion;
/* 2228:     */   }
/* 2229:     */   
/* 2230:     */   public BigDecimal getPesoMinimo()
/* 2231:     */   {
/* 2232:3463 */     if ((this.pesoMinimo.compareTo(BigDecimal.ZERO) == 0) && (this.peso.compareTo(BigDecimal.ZERO) != 0)) {
/* 2233:3464 */       this.pesoMinimo = this.peso;
/* 2234:     */     }
/* 2235:3466 */     return this.pesoMinimo;
/* 2236:     */   }
/* 2237:     */   
/* 2238:     */   public void setPesoMinimo(BigDecimal pesoMinimo)
/* 2239:     */   {
/* 2240:3470 */     this.pesoMinimo = pesoMinimo;
/* 2241:     */   }
/* 2242:     */   
/* 2243:     */   public BigDecimal getPesoMaximo()
/* 2244:     */   {
/* 2245:3474 */     if ((this.pesoMaximo.compareTo(BigDecimal.ZERO) == 0) && (this.peso.compareTo(BigDecimal.ZERO) != 0)) {
/* 2246:3475 */       this.pesoMaximo = this.peso;
/* 2247:     */     }
/* 2248:3477 */     return this.pesoMaximo;
/* 2249:     */   }
/* 2250:     */   
/* 2251:     */   public void setPesoMaximo(BigDecimal pesoMaximo)
/* 2252:     */   {
/* 2253:3481 */     this.pesoMaximo = pesoMaximo;
/* 2254:     */   }
/* 2255:     */   
/* 2256:     */   public int getPorcientoToleranciaTransferencia()
/* 2257:     */   {
/* 2258:3485 */     return this.porcientoToleranciaTransferencia;
/* 2259:     */   }
/* 2260:     */   
/* 2261:     */   public void setPorcientoToleranciaTransferencia(int porcientoToleranciaTransferencia)
/* 2262:     */   {
/* 2263:3489 */     this.porcientoToleranciaTransferencia = porcientoToleranciaTransferencia;
/* 2264:     */   }
/* 2265:     */   
/* 2266:     */   public List<BodegaTrabajoProductoSucursal> getListaBodegaTrabajoSucursal()
/* 2267:     */   {
/* 2268:3493 */     return this.listaBodegaTrabajoSucursal;
/* 2269:     */   }
/* 2270:     */   
/* 2271:     */   public void setListaBodegaTrabajoSucursal(List<BodegaTrabajoProductoSucursal> listaBodegaTrabajoSucursal)
/* 2272:     */   {
/* 2273:3497 */     this.listaBodegaTrabajoSucursal = listaBodegaTrabajoSucursal;
/* 2274:     */   }
/* 2275:     */   
/* 2276:     */   public Integer getMultiploPedido()
/* 2277:     */   {
/* 2278:3501 */     if (this.multiploPedido == null) {
/* 2279:3502 */       this.multiploPedido = Integer.valueOf(0);
/* 2280:     */     }
/* 2281:3504 */     return this.multiploPedido;
/* 2282:     */   }
/* 2283:     */   
/* 2284:     */   public void setMultiploPedido(Integer multiploPedido)
/* 2285:     */   {
/* 2286:3508 */     if (multiploPedido == null) {
/* 2287:3509 */       multiploPedido = Integer.valueOf(0);
/* 2288:     */     }
/* 2289:3511 */     this.multiploPedido = multiploPedido;
/* 2290:     */   }
/* 2291:     */   
/* 2292:     */   public BigDecimal getPesoMaterialPrincipal()
/* 2293:     */   {
/* 2294:3515 */     return this.pesoMaterialPrincipal;
/* 2295:     */   }
/* 2296:     */   
/* 2297:     */   public void setPesoMaterialPrincipal(BigDecimal pesoMaterialPrincipal)
/* 2298:     */   {
/* 2299:3519 */     this.pesoMaterialPrincipal = pesoMaterialPrincipal;
/* 2300:     */   }
/* 2301:     */   
/* 2302:     */   public Bodega getBodegaDevolucion()
/* 2303:     */   {
/* 2304:3523 */     return this.bodegaDevolucion;
/* 2305:     */   }
/* 2306:     */   
/* 2307:     */   public void setBodegaDevolucion(Bodega bodegaDevolucion)
/* 2308:     */   {
/* 2309:3527 */     this.bodegaDevolucion = bodegaDevolucion;
/* 2310:     */   }
/* 2311:     */   
/* 2312:     */   public int getVersionCosteo()
/* 2313:     */   {
/* 2314:3531 */     return this.versionCosteo;
/* 2315:     */   }
/* 2316:     */   
/* 2317:     */   public void setVersionCosteo(int versionCosteo)
/* 2318:     */   {
/* 2319:3535 */     this.versionCosteo = versionCosteo;
/* 2320:     */   }
/* 2321:     */   
/* 2322:     */   public BigDecimal getCostoMateriales()
/* 2323:     */   {
/* 2324:3539 */     return this.costoMateriales;
/* 2325:     */   }
/* 2326:     */   
/* 2327:     */   public void setCostoMateriales(BigDecimal costoMateriales)
/* 2328:     */   {
/* 2329:3543 */     this.costoMateriales = costoMateriales;
/* 2330:     */   }
/* 2331:     */   
/* 2332:     */   public BigDecimal getCostoManoObra()
/* 2333:     */   {
/* 2334:3547 */     return this.costoManoObra;
/* 2335:     */   }
/* 2336:     */   
/* 2337:     */   public void setCostoManoObra(BigDecimal costoManoObra)
/* 2338:     */   {
/* 2339:3551 */     this.costoManoObra = costoManoObra;
/* 2340:     */   }
/* 2341:     */   
/* 2342:     */   public BigDecimal getCostoDepreciaciones()
/* 2343:     */   {
/* 2344:3555 */     return this.costoDepreciaciones;
/* 2345:     */   }
/* 2346:     */   
/* 2347:     */   public void setCostoDepreciaciones(BigDecimal costoDepreciaciones)
/* 2348:     */   {
/* 2349:3559 */     this.costoDepreciaciones = costoDepreciaciones;
/* 2350:     */   }
/* 2351:     */   
/* 2352:     */   public BigDecimal getCostoIndirecto()
/* 2353:     */   {
/* 2354:3563 */     return this.costoIndirecto;
/* 2355:     */   }
/* 2356:     */   
/* 2357:     */   public void setCostoIndirecto(BigDecimal costoIndirecto)
/* 2358:     */   {
/* 2359:3567 */     this.costoIndirecto = costoIndirecto;
/* 2360:     */   }
/* 2361:     */   
/* 2362:     */   public BigDecimal getCantidadFabricada()
/* 2363:     */   {
/* 2364:3571 */     return this.cantidadFabricada;
/* 2365:     */   }
/* 2366:     */   
/* 2367:     */   public void setCantidadFabricada(BigDecimal cantidadFabricada)
/* 2368:     */   {
/* 2369:3575 */     this.cantidadFabricada = cantidadFabricada;
/* 2370:     */   }
/* 2371:     */   
/* 2372:     */   public BigDecimal getCostoTotal()
/* 2373:     */   {
/* 2374:3579 */     return this.costoMateriales.add(this.costoManoObra).add(this.costoDepreciaciones).add(this.costoIndirecto);
/* 2375:     */   }
/* 2376:     */   
/* 2377:     */   public BigDecimal getCostoUnitario()
/* 2378:     */   {
/* 2379:3583 */     if (getCantidadFabricada().compareTo(BigDecimal.ZERO) == 0) {
/* 2380:3584 */       return BigDecimal.ZERO;
/* 2381:     */     }
/* 2382:3586 */     return getCostoTotalAsignado().divide(getCantidadFabricada(), 4, RoundingMode.HALF_UP);
/* 2383:     */   }
/* 2384:     */   
/* 2385:     */   public boolean isIndicadorExplotaDevolucion()
/* 2386:     */   {
/* 2387:3590 */     if (this.indicadorExplotaDevolucion == null) {
/* 2388:3591 */       this.indicadorExplotaDevolucion = Boolean.valueOf(false);
/* 2389:     */     }
/* 2390:3593 */     return this.indicadorExplotaDevolucion.booleanValue();
/* 2391:     */   }
/* 2392:     */   
/* 2393:     */   public void setIndicadorExplotaDevolucion(boolean indicadorExplotaDevolucion)
/* 2394:     */   {
/* 2395:3597 */     this.indicadorExplotaDevolucion = Boolean.valueOf(indicadorExplotaDevolucion);
/* 2396:     */   }
/* 2397:     */   
/* 2398:     */   public Boolean getIndicadorControlCalidad()
/* 2399:     */   {
/* 2400:3601 */     if (this.indicadorControlCalidad == null) {
/* 2401:3602 */       this.indicadorControlCalidad = Boolean.valueOf(false);
/* 2402:     */     }
/* 2403:3604 */     return this.indicadorControlCalidad;
/* 2404:     */   }
/* 2405:     */   
/* 2406:     */   public void setIndicadorControlCalidad(Boolean indicadorControlCalidad)
/* 2407:     */   {
/* 2408:3608 */     this.indicadorControlCalidad = indicadorControlCalidad;
/* 2409:     */   }
/* 2410:     */   
/* 2411:     */   public BigDecimal getPesoDestareUnidad()
/* 2412:     */   {
/* 2413:3612 */     if (this.pesoDestareUnidad == null) {
/* 2414:3613 */       this.pesoDestareUnidad = BigDecimal.ZERO;
/* 2415:     */     }
/* 2416:3615 */     return this.pesoDestareUnidad;
/* 2417:     */   }
/* 2418:     */   
/* 2419:     */   public void setPesoDestareUnidad(BigDecimal pesoDestareUnidad)
/* 2420:     */   {
/* 2421:3619 */     this.pesoDestareUnidad = pesoDestareUnidad;
/* 2422:     */   }
/* 2423:     */   
/* 2424:     */   public BigDecimal getValorReceta()
/* 2425:     */   {
/* 2426:3623 */     return this.valorReceta;
/* 2427:     */   }
/* 2428:     */   
/* 2429:     */   public void setValorReceta(BigDecimal valorReceta)
/* 2430:     */   {
/* 2431:3627 */     this.valorReceta = valorReceta;
/* 2432:     */   }
/* 2433:     */   
/* 2434:     */   public BigDecimal getSubsidio()
/* 2435:     */   {
/* 2436:3631 */     return this.subsidio;
/* 2437:     */   }
/* 2438:     */   
/* 2439:     */   public void setSubsidio(BigDecimal subsidio)
/* 2440:     */   {
/* 2441:3635 */     this.subsidio = subsidio;
/* 2442:     */   }
/* 2443:     */   
/* 2444:     */   public BigDecimal getPorcentajeReceta()
/* 2445:     */   {
/* 2446:3639 */     return this.porcentajeReceta;
/* 2447:     */   }
/* 2448:     */   
/* 2449:     */   public void setPorcentajeReceta(BigDecimal porcentajeReceta)
/* 2450:     */   {
/* 2451:3643 */     this.porcentajeReceta = porcentajeReceta;
/* 2452:     */   }
/* 2453:     */   
/* 2454:     */   public String getInformacionAdicional()
/* 2455:     */   {
/* 2456:3647 */     return this.informacionAdicional;
/* 2457:     */   }
/* 2458:     */   
/* 2459:     */   public void setInformacionAdicional(String informacionAdicional)
/* 2460:     */   {
/* 2461:3651 */     this.informacionAdicional = informacionAdicional;
/* 2462:     */   }
/* 2463:     */   
/* 2464:     */   public List<VariableCalidadProducto> getListaVariableCalidadProducto()
/* 2465:     */   {
/* 2466:3658 */     return this.listaVariableCalidadProducto;
/* 2467:     */   }
/* 2468:     */   
/* 2469:     */   public void setListaVariableCalidadProducto(List<VariableCalidadProducto> listaVariableCalidadProducto)
/* 2470:     */   {
/* 2471:3666 */     this.listaVariableCalidadProducto = listaVariableCalidadProducto;
/* 2472:     */   }
/* 2473:     */   
/* 2474:     */   public TipoMaterialEnum getTipoMaterialEnum()
/* 2475:     */   {
/* 2476:3670 */     return this.tipoMaterialEnum;
/* 2477:     */   }
/* 2478:     */   
/* 2479:     */   public void setTipoMaterialEnum(TipoMaterialEnum tipoMaterialEnum)
/* 2480:     */   {
/* 2481:3674 */     this.tipoMaterialEnum = tipoMaterialEnum;
/* 2482:     */   }
/* 2483:     */   
/* 2484:     */   public boolean isIndicadorMantenimiento()
/* 2485:     */   {
/* 2486:3678 */     return this.indicadorMantenimiento;
/* 2487:     */   }
/* 2488:     */   
/* 2489:     */   public void setIndicadorMantenimiento(boolean indicadorMantenimiento)
/* 2490:     */   {
/* 2491:3682 */     this.indicadorMantenimiento = indicadorMantenimiento;
/* 2492:     */   }
/* 2493:     */   
/* 2494:     */   public boolean isIndicadorPorcentajeIce()
/* 2495:     */   {
/* 2496:3686 */     return this.indicadorPorcentajeIce;
/* 2497:     */   }
/* 2498:     */   
/* 2499:     */   public void setIndicadorPorcentajeIce(boolean indicadorPorcentajeIce)
/* 2500:     */   {
/* 2501:3690 */     this.indicadorPorcentajeIce = indicadorPorcentajeIce;
/* 2502:     */   }
/* 2503:     */   
/* 2504:     */   public BigDecimal getIce()
/* 2505:     */   {
/* 2506:3694 */     return this.ice;
/* 2507:     */   }
/* 2508:     */   
/* 2509:     */   public void setIce(BigDecimal ice)
/* 2510:     */   {
/* 2511:3698 */     this.ice = ice;
/* 2512:     */   }
/* 2513:     */   
/* 2514:     */   public String getCodigoIce()
/* 2515:     */   {
/* 2516:3702 */     return this.codigoIce;
/* 2517:     */   }
/* 2518:     */   
/* 2519:     */   public void setCodigoIce(String codigoIce)
/* 2520:     */   {
/* 2521:3706 */     this.codigoIce = codigoIce;
/* 2522:     */   }
/* 2523:     */   
/* 2524:     */   public CreditoTributarioSRI getCreditoTributarioSRI()
/* 2525:     */   {
/* 2526:3710 */     return this.creditoTributarioSRI;
/* 2527:     */   }
/* 2528:     */   
/* 2529:     */   public void setCreditoTributarioSRI(CreditoTributarioSRI creditoTributarioSRI)
/* 2530:     */   {
/* 2531:3714 */     this.creditoTributarioSRI = creditoTributarioSRI;
/* 2532:     */   }
/* 2533:     */   
/* 2534:     */   public Boolean getMigracionExisteNombreProducto()
/* 2535:     */   {
/* 2536:3718 */     return this.migracionExisteNombreProducto;
/* 2537:     */   }
/* 2538:     */   
/* 2539:     */   public void setMigracionExisteNombreProducto(Boolean migra)
/* 2540:     */   {
/* 2541:3722 */     this.migracionExisteNombreProducto = migra;
/* 2542:     */   }
/* 2543:     */   
/* 2544:     */   public List<DetalleCostoFabricacion> getListaDetalleCostoFabricacion()
/* 2545:     */   {
/* 2546:3726 */     return this.listaDetalleCostoFabricacion;
/* 2547:     */   }
/* 2548:     */   
/* 2549:     */   public void setListaDetalleCostoFabricacion(List<DetalleCostoFabricacion> listaDetalleCostoFabricacion)
/* 2550:     */   {
/* 2551:3730 */     this.listaDetalleCostoFabricacion = listaDetalleCostoFabricacion;
/* 2552:     */   }
/* 2553:     */   
/* 2554:     */   public BigDecimal getCostoTotalAsignado()
/* 2555:     */   {
/* 2556:3734 */     return this.costoTotalAsignado;
/* 2557:     */   }
/* 2558:     */   
/* 2559:     */   public void setCostoTotalAsignado(BigDecimal costoTotalAsignado)
/* 2560:     */   {
/* 2561:3738 */     this.costoTotalAsignado = costoTotalAsignado;
/* 2562:     */   }
/* 2563:     */   
/* 2564:     */   public Boolean getIndicadorExplotaDevolucion()
/* 2565:     */   {
/* 2566:3745 */     return this.indicadorExplotaDevolucion;
/* 2567:     */   }
/* 2568:     */   
/* 2569:     */   public void setIndicadorExplotaDevolucion(Boolean indicadorExplotaDevolucion)
/* 2570:     */   {
/* 2571:3753 */     this.indicadorExplotaDevolucion = indicadorExplotaDevolucion;
/* 2572:     */   }
/* 2573:     */   
/* 2574:     */   public BigDecimal getMaximoDesvio()
/* 2575:     */   {
/* 2576:3760 */     return this.maximoDesvio;
/* 2577:     */   }
/* 2578:     */   
/* 2579:     */   public void setMaximoDesvio(BigDecimal maximoDesvio)
/* 2580:     */   {
/* 2581:3768 */     this.maximoDesvio = maximoDesvio;
/* 2582:     */   }
/* 2583:     */   
/* 2584:     */   public boolean isIndicadorManejaUnidadInformativa()
/* 2585:     */   {
/* 2586:3772 */     return this.indicadorManejaUnidadInformativa;
/* 2587:     */   }
/* 2588:     */   
/* 2589:     */   public void setIndicadorManejaUnidadInformativa(boolean indicadorManejaUnidadInformativa)
/* 2590:     */   {
/* 2591:3776 */     this.indicadorManejaUnidadInformativa = indicadorManejaUnidadInformativa;
/* 2592:3777 */     if (!indicadorManejaUnidadInformativa)
/* 2593:     */     {
/* 2594:3778 */       this.unidadInformativa = null;
/* 2595:3779 */       this.productoTransformacionAutomatica = null;
/* 2596:     */     }
/* 2597:     */   }
/* 2598:     */   
/* 2599:     */   public Unidad getUnidadInformativa()
/* 2600:     */   {
/* 2601:3784 */     return this.unidadInformativa;
/* 2602:     */   }
/* 2603:     */   
/* 2604:     */   public void setUnidadInformativa(Unidad unidadInformativa)
/* 2605:     */   {
/* 2606:3788 */     this.unidadInformativa = unidadInformativa;
/* 2607:     */   }
/* 2608:     */   
/* 2609:     */   public Producto getProductoTransformacionAutomatica()
/* 2610:     */   {
/* 2611:3792 */     return this.productoTransformacionAutomatica;
/* 2612:     */   }
/* 2613:     */   
/* 2614:     */   public void setProductoTransformacionAutomatica(Producto productoTransformacionAutomatica)
/* 2615:     */   {
/* 2616:3796 */     this.productoTransformacionAutomatica = productoTransformacionAutomatica;
/* 2617:     */   }
/* 2618:     */   
/* 2619:     */   public Integer getDiasCaducidad()
/* 2620:     */   {
/* 2621:3800 */     if (this.diasCaducidad == null) {
/* 2622:3801 */       this.diasCaducidad = Integer.valueOf(0);
/* 2623:     */     }
/* 2624:3803 */     return this.diasCaducidad;
/* 2625:     */   }
/* 2626:     */   
/* 2627:     */   public void setDiasCaducidad(Integer diasCaducidad)
/* 2628:     */   {
/* 2629:3807 */     this.diasCaducidad = diasCaducidad;
/* 2630:     */   }
/* 2631:     */   
/* 2632:     */   public BigDecimal getIceGramosAzucar()
/* 2633:     */   {
/* 2634:3811 */     return this.iceGramosAzucar;
/* 2635:     */   }
/* 2636:     */   
/* 2637:     */   public void setIceGramosAzucar(BigDecimal iceGramosAzucar)
/* 2638:     */   {
/* 2639:3815 */     this.iceGramosAzucar = iceGramosAzucar;
/* 2640:     */   }
/* 2641:     */   
/* 2642:     */   public boolean isIndicadorConsumo()
/* 2643:     */   {
/* 2644:3819 */     return this.indicadorConsumo;
/* 2645:     */   }
/* 2646:     */   
/* 2647:     */   public void setIndicadorConsumo(boolean indicadorConsumo)
/* 2648:     */   {
/* 2649:3823 */     this.indicadorConsumo = indicadorConsumo;
/* 2650:     */   }
/* 2651:     */   
/* 2652:     */   public String getIcePresentacion()
/* 2653:     */   {
/* 2654:3827 */     return this.icePresentacion;
/* 2655:     */   }
/* 2656:     */   
/* 2657:     */   public void setIcePresentacion(String icePresentacion)
/* 2658:     */   {
/* 2659:3831 */     this.icePresentacion = icePresentacion;
/* 2660:     */   }
/* 2661:     */   
/* 2662:     */   public List<ProductoRutaFabricacionSucursal> getListaProductoRutaFabricacionSucursal()
/* 2663:     */   {
/* 2664:3835 */     return this.listaProductoRutaFabricacionSucursal;
/* 2665:     */   }
/* 2666:     */   
/* 2667:     */   public void setListaProductoRutaFabricacionSucursal(List<ProductoRutaFabricacionSucursal> listaProductoRutaFabricacionSucursal)
/* 2668:     */   {
/* 2669:3839 */     this.listaProductoRutaFabricacionSucursal = listaProductoRutaFabricacionSucursal;
/* 2670:     */   }
/* 2671:     */   
/* 2672:     */   public boolean isIndicadorProduccionKit()
/* 2673:     */   {
/* 2674:3843 */     return this.indicadorProduccionKit;
/* 2675:     */   }
/* 2676:     */   
/* 2677:     */   public void setIndicadorProduccionKit(boolean indicadorProduccionKit)
/* 2678:     */   {
/* 2679:3847 */     this.indicadorProduccionKit = indicadorProduccionKit;
/* 2680:     */   }
/* 2681:     */   
/* 2682:     */   public String getPrefijosLote()
/* 2683:     */   {
/* 2684:3851 */     return this.prefijosLote;
/* 2685:     */   }
/* 2686:     */   
/* 2687:     */   public void setPrefijosLote(String prefijosLote)
/* 2688:     */   {
/* 2689:3855 */     this.prefijosLote = prefijosLote;
/* 2690:     */   }
/* 2691:     */   
/* 2692:     */   public TreeMap<String, String> getValoresPrefijosLote()
/* 2693:     */   {
/* 2694:3859 */     TreeMap<String, String> tmLista = new TreeMap();
/* 2695:3860 */     if (this.prefijosLote != null) {
/* 2696:3861 */       for (String prefijo : this.prefijosLote.split(";")) {
/* 2697:3862 */         tmLista.put(prefijo, prefijo);
/* 2698:     */       }
/* 2699:     */     }
/* 2700:3865 */     return tmLista;
/* 2701:     */   }
/* 2702:     */   
/* 2703:     */   public String getPrefijoLote()
/* 2704:     */   {
/* 2705:3869 */     if (this.prefijoLote == null) {
/* 2706:3870 */       this.prefijoLote = "";
/* 2707:     */     }
/* 2708:3872 */     return this.prefijoLote.trim();
/* 2709:     */   }
/* 2710:     */   
/* 2711:     */   public void setPrefijoLote(String prefijoLote)
/* 2712:     */   {
/* 2713:3876 */     this.prefijoLote = prefijoLote;
/* 2714:     */   }
/* 2715:     */   
/* 2716:     */   public Atributo getAtributoProduccion1()
/* 2717:     */   {
/* 2718:3880 */     return this.atributoProduccion1;
/* 2719:     */   }
/* 2720:     */   
/* 2721:     */   public void setAtributoProduccion1(Atributo atributoProduccion1)
/* 2722:     */   {
/* 2723:3884 */     this.atributoProduccion1 = atributoProduccion1;
/* 2724:     */   }
/* 2725:     */   
/* 2726:     */   public Atributo getTraAtributo1()
/* 2727:     */   {
/* 2728:3888 */     return this.traAtributo1;
/* 2729:     */   }
/* 2730:     */   
/* 2731:     */   public void setTraAtributo1(Atributo traAtributo1)
/* 2732:     */   {
/* 2733:3892 */     this.traAtributo1 = traAtributo1;
/* 2734:     */   }
/* 2735:     */   
/* 2736:     */   public boolean isIndicadorAtributo1()
/* 2737:     */   {
/* 2738:3896 */     return this.indicadorAtributo1;
/* 2739:     */   }
/* 2740:     */   
/* 2741:     */   public void setIndicadorAtributo1(boolean indicadorAtributo1)
/* 2742:     */   {
/* 2743:3900 */     this.indicadorAtributo1 = indicadorAtributo1;
/* 2744:     */   }
/* 2745:     */   
/* 2746:     */   public Atributo getAtributoProduccion2()
/* 2747:     */   {
/* 2748:3904 */     return this.atributoProduccion2;
/* 2749:     */   }
/* 2750:     */   
/* 2751:     */   public void setAtributoProduccion2(Atributo atributoProduccion2)
/* 2752:     */   {
/* 2753:3908 */     this.atributoProduccion2 = atributoProduccion2;
/* 2754:     */   }
/* 2755:     */   
/* 2756:     */   public Atributo getTraAtributo2()
/* 2757:     */   {
/* 2758:3912 */     return this.traAtributo2;
/* 2759:     */   }
/* 2760:     */   
/* 2761:     */   public void setTraAtributo2(Atributo traAtributo2)
/* 2762:     */   {
/* 2763:3916 */     this.traAtributo2 = traAtributo2;
/* 2764:     */   }
/* 2765:     */   
/* 2766:     */   public boolean isIndicadorAtributo2()
/* 2767:     */   {
/* 2768:3920 */     return this.indicadorAtributo2;
/* 2769:     */   }
/* 2770:     */   
/* 2771:     */   public void setIndicadorAtributo2(boolean indicadorAtributo2)
/* 2772:     */   {
/* 2773:3924 */     this.indicadorAtributo2 = indicadorAtributo2;
/* 2774:     */   }
/* 2775:     */   
/* 2776:     */   public Atributo getAtributoProduccion3()
/* 2777:     */   {
/* 2778:3928 */     return this.atributoProduccion3;
/* 2779:     */   }
/* 2780:     */   
/* 2781:     */   public void setAtributoProduccion3(Atributo atributoProduccion3)
/* 2782:     */   {
/* 2783:3932 */     this.atributoProduccion3 = atributoProduccion3;
/* 2784:     */   }
/* 2785:     */   
/* 2786:     */   public Atributo getTraAtributo3()
/* 2787:     */   {
/* 2788:3936 */     return this.traAtributo3;
/* 2789:     */   }
/* 2790:     */   
/* 2791:     */   public void setTraAtributo3(Atributo traAtributo3)
/* 2792:     */   {
/* 2793:3940 */     this.traAtributo3 = traAtributo3;
/* 2794:     */   }
/* 2795:     */   
/* 2796:     */   public boolean isIndicadorAtributo3()
/* 2797:     */   {
/* 2798:3944 */     return this.indicadorAtributo3;
/* 2799:     */   }
/* 2800:     */   
/* 2801:     */   public void setIndicadorAtributo3(boolean indicadorAtributo3)
/* 2802:     */   {
/* 2803:3948 */     this.indicadorAtributo3 = indicadorAtributo3;
/* 2804:     */   }
/* 2805:     */   
/* 2806:     */   public Atributo getAtributoProduccion4()
/* 2807:     */   {
/* 2808:3952 */     return this.atributoProduccion4;
/* 2809:     */   }
/* 2810:     */   
/* 2811:     */   public void setAtributoProduccion4(Atributo atributoProduccion4)
/* 2812:     */   {
/* 2813:3956 */     this.atributoProduccion4 = atributoProduccion4;
/* 2814:     */   }
/* 2815:     */   
/* 2816:     */   public Atributo getTraAtributo4()
/* 2817:     */   {
/* 2818:3960 */     return this.traAtributo4;
/* 2819:     */   }
/* 2820:     */   
/* 2821:     */   public void setTraAtributo4(Atributo traAtributo4)
/* 2822:     */   {
/* 2823:3964 */     this.traAtributo4 = traAtributo4;
/* 2824:     */   }
/* 2825:     */   
/* 2826:     */   public boolean isIndicadorAtributo4()
/* 2827:     */   {
/* 2828:3968 */     return this.indicadorAtributo4;
/* 2829:     */   }
/* 2830:     */   
/* 2831:     */   public void setIndicadorAtributo4(boolean indicadorAtributo4)
/* 2832:     */   {
/* 2833:3972 */     this.indicadorAtributo4 = indicadorAtributo4;
/* 2834:     */   }
/* 2835:     */   
/* 2836:     */   public Atributo getAtributoProduccion5()
/* 2837:     */   {
/* 2838:3976 */     return this.atributoProduccion5;
/* 2839:     */   }
/* 2840:     */   
/* 2841:     */   public void setAtributoProduccion5(Atributo atributoProduccion5)
/* 2842:     */   {
/* 2843:3980 */     this.atributoProduccion5 = atributoProduccion5;
/* 2844:     */   }
/* 2845:     */   
/* 2846:     */   public Atributo getTraAtributo5()
/* 2847:     */   {
/* 2848:3984 */     return this.traAtributo5;
/* 2849:     */   }
/* 2850:     */   
/* 2851:     */   public void setTraAtributo5(Atributo traAtributo5)
/* 2852:     */   {
/* 2853:3988 */     this.traAtributo5 = traAtributo5;
/* 2854:     */   }
/* 2855:     */   
/* 2856:     */   public boolean isIndicadorAtributo5()
/* 2857:     */   {
/* 2858:3992 */     return this.indicadorAtributo5;
/* 2859:     */   }
/* 2860:     */   
/* 2861:     */   public void setIndicadorAtributo5(boolean indicadorAtributo5)
/* 2862:     */   {
/* 2863:3996 */     this.indicadorAtributo5 = indicadorAtributo5;
/* 2864:     */   }
/* 2865:     */   
/* 2866:     */   public Atributo getAtributoProduccion6()
/* 2867:     */   {
/* 2868:4000 */     return this.atributoProduccion6;
/* 2869:     */   }
/* 2870:     */   
/* 2871:     */   public void setAtributoProduccion6(Atributo atributoProduccion6)
/* 2872:     */   {
/* 2873:4004 */     this.atributoProduccion6 = atributoProduccion6;
/* 2874:     */   }
/* 2875:     */   
/* 2876:     */   public Atributo getTraAtributo6()
/* 2877:     */   {
/* 2878:4008 */     return this.traAtributo6;
/* 2879:     */   }
/* 2880:     */   
/* 2881:     */   public void setTraAtributo6(Atributo traAtributo6)
/* 2882:     */   {
/* 2883:4012 */     this.traAtributo6 = traAtributo6;
/* 2884:     */   }
/* 2885:     */   
/* 2886:     */   public boolean isIndicadorAtributo6()
/* 2887:     */   {
/* 2888:4016 */     return this.indicadorAtributo6;
/* 2889:     */   }
/* 2890:     */   
/* 2891:     */   public void setIndicadorAtributo6(boolean indicadorAtributo6)
/* 2892:     */   {
/* 2893:4020 */     this.indicadorAtributo6 = indicadorAtributo6;
/* 2894:     */   }
/* 2895:     */   
/* 2896:     */   public Atributo getAtributoProduccion7()
/* 2897:     */   {
/* 2898:4024 */     return this.atributoProduccion7;
/* 2899:     */   }
/* 2900:     */   
/* 2901:     */   public void setAtributoProduccion7(Atributo atributoProduccion7)
/* 2902:     */   {
/* 2903:4028 */     this.atributoProduccion7 = atributoProduccion7;
/* 2904:     */   }
/* 2905:     */   
/* 2906:     */   public Atributo getTraAtributo7()
/* 2907:     */   {
/* 2908:4032 */     return this.traAtributo7;
/* 2909:     */   }
/* 2910:     */   
/* 2911:     */   public void setTraAtributo7(Atributo traAtributo7)
/* 2912:     */   {
/* 2913:4036 */     this.traAtributo7 = traAtributo7;
/* 2914:     */   }
/* 2915:     */   
/* 2916:     */   public boolean isIndicadorAtributo7()
/* 2917:     */   {
/* 2918:4040 */     return this.indicadorAtributo7;
/* 2919:     */   }
/* 2920:     */   
/* 2921:     */   public void setIndicadorAtributo7(boolean indicadorAtributo7)
/* 2922:     */   {
/* 2923:4044 */     this.indicadorAtributo7 = indicadorAtributo7;
/* 2924:     */   }
/* 2925:     */   
/* 2926:     */   public Atributo getAtributoProduccion8()
/* 2927:     */   {
/* 2928:4048 */     return this.atributoProduccion8;
/* 2929:     */   }
/* 2930:     */   
/* 2931:     */   public void setAtributoProduccion8(Atributo atributoProduccion8)
/* 2932:     */   {
/* 2933:4052 */     this.atributoProduccion8 = atributoProduccion8;
/* 2934:     */   }
/* 2935:     */   
/* 2936:     */   public Atributo getTraAtributo8()
/* 2937:     */   {
/* 2938:4056 */     return this.traAtributo8;
/* 2939:     */   }
/* 2940:     */   
/* 2941:     */   public void setTraAtributo8(Atributo traAtributo8)
/* 2942:     */   {
/* 2943:4060 */     this.traAtributo8 = traAtributo8;
/* 2944:     */   }
/* 2945:     */   
/* 2946:     */   public boolean isIndicadorAtributo8()
/* 2947:     */   {
/* 2948:4064 */     return this.indicadorAtributo8;
/* 2949:     */   }
/* 2950:     */   
/* 2951:     */   public void setIndicadorAtributo8(boolean indicadorAtributo8)
/* 2952:     */   {
/* 2953:4068 */     this.indicadorAtributo8 = indicadorAtributo8;
/* 2954:     */   }
/* 2955:     */   
/* 2956:     */   public Atributo getAtributoProduccion9()
/* 2957:     */   {
/* 2958:4072 */     return this.atributoProduccion9;
/* 2959:     */   }
/* 2960:     */   
/* 2961:     */   public void setAtributoProduccion9(Atributo atributoProduccion9)
/* 2962:     */   {
/* 2963:4076 */     this.atributoProduccion9 = atributoProduccion9;
/* 2964:     */   }
/* 2965:     */   
/* 2966:     */   public Atributo getTraAtributo9()
/* 2967:     */   {
/* 2968:4080 */     return this.traAtributo9;
/* 2969:     */   }
/* 2970:     */   
/* 2971:     */   public void setTraAtributo9(Atributo traAtributo9)
/* 2972:     */   {
/* 2973:4084 */     this.traAtributo9 = traAtributo9;
/* 2974:     */   }
/* 2975:     */   
/* 2976:     */   public boolean isIndicadorAtributo9()
/* 2977:     */   {
/* 2978:4088 */     return this.indicadorAtributo9;
/* 2979:     */   }
/* 2980:     */   
/* 2981:     */   public void setIndicadorAtributo9(boolean indicadorAtributo9)
/* 2982:     */   {
/* 2983:4092 */     this.indicadorAtributo9 = indicadorAtributo9;
/* 2984:     */   }
/* 2985:     */   
/* 2986:     */   public Atributo getAtributoProduccion10()
/* 2987:     */   {
/* 2988:4096 */     return this.atributoProduccion10;
/* 2989:     */   }
/* 2990:     */   
/* 2991:     */   public void setAtributoProduccion10(Atributo atributoProduccion10)
/* 2992:     */   {
/* 2993:4100 */     this.atributoProduccion10 = atributoProduccion10;
/* 2994:     */   }
/* 2995:     */   
/* 2996:     */   public Atributo getTraAtributo10()
/* 2997:     */   {
/* 2998:4104 */     return this.traAtributo10;
/* 2999:     */   }
/* 3000:     */   
/* 3001:     */   public void setTraAtributo10(Atributo traAtributo10)
/* 3002:     */   {
/* 3003:4108 */     this.traAtributo10 = traAtributo10;
/* 3004:     */   }
/* 3005:     */   
/* 3006:     */   public boolean isIndicadorAtributo10()
/* 3007:     */   {
/* 3008:4112 */     return this.indicadorAtributo10;
/* 3009:     */   }
/* 3010:     */   
/* 3011:     */   public void setIndicadorAtributo10(boolean indicadorAtributo10)
/* 3012:     */   {
/* 3013:4116 */     this.indicadorAtributo10 = indicadorAtributo10;
/* 3014:     */   }
/* 3015:     */   
/* 3016:     */   public ValorAtributo getValorAtributo1()
/* 3017:     */   {
/* 3018:4120 */     return this.valorAtributo1;
/* 3019:     */   }
/* 3020:     */   
/* 3021:     */   public void setValorAtributo1(ValorAtributo valorAtributo1)
/* 3022:     */   {
/* 3023:4124 */     this.valorAtributo1 = valorAtributo1;
/* 3024:     */   }
/* 3025:     */   
/* 3026:     */   public ValorAtributo getValorAtributo2()
/* 3027:     */   {
/* 3028:4128 */     return this.valorAtributo2;
/* 3029:     */   }
/* 3030:     */   
/* 3031:     */   public void setValorAtributo2(ValorAtributo valorAtributo2)
/* 3032:     */   {
/* 3033:4132 */     this.valorAtributo2 = valorAtributo2;
/* 3034:     */   }
/* 3035:     */   
/* 3036:     */   public ValorAtributo getValorAtributo3()
/* 3037:     */   {
/* 3038:4136 */     return this.valorAtributo3;
/* 3039:     */   }
/* 3040:     */   
/* 3041:     */   public void setValorAtributo3(ValorAtributo valorAtributo3)
/* 3042:     */   {
/* 3043:4140 */     this.valorAtributo3 = valorAtributo3;
/* 3044:     */   }
/* 3045:     */   
/* 3046:     */   public ValorAtributo getValorAtributo4()
/* 3047:     */   {
/* 3048:4144 */     return this.valorAtributo4;
/* 3049:     */   }
/* 3050:     */   
/* 3051:     */   public void setValorAtributo4(ValorAtributo valorAtributo4)
/* 3052:     */   {
/* 3053:4148 */     this.valorAtributo4 = valorAtributo4;
/* 3054:     */   }
/* 3055:     */   
/* 3056:     */   public ValorAtributo getValorAtributo5()
/* 3057:     */   {
/* 3058:4152 */     return this.valorAtributo5;
/* 3059:     */   }
/* 3060:     */   
/* 3061:     */   public void setValorAtributo5(ValorAtributo valorAtributo5)
/* 3062:     */   {
/* 3063:4156 */     this.valorAtributo5 = valorAtributo5;
/* 3064:     */   }
/* 3065:     */   
/* 3066:     */   public ValorAtributo getValorAtributo6()
/* 3067:     */   {
/* 3068:4160 */     return this.valorAtributo6;
/* 3069:     */   }
/* 3070:     */   
/* 3071:     */   public void setValorAtributo6(ValorAtributo valorAtributo6)
/* 3072:     */   {
/* 3073:4164 */     this.valorAtributo6 = valorAtributo6;
/* 3074:     */   }
/* 3075:     */   
/* 3076:     */   public ValorAtributo getValorAtributo7()
/* 3077:     */   {
/* 3078:4168 */     return this.valorAtributo7;
/* 3079:     */   }
/* 3080:     */   
/* 3081:     */   public void setValorAtributo7(ValorAtributo valorAtributo7)
/* 3082:     */   {
/* 3083:4172 */     this.valorAtributo7 = valorAtributo7;
/* 3084:     */   }
/* 3085:     */   
/* 3086:     */   public ValorAtributo getValorAtributo8()
/* 3087:     */   {
/* 3088:4176 */     return this.valorAtributo8;
/* 3089:     */   }
/* 3090:     */   
/* 3091:     */   public void setValorAtributo8(ValorAtributo valorAtributo8)
/* 3092:     */   {
/* 3093:4180 */     this.valorAtributo8 = valorAtributo8;
/* 3094:     */   }
/* 3095:     */   
/* 3096:     */   public ValorAtributo getValorAtributo9()
/* 3097:     */   {
/* 3098:4184 */     return this.valorAtributo9;
/* 3099:     */   }
/* 3100:     */   
/* 3101:     */   public void setValorAtributo9(ValorAtributo valorAtributo9)
/* 3102:     */   {
/* 3103:4188 */     this.valorAtributo9 = valorAtributo9;
/* 3104:     */   }
/* 3105:     */   
/* 3106:     */   public ValorAtributo getValorAtributo10()
/* 3107:     */   {
/* 3108:4192 */     return this.valorAtributo10;
/* 3109:     */   }
/* 3110:     */   
/* 3111:     */   public void setValorAtributo10(ValorAtributo valorAtributo10)
/* 3112:     */   {
/* 3113:4196 */     this.valorAtributo10 = valorAtributo10;
/* 3114:     */   }
/* 3115:     */   
/* 3116:     */   public boolean isIndicadorListaMaterial()
/* 3117:     */   {
/* 3118:4200 */     return this.indicadorListaMaterial;
/* 3119:     */   }
/* 3120:     */   
/* 3121:     */   public void setIndicadorListaMaterial(boolean indicadorListaMaterial)
/* 3122:     */   {
/* 3123:4204 */     this.indicadorListaMaterial = indicadorListaMaterial;
/* 3124:     */   }
/* 3125:     */   
/* 3126:     */   public List<MezclaProducto> getListaMezclaProducto()
/* 3127:     */   {
/* 3128:4208 */     return this.listaMezclaProducto;
/* 3129:     */   }
/* 3130:     */   
/* 3131:     */   public void setListaMezclaProducto(List<MezclaProducto> listaMezclaProducto)
/* 3132:     */   {
/* 3133:4212 */     this.listaMezclaProducto = listaMezclaProducto;
/* 3134:     */   }
/* 3135:     */   
/* 3136:     */   public boolean isIndicadorMezcla()
/* 3137:     */   {
/* 3138:4216 */     return this.indicadorMezcla;
/* 3139:     */   }
/* 3140:     */   
/* 3141:     */   public void setIndicadorMezcla(boolean indicadorMezcla)
/* 3142:     */   {
/* 3143:4220 */     this.indicadorMezcla = indicadorMezcla;
/* 3144:     */   }
/* 3145:     */   
/* 3146:     */   public boolean isIndicadorIncluirPedidoSugerido()
/* 3147:     */   {
/* 3148:4224 */     return this.indicadorIncluirPedidoSugerido;
/* 3149:     */   }
/* 3150:     */   
/* 3151:     */   public void setIndicadorIncluirPedidoSugerido(boolean indicadorIncluirPedidoSugerido)
/* 3152:     */   {
/* 3153:4228 */     this.indicadorIncluirPedidoSugerido = indicadorIncluirPedidoSugerido;
/* 3154:     */   }
/* 3155:     */   
/* 3156:     */   public BigDecimal getCoeficienteProduccion()
/* 3157:     */   {
/* 3158:4232 */     return this.coeficienteProduccion;
/* 3159:     */   }
/* 3160:     */   
/* 3161:     */   public void setCoeficienteProduccion(BigDecimal coeficienteProduccion)
/* 3162:     */   {
/* 3163:4236 */     this.coeficienteProduccion = coeficienteProduccion;
/* 3164:     */   }
/* 3165:     */   
/* 3166:     */   public boolean isIndicadorGeneraSuborden()
/* 3167:     */   {
/* 3168:4240 */     return this.indicadorGeneraSuborden;
/* 3169:     */   }
/* 3170:     */   
/* 3171:     */   public void setIndicadorGeneraSuborden(boolean indicadorGeneraSuborden)
/* 3172:     */   {
/* 3173:4244 */     this.indicadorGeneraSuborden = indicadorGeneraSuborden;
/* 3174:     */   }
/* 3175:     */   
/* 3176:     */   public boolean isTraConsumoDirecto()
/* 3177:     */   {
/* 3178:4248 */     return this.traConsumoDirecto;
/* 3179:     */   }
/* 3180:     */   
/* 3181:     */   public void setTraConsumoDirecto(boolean traConsumoDirecto)
/* 3182:     */   {
/* 3183:4252 */     this.traConsumoDirecto = traConsumoDirecto;
/* 3184:     */   }
/* 3185:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Producto
 * JD-Core Version:    0.7.0.1
 */