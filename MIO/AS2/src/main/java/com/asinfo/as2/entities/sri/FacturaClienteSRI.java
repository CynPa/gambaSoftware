/*    1:     */ package com.asinfo.as2.entities.sri;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*    4:     */ import com.asinfo.as2.entities.EntidadBase;
/*    5:     */ import com.asinfo.as2.entities.FacturaCliente;
/*    6:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*    7:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*    8:     */ import com.asinfo.as2.enumeraciones.Estado;
/*    9:     */ import com.asinfo.as2.enumeraciones.RefrendoEnum;
/*   10:     */ import java.math.BigDecimal;
/*   11:     */ import java.util.Date;
/*   12:     */ import java.util.HashMap;
/*   13:     */ import javax.persistence.Column;
/*   14:     */ import javax.persistence.Entity;
/*   15:     */ import javax.persistence.EnumType;
/*   16:     */ import javax.persistence.Enumerated;
/*   17:     */ import javax.persistence.FetchType;
/*   18:     */ import javax.persistence.GeneratedValue;
/*   19:     */ import javax.persistence.GenerationType;
/*   20:     */ import javax.persistence.Id;
/*   21:     */ import javax.persistence.JoinColumn;
/*   22:     */ import javax.persistence.ManyToOne;
/*   23:     */ import javax.persistence.OneToOne;
/*   24:     */ import javax.persistence.Table;
/*   25:     */ import javax.persistence.TableGenerator;
/*   26:     */ import javax.persistence.Temporal;
/*   27:     */ import javax.persistence.TemporalType;
/*   28:     */ import javax.persistence.Transient;
/*   29:     */ import javax.validation.constraints.DecimalMin;
/*   30:     */ import javax.validation.constraints.Digits;
/*   31:     */ import javax.validation.constraints.Min;
/*   32:     */ import javax.validation.constraints.NotNull;
/*   33:     */ import javax.validation.constraints.Size;
/*   34:     */ import org.hibernate.annotations.ColumnDefault;
/*   35:     */ 
/*   36:     */ @Entity
/*   37:     */ @Table(name="factura_clienteSRI", indexes={@javax.persistence.Index(columnList="id_factura_cliente"), @javax.persistence.Index(columnList="id_factura_proveedorSRI")})
/*   38:     */ public class FacturaClienteSRI
/*   39:     */   extends EntidadBase
/*   40:     */ {
/*   41:     */   private static final long serialVersionUID = 1L;
/*   42:     */   @Id
/*   43:     */   @TableGenerator(name="factura_clienteSRI", initialValue=0, allocationSize=50)
/*   44:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="factura_clienteSRI")
/*   45:     */   @Column(name="id_factura_clienteSRI")
/*   46:     */   private int idFacturaclienteSRI;
/*   47:     */   @Column(name="id_organizacion", nullable=false)
/*   48:     */   private int idOrganizacion;
/*   49:     */   @Column(name="id_sucursal", nullable=false)
/*   50:     */   private int idSucursal;
/*   51:     */   @Column(name="estado", nullable=false)
/*   52:     */   @Enumerated(EnumType.ORDINAL)
/*   53:     */   private Estado estado;
/*   54:     */   @OneToOne(fetch=FetchType.LAZY)
/*   55:     */   @JoinColumn(name="id_factura_cliente", nullable=true)
/*   56:     */   private FacturaCliente facturaCliente;
/*   57:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   58:     */   @JoinColumn(name="id_tipo_identificacion", nullable=true)
/*   59:     */   private TipoIdentificacion tipoIdentificacion;
/*   60:     */   @Column(name="identificacion_cliente", length=20, nullable=true)
/*   61:     */   @Size(min=1, max=20)
/*   62:     */   private String identificacionCliente;
/*   63:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   64:     */   @JoinColumn(name="id_tipo_comprobante", nullable=true)
/*   65:     */   private TipoComprobanteSRI tipoComprobanteSRI;
/*   66:     */   @Temporal(TemporalType.DATE)
/*   67:     */   @Column(name="fecha_emision", nullable=true)
/*   68:     */   private Date fechaEmision;
/*   69:     */   @Column(name="establecimiento", length=3, nullable=false)
/*   70:     */   @NotNull
/*   71:     */   @Size(min=3, max=3)
/*   72:     */   private String establecimiento;
/*   73:     */   @Column(name="punto_emision", length=3, nullable=false)
/*   74:     */   @NotNull
/*   75:     */   @Size(min=3, max=3)
/*   76:     */   private String puntoEmision;
/*   77:     */   @Column(name="numero", length=20, nullable=false)
/*   78:     */   @NotNull
/*   79:     */   @Size(min=1, max=20)
/*   80:     */   private String numero;
/*   81:     */   @Column(name="monto_iva", precision=12, scale=2)
/*   82:     */   @Digits(integer=12, fraction=2)
/*   83:     */   @Min(0L)
/*   84: 115 */   private BigDecimal montoIva = BigDecimal.ZERO;
/*   85:     */   @Column(name="monto_IRBPNR", precision=12, scale=2)
/*   86:     */   @Digits(integer=12, fraction=2)
/*   87:     */   @Min(0L)
/*   88: 120 */   private BigDecimal montoIRBPNR = BigDecimal.ZERO;
/*   89:     */   @Column(name="base_imponible_tarifa_cero", precision=12, scale=2)
/*   90:     */   @Digits(integer=12, fraction=2)
/*   91:     */   @Min(0L)
/*   92: 125 */   private BigDecimal baseImponibleTarifaCero = BigDecimal.ZERO;
/*   93:     */   @Column(name="base_imponible_diferente_cero", precision=12, scale=2)
/*   94:     */   @Digits(integer=12, fraction=2)
/*   95:     */   @Min(0L)
/*   96: 130 */   private BigDecimal baseImponibleDiferenteCero = BigDecimal.ZERO;
/*   97:     */   @Column(name="base_imponible_no_objeto_iva", precision=12, scale=2)
/*   98:     */   @Digits(integer=12, fraction=2)
/*   99:     */   @Min(0L)
/*  100: 135 */   private BigDecimal baseImponibleNoObjetoIva = BigDecimal.ZERO;
/*  101:     */   @Column(name="autorizacion", length=50, nullable=true)
/*  102:     */   @Size(min=10, max=50)
/*  103:     */   private String autorizacion;
/*  104:     */   @Temporal(TemporalType.TIMESTAMP)
/*  105:     */   @Column(name="fecha_autorizacion", nullable=true)
/*  106:     */   private Date fechaAutorizacion;
/*  107:     */   @Temporal(TemporalType.DATE)
/*  108:     */   @Column(name="fecha_caducidad", nullable=true)
/*  109:     */   private Date fechaCaducidad;
/*  110:     */   @Column(name="indicador_saldo_inicial", nullable=false)
/*  111:     */   private boolean indicadorSaldoInicial;
/*  112:     */   @Enumerated(EnumType.ORDINAL)
/*  113:     */   @Column(name="refrendo", nullable=true)
/*  114:     */   private RefrendoEnum refrendo;
/*  115:     */   @Column(name="distrito_refrendo", nullable=true)
/*  116:     */   private String distritoRefrendo;
/*  117:     */   @Column(name="anio_refrendo", nullable=true)
/*  118:     */   private Integer anioRefrendo;
/*  119:     */   @Column(name="regimen_refrendo", nullable=true)
/*  120:     */   private String regimenRefrendo;
/*  121:     */   @Column(name="correlativo_refrendo", nullable=true)
/*  122:     */   private String correlativoRefrendo;
/*  123:     */   @Column(name="documento_transporte_refrendo", nullable=true)
/*  124:     */   private String documentoTransporteRefrendo;
/*  125:     */   @Temporal(TemporalType.DATE)
/*  126:     */   @Column(name="fecha_transaccion", nullable=true)
/*  127:     */   private Date fechaTransaccion;
/*  128:     */   @Column(name="valor_fob_refrendo", nullable=true)
/*  129:     */   private BigDecimal valorFobRefrendo;
/*  130:     */   @Column(name="valor_fob_comprobante_refrendo", nullable=true)
/*  131:     */   private BigDecimal valorFobComprobanteRefrendo;
/*  132:     */   @Column(name="monto_ice", precision=12, scale=2)
/*  133:     */   @Digits(integer=12, fraction=2)
/*  134:     */   @Min(0L)
/*  135: 187 */   private BigDecimal montoIce = BigDecimal.ZERO;
/*  136:     */   @Size(max=500)
/*  137:     */   @Column(name="email", nullable=true, length=500, updatable=false)
/*  138:     */   private String email;
/*  139:     */   @Transient
/*  140:     */   private int traRefrendo;
/*  141:     */   @Transient
/*  142: 199 */   private BigDecimal valorRetenidoIva = BigDecimal.ZERO;
/*  143:     */   @Column(name="valor_retenido_fuente", precision=12, scale=2)
/*  144:     */   @Digits(integer=12, fraction=2)
/*  145:     */   @Min(0L)
/*  146: 202 */   private BigDecimal valorRetenidoFuente = BigDecimal.ZERO;
/*  147:     */   @DecimalMin("0.00")
/*  148:     */   @Column(name="total_subsidio", nullable=false, precision=12, scale=2)
/*  149:     */   @NotNull
/*  150: 207 */   private BigDecimal totalSubsidio = BigDecimal.ZERO;
/*  151:     */   @OneToOne(fetch=FetchType.LAZY)
/*  152:     */   @JoinColumn(name="id_factura_proveedorSRI")
/*  153:     */   private FacturaProveedorSRI facturaProveedorSRI;
/*  154:     */   @Transient
/*  155:     */   private long numeroComprobantes;
/*  156:     */   @Transient
/*  157:     */   private String codigoTipoIdentificacion;
/*  158:     */   @Transient
/*  159:     */   private String traNumeroNuevo;
/*  160:     */   @Transient
/*  161:     */   private String codigoTipoComprobanteSRI;
/*  162:     */   @Column(name="ambiente", nullable=false)
/*  163:     */   private int ambiente;
/*  164:     */   @Column(name="tipo_emision", nullable=false)
/*  165:     */   private int tipoEmision;
/*  166:     */   @Column(name="indicador_documento_electronico", nullable=false)
/*  167:     */   private boolean indicadorDocumentoElectronico;
/*  168:     */   @Column(name="clave_acceso", length=50)
/*  169:     */   private String claveAcceso;
/*  170:     */   @Column(name="codigo_unico", length=50, nullable=true)
/*  171:     */   private String codigoUnico;
/*  172:     */   @Column(name="direccion_matriz", length=200)
/*  173:     */   private String direccionMatriz;
/*  174:     */   @Column(name="direccion_sucursal", length=200)
/*  175:     */   private String direccionSucursal;
/*  176:     */   @Column(name="generar_documento_electronico", nullable=true)
/*  177: 253 */   private Boolean generarDocumentoElectronico = Boolean.valueOf(true);
/*  178:     */   @Column(name="estado_documento_electronico", nullable=true)
/*  179:     */   @Enumerated(EnumType.ORDINAL)
/*  180:     */   private EstadoDocumentoElectronico estadoDocumentoElectronico;
/*  181:     */   @Column(name="mensaje_sri", length=5000, nullable=true)
/*  182:     */   @Size(max=5000)
/*  183:     */   private String mensajeSRI;
/*  184:     */   @Transient
/*  185:     */   private DocumentoBase documentoBase;
/*  186:     */   @Transient
/*  187:     */   private String exportParaisoFiscal;
/*  188:     */   @Transient
/*  189:     */   private String idCliente;
/*  190:     */   @Transient
/*  191:     */   private String tpIdClienteEx;
/*  192:     */   @Transient
/*  193:     */   private String paisEfecExp;
/*  194:     */   @Transient
/*  195: 285 */   private BigDecimal descuentoImpuesto = BigDecimal.ZERO;
/*  196: 289 */   private HashMap<String, String> listaCodigoFormaPagoSri = new HashMap();
/*  197:     */   @Column(name="codigo_forma_pago_sri", length=10, nullable=true)
/*  198:     */   private String codigoFormaPagoSRI;
/*  199:     */   @Column(name="valor_impuesto_exportacion", precision=12, scale=2)
/*  200:     */   @Digits(integer=12, fraction=2)
/*  201:     */   @Min(0L)
/*  202:     */   @ColumnDefault("0")
/*  203:     */   @NotNull
/*  204: 297 */   private BigDecimal valorImpuestoExportacion = BigDecimal.ZERO;
/*  205:     */   @Column(name="ingreso_exterior_grabo_impuestos", nullable=false)
/*  206:     */   @ColumnDefault("'0'")
/*  207:     */   @NotNull
/*  208:     */   private boolean ingresoExteriorGraboImpuestos;
/*  209:     */   @Column(name="tipo_ingreso_exterior", length=250, nullable=true)
/*  210:     */   @ColumnDefault("''")
/*  211:     */   private String tipoIngresoExterior;
/*  212:     */   
/*  213:     */   public FacturaClienteSRI() {}
/*  214:     */   
/*  215:     */   public FacturaClienteSRI(String codigoTipoIdentificacion, String codigoTipoComprobanteSRI, String identificacionCliente, BigDecimal montoIva, BigDecimal baseImponibleTarifaCero, BigDecimal baseImponibleDiferenteCero, BigDecimal baseImponibleNoObjetoIva, long numeroComprobantes, BigDecimal valorRetenidoIva, BigDecimal valorRetenidoFuente, boolean indicadorDocumentoElectronico, String codigoFormaPagoSRI)
/*  216:     */   {
/*  217: 319 */     this(codigoTipoIdentificacion, codigoTipoComprobanteSRI, identificacionCliente, montoIva, baseImponibleTarifaCero, baseImponibleDiferenteCero, baseImponibleNoObjetoIva, numeroComprobantes, valorRetenidoIva, valorRetenidoFuente, indicadorDocumentoElectronico);
/*  218:     */     
/*  219:     */ 
/*  220: 322 */     this.codigoFormaPagoSRI = codigoFormaPagoSRI;
/*  221:     */   }
/*  222:     */   
/*  223:     */   public FacturaClienteSRI(String codigoTipoIdentificacion, String codigoTipoComprobanteSRI, String identificacionCliente, BigDecimal montoIva, BigDecimal baseImponibleTarifaCero, BigDecimal baseImponibleDiferenteCero, BigDecimal baseImponibleNoObjetoIva, long numeroComprobantes, BigDecimal valorRetenidoIva, BigDecimal valorRetenidoFuente, boolean indicadorDocumentoElectronico)
/*  224:     */   {
/*  225: 341 */     this.codigoTipoIdentificacion = codigoTipoIdentificacion;
/*  226: 342 */     this.codigoTipoComprobanteSRI = codigoTipoComprobanteSRI;
/*  227: 343 */     this.identificacionCliente = identificacionCliente;
/*  228: 344 */     this.montoIva = montoIva;
/*  229: 345 */     this.baseImponibleTarifaCero = baseImponibleTarifaCero;
/*  230: 346 */     this.baseImponibleDiferenteCero = baseImponibleDiferenteCero;
/*  231: 347 */     this.baseImponibleNoObjetoIva = baseImponibleNoObjetoIva;
/*  232: 348 */     this.numeroComprobantes = numeroComprobantes;
/*  233: 349 */     this.valorRetenidoIva = valorRetenidoIva;
/*  234: 350 */     this.valorRetenidoFuente = valorRetenidoFuente;
/*  235: 351 */     this.indicadorDocumentoElectronico = indicadorDocumentoElectronico;
/*  236:     */   }
/*  237:     */   
/*  238:     */   public FacturaClienteSRI(String establecimiento, String codigoTipoIdentificacion, String codigoTipoComprobanteSRI, String identificacionCliente, BigDecimal montoIva, BigDecimal baseImponibleTarifaCero, BigDecimal baseImponibleDiferenteCero, BigDecimal baseImponibleNoObjetoIva, long numeroComprobantes, BigDecimal valorRetenidoIva, BigDecimal valorRetenidoFuente, BigDecimal montoICE, boolean indicadorDocumentoElectronico, String codigoFormaPagoSRI)
/*  239:     */   {
/*  240: 375 */     this(establecimiento, codigoTipoIdentificacion, codigoTipoComprobanteSRI, identificacionCliente, montoIva, baseImponibleTarifaCero, baseImponibleDiferenteCero, baseImponibleNoObjetoIva, numeroComprobantes, valorRetenidoIva, valorRetenidoFuente, montoICE, indicadorDocumentoElectronico);
/*  241:     */     
/*  242:     */ 
/*  243: 378 */     this.codigoFormaPagoSRI = codigoFormaPagoSRI;
/*  244:     */   }
/*  245:     */   
/*  246:     */   public FacturaClienteSRI(String establecimiento, String codigoTipoIdentificacion, String codigoTipoComprobanteSRI, String identificacionCliente, BigDecimal montoIva, BigDecimal baseImponibleTarifaCero, BigDecimal baseImponibleDiferenteCero, BigDecimal baseImponibleNoObjetoIva, long numeroComprobantes, BigDecimal valorRetenidoIva, BigDecimal valorRetenidoFuente, BigDecimal montoICE, boolean indicadorDocumentoElectronico, String codigoFormaPagoSRI, BigDecimal descuentoImpuesto)
/*  247:     */   {
/*  248: 385 */     this(establecimiento, codigoTipoIdentificacion, codigoTipoComprobanteSRI, identificacionCliente, montoIva, baseImponibleTarifaCero, baseImponibleDiferenteCero, baseImponibleNoObjetoIva, numeroComprobantes, valorRetenidoIva, valorRetenidoFuente, montoICE, indicadorDocumentoElectronico, codigoFormaPagoSRI);
/*  249:     */     
/*  250:     */ 
/*  251: 388 */     this.descuentoImpuesto = descuentoImpuesto;
/*  252:     */   }
/*  253:     */   
/*  254:     */   public FacturaClienteSRI(String establecimiento, String codigoTipoIdentificacion, String codigoTipoComprobanteSRI, String identificacionCliente, DocumentoBase documentoBase, BigDecimal montoIva, BigDecimal baseImponibleTarifaCero, BigDecimal baseImponibleDiferenteCero, BigDecimal baseImponibleNoObjetoIva, long numeroComprobantes, BigDecimal valorRetenidoIva, BigDecimal valorRetenidoFuente, boolean indicadorDocumentoElectronico)
/*  255:     */   {
/*  256: 395 */     this(codigoTipoIdentificacion, codigoTipoComprobanteSRI, identificacionCliente, montoIva, baseImponibleTarifaCero, baseImponibleDiferenteCero, baseImponibleNoObjetoIva, numeroComprobantes, valorRetenidoIva, valorRetenidoFuente, indicadorDocumentoElectronico);
/*  257:     */     
/*  258:     */ 
/*  259: 398 */     this.establecimiento = establecimiento;
/*  260: 399 */     this.documentoBase = documentoBase;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public FacturaClienteSRI(String establecimiento, String codigoTipoIdentificacion, String codigoTipoComprobanteSRI, String identificacionCliente, BigDecimal montoIva, BigDecimal baseImponibleTarifaCero, BigDecimal baseImponibleDiferenteCero, BigDecimal baseImponibleNoObjetoIva, long numeroComprobantes, BigDecimal valorRetenidoIva, BigDecimal valorRetenidoFuente, BigDecimal montoICE, boolean indicadorDocumentoElectronico)
/*  264:     */   {
/*  265: 407 */     this(establecimiento, codigoTipoIdentificacion, codigoTipoComprobanteSRI, identificacionCliente, null, montoIva, baseImponibleTarifaCero, baseImponibleDiferenteCero, baseImponibleNoObjetoIva, numeroComprobantes, valorRetenidoIva, valorRetenidoFuente, indicadorDocumentoElectronico);
/*  266:     */     
/*  267:     */ 
/*  268: 410 */     this.montoIce = montoICE;
/*  269:     */   }
/*  270:     */   
/*  271:     */   public FacturaClienteSRI(String identificacionCliente, BigDecimal valorRetenidoIva, BigDecimal valorRetenidoFuente)
/*  272:     */   {
/*  273: 415 */     this.identificacionCliente = identificacionCliente;
/*  274: 416 */     this.valorRetenidoIva = valorRetenidoIva;
/*  275: 417 */     this.valorRetenidoFuente = valorRetenidoFuente;
/*  276:     */   }
/*  277:     */   
/*  278:     */   public FacturaClienteSRI(Date fechaEmision, String establecimiento, String puntoEmision, String numero, String autorizacion, String distritoRefrendo, Integer anioRefrendo, String regimenRefrendo, String correlativoRefrendo, String documentoTransporteRefrendo, Date fechaTransaccion, BigDecimal valorFobRefrendo, BigDecimal valorFobComprobanteRefrendo, RefrendoEnum refrendo, String codigoTipoComprobanteSRI, String exportParaisoFiscal, String idCliente, String tpIdClienteEx, String paisEfecExp)
/*  279:     */   {
/*  280: 442 */     this.fechaEmision = fechaEmision;
/*  281: 443 */     this.establecimiento = establecimiento;
/*  282: 444 */     this.puntoEmision = puntoEmision;
/*  283: 445 */     this.numero = numero;
/*  284: 446 */     this.autorizacion = autorizacion;
/*  285: 447 */     this.distritoRefrendo = distritoRefrendo;
/*  286: 448 */     this.anioRefrendo = anioRefrendo;
/*  287: 449 */     this.regimenRefrendo = regimenRefrendo;
/*  288: 450 */     this.correlativoRefrendo = correlativoRefrendo;
/*  289: 451 */     this.documentoTransporteRefrendo = documentoTransporteRefrendo;
/*  290: 452 */     this.fechaTransaccion = fechaTransaccion;
/*  291: 453 */     this.valorFobRefrendo = valorFobRefrendo;
/*  292: 454 */     this.valorFobComprobanteRefrendo = valorFobComprobanteRefrendo;
/*  293: 455 */     this.refrendo = refrendo;
/*  294: 456 */     this.traRefrendo = Integer.valueOf(this.refrendo != null ? this.refrendo.getCodigo() : "0").intValue();
/*  295: 457 */     this.codigoTipoComprobanteSRI = codigoTipoComprobanteSRI;
/*  296: 458 */     this.exportParaisoFiscal = exportParaisoFiscal;
/*  297: 459 */     this.idCliente = idCliente;
/*  298: 460 */     this.tpIdClienteEx = tpIdClienteEx;
/*  299: 461 */     this.paisEfecExp = paisEfecExp;
/*  300:     */   }
/*  301:     */   
/*  302:     */   public FacturaClienteSRI(Date fechaEmision, String establecimiento, String puntoEmision, String numero, String autorizacion, String distritoRefrendo, Integer anioRefrendo, String regimenRefrendo, String correlativoRefrendo, String documentoTransporteRefrendo, Date fechaTransaccion, BigDecimal valorFobRefrendo, BigDecimal valorFobComprobanteRefrendo, RefrendoEnum refrendo, String codigoTipoComprobanteSRI, String exportParaisoFiscal, String idCliente, String tpIdClienteEx, String paisEfecExp, String tipoIngresoExterior, boolean ingresoExteriorGraboImpuestos, BigDecimal valorImpuestoExportacion)
/*  303:     */   {
/*  304: 472 */     this(fechaEmision, establecimiento, puntoEmision, numero, autorizacion, distritoRefrendo, anioRefrendo, regimenRefrendo, correlativoRefrendo, documentoTransporteRefrendo, fechaTransaccion, valorFobRefrendo, valorFobComprobanteRefrendo, refrendo, codigoTipoComprobanteSRI, exportParaisoFiscal, idCliente, tpIdClienteEx, paisEfecExp);
/*  305:     */     
/*  306:     */ 
/*  307: 475 */     this.tipoIngresoExterior = tipoIngresoExterior;
/*  308: 476 */     this.ingresoExteriorGraboImpuestos = ingresoExteriorGraboImpuestos;
/*  309: 477 */     this.valorImpuestoExportacion = valorImpuestoExportacion;
/*  310:     */   }
/*  311:     */   
/*  312:     */   public String getNumeroDAE()
/*  313:     */   {
/*  314: 483 */     return (getDistritoRefrendo() != null ? getDistritoRefrendo() : "") + (getRegimenRefrendo() != null ? getRegimenRefrendo() : "") + (getCorrelativoRefrendo() != null ? getCorrelativoRefrendo() : "");
/*  315:     */   }
/*  316:     */   
/*  317:     */   public int getIdFacturaclienteSRI()
/*  318:     */   {
/*  319: 492 */     return this.idFacturaclienteSRI;
/*  320:     */   }
/*  321:     */   
/*  322:     */   public void setIdFacturaclienteSRI(int idFacturaclienteSRI)
/*  323:     */   {
/*  324: 502 */     this.idFacturaclienteSRI = idFacturaclienteSRI;
/*  325:     */   }
/*  326:     */   
/*  327:     */   public int getIdOrganizacion()
/*  328:     */   {
/*  329: 511 */     return this.idOrganizacion;
/*  330:     */   }
/*  331:     */   
/*  332:     */   public void setIdOrganizacion(int idOrganizacion)
/*  333:     */   {
/*  334: 521 */     this.idOrganizacion = idOrganizacion;
/*  335:     */   }
/*  336:     */   
/*  337:     */   public int getIdSucursal()
/*  338:     */   {
/*  339: 530 */     return this.idSucursal;
/*  340:     */   }
/*  341:     */   
/*  342:     */   public void setIdSucursal(int idSucursal)
/*  343:     */   {
/*  344: 540 */     this.idSucursal = idSucursal;
/*  345:     */   }
/*  346:     */   
/*  347:     */   public Estado getEstado()
/*  348:     */   {
/*  349: 549 */     return this.estado;
/*  350:     */   }
/*  351:     */   
/*  352:     */   public void setEstado(Estado estado)
/*  353:     */   {
/*  354: 559 */     this.estado = estado;
/*  355:     */   }
/*  356:     */   
/*  357:     */   public FacturaCliente getFacturaCliente()
/*  358:     */   {
/*  359: 568 */     return this.facturaCliente;
/*  360:     */   }
/*  361:     */   
/*  362:     */   public void setFacturaCliente(FacturaCliente facturacliente)
/*  363:     */   {
/*  364: 578 */     this.facturaCliente = facturacliente;
/*  365:     */   }
/*  366:     */   
/*  367:     */   public TipoIdentificacion getTipoIdentificacion()
/*  368:     */   {
/*  369: 587 */     return this.tipoIdentificacion;
/*  370:     */   }
/*  371:     */   
/*  372:     */   public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/*  373:     */   {
/*  374: 597 */     this.tipoIdentificacion = tipoIdentificacion;
/*  375:     */   }
/*  376:     */   
/*  377:     */   public String getIdentificacionCliente()
/*  378:     */   {
/*  379: 606 */     return this.identificacionCliente;
/*  380:     */   }
/*  381:     */   
/*  382:     */   public void setIdentificacionCliente(String identificacioncliente)
/*  383:     */   {
/*  384: 616 */     this.identificacionCliente = identificacioncliente;
/*  385:     */   }
/*  386:     */   
/*  387:     */   public TipoComprobanteSRI getTipoComprobanteSRI()
/*  388:     */   {
/*  389: 625 */     return this.tipoComprobanteSRI;
/*  390:     */   }
/*  391:     */   
/*  392:     */   public void setTipoComprobanteSRI(TipoComprobanteSRI tipoComprobanteSRI)
/*  393:     */   {
/*  394: 635 */     this.tipoComprobanteSRI = tipoComprobanteSRI;
/*  395:     */   }
/*  396:     */   
/*  397:     */   public Date getFechaEmision()
/*  398:     */   {
/*  399: 644 */     return this.fechaEmision;
/*  400:     */   }
/*  401:     */   
/*  402:     */   public void setFechaEmision(Date fechaEmision)
/*  403:     */   {
/*  404: 654 */     this.fechaEmision = fechaEmision;
/*  405:     */   }
/*  406:     */   
/*  407:     */   public String getEstablecimiento()
/*  408:     */   {
/*  409: 663 */     return this.establecimiento;
/*  410:     */   }
/*  411:     */   
/*  412:     */   public void setEstablecimiento(String establecimiento)
/*  413:     */   {
/*  414: 673 */     this.establecimiento = establecimiento;
/*  415:     */   }
/*  416:     */   
/*  417:     */   public String getPuntoEmision()
/*  418:     */   {
/*  419: 682 */     return this.puntoEmision;
/*  420:     */   }
/*  421:     */   
/*  422:     */   public void setPuntoEmision(String puntoEmision)
/*  423:     */   {
/*  424: 692 */     this.puntoEmision = puntoEmision;
/*  425:     */   }
/*  426:     */   
/*  427:     */   public String getNumero()
/*  428:     */   {
/*  429: 701 */     return this.numero;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public void setNumero(String numero)
/*  433:     */   {
/*  434: 711 */     this.numero = numero;
/*  435:     */   }
/*  436:     */   
/*  437:     */   public BigDecimal getMontoIva()
/*  438:     */   {
/*  439: 720 */     return this.montoIva;
/*  440:     */   }
/*  441:     */   
/*  442:     */   public void setMontoIva(BigDecimal montoIva)
/*  443:     */   {
/*  444: 730 */     this.montoIva = montoIva;
/*  445:     */   }
/*  446:     */   
/*  447:     */   public BigDecimal getBaseImponibleTarifaCero()
/*  448:     */   {
/*  449: 739 */     return this.baseImponibleTarifaCero;
/*  450:     */   }
/*  451:     */   
/*  452:     */   public void setBaseImponibleTarifaCero(BigDecimal baseImponibleTarifaCero)
/*  453:     */   {
/*  454: 749 */     this.baseImponibleTarifaCero = baseImponibleTarifaCero;
/*  455:     */   }
/*  456:     */   
/*  457:     */   public BigDecimal getBaseImponibleDiferenteCero()
/*  458:     */   {
/*  459: 758 */     return this.baseImponibleDiferenteCero;
/*  460:     */   }
/*  461:     */   
/*  462:     */   public void setBaseImponibleDiferenteCero(BigDecimal baseImponibleDiferenteCero)
/*  463:     */   {
/*  464: 768 */     this.baseImponibleDiferenteCero = baseImponibleDiferenteCero;
/*  465:     */   }
/*  466:     */   
/*  467:     */   public BigDecimal getBaseImponibleNoObjetoIva()
/*  468:     */   {
/*  469: 777 */     return this.baseImponibleNoObjetoIva;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void setBaseImponibleNoObjetoIva(BigDecimal baseImponibleNoObjetoIva)
/*  473:     */   {
/*  474: 787 */     this.baseImponibleNoObjetoIva = baseImponibleNoObjetoIva;
/*  475:     */   }
/*  476:     */   
/*  477:     */   public BigDecimal getValorRetenidoIva()
/*  478:     */   {
/*  479: 796 */     return this.valorRetenidoIva;
/*  480:     */   }
/*  481:     */   
/*  482:     */   public void setValorRetenidoIva(BigDecimal valorRetenidoIva)
/*  483:     */   {
/*  484: 806 */     this.valorRetenidoIva = valorRetenidoIva;
/*  485:     */   }
/*  486:     */   
/*  487:     */   public BigDecimal getValorRetenidoFuente()
/*  488:     */   {
/*  489: 815 */     return this.valorRetenidoFuente;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public void setValorRetenidoFuente(BigDecimal valorRetenidoFuente)
/*  493:     */   {
/*  494: 825 */     this.valorRetenidoFuente = valorRetenidoFuente;
/*  495:     */   }
/*  496:     */   
/*  497:     */   public long getNumeroComprobantes()
/*  498:     */   {
/*  499: 834 */     return this.numeroComprobantes;
/*  500:     */   }
/*  501:     */   
/*  502:     */   public void setNumeroComprobantes(long numeroComprobantes)
/*  503:     */   {
/*  504: 844 */     this.numeroComprobantes = numeroComprobantes;
/*  505:     */   }
/*  506:     */   
/*  507:     */   public String getCodigoTipoIdentificacion()
/*  508:     */   {
/*  509: 853 */     return this.codigoTipoIdentificacion;
/*  510:     */   }
/*  511:     */   
/*  512:     */   public void setCodigoTipoIdentificacion(String codigoTipoIdentificacion)
/*  513:     */   {
/*  514: 863 */     this.codigoTipoIdentificacion = codigoTipoIdentificacion;
/*  515:     */   }
/*  516:     */   
/*  517:     */   public boolean isIndicadorSaldoInicial()
/*  518:     */   {
/*  519: 872 */     return this.indicadorSaldoInicial;
/*  520:     */   }
/*  521:     */   
/*  522:     */   public void setIndicadorSaldoInicial(boolean indicadorSaldoInicial)
/*  523:     */   {
/*  524: 882 */     this.indicadorSaldoInicial = indicadorSaldoInicial;
/*  525:     */   }
/*  526:     */   
/*  527:     */   public String getCodigoTipoComprobanteSRI()
/*  528:     */   {
/*  529: 891 */     return this.codigoTipoComprobanteSRI;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public void setCodigoTipoComprobanteSRI(String codigoTipoComprobanteSRI)
/*  533:     */   {
/*  534: 901 */     this.codigoTipoComprobanteSRI = codigoTipoComprobanteSRI;
/*  535:     */   }
/*  536:     */   
/*  537:     */   public String getAutorizacion()
/*  538:     */   {
/*  539: 910 */     return this.autorizacion;
/*  540:     */   }
/*  541:     */   
/*  542:     */   public void setAutorizacion(String autorizacion)
/*  543:     */   {
/*  544: 920 */     this.autorizacion = autorizacion;
/*  545:     */   }
/*  546:     */   
/*  547:     */   public String getTraNumeroNuevo()
/*  548:     */   {
/*  549: 929 */     return this.traNumeroNuevo;
/*  550:     */   }
/*  551:     */   
/*  552:     */   public void setTraNumeroNuevo(String traNumeroNuevo)
/*  553:     */   {
/*  554: 939 */     this.traNumeroNuevo = traNumeroNuevo;
/*  555:     */   }
/*  556:     */   
/*  557:     */   public int getId()
/*  558:     */   {
/*  559: 949 */     return getIdFacturaclienteSRI();
/*  560:     */   }
/*  561:     */   
/*  562:     */   public Integer getAnioRefrendo()
/*  563:     */   {
/*  564: 958 */     return this.anioRefrendo;
/*  565:     */   }
/*  566:     */   
/*  567:     */   public void setAnioRefrendo(Integer anioRefrendo)
/*  568:     */   {
/*  569: 968 */     this.anioRefrendo = anioRefrendo;
/*  570:     */   }
/*  571:     */   
/*  572:     */   public String getRegimenRefrendo()
/*  573:     */   {
/*  574: 977 */     return this.regimenRefrendo;
/*  575:     */   }
/*  576:     */   
/*  577:     */   public void setRegimenRefrendo(String regimenRefrendo)
/*  578:     */   {
/*  579: 987 */     this.regimenRefrendo = regimenRefrendo;
/*  580:     */   }
/*  581:     */   
/*  582:     */   public String getCorrelativoRefrendo()
/*  583:     */   {
/*  584: 996 */     return this.correlativoRefrendo;
/*  585:     */   }
/*  586:     */   
/*  587:     */   public void setCorrelativoRefrendo(String correlativoRefrendo)
/*  588:     */   {
/*  589:1006 */     this.correlativoRefrendo = correlativoRefrendo;
/*  590:     */   }
/*  591:     */   
/*  592:     */   public String getDocumentoTransporteRefrendo()
/*  593:     */   {
/*  594:1015 */     return this.documentoTransporteRefrendo;
/*  595:     */   }
/*  596:     */   
/*  597:     */   public void setDocumentoTransporteRefrendo(String documentoTransporteRefrendo)
/*  598:     */   {
/*  599:1025 */     this.documentoTransporteRefrendo = documentoTransporteRefrendo;
/*  600:     */   }
/*  601:     */   
/*  602:     */   public BigDecimal getValorFobRefrendo()
/*  603:     */   {
/*  604:1034 */     return this.valorFobRefrendo;
/*  605:     */   }
/*  606:     */   
/*  607:     */   public void setValorFobRefrendo(BigDecimal valorFobRefrendo)
/*  608:     */   {
/*  609:1044 */     this.valorFobRefrendo = valorFobRefrendo;
/*  610:     */   }
/*  611:     */   
/*  612:     */   public int getTraRefrendo()
/*  613:     */   {
/*  614:1053 */     return this.traRefrendo;
/*  615:     */   }
/*  616:     */   
/*  617:     */   public void setTraRefrendo(int traRefrendo)
/*  618:     */   {
/*  619:1063 */     this.traRefrendo = traRefrendo;
/*  620:     */   }
/*  621:     */   
/*  622:     */   public BigDecimal getValorFobComprobanteRefrendo()
/*  623:     */   {
/*  624:1072 */     return this.valorFobComprobanteRefrendo;
/*  625:     */   }
/*  626:     */   
/*  627:     */   public void setValorFobComprobanteRefrendo(BigDecimal valorFobComprobanteRefrendo)
/*  628:     */   {
/*  629:1082 */     this.valorFobComprobanteRefrendo = valorFobComprobanteRefrendo;
/*  630:     */   }
/*  631:     */   
/*  632:     */   public RefrendoEnum getRefrendo()
/*  633:     */   {
/*  634:1091 */     return this.refrendo;
/*  635:     */   }
/*  636:     */   
/*  637:     */   public void setRefrendo(RefrendoEnum refrendo)
/*  638:     */   {
/*  639:1101 */     this.refrendo = refrendo;
/*  640:     */   }
/*  641:     */   
/*  642:     */   public String getDistritoRefrendo()
/*  643:     */   {
/*  644:1110 */     return this.distritoRefrendo;
/*  645:     */   }
/*  646:     */   
/*  647:     */   public void setDistritoRefrendo(String distritoRefrendo)
/*  648:     */   {
/*  649:1120 */     this.distritoRefrendo = distritoRefrendo;
/*  650:     */   }
/*  651:     */   
/*  652:     */   public Date getFechaTransaccion()
/*  653:     */   {
/*  654:1129 */     return this.fechaTransaccion;
/*  655:     */   }
/*  656:     */   
/*  657:     */   public void setFechaTransaccion(Date fechaTransaccion)
/*  658:     */   {
/*  659:1139 */     this.fechaTransaccion = fechaTransaccion;
/*  660:     */   }
/*  661:     */   
/*  662:     */   public BigDecimal getMontoIce()
/*  663:     */   {
/*  664:1148 */     return this.montoIce;
/*  665:     */   }
/*  666:     */   
/*  667:     */   public void setMontoIce(BigDecimal montoIce)
/*  668:     */   {
/*  669:1158 */     this.montoIce = montoIce;
/*  670:     */   }
/*  671:     */   
/*  672:     */   public FacturaProveedorSRI getFacturaProveedorSRI()
/*  673:     */   {
/*  674:1165 */     return this.facturaProveedorSRI;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public void setFacturaProveedorSRI(FacturaProveedorSRI facturaProveedorSRI)
/*  678:     */   {
/*  679:1173 */     this.facturaProveedorSRI = facturaProveedorSRI;
/*  680:     */   }
/*  681:     */   
/*  682:     */   public DocumentoBase getDocumentoBase()
/*  683:     */   {
/*  684:1182 */     return this.documentoBase;
/*  685:     */   }
/*  686:     */   
/*  687:     */   public void setDocumentoBase(DocumentoBase documentoBase)
/*  688:     */   {
/*  689:1192 */     this.documentoBase = documentoBase;
/*  690:     */   }
/*  691:     */   
/*  692:     */   public Date getFechaAutorizacion()
/*  693:     */   {
/*  694:1196 */     return this.fechaAutorizacion;
/*  695:     */   }
/*  696:     */   
/*  697:     */   public void setFechaAutorizacion(Date fechaAutorizacion)
/*  698:     */   {
/*  699:1200 */     this.fechaAutorizacion = fechaAutorizacion;
/*  700:     */   }
/*  701:     */   
/*  702:     */   public Date getFechaCaducidad()
/*  703:     */   {
/*  704:1204 */     return this.fechaCaducidad;
/*  705:     */   }
/*  706:     */   
/*  707:     */   public void setFechaCaducidad(Date fechaCaducidad)
/*  708:     */   {
/*  709:1208 */     this.fechaCaducidad = fechaCaducidad;
/*  710:     */   }
/*  711:     */   
/*  712:     */   public int getAmbiente()
/*  713:     */   {
/*  714:1212 */     return this.ambiente;
/*  715:     */   }
/*  716:     */   
/*  717:     */   public void setAmbiente(int ambiente)
/*  718:     */   {
/*  719:1216 */     this.ambiente = ambiente;
/*  720:     */   }
/*  721:     */   
/*  722:     */   public int getTipoEmision()
/*  723:     */   {
/*  724:1220 */     return this.tipoEmision;
/*  725:     */   }
/*  726:     */   
/*  727:     */   public void setTipoEmision(int tipoEmision)
/*  728:     */   {
/*  729:1224 */     this.tipoEmision = tipoEmision;
/*  730:     */   }
/*  731:     */   
/*  732:     */   public boolean isIndicadorDocumentoElectronico()
/*  733:     */   {
/*  734:1228 */     return this.indicadorDocumentoElectronico;
/*  735:     */   }
/*  736:     */   
/*  737:     */   public void setIndicadorDocumentoElectronico(boolean indicadorDocumentoElectronico)
/*  738:     */   {
/*  739:1232 */     this.indicadorDocumentoElectronico = indicadorDocumentoElectronico;
/*  740:     */   }
/*  741:     */   
/*  742:     */   public String getClaveAcceso()
/*  743:     */   {
/*  744:1236 */     return this.claveAcceso;
/*  745:     */   }
/*  746:     */   
/*  747:     */   public void setClaveAcceso(String claveAcceso)
/*  748:     */   {
/*  749:1240 */     this.claveAcceso = claveAcceso;
/*  750:     */   }
/*  751:     */   
/*  752:     */   public String getDireccionMatriz()
/*  753:     */   {
/*  754:1244 */     return this.direccionMatriz;
/*  755:     */   }
/*  756:     */   
/*  757:     */   public void setDireccionMatriz(String direccionMatriz)
/*  758:     */   {
/*  759:1248 */     this.direccionMatriz = direccionMatriz;
/*  760:     */   }
/*  761:     */   
/*  762:     */   public String getDireccionSucursal()
/*  763:     */   {
/*  764:1252 */     return this.direccionSucursal;
/*  765:     */   }
/*  766:     */   
/*  767:     */   public void setDireccionSucursal(String direccionSucursal)
/*  768:     */   {
/*  769:1256 */     this.direccionSucursal = direccionSucursal;
/*  770:     */   }
/*  771:     */   
/*  772:     */   public String getEmail()
/*  773:     */   {
/*  774:1260 */     return this.email;
/*  775:     */   }
/*  776:     */   
/*  777:     */   public void setEmail(String email)
/*  778:     */   {
/*  779:1264 */     this.email = email;
/*  780:     */   }
/*  781:     */   
/*  782:     */   public String getCodigoUnico()
/*  783:     */   {
/*  784:1268 */     return this.codigoUnico;
/*  785:     */   }
/*  786:     */   
/*  787:     */   public void setCodigoUnico(String codigoUnico)
/*  788:     */   {
/*  789:1272 */     this.codigoUnico = codigoUnico;
/*  790:     */   }
/*  791:     */   
/*  792:     */   public Boolean getGenerarDocumentoElectronico()
/*  793:     */   {
/*  794:1279 */     return Boolean.valueOf(this.generarDocumentoElectronico == null ? true : this.generarDocumentoElectronico.booleanValue());
/*  795:     */   }
/*  796:     */   
/*  797:     */   public void setGenerarDocumentoElectronico(Boolean generarDocumentoElectronico)
/*  798:     */   {
/*  799:1287 */     this.generarDocumentoElectronico = generarDocumentoElectronico;
/*  800:     */   }
/*  801:     */   
/*  802:     */   public String getMensajeSRI()
/*  803:     */   {
/*  804:1291 */     return this.mensajeSRI;
/*  805:     */   }
/*  806:     */   
/*  807:     */   public void setMensajeSRI(String mensajeSRI)
/*  808:     */   {
/*  809:1295 */     this.mensajeSRI = mensajeSRI;
/*  810:     */   }
/*  811:     */   
/*  812:     */   public BigDecimal getMontoIRBPNR()
/*  813:     */   {
/*  814:1299 */     return this.montoIRBPNR;
/*  815:     */   }
/*  816:     */   
/*  817:     */   public void setMontoIRBPNR(BigDecimal montoIRBPNR)
/*  818:     */   {
/*  819:1303 */     this.montoIRBPNR = montoIRBPNR;
/*  820:     */   }
/*  821:     */   
/*  822:     */   public String getExportParaisoFiscal()
/*  823:     */   {
/*  824:1307 */     return this.exportParaisoFiscal;
/*  825:     */   }
/*  826:     */   
/*  827:     */   public void setExportParaisoFiscal(String exportParaisoFiscal)
/*  828:     */   {
/*  829:1311 */     this.exportParaisoFiscal = exportParaisoFiscal;
/*  830:     */   }
/*  831:     */   
/*  832:     */   public String getIdCliente()
/*  833:     */   {
/*  834:1315 */     return this.idCliente;
/*  835:     */   }
/*  836:     */   
/*  837:     */   public void setIdCliente(String idCliente)
/*  838:     */   {
/*  839:1319 */     this.idCliente = idCliente;
/*  840:     */   }
/*  841:     */   
/*  842:     */   public String getTpIdClienteEx()
/*  843:     */   {
/*  844:1323 */     return this.tpIdClienteEx;
/*  845:     */   }
/*  846:     */   
/*  847:     */   public void setTpIdClienteEx(String tpIdClienteEx)
/*  848:     */   {
/*  849:1327 */     this.tpIdClienteEx = tpIdClienteEx;
/*  850:     */   }
/*  851:     */   
/*  852:     */   public String getPaisEfecExp()
/*  853:     */   {
/*  854:1331 */     return this.paisEfecExp;
/*  855:     */   }
/*  856:     */   
/*  857:     */   public void setPaisEfecExp(String paisEfecExp)
/*  858:     */   {
/*  859:1335 */     this.paisEfecExp = paisEfecExp;
/*  860:     */   }
/*  861:     */   
/*  862:     */   public BigDecimal getTotalSubsidio()
/*  863:     */   {
/*  864:1339 */     return this.totalSubsidio;
/*  865:     */   }
/*  866:     */   
/*  867:     */   public void setTotalSubsidio(BigDecimal totalSubsidio)
/*  868:     */   {
/*  869:1343 */     this.totalSubsidio = totalSubsidio;
/*  870:     */   }
/*  871:     */   
/*  872:     */   public String getCodigoFormaPagoSRI()
/*  873:     */   {
/*  874:1347 */     return this.codigoFormaPagoSRI;
/*  875:     */   }
/*  876:     */   
/*  877:     */   public void setCodigoFormaPagoSRI(String codigoFormaPagoSRI)
/*  878:     */   {
/*  879:1351 */     this.codigoFormaPagoSRI = codigoFormaPagoSRI;
/*  880:     */   }
/*  881:     */   
/*  882:     */   public HashMap<String, String> getListaCodigoFormaPagoSri()
/*  883:     */   {
/*  884:1358 */     return this.listaCodigoFormaPagoSri;
/*  885:     */   }
/*  886:     */   
/*  887:     */   public void setListaCodigoFormaPagoSri(HashMap<String, String> listaCodigoFormaPagoSri)
/*  888:     */   {
/*  889:1366 */     this.listaCodigoFormaPagoSri = listaCodigoFormaPagoSri;
/*  890:     */   }
/*  891:     */   
/*  892:     */   public BigDecimal getValorImpuestoExportacion()
/*  893:     */   {
/*  894:1370 */     return this.valorImpuestoExportacion;
/*  895:     */   }
/*  896:     */   
/*  897:     */   public void setValorImpuestoExportacion(BigDecimal valorImpuestoExportacion)
/*  898:     */   {
/*  899:1374 */     this.valorImpuestoExportacion = valorImpuestoExportacion;
/*  900:     */   }
/*  901:     */   
/*  902:     */   public boolean isIngresoExteriorGraboImpuestos()
/*  903:     */   {
/*  904:1378 */     return this.ingresoExteriorGraboImpuestos;
/*  905:     */   }
/*  906:     */   
/*  907:     */   public void setIngresoExteriorGraboImpuestos(boolean ingresoExteriorGraboImpuestos)
/*  908:     */   {
/*  909:1382 */     this.ingresoExteriorGraboImpuestos = ingresoExteriorGraboImpuestos;
/*  910:     */   }
/*  911:     */   
/*  912:     */   public String getTipoIngresoExterior()
/*  913:     */   {
/*  914:1386 */     return this.tipoIngresoExterior;
/*  915:     */   }
/*  916:     */   
/*  917:     */   public void setTipoIngresoExterior(String tipoIngresoExterior)
/*  918:     */   {
/*  919:1390 */     this.tipoIngresoExterior = tipoIngresoExterior;
/*  920:     */   }
/*  921:     */   
/*  922:     */   public BigDecimal getDescuentoImpuesto()
/*  923:     */   {
/*  924:1394 */     return this.descuentoImpuesto;
/*  925:     */   }
/*  926:     */   
/*  927:     */   public void setDescuentoImpuesto(BigDecimal descuentoImpuesto)
/*  928:     */   {
/*  929:1398 */     this.descuentoImpuesto = descuentoImpuesto;
/*  930:     */   }
/*  931:     */   
/*  932:     */   public EstadoDocumentoElectronico getEstadoDocumentoElectronico()
/*  933:     */   {
/*  934:1402 */     return this.estadoDocumentoElectronico;
/*  935:     */   }
/*  936:     */   
/*  937:     */   public void setEstadoDocumentoElectronico(EstadoDocumentoElectronico estadoDocumentoElectronico)
/*  938:     */   {
/*  939:1406 */     this.estadoDocumentoElectronico = estadoDocumentoElectronico;
/*  940:     */   }
/*  941:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.FacturaClienteSRI
 * JD-Core Version:    0.7.0.1
 */