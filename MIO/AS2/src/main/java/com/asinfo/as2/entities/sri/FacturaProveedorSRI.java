/*    1:     */ package com.asinfo.as2.entities.sri;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
/*    4:     */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*    5:     */ import com.asinfo.as2.entities.CompraCajaChica;
/*    6:     */ import com.asinfo.as2.entities.Documento;
/*    7:     */ import com.asinfo.as2.entities.EntidadBase;
/*    8:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*    9:     */ import com.asinfo.as2.entities.Pago;
/*   10:     */ import com.asinfo.as2.entities.Secuencia;
/*   11:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   12:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   13:     */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*   14:     */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*   15:     */ import com.asinfo.as2.utils.validacion.email.Emails;
/*   16:     */ import java.math.BigDecimal;
/*   17:     */ import java.util.ArrayList;
/*   18:     */ import java.util.Date;
/*   19:     */ import java.util.List;
/*   20:     */ import javax.persistence.Column;
/*   21:     */ import javax.persistence.Entity;
/*   22:     */ import javax.persistence.EnumType;
/*   23:     */ import javax.persistence.Enumerated;
/*   24:     */ import javax.persistence.FetchType;
/*   25:     */ import javax.persistence.GeneratedValue;
/*   26:     */ import javax.persistence.GenerationType;
/*   27:     */ import javax.persistence.Id;
/*   28:     */ import javax.persistence.JoinColumn;
/*   29:     */ import javax.persistence.ManyToOne;
/*   30:     */ import javax.persistence.OneToMany;
/*   31:     */ import javax.persistence.OneToOne;
/*   32:     */ import javax.persistence.Table;
/*   33:     */ import javax.persistence.TableGenerator;
/*   34:     */ import javax.persistence.Temporal;
/*   35:     */ import javax.persistence.TemporalType;
/*   36:     */ import javax.persistence.Transient;
/*   37:     */ import javax.validation.constraints.Digits;
/*   38:     */ import javax.validation.constraints.Min;
/*   39:     */ import javax.validation.constraints.NotNull;
/*   40:     */ import javax.validation.constraints.Pattern;
/*   41:     */ import javax.validation.constraints.Size;
/*   42:     */ 
/*   43:     */ @Entity
/*   44:     */ @Table(name="factura_proveedorSRI", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"identificacion_proveedor", "id_tipo_comprobante", "establecimiento", "punto_emision", "autorizacion", "numero"})}, indexes={@javax.persistence.Index(columnList="id_factura_proveedor"), @javax.persistence.Index(columnList="id_pago"), @javax.persistence.Index(columnList="numero_retencion")})
/*   45:     */ public class FacturaProveedorSRI
/*   46:     */   extends EntidadBase
/*   47:     */ {
/*   48:     */   private static final long serialVersionUID = 1L;
/*   49:     */   @Id
/*   50:     */   @TableGenerator(name="factura_proveedorSRI", initialValue=0, allocationSize=50)
/*   51:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="factura_proveedorSRI")
/*   52:     */   @Column(name="id_factura_proveedorSRI")
/*   53:     */   private int idFacturaProveedorSRI;
/*   54:     */   @Column(name="id_organizacion", nullable=false)
/*   55:     */   private int idOrganizacion;
/*   56:     */   @Column(name="id_sucursal", nullable=false)
/*   57:     */   private int idSucursal;
/*   58:     */   @Column(name="estado", nullable=false)
/*   59:     */   @Enumerated(EnumType.ORDINAL)
/*   60:     */   private Estado estado;
/*   61:     */   @OneToOne(fetch=FetchType.LAZY)
/*   62:     */   @JoinColumn(name="id_factura_proveedor", nullable=true)
/*   63:     */   private FacturaProveedor facturaProveedor;
/*   64:     */   @OneToOne(mappedBy="facturaProveedorSRI", fetch=FetchType.LAZY)
/*   65:     */   private FacturaClienteSRI facturaClienteSRI;
/*   66:     */   @OneToOne(fetch=FetchType.LAZY)
/*   67:     */   @JoinColumn(name="id_pago", nullable=true)
/*   68:     */   private Pago pago;
/*   69:     */   @OneToOne(fetch=FetchType.LAZY)
/*   70:     */   @JoinColumn(name="id_compra_caja_chica", nullable=true)
/*   71:     */   private CompraCajaChica compraCajaChica;
/*   72:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   73:     */   @JoinColumn(name="id_credito_tributarioSRI", nullable=true)
/*   74:     */   private CreditoTributarioSRI creditoTributarioSRI;
/*   75:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   76:     */   @JoinColumn(name="id_tipo_identificacion", nullable=true)
/*   77:     */   private TipoIdentificacion tipoIdentificacion;
/*   78:     */   @Column(name="identificacion_proveedor", length=20, nullable=true)
/*   79:     */   @Size(min=1, max=20)
/*   80:     */   private String identificacionProveedor;
/*   81:     */   @Column(name="nombre_proveedor", length=200, nullable=true)
/*   82:     */   @Size(min=1, max=200)
/*   83:     */   private String nombreProveedor;
/*   84:     */   @Column(name="direccion_proveedor", length=300, nullable=true)
/*   85:     */   @Size(max=300)
/*   86:     */   private String direccionProveedor;
/*   87:     */   @Column(name="telefono_proveedor", length=13, nullable=true)
/*   88:     */   @Size(max=13)
/*   89:     */   private String telefonoProveedor;
/*   90:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   91:     */   @JoinColumn(name="id_tipo_comprobante", nullable=true)
/*   92:     */   private TipoComprobanteSRI tipoComprobanteSRI;
/*   93:     */   @Temporal(TemporalType.DATE)
/*   94:     */   @Column(name="fecha_registro", nullable=true)
/*   95:     */   private Date fechaRegistro;
/*   96:     */   @Column(name="establecimiento", length=3, nullable=false)
/*   97:     */   @NotNull
/*   98:     */   @Size(min=3, max=3)
/*   99:     */   private String establecimiento;
/*  100:     */   @Column(name="punto_emision", length=3, nullable=false)
/*  101:     */   @NotNull
/*  102:     */   @Size(min=3, max=3)
/*  103:     */   private String puntoEmision;
/*  104:     */   @Column(name="numero", length=20, nullable=false)
/*  105:     */   @NotNull
/*  106:     */   @Size(min=1, max=20)
/*  107:     */   @Pattern(regexp="(\\d+)", message="El numero de factura debe estar compuesto solo por numeros")
/*  108:     */   private String numero;
/*  109:     */   @Temporal(TemporalType.DATE)
/*  110:     */   @Column(name="fecha_emision", nullable=true)
/*  111:     */   private Date fechaEmision;
/*  112:     */   @Column(name="autorizacion", length=50, nullable=true)
/*  113:     */   @Size(min=10, max=50)
/*  114:     */   private String autorizacion;
/*  115:     */   @Column(name="monto_ice", precision=12, scale=2)
/*  116:     */   @Digits(integer=12, fraction=2)
/*  117:     */   @Min(0L)
/*  118: 160 */   private BigDecimal montoIce = BigDecimal.ZERO;
/*  119:     */   @Column(name="monto_iva", precision=12, scale=2)
/*  120:     */   @Digits(integer=12, fraction=2)
/*  121:     */   @Min(0L)
/*  122: 165 */   private BigDecimal montoIva = BigDecimal.ZERO;
/*  123:     */   @Column(name="base_imponible_tarifa_cero", precision=12, scale=2)
/*  124:     */   @Digits(integer=12, fraction=2)
/*  125:     */   @Min(0L)
/*  126: 170 */   private BigDecimal baseImponibleTarifaCero = BigDecimal.ZERO;
/*  127:     */   @Column(name="base_imponible_diferente_cero", precision=12, scale=2)
/*  128:     */   @Digits(integer=12, fraction=2)
/*  129:     */   @Min(0L)
/*  130: 175 */   private BigDecimal baseImponibleDiferenteCero = BigDecimal.ZERO;
/*  131:     */   @Column(name="base_imponible_no_objeto_iva", precision=12, scale=2)
/*  132:     */   @Digits(integer=12, fraction=2)
/*  133:     */   @Min(0L)
/*  134: 180 */   private BigDecimal baseImponibleNoObjetoIva = BigDecimal.ZERO;
/*  135:     */   @Column(name="valor_total_factura", precision=12, scale=2)
/*  136:     */   @Digits(integer=12, fraction=2)
/*  137:     */   @Min(0L)
/*  138: 185 */   private BigDecimal valorTotalFactura = BigDecimal.ZERO;
/*  139:     */   @Column(name="total_valor_retenido", nullable=true, precision=12, scale=2)
/*  140:     */   private BigDecimal totalValorRetenido;
/*  141:     */   @Column(name="establecimiento_retencion", length=3, nullable=true)
/*  142:     */   @Size(max=3)
/*  143:     */   private String establecimientoRetencion;
/*  144:     */   @Column(name="punto_emision_retencion", length=3, nullable=true)
/*  145:     */   @Size(max=3)
/*  146:     */   private String puntoEmisionRetencion;
/*  147:     */   @Column(name="numero_retencion", length=20, nullable=true)
/*  148:     */   @Size(max=20)
/*  149:     */   private String numeroRetencion;
/*  150:     */   @Column(name="autorizacion_retencion", length=50, nullable=true)
/*  151:     */   @Size(max=50)
/*  152:     */   private String autorizacionRetencion;
/*  153:     */   @Column(name="fecha_emision_retencion", nullable=true)
/*  154:     */   private Date fechaEmisionRetencion;
/*  155:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  156:     */   @JoinColumn(name="id_tipo_comprobante_modificado", nullable=true)
/*  157:     */   private TipoComprobanteSRI documentoModificado;
/*  158:     */   @Column(name="establecimiento_modificado", length=3, nullable=true)
/*  159:     */   @Size(max=3)
/*  160:     */   private String establecimientoModificado;
/*  161:     */   @Column(name="punto_emision_modificado", length=3, nullable=true)
/*  162:     */   @Size(max=3)
/*  163:     */   private String puntoEmisionModificado;
/*  164:     */   @Column(name="numero_modificado", length=20, nullable=true)
/*  165:     */   @Size(max=20)
/*  166:     */   private String numeroModificado;
/*  167:     */   @Column(name="autorizacion_modificado", length=50, nullable=true)
/*  168:     */   @Size(max=50)
/*  169:     */   private String autorizacionModificado;
/*  170:     */   @Column(name="provincia", length=50, nullable=true)
/*  171:     */   private String provincia;
/*  172:     */   @Column(name="ciudad", length=50, nullable=true)
/*  173:     */   private String ciudad;
/*  174:     */   @Column(name="indicador_saldo_inicial", nullable=false)
/*  175:     */   private boolean indicadorSaldoInicial;
/*  176:     */   @Column(name="indicador_retencion_emitida", nullable=false)
/*  177:     */   private boolean indicadorRetencionEmitida;
/*  178:     */   @Column(name="indicador_retencion_asumida", nullable=false)
/*  179:     */   private boolean indicadorRetencionAsumida;
/*  180:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="facturaProveedorSRI")
/*  181: 247 */   private List<DetalleFacturaProveedorSRI> listaDetalleFacturaProveedorSRI = new ArrayList();
/*  182:     */   @Column(name="indicador_factura_electronica", nullable=false)
/*  183:     */   private boolean indicadorFacturaElectronica;
/*  184:     */   @Column(name="tipo_empresa", nullable=false)
/*  185:     */   @Enumerated(EnumType.ORDINAL)
/*  186:     */   private TipoEmpresa tipoEmpresa;
/*  187:     */   @Column(name="indicador_parte_relacionada", nullable=false)
/*  188:     */   private boolean indicadorParteRelacionada;
/*  189:     */   @Emails
/*  190:     */   @Size(max=500)
/*  191:     */   @Column(name="email", nullable=true, length=500)
/*  192:     */   private String email;
/*  193:     */   @Column(name="codigo_unico", length=50, nullable=true)
/*  194:     */   private String codigoUnico;
/*  195:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  196:     */   @JoinColumn(name="id_documento", nullable=true)
/*  197:     */   private Documento documento;
/*  198:     */   @Transient
/*  199:     */   private String patronAutorizacion;
/*  200:     */   @Transient
/*  201:     */   private String numeroComprobante;
/*  202:     */   @Transient
/*  203: 277 */   private BigDecimal valorRetenidoIVA = BigDecimal.ZERO;
/*  204:     */   @Transient
/*  205: 279 */   private BigDecimal valorRetenidoFuente = BigDecimal.ZERO;
/*  206:     */   @Transient
/*  207: 281 */   private BigDecimal baseImponible = BigDecimal.ZERO;
/*  208:     */   @Transient
/*  209:     */   private AutorizacionProveedorSRI autorizacionProveedorSRI;
/*  210:     */   @Transient
/*  211:     */   private Secuencia secuenciaLiquidacionCompra;
/*  212:     */   @Transient
/*  213: 287 */   private boolean indicadorValidar = true;
/*  214:     */   @Transient
/*  215:     */   private boolean traCorregirDatos;
/*  216:     */   @Transient
/*  217:     */   private boolean emitirRetencionIva0;
/*  218:     */   @Transient
/*  219:     */   private DocumentoElectronico documentoElectronico;
/*  220:     */   @Column(name="ambiente", nullable=false)
/*  221:     */   private int ambiente;
/*  222:     */   @Column(name="indicador_documento_electronico", nullable=false)
/*  223:     */   private boolean indicadorDocumentoElectronico;
/*  224:     */   @Column(name="tipo_emision", nullable=false)
/*  225:     */   private int tipoEmision;
/*  226:     */   @Column(name="clave_acceso", length=50)
/*  227:     */   private String claveAcceso;
/*  228:     */   @Column(name="direccion_matriz", length=200)
/*  229:     */   private String direccionMatriz;
/*  230:     */   @Column(name="direccion_sucursal", length=200)
/*  231:     */   private String direccionSucursal;
/*  232:     */   @Temporal(TemporalType.TIMESTAMP)
/*  233:     */   @Column(name="fecha_autorizacion", nullable=true)
/*  234:     */   private Date fechaAutorizacion;
/*  235:     */   @Column(name="mensaje_sri", length=5000, nullable=true)
/*  236:     */   @Size(max=5000)
/*  237:     */   private String mensajeSRI;
/*  238:     */   @Column(name="estado_documento_electronico", nullable=true)
/*  239:     */   @Enumerated(EnumType.ORDINAL)
/*  240:     */   private EstadoDocumentoElectronico estadoDocumentoElectronico;
/*  241:     */   @Temporal(TemporalType.DATE)
/*  242:     */   @Column(name="fecha_pago_dividendo")
/*  243:     */   private Date fechaPagoDividendo;
/*  244:     */   @Column(name="ir_asociado_dividendo")
/*  245: 333 */   private BigDecimal irAsociadoDividendo = BigDecimal.ZERO;
/*  246:     */   @Column(name="anio_generacion_utilidades")
/*  247:     */   private Integer anioGeneracionUtilidades;
/*  248:     */   @Column(name="codigo_forma_pago_sri", length=10, nullable=true)
/*  249:     */   private String codigoFormaPagoSRI;
/*  250:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="facturaProveedorSRI")
/*  251: 347 */   private List<ReembolsoGastos> listaReembolsoGastos = new ArrayList();
/*  252:     */   
/*  253:     */   public FacturaProveedorSRI() {}
/*  254:     */   
/*  255:     */   public FacturaProveedorSRI(String nombreProveedor, String numeroRetencion, String establecimiento, String puntoEmision, String numero)
/*  256:     */   {
/*  257: 360 */     this.nombreProveedor = nombreProveedor;
/*  258: 361 */     this.establecimiento = establecimiento;
/*  259: 362 */     this.puntoEmision = puntoEmision;
/*  260: 363 */     this.numero = numero;
/*  261: 364 */     this.numeroRetencion = numeroRetencion;
/*  262:     */   }
/*  263:     */   
/*  264:     */   public int getId()
/*  265:     */   {
/*  266: 374 */     return this.idFacturaProveedorSRI;
/*  267:     */   }
/*  268:     */   
/*  269:     */   public int getIdFacturaProveedorSRI()
/*  270:     */   {
/*  271: 383 */     return this.idFacturaProveedorSRI;
/*  272:     */   }
/*  273:     */   
/*  274:     */   public void setIdFacturaProveedorSRI(int idFacturaProveedorSRI)
/*  275:     */   {
/*  276: 393 */     this.idFacturaProveedorSRI = idFacturaProveedorSRI;
/*  277:     */   }
/*  278:     */   
/*  279:     */   public int getIdOrganizacion()
/*  280:     */   {
/*  281: 402 */     return this.idOrganizacion;
/*  282:     */   }
/*  283:     */   
/*  284:     */   public void setIdOrganizacion(int idOrganizacion)
/*  285:     */   {
/*  286: 412 */     this.idOrganizacion = idOrganizacion;
/*  287:     */   }
/*  288:     */   
/*  289:     */   public int getIdSucursal()
/*  290:     */   {
/*  291: 421 */     return this.idSucursal;
/*  292:     */   }
/*  293:     */   
/*  294:     */   public void setIdSucursal(int idSucursal)
/*  295:     */   {
/*  296: 431 */     this.idSucursal = idSucursal;
/*  297:     */   }
/*  298:     */   
/*  299:     */   public FacturaProveedor getFacturaProveedor()
/*  300:     */   {
/*  301: 440 */     return this.facturaProveedor;
/*  302:     */   }
/*  303:     */   
/*  304:     */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/*  305:     */   {
/*  306: 450 */     this.facturaProveedor = facturaProveedor;
/*  307:     */   }
/*  308:     */   
/*  309:     */   public CreditoTributarioSRI getCreditoTributarioSRI()
/*  310:     */   {
/*  311: 459 */     return this.creditoTributarioSRI;
/*  312:     */   }
/*  313:     */   
/*  314:     */   public void setCreditoTributarioSRI(CreditoTributarioSRI creditoTributarioSRI)
/*  315:     */   {
/*  316: 469 */     this.creditoTributarioSRI = creditoTributarioSRI;
/*  317:     */   }
/*  318:     */   
/*  319:     */   public TipoIdentificacion getTipoIdentificacion()
/*  320:     */   {
/*  321: 478 */     return this.tipoIdentificacion;
/*  322:     */   }
/*  323:     */   
/*  324:     */   public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/*  325:     */   {
/*  326: 488 */     this.tipoIdentificacion = tipoIdentificacion;
/*  327:     */   }
/*  328:     */   
/*  329:     */   public String getIdentificacionProveedor()
/*  330:     */   {
/*  331: 497 */     return this.identificacionProveedor;
/*  332:     */   }
/*  333:     */   
/*  334:     */   public void setIdentificacionProveedor(String identificacionProveedor)
/*  335:     */   {
/*  336: 507 */     this.identificacionProveedor = identificacionProveedor;
/*  337:     */   }
/*  338:     */   
/*  339:     */   public TipoComprobanteSRI getTipoComprobanteSRI()
/*  340:     */   {
/*  341: 516 */     return this.tipoComprobanteSRI;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public void setTipoComprobanteSRI(TipoComprobanteSRI tipoComprobanteSRI)
/*  345:     */   {
/*  346: 526 */     this.tipoComprobanteSRI = tipoComprobanteSRI;
/*  347:     */   }
/*  348:     */   
/*  349:     */   public Date getFechaRegistro()
/*  350:     */   {
/*  351: 535 */     return this.fechaRegistro;
/*  352:     */   }
/*  353:     */   
/*  354:     */   public void setFechaRegistro(Date fechaRegistro)
/*  355:     */   {
/*  356: 545 */     this.fechaRegistro = fechaRegistro;
/*  357:     */   }
/*  358:     */   
/*  359:     */   public String getEstablecimiento()
/*  360:     */   {
/*  361: 554 */     return this.establecimiento;
/*  362:     */   }
/*  363:     */   
/*  364:     */   public void setEstablecimiento(String establecimiento)
/*  365:     */   {
/*  366: 564 */     this.establecimiento = establecimiento;
/*  367:     */   }
/*  368:     */   
/*  369:     */   public String getPuntoEmision()
/*  370:     */   {
/*  371: 573 */     return this.puntoEmision;
/*  372:     */   }
/*  373:     */   
/*  374:     */   public void setPuntoEmision(String puntoEmision)
/*  375:     */   {
/*  376: 583 */     this.puntoEmision = puntoEmision;
/*  377:     */   }
/*  378:     */   
/*  379:     */   public String getNumero()
/*  380:     */   {
/*  381: 592 */     return this.numero;
/*  382:     */   }
/*  383:     */   
/*  384:     */   public void setNumero(String numero)
/*  385:     */   {
/*  386: 602 */     this.numero = numero;
/*  387:     */   }
/*  388:     */   
/*  389:     */   public Date getFechaEmision()
/*  390:     */   {
/*  391: 611 */     return this.fechaEmision;
/*  392:     */   }
/*  393:     */   
/*  394:     */   public void setFechaEmision(Date fechaEmision)
/*  395:     */   {
/*  396: 621 */     this.fechaEmision = fechaEmision;
/*  397:     */   }
/*  398:     */   
/*  399:     */   public String getAutorizacion()
/*  400:     */   {
/*  401: 630 */     return this.autorizacion;
/*  402:     */   }
/*  403:     */   
/*  404:     */   public void setAutorizacion(String autorizacion)
/*  405:     */   {
/*  406: 640 */     this.autorizacion = autorizacion;
/*  407:     */   }
/*  408:     */   
/*  409:     */   public BigDecimal getMontoIce()
/*  410:     */   {
/*  411: 649 */     return this.montoIce;
/*  412:     */   }
/*  413:     */   
/*  414:     */   public void setMontoIce(BigDecimal montoIce)
/*  415:     */   {
/*  416: 659 */     this.montoIce = montoIce;
/*  417:     */   }
/*  418:     */   
/*  419:     */   public BigDecimal getMontoIva()
/*  420:     */   {
/*  421: 668 */     return this.montoIva;
/*  422:     */   }
/*  423:     */   
/*  424:     */   public void setMontoIva(BigDecimal montoIva)
/*  425:     */   {
/*  426: 678 */     this.montoIva = montoIva;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public String getEstablecimientoRetencion()
/*  430:     */   {
/*  431: 687 */     return this.establecimientoRetencion;
/*  432:     */   }
/*  433:     */   
/*  434:     */   public void setEstablecimientoRetencion(String establecimientoRetencion)
/*  435:     */   {
/*  436: 697 */     this.establecimientoRetencion = establecimientoRetencion;
/*  437:     */   }
/*  438:     */   
/*  439:     */   public String getPuntoEmisionRetencion()
/*  440:     */   {
/*  441: 706 */     return this.puntoEmisionRetencion;
/*  442:     */   }
/*  443:     */   
/*  444:     */   public void setPuntoEmisionRetencion(String puntoEmisionRetencion)
/*  445:     */   {
/*  446: 716 */     this.puntoEmisionRetencion = puntoEmisionRetencion;
/*  447:     */   }
/*  448:     */   
/*  449:     */   public String getNumeroRetencion()
/*  450:     */   {
/*  451: 725 */     return this.numeroRetencion;
/*  452:     */   }
/*  453:     */   
/*  454:     */   public void setNumeroRetencion(String numeroRetencion)
/*  455:     */   {
/*  456: 735 */     this.numeroRetencion = numeroRetencion;
/*  457:     */   }
/*  458:     */   
/*  459:     */   public String getAutorizacionRetencion()
/*  460:     */   {
/*  461: 744 */     return this.autorizacionRetencion;
/*  462:     */   }
/*  463:     */   
/*  464:     */   public void setAutorizacionRetencion(String autorizacionRetencion)
/*  465:     */   {
/*  466: 754 */     this.autorizacionRetencion = autorizacionRetencion;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public Date getFechaEmisionRetencion()
/*  470:     */   {
/*  471: 763 */     return this.fechaEmisionRetencion;
/*  472:     */   }
/*  473:     */   
/*  474:     */   public void setFechaEmisionRetencion(Date fechaEmisionRetencion)
/*  475:     */   {
/*  476: 773 */     this.fechaEmisionRetencion = fechaEmisionRetencion;
/*  477:     */   }
/*  478:     */   
/*  479:     */   public TipoComprobanteSRI getDocumentoModificado()
/*  480:     */   {
/*  481: 782 */     return this.documentoModificado;
/*  482:     */   }
/*  483:     */   
/*  484:     */   public void setDocumentoModificado(TipoComprobanteSRI documentoModificado)
/*  485:     */   {
/*  486: 792 */     this.documentoModificado = documentoModificado;
/*  487:     */   }
/*  488:     */   
/*  489:     */   public String getEstablecimientoModificado()
/*  490:     */   {
/*  491: 801 */     return this.establecimientoModificado;
/*  492:     */   }
/*  493:     */   
/*  494:     */   public void setEstablecimientoModificado(String establecimientoModificado)
/*  495:     */   {
/*  496: 811 */     this.establecimientoModificado = establecimientoModificado;
/*  497:     */   }
/*  498:     */   
/*  499:     */   public String getPuntoEmisionModificado()
/*  500:     */   {
/*  501: 820 */     return this.puntoEmisionModificado;
/*  502:     */   }
/*  503:     */   
/*  504:     */   public void setPuntoEmisionModificado(String puntoEmisionModificado)
/*  505:     */   {
/*  506: 830 */     this.puntoEmisionModificado = puntoEmisionModificado;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public String getNumeroModificado()
/*  510:     */   {
/*  511: 839 */     return this.numeroModificado;
/*  512:     */   }
/*  513:     */   
/*  514:     */   public void setNumeroModificado(String numeroModificado)
/*  515:     */   {
/*  516: 849 */     this.numeroModificado = numeroModificado;
/*  517:     */   }
/*  518:     */   
/*  519:     */   public String getAutorizacionModificado()
/*  520:     */   {
/*  521: 858 */     return this.autorizacionModificado;
/*  522:     */   }
/*  523:     */   
/*  524:     */   public void setAutorizacionModificado(String autorizacionModificado)
/*  525:     */   {
/*  526: 868 */     this.autorizacionModificado = autorizacionModificado;
/*  527:     */   }
/*  528:     */   
/*  529:     */   public List<DetalleFacturaProveedorSRI> getListaDetalleFacturaProveedorSRI()
/*  530:     */   {
/*  531: 877 */     return this.listaDetalleFacturaProveedorSRI;
/*  532:     */   }
/*  533:     */   
/*  534:     */   public List<DetalleFacturaProveedorSRI> getListaDetalleFacturaProveedorSRINoEliminados()
/*  535:     */   {
/*  536: 881 */     List<DetalleFacturaProveedorSRI> listaNoEliminados = new ArrayList();
/*  537: 882 */     if ((this.listaDetalleFacturaProveedorSRI != null) && (this.listaDetalleFacturaProveedorSRI.size() > 0)) {
/*  538: 883 */       for (DetalleFacturaProveedorSRI dfpsri : this.listaDetalleFacturaProveedorSRI) {
/*  539: 884 */         if (!dfpsri.isEliminado()) {
/*  540: 885 */           listaNoEliminados.add(dfpsri);
/*  541:     */         }
/*  542:     */       }
/*  543:     */     }
/*  544: 890 */     return listaNoEliminados;
/*  545:     */   }
/*  546:     */   
/*  547:     */   public void setListaDetalleFacturaProveedorSRI(List<DetalleFacturaProveedorSRI> listaDetalleFacturaProveedorSRI)
/*  548:     */   {
/*  549: 900 */     this.listaDetalleFacturaProveedorSRI = listaDetalleFacturaProveedorSRI;
/*  550:     */   }
/*  551:     */   
/*  552:     */   public BigDecimal getBaseImponibleTarifaCero()
/*  553:     */   {
/*  554: 909 */     return this.baseImponibleTarifaCero;
/*  555:     */   }
/*  556:     */   
/*  557:     */   public void setBaseImponibleTarifaCero(BigDecimal baseImponibleTarifaCero)
/*  558:     */   {
/*  559: 919 */     this.baseImponibleTarifaCero = baseImponibleTarifaCero;
/*  560:     */   }
/*  561:     */   
/*  562:     */   public BigDecimal getBaseImponibleDiferenteCero()
/*  563:     */   {
/*  564: 928 */     return this.baseImponibleDiferenteCero;
/*  565:     */   }
/*  566:     */   
/*  567:     */   public void setBaseImponibleDiferenteCero(BigDecimal baseImponibleDiferenteCero)
/*  568:     */   {
/*  569: 938 */     this.baseImponibleDiferenteCero = baseImponibleDiferenteCero;
/*  570:     */   }
/*  571:     */   
/*  572:     */   public BigDecimal getBaseImponibleNoObjetoIva()
/*  573:     */   {
/*  574: 947 */     return this.baseImponibleNoObjetoIva;
/*  575:     */   }
/*  576:     */   
/*  577:     */   public void setBaseImponibleNoObjetoIva(BigDecimal baseImponibleNoObjetoIva)
/*  578:     */   {
/*  579: 957 */     this.baseImponibleNoObjetoIva = baseImponibleNoObjetoIva;
/*  580:     */   }
/*  581:     */   
/*  582:     */   public CompraCajaChica getCompraCajaChica()
/*  583:     */   {
/*  584: 961 */     return this.compraCajaChica;
/*  585:     */   }
/*  586:     */   
/*  587:     */   public void setCompraCajaChica(CompraCajaChica compraCajaChica)
/*  588:     */   {
/*  589: 965 */     this.compraCajaChica = compraCajaChica;
/*  590:     */   }
/*  591:     */   
/*  592:     */   public String getNombreProveedor()
/*  593:     */   {
/*  594: 969 */     return this.nombreProveedor;
/*  595:     */   }
/*  596:     */   
/*  597:     */   public void setNombreProveedor(String nombreProveedor)
/*  598:     */   {
/*  599: 973 */     this.nombreProveedor = nombreProveedor;
/*  600:     */   }
/*  601:     */   
/*  602:     */   public String getDireccionProveedor()
/*  603:     */   {
/*  604: 977 */     return this.direccionProveedor;
/*  605:     */   }
/*  606:     */   
/*  607:     */   public void setDireccionProveedor(String direccionProveedor)
/*  608:     */   {
/*  609: 981 */     this.direccionProveedor = direccionProveedor;
/*  610:     */   }
/*  611:     */   
/*  612:     */   public String getNumeroComprobante()
/*  613:     */   {
/*  614: 985 */     this.numeroComprobante = (this.establecimiento + "-" + this.puntoEmision + "-" + this.numero);
/*  615: 986 */     return this.numeroComprobante;
/*  616:     */   }
/*  617:     */   
/*  618:     */   public void setNumeroComprobante(String numeroComprobante)
/*  619:     */   {
/*  620: 990 */     this.numeroComprobante = numeroComprobante;
/*  621:     */   }
/*  622:     */   
/*  623:     */   public BigDecimal getValorRetenidoIVA()
/*  624:     */   {
/*  625: 994 */     this.valorRetenidoIVA = BigDecimal.ZERO;
/*  626: 995 */     for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : this.listaDetalleFacturaProveedorSRI) {
/*  627: 996 */       if ((!detalleFacturaProveedorSRI.isEliminado()) && (detalleFacturaProveedorSRI.getTipoConceptoRetencion().equals(TipoConceptoRetencion.IVA))) {
/*  628: 997 */         this.valorRetenidoIVA = this.valorRetenidoIVA.add(detalleFacturaProveedorSRI.getValorRetencion());
/*  629:     */       }
/*  630:     */     }
/*  631:1001 */     return this.valorRetenidoIVA;
/*  632:     */   }
/*  633:     */   
/*  634:     */   public void setValorRetenidoIVA(BigDecimal valorRetenidoIVA)
/*  635:     */   {
/*  636:1005 */     this.valorRetenidoIVA = valorRetenidoIVA;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public BigDecimal getValorRetenidoFuente()
/*  640:     */   {
/*  641:1009 */     this.valorRetenidoFuente = BigDecimal.ZERO;
/*  642:1010 */     for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : this.listaDetalleFacturaProveedorSRI) {
/*  643:1011 */       if ((!detalleFacturaProveedorSRI.isEliminado()) && 
/*  644:1012 */         (detalleFacturaProveedorSRI.getTipoConceptoRetencion().equals(TipoConceptoRetencion.FUENTE))) {
/*  645:1013 */         this.valorRetenidoFuente = this.valorRetenidoFuente.add(detalleFacturaProveedorSRI.getValorRetencion());
/*  646:     */       }
/*  647:     */     }
/*  648:1017 */     return this.valorRetenidoFuente;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public void setValorRetenidoFuente(BigDecimal valorRetenidoFuente)
/*  652:     */   {
/*  653:1021 */     this.valorRetenidoFuente = valorRetenidoFuente;
/*  654:     */   }
/*  655:     */   
/*  656:     */   public boolean isGenerado()
/*  657:     */   {
/*  658:1031 */     return getFacturaProveedor() != null;
/*  659:     */   }
/*  660:     */   
/*  661:     */   public String getNumeroFactura()
/*  662:     */   {
/*  663:1035 */     return this.establecimiento + "-" + this.puntoEmision + "-" + this.numero.trim();
/*  664:     */   }
/*  665:     */   
/*  666:     */   public BigDecimal getBaseImponible()
/*  667:     */   {
/*  668:1040 */     this.baseImponible = this.baseImponibleDiferenteCero.add(this.baseImponibleTarifaCero).add(this.baseImponibleNoObjetoIva);
/*  669:1041 */     return this.baseImponible;
/*  670:     */   }
/*  671:     */   
/*  672:     */   public void setBaseImponible(BigDecimal baseImponible)
/*  673:     */   {
/*  674:1045 */     this.baseImponible = baseImponible;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public Pago getPago()
/*  678:     */   {
/*  679:1049 */     return this.pago;
/*  680:     */   }
/*  681:     */   
/*  682:     */   public void setPago(Pago pago)
/*  683:     */   {
/*  684:1053 */     this.pago = pago;
/*  685:     */   }
/*  686:     */   
/*  687:     */   public Estado getEstado()
/*  688:     */   {
/*  689:1062 */     return this.estado;
/*  690:     */   }
/*  691:     */   
/*  692:     */   public void setEstado(Estado estado)
/*  693:     */   {
/*  694:1072 */     this.estado = estado;
/*  695:     */   }
/*  696:     */   
/*  697:     */   public boolean isIndicadorSaldoInicial()
/*  698:     */   {
/*  699:1081 */     return this.indicadorSaldoInicial;
/*  700:     */   }
/*  701:     */   
/*  702:     */   public void setIndicadorSaldoInicial(boolean indicadorSaldoInicial)
/*  703:     */   {
/*  704:1091 */     this.indicadorSaldoInicial = indicadorSaldoInicial;
/*  705:     */   }
/*  706:     */   
/*  707:     */   public AutorizacionProveedorSRI getAutorizacionProveedorSRI()
/*  708:     */   {
/*  709:1100 */     if (this.autorizacionProveedorSRI == null)
/*  710:     */     {
/*  711:1101 */       this.autorizacionProveedorSRI = new AutorizacionProveedorSRI();
/*  712:1102 */       this.autorizacionProveedorSRI.setAutorizacion(this.autorizacion);
/*  713:1103 */       this.autorizacionProveedorSRI.setEstablecimiento(this.establecimiento);
/*  714:1104 */       this.autorizacionProveedorSRI.setPuntoEmision(this.puntoEmision);
/*  715:1105 */       this.autorizacionProveedorSRI.setFechaHasta(this.fechaEmision);
/*  716:1106 */       this.autorizacionProveedorSRI.setIndicadorFacturaElectronica(this.indicadorFacturaElectronica);
/*  717:     */     }
/*  718:1108 */     return this.autorizacionProveedorSRI;
/*  719:     */   }
/*  720:     */   
/*  721:     */   public void setAutorizacionProveedorSRI(AutorizacionProveedorSRI autorizacionProveedorSRI)
/*  722:     */   {
/*  723:1118 */     this.autorizacionProveedorSRI = autorizacionProveedorSRI;
/*  724:     */   }
/*  725:     */   
/*  726:     */   public boolean isIndicadorRetencionEmitida()
/*  727:     */   {
/*  728:1127 */     return this.indicadorRetencionEmitida;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public void setIndicadorRetencionEmitida(boolean indicadorRetencionEmitida)
/*  732:     */   {
/*  733:1137 */     this.indicadorRetencionEmitida = indicadorRetencionEmitida;
/*  734:     */   }
/*  735:     */   
/*  736:     */   public Secuencia getSecuenciaLiquidacionCompra()
/*  737:     */   {
/*  738:1141 */     return this.secuenciaLiquidacionCompra;
/*  739:     */   }
/*  740:     */   
/*  741:     */   public void setSecuenciaLiquidacionCompra(Secuencia secuenciaLiquidacionCompra)
/*  742:     */   {
/*  743:1145 */     this.secuenciaLiquidacionCompra = secuenciaLiquidacionCompra;
/*  744:     */   }
/*  745:     */   
/*  746:     */   public boolean isIndicadorLiquidacionCompra()
/*  747:     */   {
/*  748:1149 */     return ("03".equals(getTipoComprobanteSRI().getCodigo())) || (isIndicadorReembolso());
/*  749:     */   }
/*  750:     */   
/*  751:     */   public boolean isIndicadorReembolso()
/*  752:     */   {
/*  753:1153 */     return (getTipoComprobanteSRI() != null) && ("41".equals(getTipoComprobanteSRI().getCodigo()));
/*  754:     */   }
/*  755:     */   
/*  756:     */   public boolean isIndicadorDividendo()
/*  757:     */   {
/*  758:1157 */     boolean indicadorDividendo = false;
/*  759:1158 */     if ((getTipoComprobanteSRI() != null) && ("19".equals(getTipoComprobanteSRI().getCodigo()))) {
/*  760:1159 */       for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : this.listaDetalleFacturaProveedorSRI) {
/*  761:1160 */         if (detalleFacturaProveedorSRI.getConceptoRetencionSRI().getCodigo() == "327") {
/*  762:1161 */           indicadorDividendo = true;
/*  763:     */         }
/*  764:     */       }
/*  765:     */     }
/*  766:1166 */     return indicadorDividendo;
/*  767:     */   }
/*  768:     */   
/*  769:     */   public boolean isIndicadorFacturaElectronica()
/*  770:     */   {
/*  771:1175 */     return this.indicadorFacturaElectronica;
/*  772:     */   }
/*  773:     */   
/*  774:     */   public void setIndicadorFacturaElectronica(boolean indicadorFacturaElectronica)
/*  775:     */   {
/*  776:1185 */     this.indicadorFacturaElectronica = indicadorFacturaElectronica;
/*  777:     */   }
/*  778:     */   
/*  779:     */   public String getPatronAutorizacion()
/*  780:     */   {
/*  781:1194 */     this.patronAutorizacion = "";
/*  782:1195 */     if (this.indicadorFacturaElectronica) {
/*  783:1196 */       for (int i = 0; i < 37; i++) {
/*  784:1197 */         this.patronAutorizacion += "9";
/*  785:     */       }
/*  786:     */     } else {
/*  787:1200 */       for (int i = 0; i < 10; i++) {
/*  788:1201 */         this.patronAutorizacion += "9";
/*  789:     */       }
/*  790:     */     }
/*  791:1204 */     return this.patronAutorizacion;
/*  792:     */   }
/*  793:     */   
/*  794:     */   public void setPatronAutorizacion(String patronAutorizacion)
/*  795:     */   {
/*  796:1214 */     this.patronAutorizacion = patronAutorizacion;
/*  797:     */   }
/*  798:     */   
/*  799:     */   public Documento getDocumento()
/*  800:     */   {
/*  801:1223 */     return this.documento;
/*  802:     */   }
/*  803:     */   
/*  804:     */   public void setDocumento(Documento documento)
/*  805:     */   {
/*  806:1233 */     this.documento = documento;
/*  807:     */   }
/*  808:     */   
/*  809:     */   public FacturaClienteSRI getFacturaClienteSRI()
/*  810:     */   {
/*  811:1240 */     return this.facturaClienteSRI;
/*  812:     */   }
/*  813:     */   
/*  814:     */   public void setFacturaClienteSRI(FacturaClienteSRI facturaClienteSRI)
/*  815:     */   {
/*  816:1248 */     this.facturaClienteSRI = facturaClienteSRI;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public String getTelefonoProveedor()
/*  820:     */   {
/*  821:1257 */     return this.telefonoProveedor;
/*  822:     */   }
/*  823:     */   
/*  824:     */   public void setTelefonoProveedor(String telefonoProveedor)
/*  825:     */   {
/*  826:1267 */     this.telefonoProveedor = telefonoProveedor;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public boolean isIndicadorRetencionAsumida()
/*  830:     */   {
/*  831:1274 */     return this.indicadorRetencionAsumida;
/*  832:     */   }
/*  833:     */   
/*  834:     */   public void setIndicadorRetencionAsumida(boolean indicadorRetencionAsumida)
/*  835:     */   {
/*  836:1282 */     this.indicadorRetencionAsumida = indicadorRetencionAsumida;
/*  837:     */   }
/*  838:     */   
/*  839:     */   public String getProvincia()
/*  840:     */   {
/*  841:1291 */     return this.provincia;
/*  842:     */   }
/*  843:     */   
/*  844:     */   public void setProvincia(String provincia)
/*  845:     */   {
/*  846:1301 */     this.provincia = provincia;
/*  847:     */   }
/*  848:     */   
/*  849:     */   public String getCiudad()
/*  850:     */   {
/*  851:1310 */     return this.ciudad;
/*  852:     */   }
/*  853:     */   
/*  854:     */   public void setCiudad(String ciudad)
/*  855:     */   {
/*  856:1320 */     this.ciudad = ciudad;
/*  857:     */   }
/*  858:     */   
/*  859:     */   public BigDecimal getTotalValorRetenido()
/*  860:     */   {
/*  861:1329 */     return this.totalValorRetenido;
/*  862:     */   }
/*  863:     */   
/*  864:     */   public void setTotalValorRetenido(BigDecimal totalValorRetenido)
/*  865:     */   {
/*  866:1339 */     this.totalValorRetenido = totalValorRetenido;
/*  867:     */   }
/*  868:     */   
/*  869:     */   public boolean isTraCorregirDatos()
/*  870:     */   {
/*  871:1348 */     return this.traCorregirDatos;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void setTraCorregirDatos(boolean traCorregirDatos)
/*  875:     */   {
/*  876:1358 */     this.traCorregirDatos = traCorregirDatos;
/*  877:     */   }
/*  878:     */   
/*  879:     */   public TipoEmpresa getTipoEmpresa()
/*  880:     */   {
/*  881:1362 */     return this.tipoEmpresa;
/*  882:     */   }
/*  883:     */   
/*  884:     */   public void setTipoEmpresa(TipoEmpresa tipoEmpresa)
/*  885:     */   {
/*  886:1366 */     this.tipoEmpresa = tipoEmpresa;
/*  887:     */   }
/*  888:     */   
/*  889:     */   public boolean isIndicadorParteRelacionada()
/*  890:     */   {
/*  891:1370 */     return this.indicadorParteRelacionada;
/*  892:     */   }
/*  893:     */   
/*  894:     */   public void setIndicadorParteRelacionada(boolean indicadorParteRelacionada)
/*  895:     */   {
/*  896:1374 */     this.indicadorParteRelacionada = indicadorParteRelacionada;
/*  897:     */   }
/*  898:     */   
/*  899:     */   public String getEmail()
/*  900:     */   {
/*  901:1378 */     return this.email;
/*  902:     */   }
/*  903:     */   
/*  904:     */   public void setEmail(String email)
/*  905:     */   {
/*  906:1382 */     this.email = email;
/*  907:     */   }
/*  908:     */   
/*  909:     */   public DocumentoElectronico getDocumentoElectronico()
/*  910:     */   {
/*  911:1386 */     return this.documentoElectronico;
/*  912:     */   }
/*  913:     */   
/*  914:     */   public void setDocumentoElectronico(DocumentoElectronico documentoElectronico)
/*  915:     */   {
/*  916:1390 */     this.documentoElectronico = documentoElectronico;
/*  917:     */   }
/*  918:     */   
/*  919:     */   public int getAmbiente()
/*  920:     */   {
/*  921:1394 */     return this.ambiente;
/*  922:     */   }
/*  923:     */   
/*  924:     */   public void setAmbiente(int ambiente)
/*  925:     */   {
/*  926:1398 */     this.ambiente = ambiente;
/*  927:     */   }
/*  928:     */   
/*  929:     */   public int getTipoEmision()
/*  930:     */   {
/*  931:1402 */     return this.tipoEmision;
/*  932:     */   }
/*  933:     */   
/*  934:     */   public void setTipoEmision(int tipoEmision)
/*  935:     */   {
/*  936:1406 */     this.tipoEmision = tipoEmision;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public String getClaveAcceso()
/*  940:     */   {
/*  941:1410 */     return this.claveAcceso;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public void setClaveAcceso(String claveAcceso)
/*  945:     */   {
/*  946:1414 */     this.claveAcceso = claveAcceso;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public String getDireccionMatriz()
/*  950:     */   {
/*  951:1418 */     return this.direccionMatriz;
/*  952:     */   }
/*  953:     */   
/*  954:     */   public void setDireccionMatriz(String direccionMatriz)
/*  955:     */   {
/*  956:1422 */     this.direccionMatriz = direccionMatriz;
/*  957:     */   }
/*  958:     */   
/*  959:     */   public String getDireccionSucursal()
/*  960:     */   {
/*  961:1426 */     return this.direccionSucursal;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public void setDireccionSucursal(String direccionSucursal)
/*  965:     */   {
/*  966:1430 */     this.direccionSucursal = direccionSucursal;
/*  967:     */   }
/*  968:     */   
/*  969:     */   public Date getFechaAutorizacion()
/*  970:     */   {
/*  971:1434 */     return this.fechaAutorizacion;
/*  972:     */   }
/*  973:     */   
/*  974:     */   public void setFechaAutorizacion(Date fechaAutorizacion)
/*  975:     */   {
/*  976:1438 */     this.fechaAutorizacion = fechaAutorizacion;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public boolean isIndicadorDocumentoElectronico()
/*  980:     */   {
/*  981:1442 */     return this.indicadorDocumentoElectronico;
/*  982:     */   }
/*  983:     */   
/*  984:     */   public void setIndicadorDocumentoElectronico(boolean indicadorDocumentoElectronico)
/*  985:     */   {
/*  986:1446 */     this.indicadorDocumentoElectronico = indicadorDocumentoElectronico;
/*  987:     */   }
/*  988:     */   
/*  989:     */   public String getCodigoUnico()
/*  990:     */   {
/*  991:1450 */     return this.codigoUnico;
/*  992:     */   }
/*  993:     */   
/*  994:     */   public void setCodigoUnico(String codigoUnico)
/*  995:     */   {
/*  996:1454 */     this.codigoUnico = codigoUnico;
/*  997:     */   }
/*  998:     */   
/*  999:     */   public String getMensajeSRI()
/* 1000:     */   {
/* 1001:1458 */     return this.mensajeSRI;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public void setMensajeSRI(String mensajeSRI)
/* 1005:     */   {
/* 1006:1462 */     this.mensajeSRI = mensajeSRI;
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public BigDecimal getValorTotalFactura()
/* 1010:     */   {
/* 1011:1466 */     return this.valorTotalFactura;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public void setValorTotalFactura(BigDecimal valorTotalFactura)
/* 1015:     */   {
/* 1016:1470 */     this.valorTotalFactura = valorTotalFactura;
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public boolean isIndicadorValidar()
/* 1020:     */   {
/* 1021:1474 */     return this.indicadorValidar;
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public void setIndicadorValidar(boolean indicadorValidar)
/* 1025:     */   {
/* 1026:1478 */     this.indicadorValidar = indicadorValidar;
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public List<ReembolsoGastos> getListaReembolsoGastos()
/* 1030:     */   {
/* 1031:1482 */     return this.listaReembolsoGastos;
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public void setListaReembolsoGastos(List<ReembolsoGastos> listaReembolsoGastos)
/* 1035:     */   {
/* 1036:1486 */     this.listaReembolsoGastos = listaReembolsoGastos;
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public Date getFechaPagoDividendo()
/* 1040:     */   {
/* 1041:1490 */     return this.fechaPagoDividendo;
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   public void setFechaPagoDividendo(Date fechaPagoDividendo)
/* 1045:     */   {
/* 1046:1494 */     this.fechaPagoDividendo = fechaPagoDividendo;
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   public BigDecimal getIrAsociadoDividendo()
/* 1050:     */   {
/* 1051:1498 */     return this.irAsociadoDividendo;
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public void setIrAsociadoDividendo(BigDecimal irAsociadoDividendo)
/* 1055:     */   {
/* 1056:1502 */     this.irAsociadoDividendo = irAsociadoDividendo;
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public Integer getAnioGeneracionUtilidades()
/* 1060:     */   {
/* 1061:1506 */     return this.anioGeneracionUtilidades;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public void setAnioGeneracionUtilidades(Integer anioGeneracionUtilidades)
/* 1065:     */   {
/* 1066:1510 */     this.anioGeneracionUtilidades = anioGeneracionUtilidades;
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   public boolean isEmitirRetencionIva0()
/* 1070:     */   {
/* 1071:1514 */     return this.emitirRetencionIva0;
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   public void setEmitirRetencionIva0(boolean emitirRetencionIva0)
/* 1075:     */   {
/* 1076:1518 */     this.emitirRetencionIva0 = emitirRetencionIva0;
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   public String getCodigoFormaPagoSRI()
/* 1080:     */   {
/* 1081:1525 */     return this.codigoFormaPagoSRI;
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public void setCodigoFormaPagoSRI(String codigoFormaPagoSRI)
/* 1085:     */   {
/* 1086:1533 */     this.codigoFormaPagoSRI = codigoFormaPagoSRI;
/* 1087:     */   }
/* 1088:     */   
/* 1089:     */   public EstadoDocumentoElectronico getEstadoDocumentoElectronico()
/* 1090:     */   {
/* 1091:1537 */     return this.estadoDocumentoElectronico;
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   public void setEstadoDocumentoElectronico(EstadoDocumentoElectronico estadoDocumentoElectronico)
/* 1095:     */   {
/* 1096:1541 */     this.estadoDocumentoElectronico = estadoDocumentoElectronico;
/* 1097:     */   }
/* 1098:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.FacturaProveedorSRI
 * JD-Core Version:    0.7.0.1
 */