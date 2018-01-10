/*    1:     */ package com.asinfo.as2.entities;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*    4:     */ import com.asinfo.as2.enumeraciones.Estado;
/*    5:     */ import java.math.BigDecimal;
/*    6:     */ import java.util.ArrayList;
/*    7:     */ import java.util.Date;
/*    8:     */ import java.util.List;
/*    9:     */ import javax.persistence.Column;
/*   10:     */ import javax.persistence.Entity;
/*   11:     */ import javax.persistence.EnumType;
/*   12:     */ import javax.persistence.Enumerated;
/*   13:     */ import javax.persistence.FetchType;
/*   14:     */ import javax.persistence.GeneratedValue;
/*   15:     */ import javax.persistence.GenerationType;
/*   16:     */ import javax.persistence.Id;
/*   17:     */ import javax.persistence.JoinColumn;
/*   18:     */ import javax.persistence.ManyToOne;
/*   19:     */ import javax.persistence.OneToMany;
/*   20:     */ import javax.persistence.OneToOne;
/*   21:     */ import javax.persistence.Table;
/*   22:     */ import javax.persistence.TableGenerator;
/*   23:     */ import javax.persistence.Temporal;
/*   24:     */ import javax.persistence.TemporalType;
/*   25:     */ import javax.persistence.Transient;
/*   26:     */ import javax.validation.constraints.Min;
/*   27:     */ import javax.validation.constraints.NotNull;
/*   28:     */ import javax.validation.constraints.Size;
/*   29:     */ 
/*   30:     */ @Entity
/*   31:     */ @Table(name="anticipo_proveedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})}, indexes={@javax.persistence.Index(columnList="id_empresa"), @javax.persistence.Index(columnList="id_documento"), @javax.persistence.Index(columnList="id_forma_pago"), @javax.persistence.Index(columnList="id_asiento"), @javax.persistence.Index(columnList="fecha"), @javax.persistence.Index(columnList="id_factura_proveedor")})
/*   32:     */ public class AnticipoProveedor
/*   33:     */   extends EntidadBase
/*   34:     */ {
/*   35:     */   private static final long serialVersionUID = 1L;
/*   36:     */   @Id
/*   37:     */   @TableGenerator(name="anticipo_proveedor", initialValue=0, allocationSize=50)
/*   38:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="anticipo_proveedor")
/*   39:     */   @Column(name="id_anticipo_proveedor")
/*   40:     */   private int idAnticipoProveedor;
/*   41:     */   @Column(name="id_organizacion", nullable=false)
/*   42:     */   private int idOrganizacion;
/*   43:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   44:     */   @JoinColumn(name="id_sucursal", nullable=false)
/*   45:     */   private Sucursal sucursal;
/*   46:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   47:     */   @JoinColumn(name="id_empresa", nullable=false)
/*   48:     */   @NotNull
/*   49:     */   private Empresa empresa;
/*   50:     */   @Column(name="beneficiario", nullable=false)
/*   51:     */   @NotNull
/*   52:     */   @Size(max=50)
/*   53:     */   private String beneficiario;
/*   54:     */   @Enumerated(EnumType.ORDINAL)
/*   55:     */   @Column(name="estado", nullable=false)
/*   56:     */   @NotNull
/*   57:     */   private Estado estado;
/*   58:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   59:     */   @JoinColumn(name="id_documento", nullable=false)
/*   60:     */   @NotNull
/*   61:     */   private Documento documento;
/*   62:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   63:     */   @JoinColumn(name="id_forma_pago", nullable=true)
/*   64:     */   private FormaPago formaPago;
/*   65:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   66:     */   @JoinColumn(name="id_pago_cash", nullable=true)
/*   67:     */   private PagoCash pagoCash;
/*   68:     */   @OneToOne(fetch=FetchType.LAZY)
/*   69:     */   @JoinColumn(name="id_asiento", nullable=true)
/*   70:     */   private Asiento asiento;
/*   71:     */   @Column(name="fecha_contabilizacion", nullable=true)
/*   72:     */   private Date fechaContabilizacion;
/*   73:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   74:     */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=true)
/*   75:     */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*   76:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   77:     */   @JoinColumn(name="id_cuenta_contable_banco_pago_cash", nullable=true)
/*   78:     */   private CuentaContable cuentaContableBancoPagoCash;
/*   79:     */   @Column(name="numero", nullable=false, length=20)
/*   80:     */   private String numero;
/*   81:     */   @NotNull
/*   82:     */   @Temporal(TemporalType.DATE)
/*   83:     */   @Column(name="fecha", nullable=false)
/*   84:     */   private Date fecha;
/*   85:     */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*   86:     */   @Min(0L)
/*   87:     */   private BigDecimal valor;
/*   88:     */   @Column(name="saldo", precision=12, scale=2, nullable=false)
/*   89:     */   private BigDecimal saldo;
/*   90:     */   @Column(name="documento_referencia", nullable=true, length=20)
/*   91:     */   @Size(max=20)
/*   92:     */   private String documentoReferencia;
/*   93:     */   @Column(name="indicador_saldo_inicial", nullable=false)
/*   94:     */   private boolean indicadorSaldoInicial;
/*   95:     */   @Column(name="descripcion", length=200, nullable=true)
/*   96:     */   @Size(max=200)
/*   97: 136 */   private String descripcion = "";
/*   98:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   99:     */   @JoinColumn(name="id_factura_proveedor", nullable=true)
/*  100:     */   private FacturaProveedor notaCreditoProveedor;
/*  101:     */   @OneToOne(fetch=FetchType.LAZY)
/*  102:     */   @JoinColumn(name="id_factura_proveedor_sri", nullable=true)
/*  103:     */   private FacturaProveedorSRI facturaProveedorSRI;
/*  104:     */   @OneToMany(mappedBy="anticipoProveedor", fetch=FetchType.LAZY)
/*  105: 148 */   private List<LiquidacionAnticipoProveedor> listaLiquidacionAnticipoProveedor = new ArrayList();
/*  106:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  107:     */   @JoinColumn(name="id_cuenta_bancaria_organizacion_devolucion", nullable=true)
/*  108:     */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacionDevolucion;
/*  109:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  110:     */   @JoinColumn(name="id_documento_devolucion", nullable=true)
/*  111:     */   private Documento documentoDevolucion;
/*  112:     */   @Column(name="fecha_devolucion", nullable=true)
/*  113:     */   private Date fechaDevolucion;
/*  114:     */   @Column(name="valor_devolucion", precision=12, scale=2, nullable=true)
/*  115:     */   private BigDecimal valorDevolucion;
/*  116:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  117:     */   @JoinColumn(name="id_forma_pago_devolucion", nullable=true)
/*  118:     */   private FormaPago formaPagoDevolucion;
/*  119:     */   @OneToOne(fetch=FetchType.LAZY)
/*  120:     */   @JoinColumn(name="id_asiento_devolucion", nullable=true)
/*  121:     */   private Asiento asientoDevolucion;
/*  122:     */   @Column(name="documento_referencia_devolucion", nullable=true, length=20)
/*  123:     */   @Size(max=20)
/*  124:     */   private String documentoReferenciaDevolucion;
/*  125:     */   @Column(name="descripcion_devolucion", nullable=true, length=200)
/*  126:     */   @Size(max=200)
/*  127:     */   private String descripcionDevolucion;
/*  128:     */   @Temporal(TemporalType.DATE)
/*  129:     */   @Column(name="fecha_entrega_cheque", nullable=true)
/*  130:     */   private Date fechaEntregaCheque;
/*  131:     */   @Column(name="usuario_entrega_cheque", nullable=true)
/*  132:     */   private String usuarioEntregaCheque;
/*  133:     */   @Column(name="indicador_cheque_entregado", nullable=false)
/*  134:     */   private boolean indicadorChequeEntregado;
/*  135:     */   @Column(name="indicador_contabilizar", nullable=true)
/*  136:     */   private Boolean indicadorContabilizar;
/*  137:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  138:     */   @JoinColumn(name="id_detalle_orden_pago_proveedor", nullable=true)
/*  139:     */   private DetalleOrdenPagoProveedor detalleOrdenPagoProveedor;
/*  140:     */   @Column(name="indicador_cheque_posfechado", nullable=false)
/*  141:     */   private boolean indicadorChequePosfechado;
/*  142:     */   @Temporal(TemporalType.DATE)
/*  143:     */   @Column(name="fecha_posfechado", nullable=true)
/*  144:     */   private Date fechaPosfechado;
/*  145:     */   @Column(name="nota_posfechado", nullable=true, length=200)
/*  146:     */   @Size(max=200)
/*  147:     */   private String notaPosfechado;
/*  148:     */   @Column(name="pdf", nullable=true)
/*  149:     */   @Size(max=50)
/*  150:     */   private String pdf;
/*  151:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  152:     */   @JoinColumn(name="id_persona_responsable", nullable=true)
/*  153:     */   private PersonaResponsable personaResponsable;
/*  154:     */   @Transient
/*  155:     */   private Secuencia secuencia;
/*  156:     */   @Transient
/*  157:     */   private CuentaContable cuentaContableDevolucionCruce;
/*  158:     */   
/*  159:     */   public List<String> getCamposAuditables()
/*  160:     */   {
/*  161: 231 */     ArrayList<String> lista = new ArrayList();
/*  162: 232 */     lista.add("empresa");
/*  163: 233 */     lista.add("numero");
/*  164: 234 */     lista.add("fecha");
/*  165: 235 */     lista.add("valor");
/*  166: 236 */     lista.add("saldo");
/*  167: 237 */     lista.add("estado");
/*  168: 238 */     lista.add("documento");
/*  169: 239 */     lista.add("formaPago");
/*  170: 240 */     lista.add("documentoReferencia");
/*  171:     */     
/*  172: 242 */     return lista;
/*  173:     */   }
/*  174:     */   
/*  175:     */   public int getId()
/*  176:     */   {
/*  177: 247 */     return this.idAnticipoProveedor;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public int getIdAnticipoProveedor()
/*  181:     */   {
/*  182: 256 */     return this.idAnticipoProveedor;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public void setIdAnticipoProveedor(int idAnticipoProveedor)
/*  186:     */   {
/*  187: 266 */     this.idAnticipoProveedor = idAnticipoProveedor;
/*  188:     */   }
/*  189:     */   
/*  190:     */   public int getIdOrganizacion()
/*  191:     */   {
/*  192: 275 */     return this.idOrganizacion;
/*  193:     */   }
/*  194:     */   
/*  195:     */   public void setIdOrganizacion(int idOrganizacion)
/*  196:     */   {
/*  197: 285 */     this.idOrganizacion = idOrganizacion;
/*  198:     */   }
/*  199:     */   
/*  200:     */   public Sucursal getSucursal()
/*  201:     */   {
/*  202: 292 */     return this.sucursal;
/*  203:     */   }
/*  204:     */   
/*  205:     */   public void setSucursal(Sucursal sucursal)
/*  206:     */   {
/*  207: 300 */     this.sucursal = sucursal;
/*  208:     */   }
/*  209:     */   
/*  210:     */   public Empresa getEmpresa()
/*  211:     */   {
/*  212: 309 */     return this.empresa;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public void setEmpresa(Empresa empresa)
/*  216:     */   {
/*  217: 319 */     this.empresa = empresa;
/*  218:     */   }
/*  219:     */   
/*  220:     */   public Documento getDocumento()
/*  221:     */   {
/*  222: 328 */     return this.documento;
/*  223:     */   }
/*  224:     */   
/*  225:     */   public void setDocumento(Documento documento)
/*  226:     */   {
/*  227: 338 */     this.documento = documento;
/*  228:     */   }
/*  229:     */   
/*  230:     */   public FormaPago getFormaPago()
/*  231:     */   {
/*  232: 347 */     return this.formaPago;
/*  233:     */   }
/*  234:     */   
/*  235:     */   public void setFormaPago(FormaPago formaPago)
/*  236:     */   {
/*  237: 357 */     this.formaPago = formaPago;
/*  238:     */   }
/*  239:     */   
/*  240:     */   public Asiento getAsiento()
/*  241:     */   {
/*  242: 366 */     return this.asiento;
/*  243:     */   }
/*  244:     */   
/*  245:     */   public void setAsiento(Asiento asiento)
/*  246:     */   {
/*  247: 376 */     this.asiento = asiento;
/*  248:     */   }
/*  249:     */   
/*  250:     */   public Date getFechaContabilizacion()
/*  251:     */   {
/*  252: 385 */     return this.fechaContabilizacion;
/*  253:     */   }
/*  254:     */   
/*  255:     */   public void setFechaContabilizacion(Date fechaContabilizacion)
/*  256:     */   {
/*  257: 395 */     this.fechaContabilizacion = fechaContabilizacion;
/*  258:     */   }
/*  259:     */   
/*  260:     */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/*  261:     */   {
/*  262: 404 */     return this.cuentaBancariaOrganizacion;
/*  263:     */   }
/*  264:     */   
/*  265:     */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/*  266:     */   {
/*  267: 414 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/*  268:     */   }
/*  269:     */   
/*  270:     */   public String getNumero()
/*  271:     */   {
/*  272: 423 */     return this.numero;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public void setNumero(String numero)
/*  276:     */   {
/*  277: 433 */     this.numero = numero;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public Date getFecha()
/*  281:     */   {
/*  282: 442 */     return this.fecha;
/*  283:     */   }
/*  284:     */   
/*  285:     */   public void setFecha(Date fecha)
/*  286:     */   {
/*  287: 452 */     this.fecha = fecha;
/*  288:     */   }
/*  289:     */   
/*  290:     */   public BigDecimal getValor()
/*  291:     */   {
/*  292: 461 */     return this.valor;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public void setValor(BigDecimal valor)
/*  296:     */   {
/*  297: 471 */     this.valor = valor;
/*  298:     */   }
/*  299:     */   
/*  300:     */   public BigDecimal getSaldo()
/*  301:     */   {
/*  302: 480 */     return this.saldo;
/*  303:     */   }
/*  304:     */   
/*  305:     */   public void setSaldo(BigDecimal saldo)
/*  306:     */   {
/*  307: 490 */     this.saldo = saldo;
/*  308:     */   }
/*  309:     */   
/*  310:     */   public List<LiquidacionAnticipoProveedor> getListaLiquidacionAnticipoProveedor()
/*  311:     */   {
/*  312: 499 */     return this.listaLiquidacionAnticipoProveedor;
/*  313:     */   }
/*  314:     */   
/*  315:     */   public void setListaLiquidacionAnticipoProveedor(List<LiquidacionAnticipoProveedor> listaLiquidacionAnticipoProveedor)
/*  316:     */   {
/*  317: 509 */     this.listaLiquidacionAnticipoProveedor = listaLiquidacionAnticipoProveedor;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public String getDescripcion()
/*  321:     */   {
/*  322: 518 */     return this.descripcion;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public void setDescripcion(String descripcion)
/*  326:     */   {
/*  327: 528 */     this.descripcion = descripcion;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public Estado getEstado()
/*  331:     */   {
/*  332: 537 */     return this.estado;
/*  333:     */   }
/*  334:     */   
/*  335:     */   public void setEstado(Estado estado)
/*  336:     */   {
/*  337: 547 */     this.estado = estado;
/*  338:     */   }
/*  339:     */   
/*  340:     */   public String getDocumentoReferencia()
/*  341:     */   {
/*  342: 556 */     return this.documentoReferencia;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public void setDocumentoReferencia(String documentoReferencia)
/*  346:     */   {
/*  347: 566 */     this.documentoReferencia = documentoReferencia;
/*  348:     */   }
/*  349:     */   
/*  350:     */   public String toString()
/*  351:     */   {
/*  352: 571 */     return this.numero;
/*  353:     */   }
/*  354:     */   
/*  355:     */   public boolean isIndicadorSaldoInicial()
/*  356:     */   {
/*  357: 580 */     return this.indicadorSaldoInicial;
/*  358:     */   }
/*  359:     */   
/*  360:     */   public void setIndicadorSaldoInicial(boolean indicadorSaldoInicial)
/*  361:     */   {
/*  362: 589 */     this.indicadorSaldoInicial = indicadorSaldoInicial;
/*  363:     */   }
/*  364:     */   
/*  365:     */   public String getBeneficiario()
/*  366:     */   {
/*  367: 598 */     return this.beneficiario;
/*  368:     */   }
/*  369:     */   
/*  370:     */   public void setBeneficiario(String beneficiario)
/*  371:     */   {
/*  372: 607 */     this.beneficiario = beneficiario;
/*  373:     */   }
/*  374:     */   
/*  375:     */   public FacturaProveedorSRI getFacturaProveedorSRI()
/*  376:     */   {
/*  377: 616 */     return this.facturaProveedorSRI;
/*  378:     */   }
/*  379:     */   
/*  380:     */   public void setFacturaProveedorSRI(FacturaProveedorSRI facturaProveedorSRI)
/*  381:     */   {
/*  382: 625 */     this.facturaProveedorSRI = facturaProveedorSRI;
/*  383:     */   }
/*  384:     */   
/*  385:     */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacionDevolucion()
/*  386:     */   {
/*  387: 634 */     return this.cuentaBancariaOrganizacionDevolucion;
/*  388:     */   }
/*  389:     */   
/*  390:     */   public void setCuentaBancariaOrganizacionDevolucion(CuentaBancariaOrganizacion cuentaBancariaOrganizacionDevolucion)
/*  391:     */   {
/*  392: 644 */     this.cuentaBancariaOrganizacionDevolucion = cuentaBancariaOrganizacionDevolucion;
/*  393:     */   }
/*  394:     */   
/*  395:     */   public Documento getDocumentoDevolucion()
/*  396:     */   {
/*  397: 653 */     return this.documentoDevolucion;
/*  398:     */   }
/*  399:     */   
/*  400:     */   public void setDocumentoDevolucion(Documento documentoDevolucion)
/*  401:     */   {
/*  402: 663 */     this.documentoDevolucion = documentoDevolucion;
/*  403:     */   }
/*  404:     */   
/*  405:     */   public Date getFechaDevolucion()
/*  406:     */   {
/*  407: 672 */     return this.fechaDevolucion;
/*  408:     */   }
/*  409:     */   
/*  410:     */   public void setFechaDevolucion(Date fechaDevolucion)
/*  411:     */   {
/*  412: 682 */     this.fechaDevolucion = fechaDevolucion;
/*  413:     */   }
/*  414:     */   
/*  415:     */   public BigDecimal getValorDevolucion()
/*  416:     */   {
/*  417: 691 */     return this.valorDevolucion;
/*  418:     */   }
/*  419:     */   
/*  420:     */   public void setValorDevolucion(BigDecimal valorDevolucion)
/*  421:     */   {
/*  422: 701 */     this.valorDevolucion = valorDevolucion;
/*  423:     */   }
/*  424:     */   
/*  425:     */   public FormaPago getFormaPagoDevolucion()
/*  426:     */   {
/*  427: 710 */     return this.formaPagoDevolucion;
/*  428:     */   }
/*  429:     */   
/*  430:     */   public void setFormaPagoDevolucion(FormaPago formaPagoDevolucion)
/*  431:     */   {
/*  432: 720 */     this.formaPagoDevolucion = formaPagoDevolucion;
/*  433:     */   }
/*  434:     */   
/*  435:     */   public Asiento getAsientoDevolucion()
/*  436:     */   {
/*  437: 729 */     return this.asientoDevolucion;
/*  438:     */   }
/*  439:     */   
/*  440:     */   public void setAsientoDevolucion(Asiento asientoDevolucion)
/*  441:     */   {
/*  442: 739 */     this.asientoDevolucion = asientoDevolucion;
/*  443:     */   }
/*  444:     */   
/*  445:     */   public String getDocumentoReferenciaDevolucion()
/*  446:     */   {
/*  447: 748 */     return this.documentoReferenciaDevolucion;
/*  448:     */   }
/*  449:     */   
/*  450:     */   public void setDocumentoReferenciaDevolucion(String documentoReferenciaDevolucion)
/*  451:     */   {
/*  452: 758 */     this.documentoReferenciaDevolucion = documentoReferenciaDevolucion;
/*  453:     */   }
/*  454:     */   
/*  455:     */   public String getDescripcionDevolucion()
/*  456:     */   {
/*  457: 767 */     return this.descripcionDevolucion;
/*  458:     */   }
/*  459:     */   
/*  460:     */   public void setDescripcionDevolucion(String descripcionDevolucion)
/*  461:     */   {
/*  462: 777 */     this.descripcionDevolucion = descripcionDevolucion;
/*  463:     */   }
/*  464:     */   
/*  465:     */   public FacturaProveedor getNotaCreditoProveedor()
/*  466:     */   {
/*  467: 786 */     return this.notaCreditoProveedor;
/*  468:     */   }
/*  469:     */   
/*  470:     */   public void setNotaCreditoProveedor(FacturaProveedor notaCreditoProveedor)
/*  471:     */   {
/*  472: 796 */     this.notaCreditoProveedor = notaCreditoProveedor;
/*  473:     */   }
/*  474:     */   
/*  475:     */   public PagoCash getPagoCash()
/*  476:     */   {
/*  477: 805 */     return this.pagoCash;
/*  478:     */   }
/*  479:     */   
/*  480:     */   public void setPagoCash(PagoCash pagoCash)
/*  481:     */   {
/*  482: 815 */     this.pagoCash = pagoCash;
/*  483:     */   }
/*  484:     */   
/*  485:     */   public Date getFechaEntregaCheque()
/*  486:     */   {
/*  487: 824 */     return this.fechaEntregaCheque;
/*  488:     */   }
/*  489:     */   
/*  490:     */   public void setFechaEntregaCheque(Date fechaEntregaCheque)
/*  491:     */   {
/*  492: 834 */     this.fechaEntregaCheque = fechaEntregaCheque;
/*  493:     */   }
/*  494:     */   
/*  495:     */   public String getUsuarioEntregaCheque()
/*  496:     */   {
/*  497: 843 */     return this.usuarioEntregaCheque;
/*  498:     */   }
/*  499:     */   
/*  500:     */   public void setUsuarioEntregaCheque(String usuarioEntregaCheque)
/*  501:     */   {
/*  502: 853 */     this.usuarioEntregaCheque = usuarioEntregaCheque;
/*  503:     */   }
/*  504:     */   
/*  505:     */   public boolean isIndicadorChequeEntregado()
/*  506:     */   {
/*  507: 862 */     return this.indicadorChequeEntregado;
/*  508:     */   }
/*  509:     */   
/*  510:     */   public void setIndicadorChequeEntregado(boolean indicadorChequeEntregado)
/*  511:     */   {
/*  512: 872 */     this.indicadorChequeEntregado = indicadorChequeEntregado;
/*  513:     */   }
/*  514:     */   
/*  515:     */   public Secuencia getSecuencia()
/*  516:     */   {
/*  517: 881 */     return this.secuencia;
/*  518:     */   }
/*  519:     */   
/*  520:     */   public void setSecuencia(Secuencia secuencia)
/*  521:     */   {
/*  522: 891 */     this.secuencia = secuencia;
/*  523:     */   }
/*  524:     */   
/*  525:     */   public CuentaContable getCuentaContableDevolucionCruce()
/*  526:     */   {
/*  527: 900 */     return this.cuentaContableDevolucionCruce;
/*  528:     */   }
/*  529:     */   
/*  530:     */   public void setCuentaContableDevolucionCruce(CuentaContable cuentaContableDevolucionCruce)
/*  531:     */   {
/*  532: 910 */     this.cuentaContableDevolucionCruce = cuentaContableDevolucionCruce;
/*  533:     */   }
/*  534:     */   
/*  535:     */   public CuentaContable getCuentaContableBancoPagoCash()
/*  536:     */   {
/*  537: 917 */     return this.cuentaContableBancoPagoCash;
/*  538:     */   }
/*  539:     */   
/*  540:     */   public void setCuentaContableBancoPagoCash(CuentaContable cuentaContableBancoPagoCash)
/*  541:     */   {
/*  542: 925 */     this.cuentaContableBancoPagoCash = cuentaContableBancoPagoCash;
/*  543:     */   }
/*  544:     */   
/*  545:     */   public Boolean getIndicadorContabilizar()
/*  546:     */   {
/*  547: 929 */     return this.indicadorContabilizar;
/*  548:     */   }
/*  549:     */   
/*  550:     */   public void setIndicadorContabilizar(Boolean indicadorContabilizar)
/*  551:     */   {
/*  552: 933 */     this.indicadorContabilizar = indicadorContabilizar;
/*  553:     */   }
/*  554:     */   
/*  555:     */   public DetalleOrdenPagoProveedor getDetalleOrdenPagoProveedor()
/*  556:     */   {
/*  557: 937 */     return this.detalleOrdenPagoProveedor;
/*  558:     */   }
/*  559:     */   
/*  560:     */   public void setDetalleOrdenPagoProveedor(DetalleOrdenPagoProveedor detalleOrdenPagoProveedor)
/*  561:     */   {
/*  562: 941 */     this.detalleOrdenPagoProveedor = detalleOrdenPagoProveedor;
/*  563:     */   }
/*  564:     */   
/*  565:     */   public boolean isIndicadorChequePosfechado()
/*  566:     */   {
/*  567: 948 */     return this.indicadorChequePosfechado;
/*  568:     */   }
/*  569:     */   
/*  570:     */   public void setIndicadorChequePosfechado(boolean indicadorChequePosfechado)
/*  571:     */   {
/*  572: 956 */     this.indicadorChequePosfechado = indicadorChequePosfechado;
/*  573:     */   }
/*  574:     */   
/*  575:     */   public Date getFechaPosfechado()
/*  576:     */   {
/*  577: 963 */     return this.fechaPosfechado;
/*  578:     */   }
/*  579:     */   
/*  580:     */   public void setFechaPosfechado(Date fechaPosfechado)
/*  581:     */   {
/*  582: 971 */     this.fechaPosfechado = fechaPosfechado;
/*  583:     */   }
/*  584:     */   
/*  585:     */   public String getNotaPosfechado()
/*  586:     */   {
/*  587: 978 */     return this.notaPosfechado;
/*  588:     */   }
/*  589:     */   
/*  590:     */   public void setNotaPosfechado(String notaPosfechado)
/*  591:     */   {
/*  592: 986 */     this.notaPosfechado = notaPosfechado;
/*  593:     */   }
/*  594:     */   
/*  595:     */   public String getPdf()
/*  596:     */   {
/*  597: 990 */     return this.pdf;
/*  598:     */   }
/*  599:     */   
/*  600:     */   public void setPdf(String pdf)
/*  601:     */   {
/*  602: 994 */     this.pdf = pdf;
/*  603:     */   }
/*  604:     */   
/*  605:     */   public PersonaResponsable getPersonaResponsable()
/*  606:     */   {
/*  607: 998 */     return this.personaResponsable;
/*  608:     */   }
/*  609:     */   
/*  610:     */   public void setPersonaResponsable(PersonaResponsable personaResponsable)
/*  611:     */   {
/*  612:1002 */     this.personaResponsable = personaResponsable;
/*  613:     */   }
/*  614:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.AnticipoProveedor
 * JD-Core Version:    0.7.0.1
 */