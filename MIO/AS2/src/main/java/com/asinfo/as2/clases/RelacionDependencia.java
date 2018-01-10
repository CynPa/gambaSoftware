/*    1:     */ package com.asinfo.as2.clases;
/*    2:     */ 
/*    3:     */ import java.math.BigDecimal;
/*    4:     */ import javax.persistence.Column;
/*    5:     */ import javax.persistence.Entity;
/*    6:     */ import javax.persistence.Id;
/*    7:     */ import javax.persistence.Table;
/*    8:     */ import javax.persistence.Transient;
/*    9:     */ 
/*   10:     */ @Entity
/*   11:     */ @Table(name="tmp_relacion_dependencia")
/*   12:     */ public class RelacionDependencia
/*   13:     */ {
/*   14:     */   @Id
/*   15:     */   @Column(name="relacion_dependencia")
/*   16:     */   private Integer idEmpleado;
/*   17:     */   @Column(name="nombre")
/*   18:     */   private String nombre;
/*   19:     */   @Column(name="apellido")
/*   20:     */   private String apellido;
/*   21:     */   @Column(name="cedula")
/*   22:     */   private String cedula;
/*   23:     */   @Column(name="direccionCalle")
/*   24:     */   private String direccionCalle;
/*   25:     */   @Column(name="direccionNumeroCasa")
/*   26:     */   private String direccionNumeroCasa;
/*   27:     */   @Column(name="direccionCiudad")
/*   28:     */   private String direccionCiudad;
/*   29:     */   @Column(name="direccionProvincia")
/*   30:     */   private String direccionProvincia;
/*   31:     */   @Column(name="telefono")
/*   32:     */   private String telefono;
/*   33:     */   @Column(name="sueldoSalario")
/*   34:     */   private BigDecimal sueldoSalario;
/*   35:     */   @Column(name="sobreSueldo")
/*   36:     */   private BigDecimal sobreSueldo;
/*   37:  64 */   private transient BigDecimal sueldoSalarioOtroEmpleador = BigDecimal.ZERO;
/*   38:     */   @Column(name="decimoTercero")
/*   39:     */   private BigDecimal decimoTercero;
/*   40:     */   @Column(name="decimoCuarto")
/*   41:     */   private BigDecimal decimoCuarto;
/*   42:     */   @Column(name="fondoReserva")
/*   43:     */   private BigDecimal fondoReserva;
/*   44:     */   @Column(name="utilidades")
/*   45:     */   private BigDecimal utilidades;
/*   46:     */   @Column(name="aportePersonalIess")
/*   47:     */   private BigDecimal aportePersonalIess;
/*   48:  81 */   private transient BigDecimal aportePersonalIessOtroEmpleador = BigDecimal.ZERO;
/*   49:     */   @Column(name="deducibleVivienda")
/*   50:  83 */   private BigDecimal deducibleVivienda = BigDecimal.ZERO;
/*   51:     */   @Column(name="deducibleSalud")
/*   52:  86 */   private BigDecimal deducibleSalud = BigDecimal.ZERO;
/*   53:     */   @Column(name="deducibleEducacion")
/*   54:  89 */   private BigDecimal deducibleEducacion = BigDecimal.ZERO;
/*   55:     */   @Column(name="deducibleAliementacion")
/*   56:  92 */   private BigDecimal deducibleAlimentacion = BigDecimal.ZERO;
/*   57:     */   @Column(name="deducibleVestimenta")
/*   58:  95 */   private BigDecimal deducibleVestimenta = BigDecimal.ZERO;
/*   59:     */   @Column(name="numeroRetenciones")
/*   60:     */   private long numeroRetenciones;
/*   61:     */   @Column(name="numeroMesesEmpleado")
/*   62:     */   private long numeroMesesEmpleado;
/*   63:     */   @Column(name="valorRetenido")
/*   64:     */   private BigDecimal valorRetenido;
/*   65: 107 */   private transient BigDecimal valorRetenidoOtroEmpleado = BigDecimal.ZERO;
/*   66:     */   @Column(name="establecimiento")
/*   67:     */   private String establecimiento;
/*   68:     */   @Column(name="residencia_trabajador")
/*   69:     */   private String residenciaTrabajador;
/*   70:     */   @Column(name="pais_residencia")
/*   71:     */   private String paisResidencia;
/*   72:     */   @Column(name="aplica_convenio")
/*   73:     */   private String aplicaConvenio;
/*   74:     */   @Column(name="tipo_trabajador_discapacidad")
/*   75:     */   private String tipoTrabajadorDiscapacidad;
/*   76:     */   @Column(name="porcentaje_discapacidad")
/*   77:     */   private String porcentajeDiscapacidad;
/*   78:     */   @Column(name="tipo_identificacion_discapacidad")
/*   79:     */   private String tipoIdentificacionDiscapacidad;
/*   80:     */   @Column(name="identificacion_discapacidad")
/*   81:     */   private String identificacionDiscapacidad;
/*   82:     */   @Transient
/*   83:     */   private BigDecimal baseImponible;
/*   84:     */   @Transient
/*   85:     */   private BigDecimal impuestoRentaCausado;
/*   86:     */   @Column(name="tipo_identificacion")
/*   87:     */   private String tipoIdentificacion;
/*   88:     */   @Column(name="sueldo_agravada_contribucion_empleador")
/*   89:     */   private BigDecimal sueldoAgravadaContribucionEmpleador;
/*   90:     */   @Column(name="sobresueldo_agravada_contribucion_empleador")
/*   91:     */   private BigDecimal sobreSueldoAgravadaContribucionEmpleador;
/*   92:     */   @Column(name="meses_trabajados_vigente_contribucion_empleador")
/*   93:     */   private long mesesTrabajadosVigenciaContribucionEmpleador;
/*   94:     */   @Column(name="meses_trabajados_contribucion_empleador")
/*   95:     */   private long mesesTrabajadosContribucionEmpleador;
/*   96:     */   @Column(name="sueldo_agravada_contribucion_otro_empleador")
/*   97:     */   private BigDecimal sueldoAgravadaContribucionOtroEmpleador;
/*   98:     */   @Column(name="meses_trabajados_vigente_contribucion_otro_empleador")
/*   99:     */   private long mesesTrabajadosVigenciaContribucionOtroEmpleador;
/*  100:     */   @Column(name="meses_trabajados_contribucion_otro_empleador")
/*  101:     */   private long mesesTrabajadosContribucionOtroEmpleador;
/*  102:     */   @Column(name="contribucion_retenida")
/*  103:     */   private BigDecimal contribucionRetenida;
/*  104:     */   
/*  105:     */   public RelacionDependencia(String cedula, String apellidos, String nombres, String direccionCalle, String direccionNumeroCasa, String direccionCiudad, String direccionProvincia, String telefono, BigDecimal sueldoSalario, BigDecimal sobreSueldo, BigDecimal decimoTercero, BigDecimal decimoCuarto, BigDecimal fondoReserva, BigDecimal utilidades, BigDecimal aportePersonalIess, BigDecimal deducibleVivienda, BigDecimal deducibleSalud, BigDecimal deducibleEducacion, BigDecimal deducibleAlimentacion, BigDecimal deducibleVestimenta, long numeroRetenciones, long numeroMesesEmpleado, BigDecimal valorRetenido)
/*  106:     */   {
/*  107: 199 */     this.cedula = cedula;
/*  108: 200 */     this.nombre = nombres;
/*  109: 201 */     this.apellido = apellidos;
/*  110: 202 */     this.direccionCalle = direccionCalle;
/*  111: 203 */     this.direccionNumeroCasa = direccionNumeroCasa;
/*  112: 204 */     this.direccionCiudad = direccionCiudad;
/*  113: 205 */     this.direccionProvincia = direccionProvincia;
/*  114: 206 */     this.telefono = telefono;
/*  115: 207 */     this.sueldoSalario = sueldoSalario;
/*  116: 208 */     this.sobreSueldo = sobreSueldo;
/*  117: 209 */     this.decimoTercero = decimoTercero;
/*  118: 210 */     this.decimoCuarto = decimoCuarto;
/*  119: 211 */     this.fondoReserva = fondoReserva;
/*  120: 212 */     this.utilidades = utilidades;
/*  121: 213 */     this.aportePersonalIess = aportePersonalIess;
/*  122: 214 */     this.deducibleVivienda = deducibleVivienda;
/*  123: 215 */     this.deducibleSalud = deducibleSalud;
/*  124: 216 */     this.deducibleEducacion = deducibleEducacion;
/*  125: 217 */     this.deducibleAlimentacion = deducibleAlimentacion;
/*  126: 218 */     this.deducibleVestimenta = deducibleVestimenta;
/*  127: 219 */     this.numeroRetenciones = numeroRetenciones;
/*  128: 220 */     this.numeroMesesEmpleado = numeroMesesEmpleado;
/*  129: 221 */     this.valorRetenido = valorRetenido;
/*  130:     */   }
/*  131:     */   
/*  132:     */   public RelacionDependencia(String cedula, String apellidos, String nombres, String direccionCalle, String direccionNumeroCasa, String direccionCiudad, String direccionProvincia, String telefono, String establecimiento, String residenciaTrabajador, String paisResidencia, String aplicaConvenio, String tipoTrabajadorDiscapacidad, String porcentajeDiscapacidad, String tipoIdentificacionDiscapacidad, String identificacionDiscapacidad, BigDecimal sueldoSalario, BigDecimal sobreSueldo, BigDecimal decimoTercero, BigDecimal decimoCuarto, BigDecimal fondoReserva, BigDecimal utilidades, BigDecimal aportePersonalIess, BigDecimal deducibleVivienda, BigDecimal deducibleSalud, BigDecimal deducibleEducacion, BigDecimal deducibleAlimentacion, BigDecimal deducibleVestimenta, long numeroRetenciones, long numeroMesesEmpleado, BigDecimal valorRetenido)
/*  133:     */   {
/*  134: 266 */     this(cedula, apellidos, nombres, direccionCalle, direccionNumeroCasa, direccionCiudad, direccionProvincia, telefono, sueldoSalario, sobreSueldo, decimoTercero, decimoCuarto, fondoReserva, utilidades, aportePersonalIess, deducibleVivienda, deducibleSalud, deducibleEducacion, deducibleAlimentacion, deducibleVestimenta, numeroRetenciones, numeroMesesEmpleado, valorRetenido);
/*  135:     */     
/*  136:     */ 
/*  137: 269 */     this.establecimiento = establecimiento;
/*  138: 270 */     this.residenciaTrabajador = residenciaTrabajador;
/*  139: 271 */     this.paisResidencia = paisResidencia;
/*  140: 272 */     this.aplicaConvenio = aplicaConvenio;
/*  141: 273 */     this.tipoTrabajadorDiscapacidad = tipoTrabajadorDiscapacidad;
/*  142: 274 */     this.porcentajeDiscapacidad = porcentajeDiscapacidad;
/*  143: 275 */     this.tipoIdentificacionDiscapacidad = tipoIdentificacionDiscapacidad;
/*  144: 276 */     this.identificacionDiscapacidad = identificacionDiscapacidad;
/*  145:     */   }
/*  146:     */   
/*  147:     */   public RelacionDependencia(String cedula, String apellidos, String nombres, String direccionCalle, String direccionNumeroCasa, String direccionCiudad, String direccionProvincia, String telefono, String establecimiento, String residenciaTrabajador, String paisResidencia, String aplicaConvenio, String tipoTrabajadorDiscapacidad, String porcentajeDiscapacidad, String tipoIdentificacionDiscapacidad, String identificacionDiscapacidad, BigDecimal sueldoSalario, BigDecimal sobreSueldo, BigDecimal decimoTercero, BigDecimal decimoCuarto, BigDecimal fondoReserva, BigDecimal utilidades, BigDecimal aportePersonalIess, BigDecimal deducibleVivienda, BigDecimal deducibleSalud, BigDecimal deducibleEducacion, BigDecimal deducibleAlimentacion, BigDecimal deducibleVestimenta, long numeroRetenciones, long numeroMesesEmpleado, BigDecimal valorRetenido, String tipoIdentificacion)
/*  148:     */   {
/*  149: 287 */     this(cedula, apellidos, nombres, direccionCalle, direccionNumeroCasa, direccionCiudad, direccionProvincia, telefono, establecimiento, residenciaTrabajador, paisResidencia, aplicaConvenio, tipoTrabajadorDiscapacidad, porcentajeDiscapacidad, tipoIdentificacionDiscapacidad, identificacionDiscapacidad, sueldoSalario, sobreSueldo, decimoTercero, decimoCuarto, fondoReserva, utilidades, aportePersonalIess, deducibleVivienda, deducibleSalud, deducibleEducacion, deducibleAlimentacion, deducibleVestimenta, numeroRetenciones, numeroMesesEmpleado, valorRetenido);
/*  150:     */     
/*  151:     */ 
/*  152:     */ 
/*  153:     */ 
/*  154: 292 */     this.tipoIdentificacion = tipoIdentificacion;
/*  155:     */   }
/*  156:     */   
/*  157:     */   public RelacionDependencia(String cedula, BigDecimal sueldoAgravadaContribucionEmpleador, BigDecimal sobreSueldoAgravadaContribucionEmpleador, long mesesTrabajadosVigenciaContribucionEmpleador, long mesesTrabajadosContribucionEmpleador, BigDecimal contribucionRetenida)
/*  158:     */   {
/*  159: 299 */     this.cedula = cedula;
/*  160: 300 */     this.sueldoAgravadaContribucionEmpleador = sueldoAgravadaContribucionEmpleador;
/*  161: 301 */     this.sobreSueldoAgravadaContribucionEmpleador = sobreSueldoAgravadaContribucionEmpleador;
/*  162: 302 */     this.mesesTrabajadosVigenciaContribucionEmpleador = mesesTrabajadosVigenciaContribucionEmpleador;
/*  163: 303 */     this.mesesTrabajadosContribucionEmpleador = mesesTrabajadosContribucionEmpleador;
/*  164: 304 */     this.contribucionRetenida = contribucionRetenida;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public Integer getIdEmpleado()
/*  168:     */   {
/*  169: 316 */     return this.idEmpleado;
/*  170:     */   }
/*  171:     */   
/*  172:     */   public void setIdEmpleado(Integer idEmpleado)
/*  173:     */   {
/*  174: 326 */     this.idEmpleado = idEmpleado;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public String getCedula()
/*  178:     */   {
/*  179: 335 */     return this.cedula;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public void setCedula(String cedula)
/*  183:     */   {
/*  184: 345 */     this.cedula = cedula;
/*  185:     */   }
/*  186:     */   
/*  187:     */   public String getDireccionCalle()
/*  188:     */   {
/*  189: 354 */     return this.direccionCalle;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public void setDireccionCalle(String direccionCalle)
/*  193:     */   {
/*  194: 364 */     this.direccionCalle = direccionCalle;
/*  195:     */   }
/*  196:     */   
/*  197:     */   public String getDireccionNumeroCasa()
/*  198:     */   {
/*  199: 373 */     return this.direccionNumeroCasa;
/*  200:     */   }
/*  201:     */   
/*  202:     */   public void setDireccionNumeroCasa(String direccionNumeroCasa)
/*  203:     */   {
/*  204: 383 */     this.direccionNumeroCasa = direccionNumeroCasa;
/*  205:     */   }
/*  206:     */   
/*  207:     */   public String getDireccionCiudad()
/*  208:     */   {
/*  209: 392 */     return this.direccionCiudad;
/*  210:     */   }
/*  211:     */   
/*  212:     */   public void setDireccionCiudad(String direccionCiudad)
/*  213:     */   {
/*  214: 402 */     this.direccionCiudad = direccionCiudad;
/*  215:     */   }
/*  216:     */   
/*  217:     */   public String getDireccionProvincia()
/*  218:     */   {
/*  219: 411 */     return this.direccionProvincia;
/*  220:     */   }
/*  221:     */   
/*  222:     */   public void setDireccionProvincia(String direccionProvincia)
/*  223:     */   {
/*  224: 421 */     this.direccionProvincia = direccionProvincia;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public String getTelefono()
/*  228:     */   {
/*  229: 430 */     return this.telefono;
/*  230:     */   }
/*  231:     */   
/*  232:     */   public void setTelefono(String telefono)
/*  233:     */   {
/*  234: 440 */     this.telefono = telefono;
/*  235:     */   }
/*  236:     */   
/*  237:     */   public BigDecimal getSueldoSalario()
/*  238:     */   {
/*  239: 449 */     return this.sueldoSalario;
/*  240:     */   }
/*  241:     */   
/*  242:     */   public void setSueldoSalario(BigDecimal sueldoSalario)
/*  243:     */   {
/*  244: 459 */     this.sueldoSalario = sueldoSalario;
/*  245:     */   }
/*  246:     */   
/*  247:     */   public BigDecimal getSobreSueldo()
/*  248:     */   {
/*  249: 468 */     return this.sobreSueldo;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public void setSobreSueldo(BigDecimal sobreSueldo)
/*  253:     */   {
/*  254: 478 */     this.sobreSueldo = sobreSueldo;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public BigDecimal getDecimoTercero()
/*  258:     */   {
/*  259: 487 */     return this.decimoTercero;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public void setDecimoTercero(BigDecimal decimoTercero)
/*  263:     */   {
/*  264: 497 */     this.decimoTercero = decimoTercero;
/*  265:     */   }
/*  266:     */   
/*  267:     */   public BigDecimal getDecimoCuarto()
/*  268:     */   {
/*  269: 506 */     return this.decimoCuarto;
/*  270:     */   }
/*  271:     */   
/*  272:     */   public void setDecimoCuarto(BigDecimal decimoCuarto)
/*  273:     */   {
/*  274: 516 */     this.decimoCuarto = decimoCuarto;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public BigDecimal getFondoReserva()
/*  278:     */   {
/*  279: 525 */     return this.fondoReserva;
/*  280:     */   }
/*  281:     */   
/*  282:     */   public void setFondoReserva(BigDecimal fondoReserva)
/*  283:     */   {
/*  284: 535 */     this.fondoReserva = fondoReserva;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public BigDecimal getUtilidades()
/*  288:     */   {
/*  289: 544 */     return this.utilidades;
/*  290:     */   }
/*  291:     */   
/*  292:     */   public void setUtilidades(BigDecimal utilidades)
/*  293:     */   {
/*  294: 554 */     this.utilidades = utilidades;
/*  295:     */   }
/*  296:     */   
/*  297:     */   public BigDecimal getAportePersonalIess()
/*  298:     */   {
/*  299: 563 */     return this.aportePersonalIess;
/*  300:     */   }
/*  301:     */   
/*  302:     */   public void setAportePersonalIess(BigDecimal aportePersonalIess)
/*  303:     */   {
/*  304: 573 */     this.aportePersonalIess = aportePersonalIess;
/*  305:     */   }
/*  306:     */   
/*  307:     */   public BigDecimal getDeducibleVivienda()
/*  308:     */   {
/*  309: 582 */     return this.deducibleVivienda;
/*  310:     */   }
/*  311:     */   
/*  312:     */   public void setDeducibleVivienda(BigDecimal deducibleVivienda)
/*  313:     */   {
/*  314: 592 */     this.deducibleVivienda = deducibleVivienda;
/*  315:     */   }
/*  316:     */   
/*  317:     */   public BigDecimal getDeducibleSalud()
/*  318:     */   {
/*  319: 601 */     return this.deducibleSalud;
/*  320:     */   }
/*  321:     */   
/*  322:     */   public void setDeducibleSalud(BigDecimal deducibleSalud)
/*  323:     */   {
/*  324: 611 */     this.deducibleSalud = deducibleSalud;
/*  325:     */   }
/*  326:     */   
/*  327:     */   public BigDecimal getDeducibleEducacion()
/*  328:     */   {
/*  329: 620 */     return this.deducibleEducacion;
/*  330:     */   }
/*  331:     */   
/*  332:     */   public void setDeducibleEducacion(BigDecimal deducibleEducacion)
/*  333:     */   {
/*  334: 630 */     this.deducibleEducacion = deducibleEducacion;
/*  335:     */   }
/*  336:     */   
/*  337:     */   public BigDecimal getDeducibleAlimentacion()
/*  338:     */   {
/*  339: 639 */     return this.deducibleAlimentacion;
/*  340:     */   }
/*  341:     */   
/*  342:     */   public void setDeducibleAlimentacion(BigDecimal deducibleAlimentacion)
/*  343:     */   {
/*  344: 649 */     this.deducibleAlimentacion = deducibleAlimentacion;
/*  345:     */   }
/*  346:     */   
/*  347:     */   public BigDecimal getDeducibleVestimenta()
/*  348:     */   {
/*  349: 658 */     return this.deducibleVestimenta;
/*  350:     */   }
/*  351:     */   
/*  352:     */   public void setDeducibleVestimenta(BigDecimal deducibleVestimenta)
/*  353:     */   {
/*  354: 668 */     this.deducibleVestimenta = deducibleVestimenta;
/*  355:     */   }
/*  356:     */   
/*  357:     */   public long getNumeroRetenciones()
/*  358:     */   {
/*  359: 677 */     return this.numeroRetenciones;
/*  360:     */   }
/*  361:     */   
/*  362:     */   public void setNumeroRetenciones(long numeroRetenciones)
/*  363:     */   {
/*  364: 687 */     this.numeroRetenciones = numeroRetenciones;
/*  365:     */   }
/*  366:     */   
/*  367:     */   public long getNumeroMesesEmpleado()
/*  368:     */   {
/*  369: 696 */     return this.numeroMesesEmpleado;
/*  370:     */   }
/*  371:     */   
/*  372:     */   public void setNumeroMesesEmpleado(long numeroMesesEmpleado)
/*  373:     */   {
/*  374: 706 */     this.numeroMesesEmpleado = numeroMesesEmpleado;
/*  375:     */   }
/*  376:     */   
/*  377:     */   public BigDecimal getValorRetenido()
/*  378:     */   {
/*  379: 715 */     return this.valorRetenido;
/*  380:     */   }
/*  381:     */   
/*  382:     */   public void setValorRetenido(BigDecimal valorRetenido)
/*  383:     */   {
/*  384: 725 */     this.valorRetenido = valorRetenido;
/*  385:     */   }
/*  386:     */   
/*  387:     */   public BigDecimal getBaseImponible()
/*  388:     */   {
/*  389: 734 */     return this.baseImponible;
/*  390:     */   }
/*  391:     */   
/*  392:     */   public void setBaseImponible(BigDecimal baseImponible)
/*  393:     */   {
/*  394: 744 */     this.baseImponible = baseImponible;
/*  395:     */   }
/*  396:     */   
/*  397:     */   public BigDecimal getImpuestoRentaCausado()
/*  398:     */   {
/*  399: 753 */     return this.impuestoRentaCausado;
/*  400:     */   }
/*  401:     */   
/*  402:     */   public void setImpuestoRentaCausado(BigDecimal impuestoRentaCausado)
/*  403:     */   {
/*  404: 763 */     this.impuestoRentaCausado = impuestoRentaCausado;
/*  405:     */   }
/*  406:     */   
/*  407:     */   public String getNombre()
/*  408:     */   {
/*  409: 772 */     return this.nombre;
/*  410:     */   }
/*  411:     */   
/*  412:     */   public void setNombre(String nombre)
/*  413:     */   {
/*  414: 782 */     this.nombre = nombre;
/*  415:     */   }
/*  416:     */   
/*  417:     */   public String getApellido()
/*  418:     */   {
/*  419: 791 */     return this.apellido;
/*  420:     */   }
/*  421:     */   
/*  422:     */   public void setApellido(String apellido)
/*  423:     */   {
/*  424: 801 */     this.apellido = apellido;
/*  425:     */   }
/*  426:     */   
/*  427:     */   public String getEstablecimiento()
/*  428:     */   {
/*  429: 810 */     return this.establecimiento;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public void setEstablecimiento(String establecimiento)
/*  433:     */   {
/*  434: 820 */     this.establecimiento = establecimiento;
/*  435:     */   }
/*  436:     */   
/*  437:     */   public String getResidenciaTrabajador()
/*  438:     */   {
/*  439: 829 */     return this.residenciaTrabajador;
/*  440:     */   }
/*  441:     */   
/*  442:     */   public void setResidenciaTrabajador(String residenciaTrabajador)
/*  443:     */   {
/*  444: 839 */     this.residenciaTrabajador = residenciaTrabajador;
/*  445:     */   }
/*  446:     */   
/*  447:     */   public String getPaisResidencia()
/*  448:     */   {
/*  449: 848 */     return this.paisResidencia;
/*  450:     */   }
/*  451:     */   
/*  452:     */   public void setPaisResidencia(String paisResidencia)
/*  453:     */   {
/*  454: 858 */     this.paisResidencia = paisResidencia;
/*  455:     */   }
/*  456:     */   
/*  457:     */   public String getAplicaConvenio()
/*  458:     */   {
/*  459: 867 */     return this.aplicaConvenio;
/*  460:     */   }
/*  461:     */   
/*  462:     */   public void setAplicaConvenio(String aplicaConvenio)
/*  463:     */   {
/*  464: 877 */     this.aplicaConvenio = aplicaConvenio;
/*  465:     */   }
/*  466:     */   
/*  467:     */   public String getTipoTrabajadorDiscapacidad()
/*  468:     */   {
/*  469: 886 */     return this.tipoTrabajadorDiscapacidad;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void setTipoTrabajadorDiscapacidad(String tipoTrabajadorDiscapacidad)
/*  473:     */   {
/*  474: 896 */     this.tipoTrabajadorDiscapacidad = tipoTrabajadorDiscapacidad;
/*  475:     */   }
/*  476:     */   
/*  477:     */   public String getPorcentajeDiscapacidad()
/*  478:     */   {
/*  479: 905 */     return this.porcentajeDiscapacidad;
/*  480:     */   }
/*  481:     */   
/*  482:     */   public void setPorcentajeDiscapacidad(String porcentajeDiscapacidad)
/*  483:     */   {
/*  484: 915 */     this.porcentajeDiscapacidad = porcentajeDiscapacidad;
/*  485:     */   }
/*  486:     */   
/*  487:     */   public String getTipoIdentificacionDiscapacidad()
/*  488:     */   {
/*  489: 924 */     return this.tipoIdentificacionDiscapacidad;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public void setTipoIdentificacionDiscapacidad(String tipoIdentificacionDiscapacidad)
/*  493:     */   {
/*  494: 934 */     this.tipoIdentificacionDiscapacidad = tipoIdentificacionDiscapacidad;
/*  495:     */   }
/*  496:     */   
/*  497:     */   public String getIdentificacionDiscapacidad()
/*  498:     */   {
/*  499: 943 */     return this.identificacionDiscapacidad;
/*  500:     */   }
/*  501:     */   
/*  502:     */   public void setIdentificacionDiscapacidad(String identificacionDiscapacidad)
/*  503:     */   {
/*  504: 953 */     this.identificacionDiscapacidad = identificacionDiscapacidad;
/*  505:     */   }
/*  506:     */   
/*  507:     */   public String getTipoIdentificacion()
/*  508:     */   {
/*  509: 960 */     return this.tipoIdentificacion;
/*  510:     */   }
/*  511:     */   
/*  512:     */   public void setTipoIdentificacion(String tipoIdentificacion)
/*  513:     */   {
/*  514: 968 */     this.tipoIdentificacion = tipoIdentificacion;
/*  515:     */   }
/*  516:     */   
/*  517:     */   public BigDecimal getSueldoSalarioOtroEmpleador()
/*  518:     */   {
/*  519: 975 */     return this.sueldoSalarioOtroEmpleador;
/*  520:     */   }
/*  521:     */   
/*  522:     */   public void setSueldoSalarioOtroEmpleador(BigDecimal sueldoSalarioOtroEmpleador)
/*  523:     */   {
/*  524: 983 */     this.sueldoSalarioOtroEmpleador = sueldoSalarioOtroEmpleador;
/*  525:     */   }
/*  526:     */   
/*  527:     */   public BigDecimal getAportePersonalIessOtroEmpleador()
/*  528:     */   {
/*  529: 990 */     return this.aportePersonalIessOtroEmpleador;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public void setAportePersonalIessOtroEmpleador(BigDecimal aportePersonalIessOtroEmpleador)
/*  533:     */   {
/*  534: 998 */     this.aportePersonalIessOtroEmpleador = aportePersonalIessOtroEmpleador;
/*  535:     */   }
/*  536:     */   
/*  537:     */   public BigDecimal getValorRetenidoOtroEmpleado()
/*  538:     */   {
/*  539:1005 */     return this.valorRetenidoOtroEmpleado;
/*  540:     */   }
/*  541:     */   
/*  542:     */   public void setValorRetenidoOtroEmpleado(BigDecimal valorRetenidoOtroEmpleado)
/*  543:     */   {
/*  544:1013 */     this.valorRetenidoOtroEmpleado = valorRetenidoOtroEmpleado;
/*  545:     */   }
/*  546:     */   
/*  547:     */   public BigDecimal getSueldoAgravadaContribucionEmpleador()
/*  548:     */   {
/*  549:1019 */     if (this.sueldoAgravadaContribucionEmpleador == null) {
/*  550:1020 */       this.sueldoAgravadaContribucionEmpleador = BigDecimal.ZERO;
/*  551:     */     }
/*  552:1022 */     return this.sueldoAgravadaContribucionEmpleador;
/*  553:     */   }
/*  554:     */   
/*  555:     */   public void setSueldoAgravadaContribucionEmpleador(BigDecimal sueldoAgravadaContribucionEmpleador)
/*  556:     */   {
/*  557:1026 */     this.sueldoAgravadaContribucionEmpleador = sueldoAgravadaContribucionEmpleador;
/*  558:     */   }
/*  559:     */   
/*  560:     */   public BigDecimal getSobreSueldoAgravadaContribucionEmpleador()
/*  561:     */   {
/*  562:1030 */     if (this.sobreSueldoAgravadaContribucionEmpleador == null) {
/*  563:1031 */       this.sobreSueldoAgravadaContribucionEmpleador = BigDecimal.ZERO;
/*  564:     */     }
/*  565:1033 */     return this.sobreSueldoAgravadaContribucionEmpleador;
/*  566:     */   }
/*  567:     */   
/*  568:     */   public void setSobreSueldoAgravadaContribucionEmpleador(BigDecimal sobreSueldoAgravadaContribucionEmpleador)
/*  569:     */   {
/*  570:1037 */     this.sobreSueldoAgravadaContribucionEmpleador = sobreSueldoAgravadaContribucionEmpleador;
/*  571:     */   }
/*  572:     */   
/*  573:     */   public long getMesesTrabajadosVigenciaContribucionEmpleador()
/*  574:     */   {
/*  575:1041 */     return this.mesesTrabajadosVigenciaContribucionEmpleador;
/*  576:     */   }
/*  577:     */   
/*  578:     */   public void setMesesTrabajadosVigenciaContribucionEmpleador(long mesesTrabajadosVigenciaContribucionEmpleador)
/*  579:     */   {
/*  580:1045 */     this.mesesTrabajadosVigenciaContribucionEmpleador = mesesTrabajadosVigenciaContribucionEmpleador;
/*  581:     */   }
/*  582:     */   
/*  583:     */   public long getMesesTrabajadosContribucionEmpleador()
/*  584:     */   {
/*  585:1049 */     return this.mesesTrabajadosContribucionEmpleador;
/*  586:     */   }
/*  587:     */   
/*  588:     */   public void setMesesTrabajadosContribucionEmpleador(long mesesTrabajadosContribucionEmpleador)
/*  589:     */   {
/*  590:1053 */     this.mesesTrabajadosContribucionEmpleador = mesesTrabajadosContribucionEmpleador;
/*  591:     */   }
/*  592:     */   
/*  593:     */   public BigDecimal getSueldoAgravadaContribucionOtroEmpleador()
/*  594:     */   {
/*  595:1057 */     if (this.sueldoAgravadaContribucionOtroEmpleador == null) {
/*  596:1058 */       this.sueldoAgravadaContribucionOtroEmpleador = BigDecimal.ZERO;
/*  597:     */     }
/*  598:1060 */     return this.sueldoAgravadaContribucionOtroEmpleador;
/*  599:     */   }
/*  600:     */   
/*  601:     */   public void setSueldoAgravadaContribucionOtroEmpleador(BigDecimal sueldoAgravadaContribucionOtroEmpleador)
/*  602:     */   {
/*  603:1064 */     this.sueldoAgravadaContribucionOtroEmpleador = sueldoAgravadaContribucionOtroEmpleador;
/*  604:     */   }
/*  605:     */   
/*  606:     */   public long getMesesTrabajadosVigenciaContribucionOtroEmpleador()
/*  607:     */   {
/*  608:1068 */     return this.mesesTrabajadosVigenciaContribucionOtroEmpleador;
/*  609:     */   }
/*  610:     */   
/*  611:     */   public void setMesesTrabajadosVigenciaContribucionOtroEmpleador(long mesesTrabajadosVigenciaContribucionOtroEmpleador)
/*  612:     */   {
/*  613:1072 */     this.mesesTrabajadosVigenciaContribucionOtroEmpleador = mesesTrabajadosVigenciaContribucionOtroEmpleador;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public long getMesesTrabajadosContribucionOtroEmpleador()
/*  617:     */   {
/*  618:1076 */     return this.mesesTrabajadosContribucionOtroEmpleador;
/*  619:     */   }
/*  620:     */   
/*  621:     */   public void setMesesTrabajadosContribucionOtroEmpleador(long mesesTrabajadosContribucionOtroEmpleador)
/*  622:     */   {
/*  623:1080 */     this.mesesTrabajadosContribucionOtroEmpleador = mesesTrabajadosContribucionOtroEmpleador;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public BigDecimal getContribucionRetenida()
/*  627:     */   {
/*  628:1084 */     if (this.contribucionRetenida == null) {
/*  629:1085 */       this.contribucionRetenida = BigDecimal.ZERO;
/*  630:     */     }
/*  631:1087 */     return this.contribucionRetenida;
/*  632:     */   }
/*  633:     */   
/*  634:     */   public void setContribucionRetenida(BigDecimal contribucionRetenida)
/*  635:     */   {
/*  636:1091 */     this.contribucionRetenida = contribucionRetenida;
/*  637:     */   }
/*  638:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.RelacionDependencia
 * JD-Core Version:    0.7.0.1
 */