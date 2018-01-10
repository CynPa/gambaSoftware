/*    1:     */ package com.asinfo.as2.entities;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.listener.EmpleadoListener;
/*    4:     */ import com.asinfo.as2.entities.nomina.asistencia.GrupoTrabajo;
/*    5:     */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/*    6:     */ import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
/*    7:     */ import com.asinfo.as2.enumeraciones.Genero;
/*    8:     */ import com.asinfo.as2.enumeraciones.TipoSangre;
/*    9:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   10:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   11:     */ import java.io.Serializable;
/*   12:     */ import java.math.BigDecimal;
/*   13:     */ import java.util.ArrayList;
/*   14:     */ import java.util.Date;
/*   15:     */ import java.util.List;
/*   16:     */ import javax.persistence.Column;
/*   17:     */ import javax.persistence.Entity;
/*   18:     */ import javax.persistence.EntityListeners;
/*   19:     */ import javax.persistence.EnumType;
/*   20:     */ import javax.persistence.Enumerated;
/*   21:     */ import javax.persistence.FetchType;
/*   22:     */ import javax.persistence.GeneratedValue;
/*   23:     */ import javax.persistence.GenerationType;
/*   24:     */ import javax.persistence.Id;
/*   25:     */ import javax.persistence.JoinColumn;
/*   26:     */ import javax.persistence.ManyToOne;
/*   27:     */ import javax.persistence.OneToMany;
/*   28:     */ import javax.persistence.OneToOne;
/*   29:     */ import javax.persistence.Table;
/*   30:     */ import javax.persistence.TableGenerator;
/*   31:     */ import javax.persistence.Temporal;
/*   32:     */ import javax.persistence.TemporalType;
/*   33:     */ import javax.persistence.Transient;
/*   34:     */ import javax.validation.constraints.Digits;
/*   35:     */ import javax.validation.constraints.Min;
/*   36:     */ import javax.validation.constraints.NotNull;
/*   37:     */ import javax.validation.constraints.Size;
/*   38:     */ 
/*   39:     */ @Entity
/*   40:     */ @Table(name="empleado", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_empresa"})})
/*   41:     */ @EntityListeners({EmpleadoListener.class})
/*   42:     */ public class Empleado
/*   43:     */   extends EntidadBase
/*   44:     */   implements Serializable
/*   45:     */ {
/*   46:     */   private static final long serialVersionUID = -5067816832914433772L;
/*   47:     */   @Id
/*   48:     */   @TableGenerator(name="empleado", initialValue=0, allocationSize=50)
/*   49:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="empleado")
/*   50:     */   @Column(name="id_empleado", unique=true, nullable=false)
/*   51:     */   private int idEmpleado;
/*   52:     */   @Column(name="id_organizacion", nullable=false)
/*   53:     */   private int idOrganizacion;
/*   54:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   55:     */   @JoinColumn(name="id_sucursal", nullable=true)
/*   56:     */   private Sucursal sucursal;
/*   57:     */   @OneToOne(fetch=FetchType.LAZY)
/*   58:     */   @JoinColumn(name="id_empresa")
/*   59:     */   private Empresa empresa;
/*   60:     */   @Column(name="activo", nullable=false)
/*   61:     */   private boolean activo;
/*   62:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   63:     */   @JoinColumn(name="id_estado_civil", nullable=true)
/*   64:     */   private EstadoCivil estadoCivil;
/*   65:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   66:     */   @JoinColumn(name="id_departamento", nullable=true)
/*   67:     */   private Departamento departamento;
/*   68:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   69:     */   @JoinColumn(name="id_cargo_empleado", nullable=true)
/*   70:     */   private CargoEmpleado cargoEmpleado;
/*   71:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   72:     */   @JoinColumn(name="id_tipo_discapacidad", nullable=true)
/*   73:     */   private TipoDiscapacidad tipoDiscapacidad;
/*   74:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   75:     */   @JoinColumn(name="id_titulo", nullable=true)
/*   76:     */   private Titulo titulo;
/*   77:     */   @Column(name="fecha_vencimiento_contrato", nullable=true)
/*   78:     */   @Temporal(TemporalType.DATE)
/*   79:     */   private Date fechaVencimientoContrato;
/*   80:     */   @Column(name="genero", nullable=false)
/*   81:     */   @Enumerated(EnumType.ORDINAL)
/*   82:     */   private Genero genero;
/*   83:     */   @Column(name="forma_pago_rol", nullable=false)
/*   84:     */   @Enumerated(EnumType.ORDINAL)
/*   85:     */   @NotNull
/*   86:     */   private FormaPagoEmpleado formaPagoEmpleado;
/*   87:     */   @Column(name="porcentaje_discapacidad", nullable=true, precision=12, scale=2)
/*   88:     */   @Digits(integer=12, fraction=2)
/*   89:     */   @Min(0L)
/*   90:     */   private BigDecimal porcentajeDiscapacidad;
/*   91:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   92:     */   @JoinColumn(name="id_tipo_contrato", nullable=true)
/*   93:     */   private TipoContrato tipoContrato;
/*   94:     */   @Column(name="imagen", nullable=true, length=50)
/*   95:     */   @Size(max=50)
/*   96:     */   private String imagen;
/*   97:     */   @Column(name="fecha_nacimiento", nullable=true)
/*   98:     */   private Date fechaNacimiento;
/*   99:     */   @Column(name="lugar_nacimiento", nullable=false, length=50)
/*  100:     */   @NotNull
/*  101:     */   @Size(min=2, max=50)
/*  102:     */   private String lugarNacimiento;
/*  103:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empleado")
/*  104: 143 */   private List<CargaEmpleado> listaCargaEmpleado = new ArrayList();
/*  105:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empleado")
/*  106: 146 */   private List<RubroEmpleado> listaRubroEmpleado = new ArrayList();
/*  107:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empleado")
/*  108: 149 */   private List<PagoRolEmpleado> listaPagoRolEmpleado = new ArrayList();
/*  109:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empleado")
/*  110: 152 */   private List<FormacionAcademica> listaFormacionAcademica = new ArrayList();
/*  111:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empleado")
/*  112: 155 */   private List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizadoEmpleado = new ArrayList();
/*  113:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empleado")
/*  114: 158 */   private List<LlamadoAtencion> listaLlamadoAtencion = new ArrayList();
/*  115:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empleado")
/*  116: 161 */   private List<Prestamo> listaPrestamo = new ArrayList();
/*  117:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empleado")
/*  118: 164 */   private List<HistoricoEmpleado> listaHistoricoEmpleado = new ArrayList();
/*  119:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="empleado")
/*  120: 167 */   private List<DotacionEmpleado> listaDotacion = new ArrayList();
/*  121:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  122:     */   @JoinColumn(name="id_pais", nullable=false)
/*  123:     */   private Pais pais;
/*  124:     */   @Column(name="calificacion", nullable=true, length=50)
/*  125:     */   @Size(max=50)
/*  126:     */   private String calificacion;
/*  127:     */   @Column(name="memos", nullable=true)
/*  128:     */   @Digits(integer=12, fraction=2)
/*  129:     */   @Min(0L)
/*  130:     */   private int memos;
/*  131:     */   @Column(name="numero_cargas_activas", nullable=true)
/*  132:     */   private int numeroCargasActivas;
/*  133:     */   @Column(name="indicador_pago_cash", nullable=false)
/*  134:     */   private boolean indicadorPagoCash;
/*  135:     */   @Column(name="nombres", nullable=false, length=100)
/*  136:     */   @NotNull
/*  137:     */   @Size(min=1, max=100)
/*  138:     */   private String nombres;
/*  139:     */   @Column(name="apellidos", nullable=false, length=100)
/*  140:     */   @NotNull
/*  141:     */   @Size(min=1, max=100)
/*  142:     */   private String apellidos;
/*  143:     */   @Column(name="filtro", nullable=true, length=250)
/*  144:     */   private String filtro;
/*  145:     */   @Column(name="codigo_sectorial", nullable=true, length=20)
/*  146:     */   @Size(max=20)
/*  147:     */   private String codigoSectorial;
/*  148:     */   @Enumerated(EnumType.ORDINAL)
/*  149:     */   @Column(name="tipo_sangre", nullable=true)
/*  150:     */   private TipoSangre tipoSangre;
/*  151:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  152:     */   @JoinColumn(name="id_horario_empleado", nullable=true)
/*  153:     */   private HorarioEmpleado horarioEmpleado;
/*  154:     */   @Transient
/*  155:     */   private boolean traCargadoRubros;
/*  156:     */   @Transient
/*  157:     */   private long traNumeroCargasFamiliares;
/*  158:     */   @Transient
/*  159:     */   private BigDecimal traNumeroDiasTrabajados;
/*  160:     */   @Transient
/*  161:     */   private BigDecimal traNumeroDiasRealesTrabajados;
/*  162:     */   @Transient
/*  163:     */   private long traNumeroDiasFalta;
/*  164:     */   @Transient
/*  165:     */   private boolean aplicarRubroEmpleado;
/*  166:     */   @Column(name="residencia_trabajador", nullable=true, length=10)
/*  167:     */   private String residenciaTrabajador;
/*  168:     */   @Column(name="pais_residencia_trabajador", nullable=true, length=10)
/*  169:     */   private String paisResidenciaTrabajador;
/*  170:     */   @Column(name="convenio_doble_imposicion", nullable=true, length=10)
/*  171:     */   private String convenioDobleImposicion;
/*  172:     */   @Column(name="condicion_respecto_discapacidad", nullable=true, length=10)
/*  173:     */   private String condicionRespectoDiscapacidad;
/*  174:     */   @Column(name="tipo_identificacion_sustituto_pariente", nullable=true, length=10)
/*  175:     */   private String tipoIdentificacionSustitutoPariente;
/*  176:     */   @Column(name="identificacion_sustituto_pariente", nullable=true, length=13)
/*  177:     */   private String identificacionSustitutoPariente;
/*  178:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  179:     */   @JoinColumn(name="id_centro_costo", nullable=true)
/*  180:     */   private DimensionContable centroCosto;
/*  181:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  182:     */   @JoinColumn(name="id_categoria_rubro", nullable=true)
/*  183:     */   private CategoriaRubro categoriaRubro;
/*  184:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  185:     */   @JoinColumn(name="id_grupo_trabajo", nullable=true)
/*  186:     */   private GrupoTrabajo grupoTrabajo;
/*  187:     */   @Column(name="imagen_huella_1", nullable=true, length=50)
/*  188:     */   @Size(max=50)
/*  189:     */   private String imagenHuella1;
/*  190:     */   @Column(name="imagen_huella_2", nullable=true, length=50)
/*  191:     */   @Size(max=50)
/*  192:     */   private String imagenHuella2;
/*  193:     */   @Column(name="imagen_huella_3", nullable=true, length=50)
/*  194:     */   @Size(max=50)
/*  195:     */   private String imagenHuella3;
/*  196:     */   @Column(name="imagen_huella_4", nullable=true, length=50)
/*  197:     */   @Size(max=50)
/*  198:     */   private String imagenHuella4;
/*  199:     */   @Column(name="imagen_huella_5", nullable=true, length=50)
/*  200:     */   @Size(max=50)
/*  201:     */   private String imagenHuella5;
/*  202:     */   @Column(name="imagen_huella_6", nullable=true, length=50)
/*  203:     */   @Size(max=50)
/*  204:     */   private String imagenHuella6;
/*  205:     */   @Column(name="imagen_huella_7", nullable=true, length=50)
/*  206:     */   @Size(max=50)
/*  207:     */   private String imagenHuella7;
/*  208:     */   @Column(name="imagen_huella_8", nullable=true, length=50)
/*  209:     */   @Size(max=50)
/*  210:     */   private String imagenHuella8;
/*  211:     */   @Column(name="imagen_huella_9", nullable=true, length=50)
/*  212:     */   @Size(max=50)
/*  213:     */   private String imagenHuella9;
/*  214:     */   @Column(name="imagen_huella_10", nullable=true, length=50)
/*  215:     */   @Size(max=50)
/*  216:     */   private String imagenHuella10;
/*  217:     */   @Transient
/*  218:     */   private boolean terceraEdad;
/*  219:     */   @Transient
/*  220:     */   private boolean discapacitado;
/*  221:     */   
/*  222:     */   public Empleado() {}
/*  223:     */   
/*  224:     */   public Empleado(int idEmpleado, long traNumeroCargasFamiliares, long traNumeroDiasTrabajados)
/*  225:     */   {
/*  226: 322 */     this.idEmpleado = idEmpleado;
/*  227: 323 */     this.traNumeroCargasFamiliares = traNumeroCargasFamiliares;
/*  228: 324 */     this.traNumeroDiasTrabajados = new BigDecimal(traNumeroDiasTrabajados);
/*  229:     */   }
/*  230:     */   
/*  231:     */   public Empleado(int idEmpleado, long traNumeroCargasFamiliares, long traNumeroDiasTrabajados, long traNumeroDiasFalta)
/*  232:     */   {
/*  233: 328 */     this(idEmpleado, traNumeroCargasFamiliares, traNumeroDiasTrabajados);
/*  234: 329 */     this.traNumeroDiasFalta = traNumeroDiasFalta;
/*  235:     */   }
/*  236:     */   
/*  237:     */   public int getId()
/*  238:     */   {
/*  239: 339 */     return this.idEmpleado;
/*  240:     */   }
/*  241:     */   
/*  242:     */   public int getIdEmpleado()
/*  243:     */   {
/*  244: 343 */     return this.idEmpleado;
/*  245:     */   }
/*  246:     */   
/*  247:     */   public void setIdEmpleado(int idEmpleado)
/*  248:     */   {
/*  249: 347 */     this.idEmpleado = idEmpleado;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public int getIdOrganizacion()
/*  253:     */   {
/*  254: 351 */     return this.idOrganizacion;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public void setIdOrganizacion(int idOrganizacion)
/*  258:     */   {
/*  259: 355 */     this.idOrganizacion = idOrganizacion;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public Sucursal getSucursal()
/*  263:     */   {
/*  264: 359 */     return this.sucursal;
/*  265:     */   }
/*  266:     */   
/*  267:     */   public void setSucursal(Sucursal sucursal)
/*  268:     */   {
/*  269: 363 */     this.sucursal = sucursal;
/*  270:     */   }
/*  271:     */   
/*  272:     */   public Empresa getEmpresa()
/*  273:     */   {
/*  274: 367 */     return this.empresa;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public void setEmpresa(Empresa empresa)
/*  278:     */   {
/*  279: 371 */     this.empresa = empresa;
/*  280:     */   }
/*  281:     */   
/*  282:     */   public EstadoCivil getEstadoCivil()
/*  283:     */   {
/*  284: 380 */     return this.estadoCivil;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public void setEstadoCivil(EstadoCivil estadoCivil)
/*  288:     */   {
/*  289: 390 */     this.estadoCivil = estadoCivil;
/*  290:     */   }
/*  291:     */   
/*  292:     */   public Departamento getDepartamento()
/*  293:     */   {
/*  294: 399 */     return this.departamento;
/*  295:     */   }
/*  296:     */   
/*  297:     */   public void setDepartamento(Departamento departamento)
/*  298:     */   {
/*  299: 409 */     this.departamento = departamento;
/*  300:     */   }
/*  301:     */   
/*  302:     */   public CargoEmpleado getCargoEmpleado()
/*  303:     */   {
/*  304: 418 */     return this.cargoEmpleado;
/*  305:     */   }
/*  306:     */   
/*  307:     */   public void setCargoEmpleado(CargoEmpleado cargoEmpleado)
/*  308:     */   {
/*  309: 428 */     this.cargoEmpleado = cargoEmpleado;
/*  310:     */   }
/*  311:     */   
/*  312:     */   public Titulo getTitulo()
/*  313:     */   {
/*  314: 437 */     return this.titulo;
/*  315:     */   }
/*  316:     */   
/*  317:     */   public void setTitulo(Titulo titulo)
/*  318:     */   {
/*  319: 447 */     this.titulo = titulo;
/*  320:     */   }
/*  321:     */   
/*  322:     */   public Genero getGenero()
/*  323:     */   {
/*  324: 456 */     return this.genero;
/*  325:     */   }
/*  326:     */   
/*  327:     */   public void setGenero(Genero genero)
/*  328:     */   {
/*  329: 466 */     this.genero = genero;
/*  330:     */   }
/*  331:     */   
/*  332:     */   public String getImagen()
/*  333:     */   {
/*  334: 475 */     return this.imagen;
/*  335:     */   }
/*  336:     */   
/*  337:     */   public void setImagen(String imagen)
/*  338:     */   {
/*  339: 485 */     this.imagen = imagen;
/*  340:     */   }
/*  341:     */   
/*  342:     */   public List<CargaEmpleado> getListaCargaEmpleado()
/*  343:     */   {
/*  344: 494 */     return this.listaCargaEmpleado;
/*  345:     */   }
/*  346:     */   
/*  347:     */   public void setListaCargaEmpleado(List<CargaEmpleado> listaCargaEmpleado)
/*  348:     */   {
/*  349: 504 */     this.listaCargaEmpleado = listaCargaEmpleado;
/*  350:     */   }
/*  351:     */   
/*  352:     */   public List<RubroEmpleado> getListaRubroEmpleado()
/*  353:     */   {
/*  354: 513 */     return this.listaRubroEmpleado;
/*  355:     */   }
/*  356:     */   
/*  357:     */   public void setListaRubroEmpleado(List<RubroEmpleado> listaRubroEmpleado)
/*  358:     */   {
/*  359: 523 */     this.listaRubroEmpleado = listaRubroEmpleado;
/*  360:     */   }
/*  361:     */   
/*  362:     */   public boolean isTraCargadoRubros()
/*  363:     */   {
/*  364: 532 */     return this.traCargadoRubros;
/*  365:     */   }
/*  366:     */   
/*  367:     */   public void setTraCargadoRubros(boolean traCargadoRubros)
/*  368:     */   {
/*  369: 542 */     this.traCargadoRubros = traCargadoRubros;
/*  370:     */   }
/*  371:     */   
/*  372:     */   public List<PagoRolEmpleado> getListaPagoRolEmpleado()
/*  373:     */   {
/*  374: 551 */     return this.listaPagoRolEmpleado;
/*  375:     */   }
/*  376:     */   
/*  377:     */   public void setListaPagoRolEmpleado(List<PagoRolEmpleado> listaPagoRolEmpleado)
/*  378:     */   {
/*  379: 561 */     this.listaPagoRolEmpleado = listaPagoRolEmpleado;
/*  380:     */   }
/*  381:     */   
/*  382:     */   public long getTraNumeroCargasFamiliares()
/*  383:     */   {
/*  384: 570 */     return this.traNumeroCargasFamiliares;
/*  385:     */   }
/*  386:     */   
/*  387:     */   public void setTraNumeroCargasFamiliares(long traNumeroCargasFamiliares)
/*  388:     */   {
/*  389: 580 */     this.traNumeroCargasFamiliares = traNumeroCargasFamiliares;
/*  390:     */   }
/*  391:     */   
/*  392:     */   public BigDecimal getTraNumeroDiasTrabajados()
/*  393:     */   {
/*  394: 589 */     return this.traNumeroDiasTrabajados;
/*  395:     */   }
/*  396:     */   
/*  397:     */   public void setTraNumeroDiasTrabajados(BigDecimal traNumeroDiasTrabajados)
/*  398:     */   {
/*  399: 599 */     this.traNumeroDiasTrabajados = traNumeroDiasTrabajados;
/*  400:     */   }
/*  401:     */   
/*  402:     */   public TipoDiscapacidad getTipoDiscapacidad()
/*  403:     */   {
/*  404: 608 */     return this.tipoDiscapacidad;
/*  405:     */   }
/*  406:     */   
/*  407:     */   public void setTipoDiscapacidad(TipoDiscapacidad tipoDiscapacidad)
/*  408:     */   {
/*  409: 618 */     this.tipoDiscapacidad = tipoDiscapacidad;
/*  410:     */   }
/*  411:     */   
/*  412:     */   public Date getFechaNacimiento()
/*  413:     */   {
/*  414: 627 */     return this.fechaNacimiento;
/*  415:     */   }
/*  416:     */   
/*  417:     */   public void setFechaNacimiento(Date fechaNacimiento)
/*  418:     */   {
/*  419: 637 */     this.fechaNacimiento = fechaNacimiento;
/*  420:     */   }
/*  421:     */   
/*  422:     */   public boolean isAplicarRubroEmpleado()
/*  423:     */   {
/*  424: 646 */     return this.aplicarRubroEmpleado;
/*  425:     */   }
/*  426:     */   
/*  427:     */   public void setAplicarRubroEmpleado(boolean aplicarRubroEmpleado)
/*  428:     */   {
/*  429: 656 */     this.aplicarRubroEmpleado = aplicarRubroEmpleado;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public List<Prestamo> getListaPrestamo()
/*  433:     */   {
/*  434: 665 */     return this.listaPrestamo;
/*  435:     */   }
/*  436:     */   
/*  437:     */   public void setListaPrestamo(List<Prestamo> listaPrestamo)
/*  438:     */   {
/*  439: 675 */     this.listaPrestamo = listaPrestamo;
/*  440:     */   }
/*  441:     */   
/*  442:     */   public TipoContrato getTipoContrato()
/*  443:     */   {
/*  444: 684 */     return this.tipoContrato;
/*  445:     */   }
/*  446:     */   
/*  447:     */   public void setTipoContrato(TipoContrato tipoContrato)
/*  448:     */   {
/*  449: 694 */     this.tipoContrato = tipoContrato;
/*  450:     */   }
/*  451:     */   
/*  452:     */   public Date getFechaVencimientoContrato()
/*  453:     */   {
/*  454: 703 */     return this.fechaVencimientoContrato;
/*  455:     */   }
/*  456:     */   
/*  457:     */   public void setFechaVencimientoContrato(Date fechaVencimientoContrato)
/*  458:     */   {
/*  459: 713 */     this.fechaVencimientoContrato = fechaVencimientoContrato;
/*  460:     */   }
/*  461:     */   
/*  462:     */   public String getLugarNacimiento()
/*  463:     */   {
/*  464: 722 */     return this.lugarNacimiento;
/*  465:     */   }
/*  466:     */   
/*  467:     */   public void setLugarNacimiento(String lugarNacimiento)
/*  468:     */   {
/*  469: 732 */     this.lugarNacimiento = lugarNacimiento;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public BigDecimal getPorcentajeDiscapacidad()
/*  473:     */   {
/*  474: 741 */     return this.porcentajeDiscapacidad;
/*  475:     */   }
/*  476:     */   
/*  477:     */   public void setPorcentajeDiscapacidad(BigDecimal porcentajeDiscapacidad)
/*  478:     */   {
/*  479: 751 */     this.porcentajeDiscapacidad = porcentajeDiscapacidad;
/*  480:     */   }
/*  481:     */   
/*  482:     */   public Pais getPais()
/*  483:     */   {
/*  484: 760 */     return this.pais;
/*  485:     */   }
/*  486:     */   
/*  487:     */   public void setPais(Pais pais)
/*  488:     */   {
/*  489: 770 */     this.pais = pais;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public FormaPagoEmpleado getFormaPagoEmpleado()
/*  493:     */   {
/*  494: 779 */     return this.formaPagoEmpleado;
/*  495:     */   }
/*  496:     */   
/*  497:     */   public void setFormaPagoEmpleado(FormaPagoEmpleado formaPagoEmpleado)
/*  498:     */   {
/*  499: 789 */     this.formaPagoEmpleado = formaPagoEmpleado;
/*  500:     */   }
/*  501:     */   
/*  502:     */   public boolean isIndicadorPagoCash()
/*  503:     */   {
/*  504: 798 */     return this.indicadorPagoCash;
/*  505:     */   }
/*  506:     */   
/*  507:     */   public void setIndicadorPagoCash(boolean indicadorPagoCash)
/*  508:     */   {
/*  509: 808 */     this.indicadorPagoCash = indicadorPagoCash;
/*  510:     */   }
/*  511:     */   
/*  512:     */   public List<FormacionAcademica> getListaFormacionAcademica()
/*  513:     */   {
/*  514: 817 */     return this.listaFormacionAcademica;
/*  515:     */   }
/*  516:     */   
/*  517:     */   public void setListaFormacionAcademica(List<FormacionAcademica> listaFormacionAcademica)
/*  518:     */   {
/*  519: 827 */     this.listaFormacionAcademica = listaFormacionAcademica;
/*  520:     */   }
/*  521:     */   
/*  522:     */   public String getCalificacion()
/*  523:     */   {
/*  524: 836 */     return this.calificacion;
/*  525:     */   }
/*  526:     */   
/*  527:     */   public void setCalificacion(String calificacion)
/*  528:     */   {
/*  529: 846 */     this.calificacion = calificacion;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public int getMemos()
/*  533:     */   {
/*  534: 855 */     return this.memos;
/*  535:     */   }
/*  536:     */   
/*  537:     */   public void setMemos(int memos)
/*  538:     */   {
/*  539: 865 */     this.memos = memos;
/*  540:     */   }
/*  541:     */   
/*  542:     */   public String getNombres()
/*  543:     */   {
/*  544: 874 */     return this.nombres;
/*  545:     */   }
/*  546:     */   
/*  547:     */   public void setNombres(String nombres)
/*  548:     */   {
/*  549: 884 */     this.nombres = nombres;
/*  550:     */   }
/*  551:     */   
/*  552:     */   public String getApellidos()
/*  553:     */   {
/*  554: 893 */     return this.apellidos;
/*  555:     */   }
/*  556:     */   
/*  557:     */   public String getNombreCompleto()
/*  558:     */   {
/*  559: 897 */     return this.apellidos + " " + this.nombres;
/*  560:     */   }
/*  561:     */   
/*  562:     */   public void setApellidos(String apellidos)
/*  563:     */   {
/*  564: 907 */     this.apellidos = apellidos;
/*  565:     */   }
/*  566:     */   
/*  567:     */   public void setEliminado(boolean eliminado)
/*  568:     */   {
/*  569: 912 */     super.setEliminado(eliminado);
/*  570: 913 */     if (this.listaFormacionAcademica != null) {
/*  571: 914 */       for (FormacionAcademica formacionAcademica : this.listaFormacionAcademica) {
/*  572: 915 */         formacionAcademica.setEliminado(eliminado);
/*  573:     */       }
/*  574:     */     }
/*  575:     */   }
/*  576:     */   
/*  577:     */   public String getFiltro()
/*  578:     */   {
/*  579: 921 */     return this.filtro;
/*  580:     */   }
/*  581:     */   
/*  582:     */   public void setFiltro(String filtro)
/*  583:     */   {
/*  584: 925 */     this.filtro = filtro;
/*  585:     */   }
/*  586:     */   
/*  587:     */   public String getCodigoSectorial()
/*  588:     */   {
/*  589: 934 */     return this.codigoSectorial;
/*  590:     */   }
/*  591:     */   
/*  592:     */   public void setCodigoSectorial(String codigoSectorial)
/*  593:     */   {
/*  594: 944 */     this.codigoSectorial = codigoSectorial;
/*  595:     */   }
/*  596:     */   
/*  597:     */   public long getTraNumeroDiasFalta()
/*  598:     */   {
/*  599: 953 */     return this.traNumeroDiasFalta;
/*  600:     */   }
/*  601:     */   
/*  602:     */   public void setTraNumeroDiasFalta(long traNumeroDiasFalta)
/*  603:     */   {
/*  604: 963 */     this.traNumeroDiasFalta = traNumeroDiasFalta;
/*  605:     */   }
/*  606:     */   
/*  607:     */   public String getResidenciaTrabajador()
/*  608:     */   {
/*  609: 974 */     return this.residenciaTrabajador;
/*  610:     */   }
/*  611:     */   
/*  612:     */   public void setResidenciaTrabajador(String residenciaTrabajador)
/*  613:     */   {
/*  614: 984 */     this.residenciaTrabajador = residenciaTrabajador;
/*  615:     */   }
/*  616:     */   
/*  617:     */   public String getPaisResidenciaTrabajador()
/*  618:     */   {
/*  619: 993 */     return this.paisResidenciaTrabajador;
/*  620:     */   }
/*  621:     */   
/*  622:     */   public void setPaisResidenciaTrabajador(String paisResidenciaTrabajador)
/*  623:     */   {
/*  624:1003 */     this.paisResidenciaTrabajador = paisResidenciaTrabajador;
/*  625:     */   }
/*  626:     */   
/*  627:     */   public String getConvenioDobleImposicion()
/*  628:     */   {
/*  629:1012 */     return this.convenioDobleImposicion;
/*  630:     */   }
/*  631:     */   
/*  632:     */   public void setConvenioDobleImposicion(String convenioDobleImposicion)
/*  633:     */   {
/*  634:1022 */     this.convenioDobleImposicion = convenioDobleImposicion;
/*  635:     */   }
/*  636:     */   
/*  637:     */   public String getCondicionRespectoDiscapacidad()
/*  638:     */   {
/*  639:1031 */     return this.condicionRespectoDiscapacidad;
/*  640:     */   }
/*  641:     */   
/*  642:     */   public void setCondicionRespectoDiscapacidad(String condicionRespectoDiscapacidad)
/*  643:     */   {
/*  644:1041 */     this.condicionRespectoDiscapacidad = condicionRespectoDiscapacidad;
/*  645:     */   }
/*  646:     */   
/*  647:     */   public String getTipoIdentificacionSustitutoPariente()
/*  648:     */   {
/*  649:1050 */     return this.tipoIdentificacionSustitutoPariente;
/*  650:     */   }
/*  651:     */   
/*  652:     */   public void setTipoIdentificacionSustitutoPariente(String tipoIdentificacionSustitutoPariente)
/*  653:     */   {
/*  654:1060 */     this.tipoIdentificacionSustitutoPariente = tipoIdentificacionSustitutoPariente;
/*  655:     */   }
/*  656:     */   
/*  657:     */   public String getIdentificacionSustitutoPariente()
/*  658:     */   {
/*  659:1069 */     return this.identificacionSustitutoPariente;
/*  660:     */   }
/*  661:     */   
/*  662:     */   public void setIdentificacionSustitutoPariente(String identificacionSustitutoPariente)
/*  663:     */   {
/*  664:1079 */     this.identificacionSustitutoPariente = identificacionSustitutoPariente;
/*  665:     */   }
/*  666:     */   
/*  667:     */   public TipoSangre getTipoSangre()
/*  668:     */   {
/*  669:1088 */     return this.tipoSangre;
/*  670:     */   }
/*  671:     */   
/*  672:     */   public void setTipoSangre(TipoSangre tipoSangre)
/*  673:     */   {
/*  674:1098 */     this.tipoSangre = tipoSangre;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public int getNumeroCargasActivas()
/*  678:     */   {
/*  679:1102 */     return this.numeroCargasActivas;
/*  680:     */   }
/*  681:     */   
/*  682:     */   public void setNumeroCargasActivas(int numeroCargasActivas)
/*  683:     */   {
/*  684:1106 */     this.numeroCargasActivas = numeroCargasActivas;
/*  685:     */   }
/*  686:     */   
/*  687:     */   public List<LlamadoAtencion> getListaLlamadoAtencion()
/*  688:     */   {
/*  689:1110 */     return this.listaLlamadoAtencion;
/*  690:     */   }
/*  691:     */   
/*  692:     */   public void setListaLlamadoAtencion(List<LlamadoAtencion> listaLlamadoAtencion)
/*  693:     */   {
/*  694:1114 */     this.listaLlamadoAtencion = listaLlamadoAtencion;
/*  695:     */   }
/*  696:     */   
/*  697:     */   public List<HistoricoEmpleado> getListaHistoricoEmpleado()
/*  698:     */   {
/*  699:1121 */     return this.listaHistoricoEmpleado;
/*  700:     */   }
/*  701:     */   
/*  702:     */   public void setListaHistoricoEmpleado(List<HistoricoEmpleado> listaHistoricoEmpleado)
/*  703:     */   {
/*  704:1129 */     this.listaHistoricoEmpleado = listaHistoricoEmpleado;
/*  705:     */   }
/*  706:     */   
/*  707:     */   public DimensionContable getCentroCosto()
/*  708:     */   {
/*  709:1133 */     return this.centroCosto;
/*  710:     */   }
/*  711:     */   
/*  712:     */   public void setCentroCosto(DimensionContable centroCosto)
/*  713:     */   {
/*  714:1137 */     this.centroCosto = centroCosto;
/*  715:     */   }
/*  716:     */   
/*  717:     */   public BigDecimal getTraNumeroDiasRealesTrabajados()
/*  718:     */   {
/*  719:1144 */     return this.traNumeroDiasRealesTrabajados;
/*  720:     */   }
/*  721:     */   
/*  722:     */   public void setTraNumeroDiasRealesTrabajados(BigDecimal traNumeroDiasRealesTrabajados)
/*  723:     */   {
/*  724:1152 */     this.traNumeroDiasRealesTrabajados = traNumeroDiasRealesTrabajados;
/*  725:     */   }
/*  726:     */   
/*  727:     */   public HorarioEmpleado getHorarioEmpleado()
/*  728:     */   {
/*  729:1156 */     return this.horarioEmpleado;
/*  730:     */   }
/*  731:     */   
/*  732:     */   public void setHorarioEmpleado(HorarioEmpleado horarioEmpleado)
/*  733:     */   {
/*  734:1160 */     this.horarioEmpleado = horarioEmpleado;
/*  735:     */   }
/*  736:     */   
/*  737:     */   public List<DotacionEmpleado> getListaDotacion()
/*  738:     */   {
/*  739:1164 */     return this.listaDotacion;
/*  740:     */   }
/*  741:     */   
/*  742:     */   public void setListaDotacion(List<DotacionEmpleado> listaDotacion)
/*  743:     */   {
/*  744:1168 */     this.listaDotacion = listaDotacion;
/*  745:     */   }
/*  746:     */   
/*  747:     */   public boolean isActivo()
/*  748:     */   {
/*  749:1172 */     return this.activo;
/*  750:     */   }
/*  751:     */   
/*  752:     */   public void setActivo(boolean activo)
/*  753:     */   {
/*  754:1176 */     this.activo = activo;
/*  755:     */   }
/*  756:     */   
/*  757:     */   public CategoriaRubro getCategoriaRubro()
/*  758:     */   {
/*  759:1180 */     return this.categoriaRubro;
/*  760:     */   }
/*  761:     */   
/*  762:     */   public void setCategoriaRubro(CategoriaRubro categoriaRubro)
/*  763:     */   {
/*  764:1184 */     this.categoriaRubro = categoriaRubro;
/*  765:     */   }
/*  766:     */   
/*  767:     */   public GrupoTrabajo getGrupoTrabajo()
/*  768:     */   {
/*  769:1188 */     return this.grupoTrabajo;
/*  770:     */   }
/*  771:     */   
/*  772:     */   public void setGrupoTrabajo(GrupoTrabajo grupoTrabajo)
/*  773:     */   {
/*  774:1192 */     this.grupoTrabajo = grupoTrabajo;
/*  775:     */   }
/*  776:     */   
/*  777:     */   public boolean isDiscapacitado()
/*  778:     */   {
/*  779:1196 */     if ((this.porcentajeDiscapacidad == null) || (this.porcentajeDiscapacidad.compareTo(BigDecimal.ZERO) == 0)) {
/*  780:1197 */       this.discapacitado = false;
/*  781:     */     } else {
/*  782:1199 */       this.discapacitado = true;
/*  783:     */     }
/*  784:1201 */     return this.discapacitado;
/*  785:     */   }
/*  786:     */   
/*  787:     */   public void setDiscapacitado(boolean discapacitado)
/*  788:     */   {
/*  789:1205 */     this.discapacitado = discapacitado;
/*  790:     */   }
/*  791:     */   
/*  792:     */   public boolean isTerceraEdad()
/*  793:     */   {
/*  794:1209 */     if (this.fechaNacimiento != null)
/*  795:     */     {
/*  796:1210 */       int edad = FuncionesUtiles.obtenerEdad(this.fechaNacimiento);
/*  797:1211 */       int edadMinimaTerceraEdad = ParametrosSistema.getEdadMinimaTerceraEdad(this.idOrganizacion).intValue();
/*  798:1212 */       if (edad >= edadMinimaTerceraEdad) {
/*  799:1213 */         this.terceraEdad = true;
/*  800:     */       }
/*  801:     */     }
/*  802:1216 */     return this.terceraEdad;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public void setTerceraEdad(boolean terceraEdad)
/*  806:     */   {
/*  807:1220 */     this.terceraEdad = terceraEdad;
/*  808:     */   }
/*  809:     */   
/*  810:     */   public String getImagenHuella1()
/*  811:     */   {
/*  812:1224 */     return this.imagenHuella1;
/*  813:     */   }
/*  814:     */   
/*  815:     */   public void setImagenHuella1(String imagenHuella1)
/*  816:     */   {
/*  817:1228 */     this.imagenHuella1 = imagenHuella1;
/*  818:     */   }
/*  819:     */   
/*  820:     */   public String getImagenHuella2()
/*  821:     */   {
/*  822:1232 */     return this.imagenHuella2;
/*  823:     */   }
/*  824:     */   
/*  825:     */   public void setImagenHuella2(String imagenHuella2)
/*  826:     */   {
/*  827:1236 */     this.imagenHuella2 = imagenHuella2;
/*  828:     */   }
/*  829:     */   
/*  830:     */   public String getImagenHuella3()
/*  831:     */   {
/*  832:1240 */     return this.imagenHuella3;
/*  833:     */   }
/*  834:     */   
/*  835:     */   public void setImagenHuella3(String imagenHuella3)
/*  836:     */   {
/*  837:1244 */     this.imagenHuella3 = imagenHuella3;
/*  838:     */   }
/*  839:     */   
/*  840:     */   public String getImagenHuella4()
/*  841:     */   {
/*  842:1248 */     return this.imagenHuella4;
/*  843:     */   }
/*  844:     */   
/*  845:     */   public void setImagenHuella4(String imagenHuella4)
/*  846:     */   {
/*  847:1252 */     this.imagenHuella4 = imagenHuella4;
/*  848:     */   }
/*  849:     */   
/*  850:     */   public String getImagenHuella5()
/*  851:     */   {
/*  852:1256 */     return this.imagenHuella5;
/*  853:     */   }
/*  854:     */   
/*  855:     */   public void setImagenHuella5(String imagenHuella5)
/*  856:     */   {
/*  857:1260 */     this.imagenHuella5 = imagenHuella5;
/*  858:     */   }
/*  859:     */   
/*  860:     */   public String getImagenHuella6()
/*  861:     */   {
/*  862:1264 */     return this.imagenHuella6;
/*  863:     */   }
/*  864:     */   
/*  865:     */   public void setImagenHuella6(String imagenHuella6)
/*  866:     */   {
/*  867:1268 */     this.imagenHuella6 = imagenHuella6;
/*  868:     */   }
/*  869:     */   
/*  870:     */   public String getImagenHuella7()
/*  871:     */   {
/*  872:1272 */     return this.imagenHuella7;
/*  873:     */   }
/*  874:     */   
/*  875:     */   public void setImagenHuella7(String imagenHuella7)
/*  876:     */   {
/*  877:1276 */     this.imagenHuella7 = imagenHuella7;
/*  878:     */   }
/*  879:     */   
/*  880:     */   public String getImagenHuella8()
/*  881:     */   {
/*  882:1280 */     return this.imagenHuella8;
/*  883:     */   }
/*  884:     */   
/*  885:     */   public void setImagenHuella8(String imagenHuella8)
/*  886:     */   {
/*  887:1284 */     this.imagenHuella8 = imagenHuella8;
/*  888:     */   }
/*  889:     */   
/*  890:     */   public String getImagenHuella9()
/*  891:     */   {
/*  892:1288 */     return this.imagenHuella9;
/*  893:     */   }
/*  894:     */   
/*  895:     */   public void setImagenHuella9(String imagenHuella9)
/*  896:     */   {
/*  897:1292 */     this.imagenHuella9 = imagenHuella9;
/*  898:     */   }
/*  899:     */   
/*  900:     */   public String getImagenHuella10()
/*  901:     */   {
/*  902:1296 */     return this.imagenHuella10;
/*  903:     */   }
/*  904:     */   
/*  905:     */   public void setImagenHuella10(String imagenHuella10)
/*  906:     */   {
/*  907:1300 */     this.imagenHuella10 = imagenHuella10;
/*  908:     */   }
/*  909:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Empleado
 * JD-Core Version:    0.7.0.1
 */