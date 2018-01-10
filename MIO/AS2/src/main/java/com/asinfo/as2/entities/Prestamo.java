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
/*  26:    */ import javax.validation.constraints.Max;
/*  27:    */ import javax.validation.constraints.Min;
/*  28:    */ import javax.validation.constraints.NotNull;
/*  29:    */ import javax.validation.constraints.Size;
/*  30:    */ 
/*  31:    */ @Entity
/*  32:    */ @Table(name="prestamo")
/*  33:    */ public class Prestamo
/*  34:    */   extends EntidadBase
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = -6727525640004411869L;
/*  37:    */   @Column(name="id_organizacion", nullable=false)
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="id_sucursal", nullable=false)
/*  40:    */   private int idSucursal;
/*  41:    */   @Id
/*  42:    */   @TableGenerator(name="prestamo", initialValue=0, allocationSize=50)
/*  43:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="prestamo")
/*  44:    */   @Column(name="id_prestamo")
/*  45:    */   private int idPrestamo;
/*  46:    */   @Column(name="numero", nullable=true, length=20)
/*  47:    */   @Size(max=20)
/*  48:    */   private String numero;
/*  49:    */   @Column(name="monto", scale=12, precision=2, nullable=false)
/*  50:    */   @NotNull
/*  51:    */   @Digits(integer=12, fraction=2)
/*  52:    */   @Min(0L)
/*  53:    */   private BigDecimal monto;
/*  54:    */   @Column(name="plazo", nullable=false)
/*  55:    */   @Min(0L)
/*  56:    */   @Max(999L)
/*  57:    */   private int plazo;
/*  58:    */   @Column(name="interes", scale=12, precision=2, nullable=false)
/*  59:    */   @NotNull
/*  60:    */   @Digits(integer=12, fraction=2)
/*  61:    */   @Min(0L)
/*  62:    */   @Max(100L)
/*  63:    */   private BigDecimal interes;
/*  64:    */   @Column(name="fecha_solicitud", nullable=false)
/*  65:    */   @Temporal(TemporalType.DATE)
/*  66:    */   private Date fechaSolicitud;
/*  67:    */   @Column(name="fecha_aprobacion", nullable=true)
/*  68:    */   @Temporal(TemporalType.DATE)
/*  69:    */   private Date fechaAprobacion;
/*  70:    */   @Column(name="fecha_inicio_descuento", nullable=true)
/*  71:    */   @Temporal(TemporalType.DATE)
/*  72:    */   private Date fechaInicioDescuento;
/*  73:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  74:    */   @Temporal(TemporalType.DATE)
/*  75:    */   private Date fechaContabilizacion;
/*  76:    */   @Column(name="estado", nullable=false)
/*  77:    */   @Enumerated(EnumType.ORDINAL)
/*  78:    */   private Estado estado;
/*  79:    */   @Column(name="beneficiario", nullable=true)
/*  80:    */   @Size(max=50)
/*  81:    */   private String beneficiario;
/*  82:    */   @Column(name="documento_referencia", nullable=true, length=20)
/*  83:    */   @Size(max=20)
/*  84:    */   private String documentoReferencia;
/*  85:    */   @Column(name="observacion", nullable=true)
/*  86:    */   private String observacion;
/*  87:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  88:    */   @JoinColumn(name="id_empleado", nullable=true)
/*  89:    */   private Empleado empleado;
/*  90:    */   @OneToMany(mappedBy="prestamo", fetch=FetchType.LAZY)
/*  91:136 */   private List<DetallePrestamo> listaDetallePrestamo = new ArrayList();
/*  92:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  93:    */   @JoinColumn(name="id_tipo_prestamo", nullable=true)
/*  94:    */   private TipoPrestamo tipoPrestamo;
/*  95:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  96:    */   @JoinColumn(name="id_forma_pago", nullable=true)
/*  97:    */   private FormaPago formaPago;
/*  98:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  99:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=true)
/* 100:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/* 101:    */   @OneToOne(fetch=FetchType.LAZY)
/* 102:    */   @JoinColumn(name="id_asiento", nullable=true)
/* 103:    */   private Asiento asiento;
/* 104:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 105:    */   @JoinColumn(name="id_prestamo_padre", nullable=true)
/* 106:    */   private Prestamo prestamoPadre;
/* 107:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 108:    */   @JoinColumn(name="id_cuenta_contable", nullable=true)
/* 109:    */   private CuentaContable cuentaContable;
/* 110:    */   @Transient
/* 111:    */   private BigDecimal totalCapitalCuota;
/* 112:    */   @Transient
/* 113:    */   private BigDecimal totalCuota;
/* 114:    */   @Transient
/* 115:    */   private BigDecimal totalInteresCuota;
/* 116:    */   @Transient
/* 117:    */   private boolean indicadorRenegociarPrestamo;
/* 118:    */   @Transient
/* 119:    */   private Secuencia secuencia;
/* 120:    */   
/* 121:    */   public int getId()
/* 122:    */   {
/* 123:200 */     return this.idPrestamo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getIdOrganizacion()
/* 127:    */   {
/* 128:209 */     return this.idOrganizacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setIdOrganizacion(int idOrganizacion)
/* 132:    */   {
/* 133:219 */     this.idOrganizacion = idOrganizacion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getIdSucursal()
/* 137:    */   {
/* 138:228 */     return this.idSucursal;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setIdSucursal(int idSucursal)
/* 142:    */   {
/* 143:238 */     this.idSucursal = idSucursal;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getIdPrestamo()
/* 147:    */   {
/* 148:247 */     return this.idPrestamo;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setIdPrestamo(int idPrestamo)
/* 152:    */   {
/* 153:257 */     this.idPrestamo = idPrestamo;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public BigDecimal getMonto()
/* 157:    */   {
/* 158:266 */     return this.monto;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setMonto(BigDecimal monto)
/* 162:    */   {
/* 163:276 */     this.monto = monto;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public BigDecimal getInteres()
/* 167:    */   {
/* 168:285 */     return this.interes;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setInteres(BigDecimal interes)
/* 172:    */   {
/* 173:295 */     this.interes = interes;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Date getFechaSolicitud()
/* 177:    */   {
/* 178:304 */     return this.fechaSolicitud;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setFechaSolicitud(Date fechaSolicitud)
/* 182:    */   {
/* 183:314 */     this.fechaSolicitud = fechaSolicitud;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Date getFechaAprobacion()
/* 187:    */   {
/* 188:323 */     return this.fechaAprobacion;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setFechaAprobacion(Date fechaAprobacion)
/* 192:    */   {
/* 193:333 */     this.fechaAprobacion = fechaAprobacion;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Empleado getEmpleado()
/* 197:    */   {
/* 198:342 */     return this.empleado;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setEmpleado(Empleado empleado)
/* 202:    */   {
/* 203:352 */     this.empleado = empleado;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public List<DetallePrestamo> getListaDetallePrestamo()
/* 207:    */   {
/* 208:361 */     return this.listaDetallePrestamo;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setListaDetallePrestamo(List<DetallePrestamo> listaDetallePrestamo)
/* 212:    */   {
/* 213:371 */     this.listaDetallePrestamo = listaDetallePrestamo;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public TipoPrestamo getTipoPrestamo()
/* 217:    */   {
/* 218:380 */     return this.tipoPrestamo;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setTipoPrestamo(TipoPrestamo tipoPrestamo)
/* 222:    */   {
/* 223:390 */     this.tipoPrestamo = tipoPrestamo;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public int getPlazo()
/* 227:    */   {
/* 228:399 */     return this.plazo;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setPlazo(int plazo)
/* 232:    */   {
/* 233:409 */     this.plazo = plazo;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public BigDecimal getTotalCapitalCuota()
/* 237:    */   {
/* 238:418 */     return this.totalCapitalCuota;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setTotalCapitalCuota(BigDecimal totalCapitalCuota)
/* 242:    */   {
/* 243:428 */     this.totalCapitalCuota = totalCapitalCuota;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public BigDecimal getTotalCuota()
/* 247:    */   {
/* 248:437 */     this.totalCuota = BigDecimal.ZERO;
/* 249:438 */     for (DetallePrestamo d : getListaDetallePrestamo()) {
/* 250:439 */       if (!d.isEliminado()) {
/* 251:440 */         this.totalCuota = this.totalCuota.add(d.getCuota());
/* 252:    */       }
/* 253:    */     }
/* 254:443 */     return this.totalCuota;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setTotalCuota(BigDecimal totalCuota)
/* 258:    */   {
/* 259:453 */     this.totalCuota = totalCuota;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public BigDecimal getTotalInteresCuota()
/* 263:    */   {
/* 264:462 */     return this.totalInteresCuota;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setTotalInteresCuota(BigDecimal totalInteresCuota)
/* 268:    */   {
/* 269:472 */     this.totalInteresCuota = totalInteresCuota;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public Estado getEstado()
/* 273:    */   {
/* 274:481 */     return this.estado;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setEstado(Estado estado)
/* 278:    */   {
/* 279:491 */     this.estado = estado;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public String getBeneficiario()
/* 283:    */   {
/* 284:500 */     return this.beneficiario;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setBeneficiario(String beneficiario)
/* 288:    */   {
/* 289:510 */     this.beneficiario = beneficiario;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public String getDocumentoReferencia()
/* 293:    */   {
/* 294:519 */     return this.documentoReferencia;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 298:    */   {
/* 299:529 */     this.documentoReferencia = documentoReferencia;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public FormaPago getFormaPago()
/* 303:    */   {
/* 304:538 */     return this.formaPago;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setFormaPago(FormaPago formaPago)
/* 308:    */   {
/* 309:548 */     this.formaPago = formaPago;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 313:    */   {
/* 314:557 */     return this.cuentaBancariaOrganizacion;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 318:    */   {
/* 319:567 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public Asiento getAsiento()
/* 323:    */   {
/* 324:576 */     return this.asiento;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setAsiento(Asiento asiento)
/* 328:    */   {
/* 329:586 */     this.asiento = asiento;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public String getObservacion()
/* 333:    */   {
/* 334:595 */     return this.observacion;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setObservacion(String observacion)
/* 338:    */   {
/* 339:605 */     this.observacion = observacion;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public boolean isIndicadorRenegociarPrestamo()
/* 343:    */   {
/* 344:614 */     return this.indicadorRenegociarPrestamo;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setIndicadorRenegociarPrestamo(boolean indicadorRenegociarPrestamo)
/* 348:    */   {
/* 349:624 */     this.indicadorRenegociarPrestamo = indicadorRenegociarPrestamo;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public Date getFechaInicioDescuento()
/* 353:    */   {
/* 354:633 */     return this.fechaInicioDescuento;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setFechaInicioDescuento(Date fechaInicioDescuento)
/* 358:    */   {
/* 359:643 */     this.fechaInicioDescuento = fechaInicioDescuento;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public Date getFechaContabilizacion()
/* 363:    */   {
/* 364:652 */     return this.fechaContabilizacion;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 368:    */   {
/* 369:662 */     this.fechaContabilizacion = fechaContabilizacion;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public Secuencia getSecuencia()
/* 373:    */   {
/* 374:671 */     return this.secuencia;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void setSecuencia(Secuencia secuencia)
/* 378:    */   {
/* 379:681 */     this.secuencia = secuencia;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public Prestamo getPrestamoPadre()
/* 383:    */   {
/* 384:690 */     return this.prestamoPadre;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void setPrestamoPadre(Prestamo prestamoPadre)
/* 388:    */   {
/* 389:700 */     this.prestamoPadre = prestamoPadre;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public String getNumero()
/* 393:    */   {
/* 394:709 */     return this.numero;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void setNumero(String numero)
/* 398:    */   {
/* 399:719 */     this.numero = numero;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public CuentaContable getCuentaContable()
/* 403:    */   {
/* 404:723 */     return this.cuentaContable;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 408:    */   {
/* 409:727 */     this.cuentaContable = cuentaContable;
/* 410:    */   }
/* 411:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Prestamo
 * JD-Core Version:    0.7.0.1
 */