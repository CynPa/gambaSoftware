/*    1:     */ package com.asinfo.as2.entities;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
/*    4:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*    5:     */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*    6:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*    7:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*    8:     */ import com.asinfo.as2.enumeraciones.Estado;
/*    9:     */ import com.asinfo.as2.enumeraciones.OrigenEnum;
/*   10:     */ import com.asinfo.as2.utils.validacion.email.Emails;
/*   11:     */ import java.io.Serializable;
/*   12:     */ import java.math.BigDecimal;
/*   13:     */ import java.util.ArrayList;
/*   14:     */ import java.util.Date;
/*   15:     */ import java.util.List;
/*   16:     */ import javax.persistence.Column;
/*   17:     */ import javax.persistence.Entity;
/*   18:     */ import javax.persistence.EnumType;
/*   19:     */ import javax.persistence.Enumerated;
/*   20:     */ import javax.persistence.FetchType;
/*   21:     */ import javax.persistence.GeneratedValue;
/*   22:     */ import javax.persistence.GenerationType;
/*   23:     */ import javax.persistence.Id;
/*   24:     */ import javax.persistence.JoinColumn;
/*   25:     */ import javax.persistence.ManyToOne;
/*   26:     */ import javax.persistence.OneToMany;
/*   27:     */ import javax.persistence.OneToOne;
/*   28:     */ import javax.persistence.Table;
/*   29:     */ import javax.persistence.TableGenerator;
/*   30:     */ import javax.persistence.Temporal;
/*   31:     */ import javax.persistence.TemporalType;
/*   32:     */ import javax.persistence.Transient;
/*   33:     */ import javax.validation.constraints.Digits;
/*   34:     */ import javax.validation.constraints.Min;
/*   35:     */ import javax.validation.constraints.NotNull;
/*   36:     */ import javax.validation.constraints.Size;
/*   37:     */ import org.hibernate.annotations.ColumnDefault;
/*   38:     */ 
/*   39:     */ @Entity
/*   40:     */ @Table(name="factura_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_documento", "numero"})}, indexes={@javax.persistence.Index(columnList="id_empresa"), @javax.persistence.Index(columnList="id_factura_cliente_padre"), @javax.persistence.Index(columnList="numero"), @javax.persistence.Index(columnList="fecha"), @javax.persistence.Index(name="ix_factura_cliente_estado", columnList="estado"), @javax.persistence.Index(name="ix_factura_cliente_organizacion", columnList="id_organizacion")})
/*   41:     */ public class FacturaCliente
/*   42:     */   extends EntidadBase
/*   43:     */   implements Serializable
/*   44:     */ {
/*   45:     */   private static final long serialVersionUID = 4940608110592953095L;
/*   46:     */   @Id
/*   47:     */   @TableGenerator(name="factura_cliente", initialValue=0, allocationSize=50)
/*   48:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="factura_cliente")
/*   49:     */   @Column(name="id_factura_cliente")
/*   50:     */   private int idFacturaCliente;
/*   51:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*   52:     */   @JoinColumn(name="id_sucursal", nullable=true)
/*   53:     */   private Sucursal sucursal;
/*   54:     */   @Column(name="id_organizacion", nullable=false)
/*   55:     */   @NotNull
/*   56:     */   private int idOrganizacion;
/*   57:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*   58:     */   @JoinColumn(name="id_documento", nullable=true)
/*   59:     */   @NotNull
/*   60:     */   private Documento documento;
/*   61:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*   62:     */   @JoinColumn(name="id_empresa", nullable=true)
/*   63:     */   @NotNull
/*   64:     */   private Empresa empresa;
/*   65:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*   66:     */   @JoinColumn(name="id_condicion_pago", nullable=true)
/*   67:     */   private CondicionPago condicionPago;
/*   68:     */   @OneToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*   69:     */   @JoinColumn(name="id_asiento", nullable=true)
/*   70:     */   private Asiento asiento;
/*   71:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*   72:     */   @JoinColumn(name="id_direccion_empresa", nullable=false)
/*   73:     */   @NotNull
/*   74:     */   private DireccionEmpresa direccionEmpresa;
/*   75:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*   76:     */   @JoinColumn(name="id_zona", nullable=true)
/*   77:     */   private Zona zona;
/*   78:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*   79:     */   @JoinColumn(name="id_canal", nullable=true)
/*   80:     */   private Canal canal;
/*   81:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   82:     */   @JoinColumn(name="id_motivo_nota_credito_cliente", nullable=true)
/*   83:     */   private MotivoNotaCreditoCliente motivoNotaCreditoCliente;
/*   84:     */   @Column(name="numero", nullable=false, length=20)
/*   85:     */   @NotNull
/*   86:     */   @Size(max=20)
/*   87:     */   private String numero;
/*   88:     */   @Temporal(TemporalType.DATE)
/*   89:     */   @Column(name="fecha", nullable=false)
/*   90:     */   @NotNull
/*   91:     */   private Date fecha;
/*   92:     */   @Temporal(TemporalType.DATE)
/*   93:     */   @Column(name="fecha_vencimiento", nullable=true)
/*   94:     */   private Date fechaVencimiento;
/*   95:     */   @Temporal(TemporalType.DATE)
/*   96:     */   @Column(name="fecha_contabilizacion", nullable=true)
/*   97:     */   private Date fechaContabilizacion;
/*   98:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*   99:     */   @JoinColumn(name="id_pedido_cliente", nullable=true)
/*  100:     */   private PedidoCliente pedidoCliente;
/*  101:     */   @Column(name="total", nullable=false, precision=12, scale=2)
/*  102:     */   @NotNull
/*  103:     */   @Digits(integer=12, fraction=2)
/*  104:     */   @Min(0L)
/*  105:     */   private BigDecimal total;
/*  106:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  107:     */   @JoinColumn(name="id_autorizacion_documento_punto_de_venta_autoimpresor_SRI", nullable=true)
/*  108:     */   private AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI autorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  109:     */   @Transient
/*  110:     */   @Min(0L)
/*  111:     */   private BigDecimal totalFactura;
/*  112:     */   @Transient
/*  113:     */   @Min(0L)
/*  114: 149 */   private BigDecimal baseImponible = BigDecimal.ZERO;
/*  115:     */   @Column(name="descuento", nullable=true, precision=12, scale=2)
/*  116:     */   @Digits(integer=12, fraction=2)
/*  117:     */   @Min(0L)
/*  118:     */   private BigDecimal descuento;
/*  119:     */   @Column(name="impuesto", nullable=false, precision=12, scale=2)
/*  120:     */   @NotNull
/*  121:     */   @Digits(integer=12, fraction=2)
/*  122:     */   @Min(0L)
/*  123:     */   private BigDecimal impuesto;
/*  124:     */   @Column(name="valor_otros", nullable=false, precision=12, scale=2)
/*  125:     */   @NotNull
/*  126:     */   @Digits(integer=12, fraction=2)
/*  127:     */   @Min(0L)
/*  128: 164 */   private BigDecimal valorOtros = BigDecimal.ZERO;
/*  129:     */   @Column(name="valor_devuelto", nullable=true, precision=12, scale=2)
/*  130:     */   @Digits(integer=12, fraction=2)
/*  131:     */   @Min(0L)
/*  132: 170 */   private BigDecimal valorDevuelto = BigDecimal.ZERO;
/*  133:     */   @Column(name="numero_cuotas", nullable=false, precision=12, scale=2)
/*  134:     */   @NotNull
/*  135:     */   @Digits(integer=12, fraction=2)
/*  136:     */   @Min(1L)
/*  137:     */   private int numeroCuotas;
/*  138:     */   @Column(name="descripcion", nullable=true, length=500)
/*  139:     */   @Size(max=500)
/*  140:     */   private String descripcion;
/*  141:     */   @Column(name="descripcion2", nullable=true, length=500)
/*  142:     */   @Size(max=500)
/*  143:     */   private String descripcion2;
/*  144:     */   @Column(name="pdf", nullable=true)
/*  145:     */   @Size(max=50)
/*  146:     */   private String pdf;
/*  147:     */   @Column(name="referencia1", nullable=false, length=500)
/*  148:     */   @Size(max=500)
/*  149: 193 */   private String referencia1 = "";
/*  150:     */   @Column(name="estado", nullable=false)
/*  151:     */   @Enumerated(EnumType.ORDINAL)
/*  152:     */   private Estado estado;
/*  153:     */   @Column(name="direccion_factura", nullable=true)
/*  154:     */   private String direccionFactura;
/*  155:     */   @Column(name="indicador_saldo_inicial", nullable=false)
/*  156:     */   private boolean indicadorSaldoInicial;
/*  157:     */   @Column(name="id_dispositivo_sincronizacion", nullable=true)
/*  158:     */   private Integer idDispositivoSincronizacion;
/*  159:     */   @Column(name="referencia2", nullable=true, length=200)
/*  160:     */   @Size(max=200)
/*  161: 213 */   private String referencia2 = "";
/*  162:     */   @Column(name="referencia3", nullable=true, length=200)
/*  163:     */   @Size(max=200)
/*  164: 220 */   private String referencia3 = "";
/*  165:     */   @Column(name="valor_referencia1", nullable=true, length=200)
/*  166:     */   @Digits(integer=12, fraction=4)
/*  167:     */   @Min(0L)
/*  168:     */   private BigDecimal valorReferencia1;
/*  169:     */   @Column(name="valor_referencia2", nullable=true, length=200)
/*  170:     */   @Digits(integer=12, fraction=2)
/*  171:     */   @Min(0L)
/*  172:     */   private BigDecimal valorReferencia2;
/*  173:     */   @Column(name="valor_referencia3", nullable=true, length=200)
/*  174:     */   @Digits(integer=12, fraction=2)
/*  175:     */   @Min(0L)
/*  176:     */   private BigDecimal valorReferencia3;
/*  177:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  178:     */   @JoinColumn(name="id_despacho_cliente", nullable=true)
/*  179:     */   private DespachoCliente despachoCliente;
/*  180:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  181:     */   @JoinColumn(name="id_forma_pago", nullable=true)
/*  182:     */   private FormaPago formaPago;
/*  183:     */   @OneToMany(cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.LAZY, mappedBy="facturaCliente")
/*  184: 256 */   private List<DetalleFacturaCliente> listaDetalleFacturaCliente = new ArrayList();
/*  185:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="facturaCliente")
/*  186: 259 */   private List<DetalleFacturaClienteComercializadora> listaDetalleFacturaClienteComercializadora = new ArrayList();
/*  187:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="facturaCliente")
/*  188: 262 */   private List<CuentaPorCobrar> listaCuentaPorCobrar = new ArrayList();
/*  189:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  190:     */   @JoinColumn(name="id_interfaz_contable_proceso", nullable=true)
/*  191:     */   private InterfazContableProceso interfazContableProceso;
/*  192:     */   @OneToOne(cascade={javax.persistence.CascadeType.DETACH}, mappedBy="facturaCliente", fetch=FetchType.LAZY)
/*  193:     */   private FacturaClienteSRI facturaClienteSRI;
/*  194:     */   @OneToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  195:     */   @JoinColumn(name="id_agente_comercial", nullable=true)
/*  196:     */   private EntidadUsuario agenteComercial;
/*  197:     */   @ManyToOne(cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.LAZY)
/*  198:     */   @JoinColumn(name="id_factura_cliente_padre", nullable=true)
/*  199:     */   private FacturaCliente facturaClientePadre;
/*  200:     */   @OneToOne(mappedBy="notaCreditoCliente", fetch=FetchType.LAZY)
/*  201:     */   private AnticipoCliente anticipoCliente;
/*  202:     */   @Column(name="usuario_autoriza_ventas", nullable=true, length=50)
/*  203:     */   private String usuarioAutorizaVentas;
/*  204:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  205:     */   @JoinColumn(name="id_subempresa", nullable=true)
/*  206:     */   private Subempresa subempresa;
/*  207:     */   @Column(name="indicador_emision_retencion", nullable=false)
/*  208:     */   private boolean indicadorEmisionRetencion;
/*  209:     */   @Column(name="indicador_generado_prefactura")
/*  210:     */   private boolean indicadorGeneradoPrefactura;
/*  211:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  212:     */   @JoinColumn(name="id_proyecto", nullable=true)
/*  213:     */   private DimensionContable proyecto;
/*  214:     */   @Column(name="indicador_automatico", nullable=true)
/*  215:     */   private boolean indicadorAutomatico;
/*  216:     */   @Column(name="indicador_genero_transformacion", nullable=false)
/*  217:     */   @NotNull
/*  218:     */   @ColumnDefault("'0'")
/*  219: 304 */   private boolean indicadorGeneroTransformacion = false;
/*  220:     */   @Transient
/*  221:     */   private Secuencia secuencia;
/*  222:     */   @Transient
/*  223:     */   private boolean indicadorAutorizaVenta;
/*  224:     */   @Transient
/*  225:     */   private Cobro cobro;
/*  226:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  227:     */   @JoinColumn(name="id_motivo_anulacion", nullable=true)
/*  228:     */   private MotivoAnulacion motivoAnulacion;
/*  229:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="facturaCliente")
/*  230: 322 */   private List<PrefacturaCliente> listaPrefacturaCliente = new ArrayList();
/*  231:     */   @Transient
/*  232:     */   private DocumentoElectronico documentoElectronico;
/*  233:     */   @Emails
/*  234:     */   @Size(max=500)
/*  235:     */   @Column(name="email", nullable=true, length=500)
/*  236:     */   private String email;
/*  237:     */   @Column(name="referencia4", nullable=true, length=200)
/*  238:     */   @Size(max=200)
/*  239: 335 */   private String referencia4 = "";
/*  240:     */   @Column(name="referencia5", nullable=true, length=200)
/*  241:     */   @Size(max=200)
/*  242: 339 */   private String referencia5 = "";
/*  243:     */   @Column(name="referencia6", nullable=true, length=200)
/*  244:     */   @Size(max=200)
/*  245: 343 */   private String referencia6 = "";
/*  246:     */   @Column(name="referencia7", nullable=true, length=200)
/*  247:     */   @Size(max=200)
/*  248: 348 */   private String referencia7 = "";
/*  249:     */   @Column(name="referencia8", nullable=true, length=200)
/*  250:     */   @Size(max=200)
/*  251: 355 */   private String referencia8 = "";
/*  252:     */   @Column(name="referencia9", nullable=true, length=200)
/*  253:     */   @Size(max=200)
/*  254: 359 */   private String referencia9 = "";
/*  255:     */   @Column(name="referencia10", nullable=true, length=200)
/*  256:     */   @Size(max=200)
/*  257: 366 */   private String referencia10 = "";
/*  258:     */   @Column(name="codigo_movil", nullable=true, length=200)
/*  259:     */   @Size(max=200)
/*  260: 370 */   private String codigoMovil = "";
/*  261:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="devolucionClienteTransformacion")
/*  262: 374 */   private List<MovimientoInventario> listaTransformacionDevolucion = new ArrayList();
/*  263:     */   @Column(name="archivo", nullable=true)
/*  264:     */   @Size(max=50)
/*  265:     */   private String archivo;
/*  266:     */   @Column(name="cantidad_garantias", nullable=false)
/*  267:     */   @NotNull
/*  268:     */   @ColumnDefault("0")
/*  269:     */   private int cantidadGarantias;
/*  270:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="facturaCliente")
/*  271: 386 */   private List<NotaFacturaCliente> listaNotaFacturaCliente = new ArrayList();
/*  272:     */   @Column(name="incoterm", nullable=true, length=10)
/*  273:     */   @Size(max=10)
/*  274:     */   private String incoterm;
/*  275:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  276:     */   @JoinColumn(name="id_ciudad_incoterm", nullable=true)
/*  277:     */   private Ciudad lugarIncoterm;
/*  278:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  279:     */   @JoinColumn(name="id_pais_origen", nullable=true)
/*  280:     */   private Pais paisOrigen;
/*  281:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  282:     */   @JoinColumn(name="id_pais_adquisicion", nullable=true)
/*  283:     */   private Pais paisAdquisicion;
/*  284:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  285:     */   @JoinColumn(name="id_puerto_embarque", nullable=true)
/*  286:     */   private Ciudad puertoEmbarque;
/*  287:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  288:     */   @JoinColumn(name="id_pais_destino", nullable=true)
/*  289:     */   private Pais paisDestino;
/*  290:     */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  291:     */   @JoinColumn(name="id_puerto_destino", nullable=true)
/*  292:     */   private Ciudad puertoDestino;
/*  293:     */   @Column(name="flete_internacional", nullable=false, precision=12, scale=2)
/*  294:     */   @Digits(integer=12, fraction=2)
/*  295:     */   @Min(0L)
/*  296:     */   @ColumnDefault("0.00")
/*  297: 418 */   private BigDecimal fleteInternacional = BigDecimal.ZERO;
/*  298:     */   @Column(name="seguro_internacional", nullable=false, precision=12, scale=2)
/*  299:     */   @Digits(integer=12, fraction=2)
/*  300:     */   @Min(0L)
/*  301:     */   @ColumnDefault("0.00")
/*  302: 424 */   private BigDecimal seguroInternacional = BigDecimal.ZERO;
/*  303:     */   @Column(name="gastos_aduaneros", nullable=false, precision=12, scale=2)
/*  304:     */   @Digits(integer=12, fraction=2)
/*  305:     */   @Min(0L)
/*  306:     */   @ColumnDefault("0.00")
/*  307: 430 */   private BigDecimal gastosAduaneros = BigDecimal.ZERO;
/*  308:     */   @Column(name="otros_gastos_transporte", nullable=false, precision=12, scale=2)
/*  309:     */   @Digits(integer=12, fraction=2)
/*  310:     */   @Min(0L)
/*  311:     */   @ColumnDefault("0.00")
/*  312: 436 */   private BigDecimal otrosGastosTransporte = BigDecimal.ZERO;
/*  313:     */   @Column(name="descuento_impuesto", nullable=false, precision=12, scale=2)
/*  314:     */   @NotNull
/*  315:     */   @Digits(integer=12, fraction=2)
/*  316:     */   @Min(0L)
/*  317:     */   @ColumnDefault("0.00")
/*  318: 444 */   private BigDecimal descuentoImpuesto = BigDecimal.ZERO;
/*  319: 451 */   public transient boolean indicadorNoAutorizadoSRI = false;
/*  320:     */   @Column(name="indicador_genera_cxc", nullable=false)
/*  321:     */   @NotNull
/*  322:     */   @ColumnDefault("'1'")
/*  323: 453 */   private boolean indicadorGeneraCxC = true;
/*  324:     */   @Transient
/*  325:     */   @Min(0L)
/*  326:     */   private BigDecimal totalFacturaTicket;
/*  327:     */   @Transient
/*  328:     */   @Min(0L)
/*  329:     */   private BigDecimal totalImpuestosNacionales;
/*  330:     */   @Transient
/*  331:     */   @Min(0L)
/*  332:     */   private BigDecimal totalImpuestosExtranjeros;
/*  333:     */   @Transient
/*  334:     */   private Empresa empresaFinal;
/*  335:     */   @Transient
/*  336:     */   private boolean facturaClienteAgil;
/*  337:     */   @Transient
/*  338: 477 */   private boolean facturaClienteFloricola = false;
/*  339:     */   @OneToMany(cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.LAZY, mappedBy="facturaCliente")
/*  340: 480 */   private List<DetalleValoresContratoVenta> listaDetalleValoresContratoVenta = new ArrayList();
/*  341:     */   @Column(name="origen", nullable=false)
/*  342:     */   @Enumerated(EnumType.ORDINAL)
/*  343:     */   @ColumnDefault("'0'")
/*  344:     */   @NotNull
/*  345: 483 */   private OrigenEnum origen = OrigenEnum.AS2;
/*  346:     */   @Transient
/*  347: 489 */   private boolean facturaClientefloricola = false;
/*  348:     */   
/*  349:     */   public FacturaCliente() {}
/*  350:     */   
/*  351:     */   public FacturaCliente(int idFacturaCliente, String numero, Date fecha)
/*  352:     */   {
/*  353: 504 */     this.idFacturaCliente = idFacturaCliente;
/*  354: 505 */     this.numero = numero;
/*  355: 506 */     this.fecha = fecha;
/*  356:     */   }
/*  357:     */   
/*  358:     */   public FacturaCliente(int idOrganizacion, Integer idSucursal, Integer idFacturaCliente, String numero, Integer idEmpresa, String nombreFiscal, Integer idPedidoCliente, String numeroPedidoCliente, Date fecha, BigDecimal total, BigDecimal descuento, BigDecimal impuesto, Integer idDespachoCliente, String numeroDespachoCliente, Estado estado, boolean indicadorGeneradoPrefactura, boolean indicadorEmisionRetencion, Integer idFacturaClienteSRI, String descripcion, Integer idDocumento, String reporteDocumento, boolean indicadorDocumentoExterior, Integer idDireccionEmpresa, Integer idAnticipoCliente, String numeroAnticipoCliente, BigDecimal saldoAnticipoCliente, Integer idAsiento, String numeroAsiento, Integer idTipoAsiento, String nombreTipoAsiento, Integer idCliente, BigDecimal creditoMaximo, BigDecimal creditoUtilizado, DocumentoBase documentoBase, Integer idFacturaClientePadre, String numeroFacturaClientePadre, Boolean indicadorDocumentoElectronico, Integer idProyecto, String codigoProyecto, String nombreProyecto, String email, String usuarioCreacion, String usuarioModificacion, Date fechaCreacion, Date fechaModificacion, String mensajeSRI, String nombreSucursal, BigDecimal valorOtros, String codigoMovil)
/*  359:     */   {
/*  360: 520 */     this(idOrganizacion, idSucursal, idFacturaCliente, numero, idEmpresa, nombreFiscal, idPedidoCliente, numeroPedidoCliente, fecha, total, descuento, impuesto, idDespachoCliente, numeroDespachoCliente, estado, indicadorGeneradoPrefactura, indicadorEmisionRetencion, idFacturaClienteSRI, descripcion, idDocumento, reporteDocumento, indicadorDocumentoExterior, idDireccionEmpresa, idAnticipoCliente, numeroAnticipoCliente, saldoAnticipoCliente, idAsiento, numeroAsiento, idTipoAsiento, nombreTipoAsiento, idCliente, creditoMaximo, creditoUtilizado, documentoBase, idFacturaClientePadre, numeroFacturaClientePadre, indicadorDocumentoElectronico, idProyecto, codigoProyecto, nombreProyecto, email, usuarioCreacion, usuarioModificacion, fechaCreacion, fechaModificacion, mensajeSRI, nombreSucursal, valorOtros);
/*  361:     */     
/*  362:     */ 
/*  363:     */ 
/*  364:     */ 
/*  365:     */ 
/*  366:     */ 
/*  367: 527 */     this.codigoMovil = codigoMovil;
/*  368:     */   }
/*  369:     */   
/*  370:     */   public FacturaCliente(int idOrganizacion, Integer idSucursal, Integer idFacturaCliente, String numero, Integer idEmpresa, String nombreFiscal, Integer idPedidoCliente, String numeroPedidoCliente, Date fecha, BigDecimal total, BigDecimal descuento, BigDecimal impuesto, Integer idDespachoCliente, String numeroDespachoCliente, Estado estado, boolean indicadorGeneradoPrefactura, boolean indicadorEmisionRetencion, Integer idFacturaClienteSRI, String descripcion, Integer idDocumento, String reporteDocumento, boolean indicadorDocumentoExterior, Integer idDireccionEmpresa, Integer idAnticipoCliente, String numeroAnticipoCliente, BigDecimal saldoAnticipoCliente, Integer idAsiento, String numeroAsiento, Integer idTipoAsiento, String nombreTipoAsiento, Integer idCliente, BigDecimal creditoMaximo, BigDecimal creditoUtilizado, DocumentoBase documentoBase, Integer idFacturaClientePadre, String numeroFacturaClientePadre, Boolean indicadorDocumentoElectronico, Integer idProyecto, String codigoProyecto, String nombreProyecto, String email, String usuarioCreacion, String usuarioModificacion, Date fechaCreacion, Date fechaModificacion, String mensajeSRI, String nombreSucursal, BigDecimal valorOtros, String codigoMovil, String archivo)
/*  371:     */   {
/*  372: 541 */     this(idOrganizacion, idSucursal, idFacturaCliente, numero, idEmpresa, nombreFiscal, idPedidoCliente, numeroPedidoCliente, fecha, total, descuento, impuesto, idDespachoCliente, numeroDespachoCliente, estado, indicadorGeneradoPrefactura, indicadorEmisionRetencion, idFacturaClienteSRI, descripcion, idDocumento, reporteDocumento, indicadorDocumentoExterior, idDireccionEmpresa, idAnticipoCliente, numeroAnticipoCliente, saldoAnticipoCliente, idAsiento, numeroAsiento, idTipoAsiento, nombreTipoAsiento, idCliente, creditoMaximo, creditoUtilizado, documentoBase, idFacturaClientePadre, numeroFacturaClientePadre, indicadorDocumentoElectronico, idProyecto, codigoProyecto, nombreProyecto, email, usuarioCreacion, usuarioModificacion, fechaCreacion, fechaModificacion, mensajeSRI, nombreSucursal, valorOtros, codigoMovil);
/*  373:     */     
/*  374:     */ 
/*  375:     */ 
/*  376:     */ 
/*  377:     */ 
/*  378:     */ 
/*  379: 548 */     this.archivo = archivo;
/*  380:     */   }
/*  381:     */   
/*  382:     */   public FacturaCliente(int idOrganizacion, Integer idSucursal, Integer idFacturaCliente, String numero, Integer idEmpresa, String nombreFiscal, Integer idPedidoCliente, String numeroPedidoCliente, Date fecha, BigDecimal total, BigDecimal descuento, BigDecimal impuesto, Integer idDespachoCliente, String numeroDespachoCliente, Estado estado, boolean indicadorGeneradoPrefactura, boolean indicadorEmisionRetencion, Integer idFacturaClienteSRI, String descripcion, Integer idDocumento, String reporteDocumento, boolean indicadorDocumentoExterior, Integer idDireccionEmpresa, Integer idAnticipoCliente, String numeroAnticipoCliente, BigDecimal saldoAnticipoCliente, Integer idAsiento, String numeroAsiento, Integer idTipoAsiento, String nombreTipoAsiento, Integer idCliente, BigDecimal creditoMaximo, BigDecimal creditoUtilizado, DocumentoBase documentoBase, Integer idFacturaClientePadre, String numeroFacturaClientePadre, Boolean indicadorDocumentoElectronico, Integer idProyecto, String codigoProyecto, String nombreProyecto, String email, String usuarioCreacion, String usuarioModificacion, Date fechaCreacion, Date fechaModificacion, String mensajeSRI, String nombreSucursal, BigDecimal valorOtros, String codigoMovil, String archivo, String claveAcceso, BigDecimal montoIce, boolean indicadorGeneroTransformacion, BigDecimal descuentoImpuesto)
/*  383:     */   {
/*  384: 562 */     this(idOrganizacion, idSucursal, idFacturaCliente, numero, idEmpresa, nombreFiscal, idPedidoCliente, numeroPedidoCliente, fecha, total, descuento, impuesto, idDespachoCliente, numeroDespachoCliente, estado, indicadorGeneradoPrefactura, indicadorEmisionRetencion, idFacturaClienteSRI, descripcion, idDocumento, reporteDocumento, indicadorDocumentoExterior, idDireccionEmpresa, idAnticipoCliente, numeroAnticipoCliente, saldoAnticipoCliente, idAsiento, numeroAsiento, idTipoAsiento, nombreTipoAsiento, idCliente, creditoMaximo, creditoUtilizado, documentoBase, idFacturaClientePadre, numeroFacturaClientePadre, indicadorDocumentoElectronico, idProyecto, codigoProyecto, nombreProyecto, email, usuarioCreacion, usuarioModificacion, fechaCreacion, fechaModificacion, mensajeSRI, nombreSucursal, valorOtros, codigoMovil, archivo, claveAcceso, montoIce);
/*  385:     */     
/*  386:     */ 
/*  387:     */ 
/*  388:     */ 
/*  389:     */ 
/*  390:     */ 
/*  391: 569 */     this.indicadorGeneroTransformacion = indicadorGeneroTransformacion;
/*  392: 570 */     this.descuentoImpuesto = descuentoImpuesto;
/*  393:     */   }
/*  394:     */   
/*  395:     */   public FacturaCliente(int idOrganizacion, Integer idSucursal, Integer idFacturaCliente, String numero, Integer idEmpresa, String nombreFiscal, Integer idPedidoCliente, String numeroPedidoCliente, Date fecha, BigDecimal total, BigDecimal descuento, BigDecimal impuesto, Integer idDespachoCliente, String numeroDespachoCliente, Estado estado, boolean indicadorGeneradoPrefactura, boolean indicadorEmisionRetencion, Integer idFacturaClienteSRI, String descripcion, Integer idDocumento, String reporteDocumento, boolean indicadorDocumentoExterior, Integer idDireccionEmpresa, Integer idAnticipoCliente, String numeroAnticipoCliente, BigDecimal saldoAnticipoCliente, Integer idAsiento, String numeroAsiento, Integer idTipoAsiento, String nombreTipoAsiento, Integer idCliente, BigDecimal creditoMaximo, BigDecimal creditoUtilizado, DocumentoBase documentoBase, Integer idFacturaClientePadre, String numeroFacturaClientePadre, Boolean indicadorDocumentoElectronico, Integer idProyecto, String codigoProyecto, String nombreProyecto, String email, String usuarioCreacion, String usuarioModificacion, Date fechaCreacion, Date fechaModificacion, String mensajeSRI, String nombreSucursal, BigDecimal valorOtros, String codigoMovil, String archivo, String claveAcceso, BigDecimal montoIce)
/*  396:     */   {
/*  397: 584 */     this(idOrganizacion, idSucursal, idFacturaCliente, numero, idEmpresa, nombreFiscal, idPedidoCliente, numeroPedidoCliente, fecha, total, descuento, impuesto, idDespachoCliente, numeroDespachoCliente, estado, indicadorGeneradoPrefactura, indicadorEmisionRetencion, idFacturaClienteSRI, descripcion, idDocumento, reporteDocumento, indicadorDocumentoExterior, idDireccionEmpresa, idAnticipoCliente, numeroAnticipoCliente, saldoAnticipoCliente, idAsiento, numeroAsiento, idTipoAsiento, nombreTipoAsiento, idCliente, creditoMaximo, creditoUtilizado, documentoBase, idFacturaClientePadre, numeroFacturaClientePadre, indicadorDocumentoElectronico, idProyecto, codigoProyecto, nombreProyecto, email, usuarioCreacion, usuarioModificacion, fechaCreacion, fechaModificacion, mensajeSRI, nombreSucursal, valorOtros, codigoMovil);
/*  398:     */     
/*  399:     */ 
/*  400:     */ 
/*  401:     */ 
/*  402:     */ 
/*  403:     */ 
/*  404: 591 */     this.archivo = archivo;
/*  405: 592 */     if (this.facturaClienteSRI != null)
/*  406:     */     {
/*  407: 593 */       this.facturaClienteSRI.setClaveAcceso(claveAcceso);
/*  408: 594 */       this.facturaClienteSRI.setMontoIce(montoIce);
/*  409:     */     }
/*  410:     */   }
/*  411:     */   
/*  412:     */   public FacturaCliente(int idOrganizacion, Integer idSucursal, Integer idFacturaCliente, String numero, Integer idEmpresa, String nombreFiscal, Integer idPedidoCliente, String numeroPedidoCliente, Date fecha, BigDecimal total, BigDecimal descuento, BigDecimal impuesto, Integer idDespachoCliente, String numeroDespachoCliente, Estado estado, boolean indicadorGeneradoPrefactura, boolean indicadorEmisionRetencion, Integer idFacturaClienteSRI, String descripcion, Integer idDocumento, String reporteDocumento, boolean indicadorDocumentoExterior, Integer idDireccionEmpresa, Integer idAnticipoCliente, String numeroAnticipoCliente, BigDecimal saldoAnticipoCliente, Integer idAsiento, String numeroAsiento, Integer idTipoAsiento, String nombreTipoAsiento, Integer idCliente, BigDecimal creditoMaximo, BigDecimal creditoUtilizado, DocumentoBase documentoBase, Integer idFacturaClientePadre, String numeroFacturaClientePadre, Boolean indicadorDocumentoElectronico, Integer idProyecto, String codigoProyecto, String nombreProyecto, String email, String usuarioCreacion, String usuarioModificacion, Date fechaCreacion, Date fechaModificacion, String mensajeSRI, String nombreSucursal, BigDecimal valorOtros)
/*  413:     */   {
/*  414: 609 */     this.valorOtros = valorOtros;
/*  415:     */     
/*  416: 611 */     this.idOrganizacion = idOrganizacion;
/*  417:     */     
/*  418: 613 */     this.email = email;
/*  419: 614 */     this.usuarioCreacion = usuarioCreacion;
/*  420: 615 */     this.usuarioModificacion = usuarioModificacion;
/*  421:     */     
/*  422: 617 */     this.fechaCreacion = fechaCreacion;
/*  423: 618 */     this.fechaModificacion = fechaModificacion;
/*  424: 620 */     if (idSucursal != null)
/*  425:     */     {
/*  426: 621 */       Sucursal sucursal = new Sucursal();
/*  427: 622 */       sucursal.setIdSucursal(idSucursal.intValue());
/*  428: 623 */       sucursal.setIdOrganizacion(idOrganizacion);
/*  429: 624 */       sucursal.setNombre(nombreSucursal);
/*  430: 625 */       this.sucursal = sucursal;
/*  431:     */     }
/*  432: 628 */     this.idFacturaCliente = idFacturaCliente.intValue();
/*  433: 629 */     this.numero = numero;
/*  434: 630 */     if (nombreFiscal != null)
/*  435:     */     {
/*  436: 631 */       Empresa emp = new Empresa();
/*  437: 632 */       emp.setIdEmpresa(idEmpresa.intValue());
/*  438: 633 */       emp.setNombreFiscal(nombreFiscal);
/*  439: 634 */       this.empresa = emp;
/*  440:     */     }
/*  441: 636 */     if (idPedidoCliente != null)
/*  442:     */     {
/*  443: 637 */       PedidoCliente pedido = new PedidoCliente();
/*  444: 638 */       pedido.setIdPedidoCliente(idPedidoCliente.intValue());
/*  445: 639 */       pedido.setNumero(numeroPedidoCliente);
/*  446: 640 */       this.pedidoCliente = pedido;
/*  447:     */     }
/*  448: 642 */     this.fecha = fecha;
/*  449: 643 */     this.total = total;
/*  450: 644 */     this.descuento = descuento;
/*  451: 645 */     this.impuesto = impuesto;
/*  452: 646 */     if (idDespachoCliente != null)
/*  453:     */     {
/*  454: 647 */       DespachoCliente despachoCli = new DespachoCliente();
/*  455: 648 */       despachoCli.setIdDespachoCliente(idDespachoCliente.intValue());
/*  456: 649 */       despachoCli.setNumero(numeroDespachoCliente);
/*  457: 650 */       this.despachoCliente = despachoCli;
/*  458:     */     }
/*  459: 652 */     this.estado = estado;
/*  460: 653 */     this.indicadorGeneradoPrefactura = indicadorGeneradoPrefactura;
/*  461: 654 */     this.indicadorEmisionRetencion = indicadorEmisionRetencion;
/*  462: 655 */     if (idFacturaClienteSRI != null)
/*  463:     */     {
/*  464: 656 */       FacturaClienteSRI facturaCLienteSRI = new FacturaClienteSRI();
/*  465: 657 */       facturaCLienteSRI.setIdFacturaclienteSRI(idFacturaClienteSRI.intValue());
/*  466: 658 */       facturaCLienteSRI.setMensajeSRI(mensajeSRI);
/*  467: 659 */       this.facturaClienteSRI = facturaCLienteSRI;
/*  468:     */     }
/*  469: 661 */     this.descripcion = descripcion;
/*  470: 662 */     Documento docu = new Documento();
/*  471: 663 */     docu.setIdDocumento(idDocumento.intValue());
/*  472: 664 */     docu.setDocumentoBase(documentoBase);
/*  473: 665 */     docu.setIndicadorDocumentoExterior(indicadorDocumentoExterior);
/*  474: 666 */     docu.setReporte(reporteDocumento);
/*  475: 667 */     docu.setIndicadorDocumentoElectronico(indicadorDocumentoElectronico.booleanValue());
/*  476: 668 */     this.documento = docu;
/*  477: 669 */     if (idDireccionEmpresa != null)
/*  478:     */     {
/*  479: 670 */       DireccionEmpresa direccion = new DireccionEmpresa();
/*  480: 671 */       direccion.setIdDireccionEmpresa(idDireccionEmpresa.intValue());
/*  481: 672 */       this.direccionEmpresa = direccion;
/*  482:     */     }
/*  483: 675 */     if (idAnticipoCliente != null)
/*  484:     */     {
/*  485: 676 */       AnticipoCliente anticipoCliente = new AnticipoCliente();
/*  486: 677 */       anticipoCliente.setIdAnticipoCliente(idAnticipoCliente.intValue());
/*  487: 678 */       anticipoCliente.setNumero(numeroAnticipoCliente);
/*  488: 679 */       anticipoCliente.setSaldo(saldoAnticipoCliente);
/*  489: 680 */       this.anticipoCliente = anticipoCliente;
/*  490:     */     }
/*  491: 683 */     if (idAsiento != null)
/*  492:     */     {
/*  493: 684 */       Asiento asiento = new Asiento();
/*  494: 685 */       asiento.setIdAsiento(idAsiento.intValue());
/*  495: 686 */       asiento.setNumero(numeroAsiento);
/*  496: 687 */       this.asiento = asiento;
/*  497:     */     }
/*  498: 689 */     if (idTipoAsiento != null)
/*  499:     */     {
/*  500: 690 */       TipoAsiento tipoAsiento = new TipoAsiento();
/*  501: 691 */       tipoAsiento.setIdTipoAsiento(idTipoAsiento.intValue());
/*  502: 692 */       tipoAsiento.setNombre(nombreTipoAsiento);
/*  503: 693 */       this.asiento.setTipoAsiento(tipoAsiento);
/*  504:     */     }
/*  505: 696 */     if (idCliente != null)
/*  506:     */     {
/*  507: 697 */       Cliente cliente = new Cliente();
/*  508: 698 */       cliente.setIdCliente(idCliente.intValue());
/*  509: 699 */       cliente.setCreditoMaximo(creditoMaximo);
/*  510: 700 */       cliente.setCreditoUtilizado(creditoUtilizado);
/*  511: 701 */       this.empresa.setCliente(cliente);
/*  512:     */     }
/*  513: 703 */     if (idFacturaClientePadre != null)
/*  514:     */     {
/*  515: 704 */       FacturaCliente facturaClientePadre = new FacturaCliente();
/*  516: 705 */       facturaClientePadre.setIdFacturaCliente(idFacturaClientePadre.intValue());
/*  517: 706 */       facturaClientePadre.setNumero(numeroFacturaClientePadre);
/*  518: 707 */       this.facturaClientePadre = facturaClientePadre;
/*  519:     */     }
/*  520: 710 */     if (idProyecto != null)
/*  521:     */     {
/*  522: 711 */       DimensionContable proyecto = new DimensionContable();
/*  523: 712 */       proyecto.setIdDimensionContable(idProyecto.intValue());
/*  524: 713 */       proyecto.setCodigo(codigoProyecto);
/*  525: 714 */       proyecto.setNombre(nombreProyecto);
/*  526: 715 */       setProyecto(proyecto);
/*  527:     */     }
/*  528:     */   }
/*  529:     */   
/*  530:     */   public BigDecimal getMontoIce()
/*  531:     */   {
/*  532: 721 */     return this.facturaClienteSRI != null ? this.facturaClienteSRI.getMontoIce() : BigDecimal.ZERO;
/*  533:     */   }
/*  534:     */   
/*  535:     */   public BigDecimal getTotalCuentaPorCobrar()
/*  536:     */   {
/*  537: 732 */     BigDecimal valorCuentaPorCobrar = BigDecimal.ZERO;
/*  538: 734 */     for (CuentaPorCobrar cuentaPorCobrar : this.listaCuentaPorCobrar) {
/*  539: 735 */       if (!cuentaPorCobrar.isEliminado()) {
/*  540: 736 */         valorCuentaPorCobrar = valorCuentaPorCobrar.add(cuentaPorCobrar.getValor());
/*  541:     */       }
/*  542:     */     }
/*  543: 740 */     return valorCuentaPorCobrar;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public int getId()
/*  547:     */   {
/*  548: 750 */     return this.idFacturaCliente;
/*  549:     */   }
/*  550:     */   
/*  551:     */   public int getIdFacturaCliente()
/*  552:     */   {
/*  553: 756 */     return this.idFacturaCliente;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public void setIdFacturaCliente(int idFacturaCliente)
/*  557:     */   {
/*  558: 760 */     this.idFacturaCliente = idFacturaCliente;
/*  559:     */   }
/*  560:     */   
/*  561:     */   public Sucursal getSucursal()
/*  562:     */   {
/*  563: 764 */     return this.sucursal;
/*  564:     */   }
/*  565:     */   
/*  566:     */   public void setSucursal(Sucursal sucursal)
/*  567:     */   {
/*  568: 768 */     this.sucursal = sucursal;
/*  569:     */   }
/*  570:     */   
/*  571:     */   public int getIdOrganizacion()
/*  572:     */   {
/*  573: 772 */     return this.idOrganizacion;
/*  574:     */   }
/*  575:     */   
/*  576:     */   public void setIdOrganizacion(int idOrganizacion)
/*  577:     */   {
/*  578: 776 */     this.idOrganizacion = idOrganizacion;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public Documento getDocumento()
/*  582:     */   {
/*  583: 780 */     return this.documento;
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void setDocumento(Documento documento)
/*  587:     */   {
/*  588: 784 */     this.documento = documento;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public Empresa getEmpresa()
/*  592:     */   {
/*  593: 788 */     return this.empresa;
/*  594:     */   }
/*  595:     */   
/*  596:     */   public void setEmpresa(Empresa empresa)
/*  597:     */   {
/*  598: 792 */     this.empresa = empresa;
/*  599:     */   }
/*  600:     */   
/*  601:     */   public Asiento getAsiento()
/*  602:     */   {
/*  603: 796 */     return this.asiento;
/*  604:     */   }
/*  605:     */   
/*  606:     */   public void setAsiento(Asiento asiento)
/*  607:     */   {
/*  608: 800 */     this.asiento = asiento;
/*  609:     */   }
/*  610:     */   
/*  611:     */   public String getNumero()
/*  612:     */   {
/*  613: 804 */     return this.numero;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public void setNumero(String numero)
/*  617:     */   {
/*  618: 808 */     this.numero = numero;
/*  619:     */   }
/*  620:     */   
/*  621:     */   public Date getFecha()
/*  622:     */   {
/*  623: 812 */     return this.fecha;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public void setFecha(Date fecha)
/*  627:     */   {
/*  628: 816 */     this.fecha = fecha;
/*  629:     */   }
/*  630:     */   
/*  631:     */   public Date getFechaContabilizacion()
/*  632:     */   {
/*  633: 820 */     return this.fechaContabilizacion;
/*  634:     */   }
/*  635:     */   
/*  636:     */   public void setFechaContabilizacion(Date fechaContabilizacion)
/*  637:     */   {
/*  638: 824 */     this.fechaContabilizacion = fechaContabilizacion;
/*  639:     */   }
/*  640:     */   
/*  641:     */   public BigDecimal getTotal()
/*  642:     */   {
/*  643: 828 */     if (this.total == null) {
/*  644: 829 */       this.total = BigDecimal.ZERO;
/*  645:     */     }
/*  646: 831 */     return this.total;
/*  647:     */   }
/*  648:     */   
/*  649:     */   public void setTotal(BigDecimal total)
/*  650:     */   {
/*  651: 835 */     this.total = total;
/*  652:     */   }
/*  653:     */   
/*  654:     */   public BigDecimal getImpuesto()
/*  655:     */   {
/*  656: 839 */     if (this.impuesto == null) {
/*  657: 840 */       this.impuesto = BigDecimal.ZERO;
/*  658:     */     }
/*  659: 842 */     return this.impuesto;
/*  660:     */   }
/*  661:     */   
/*  662:     */   public void setImpuesto(BigDecimal impuesto)
/*  663:     */   {
/*  664: 846 */     this.impuesto = impuesto;
/*  665:     */   }
/*  666:     */   
/*  667:     */   public BigDecimal getDescuento()
/*  668:     */   {
/*  669: 850 */     if (this.descuento == null) {
/*  670: 851 */       this.descuento = BigDecimal.ZERO;
/*  671:     */     }
/*  672: 853 */     return this.descuento;
/*  673:     */   }
/*  674:     */   
/*  675:     */   public void setDescuento(BigDecimal descuento)
/*  676:     */   {
/*  677: 857 */     this.descuento = descuento;
/*  678:     */   }
/*  679:     */   
/*  680:     */   public int getNumeroCuotas()
/*  681:     */   {
/*  682: 861 */     return this.numeroCuotas;
/*  683:     */   }
/*  684:     */   
/*  685:     */   public void setNumeroCuotas(int numeroCuotas)
/*  686:     */   {
/*  687: 865 */     this.numeroCuotas = numeroCuotas;
/*  688:     */   }
/*  689:     */   
/*  690:     */   public String getDescripcion()
/*  691:     */   {
/*  692: 869 */     return this.descripcion;
/*  693:     */   }
/*  694:     */   
/*  695:     */   public void setDescripcion(String descripcion)
/*  696:     */   {
/*  697: 873 */     this.descripcion = descripcion;
/*  698:     */   }
/*  699:     */   
/*  700:     */   public Estado getEstado()
/*  701:     */   {
/*  702: 877 */     return this.estado;
/*  703:     */   }
/*  704:     */   
/*  705:     */   public void setEstado(Estado estado)
/*  706:     */   {
/*  707: 881 */     this.estado = estado;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public List<DetalleFacturaCliente> getListaDetalleFacturaCliente()
/*  711:     */   {
/*  712: 885 */     return this.listaDetalleFacturaCliente;
/*  713:     */   }
/*  714:     */   
/*  715:     */   public void setListaDetalleFacturaCliente(List<DetalleFacturaCliente> listaDetalleFacturaCliente)
/*  716:     */   {
/*  717: 889 */     this.listaDetalleFacturaCliente = listaDetalleFacturaCliente;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public List<CuentaPorCobrar> getListaCuentaPorCobrar()
/*  721:     */   {
/*  722: 893 */     if (this.listaCuentaPorCobrar == null) {
/*  723: 894 */       this.listaCuentaPorCobrar = new ArrayList();
/*  724:     */     }
/*  725: 896 */     return this.listaCuentaPorCobrar;
/*  726:     */   }
/*  727:     */   
/*  728:     */   public void setListaCuentaPorCobrar(List<CuentaPorCobrar> listaCuentaPorCobrar)
/*  729:     */   {
/*  730: 900 */     this.listaCuentaPorCobrar = listaCuentaPorCobrar;
/*  731:     */   }
/*  732:     */   
/*  733:     */   public CondicionPago getCondicionPago()
/*  734:     */   {
/*  735: 909 */     return this.condicionPago;
/*  736:     */   }
/*  737:     */   
/*  738:     */   public void setCondicionPago(CondicionPago condicionPago)
/*  739:     */   {
/*  740: 919 */     this.condicionPago = condicionPago;
/*  741:     */   }
/*  742:     */   
/*  743:     */   public DireccionEmpresa getDireccionEmpresa()
/*  744:     */   {
/*  745: 928 */     return this.direccionEmpresa;
/*  746:     */   }
/*  747:     */   
/*  748:     */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/*  749:     */   {
/*  750: 938 */     this.direccionEmpresa = direccionEmpresa;
/*  751:     */   }
/*  752:     */   
/*  753:     */   public String getDireccionFactura()
/*  754:     */   {
/*  755: 947 */     return this.direccionFactura;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public void setDireccionFactura(String direccionFactura)
/*  759:     */   {
/*  760: 957 */     this.direccionFactura = direccionFactura;
/*  761:     */   }
/*  762:     */   
/*  763:     */   public String toString()
/*  764:     */   {
/*  765: 967 */     return this.numero;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public boolean isIndicadorSaldoInicial()
/*  769:     */   {
/*  770: 976 */     return this.indicadorSaldoInicial;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void setIndicadorSaldoInicial(boolean indicadorSaldoInicial)
/*  774:     */   {
/*  775: 986 */     this.indicadorSaldoInicial = indicadorSaldoInicial;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public BigDecimal getTotalFactura()
/*  779:     */   {
/*  780: 990 */     this.totalFactura = getTotal().subtract(getDescuento()).add(getImpuesto()).add(getMontoIce()).subtract(getDescuentoImpuesto());
/*  781:     */     
/*  782: 992 */     return this.totalFactura;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public void setTotalFactura(BigDecimal totalFactura)
/*  786:     */   {
/*  787: 996 */     this.totalFactura = totalFactura;
/*  788:     */   }
/*  789:     */   
/*  790:     */   public BigDecimal getBaseImponible()
/*  791:     */   {
/*  792:1000 */     return this.baseImponible;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public void setBaseImponible(BigDecimal baseImponible)
/*  796:     */   {
/*  797:1004 */     this.baseImponible = baseImponible;
/*  798:     */   }
/*  799:     */   
/*  800:     */   public List<String> getCamposAuditables()
/*  801:     */   {
/*  802:1013 */     List<String> lista = new ArrayList();
/*  803:1014 */     lista.add("idOrganizacion");
/*  804:1015 */     lista.add("numero");
/*  805:1016 */     lista.add("fecha");
/*  806:1017 */     lista.add("fechaContabilizacion");
/*  807:1018 */     lista.add("total");
/*  808:1019 */     lista.add("descuento");
/*  809:1020 */     lista.add("impuesto");
/*  810:1021 */     lista.add("numeroCuotas");
/*  811:1022 */     lista.add("descripcion");
/*  812:1023 */     lista.add("estado");
/*  813:1024 */     lista.add("direccionFactura");
/*  814:1025 */     lista.add("indicadorSaldoInicial");
/*  815:     */     
/*  816:1027 */     return lista;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public EntidadUsuario getAgenteComercial()
/*  820:     */   {
/*  821:1037 */     return this.agenteComercial;
/*  822:     */   }
/*  823:     */   
/*  824:     */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/*  825:     */   {
/*  826:1047 */     this.agenteComercial = agenteComercial;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public InterfazContableProceso getInterfazContableProceso()
/*  830:     */   {
/*  831:1051 */     return this.interfazContableProceso;
/*  832:     */   }
/*  833:     */   
/*  834:     */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/*  835:     */   {
/*  836:1055 */     this.interfazContableProceso = interfazContableProceso;
/*  837:     */   }
/*  838:     */   
/*  839:     */   public Canal getCanal()
/*  840:     */   {
/*  841:1064 */     return this.canal;
/*  842:     */   }
/*  843:     */   
/*  844:     */   public void setCanal(Canal canal)
/*  845:     */   {
/*  846:1074 */     this.canal = canal;
/*  847:     */   }
/*  848:     */   
/*  849:     */   public Secuencia getSecuencia()
/*  850:     */   {
/*  851:1083 */     return this.secuencia;
/*  852:     */   }
/*  853:     */   
/*  854:     */   public void setSecuencia(Secuencia secuencia)
/*  855:     */   {
/*  856:1093 */     this.secuencia = secuencia;
/*  857:     */   }
/*  858:     */   
/*  859:     */   public FacturaClienteSRI getFacturaClienteSRI()
/*  860:     */   {
/*  861:1102 */     return this.facturaClienteSRI;
/*  862:     */   }
/*  863:     */   
/*  864:     */   public void setFacturaClienteSRI(FacturaClienteSRI facturaClienteSRI)
/*  865:     */   {
/*  866:1112 */     this.facturaClienteSRI = facturaClienteSRI;
/*  867:     */   }
/*  868:     */   
/*  869:     */   public FacturaCliente getFacturaClientePadre()
/*  870:     */   {
/*  871:1121 */     return this.facturaClientePadre;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void setFacturaClientePadre(FacturaCliente facturaClientePadre)
/*  875:     */   {
/*  876:1131 */     this.facturaClientePadre = facturaClientePadre;
/*  877:     */   }
/*  878:     */   
/*  879:     */   public AnticipoCliente getAnticipoCliente()
/*  880:     */   {
/*  881:1140 */     return this.anticipoCliente;
/*  882:     */   }
/*  883:     */   
/*  884:     */   public void setAnticipoCliente(AnticipoCliente anticipoCliente)
/*  885:     */   {
/*  886:1150 */     this.anticipoCliente = anticipoCliente;
/*  887:     */   }
/*  888:     */   
/*  889:     */   public Zona getZona()
/*  890:     */   {
/*  891:1154 */     return this.zona;
/*  892:     */   }
/*  893:     */   
/*  894:     */   public void setZona(Zona zona)
/*  895:     */   {
/*  896:1158 */     this.zona = zona;
/*  897:     */   }
/*  898:     */   
/*  899:     */   public DespachoCliente getDespachoCliente()
/*  900:     */   {
/*  901:1165 */     return this.despachoCliente;
/*  902:     */   }
/*  903:     */   
/*  904:     */   public void setDespachoCliente(DespachoCliente despachoCliente)
/*  905:     */   {
/*  906:1173 */     this.despachoCliente = despachoCliente;
/*  907:     */   }
/*  908:     */   
/*  909:     */   public FormaPago getFormaPago()
/*  910:     */   {
/*  911:1182 */     return this.formaPago;
/*  912:     */   }
/*  913:     */   
/*  914:     */   public void setFormaPago(FormaPago formaPago)
/*  915:     */   {
/*  916:1192 */     this.formaPago = formaPago;
/*  917:     */   }
/*  918:     */   
/*  919:     */   public String getReferencia1()
/*  920:     */   {
/*  921:1201 */     return this.referencia1;
/*  922:     */   }
/*  923:     */   
/*  924:     */   public void setReferencia1(String referencia1)
/*  925:     */   {
/*  926:1211 */     this.referencia1 = referencia1;
/*  927:     */   }
/*  928:     */   
/*  929:     */   public String getReferencia2()
/*  930:     */   {
/*  931:1220 */     return this.referencia2;
/*  932:     */   }
/*  933:     */   
/*  934:     */   public void setReferencia2(String referencia2)
/*  935:     */   {
/*  936:1230 */     this.referencia2 = referencia2;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public MotivoNotaCreditoCliente getMotivoNotaCreditoCliente()
/*  940:     */   {
/*  941:1239 */     return this.motivoNotaCreditoCliente;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public void setMotivoNotaCreditoCliente(MotivoNotaCreditoCliente motivoNotaCreditoCliente)
/*  945:     */   {
/*  946:1249 */     this.motivoNotaCreditoCliente = motivoNotaCreditoCliente;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public Date getFechaVencimiento()
/*  950:     */   {
/*  951:1258 */     return this.fechaVencimiento;
/*  952:     */   }
/*  953:     */   
/*  954:     */   public void setFechaVencimiento(Date fechaVencimiento)
/*  955:     */   {
/*  956:1268 */     this.fechaVencimiento = fechaVencimiento;
/*  957:     */   }
/*  958:     */   
/*  959:     */   public boolean isIndicadorAutorizaVenta()
/*  960:     */   {
/*  961:1277 */     return this.indicadorAutorizaVenta;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public void setIndicadorAutorizaVenta(boolean indicadorAutorizaVenta)
/*  965:     */   {
/*  966:1287 */     this.indicadorAutorizaVenta = indicadorAutorizaVenta;
/*  967:     */   }
/*  968:     */   
/*  969:     */   public String getUsuarioAutorizaVentas()
/*  970:     */   {
/*  971:1296 */     return this.usuarioAutorizaVentas;
/*  972:     */   }
/*  973:     */   
/*  974:     */   public void setUsuarioAutorizaVentas(String usuarioAutorizaVentas)
/*  975:     */   {
/*  976:1306 */     this.usuarioAutorizaVentas = usuarioAutorizaVentas;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public List<DetalleFacturaClienteComercializadora> getListaDetalleFacturaClienteComercializadora()
/*  980:     */   {
/*  981:1315 */     return this.listaDetalleFacturaClienteComercializadora;
/*  982:     */   }
/*  983:     */   
/*  984:     */   public void setListaDetalleFacturaClienteComercializadora(List<DetalleFacturaClienteComercializadora> listaDetalleFacturaClienteComercializadora)
/*  985:     */   {
/*  986:1326 */     this.listaDetalleFacturaClienteComercializadora = listaDetalleFacturaClienteComercializadora;
/*  987:     */   }
/*  988:     */   
/*  989:     */   public BigDecimal getValorOtros()
/*  990:     */   {
/*  991:1335 */     return this.valorOtros;
/*  992:     */   }
/*  993:     */   
/*  994:     */   public void setValorOtros(BigDecimal valorOtros)
/*  995:     */   {
/*  996:1345 */     this.valorOtros = valorOtros;
/*  997:     */   }
/*  998:     */   
/*  999:     */   public BigDecimal getValorDevuelto()
/* 1000:     */   {
/* 1001:1352 */     return this.valorDevuelto;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public void setValorDevuelto(BigDecimal valorDevuelto)
/* 1005:     */   {
/* 1006:1360 */     this.valorDevuelto = valorDevuelto;
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public Subempresa getSubempresa()
/* 1010:     */   {
/* 1011:1369 */     return this.subempresa;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public void setSubempresa(Subempresa subempresa)
/* 1015:     */   {
/* 1016:1379 */     this.subempresa = subempresa;
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public boolean isIndicadorEmisionRetencion()
/* 1020:     */   {
/* 1021:1388 */     return this.indicadorEmisionRetencion;
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public void setIndicadorEmisionRetencion(boolean indicadorEmisionRetencion)
/* 1025:     */   {
/* 1026:1398 */     this.indicadorEmisionRetencion = indicadorEmisionRetencion;
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public PedidoCliente getPedidoCliente()
/* 1030:     */   {
/* 1031:1407 */     return this.pedidoCliente;
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public void setPedidoCliente(PedidoCliente pedidoCliente)
/* 1035:     */   {
/* 1036:1417 */     this.pedidoCliente = pedidoCliente;
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public Cobro getCobro()
/* 1040:     */   {
/* 1041:1426 */     return this.cobro;
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   public void setCobro(Cobro cobro)
/* 1045:     */   {
/* 1046:1436 */     this.cobro = cobro;
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   public MotivoAnulacion getMotivoAnulacion()
/* 1050:     */   {
/* 1051:1440 */     return this.motivoAnulacion;
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public void setMotivoAnulacion(MotivoAnulacion motivoAnulacion)
/* 1055:     */   {
/* 1056:1444 */     this.motivoAnulacion = motivoAnulacion;
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public List<PrefacturaCliente> getListaPrefacturaCliente()
/* 1060:     */   {
/* 1061:1453 */     return this.listaPrefacturaCliente;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public void setListaPrefacturaCliente(List<PrefacturaCliente> listaPrefacturaCliente)
/* 1065:     */   {
/* 1066:1463 */     this.listaPrefacturaCliente = listaPrefacturaCliente;
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   public AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()
/* 1070:     */   {
/* 1071:1467 */     return this.autorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   public void setAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI autorizacionDocumentoPuntoDeVentaAutoimpresorSRI)
/* 1075:     */   {
/* 1076:1472 */     this.autorizacionDocumentoPuntoDeVentaAutoimpresorSRI = autorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   public String getDescripcion2()
/* 1080:     */   {
/* 1081:1476 */     return this.descripcion2;
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public void setDescripcion2(String descripcion2)
/* 1085:     */   {
/* 1086:1480 */     this.descripcion2 = descripcion2;
/* 1087:     */   }
/* 1088:     */   
/* 1089:     */   public DocumentoElectronico getDocumentoElectronico()
/* 1090:     */   {
/* 1091:1484 */     return this.documentoElectronico;
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   public void setDocumentoElectronico(DocumentoElectronico documentoElectronico)
/* 1095:     */   {
/* 1096:1488 */     this.documentoElectronico = documentoElectronico;
/* 1097:     */   }
/* 1098:     */   
/* 1099:     */   public String getEmail()
/* 1100:     */   {
/* 1101:1492 */     return this.email;
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   public void setEmail(String email)
/* 1105:     */   {
/* 1106:1496 */     this.email = email;
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public boolean isIndicadorGeneradoPrefactura()
/* 1110:     */   {
/* 1111:1500 */     return this.indicadorGeneradoPrefactura;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public void setIndicadorGeneradoPrefactura(boolean indicadorGeneradoPrefactura)
/* 1115:     */   {
/* 1116:1504 */     this.indicadorGeneradoPrefactura = indicadorGeneradoPrefactura;
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public String getReferencia3()
/* 1120:     */   {
/* 1121:1508 */     return this.referencia3;
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   public void setReferencia3(String referencia3)
/* 1125:     */   {
/* 1126:1512 */     this.referencia3 = referencia3;
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   public BigDecimal getValorReferencia1()
/* 1130:     */   {
/* 1131:1516 */     return this.valorReferencia1;
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public void setValorReferencia1(BigDecimal valorReferencia1)
/* 1135:     */   {
/* 1136:1520 */     this.valorReferencia1 = valorReferencia1;
/* 1137:     */   }
/* 1138:     */   
/* 1139:     */   public BigDecimal getValorReferencia2()
/* 1140:     */   {
/* 1141:1524 */     return this.valorReferencia2;
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public void setValorReferencia2(BigDecimal valorReferencia2)
/* 1145:     */   {
/* 1146:1528 */     this.valorReferencia2 = valorReferencia2;
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   public BigDecimal getValorReferencia3()
/* 1150:     */   {
/* 1151:1532 */     return this.valorReferencia3;
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   public void setValorReferencia3(BigDecimal valorReferencia3)
/* 1155:     */   {
/* 1156:1536 */     this.valorReferencia3 = valorReferencia3;
/* 1157:     */   }
/* 1158:     */   
/* 1159:     */   public DimensionContable getProyecto()
/* 1160:     */   {
/* 1161:1543 */     return this.proyecto;
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   public void setProyecto(DimensionContable proyecto)
/* 1165:     */   {
/* 1166:1551 */     this.proyecto = proyecto;
/* 1167:     */   }
/* 1168:     */   
/* 1169:     */   public String getReferencia4()
/* 1170:     */   {
/* 1171:1558 */     return this.referencia4;
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public void setReferencia4(String referencia4)
/* 1175:     */   {
/* 1176:1566 */     this.referencia4 = referencia4;
/* 1177:     */   }
/* 1178:     */   
/* 1179:     */   public String getReferencia5()
/* 1180:     */   {
/* 1181:1573 */     return this.referencia5;
/* 1182:     */   }
/* 1183:     */   
/* 1184:     */   public void setReferencia5(String referencia5)
/* 1185:     */   {
/* 1186:1581 */     this.referencia5 = referencia5;
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   public String getReferencia6()
/* 1190:     */   {
/* 1191:1588 */     return this.referencia6;
/* 1192:     */   }
/* 1193:     */   
/* 1194:     */   public void setReferencia6(String referencia6)
/* 1195:     */   {
/* 1196:1596 */     this.referencia6 = referencia6;
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public String getReferencia7()
/* 1200:     */   {
/* 1201:1600 */     return this.referencia7;
/* 1202:     */   }
/* 1203:     */   
/* 1204:     */   public void setReferencia7(String referencia7)
/* 1205:     */   {
/* 1206:1604 */     this.referencia7 = referencia7;
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   public String getReferencia8()
/* 1210:     */   {
/* 1211:1608 */     return this.referencia8;
/* 1212:     */   }
/* 1213:     */   
/* 1214:     */   public void setReferencia8(String referencia8)
/* 1215:     */   {
/* 1216:1612 */     this.referencia8 = referencia8;
/* 1217:     */   }
/* 1218:     */   
/* 1219:     */   public String getReferencia9()
/* 1220:     */   {
/* 1221:1616 */     return this.referencia9;
/* 1222:     */   }
/* 1223:     */   
/* 1224:     */   public void setReferencia9(String referencia9)
/* 1225:     */   {
/* 1226:1620 */     this.referencia9 = referencia9;
/* 1227:     */   }
/* 1228:     */   
/* 1229:     */   public String getReferencia10()
/* 1230:     */   {
/* 1231:1624 */     return this.referencia10;
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   public void setReferencia10(String referencia10)
/* 1235:     */   {
/* 1236:1628 */     this.referencia10 = referencia10;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   public boolean isIndicadorAutomatico()
/* 1240:     */   {
/* 1241:1632 */     return this.indicadorAutomatico;
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   public void setIndicadorAutomatico(boolean indicadorAutomatico)
/* 1245:     */   {
/* 1246:1636 */     this.indicadorAutomatico = indicadorAutomatico;
/* 1247:     */   }
/* 1248:     */   
/* 1249:     */   public boolean isIndicadorNoAutorizadoSRI()
/* 1250:     */   {
/* 1251:1640 */     return this.indicadorNoAutorizadoSRI;
/* 1252:     */   }
/* 1253:     */   
/* 1254:     */   public void setIndicadorNoAutorizadoSRI(boolean indicadorNoAutorizadoSRI)
/* 1255:     */   {
/* 1256:1644 */     this.indicadorNoAutorizadoSRI = indicadorNoAutorizadoSRI;
/* 1257:     */   }
/* 1258:     */   
/* 1259:     */   public BigDecimal getTotalFacturaTicket()
/* 1260:     */   {
/* 1261:1648 */     return this.totalFacturaTicket = getTotalFactura().add(getValorOtros());
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   public void setTotalFacturaTicket(BigDecimal totalFacturaTicket)
/* 1265:     */   {
/* 1266:1652 */     this.totalFacturaTicket = totalFacturaTicket;
/* 1267:     */   }
/* 1268:     */   
/* 1269:     */   public BigDecimal getTotalImpuestosNacionales()
/* 1270:     */   {
/* 1271:1656 */     return this.totalImpuestosNacionales;
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   public void setTotalImpuestosNacionales(BigDecimal totalImpuestosNacionales)
/* 1275:     */   {
/* 1276:1660 */     this.totalImpuestosNacionales = totalImpuestosNacionales;
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   public BigDecimal getTotalImpuestosExtranjeros()
/* 1280:     */   {
/* 1281:1664 */     return this.totalImpuestosExtranjeros;
/* 1282:     */   }
/* 1283:     */   
/* 1284:     */   public void setTotalImpuestosExtranjeros(BigDecimal totalImpuestosExtranjeros)
/* 1285:     */   {
/* 1286:1668 */     this.totalImpuestosExtranjeros = totalImpuestosExtranjeros;
/* 1287:     */   }
/* 1288:     */   
/* 1289:     */   public Empresa getEmpresaFinal()
/* 1290:     */   {
/* 1291:1672 */     if (this.subempresa != null) {
/* 1292:1673 */       this.empresaFinal = this.subempresa.getEmpresa();
/* 1293:     */     } else {
/* 1294:1675 */       this.empresaFinal = this.empresa;
/* 1295:     */     }
/* 1296:1677 */     return this.empresaFinal;
/* 1297:     */   }
/* 1298:     */   
/* 1299:     */   public void setEmpresaFinal(Empresa empresaFinal)
/* 1300:     */   {
/* 1301:1681 */     this.empresaFinal = empresaFinal;
/* 1302:     */   }
/* 1303:     */   
/* 1304:     */   public String getCodigoMovil()
/* 1305:     */   {
/* 1306:1685 */     return this.codigoMovil;
/* 1307:     */   }
/* 1308:     */   
/* 1309:     */   public void setCodigoMovil(String codigoMovil)
/* 1310:     */   {
/* 1311:1689 */     this.codigoMovil = codigoMovil;
/* 1312:     */   }
/* 1313:     */   
/* 1314:     */   public List<MovimientoInventario> getListaTransformacionDevolucion()
/* 1315:     */   {
/* 1316:1693 */     return this.listaTransformacionDevolucion;
/* 1317:     */   }
/* 1318:     */   
/* 1319:     */   public void setListaTransformacionDevolucion(List<MovimientoInventario> listaTransformacionDevolucion)
/* 1320:     */   {
/* 1321:1697 */     this.listaTransformacionDevolucion = listaTransformacionDevolucion;
/* 1322:     */   }
/* 1323:     */   
/* 1324:     */   public String getArchivo()
/* 1325:     */   {
/* 1326:1701 */     return this.archivo;
/* 1327:     */   }
/* 1328:     */   
/* 1329:     */   public void setArchivo(String archivo)
/* 1330:     */   {
/* 1331:1705 */     this.archivo = archivo;
/* 1332:     */   }
/* 1333:     */   
/* 1334:     */   public int getCantidadGarantias()
/* 1335:     */   {
/* 1336:1709 */     return this.cantidadGarantias;
/* 1337:     */   }
/* 1338:     */   
/* 1339:     */   public void setCantidadGarantias(int cantidadGarantias)
/* 1340:     */   {
/* 1341:1713 */     this.cantidadGarantias = cantidadGarantias;
/* 1342:     */   }
/* 1343:     */   
/* 1344:     */   public List<NotaFacturaCliente> getListaNotaFacturaCliente()
/* 1345:     */   {
/* 1346:1717 */     return this.listaNotaFacturaCliente;
/* 1347:     */   }
/* 1348:     */   
/* 1349:     */   public void setListaNotaFacturaCliente(List<NotaFacturaCliente> listaNotaFacturaCliente)
/* 1350:     */   {
/* 1351:1721 */     this.listaNotaFacturaCliente = listaNotaFacturaCliente;
/* 1352:     */   }
/* 1353:     */   
/* 1354:     */   public boolean isIndicadorGeneroTransformacion()
/* 1355:     */   {
/* 1356:1725 */     return this.indicadorGeneroTransformacion;
/* 1357:     */   }
/* 1358:     */   
/* 1359:     */   public void setIndicadorGeneroTransformacion(boolean indicadorGeneroTransformacion)
/* 1360:     */   {
/* 1361:1729 */     this.indicadorGeneroTransformacion = indicadorGeneroTransformacion;
/* 1362:     */   }
/* 1363:     */   
/* 1364:     */   public String getIncoterm()
/* 1365:     */   {
/* 1366:1733 */     return this.incoterm;
/* 1367:     */   }
/* 1368:     */   
/* 1369:     */   public void setIncoterm(String incoterm)
/* 1370:     */   {
/* 1371:1737 */     this.incoterm = incoterm;
/* 1372:     */   }
/* 1373:     */   
/* 1374:     */   public Ciudad getLugarIncoterm()
/* 1375:     */   {
/* 1376:1741 */     return this.lugarIncoterm;
/* 1377:     */   }
/* 1378:     */   
/* 1379:     */   public void setLugarIncoterm(Ciudad lugarIncoterm)
/* 1380:     */   {
/* 1381:1745 */     this.lugarIncoterm = lugarIncoterm;
/* 1382:     */   }
/* 1383:     */   
/* 1384:     */   public Pais getPaisOrigen()
/* 1385:     */   {
/* 1386:1749 */     return this.paisOrigen;
/* 1387:     */   }
/* 1388:     */   
/* 1389:     */   public void setPaisOrigen(Pais paisOrigen)
/* 1390:     */   {
/* 1391:1753 */     this.paisOrigen = paisOrigen;
/* 1392:     */   }
/* 1393:     */   
/* 1394:     */   public Pais getPaisDestino()
/* 1395:     */   {
/* 1396:1757 */     return this.paisDestino;
/* 1397:     */   }
/* 1398:     */   
/* 1399:     */   public void setPaisDestino(Pais paisDestino)
/* 1400:     */   {
/* 1401:1761 */     this.paisDestino = paisDestino;
/* 1402:     */   }
/* 1403:     */   
/* 1404:     */   public Ciudad getPuertoEmbarque()
/* 1405:     */   {
/* 1406:1765 */     return this.puertoEmbarque;
/* 1407:     */   }
/* 1408:     */   
/* 1409:     */   public void setPuertoEmbarque(Ciudad puertoEmbarque)
/* 1410:     */   {
/* 1411:1769 */     this.puertoEmbarque = puertoEmbarque;
/* 1412:     */   }
/* 1413:     */   
/* 1414:     */   public Ciudad getPuertoDestino()
/* 1415:     */   {
/* 1416:1773 */     return this.puertoDestino;
/* 1417:     */   }
/* 1418:     */   
/* 1419:     */   public void setPuertoDestino(Ciudad puertoDestino)
/* 1420:     */   {
/* 1421:1777 */     this.puertoDestino = puertoDestino;
/* 1422:     */   }
/* 1423:     */   
/* 1424:     */   public BigDecimal getFleteInternacional()
/* 1425:     */   {
/* 1426:1781 */     return this.fleteInternacional;
/* 1427:     */   }
/* 1428:     */   
/* 1429:     */   public void setFleteInternacional(BigDecimal fleteInternacional)
/* 1430:     */   {
/* 1431:1785 */     this.fleteInternacional = fleteInternacional;
/* 1432:     */   }
/* 1433:     */   
/* 1434:     */   public BigDecimal getSeguroInternacional()
/* 1435:     */   {
/* 1436:1789 */     return this.seguroInternacional;
/* 1437:     */   }
/* 1438:     */   
/* 1439:     */   public void setSeguroInternacional(BigDecimal seguroInternacional)
/* 1440:     */   {
/* 1441:1793 */     this.seguroInternacional = seguroInternacional;
/* 1442:     */   }
/* 1443:     */   
/* 1444:     */   public BigDecimal getGastosAduaneros()
/* 1445:     */   {
/* 1446:1797 */     return this.gastosAduaneros;
/* 1447:     */   }
/* 1448:     */   
/* 1449:     */   public void setGastosAduaneros(BigDecimal gastosAduaneros)
/* 1450:     */   {
/* 1451:1801 */     this.gastosAduaneros = gastosAduaneros;
/* 1452:     */   }
/* 1453:     */   
/* 1454:     */   public BigDecimal getOtrosGastosTransporte()
/* 1455:     */   {
/* 1456:1805 */     return this.otrosGastosTransporte;
/* 1457:     */   }
/* 1458:     */   
/* 1459:     */   public void setOtrosGastosTransporte(BigDecimal otrosGastosTransporte)
/* 1460:     */   {
/* 1461:1809 */     this.otrosGastosTransporte = otrosGastosTransporte;
/* 1462:     */   }
/* 1463:     */   
/* 1464:     */   public Pais getPaisAdquisicion()
/* 1465:     */   {
/* 1466:1813 */     return this.paisAdquisicion;
/* 1467:     */   }
/* 1468:     */   
/* 1469:     */   public void setPaisAdquisicion(Pais paisAdquisicion)
/* 1470:     */   {
/* 1471:1817 */     this.paisAdquisicion = paisAdquisicion;
/* 1472:     */   }
/* 1473:     */   
/* 1474:     */   public Integer getIdDispositivoSincronizacion()
/* 1475:     */   {
/* 1476:1821 */     return this.idDispositivoSincronizacion;
/* 1477:     */   }
/* 1478:     */   
/* 1479:     */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 1480:     */   {
/* 1481:1825 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 1482:     */   }
/* 1483:     */   
/* 1484:     */   public BigDecimal getDescuentoImpuesto()
/* 1485:     */   {
/* 1486:1829 */     return this.descuentoImpuesto;
/* 1487:     */   }
/* 1488:     */   
/* 1489:     */   public void setDescuentoImpuesto(BigDecimal descuentoImpuesto)
/* 1490:     */   {
/* 1491:1833 */     this.descuentoImpuesto = descuentoImpuesto;
/* 1492:     */   }
/* 1493:     */   
/* 1494:     */   public boolean isIndicadorGeneraCxC()
/* 1495:     */   {
/* 1496:1837 */     return this.indicadorGeneraCxC;
/* 1497:     */   }
/* 1498:     */   
/* 1499:     */   public void setIndicadorGeneraCxC(boolean indicadorGeneraCxC)
/* 1500:     */   {
/* 1501:1841 */     this.indicadorGeneraCxC = indicadorGeneraCxC;
/* 1502:     */   }
/* 1503:     */   
/* 1504:     */   public boolean isFacturaClienteAgil()
/* 1505:     */   {
/* 1506:1845 */     return this.facturaClienteAgil;
/* 1507:     */   }
/* 1508:     */   
/* 1509:     */   public void setFacturaClienteAgil(boolean facturaClienteAgil)
/* 1510:     */   {
/* 1511:1849 */     this.facturaClienteAgil = facturaClienteAgil;
/* 1512:     */   }
/* 1513:     */   
/* 1514:     */   public List<DetalleValoresContratoVenta> getListaDetalleValoresContratoVenta()
/* 1515:     */   {
/* 1516:1853 */     return this.listaDetalleValoresContratoVenta;
/* 1517:     */   }
/* 1518:     */   
/* 1519:     */   public void setListaDetalleValoresContratoVenta(List<DetalleValoresContratoVenta> listaDetalleValoresContratoVenta)
/* 1520:     */   {
/* 1521:1857 */     this.listaDetalleValoresContratoVenta = listaDetalleValoresContratoVenta;
/* 1522:     */   }
/* 1523:     */   
/* 1524:     */   public int getCantidadContratosMostrar()
/* 1525:     */   {
/* 1526:1861 */     int cantidad = 0;
/* 1527:1862 */     if (this.listaDetalleValoresContratoVenta != null) {
/* 1528:1863 */       if (this.listaDetalleValoresContratoVenta.size() > 10) {
/* 1529:1864 */         cantidad = 10;
/* 1530:     */       } else {
/* 1531:1866 */         cantidad = this.listaDetalleValoresContratoVenta.size();
/* 1532:     */       }
/* 1533:     */     }
/* 1534:1869 */     return cantidad;
/* 1535:     */   }
/* 1536:     */   
/* 1537:     */   public boolean isFacturaClienteFloricola()
/* 1538:     */   {
/* 1539:1873 */     return this.facturaClienteFloricola;
/* 1540:     */   }
/* 1541:     */   
/* 1542:     */   public void setFacturaClienteFloricola(boolean facturaClienteFloricola)
/* 1543:     */   {
/* 1544:1877 */     this.facturaClienteFloricola = facturaClienteFloricola;
/* 1545:     */   }
/* 1546:     */   
/* 1547:     */   public String getPdf()
/* 1548:     */   {
/* 1549:1881 */     return this.pdf;
/* 1550:     */   }
/* 1551:     */   
/* 1552:     */   public void setPdf(String pdf)
/* 1553:     */   {
/* 1554:1885 */     this.pdf = pdf;
/* 1555:     */   }
/* 1556:     */   
/* 1557:     */   public OrigenEnum getOrigen()
/* 1558:     */   {
/* 1559:1889 */     return this.origen;
/* 1560:     */   }
/* 1561:     */   
/* 1562:     */   public void setOrigen(OrigenEnum origen)
/* 1563:     */   {
/* 1564:1893 */     this.origen = origen;
/* 1565:     */   }
/* 1566:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.FacturaCliente
 * JD-Core Version:    0.7.0.1
 */