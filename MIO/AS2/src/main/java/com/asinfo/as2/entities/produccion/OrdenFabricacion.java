/*    1:     */ package com.asinfo.as2.entities.produccion;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.Atributo;
/*    4:     */ import com.asinfo.as2.entities.Bodega;
/*    5:     */ import com.asinfo.as2.entities.Documento;
/*    6:     */ import com.asinfo.as2.entities.EntidadBase;
/*    7:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*    8:     */ import com.asinfo.as2.entities.OrdenFabricacionMaquina;
/*    9:     */ import com.asinfo.as2.entities.OrdenFabricacionOrdenSalidaMaterial;
/*   10:     */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   11:     */ import com.asinfo.as2.entities.PersonaResponsable;
/*   12:     */ import com.asinfo.as2.entities.Producto;
/*   13:     */ import com.asinfo.as2.entities.Sucursal;
/*   14:     */ import com.asinfo.as2.entities.ValorAtributo;
/*   15:     */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*   16:     */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*   17:     */ import com.asinfo.as2.utils.EjbUtil;
/*   18:     */ import java.math.BigDecimal;
/*   19:     */ import java.math.RoundingMode;
/*   20:     */ import java.util.ArrayList;
/*   21:     */ import java.util.Date;
/*   22:     */ import java.util.List;
/*   23:     */ import javax.persistence.Column;
/*   24:     */ import javax.persistence.Entity;
/*   25:     */ import javax.persistence.EnumType;
/*   26:     */ import javax.persistence.Enumerated;
/*   27:     */ import javax.persistence.FetchType;
/*   28:     */ import javax.persistence.GeneratedValue;
/*   29:     */ import javax.persistence.GenerationType;
/*   30:     */ import javax.persistence.Id;
/*   31:     */ import javax.persistence.JoinColumn;
/*   32:     */ import javax.persistence.ManyToOne;
/*   33:     */ import javax.persistence.OneToMany;
/*   34:     */ import javax.persistence.Table;
/*   35:     */ import javax.persistence.TableGenerator;
/*   36:     */ import javax.persistence.Temporal;
/*   37:     */ import javax.persistence.TemporalType;
/*   38:     */ import javax.persistence.Transient;
/*   39:     */ import javax.validation.constraints.DecimalMin;
/*   40:     */ import javax.validation.constraints.Digits;
/*   41:     */ import javax.validation.constraints.NotNull;
/*   42:     */ import javax.validation.constraints.Size;
/*   43:     */ import org.hibernate.annotations.ColumnDefault;
/*   44:     */ 
/*   45:     */ @Entity
/*   46:     */ @Table(name="orden_fabricacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_documento", "numero"})})
/*   47:     */ public class OrdenFabricacion
/*   48:     */   extends EntidadBase
/*   49:     */ {
/*   50:     */   private static final long serialVersionUID = 43902058619899582L;
/*   51:     */   @Id
/*   52:     */   @TableGenerator(name="orden_fabricacion", initialValue=0, allocationSize=50)
/*   53:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="orden_fabricacion")
/*   54:     */   @Column(name="id_orden_fabricacion")
/*   55:     */   private int idOrdenFabricacion;
/*   56:     */   @Column(name="id_organizacion", nullable=false)
/*   57:     */   private int idOrganizacion;
/*   58:     */   @NotNull
/*   59:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   60:     */   @JoinColumn(name="id_sucursal", nullable=false)
/*   61:     */   private Sucursal sucursal;
/*   62:     */   @Column(name="numero", nullable=false, length=20, insertable=true, updatable=false)
/*   63:     */   @NotNull
/*   64:     */   @Size(max=20)
/*   65:     */   private String numero;
/*   66:     */   @Column(name="estado_produccion", nullable=false)
/*   67:     */   @Enumerated(EnumType.ORDINAL)
/*   68:     */   private EstadoProduccionEnum estado;
/*   69:     */   @Column(name="tipo_ciclo_produccion_enum", nullable=false)
/*   70:     */   @Enumerated(EnumType.ORDINAL)
/*   71:     */   @NotNull
/*   72:     */   @ColumnDefault("0")
/*   73: 100 */   private TipoCicloProduccionEnum tipoCicloProduccionEnum = TipoCicloProduccionEnum.CICLO_CORTO;
/*   74:     */   @Column(name="cantidad_inicial", precision=12, scale=4)
/*   75:     */   @Digits(integer=12, fraction=4)
/*   76:     */   @DecimalMin("0.0000")
/*   77:     */   private BigDecimal cantidadInicial;
/*   78:     */   @Column(name="cantidad", precision=12, scale=4)
/*   79:     */   @Digits(integer=12, fraction=4)
/*   80:     */   @DecimalMin("0.0001")
/*   81: 115 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*   82:     */   @Column(name="cantidad_batch", precision=12, scale=4)
/*   83:     */   @Digits(integer=12, fraction=4)
/*   84:     */   @DecimalMin("0.0000")
/*   85: 120 */   private BigDecimal cantidadBatch = BigDecimal.ZERO;
/*   86:     */   @Column(name="cantidad_fabricada", precision=12, scale=4)
/*   87:     */   @Digits(integer=12, fraction=4)
/*   88:     */   @DecimalMin("0.0000")
/*   89: 125 */   private BigDecimal cantidadFabricada = BigDecimal.ZERO;
/*   90:     */   @Column(name="cantidad_batch_fabricados", precision=12, scale=4)
/*   91:     */   @Digits(integer=12, fraction=4)
/*   92:     */   @DecimalMin("0.0000")
/*   93: 130 */   private BigDecimal cantidadBatchFabricados = BigDecimal.ZERO;
/*   94:     */   @Column(name="cantidad_rechazada", precision=12, scale=4)
/*   95:     */   @Digits(integer=12, fraction=4)
/*   96:     */   @DecimalMin("0.0000")
/*   97: 135 */   private BigDecimal cantidadRechazada = BigDecimal.ZERO;
/*   98:     */   @Temporal(TemporalType.DATE)
/*   99:     */   @Column(name="fecha", nullable=false)
/*  100:     */   @NotNull
/*  101:     */   private Date fecha;
/*  102:     */   @Temporal(TemporalType.TIMESTAMP)
/*  103:     */   @Column(name="fecha_lanzamiento", nullable=true)
/*  104:     */   private Date fechaLanzamiento;
/*  105:     */   @Temporal(TemporalType.TIMESTAMP)
/*  106:     */   @Column(name="fecha_inicio", nullable=true)
/*  107:     */   private Date fechaInicio;
/*  108:     */   @Temporal(TemporalType.TIMESTAMP)
/*  109:     */   @Column(name="fecha_cierre", nullable=true)
/*  110:     */   private Date fechaCierre;
/*  111:     */   @Column(name="descripcion", nullable=true, length=200)
/*  112:     */   @Size(max=200)
/*  113:     */   private String descripcion;
/*  114:     */   @Column(name="descripcion_formula", nullable=true, length=200)
/*  115:     */   @Size(max=200)
/*  116:     */   private String descripcionFormula;
/*  117:     */   @Column(name="materiales_solicitados", nullable=true)
/*  118:     */   private boolean materialesSolicitados;
/*  119:     */   @Transient
/*  120:     */   private BigDecimal cantidadPorProducir;
/*  121:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  122:     */   @JoinColumn(name="id_orden_fabricacion_formula", nullable=true)
/*  123:     */   private OrdenFabricacion ordenFabricacionFormulacion;
/*  124:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  125:     */   @JoinColumn(name="id_orden_fabricacion_principal", nullable=true)
/*  126:     */   private OrdenFabricacion ordenFabricacionPrincipal;
/*  127:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  128:     */   @JoinColumn(name="id_orden_fabricacion_padre", nullable=true)
/*  129:     */   private OrdenFabricacion ordenFabricacionPadre;
/*  130:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  131:     */   @JoinColumn(name="id_producto", nullable=true)
/*  132:     */   private Producto producto;
/*  133:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  134:     */   @JoinColumn(name="id_ruta_fabricacion", nullable=true)
/*  135:     */   private RutaFabricacion rutaFabricacion;
/*  136:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  137:     */   @JoinColumn(name="id_bodega", nullable=true)
/*  138:     */   private Bodega bodega;
/*  139:     */   @NotNull
/*  140:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  141:     */   @JoinColumn(name="id_documento", nullable=false)
/*  142:     */   private Documento documento;
/*  143:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  144:     */   @JoinColumn(name="id_plan_produccion", nullable=true)
/*  145:     */   private PlanProduccion planProduccion;
/*  146:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  147:     */   @JoinColumn(name="id_orden_salida_material_principal", nullable=true)
/*  148:     */   private OrdenSalidaMaterial ordenSalidaMaterialPrincipal;
/*  149:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  150:     */   @JoinColumn(name="id_responsable_salida_mercaderia", nullable=true)
/*  151:     */   private PersonaResponsable personaResponsable;
/*  152:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenFabricacion")
/*  153: 216 */   private List<DetalleOrdenFabricacion> detalleOrdenFabricacion = new ArrayList();
/*  154:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenFabricacion")
/*  155: 219 */   private List<MovimientoFabricacion> listaMovimientoFabricacion = new ArrayList();
/*  156:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenFabricacion")
/*  157: 222 */   private List<OperacionOrdenFabricacion> listaOperacionProduccion = new ArrayList();
/*  158:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenFabricacionPadre")
/*  159: 225 */   private List<OrdenFabricacion> listaSubordenes = new ArrayList();
/*  160:     */   @Column(name="indicador_formulada", nullable=false)
/*  161:     */   @NotNull
/*  162:     */   @ColumnDefault("'0'")
/*  163:     */   private boolean indicadorFormulada;
/*  164:     */   @Column(name="indicador_hoja", nullable=false)
/*  165:     */   @NotNull
/*  166:     */   @ColumnDefault("'1'")
/*  167: 233 */   private boolean indicadorHoja = true;
/*  168:     */   @Column(name="indicador_suborden", nullable=false)
/*  169:     */   @NotNull
/*  170:     */   @ColumnDefault("'0'")
/*  171: 238 */   private boolean indicadorSuborden = false;
/*  172:     */   @Column(name="indicador_directo", nullable=false)
/*  173:     */   @NotNull
/*  174:     */   @ColumnDefault("'1'")
/*  175: 243 */   private boolean indicadorDirecto = true;
/*  176:     */   @Column(name="cantidad_liberada_calidad", precision=12, scale=4)
/*  177:     */   @Digits(integer=12, fraction=4)
/*  178:     */   @DecimalMin("0.0000")
/*  179: 248 */   private BigDecimal cantidadLiberadaCalidad = BigDecimal.ZERO;
/*  180:     */   @Column(name="cantidad_rechazada_calidad", precision=12, scale=4)
/*  181:     */   @Digits(integer=12, fraction=4)
/*  182:     */   @DecimalMin("0.0000")
/*  183: 253 */   private BigDecimal cantidadRechazadaCalidad = BigDecimal.ZERO;
/*  184:     */   @Column(name="cantidad_cuarentena_calidad", precision=12, scale=4)
/*  185:     */   @Digits(integer=12, fraction=4)
/*  186:     */   @DecimalMin("0.0000")
/*  187: 258 */   private BigDecimal cantidadCuarentenaCalidad = BigDecimal.ZERO;
/*  188:     */   @Column(name="cantidad_reproceso_calidad", precision=12, scale=4)
/*  189:     */   @Digits(integer=12, fraction=4)
/*  190:     */   @DecimalMin("0.0000")
/*  191: 263 */   private BigDecimal cantidadReprocesoCalidad = BigDecimal.ZERO;
/*  192:     */   @Column(name="cantidad_utilizada_calidad", precision=12, scale=4)
/*  193:     */   @Digits(integer=12, fraction=4)
/*  194:     */   @DecimalMin("0.0000")
/*  195: 268 */   private BigDecimal cantidadUtilizadaCalidad = BigDecimal.ZERO;
/*  196:     */   @Column(name="cantidad_formulacion", precision=12, scale=2)
/*  197:     */   @Digits(integer=12, fraction=4)
/*  198:     */   @DecimalMin("0.0000")
/*  199: 273 */   private BigDecimal cantidadFormulacion = BigDecimal.ZERO;
/*  200:     */   @Column(name="proporcion_orden_padre", precision=12, scale=10)
/*  201:     */   @Digits(integer=12, fraction=10)
/*  202:     */   @DecimalMin("0.00")
/*  203: 278 */   private BigDecimal proporcionOrdenPadre = BigDecimal.ZERO;
/*  204:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenFabricacion")
/*  205: 283 */   private List<OrdenFabricacionOrdenSalidaMaterial> listaOrdenFabricacionOrdenSalidaMaterial = new ArrayList();
/*  206:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenFabricacion")
/*  207: 286 */   private List<OrdenFabricacionMaquina> listaOrdenFabricacionMaquina = new ArrayList();
/*  208:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  209:     */   @JoinColumn(name="id_atributo_orden_fabricacion", nullable=true)
/*  210:     */   private Atributo atributoOrdenFabricacion;
/*  211:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  212:     */   @JoinColumn(name="id_valor_atributo_orden_fabricacion", nullable=true)
/*  213:     */   private ValorAtributo valorAtributoOrdenFabricacion;
/*  214:     */   @Column(name="indicador_valida_stock_suborden", nullable=false)
/*  215:     */   @NotNull
/*  216:     */   @ColumnDefault("'0'")
/*  217: 297 */   private boolean indicadorValidaStockSuborden = false;
/*  218:     */   @Column(name="indicador_registrado_calidad", nullable=false)
/*  219:     */   @NotNull
/*  220:     */   @ColumnDefault("'0'")
/*  221:     */   private boolean indicadorRegistradoCalidad;
/*  222:     */   @Temporal(TemporalType.DATE)
/*  223:     */   @Column(name="fecha_formulacion", nullable=true)
/*  224:     */   private Date fechaFormulacion;
/*  225:     */   @Transient
/*  226:     */   private MovimientoInventario ingresoFabricacion;
/*  227:     */   @Transient
/*  228: 316 */   private BigDecimal costoMateriales = BigDecimal.ZERO;
/*  229:     */   @Transient
/*  230: 319 */   private BigDecimal costoManoObra = BigDecimal.ZERO;
/*  231:     */   @Transient
/*  232: 322 */   private BigDecimal costoDepreciacion = BigDecimal.ZERO;
/*  233:     */   @Transient
/*  234: 325 */   private BigDecimal costoIndirecto = BigDecimal.ZERO;
/*  235:     */   @Transient
/*  236: 328 */   private BigDecimal cantidadRequeridaCalidad = BigDecimal.ZERO;
/*  237:     */   @Transient
/*  238:     */   private BigDecimal saldoBodegaTrabajo;
/*  239:     */   @Transient
/*  240:     */   private Bodega bodegaTrabajo;
/*  241:     */   @Transient
/*  242: 337 */   private List<OperacionOrdenFabricacion> listaOperacionOrdenFabricacion = new ArrayList();
/*  243:     */   @Transient
/*  244: 340 */   private List<OrdenSalidaMaterial> listaOrdenSalidaMaterial = new ArrayList();
/*  245:     */   @Transient
/*  246: 343 */   private List<OrdenFabricacion> listaSubordenesFabricacion = new ArrayList();
/*  247:     */   @Transient
/*  248: 346 */   private List<HistoricoCalidadOrdenFabricacion> listaHistoricoCalidadOrdenFabricacion = new ArrayList();
/*  249:     */   @Transient
/*  250:     */   private boolean IndicadorRequiereFormulacion;
/*  251:     */   
/*  252:     */   public int getIdOrdenFabricacion()
/*  253:     */   {
/*  254: 365 */     return this.idOrdenFabricacion;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public void setIdOrdenFabricacion(int idOrdenFabricacion)
/*  258:     */   {
/*  259: 375 */     this.idOrdenFabricacion = idOrdenFabricacion;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public int getIdOrganizacion()
/*  263:     */   {
/*  264: 384 */     return this.idOrganizacion;
/*  265:     */   }
/*  266:     */   
/*  267:     */   public void setIdOrganizacion(int idOrganizacion)
/*  268:     */   {
/*  269: 394 */     this.idOrganizacion = idOrganizacion;
/*  270:     */   }
/*  271:     */   
/*  272:     */   public Sucursal getSucursal()
/*  273:     */   {
/*  274: 398 */     return this.sucursal;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public void setSucursal(Sucursal sucursal)
/*  278:     */   {
/*  279: 402 */     this.sucursal = sucursal;
/*  280:     */   }
/*  281:     */   
/*  282:     */   public String getNumero()
/*  283:     */   {
/*  284: 411 */     return this.numero;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public void setNumero(String numero)
/*  288:     */   {
/*  289: 421 */     this.numero = numero;
/*  290:     */   }
/*  291:     */   
/*  292:     */   public EstadoProduccionEnum getEstado()
/*  293:     */   {
/*  294: 430 */     return this.estado;
/*  295:     */   }
/*  296:     */   
/*  297:     */   public void setEstado(EstadoProduccionEnum estado)
/*  298:     */   {
/*  299: 440 */     this.estado = estado;
/*  300:     */   }
/*  301:     */   
/*  302:     */   public Date getFechaLanzamiento()
/*  303:     */   {
/*  304: 449 */     return this.fechaLanzamiento;
/*  305:     */   }
/*  306:     */   
/*  307:     */   public void setFechaLanzamiento(Date fechaLanzamiento)
/*  308:     */   {
/*  309: 459 */     this.fechaLanzamiento = fechaLanzamiento;
/*  310:     */   }
/*  311:     */   
/*  312:     */   public BigDecimal getCantidad()
/*  313:     */   {
/*  314: 468 */     if (this.cantidad == null) {
/*  315: 469 */       this.cantidad = BigDecimal.ZERO;
/*  316:     */     }
/*  317: 471 */     return this.cantidad;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public void setCantidad(BigDecimal cantidad)
/*  321:     */   {
/*  322: 481 */     this.cantidad = cantidad;
/*  323: 482 */     if (isIndicadorDirecto())
/*  324:     */     {
/*  325: 483 */       if ((getProducto() != null) && (getProducto().getCantidadProduccion() != null) && (cantidad != null)) {
/*  326: 484 */         this.cantidadBatch = cantidad.divide(getProducto().getCantidadProduccion(), 2, RoundingMode.HALF_UP);
/*  327:     */       }
/*  328:     */     }
/*  329: 487 */     else if (cantidad != null) {
/*  330: 488 */       this.cantidadBatch = cantidad.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
/*  331:     */     }
/*  332:     */   }
/*  333:     */   
/*  334:     */   public BigDecimal getCantidadFabricada()
/*  335:     */   {
/*  336: 494 */     if (this.cantidadFabricada == null) {
/*  337: 495 */       this.cantidadFabricada = BigDecimal.ZERO;
/*  338:     */     }
/*  339: 497 */     return this.cantidadFabricada;
/*  340:     */   }
/*  341:     */   
/*  342:     */   public void setCantidadFabricada(BigDecimal cantidadFabricada)
/*  343:     */   {
/*  344: 501 */     this.cantidadFabricada = cantidadFabricada;
/*  345:     */   }
/*  346:     */   
/*  347:     */   public BigDecimal getCantidadRechazada()
/*  348:     */   {
/*  349: 510 */     if (this.cantidadRechazada == null) {
/*  350: 511 */       this.cantidadRechazada = BigDecimal.ZERO;
/*  351:     */     }
/*  352: 513 */     return this.cantidadRechazada;
/*  353:     */   }
/*  354:     */   
/*  355:     */   public void setCantidadRechazada(BigDecimal cantidadRechazada)
/*  356:     */   {
/*  357: 523 */     this.cantidadRechazada = cantidadRechazada;
/*  358:     */   }
/*  359:     */   
/*  360:     */   public Date getFecha()
/*  361:     */   {
/*  362: 532 */     return this.fecha;
/*  363:     */   }
/*  364:     */   
/*  365:     */   public void setFecha(Date fecha)
/*  366:     */   {
/*  367: 542 */     this.fecha = fecha;
/*  368:     */   }
/*  369:     */   
/*  370:     */   public Date getFechaInicio()
/*  371:     */   {
/*  372: 551 */     return this.fechaInicio;
/*  373:     */   }
/*  374:     */   
/*  375:     */   public void setFechaInicio(Date fechaInicio)
/*  376:     */   {
/*  377: 561 */     this.fechaInicio = fechaInicio;
/*  378:     */   }
/*  379:     */   
/*  380:     */   public String getDescripcion()
/*  381:     */   {
/*  382: 570 */     return this.descripcion;
/*  383:     */   }
/*  384:     */   
/*  385:     */   public void setDescripcion(String descripcion)
/*  386:     */   {
/*  387: 580 */     this.descripcion = descripcion;
/*  388:     */   }
/*  389:     */   
/*  390:     */   public Producto getProducto()
/*  391:     */   {
/*  392: 589 */     return this.producto;
/*  393:     */   }
/*  394:     */   
/*  395:     */   public void setProducto(Producto producto)
/*  396:     */   {
/*  397: 599 */     this.producto = producto;
/*  398: 600 */     if ((getProducto() != null) && (getProducto().getCantidadProduccion() != null) && (this.cantidadBatch != null)) {
/*  399: 601 */       this.cantidad = this.cantidadBatch.multiply(getProducto().getCantidadProduccion()).setScale(2, RoundingMode.HALF_UP);
/*  400:     */     }
/*  401:     */   }
/*  402:     */   
/*  403:     */   public RutaFabricacion getRutaFabricacion()
/*  404:     */   {
/*  405: 611 */     return this.rutaFabricacion;
/*  406:     */   }
/*  407:     */   
/*  408:     */   public void setRutaFabricacion(RutaFabricacion rutaFabricacion)
/*  409:     */   {
/*  410: 621 */     this.rutaFabricacion = rutaFabricacion;
/*  411:     */   }
/*  412:     */   
/*  413:     */   public Bodega getBodega()
/*  414:     */   {
/*  415: 630 */     return this.bodega;
/*  416:     */   }
/*  417:     */   
/*  418:     */   public void setBodega(Bodega bodega)
/*  419:     */   {
/*  420: 640 */     this.bodega = bodega;
/*  421:     */   }
/*  422:     */   
/*  423:     */   public BigDecimal getCantidadInicial()
/*  424:     */   {
/*  425: 649 */     return this.cantidadInicial;
/*  426:     */   }
/*  427:     */   
/*  428:     */   public void setCantidadInicial(BigDecimal cantidadInicial)
/*  429:     */   {
/*  430: 659 */     this.cantidadInicial = cantidadInicial;
/*  431:     */   }
/*  432:     */   
/*  433:     */   public Date getFechaCierre()
/*  434:     */   {
/*  435: 668 */     return this.fechaCierre;
/*  436:     */   }
/*  437:     */   
/*  438:     */   public void setFechaCierre(Date fechaCierre)
/*  439:     */   {
/*  440: 678 */     this.fechaCierre = fechaCierre;
/*  441:     */   }
/*  442:     */   
/*  443:     */   public int getId()
/*  444:     */   {
/*  445: 688 */     return this.idOrdenFabricacion;
/*  446:     */   }
/*  447:     */   
/*  448:     */   public Documento getDocumento()
/*  449:     */   {
/*  450: 697 */     return this.documento;
/*  451:     */   }
/*  452:     */   
/*  453:     */   public void setDocumento(Documento documento)
/*  454:     */   {
/*  455: 707 */     this.documento = documento;
/*  456:     */   }
/*  457:     */   
/*  458:     */   public boolean isMaterialesSolicitados()
/*  459:     */   {
/*  460: 716 */     return this.materialesSolicitados;
/*  461:     */   }
/*  462:     */   
/*  463:     */   public void setMaterialesSolicitados(boolean materialesSolicitados)
/*  464:     */   {
/*  465: 726 */     this.materialesSolicitados = materialesSolicitados;
/*  466:     */   }
/*  467:     */   
/*  468:     */   public List<DetalleOrdenFabricacion> getDetalleOrdenFabricacion()
/*  469:     */   {
/*  470: 730 */     return this.detalleOrdenFabricacion;
/*  471:     */   }
/*  472:     */   
/*  473:     */   public void setDetalleOrdenFabricacion(List<DetalleOrdenFabricacion> detalleOrdenFabricacion)
/*  474:     */   {
/*  475: 734 */     this.detalleOrdenFabricacion = detalleOrdenFabricacion;
/*  476:     */   }
/*  477:     */   
/*  478:     */   public OrdenFabricacion getOrdenFabricacionPrincipal()
/*  479:     */   {
/*  480: 741 */     return this.ordenFabricacionPrincipal;
/*  481:     */   }
/*  482:     */   
/*  483:     */   public void setOrdenFabricacionPrincipal(OrdenFabricacion ordenFabricacionPrincipal)
/*  484:     */   {
/*  485: 749 */     this.ordenFabricacionPrincipal = ordenFabricacionPrincipal;
/*  486:     */   }
/*  487:     */   
/*  488:     */   public OrdenFabricacion getOrdenFabricacionPadre()
/*  489:     */   {
/*  490: 753 */     return this.ordenFabricacionPadre;
/*  491:     */   }
/*  492:     */   
/*  493:     */   public void setOrdenFabricacionPadre(OrdenFabricacion ordenFabricacionPadre)
/*  494:     */   {
/*  495: 757 */     this.ordenFabricacionPadre = ordenFabricacionPadre;
/*  496:     */   }
/*  497:     */   
/*  498:     */   public List<MovimientoFabricacion> getListaMovimientoFabricacion()
/*  499:     */   {
/*  500: 761 */     return this.listaMovimientoFabricacion;
/*  501:     */   }
/*  502:     */   
/*  503:     */   public void setListaMovimientoFabricacion(List<MovimientoFabricacion> listaMovimientoFabricacion)
/*  504:     */   {
/*  505: 765 */     this.listaMovimientoFabricacion = listaMovimientoFabricacion;
/*  506:     */   }
/*  507:     */   
/*  508:     */   public PlanProduccion getPlanProduccion()
/*  509:     */   {
/*  510: 769 */     return this.planProduccion;
/*  511:     */   }
/*  512:     */   
/*  513:     */   public void setPlanProduccion(PlanProduccion planProduccion)
/*  514:     */   {
/*  515: 773 */     this.planProduccion = planProduccion;
/*  516:     */   }
/*  517:     */   
/*  518:     */   public BigDecimal getCantidadPorProducir()
/*  519:     */   {
/*  520: 777 */     this.cantidadPorProducir = getCantidad().subtract(getCantidadFabricada());
/*  521: 778 */     if (this.cantidadPorProducir.compareTo(BigDecimal.ZERO) < 0) {
/*  522: 779 */       this.cantidadPorProducir = BigDecimal.ZERO;
/*  523:     */     }
/*  524: 781 */     return this.cantidadPorProducir;
/*  525:     */   }
/*  526:     */   
/*  527:     */   public void setCantidadPorProducir(BigDecimal cantidadPorProducir)
/*  528:     */   {
/*  529: 785 */     this.cantidadPorProducir = cantidadPorProducir;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public BigDecimal getCantidadBatch()
/*  533:     */   {
/*  534: 789 */     return this.cantidadBatch;
/*  535:     */   }
/*  536:     */   
/*  537:     */   public void setCantidadBatch(BigDecimal cantidadBatch)
/*  538:     */   {
/*  539: 793 */     this.cantidadBatch = cantidadBatch;
/*  540: 794 */     if (isIndicadorDirecto())
/*  541:     */     {
/*  542: 795 */       if ((getProducto() != null) && (getProducto().getCantidadProduccion() != null) && (cantidadBatch != null)) {
/*  543: 796 */         this.cantidad = cantidadBatch.multiply(getProducto().getCantidadProduccion()).setScale(2, RoundingMode.HALF_UP);
/*  544:     */       }
/*  545:     */     }
/*  546: 799 */     else if (cantidadBatch != null) {
/*  547: 800 */       this.cantidad = cantidadBatch.multiply(BigDecimal.ONE).setScale(2, RoundingMode.HALF_UP);
/*  548:     */     }
/*  549:     */   }
/*  550:     */   
/*  551:     */   public List<OperacionOrdenFabricacion> getListaOperacionProduccion()
/*  552:     */   {
/*  553: 807 */     return this.listaOperacionProduccion;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public void setListaOperacionProduccion(List<OperacionOrdenFabricacion> listaOperacionProduccion)
/*  557:     */   {
/*  558: 811 */     this.listaOperacionProduccion = listaOperacionProduccion;
/*  559:     */   }
/*  560:     */   
/*  561:     */   public BigDecimal getCostoMateriales()
/*  562:     */   {
/*  563: 815 */     return this.costoMateriales;
/*  564:     */   }
/*  565:     */   
/*  566:     */   public void setCostoMateriales(BigDecimal costoMateriales)
/*  567:     */   {
/*  568: 819 */     this.costoMateriales = costoMateriales;
/*  569:     */   }
/*  570:     */   
/*  571:     */   public BigDecimal getCostoManoObra()
/*  572:     */   {
/*  573: 823 */     return this.costoManoObra;
/*  574:     */   }
/*  575:     */   
/*  576:     */   public void setCostoManoObra(BigDecimal costoManoObra)
/*  577:     */   {
/*  578: 827 */     this.costoManoObra = costoManoObra;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public BigDecimal getCostoDepreciacion()
/*  582:     */   {
/*  583: 831 */     return this.costoDepreciacion;
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void setCostoDepreciacion(BigDecimal costoDepreciacion)
/*  587:     */   {
/*  588: 835 */     this.costoDepreciacion = costoDepreciacion;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public BigDecimal getCostoIndirecto()
/*  592:     */   {
/*  593: 839 */     return this.costoIndirecto;
/*  594:     */   }
/*  595:     */   
/*  596:     */   public void setCostoIndirecto(BigDecimal costoIndirecto)
/*  597:     */   {
/*  598: 843 */     this.costoIndirecto = costoIndirecto;
/*  599:     */   }
/*  600:     */   
/*  601:     */   public BigDecimal getCostoTotal()
/*  602:     */   {
/*  603: 847 */     return this.costoMateriales.add(this.costoManoObra).add(this.costoDepreciacion).add(this.costoIndirecto);
/*  604:     */   }
/*  605:     */   
/*  606:     */   public BigDecimal getCostoUnitario()
/*  607:     */   {
/*  608: 851 */     if (getCantidadFabricada().compareTo(BigDecimal.ZERO) == 0) {
/*  609: 852 */       return BigDecimal.ZERO;
/*  610:     */     }
/*  611: 854 */     return getCostoTotal().divide(getCantidadFabricada(), 4, RoundingMode.HALF_UP);
/*  612:     */   }
/*  613:     */   
/*  614:     */   public BigDecimal getCostoUnitarioMateriales()
/*  615:     */   {
/*  616: 858 */     return getCostoMateriales().divide(getCantidadFabricada(), 4, RoundingMode.HALF_UP);
/*  617:     */   }
/*  618:     */   
/*  619:     */   public BigDecimal getCostoUnitarioManoObra()
/*  620:     */   {
/*  621: 862 */     return getCostoManoObra().divide(getCantidadFabricada(), 4, RoundingMode.HALF_UP);
/*  622:     */   }
/*  623:     */   
/*  624:     */   public BigDecimal getCostoUnitarioDepreciacion()
/*  625:     */   {
/*  626: 866 */     return getCostoDepreciacion().divide(getCantidadFabricada(), 4, RoundingMode.HALF_UP);
/*  627:     */   }
/*  628:     */   
/*  629:     */   public BigDecimal getCostoUnitarioIndirecto()
/*  630:     */   {
/*  631: 870 */     return getCostoIndirecto().divide(getCantidadFabricada(), 4, RoundingMode.HALF_UP);
/*  632:     */   }
/*  633:     */   
/*  634:     */   public BigDecimal getCantidadBatchFabricados()
/*  635:     */   {
/*  636: 874 */     return this.cantidadBatchFabricados;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void setCantidadBatchFabricados(BigDecimal cantidadBatchFabricados)
/*  640:     */   {
/*  641: 878 */     this.cantidadBatchFabricados = cantidadBatchFabricados;
/*  642:     */   }
/*  643:     */   
/*  644:     */   public String getDescripcionFormula()
/*  645:     */   {
/*  646: 882 */     return this.descripcionFormula;
/*  647:     */   }
/*  648:     */   
/*  649:     */   public void setDescripcionFormula(String descripcionFormula)
/*  650:     */   {
/*  651: 886 */     this.descripcionFormula = descripcionFormula;
/*  652:     */   }
/*  653:     */   
/*  654:     */   public OrdenFabricacion getOrdenFabricacionFormulacion()
/*  655:     */   {
/*  656: 890 */     return this.ordenFabricacionFormulacion;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void setOrdenFabricacionFormulacion(OrdenFabricacion ordenFabricacionFormulacion)
/*  660:     */   {
/*  661: 894 */     this.ordenFabricacionFormulacion = ordenFabricacionFormulacion;
/*  662:     */   }
/*  663:     */   
/*  664:     */   public boolean isIndicadorFormulada()
/*  665:     */   {
/*  666: 898 */     return this.indicadorFormulada;
/*  667:     */   }
/*  668:     */   
/*  669:     */   public void setIndicadorFormulada(boolean indicadorFormulada)
/*  670:     */   {
/*  671: 902 */     this.indicadorFormulada = indicadorFormulada;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public boolean isIndicadorHoja()
/*  675:     */   {
/*  676: 906 */     return this.indicadorHoja;
/*  677:     */   }
/*  678:     */   
/*  679:     */   public void setIndicadorHoja(boolean indicadorHoja)
/*  680:     */   {
/*  681: 910 */     this.indicadorHoja = indicadorHoja;
/*  682:     */   }
/*  683:     */   
/*  684:     */   public List<OrdenFabricacion> getListaSubordenes()
/*  685:     */   {
/*  686: 914 */     return this.listaSubordenes;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public void setListaSubordenes(List<OrdenFabricacion> listaSubordenes)
/*  690:     */   {
/*  691: 918 */     this.listaSubordenes = listaSubordenes;
/*  692:     */   }
/*  693:     */   
/*  694:     */   public boolean isIndicadorSuborden()
/*  695:     */   {
/*  696: 922 */     return this.indicadorSuborden;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public void setIndicadorSuborden(boolean indicadorSuborden)
/*  700:     */   {
/*  701: 926 */     this.indicadorSuborden = indicadorSuborden;
/*  702:     */   }
/*  703:     */   
/*  704:     */   public BigDecimal getCantidadLiberadaCalidad()
/*  705:     */   {
/*  706: 930 */     if (this.cantidadLiberadaCalidad == null) {
/*  707: 931 */       this.cantidadLiberadaCalidad = BigDecimal.ZERO;
/*  708:     */     }
/*  709: 933 */     return this.cantidadLiberadaCalidad;
/*  710:     */   }
/*  711:     */   
/*  712:     */   public void setCantidadLiberadaCalidad(BigDecimal cantidadLiberadaCalidad)
/*  713:     */   {
/*  714: 937 */     this.cantidadLiberadaCalidad = cantidadLiberadaCalidad;
/*  715:     */   }
/*  716:     */   
/*  717:     */   public BigDecimal getCantidadRechazadaCalidad()
/*  718:     */   {
/*  719: 941 */     if (this.cantidadRechazadaCalidad == null) {
/*  720: 942 */       this.cantidadRechazadaCalidad = BigDecimal.ZERO;
/*  721:     */     }
/*  722: 944 */     return this.cantidadRechazadaCalidad;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void setCantidadRechazadaCalidad(BigDecimal cantidadRechazadaCalidad)
/*  726:     */   {
/*  727: 948 */     this.cantidadRechazadaCalidad = cantidadRechazadaCalidad;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public BigDecimal getCantidadCuarentenaCalidad()
/*  731:     */   {
/*  732: 952 */     if (this.cantidadCuarentenaCalidad == null) {
/*  733: 953 */       this.cantidadCuarentenaCalidad = BigDecimal.ZERO;
/*  734:     */     }
/*  735: 955 */     return this.cantidadCuarentenaCalidad;
/*  736:     */   }
/*  737:     */   
/*  738:     */   public void setCantidadCuarentenaCalidad(BigDecimal cantidadCuarentenaCalidad)
/*  739:     */   {
/*  740: 959 */     this.cantidadCuarentenaCalidad = cantidadCuarentenaCalidad;
/*  741:     */   }
/*  742:     */   
/*  743:     */   public BigDecimal getCantidadReprocesoCalidad()
/*  744:     */   {
/*  745: 963 */     if (this.cantidadReprocesoCalidad == null) {
/*  746: 964 */       this.cantidadReprocesoCalidad = BigDecimal.ZERO;
/*  747:     */     }
/*  748: 966 */     return this.cantidadReprocesoCalidad;
/*  749:     */   }
/*  750:     */   
/*  751:     */   public void setCantidadReprocesoCalidad(BigDecimal cantidadReprocesoCalidad)
/*  752:     */   {
/*  753: 970 */     this.cantidadReprocesoCalidad = cantidadReprocesoCalidad;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public BigDecimal getCantidadUtilizadaCalidad()
/*  757:     */   {
/*  758: 974 */     if (this.cantidadUtilizadaCalidad == null) {
/*  759: 975 */       this.cantidadUtilizadaCalidad = BigDecimal.ZERO;
/*  760:     */     }
/*  761: 977 */     return this.cantidadUtilizadaCalidad;
/*  762:     */   }
/*  763:     */   
/*  764:     */   public void setCantidadUtilizadaCalidad(BigDecimal cantidadUtilizadaCalidad)
/*  765:     */   {
/*  766: 981 */     this.cantidadUtilizadaCalidad = cantidadUtilizadaCalidad;
/*  767:     */   }
/*  768:     */   
/*  769:     */   public BigDecimal getCantidadDisponibleCalidad()
/*  770:     */   {
/*  771: 985 */     BigDecimal cantidadDisponible = getCantidadFabricada().subtract(getCantidadUtilizadaCalidad());
/*  772: 986 */     return cantidadDisponible;
/*  773:     */   }
/*  774:     */   
/*  775:     */   public BigDecimal getCantidadRequeridaCalidad()
/*  776:     */   {
/*  777: 990 */     return this.cantidadRequeridaCalidad;
/*  778:     */   }
/*  779:     */   
/*  780:     */   public void setCantidadRequeridaCalidad(BigDecimal cantidadRequeridaCalidad)
/*  781:     */   {
/*  782: 994 */     this.cantidadRequeridaCalidad = cantidadRequeridaCalidad;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public BigDecimal getSaldoBodegaTrabajo()
/*  786:     */   {
/*  787: 998 */     return this.saldoBodegaTrabajo;
/*  788:     */   }
/*  789:     */   
/*  790:     */   public void setSaldoBodegaTrabajo(BigDecimal saldoBodegaTrabajo)
/*  791:     */   {
/*  792:1002 */     this.saldoBodegaTrabajo = saldoBodegaTrabajo;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public Bodega getBodegaTrabajo()
/*  796:     */   {
/*  797:1006 */     return this.bodegaTrabajo;
/*  798:     */   }
/*  799:     */   
/*  800:     */   public void setBodegaTrabajo(Bodega bodegaTrabajo)
/*  801:     */   {
/*  802:1010 */     this.bodegaTrabajo = bodegaTrabajo;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public BigDecimal getCantidadFormulacion()
/*  806:     */   {
/*  807:1014 */     return this.cantidadFormulacion;
/*  808:     */   }
/*  809:     */   
/*  810:     */   public void setCantidadFormulacion(BigDecimal cantidadFormulacion)
/*  811:     */   {
/*  812:1018 */     this.cantidadFormulacion = cantidadFormulacion;
/*  813:     */   }
/*  814:     */   
/*  815:     */   public BigDecimal getProporcionOrdenPadre()
/*  816:     */   {
/*  817:1022 */     if (this.proporcionOrdenPadre == null) {
/*  818:1023 */       this.proporcionOrdenPadre = BigDecimal.ZERO;
/*  819:     */     }
/*  820:1025 */     return this.proporcionOrdenPadre;
/*  821:     */   }
/*  822:     */   
/*  823:     */   public void setProporcionOrdenPadre(BigDecimal proporcionOrdenPadre)
/*  824:     */   {
/*  825:1029 */     this.proporcionOrdenPadre = proporcionOrdenPadre;
/*  826:     */   }
/*  827:     */   
/*  828:     */   public TipoCicloProduccionEnum getTipoCicloProduccionEnum()
/*  829:     */   {
/*  830:1033 */     return this.tipoCicloProduccionEnum;
/*  831:     */   }
/*  832:     */   
/*  833:     */   public void setTipoCicloProduccionEnum(TipoCicloProduccionEnum tipoCicloProduccionEnum)
/*  834:     */   {
/*  835:1037 */     this.tipoCicloProduccionEnum = tipoCicloProduccionEnum;
/*  836:     */   }
/*  837:     */   
/*  838:     */   public List<OrdenFabricacionOrdenSalidaMaterial> getListaOrdenFabricacionOrdenSalidaMaterial()
/*  839:     */   {
/*  840:1041 */     return this.listaOrdenFabricacionOrdenSalidaMaterial;
/*  841:     */   }
/*  842:     */   
/*  843:     */   public void setListaOrdenFabricacionOrdenSalidaMaterial(List<OrdenFabricacionOrdenSalidaMaterial> listaOrdenFabricacionOrdenSalidaMaterial)
/*  844:     */   {
/*  845:1045 */     this.listaOrdenFabricacionOrdenSalidaMaterial = listaOrdenFabricacionOrdenSalidaMaterial;
/*  846:     */   }
/*  847:     */   
/*  848:     */   public OrdenSalidaMaterial getOrdenSalidaMaterialPrincipal()
/*  849:     */   {
/*  850:1049 */     return this.ordenSalidaMaterialPrincipal;
/*  851:     */   }
/*  852:     */   
/*  853:     */   public void setOrdenSalidaMaterialPrincipal(OrdenSalidaMaterial ordenSalidaMaterialPrincipal)
/*  854:     */   {
/*  855:1053 */     this.ordenSalidaMaterialPrincipal = ordenSalidaMaterialPrincipal;
/*  856:     */   }
/*  857:     */   
/*  858:     */   public int getCantidadOSMMostrar()
/*  859:     */   {
/*  860:1057 */     int cantidad = 0;
/*  861:1058 */     if (getListaOrdenFabricacionOrdenSalidaMaterial().size() > 5) {
/*  862:1059 */       cantidad = 5;
/*  863:     */     } else {
/*  864:1061 */       cantidad = getListaOrdenFabricacionOrdenSalidaMaterial().size();
/*  865:     */     }
/*  866:1063 */     return cantidad;
/*  867:     */   }
/*  868:     */   
/*  869:     */   public List<OperacionOrdenFabricacion> getListaOperacionOrdenFabricacion()
/*  870:     */   {
/*  871:1067 */     return this.listaOperacionOrdenFabricacion;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void setListaOperacionOrdenFabricacion(List<OperacionOrdenFabricacion> listaOperacionOrdenFabricacion)
/*  875:     */   {
/*  876:1071 */     this.listaOperacionOrdenFabricacion = listaOperacionOrdenFabricacion;
/*  877:     */   }
/*  878:     */   
/*  879:     */   public BigDecimal getPorcentajeFabricado()
/*  880:     */   {
/*  881:1075 */     return this.cantidadFabricada.divide(this.cantidad, 6, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
/*  882:     */   }
/*  883:     */   
/*  884:     */   public List<OrdenSalidaMaterial> getListaOrdenSalidaMaterial()
/*  885:     */   {
/*  886:1079 */     return this.listaOrdenSalidaMaterial;
/*  887:     */   }
/*  888:     */   
/*  889:     */   public void setListaOrdenSalidaMaterial(List<OrdenSalidaMaterial> listaOrdenSalidaMaterial)
/*  890:     */   {
/*  891:1083 */     this.listaOrdenSalidaMaterial = listaOrdenSalidaMaterial;
/*  892:     */   }
/*  893:     */   
/*  894:     */   public PersonaResponsable getPersonaResponsable()
/*  895:     */   {
/*  896:1087 */     return this.personaResponsable;
/*  897:     */   }
/*  898:     */   
/*  899:     */   public void setPersonaResponsable(PersonaResponsable personaResponsable)
/*  900:     */   {
/*  901:1091 */     this.personaResponsable = personaResponsable;
/*  902:     */   }
/*  903:     */   
/*  904:     */   public List<OrdenFabricacionMaquina> getListaOrdenFabricacionMaquina()
/*  905:     */   {
/*  906:1095 */     return this.listaOrdenFabricacionMaquina;
/*  907:     */   }
/*  908:     */   
/*  909:     */   public void setListaOrdenFabricacionMaquina(List<OrdenFabricacionMaquina> listaOrdenFabricacionMaquina)
/*  910:     */   {
/*  911:1099 */     this.listaOrdenFabricacionMaquina = listaOrdenFabricacionMaquina;
/*  912:     */   }
/*  913:     */   
/*  914:     */   public boolean isIndicadorDirecto()
/*  915:     */   {
/*  916:1103 */     return this.indicadorDirecto;
/*  917:     */   }
/*  918:     */   
/*  919:     */   public void setIndicadorDirecto(boolean indicadorDirecto)
/*  920:     */   {
/*  921:1107 */     this.indicadorDirecto = indicadorDirecto;
/*  922:     */   }
/*  923:     */   
/*  924:     */   public Atributo getAtributoOrdenFabricacion()
/*  925:     */   {
/*  926:1111 */     return this.atributoOrdenFabricacion;
/*  927:     */   }
/*  928:     */   
/*  929:     */   public void setAtributoOrdenFabricacion(Atributo atributoOrdenFabricacion)
/*  930:     */   {
/*  931:1115 */     this.atributoOrdenFabricacion = atributoOrdenFabricacion;
/*  932:     */   }
/*  933:     */   
/*  934:     */   public ValorAtributo getValorAtributoOrdenFabricacion()
/*  935:     */   {
/*  936:1119 */     return this.valorAtributoOrdenFabricacion;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public void setValorAtributoOrdenFabricacion(ValorAtributo valorAtributoOrdenFabricacion)
/*  940:     */   {
/*  941:1123 */     this.valorAtributoOrdenFabricacion = valorAtributoOrdenFabricacion;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public boolean isIndicadorRequiereFormulacion()
/*  945:     */   {
/*  946:1127 */     return this.IndicadorRequiereFormulacion;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public void setIndicadorRequiereFormulacion(boolean indicadorRequiereFormulacion)
/*  950:     */   {
/*  951:1131 */     this.IndicadorRequiereFormulacion = indicadorRequiereFormulacion;
/*  952:     */   }
/*  953:     */   
/*  954:     */   public List<OrdenFabricacion> getListaSubordenesFabricacion()
/*  955:     */   {
/*  956:1135 */     return this.listaSubordenesFabricacion;
/*  957:     */   }
/*  958:     */   
/*  959:     */   public void setListaSubordenesFabricacion(List<OrdenFabricacion> listaSubordenesFabricacion)
/*  960:     */   {
/*  961:1139 */     this.listaSubordenesFabricacion = listaSubordenesFabricacion;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public MovimientoInventario getIngresoFabricacion()
/*  965:     */   {
/*  966:1143 */     return this.ingresoFabricacion;
/*  967:     */   }
/*  968:     */   
/*  969:     */   public void setIngresoFabricacion(MovimientoInventario ingresoFabricacion)
/*  970:     */   {
/*  971:1147 */     this.ingresoFabricacion = ingresoFabricacion;
/*  972:     */   }
/*  973:     */   
/*  974:     */   public List<HistoricoCalidadOrdenFabricacion> getListaHistoricoCalidadOrdenFabricacion()
/*  975:     */   {
/*  976:1151 */     return this.listaHistoricoCalidadOrdenFabricacion;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public void setListaHistoricoCalidadOrdenFabricacion(List<HistoricoCalidadOrdenFabricacion> listaHistoricoCalidadOrdenFabricacion)
/*  980:     */   {
/*  981:1155 */     this.listaHistoricoCalidadOrdenFabricacion = listaHistoricoCalidadOrdenFabricacion;
/*  982:     */   }
/*  983:     */   
/*  984:     */   public boolean isIndicadorRegistradoCalidad()
/*  985:     */   {
/*  986:1159 */     return this.indicadorRegistradoCalidad;
/*  987:     */   }
/*  988:     */   
/*  989:     */   public void setIndicadorRegistradoCalidad(boolean indicadorRegistradoCalidad)
/*  990:     */   {
/*  991:1163 */     this.indicadorRegistradoCalidad = indicadorRegistradoCalidad;
/*  992:     */   }
/*  993:     */   
/*  994:     */   public boolean isIndicadorValidaStockSuborden()
/*  995:     */   {
/*  996:1167 */     return this.indicadorValidaStockSuborden;
/*  997:     */   }
/*  998:     */   
/*  999:     */   public void setIndicadorValidaStockSuborden(boolean indicadorValidaStockSuborden)
/* 1000:     */   {
/* 1001:1171 */     this.indicadorValidaStockSuborden = indicadorValidaStockSuborden;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public List<OrdenFabricacion> getListaSubordenesView()
/* 1005:     */   {
/* 1006:1175 */     return EjbUtil.getEntidadesNoEliminadas(this.listaSubordenes);
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public Date getFechaFormulacion()
/* 1010:     */   {
/* 1011:1179 */     return this.fechaFormulacion;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public void setFechaFormulacion(Date fechaFormulacion)
/* 1015:     */   {
/* 1016:1183 */     this.fechaFormulacion = fechaFormulacion;
/* 1017:     */   }
/* 1018:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.OrdenFabricacion
 * JD-Core Version:    0.7.0.1
 */