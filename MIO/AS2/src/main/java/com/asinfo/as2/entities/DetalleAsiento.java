/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.OneToOne;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.Digits;
/*  21:    */ import javax.validation.constraints.Min;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ import javax.validation.constraints.Size;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="detalle_asiento", indexes={@javax.persistence.Index(columnList="id_asiento"), @javax.persistence.Index(columnList="id_dimension_contable1"), @javax.persistence.Index(columnList="id_dimension_contable2"), @javax.persistence.Index(columnList="id_dimension_contable5")})
/*  27:    */ public class DetalleAsiento
/*  28:    */   extends EntidadBase
/*  29:    */   implements Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="detalle_asiento", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_asiento")
/*  35:    */   @Column(name="id_detalle_asiento", unique=true, nullable=false)
/*  36:    */   private int idDetalleAsiento;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  39:    */   private Asiento asiento;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_cuenta_contable", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private CuentaContable cuentaContable;
/*  44:    */   @Column(name="id_organizacion", nullable=false)
/*  45:    */   private int idOrganizacion;
/*  46:    */   @Column(name="id_sucursal", nullable=false)
/*  47:    */   private int idSucursal;
/*  48:    */   @Column(name="debe", precision=12, scale=2)
/*  49:    */   @Digits(integer=12, fraction=2)
/*  50:    */   @Min(0L)
/*  51: 61 */   private BigDecimal debe = BigDecimal.ZERO
/*  52:    */   
/*  53:    */ 
/*  54: 64 */     .setScale(0);
/*  55:    */   @Column(name="haber", precision=12, scale=2)
/*  56:    */   @Digits(integer=12, fraction=2)
/*  57:    */   @Min(0L)
/*  58: 66 */   private BigDecimal haber = BigDecimal.ZERO
/*  59:    */   
/*  60:    */ 
/*  61: 69 */     .setScale(0);
/*  62:    */   @Column(name="descripcion", length=200)
/*  63:    */   @Size(max=200)
/*  64:    */   private String descripcion;
/*  65:    */   @Column(name="descripcion2", length=200)
/*  66:    */   @Size(max=200)
/*  67:    */   private String descripcion2;
/*  68:    */   @OneToOne(mappedBy="detalleAsiento", fetch=FetchType.LAZY)
/*  69:    */   private MovimientoBancario movimientoBancario;
/*  70:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  71:    */   @JoinColumn(name="id_dimension_contable1")
/*  72:    */   private DimensionContable dimensionContable1;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_dimension_contable2")
/*  75:    */   private DimensionContable dimensionContable2;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_dimension_contable3")
/*  78:    */   private DimensionContable dimensionContable3;
/*  79:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  80:    */   @JoinColumn(name="id_dimension_contable4")
/*  81:    */   private DimensionContable dimensionContable4;
/*  82:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  83:    */   @JoinColumn(name="id_dimension_contable5")
/*  84:    */   private DimensionContable dimensionContable5;
/*  85:    */   @Transient
/*  86:    */   private String concepto;
/*  87:    */   @Transient
/*  88:    */   private String tipoAsiento;
/*  89:    */   @Transient
/*  90:    */   private String numero;
/*  91:    */   @Transient
/*  92:    */   private Date fecha;
/*  93:    */   @Transient
/*  94:    */   private String nombreCuentaContable;
/*  95:    */   @Transient
/*  96:    */   private String codigoCuenta;
/*  97:    */   @Transient
/*  98:    */   private String nombrePartidaPresupuestaria;
/*  99:    */   @Transient
/* 100:    */   private String codigoPartidaPresupuestaria;
/* 101:    */   @Transient
/* 102:    */   private String documentoReferencia;
/* 103:    */   @Transient
/* 104:    */   private String beneficiario;
/* 105:    */   @Transient
/* 106:132 */   private BigDecimal saldo = BigDecimal.ZERO;
/* 107:    */   
/* 108:    */   public DetalleAsiento() {}
/* 109:    */   
/* 110:    */   public DetalleAsiento(CuentaContable cuentaContable, BigDecimal debe, BigDecimal haber)
/* 111:    */   {
/* 112:146 */     this.cuentaContable = cuentaContable;
/* 113:147 */     this.debe = debe;
/* 114:148 */     this.haber = haber;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public DetalleAsiento(Integer idCuentaContable, BigDecimal debe, BigDecimal haber, String descripcion)
/* 118:    */   {
/* 119:152 */     this(new CuentaContable(idCuentaContable.intValue()), debe, haber);
/* 120:153 */     this.descripcion = descripcion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public DetalleAsiento(Integer idCuentaContable, String codigoCuenta, String nombreCuenta, boolean indicadorMovimiento, BigDecimal debe, BigDecimal haber, String descripcion)
/* 124:    */   {
/* 125:158 */     this(new CuentaContable(idCuentaContable.intValue(), codigoCuenta, nombreCuenta, indicadorMovimiento), debe, haber);
/* 126:159 */     this.descripcion = descripcion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public DetalleAsiento(Integer idCuenta, String codigo, String nombre, boolean indicadorValidarDistribucion, Integer idDimensionContable1, String numeroDimensionContable1, String codigoDimensionContable1, String nombreDimensionContable1, Integer idDimensionContable2, String numeroDimensionContable2, String codigoDimensionContable2, String nombreDimensionContable2, Integer idDimensionContable3, String numeroDimensionContable3, String codigoDimensionContable3, String nombreDimensionContable3, Integer idDimensionContable4, String numeroDimensionContable4, String codigoDimensionContable4, String nombreDimensionContable4, Integer idDimensionContable5, String numeroDimensionContable5, String codigoDimensionContable5, String nombreDimensionContable5, BigDecimal valor, boolean indicadorMovimiento)
/* 130:    */   {
/* 131:171 */     this.cuentaContable = new CuentaContable(idCuenta.intValue(), codigo, nombre, indicadorValidarDistribucion);
/* 132:172 */     this.cuentaContable.setIndicadorMovimiento(indicadorMovimiento);
/* 133:173 */     this.dimensionContable1 = (idDimensionContable1 != null ? new DimensionContable(idDimensionContable1.intValue(), numeroDimensionContable1, codigoDimensionContable1, nombreDimensionContable1) : null);
/* 134:    */     
/* 135:175 */     this.dimensionContable2 = (idDimensionContable2 != null ? new DimensionContable(idDimensionContable2.intValue(), numeroDimensionContable2, codigoDimensionContable2, nombreDimensionContable2) : null);
/* 136:    */     
/* 137:177 */     this.dimensionContable3 = (idDimensionContable3 != null ? new DimensionContable(idDimensionContable3.intValue(), numeroDimensionContable3, codigoDimensionContable3, nombreDimensionContable3) : null);
/* 138:    */     
/* 139:179 */     this.dimensionContable4 = (idDimensionContable4 != null ? new DimensionContable(idDimensionContable4.intValue(), numeroDimensionContable4, codigoDimensionContable4, nombreDimensionContable4) : null);
/* 140:    */     
/* 141:181 */     this.dimensionContable5 = (idDimensionContable5 != null ? new DimensionContable(idDimensionContable5.intValue(), numeroDimensionContable5, codigoDimensionContable5, nombreDimensionContable5) : null);
/* 142:184 */     if (valor.compareTo(BigDecimal.ZERO) >= 0)
/* 143:    */     {
/* 144:185 */       this.debe = valor;
/* 145:186 */       this.haber = BigDecimal.ZERO;
/* 146:    */     }
/* 147:    */     else
/* 148:    */     {
/* 149:188 */       this.haber = valor.negate();
/* 150:189 */       this.debe = BigDecimal.ZERO;
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public DetalleAsiento(CuentaContable cuentaContable, BigDecimal valor)
/* 155:    */   {
/* 156:198 */     this.cuentaContable = cuentaContable;
/* 157:199 */     if (valor.compareTo(BigDecimal.ZERO) >= 0)
/* 158:    */     {
/* 159:200 */       this.debe = valor;
/* 160:201 */       this.haber = BigDecimal.ZERO;
/* 161:    */     }
/* 162:    */     else
/* 163:    */     {
/* 164:203 */       this.haber = valor.abs();
/* 165:204 */       this.debe = BigDecimal.ZERO;
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public DetalleAsiento(Asiento asiento, CuentaContable cuentaContable, BigDecimal valor, String descripcion)
/* 170:    */   {
/* 171:209 */     this(cuentaContable, valor);
/* 172:210 */     this.asiento = asiento;
/* 173:211 */     this.descripcion = descripcion;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public DetalleAsiento(Asiento asiento, CuentaContable cuentaContable, BigDecimal valor, String descripcion, String descripcion2)
/* 177:    */   {
/* 178:215 */     this(asiento, cuentaContable, valor, descripcion);
/* 179:216 */     this.descripcion2 = descripcion2;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public DetalleAsiento(BigDecimal debe, BigDecimal haber, String descripcion, String concepto, String numero, Date fecha, String nombreCuentaContable, String codigoCuenta)
/* 183:    */   {
/* 184:231 */     this.debe = debe;
/* 185:232 */     this.haber = haber;
/* 186:233 */     this.descripcion = descripcion;
/* 187:234 */     this.concepto = concepto;
/* 188:235 */     this.numero = numero;
/* 189:236 */     this.fecha = fecha;
/* 190:237 */     this.nombreCuentaContable = nombreCuentaContable;
/* 191:238 */     this.codigoCuenta = codigoCuenta;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public DetalleAsiento(BigDecimal debe, BigDecimal haber, String descripcion, String concepto, String numero, Date fecha, String tipoAsiento)
/* 195:    */   {
/* 196:251 */     this.debe = debe;
/* 197:252 */     this.haber = haber;
/* 198:253 */     this.descripcion = descripcion;
/* 199:254 */     this.concepto = concepto;
/* 200:255 */     this.numero = numero;
/* 201:256 */     this.fecha = fecha;
/* 202:257 */     this.tipoAsiento = tipoAsiento;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public DetalleAsiento(BigDecimal debe, BigDecimal haber, String descripcion, String concepto, String tipoAsiento, String numero, Date fecha, String documentoReferencia, String beneficiario)
/* 206:    */   {
/* 207:272 */     this.debe = debe;
/* 208:273 */     this.haber = haber;
/* 209:274 */     this.descripcion = descripcion;
/* 210:275 */     this.concepto = concepto;
/* 211:276 */     this.tipoAsiento = tipoAsiento;
/* 212:277 */     this.numero = numero;
/* 213:278 */     this.fecha = fecha;
/* 214:279 */     this.documentoReferencia = documentoReferencia;
/* 215:280 */     this.beneficiario = beneficiario;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public DetalleAsiento(BigDecimal debe, BigDecimal haber, String descripcion, String concepto, String tipoAsiento, String numero, Date fecha, String documentoReferencia, String beneficiario, String descripcion2, String usuarioCreacion, String usuarioModificacion, Date fechaCreacion, Date fechaModificacion)
/* 219:    */   {
/* 220:286 */     this.debe = debe;
/* 221:287 */     this.haber = haber;
/* 222:288 */     this.descripcion = descripcion;
/* 223:289 */     this.concepto = concepto;
/* 224:290 */     this.tipoAsiento = tipoAsiento;
/* 225:291 */     this.numero = numero;
/* 226:292 */     this.fecha = fecha;
/* 227:293 */     this.documentoReferencia = documentoReferencia;
/* 228:294 */     this.beneficiario = beneficiario;
/* 229:295 */     this.descripcion2 = descripcion2;
/* 230:296 */     this.usuarioCreacion = usuarioCreacion;
/* 231:297 */     this.usuarioModificacion = usuarioModificacion;
/* 232:298 */     this.fechaCreacion = fechaCreacion;
/* 233:299 */     this.fechaModificacion = fechaModificacion;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public DetalleAsiento(int idCuentaContable, BigDecimal debe, BigDecimal haber, String descripcion, String concepto, String tipoAsiento, String numero, Date fecha, String documentoReferencia, String beneficiario)
/* 237:    */   {
/* 238:304 */     this(debe, haber, descripcion, concepto, tipoAsiento, numero, fecha, documentoReferencia, beneficiario);
/* 239:305 */     this.cuentaContable = new CuentaContable(idCuentaContable);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public DetalleAsiento(int idCuentaContable, BigDecimal debe, BigDecimal haber, String descripcion, String concepto, String tipoAsiento, String numero, Date fecha, String documentoReferencia, String beneficiario, String descripcion2, String usuarioCreacion, String usuarioModificacion, Date fechaCreacion, Date fechaModificacion)
/* 243:    */   {
/* 244:311 */     this(debe, haber, descripcion, concepto, tipoAsiento, numero, fecha, documentoReferencia, beneficiario, descripcion2, usuarioCreacion, usuarioModificacion, fechaCreacion, fechaModificacion);
/* 245:    */     
/* 246:313 */     this.cuentaContable = new CuentaContable(idCuentaContable);
/* 247:    */   }
/* 248:    */   
/* 249:    */   public DetalleAsiento(BigDecimal debe, BigDecimal haber, String descripcion, String concepto, String numero, Date fecha, String tipoAsiento, String nombreCuentaContable, String codigoCuenta)
/* 250:    */   {
/* 251:327 */     this.debe = debe;
/* 252:328 */     this.haber = haber;
/* 253:329 */     this.descripcion = descripcion;
/* 254:330 */     this.concepto = concepto;
/* 255:331 */     this.numero = numero;
/* 256:332 */     this.fecha = fecha;
/* 257:333 */     this.tipoAsiento = tipoAsiento;
/* 258:334 */     this.nombreCuentaContable = nombreCuentaContable;
/* 259:335 */     this.codigoCuenta = codigoCuenta;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public DetalleAsiento(BigDecimal debe, BigDecimal haber, String descripcion, String concepto, String numero, Date fecha, String tipoAsiento, String nombreCuentaContable, String codigoCuenta, String nombrePartidaPresupuestaria, String codigoPartidaPresupuestaria)
/* 263:    */   {
/* 264:349 */     this.debe = debe;
/* 265:350 */     this.haber = haber;
/* 266:351 */     this.descripcion = descripcion;
/* 267:352 */     this.concepto = concepto;
/* 268:353 */     this.numero = numero;
/* 269:354 */     this.fecha = fecha;
/* 270:355 */     this.tipoAsiento = tipoAsiento;
/* 271:356 */     this.nombreCuentaContable = nombreCuentaContable;
/* 272:357 */     this.codigoCuenta = codigoCuenta;
/* 273:358 */     this.codigoPartidaPresupuestaria = codigoPartidaPresupuestaria;
/* 274:359 */     this.nombrePartidaPresupuestaria = nombrePartidaPresupuestaria;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public DetalleAsiento(CuentaContable cuentaContable, BigDecimal debe, BigDecimal haber, String concepto, String documentoReferencia)
/* 278:    */   {
/* 279:363 */     this.cuentaContable = cuentaContable;
/* 280:364 */     this.debe = debe;
/* 281:365 */     this.haber = haber;
/* 282:366 */     this.concepto = concepto;
/* 283:367 */     this.documentoReferencia = documentoReferencia;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public List<String> getCamposAuditables()
/* 287:    */   {
/* 288:377 */     ArrayList<String> lista = new ArrayList();
/* 289:    */     
/* 290:379 */     lista.add("debe");
/* 291:380 */     lista.add("haber");
/* 292:    */     
/* 293:382 */     return lista;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public int getId()
/* 297:    */   {
/* 298:393 */     return getIdDetalleAsiento();
/* 299:    */   }
/* 300:    */   
/* 301:    */   public int getIdDetalleAsiento()
/* 302:    */   {
/* 303:402 */     return this.idDetalleAsiento;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setIdDetalleAsiento(int idDetalleAsiento)
/* 307:    */   {
/* 308:412 */     this.idDetalleAsiento = idDetalleAsiento;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public Asiento getAsiento()
/* 312:    */   {
/* 313:421 */     return this.asiento;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setAsiento(Asiento asiento)
/* 317:    */   {
/* 318:431 */     this.asiento = asiento;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public CuentaContable getCuentaContable()
/* 322:    */   {
/* 323:440 */     return this.cuentaContable;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 327:    */   {
/* 328:450 */     this.cuentaContable = cuentaContable;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public int getIdOrganizacion()
/* 332:    */   {
/* 333:459 */     return this.idOrganizacion;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setIdOrganizacion(int idOrganizacion)
/* 337:    */   {
/* 338:469 */     this.idOrganizacion = idOrganizacion;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public int getIdSucursal()
/* 342:    */   {
/* 343:478 */     return this.idSucursal;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setIdSucursal(int idSucursal)
/* 347:    */   {
/* 348:488 */     this.idSucursal = idSucursal;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public BigDecimal getDebe()
/* 352:    */   {
/* 353:497 */     return this.debe;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setDebe(BigDecimal debe)
/* 357:    */   {
/* 358:507 */     this.debe = debe;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public BigDecimal getHaber()
/* 362:    */   {
/* 363:516 */     return this.haber;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setHaber(BigDecimal haber)
/* 367:    */   {
/* 368:526 */     this.haber = haber;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public String getDescripcion()
/* 372:    */   {
/* 373:535 */     return this.descripcion;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setDescripcion(String descripcion)
/* 377:    */   {
/* 378:545 */     this.descripcion = (descripcion.length() > 200 ? descripcion.substring(0, 200) : descripcion == null ? "" : descripcion);
/* 379:    */   }
/* 380:    */   
/* 381:    */   public String getConcepto()
/* 382:    */   {
/* 383:549 */     return this.concepto;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void setConcepto(String concepto)
/* 387:    */   {
/* 388:553 */     this.concepto = concepto;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public String getNumero()
/* 392:    */   {
/* 393:557 */     return this.numero;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void setNumero(String numero)
/* 397:    */   {
/* 398:561 */     this.numero = numero;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public Date getFecha()
/* 402:    */   {
/* 403:565 */     return this.fecha;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setFecha(Date fecha)
/* 407:    */   {
/* 408:569 */     this.fecha = fecha;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public String getNombreCuentaContable()
/* 412:    */   {
/* 413:573 */     return this.nombreCuentaContable;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setNombreCuentaContable(String nombreCuentaContable)
/* 417:    */   {
/* 418:577 */     this.nombreCuentaContable = nombreCuentaContable;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public String getCodigoCuenta()
/* 422:    */   {
/* 423:581 */     return this.codigoCuenta;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setCodigoCuenta(String codigoCuenta)
/* 427:    */   {
/* 428:585 */     this.codigoCuenta = codigoCuenta;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public String getTipoAsiento()
/* 432:    */   {
/* 433:589 */     return this.tipoAsiento;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setTipoAsiento(String tipoAsiento)
/* 437:    */   {
/* 438:593 */     this.tipoAsiento = tipoAsiento;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public MovimientoBancario getMovimientoBancario()
/* 442:    */   {
/* 443:597 */     return this.movimientoBancario;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setMovimientoBancario(MovimientoBancario movimientoBancario)
/* 447:    */   {
/* 448:601 */     this.movimientoBancario = movimientoBancario;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public String getNombrePartidaPresupuestaria()
/* 452:    */   {
/* 453:610 */     return this.nombrePartidaPresupuestaria;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setNombrePartidaPresupuestaria(String nombrePartidaPresupuestaria)
/* 457:    */   {
/* 458:620 */     this.nombrePartidaPresupuestaria = nombrePartidaPresupuestaria;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public String getCodigoPartidaPresupuestaria()
/* 462:    */   {
/* 463:629 */     return this.codigoPartidaPresupuestaria;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setCodigoPartidaPresupuestaria(String codigoPartidaPresupuestaria)
/* 467:    */   {
/* 468:639 */     this.codigoPartidaPresupuestaria = codigoPartidaPresupuestaria;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public String getDocumentoReferencia()
/* 472:    */   {
/* 473:648 */     return this.documentoReferencia;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 477:    */   {
/* 478:658 */     this.documentoReferencia = documentoReferencia;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public String getBeneficiario()
/* 482:    */   {
/* 483:667 */     return this.beneficiario;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void setBeneficiario(String beneficiario)
/* 487:    */   {
/* 488:677 */     this.beneficiario = beneficiario;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public DimensionContable getDimensionContable1()
/* 492:    */   {
/* 493:681 */     return this.dimensionContable1;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void setDimensionContable1(DimensionContable dimensionContable1)
/* 497:    */   {
/* 498:685 */     this.dimensionContable1 = dimensionContable1;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public DimensionContable getDimensionContable2()
/* 502:    */   {
/* 503:689 */     return this.dimensionContable2;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public void setDimensionContable2(DimensionContable dimensionContable2)
/* 507:    */   {
/* 508:693 */     this.dimensionContable2 = dimensionContable2;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public DimensionContable getDimensionContable3()
/* 512:    */   {
/* 513:697 */     return this.dimensionContable3;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public void setDimensionContable3(DimensionContable dimensionContable3)
/* 517:    */   {
/* 518:701 */     this.dimensionContable3 = dimensionContable3;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public DimensionContable getDimensionContable4()
/* 522:    */   {
/* 523:705 */     return this.dimensionContable4;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public void setDimensionContable4(DimensionContable dimensionContable4)
/* 527:    */   {
/* 528:709 */     this.dimensionContable4 = dimensionContable4;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public DimensionContable getDimensionContable5()
/* 532:    */   {
/* 533:713 */     return this.dimensionContable5;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public void setDimensionContable5(DimensionContable dimensionContable5)
/* 537:    */   {
/* 538:717 */     this.dimensionContable5 = dimensionContable5;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public BigDecimal getSaldo()
/* 542:    */   {
/* 543:721 */     return this.saldo;
/* 544:    */   }
/* 545:    */   
/* 546:    */   public void setSaldo(BigDecimal saldo)
/* 547:    */   {
/* 548:725 */     this.saldo = saldo;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public String getDescripcion2()
/* 552:    */   {
/* 553:729 */     return this.descripcion2;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public void setDescripcion2(String descripcion2)
/* 557:    */   {
/* 558:733 */     this.descripcion2 = descripcion2;
/* 559:    */   }
/* 560:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleAsiento
 * JD-Core Version:    0.7.0.1
 */