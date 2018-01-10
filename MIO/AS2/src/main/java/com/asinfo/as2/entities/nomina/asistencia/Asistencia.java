/*    1:     */ package com.asinfo.as2.entities.nomina.asistencia;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.Empleado;
/*    4:     */ import com.asinfo.as2.entities.EntidadBase;
/*    5:     */ import com.asinfo.as2.entities.SubsidioEmpleado;
/*    6:     */ import java.math.BigDecimal;
/*    7:     */ import java.util.Calendar;
/*    8:     */ import java.util.Date;
/*    9:     */ import javax.persistence.Column;
/*   10:     */ import javax.persistence.Entity;
/*   11:     */ import javax.persistence.FetchType;
/*   12:     */ import javax.persistence.GeneratedValue;
/*   13:     */ import javax.persistence.GenerationType;
/*   14:     */ import javax.persistence.Id;
/*   15:     */ import javax.persistence.JoinColumn;
/*   16:     */ import javax.persistence.ManyToOne;
/*   17:     */ import javax.persistence.PrePersist;
/*   18:     */ import javax.persistence.PreUpdate;
/*   19:     */ import javax.persistence.Table;
/*   20:     */ import javax.persistence.TableGenerator;
/*   21:     */ import javax.persistence.Temporal;
/*   22:     */ import javax.persistence.TemporalType;
/*   23:     */ import javax.persistence.Transient;
/*   24:     */ import javax.validation.constraints.Digits;
/*   25:     */ import javax.validation.constraints.NotNull;
/*   26:     */ import javax.validation.constraints.Size;
/*   27:     */ import org.hibernate.annotations.ColumnDefault;
/*   28:     */ 
/*   29:     */ @Entity
/*   30:     */ @Table(name="asistencia")
/*   31:     */ public class Asistencia
/*   32:     */   extends EntidadBase
/*   33:     */ {
/*   34:     */   private static final long serialVersionUID = 1L;
/*   35:     */   @Id
/*   36:     */   @TableGenerator(name="asistencia", initialValue=0, allocationSize=50)
/*   37:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="asistencia")
/*   38:     */   @Column(name="id_asistencia")
/*   39:     */   private int idAsistencia;
/*   40:     */   @Column(name="id_organizacion", nullable=false)
/*   41:     */   private int idOrganizacion;
/*   42:     */   @Column(name="id_sucursal", nullable=false)
/*   43:     */   private int idSucursal;
/*   44:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   45:     */   @JoinColumn(name="id_empleado", nullable=true)
/*   46:     */   private Empleado empleado;
/*   47:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   48:     */   @JoinColumn(name="id_subsidio_empleado", nullable=true)
/*   49:     */   private SubsidioEmpleado subsidioEmpleado;
/*   50:     */   @Temporal(TemporalType.DATE)
/*   51:     */   @Column(name="fecha", nullable=false)
/*   52:     */   private Date fecha;
/*   53:     */   @Temporal(TemporalType.DATE)
/*   54:     */   @Column(name="fecha_filtro", nullable=true)
/*   55:     */   private Date fechaFiltro;
/*   56:     */   @Temporal(TemporalType.TIME)
/*   57:     */   @Column(name="entrada", nullable=true)
/*   58:     */   private Date entrada;
/*   59:     */   @Temporal(TemporalType.TIME)
/*   60:     */   @Column(name="marcacion_entrada", nullable=true)
/*   61:     */   private Date marcacionEntrada;
/*   62:     */   @Temporal(TemporalType.TIME)
/*   63:     */   @Column(name="salida", nullable=true)
/*   64:     */   private Date salida;
/*   65:     */   @Temporal(TemporalType.TIME)
/*   66:     */   @Column(name="marcacion_salida", nullable=true)
/*   67:     */   private Date marcacionSalida;
/*   68:     */   @Temporal(TemporalType.TIME)
/*   69:     */   @Column(name="salida_receso", nullable=true)
/*   70:     */   private Date salidaReceso;
/*   71:     */   @Temporal(TemporalType.TIME)
/*   72:     */   @Column(name="marcacion_salida_receso", nullable=true)
/*   73:     */   private Date marcacionSalidaReceso;
/*   74:     */   @Temporal(TemporalType.TIME)
/*   75:     */   @Column(name="entrada_receso", nullable=true)
/*   76:     */   private Date entradaReceso;
/*   77:     */   @Temporal(TemporalType.TIME)
/*   78:     */   @Column(name="marcacion_entrada_receso", nullable=true)
/*   79:     */   private Date marcacionEntradaReceso;
/*   80:     */   @Temporal(TemporalType.TIME)
/*   81:     */   @Column(name="marcacion_entrada_reingreso1", nullable=true)
/*   82:     */   private Date marcacionEntradaReingreso1;
/*   83:     */   @Temporal(TemporalType.TIME)
/*   84:     */   @Column(name="marcacion_salida_reingreso1", nullable=true)
/*   85:     */   private Date marcacionSalidaReingreso1;
/*   86:     */   @Temporal(TemporalType.TIME)
/*   87:     */   @Column(name="marcacion_entrada_reingreso2", nullable=true)
/*   88:     */   private Date marcacionEntradaReingreso2;
/*   89:     */   @Temporal(TemporalType.TIME)
/*   90:     */   @Column(name="marcacion_salida_reingreso2", nullable=true)
/*   91:     */   private Date marcacionSalidaReingreso2;
/*   92:     */   @Column(name="horas_extras25", nullable=true)
/*   93:     */   @Digits(integer=12, fraction=2)
/*   94: 130 */   private BigDecimal horasExtras25 = BigDecimal.ZERO;
/*   95:     */   @Column(name="horas_extras25_aprobadas", nullable=true)
/*   96:     */   @Digits(integer=12, fraction=2)
/*   97: 134 */   private BigDecimal horasExtras25Aprobadas = BigDecimal.ZERO;
/*   98:     */   @Column(name="horas_extras50", nullable=true)
/*   99:     */   @Digits(integer=12, fraction=2)
/*  100: 138 */   private BigDecimal horasExtras50 = BigDecimal.ZERO;
/*  101:     */   @Column(name="horas_extras50_aprobadas", nullable=true)
/*  102:     */   @Digits(integer=12, fraction=2)
/*  103: 142 */   private BigDecimal horasExtras50Aprobadas = BigDecimal.ZERO;
/*  104:     */   @Column(name="horas_extras100", nullable=true)
/*  105:     */   @Digits(integer=12, fraction=2)
/*  106: 146 */   private BigDecimal horasExtras100 = BigDecimal.ZERO;
/*  107:     */   @Column(name="horas_extras100_feriado", nullable=true)
/*  108:     */   @Digits(integer=12, fraction=2)
/*  109: 150 */   private BigDecimal horasExtras100Feriado = BigDecimal.ZERO;
/*  110:     */   @Column(name="horas_extras100_feriado_aprobadas", nullable=false)
/*  111:     */   @Digits(integer=12, fraction=2)
/*  112:     */   @ColumnDefault("0")
/*  113:     */   @NotNull
/*  114: 154 */   private BigDecimal horasExtras100FeriadoAprobadas = BigDecimal.ZERO;
/*  115:     */   @Column(name="horas_extras100_aprobadas", nullable=true)
/*  116:     */   @Digits(integer=12, fraction=2)
/*  117: 160 */   private BigDecimal horasExtras100Aprobadas = BigDecimal.ZERO;
/*  118:     */   @Column(name="horas_falta", nullable=true)
/*  119:     */   @Digits(integer=12, fraction=2)
/*  120: 164 */   private BigDecimal horasFalta = BigDecimal.ZERO;
/*  121:     */   @Column(name="horas_permiso", nullable=true)
/*  122: 168 */   private BigDecimal horasPermiso = BigDecimal.ZERO;
/*  123:     */   @Column(name="indicador_vacacion", nullable=false)
/*  124:     */   private boolean indicadorVacacion;
/*  125:     */   @Column(name="indicador_dia_festivo", nullable=false)
/*  126: 174 */   private boolean indicadorDiaFestivo = false;
/*  127:     */   @Column(name="indicador_dia_descanso", nullable=true)
/*  128: 178 */   private Boolean indicadorDiaDescanso = Boolean.valueOf(false);
/*  129:     */   @Column(name="indicador_creada_manualmente", nullable=false)
/*  130:     */   @NotNull
/*  131:     */   private boolean indicadorCreadaManualmente;
/*  132:     */   @Column(name="indicador_modificado_manual1", nullable=true)
/*  133: 186 */   private Boolean indicadorModificadoManual1 = Boolean.valueOf(false);
/*  134:     */   @Column(name="indicador_modificado_manual2", nullable=true)
/*  135: 190 */   private Boolean indicadorModificadoManual2 = Boolean.valueOf(false);
/*  136:     */   @Column(name="indicador_modificado_manual3", nullable=true)
/*  137: 194 */   private Boolean indicadorModificadoManual3 = Boolean.valueOf(false);
/*  138:     */   @Column(name="indicador_modificado_manual4", nullable=true)
/*  139: 198 */   private Boolean indicadorModificadoManual4 = Boolean.valueOf(false);
/*  140:     */   @Column(name="indicador_modificado_manual5", nullable=true)
/*  141: 202 */   private Boolean indicadorModificadoManual5 = Boolean.valueOf(false);
/*  142:     */   @Column(name="indicador_modificado_manual6", nullable=true)
/*  143: 206 */   private Boolean indicadorModificadoManual6 = Boolean.valueOf(false);
/*  144:     */   @Column(name="indicador_modificado_manual7", nullable=true)
/*  145: 210 */   private Boolean indicadorModificadoManual7 = Boolean.valueOf(false);
/*  146:     */   @Column(name="indicador_modificado_manual8", nullable=true)
/*  147: 214 */   private Boolean indicadorModificadoManual8 = Boolean.valueOf(false);
/*  148:     */   @Column(name="indicador_modificado_manual9", nullable=true)
/*  149:     */   private Boolean indicadorModificadoManual9;
/*  150:     */   @Column(name="indicador_modificado_manual10", nullable=true)
/*  151:     */   private Boolean indicadorModificadoManual10;
/*  152:     */   @Column(name="indicador_modificado_manual11", nullable=true)
/*  153:     */   private Boolean indicadorModificadoManual11;
/*  154:     */   @Column(name="indicador_modificado_manual12", nullable=true)
/*  155:     */   private Boolean indicadorModificadoManual12;
/*  156:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  157:     */   @JoinColumn(name="id_asistencia_padre")
/*  158:     */   private Asistencia asistenciaPadre;
/*  159:     */   @Column(name="dia_mes")
/*  160:     */   private int diaMes;
/*  161:     */   @Column(name="horas_subsidio")
/*  162: 241 */   private Integer horasSubsidio = Integer.valueOf(0);
/*  163:     */   @Column(name="indicador_subsidio_vespertino")
/*  164: 244 */   private Boolean indicadorSubsidioVespertino = Boolean.valueOf(true);
/*  165:     */   @Column(name="descripcion", length=500)
/*  166:     */   @Size(max=500)
/*  167:     */   private String descripcion;
/*  168:     */   @Column(name="indicador_horas_adelanto_extra")
/*  169: 251 */   private Boolean indicadorHorasAdelantoExtra = Boolean.valueOf(false);
/*  170:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  171:     */   @JoinColumn(name="id_tipo_falta", nullable=true)
/*  172:     */   private TipoFalta tipoFalta;
/*  173:     */   @Transient
/*  174:     */   private Date salidaReal;
/*  175:     */   @Transient
/*  176:     */   private Date marcacionSalidaReal;
/*  177:     */   @Transient
/*  178:     */   private boolean editable;
/*  179:     */   @Transient
/*  180:     */   private String styleClass;
/*  181:     */   @Transient
/*  182:     */   private Asistencia asistenciaDiaPrevio;
/*  183:     */   @Column(name="indicador_pago_horas_suplementarias", nullable=true)
/*  184:     */   private boolean indicadorPagoHorasSuplementarias;
/*  185:     */   @Column(name="indicador_dia_complementario", nullable=false)
/*  186:     */   @NotNull
/*  187:     */   @ColumnDefault("'0'")
/*  188: 277 */   private boolean indicadorDiaComplementario = false;
/*  189:     */   @Column(name="indicador_dia_opcional", nullable=false)
/*  190:     */   @NotNull
/*  191:     */   @ColumnDefault("'0'")
/*  192: 282 */   private boolean indicadorDiaOpcional = false;
/*  193:     */   @Column(name="indicador_permiso_dia_completo", nullable=false)
/*  194:     */   @NotNull
/*  195:     */   @ColumnDefault("'0'")
/*  196: 287 */   private boolean indicadorPermisoDiaCompleto = false;
/*  197:     */   
/*  198:     */   public int getId()
/*  199:     */   {
/*  200: 298 */     return this.idAsistencia;
/*  201:     */   }
/*  202:     */   
/*  203:     */   public int getIdAsistencia()
/*  204:     */   {
/*  205: 305 */     return this.idAsistencia;
/*  206:     */   }
/*  207:     */   
/*  208:     */   public void setIdAsistencia(int idAsistencia)
/*  209:     */   {
/*  210: 313 */     this.idAsistencia = idAsistencia;
/*  211:     */   }
/*  212:     */   
/*  213:     */   public int getIdOrganizacion()
/*  214:     */   {
/*  215: 320 */     return this.idOrganizacion;
/*  216:     */   }
/*  217:     */   
/*  218:     */   public void setIdOrganizacion(int idOrganizacion)
/*  219:     */   {
/*  220: 328 */     this.idOrganizacion = idOrganizacion;
/*  221:     */   }
/*  222:     */   
/*  223:     */   public int getIdSucursal()
/*  224:     */   {
/*  225: 335 */     return this.idSucursal;
/*  226:     */   }
/*  227:     */   
/*  228:     */   public void setIdSucursal(int idSucursal)
/*  229:     */   {
/*  230: 343 */     this.idSucursal = idSucursal;
/*  231:     */   }
/*  232:     */   
/*  233:     */   public Empleado getEmpleado()
/*  234:     */   {
/*  235: 350 */     return this.empleado;
/*  236:     */   }
/*  237:     */   
/*  238:     */   public void setEmpleado(Empleado empleado)
/*  239:     */   {
/*  240: 358 */     this.empleado = empleado;
/*  241:     */   }
/*  242:     */   
/*  243:     */   public Date getFecha()
/*  244:     */   {
/*  245: 365 */     return this.fecha;
/*  246:     */   }
/*  247:     */   
/*  248:     */   public void setFecha(Date fecha)
/*  249:     */   {
/*  250: 373 */     this.fecha = fecha;
/*  251:     */   }
/*  252:     */   
/*  253:     */   public Date getEntrada()
/*  254:     */   {
/*  255: 380 */     return this.entrada;
/*  256:     */   }
/*  257:     */   
/*  258:     */   public void setEntrada(Date entrada)
/*  259:     */   {
/*  260: 388 */     this.entrada = entrada;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public Date getMarcacionEntrada()
/*  264:     */   {
/*  265: 395 */     return this.marcacionEntrada;
/*  266:     */   }
/*  267:     */   
/*  268:     */   public void setMarcacionEntrada(Date marcacionEntrada)
/*  269:     */   {
/*  270: 403 */     this.marcacionEntrada = marcacionEntrada;
/*  271:     */   }
/*  272:     */   
/*  273:     */   public Date getSalida()
/*  274:     */   {
/*  275: 410 */     return this.salida;
/*  276:     */   }
/*  277:     */   
/*  278:     */   public void setSalida(Date salida)
/*  279:     */   {
/*  280: 418 */     this.salida = salida;
/*  281:     */   }
/*  282:     */   
/*  283:     */   public Date getMarcacionSalida()
/*  284:     */   {
/*  285: 425 */     return this.marcacionSalida;
/*  286:     */   }
/*  287:     */   
/*  288:     */   public void setMarcacionSalida(Date marcacionSalida)
/*  289:     */   {
/*  290: 433 */     this.marcacionSalida = marcacionSalida;
/*  291:     */   }
/*  292:     */   
/*  293:     */   public Date getSalidaReceso()
/*  294:     */   {
/*  295: 440 */     return this.salidaReceso;
/*  296:     */   }
/*  297:     */   
/*  298:     */   public void setSalidaReceso(Date salidaReceso)
/*  299:     */   {
/*  300: 448 */     this.salidaReceso = salidaReceso;
/*  301:     */   }
/*  302:     */   
/*  303:     */   public Date getMarcacionSalidaReceso()
/*  304:     */   {
/*  305: 455 */     return this.marcacionSalidaReceso;
/*  306:     */   }
/*  307:     */   
/*  308:     */   public void setMarcacionSalidaReceso(Date marcacionSalidaReceso)
/*  309:     */   {
/*  310: 463 */     this.marcacionSalidaReceso = marcacionSalidaReceso;
/*  311:     */   }
/*  312:     */   
/*  313:     */   public Date getEntradaReceso()
/*  314:     */   {
/*  315: 470 */     return this.entradaReceso;
/*  316:     */   }
/*  317:     */   
/*  318:     */   public void setEntradaReceso(Date entradaReceso)
/*  319:     */   {
/*  320: 478 */     this.entradaReceso = entradaReceso;
/*  321:     */   }
/*  322:     */   
/*  323:     */   public Date getMarcacionEntradaReceso()
/*  324:     */   {
/*  325: 485 */     return this.marcacionEntradaReceso;
/*  326:     */   }
/*  327:     */   
/*  328:     */   public void setMarcacionEntradaReceso(Date marcacionEntradaReceso)
/*  329:     */   {
/*  330: 493 */     this.marcacionEntradaReceso = marcacionEntradaReceso;
/*  331:     */   }
/*  332:     */   
/*  333:     */   public BigDecimal getHorasExtras25()
/*  334:     */   {
/*  335: 500 */     return this.horasExtras25;
/*  336:     */   }
/*  337:     */   
/*  338:     */   public void setHorasExtras25(BigDecimal horasExtras25)
/*  339:     */   {
/*  340: 508 */     this.horasExtras25 = horasExtras25;
/*  341:     */   }
/*  342:     */   
/*  343:     */   public BigDecimal getHorasExtras50()
/*  344:     */   {
/*  345: 515 */     return this.horasExtras50;
/*  346:     */   }
/*  347:     */   
/*  348:     */   public void setHorasExtras50(BigDecimal horasExtras50)
/*  349:     */   {
/*  350: 523 */     this.horasExtras50 = horasExtras50;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public BigDecimal getHorasExtras100()
/*  354:     */   {
/*  355: 530 */     return this.horasExtras100;
/*  356:     */   }
/*  357:     */   
/*  358:     */   public void setHorasExtras100(BigDecimal horasExtras100)
/*  359:     */   {
/*  360: 538 */     this.horasExtras100 = horasExtras100;
/*  361:     */   }
/*  362:     */   
/*  363:     */   public BigDecimal getHorasExtras100Feriado()
/*  364:     */   {
/*  365: 545 */     return this.horasExtras100Feriado;
/*  366:     */   }
/*  367:     */   
/*  368:     */   public void setHorasExtras100Feriado(BigDecimal horasExtras100Feriado)
/*  369:     */   {
/*  370: 553 */     this.horasExtras100Feriado = horasExtras100Feriado;
/*  371:     */   }
/*  372:     */   
/*  373:     */   public SubsidioEmpleado getSubsidioEmpleado()
/*  374:     */   {
/*  375: 560 */     return this.subsidioEmpleado;
/*  376:     */   }
/*  377:     */   
/*  378:     */   public void setSubsidioEmpleado(SubsidioEmpleado subsidioEmpleado)
/*  379:     */   {
/*  380: 568 */     this.subsidioEmpleado = subsidioEmpleado;
/*  381:     */   }
/*  382:     */   
/*  383:     */   public boolean isIndicadorVacacion()
/*  384:     */   {
/*  385: 575 */     return this.indicadorVacacion;
/*  386:     */   }
/*  387:     */   
/*  388:     */   public void setIndicadorVacacion(boolean indicadorVacacion)
/*  389:     */   {
/*  390: 583 */     this.indicadorVacacion = indicadorVacacion;
/*  391:     */   }
/*  392:     */   
/*  393:     */   public boolean isIndicadorDiaFestivo()
/*  394:     */   {
/*  395: 590 */     return this.indicadorDiaFestivo;
/*  396:     */   }
/*  397:     */   
/*  398:     */   public void setIndicadorDiaFestivo(boolean indicadorDiaFestivo)
/*  399:     */   {
/*  400: 598 */     this.indicadorDiaFestivo = indicadorDiaFestivo;
/*  401:     */   }
/*  402:     */   
/*  403:     */   public Asistencia getAsistenciaPadre()
/*  404:     */   {
/*  405: 605 */     return this.asistenciaPadre;
/*  406:     */   }
/*  407:     */   
/*  408:     */   public void setAsistenciaPadre(Asistencia asistenciaPadre)
/*  409:     */   {
/*  410: 613 */     this.asistenciaPadre = asistenciaPadre;
/*  411:     */   }
/*  412:     */   
/*  413:     */   public int getDiaMes()
/*  414:     */   {
/*  415: 620 */     return this.diaMes;
/*  416:     */   }
/*  417:     */   
/*  418:     */   public void setDiaMes(int diaMes)
/*  419:     */   {
/*  420: 628 */     this.diaMes = diaMes;
/*  421:     */   }
/*  422:     */   
/*  423:     */   public Integer getHorasSubsidio()
/*  424:     */   {
/*  425: 635 */     return this.horasSubsidio;
/*  426:     */   }
/*  427:     */   
/*  428:     */   public void setHorasSubsidio(Integer horasSubsidio)
/*  429:     */   {
/*  430: 643 */     this.horasSubsidio = horasSubsidio;
/*  431:     */   }
/*  432:     */   
/*  433:     */   public Boolean getIndicadorSubsidioVespertino()
/*  434:     */   {
/*  435: 650 */     return this.indicadorSubsidioVespertino;
/*  436:     */   }
/*  437:     */   
/*  438:     */   public void setIndicadorSubsidioVespertino(Boolean indicadorSubsidioVespertino)
/*  439:     */   {
/*  440: 658 */     this.indicadorSubsidioVespertino = indicadorSubsidioVespertino;
/*  441:     */   }
/*  442:     */   
/*  443:     */   public BigDecimal getHorasFalta()
/*  444:     */   {
/*  445: 662 */     if (this.horasFalta == null) {
/*  446: 663 */       this.horasFalta = BigDecimal.ZERO;
/*  447:     */     }
/*  448: 665 */     return this.horasFalta;
/*  449:     */   }
/*  450:     */   
/*  451:     */   public void setHorasFalta(BigDecimal horasFalta)
/*  452:     */   {
/*  453: 669 */     this.horasFalta = horasFalta;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public BigDecimal getHorasPermiso()
/*  457:     */   {
/*  458: 673 */     if (this.horasPermiso == null) {
/*  459: 674 */       this.horasPermiso = BigDecimal.ZERO;
/*  460:     */     }
/*  461: 676 */     return this.horasPermiso;
/*  462:     */   }
/*  463:     */   
/*  464:     */   public void setHorasPermiso(BigDecimal horasPermiso)
/*  465:     */   {
/*  466: 680 */     this.horasPermiso = horasPermiso;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public Date getSalidaReal()
/*  470:     */   {
/*  471: 684 */     if (this.salida == null)
/*  472:     */     {
/*  473: 685 */       this.salidaReal = null;
/*  474:     */     }
/*  475:     */     else
/*  476:     */     {
/*  477: 687 */       Calendar salidaRealC = Calendar.getInstance();
/*  478: 688 */       salidaRealC.setTime(this.salida);
/*  479: 689 */       if (salidaRealC.get(12) == 0)
/*  480:     */       {
/*  481: 690 */         if (salidaRealC.get(11) == 0) {
/*  482: 691 */           salidaRealC.add(5, 1);
/*  483:     */         }
/*  484: 693 */         salidaRealC.add(12, -1);
/*  485:     */       }
/*  486: 695 */       this.salidaReal = salidaRealC.getTime();
/*  487:     */     }
/*  488: 697 */     return this.salidaReal;
/*  489:     */   }
/*  490:     */   
/*  491:     */   public void setSalidaReal(Date salidaReal)
/*  492:     */   {
/*  493: 701 */     this.salidaReal = salidaReal;
/*  494:     */   }
/*  495:     */   
/*  496:     */   public Date getMarcacionSalidaReal()
/*  497:     */   {
/*  498: 705 */     if (this.marcacionSalida == null)
/*  499:     */     {
/*  500: 706 */       this.marcacionSalidaReal = null;
/*  501:     */     }
/*  502:     */     else
/*  503:     */     {
/*  504: 708 */       Date marcacion = this.marcacionSalidaReingreso2 != null ? this.marcacionSalidaReingreso2 : this.marcacionSalidaReingreso1;
/*  505: 709 */       marcacion = marcacion != null ? marcacion : this.marcacionSalida;
/*  506: 710 */       Calendar marcacionSalidaRealC = Calendar.getInstance();
/*  507: 711 */       marcacionSalidaRealC.setTime(this.marcacionSalida);
/*  508: 712 */       if (marcacionSalidaRealC.get(12) == 0)
/*  509:     */       {
/*  510: 713 */         if (marcacionSalidaRealC.get(11) == 0) {
/*  511: 714 */           marcacionSalidaRealC.add(5, 1);
/*  512:     */         }
/*  513: 716 */         marcacionSalidaRealC.add(12, -1);
/*  514:     */       }
/*  515: 718 */       this.marcacionSalidaReal = marcacionSalidaRealC.getTime();
/*  516:     */     }
/*  517: 720 */     return this.marcacionSalidaReal;
/*  518:     */   }
/*  519:     */   
/*  520:     */   public void setMarcacionSalidaReal(Date marcacionSalidaReal)
/*  521:     */   {
/*  522: 724 */     this.marcacionSalidaReal = marcacionSalidaReal;
/*  523:     */   }
/*  524:     */   
/*  525:     */   public BigDecimal getHorasExtras25Aprobadas()
/*  526:     */   {
/*  527: 728 */     if (this.horasExtras25Aprobadas == null) {
/*  528: 729 */       this.horasExtras25Aprobadas = BigDecimal.ZERO;
/*  529:     */     }
/*  530: 731 */     return this.horasExtras25Aprobadas;
/*  531:     */   }
/*  532:     */   
/*  533:     */   public void setHorasExtras25Aprobadas(BigDecimal horasExtras25Aprobadas)
/*  534:     */   {
/*  535: 735 */     this.horasExtras25Aprobadas = horasExtras25Aprobadas;
/*  536:     */   }
/*  537:     */   
/*  538:     */   public BigDecimal getHorasExtras50Aprobadas()
/*  539:     */   {
/*  540: 739 */     if (this.horasExtras50Aprobadas == null) {
/*  541: 740 */       this.horasExtras50Aprobadas = BigDecimal.ZERO;
/*  542:     */     }
/*  543: 742 */     return this.horasExtras50Aprobadas;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void setHorasExtras50Aprobadas(BigDecimal horasExtras50Aprobadas)
/*  547:     */   {
/*  548: 746 */     this.horasExtras50Aprobadas = horasExtras50Aprobadas;
/*  549:     */   }
/*  550:     */   
/*  551:     */   public BigDecimal getHorasExtras100Aprobadas()
/*  552:     */   {
/*  553: 750 */     if (this.horasExtras100Aprobadas == null) {
/*  554: 751 */       this.horasExtras100Aprobadas = BigDecimal.ZERO;
/*  555:     */     }
/*  556: 753 */     return this.horasExtras100Aprobadas;
/*  557:     */   }
/*  558:     */   
/*  559:     */   public void setHorasExtras100Aprobadas(BigDecimal horasExtras100Aprobadas)
/*  560:     */   {
/*  561: 757 */     this.horasExtras100Aprobadas = horasExtras100Aprobadas;
/*  562:     */   }
/*  563:     */   
/*  564:     */   public String getDescripcion()
/*  565:     */   {
/*  566: 764 */     return this.descripcion;
/*  567:     */   }
/*  568:     */   
/*  569:     */   public void setDescripcion(String descripcion)
/*  570:     */   {
/*  571: 772 */     this.descripcion = descripcion;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public Boolean getIndicadorHorasAdelantoExtra()
/*  575:     */   {
/*  576: 776 */     if (this.indicadorHorasAdelantoExtra == null) {
/*  577: 777 */       this.indicadorHorasAdelantoExtra = Boolean.valueOf(false);
/*  578:     */     }
/*  579: 779 */     return this.indicadorHorasAdelantoExtra;
/*  580:     */   }
/*  581:     */   
/*  582:     */   public void setIndicadorHorasAdelantoExtra(Boolean indicadorHorasAdelantoExtra)
/*  583:     */   {
/*  584: 783 */     this.indicadorHorasAdelantoExtra = indicadorHorasAdelantoExtra;
/*  585:     */   }
/*  586:     */   
/*  587:     */   public boolean isEditable()
/*  588:     */   {
/*  589: 787 */     this.editable = true;
/*  590: 788 */     if ((this.subsidioEmpleado != null) && (this.horasSubsidio.intValue() == 0)) {
/*  591: 789 */       this.editable = false;
/*  592:     */     }
/*  593: 791 */     if (this.indicadorVacacion) {
/*  594: 792 */       this.editable = false;
/*  595:     */     }
/*  596: 794 */     Calendar hoy = Calendar.getInstance();
/*  597: 795 */     hoy.set(11, 0);
/*  598: 796 */     hoy.set(12, 0);
/*  599: 797 */     hoy.set(13, 0);
/*  600: 798 */     hoy.set(14, 0);
/*  601: 799 */     if (!this.fecha.before(hoy.getTime())) {
/*  602: 800 */       this.editable = false;
/*  603:     */     }
/*  604: 803 */     return this.editable;
/*  605:     */   }
/*  606:     */   
/*  607:     */   public void setEditable(boolean editable)
/*  608:     */   {
/*  609: 807 */     this.editable = editable;
/*  610:     */   }
/*  611:     */   
/*  612:     */   public Boolean getIndicadorDiaDescanso()
/*  613:     */   {
/*  614: 811 */     if (this.indicadorDiaDescanso == null) {
/*  615: 812 */       this.indicadorDiaDescanso = Boolean.valueOf(false);
/*  616:     */     }
/*  617: 814 */     return this.indicadorDiaDescanso;
/*  618:     */   }
/*  619:     */   
/*  620:     */   public void setIndicadorDiaDescanso(Boolean indicadorDiaDescanso)
/*  621:     */   {
/*  622: 818 */     this.indicadorDiaDescanso = indicadorDiaDescanso;
/*  623:     */   }
/*  624:     */   
/*  625:     */   public String getStyleClass()
/*  626:     */   {
/*  627: 822 */     if (getIndicadorDiaDescanso().booleanValue()) {
/*  628: 823 */       this.styleClass = "color_dia_descanso";
/*  629:     */     }
/*  630: 825 */     return this.styleClass;
/*  631:     */   }
/*  632:     */   
/*  633:     */   public void setStyleClass(String styleClass)
/*  634:     */   {
/*  635: 829 */     this.styleClass = styleClass;
/*  636:     */   }
/*  637:     */   
/*  638:     */   public Boolean getIndicadorModificadoManual1()
/*  639:     */   {
/*  640: 833 */     if (this.indicadorModificadoManual1 == null) {
/*  641: 834 */       this.indicadorModificadoManual1 = Boolean.valueOf(false);
/*  642:     */     }
/*  643: 836 */     return this.indicadorModificadoManual1;
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void setIndicadorModificadoManual1(Boolean indicadorModificadoManual1)
/*  647:     */   {
/*  648: 840 */     this.indicadorModificadoManual1 = indicadorModificadoManual1;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public Boolean getIndicadorModificadoManual2()
/*  652:     */   {
/*  653: 844 */     if (this.indicadorModificadoManual2 == null) {
/*  654: 845 */       this.indicadorModificadoManual2 = Boolean.valueOf(false);
/*  655:     */     }
/*  656: 847 */     return this.indicadorModificadoManual2;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void setIndicadorModificadoManual2(Boolean indicadorModificadoManual2)
/*  660:     */   {
/*  661: 851 */     this.indicadorModificadoManual2 = indicadorModificadoManual2;
/*  662:     */   }
/*  663:     */   
/*  664:     */   public Boolean getIndicadorModificadoManual3()
/*  665:     */   {
/*  666: 855 */     if (this.indicadorModificadoManual3 == null) {
/*  667: 856 */       this.indicadorModificadoManual3 = Boolean.valueOf(false);
/*  668:     */     }
/*  669: 858 */     return this.indicadorModificadoManual3;
/*  670:     */   }
/*  671:     */   
/*  672:     */   public void setIndicadorModificadoManual3(Boolean indicadorModificadoManual3)
/*  673:     */   {
/*  674: 862 */     this.indicadorModificadoManual3 = indicadorModificadoManual3;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public Boolean getIndicadorModificadoManual4()
/*  678:     */   {
/*  679: 866 */     if (this.indicadorModificadoManual4 == null) {
/*  680: 867 */       this.indicadorModificadoManual4 = Boolean.valueOf(false);
/*  681:     */     }
/*  682: 869 */     return this.indicadorModificadoManual4;
/*  683:     */   }
/*  684:     */   
/*  685:     */   public void setIndicadorModificadoManual4(Boolean indicadorModificadoManual4)
/*  686:     */   {
/*  687: 873 */     this.indicadorModificadoManual4 = indicadorModificadoManual4;
/*  688:     */   }
/*  689:     */   
/*  690:     */   public Boolean getIndicadorModificadoManual5()
/*  691:     */   {
/*  692: 877 */     if (this.indicadorModificadoManual5 == null) {
/*  693: 878 */       this.indicadorModificadoManual5 = Boolean.valueOf(false);
/*  694:     */     }
/*  695: 880 */     return this.indicadorModificadoManual5;
/*  696:     */   }
/*  697:     */   
/*  698:     */   public void setIndicadorModificadoManual5(Boolean indicadorModificadoManual5)
/*  699:     */   {
/*  700: 884 */     this.indicadorModificadoManual5 = indicadorModificadoManual5;
/*  701:     */   }
/*  702:     */   
/*  703:     */   public Boolean getIndicadorModificadoManual6()
/*  704:     */   {
/*  705: 888 */     if (this.indicadorModificadoManual6 == null) {
/*  706: 889 */       this.indicadorModificadoManual6 = Boolean.valueOf(false);
/*  707:     */     }
/*  708: 891 */     return this.indicadorModificadoManual6;
/*  709:     */   }
/*  710:     */   
/*  711:     */   public void setIndicadorModificadoManual6(Boolean indicadorModificadoManual6)
/*  712:     */   {
/*  713: 895 */     this.indicadorModificadoManual6 = indicadorModificadoManual6;
/*  714:     */   }
/*  715:     */   
/*  716:     */   public Boolean getIndicadorModificadoManual7()
/*  717:     */   {
/*  718: 899 */     if (this.indicadorModificadoManual7 == null) {
/*  719: 900 */       this.indicadorModificadoManual7 = Boolean.valueOf(false);
/*  720:     */     }
/*  721: 902 */     return this.indicadorModificadoManual7;
/*  722:     */   }
/*  723:     */   
/*  724:     */   public void setIndicadorModificadoManual7(Boolean indicadorModificadoManual7)
/*  725:     */   {
/*  726: 906 */     this.indicadorModificadoManual7 = indicadorModificadoManual7;
/*  727:     */   }
/*  728:     */   
/*  729:     */   public Boolean getIndicadorModificadoManual8()
/*  730:     */   {
/*  731: 910 */     if (this.indicadorModificadoManual8 == null) {
/*  732: 911 */       this.indicadorModificadoManual8 = Boolean.valueOf(false);
/*  733:     */     }
/*  734: 913 */     return this.indicadorModificadoManual8;
/*  735:     */   }
/*  736:     */   
/*  737:     */   public void setIndicadorModificadoManual8(Boolean indicadorModificadoManual8)
/*  738:     */   {
/*  739: 917 */     this.indicadorModificadoManual8 = indicadorModificadoManual8;
/*  740:     */   }
/*  741:     */   
/*  742:     */   public boolean isIndicadorCreadaManualmente()
/*  743:     */   {
/*  744: 921 */     return this.indicadorCreadaManualmente;
/*  745:     */   }
/*  746:     */   
/*  747:     */   public void setIndicadorCreadaManualmente(boolean indicadorCreadaManualmente)
/*  748:     */   {
/*  749: 925 */     this.indicadorCreadaManualmente = indicadorCreadaManualmente;
/*  750:     */   }
/*  751:     */   
/*  752:     */   public Asistencia getAsistenciaDiaPrevio()
/*  753:     */   {
/*  754: 932 */     return this.asistenciaDiaPrevio;
/*  755:     */   }
/*  756:     */   
/*  757:     */   public void setAsistenciaDiaPrevio(Asistencia asistenciaDiaPrevio)
/*  758:     */   {
/*  759: 940 */     this.asistenciaDiaPrevio = asistenciaDiaPrevio;
/*  760:     */   }
/*  761:     */   
/*  762:     */   public boolean isIndicadorPagoHorasSuplementarias()
/*  763:     */   {
/*  764: 944 */     return this.indicadorPagoHorasSuplementarias;
/*  765:     */   }
/*  766:     */   
/*  767:     */   public void setIndicadorPagoHorasSuplementarias(boolean indicadorPagoHorasSuplementarias)
/*  768:     */   {
/*  769: 948 */     this.indicadorPagoHorasSuplementarias = indicadorPagoHorasSuplementarias;
/*  770:     */   }
/*  771:     */   
/*  772:     */   public boolean isIndicadorDiaComplementario()
/*  773:     */   {
/*  774: 952 */     return this.indicadorDiaComplementario;
/*  775:     */   }
/*  776:     */   
/*  777:     */   public void setIndicadorDiaComplementario(boolean indicadorDiaComplementario)
/*  778:     */   {
/*  779: 956 */     this.indicadorDiaComplementario = indicadorDiaComplementario;
/*  780:     */   }
/*  781:     */   
/*  782:     */   public TipoFalta getTipoFalta()
/*  783:     */   {
/*  784: 960 */     return this.tipoFalta;
/*  785:     */   }
/*  786:     */   
/*  787:     */   public void setTipoFalta(TipoFalta tipoFalta)
/*  788:     */   {
/*  789: 964 */     this.tipoFalta = tipoFalta;
/*  790:     */   }
/*  791:     */   
/*  792:     */   public boolean isIndicadorDiaOpcional()
/*  793:     */   {
/*  794: 968 */     return this.indicadorDiaOpcional;
/*  795:     */   }
/*  796:     */   
/*  797:     */   public void setIndicadorDiaOpcional(boolean indicadorDiaOpcional)
/*  798:     */   {
/*  799: 972 */     this.indicadorDiaOpcional = indicadorDiaOpcional;
/*  800:     */   }
/*  801:     */   
/*  802:     */   public Date getFechaFiltro()
/*  803:     */   {
/*  804: 976 */     return this.fechaFiltro;
/*  805:     */   }
/*  806:     */   
/*  807:     */   public void setFechaFiltro(Date fechaFiltro)
/*  808:     */   {
/*  809: 980 */     this.fechaFiltro = fechaFiltro;
/*  810:     */   }
/*  811:     */   
/*  812:     */   public BigDecimal getHorasExtras100FeriadoAprobadas()
/*  813:     */   {
/*  814: 984 */     return this.horasExtras100FeriadoAprobadas;
/*  815:     */   }
/*  816:     */   
/*  817:     */   public void setHorasExtras100FeriadoAprobadas(BigDecimal horasExtras100FeriadoAprobadas)
/*  818:     */   {
/*  819: 988 */     this.horasExtras100FeriadoAprobadas = horasExtras100FeriadoAprobadas;
/*  820:     */   }
/*  821:     */   
/*  822:     */   public Date getMarcacionEntradaReingreso1()
/*  823:     */   {
/*  824: 992 */     return this.marcacionEntradaReingreso1;
/*  825:     */   }
/*  826:     */   
/*  827:     */   public void setMarcacionEntradaReingreso1(Date marcacionEntradaReingreso1)
/*  828:     */   {
/*  829: 996 */     this.marcacionEntradaReingreso1 = marcacionEntradaReingreso1;
/*  830:     */   }
/*  831:     */   
/*  832:     */   public Date getMarcacionSalidaReingreso1()
/*  833:     */   {
/*  834:1000 */     return this.marcacionSalidaReingreso1;
/*  835:     */   }
/*  836:     */   
/*  837:     */   public void setMarcacionSalidaReingreso1(Date marcacionSalidaReingreso1)
/*  838:     */   {
/*  839:1004 */     this.marcacionSalidaReingreso1 = marcacionSalidaReingreso1;
/*  840:     */   }
/*  841:     */   
/*  842:     */   public Date getMarcacionEntradaReingreso2()
/*  843:     */   {
/*  844:1008 */     return this.marcacionEntradaReingreso2;
/*  845:     */   }
/*  846:     */   
/*  847:     */   public void setMarcacionEntradaReingreso2(Date marcacionEntradaReingreso2)
/*  848:     */   {
/*  849:1012 */     this.marcacionEntradaReingreso2 = marcacionEntradaReingreso2;
/*  850:     */   }
/*  851:     */   
/*  852:     */   public Date getMarcacionSalidaReingreso2()
/*  853:     */   {
/*  854:1016 */     return this.marcacionSalidaReingreso2;
/*  855:     */   }
/*  856:     */   
/*  857:     */   public void setMarcacionSalidaReingreso2(Date marcacionSalidaReingreso2)
/*  858:     */   {
/*  859:1020 */     this.marcacionSalidaReingreso2 = marcacionSalidaReingreso2;
/*  860:     */   }
/*  861:     */   
/*  862:     */   public Boolean getIndicadorModificadoManual9()
/*  863:     */   {
/*  864:1024 */     if (this.indicadorModificadoManual9 == null) {
/*  865:1025 */       this.indicadorModificadoManual9 = Boolean.valueOf(false);
/*  866:     */     }
/*  867:1027 */     return this.indicadorModificadoManual9;
/*  868:     */   }
/*  869:     */   
/*  870:     */   public void setIndicadorModificadoManual9(Boolean indicadorModificadoManual9)
/*  871:     */   {
/*  872:1031 */     this.indicadorModificadoManual9 = indicadorModificadoManual9;
/*  873:     */   }
/*  874:     */   
/*  875:     */   public Boolean getIndicadorModificadoManual10()
/*  876:     */   {
/*  877:1035 */     if (this.indicadorModificadoManual10 == null) {
/*  878:1036 */       this.indicadorModificadoManual10 = Boolean.valueOf(false);
/*  879:     */     }
/*  880:1038 */     return this.indicadorModificadoManual10;
/*  881:     */   }
/*  882:     */   
/*  883:     */   public void setIndicadorModificadoManual10(Boolean indicadorModificadoManual10)
/*  884:     */   {
/*  885:1042 */     this.indicadorModificadoManual10 = indicadorModificadoManual10;
/*  886:     */   }
/*  887:     */   
/*  888:     */   public Boolean getIndicadorModificadoManual11()
/*  889:     */   {
/*  890:1046 */     if (this.indicadorModificadoManual11 == null) {
/*  891:1047 */       this.indicadorModificadoManual11 = Boolean.valueOf(false);
/*  892:     */     }
/*  893:1049 */     return this.indicadorModificadoManual11;
/*  894:     */   }
/*  895:     */   
/*  896:     */   public void setIndicadorModificadoManual11(Boolean indicadorModificadoManual11)
/*  897:     */   {
/*  898:1053 */     this.indicadorModificadoManual11 = indicadorModificadoManual11;
/*  899:     */   }
/*  900:     */   
/*  901:     */   public Boolean getIndicadorModificadoManual12()
/*  902:     */   {
/*  903:1057 */     if (this.indicadorModificadoManual12 == null) {
/*  904:1058 */       this.indicadorModificadoManual12 = Boolean.valueOf(false);
/*  905:     */     }
/*  906:1060 */     return this.indicadorModificadoManual12;
/*  907:     */   }
/*  908:     */   
/*  909:     */   public void setIndicadorModificadoManual12(Boolean indicadorModificadoManual12)
/*  910:     */   {
/*  911:1064 */     this.indicadorModificadoManual12 = indicadorModificadoManual12;
/*  912:     */   }
/*  913:     */   
/*  914:     */   public boolean isIndicadorPermisoDiaCompleto()
/*  915:     */   {
/*  916:1068 */     return this.indicadorPermisoDiaCompleto;
/*  917:     */   }
/*  918:     */   
/*  919:     */   public void setIndicadorPermisoDiaCompleto(boolean indicadorPermisoDiaCompleto)
/*  920:     */   {
/*  921:1072 */     this.indicadorPermisoDiaCompleto = indicadorPermisoDiaCompleto;
/*  922:     */   }
/*  923:     */   
/*  924:     */   @PrePersist
/*  925:     */   @PreUpdate
/*  926:     */   public void actualizarFechaFiltro()
/*  927:     */   {
/*  928:1078 */     if (this.asistenciaPadre == null) {
/*  929:1079 */       this.fechaFiltro = this.fecha;
/*  930:     */     } else {
/*  931:1081 */       this.fechaFiltro = this.asistenciaPadre.getFecha();
/*  932:     */     }
/*  933:     */   }
/*  934:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.Asistencia
 * JD-Core Version:    0.7.0.1
 */