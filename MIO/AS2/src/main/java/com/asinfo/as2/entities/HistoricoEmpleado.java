/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.OneToOne;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Temporal;
/*  23:    */ import javax.persistence.TemporalType;
/*  24:    */ import javax.persistence.Transient;
/*  25:    */ import javax.validation.constraints.Digits;
/*  26:    */ import javax.validation.constraints.Min;
/*  27:    */ import javax.validation.constraints.NotNull;
/*  28:    */ import javax.validation.constraints.Size;
/*  29:    */ 
/*  30:    */ @Entity
/*  31:    */ @Table(name="historico_empleado")
/*  32:    */ public class HistoricoEmpleado
/*  33:    */   extends EntidadBase
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @Id
/*  37:    */   @TableGenerator(name="historico_empleado", initialValue=0, allocationSize=50)
/*  38:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="historico_empleado")
/*  39:    */   @Column(name="id_historico_empleado")
/*  40:    */   private int idHistoricoEmpleado;
/*  41:    */   @Column(name="id_organizacion", nullable=false)
/*  42:    */   private int idOrganizacion;
/*  43:    */   @Column(name="id_sucursal", nullable=false)
/*  44:    */   private int idSucursal;
/*  45:    */   @Column(name="numero_finiquito", nullable=false, length=20)
/*  46:    */   @NotNull
/*  47: 73 */   private String numeroFiniquito = "";
/*  48:    */   @Temporal(TemporalType.DATE)
/*  49:    */   @Column(name="fecha_ingreso", nullable=false)
/*  50:    */   @NotNull
/*  51:    */   private Date fechaIngreso;
/*  52:    */   @Temporal(TemporalType.DATE)
/*  53:    */   @Column(name="fecha_salida", nullable=true)
/*  54:    */   private Date fechaSalida;
/*  55:    */   @Temporal(TemporalType.DATE)
/*  56:    */   @Column(name="fecha_finiquito", nullable=true)
/*  57:    */   private Date fechaFiniquito;
/*  58:    */   @Column(name="fecha_vencimiento_contrato", nullable=true)
/*  59:    */   @Temporal(TemporalType.DATE)
/*  60:    */   private Date fechaVencimientoContrato;
/*  61:    */   @Column(name="estado_finiquito", nullable=true)
/*  62:    */   @Enumerated(EnumType.ORDINAL)
/*  63:    */   private Estado estadoFiniquito;
/*  64:    */   @Column(name="descripcion", length=200)
/*  65:    */   @Size(max=200)
/*  66:    */   private String descripcion;
/*  67:    */   @Column(name="dias_fondo_reserva", nullable=false)
/*  68:    */   @Min(0L)
/*  69:    */   private int diasFondoReserva;
/*  70:    */   @Column(name="contrato", nullable=true, length=50)
/*  71:    */   @Size(max=50)
/*  72:    */   private String contrato;
/*  73:    */   @Column(name="valor_ultima_remuneracion", precision=12, scale=2)
/*  74:    */   @Digits(integer=12, fraction=2)
/*  75:110 */   private BigDecimal valorUltimaRemuneracion = BigDecimal.ZERO;
/*  76:    */   @Column(name="valor_remuneracion_vacacion", precision=12, scale=2)
/*  77:    */   @Digits(integer=12, fraction=2)
/*  78:114 */   private BigDecimal valorRemuneracionVacacion = BigDecimal.ZERO;
/*  79:    */   @Column(name="valor_remuneracion_decimo_tercero", precision=12, scale=2)
/*  80:    */   @Digits(integer=12, fraction=2)
/*  81:118 */   private BigDecimal valorRemuneracionDecimoTercero = BigDecimal.ZERO;
/*  82:    */   @Column(name="dias_vacaciones_finiquito", nullable=true)
/*  83:    */   private int diasVacacionesFiniquito;
/*  84:    */   @Column(name="saldo_prestamos", precision=12, scale=2)
/*  85:    */   @Digits(integer=12, fraction=2)
/*  86:125 */   private BigDecimal saldoPrestamos = BigDecimal.ZERO;
/*  87:    */   @Column(name="indicador_finiquito", nullable=false)
/*  88:    */   @NotNull
/*  89:129 */   private boolean indicadorFiniquito = false;
/*  90:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  91:    */   @JoinColumn(name="id_empleado", nullable=false, insertable=true, updatable=false)
/*  92:    */   @NotNull
/*  93:    */   private Empleado empleado;
/*  94:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  95:    */   @JoinColumn(name="id_causa_salida_empleado", nullable=true)
/*  96:    */   private CausaSalidaEmpleado causaSalidaEmpleado;
/*  97:    */   @OneToOne(fetch=FetchType.LAZY)
/*  98:    */   @JoinColumn(name="id_pago_rol_empleado", nullable=true)
/*  99:    */   private PagoRolEmpleado pagoRolEmpleadoFiniquito;
/* 100:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="historicoEmpleado")
/* 101:149 */   private List<Vacacion> listaVacacion = new ArrayList();
/* 102:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="historicoEmpleado")
/* 103:152 */   private List<PermisoEmpleado> listaPermisoEmpleado = new ArrayList();
/* 104:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="historicoEmpleado")
/* 105:155 */   private List<PagoRolEmpleado> listaPagoRolEmpleado = new ArrayList();
/* 106:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="historicoEmpleado")
/* 107:158 */   private List<DetalleHistoricoEmpleado> listaDetalleHistoricoEmpleado = new ArrayList();
/* 108:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="historicoEmpleado")
/* 109:161 */   private List<DetalleFiniquitoEmpleado> listaDetalleFiniquitoEmpleado = new ArrayList();
/* 110:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 111:    */   @JoinColumn(name="id_rubro_fondo_reserva", nullable=true)
/* 112:    */   private Rubro rubroFondoReserva;
/* 113:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 114:    */   @JoinColumn(name="id_rubro_decimo_tercero", nullable=true)
/* 115:    */   private Rubro rubroDecimoTercero;
/* 116:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 117:    */   @JoinColumn(name="id_rubro_decimo_cuarto", nullable=true)
/* 118:    */   private Rubro rubroDecimoCuarto;
/* 119:    */   @Column(name="valor_desahucio", precision=12, scale=2)
/* 120:    */   @Digits(integer=12, fraction=2)
/* 121:176 */   private BigDecimal valorDesahucio = BigDecimal.ZERO;
/* 122:    */   @Transient
/* 123:    */   private Date fechaInicioPeriodo;
/* 124:    */   @Transient
/* 125:    */   private Date fechaFinPeriodo;
/* 126:    */   
/* 127:    */   public HistoricoEmpleado() {}
/* 128:    */   
/* 129:    */   public HistoricoEmpleado(HistoricoEmpleado historico)
/* 130:    */   {
/* 131:199 */     this.idHistoricoEmpleado = historico.getIdHistoricoEmpleado();
/* 132:200 */     this.fechaIngreso = historico.getFechaIngreso();
/* 133:201 */     this.fechaSalida = historico.getFechaSalida();
/* 134:202 */     this.diasFondoReserva = historico.getDiasFondoReserva();
/* 135:203 */     this.contrato = historico.getContrato();
/* 136:204 */     this.empleado = historico.getEmpleado();
/* 137:205 */     this.estadoFiniquito = historico.getEstadoFiniquito();
/* 138:206 */     this.fechaVencimientoContrato = historico.getFechaVencimientoContrato();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public HistoricoEmpleado(int idHistoricoEmpleado, Date fechaInicioPeriodo, Date fechaFinPeriodo)
/* 142:    */   {
/* 143:211 */     this.idHistoricoEmpleado = idHistoricoEmpleado;
/* 144:212 */     this.fechaInicioPeriodo = fechaInicioPeriodo;
/* 145:213 */     this.fechaFinPeriodo = fechaFinPeriodo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int getIdHistoricoEmpleado()
/* 149:    */   {
/* 150:226 */     return this.idHistoricoEmpleado;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIdHistoricoEmpleado(int idHistoricoEmpleado)
/* 154:    */   {
/* 155:236 */     this.idHistoricoEmpleado = idHistoricoEmpleado;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int getIdOrganizacion()
/* 159:    */   {
/* 160:245 */     return this.idOrganizacion;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setIdOrganizacion(int idOrganizacion)
/* 164:    */   {
/* 165:255 */     this.idOrganizacion = idOrganizacion;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public int getIdSucursal()
/* 169:    */   {
/* 170:264 */     return this.idSucursal;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIdSucursal(int idSucursal)
/* 174:    */   {
/* 175:274 */     this.idSucursal = idSucursal;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Date getFechaIngreso()
/* 179:    */   {
/* 180:283 */     return this.fechaIngreso;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setFechaIngreso(Date fechaIngreso)
/* 184:    */   {
/* 185:293 */     this.fechaIngreso = fechaIngreso;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Date getFechaSalida()
/* 189:    */   {
/* 190:302 */     return this.fechaSalida;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setFechaSalida(Date fechaSalida)
/* 194:    */   {
/* 195:312 */     this.fechaSalida = fechaSalida;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Date getFechaFiniquito()
/* 199:    */   {
/* 200:321 */     return this.fechaFiniquito;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setFechaFiniquito(Date fechaFiniquito)
/* 204:    */   {
/* 205:331 */     this.fechaFiniquito = fechaFiniquito;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Estado getEstadoFiniquito()
/* 209:    */   {
/* 210:340 */     return this.estadoFiniquito;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setEstadoFiniquito(Estado estadoFiniquito)
/* 214:    */   {
/* 215:350 */     this.estadoFiniquito = estadoFiniquito;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String getDescripcion()
/* 219:    */   {
/* 220:359 */     return this.descripcion;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setDescripcion(String descripcion)
/* 224:    */   {
/* 225:369 */     this.descripcion = descripcion;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Empleado getEmpleado()
/* 229:    */   {
/* 230:378 */     return this.empleado;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setEmpleado(Empleado empleado)
/* 234:    */   {
/* 235:388 */     this.empleado = empleado;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public CausaSalidaEmpleado getCausaSalidaEmpleado()
/* 239:    */   {
/* 240:397 */     return this.causaSalidaEmpleado;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setCausaSalidaEmpleado(CausaSalidaEmpleado causaSalidaEmpleado)
/* 244:    */   {
/* 245:407 */     this.causaSalidaEmpleado = causaSalidaEmpleado;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public PagoRolEmpleado getPagoRolEmpleadoFiniquito()
/* 249:    */   {
/* 250:416 */     return this.pagoRolEmpleadoFiniquito;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setPagoRolEmpleadoFiniquito(PagoRolEmpleado pagoRolEmpleadoFiniquito)
/* 254:    */   {
/* 255:426 */     this.pagoRolEmpleadoFiniquito = pagoRolEmpleadoFiniquito;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public List<Vacacion> getListaVacacion()
/* 259:    */   {
/* 260:435 */     return this.listaVacacion;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public List<Vacacion> getListaVacacionPendiente()
/* 264:    */   {
/* 265:444 */     List<Vacacion> listaPendientes = new ArrayList();
/* 266:445 */     for (Vacacion vacacion : this.listaVacacion) {
/* 267:446 */       if (vacacion.getDias() + vacacion.getDiasAdicionales() > vacacion.getDiasTomados()) {
/* 268:447 */         listaPendientes.add(vacacion);
/* 269:    */       }
/* 270:    */     }
/* 271:450 */     return listaPendientes;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setListaVacacion(List<Vacacion> listaVacacion)
/* 275:    */   {
/* 276:460 */     this.listaVacacion = listaVacacion;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public List<PermisoEmpleado> getListaPermisoEmpleado()
/* 280:    */   {
/* 281:469 */     return this.listaPermisoEmpleado;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setListaPermisoEmpleado(List<PermisoEmpleado> listaPermisoEmpleado)
/* 285:    */   {
/* 286:479 */     this.listaPermisoEmpleado = listaPermisoEmpleado;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public int getId()
/* 290:    */   {
/* 291:489 */     return this.idHistoricoEmpleado;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public Date getFechaInicioPeriodo()
/* 295:    */   {
/* 296:499 */     return this.fechaInicioPeriodo;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setFechaInicioPeriodo(Date fechaInicioPeriodo)
/* 300:    */   {
/* 301:509 */     this.fechaInicioPeriodo = fechaInicioPeriodo;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public Date getFechaFinPeriodo()
/* 305:    */   {
/* 306:519 */     return this.fechaFinPeriodo;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setFechaFinPeriodo(Date fechaFinPeriodo)
/* 310:    */   {
/* 311:529 */     this.fechaFinPeriodo = fechaFinPeriodo;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public int getDiasFondoReserva()
/* 315:    */   {
/* 316:538 */     return this.diasFondoReserva;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setDiasFondoReserva(int diasFondoReserva)
/* 320:    */   {
/* 321:548 */     this.diasFondoReserva = diasFondoReserva;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public Date getFechaVencimientoContrato()
/* 325:    */   {
/* 326:557 */     return this.fechaVencimientoContrato;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setFechaVencimientoContrato(Date fechaVencimientoContrato)
/* 330:    */   {
/* 331:567 */     this.fechaVencimientoContrato = fechaVencimientoContrato;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public String getContrato()
/* 335:    */   {
/* 336:576 */     return this.contrato;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setContrato(String contrato)
/* 340:    */   {
/* 341:586 */     this.contrato = contrato;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public String getNumeroFiniquito()
/* 345:    */   {
/* 346:595 */     return this.numeroFiniquito;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void setNumeroFiniquito(String numeroFiniquito)
/* 350:    */   {
/* 351:605 */     this.numeroFiniquito = numeroFiniquito;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public List<DetalleHistoricoEmpleado> getListaDetalleHistoricoEmpleado()
/* 355:    */   {
/* 356:614 */     return this.listaDetalleHistoricoEmpleado;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void setListaDetalleHistoricoEmpleado(List<DetalleHistoricoEmpleado> listaDetalleHistoricoEmpleado)
/* 360:    */   {
/* 361:624 */     this.listaDetalleHistoricoEmpleado = listaDetalleHistoricoEmpleado;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public boolean isIndicadorFiniquito()
/* 365:    */   {
/* 366:628 */     return this.indicadorFiniquito;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setIndicadorFiniquito(boolean indicadorFiniquito)
/* 370:    */   {
/* 371:632 */     this.indicadorFiniquito = indicadorFiniquito;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public BigDecimal getValorUltimaRemuneracion()
/* 375:    */   {
/* 376:636 */     return this.valorUltimaRemuneracion;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setValorUltimaRemuneracion(BigDecimal valorUltimaRemuneracion)
/* 380:    */   {
/* 381:640 */     this.valorUltimaRemuneracion = valorUltimaRemuneracion;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public Rubro getRubroFondoReserva()
/* 385:    */   {
/* 386:644 */     return this.rubroFondoReserva;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void setRubroFondoReserva(Rubro rubroFondoReserva)
/* 390:    */   {
/* 391:648 */     this.rubroFondoReserva = rubroFondoReserva;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public Rubro getRubroDecimoTercero()
/* 395:    */   {
/* 396:652 */     return this.rubroDecimoTercero;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void setRubroDecimoTercero(Rubro rubroDecimoTercero)
/* 400:    */   {
/* 401:656 */     this.rubroDecimoTercero = rubroDecimoTercero;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public Rubro getRubroDecimoCuarto()
/* 405:    */   {
/* 406:660 */     return this.rubroDecimoCuarto;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public void setRubroDecimoCuarto(Rubro rubroDecimoCuarto)
/* 410:    */   {
/* 411:664 */     this.rubroDecimoCuarto = rubroDecimoCuarto;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public List<DetalleFiniquitoEmpleado> getListaDetalleFiniquitoEmpleado()
/* 415:    */   {
/* 416:668 */     return this.listaDetalleFiniquitoEmpleado;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void setListaDetalleFiniquitoEmpleado(List<DetalleFiniquitoEmpleado> listaDetalleFiniquitoEmpleado)
/* 420:    */   {
/* 421:672 */     this.listaDetalleFiniquitoEmpleado = listaDetalleFiniquitoEmpleado;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public int getDiasVacacionesFiniquito()
/* 425:    */   {
/* 426:676 */     return this.diasVacacionesFiniquito;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public void setDiasVacacionesFiniquito(int diasVacacionesFiniquito)
/* 430:    */   {
/* 431:680 */     this.diasVacacionesFiniquito = diasVacacionesFiniquito;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public BigDecimal getSaldoPrestamos()
/* 435:    */   {
/* 436:687 */     return this.saldoPrestamos;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void setSaldoPrestamos(BigDecimal saldoPrestamos)
/* 440:    */   {
/* 441:694 */     this.saldoPrestamos = saldoPrestamos;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public BigDecimal getValorRemuneracionVacacion()
/* 445:    */   {
/* 446:698 */     return this.valorRemuneracionVacacion;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void setValorRemuneracionVacacion(BigDecimal valorRemuneracionVacacion)
/* 450:    */   {
/* 451:702 */     this.valorRemuneracionVacacion = valorRemuneracionVacacion;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public BigDecimal getValorRemuneracionDecimoTercero()
/* 455:    */   {
/* 456:706 */     return this.valorRemuneracionDecimoTercero;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public void setValorRemuneracionDecimoTercero(BigDecimal valorRemuneracionDecimoTercero)
/* 460:    */   {
/* 461:710 */     this.valorRemuneracionDecimoTercero = valorRemuneracionDecimoTercero;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public BigDecimal getValorDesahucio()
/* 465:    */   {
/* 466:714 */     return this.valorDesahucio;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public void setValorDesahucio(BigDecimal valorDesahucio)
/* 470:    */   {
/* 471:718 */     this.valorDesahucio = valorDesahucio;
/* 472:    */   }
/* 473:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.HistoricoEmpleado
 * JD-Core Version:    0.7.0.1
 */