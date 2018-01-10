/*    1:     */ package com.asinfo.as2.entities;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.listener.EmpresaListener;
/*    4:     */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*    5:     */ import com.asinfo.as2.enumeraciones.Genero;
/*    6:     */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*    7:     */ import com.asinfo.as2.utils.validacion.email.Emails;
/*    8:     */ import java.io.Serializable;
/*    9:     */ import java.math.BigDecimal;
/*   10:     */ import java.util.ArrayList;
/*   11:     */ import java.util.List;
/*   12:     */ import javax.persistence.Column;
/*   13:     */ import javax.persistence.Entity;
/*   14:     */ import javax.persistence.EntityListeners;
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
/*   27:     */ import javax.persistence.Transient;
/*   28:     */ import javax.validation.constraints.Digits;
/*   29:     */ import javax.validation.constraints.NotNull;
/*   30:     */ import javax.validation.constraints.Size;
/*   31:     */ import org.hibernate.annotations.Fetch;
/*   32:     */ import org.hibernate.annotations.FetchMode;
/*   33:     */ 
/*   34:     */ @Entity
/*   35:     */ @Table(name="empresa", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"}), @javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "identificacion"})}, indexes={@javax.persistence.Index(columnList="id_categoria_empresa")})
/*   36:     */ @EntityListeners({EmpresaListener.class})
/*   37:     */ public class Empresa
/*   38:     */   extends EntidadBase
/*   39:     */   implements Serializable
/*   40:     */ {
/*   41:     */   private static final long serialVersionUID = 1L;
/*   42:     */   @Id
/*   43:     */   @TableGenerator(name="empresa", initialValue=0, allocationSize=50)
/*   44:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="empresa")
/*   45:     */   @Column(name="id_empresa", unique=true, nullable=false)
/*   46:     */   private int idEmpresa;
/*   47:     */   @Column(name="id_organizacion", nullable=false)
/*   48:     */   private int idOrganizacion;
/*   49:     */   @Column(name="id_sucursal", nullable=false)
/*   50:     */   private int idSucursal;
/*   51:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   52:     */   @JoinColumn(name="id_categoria_empresa", nullable=true)
/*   53:     */   private CategoriaEmpresa categoriaEmpresa;
/*   54:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   55:     */   @JoinColumn(name="id_tipo_identificacion", nullable=false)
/*   56:     */   private TipoIdentificacion tipoIdentificacion;
/*   57:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   58:     */   @JoinColumn(name="id_origen_ingresos", nullable=true)
/*   59:     */   private OrigenIngresos origenIngresos;
/*   60:     */   @Column(name="tipo_empresa", nullable=false)
/*   61:     */   @Enumerated(EnumType.ORDINAL)
/*   62:     */   private TipoEmpresa tipoEmpresa;
/*   63:     */   @Column(name="codigo", nullable=false, length=20)
/*   64:     */   @Size(min=0, max=20)
/*   65:     */   private String codigo;
/*   66:     */   @Column(name="codigo_alterno", nullable=false, length=20)
/*   67:     */   @Size(max=20)
/*   68:  95 */   private String codigoAlterno = "";
/*   69:     */   @Column(name="identificacion", nullable=false, length=20)
/*   70:     */   @NotNull
/*   71:     */   @Size(min=2, max=20)
/*   72:     */   private String identificacion;
/*   73:     */   @Column(name="nombre_comercial", nullable=false, length=200)
/*   74:     */   @NotNull
/*   75:     */   @Size(min=1, max=200)
/*   76:     */   private String nombreComercial;
/*   77:     */   @Column(name="nombre_fiscal", nullable=false, length=200)
/*   78:     */   @NotNull
/*   79:     */   @Size(min=1, max=200)
/*   80:     */   private String nombreFiscal;
/*   81:     */   @Emails
/*   82:     */   @Column(name="email1", nullable=true, length=100)
/*   83:     */   @Size(max=100)
/*   84:     */   private String email1;
/*   85:     */   @Emails
/*   86:     */   @Column(name="email2", nullable=true, length=100)
/*   87:     */   @Size(max=100)
/*   88:     */   private String email2;
/*   89:     */   @Emails
/*   90:     */   @Column(name="email3", nullable=true, length=500)
/*   91:     */   @Size(max=500)
/*   92:     */   private String email3;
/*   93:     */   @Column(name="indicador_cliente", nullable=false, insertable=true)
/*   94:     */   private boolean indicadorCliente;
/*   95:     */   @Column(name="indicador_cliente_proveedor", nullable=false, insertable=true)
/*   96:     */   private boolean indicadorClienteProveedor;
/*   97:     */   @Column(name="indicador_proveedor", nullable=false, insertable=true)
/*   98:     */   private boolean indicadorProveedor;
/*   99:     */   @Column(name="indicador_empleado", nullable=false, insertable=true)
/*  100:     */   private boolean indicadorEmpleado;
/*  101:     */   @Column(name="pagina_web", nullable=true, length=50)
/*  102:     */   @Size(max=50)
/*  103:     */   private String paginaWeb;
/*  104:     */   @Column(name="descripcion", nullable=true, length=500)
/*  105:     */   @Size(max=500)
/*  106:     */   private String descripcion;
/*  107:     */   @Column(name="cuenta_facebook", nullable=true, length=50)
/*  108:     */   @Size(max=50)
/*  109:     */   private String cuentaFacebook;
/*  110:     */   @Column(name="cuenta_twitter", nullable=true, length=50)
/*  111:     */   @Size(max=50)
/*  112:     */   private String cuentaTwitter;
/*  113:     */   @Column(name="activo", nullable=false)
/*  114:     */   private boolean activo;
/*  115:     */   @Column(name="id_dispositivo_sincronizacion", nullable=true)
/*  116:     */   private Integer idDispositivoSincronizacion;
/*  117:     */   @Column(name="genero", nullable=true)
/*  118:     */   @Enumerated(EnumType.ORDINAL)
/*  119:     */   private Genero genero;
/*  120:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  121:     */   @JoinColumn(name="id_estado_civil", nullable=true)
/*  122:     */   private EstadoCivil estadoCivil;
/*  123:     */   @NotNull
/*  124:     */   @Column(name="texto_busqueda", nullable=true, length=1000)
/*  125:     */   @Size(max=1000)
/*  126:     */   private String textoBusqueda;
/*  127:     */   @Column(name="latitud", nullable=true, precision=12, scale=2)
/*  128:     */   @Digits(integer=2, fraction=6)
/*  129:     */   private BigDecimal latitud;
/*  130:     */   @Column(name="longitud", nullable=true, precision=12, scale=2)
/*  131:     */   @Digits(integer=3, fraction=6)
/*  132:     */   private BigDecimal longitud;
/*  133:     */   @OneToOne(mappedBy="empresa", fetch=FetchType.LAZY, optional=true)
/*  134:     */   @Fetch(FetchMode.JOIN)
/*  135:     */   private Proveedor proveedor;
/*  136:     */   @OneToOne(mappedBy="empresa", fetch=FetchType.LAZY, optional=true)
/*  137:     */   @Fetch(FetchMode.JOIN)
/*  138:     */   private Cliente cliente;
/*  139:     */   @OneToOne(mappedBy="empresa", fetch=FetchType.LAZY, optional=true)
/*  140:     */   @Fetch(FetchMode.JOIN)
/*  141:     */   private Empleado empleado;
/*  142:     */   @OneToMany(mappedBy="empresa", fetch=FetchType.LAZY)
/*  143: 196 */   private List<DireccionEmpresa> direcciones = new ArrayList();
/*  144:     */   @OneToMany(mappedBy="empresa", fetch=FetchType.LAZY)
/*  145: 199 */   private List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRI = new ArrayList();
/*  146:     */   @OneToMany(mappedBy="empresa", fetch=FetchType.LAZY)
/*  147: 202 */   private List<CuentaBancariaEmpresa> listaCuentaBancariaEmpresa = new ArrayList();
/*  148:     */   @OneToMany(mappedBy="empresa", fetch=FetchType.LAZY)
/*  149: 205 */   private List<RecargoListaPreciosCliente> listaRecargoListaPreciosCliente = new ArrayList();
/*  150:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empresaPadre")
/*  151: 208 */   private List<Subempresa> listaSubempresa = new ArrayList();
/*  152:     */   @OneToMany(mappedBy="empresa", fetch=FetchType.LAZY)
/*  153: 211 */   private List<FormaPagoSRI> listaFormaPagoSRI = new ArrayList();
/*  154:     */   @OneToMany(mappedBy="empresa", fetch=FetchType.LAZY)
/*  155: 214 */   private List<Contacto> listaContacto = new ArrayList();
/*  156:     */   @Column(name="indicador_entidad_publica", nullable=false)
/*  157:     */   @NotNull
/*  158:     */   private boolean indicadorEntidadPublica;
/*  159:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  160:     */   @JoinColumn(name="id_conjunto_atributo_cliente", nullable=true)
/*  161:     */   private ConjuntoAtributo conjuntoAtributoCliente;
/*  162:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  163:     */   @JoinColumn(name="id_conjunto_atributo_proveedor", nullable=true)
/*  164:     */   private ConjuntoAtributo conjuntoAtributoProveedor;
/*  165:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empresa")
/*  166: 229 */   private List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizadoEmpleado = new ArrayList();
/*  167:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empresa")
/*  168: 232 */   private List<EmpresaAtributo> listaEmpresaAtributo = new ArrayList();
/*  169:     */   @Transient
/*  170:     */   @Emails
/*  171:     */   private String envioEmails;
/*  172:     */   @Transient
/*  173:     */   private DetalleVersionListaPrecios detalleVersionListaPrecios;
/*  174:     */   @Transient
/*  175: 241 */   private List<ProductoUltimaCompra> listaProductoUltimaCompra = new ArrayList();
/*  176:     */   @Transient
/*  177:     */   private boolean indicadorProveedorSeleccionado;
/*  178:     */   @Transient
/*  179:     */   private Boolean migracionExisteIdentificacion;
/*  180:     */   
/*  181:     */   public int getId()
/*  182:     */   {
/*  183: 257 */     return this.idEmpresa;
/*  184:     */   }
/*  185:     */   
/*  186:     */   public Empresa() {}
/*  187:     */   
/*  188:     */   public Empresa(String identificacion)
/*  189:     */   {
/*  190: 270 */     this.codigo = identificacion;
/*  191: 271 */     this.identificacion = identificacion;
/*  192:     */   }
/*  193:     */   
/*  194:     */   public Empresa(int idEmpresa, String codigo, String identificacion, String nombreFiscal, String nombreComercial)
/*  195:     */   {
/*  196: 276 */     this.idEmpresa = idEmpresa;
/*  197: 277 */     this.codigo = codigo;
/*  198: 278 */     this.identificacion = identificacion;
/*  199: 279 */     this.nombreFiscal = nombreFiscal;
/*  200: 280 */     this.nombreComercial = nombreComercial;
/*  201:     */   }
/*  202:     */   
/*  203:     */   public Empresa(int idEmpresa, String codigo, String identificacion, String nombreFiscal, String nombreComercial, Boolean excentoImpuestos)
/*  204:     */   {
/*  205: 287 */     new Empresa(idEmpresa, codigo, identificacion, nombreFiscal, nombreComercial);
/*  206:     */   }
/*  207:     */   
/*  208:     */   public int getIdEmpresa()
/*  209:     */   {
/*  210: 296 */     return this.idEmpresa;
/*  211:     */   }
/*  212:     */   
/*  213:     */   public void setIdEmpresa(int idEmpresa)
/*  214:     */   {
/*  215: 306 */     this.idEmpresa = idEmpresa;
/*  216:     */   }
/*  217:     */   
/*  218:     */   public int getIdOrganizacion()
/*  219:     */   {
/*  220: 315 */     return this.idOrganizacion;
/*  221:     */   }
/*  222:     */   
/*  223:     */   public void setIdOrganizacion(int idOrganizacion)
/*  224:     */   {
/*  225: 325 */     this.idOrganizacion = idOrganizacion;
/*  226:     */   }
/*  227:     */   
/*  228:     */   public int getIdSucursal()
/*  229:     */   {
/*  230: 334 */     return this.idSucursal;
/*  231:     */   }
/*  232:     */   
/*  233:     */   public void setIdSucursal(int idSucursal)
/*  234:     */   {
/*  235: 344 */     this.idSucursal = idSucursal;
/*  236:     */   }
/*  237:     */   
/*  238:     */   public CategoriaEmpresa getCategoriaEmpresa()
/*  239:     */   {
/*  240: 353 */     return this.categoriaEmpresa;
/*  241:     */   }
/*  242:     */   
/*  243:     */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/*  244:     */   {
/*  245: 363 */     this.categoriaEmpresa = categoriaEmpresa;
/*  246:     */   }
/*  247:     */   
/*  248:     */   public TipoIdentificacion getTipoIdentificacion()
/*  249:     */   {
/*  250: 372 */     return this.tipoIdentificacion;
/*  251:     */   }
/*  252:     */   
/*  253:     */   public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/*  254:     */   {
/*  255: 382 */     this.tipoIdentificacion = tipoIdentificacion;
/*  256:     */   }
/*  257:     */   
/*  258:     */   public String getCodigo()
/*  259:     */   {
/*  260: 391 */     return this.codigo;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public void setCodigo(String codigo)
/*  264:     */   {
/*  265: 401 */     this.codigo = codigo;
/*  266:     */   }
/*  267:     */   
/*  268:     */   public String getIdentificacion()
/*  269:     */   {
/*  270: 410 */     return this.identificacion;
/*  271:     */   }
/*  272:     */   
/*  273:     */   public void setIdentificacion(String identificacion)
/*  274:     */   {
/*  275: 420 */     this.identificacion = identificacion;
/*  276:     */   }
/*  277:     */   
/*  278:     */   public String getNombreComercial()
/*  279:     */   {
/*  280: 429 */     return this.nombreComercial;
/*  281:     */   }
/*  282:     */   
/*  283:     */   public void setNombreComercial(String nombreComercial)
/*  284:     */   {
/*  285: 439 */     this.nombreComercial = nombreComercial;
/*  286:     */   }
/*  287:     */   
/*  288:     */   public String getNombreFiscal()
/*  289:     */   {
/*  290: 448 */     return this.nombreFiscal;
/*  291:     */   }
/*  292:     */   
/*  293:     */   public void setNombreFiscal(String nombreFiscal)
/*  294:     */   {
/*  295: 458 */     this.nombreFiscal = nombreFiscal;
/*  296:     */   }
/*  297:     */   
/*  298:     */   public String getEmail1()
/*  299:     */   {
/*  300: 467 */     return this.email1;
/*  301:     */   }
/*  302:     */   
/*  303:     */   public void setEmail1(String email1)
/*  304:     */   {
/*  305: 477 */     this.email1 = email1;
/*  306:     */   }
/*  307:     */   
/*  308:     */   public String getEmail2()
/*  309:     */   {
/*  310: 486 */     return this.email2;
/*  311:     */   }
/*  312:     */   
/*  313:     */   public void setEmail2(String email2)
/*  314:     */   {
/*  315: 496 */     this.email2 = email2;
/*  316:     */   }
/*  317:     */   
/*  318:     */   public boolean isIndicadorCliente()
/*  319:     */   {
/*  320: 505 */     return this.indicadorCliente;
/*  321:     */   }
/*  322:     */   
/*  323:     */   public void setIndicadorCliente(boolean indicadorCliente)
/*  324:     */   {
/*  325: 515 */     this.indicadorCliente = indicadorCliente;
/*  326:     */   }
/*  327:     */   
/*  328:     */   public boolean isIndicadorProveedor()
/*  329:     */   {
/*  330: 524 */     return this.indicadorProveedor;
/*  331:     */   }
/*  332:     */   
/*  333:     */   public void setIndicadorProveedor(boolean indicadorProveedor)
/*  334:     */   {
/*  335: 534 */     this.indicadorProveedor = indicadorProveedor;
/*  336:     */   }
/*  337:     */   
/*  338:     */   public String getPaginaWeb()
/*  339:     */   {
/*  340: 543 */     return this.paginaWeb;
/*  341:     */   }
/*  342:     */   
/*  343:     */   public void setPaginaWeb(String paginaWeb)
/*  344:     */   {
/*  345: 553 */     this.paginaWeb = paginaWeb;
/*  346:     */   }
/*  347:     */   
/*  348:     */   public String getDescripcion()
/*  349:     */   {
/*  350: 562 */     return this.descripcion;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public void setDescripcion(String descripcion)
/*  354:     */   {
/*  355: 572 */     this.descripcion = descripcion;
/*  356:     */   }
/*  357:     */   
/*  358:     */   public boolean isActivo()
/*  359:     */   {
/*  360: 581 */     return this.activo;
/*  361:     */   }
/*  362:     */   
/*  363:     */   public void setActivo(boolean activo)
/*  364:     */   {
/*  365: 591 */     this.activo = activo;
/*  366:     */   }
/*  367:     */   
/*  368:     */   public Proveedor getProveedor()
/*  369:     */   {
/*  370: 600 */     return this.proveedor;
/*  371:     */   }
/*  372:     */   
/*  373:     */   public void setProveedor(Proveedor proveedor)
/*  374:     */   {
/*  375: 610 */     this.proveedor = proveedor;
/*  376:     */   }
/*  377:     */   
/*  378:     */   public Cliente getCliente()
/*  379:     */   {
/*  380: 619 */     return this.cliente;
/*  381:     */   }
/*  382:     */   
/*  383:     */   public void setCliente(Cliente cliente)
/*  384:     */   {
/*  385: 629 */     this.cliente = cliente;
/*  386:     */   }
/*  387:     */   
/*  388:     */   public TipoEmpresa getTipoEmpresa()
/*  389:     */   {
/*  390: 638 */     return this.tipoEmpresa;
/*  391:     */   }
/*  392:     */   
/*  393:     */   public void setTipoEmpresa(TipoEmpresa tipoEmpresa)
/*  394:     */   {
/*  395: 648 */     this.tipoEmpresa = tipoEmpresa;
/*  396:     */   }
/*  397:     */   
/*  398:     */   public boolean isIndicadorEmpleado()
/*  399:     */   {
/*  400: 657 */     return this.indicadorEmpleado;
/*  401:     */   }
/*  402:     */   
/*  403:     */   public void setIndicadorEmpleado(boolean indicadorEmpleado)
/*  404:     */   {
/*  405: 667 */     this.indicadorEmpleado = indicadorEmpleado;
/*  406:     */   }
/*  407:     */   
/*  408:     */   public List<DireccionEmpresa> getDirecciones()
/*  409:     */   {
/*  410: 676 */     return this.direcciones;
/*  411:     */   }
/*  412:     */   
/*  413:     */   public void setDirecciones(List<DireccionEmpresa> direcciones)
/*  414:     */   {
/*  415: 686 */     this.direcciones = direcciones;
/*  416:     */   }
/*  417:     */   
/*  418:     */   public List<AutorizacionProveedorSRI> getListaAutorizacionProveedorSRI()
/*  419:     */   {
/*  420: 695 */     return this.listaAutorizacionProveedorSRI;
/*  421:     */   }
/*  422:     */   
/*  423:     */   public void setListaAutorizacionProveedorSRI(List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRI)
/*  424:     */   {
/*  425: 705 */     this.listaAutorizacionProveedorSRI = listaAutorizacionProveedorSRI;
/*  426:     */   }
/*  427:     */   
/*  428:     */   public String toString()
/*  429:     */   {
/*  430: 715 */     return this.nombreFiscal;
/*  431:     */   }
/*  432:     */   
/*  433:     */   public Empleado getEmpleado()
/*  434:     */   {
/*  435: 719 */     return this.empleado;
/*  436:     */   }
/*  437:     */   
/*  438:     */   public void setEmpleado(Empleado empleado)
/*  439:     */   {
/*  440: 723 */     this.empleado = empleado;
/*  441:     */   }
/*  442:     */   
/*  443:     */   public List<CuentaBancariaEmpresa> getListaCuentaBancariaEmpresa()
/*  444:     */   {
/*  445: 732 */     return this.listaCuentaBancariaEmpresa;
/*  446:     */   }
/*  447:     */   
/*  448:     */   public void setListaCuentaBancariaEmpresa(List<CuentaBancariaEmpresa> listaCuentaBancariaEmpresa)
/*  449:     */   {
/*  450: 742 */     this.listaCuentaBancariaEmpresa = listaCuentaBancariaEmpresa;
/*  451:     */   }
/*  452:     */   
/*  453:     */   public void setEliminado(boolean eliminado)
/*  454:     */   {
/*  455: 752 */     super.setEliminado(eliminado);
/*  456: 753 */     if (this.proveedor != null) {
/*  457: 754 */       this.proveedor.setEliminado(eliminado);
/*  458:     */     }
/*  459: 757 */     if (this.cliente != null) {
/*  460: 758 */       this.cliente.setEliminado(eliminado);
/*  461:     */     }
/*  462: 761 */     if (this.empleado != null) {
/*  463: 762 */       this.empleado.setEliminado(eliminado);
/*  464:     */     }
/*  465: 765 */     if (this.listaCuentaBancariaEmpresa != null) {
/*  466: 766 */       for (CuentaBancariaEmpresa cuentaBancariaEmpresa : this.listaCuentaBancariaEmpresa) {
/*  467: 767 */         cuentaBancariaEmpresa.setEliminado(eliminado);
/*  468:     */       }
/*  469:     */     }
/*  470: 772 */     if (this.direcciones != null) {
/*  471: 773 */       for (DireccionEmpresa direccionEmpresa : this.direcciones)
/*  472:     */       {
/*  473: 774 */         direccionEmpresa.setEliminado(true);
/*  474: 775 */         direccionEmpresa.getUbicacion().setEliminado(true);
/*  475:     */       }
/*  476:     */     }
/*  477: 779 */     if (this.listaAutorizacionProveedorSRI != null) {
/*  478: 780 */       for (AutorizacionProveedorSRI autorizacionProveedorSRI : this.listaAutorizacionProveedorSRI) {
/*  479: 781 */         autorizacionProveedorSRI.setEliminado(true);
/*  480:     */       }
/*  481:     */     }
/*  482:     */   }
/*  483:     */   
/*  484:     */   public String getCodigoAlterno()
/*  485:     */   {
/*  486: 793 */     return this.codigoAlterno;
/*  487:     */   }
/*  488:     */   
/*  489:     */   public void setCodigoAlterno(String codigoAlterno)
/*  490:     */   {
/*  491: 803 */     this.codigoAlterno = codigoAlterno;
/*  492:     */   }
/*  493:     */   
/*  494:     */   public boolean isIndicadorClienteProveedor()
/*  495:     */   {
/*  496: 810 */     return this.indicadorClienteProveedor;
/*  497:     */   }
/*  498:     */   
/*  499:     */   public void setIndicadorClienteProveedor(boolean indicadorClienteProveedor)
/*  500:     */   {
/*  501: 818 */     this.indicadorClienteProveedor = indicadorClienteProveedor;
/*  502:     */   }
/*  503:     */   
/*  504:     */   public String getCuentaFacebook()
/*  505:     */   {
/*  506: 827 */     return this.cuentaFacebook;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public void setCuentaFacebook(String cuentaFacebook)
/*  510:     */   {
/*  511: 837 */     this.cuentaFacebook = cuentaFacebook;
/*  512:     */   }
/*  513:     */   
/*  514:     */   public String getCuentaTwitter()
/*  515:     */   {
/*  516: 846 */     return this.cuentaTwitter;
/*  517:     */   }
/*  518:     */   
/*  519:     */   public void setCuentaTwitter(String cuentaTwitter)
/*  520:     */   {
/*  521: 856 */     this.cuentaTwitter = cuentaTwitter;
/*  522:     */   }
/*  523:     */   
/*  524:     */   public List<RecargoListaPreciosCliente> getListaRecargoListaPreciosCliente()
/*  525:     */   {
/*  526: 863 */     return this.listaRecargoListaPreciosCliente;
/*  527:     */   }
/*  528:     */   
/*  529:     */   public void setListaRecargoListaPreciosCliente(List<RecargoListaPreciosCliente> listaRecargoListaPreciosCliente)
/*  530:     */   {
/*  531: 871 */     this.listaRecargoListaPreciosCliente = listaRecargoListaPreciosCliente;
/*  532:     */   }
/*  533:     */   
/*  534:     */   public List<Subempresa> getListaSubempresa()
/*  535:     */   {
/*  536: 880 */     return this.listaSubempresa;
/*  537:     */   }
/*  538:     */   
/*  539:     */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/*  540:     */   {
/*  541: 890 */     this.listaSubempresa = listaSubempresa;
/*  542:     */   }
/*  543:     */   
/*  544:     */   public List<FormaPagoSRI> getListaFormaPagoSRI()
/*  545:     */   {
/*  546: 894 */     return this.listaFormaPagoSRI;
/*  547:     */   }
/*  548:     */   
/*  549:     */   public void setListaFormaPagoSRI(List<FormaPagoSRI> listaFormaPagoSRI)
/*  550:     */   {
/*  551: 898 */     this.listaFormaPagoSRI = listaFormaPagoSRI;
/*  552:     */   }
/*  553:     */   
/*  554:     */   public String getTextoBusqueda()
/*  555:     */   {
/*  556: 907 */     return this.textoBusqueda;
/*  557:     */   }
/*  558:     */   
/*  559:     */   public void setTextoBusqueda(String textoBusqueda)
/*  560:     */   {
/*  561: 917 */     this.textoBusqueda = textoBusqueda;
/*  562:     */   }
/*  563:     */   
/*  564:     */   public List<Contacto> getListaContacto()
/*  565:     */   {
/*  566: 921 */     return this.listaContacto;
/*  567:     */   }
/*  568:     */   
/*  569:     */   public void setListaContacto(List<Contacto> listaContacto)
/*  570:     */   {
/*  571: 925 */     this.listaContacto = listaContacto;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public OrigenIngresos getOrigenIngresos()
/*  575:     */   {
/*  576: 929 */     return this.origenIngresos;
/*  577:     */   }
/*  578:     */   
/*  579:     */   public void setOrigenIngresos(OrigenIngresos origenIngresos)
/*  580:     */   {
/*  581: 933 */     this.origenIngresos = origenIngresos;
/*  582:     */   }
/*  583:     */   
/*  584:     */   public Genero getGenero()
/*  585:     */   {
/*  586: 937 */     return this.genero;
/*  587:     */   }
/*  588:     */   
/*  589:     */   public void setGenero(Genero genero)
/*  590:     */   {
/*  591: 941 */     this.genero = genero;
/*  592:     */   }
/*  593:     */   
/*  594:     */   public EstadoCivil getEstadoCivil()
/*  595:     */   {
/*  596: 945 */     return this.estadoCivil;
/*  597:     */   }
/*  598:     */   
/*  599:     */   public void setEstadoCivil(EstadoCivil estadoCivil)
/*  600:     */   {
/*  601: 949 */     this.estadoCivil = estadoCivil;
/*  602:     */   }
/*  603:     */   
/*  604:     */   public boolean isIndicadorEntidadPublica()
/*  605:     */   {
/*  606: 953 */     return this.indicadorEntidadPublica;
/*  607:     */   }
/*  608:     */   
/*  609:     */   public void setIndicadorEntidadPublica(boolean indicadorEntidadPublica)
/*  610:     */   {
/*  611: 957 */     this.indicadorEntidadPublica = indicadorEntidadPublica;
/*  612:     */   }
/*  613:     */   
/*  614:     */   public List<DetalleDocumentoDigitalizado> getListaDetalleDocumentoDigitalizadoEmpleado()
/*  615:     */   {
/*  616: 961 */     return this.listaDetalleDocumentoDigitalizadoEmpleado;
/*  617:     */   }
/*  618:     */   
/*  619:     */   public void setListaDetalleDocumentoDigitalizadoEmpleado(List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizadoEmpleado)
/*  620:     */   {
/*  621: 965 */     this.listaDetalleDocumentoDigitalizadoEmpleado = listaDetalleDocumentoDigitalizadoEmpleado;
/*  622:     */   }
/*  623:     */   
/*  624:     */   public List<EmpresaAtributo> getListaEmpresaAtributo()
/*  625:     */   {
/*  626: 972 */     return this.listaEmpresaAtributo;
/*  627:     */   }
/*  628:     */   
/*  629:     */   public void setListaEmpresaAtributo(List<EmpresaAtributo> listaEmpresaAtributo)
/*  630:     */   {
/*  631: 980 */     this.listaEmpresaAtributo = listaEmpresaAtributo;
/*  632:     */   }
/*  633:     */   
/*  634:     */   public ConjuntoAtributo getConjuntoAtributoCliente()
/*  635:     */   {
/*  636: 987 */     return this.conjuntoAtributoCliente;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void setConjuntoAtributoCliente(ConjuntoAtributo conjuntoAtributoCliente)
/*  640:     */   {
/*  641: 995 */     this.conjuntoAtributoCliente = conjuntoAtributoCliente;
/*  642:     */   }
/*  643:     */   
/*  644:     */   public ConjuntoAtributo getConjuntoAtributoProveedor()
/*  645:     */   {
/*  646:1002 */     return this.conjuntoAtributoProveedor;
/*  647:     */   }
/*  648:     */   
/*  649:     */   public void setConjuntoAtributoProveedor(ConjuntoAtributo conjuntoAtributoProveedor)
/*  650:     */   {
/*  651:1010 */     this.conjuntoAtributoProveedor = conjuntoAtributoProveedor;
/*  652:     */   }
/*  653:     */   
/*  654:     */   public String getEmail3()
/*  655:     */   {
/*  656:1017 */     return this.email3;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void setEmail3(String email3)
/*  660:     */   {
/*  661:1025 */     this.email3 = email3;
/*  662:     */   }
/*  663:     */   
/*  664:     */   public String getEnvioEmails()
/*  665:     */   {
/*  666:1029 */     return this.envioEmails;
/*  667:     */   }
/*  668:     */   
/*  669:     */   public void setEnvioEmails(String envioEmails)
/*  670:     */   {
/*  671:1033 */     this.envioEmails = envioEmails;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public DetalleVersionListaPrecios getDetalleVersionListaPrecios()
/*  675:     */   {
/*  676:1037 */     return this.detalleVersionListaPrecios;
/*  677:     */   }
/*  678:     */   
/*  679:     */   public void setDetalleVersionListaPrecios(DetalleVersionListaPrecios detalleVersionListaPrecios)
/*  680:     */   {
/*  681:1041 */     this.detalleVersionListaPrecios = detalleVersionListaPrecios;
/*  682:     */   }
/*  683:     */   
/*  684:     */   public boolean isIndicadorProveedorSeleccionado()
/*  685:     */   {
/*  686:1045 */     return this.indicadorProveedorSeleccionado;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public void setIndicadorProveedorSeleccionado(boolean indicadorProveedorSeleccionado)
/*  690:     */   {
/*  691:1049 */     this.indicadorProveedorSeleccionado = indicadorProveedorSeleccionado;
/*  692:     */   }
/*  693:     */   
/*  694:     */   public Integer getIdDispositivoSincronizacion()
/*  695:     */   {
/*  696:1053 */     return this.idDispositivoSincronizacion;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/*  700:     */   {
/*  701:1057 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/*  702:     */   }
/*  703:     */   
/*  704:     */   public BigDecimal getLatitud()
/*  705:     */   {
/*  706:1061 */     return this.latitud;
/*  707:     */   }
/*  708:     */   
/*  709:     */   public void setLatitud(BigDecimal latitud)
/*  710:     */   {
/*  711:1065 */     this.latitud = latitud;
/*  712:     */   }
/*  713:     */   
/*  714:     */   public BigDecimal getLongitud()
/*  715:     */   {
/*  716:1069 */     return this.longitud;
/*  717:     */   }
/*  718:     */   
/*  719:     */   public void setLongitud(BigDecimal longitud)
/*  720:     */   {
/*  721:1073 */     this.longitud = longitud;
/*  722:     */   }
/*  723:     */   
/*  724:     */   public Boolean getMigracionExisteIdentificacion()
/*  725:     */   {
/*  726:1077 */     return this.migracionExisteIdentificacion;
/*  727:     */   }
/*  728:     */   
/*  729:     */   public void setMigracionExisteIdentificacion(Boolean migracionExisteIdentificacion)
/*  730:     */   {
/*  731:1081 */     this.migracionExisteIdentificacion = migracionExisteIdentificacion;
/*  732:     */   }
/*  733:     */   
/*  734:     */   public List<ProductoUltimaCompra> getListaProductoUltimaCompra()
/*  735:     */   {
/*  736:1085 */     return this.listaProductoUltimaCompra;
/*  737:     */   }
/*  738:     */   
/*  739:     */   public void setListaProductoUltimaCompra(List<ProductoUltimaCompra> listaProductoUltimaCompra)
/*  740:     */   {
/*  741:1089 */     this.listaProductoUltimaCompra = listaProductoUltimaCompra;
/*  742:     */   }
/*  743:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Empresa
 * JD-Core Version:    0.7.0.1
 */