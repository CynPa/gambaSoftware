/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.OneToOne;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Temporal;
/*  20:    */ import javax.persistence.TemporalType;
/*  21:    */ import javax.persistence.Transient;
/*  22:    */ import javax.validation.constraints.Digits;
/*  23:    */ import javax.validation.constraints.Max;
/*  24:    */ import javax.validation.constraints.Min;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="pago_rol_empleado", indexes={@javax.persistence.Index(columnList="id_pago_rol"), @javax.persistence.Index(columnList="id_empleado")})
/*  30:    */ public class PagoRolEmpleado
/*  31:    */   extends EntidadBase
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @Id
/*  35:    */   @TableGenerator(name="pago_rol_empleado", initialValue=0, allocationSize=50)
/*  36:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="pago_rol_empleado")
/*  37:    */   @Column(name="id_pago_rol_empleado")
/*  38:    */   private int idPagoRolEmpleado;
/*  39:    */   @Column(name="id_organizacion", nullable=false)
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="id_sucursal", nullable=false)
/*  42:    */   private int idSucursal;
/*  43:    */   @Column(name="dias_falta", nullable=false)
/*  44:    */   @Min(0L)
/*  45:    */   @Max(30L)
/*  46:    */   private int diasFalta;
/*  47:    */   @Column(name="indicador_cobrado", nullable=true)
/*  48:    */   private boolean indicadorCobrado;
/*  49:    */   @Column(name="indicador_aprobado", nullable=true)
/*  50:    */   private boolean indicadorAprobado;
/*  51:    */   @Column(name="valor_a_pagar", precision=12, scale=2)
/*  52:    */   @Digits(integer=12, fraction=2)
/*  53: 81 */   private BigDecimal valorAPagar = BigDecimal.ZERO;
/*  54:    */   @Column(name="base_imponible_impuesto_renta", precision=12, scale=2)
/*  55:    */   @Digits(integer=12, fraction=2)
/*  56: 85 */   private BigDecimal baseImponibleImpuestoRenta = BigDecimal.ZERO;
/*  57:    */   @Column(name="base_imponible_iees", precision=12, scale=2)
/*  58:    */   @Digits(integer=12, fraction=2)
/*  59: 89 */   private BigDecimal baseImponibleIEES = BigDecimal.ZERO;
/*  60:    */   @Column(name="salario_asignado", precision=12, scale=2, nullable=false)
/*  61:    */   @Digits(integer=12, fraction=2)
/*  62: 93 */   private BigDecimal salarioAsignado = BigDecimal.ZERO;
/*  63:    */   @Column(name="documento_referencia", nullable=false, length=20)
/*  64:    */   @Size(max=20)
/*  65: 97 */   private String documentoReferencia = "";
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_pago_rol", nullable=true)
/*  68:    */   private PagoRol pagoRol;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_empleado", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   private Empleado empleado;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_departamento", nullable=true)
/*  75:    */   private Departamento departamento;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_centro_costo", nullable=true)
/*  78:    */   private DimensionContable centroCosto;
/*  79:    */   @OneToOne(fetch=FetchType.LAZY)
/*  80:    */   @JoinColumn(name="id_pago_cash", nullable=true)
/*  81:    */   private PagoCash pagoCash;
/*  82:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="pagoRolEmpleado")
/*  83:125 */   private List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubro = new ArrayList();
/*  84:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  85:    */   @JoinColumn(name="id_pago_empleado", nullable=true)
/*  86:    */   private PagoEmpleado pagoEmpleado;
/*  87:    */   @OneToOne(fetch=FetchType.LAZY, mappedBy="pagoRolEmpleadoFiniquito")
/*  88:    */   private HistoricoEmpleado historicoEmpleadoFiniquito;
/*  89:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  90:    */   @JoinColumn(name="id_historico_empleado", nullable=true)
/*  91:    */   private HistoricoEmpleado historicoEmpleado;
/*  92:    */   @Column(name="dias_proceso", precision=12, scale=2)
/*  93:    */   @Digits(integer=12, fraction=2)
/*  94:    */   @Min(0L)
/*  95:    */   private int diasProceso;
/*  96:    */   @Column(name="dias_trabajados", precision=12, scale=2)
/*  97:    */   @Digits(integer=12, fraction=2)
/*  98:    */   @Min(0L)
/*  99:    */   private int diasTrabajados;
/* 100:    */   @Transient
/* 101:149 */   private BigDecimal diasTrabajadosReales = BigDecimal.ZERO;
/* 102:    */   @Temporal(TemporalType.DATE)
/* 103:    */   @Column(name="fecha_ingreso_empleado", nullable=true)
/* 104:    */   private Date fechaIngresoEmpleado;
/* 105:    */   @Temporal(TemporalType.DATE)
/* 106:    */   @Column(name="fecha_salida_empleado", nullable=true)
/* 107:    */   private Date fechaSalidaEmpleado;
/* 108:    */   @Transient
/* 109:    */   private boolean traCargadoRubros;
/* 110:    */   @Transient
/* 111:    */   private boolean traSeleccionado;
/* 112:    */   @Transient
/* 113:    */   private Secuencia secuencia;
/* 114:    */   
/* 115:    */   public PagoRolEmpleado() {}
/* 116:    */   
/* 117:    */   public PagoRolEmpleado(Empleado empleado, boolean eliminado)
/* 118:    */   {
/* 119:191 */     this.empleado = empleado;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getIdPagoRolEmpleado()
/* 123:    */   {
/* 124:204 */     return this.idPagoRolEmpleado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setIdPagoRolEmpleado(int idPagoRolEmpleado)
/* 128:    */   {
/* 129:214 */     this.idPagoRolEmpleado = idPagoRolEmpleado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int getIdOrganizacion()
/* 133:    */   {
/* 134:223 */     return this.idOrganizacion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setIdOrganizacion(int idOrganizacion)
/* 138:    */   {
/* 139:233 */     this.idOrganizacion = idOrganizacion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int getIdSucursal()
/* 143:    */   {
/* 144:242 */     return this.idSucursal;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setIdSucursal(int idSucursal)
/* 148:    */   {
/* 149:252 */     this.idSucursal = idSucursal;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public int getDiasFalta()
/* 153:    */   {
/* 154:261 */     return this.diasFalta;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setDiasFalta(int diasFalta)
/* 158:    */   {
/* 159:271 */     this.diasFalta = diasFalta;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public PagoRol getPagoRol()
/* 163:    */   {
/* 164:280 */     return this.pagoRol;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setPagoRol(PagoRol pagoRol)
/* 168:    */   {
/* 169:290 */     this.pagoRol = pagoRol;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Empleado getEmpleado()
/* 173:    */   {
/* 174:299 */     return this.empleado;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setEmpleado(Empleado empleado)
/* 178:    */   {
/* 179:309 */     this.empleado = empleado;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public Departamento getDepartamento()
/* 183:    */   {
/* 184:316 */     return this.departamento;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setDepartamento(Departamento departamento)
/* 188:    */   {
/* 189:324 */     this.departamento = departamento;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public DimensionContable getCentroCosto()
/* 193:    */   {
/* 194:331 */     return this.centroCosto;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setCentroCosto(DimensionContable centroCosto)
/* 198:    */   {
/* 199:339 */     this.centroCosto = centroCosto;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<PagoRolEmpleadoRubro> getListaPagoRolEmpleadoRubro()
/* 203:    */   {
/* 204:348 */     return this.listaPagoRolEmpleadoRubro;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setListaPagoRolEmpleadoRubro(List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubro)
/* 208:    */   {
/* 209:358 */     this.listaPagoRolEmpleadoRubro = listaPagoRolEmpleadoRubro;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean isTraCargadoRubros()
/* 213:    */   {
/* 214:367 */     return this.traCargadoRubros;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setTraCargadoRubros(boolean traCargadoRubros)
/* 218:    */   {
/* 219:377 */     this.traCargadoRubros = traCargadoRubros;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public boolean isIndicadorCobrado()
/* 223:    */   {
/* 224:386 */     return this.indicadorCobrado;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setIndicadorCobrado(boolean indicadorCobrado)
/* 228:    */   {
/* 229:396 */     this.indicadorCobrado = indicadorCobrado;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean isIndicadorAprobado()
/* 233:    */   {
/* 234:405 */     return this.indicadorAprobado;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setIndicadorAprobado(boolean indicadorAprobado)
/* 238:    */   {
/* 239:415 */     this.indicadorAprobado = indicadorAprobado;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public BigDecimal getValorAPagar()
/* 243:    */   {
/* 244:424 */     return this.valorAPagar;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setValorAPagar(BigDecimal valorAPagar)
/* 248:    */   {
/* 249:434 */     this.valorAPagar = valorAPagar;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public PagoEmpleado getPagoEmpleado()
/* 253:    */   {
/* 254:441 */     return this.pagoEmpleado;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setPagoEmpleado(PagoEmpleado pagoEmpleado)
/* 258:    */   {
/* 259:449 */     this.pagoEmpleado = pagoEmpleado;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public boolean isTraSeleccionado()
/* 263:    */   {
/* 264:458 */     return this.traSeleccionado;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setTraSeleccionado(boolean traSeleccionado)
/* 268:    */   {
/* 269:468 */     this.traSeleccionado = traSeleccionado;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public String getDocumentoReferencia()
/* 273:    */   {
/* 274:477 */     return this.documentoReferencia;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 278:    */   {
/* 279:487 */     this.documentoReferencia = documentoReferencia;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public HistoricoEmpleado getHistoricoEmpleadoFiniquito()
/* 283:    */   {
/* 284:496 */     return this.historicoEmpleadoFiniquito;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setHistoricoEmpleadoFiniquito(HistoricoEmpleado historicoEmpleadoFiniquito)
/* 288:    */   {
/* 289:506 */     this.historicoEmpleadoFiniquito = historicoEmpleadoFiniquito;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public BigDecimal getSalarioAsignado()
/* 293:    */   {
/* 294:515 */     return this.salarioAsignado;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setSalarioAsignado(BigDecimal salarioAsignado)
/* 298:    */   {
/* 299:525 */     this.salarioAsignado = salarioAsignado;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public int getId()
/* 303:    */   {
/* 304:535 */     return this.idPagoRolEmpleado;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public Secuencia getSecuencia()
/* 308:    */   {
/* 309:544 */     return this.secuencia;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setSecuencia(Secuencia secuencia)
/* 313:    */   {
/* 314:554 */     this.secuencia = secuencia;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public BigDecimal getBaseImponibleImpuestoRenta()
/* 318:    */   {
/* 319:558 */     return this.baseImponibleImpuestoRenta;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setBaseImponibleImpuestoRenta(BigDecimal baseImponibleImpuestoRenta)
/* 323:    */   {
/* 324:562 */     this.baseImponibleImpuestoRenta = baseImponibleImpuestoRenta;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public int getDiasProceso()
/* 328:    */   {
/* 329:566 */     return this.diasProceso;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setDiasProceso(int diasProceso)
/* 333:    */   {
/* 334:570 */     this.diasProceso = diasProceso;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public PagoCash getPagoCash()
/* 338:    */   {
/* 339:579 */     return this.pagoCash;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setPagoCash(PagoCash pagoCash)
/* 343:    */   {
/* 344:589 */     this.pagoCash = pagoCash;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public int getDiasTrabajados()
/* 348:    */   {
/* 349:598 */     return this.diasTrabajados;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void setDiasTrabajados(int diasTrabajados)
/* 353:    */   {
/* 354:608 */     this.diasTrabajados = diasTrabajados;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public BigDecimal getDiasTrabajadosReales()
/* 358:    */   {
/* 359:612 */     return this.diasTrabajadosReales;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setDiasTrabajadosReales(BigDecimal diasTrabajadosReales)
/* 363:    */   {
/* 364:616 */     this.diasTrabajadosReales = diasTrabajadosReales;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public Date getFechaIngresoEmpleado()
/* 368:    */   {
/* 369:620 */     return this.fechaIngresoEmpleado;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setFechaIngresoEmpleado(Date fechaIngresoEmpleado)
/* 373:    */   {
/* 374:624 */     this.fechaIngresoEmpleado = fechaIngresoEmpleado;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public HistoricoEmpleado getHistoricoEmpleado()
/* 378:    */   {
/* 379:628 */     return this.historicoEmpleado;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setHistoricoEmpleado(HistoricoEmpleado historicoEmpleado)
/* 383:    */   {
/* 384:632 */     this.historicoEmpleado = historicoEmpleado;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public Date getFechaSalidaEmpleado()
/* 388:    */   {
/* 389:636 */     return this.fechaSalidaEmpleado;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setFechaSalidaEmpleado(Date fechaSalidaEmpleado)
/* 393:    */   {
/* 394:640 */     this.fechaSalidaEmpleado = fechaSalidaEmpleado;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public BigDecimal getBaseImponibleIEES()
/* 398:    */   {
/* 399:644 */     return this.baseImponibleIEES;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setBaseImponibleIEES(BigDecimal baseImponibleIEES)
/* 403:    */   {
/* 404:648 */     this.baseImponibleIEES = baseImponibleIEES;
/* 405:    */   }
/* 406:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PagoRolEmpleado
 * JD-Core Version:    0.7.0.1
 */