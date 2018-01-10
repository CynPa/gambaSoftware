/*    1:     */ package com.asinfo.as2.entities;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*    4:     */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*    5:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*    6:     */ import com.asinfo.as2.enumeraciones.Estado;
/*    7:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*    8:     */ import java.io.Serializable;
/*    9:     */ import java.math.BigDecimal;
/*   10:     */ import java.util.ArrayList;
/*   11:     */ import java.util.Date;
/*   12:     */ import java.util.List;
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
/*   23:     */ import javax.persistence.OneToMany;
/*   24:     */ import javax.persistence.OneToOne;
/*   25:     */ import javax.persistence.Table;
/*   26:     */ import javax.persistence.TableGenerator;
/*   27:     */ import javax.persistence.Temporal;
/*   28:     */ import javax.persistence.TemporalType;
/*   29:     */ import javax.persistence.Transient;
/*   30:     */ import javax.validation.constraints.Digits;
/*   31:     */ import javax.validation.constraints.Min;
/*   32:     */ import javax.validation.constraints.NotNull;
/*   33:     */ import javax.validation.constraints.Size;
/*   34:     */ 
/*   35:     */ @Entity
/*   36:     */ @Table(name="factura_proveedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_documento", "numero"})}, indexes={@javax.persistence.Index(columnList="id_empresa"), @javax.persistence.Index(columnList="id_factura_proveedor_padre"), @javax.persistence.Index(columnList="numero"), @javax.persistence.Index(columnList="fecha"), @javax.persistence.Index(columnList="id_asiento"), @javax.persistence.Index(columnList="id_documento"), @javax.persistence.Index(columnList="id_organizacion, id_recepcion_proveedor, estado, fecha")})
/*   37:     */ public class FacturaProveedor
/*   38:     */   extends EntidadBase
/*   39:     */   implements Serializable
/*   40:     */ {
/*   41:     */   private static final long serialVersionUID = 1L;
/*   42:     */   @Id
/*   43:     */   @TableGenerator(name="factura_proveedor", initialValue=0, allocationSize=50)
/*   44:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="factura_proveedor")
/*   45:     */   @Column(name="id_factura_proveedor")
/*   46:     */   private int idFacturaProveedor;
/*   47:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   48:     */   @JoinColumn(name="id_sucursal", nullable=true)
/*   49:     */   private Sucursal sucursal;
/*   50:     */   @Column(name="id_organizacion", nullable=false)
/*   51:     */   @NotNull
/*   52:     */   private int idOrganizacion;
/*   53:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   54:     */   @JoinColumn(name="id_documento", nullable=true)
/*   55:     */   @NotNull
/*   56:     */   private Documento documento;
/*   57:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   58:     */   @JoinColumn(name="id_empresa", nullable=true)
/*   59:     */   @NotNull
/*   60:     */   private Empresa empresa;
/*   61:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   62:     */   @JoinColumn(name="id_condicion_pago", nullable=true)
/*   63:     */   private CondicionPago condicionPago;
/*   64:     */   @OneToOne(fetch=FetchType.LAZY)
/*   65:     */   @JoinColumn(name="id_asiento", nullable=true)
/*   66:     */   private Asiento asiento;
/*   67:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   68:     */   @JoinColumn(name="id_direccion_empresa", nullable=false)
/*   69:     */   @NotNull
/*   70:     */   private DireccionEmpresa direccionEmpresa;
/*   71:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   72:     */   @JoinColumn(name="id_motivo_nota_credito_proveedor", nullable=true)
/*   73:     */   private MotivoNotaCreditoProveedor motivoNotaCreditoProveedor;
/*   74:     */   @Column(name="numero", nullable=false, length=20)
/*   75:     */   @NotNull
/*   76:     */   @Size(max=20)
/*   77:     */   private String numero;
/*   78:     */   @Temporal(TemporalType.DATE)
/*   79:     */   @Column(name="fecha", nullable=false)
/*   80:     */   @NotNull
/*   81:     */   private Date fecha;
/*   82:     */   @Temporal(TemporalType.DATE)
/*   83:     */   @Column(name="fecha_recepcion", nullable=true)
/*   84:     */   private Date fechaRecepcion;
/*   85:     */   @Temporal(TemporalType.DATE)
/*   86:     */   @Column(name="fecha_contabilizacion", nullable=true)
/*   87:     */   private Date fechaContabilizacion;
/*   88:     */   @Column(name="total", nullable=false, precision=12, scale=2)
/*   89:     */   @NotNull
/*   90:     */   @Digits(integer=12, fraction=2)
/*   91:     */   @Min(0L)
/*   92: 119 */   private BigDecimal total = BigDecimal.ZERO;
/*   93:     */   @Transient
/*   94:     */   @Min(0L)
/*   95: 125 */   private BigDecimal totalFactura = BigDecimal.ZERO;
/*   96:     */   @Column(name="impuesto", nullable=false, precision=12, scale=2)
/*   97:     */   @NotNull
/*   98:     */   @Digits(integer=12, fraction=2)
/*   99:     */   @Min(0L)
/*  100: 129 */   private BigDecimal impuesto = BigDecimal.ZERO;
/*  101:     */   @Column(name="descuento_impuesto", nullable=false, precision=12, scale=2)
/*  102:     */   @NotNull
/*  103:     */   @Digits(integer=12, fraction=2)
/*  104:     */   @Min(0L)
/*  105: 135 */   private BigDecimal descuentoImpuesto = BigDecimal.ZERO;
/*  106:     */   @Column(name="descuento", nullable=true, precision=12, scale=2)
/*  107:     */   @Digits(integer=12, fraction=2)
/*  108:     */   @Min(0L)
/*  109: 141 */   private BigDecimal descuento = BigDecimal.ZERO;
/*  110:     */   @Column(name="numero_cuotas", nullable=false, precision=12, scale=2)
/*  111:     */   @NotNull
/*  112:     */   @Digits(integer=12, fraction=2)
/*  113:     */   @Min(1L)
/*  114:     */   private int numeroCuotas;
/*  115:     */   @Column(name="descripcion", nullable=true, length=500)
/*  116:     */   @Size(max=500)
/*  117: 152 */   private String descripcion = "";
/*  118:     */   @Column(name="descripcion2", nullable=true, length=500)
/*  119:     */   @Size(max=500)
/*  120:     */   private String descripcion2;
/*  121:     */   @Column(name="estado", nullable=false)
/*  122:     */   @Enumerated(EnumType.ORDINAL)
/*  123:     */   private Estado estado;
/*  124:     */   @Column(name="direccion_factura", nullable=true)
/*  125:     */   private String direccionFactura;
/*  126:     */   @Column(name="pdf", nullable=true)
/*  127:     */   @Size(max=50)
/*  128:     */   private String pdf;
/*  129:     */   @Column(name="indicador_saldo_inicial", nullable=false)
/*  130:     */   private boolean indicadorSaldoInicial;
/*  131:     */   @Column(name="indicador_credito_tributario", nullable=false)
/*  132: 174 */   private boolean indicadorCreditoTributario = true;
/*  133:     */   @NotNull
/*  134:     */   @Column(name="bono", nullable=false, precision=12, scale=2)
/*  135:     */   @Digits(integer=12, fraction=2)
/*  136:     */   @Min(0L)
/*  137: 177 */   private BigDecimal bono = BigDecimal.ZERO;
/*  138:     */   @OneToOne(mappedBy="facturaProveedor", fetch=FetchType.LAZY)
/*  139:     */   private FacturaProveedorSRI facturaProveedorSRI;
/*  140:     */   @OneToOne(mappedBy="notaCreditoProveedor", fetch=FetchType.LAZY)
/*  141:     */   private AnticipoProveedor anticipoProveedor;
/*  142:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  143:     */   @JoinColumn(name="id_factura_proveedor_padre", nullable=true)
/*  144:     */   private FacturaProveedor facturaProveedorPadre;
/*  145:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  146:     */   @JoinColumn(name="id_recepcion_proveedor", nullable=true)
/*  147:     */   private RecepcionProveedor recepcionProveedor;
/*  148:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="facturaProveedor")
/*  149: 197 */   private List<DetalleFacturaProveedor> listaDetalleFacturaProveedor = new ArrayList();
/*  150:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="facturaProveedor")
/*  151: 200 */   private List<CuentaPorPagar> listaCuentaPorPagar = new ArrayList();
/*  152:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  153:     */   @JoinColumn(name="id_tipo_operacion", nullable=true)
/*  154:     */   private TipoOperacion tipoOperacion;
/*  155:     */   @Column(name="indicador_nota_credito_debito", nullable=false)
/*  156:     */   private boolean indicadorNotaCreditoDebito;
/*  157:     */   @OneToOne(mappedBy="facturaProveedor", fetch=FetchType.LAZY)
/*  158:     */   private FacturaProveedorImportacion facturaProveedorImportacion;
/*  159:     */   @Column(name="indicador_gasto_importacion", nullable=false)
/*  160:     */   private boolean indicadorGastoImportacion;
/*  161:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="facturaProveedor")
/*  162: 218 */   private List<DetalleFacturaProveedorImportacion> listaDetalleFacturaProveedorImportacion = new ArrayList();
/*  163:     */   @Column(name="referencia1", length=200, nullable=false)
/*  164:     */   @Size(max=200)
/*  165: 221 */   private String referencia1 = "";
/*  166:     */   @Column(name="referencia2", length=200, nullable=false)
/*  167:     */   @Size(max=200)
/*  168: 225 */   private String referencia2 = "";
/*  169:     */   @Column(name="referencia3", length=200, nullable=false)
/*  170:     */   @Size(max=200)
/*  171: 230 */   private String referencia3 = "";
/*  172:     */   @Column(name="valor_referencia1", nullable=true, length=200)
/*  173:     */   @Digits(integer=12, fraction=3)
/*  174:     */   private BigDecimal valorReferencia1;
/*  175:     */   @Column(name="valor_referencia2", nullable=true, length=200)
/*  176:     */   @Digits(integer=12, fraction=2)
/*  177:     */   private BigDecimal valorReferencia2;
/*  178:     */   @Column(name="valor_referencia3", nullable=true, length=200)
/*  179:     */   @Digits(integer=12, fraction=2)
/*  180:     */   private BigDecimal valorReferencia3;
/*  181:     */   @Column(name="retencion_comercializadora", nullable=false, precision=12, scale=2)
/*  182:     */   @NotNull
/*  183:     */   @Digits(integer=12, fraction=2)
/*  184:     */   @Min(0L)
/*  185: 255 */   private BigDecimal retencionComercializadora = BigDecimal.ZERO;
/*  186:     */   @Column(name="retencion_3x1000", nullable=false, precision=12, scale=2)
/*  187:     */   @NotNull
/*  188:     */   @Digits(integer=12, fraction=2)
/*  189:     */   @Min(0L)
/*  190: 261 */   private BigDecimal retencion3X1000 = BigDecimal.ZERO;
/*  191:     */   @Column(name="retencion_iva_presuntivo", nullable=false, precision=12, scale=2)
/*  192:     */   @NotNull
/*  193:     */   @Digits(integer=12, fraction=2)
/*  194:     */   @Min(0L)
/*  195: 267 */   private BigDecimal retencionIvaPresuntivo = BigDecimal.ZERO;
/*  196:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  197:     */   @JoinColumn(name="id_persona_responsable", nullable=true)
/*  198:     */   private PersonaResponsable personaResponsable;
/*  199:     */   @Temporal(TemporalType.DATE)
/*  200:     */   @Column(name="fecha_vencimiento", nullable=true)
/*  201:     */   private Date fechaVencimiento;
/*  202:     */   @Transient
/*  203: 281 */   private BigDecimal totalGastoReal = BigDecimal.ZERO;
/*  204:     */   @Transient
/*  205: 284 */   private BigDecimal totalGastoPresupuesto = BigDecimal.ZERO;
/*  206:     */   @Transient
/*  207: 287 */   private boolean indicadorCreditoTributarioSRI = true;
/*  208:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  209:     */   @JoinColumn(name="id_proyecto", nullable=true)
/*  210:     */   private DimensionContable proyecto;
/*  211:     */   @Transient
/*  212:     */   private String traNumeroFacturaCompleto;
/*  213:     */   @Transient
/*  214: 299 */   private BigDecimal traImpuestoBienes = BigDecimal.ZERO;
/*  215:     */   @Transient
/*  216: 302 */   private BigDecimal traValorTotalSubida = BigDecimal.ZERO;
/*  217:     */   @Transient
/*  218:     */   private boolean indicadorLiquidarImportacion;
/*  219:     */   @Transient
/*  220: 308 */   private boolean indicadorNoValidarPrecioMayorCero = false;
/*  221:     */   @Transient
/*  222: 311 */   private boolean indicadorConRetencion = false;
/*  223:     */   
/*  224:     */   public FacturaProveedor(int idFacturaProveedor, String numero, Date fecha)
/*  225:     */   {
/*  226: 316 */     this.idFacturaProveedor = idFacturaProveedor;
/*  227: 317 */     this.numero = numero;
/*  228: 318 */     this.fecha = fecha;
/*  229:     */   }
/*  230:     */   
/*  231:     */   public FacturaProveedor(int idFacturaProveedor, String establecimiento, String puntoEmision, String numero)
/*  232:     */   {
/*  233: 323 */     this.idFacturaProveedor = idFacturaProveedor;
/*  234: 324 */     this.traNumeroFacturaCompleto = (establecimiento + "-" + puntoEmision + "-" + numero);
/*  235:     */   }
/*  236:     */   
/*  237:     */   public FacturaProveedor() {}
/*  238:     */   
/*  239:     */   public FacturaProveedor(Integer idFacturaProveedor_FP, Integer idOrganizacion_FP, String usuarioCreacion_FP, String usuarioModificacion_FP, Date fechaCreacion_FP, Date fechaModificacion_FP, String numero_FP, BigDecimal descuentoImpuesto_FP, Date fechaContabilizacion_FP, Date fechaRecepcion_FP, String descripcion_FP, Date fecha_FP, BigDecimal total_FP, BigDecimal descuento_FP, BigDecimal impuesto_FP, Estado estado_FP, String PDF_FP, Integer idEmpresa_E, String nombreFiscal_E, String nombreComercial_E, Integer idRecepcionProveedor_RP, String numeroRecepcionProveedor_RP, Integer idDocumento_DOC, String reporteDocumento_DOC, Boolean indicadorDocumentoExterior_DOC, DocumentoBase documentoBase_DOC, Boolean indicadorDocumentoElectronico_DOC, Boolean indicadorDocuemntoTributario_DOC, Integer idFacturaProveeedorSRI_FP_SRI, String establecimiento_FP_SRI, String puntoEmision_FP_SRI, String numeroSRI_FP_SRI, String numeroRetencion_FP_SRI, Boolean indicadorRetencionEmitida_FP_SRI, String puntoEmisionRetencion_FP_SRI, String establecimientoRetencion_FP_SRI, String mensajeFacturaProveedorSRI_FP_SRI, Estado estadoFacturaProveedorSRI_FP_SRI, Boolean indicadorDocumentoElectronicoFacturaProveedorSRI_FP_SRI, String claveAccesoFacturaProveedorSRI_FP_SRI, Integer idFacturaProveedorPadre_FP_Padre, String numeroFacturaProveedorPadre_FP_Padre, Estado estadoFacturaProveedorPadre_FP_Padre, Integer idFacturaProveeedorSRI_FP_Padre, String establecimientoSRI_FP_Padre, String puntoEmisionSRI_FP_Padre, String numeroSRI_FP_Padre, String establecimientoRetencion_FP_SRI_Padre, String numeroRetencion_FP_SRI_Padre, Boolean indicadorRetencionEmitida_FP_SRI_Padre, String puntoEmisionRetencion_FP_SRI_Padre, String claveAccesoFacturaProveedorSRI_FP_SRI_Padre, Integer idAnticipoProveeedor_A, String numeroAnticipoProveeedor_A, BigDecimal saldoAnticipoProveeedor_A, Integer idAsiento_AS, String numeroAsiento_AS, Estado estado_AS, Integer idTipoAsiento_T_AS, String nombreTipoAsiento_T_AS, Integer idProveedor_P, Integer idProyecto_PY, String codigoProyecto_PY, String nombreProyecto_PY, Integer idSucursal_SU, String nombreSucursal_SU, Integer idFacturaProveedorImportacion_F_P_IM, Integer idMoneda_MON, String nombreMoneda_MON, Integer idPais_PAIS, String nombre_PAIS, Integer idTipoComprobanteSRI_FP_SRI, BigDecimal totalValorGastoReal, Integer idAsiento_AS_Importacion, String numeroAsiento_AS_Importacion, Integer idTipoAsiento_T_AS_Importacion, String nombreTipoAsiento_T_AS_Importacion)
/*  240:     */   {
/*  241: 356 */     this.idOrganizacion = idOrganizacion_FP.intValue();
/*  242: 357 */     this.idFacturaProveedor = idFacturaProveedor_FP.intValue();
/*  243: 358 */     this.numero = numero_FP;
/*  244: 360 */     if (idSucursal_SU != null)
/*  245:     */     {
/*  246: 361 */       Sucursal sucursal = new Sucursal();
/*  247: 362 */       sucursal.setIdSucursal(idSucursal_SU.intValue());
/*  248: 363 */       sucursal.setIdOrganizacion(idOrganizacion_FP.intValue());
/*  249: 364 */       sucursal.setNombre(nombreSucursal_SU);
/*  250: 365 */       this.sucursal = sucursal;
/*  251:     */     }
/*  252: 368 */     if (idRecepcionProveedor_RP != null)
/*  253:     */     {
/*  254: 369 */       RecepcionProveedor recepcionProveedor = new RecepcionProveedor();
/*  255: 370 */       recepcionProveedor.setIdRecepcionProveedor(idRecepcionProveedor_RP.intValue());
/*  256: 371 */       recepcionProveedor.setNumero(numeroRecepcionProveedor_RP);
/*  257: 372 */       this.recepcionProveedor = recepcionProveedor;
/*  258:     */     }
/*  259: 375 */     this.fecha = fecha_FP;
/*  260: 376 */     this.total = total_FP;
/*  261: 377 */     this.descuento = descuento_FP;
/*  262: 378 */     this.impuesto = impuesto_FP;
/*  263: 379 */     this.estado = estado_FP;
/*  264: 381 */     if (idFacturaProveeedorSRI_FP_SRI != null)
/*  265:     */     {
/*  266: 382 */       FacturaProveedorSRI facturaProveedorSRI = new FacturaProveedorSRI();
/*  267: 383 */       facturaProveedorSRI.setIdOrganizacion(idOrganizacion_FP.intValue());
/*  268: 384 */       facturaProveedorSRI.setIdSucursal(idSucursal_SU.intValue());
/*  269: 385 */       facturaProveedorSRI.setIdFacturaProveedorSRI(idFacturaProveeedorSRI_FP_SRI.intValue());
/*  270: 386 */       facturaProveedorSRI.setEstablecimiento(establecimiento_FP_SRI);
/*  271: 387 */       facturaProveedorSRI.setPuntoEmision(puntoEmision_FP_SRI);
/*  272: 388 */       facturaProveedorSRI.setNumero(numeroSRI_FP_SRI);
/*  273: 389 */       facturaProveedorSRI.setNumeroRetencion(numeroRetencion_FP_SRI);
/*  274: 390 */       facturaProveedorSRI.setIndicadorRetencionEmitida(indicadorRetencionEmitida_FP_SRI.booleanValue());
/*  275: 391 */       facturaProveedorSRI.setPuntoEmisionRetencion(puntoEmisionRetencion_FP_SRI);
/*  276: 392 */       facturaProveedorSRI.setEstablecimientoRetencion(establecimientoRetencion_FP_SRI);
/*  277: 393 */       facturaProveedorSRI.setMensajeSRI(mensajeFacturaProveedorSRI_FP_SRI);
/*  278: 394 */       facturaProveedorSRI.setEstado(estadoFacturaProveedorSRI_FP_SRI);
/*  279: 395 */       facturaProveedorSRI.setClaveAcceso(claveAccesoFacturaProveedorSRI_FP_SRI);
/*  280: 397 */       if (idTipoComprobanteSRI_FP_SRI != null)
/*  281:     */       {
/*  282: 398 */         TipoComprobanteSRI tipoComporbanteSRI = new TipoComprobanteSRI();
/*  283: 399 */         tipoComporbanteSRI.setIdTipoComprobanteSRI(idTipoComprobanteSRI_FP_SRI.intValue());
/*  284: 400 */         facturaProveedorSRI.setTipoComprobanteSRI(tipoComporbanteSRI);
/*  285:     */       }
/*  286: 403 */       facturaProveedorSRI.setIndicadorDocumentoElectronico(indicadorDocumentoElectronicoFacturaProveedorSRI_FP_SRI.booleanValue());
/*  287: 404 */       this.facturaProveedorSRI = facturaProveedorSRI;
/*  288: 405 */       this.traNumeroFacturaCompleto = (establecimiento_FP_SRI + "-" + puntoEmision_FP_SRI + "-" + numeroSRI_FP_SRI);
/*  289:     */     }
/*  290: 408 */     this.descripcion = descripcion_FP;
/*  291: 409 */     Documento docu = new Documento();
/*  292: 410 */     docu.setIdDocumento(idDocumento_DOC.intValue());
/*  293: 411 */     docu.setDocumentoBase(documentoBase_DOC);
/*  294: 412 */     docu.setIndicadorDocumentoExterior(indicadorDocumentoExterior_DOC.booleanValue());
/*  295: 413 */     docu.setReporte(reporteDocumento_DOC);
/*  296: 414 */     docu.setIndicadorDocumentoElectronico(indicadorDocumentoElectronico_DOC.booleanValue());
/*  297: 415 */     docu.setIndicadorDocumentoTributario(indicadorDocuemntoTributario_DOC.booleanValue());
/*  298: 416 */     this.documento = docu;
/*  299: 418 */     if (idAnticipoProveeedor_A != null)
/*  300:     */     {
/*  301: 419 */       AnticipoProveedor anticipoProveedor = new AnticipoProveedor();
/*  302: 420 */       anticipoProveedor.setIdAnticipoProveedor(idAnticipoProveeedor_A.intValue());
/*  303: 421 */       anticipoProveedor.setNumero(numeroAnticipoProveeedor_A);
/*  304: 422 */       anticipoProveedor.setSaldo(saldoAnticipoProveeedor_A);
/*  305: 423 */       this.anticipoProveedor = anticipoProveedor;
/*  306:     */     }
/*  307: 426 */     if (idAsiento_AS != null)
/*  308:     */     {
/*  309: 427 */       Asiento asiento = new Asiento();
/*  310: 428 */       asiento.setIdAsiento(idAsiento_AS.intValue());
/*  311: 429 */       asiento.setNumero(numeroAsiento_AS);
/*  312: 430 */       asiento.setEstado(estado_AS);
/*  313: 431 */       if (idTipoAsiento_T_AS != null)
/*  314:     */       {
/*  315: 432 */         TipoAsiento tipoAsiento = new TipoAsiento();
/*  316: 433 */         tipoAsiento.setIdTipoAsiento(idTipoAsiento_T_AS.intValue());
/*  317: 434 */         tipoAsiento.setNombre(nombreTipoAsiento_T_AS);
/*  318: 435 */         asiento.setTipoAsiento(tipoAsiento);
/*  319:     */       }
/*  320: 437 */       this.asiento = asiento;
/*  321:     */     }
/*  322: 440 */     if (idEmpresa_E != null)
/*  323:     */     {
/*  324: 441 */       Empresa empresa = new Empresa();
/*  325: 442 */       empresa.setIdEmpresa(idEmpresa_E.intValue());
/*  326: 443 */       empresa.setIdOrganizacion(idOrganizacion_FP.intValue());
/*  327: 444 */       empresa.setIdEmpresa(idEmpresa_E.intValue());
/*  328: 445 */       empresa.setNombreComercial(nombreComercial_E);
/*  329: 446 */       empresa.setNombreFiscal(nombreFiscal_E);
/*  330: 447 */       if (idProveedor_P != null)
/*  331:     */       {
/*  332: 448 */         Proveedor proveedor = new Proveedor();
/*  333: 449 */         proveedor.setIdProveedor(idProveedor_P.intValue());
/*  334: 450 */         empresa.setProveedor(proveedor);
/*  335:     */       }
/*  336: 452 */       this.empresa = empresa;
/*  337:     */     }
/*  338: 455 */     if (idFacturaProveedorPadre_FP_Padre != null)
/*  339:     */     {
/*  340: 456 */       FacturaProveedor facturaProveedorPadre = new FacturaProveedor();
/*  341: 457 */       facturaProveedorPadre.setIdFacturaProveedor(idFacturaProveedorPadre_FP_Padre.intValue());
/*  342: 458 */       facturaProveedorPadre.setNumero(numeroFacturaProveedorPadre_FP_Padre);
/*  343: 459 */       facturaProveedorPadre.setEstado(estadoFacturaProveedorPadre_FP_Padre);
/*  344: 460 */       if (idFacturaProveeedorSRI_FP_Padre != null)
/*  345:     */       {
/*  346: 461 */         FacturaProveedorSRI facturaProveeedorSRIPadre = new FacturaProveedorSRI();
/*  347: 462 */         facturaProveeedorSRIPadre.setIdFacturaProveedorSRI(idFacturaProveeedorSRI_FP_Padre.intValue());
/*  348: 463 */         facturaProveeedorSRIPadre.setPuntoEmision(puntoEmisionSRI_FP_Padre);
/*  349: 464 */         facturaProveeedorSRIPadre.setEstablecimiento(establecimientoSRI_FP_Padre);
/*  350: 465 */         facturaProveeedorSRIPadre.setNumero(numeroSRI_FP_Padre);
/*  351: 466 */         facturaProveeedorSRIPadre.setEstablecimientoRetencion(establecimientoRetencion_FP_SRI_Padre);
/*  352: 467 */         facturaProveeedorSRIPadre.setNumeroRetencion(numeroRetencion_FP_SRI_Padre);
/*  353: 468 */         facturaProveeedorSRIPadre.setPuntoEmisionRetencion(puntoEmisionRetencion_FP_SRI_Padre);
/*  354: 469 */         facturaProveeedorSRIPadre.setIndicadorRetencionEmitida(indicadorRetencionEmitida_FP_SRI_Padre.booleanValue());
/*  355: 470 */         facturaProveeedorSRIPadre.setClaveAcceso(claveAccesoFacturaProveedorSRI_FP_SRI_Padre);
/*  356: 471 */         facturaProveedorPadre.setFacturaProveedorSRI(facturaProveeedorSRIPadre);
/*  357:     */       }
/*  358: 473 */       this.facturaProveedorPadre = facturaProveedorPadre;
/*  359:     */     }
/*  360: 476 */     if (idProyecto_PY != null)
/*  361:     */     {
/*  362: 477 */       DimensionContable proyecto = new DimensionContable();
/*  363: 478 */       proyecto.setIdDimensionContable(idProyecto_PY.intValue());
/*  364: 479 */       proyecto.setCodigo(codigoProyecto_PY);
/*  365: 480 */       proyecto.setNombre(nombreProyecto_PY);
/*  366: 481 */       setProyecto(proyecto);
/*  367:     */     }
/*  368: 484 */     this.usuarioCreacion = usuarioCreacion_FP;
/*  369: 485 */     this.usuarioModificacion = usuarioModificacion_FP;
/*  370: 486 */     this.fechaCreacion = fechaCreacion_FP;
/*  371: 487 */     this.fechaModificacion = fechaModificacion_FP;
/*  372: 489 */     if (idFacturaProveedorImportacion_F_P_IM != null)
/*  373:     */     {
/*  374: 490 */       FacturaProveedorImportacion facturaProveedorImportacion = new FacturaProveedorImportacion();
/*  375: 491 */       facturaProveedorImportacion.setIdFacturaProveedorImportacion(idFacturaProveedorImportacion_F_P_IM.intValue());
/*  376: 492 */       if (idMoneda_MON != null)
/*  377:     */       {
/*  378: 493 */         Moneda moneda = new Moneda();
/*  379: 494 */         moneda.setIdMoneda(idMoneda_MON.intValue());
/*  380: 495 */         moneda.setNombre(nombreMoneda_MON);
/*  381: 496 */         facturaProveedorImportacion.setMoneda(moneda);
/*  382:     */       }
/*  383: 498 */       if (idPais_PAIS != null)
/*  384:     */       {
/*  385: 499 */         Pais pais = new Pais();
/*  386: 500 */         pais.setIdPais(idPais_PAIS.intValue());
/*  387: 501 */         pais.setNombre(nombre_PAIS);
/*  388: 502 */         facturaProveedorImportacion.setPais(pais);
/*  389:     */       }
/*  390: 504 */       if (idAsiento_AS_Importacion != null)
/*  391:     */       {
/*  392: 505 */         Asiento asientoImportacion = new Asiento();
/*  393: 506 */         asientoImportacion.setIdAsiento(idAsiento_AS_Importacion.intValue());
/*  394: 507 */         asientoImportacion.setNumero(numeroAsiento_AS_Importacion);
/*  395: 508 */         if (idTipoAsiento_T_AS_Importacion != null)
/*  396:     */         {
/*  397: 509 */           TipoAsiento tipoAsientoImportacion = new TipoAsiento();
/*  398: 510 */           tipoAsientoImportacion.setIdTipoAsiento(idTipoAsiento_T_AS_Importacion.intValue());
/*  399: 511 */           tipoAsientoImportacion.setNombre(nombreTipoAsiento_T_AS_Importacion);
/*  400: 512 */           asientoImportacion.setTipoAsiento(tipoAsientoImportacion);
/*  401:     */         }
/*  402: 514 */         facturaProveedorImportacion.setAsiento(asientoImportacion);
/*  403:     */       }
/*  404: 516 */       facturaProveedorImportacion.setTotalValorGastoReal(totalValorGastoReal);
/*  405: 517 */       this.facturaProveedorImportacion = facturaProveedorImportacion;
/*  406:     */     }
/*  407: 520 */     this.descuentoImpuesto = descuentoImpuesto_FP;
/*  408: 521 */     this.fechaContabilizacion = fechaContabilizacion_FP;
/*  409: 522 */     this.fechaRecepcion = fechaRecepcion_FP;
/*  410: 523 */     this.pdf = PDF_FP;
/*  411:     */   }
/*  412:     */   
/*  413:     */   public BigDecimal getTotalCuentaPorPagar()
/*  414:     */   {
/*  415: 533 */     BigDecimal valorCuentaPorPagar = BigDecimal.ZERO;
/*  416: 535 */     for (CuentaPorPagar cuentaPorPagar : this.listaCuentaPorPagar) {
/*  417: 536 */       if (!cuentaPorPagar.isEliminado()) {
/*  418: 537 */         valorCuentaPorPagar = valorCuentaPorPagar.add(cuentaPorPagar.getValor());
/*  419:     */       }
/*  420:     */     }
/*  421: 541 */     return valorCuentaPorPagar;
/*  422:     */   }
/*  423:     */   
/*  424:     */   public int getId()
/*  425:     */   {
/*  426: 551 */     return this.idFacturaProveedor;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public void setId(int idFacturaProveedor)
/*  430:     */   {
/*  431: 556 */     this.idFacturaProveedor = idFacturaProveedor;
/*  432:     */   }
/*  433:     */   
/*  434:     */   public int getIdFacturaProveedor()
/*  435:     */   {
/*  436: 562 */     return this.idFacturaProveedor;
/*  437:     */   }
/*  438:     */   
/*  439:     */   public void setIdFacturaProveedor(int idFacturaProveedor)
/*  440:     */   {
/*  441: 566 */     this.idFacturaProveedor = idFacturaProveedor;
/*  442:     */   }
/*  443:     */   
/*  444:     */   public Sucursal getSucursal()
/*  445:     */   {
/*  446: 570 */     return this.sucursal;
/*  447:     */   }
/*  448:     */   
/*  449:     */   public void setSucursal(Sucursal sucursal)
/*  450:     */   {
/*  451: 574 */     this.sucursal = sucursal;
/*  452:     */   }
/*  453:     */   
/*  454:     */   public int getIdOrganizacion()
/*  455:     */   {
/*  456: 578 */     return this.idOrganizacion;
/*  457:     */   }
/*  458:     */   
/*  459:     */   public void setIdOrganizacion(int idOrganizacion)
/*  460:     */   {
/*  461: 582 */     this.idOrganizacion = idOrganizacion;
/*  462:     */   }
/*  463:     */   
/*  464:     */   public Documento getDocumento()
/*  465:     */   {
/*  466: 586 */     return this.documento;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public void setDocumento(Documento documento)
/*  470:     */   {
/*  471: 590 */     this.documento = documento;
/*  472:     */   }
/*  473:     */   
/*  474:     */   public Empresa getEmpresa()
/*  475:     */   {
/*  476: 594 */     return this.empresa;
/*  477:     */   }
/*  478:     */   
/*  479:     */   public void setEmpresa(Empresa empresa)
/*  480:     */   {
/*  481: 598 */     this.empresa = empresa;
/*  482:     */   }
/*  483:     */   
/*  484:     */   public Asiento getAsiento()
/*  485:     */   {
/*  486: 602 */     return this.asiento;
/*  487:     */   }
/*  488:     */   
/*  489:     */   public void setAsiento(Asiento asiento)
/*  490:     */   {
/*  491: 606 */     this.asiento = asiento;
/*  492:     */   }
/*  493:     */   
/*  494:     */   public String getNumero()
/*  495:     */   {
/*  496: 610 */     return this.numero;
/*  497:     */   }
/*  498:     */   
/*  499:     */   public void setNumero(String numero)
/*  500:     */   {
/*  501: 614 */     this.numero = numero;
/*  502:     */   }
/*  503:     */   
/*  504:     */   public Date getFecha()
/*  505:     */   {
/*  506: 618 */     return this.fecha;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public void setFecha(Date fecha)
/*  510:     */   {
/*  511: 622 */     this.fecha = fecha;
/*  512:     */   }
/*  513:     */   
/*  514:     */   public Date getFechaRecepcion()
/*  515:     */   {
/*  516: 626 */     return this.fechaRecepcion;
/*  517:     */   }
/*  518:     */   
/*  519:     */   public void setFechaRecepcion(Date fechaRecepcion)
/*  520:     */   {
/*  521: 630 */     this.fechaRecepcion = fechaRecepcion;
/*  522:     */   }
/*  523:     */   
/*  524:     */   public Date getFechaContabilizacion()
/*  525:     */   {
/*  526: 634 */     return this.fechaContabilizacion;
/*  527:     */   }
/*  528:     */   
/*  529:     */   public void setFechaContabilizacion(Date fechaContabilizacion)
/*  530:     */   {
/*  531: 638 */     this.fechaContabilizacion = fechaContabilizacion;
/*  532:     */   }
/*  533:     */   
/*  534:     */   public BigDecimal getTotal()
/*  535:     */   {
/*  536: 642 */     return this.total;
/*  537:     */   }
/*  538:     */   
/*  539:     */   public void setTotal(BigDecimal total)
/*  540:     */   {
/*  541: 646 */     this.total = total;
/*  542:     */   }
/*  543:     */   
/*  544:     */   public BigDecimal getImpuesto()
/*  545:     */   {
/*  546: 650 */     return this.impuesto;
/*  547:     */   }
/*  548:     */   
/*  549:     */   public void setImpuesto(BigDecimal impuesto)
/*  550:     */   {
/*  551: 654 */     this.impuesto = impuesto;
/*  552:     */   }
/*  553:     */   
/*  554:     */   public BigDecimal getDescuento()
/*  555:     */   {
/*  556: 658 */     return this.descuento;
/*  557:     */   }
/*  558:     */   
/*  559:     */   public void setDescuento(BigDecimal descuento)
/*  560:     */   {
/*  561: 662 */     this.descuento = descuento;
/*  562:     */   }
/*  563:     */   
/*  564:     */   public int getNumeroCuotas()
/*  565:     */   {
/*  566: 666 */     return this.numeroCuotas;
/*  567:     */   }
/*  568:     */   
/*  569:     */   public void setNumeroCuotas(int numeroCuotas)
/*  570:     */   {
/*  571: 670 */     this.numeroCuotas = numeroCuotas;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public String getDescripcion()
/*  575:     */   {
/*  576: 674 */     return this.descripcion;
/*  577:     */   }
/*  578:     */   
/*  579:     */   public void setDescripcion(String descripcion)
/*  580:     */   {
/*  581: 678 */     this.descripcion = descripcion;
/*  582:     */   }
/*  583:     */   
/*  584:     */   public List<DetalleFacturaProveedor> getListaDetalleFacturaProveedor()
/*  585:     */   {
/*  586: 682 */     return this.listaDetalleFacturaProveedor;
/*  587:     */   }
/*  588:     */   
/*  589:     */   public void setListaDetalleFacturaProveedor(List<DetalleFacturaProveedor> listaDetalleFacturaProveedor)
/*  590:     */   {
/*  591: 686 */     this.listaDetalleFacturaProveedor = listaDetalleFacturaProveedor;
/*  592:     */   }
/*  593:     */   
/*  594:     */   public List<CuentaPorPagar> getListaCuentaPorPagar()
/*  595:     */   {
/*  596: 690 */     return this.listaCuentaPorPagar;
/*  597:     */   }
/*  598:     */   
/*  599:     */   public void setListaCuentaPorPagar(List<CuentaPorPagar> listaCuentaPorPagar)
/*  600:     */   {
/*  601: 694 */     this.listaCuentaPorPagar = listaCuentaPorPagar;
/*  602:     */   }
/*  603:     */   
/*  604:     */   public CondicionPago getCondicionPago()
/*  605:     */   {
/*  606: 703 */     return this.condicionPago;
/*  607:     */   }
/*  608:     */   
/*  609:     */   public void setCondicionPago(CondicionPago condicionPago)
/*  610:     */   {
/*  611: 713 */     this.condicionPago = condicionPago;
/*  612:     */   }
/*  613:     */   
/*  614:     */   public DireccionEmpresa getDireccionEmpresa()
/*  615:     */   {
/*  616: 722 */     return this.direccionEmpresa;
/*  617:     */   }
/*  618:     */   
/*  619:     */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/*  620:     */   {
/*  621: 732 */     this.direccionEmpresa = direccionEmpresa;
/*  622:     */   }
/*  623:     */   
/*  624:     */   public String getDireccionFactura()
/*  625:     */   {
/*  626: 741 */     return this.direccionFactura;
/*  627:     */   }
/*  628:     */   
/*  629:     */   public void setDireccionFactura(String direccionFactura)
/*  630:     */   {
/*  631: 751 */     this.direccionFactura = direccionFactura;
/*  632:     */   }
/*  633:     */   
/*  634:     */   public Estado getEstado()
/*  635:     */   {
/*  636: 760 */     return this.estado;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void setEstado(Estado estado)
/*  640:     */   {
/*  641: 770 */     this.estado = estado;
/*  642:     */   }
/*  643:     */   
/*  644:     */   public FacturaProveedorSRI getFacturaProveedorSRI()
/*  645:     */   {
/*  646: 774 */     return this.facturaProveedorSRI;
/*  647:     */   }
/*  648:     */   
/*  649:     */   public void setFacturaProveedorSRI(FacturaProveedorSRI facturaProveedorSRI)
/*  650:     */   {
/*  651: 778 */     this.facturaProveedorSRI = facturaProveedorSRI;
/*  652:     */   }
/*  653:     */   
/*  654:     */   public String getPdf()
/*  655:     */   {
/*  656: 782 */     return this.pdf;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void setPdf(String pdf)
/*  660:     */   {
/*  661: 786 */     this.pdf = pdf;
/*  662:     */   }
/*  663:     */   
/*  664:     */   public boolean isIndicadorSaldoInicial()
/*  665:     */   {
/*  666: 795 */     return this.indicadorSaldoInicial;
/*  667:     */   }
/*  668:     */   
/*  669:     */   public void setIndicadorSaldoInicial(boolean indicadorSaldoInicial)
/*  670:     */   {
/*  671: 805 */     this.indicadorSaldoInicial = indicadorSaldoInicial;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public BigDecimal getTotalFactura()
/*  675:     */   {
/*  676: 809 */     this.totalFactura = getTotal().subtract(getDescuento()).add(getImpuesto());
/*  677: 810 */     return this.totalFactura;
/*  678:     */   }
/*  679:     */   
/*  680:     */   public void setTotalFactura(BigDecimal totalFactura)
/*  681:     */   {
/*  682: 814 */     this.totalFactura = totalFactura;
/*  683:     */   }
/*  684:     */   
/*  685:     */   public String getTraNumeroFacturaCompleto()
/*  686:     */   {
/*  687: 818 */     return this.traNumeroFacturaCompleto;
/*  688:     */   }
/*  689:     */   
/*  690:     */   public void setTraNumeroFacturaCompleto(String traNumeroFacturaCompleto)
/*  691:     */   {
/*  692: 822 */     this.traNumeroFacturaCompleto = traNumeroFacturaCompleto;
/*  693:     */   }
/*  694:     */   
/*  695:     */   public FacturaProveedor getFacturaProveedorPadre()
/*  696:     */   {
/*  697: 831 */     return this.facturaProveedorPadre;
/*  698:     */   }
/*  699:     */   
/*  700:     */   public void setFacturaProveedorPadre(FacturaProveedor facturaProveedorPadre)
/*  701:     */   {
/*  702: 841 */     this.facturaProveedorPadre = facturaProveedorPadre;
/*  703:     */   }
/*  704:     */   
/*  705:     */   public AnticipoProveedor getAnticipoProveedor()
/*  706:     */   {
/*  707: 850 */     return this.anticipoProveedor;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public void setAnticipoProveedor(AnticipoProveedor anticipoProveedor)
/*  711:     */   {
/*  712: 860 */     this.anticipoProveedor = anticipoProveedor;
/*  713:     */   }
/*  714:     */   
/*  715:     */   public MotivoNotaCreditoProveedor getMotivoNotaCreditoProveedor()
/*  716:     */   {
/*  717: 867 */     return this.motivoNotaCreditoProveedor;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public void setMotivoNotaCreditoProveedor(MotivoNotaCreditoProveedor motivoNotaCreditoProveedor)
/*  721:     */   {
/*  722: 875 */     this.motivoNotaCreditoProveedor = motivoNotaCreditoProveedor;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public BigDecimal getBono()
/*  726:     */   {
/*  727: 879 */     return this.bono;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public void setBono(BigDecimal bono)
/*  731:     */   {
/*  732: 883 */     this.bono = bono;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public boolean isIndicadorCreditoTributario()
/*  736:     */   {
/*  737: 887 */     return this.indicadorCreditoTributario;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public void setIndicadorCreditoTributario(boolean indicadorCreditoTributario)
/*  741:     */   {
/*  742: 891 */     this.indicadorCreditoTributario = indicadorCreditoTributario;
/*  743:     */   }
/*  744:     */   
/*  745:     */   public boolean isIndicadorNotaCreditoDebito()
/*  746:     */   {
/*  747: 900 */     return this.indicadorNotaCreditoDebito;
/*  748:     */   }
/*  749:     */   
/*  750:     */   public void setIndicadorNotaCreditoDebito(boolean indicadorNotaCreditoDebito)
/*  751:     */   {
/*  752: 910 */     this.indicadorNotaCreditoDebito = indicadorNotaCreditoDebito;
/*  753:     */   }
/*  754:     */   
/*  755:     */   public TipoOperacion getTipoOperacion()
/*  756:     */   {
/*  757: 921 */     return this.tipoOperacion;
/*  758:     */   }
/*  759:     */   
/*  760:     */   public void setTipoOperacion(TipoOperacion tipoOperacion)
/*  761:     */   {
/*  762: 931 */     this.tipoOperacion = tipoOperacion;
/*  763:     */   }
/*  764:     */   
/*  765:     */   public boolean isIndicadorGastoImportacion()
/*  766:     */   {
/*  767: 940 */     return this.indicadorGastoImportacion;
/*  768:     */   }
/*  769:     */   
/*  770:     */   public void setIndicadorGastoImportacion(boolean indicadorGastoImportacion)
/*  771:     */   {
/*  772: 950 */     this.indicadorGastoImportacion = indicadorGastoImportacion;
/*  773:     */   }
/*  774:     */   
/*  775:     */   public FacturaProveedorImportacion getFacturaProveedorImportacion()
/*  776:     */   {
/*  777: 959 */     return this.facturaProveedorImportacion;
/*  778:     */   }
/*  779:     */   
/*  780:     */   public void setFacturaProveedorImportacion(FacturaProveedorImportacion facturaProveedorImportacion)
/*  781:     */   {
/*  782: 969 */     this.facturaProveedorImportacion = facturaProveedorImportacion;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public RecepcionProveedor getRecepcionProveedor()
/*  786:     */   {
/*  787: 976 */     return this.recepcionProveedor;
/*  788:     */   }
/*  789:     */   
/*  790:     */   public void setRecepcionProveedor(RecepcionProveedor recepcionProveedor)
/*  791:     */   {
/*  792: 984 */     this.recepcionProveedor = recepcionProveedor;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public List<DetalleFacturaProveedorImportacion> getListaDetalleFacturaProveedorImportacion()
/*  796:     */   {
/*  797: 993 */     return this.listaDetalleFacturaProveedorImportacion;
/*  798:     */   }
/*  799:     */   
/*  800:     */   public void setListaDetalleFacturaProveedorImportacion(List<DetalleFacturaProveedorImportacion> listaDetalleFacturaProveedorImportacion)
/*  801:     */   {
/*  802:1003 */     this.listaDetalleFacturaProveedorImportacion = listaDetalleFacturaProveedorImportacion;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public BigDecimal getTotalGastoPresupuesto()
/*  806:     */   {
/*  807:1012 */     this.totalGastoPresupuesto = BigDecimal.ZERO;
/*  808:1013 */     for (DetalleFacturaProveedor dfp : this.listaDetalleFacturaProveedor) {
/*  809:1014 */       this.totalGastoPresupuesto = this.totalGastoPresupuesto.add(dfp.getGastoPresupuesto());
/*  810:     */     }
/*  811:1016 */     return FuncionesUtiles.redondearBigDecimal(this.totalGastoPresupuesto, 2);
/*  812:     */   }
/*  813:     */   
/*  814:     */   public void setTotalGastoPresupuesto(BigDecimal totalGastoPresupuesto)
/*  815:     */   {
/*  816:1026 */     this.totalGastoPresupuesto = totalGastoPresupuesto;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public String getReferencia1()
/*  820:     */   {
/*  821:1035 */     return this.referencia1;
/*  822:     */   }
/*  823:     */   
/*  824:     */   public void setReferencia1(String referencia1)
/*  825:     */   {
/*  826:1045 */     this.referencia1 = referencia1;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public String getReferencia2()
/*  830:     */   {
/*  831:1054 */     return this.referencia2;
/*  832:     */   }
/*  833:     */   
/*  834:     */   public void setReferencia2(String referencia2)
/*  835:     */   {
/*  836:1064 */     this.referencia2 = referencia2;
/*  837:     */   }
/*  838:     */   
/*  839:     */   public String getReferencia3()
/*  840:     */   {
/*  841:1068 */     return this.referencia3;
/*  842:     */   }
/*  843:     */   
/*  844:     */   public void setReferencia3(String referencia3)
/*  845:     */   {
/*  846:1072 */     this.referencia3 = referencia3;
/*  847:     */   }
/*  848:     */   
/*  849:     */   public BigDecimal getRetencionComercializadora()
/*  850:     */   {
/*  851:1081 */     return this.retencionComercializadora;
/*  852:     */   }
/*  853:     */   
/*  854:     */   public void setRetencionComercializadora(BigDecimal retencionComercializadora)
/*  855:     */   {
/*  856:1091 */     this.retencionComercializadora = retencionComercializadora;
/*  857:     */   }
/*  858:     */   
/*  859:     */   public BigDecimal getTraValorTotalSubida()
/*  860:     */   {
/*  861:1100 */     return this.traValorTotalSubida;
/*  862:     */   }
/*  863:     */   
/*  864:     */   public void setTraValorTotalSubida(BigDecimal traValorTotalSubida)
/*  865:     */   {
/*  866:1110 */     this.traValorTotalSubida = traValorTotalSubida;
/*  867:     */   }
/*  868:     */   
/*  869:     */   public BigDecimal getTraImpuestoBienes()
/*  870:     */   {
/*  871:1119 */     return this.traImpuestoBienes;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void setTraImpuestoBienes(BigDecimal traImpuestoBienes)
/*  875:     */   {
/*  876:1129 */     this.traImpuestoBienes = traImpuestoBienes;
/*  877:     */   }
/*  878:     */   
/*  879:     */   public BigDecimal getRetencion3X1000()
/*  880:     */   {
/*  881:1133 */     return this.retencion3X1000;
/*  882:     */   }
/*  883:     */   
/*  884:     */   public void setRetencion3X1000(BigDecimal retencion3x1000)
/*  885:     */   {
/*  886:1137 */     this.retencion3X1000 = retencion3x1000;
/*  887:     */   }
/*  888:     */   
/*  889:     */   public BigDecimal getRetencionIvaPresuntivo()
/*  890:     */   {
/*  891:1141 */     return this.retencionIvaPresuntivo;
/*  892:     */   }
/*  893:     */   
/*  894:     */   public void setRetencionIvaPresuntivo(BigDecimal retencionIvaPresuntivo)
/*  895:     */   {
/*  896:1145 */     this.retencionIvaPresuntivo = retencionIvaPresuntivo;
/*  897:     */   }
/*  898:     */   
/*  899:     */   public boolean isIndicadorLiquidarImportacion()
/*  900:     */   {
/*  901:1149 */     return this.indicadorLiquidarImportacion;
/*  902:     */   }
/*  903:     */   
/*  904:     */   public void setIndicadorLiquidarImportacion(boolean indicadorLiquidarImportacion)
/*  905:     */   {
/*  906:1153 */     this.indicadorLiquidarImportacion = indicadorLiquidarImportacion;
/*  907:     */   }
/*  908:     */   
/*  909:     */   public DimensionContable getProyecto()
/*  910:     */   {
/*  911:1160 */     return this.proyecto;
/*  912:     */   }
/*  913:     */   
/*  914:     */   public void setProyecto(DimensionContable proyecto)
/*  915:     */   {
/*  916:1168 */     this.proyecto = proyecto;
/*  917:     */   }
/*  918:     */   
/*  919:     */   public BigDecimal getValorReferencia1()
/*  920:     */   {
/*  921:1172 */     return this.valorReferencia1;
/*  922:     */   }
/*  923:     */   
/*  924:     */   public void setValorReferencia1(BigDecimal valorReferencia1)
/*  925:     */   {
/*  926:1176 */     this.valorReferencia1 = valorReferencia1;
/*  927:     */   }
/*  928:     */   
/*  929:     */   public BigDecimal getValorReferencia2()
/*  930:     */   {
/*  931:1180 */     return this.valorReferencia2;
/*  932:     */   }
/*  933:     */   
/*  934:     */   public void setValorReferencia2(BigDecimal valorReferencia2)
/*  935:     */   {
/*  936:1184 */     this.valorReferencia2 = valorReferencia2;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public BigDecimal getValorReferencia3()
/*  940:     */   {
/*  941:1188 */     return this.valorReferencia3;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public void setValorReferencia3(BigDecimal valorReferencia3)
/*  945:     */   {
/*  946:1192 */     this.valorReferencia3 = valorReferencia3;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public boolean isIndicadorNoValidarPrecioMayorCero()
/*  950:     */   {
/*  951:1196 */     return this.indicadorNoValidarPrecioMayorCero;
/*  952:     */   }
/*  953:     */   
/*  954:     */   public void setIndicadorNoValidarPrecioMayorCero(boolean indicadorNoValidarPrecioMayorCero)
/*  955:     */   {
/*  956:1200 */     this.indicadorNoValidarPrecioMayorCero = indicadorNoValidarPrecioMayorCero;
/*  957:     */   }
/*  958:     */   
/*  959:     */   public boolean isIndicadorConRetencion()
/*  960:     */   {
/*  961:1204 */     return this.indicadorConRetencion;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public void setIndicadorConRetencion(boolean indicadorConRetencion)
/*  965:     */   {
/*  966:1208 */     this.indicadorConRetencion = indicadorConRetencion;
/*  967:     */   }
/*  968:     */   
/*  969:     */   public BigDecimal getDescuentoImpuesto()
/*  970:     */   {
/*  971:1214 */     return this.descuentoImpuesto;
/*  972:     */   }
/*  973:     */   
/*  974:     */   public void setDescuentoImpuesto(BigDecimal descuentoImpuesto)
/*  975:     */   {
/*  976:1218 */     this.descuentoImpuesto = descuentoImpuesto;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public String getDescripcion2()
/*  980:     */   {
/*  981:1222 */     return this.descripcion2;
/*  982:     */   }
/*  983:     */   
/*  984:     */   public void setDescripcion2(String descripcion2)
/*  985:     */   {
/*  986:1226 */     this.descripcion2 = descripcion2;
/*  987:     */   }
/*  988:     */   
/*  989:     */   public boolean isIndicadorCreditoTributarioSRI()
/*  990:     */   {
/*  991:1233 */     return this.indicadorCreditoTributarioSRI;
/*  992:     */   }
/*  993:     */   
/*  994:     */   public void setIndicadorCreditoTributarioSRI(boolean indicadorCreditoTributarioSRI)
/*  995:     */   {
/*  996:1241 */     this.indicadorCreditoTributarioSRI = indicadorCreditoTributarioSRI;
/*  997:     */   }
/*  998:     */   
/*  999:     */   public PersonaResponsable getPersonaResponsable()
/* 1000:     */   {
/* 1001:1245 */     return this.personaResponsable;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public void setPersonaResponsable(PersonaResponsable personaResponsable)
/* 1005:     */   {
/* 1006:1249 */     this.personaResponsable = personaResponsable;
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public Date getFechaVencimiento()
/* 1010:     */   {
/* 1011:1253 */     return this.fechaVencimiento;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public void setFechaVencimiento(Date fechaVencimiento)
/* 1015:     */   {
/* 1016:1257 */     this.fechaVencimiento = fechaVencimiento;
/* 1017:     */   }
/* 1018:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.FacturaProveedor
 * JD-Core Version:    0.7.0.1
 */